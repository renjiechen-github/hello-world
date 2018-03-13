package com.yc.api.client.http;

import com.yc.api.client.util.JsonUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
	private static String httpURL;

	public static void setHttpUrl(String httpURL) {
		HttpClient.httpURL = httpURL;
		System.out.println("httpURL---------------------:"+HttpClient.httpURL);
	}

	public static String getHttpUrl() {
		return HttpClient.httpURL;
	}

	public static String[] sendJsonRequest(String sessionId, String msgId,
			String merchantNo, String api, String request)
			throws ConnectException, IOException {
		System.out.println("httpURL:"+HttpClient.httpURL);
		String[] response = new String[2];

		HttpURLConnection httpConn = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			String[] arrayOfString1;
			if (httpURL == null) {
				StringBuffer response_head = new StringBuffer();
				response_head.append("{");
				response_head.append("code:-1,");
				response_head.append("message:\"参数配置错误：：银城接口平台服务器地址为空！\"");
				response_head.append("}");

				response[0] = response_head.toString();
				response[1] = "";
				return response;
			}
			httpConn = (HttpURLConnection) new URL(HttpClient.httpURL).openConnection();
			httpConn.setReadTimeout(5000);
			httpConn.setConnectTimeout(5000);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			httpConn.setRequestMethod("POST");

			httpConn.connect();
			if (sessionId == null) {
				sessionId = "";
			}
			writer = new BufferedWriter(new OutputStreamWriter(
					httpConn.getOutputStream(), "UTF-8"));
			writer.write(sessionId);
			writer.newLine();
			writer.flush();
			writer.write(msgId);
			writer.newLine();
			writer.flush();
			writer.write(merchantNo);
			writer.newLine();
			writer.flush();
			writer.write(api);
			writer.newLine();
			writer.flush();

			int length = request.length();

			int size = 10240;
			int count = length / size;
			if (length % size > 0) {
				count++;
			}
			for (int i = 0; i < count; i++) {
				int begin = size * i;
				int end = size * (i + 1);
				if (end > length) {
					end = length;
				}
				String line = request.substring(begin, end);
				writer.write(line);
				writer.newLine();
				writer.flush();
			}
			writer.write("@msgbye@");
			writer.newLine();
			writer.flush();

			int httpResponseCode = httpConn.getResponseCode();
			if (httpResponseCode == 200) {
				reader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(),
								"UTF-8"));

				String retCode = reader.readLine();
				String message = reader.readLine();

				StringBuffer dataBuff = new StringBuffer();
				String line = reader.readLine();
				while ((line != null) && (!"@msgbye@".equalsIgnoreCase(line))) {
					dataBuff.append(line);
					line = reader.readLine();
				}
				response[0] = JsonUtil.toResponse(retCode, message);
				response[1] = dataBuff.toString();
				return response;
			}
			String msg = httpConn.getResponseMessage();

			response[0] = JsonUtil.toResponse("-1", "http error: "
					+ httpResponseCode + " msg=" + msg);
			response[1] = "";

			return response;
		} finally {
			if(reader != null){
				reader.close();
				reader = null;
			}
			if(writer != null){
				writer.close();
				writer = null;
			}
			if (httpConn != null) {
				httpConn.disconnect();
				httpConn = null;
			}
		}
	}
}
