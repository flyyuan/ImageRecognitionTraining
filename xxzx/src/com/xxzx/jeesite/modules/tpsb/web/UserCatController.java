package com.xxzx.jeesite.modules.tpsb.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.tpsb.entity.UserCat;
import com.xxzx.jeesite.modules.tpsb.service.UserCatService;
@Controller
@RequestMapping(value = "${adminPath}/tpsb/userCat")
public class UserCatController  extends BaseController{

	@Autowired
	private UserCatService service;
	
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String,Integer> insert(UserCat userCat){
		int count = 0;
		Map<String,Integer> map = Maps.newHashMap();
		if(userCat.getCatList()!=null&&userCat.getCatList().size()>0){
			service.insertAll(userCat);
			map.put("insertStatus", 1);
		}else{
			map.put("insertStatus", 0);
		}
//		if(userCat.getCatId()!=null){
//			count = service.insert(userCat);
//		}
//		if(count>0){
//			map.put("insertStatus", 1);
//		}else{
//			map.put("insertStatus", 1);
//		}
		return map;
	}
	
	
	@RequestMapping("/getUserCat")
	@ResponseBody
	public List<UserCat> getUserCat(UserCat userCat){
		return service.getUserCat(userCat.getCurrentUser().getId());
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Integer> delete(UserCat userCat){
		Map<String,Integer> map = Maps.newHashMap();
		userCat.preUpdate();
		service.delete(userCat);
		map.put("deleteStatus", 1);
		return  map;
	}
	
	
	
		
}
