package com.xxzx.jeesite.modules.tpsb.util;

import org.json.JSONObject;

import com.xxzx.jeesite.common.mapper.JsonMapper;
import com.xxzx.jeesite.modules.tpsb.entity.PictureYouTu;
import com.xxzx.jeesite.modules.tpsb.util.youtu.Youtu;

public class PictureYouTuUtil {
	public static final String APP_ID = "10084457";
	public static final String SECRET_ID = "AKIDZ5VknfEo95uWwBvBuIaghXBdGoCVsXGw";
	public static final String SECRET_KEY = "vkRwFMH0W1QddWDqMgokU2OafaMhw0ud";
	public static final String USER_ID = "295524115";
	
	
	public static PictureYouTu getPicYouTuByUrl(String url){
		
		try {
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
			JSONObject respose;
			respose = faceYoutu.ImageTagUrl(url);
			PictureYouTu picYouTu = (PictureYouTu)JsonMapper.fromJsonString(respose.toString(), PictureYouTu.class);
			if(picYouTu.getErrormsg()!=null && "OK".equals(picYouTu.getErrormsg())){
				return picYouTu;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	} 
	
public static PictureYouTu getPicYouTuByPath(String path){
		
		try {
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
			JSONObject respose;
			respose = faceYoutu.ImageTag(path);
			PictureYouTu picYouTu = (PictureYouTu)JsonMapper.fromJsonString(respose.toString(), PictureYouTu.class);
			if(picYouTu.getErrormsg()!=null && "OK".equals(picYouTu.getErrormsg())){
				return picYouTu;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	} 
	
	
	
}	
