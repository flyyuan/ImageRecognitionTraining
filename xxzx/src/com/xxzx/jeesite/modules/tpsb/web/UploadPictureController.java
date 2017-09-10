package com.xxzx.jeesite.modules.tpsb.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tools.zip.ZipFile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.Constants;
import com.xxzx.jeesite.common.utils.IdGen;
import com.xxzx.jeesite.common.web.BaseController;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
import com.xxzx.jeesite.modules.tpsb.service.UploadPictureService;

@Controller
@RequestMapping("${adminPath}/tpsb")
public class UploadPictureController extends BaseController {

	@Autowired
	private UploadPictureService uploadPictureService;

	@RequestMapping(value = { "", "list" })
	public String test() {
		return "modules/tpsb/uploadImage";
	}
	
	@RequestMapping("/uploadImageByZip")
	public String uploadImageByZip(){
		return "modules/tpsb/uploadImageByZip";
	}
	
	

	@RequestMapping(value = "/uploadPicture")
	@ResponseBody
	public Map<Object, Object> uploadPicture(HttpServletResponse response,
			HttpServletRequest request, UploadPicture uploadPicture) {
		Map<Object, Object> uploadResult = uploadPictureService.uploadPicture(
				request, uploadPicture);
		return uploadResult;
	}

	@RequestMapping("/uploadPictureByZIP")
	@ResponseBody
	public Map<Object, Object> uploadPictureByZIP(HttpServletResponse response,
			HttpServletRequest request, UploadPicture uploadPicture)
			throws ZipException, IOException {
		Map<Object, Object> uploadResult = uploadPictureService.uploadPictureByZIP(
				request, uploadPicture);
		return uploadResult;
	}

}
