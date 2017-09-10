package com.xxzx.jeesite.modules.tpsb.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.xxzx.jeesite.common.utils.JedisUtils;
import com.xxzx.jeesite.modules.tpsb.dao.PushPictureDao;
import com.xxzx.jeesite.modules.tpsb.entity.TaskAllocat;
import com.xxzx.jeesite.modules.tpsb.service.TaskAllocatService;
import com.xxzx.jeesite.modules.tpsb.util.SolrDDLUtil;

@Service
@Lazy(false)
public class AllocatTask {
	@Autowired
	private TaskAllocatService taskService;
	
	@Value("${taskNum}")
	private String taskNum;
	
	@Autowired
	private SolrServer solrServer1;
	
	@Autowired
	private PushPictureDao dao;
	
	@Value("${taskNumCacheSeconds}")
	private String taskNumCacheSeconds;
	
	//初始化当天任务
	@Scheduled(cron ="0 0 1 * * ?")
//	@Scheduled(cron ="0 */5 * * * ?")
	public void initUserTask(){
		//获取所有用户的id
		List<String> userIds = taskService.findAllUserId();
		TaskAllocat taskAllocat ;
		String taskId = null;
		//遍历所有用户
		for (String userId : userIds) {
			
			//设置任务
			taskAllocat = new TaskAllocat();
			//设置用户任务数量
			taskAllocat.setUserid(userId);
			taskAllocat.setTaskSum(taskNum);
			taskAllocat.preInsert();
			taskService.insert(taskAllocat);
			//获取到任务id
			taskId = taskAllocat.getId();
			//根据用户id获取用户历史填过得标签
			
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
			
			List<String> picIdList = new ArrayList<String>();
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
			//根据用户的标签搜索solr  随机获取图片id 
			HashSet h = new HashSet(picIdList);
			picIdList.clear();
			picIdList.addAll(h);
			
			//3..solr中没有用户历史填过标签相识的图片，随机获取10张
			if(picIdList==null || picIdList.size()==0){
				//随机获取10zhang
				picIdList = taskService.getPicByNum(taskNum);
			}else if(picIdList.size()>0 && picIdList.size()<Integer.valueOf(taskNum)){
				//2.图片id没有满足任务数（差几张）
				int picNum = Integer.valueOf(taskNum)-picIdList.size();
				picIdList = taskService.getPicByNum(String.valueOf(picNum));
			}else if(picIdList.size()>Integer.valueOf(taskNum)){
				//1.图片id满足最大任务数
//				picIdList.remove(picIdList.size()-Integer.valueOf(taskNum));
				Random random = new Random();
				int z = picIdList.size()-Integer.valueOf(taskNum);
				for(int i = 0;i<z;i++){
					int j = random.nextInt(picIdList.size());
					picIdList.remove(j);
				}
			}
			System.out.println(picIdList);
			//保存图片于任务-图片表中
			//把获取 到的图片id存在redis中，用户首先根据redis中的图片进行获取
			JedisUtils.setList(userId+"-taskId-"+taskAllocat.getId(), picIdList, Integer.valueOf(taskNumCacheSeconds));
			
		}
		//初始化完成
	}
	
//	@Scheduled(cron="0 */1 * * * ?")
	public void testkey(){
		List<String> keys = JedisUtils.getkeys("*-taskId-*");
		System.out.println(keys);
	}
	
	
	
}
