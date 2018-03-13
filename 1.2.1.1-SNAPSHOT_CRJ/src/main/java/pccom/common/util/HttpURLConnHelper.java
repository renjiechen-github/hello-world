/*
 * @author 许正海
 * @date 2013-08-15 12:45:40  
 * Copyright (c) 2013 Njry. All Rights Reserved.
 */
package pccom.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.ycroom.util.aes.MyX509TrustManager;

/**
 * @description HttpURLConnection + XML 接口请求方式
 * @author 许正海
 * @date 2013-08-15
 */
public class HttpURLConnHelper
{
	public static final Logger log            = org.slf4j.LoggerFactory.getLogger(HttpURLConnHelper.class);
	
	private static int         CONNECTTIMEOUT = 5000;                                     // 连接主机的超时时间（单位：毫秒）
	                                                                                       
	private static int         READTIMEOUT    = 5000;                                     // 从主机读取数据的超时时间（单位：毫秒）
	                                                                                       
	/**
	 * @description 读取HTTP请求的内容信息
	 * @author 许正海 2013-08-08
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String readRequestInfo (HttpServletRequest request)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			InputStream in = request.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = "";
			while ( (line = bufferReader.readLine()) != null)
			{
				sb.append(line);
			}
			bufferReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * @description 调用Http请求
	 * @author 许正海 2013-08-07
	 * @return
	 * @throws IOException
	 */
	public static String execute (String serverUrl, String message) throws IOException
	{
		java.net.URL connURL = new java.net.URL(serverUrl);
		HttpURLConnection httpCon = (HttpURLConnection) connURL.openConnection();
		
		// 设置http请求的头部
		httpCon.setUseCaches(false);// Post 请求不能使用缓存
		httpCon.setDoOutput(true); // http正文内，因此需要设为true, 默认情况下是false;
		httpCon.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true;
		
		// 设定传送的内容类型是可序列化的java对象
		httpCon.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
		httpCon.setRequestMethod("POST"); // 设定请求的方法为"POST"，默认是GET
		httpCon.setConnectTimeout(CONNECTTIMEOUT); // 连接主机的超时时间（单位：毫秒）
		httpCon.setReadTimeout(READTIMEOUT); // 从主机读取数据的超时时间（单位：毫秒）
		
		// 写入http请求的正文
		DataOutputStream dataOutputStream = new DataOutputStream(httpCon.getOutputStream());
		dataOutputStream.write(message.getBytes("UTF-8"));
		dataOutputStream.flush();
		dataOutputStream.close();
		int responseCode = httpCon.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) // 返回码正确
		{
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ( (line = bufReader.readLine()) != null)
			{
				sb.append(line);
			}
			bufReader.close();
			return sb.toString();
		}
		else
		// 返回码错误，例如：404
		{
			log.debug("调用Http请求有异常！返回码为：" + responseCode);
			return "";
		}
	}
	
	/**
	 * @description 调用Http请求
	 * @author 许正海 2013-08-07
	 * @return
	 * @throws IOException
	 */
	public static String execute (String serverUrl, String method, String message) throws IOException
	{
		java.net.URL connURL = new java.net.URL(serverUrl);
		HttpURLConnection httpCon = (HttpURLConnection) connURL.openConnection();
		
		// 设置http请求的头部
		httpCon.setUseCaches(false);// Post 请求不能使用缓存
		httpCon.setDoOutput(true); // http正文内，因此需要设为true, 默认情况下是false;
		httpCon.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true;
		
		// 设定传送的内容类型是可序列化的java对象
		httpCon.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
		httpCon.setRequestMethod(method); // 设定请求的方法为"POST"，默认是GET
		httpCon.setConnectTimeout(CONNECTTIMEOUT); // 连接主机的超时时间（单位：毫秒）
		httpCon.setReadTimeout(READTIMEOUT); // 从主机读取数据的超时时间（单位：毫秒）
		
		// 写入http请求的正文
		DataOutputStream dataOutputStream = new DataOutputStream(httpCon.getOutputStream());
		dataOutputStream.write(message.getBytes("UTF-8"));
		dataOutputStream.flush();
		dataOutputStream.close();
		int responseCode = httpCon.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) // 返回码正确
		{
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ( (line = bufReader.readLine()) != null)
			{
				sb.append(line);
			}
			bufReader.close();
			return sb.toString();
		}
		else
		// 返回码错误，例如：404
		{
			log.debug("调用Http请求有异常！返回码为：" + responseCode);
			return "";
		}
	}
	
	public static JSONObject executessl (String serverUrl, String method, Object outputStr){
		JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        HttpsURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try{
        	// 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
        	URL url = new URL(serverUrl);
            httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(method);

            if("GET".equalsIgnoreCase(method))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if(null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                //log.debug(outputStr);
                outputStream.write(outputStr.toString().getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            // 释放资源
//            inputStream.close();
            jsonObject = JSONObject.fromObject(buffer.toString());
        }catch(Exception e){
        	e.printStackTrace();
        	jsonObject = new JSONObject();
        	jsonObject.put("state", -1);
        	jsonObject.put("msg", e.getMessage());
        }finally{
        	if(bufferedReader != null){
        		try {
        			bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		bufferedReader = null;
        	}
        	if(inputStreamReader != null){
        		try {
        			inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		inputStreamReader = null;
        	}
        	if(inputStream != null){
        		try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		inputStream = null;
        	}
        	
        	if(httpUrlConn != null){
        		try{
        			httpUrlConn.disconnect();
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        		
        	}
        }
        return jsonObject;
	}
	
	public static int getCONNECTTIMEOUT ()
	{
		return CONNECTTIMEOUT;
	}
	
	public static void setCONNECTTIMEOUT (int connecttimeout)
	{
		CONNECTTIMEOUT = connecttimeout;
	}
	
	public static int getREADTIMEOUT ()
	{
		return READTIMEOUT;
	}
	
	public static void setREADTIMEOUT (int readtimeout)
	{
		READTIMEOUT = readtimeout;
	}
}
