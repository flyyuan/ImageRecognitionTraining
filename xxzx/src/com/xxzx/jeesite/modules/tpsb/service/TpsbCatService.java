package com.xxzx.jeesite.modules.tpsb.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxzx.jeesite.common.persistence.Page;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.modules.tpsb.dao.TpsbCatDao;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;
import com.xxzx.jeesite.modules.tpsb.util.SolrDDLUtil;

/**
 * 单表生成Service
 * @author duang
 * @version 2017-06-02
 */
@Service
@Transactional(readOnly = true)
public class TpsbCatService extends CrudService<TpsbCatDao, TpsbCat> {
	
	
	@Autowired
	private SolrServer solrServer;

	public TpsbCat get(String id) {
		return super.get(id);
	}
	
	public List<TpsbCat> findList(TpsbCat tpsbCat) {
		return super.findList(tpsbCat);
	}
	
	public Page<TpsbCat> findPage(Page<TpsbCat> page, TpsbCat tpsbCat) {
		return super.findPage(page, tpsbCat);
	}
	
	@Transactional(readOnly = false)
	public void save(TpsbCat tpsbCat) {
		super.save(tpsbCat);
	}
	
	@Transactional(readOnly = false)
	public void delete(TpsbCat tpsbCat) {
		super.delete(tpsbCat);
	}
	
	
	//根据大类id获取大类以下的所有图片
	public List<PushPicture> findAllPictureByPicCatId(String picCatId) {
		List<PushPicture> pictureList = SolrDDLUtil.findAllPictureByPicCatId(picCatId, solrServer);
		return pictureList;
	}
	
}