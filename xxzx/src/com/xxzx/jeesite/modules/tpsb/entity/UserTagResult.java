package com.xxzx.jeesite.modules.tpsb.entity;

import com.xxzx.jeesite.common.persistence.DataEntity;

//用户管理
public class UserTagResult extends DataEntity<UserTagResult>{
	private String name;//用户名
	private String userId;
	private String photo;
	
	private String jurisdicStatusName;
	
	private double writeNum=0;//填写标签数量
	private double writeSuccNum=0;//填写正确标签数量
	private double writeSuccRate =0;
	
	
	private double selectNum=0;//选择标签数量
	private double selectSuccNum=0;//选择标签成功数量
	private double selectSuccRate = 0;
	private int  jurisdicStatus = 0;
	
	private String nowdate;
	
	
	
	
	
	public String getNowdate() {
		return nowdate;
	}
	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getJurisdicStatusName() {
		return jurisdicStatusName;
	}
	public void setJurisdicStatusName(String jurisdicStatusName) {
		this.jurisdicStatusName = jurisdicStatusName;
	}
	public double getWriteSuccRate() {
		return writeSuccRate;
	}
	public void setWriteSuccRate(double writeSuccRate) {
		this.writeSuccRate = writeSuccRate;
	}
	public double getSelectSuccRate() {
		return selectSuccRate;
	}
	public void setSelectSuccRate(double selectSuccRate) {
		this.selectSuccRate = selectSuccRate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getJurisdicStatus() {
		return jurisdicStatus;
	}
	public void setJurisdicStatus(int jurisdicStatus) {
		this.jurisdicStatus = jurisdicStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getWriteNum() {
		return writeNum;
	}
	public void setWriteNum(double writeNum) {
		this.writeNum = writeNum;
	}
	public double getWriteSuccNum() {
		return writeSuccNum;
	}
	public void setWriteSuccNum(double writeSuccNum) {
		this.writeSuccNum = writeSuccNum;
	}
	public double getSelectNum() {
		return selectNum;
	}
	public void setSelectNum(double selectNum) {
		this.selectNum = selectNum;
	}
	public double getSelectSuccNum() {
		return selectSuccNum;
	}
	public void setSelectSuccNum(double selectSuccNum) {
		this.selectSuccNum = selectSuccNum;
	}
	
	
}
