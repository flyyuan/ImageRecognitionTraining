package com.xxzx.jeesite.modules.tpsb.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.xxzx.jeesite.common.utils.CacheUtils;
import com.xxzx.jeesite.modules.sys.utils.UserUtils;

@Service
@Lazy(false)
public class RemUserCacheTask {
	//去除用户缓存
	@Scheduled(cron = "0 0 23 * * ?")
	public void RemUserCache(){
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> allUserId = (ArrayList<String>)CacheUtils.get(nowDate);
		if(allUserId!=null && allUserId.size()>0){
			 for (String userId : allUserId) {
				UserUtils.removeCache(userId+"-"+nowDate);
			}
			allUserId.removeAll(allUserId);
		}
	}
	
}
