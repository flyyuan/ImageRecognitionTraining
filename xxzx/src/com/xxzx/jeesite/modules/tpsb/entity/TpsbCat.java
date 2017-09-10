package com.xxzx.jeesite.modules.tpsb.entity;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xxzx.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author duang
 * @version 2017-06-02
 */
public class TpsbCat extends DataEntity<TpsbCat> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;		// 父类目ID=0时，代表的是一级的类目
	private String name;		// 类目名称
	private String status;		// 状态。可选值:1(正常),2(删除)
	private String sortOrder;		// 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	private String isParent;		// 该类目是否为父类目，1为true，0为false
	private Date created;		// 创建时间
	private Date updated;		// 创建时间
	
	public TpsbCat() {
		super();
	}

	public TpsbCat(String id){
		super(id);
	}

	
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Length(min=0, max=50, message="类目名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="状态。可选值:1(正常),2(删除)长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=4, message="排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数长度必须介于 0 和 4 之间")
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Length(min=0, max=1, message="该类目是否为父类目，1为true，0为false长度必须介于 0 和 1 之间")
	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}