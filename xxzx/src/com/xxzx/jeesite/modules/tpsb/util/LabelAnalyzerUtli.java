package com.xxzx.jeesite.modules.tpsb.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.wltea.analyzer.lucene.IKAnalyzer;




public class LabelAnalyzerUtli {

	
	
	//label 用户传入标签    返回数据格式    词元 ，词元 ，词元，词元，
	public static String analyzer(String label){  
		try {
			
			IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
			StringReader reader = new StringReader(label);  
			TokenStream ts;
			ts = ikAnalyzer.tokenStream("", reader);
			ts.reset();
			String analyzerResult ="";
			CharTermAttribute attribute = ts.getAttribute(CharTermAttribute.class);
			while(ts.incrementToken()){
				String i = attribute.toString();
				analyzerResult = analyzerResult+ i +",";
			}
			reader.close();
			return analyzerResult.substring(0, analyzerResult.length()-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//去掉重复值
		public static List<String> removeDuplicatesLabels(List<String> labelSets){
			
			List<String> labelList = new ArrayList<String>();
			
			for (String labelSet : labelSets) {
				String[] labels = labelSet.split(",");
				for (String label : labels) {
					labelList.add(label);
				}
				
			}
			if(labelList.size()>0){
				String findLabel ;
				Map<String, Integer> labelMap = new TreeMap<String, Integer>();
				Iterator<String> iterator = labelList.iterator();
				while(iterator.hasNext()){
					findLabel=iterator.next();
					
					int i = 1;
					while(iterator.hasNext()){
						String findLabel1 = iterator.next();
						if(findLabel1.equals(findLabel)){
							i++;
						}
					}
					iterator = labelList.iterator();
					iterator.next(); 
					 iterator.remove();
					 while(iterator.hasNext()){
						 String findLabel1 = iterator.next();
						 if(findLabel1.equals(findLabel)){
							 iterator.remove();
						 }
					 }
					 iterator = labelList.iterator();
					 labelMap.put(findLabel,i );
				}
				//这里将map.entrySet()转换成list
		        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(labelMap.entrySet());
		        //然后通过比较器来实现排序
		        Collections.sort(list,new Comparator<Map.Entry<String, Integer>>() {
		            //降序排序
					@Override
					public int compare(Entry<String, Integer> o1,
							Entry<String, Integer> o2) {
						return o2.getValue().compareTo(o1.getValue());
					}
		        });
		        labelList.clear();
		        //提取标签数量最大的五个
		        if(list.size()>5){
		        	for (int i = 0; i < 5; i++) {
		        		Map.Entry<String, Integer> string = list.get(i);
		        		labelList.add(string.getKey());
		        	}
		        }else{
		        	for (int i = 0; i < list.size(); i++) {
		        		Map.Entry<String, Integer> string = list.get(i);
		        		labelList.add(string.getKey());
		        	}
		        }
			}
		
			return labelList;
		}
	
		//去掉重复值
		public static String [] removeDuplicatesLabels(String [] labelSets){
			
			List<String> labelList = new ArrayList<String>();
			
			for (String labelSet : labelSets) {
					labelList.add(labelSet);
			}
			if(labelList.size()>0){
				String findLabel ;
				Map<String, Integer> labelMap = new TreeMap<String, Integer>();
				Iterator<String> iterator = labelList.iterator();
				while(iterator.hasNext()){
					findLabel=iterator.next();
					
					int i = 1;
					while(iterator.hasNext()){
						String findLabel1 = iterator.next();
						if(findLabel1.equals(findLabel)){
							i++;
						}
					}
					iterator = labelList.iterator();
					iterator.next(); 
					 iterator.remove();
					 while(iterator.hasNext()){
						 String findLabel1 = iterator.next();
						 if(findLabel1.equals(findLabel)){
							 iterator.remove();
						 }
					 }
					 iterator = labelList.iterator();
					 labelMap.put(findLabel,i );
				}
				//这里将map.entrySet()转换成list
		        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(labelMap.entrySet());
		        //然后通过比较器来实现排序
		        Collections.sort(list,new Comparator<Map.Entry<String, Integer>>() {
		            //降序排序
					@Override
					public int compare(Entry<String, Integer> o1,
							Entry<String, Integer> o2) {
						return o2.getValue().compareTo(o1.getValue());
					}
		        });
		        labelList.clear();
		        //提取标签数量最大的五个
		        
		        	for (int i = 0; i < list.size(); i++) {
		        		Map.Entry<String, Integer> string = list.get(i);
		        		labelList.add(string.getKey());
		        	}
		        
			}
			String [] labels = new String[labelList.size()];
			labelList.toArray(labels);
			return labels;
		}
	
	
	
	
	
	//分析标签分词，返回出现概率最多的五个词元
	public static List<String> checkLabelsWord(List<String> labelSets){
		
		List<String> labelList = new ArrayList<String>();
		
		for (String labelSet : labelSets) {
			String[] labels = labelSet.split(",");
			for (String label : labels) {
				labelList.add(label);
			}
			
		}
		if(labelList.size()>0){
			String findLabel ;
			Map<String, Integer> labelMap = new TreeMap<String, Integer>();
			Iterator<String> iterator = labelList.iterator();
			while(iterator.hasNext()){
				findLabel=iterator.next();
				
				int i = 1;
				while(iterator.hasNext()){
					String findLabel1 = iterator.next();
					if(findLabel1.equals(findLabel)){
						i++;
					}
				}
				iterator = labelList.iterator();
				iterator.next(); 
				 iterator.remove();
				 while(iterator.hasNext()){
					 String findLabel1 = iterator.next();
					 if(findLabel1.equals(findLabel)){
						 iterator.remove();
					 }
				 }
				 iterator = labelList.iterator();
				 labelMap.put(findLabel,i );
			}
			//这里将map.entrySet()转换成list
	        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(labelMap.entrySet());
	        //然后通过比较器来实现排序
	        Collections.sort(list,new Comparator<Map.Entry<String, Integer>>() {
	            //降序排序
				@Override
				public int compare(Entry<String, Integer> o1,
						Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
	        });
	        labelList.clear();
	        //提取标签数量最大的五个
	        if(list.size()>5){
	        	for (int i = 0; i < 5; i++) {
	        		Map.Entry<String, Integer> string = list.get(i);
	        		labelList.add(string.getKey());
	        	}
	        }else{
	        	for (int i = 0; i < list.size(); i++) {
	        		Map.Entry<String, Integer> string = list.get(i);
	        		labelList.add(string.getKey());
	        	}
	        }
		}
	
		return labelList;
	}
	
	
		//分析标签，返回出现概率最多的五个标签
		public static List<String> checkLabels(List<String> labelList,int labelMinNum){
			if(labelList.size()>0){
				String findLabel ;
				Map<String, Integer> labelMap = new TreeMap<String, Integer>();
				Iterator<String> iterator = labelList.iterator();
				while(iterator.hasNext()){
					findLabel=iterator.next();
					int i = 1;
					while(iterator.hasNext()){
						String findLabel1 = iterator.next();
						if(findLabel1.equals(findLabel)){
							i++;
						}
					}
					iterator = labelList.iterator();
					iterator.next(); 
					 iterator.remove();
					 while(iterator.hasNext()){
						 String findLabel1 = iterator.next();
						 if(findLabel1.equals(findLabel)){
							 iterator.remove();
						 }
					 }
					 iterator = labelList.iterator();
					 labelMap.put(findLabel,i );
				}
				//这里将map.entrySet()转换成list
		        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(labelMap.entrySet());
		        //然后通过比较器来实现排序
		        Collections.sort(list,new Comparator<Map.Entry<String, Integer>>() {
		            //降序排序
					@Override
					public int compare(Entry<String, Integer> o1,
							Entry<String, Integer> o2) {
						return o2.getValue().compareTo(o1.getValue());
					}
		        });
		        labelList.clear();
		        //提取标签数量最大的五个
		        if(list.size()>5){
		        	for (int i = 0; i < 5; i++) {
		        		Map.Entry<String, Integer> string = list.get(i);
		        		
		        		if(string.getValue().intValue()>=labelMinNum){
		        			labelList.add(string.getKey());
		        		}
//		        	string.getValue()+":"+
		        		System.out.println(string.getKey()+"--------------"+string.getValue());
		        	}
		        }else{
		        	for (int i = 0; i < list.size(); i++) {
		        		Map.Entry<String, Integer> string = list.get(i);
		        		if(string.getValue().intValue()>=labelMinNum){
		        			labelList.add(string.getKey());
		        		}
//		        	string.getValue()+":"+
		        		System.out.println(string.getKey()+"--------------"+string.getValue());
		        	}
		        }
			}
			return labelList;
		}
	
	
	
	
	
	
}
