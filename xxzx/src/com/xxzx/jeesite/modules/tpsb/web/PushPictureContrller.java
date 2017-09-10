package com.xxzx.jeesite.modules.tpsb.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.utils.CacheUtils;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.sys.utils.UserUtils;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
import com.xxzx.jeesite.modules.tpsb.service.PushPictureService;

@Controller
@RequestMapping(value="${adminPath}/tpsb")
public class PushPictureContrller extends BaseController{
	@Autowired
	private PushPictureService service;
	
	@RequestMapping("/pushPicture")
	@ResponseBody
	public Map<String,Object> checkUserPermissions(PushPicture pushPicture,HttpServletRequest request){
		
		//设置全局缓存
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> allUserId = (ArrayList<String>)CacheUtils.get(nowDate);
		if(allUserId==null){
			allUserId = new ArrayList<String>();
			allUserId.add(pushPicture.getCurrentUser().getId());
			CacheUtils.put(nowDate, allUserId);
		}else if(allUserId.size()>0 && !allUserId.contains(pushPicture.getCurrentUser().getId())){
			allUserId.add(pushPicture.getCurrentUser().getId());
		}
		
		
		//初始化用户缓存
		String userKey = pushPicture.getCurrentUser().getId()+"-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Object object = UserUtils.getCache(userKey);
		if(object ==null){
			UserUtils.putCache(userKey, new ArrayList<String>());
		}
		
		String userId = pushPicture.getCurrentUser().getId();
		//获取用户权限标识
		String userPermiaStatus = service.getUserPermisStatusByUserId(userId);
		
		if(userPermiaStatus == null || "6" .equals(userPermiaStatus) || "0".equals(userPermiaStatus)){
			return this.pushPicture(pushPicture, request);
		}else if("3".equals(userPermiaStatus)){
			return this.pushSelectPicture(pushPicture, request,0);
		}else if("4".equals(userPermiaStatus)){
			return this.pushWritePicture(pushPicture, request,0);
		}else if("1".equals(userPermiaStatus)){
			Map<String,Object> writePictureMap = this.pushWritePicture(pushPicture, request,1);
			List<PushPicture> wirtePictureList = (List<PushPicture>)writePictureMap.get("picture");
			if(wirtePictureList!=null && wirtePictureList.size()>0){
				return writePictureMap;
			}else{
				Map<String,Object> selectPictureMap =this.pushSelectPicture(pushPicture, request,0);
				UserUtils.removeCache(userKey);
				return selectPictureMap;
			}
		}else if("2".equals(userPermiaStatus)){
			Map<String,Object> selectPictureMap = this.pushSelectPicture(pushPicture, request,1);
			List<PushPicture> selectPictureList = (List<PushPicture>)selectPictureMap.get("picture");
			if(selectPictureList!=null && selectPictureList.size()>0){
				return selectPictureMap;
			}else{
				Map<String,Object> writePictureMap =this.pushWritePicture(pushPicture, request,0);
//				UserUtils.removeCache(userKey);
				return writePictureMap;
			}
		}else if("5".equals(userPermiaStatus)){
			return this.pushNULLPicture(pushPicture, request);
		}
		return null;
	}
	
	
	//禁止填写选择
	public Map<String,Object> pushNULLPicture(PushPicture pushPicture,HttpServletRequest request){
		Map<String,Object> map = Maps.newLinkedHashMap();
		map.put("picture", new ArrayList<UploadPicture>());
		return map;
	}
	
	//只填写
	public Map<String,Object> pushWritePicture(PushPicture pushPicture,HttpServletRequest request,int firstStatus){
		Map<String,Object> map = Maps.newLinkedHashMap();
		List<UploadPicture> list = service.pushWritePicture(pushPicture,firstStatus);
		map.put("picture", list);
		return map;
	}
	
	//只选择
	public Map<String,Object> pushSelectPicture(PushPicture pushPicture,HttpServletRequest request,int firstStatus){
		Map<String,Object> map = Maps.newLinkedHashMap();
		List<UploadPicture> list = service.pushSelectPicture(pushPicture,firstStatus); 
		map.put("picture", list);
		return map;
	}
	
	
	//正常获取图片
	public Map<String,Object> pushPicture(PushPicture pushPicture,HttpServletRequest request){
		
		Map<String,Object> map = Maps.newLinkedHashMap();
		
//		List<String> picIdList = (ArrayList<String>)CacheUtils.get(userKey);
//		System.out.println(picIdList.size());
		
		
		//完成任务
		List<UploadPicture> task = service.findPictureByTask(pushPicture);
		if(task!=null&&task.size()>0){
			map.put("picture", task);
			return map;
		}
		
		
		//根据用户历史填过的便签   搜索solr获取图片信息
		List<UploadPicture> list = service.findPictureByUserIdAndSolr(pushPicture);
		if(list!=null&&list.size()>0){
			map.put("picture", list);
			return map;
		}
		
		
		//根据用户喜欢的分类获取图片
		List<UploadPicture> catList = service.findPictureByUserCat(pushPicture);
		if(catList!=null&&catList.size()>0){
			map.put("picture", catList);
			return map;
		}
		
		//获取 关联用户标签的图片
		List<UploadPicture> associateUserLabels = service.findPictureByAssoUserAndSolr(pushPicture);
		if(associateUserLabels!=null&&associateUserLabels.size()>0){
			map.put("picture", associateUserLabels);
			return map;
		}
		
		//随机获取图片
		List<UploadPicture> picList = service.getPictureByNum(pushPicture);
		map.put("picture", picList);
		return map;
	}
	
	

	
	
}
