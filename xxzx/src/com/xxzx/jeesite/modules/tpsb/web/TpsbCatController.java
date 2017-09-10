package com.xxzx.jeesite.modules.tpsb.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.config.Global;
import com.xxzx.jeesite.common.persistence.Page;
import com.xxzx.jeesite.common.utils.StringUtils;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;
import com.xxzx.jeesite.modules.tpsb.service.TpsbCatService;

/**
 * 单表生成Controller
 * @author duang
 * @version 2017-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/tpsb/tpsbCat")
public class TpsbCatController extends BaseController {

	@Autowired
	private TpsbCatService tpsbCatService;
	
	@ModelAttribute
	public TpsbCat get(@RequestParam(required=false) String id) {
		TpsbCat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tpsbCatService.get(id);
		}
		if (entity == null){
			entity = new TpsbCat();
		}
		return entity;
	}
	
	@RequiresPermissions("tpsb:tpsbCat:view")
	@RequestMapping(value = {"list", ""})
	public String list(TpsbCat tpsbCat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TpsbCat> page = tpsbCatService.findPage(new Page<TpsbCat>(request, response), tpsbCat); 
		model.addAttribute("page", page);
		return "modules/tpsb/tpsbCatList";
	}

	@RequiresPermissions("tpsb:tpsbCat:view")
	@RequestMapping(value = "form")
	public String form(TpsbCat tpsbCat, Model model) {
		model.addAttribute("tpsbCat", tpsbCat);
		return "modules/tpsb/tpsbCatForm";
	}

	@RequiresPermissions("tpsb:tpsbCat:edit")
	@RequestMapping(value = "save")
	public String save(TpsbCat tpsbCat, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tpsbCat)){
			return form(tpsbCat, model);
		}
		tpsbCatService.save(tpsbCat);
		addMessage(redirectAttributes, "保存保存分类成功成功");
		return "redirect:"+Global.getAdminPath()+"/tpsb/tpsbCat/?repage";
	}
	
	@RequiresPermissions("tpsb:tpsbCat:edit")
	@RequestMapping(value = "addPicCat")
	@ResponseBody
	public Map<String,Object> save(TpsbCat tpsbCat, Model model) {
		Map<String,Object> map = Maps.newHashMap();
		try {
			tpsbCatService.save(tpsbCat);
			map.put("status", "success");
			return map;
		} catch (Exception e) {
			map.put("status", "error");
		}
		return  null;
		
	}
	
	@RequiresPermissions("tpsb:tpsbCat:edit")
	@RequestMapping(value = "delete")
	public String delete(TpsbCat tpsbCat, RedirectAttributes redirectAttributes) {
		tpsbCatService.delete(tpsbCat);
		addMessage(redirectAttributes, "删除保存分类成功成功");
		return "redirect:"+Global.getAdminPath()+"/tpsb/tpsbCat/?repage";
	}
	
	
	
	//获取所有大类
	@RequestMapping("/findAllPicCat")
	@ResponseBody
	public Map<String,Object> findAllPicCat(TpsbCat tpsbCat){
		Map<String,Object> map =  Maps.newHashMap();
		List<TpsbCat> catList = tpsbCatService.findList(tpsbCat);
		map.put("picCatList", catList);
		return map;
	}
	
	//根据大类id获取大类以下的所有图片
	@RequestMapping( value="/findAllPictureByPicCatId")
	@ResponseBody
	public List<PushPicture> findAllPictureByPicCatId(TpsbCat tpsbCat,String picCatId){
		Map<String,Object> map =  Maps.newHashMap();
		List<PushPicture> pictureList = tpsbCatService.findAllPictureByPicCatId(picCatId);
		return pictureList;
	}
	
	
	

}