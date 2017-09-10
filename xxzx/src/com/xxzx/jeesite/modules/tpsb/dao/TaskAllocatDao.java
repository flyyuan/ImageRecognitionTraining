package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.TaskAllocat;
@MyBatisDao
public interface TaskAllocatDao extends CrudDao<TaskAllocat> {
	
	
	int insert(TaskAllocat allocat);
	//添加所有用户的任务
	void insertAllTask(List<TaskAllocat> list);
	
	//用户完成一条任务
	void finishUpdate(TaskAllocat taskAllocat);
	//获取所有用户id
	List<String> findAllUserId();
	
	//随机获取指定张图片
	List<String> getPicByNum(@Param("picNum")String picNum);
	
	
	int insertToPicTask(TaskAllocat allocat);
	
	List<TaskAllocat> findTaskByDate(@Param("date")String date);
	
	int getPicNumByPicIdAndTaskId(TaskAllocat taskAllocat);
	
	List<TaskAllocat> getPicsByTaskId(@Param("taskId")String taskId);
	
	
}
