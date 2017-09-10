package com.xxzx.jeesite.modules.tpsb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.xxzx.jeesite.modules.tpsb.entity.TpsbCat;
import com.xxzx.jeesite.modules.tpsb.service.TpsbCatService;

//爬虫工具类
public class CrawlerUtil {
	
	
	@Autowired
	private TpsbCatService service;
	
	
	//第一种 方式  通过百度百科确定图片分类
	public static List<String> getClassByBaiKe(List<String> labels, List<String> classs) {
		
		// 根据标签集合获取文本内容
		List<String> labelTexts = getTextByLabels(labels); 
		
		Map<String, Integer> classMap = new TreeMap<String, Integer>();
		List<String> classList = new ArrayList<String>();
		if (labelTexts != null && labelTexts.size() > 0) {
			for (String c : classs) {
				int sum = 0;    
				for (String labelText : labelTexts) {
					int count = hit(labelText, c);
					sum = sum + count;
				}
				System.out.println(c+"分类出现的次数为"+sum);
				if(sum>0){
					classMap.put(c, sum);
				}
			}
			//这里将map.entrySet()转换成list
	        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(classMap.entrySet());
	        //然后通过比较器来实现排序
	        Collections.sort(list,new Comparator<Map.Entry<String, Integer>>() {
	            //降序排序
				@Override
				public int compare(Entry<String, Integer> o1,
						Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
	        });
	      //提取标签数量最大的五个
	        if(list.size()>5){
	        	for (int i = 0; i < 5; i++) {
	        		Map.Entry<String, Integer> string = list.get(i);
	        		classList.add(string.getKey());
	        	}
	        }else{
	        	for (int i = 0; i < list.size(); i++) {
	        		Map.Entry<String, Integer> string = list.get(i);
	        		classList.add(string.getKey());
	        	}
	        }
		}
		return classList;
	}
	
	
	//第二种方式   百度搜索结果确定图片分类
	public static List<String> getClassByBaidu(List<String> labels, List<String> classs,int count){
		Map<String,Long> map = Maps.newHashMap();
		List<String> classList = new ArrayList<String>();
		for (String c : classs) {
			//以关键字  ‘标签、分类’  搜索百度获取搜索结果数量
			long sum = getSumByClassAndLabels(c, labels);
			map.put(c, sum);
			System.out.println("标签集合与  "+c+"  分类搜索结果数量为  " + sum  );
		}
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String, Long>>() {
            //降序排序
			@Override
			public int compare(Entry<String, Long> o1,
					Entry<String, Long> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
        });
      //提取标签数量最大的五个
        if(list.size()>count){
        	for (int i = 0; i < count; i++) {
        		Map.Entry<String, Long> string = list.get(i);
        		classList.add(string.getKey());
        	}
        }else{
        	for (int i = 0; i < list.size(); i++) {
        		Map.Entry<String, Long> string = list.get(i);
        		classList.add(string.getKey());
        	}
        }
		return classList;
	}
	
	//以关键字  ‘标签、分类’  搜索百度获取搜索结果数量
	public static long getSumByClassAndLabels(String classs,List<String> labels){
		long sum = 0;
		long count = 0;
		for (String label : labels) {
			count = getCountByClassAndLabel(classs, label);
			sum = sum +count;
		}
		return sum;
	}
	
	//获取结果数量
	public static long getCountByClassAndLabel(String classs,String label){
		Document doc;
		long result = 0;
		try {
			doc = Jsoup
					.connect(
							"http://www.baidu.com/s?q1="+classs+"+"+label+"&q2=&q3=&q4=&gpc=stf&ft=&q5=&q6=&tn=58025142_oem_dg")
					.get();
			Element nums = doc.select("div.nums").first();
			String text = nums.text().toString();
			//获取数字
			String regEx="[^0-9]";   
			Pattern p = Pattern.compile(regEx);   
			Matcher m = p.matcher(text);   
			result =Long.valueOf(m.replaceAll("").trim())/100000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Test
	public void testBaidu(){
		//标签
		List<String> labels = new ArrayList<String>();
		labels.add("房屋");
		
		//分类
		List<String> classs = new ArrayList<String>();
		classs.add("商务职场");
		classs.add("家居");
		classs.add("生活用品");
		classs.add("建筑");
		classs.add("人物");
		classs.add("生活方式");
		classs.add("文化艺术");
		classs.add("自然风光");
		classs.add("城市风光");
		classs.add("旅游地理");
		classs.add("科技");
		classs.add("交通运输");
		
		getClassByBaidu(labels, classs, 5);
		
	}
	
	
	
	

	// 根据标签集合获取文本内容
	private static List<String> getTextByLabels(List<String> labels) {
		List<String> results = new ArrayList<String>();
		for (String label : labels) {
			//根据标签获取文本内容
			String textByLabel = getTextByLabel(label);
			results.add(textByLabel);
		}
		return results;
	}

	// //根据标签获取文本内容
	private static String getTextByLabel(String label) {
		Document doc;
		String result = "";
		String a = "http://baike.baidu.com/search/word?word=";
		try {
			label = URLEncoder.encode(label, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		String url = a+label;
		try {
			doc = Jsoup.connect(url).get();
			Elements listClass = doc.getElementsByAttributeValue("class",
					"para");
			for (Element element : listClass) {
				result = result + element.getElementsByTag("div").text();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	@Test
	public void testByBaiKe(){
		
		//标签
		List<String> labels = new ArrayList<String>();
		
		labels.add("房屋");
		
		//分类
		List<String> classs = new ArrayList<String>();
		classs.add("商务职场");
		classs.add("家居");
		classs.add("生活用品");
		classs.add("建筑");
		classs.add("人物");
		classs.add("生活方式");
		classs.add("文化艺术");
		classs.add("自然风光");
		classs.add("城市风光");
		classs.add("旅游地理");
		classs.add("科技");
		classs.add("交通运输");
		getClassByBaiKe(labels,classs);
	}
	
	
	

	@Test
	public void testbaike() {
		Document doc;
		try {
			doc = Jsoup.connect("http://baike.baidu.com/item/树木").get();
			Elements listClass = doc.getElementsByAttributeValue("class",
					"para");
			for (Element element : listClass) {
//				System.out.println(element.getElementsByTag("div").text());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test
	public void test() {

		Document doc;
		try {
			doc = Jsoup
					.connect(
							"http://www.baidu.com/s?q1=城市风光+房屋&q2=&q3=&q4=&gpc=stf&ft=&q5=&q6=&tn=58025142_oem_dg")
					.get();
			Element nums = doc.select("div.nums").first();
			
			String text = nums.text().toString();
			System.out.println(text);
			//获取数字
			String regEx="[^0-9]";   
			Pattern p = Pattern.compile(regEx);   
			Matcher m = p.matcher(text);   
			long result =Long.valueOf(m.replaceAll("").trim());
			System.out.println(result);
		} catch (Exception e) {

		}
	}
	
//	/**
//	* 求Map<K,V>中Key(键)的最大值
//	* @param map
//	* @return
//	*/
//	public static Object getMaxKey(Map<String, Integer> map) {
//	if (map == null) return null;
//	Set<String> set = map.keySet();
//	Object[] obj = set.toArray();
//	Arrays.sort(obj);
//	return obj[obj.length-1];
//	}、、
	
	public static List<String> getMaxValueKey(Map<String, Integer> map,Integer value) {
		List<String> results = new ArrayList<String>();
		Iterator<String> iter = map.keySet().iterator(); 
        while(iter.hasNext()){ 
            String key=iter.next(); 
            int values = map.get(key); 
            if(values == value){
            	results.add(key);
            }
        }
		return results;
	}
	
	
	/**
	* 求Map<K,V>中Value(值)的最大值
	* @param map
	* @return
	*/
	public static Object getMaxValue(Map<String, Integer> map) {
	if (map == null) return null;
	Collection<Integer> c = map.values();
	Object[] obj = c.toArray();
	Arrays.sort(obj);
	return obj[obj.length-1];
	}
	
	public static List<String> getMaxValueKeyBylong(Map<String, Long> map,long value) {
		List<String> results = new ArrayList<String>();
		Iterator<String> iter = map.keySet().iterator(); 
        while(iter.hasNext()){ 
            String key=iter.next(); 
            long values = (long) map.get(key); 
            if(values == value){
            	results.add(key);
            }
        }
		return results;
	}
	
	
	/**
	* 求Map<K,V>中Value(值)的最大值
	* @param map
	* @return
	*/
	public static Object getMaxValueBylong(Map<String, Long> map) {
	if (map == null) return null;
	Collection<Long> c = map.values();
	Object[] obj = c.toArray();
	Arrays.sort(obj);
	return obj[obj.length-1];
	}
	

	public static void main(String[] args) {
		List<String> labels = new ArrayList<String>();
//		labels.add("阶梯");
		labels.add("建筑");
		labels.add("广场");
//		labels.add("人群");
//		labels.add("岩石");
		labels.add("天空");
//		labels.add("男孩");
		List<String> classs = new ArrayList<String>();
		classs.add("商务职场");
		classs.add("家居");
		classs.add("动物");
		classs.add("生活用品");
//		classs.add("建筑");
//		classs.add("其他");
		classs.add("人物");
		classs.add("生活方式");
		classs.add("文化艺术");
		classs.add("自然风光");
		classs.add("城市风光");
		classs.add("旅游地理");
		classs.add("科技");
		classs.add("设计素材");
		classs.add("美食");
		classs.add("交通运输");
		classs.add("金融财经");
		List<String> result = getClassByBaiKe(labels,classs);
		System.out.println(result);
	}

	/**
	 * 
	 * @param a
	 *            被匹配的长字符串
	 * @param b
	 *            匹配的短字符串
	 * @return 匹配次数
	 */
	private static int hit(String a, String b) {
		if (a.length() < b.length()) {
			return 0;
		}
		char[] a_t = a.toCharArray();
		int count = 0;

		for (int i = 0; i < a.length() - b.length(); i++) {
			StringBuffer buffer = new StringBuffer();
			for (int j = 0; j < b.length(); j++) {
				buffer.append(a_t[i + j]);
			}
			if (buffer.toString().equals(b)) {
				count++;
			}
		}

		return count;
	}

}
