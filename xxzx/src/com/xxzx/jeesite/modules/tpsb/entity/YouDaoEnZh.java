package com.xxzx.jeesite.modules.tpsb.entity;

import java.util.List;
import java.util.Map;

public class YouDaoEnZh {
	private String errorCode;
	private String query;
	private List<String> translation;
	private Map<String,Object> basic;
	private List<Object> web;
	private String l;
	
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<String> getTranslation() {
		return translation;
	}
	public void setTranslation(List<String> translation) {
		this.translation = translation;
	}
	public Map<String, Object> getBasic() {
		return basic;
	}
	public void setBasic(Map<String, Object> basic) {
		this.basic = basic;
	}
	public List<Object> getWeb() {
		return web;
	}
	public void setWeb(List<Object> web) {
		this.web = web;
	}
	@Override
	public String toString() {
		return "YouDaoEnZh [errorCode=" + errorCode + ", query=" + query
				+ ", translation=" + translation + ", basic=" + basic
				+ ", web=" + web + ", l=" + l + "]";
	}
	
	
	
	
}
