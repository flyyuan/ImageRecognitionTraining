package com.xxzx.jeesite.modules.tpsb.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.modules.tpsb.dao.TaskAllocatDao;
import com.xxzx.jeesite.modules.tpsb.entity.TaskAllocat;
@Service
@Transactional(readOnly = true)
public class TaskAllocatService extends CrudService<TaskAllocatDao, TaskAllocat> {
	
	@Autowired
	private TaskAllocatDao dao;
	
	public int insert(TaskAllocat allocat){
		return dao.insert(allocat);
	}
	
	//添加所有用户的任务
	public void insertAllTask(List<TaskAllocat> list){
		dao.insertAllTask(list);
	}
		
		//用户完成一条任务
	public void finishUpdate(TaskAllocat taskAllocat){
		dao.finishUpdate(taskAllocat);
	}
	
	public List<String> findAllUserId(){
		return dao.findAllUserId();
	}
	
	//随机获取指定张图片
	public List<String> getPicByNum(@Param("picNum")String picNum){
		return dao.getPicByNum(picNum);
	}
	public List<TaskAllocat> findTaskByDate(String date){
		return dao.findTaskByDate(date);
	}
	
	
	public List<TaskAllocat> getPicsByTaskId(String taskId){
		return dao.getPicsByTaskId(taskId);
	}
}
