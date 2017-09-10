package com.xxzx.jeesite.modules.tpsb.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxzx.jeesite.common.persistence.Page;
import com.xxzx.jeesite.common.service.CrudService;
import com.xxzx.jeesite.common.utils.DateUtils;
import com.xxzx.jeesite.modules.tpsb.dao.LabelLogDao;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
@Service
public class LabelLogService extends
			CrudService<LabelLogDao,UserLabel> {
	
	@Autowired
	private LabelLogDao dao;

	@Override
	public Page<UserLabel> findPage(Page<UserLabel> page, UserLabel entity) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(entity.getBeginDateString()!=null){
			try {
				Date beginDate = sdf.parse(entity.getBeginDateString());
				entity.setBeginDate(beginDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(entity.getEndDateString()!=null){
			try {
				Date endDate = sdf.parse(entity.getEndDateString());
				entity.setEndDate(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// 设置默认时间范围，默认当前月
		if (entity.getBeginDate() == null){
			entity.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (entity.getEndDate() == null){
			entity.setEndDate(DateUtils.addMonths(entity.getBeginDate(), 1));
		}
		return super.findPage(page, entity);
	}
	
	
	
	
}
