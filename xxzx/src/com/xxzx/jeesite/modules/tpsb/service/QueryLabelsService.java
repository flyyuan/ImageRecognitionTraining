package com.xxzx.jeesite.modules.tpsb.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxzx.jeesite.modules.tpsb.dao.QueryLabelsDao;
import com.xxzx.jeesite.modules.tpsb.entity.QueryLabels;

@Service
public class QueryLabelsService {
	@Autowired
	private QueryLabelsDao dao;
	
	
	
	public QueryLabels queryLabels(String queryString) throws Exception{
		SolrQuery query = new SolrQuery();
		if(queryString!=null && !"".equals(queryString)){
			query.setQuery("labels:"+queryString);
		}
		QueryLabels queryLabels = dao.queryLabels(query);
		return queryLabels;
	}
	
}
