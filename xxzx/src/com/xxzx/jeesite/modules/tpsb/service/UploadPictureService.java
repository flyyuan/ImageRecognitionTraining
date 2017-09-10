package com.xxzx.jeesite.modules.tpsb.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.common.utils.IdGen;
import com.xxzx.jeesite.common.utils.StringUtils;
import com.xxzx.jeesite.modules.tpsb.dao.UploadPictureDao;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
import com.xxzx.jeesite.modules.tpsb.util.ZipUtil;
@Service
@Transactional(readOnly = true)
public class UploadPictureService extends CrudService<UploadPictureDao, UploadPicture> {

	@Autowired
	private UploadPictureDao uploadPictureDao;
	
	@Value("${upLoadUrl}")
	private String upLoadUrl;
	@Value("${tomcatPicUrl}")
	private String tomcatPicUrl;
	
	
	public Map<Object,Object> uploadPicture(HttpServletRequest request,UploadPicture uploadPicture){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		int successNum = 0;
		int failureNum = 0;
		List<String> failureList = new ArrayList<String>() ;
		Map<Object, Object> map = Maps.newHashMap();
		
		//判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                	uploadPicture = new UploadPicture();
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String extName = myFileName.substring(myFileName.lastIndexOf(".")+1);
                        String uuid = IdGen.uuid();
                        
                        //定义上传路径 
                        uploadPicture.setId(uuid);
                        uploadPicture.setPicName(myFileName);
                        uploadPicture.setUrl(uuid+"."+extName);
                        String path = upLoadUrl + uuid+"."+extName;  
                        File localFile = new File(path);  
                        try {
							file.transferTo(localFile);
							this.save(uploadPicture);
							successNum++;
						} catch (IllegalStateException e) {
							System.out.println("上传失败！！！！");
							failureNum++;
							failureList.add(myFileName);
							e.printStackTrace();
						} catch (IOException e) {
							System.out.println("上传失败！！！！");
							e.printStackTrace();
						}  
                    }  
                }  
               
            }  
              
        }  
        
        map.put("successNum", successNum);
        map.put("failureNum", failureNum);
        map.put("failureList", failureList);
        return map;
	}

	@Override
	public void save(UploadPicture entity) {
		// TODO Auto-generated method stub
		entity.setIsNewRecord(true);
		super.save(entity);
	}
	
	public List<UploadPicture> findNoRecogPic(){
		return dao.findNoRecogPic();
	}
	
	
	public void RecogPicSucc(String id){
		dao.RecogPicSucc(id);
	}

	public Map<Object, Object> uploadPictureByZIP(HttpServletRequest request,
			UploadPicture uploadPicture) {
		List<UploadPicture> list = new ArrayList<UploadPicture>();
		 Map<Object, Object> map= Maps.newHashMap();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                	uploadPicture = new UploadPicture();
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();
                    if(StringUtils.substringAfterLast(myFileName, ".").equals("zip")){
                    	CommonsMultipartFile cf= (CommonsMultipartFile)file; 
                        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
                        File f = fi.getStoreLocation();
                    	List<String> fileNameUUId = ZipUtil.decompressByFile(f, upLoadUrl, "utf-8");
                    	System.out.println(fileNameUUId);
                    	UploadPicture picture;
                    	for (String fileName : fileNameUUId) {
                    		picture = new UploadPicture();
                    		String extName = StringUtils.substringAfterLast(fileName,".");
                    		String uuIdName = StringUtils.substringBeforeLast(fileName, ".");
                    		 //定义上传路径 
                    		picture.setId(uuIdName);
                    		picture.setPicName(fileName);
                    		picture.setUrl(uuIdName+"."+extName);
                    		picture.setIsNewRecord(true);
                    		picture.preInsert();
                            list.add(picture);
						}
                    	if(list!=null && list.size()>0){
                    		dao.insertAllPic(list);
                    	}
                    }
                }
            }
        }
        map.put("successNum", list);
        return map;
	}
	
	
}
