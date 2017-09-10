package com.xxzx.jeesite.modules.tpsb.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.modules.tpsb.entity.UserSuccessRecord;
import com.xxzx.jeesite.modules.tpsb.service.UserManageService;

@Controller
@RequestMapping(value = "${adminPath}/tpsb/userManage")
public class UserManageController {

	@Autowired
	private UserManageService service;
	
	
	@RequestMapping("/setPermissions")
	@ResponseBody
	public Map<String,Object> setPermissions(UserSuccessRecord successRecord){
		Map<String,Object> map = Maps.newHashMap();
		
		//保存权限设置
		int count = service.getCountByUserId(successRecord);
		if(count==1){
			service.update(successRecord);
			map.put("status", 1);
			return map;
		}else if(count ==0){
			service.insert(successRecord);
			map.put("status", 1);
			return map;
		}
		
		map.put("status", 0);
		return map;
		
		
	}
	
	
}
