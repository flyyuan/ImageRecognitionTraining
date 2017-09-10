package com.xxzx.jeesite.modules.tpsb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxzx.jeesite.modules.tpsb.entity.TaskAllocat;
import com.xxzx.jeesite.modules.tpsb.service.TaskAllocatService;

@Controller
@RequestMapping(value = "${adminPath}/tpsb/task")
public class TaskAllocatController {
		
	@Autowired
	private TaskAllocatService service;
	
	@RequestMapping("/findTaskByDate")
	@ResponseBody
	public List<TaskAllocat> findTaskByDate(String date){
		return service.findTaskByDate(date);
	}
	
	@RequestMapping("/getPicsByTaskId")
	@ResponseBody
	public  List<TaskAllocat> getPicsByTaskId(String taskId){
		return service.getPicsByTaskId(taskId);
	}
	
	
	
}
