package com.wzes.proxy.util;


import com.alibaba.fastjson.JSONObject;
import com.wzes.proxy.domain.Proxy;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Site;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Random;

public class HttpHelper {

	/**
	 * //url解密
	 * @param param
	 * @param encoding
	 * @return
	 */
	public static String URLDecode(String param,String encoding){
		try {
			String res=java.net.URLDecoder.decode(param,encoding);
			return res;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * //加密
	 * @param param
	 * @param encoding
	 * @return
	 */
	public static String URLEncode(String param,String encoding){
		try {
			String res=java.net.URLEncoder.encode(param,encoding);
			return res;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	


	/**
	 * //截取首位字符串
	 * @param sourceStr
	 * @param begStr
	 * @param endStr
	 * @return
	 */
	public static String regexStr(String sourceStr,String begStr,String endStr){
		String res="";
		String [] spStr = sourceStr.split(begStr);
		if (spStr.length>0) {
			spStr = spStr[1].split(endStr);
			if (spStr.length>0) {
				res = spStr[0];
			}
		}
		return res;
	}


	/**
	 * 快速检测一个代理是否能用
	 * @param proxy
	 * @return
	 */
	public static boolean checkGoogleProxy(Proxy proxy){
		URL url = null;
		try {
			url = new URL("https://www.google.com/");
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		// 创建代理服务器
		InetSocketAddress addr = new InetSocketAddress(proxy.getIp(), Integer.parseInt(proxy.getPort()));
		java.net.Proxy mProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);
		HttpURLConnection conn = null;
		try {
			if (url != null) {
				conn = (HttpURLConnection)url.openConnection(mProxy);
				conn.setConnectTimeout(3000);
				conn.connect();
				int code = conn.getResponseCode();
				if(code == 200){
					return true;
				}else {
					System.out.println(code);
					return false;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static boolean checkBaiduProxy(Proxy proxy){
		URL url = null;
		try {
			url = new URL("http://www.baidu.com/");
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		// 创建代理服务器
		InetSocketAddress addr = new InetSocketAddress(proxy.getIp(), Integer.parseInt(proxy.getPort()));
		java.net.Proxy mProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);
		HttpURLConnection conn = null;
		try {
			if (url != null) {
				conn = (HttpURLConnection)url.openConnection(mProxy);
				conn.setConnectTimeout(3000);
				conn.connect();
				int code = conn.getResponseCode();
				if(code == 200){
					return true;
				}else {
					System.out.println(code);
					return false;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

//	public static Site getRandomSite(){
//		return Site.me().setRetryTimes(3).setSleepTime(100)
//				.addHeader("User-Agent", getRandomUserAgent())
//				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//				.addHeader("Accept-Encoding", "gzip, deflate, sdch")
//				.addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//				.addHeader("Connection", "keep-alive")
//				.setCharset("utf-8");
//	}

	public static String getRandomUserAgent(){
		String[] agents = {
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
				"Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
				"Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
				"Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
				"Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
				"Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
				"Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
				"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
				"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER)",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 LBBROWSER",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; 360SE)",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
				"Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b13pre) Gecko/20110307 Firefox/4.0b13pre",
				"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
				"Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10"
		};

		return agents[new Random(System.currentTimeMillis()).nextInt(agents.length)];
	}

	public static void main(String[] args) {
		System.out.println(getIpLocation("45.32.155.159"));
//		String word = URLEncode("扬州瓜洲音乐节", "utf-8");
//		System.out.println(word);
//		String param = URLDecode("trialYear%2B2001%2B7%2B2001", "utf8");
//		String Order = URLDecode("%E6%B3%95%E9%99%A2%E5%B1%82%E7%BA%A7", "utf8");
//		System.out.println(param);
//		System.out.println(Order);
	}


	public static String getIpLocation(String ip){
		String api = "http://www.ip138.com/ips138.asp?ip=";
		String url = api + ip + "&datatype=text";
		String location = null;
		try {
			Document document = Jsoup.connect(url).get();
			location = document.select("li").get(0).text().substring(5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return location;
	}
}
