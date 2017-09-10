package com.xxzx.jeesite.modules.tpsb.filter;


public class FaceFilter implements Filter {

	@Override
	public String doFilter(String str) {
		return str.replace(":)", "^V^");
	}

}
