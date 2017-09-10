package com.xxzx.jeesite.modules.tpsb.util.youtu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.xxzx.jeesite.common.mapper.JsonMapper;
import com.xxzx.jeesite.modules.tpsb.entity.PictureYouTu;


public class Demo {

	// appid, secretid secretkey请到http://open.youtu.qq.com/获取
	// 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
	public static final String APP_ID = "10084457";
	public static final String SECRET_ID = "AKIDZ5VknfEo95uWwBvBuIaghXBdGoCVsXGw";
	public static final String SECRET_KEY = "vkRwFMH0W1QddWDqMgokU2OafaMhw0ud";
	public static final String USER_ID = "295524115";

	public static void main(String[] args) {

		try {
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
			JSONObject respose;
			respose = faceYoutu.ImageTagUrl("http://39.108.69.214:8080/pic/image/ee3807ce864944da8dd61abdec212dea.jpg");
			
			PictureYouTu picYouTu = (PictureYouTu)JsonMapper.fromJsonString(respose.toString(), PictureYouTu.class);
			
			List<Map<String, Object>> tagsList = picYouTu.getTags();
			
			System.out.println("-------正在识别-------");
			
			for (Map<String, Object> map : tagsList) {
				
				String tagname = "";
				int tagconfidence=0;
				for (Map.Entry<String, Object> map1 : map.entrySet()) {
					if("tag_name".equals(map1.getKey())){
						tagname = map1.getValue().toString();
					}
					
					if("tag_confidence".equals(map1.getKey())){
						tagconfidence =  (int) map1.getValue();
					}
				}
				System.out.println("已识别出图片标签-->  " +tagname + "  图片标签置信度 -->  " + tagconfidence );
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
