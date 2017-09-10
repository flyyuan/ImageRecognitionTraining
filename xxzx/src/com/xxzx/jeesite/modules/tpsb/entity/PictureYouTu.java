package com.xxzx.jeesite.modules.tpsb.entity;

import java.util.List;
import java.util.Map;

public class PictureYouTu {
	private  List<Map<String,Object>> tags;//图像的分类标签
	private  List<Object> faces;
	private String errormsg;//返回错误描述
	private int errorcode;//返回状态码,非0值为出错
	public List<Map<String, Object>> getTags() {
		return tags;
	}
	public void setTags(List<Map<String, Object>> tags) {
		this.tags = tags;
	}
	public List<Object> getFaces() {
		return faces;
	}
	public void setFaces(List<Object> faces) {
		this.faces = faces;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	@Override
	public String toString() {
		return "PictureYouTu [tags=" + tags + ", faces=" + faces
				+ ", errormsg=" + errormsg + ", errorcode=" + errorcode + "]";
	}
	
	
	
	
	
	
}
