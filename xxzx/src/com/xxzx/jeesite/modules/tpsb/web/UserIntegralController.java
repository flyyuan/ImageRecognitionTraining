package com.xxzx.jeesite.modules.tpsb.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.tpsb.entity.UserIntegral;
import com.xxzx.jeesite.modules.tpsb.service.UserIntegralService;
@Controller
@RequestMapping(value = "${adminPath}/tpsb/userIntegral")
public class UserIntegralController extends BaseController {
	
	@Autowired
	private UserIntegralService service;
	
	
	
	@RequestMapping("/getIntegral")
	@ResponseBody
	public Map<String,Object> getIntegral(UserIntegral userIntegral){
		Map<String,Object> map = Maps.newHashMap();
		List<UserIntegral> userIntegralList = service.findUserIntegral(userIntegral.getCurrentUser().getId());
		map.put("integralList", userIntegralList);
		return map;
	}

}
