package com.xxzx.jeesite.modules.tpsb.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.service.UserLabelService;
@Controller
@RequestMapping(value="${adminPath}/tpsb")
public class UserLabelController extends BaseController {
	
	@Autowired
	private UserLabelService service;
	
	
	@RequestMapping("/addPictureLabelByUser")
	@ResponseBody
	public List<Map<String,Object>> addPictureLabelByUser(PushPicture pushPicture,UserLabel userLabel){
		List<Map<String,Object>> mapList = Lists.newArrayList();
		String userId = pushPicture.getCurrentUser().getId();
		//获取用户权限标识
		String userPermiaStatus = service.getUserPermisStatusByUserId(userId);
		if("3".equals(userPermiaStatus) || "5".equals(userPermiaStatus)){
			Map<String,Object> map = Maps.newHashMap();
			map.put("addLabelsStatus", -1);
			mapList.add(map);
			return mapList;
		}
		
		int errorLabel = pushPicture.getErrorLabel();
		//标签标记错误
		if(errorLabel==1){
			Map<String, Object> map = service.submitErrorTabels(pushPicture);
			mapList.add(map);
			return mapList;
		}
		//无错误标记，同步solr
		Map<String, Object> map = service.addPictureLabelByUser(pushPicture,userLabel);
		
		
		mapList.add(map);
		return mapList;
	}
	
	@RequestMapping("/addTwoCandidateLabel")
	@ResponseBody
	public List<Map<String,Object>> addTwoCandidateLabel(PushPicture picture){
		List<Map<String,Object>> mapList = Lists.newArrayList();
		String userId = picture.getCurrentUser().getId();
		//获取用户权限标识
		String userPermiaStatus = service.getUserPermisStatusByUserId(userId);
		if("4".equals(userPermiaStatus) || "5".equals(userPermiaStatus)){
			Map<String,Object> map = Maps.newHashMap();
			map.put("addLabelsStatus", -1);
			mapList.add(map);
			return mapList;
		}
		
		
		Map<String, Object> map = service.addTwoCandidateLabel(picture);
		mapList.add(map);
		return mapList;
		
	}
	
	
	//获取用户历史填过的标签（没有判定结束）
	@RequestMapping("/findHistoryWriteLabels")
	@ResponseBody
	public List<UserLabel> findHistoryLabels(UserLabel userLabel){
		return service.findHistoryLabelsByUserId(userLabel.getCurrentUser().getId());
	}
	
	//获取用户历史选择过得标签（没有判定结束）
	@RequestMapping("/findHistorySelectLabels")
	@ResponseBody
	public List<UserLabel> findHistorySelectLabels(UserLabel userLabel){
		return service.findHistorySelectLabels(userLabel.getCurrentUser().getId());
	}
	
	
	//根据图片id修改标签
	@RequestMapping("/updateWrite")
	@ResponseBody
	public Map<String,Object> update(UserLabel userLabel){
		Map<String,Object> map = Maps.newHashMap();
		int canUpdateNum = service.updateWrite(userLabel);
			
		if(canUpdateNum>=0){
			map.put("canUpdateNum", canUpdateNum);
			map.put("updateStatus", 1);
		}else{
			map.put("canUpdateNum", 0);
			map.put("updateStatus", 0);
		}
		return map;
	}
	
	@RequestMapping("/deleteWrite")
	@ResponseBody
	public Map<String,Object> deleteWrite(UserLabel userLabel){
		Map<String,Object> map = Maps.newHashMap();
		try {
			service.deleteWrite(userLabel);
			map.put("deleteStatus", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("deleteStatus", 0);
		}
		return map;
	}
	
	@RequestMapping("/deleteSelect")
	@ResponseBody
	public Map<String,Object> deleteSelect(UserLabel userLabel){
		Map<String,Object> map = Maps.newHashMap();
		try {
			service.deleteSelect(userLabel);
			map.put("deleteStatus", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("deleteStatus", 0);
		}
		return map;
	}
	
	
	
}
