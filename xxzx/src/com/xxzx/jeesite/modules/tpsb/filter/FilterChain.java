package com.xxzx.jeesite.modules.tpsb.filter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterChain implements Filter {
	List<Filter> filters = new ArrayList<Filter>();
	
	public FilterChain addFilter(Filter f) {
		this.filters.add(f);
		return this;
	}
	
	public List<String> doFilter(List<String> strs) {
		List<String> resultList = new ArrayList<String>();
		for (String r : strs) {
			String a = null;
			a=r;
			for(Filter f: filters) {
				a = f.doFilter(a);
				if(a==null){
					break;
				}
			}
			if(a!=null){
				resultList.add(a);
			}
		}
		return resultList;
	}

	public String doFilterByStr(String str) {
		String[] labels = str.split(",");
		List<String> labelList = Arrays.asList(labels);
		List<String> resultList = this.doFilter(labelList);
		String result = "";
		for (String label : resultList) {
			result = result +label+",";
		}
		return result.substring(0, result.length()-1);
	}

	@Override
	public String doFilter(String str) {
		// TODO Auto-generated method stub
		return null;
	}
}
