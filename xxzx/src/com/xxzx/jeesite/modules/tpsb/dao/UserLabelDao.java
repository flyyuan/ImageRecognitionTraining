package com.xxzx.jeesite.modules.tpsb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxzx.jeesite.common.persistence.CrudDao;
import com.xxzx.jeesite.common.persistence.annotation.MyBatisDao;
import com.xxzx.jeesite.modules.tpsb.entity.PicLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserIntegral;
import com.xxzx.jeesite.modules.tpsb.entity.UserLabel;
import com.xxzx.jeesite.modules.tpsb.entity.UserSuccessRecord;
@MyBatisDao
public interface UserLabelDao extends CrudDao<UserLabel>{
	
	
	
	/*
	 * 获取图标被标注人数
	 */
	int checkLabelsNum(@Param("picId") String picId);
	/*
	 * 获取图片标注分词集
	 */
	List<String> findLabelsWordByPicId(@Param("picId") String picId);
	
	/*
	 * 获取图片标注集
	 */
	List<String> findLabelsByPicId(@Param("picId") String picId);
	
	
	/*
	 * 获取pic是否被标注
	 */
	int picLabelstate(@Param("picId") String picId);
	
	//添加图片标签
	void insertPicLabel(PicLabel picLabel);
	//删除图片标签
	List<PicLabel> findPicLabelByPicId(@Param("picId")String picId);
	//设置图片为 不需要填写标签状态
	void updatePicLabelStatusTo1(@Param("picId") String picId);
	
	//设置图片为  需要填写标签状态
		void updatePicLabelStatusTo0(@Param("picId") String picId);
	
	/*
	 * 获取图片标注集,去除重复值
	 */
	List<String> findLabelsByPicIdDis(@Param("picId") String picId);
	
	//往标签错误中间表插入数据
	void insertErrorTabel(UserLabel userLabel);
	
	//往标签错误中间表插入数据 ，更新userlabel表，设置labelerrornum+1
	void errorUserLabel(UserLabel userLabel);
	
	//是否以前标记过图片错误
	String errorTabelNumByUser(UserLabel userLabel);
	
	void completePicLabel(@Param("picId") String picId);
	
	//判断父标签id是否存在(>0表示存在)
	int checkPicParentLabelByPicId(@Param("picId")String picId, @Param("oneLabelId")String oneLabelId);
	
	
	//获取 图片所有已经判定的标签
	List<PicLabel> findFinaLabelsByPicId(@Param("picId") String picId);
	
	//保存第二层候选标签于第一层第二层关系表 
	void insertOneTwoCandiLabel(PicLabel picLabel);
	
	//获取 关系表中第二层标签个数
	int checkOnoTwoLabelNumByPicIdAndOneId(@Param("picId")String picId, 
			@Param("oneLabelId")String oneLabelId,@Param("twoCandidateLabel") String twoCandidateLabel);
	
	
	//获取tpsb_user_label中含有label关键信息的用户id，参数图片id，标签
	List<String> findWriteSuccNumUserIdByPicIdAndLabel(@Param("picId") String picId,@Param("label")String label);
	
	//获取TPSB_ONELABEL_TWOCANDILABEL中含有label的用户id，参数（picid,label）
	List<String> findSelectSuccNumUserIdByPicIdAndLabel(@Param("picId")String picId,
			@Param("label")String label);
	
	//获取用户成功标记记录
	UserSuccessRecord getUserSuccessRecord(@Param("userId")String userId);
	
	//插入用户成功标记记录
	void insertUserSuccessRecord(UserSuccessRecord successRecord);
	
	
	//更新用户成功标记记录
	void updateUserSuccessRecord(UserSuccessRecord successRecord);
	
	
	//保存零时图片分类
	void savePicCatTmp(@Param("picId") String picId,@Param("picCatId") String picCatId);
	
	//保存图片分类
		void savePicCat(@Param("picId") String picId,@Param("picCatId") String picCatId);
	
	//获取填写分类数最多的分类
	String getMaxPicCatIdByPicId(@Param("picId") String picId);
	
	
	//获取成功正确的标签
	List<PicLabel> getSuccLabelsByPicId(@Param("picId")String picId);
	
	//保存用户积分
	void insertUserIntegral(UserIntegral userIntegral);
	
	//检查用户级ifen
	int getUserIntegralNum(UserIntegral userIntegral);
	
	//获取用户历史填过的标签
	List<UserLabel> findHistoryLabelsByUserId(@Param("userId")String userId);
	
	
	//获取用户历史填过的标签
	List<UserLabel> findHistorySelectLabels(@Param("userId")String userId);
	
	//获取用户修改标签的次数
	int getUpdateNum(UserLabel userLabel);
	
	//修改用户标签
	int updateWrite(UserLabel userLabel);
	
	//删除用户填写标签
	void deleteWrite(UserLabel userLabel);
	
	//删除用户选择标签
	void deleteSelect(UserLabel userLabel);
	
	////把用户标签设置为不可修改状态
	void updateModifyStatusToNot(@Param("picId")String picId,@Param("labels")List<String> labels);
	//把用户选择标签设置为不可修改状态
	void updateSelectModifyStatusToNot(@Param("picId")String picId,@Param("label")String label);
	
	
	String getUserPermisStatusByUserId(@Param("userId")String userId);
	
}
