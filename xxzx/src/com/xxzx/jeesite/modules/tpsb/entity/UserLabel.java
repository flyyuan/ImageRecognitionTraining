package com.xxzx.jeesite.modules.tpsb.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xxzx.jeesite.common.persistence.DataEntity;
//用户标签
public class UserLabel extends DataEntity<UserLabel>{
	private static long serialVersionUID = 1L;
	private String picName;
	private String url;
	private String label;//便签
	private String picId;//图片id
	private String labelAnalyzer;//分词后的标签
	private int labelErrorNum;//便签错误
	private String targetLabel;
	private int updateNum;
	private List<String> errorLabels;
	private List<String> succLabels;
	private Date beginDate;		// 开始日期
	private Date endDate;		// 结束日期
	private String beginDateString;
	private String endDateString;
	private String modifyStatus;
	private String labelId;
	private String labelParentId;
	private List<UserLabel> userLabels;
	
	
	
	
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getLabelParentId() {
		return labelParentId;
	}
	public void setLabelParentId(String labelParentId) {
		this.labelParentId = labelParentId;
	}
	public List<UserLabel> getUserLabels() {
		return userLabels;
	}
	public void setUserLabels(List<UserLabel> userLabels) {
		this.userLabels = userLabels;
	}
	public List<String> getSuccLabels() {
		return succLabels;
	}
	public void setSuccLabels(List<String> succLabels) {
		this.succLabels = succLabels;
	}
	public List<String> getErrorLabels() {
		return errorLabels;
	}
	public void setErrorLabels(List<String> errorLabels) {
		this.errorLabels = errorLabels;
	}
	public int getUpdateNum() {
		return updateNum;
	}
	public void setUpdateNum(int updateNum) {
		this.updateNum = updateNum;
	}
	public String getModifyStatus() {
		return modifyStatus;
	}
	public void setModifyStatus(String modifyStatus) {
		this.modifyStatus = modifyStatus;
	}
	public String getBeginDateString() {
		return beginDateString;
	}
	public void setBeginDateString(String beginDateString) {
		this.beginDateString = beginDateString;
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	public UserLabel(){
		super();
	}
	public UserLabel(String id){
		super(id);
	}
	
	
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		UserLabel.serialVersionUID = serialVersionUID;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getTargetLabel() {
		return targetLabel;
	}
	public void setTargetLabel(String targetLabel) {
		this.targetLabel = targetLabel;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabelAnalyzer() {
		return labelAnalyzer;
	}
	public void setLabelAnalyzer(String labelAnalyzer) {
		this.labelAnalyzer = labelAnalyzer;
	}
	@JsonBackReference
	public int getLabelErrorNum() {
		return labelErrorNum;
	}
	public void setLabelErrorNum(int labelErrorNum) {
		this.labelErrorNum = labelErrorNum;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	
	
	
	
}
