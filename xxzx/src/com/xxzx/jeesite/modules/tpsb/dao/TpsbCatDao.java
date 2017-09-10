package com.xxzx.jeesite.modules.tpsb.dao;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;

/**
 * 单表生成DAO接口
 * @author duang
 * @version 2017-06-02
 */
@MyBatisDao
public interface TpsbCatDao extends CrudDao<TpsbCat> {
	
}