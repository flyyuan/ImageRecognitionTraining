package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.QueryLabels;

@Repository
public class QueryLabelsDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public QueryLabels queryLabels(SolrQuery query) throws Exception {
		
		QueryLabels queryLabels  = new QueryLabels();
		PushPicture pushPicture;
		
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		
		List<PushPicture> productList = new ArrayList<PushPicture>();
		
		for (SolrDocument solrDocument : solrDocumentList) {
			pushPicture = new PushPicture();
//			pushPicture.setPicId((String)solrDocument.get("id"));
			String picture_name = solrDocument.get("picture_name").toString();
//			picture_name = picture_name.substring(0,picture_name.length()-1);
//			picture_name = picture_name.substring(1, picture_name.length());
			pushPicture.setPicture_name(picture_name);
			
			
			String finish_time = solrDocument.get("finish_time").toString();
			pushPicture.setFinish_time(finish_time);
			
			String string = solrDocument.get("labels").toString();
			string = string.substring(0,string.length()-1);
			string = string.substring(1, string.length());
			String[] labels = string.split(",");
			pushPicture.setLabels( labels);
			productList.add(pushPicture);
		}
		queryLabels.setPushPicture(productList);
		
		return queryLabels;

	
	}
	
}
