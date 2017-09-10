package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.PicLabel;
import com.xxzx.jeesite.modules.tpsb.entity.PushPicture;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;
import com.xxzx.jeesite.modules.tpsb.entity.UploadPicture;
@MyBatisDao
public interface PushPictureDao extends CrudDao<PushPicture>{
	

	List<UploadPicture> getPictureByNum(PushPicture picture);
	
	List<String> findLabelsByPicId(@Param("picId") String picId);
	
	
	//获取用户历史填过的标签
	List<String> findLabelsByUserId(@Param("userId") String userId);
	
	
	//根据图片id获取图片信息
	List<UploadPicture> findPictureByPicIdList(@Param("picIdList") List<String> picIdList);

	
	List<UploadPicture> getPictureByNumAndNotPicId(@Param("picNumber")int picNumber,
			@Param("picIdList") List<String> picIdList);
	
	List<String> findPicNum();
	
	List<String> findSelectPic();
	
	//判断图片id在数据库是否存在
	int checkPicId(@Param("picId") String picId);
	
	//获取第一层标签
	List<PicLabel> findOneLabelsByPicId(@Param("picId") String picId);
	
	//获取第一层候选标签
	List<String> findTwoCandidateLabelsByPidId(@Param("picId") String picId);
	
	//获取用户权限状态
	String getUserPermisStatusByUserId (@Param("userId")String userId);

	
	//获取填写完成的图片
	List<UploadPicture> getSelectPicId();
	//获取需要填写的图片
	List<UploadPicture> getWritePicId();
	
	//随机获取五个标签分类
	List<TpsbCat> getPicCatByNum(@Param("picCatNum")int picCatNum);
	
	//根据图片id获取分类 内容
	List<TpsbCat> getPicCatByCatIds(@Param("picCatIds")List<String> picCatIds);

	List<String> findAssoLabelsByUserId(@Param("userId")String userId);
	
	
	List<String> findUserCatsByUserId(@Param("userId")String userId);
	
	List<String> getPicByNum(@Param("picNum")int picNum);
	
	
	
}
