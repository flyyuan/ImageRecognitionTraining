package com.xxzx.jeesite.modules.tpsb.entity;

import com.xxzx.jeesite.common.persistence.DataEntity;
//用户积分
public class UserIntegral extends DataEntity<UserIntegral>{
	
	private String picId;
	private String userId;
	private String label;
	private double integral;
	private String picUrl;
	private String name;
	private double integralSum;
	
	
	
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getIntegral() {
		return integral;
	}
	public void setIntegral(double integral) {
		this.integral = integral;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getIntegralSum() {
		return integralSum;
	}
	public void setIntegralSum(double integralSum) {
		this.integralSum = integralSum;
	}
	
	
	
	
	
}
