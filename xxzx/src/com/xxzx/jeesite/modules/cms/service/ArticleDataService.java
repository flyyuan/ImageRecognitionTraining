/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.modules.cms.dao.ArticleDataDao;
import com.xxzx.jeesite.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author xxzx
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
