/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.modules.sys.dao;

import java.util.List;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author xxzx
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
