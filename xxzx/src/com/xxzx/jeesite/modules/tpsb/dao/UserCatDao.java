package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserCat;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
@MyBatisDao
public interface UserCatDao extends CrudDao<UserCat> {
	
	List<UserCat> getUserCat(@Param("userId") String userId);
	
	void insertAll(UserCat userCat);
	
	
	
}
