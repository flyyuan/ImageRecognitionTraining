/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.modules.sys.dao;

import com.xxzx.jeesite.common.persistence.TreeDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author xxzx
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
