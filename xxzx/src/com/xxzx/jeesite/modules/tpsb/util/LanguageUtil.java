package com.xxzx.jeesite.modules.tpsb.util;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class LanguageUtil {
	
	
	/**
	 * 判断该字符串是否为中文
	 * @param string
	 * @return
	 */
	public static boolean isChinese(String string){
	    int n = 0;
	    for(int i = 0; i < string.length(); i++) {
	        n = (int)string.charAt(i);
	        if(!(19968 <= n && n <40869)) {
	            return false;
	        }
	    }
	    return true;
	}
	/**
	 * 判断该字符串是否为英文
	 * @param string
	 * @return
	 */
	public static boolean isEnglish(String string){
		int n = 0;
	    for(int i = 0; i < string.length(); i++) {
	        n = (int)string.charAt(i);
	        if(97 <= n && n <122 || 64 <= n && n <90 ) {
	            return true;
	        }
	    }
		return false;
	}
	
	/**
	 * 判断该字符串是否为日文
	 * @param string
	 * @return
	 */
	public static boolean isJapanese(String string){
		int n = 0;
	    for(int i = 0; i < string.length(); i++) {
	        n = (int)string.charAt(i);
	        if(12352 <= n && n <12447 || 12448 <= n && n <12543 || 12784 <= n && n <12799 ) {
	            return true;
	        }
	    }
		return false;
	}
	
	/**
	 * 判断该字符串是否为韩文
	 * @param string
	 * @return
	 */
	public static boolean isKorean(String string){
		int n = 0;
	    for(int i = 0; i < string.length(); i++) {
	        n = (int)string.charAt(i);
	        if(44032 <= n && n <55295) {
	            return true;
	        }
	    }
		return false;
	}
	
	
	
	
	@Test
	public void test(){
		boolean result = isKorean("한글");
		if(result){
			System.out.println("是英文");
		}else{
			System.out.println("不是英文");
		}
	}
	
	
}
