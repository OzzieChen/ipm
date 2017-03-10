package net.xssu.ipm.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 常用工具类
 * @author bonult
 *
 */
public class C {

	/**
	 * 通用类型强转
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object obj) {
		if(obj == null)
			return null;
		return (T) obj;
	}

	/**
	 * String转换double
	 */
	public static double strToDouble(String str) throws Exception {
		if(str != null && !"".equals(str)){
			return Double.valueOf(str);
		}
		throw new NumberFormatException("Convert Error!");
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String s) {
		if(null == s || "".equals(s) || "".equals(s.trim())){
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isNotEmpty(String s) {
		if(null == s || "".equals(s) || "".equals(s.trim())){
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为空
	 */
	public static String notEmpty(String s) {
		if(null == s || "".equals(s) || "".equals(s.trim())){
			return null;
		}
		return s;
	}

	/**
	 * 无null值字符串
	 */
	public static String notNullStr(String s) {
		if(null == s){
			return "";
		}
		return s;
	}

	/**
	 * 使用率计算
	 */
	public static String fromUsage(long free, long total) {
		Double d = new BigDecimal(free * 100 / total).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}

	/**
	 * 保留两个小数
	 */
	public static String formatDouble(Double b) {
		BigDecimal bg = new BigDecimal(b);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 返回当前时间　格式：yyyy-MM-dd hh:mm:ss
	 */
	public static String fromDateH() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format1.format(new Date());
	}

	/**
	 * 返回当前时间　格式：yyyy-MM-dd
	 */
	public static String fromDate() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(new Date());
	}

	/**
	 * 返回用户的IP地址
	 */
	public static String toIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 提供精确的减法运算。
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 */
	public static double sub(double v1, double v2, double v3) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		return b1.subtract(b2).subtract(b3).doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 将Map形式的键值对中的值转换为泛型形参给出的类中的属性值 t一般代表pojo类
	 */
	public static <T extends Object> T flushObject(T t, Map<String,Object> params) {
		if(params == null || t == null)
			return t;
		Class<?> clazz = t.getClass();
		for(; clazz != Object.class; clazz = clazz.getSuperclass()){
			try{
				Field[] fields = clazz.getDeclaredFields();

				for(int i = 0; i < fields.length; i++){
					String name = fields[i].getName();
					Object value = params.get(name);
					if(value != null && !"".equals(value)){
						fields[i].setAccessible(true);
						fields[i].set(t, value);
					}
				}
			}catch(Exception e){}
		}
		return t;
	}

	/**
	 * html转义
	 */
	public static String htmlToString(String content) {
		if(content == null)
			return "";
		String html = content.replace("'", "&apos;").replaceAll("&", "&amp;").replace("\"", "&quot;").replace("\t", "&nbsp;&nbsp;").replace(" ", "&nbsp;").replace("<", "&lt;").replaceAll(">", "&gt;");// 替换空格
		return html;
	}

	/**
	 * html转义
	 */
	public static String stringToHTML(String content) {
		if(content == null)
			return "";
		String result = content.replace("&apos;", "'").replaceAll("&amp;", "&").replace("&quot;", "\"").replace("&nbsp;&nbsp;", "\t").replace("&nbsp;", " ").replace("&lt;", "<").replaceAll("&gt;", ">");
		return result;
	}

	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	public static String net(String strUrl, Map<String,String> params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try{
			StringBuffer sb = new StringBuffer();
			if(method == null || method.equals("GET")){
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method == null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if(params != null && method.equals("POST")){
				try{
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				}catch(Exception e){}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while((strRead = reader.readLine()) != null){
				sb.append(strRead);
			}
			rs = sb.toString();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				reader.close();
			}
			if(conn != null){
				conn.disconnect();
			}
		}
		return rs;
	}

	public static String urlencode(Map<String,String> data) {
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String,String> i : data.entrySet()){
			try{
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
