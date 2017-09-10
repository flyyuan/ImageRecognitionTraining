package com.xxzx.jeesite.modules.tpsb.entity;

import com.xxzx.jeesite.common.persistence.BaseEntity;
import com.xxzx.jeesite.common.persistence.DataEntity;

public class UserSuccessRecord extends DataEntity<UserSuccessRecord>{
	
	private String userId;
	private Double writeSuccNum;
	private Double selectSuccNum;
	private int status;
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getWriteSuccNum() {
		return writeSuccNum;
	}
	public void setWriteSuccNum(Double writeSuccNum) {
		this.writeSuccNum = writeSuccNum;
	}
	public Double getSelectSuccNum() {
		return selectSuccNum;
	}
	public void setSelectSuccNum(Double selectSuccNum) {
		this.selectSuccNum = selectSuccNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
