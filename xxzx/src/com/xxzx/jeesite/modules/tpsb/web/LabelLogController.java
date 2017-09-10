package com.xxzx.jeesite.modules.tpsb.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xxzx.jeesite.common.persistence.Page;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.service.LabelLogService;
@Controller               
@RequestMapping(value ="${adminPath}/tpsb/log")
public class LabelLogController {

	@Autowired
	private LabelLogService service;
	
	
	@RequestMapping(value={"list",""})
	public String list(UserLabel userLabel, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserLabel> page = service.findPage(new Page<UserLabel>(request, response), userLabel); 
        model.addAttribute("page", page);
		return "modules/tpsb/labelLoglist";
	}
	
}
