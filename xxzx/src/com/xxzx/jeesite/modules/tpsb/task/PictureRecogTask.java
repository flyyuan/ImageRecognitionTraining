package com.xxzx.jeesite.modules.tpsb.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;

import com.xxzx.jeesite.modules.tpsb.entity.PictureYouTu;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
import com.xxzx.jeesite.modules.tpsb.service.TpsbCatService;
import com.xxzx.jeesite.modules.tpsb.service.UploadPictureService;
import com.xxzx.jeesite.modules.tpsb.util.CrawlerUtil;
import com.xxzx.jeesite.modules.tpsb.util.PictureYouTuUtil;
import com.xxzx.jeesite.modules.tpsb.util.SolrDDLUtil;

@Service
@Lazy(false)
public class PictureRecogTask {
	
	@Autowired
	private UploadPictureService service;
	
	@Autowired
	private TpsbCatService catService;
	
	@Value("${upLoadUrl}")
	private String upLoadUrl;
	
	@Value("${tomcatUrl}")
	private String tomcatUrl;
	
	@Autowired
	private SolrServer solrServer1;
	
	
	
//	@Scheduled(cron ="0 */2 * * * ?")
	//Spring Task 4.1   任务调度，，，每天凌晨2点
	@Scheduled(cron ="0 0 2 * * ?")
	public void pictureRecog(){
		//获取未识别的图片
		List<UploadPicture> NoRecogPicList = service.findNoRecogPic();
		//获取所有图片分类
		List<TpsbCat> catList = catService.findList(new TpsbCat());	
		List<String> classs = new ArrayList<String>();
		for (TpsbCat tpsbCat : catList) {
			classs.add(tpsbCat.getName().toString());
		}
		
		//进行图片初识别
		for (UploadPicture uploadPicture : NoRecogPicList) {
			//得到图片识别的结果
			List<String> tagList = null;
			PictureYouTu picYouTu = PictureYouTuUtil.getPicYouTuByPath(upLoadUrl+uploadPicture.getUrl());
			
			if(picYouTu==null){
				picYouTu = PictureYouTuUtil.getPicYouTuByUrl(tomcatUrl+uploadPicture.getUrl());
			}
			if(picYouTu!=null){
				//获取标签中的所有结果
				tagList = new ArrayList<String>();
				List<Map<String, Object>> tagsList = picYouTu.getTags();
				for (Map<String, Object> map : tagsList) {
					for (Map.Entry<String, Object> map1 : map.entrySet()) {
						if("tag_name".equals(map1.getKey())){
							tagList.add(map1.getValue().toString());
						}
					}
				}
				if(tagList!=null && tagList.size()>0 ){
					//百度百科
					List<String> classList = CrawlerUtil.getClassByBaiKe(tagList, classs);
					List<String> picCatIds = new ArrayList<String>();
					List<String> classss ;
					if(classList!=null && classList.size()>0 && classList.size()<5){
						classss = new ArrayList<String>();
						classss = classs;
						classss.removeAll(classList);
						classList.addAll(CrawlerUtil.getClassByBaidu(tagList, classss,5-classList.size()));
					}else if (classList==null || classList.size()==0){
						//百度搜索结果
						classList=CrawlerUtil.getClassByBaidu(tagList, classs,5);
					}
					for (String picCatName : classList) {
						for (TpsbCat tpsbCat : catList) {
							if(picCatName.trim().equals(tpsbCat.getName().trim())){
								picCatIds.add(tpsbCat.getId());
							}
						}
					}
					//更新到solr
					try {
						PushPicture picture = SolrDDLUtil.queryPicByPicId(uploadPicture.getId(), solrServer1);
						if(picture!=null && picture.getLabels()!=null && picture.getLabels().length>0){
							String[] tags = (String[])tagList.toArray(new String[tagList.size()]);
							String[] labels = picture.getLabels();
							String[] resultTag = Arrays.copyOf(tags, tags.length + labels.length);  
							System.arraycopy(labels, 0, resultTag, tags.length, labels.length);  
							picture.setPicCatIds(picCatIds);
							picture.setLabels(resultTag);
							SolrDDLUtil.inserOrUpdateSolr(picture, solrServer1);
						}else{
							PushPicture picture1 = new PushPicture();
							picture1.setPicId(uploadPicture.getId());
							picture1.setUrl(tomcatUrl+uploadPicture.getUrl());
							String [] a = (String[])tagList.toArray(new String[tagList.size()]);
							System.out.println("------------->"+a.toString());
							picture1.setLabels(a);
							picture1.setPicCatIds(picCatIds);
							SolrDDLUtil.inserOrUpdateSolr(picture1, solrServer1);
						}
						
						//标记图片为已经识别
						service.RecogPicSucc(uploadPicture.getId());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				
				
				
			}
			
			
		}
		
		
		
		
	}
	
}
