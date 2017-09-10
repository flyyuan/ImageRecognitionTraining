package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
//用户关联类
public interface UserAssociateDao {
		List<String> getNowUserId();
		List<String> getAllUserId();
		List<String> getAllLabelByUserId(@Param("userId") String userId);
		void delete(@Param("userIdA") String userIdA);
		void insert(@Param("userIdA") String userIdA,@Param("userIdB") String userIdB);
}
