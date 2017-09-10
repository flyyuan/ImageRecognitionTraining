package com.xxzx.jeesite.modules.tpsb.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.xxzx.jeesite.common.mapper.JsonMapper;
import com.xxzx.jeesite.modules.tpsb.entity.YouDaoEnZh;

public class YouDaoUtil {
	static final String EN = "en";//英文
	static final String ZHCHS = "zh-CHS";//中文
	static final String JA = "ja";//日文
	static final String KO = "ko";//韩文
	
	public static YouDaoEnZh kO2zhCHS(String query){
		String requestForHttp = requestForHttp(query,KO , ZHCHS);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh kO2EN(String query){
		String requestForHttp = requestForHttp(query,KO , EN);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh kO2JA(String query){
		String requestForHttp = requestForHttp(query,KO , JA);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh jA2zhCHS(String query){
		String requestForHttp = requestForHttp(query,JA , ZHCHS);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh jA2EN(String query){
		String requestForHttp = requestForHttp(query,JA , EN);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh jA2KO(String query){
		String requestForHttp = requestForHttp(query,JA , KO);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh zhCHS2EN(String query){
		String requestForHttp = requestForHttp(query,ZHCHS , EN);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh zhCHS2JA(String query){
		String requestForHttp = requestForHttp(query,ZHCHS , JA);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh zhCHS2KO(String query){
		String requestForHttp = requestForHttp(query,ZHCHS , KO);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh eN2zhCHS(String query){
		String requestForHttp = requestForHttp(query,EN , ZHCHS);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh eN2JA(String query){
		String requestForHttp = requestForHttp(query,EN , JA);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	public static YouDaoEnZh eN2KO(String query){
		String requestForHttp = requestForHttp(query,EN , KO);
		return (YouDaoEnZh) JsonMapper.fromJsonString(requestForHttp, YouDaoEnZh.class);
	}
	
	
	@Test
	public void test(){
		YouDaoEnZh zhCHS2EN = this.zhCHS2KO("韩文");
		System.out.println(zhCHS2EN);
	}
	
	
	
	 public static String requestForHttp(String query,String from,String to) {
	    	String appKey ="15bea94178b8c7e1";
	        String salt = String.valueOf(System.currentTimeMillis());
	        String sign = md5(appKey + query + salt+ "Co8NyCuZ1cuSXcgupFkaJCMor56XpQve");
	        Map<String, String> requestParams = new HashMap<String, String>();
	        requestParams.put("q", query);
	        requestParams.put("from", from);
	        requestParams.put("to", to);
	        requestParams.put("sign", sign);
	        requestParams.put("salt", salt);
	        requestParams.put("appKey", appKey);
	    	
	        String result = null;
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        /**HttpPost*/
	        HttpPost httpPost = new HttpPost("https://openapi.youdao.com/api");
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        Iterator<Entry<String, String>> it = requestParams.entrySet().iterator();
	        while (it.hasNext()) {
	            Entry<String, String> en = it.next();
	            String key = en.getKey();
	            String value = en.getValue();
	            if (value != null) {
	                params.add(new BasicNameValuePair(key, value));
	            }
	        }
	        try {
				httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        /**HttpResponse*/
	        CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try{
	            HttpEntity httpEntity = httpResponse.getEntity();
	            result = EntityUtils.toString(httpEntity, "utf-8");
	            EntityUtils.consume(httpEntity);
	        }catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
	            try{
	                if(httpResponse!=null){
	                    httpResponse.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	        return result;
	    }

	    /**
	     * 生成32位MD5摘要
	     * @param string
	     * @return
	     */
	    public static String md5(String string) {
	        if(string == null){
	            return null;
	        }
	        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	                'A', 'B', 'C', 'D', 'E', 'F'};

	        try{
	            byte[] btInput = string.getBytes("utf-8");
	            /** 获得MD5摘要算法的 MessageDigest 对象 */
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            /** 使用指定的字节更新摘要 */
	            mdInst.update(btInput);
	            /** 获得密文 */
	            byte[] md = mdInst.digest();
	            /** 把密文转换成十六进制的字符串形式 */
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (byte byte0 : md) {
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
	            return null;
	        }
	    }

	    /**
	     * 根据api地址和参数生成请求URL
	     * @param url
	     * @param params
	     * @return
	     */
	    public static String getUrlWithQueryString(String url, Map<String, String> params) {
	        if (params == null) {
	            return url;
	        }

	        StringBuilder builder = new StringBuilder(url);
	        if (url.contains("?")) {
	            builder.append("&");
	        } else {
	            builder.append("?");
	        }

	        int i = 0;
	        for (String key : params.keySet()) {
	            String value = params.get(key);
	            if (value == null) { // 过滤空的key
	                continue;
	            }

	            if (i != 0) {
	                builder.append('&');
	            }

	            builder.append(key);
	            builder.append('=');
	            builder.append(encode(value));

	            i++;
	        }

	        return builder.toString();
	    }
	    /**
	     * 进行URL编码
	     * @param input
	     * @return
	     */
	    public static String encode(String input) {
	        if (input == null) {
	            return "";
	        }

	        try {
	            return URLEncoder.encode(input, "utf-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }

	        return input;
	    }
}
