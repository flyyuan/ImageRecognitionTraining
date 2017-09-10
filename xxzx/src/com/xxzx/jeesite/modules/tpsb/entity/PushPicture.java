package com.xxzx.jeesite.modules.tpsb.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xxzx.jeesite.common.persistence.DataEntity;
//推送图片类
public class PushPicture  extends DataEntity<PushPicture>{
	
	private static final long serialVersionUID = 1L;
	private int picNumber = 1;//默认推送图片为1
	
	private String picture_name;
	private String finish_time;
	
	private String[] labels;//用户 提交过来标签集合
	private String picCatId;
	private List<String> picCatIds;
	private  String picId;//图片的id
	private String url;   //图片 url，用户传过来的虚拟路径
	private int errorLabel;//错误标签
	private List<String> picIdList;//保存历史图片id
	
	private String oneLabelId;
	private String twoCandidateLabel;
	
	
	
	
	
	
	public List<String> getPicCatIds() {
		return picCatIds;
	}
	public void setPicCatIds(List<String> picCatIds) {
		this.picCatIds = picCatIds;
	}
	@JsonBackReference
	@Override
	public boolean getIsNewRecord() {
		// TODO Auto-generated method stub
		return super.getIsNewRecord();
	}
	public String getPicture_name() {
		return picture_name;
	}
	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	public String getPicCatId() {
		return picCatId;
	}
	public void setPicCatId(String picCatId) {
		this.picCatId = picCatId;
	}
	public String getOneLabelId() {
		return oneLabelId;
	}
	public void setOneLabelId(String oneLabelId) {
		this.oneLabelId = oneLabelId;
	}
	public String getTwoCandidateLabel() {
		return twoCandidateLabel;
	}
	public void setTwoCandidateLabel(String twoCandidateLabel) {
		this.twoCandidateLabel = twoCandidateLabel;
	}
	public List<String> getPicIdList() {
		return picIdList;
	}
	public void setPicIdList(List<String> picIdList) {
		this.picIdList = picIdList;
	}
	@JsonBackReference
	public int getErrorLabel() {
		return errorLabel;
	}
	public void setErrorLabel(int errorLabel) {
		this.errorLabel = errorLabel;
	}
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
	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JsonBackReference
	public int getPicNumber() {
		return picNumber;
	}
	
	public void setPicNumber(int picNumber) {
		this.picNumber = picNumber;
	}
	
	
	
}
