/**
 * date: 2017年5月18日
 */
package com.ycdc.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.mozilla.javascript.ast.LetNode;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @name: HTTPHelper.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月18日
 */
public class HTTPSHelper {
	
	// 默认实例
	public static HTTPSHelper defaultHelper = new HTTPSHelper();
	
	private String hostname;
	
	private int timeout = 30000;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @return the hostname 域名
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	private void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	/**
	 * 
	 * @param hostname 域名 例：www.room1000.com
	 * @param port 端口号
	 * @param path 域名后面的地址 例：/dinglock/login.do
	 * @param method 请求方法 例： POST
	 * @param requestPropertys 请求头设置
	 * @param params 参数
	 * @return 请求返回的字符串
	 * @throws Exception
	 * @Description:
	 */
	public String getHttpsConnection(String host, String port, String path, String method, Map<String, String> requestPropertys, Object params) throws Exception
	{
		logger.debug("[Client.getHttpsConnection] Verify SSL");
		this.setHostname(host);
		URL url = new URL("https://"+host+port+path);
		if (method.toLowerCase().equals("get")) {
			Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(params), new TypeReference<Map<String, Object>>(){});
			Iterator<String> iterator = map.keySet().iterator();
			String paramsString = "";
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (map.get(key) instanceof String) {
					paramsString += "&"+key+"="+URLEncoder.encode((String)map.get(key), "UTF-8");
				} else {
					paramsString += "&"+key+"="+map.get(key);
				}
			}
			paramsString = paramsString.substring(1);
			String urlString = "https://"+host+port+path+"?"+paramsString;
			url = new URL(urlString);
		}
		
		logger.debug("[Client.getHttpsConnection] connect url = " + url);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		logger.debug("[Client.getHttpsConnection] method = "+method);
		Iterator<String> iter = requestPropertys.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = requestPropertys.get(key);
			conn.setRequestProperty(key, value);
		}
		logger.debug("[Client.getHttpsConnection] connect requestPropertys = " + requestPropertys.toString());
		conn.setSSLSocketFactory(setupSSL().getSocketFactory());
		conn.setConnectTimeout(getServerTimeout());
		conn.setReadTimeout(getServerTimeout());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (!(method.toLowerCase().equals("get"))) {
			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			String paramsString = JSONObject.toJSONString(params);
			logger.debug("[Client.getHttpsConnection] connect paramsString = " + paramsString);
			outputStream.write(paramsString.getBytes());
		}
		logger.debug("[Client.getHttpsConnection] connect responseCode = " + conn.getResponseCode());
		if ((new Integer(conn.getResponseCode()).toString()).contains("2")) {
			return this.parseInputStream(conn.getInputStream());
		}
		logger.debug("[Client.getHttpsConnection] connect responseMessage = "+conn.getResponseMessage());
		return "";
	}
	
	/**
	 * 解析请求响应体中的流数据
	 * 
	 * @param stream
	 * @return 流数据解析后的字符串(默认为接口返回json)
	 * @throws IOException
	 * @Description:
	 */
	private String parseInputStream(InputStream stream) throws IOException
	{
		// 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
		BufferedReader bf = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder sb = new StringBuilder(); // 用来存储响应数据
		// 循环读取流,若不到结尾处
		while ((line = bf.readLine()) != null)
		{
			sb.append(line);
		}
		bf.close(); // 重要且易忽略步骤 (关闭流,切记!)
		logger.debug("[Client.parseInputStream] parse dataString = "+sb.toString());
		return sb.toString().trim();
	}
	
	// 设置ssl信任
	private SSLContext setupSSL() throws Exception
	{
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[]{ new MyTrustManager() }, new SecureRandom());
		HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
		return sc;
	}

	public int getServerTimeout()
	{
		return this.timeout;
	}

	public boolean setServerTimeout(int value)
	{
		if (value < 5000 || value > 120000)
		{
			return false;
		}
		this.timeout = value;
		return true;
	}

	private class MyHostnameVerifier implements HostnameVerifier
	{
		@Override
		public boolean verify(String hostname, SSLSession session)
		{
			if ("".equals(hostname)) {
				return true;
			}
			return false;
		}

	}

	private class MyTrustManager implements X509TrustManager
	{

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
		{
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
		{
		}

		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

	}

	class RegulateNumber
	{

		// process phone number, change +861xxx, 00861xxx 1xxx formats to 861xxxxxx
		String process(String phone)
		{

			if (null == phone || phone.length() < 1)
			{
				return phone;
			}
			// remove +, -, etc
			phone = phone.replaceAll("[+]", "");
			phone = phone.replaceAll(" ", "");
			phone = phone.replaceAll("-", "");
			// remove 00, e.g. 008619912345678 -> 8619912345678
			if (null != phone && phone.length() == 15 && phone.startsWith("00"))
			{
				phone = phone.substring(2);
			}
			// add 86, e.g. 19912345678 -> 8619912345678
			if (null != phone && phone.length() == 11 && phone.startsWith("1"))
			{
				phone = "86" + phone;
			}

			return phone;
		}

	}

	class BASE64Encoder
	{

		private char[] codec_table =
		{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

		public BASE64Encoder()
		{

		}

		public String encode(byte[] a)
		{
			int totalBits = a.length * 8;
			int nn = totalBits % 6;
			int curPos = 0;// process bits
			StringBuffer toReturn = new StringBuffer();
			while (curPos < totalBits)
			{
				int bytePos = curPos / 8;
				switch (curPos % 8)
				{
				case 0:
					toReturn.append(codec_table[(a[bytePos] & 0xfc) >> 2]);
					break;
				case 2:

					toReturn.append(codec_table[(a[bytePos] & 0x3f)]);
					break;
				case 4:
					if (bytePos == a.length - 1)
					{
						toReturn.append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);
					} else
					{
						int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
						toReturn.append(codec_table[pos]);
					}
					break;
				case 6:
					if (bytePos == a.length - 1)
					{
						toReturn.append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);
					} else
					{
						int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
						toReturn.append(codec_table[pos]);
					}
					break;
				default:
					// never hanppen
					break;
				}
				curPos += 6;
			}
			if (nn == 2)
			{
				toReturn.append("==");
			} else if (nn == 4)
			{
				toReturn.append("=");
			}
			return toReturn.toString();

		}
	}
}
