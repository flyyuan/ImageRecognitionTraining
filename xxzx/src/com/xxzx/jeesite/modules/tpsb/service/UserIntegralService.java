package com.xxzx.jeesite.modules.tpsb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.modules.tpsb.dao.UserIntegralDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserIntegral;

@Service
@Transactional(readOnly = true)
public class UserIntegralService extends CrudService<UserIntegralDao,UserIntegral>{

	public List<UserIntegral> findUserIntegral(String userId) {
		return dao.findUserIntegral(userId); 
	}
	
}
