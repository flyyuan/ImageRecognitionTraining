package com.xxzx.jeesite.modules.tpsb.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xxzx.jeesite.common.config.Global;
import com.xxzx.jeesite.common.persistence.Page;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserSuccessRecord;
import com.xxzx.jeesite.modules.tpsb.entity.UserTagResult;
import com.xxzx.jeesite.modules.tpsb.service.UserManageService;
import com.xxzx.jeesite.modules.tpsb.service.UserTagResultService;
@Controller
@RequestMapping(value="${adminPath}/tpsb/userManage")
public class UserTagResultController extends BaseController {
	
	
	@Autowired
	private UserTagResultService service;
	
	@Autowired
	private UserManageService manageService;
	
	@RequestMapping("/getAllUserTagResult")
	@ResponseBody
	public List<UserTagResult> getAllUserTagResult(UserTagResult tagResult){
		List<UserTagResult> allUserTagResult = service.getAllUserTagResult(tagResult);
		if(allUserTagResult!=null &&allUserTagResult.size()>0){
			return allUserTagResult;
		}else{
			return new ArrayList<UserTagResult>();
		}
		
	}
	
	@RequestMapping("/getUserTagResultByDate")
	@ResponseBody
	public List<UserTagResult> getUserTagResultByDate(String userId,String startDate,String endDate){
		UserTagResult user=service.getUserTagResultByDate(userId, startDate, endDate);
		List<UserTagResult> list = new ArrayList<UserTagResult>();
		list.add(user);
		return list;
	}
	
	
	@RequestMapping("/getUserTagByDate")
	@ResponseBody
	public List<UserTagResult> getUserTagByDate(String userId,String startDate,String endDate){
		List<UserTagResult> list = service.getUserTagByDate(userId,startDate,endDate);
		return list;
	}
	
	@RequestMapping("/list")
	@RequiresPermissions("tpsb:userManage:view")
//	@ResponseBody
	public String list(HttpServletRequest request,HttpServletResponse response,UserTagResult tagResult,Model model){
		  Page<UserTagResult> page = service.findPage(new Page<UserTagResult>(request, response), tagResult); 
		  model.addAttribute("page", page);
		return "modules/tpsb/userTagResultlist";
	}
	
	
	@RequestMapping("/delete")
	@RequiresPermissions("tpsb:userManage:edit")
//	@ResponseBody
	public String delete(HttpServletRequest request,HttpServletResponse response
								,UserTagResult tagResult,Model model,String id,UserSuccessRecord record
								,RedirectAttributes redirectAttributes){
		String[] ids = id.split(",");
		for (String ida : ids) {
			record.setUserId(ida);
			int count = manageService.getCountByUserId(record);
			if(count==1){
				record.preUpdate();
				manageService.update(record);
			}else if(count ==0){
				record.preInsert();
				manageService.insert(record);
			}
		}
		addMessage(redirectAttributes, "保存用户权限成功");
		return "redirect:"+Global.getAdminPath()+"/tpsb/userManage/list";
	}
	
	@RequestMapping("/findUserLabelsByUserId")
	@RequiresPermissions("tpsb:userManage:view")
	public String findUserLabelsByUserId(HttpServletRequest request,HttpServletResponse response,Model model
			,UserLabel userLabel){
//		 Page<UserLabel> page = service.findUserLabelsPage(new Page<UserLabel>(request, response), userLabel); 
		List<UserLabel> list = service.findUserLabelsByUserId(userLabel);
		if(list==null){
			list= new ArrayList<UserLabel>();
		}
		 model.addAttribute("list", list);
		return "modules/tpsb/userLabellist";
	}
	
	@RequestMapping("/findLabelsByUserId")
	@ResponseBody
	public List<UserLabel> findLabelsByUserId(HttpServletRequest request,HttpServletResponse response,Model model
			,UserLabel userLabel){
		List<UserLabel> list = service.findUserLabelsByUserId(userLabel);
		if(list==null){
			list= new ArrayList<UserLabel>();
		}
		return list;
	}
	
	@RequestMapping("/findAllPicResult")
	public String findAllPicResult(){
		service.findAllPicResult();
		return null;
	}
	
	
	
	
}
