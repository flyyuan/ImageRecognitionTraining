package com.xxzx.jeesite.modules.tpsb.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xxzx.jeesite.common.persistence.DataEntity;

public class UserCat extends DataEntity<UserCat> {
	
	private String catId;
	
	private List<String>  catList;
	
	private String name;
	
	
	
	@JsonBackReference
	@Override
	public boolean getIsNewRecord() {
		// TODO Auto-generated method stub
		return super.getIsNewRecord();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public List<String> getCatList() {
		return catList;
	}

	public void setCatList(List<String> catList) {
		this.catList = catList;
	}
	
	
	
	
	
	
}
