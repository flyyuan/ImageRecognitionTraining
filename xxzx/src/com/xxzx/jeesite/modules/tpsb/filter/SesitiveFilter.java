package com.xxzx.jeesite.modules.tpsb.filter;

public class SesitiveFilter implements Filter {

	@Override
	public String doFilter(String str) {
		//process the sensitive words
//		String r = str.replace("����ҵ", "��ҵ")
//			 .replace("����", "");
		 boolean result = str.contains("傻逼");
		 if(result){
			 return null;
		 }
		 return str;
	}

}
