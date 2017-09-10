package com.xxzx.jeesite.modules.tpsb.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.modules.tpsb.dao.UserCatDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserCat;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
@Service
@Transactional(readOnly = true)
public class UserCatService  extends CrudService<UserCatDao,UserCat>{
	
	@Autowired
	private UserCatDao dao;
	
	public List<UserCat> getUserCat(String userId){
		return dao.getUserCat(userId);
	}

	public int insert(UserCat userCat) {
		userCat.preInsert();
		int count = dao.insert(userCat);
		return count;
	}
	
	public void insertAll(UserCat userCat){
		userCat.preInsert();
		dao.insertAll(userCat);
	}
	
	
	
}
