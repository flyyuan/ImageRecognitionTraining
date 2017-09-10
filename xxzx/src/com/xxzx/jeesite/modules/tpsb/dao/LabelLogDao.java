package com.xxzx.jeesite.modules.tpsb.dao;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
@MyBatisDao
public interface LabelLogDao extends CrudDao<UserLabel> {
	
}
