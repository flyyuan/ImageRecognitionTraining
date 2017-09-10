package com.xxzx.jeesite.modules.tpsb.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.xxzx.jeesite.modules.tpsb.dao.UserAssociateDao;

@Service
@Lazy(false)
public class UserAssociateTask {
	
	@Autowired
	private UserAssociateDao dao;
	
	@Value("${sameLabelNum}")
	private int sameLabelNum;
	
	
//	@Test
//	@Scheduled(cron ="0 */5 * * * ?")
	@Scheduled(cron ="0 0 5 * * ?")
	public void userAssociate(){
		//获取今天所有更新过标签 的userID
		List<String> nowUserIdList = dao.getNowUserId();
		if(nowUserIdList!=null && nowUserIdList.size()>0){
			//获取所有人的id
			List<String> allUserId = dao.getAllUserId();
			//遍历所有人id
			for (String userid : allUserId) {
				//遍历更新过标签的userId
				for(String nowUserId : nowUserIdList){
					//获取更新过userid 的所有标签
					List<String> nowLabels = dao.getAllLabelByUserId(nowUserId);
					//获取所有人所有的标签
					List<String> allLabels = dao.getAllLabelByUserId(userid);
					
					System.out.println("now-----------"+nowLabels);
					System.out.println("all-----------"+allLabels);
					Collection exists=new ArrayList<String>(nowLabels);
					Collection notexists=new ArrayList<String>(nowLabels);
					exists.removeAll(allLabels);
					notexists.removeAll(exists);
					//如果两个所有标签相同个数大于2
					if(notexists.size()>=sameLabelNum && !nowUserId.equals(userid)){
						//保存到关联表
						System.out.println(notexists.size());
						dao.insert(nowUserId, userid);
					}
				}
				
			}
			
			
		}
		
	}
	
}
