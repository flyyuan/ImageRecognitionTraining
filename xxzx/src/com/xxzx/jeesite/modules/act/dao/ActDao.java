/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.modules.act.dao;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author xxzx
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
