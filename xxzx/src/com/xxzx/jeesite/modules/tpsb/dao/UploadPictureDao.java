package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
@MyBatisDao
public interface UploadPictureDao  extends CrudDao<UploadPicture>{

	List<UploadPicture> findNoRecogPic();
	
	void RecogPicSucc(@Param("id")String id);

	void insertAllPic(@Param("list")List<UploadPicture> list);
	
	
}
