/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.test.dao;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.test.entity.TestDataChild;

/**
 * 主子表生成DAO接口
 * @author xxzx
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataChildDao extends CrudDao<TestDataChild> {
	
}