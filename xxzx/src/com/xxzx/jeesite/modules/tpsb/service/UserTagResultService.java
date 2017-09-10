package com.xxzx.jeesite.modules.tpsb.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.persistence.Page;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.common.utils.DateUtils;
import com.xxzx.jeesite.modules.tpsb.dao.UserTagResultDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserTagResult;
@Service
@Transactional(readOnly = true)
public class UserTagResultService extends CrudService<UserTagResultDao,UserTagResult>{
	
	@Autowired
	private UserTagResultDao dao;
	
	//获取用户标记情况
	public List<UserTagResult> getAllUserTagResult(UserTagResult entity){
		 
		List<UserTagResult> writeUserResult = dao.findWriteUserResult(entity);
		List<UserTagResult> selectUserResult = dao.findSelectUserResult(entity);
		
		List<UserTagResult> AllUserTagResult = null ;
		
		if(writeUserResult!=null &&selectUserResult!=null &&writeUserResult.size()>0 &&selectUserResult.size()>0){
			AllUserTagResult = new ArrayList<UserTagResult>();
			
			AllUserTagResult = writeUserResult;
			int i = 0;
			for(UserTagResult wuserResult:writeUserResult){
				
				for(int j=0; j<selectUserResult.size();j++){
					UserTagResult suserResult = selectUserResult.get(j);
					//对比填写的用户和选择的用户，如果相同把选择用户的次数 存入 填写用户中
					if(wuserResult.getName().equals(suserResult.getName()) ){
						AllUserTagResult.get(i).setSelectNum(suserResult.getSelectNum());
						selectUserResult.remove(j);
					}
				}
				i++;
			}
			if(selectUserResult.size()>0){
				for (UserTagResult userTagResult : selectUserResult) {
					AllUserTagResult.add(userTagResult);
				}
			}
			
			this.getUserSuccRecord(AllUserTagResult);
			
		}
		
		
		if(writeUserResult!=null && selectUserResult.size()==0){
			this.getUserSuccRecord(writeUserResult);
			return writeUserResult;
		}
		
		
		System.out.println(AllUserTagResult);
		
		return AllUserTagResult;
	}
	
	//获取用户成功填写记录
	private List<UserTagResult> getUserSuccRecord(List<UserTagResult> AllUserTagResult){
		//获取用户成功填写记录
		List<UserTagResult> userSuccRecord = dao.findUserSuccRecord();
		if(userSuccRecord!=null&&userSuccRecord.size()>0){
			
			for ( int j=0;j<AllUserTagResult.size();j++) {
				UserTagResult userTagResult = AllUserTagResult.get(j);
				for (UserTagResult userTagSuccResult : userSuccRecord) {
					if(userTagSuccResult.getUserId().equals(userTagResult.getUserId())){
						userTagResult.setWriteSuccNum(userTagSuccResult.getWriteSuccNum());
						userTagResult.setSelectSuccNum(userTagSuccResult.getSelectSuccNum());
						userTagResult.setJurisdicStatus(userTagSuccResult.getJurisdicStatus());
					}
				}
			}
		}
		return AllUserTagResult;
	}

	public UserTagResult getUserTagResultByDate(String userId,String startDate,String endDate) {
		UserTagResult user = dao.getUserTagResult(userId, startDate, endDate);
		List<Double> nums = dao.getSuccWriteAndSelect(userId, startDate, endDate);
		if(user!=null &&nums!=null&&nums.size()==2){
			user.setWriteSuccNum(nums.get(0));
			user.setSelectSuccNum(nums.get(1));
		}
		return user;
	}

	@Override
	public Page<UserTagResult> findPage(Page<UserTagResult> page,
			UserTagResult entity) {
		// TODO Auto-generated method stub
		entity.setPage(page);
		List<UserTagResult> list = this.getAllUserTagResult(entity);
		for (UserTagResult userTagResult : list) {
//			int jurisdicStatus = userTagResult.getJurisdicStatus();
//			if(jurisdicStatus==1){
//				userTagResult.setJurisdicStatusName("优先填写");
//			}else if(jurisdicStatus==2){
//				userTagResult.setJurisdicStatusName("优选选择");
//			}else if(jurisdicStatus==3){
//				userTagResult.setJurisdicStatusName("禁止填写");
//			}else if(jurisdicStatus==4){
//				userTagResult.setJurisdicStatusName("禁止选择");
//			}else if(jurisdicStatus==5){
//				userTagResult.setJurisdicStatusName("禁止填写选择");
//			}else{
//				userTagResult.setJurisdicStatusName("正常");
//			}
			
			if(userTagResult.getWriteSuccNum()!=0.0 && userTagResult.getWriteNum()!=0.0){
				userTagResult.setWriteSuccRate(userTagResult.getWriteSuccNum()/userTagResult.getWriteNum()*100);
			}
			if(userTagResult.getSelectSuccNum()!=0.0&&userTagResult.getSelectNum()!=0.0){
				userTagResult.setSelectSuccRate(userTagResult.getSelectSuccNum()/userTagResult.getSelectNum()*100);
			}
		}
		page.setList(list);
		return page;
	}
	
	public Page<UserLabel> findUserLabelsPage(Page<UserLabel> page,
			UserLabel entity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(entity.getBeginDateString()!=null){
			try {
				Date beginDate = sdf.parse(entity.getBeginDateString());
				entity.setBeginDate(beginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(entity.getEndDateString()!=null){
			try {
				Date endDate = sdf.parse(entity.getEndDateString());
				entity.setEndDate(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		// 设置默认时间范围，默认当前月
		if (entity.getBeginDate() == null){
			entity.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (entity.getEndDate() == null){
			entity.setEndDate(DateUtils.addMonths(entity.getBeginDate(), 1));
		}
		entity.setPage(page);
		List<UserLabel> list = this.findUserLabelsByUserId(entity);
//		list.addAll(this.findUserSelectLabelsByUserId(entity));
		page.setList(list);
		return page;
	}
	

	//获取用户所有填写标签情况
	public List<UserLabel> findUserLabelsByUserId(UserLabel userLabel){
		List<UserLabel> list = dao.findUserLabelsByUserId(userLabel);
		//获取 所有的图片id
		List<String> picIds = new ArrayList<String>();
		for (UserLabel userlabel : list) {
			picIds.add(userlabel.getPicId());
		}
		HashSet h = new HashSet(picIds);
		picIds.clear();
		picIds.addAll(h);
		UserLabel resultUserLabel;
		List<UserLabel> resultUserLabels = new ArrayList<UserLabel>();
		for (String picId : picIds) {
			resultUserLabel = new UserLabel();
			resultUserLabel.setPicId(picId);
			resultUserLabel.setId(userLabel.getId());
			List<String> allLabels = new ArrayList<String>();
			for (UserLabel userlabel : list) {
				if(picId.equals(userlabel.getPicId())){
					allLabels.add(userlabel.getLabel());
					resultUserLabel.setUrl(userlabel.getUrl());
					resultUserLabel.setPicName(userlabel.getPicName());
				}
			}
			List<String> selectLabels = dao.getSelectLabelByUserIdAndPicId(resultUserLabel);
			allLabels.addAll(selectLabels);
			HashSet h1 = new HashSet(allLabels);
			allLabels.clear();
			allLabels.addAll(h1);
			
			List<String> succLabels = dao.getSuccLabelByUserIdAndPicId(resultUserLabel);
//			allLabels.removeAll(succLabels);
			allLabels.removeAll(succLabels);
			resultUserLabel.setErrorLabels(allLabels);
			resultUserLabel.setSuccLabels(succLabels);
			resultUserLabels.add(resultUserLabel);
		}
		return resultUserLabels;
	}
	
		//获取用户所有填写标签情况
	public List<UserLabel> findUserSelectLabelsByUserId(UserLabel userLabel){
		return dao.findUserSelectLabelsByUserId(userLabel);
	}

	
	//获取用户数据请款
	public List<UserTagResult> getUserTagByDate(String userId,String startDate,String endDate) {
		List<UserTagResult> list = dao.getUserTagByDate(userId,startDate,endDate);
		for (UserTagResult userTagResult : list) {
			userTagResult.setWriteSuccNum(dao.getUserWriteSuccTagByDate(userId, userTagResult.getNowdate()));
			userTagResult.setSelectSuccNum(dao.getUserSelectSuccTagByDate(userId, userTagResult.getNowdate()));
		}
		return list;
	}
	
	
	//获取图片标签化结果
	public List<UserLabel> findAllPicResult(){
		List<UserLabel> picResults = dao.findAllPicResult();
		System.out.println(picResults.size());
		return null;
	}
	
	
	
}
