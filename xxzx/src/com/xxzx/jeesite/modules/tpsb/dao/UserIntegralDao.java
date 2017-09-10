package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserIntegral;
@MyBatisDao
public interface UserIntegralDao extends CrudDao<UserIntegral>{
	List<UserIntegral> findUserIntegral(String userId);
}
