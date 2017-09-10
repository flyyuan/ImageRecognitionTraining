package com.xxzx.jeesite.modules.tpsb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.common.utils.JedisUtils;
import com.xxzx.jeesite.modules.sys.utils.UserUtils;
import com.xxzx.jeesite.modules.tpsb.dao.PushPictureDao;
import com.xxzx.jeesite.modules.tpsb.entity.PicLabel;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
import com.xxzx.jeesite.modules.tpsb.util.LabelAnalyzerUtli;
import com.xxzx.jeesite.modules.tpsb.util.SolrDDLUtil;

@Service
@Transactional(readOnly = true)
public class PushPictureService extends
		CrudService<PushPictureDao, PushPicture> {

	@Autowired
	private PushPictureDao dao;
	@Value("${tomcatUrl}")
	private String tomcatUrl;

	@Value("${picCatNum}")
	private int picCatNum;

	@Value("${jedisCacheSeconds}")
	private int jedisCacheSeconds;

	@Autowired
	private SolrServer solrServer1;

	public List<UploadPicture> getPictureByNum(PushPicture picture) {

		String userKey = picture.getCurrentUser().getId() + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> picIdList = (ArrayList<String>) UserUtils
				.getCache(userKey);

		List<String> picNumList = dao.findPicNum();
		if (picIdList.size() >= picNumList.size()) {
			picIdList.removeAll(picIdList);
		}

		if (picIdList.size() > 0) {
			picture.setPicIdList(picIdList);
		}
		List<UploadPicture> pictureList = dao.getPictureByNum(picture);
		for (UploadPicture uploadPicture : pictureList) {

			picIdList.add(uploadPicture.getId());

			List<String> labelSet = dao
					.findLabelsByPicId(uploadPicture.getId());
			List<String> labelsSet1 = dao
					.findTwoCandidateLabelsByPidId(uploadPicture.getId());
			List<String> labelList = new ArrayList<String>();
			Map<String, String> oneLabelsMap = null;
			if (labelSet != null && labelSet.size() > 0) {

				for (int i = 0; i < labelSet.size(); i++) {
					String label = labelsSet1.get(i);
					labelList.add(label);
					String labels = labelSet.get(i);
					String[] labelSplit = labels.split(",");
					for (String string : labelSplit) {
						labelList.add(string);
					}
				}

				// for (String label : labelSet) {
				// String[] labelSplit = label.split(",");
				// for (String string : labelSplit) {
				// labelList.add(string);
				// }
				// }
				HashSet h1 = new HashSet(labelList);
				labelList.clear();
				labelList.addAll(h1);
				uploadPicture.setLabels(labelList);

				this.getOneLabelAndTwoCandi(uploadPicture);

			} else {
				this.getPicCat(uploadPicture);
				oneLabelsMap = Maps.newLinkedHashMap();
				oneLabelsMap.put("", "");
				labelList.add("");
				uploadPicture.setOneLabelsMap(oneLabelsMap);
				uploadPicture.setLabels(labelList);
				uploadPicture.setTwoCandidateLabels(labelList);
			}

			uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
		}

		return pictureList;
	}

	public List<UploadPicture> getPictureByNumAndNotPicId(PushPicture picture,
			List<String> picIdList) {
		List<UploadPicture> pictureList = dao.getPictureByNumAndNotPicId(
				picture.getPicNumber(), picIdList);
		for (UploadPicture uploadPicture : pictureList) {

			List<String> labelSet = dao
					.findLabelsByPicId(uploadPicture.getId());
			List<String> labelsSet1 = dao
					.findTwoCandidateLabelsByPidId(uploadPicture.getId());
			List<String> labelList = new ArrayList<String>();
			Map<String, String> oneLabelsMap = null;

			if (labelSet != null && labelSet.size() > 0) {

				for (int i = 0; i < labelSet.size(); i++) {
					String label = labelsSet1.get(i);
					labelList.add(label);
					String labels = labelSet.get(i);
					String[] labelSplit = labels.split(",");
					for (String string : labelSplit) {
						labelList.add(string);
					}
				}

				// for (String label : labelSet) {
				// String[] labelSplit = label.split(",");
				// for (String string : labelSplit) {
				// labelList.add(string);
				// }
				// }
				HashSet h1 = new HashSet(labelList);
				labelList.clear();
				labelList.addAll(h1);
				uploadPicture.setLabels(labelList);

				this.getOneLabelAndTwoCandi(uploadPicture);

			} else {
				this.getPicCat(uploadPicture);
				oneLabelsMap = Maps.newLinkedHashMap();
				oneLabelsMap.put("", "");
				labelList.add("");
				uploadPicture.setOneLabelsMap(oneLabelsMap);
				uploadPicture.setLabels(labelList);
				uploadPicture.setTwoCandidateLabels(labelList);
			}

			uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
		}

		return pictureList;
	}

	// 根据用户本人历史填过的标签获取
	public List<UploadPicture> findPictureByUserIdAndSolr(PushPicture picture) {
		String userId = picture.getCurrentUser().getId();
		String userKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> cachepicIdList = (ArrayList<String>) UserUtils
				.getCache(userKey);
		// 去掉缓存重复值
		if (cachepicIdList != null) {
			HashSet h2 = new HashSet(cachepicIdList);
			cachepicIdList.clear();
			cachepicIdList.addAll(h2);
		}
		// 获取图片数量
		List<String> picNumList = dao.findPicNum();
		// 如果缓存图片数量大于图片数量，清空缓存内容
		if (cachepicIdList != null
				&& cachepicIdList.size() >= picNumList.size()) {
			cachepicIdList.removeAll(cachepicIdList);
		}

		List<String> picIdListJedis = JedisUtils.getList(userKey);
		List<String> picIdList = null;

		if (picIdListJedis == null || picIdListJedis.size() == 0) {
			// 获取用户历史填过的标签，去掉 重复值（分词后的）
			List<String> labels1 = dao.findLabelsByUserId(userId);
			// 去掉重复值，获取词元
			List<String> labels = new ArrayList<String>();
			
			for (String labelsss : labels1) {
				String [] labelss = labelsss.split(",");
				for (String label : labelss) {
					labels.add(label);
				}
			}
			HashSet hs = new HashSet(labels);
			labels.clear();
			labels.addAll(hs);
			

			picIdList = new ArrayList<String>();
			// 根据用户历史填过的词元搜索solr
			for (String label : labels) {
				List<String> picidList = SolrDDLUtil.queryPicIdById(label,
						solrServer1);
				if (picidList != null && picidList.size() > 0) {
					for (String picId : picidList) {
						// 保存picid于picIdlist中
						picIdList.add(picId);
					}
				}
			}
			HashSet h = new HashSet(picIdList);
			picIdList.clear();
			picIdList.addAll(h);
			if(picIdList!=null && picIdList.size()>0){
				JedisUtils.setList(userKey, picIdList, jedisCacheSeconds);
			}
		} else if (picIdListJedis != null && picIdListJedis.size() > 0) {
			if (cachepicIdList.size() >= picIdListJedis.size()) {
				return null;
			}
			picIdListJedis.removeAll(cachepicIdList);
			picIdList = picIdListJedis;
		}
		
		// 判断此图片id是否已经被使用
//		for (int i = 0; i < picIdList.size(); i++) {
//			String picId = picIdList.get(i);
//			for (String cachePicId : cachepicIdList) {
//				if (cachePicId.trim().equals(picId.trim())) {
//					picIdList.remove(i);
//					i--;
//				}
//			}
//		}
		List<String> picIds = new ArrayList<String>();
		if (picIdList.size() > 0) {
			// 随机获取指定图片的id
			// 随机获取图片
			if(picIdList.size()>picture.getPicNumber()){
				Random random = new Random();
				for (int i = 0; i < picture.getPicNumber(); i++) {
					int target = random.nextInt(picIdList.size());
					picIds.add(picIdList.get(target));
					cachepicIdList.add(picIdList.get(target));
					picIdList.remove(target);
				}
			}else{
				picIds = picIdList;
				cachepicIdList.addAll(picIdList);
			}

			// 根据solr获取的图片id，获取 数据库中的图片详细信息
			List<UploadPicture> pictureList = dao
					.findPictureByPicIdList(picIds);
			// 遍历图片详细信息，完善图片信息
			for (UploadPicture uploadPicture : pictureList) {
				// 获取图片历史填过的词元
				List<String> labelSet = dao.findLabelsByPicId(uploadPicture
						.getId());
				List<String> labelsSet1 = dao
						.findTwoCandidateLabelsByPidId(uploadPicture.getId());
				List<String> labelList = new ArrayList<String>();
				Map<String, String> oneLabelsMap = null;
				// 得到无重复值的词元
				if (labelSet != null && labelSet.size() > 0) {
					for (int i = 0; i < labelSet.size(); i++) {
						String label = labelsSet1.get(i);
						labelList.add(label);
						String labelss = labelSet.get(i);
						String[] labelSplit = labelss.split(",");
						for (String string : labelSplit) {
							labelList.add(string);
						}
					}
					HashSet h1 = new HashSet(labelList);
					labelList.clear();
					labelList.addAll(h1);
					// 保存所有词元于javabean
					uploadPicture.setLabels(labelList);
					this.getOneLabelAndTwoCandi(uploadPicture);
				} else {
					this.getOneLabelAndTwoCandi(uploadPicture);
				}
				uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
			}
			// solr中的图片数量大于用户需要获取的图片数量
			if (pictureList.size() >= picture.getPicNumber()) {
				List<UploadPicture> pictureList1 = new ArrayList<UploadPicture>();
				// 随机获取图片
//				Random random1 = new Random();
//				for (int i = 0; i < picture.getPicNumber(); i++) {
//					int target = random1.nextInt(pictureList.size());
//					pictureList1.add(pictureList.get(target));
//					cachepicIdList.add(pictureList.get(target).getId());
//					pictureList.remove(target);
//				}
				return pictureList;
				// solr中的图片数量小于于用户需要获取的图片数量
			} else if (pictureList.size() < picture.getPicNumber()) {
				for (UploadPicture uploadPicture : pictureList) {
					cachepicIdList.add(uploadPicture.getId());
				}
				picIdList.addAll(cachepicIdList);
				picture.setPicNumber(picture.getPicNumber()
						- pictureList.size());
				List<UploadPicture> picture1 = this.getPictureByNumAndNotPicId(
						picture, picIdList);
				for (UploadPicture uploadPicture : picture1) {
					cachepicIdList.add(uploadPicture.getId());
					pictureList.add(uploadPicture);
				}
				UserUtils.removeCache(userKey);
				UserUtils.putCache(userKey, cachepicIdList);
				return pictureList;
			} else if (pictureList.size() == 0) {
				return null;
			}
		}
		return null;
	}

	public String getUserPermisStatusByUserId(String userId) {
		return dao.getUserPermisStatusByUserId(userId);
	}

	// 获取只选择的图片
	public List<UploadPicture> pushSelectPicture(PushPicture pushPicture,
			int firstStatu) {
		
		// 获取缓存内容
		String userKey = pushPicture.getCurrentUser().getId() + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> cachePicIdList = (List<String>) UserUtils
				.getCache(userKey);

		// 获取填写完成的图片
		List<UploadPicture> uploadPictures = dao.getSelectPicId();
		List<UploadPicture> uploadPictureLists = new ArrayList<UploadPicture>();

		int j = 0;
		for (String cachePicId : cachePicIdList) {
			for (UploadPicture uploadPicture : uploadPictures) {
				if (cachePicId.equals(uploadPicture.getId())) {
					j++;
				}
			}
		}

		if (j >= uploadPictures.size()) {
			if (firstStatu == 0) {
				cachePicIdList.removeAll(cachePicIdList);
			}
			return new ArrayList<UploadPicture>();
		}

		// 去掉历史获取过的图片
		for (int i = 0; i < uploadPictures.size(); i++) {
			UploadPicture uploadPicture = uploadPictures.get(i);
			for (String cachePicId : cachePicIdList) {
				if (cachePicId.equals(uploadPicture.getId())) {
					uploadPictures.remove(i);
				}
			}
		}
		Random random = new Random();
		for (int i = 0; i < pushPicture.getPicNumber(); i++) {
			int target = random.nextInt(uploadPictures.size());
			uploadPictureLists.add(uploadPictures.get(target));
			uploadPictures.remove(target);
		}

		if (uploadPictureLists != null && uploadPictureLists.size() > 0) {
			for (UploadPicture uploadPicture : uploadPictureLists) {

				// 提交缓存
				cachePicIdList.add(uploadPicture.getId());

				// 获取标签
				List<String> labelSet = dao.findLabelsByPicId(uploadPicture
						.getId());
				List<String> labelsSet1 = dao
						.findTwoCandidateLabelsByPidId(uploadPicture.getId());
				List<String> labelList = new ArrayList<String>();
				Map<String, String> oneLabelsMap = null;
				if (labelSet != null && labelSet.size() > 0) {

					for (int i = 0; i < labelSet.size(); i++) {
						String label = labelsSet1.get(i);
						labelList.add(label);
						String labels = labelSet.get(i);
						String[] labelSplit = labels.split(",");
						for (String string : labelSplit) {
							labelList.add(string);
						}
					}

					// for (String label : labelSet) {
					// String[] labelSplit = label.split(",");
					// for (String string : labelSplit) {
					// labelList.add(string);
					// }
					// }
					HashSet h1 = new HashSet(labelList);
					labelList.clear();
					labelList.addAll(h1);
					uploadPicture.setLabels(labelList);
				} else {
					labelList.add("");
					uploadPicture.setLabels(labelList);
				}

				this.getOneLabelAndTwoCandi(uploadPicture);

				uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());

			}
			return uploadPictureLists;
		} else {
			return null;
		}
	}

	// 获取只填写的图片
	public List<UploadPicture> pushWritePicture(PushPicture pushPicture,
			int firstStatu) {
		//获取缓存内容
		// 获取缓存信息
		//获取用户历史填过的标签
		//根据历史填过的标签搜索solr返回图片id
		//获取选择标签图片的 所有id
		//去除选择标签的图片
		//保存id信息于缓存中
		//获取图片详细信息
		String userId = pushPicture.getCurrentUser().getId();
		String userKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> cachepicIdList = (ArrayList<String>) UserUtils
				.getCache(userKey);
		// 去掉缓存重复值
		if (cachepicIdList != null) {
			HashSet h2 = new HashSet(cachepicIdList);
			cachepicIdList.clear();
			cachepicIdList.addAll(h2);
		}
		// 获取图片数量
		List<String> selectPics = dao.findSelectPic();
		// 如果缓存图片数量大于图片数量，清空缓存内容
		if (cachepicIdList != null
				&& cachepicIdList.size() >= selectPics.size()) {
			cachepicIdList.removeAll(cachepicIdList);
		}

		List<String> picIdListJedis = JedisUtils.getList(userKey);
		List<String> picIdList = null;

		if (picIdListJedis == null || picIdListJedis.size() == 0) {
			// 获取用户历史填过的标签，去掉 重复值（分词后的）
			List<String> labels1 = dao.findLabelsByUserId(userId);
			// 去掉重复值，获取词元
			List<String> labels = new ArrayList<String>();
			
			for (String labelsss : labels1) {
				String [] labelss = labelsss.split(",");
				for (String label : labelss) {
					labels.add(label);
				}
			}
			HashSet hs = new HashSet(labels);
			labels.clear();
			labels.addAll(hs);
			

			picIdList = new ArrayList<String>();
			// 根据用户历史填过的词元搜索solr
			for (String label : labels) {
				List<String> picidList = SolrDDLUtil.queryPicIdById(label,
						solrServer1);
				if (picidList != null && picidList.size() > 0) {
					for (String picId : picidList) {
						// 保存picid于picIdlist中
						picIdList.add(picId);
					}
				}
			} 
			HashSet h = new HashSet(picIdList);
			picIdList.clear();
			picIdList.addAll(h);
			picIdList.removeAll(selectPics);
			if(picIdList!=null && picIdList.size()>0){
				JedisUtils.setList(userKey, picIdList, jedisCacheSeconds);
			}
		} else if (picIdListJedis != null && picIdListJedis.size() > 0) {
			if (cachepicIdList.size() >= picIdListJedis.size()) {
				return null;
			}
			picIdListJedis.removeAll(cachepicIdList);
			picIdList = picIdListJedis;
		}
		
		// 判断此图片id是否已经被使用
		List<String> picIds = new ArrayList<String>();
		if (picIdList.size() > 0) {
			// 随机获取指定图片的id
			// 随机获取图片
			if(picIdList.size()>pushPicture.getPicNumber()){
				Random random = new Random();
				for (int i = 0; i < pushPicture.getPicNumber(); i++) {
					int target = random.nextInt(picIdList.size());
					picIds.add(picIdList.get(target));
					cachepicIdList.add(picIdList.get(target));
					picIdList.remove(target);
				}
			}else{
				picIds = picIdList;
				cachepicIdList.addAll(picIdList);
			}

			// 根据solr获取的图片id，获取 数据库中的图片详细信息
			List<UploadPicture> pictureList = dao
					.findPictureByPicIdList(picIds);
			// 遍历图片详细信息，完善图片信息
			for (UploadPicture uploadPicture : pictureList) {
				// 获取图片历史填过的词元
				List<String> labelSet = dao.findLabelsByPicId(uploadPicture
						.getId());
				List<String> labelsSet1 = dao
						.findTwoCandidateLabelsByPidId(uploadPicture.getId());
				List<String> labelList = new ArrayList<String>();
				Map<String, String> oneLabelsMap = null;
				// 得到无重复值的词元
				if (labelSet != null && labelSet.size() > 0) {
					for (int i = 0; i < labelSet.size(); i++) {
						String label = labelsSet1.get(i);
						labelList.add(label);
						String labelss = labelSet.get(i);
						String[] labelSplit = labelss.split(",");
						for (String string : labelSplit) {
							labelList.add(string);
						}
					}
					HashSet h1 = new HashSet(labelList);
					labelList.clear();
					labelList.addAll(h1);
					// 保存所有词元于javabean
					uploadPicture.setLabels(labelList);
					this.getOneLabelAndTwoCandi(uploadPicture);
				} else {
					this.getOneLabelAndTwoCandi(uploadPicture);
				}
				uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
			}
			// solr中的图片数量大于用户需要获取的图片数量
			if (pictureList.size() >= pushPicture.getPicNumber()) {
				List<UploadPicture> pictureList1 = new ArrayList<UploadPicture>();
				// 随机获取图片
				return pictureList;
				// solr中的图片数量小于于用户需要获取的图片数量
			} else if (pictureList.size() < pushPicture.getPicNumber()) {
				for (UploadPicture uploadPicture : pictureList) {
					cachepicIdList.add(uploadPicture.getId());
				}
				picIdList.addAll(cachepicIdList);
				pushPicture.setPicNumber(pushPicture.getPicNumber()
						- pictureList.size());
				List<UploadPicture> picture1 = this.getPictureByNumAndNotPicId(
						pushPicture, picIdList);
				for (UploadPicture uploadPicture : picture1) {
					cachepicIdList.add(uploadPicture.getId());
					pictureList.add(uploadPicture);
				}
				UserUtils.removeCache(userKey);
				UserUtils.putCache(userKey, cachepicIdList);
				return pictureList;
			} else if (pictureList.size() == 0) {
				return null;
			}
		}
		return null;
		
		//
		
		// 获取缓存内容
//		String userKey = pushPicture.getCurrentUser().getId() + "-"
//				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//		List<String> cachePicIdList = (List<String>) UserUtils
//				.getCache(userKey);
//
//		// 获取需要填写的图片
//		List<UploadPicture> uploadPictures = dao.getWritePicId();
//		List<UploadPicture> uploadPictureLists = new ArrayList<UploadPicture>();
//		int j = 0;
//		for (String cachePicId : cachePicIdList) {
//			for (UploadPicture uploadPicture : uploadPictures) {
//				if (cachePicId.equals(uploadPicture.getId())) {
//					j++;
//				}
//			}
//		}
//
//		if (j >= uploadPictures.size()) {
//			if (firstStatu == 0) {
//				cachePicIdList.removeAll(cachePicIdList);
//			}
//			return new ArrayList<UploadPicture>();
//		}
//
//		// 去掉历史获取过的图片
//		for (int i = 0; i < uploadPictures.size(); i++) {
//			UploadPicture uploadPicture = uploadPictures.get(i);
//			for (String cachePicId : cachePicIdList) {
//				if (cachePicId.equals(uploadPicture.getId())) {
//					uploadPictures.remove(i);
//				}
//			}
//		}
//		Random random = new Random();
//		for (int i = 0; i < pushPicture.getPicNumber(); i++) {
//			int target = random.nextInt(uploadPictures.size());
//			uploadPictureLists.add(uploadPictures.get(target));
//			uploadPictures.remove(target);
//		}
//		if (uploadPictureLists != null && uploadPictureLists.size() > 0) {
//			for (UploadPicture uploadPicture : uploadPictureLists) {
//
//				// 提交缓存
//				cachePicIdList.add(uploadPicture.getId());
//
//				// 获取标签
//				List<String> labelSet = dao.findLabelsByPicId(uploadPicture
//						.getId());
//				List<String> labelsSet1 = dao
//						.findTwoCandidateLabelsByPidId(uploadPicture.getId());
//				List<String> labelList = new ArrayList<String>();
//				Map<String, String> oneLabelsMap = null;
//				if (labelSet != null && labelSet.size() > 0) {
//
//					for (int i = 0; i < labelSet.size(); i++) {
//						String label = labelsSet1.get(i);
//						labelList.add(label);
//						String labels = labelSet.get(i);
//						String[] labelSplit = labels.split(",");
//						for (String string : labelSplit) {
//							labelList.add(string);
//						}
//					}
//
//					// for (String label : labelSet) {
//					// String[] labelSplit = label.split(",");
//					// for (String string : labelSplit) {
//					// labelList.add(string);
//					// }
//					// }
//					HashSet h1 = new HashSet(labelList);
//					labelList.clear();
//					labelList.addAll(h1);
//					uploadPicture.setLabels(labelList);
//				} else {
//					labelList.add("");
//					uploadPicture.setLabels(labelList);
//				}
//
//				this.getOneLabelAndTwoCandi(uploadPicture);
//
//				uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
//			}
//			return uploadPictureLists;
//		} else {
//			return null;
//		}
	}

	// 获取第一层标签，第一层候选标签,随机 获取五个便签

	private UploadPicture getOneLabelAndTwoCandi(UploadPicture uploadPicture) {
		// 获取第一层标签
		List<PicLabel> oneLabels = dao.findOneLabelsByPicId(uploadPicture
				.getId());
		List<String> oneLabelsList = null;
		Map<String, String> oneLabelsMap = null;
		Map<String, String> picCatMap = null;
		if (oneLabels != null && oneLabels.size() > 0) {
			oneLabelsMap = Maps.newLinkedHashMap();
			oneLabelsList = new ArrayList<String>();
			for (PicLabel picLabel : oneLabels) {
				oneLabelsMap.put(picLabel.getId(), picLabel.getLabel());
				oneLabelsList.add(picLabel.getLabel());
			}
			uploadPicture.setOneLabelsMap(oneLabelsMap);
		} else {
			oneLabelsMap = Maps.newHashMap();
			oneLabelsMap.put("", "");
			uploadPicture.setOneLabelsMap(oneLabelsMap);
		}

		// 获取第一层候选标签
		List<String> twoCandidateLabels = dao
				.findTwoCandidateLabelsByPidId(uploadPicture.getId());
		if (twoCandidateLabels != null && twoCandidateLabels.size() > 0
				&& oneLabelsList != null) {
			for (String string : oneLabelsList) {
				for (int i = 0; i < twoCandidateLabels.size(); i++) {
					String twoLabel = twoCandidateLabels.get(i);
					if (twoLabel.equals(string)) {
						twoCandidateLabels.remove(i);
					}
				}
			}
			uploadPicture.setTwoCandidateLabels(twoCandidateLabels);
		} else {
			twoCandidateLabels.add("");
			uploadPicture.setTwoCandidateLabels(twoCandidateLabels);
		}
		
		//获取solr中的图片分类
		List<String> picCatIds = SolrDDLUtil.queryPicCatIdsByPicId(uploadPicture.getId(), solrServer1);
		if(picCatIds!=null &&picCatIds.size()>0){
			List<TpsbCat> picCatList = dao.getPicCatByCatIds(picCatIds);
			if (picCatList != null && picCatList.size() > 0 && picCatList.size()==5) {
				picCatMap = Maps.newHashMap();
				for (TpsbCat tpsbCat : picCatList) {
					picCatMap.put(tpsbCat.getId(), tpsbCat.getName());
				}
				uploadPicture.setPicCatMap(picCatMap);
			}else{
				// 随机获取五个标签分类
				List<TpsbCat> picCatList1 = dao.getPicCatByNum(picCatNum);
				if (picCatList1 != null && picCatList1.size() > 0) {
					picCatMap = Maps.newHashMap();
					for (TpsbCat tpsbCat : picCatList1) {
						picCatMap.put(tpsbCat.getId(), tpsbCat.getName());
					}
					uploadPicture.setPicCatMap(picCatMap);
				} else {
					picCatMap = Maps.newHashMap();
					picCatMap.put("", "");
					uploadPicture.setPicCatMap(picCatMap);
				}
			}
		}else{
			// 随机获取五个标签分类
			List<TpsbCat> picCatList = dao.getPicCatByNum(picCatNum);
			if (picCatList != null && picCatList.size() > 0) {
				picCatMap = Maps.newHashMap();
				for (TpsbCat tpsbCat : picCatList) {
					picCatMap.put(tpsbCat.getId(), tpsbCat.getName());
				}
				uploadPicture.setPicCatMap(picCatMap);
			} else {
				picCatMap = Maps.newHashMap();
				picCatMap.put("", "");
				uploadPicture.setPicCatMap(picCatMap);
			}
		}
		
		

		return uploadPicture;
	}

	// 随机获取便签分类
	private UploadPicture getPicCat(UploadPicture uploadPicture) {

		// 随机获取五个标签分类
		List<TpsbCat> picCatList = dao.getPicCatByNum(picCatNum);
		Map<String, String> picCatMap = null;
		if (picCatList != null && picCatList.size() > 0) {
			picCatMap = Maps.newHashMap();
			for (TpsbCat tpsbCat : picCatList) {
				picCatMap.put(tpsbCat.getId(), tpsbCat.getName());
			}
			uploadPicture.setPicCatMap(picCatMap);
		} else {
			picCatMap = Maps.newHashMap();
			picCatMap.put("", "");
			uploadPicture.setPicCatMap(picCatMap);
		}
		return uploadPicture;
	}

	// 获取相关联用户的标签
	public List<UploadPicture> findPictureByAssoUserAndSolr(PushPicture picture) {
		// 获取缓存信息
		String userId = picture.getCurrentUser().getId();
		String userKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String assouserKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ "-asso";
		List<String> cachepicIdList = (ArrayList<String>) UserUtils
				.getCache(userKey);
		// 去掉缓存重复值
		if (cachepicIdList != null) {
			HashSet h2 = new HashSet(cachepicIdList);
			cachepicIdList.clear();
			cachepicIdList.addAll(h2);
		}
		// 获取图片数量
		List<String> picNumList = dao.findPicNum();
		// 如果缓存图片数量大于图片数量，清空缓存内容
		if (cachepicIdList != null
				&& cachepicIdList.size() >= picNumList.size()) {
			cachepicIdList.removeAll(cachepicIdList);
		}
		List<String> picIdListJedis = JedisUtils.getList(assouserKey);
		List<String> picIdList = null;

		if (picIdListJedis == null || picIdListJedis.size() == 0) {
			// 获取关联用户所有标签
			List<String> labels = dao.findAssoLabelsByUserId(userId);

			picIdList = new ArrayList<String>();
			// 根据关联用户搜索solr返回所有图片id
			for (String label : labels) {
				List<String> picidList = SolrDDLUtil.queryPicIdById(label,
						solrServer1);
				if (picidList != null && picidList.size() > 0) {
					for (String picId : picidList) {
						// 保存picid于picIdlist中
						picIdList.add(picId);
					}
				}
			}
			HashSet h = new HashSet(picIdList);
			picIdList.clear();
			picIdList.addAll(h);
			// 去除已经获取过的图片
			List<String> exists = new ArrayList<String>(picIdList);
			exists.removeAll(cachepicIdList);
			if(exists!=null && exists.size()>0){
				JedisUtils.setList(assouserKey, exists, jedisCacheSeconds);
			}
		} else if (picIdListJedis != null && picIdListJedis.size() > 0) {
			picIdListJedis.removeAll(cachepicIdList);
			picIdList = picIdListJedis;
		}

		// 根据图片id获取数据库中图片的详细信息
		List<String> picIds = new ArrayList<String>();
		if (picIdList.size() > 0) {
			// 随机获取指定图片的id
			// 随机获取图片
			if(picIdList.size()>picture.getPicNumber()){
				Random random = new Random();
				for (int i = 0; i < picture.getPicNumber(); i++) {
					int target = random.nextInt(picIdList.size());
					picIds.add(picIdList.get(target));
					cachepicIdList.add(picIdList.get(target));
					picIdList.remove(target);
				}
			}else{
				picIds = picIdList;
				cachepicIdList.addAll(picIdList);
			}

			// 根据solr获取的图片id，获取 数据库中的图片详细信息
			List<UploadPicture> pictureList = dao
					.findPictureByPicIdList(picIds);
			// 遍历图片详细信息，完善图片信息
			for (UploadPicture uploadPicture : pictureList) {
				// 获取图片历史填过的词元
				List<String> labelSet = dao.findLabelsByPicId(uploadPicture
						.getId());
				List<String> labelsSet1 = dao
						.findTwoCandidateLabelsByPidId(uploadPicture.getId());
				List<String> labelList = new ArrayList<String>();
				Map<String, String> oneLabelsMap = null;
				// 得到无重复值的词元
				if (labelSet != null && labelSet.size() > 0) {
					for (int i = 0; i < labelSet.size(); i++) {
						String label = labelsSet1.get(i);
						labelList.add(label);
						String labelss = labelSet.get(i);
						String[] labelSplit = labelss.split(",");
						for (String string : labelSplit) {
							labelList.add(string);
						}
					}
					HashSet h1 = new HashSet(labelList);
					labelList.clear();
					labelList.addAll(h1);
					// 保存所有词元于javabean
					uploadPicture.setLabels(labelList);
					this.getOneLabelAndTwoCandi(uploadPicture);
				} else {
					this.getOneLabelAndTwoCandi(uploadPicture);
				}
				uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
			}
			
			// solr中的图片数量大于用户需要获取的图片数量
			if (pictureList.size() >= picture.getPicNumber()) {
//				List<UploadPicture> pictureList1 = new ArrayList<UploadPicture>();
//				// 随机获取图片
//				Random random1 = new Random();
//				for (int i = 0; i < picture.getPicNumber(); i++) {
//					int target = random1.nextInt(pictureList.size());
//					pictureList1.add(pictureList.get(target));
//					cachepicIdList.add(pictureList.get(target).getId());
//					pictureList.remove(target);
//				}
				return pictureList;
				// solr中的图片数量小于于用户需要获取的图片数量
			} else if (pictureList.size() < picture.getPicNumber()) {
//				for (UploadPicture uploadPicture : pictureList) {
//					cachepicIdList.add(uploadPicture.getId());
//				}
//				picIdList.addAll(cachepicIdList);
				picture.setPicNumber(picture.getPicNumber()
						- pictureList.size());
				List<UploadPicture> picture1 = this.getPictureByNumAndNotPicId(
						picture, picIdList);
				for (UploadPicture uploadPicture : picture1) {
					cachepicIdList.add(uploadPicture.getId());
					pictureList.add(uploadPicture);
				}
//				UserUtils.removeCache(userKey);
//				UserUtils.putCache(userKey, cachepicIdList);
				return pictureList;
			} else if (pictureList.size() == 0) {
				return null;
			}
		}
		return null;
	}
	//完成图片任务
	public List<UploadPicture> findPictureByTask(PushPicture picture) {
		//获取缓存中的任务图片
		String userId = picture.getCurrentUser().getId();
		String userKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> cachepicIdList = (ArrayList<String>) UserUtils
				.getCache(userKey);
		// 去掉缓存重复值
		if (cachepicIdList != null) {
			HashSet h2 = new HashSet(cachepicIdList);
			cachepicIdList.clear();
			cachepicIdList.addAll(h2);
		}
		
		List<String> userTaskKeys = JedisUtils.getkeys(userId+"-taskId-*");
		String userTaskKey = null;
		if(userTaskKeys!=null && userTaskKeys.size()>0 && userTaskKeys.size()==1){
			userTaskKey = userTaskKeys.get(0);
		}else{
			return null;
		}
		List<String> picIdlist = JedisUtils.getList(userTaskKey);
		//去除缓存的中的图片
		picIdlist.removeAll(cachepicIdList);
		
		List<String> picIds = new ArrayList<String>();
		
		if(picIdlist.size()>picture.getPicNumber()){
			//有足够多的任务图片
			Random random = new Random();
			int j = picIdlist.size() - picture.getPicNumber();
			for (int i = 0; i <j ; i++) {
				int k = random.nextInt(picIdlist.size());
				picIdlist.remove(k);
			}
			picIds.addAll(picIdlist);
		}else if(picIdlist.size()>0 && picIdlist.size()<=picture.getPicNumber()){
			//没有 足够多的任务图片
			int j = picture.getPicNumber()-picIdlist.size();
			//获取缺少的图片
			List<String> picids = dao.getPicByNum(j);
			picIds.addAll(picIdlist);
			picIds.addAll(picids);
			
		}else if(picIdlist.size()<=0){
			return null;
		}
		
		cachepicIdList.addAll(picIds);
		
		//根据缓存中的图片id获取图片id
		// 根据solr获取的图片id，获取 数据库中的图片详细信息
		//根据图片id获取图片详细信息
		List<UploadPicture> pictureList = dao
				.findPictureByPicIdList(picIds);
		// 遍历图片详细信息，完善图片信息
		for (UploadPicture uploadPicture : pictureList) {
			// 获取图片历史填过的词元
			List<String> labelSet = dao.findLabelsByPicId(uploadPicture
					.getId());
			List<String> labelsSet1 = dao
					.findTwoCandidateLabelsByPidId(uploadPicture.getId());
			List<String> labelList = new ArrayList<String>();
			Map<String, String> oneLabelsMap = null;
			// 得到无重复值的词元
			if (labelSet != null && labelSet.size() > 0) {
				for (int i = 0; i < labelSet.size(); i++) {
					String label = labelsSet1.get(i);
					labelList.add(label);
					String labelss = labelSet.get(i);
					String[] labelSplit = labelss.split(",");
					for (String string : labelSplit) {
						labelList.add(string);
					}
				}
				HashSet h1 = new HashSet(labelList);
				labelList.clear();
				labelList.addAll(h1);
				// 保存所有词元于javabean
				uploadPicture.setLabels(labelList);
				this.getOneLabelAndTwoCandi(uploadPicture);
			} else {
				this.getOneLabelAndTwoCandi(uploadPicture);
			}
			uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
		}
		//返回
		return pictureList;
	}
	
	//根据用户喜欢的分类获取图片
	public List<UploadPicture> findPictureByUserCat(PushPicture picture) {
		//获取缓存信息
		//根据分类id搜索solr获取图片id
		
		//补充图片信息
		// 获取缓存信息
		String userId = picture.getCurrentUser().getId();
		String userKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String catKey = userId + "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ "-ByCatId";
		List<String> cachepicIdList = (ArrayList<String>) UserUtils
				.getCache(userKey);
		// 去掉缓存重复值
		if (cachepicIdList != null) {
			HashSet h2 = new HashSet(cachepicIdList);
			cachepicIdList.clear();
			cachepicIdList.addAll(h2);
		}
		// 获取图片数量
		List<String> picNumList = dao.findPicNum();
		// 如果缓存图片数量大于图片数量，清空缓存内容
		if (cachepicIdList != null
				&& cachepicIdList.size() >= picNumList.size()) {
			cachepicIdList.removeAll(cachepicIdList);
		}
		List<String> picIdListJedis = JedisUtils.getList(catKey);
		List<String> picIdList = null;

		if (picIdListJedis == null || picIdListJedis.size() == 0) {
			//获取用户的兴趣分类id
			List<String> catIds = dao.findUserCatsByUserId(userId);

			picIdList = new ArrayList<String>();
			// 根据关联用户搜索solr返回所有图片id
			picIdList = SolrDDLUtil.queryPicIdByCatIds(catIds, solrServer1);
			if(picIdList==null || picIdList.size()==0){
				return null;
			}
			// 去除已经获取过的图片
			List<String> exists = new ArrayList<String>(picIdList);
			exists.removeAll(cachepicIdList);
			if(exists!=null && exists.size()>0){
				JedisUtils.setList(catKey, exists, jedisCacheSeconds);
			}
		} else if (picIdListJedis != null && picIdListJedis.size() > 0) {
			picIdListJedis.removeAll(cachepicIdList);
			picIdList = picIdListJedis;
		}

		// 根据图片id获取数据库中图片的详细信息
		List<String> picIds = new ArrayList<String>();
		if (picIdList.size() > 0) {
			// 随机获取指定图片的id
			// 随机获取图片
			if(picIdList.size()>picture.getPicNumber()){
				Random random = new Random();
				for (int i = 0; i < picture.getPicNumber(); i++) {
					int target = random.nextInt(picIdList.size());
					picIds.add(picIdList.get(target));
					cachepicIdList.add(picIdList.get(target));
					picIdList.remove(target);
				}
			}else{
				picIds = picIdList;
				cachepicIdList.addAll(picIdList);
			}

			// 根据solr获取的图片id，获取 数据库中的图片详细信息
			List<UploadPicture> pictureList = dao
					.findPictureByPicIdList(picIds);
			// 遍历图片详细信息，完善图片信息
			for (UploadPicture uploadPicture : pictureList) {
				// 获取图片历史填过的词元
				List<String> labelSet = dao.findLabelsByPicId(uploadPicture
						.getId());
				List<String> labelsSet1 = dao
						.findTwoCandidateLabelsByPidId(uploadPicture.getId());
				List<String> labelList = new ArrayList<String>();
				Map<String, String> oneLabelsMap = null;
				// 得到无重复值的词元
				if (labelSet != null && labelSet.size() > 0) {
					for (int i = 0; i < labelSet.size(); i++) {
						String label = labelsSet1.get(i);
						labelList.add(label);
						String labelss = labelSet.get(i);
						String[] labelSplit = labelss.split(",");
						for (String string : labelSplit) {
							labelList.add(string);
						}
					}
					HashSet h1 = new HashSet(labelList);
					labelList.clear();
					labelList.addAll(h1);
					// 保存所有词元于javabean
					uploadPicture.setLabels(labelList);
					this.getOneLabelAndTwoCandi(uploadPicture);
				} else {
					this.getOneLabelAndTwoCandi(uploadPicture);
				}
				uploadPicture.setUrl(tomcatUrl + uploadPicture.getUrl());
			}
			
			// solr中的图片数量大于用户需要获取的图片数量
			if (pictureList.size() >= picture.getPicNumber()) {
//						List<UploadPicture> pictureList1 = new ArrayList<UploadPicture>();
//						// 随机获取图片
//						Random random1 = new Random();
//						for (int i = 0; i < picture.getPicNumber(); i++) {
//							int target = random1.nextInt(pictureList.size());
//							pictureList1.add(pictureList.get(target));
//							cachepicIdList.add(pictureList.get(target).getId());
//							pictureList.remove(target);
//						}
				return pictureList;
				// solr中的图片数量小于于用户需要获取的图片数量
			} else if (pictureList.size() < picture.getPicNumber()) {
//						for (UploadPicture uploadPicture : pictureList) {
//							cachepicIdList.add(uploadPicture.getId());
//						}
//						picIdList.addAll(cachepicIdList);
				picture.setPicNumber(picture.getPicNumber()
						- pictureList.size());
				List<UploadPicture> picture1 = this.getPictureByNumAndNotPicId(
						picture, picIdList);
				for (UploadPicture uploadPicture : picture1) {
					cachepicIdList.add(uploadPicture.getId());
					pictureList.add(uploadPicture);
				}
//						UserUtils.removeCache(userKey);
//						UserUtils.putCache(userKey, cachepicIdList);
				return pictureList;
			} else if (pictureList.size() == 0) {
				return null;
			}
		}
		return null;
	}

}
