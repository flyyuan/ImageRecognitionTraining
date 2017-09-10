package com.xxzx.jeesite.modules.tpsb.entity;

import java.util.List;
import java.util.Map;

import com.xxzx.jeesite.common.persistence.DataEntity;

public class UploadPicture  extends DataEntity<UploadPicture>{
	private static final long serialVersionUID = 1L;
	private String id;      //图片id   
	private String tagStatus;    //图片便签状态
	private String url;   //图片url地址
	private String picName;//图片原始名字
	private List<String> labels ;//图片便签
	private int labelStatus; //图片是否需要填写状态，默认0，，，0表示需要填写。1表示需要填写
	
	private Map<String, String> oneLabelsMap;//第一层确定标签
	private List<String> twoCandidateLabels;//第二成候选标签
	
	private Map<String,String> picCatMap;
	
	
	
	
	
	public Map<String, String> getPicCatMap() {
		return picCatMap;
	}
	public void setPicCatMap(Map<String, String> picCatMap) {
		this.picCatMap = picCatMap;
	}
	public Map<String, String> getOneLabelsMap() {
		return oneLabelsMap;
	}
	public void setOneLabelsMap(Map<String, String> oneLabelsMap) {
		this.oneLabelsMap = oneLabelsMap;
	}
	public List<String> getTwoCandidateLabels() {
		return twoCandidateLabels;
	}
	public void setTwoCandidateLabels(List<String> twoCandidateLabels) {
		this.twoCandidateLabels = twoCandidateLabels;
	}
	public int getLabelStatus() {
		return labelStatus;
	}
	public void setLabelStatus(int labelStatus) {
		this.labelStatus = labelStatus;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTagStatus() {
		return tagStatus;
	}
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	
	
	
	
	
	
	
	
	
	
}
