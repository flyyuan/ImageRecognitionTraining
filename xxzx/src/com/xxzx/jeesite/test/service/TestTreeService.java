/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.service.TreeService;
import com.xxzx.jeesite.common.utils.StringUtils;
import com.xxzx.jeesite.test.dao.TestTreeDao;
import com.xxzx.jeesite.test.entity.TestTree;

/**
 * 树结构生成Service
 * @author xxzx
 * @version 2015-04-06
 */
@Service
@Transactional(readOnly = true)
public class TestTreeService extends TreeService<TestTreeDao, TestTree> {

	public TestTree get(String id) {
		return super.get(id);
	}
	
	public List<TestTree> findList(TestTree testTree) {
		if (StringUtils.isNotBlank(testTree.getParentIds())){
			testTree.setParentIds(","+testTree.getParentIds()+",");
		}
		return super.findList(testTree);
	}
	
	@Transactional(readOnly = false)
	public void save(TestTree testTree) {
		super.save(testTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestTree testTree) {
		super.delete(testTree);
	}
	
}