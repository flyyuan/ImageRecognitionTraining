/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.modules.cms.dao;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.cms.entity.Site;

/**
 * 站点DAO接口
 * @author xxzx
 * @version 2013-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
