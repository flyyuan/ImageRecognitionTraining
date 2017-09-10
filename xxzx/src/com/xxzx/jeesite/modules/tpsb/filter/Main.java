package com.xxzx.jeesite.modules.tpsb.filter;

import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String msg = "��Һ�:)��<script>�����У�����ҵ�������ڿ�û�о�����Ϊ�������һ��";
//		MsgProcessor mp = new MsgProcessor();
//		mp.setMsg(msg);
		List<String> testList = new ArrayList<String>();
		testList.add("傻逼啊你");
		testList.add("<script>");
		System.out.println(testList);
		FilterChain fc = new FilterChain();
		fc.addFilter(new HTMLFilter())
		  .addFilter(new SesitiveFilter());
		String resultList = fc.doFilterByStr("傻逼啊你,<script>,hahhah");
		System.out.println("--------"+resultList);
//		FilterChain fc2 = new FilterChain();
//		fc2.addFilter(new FaceFilter());
//		
//		fc.addFilter(fc2);
//		mp.setFc(fc);
//		String result = mp.process();
//		System.out.println(result);
	}

}
