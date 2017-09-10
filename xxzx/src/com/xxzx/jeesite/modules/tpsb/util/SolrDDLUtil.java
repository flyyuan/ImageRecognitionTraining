package com.xxzx.jeesite.modules.tpsb.util;

import java.util.ArrayList;
import java.util.List;










import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.QueryLabels;

public class SolrDDLUtil {
	
	
	
	//根据便签。查询图片id
	public static List<String> queryPicIdById(String label,SolrServer solrServer){
		if(label!=null && !"".equals(label)){
			SolrQuery query = new SolrQuery();
			query.setQuery("labels:"+label);
			QueryResponse queryResponse;
			List<String> picIdList = new ArrayList<String>();
			try {
				queryResponse = solrServer.query(query);
				SolrDocumentList solrDocumentList = queryResponse.getResults();
				for (SolrDocument solrDocument : solrDocumentList) {
					String picId = (String)solrDocument.get("id");
					picIdList.add(picId);
				}
				return picIdList;
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//根据用户喜欢的分类获取图片
	public static List<String> queryPicIdByCatIds(List<String> catIds,SolrServer solrServer){
		List<String> result = new ArrayList<String>();
		if(catIds!=null && catIds.size()>0){
			for (String catId : catIds) {
				SolrQuery query = new SolrQuery();
				query.setQuery("picCatIds:"+catId);
				QueryResponse queryResponse;
				try {
					queryResponse = solrServer.query(query);
					SolrDocumentList solrDocumentList = queryResponse.getResults();
					for (SolrDocument solrDocument : solrDocumentList) {
						String picId = (String)solrDocument.get("id");
						result.add(picId);
					}
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
			}
			if(result!=null&&result.size()>0){
				return result;
			}
		}
		
		return null;
	}
	
	//根据图片id获取solr中的分类id
	public static List<String> queryPicCatIdsByPicId(String picId,SolrServer solrServer){
		if(picId!=null && !"".equals(picId)){
			SolrQuery query = new SolrQuery();
			query.setQuery("id:"+picId);
			QueryResponse queryResponse;
			List<String> picCatIds = new ArrayList<String>();
			try {
				queryResponse = solrServer.query(query);
				SolrDocumentList solrDocumentList = queryResponse.getResults();
				for (SolrDocument solrDocument : solrDocumentList) {
					String string = solrDocument.get("picCatIds").toString();
					string = string.substring(0,string.length()-1);
					string = string.substring(1, string.length());
					String[] labels1 = string.split(",");
					String[] labels = new String[labels1.length];
					for (int i=0;i<labels1.length;i++) {
						labels[i] = labels1[i].trim();
						picCatIds.add(labels[i]);
					}
				}
				return picCatIds;
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static PushPicture queryPicByPicId(String picId,SolrServer solrServer){
		if(picId!=null && !"".equals(picId)){
			SolrQuery query = new SolrQuery();
			query.setQuery("id:"+picId);
			PushPicture pushPicture = null;
			QueryResponse queryResponse;
			try {
				queryResponse = solrServer.query(query);
				SolrDocumentList solrDocumentList = queryResponse.getResults();
				for (SolrDocument solrDocument : solrDocumentList) {
					pushPicture = new PushPicture();
					pushPicture.setPicId((String)solrDocument.get("id"));
					String picUrl = solrDocument.get("picUrl").toString();
					picUrl = picUrl.substring(0,picUrl.length()-1);
					picUrl = picUrl.substring(1, picUrl.length());
					pushPicture.setUrl(picUrl);
					
					String string = solrDocument.get("labels").toString();
					string = string.substring(0,string.length()-1);
					string = string.substring(1, string.length());
					String[] labels1 = string.split(",");
					String[] labels = new String[labels1.length];
					for (int i=0;i<labels1.length;i++) {
						labels[i] = labels1[i].trim();
					}
					pushPicture.setLabels( labels);
					
					String picCatIds = solrDocument.get("picCatIds").toString();
					picCatIds = picCatIds.substring(0,picCatIds.length()-1);
					picCatIds = picCatIds.substring(1, picCatIds.length());
					String[] picCatIdss = picCatIds.split(",");
					List<String> catIds = new ArrayList<String>();
					for (int i=0;i<picCatIdss.length;i++) {
						catIds.add(picCatIdss[i].trim());
					}
					pushPicture.setPicCatIds(catIds);
					
				}
				return pushPicture;
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static List<PushPicture> findAllPictureByPicCatId(String picCatId,SolrServer solrServer){
		List<PushPicture> pictureList = null;
		if(picCatId!=null && !"".equals(picCatId)){
			pictureList = new ArrayList<PushPicture>();
			SolrQuery query = new SolrQuery();
			query.setQuery("picCatId:"+picCatId);
			PushPicture pushPicture = null;
			QueryResponse queryResponse;
			try {
				queryResponse = solrServer.query(query);
				SolrDocumentList solrDocumentList = queryResponse.getResults();
				for (SolrDocument solrDocument : solrDocumentList) {
					pushPicture = new PushPicture();
//					pushPicture.setPicId((String)solrDocument.get("id"));
					String picture_name = solrDocument.get("picture_name").toString();
					picture_name = picture_name.substring(0,picture_name.length()-1);
					picture_name = picture_name.substring(1, picture_name.length());
					pushPicture.setPicture_name(picture_name);
					
					String finish_time = solrDocument.get("finish_time").toString();
					pushPicture.setFinish_time(finish_time);
					
					String string = solrDocument.get("labels").toString();
					string = string.substring(0,string.length()-1);
					string = string.substring(1, string.length());
					String[] labels1 = string.split(",");
					String[] labels = new String[labels1.length];
					for (int i=0;i<labels1.length;i++) {
						labels[i] = labels1[i].trim();
					}
					pushPicture.setLabels( labels);
//					pushPicture.setPicCatId((String)solrDocument.get("picCatId"));
					pictureList.add(pushPicture);
				}
				return pictureList;
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static void inserOrUpdateSolr(PushPicture pushPicture,SolrServer solrServer){
		System.out.println("++++++++++++++>"+pushPicture.getLabels().toString());
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", pushPicture.getPicId());
		doc.addField("picUrl", pushPicture.getUrl());
		doc.addField("labels",pushPicture.getLabels() );
		doc.addField("picCatId", pushPicture.getPicCatId());
		
		
		doc.addField("picCatIds", (String [])pushPicture.getPicCatIds().toArray(new String[pushPicture.getPicCatIds().size()]));
		try {
			solrServer.add(doc);
			solrServer.commit();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void insertOrUpdateSolr(String picId,String picUrl,List<String> labels,SolrServer solrServer) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", picId);
		doc.addField("picUrl", picUrl);
		doc.addField("labels",labels );
		try {
			
			solrServer.add(doc);
			solrServer.commit();
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void insertOrUpdateSolr1(String picId,String picUrl,String [] labels,List<String> picCatIds,SolrServer solrServer) {
		String[] arr = (String[])picCatIds.toArray(new String[picCatIds.size()]);
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", picId);
		doc.addField("picUrl", picUrl);
		doc.addField("labels",labels );
		doc.addField("picCatIds",arr );
		try {
			
			solrServer.add(doc);
			solrServer.commit();
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void saveSuccPicture(String picId,String url,String finish_time,String picCatId,String[] labels,SolrServer solrServer){
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", picId);
		doc.addField("picUrl", url);
		doc.addField("picture_name", url);
		doc.addField("finish_time", finish_time);
		doc.addField("labels",labels );
		doc.addField("picCatId", picCatId);
		try {
			
			solrServer.add(doc);
			solrServer.commit();
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void deleteSolr(String picId,SolrServer solrServer) {
		try {
			solrServer.deleteById(picId);
			solrServer.commit();
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void  testQuerySolr(){
		SolrServer solrServer = new HttpSolrServer("http://114.115.139.232:8082/solr/collection2");
		SolrQuery query = new SolrQuery();
		query.setQuery("labels:"+"天空");
		QueryResponse queryResponse;
		List<String> picIdList = new ArrayList<String>();
		try {
			queryResponse = solrServer.query(query);
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			for (SolrDocument solrDocument : solrDocumentList) {
				String picId = (String)solrDocument.get("id");
				picIdList.add(picId);
			}
			System.out.println(picIdList.size());
			System.out.println(picIdList.toString());
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
	
}
