package com.xxzx.jeesite.modules.tpsb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.service.BaseService;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.modules.tpsb.dao.UserManageDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserSuccessRecord;
@Service
@Transactional(readOnly = true)
public class UserManageService extends BaseService {
	
	@Autowired
	private UserManageDao dao;
	
	
	
	public void update(UserSuccessRecord entity) {
		entity.preUpdate();
		dao.update(entity);
	}
	
	
	public int getCountByUserId(UserSuccessRecord entity){
		return dao.getCountByUserId(entity);
	}
	
	public void insert(UserSuccessRecord entity){
		entity.preInsert();
		dao.insert(entity);
	}
	
	
		
		
	
}
