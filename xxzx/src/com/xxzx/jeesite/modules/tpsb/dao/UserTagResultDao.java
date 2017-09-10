package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserTagResult;
@MyBatisDao
public interface UserTagResultDao extends CrudDao<UserTagResult>{

	//获取填写人和填写次数
	List<UserTagResult> findWriteUserResult(UserTagResult entity);
	
	//获取选择人和选择次数
	List<UserTagResult> findSelectUserResult(UserTagResult entity);
	
	//获取用户成功填写记录
	List<UserTagResult> findUserSuccRecord();
	
	//获取填写选择正确数
	List<Double> getSuccWriteAndSelect(@Param("userId")String userId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	//获取选择填写总数
	UserTagResult getUserTagResult(@Param("userId")String userId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	//获取用户所有标签情况
	List<UserLabel> findUserLabelsByUserId(UserLabel userLabel);
	
	//获取用户填写成功的标签
	List<String> getSuccLabelByUserIdAndPicId(UserLabel userLabel);
	
	//获取用户填写成功的标签
		List<String> getSelectLabelByUserIdAndPicId(UserLabel userLabel);

	List<UserLabel> findUserSelectLabelsByUserId(UserLabel userLabel);

	List<UserTagResult> getUserTagByDate(@Param("userId")String userId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	
	int getUserWriteSuccTagByDate(@Param("userId")String userId,@Param("nowdate")String nowdate);
	int getUserSelectSuccTagByDate(@Param("userId")String userId,@Param("nowdate")String nowdate);
	
	//获取图片标签 化结果
	List<UserLabel> findAllPicResult();
	
}
