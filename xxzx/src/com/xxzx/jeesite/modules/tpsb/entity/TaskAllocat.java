package com.xxzx.jeesite.modules.tpsb.entity;

import com.xxzx.jeesite.common.persistence.DataEntity;
//任务分配相关类
public class TaskAllocat extends DataEntity<TaskAllocat> {
	
	private String userid;//用户id
	private String taskSum;//任务数量
	private String finishNum;//完成数量
	private String finishSpeed;//完成进度
	private String  picId;
	private String taskId;
	private String name;
	
	
	
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTaskSum() {
		return taskSum;
	}
	public void setTaskSum(String taskSum) {
		this.taskSum = taskSum;
	}
	public String getFinishNum() {
		return finishNum;
	}
	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}
	public String getFinishSpeed() {
		return finishSpeed;
	}
	public void setFinishSpeed(String finishSpeed) {
		this.finishSpeed = finishSpeed;
	}
	
	
	
	
}
