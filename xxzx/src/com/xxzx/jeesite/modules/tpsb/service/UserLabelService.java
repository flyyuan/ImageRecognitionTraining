package com.xxzx.jeesite.modules.tpsb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.common.utils.JedisUtils;
import com.xxzx.jeesite.common.utils.StringUtils;
import com.xxzx.jeesite.modules.sys.utils.UserUtils;
import com.xxzx.jeesite.modules.tpsb.dao.TaskAllocatDao;
import com.xxzx.jeesite.modules.tpsb.dao.UserLabelDao;
import com.xxzx.jeesite.modules.tpsb.entity.PicLabel;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.TaskAllocat;
import com.xxzx.jeesite.modules.tpsb.entity.UserIntegral;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserSuccessRecord;
import com.xxzx.jeesite.modules.tpsb.filter.FilterChain;
import com.xxzx.jeesite.modules.tpsb.filter.HTMLFilter;
import com.xxzx.jeesite.modules.tpsb.filter.SesitiveFilter;
import com.xxzx.jeesite.modules.tpsb.util.LabelAnalyzerUtli;
import com.xxzx.jeesite.modules.tpsb.util.SolrDDLUtil;
@Service
@Transactional(readOnly = true)
public class UserLabelService extends CrudService<UserLabelDao, UserLabel> {
	
	@Autowired
	private UserLabelDao dao;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private TaskAllocatDao taskDao;
	
	@Autowired
	private SolrServer solrServer1;
	
	@Value("${picNeedLabelNum}")
	private int picNeedLabelNum;
	
	@Value("${labelNum}")
	private int labelNum;
	
	@Value("${labelMinNum}")
	private int labelMinNum;
	
	@Value("${finaLabelNum}")
	private int finaLabelNum;
	
	@Value("${twoLabelNeedNum}")
	private int twoLabelNeedNum;
	
	@Value("${tomcatUrl}")
	private String tomcatUrl;
	
	@Value("${MaxUpdateNum}")
	private int MaxUpdateNum;
	
	//保存用户添加的标签
	public Map<String, Object> addPictureLabelByUser(PushPicture picture,UserLabel userLabel){
			
			String[] labels = picture.getLabels();
			String picId = picture.getPicId();
			String userId = picture.getCurrentUser().getId();
			String picUrl = picture.getUrl();
			String picCatId = picture.getPicCatId();
			
			//保存图片分类
			if(picId !=null && picCatId !=null && !"".equals(picCatId)){
				dao.savePicCatTmp(picId, picCatId);
			}
			if(labels!=null && labels.length>0 && picId!=null && !"".equals(picUrl) &&picUrl!=null&&!"".equals(picUrl)){
				
				//维护solr
				PushPicture pushPicture = SolrDDLUtil.queryPicByPicId(picId, solrServer1);
				if(pushPicture==null){
					//同步图片标签solr
					List<String> picCatIds = new ArrayList<>();
					if(picCatId!=null && !"".equals(picCatId)){
						picCatIds.add(picCatId);
					}else{
						picCatIds.add("308985a41e9d4fa78b93b3a80d264d36");
					}
					SolrDDLUtil.insertOrUpdateSolr1(picId, picUrl, labels, picCatIds,solrServer1);
				}else{
					String[] labels1 = labels;
					String [] historyLabels = pushPicture.getLabels();
					int lebelsLen = labels1.length;
					int historyLen = historyLabels.length;
					labels1= Arrays.copyOf(labels1,lebelsLen+historyLen );//扩容
			        System.arraycopy(historyLabels, 0, labels1, lebelsLen,historyLen );//将第二个数组与第一个数组合并
			        //同步图片标签solr
					SolrDDLUtil.insertOrUpdateSolr1(picId, picUrl, LabelAnalyzerUtli.removeDuplicatesLabels(labels1),pushPicture.getPicCatIds(), solrServer1);
			        
				}
				
				//保存用户填写的标签
				for (String label : labels) {
					userLabel.setPicId(picId);
					String labelsResult = LabelAnalyzerUtli.analyzer(label);
					
					//敏感词过滤
					FilterChain fc = new FilterChain();
					fc.addFilter(new HTMLFilter())
					  .addFilter(new SesitiveFilter());
					labelsResult = fc.doFilterByStr(labelsResult);
					
					userLabel.setLabel(label);
					userLabel.setLabelAnalyzer(labelsResult);
					userLabel.preInsert();
					dao.insert(userLabel);
				}
				this.checkFinishTask(picture);
				//如果标签大于五人标记，修改标签状态为不需要填
				//便签人数
				int labelsNum = dao.checkLabelsNum(picId);
				//标签个数
				List<String> picLabelsList = dao.findLabelsByPicIdDis(picId);
				List<String> picAnalyerLabels = new ArrayList<String>();
				
				
				for (String picLabels : picLabelsList) {
					String[] picLabel = picLabels.split(",");
					for (String string : picLabel) {
						picAnalyerLabels.add(string);
					}
				}
				
				if(labelsNum>=picNeedLabelNum && picAnalyerLabels.size()>=labelNum){
					//不需要在标注标签
					dao.updatePicLabelStatusTo1(picId);
					//标签判定业务
					this.checkAndUpdateLabels(picId,picUrl);
				}
				
				
				Map<String,Object> map = Maps.newHashMap();
				map.put("addLabelsStatus", 1);
				return map;
			}
			Map<String,Object> map = Maps.newHashMap();
			map.put("addLabelsStatus", 0);
			return map;
		}
		
		
		//分析图片标签结果集，首次调用插入数据 ，非首次更新数据
		private void checkAndUpdateLabels(String picId,String picUrl){
			PicLabel PicLabel ;
//			int labelNum = dao.checkLabelsNum(picId);
//			if(labelNum<picNeedLabelNum){
//				return;
//			}
			int picLabelstate = dao.picLabelstate(picId);
			//首次标注图片标签
			if(picLabelstate ==0){
				//获取这张图片所有标签
				List<String> labelSet = dao.findLabelsByPicId(picId);
				List<String> picAnalyerLabels = new ArrayList<String>();
				for (String picLabels : labelSet) {
					String[] picLabel = picLabels.split(",");
					for (String string : picLabel) {
						picAnalyerLabels.add(string);
					}
				}
				
				List<String> labels = LabelAnalyzerUtli.checkLabels(picAnalyerLabels,labelMinNum);
				System.out.println(labels);
				//维护solr
//				SolrDDLUtil.insertOrUpdateSolr(picId, picUrl,labels,solrServer);
				
				if(labels.size()>0){
					for (String label : labels) {
						//第一城标签
						PicLabel = new PicLabel();
						PicLabel.setPicId(picId);
						PicLabel.setIsParent(1);
						PicLabel.setStatus(1);
						PicLabel.setLabelParentId("0");
						PicLabel.setLabel(label);
						PicLabel.preInsert();
						dao.insertPicLabel(PicLabel);
					}
					
					//把用户标签设置为不可修改
					this.updateModifyStatusToNot(picId, labels);
					
					//保存成功添加标签的人（picid,labels）
					this.saveSuccLabels(picId, labels,picUrl);
					
					//判断图片标签是否标记完成
					this.checkPicLabelComplete(picId);
				}
			}else if(picLabelstate >0){
				List<String> labelSet = dao.findLabelsByPicId(picId);
				
				List<String> picAnalyerLabels = new ArrayList<String>();
				for (String picLabels : labelSet) {
					String[] picLabel = picLabels.split(",");
					for (String string : picLabel) {
						picAnalyerLabels.add(string);
					}
				}
				List<String> labels = LabelAnalyzerUtli.checkLabels(picAnalyerLabels,labelMinNum);
				List<PicLabel> picLabel = dao.findPicLabelByPicId(picId);
				
//				SolrDDLUtil.insertOrUpdateSolr(picId, picUrl,labels,solrServer);
				
				for(int i = 0;i<labels.size();i++){
					String label = labels.get(i);
					for (PicLabel picLabel2 : picLabel) {
						if(label.equals(picLabel2.getLabel())){
							labels.remove(i);
						}
					}
					
				}
				
				System.out.println(labels);
				
				if(labels.size()>0){
					
					for (String label : labels) {
						PicLabel = new PicLabel();
						PicLabel.setPicId(picId);
						PicLabel.setLabel(label);
						PicLabel.setIsParent(1);
						PicLabel.setStatus(1);
						PicLabel.setLabelParentId("0");
						PicLabel.preInsert();
						dao.insertPicLabel(PicLabel);
						
					}
					
					//把用户标签设置为不可修改
					this.updateModifyStatusToNot(picId, labels);
					
					//保存成功添加标签的人（picid,labels）
					this.saveSuccLabels(picId, labels,picUrl);
					
					//判断图片标注是否完成
					this.checkPicLabelComplete(picId);
					
//					if(labels.size()==5){
//						dao.completePicLabel(picId);
//						//图片标记成功，删除便签solr
//						SolrDDLUtil.deleteSolr(picId, solrServer1);
//					}
				}
			}
		}
		
		
		//用户提交错误标签
		public Map<String,Object> submitErrorTabels(PushPicture picture){
			String[] labels = picture.getLabels();
			Map<String,Object> map;
			UserLabel userLabel;
			for (String label : labels) {
				userLabel = new UserLabel();
				userLabel.setLabel(label);
				userLabel.setId(UserUtils.getUser().getId());
				userLabel.setPicId(picture.getPicId());
				String num = dao.errorTabelNumByUser(userLabel);
				if(num==null){
					dao.insertErrorTabel(userLabel);
					dao.errorUserLabel(userLabel);
					
					//如果标签大于五人标记，修改标签状态为不需要填
					int labelsNum = dao.checkLabelsNum(picture.getPicId());
					List<String> picLabels = dao.findLabelsByPicIdDis(picture.getPicId());
					if(labelsNum<picNeedLabelNum || picLabels.size()<labelNum){
						dao.updatePicLabelStatusTo0(picture.getPicId());
					}
					
					map = Maps.newHashMap();
					map.put("addErrorLabelStatus", 1);
					return map;
				}				
			}
			map = Maps.newHashMap();
			map.put("addErrorLabelStatus", 0);
			return map;
			
		}


		public Map<String, Object> addTwoCandidateLabel(PushPicture picture) {
			Map<String, Object> map;
			String oneLabelId = picture.getOneLabelId();
			String twoCandidateLabel = picture.getTwoCandidateLabel();
			String picId = picture.getPicId();
			PicLabel picLabel;
			String picCatId = picture.getPicCatId();
			String picUrl = picture.getUrl();
			
			//保存图片分类
			if(picId !=null && picCatId !=null && !"".equals(picCatId)){
				dao.savePicCatTmp(picId, picCatId);
			}
			
			
			if(oneLabelId!=null && !"".equals(oneLabelId) && twoCandidateLabel!=null && !"".equals(twoCandidateLabel)){
				
				//判断该二层标签是否大于三人
				picLabel = new PicLabel();
				picLabel.setPicId(picId);
				picLabel.setLabelParentId(oneLabelId);
				picLabel.setLabel(twoCandidateLabel);
				picLabel.setIsParent(0);
				picLabel.setStatus(2);
				picLabel.preInsert();
				dao.insertOneTwoCandiLabel(picLabel);
				
				int labelCount = dao.checkOnoTwoLabelNumByPicIdAndOneId(picId,oneLabelId,twoCandidateLabel);
				
				
				//判断该图片是否存在此父标签
				int result = dao.checkPicParentLabelByPicId(picId,oneLabelId);
				//该父标签存在,确定第二层标签
				if(result>0 && labelCount>=twoLabelNeedNum){
					dao.insertPicLabel(picLabel);
					//维护solr
//					List<String> labels = dao.getSuccLabelsByPicId(picId);
//					PushPicture uploadPicture = SolrDDLUtil.queryPicByPicId(picId, solrServer);
//					String[] labels = uploadPicture.getLabels();
//					String[] labless = Arrays.copyOf(labels,labels.length+1);
//					labless[labels.length] = picLabel.getLabel();
//					uploadPicture.setLabels(labless);
//					SolrDDLUtil.inserOrUpdateSolr(uploadPicture, solrServer);
					
					this.checkFinishTask(picture);
					//把用户选择标签设置为不可修改状态
					this.updateSelectModifyStatusToNot(picId, picLabel.getLabel());
					
					
					
					this.saveSelectSuccLabels(picId, twoCandidateLabel,picUrl);
					
					//判断标签是否标记完成
					this.checkPicLabelComplete(picId);
				}
				
				map = Maps.newHashMap();
				map.put("addErrorLabelStatus", 1);
				return map;
				
				
			}else{
				map = Maps.newHashMap();
				map.put("addErrorLabelStatus", 0);
				return map;
			}
			
			
		}
		
		/*
		 * *判读图片便签是否标注完成
		 * 规则：
		 * 当该图片的第一层标签与第二层标签的总数大于6 
		 */
		public void checkPicLabelComplete(String picId){
			
			List<PicLabel> finaLabelList = dao.findFinaLabelsByPicId(picId);
			if(finaLabelList.size()>=finaLabelNum){
				dao.completePicLabel(picId);
				
				//获取填写分类数最多的分类
				String maxPicCatId = dao.getMaxPicCatIdByPicId(picId);
				if(maxPicCatId!=null &&!"".equals(maxPicCatId)){
					
					//保存图片分类
					dao.savePicCat(picId, maxPicCatId);
					
					//获取图片标签信息
					List<PicLabel> succLabels = dao.getSuccLabelsByPicId(picId);
					//封装labels
					String [] labels = new String [10];
					String label = null;
					int i = 0;
					for (PicLabel picLabel : succLabels) {
//						picLabel.getId()+"-"+picLabel.getLabelParentId()+"-"+
						label = picLabel.getLabel();
						labels[i] = label;
						i++;
					}
					String picUrl = succLabels.get(0).getUrl();
					succLabels.get(0).preUpdate();
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					String finish_time = df.format(succLabels.get(0).getUpdateDate()).toString();
					//保存结果到solr 
					SolrDDLUtil.saveSuccPicture(picId, picUrl,finish_time, maxPicCatId, labels, solrServer);
					
//					PushPicture pushPicture = SolrDDLUtil.queryPicByPicId(picId, solrServer);
//					pushPicture.setPicCatId(maxPicCatId);
//					SolrDDLUtil.inserOrUpdateSolr(pushPicture, solrServer);
				}else{
//					PushPicture pushPicture = SolrDDLUtil.queryPicByPicId(picId, solrServer);
//					pushPicture.setPicCatId("308985a41e9d4fa78b93b3a80d264d36");
//					SolrDDLUtil.inserOrUpdateSolr(pushPicture, solrServer);
					dao.savePicCat(picId, "308985a41e9d4fa78b93b3a80d264d36");
					
					//获取图片标签信息
					List<PicLabel> succLabels = dao.getSuccLabelsByPicId(picId);
					//封装labels
					String [] labels = new String [10];
					String label = null;
					int i = 0;
					for (PicLabel picLabel : succLabels) {
//						picLabel.getId()+"-"+picLabel.getLabelParentId()+"-"+
						label = picLabel.getLabel();
						labels[i] = label;
						i++;
					}
					String picUrl = succLabels.get(0).getUrl();
					succLabels.get(0).preUpdate();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					String finish_time = df.format(succLabels.get(0).getUpdateDate()).toString();
					//保存结果到solr 
					SolrDDLUtil.saveSuccPicture(picId, picUrl,finish_time, "308985a41e9d4fa78b93b3a80d264d36", labels, solrServer);
					
				}
				
				//图片标记成功，删除便签solr
				SolrDDLUtil.deleteSolr(picId, solrServer1);
			}
			
		}
		
		private void saveSelectSuccLabels(String picId,String label,String picUrl){
			//获取TPSB_ONELABEL_TWOCANDILABEL中含有label的用户id，参数（picid,label）
			UserIntegral userIntegral ;
			List<String> selectUserIdList = dao.findSelectSuccNumUserIdByPicIdAndLabel(picId,label);
			if(selectUserIdList!=null && selectUserIdList.size()>0){
				for (String userId : selectUserIdList) {
					//获取用户成功标记记录
					UserSuccessRecord userSuccRecord = dao.getUserSuccessRecord(userId);
					
					
					//保存用户积分
					userIntegral = new UserIntegral();
					userIntegral.setPicId(picId);
					userIntegral.setUserId(userId);
					userIntegral.setPicUrl(picUrl);
					userIntegral.setLabel(label);
					userIntegral.setIntegral(1);
					userIntegral.preInsert();
					if(dao.getUserIntegralNum(userIntegral)==0){
						dao.insertUserIntegral(userIntegral);
					}
					
					
					if(userSuccRecord==null){
						userSuccRecord = new UserSuccessRecord();
						userSuccRecord.setUserId(userId);
						userSuccRecord.setWriteSuccNum(0.0);
						userSuccRecord.setSelectSuccNum(1.0);
						userSuccRecord.setStatus(0);
						dao.insertUserSuccessRecord(userSuccRecord);
					}else if (userSuccRecord!=null){
						userSuccRecord.setSelectSuccNum(userSuccRecord.getSelectSuccNum()+1);
						dao.updateUserSuccessRecord(userSuccRecord);
					}
				}
			}
			
		}
		
		/*
		 * 保存成功添加标签的人
		 */
		private void saveSuccLabels(String picId,List<String> labels,String picUrl){
			UserIntegral userIntegral ;
			for (String label : labels) {
				//获取tpsb_user_label中含有label关键信息的用户id，参数图片id，标签
				List<String> writeUserIdList = dao.findWriteSuccNumUserIdByPicIdAndLabel(picId, label);
				for (String userId : writeUserIdList) {
					
					//保存用户积分
					userIntegral = new UserIntegral();
					userIntegral.setPicId(picId);
					userIntegral.setUserId(userId);
					userIntegral.setPicUrl(picUrl);
					userIntegral.setLabel(label);
					userIntegral.setIntegral(2);
					userIntegral.preInsert();
					
					if(dao.getUserIntegralNum(userIntegral)==0){
						dao.insertUserIntegral(userIntegral);
					}
					
					
					
					
					
					//获取用户成功标记记录
					UserSuccessRecord userSuccRecord = dao.getUserSuccessRecord(userId);
					
					if(userSuccRecord==null){
						userSuccRecord = new UserSuccessRecord();
						userSuccRecord.setUserId(userId);
						userSuccRecord.setWriteSuccNum(1.0);
						userSuccRecord.setSelectSuccNum(0.0);
						userSuccRecord.setStatus(0);
						dao.insertUserSuccessRecord(userSuccRecord);
					}else if (userSuccRecord!=null){
						userSuccRecord.setWriteSuccNum(userSuccRecord.getWriteSuccNum()+1);
						dao.updateUserSuccessRecord(userSuccRecord);
					}
				}
				
				//获取TPSB_ONELABEL_TWOCANDILABEL中含有label的用户id，参数（picid,label）
				List<String> selectUserIdList = dao.findSelectSuccNumUserIdByPicIdAndLabel(picId,label);
				if(selectUserIdList!=null && selectUserIdList.size()>0){
					for (String userId : selectUserIdList) {
						//获取用户成功标记记录
						UserSuccessRecord userSuccRecord = dao.getUserSuccessRecord(userId);
						
						if(userSuccRecord==null){
							userSuccRecord.setUserId(userId);
							userSuccRecord.setWriteSuccNum(0.0);
							userSuccRecord.setSelectSuccNum(1.0);
							userSuccRecord.setStatus(0);
							dao.insertUserSuccessRecord(userSuccRecord);
						}else if (userSuccRecord!=null){
							userSuccRecord.setSelectSuccNum(userSuccRecord.getSelectSuccNum()+1);
							dao.updateUserSuccessRecord(userSuccRecord);
						}
					}
				}
				
			}
			
			
		}
		
		//把用户标签设置为不可修改状态
		public void updateModifyStatusToNot(String picId , List<String> labels){
			dao.updateModifyStatusToNot(picId, labels);
		}
		
		
		
		public void updateSelectModifyStatusToNot(String picId,String label){
			dao.updateSelectModifyStatusToNot(picId, label);
		}
		
		
		
		@Test
		public void testSolr(){
			SolrServer solrServer = new HttpSolrServer("http://10.0.26.2:8082/solr");
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", "1111111");
			doc.addField("picUrl", "http://localhost:8080/pic/image/80e2557b5b1242d4a56f58243b9f3653.jpg");
			String [] labels = new String []{"24小时取款机","取款机","银行"};
			doc.addField("labels",labels);
			doc.addField("picCatId", "308985a41e9d4fa78b93b3a80d264d36");
			try {
				solrServer.add(doc);
				solrServer.commit();
				
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		public List<UserLabel> findHistoryLabelsByUserId(String userId){
			return dao.findHistoryLabelsByUserId(userId);
		}
		
		public List<UserLabel> findHistorySelectLabels(String userId){
			return dao.findHistorySelectLabels(userId);
		}
		
		public int updateWrite(UserLabel userLabel) {
			//获取用户修改标签的次数
			userLabel.preUpdate();
			int updateNum = dao.getUpdateNum(userLabel);
			//判断用户是否可以修改标签
			if(updateNum<MaxUpdateNum){
				//分词
				userLabel.setLabelAnalyzer(LabelAnalyzerUtli.analyzer(userLabel.getTargetLabel()));
				//修改
				dao.updateWrite(userLabel);
				return MaxUpdateNum - updateNum -1;
			}
			return -1;
		}
		
		public void deleteWrite(UserLabel userLabel){
			userLabel.preUpdate();
			dao.deleteWrite(userLabel);
		}


		public void deleteSelect(UserLabel userLabel) {
			userLabel.preUpdate();
			dao.deleteSelect(userLabel);
		}



		public String getUserPermisStatusByUserId(String userId) {
			return dao.getUserPermisStatusByUserId(userId); 
		}
		
		
		//判断是否完成任务
		private void checkFinishTask(PushPicture picture){
			String userId = picture.getCurrentUser().getId();
			String picId = picture.getPicId();
			List<String> userTaskIds = JedisUtils.getkeys(userId+"-taskId-*");
			if(userTaskIds!=null && userTaskIds.size()>0 && userTaskIds.size()==1){
				List<String> picIds = JedisUtils.getList(userTaskIds.get(0));
				boolean exit = picIds.contains(picId);
				TaskAllocat taskAllocat ;
				if(exit){
					//保存成功
					taskAllocat = new TaskAllocat();
					taskAllocat.setId(StringUtils.substringAfterLast(userTaskIds.get(0), "-"));
					taskAllocat.setPicId(picture.getPicId());
					taskAllocat.setUserid(userId);
					taskAllocat.preUpdate();
					int picNum = taskDao.getPicNumByPicIdAndTaskId(taskAllocat);
					if(picNum==0){
						taskDao.finishUpdate(taskAllocat);
						taskDao.insertToPicTask(taskAllocat);
					}
					
				}
				
			}
			
		}
	
	
}
