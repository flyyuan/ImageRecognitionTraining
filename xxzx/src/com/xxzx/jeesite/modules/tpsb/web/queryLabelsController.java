package com.xxzx.jeesite.modules.tpsb.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.QueryLabels;
import com.xxzx.jeesite.modules.tpsb.service.QueryLabelsService;

@Controller
@RequestMapping(value="${adminPath}/tpsb")
public class queryLabelsController {
	@Autowired
	private QueryLabelsService service;
	
	@RequestMapping(value={"/queryPicByKeyWord"})
	@ResponseBody
	public List<PushPicture> queryPicByKeyWord(String keyWord){
		
		Map<String,Object> map = Maps.newLinkedHashMap();
		QueryLabels queryLabels = null;
		try {
			 queryLabels = service.queryLabels(keyWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return queryLabels.getPushPicture();
	}
	
}
