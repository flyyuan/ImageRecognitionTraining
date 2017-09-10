package com.xxzx.jeesite.modules.tpsb.entity;

import com.xxzx.jeesite.common.persistence.DataEntity;
//图片标签表相关字段
public class PicLabel extends DataEntity<PicLabel>{
	
	private String picId;//图片id
	private String labelParentId;//父标签id
	private String label;//标签
	private int isParent;//是否是父标签
	private int status;//状态  1.第一层标签，。2.第二层标签
	private String url;
	
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getLabelParentId() {
		return labelParentId;
	}
	public void setLabelParentId(String labelParentId) {
		this.labelParentId = labelParentId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getIsParent() {
		return isParent;
	}
	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
}
