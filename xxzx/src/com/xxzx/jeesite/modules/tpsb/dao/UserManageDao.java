package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.BaseDao;
import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserSuccessRecord;
@MyBatisDao
public interface UserManageDao extends BaseDao{
	
	void update(UserSuccessRecord entity);
	
	int getCountByUserId(UserSuccessRecord entity);
	
	void insert(UserSuccessRecord entity);
	
}
