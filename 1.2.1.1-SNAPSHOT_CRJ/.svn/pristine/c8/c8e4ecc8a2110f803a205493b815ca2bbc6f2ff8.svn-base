/**
 * date: 2017年3月31日
 */
package com.ycdc.huawei.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.ycdc.huawei.model.entity.Click2CallEntity;
import com.ycdc.huawei.model.entity.Click2CallResult;
import com.ycdc.huawei.model.entity.FastloginResult;
import com.ycdc.huawei.model.entity.SmsResultsEntity;
import com.ycdc.huawei.model.entity.SmsSendEntity;

/**
 * @name: HuaWeiServiceImpl.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年3月31日
 */

@Service("huaWeiService")
public class HuaWeiServiceImpl implements IHuaWeiService
{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	// @Autowired
	// private IHuaWeiDao huaWeiDao;

	// 功能类型
	private enum InnerType
	{
		sms, virtual;
	}

	// 华为接口域名
	private static final String SERVER_ROOT = "http://ecommapi.huaweiapi.com";

	private static final String RECORD_PATH = "/Users/duanyongrui/Documents/workspace/Records";

	// 发送短信接口地址
	private static final String SENDSMS_URL = "/sms/batchSendSms/v1";
	// 发送模板短信接口地址
	private static final String SENDSMSTMP_URL = "/sms/batchSendSmsViaTemplate/v1";

	// 绑定交易小号接口地址
	private static final String BINDTEL_URL = "/axb/bindNumber/v1";
	// 解绑交易小号接口地址
	private static final String UNBINDTEL_URL = "/axb/unbindNumber/v1";
	// 转绑交易小号接口地址
	private static final String MODIFYTEL_URL = "/axb/modifyNumber/v1";
	// 查询虚拟号码接口地址
	private static final String QUERYVIRTEL_URL = "/phonenumber/queryOrderedNumber/v1/virtualNumber/";

	// 查询交易小号录音列表接口
	private static final String QUERYRECORDS_URL = "/voice/queryRecordList/v1";
	// 下载交易小号录音文件接口
	private static final String DOWNLOADRECORD_URL = "/voice/downloadRecord/v1/recordId/";
	// 删除交易小号录音文件接口
	private static final String DELETERECORD_URL = "/voice/deleteRecord/v1";

	// 发送短信
	public static final String APP_KEY_SMS = "549af35949fa438aa4ecd933ccf46591";
	public static final String APP_SECRECT_SMS = "9f9f4f1ffa52436d8101cb96c28870da";
	// 虚拟小号
	public static final String APP_KEY_VIR = "d0e1080b69744d1fbc34b95661f5d367";
	public static final String APP_SECRECT_VIR = "d28f1c71d3c94825adfa641370dfc020";
	// 双呼
	public static final String APP_KEY_OMP = "svAn4y4DiliL3JEOEhk3F9pnF3nG";
	public static final String APP_SECRET_OMP = "8odyP05343YXZ14jY8Pay30X7f0N";
	public static final String FAST_USERNAME = "CaaS_Test_01";
	public static final String FAST_PASSWORD = "CaaS2.0?";
	
	// 双呼下载录音文件key和secretkey
	public static final String DOWNLOAD_ACCESS_KEY = "C7040CEEF0EB1D1315B8";
	public static final String DOWNLOAD_SECRET_KEY = "U4homTVNYrgbbSOuGkS3tB8MN0wAAAFa8OsdE5Iv";
	// public static final String DOWNLOAD_API = "https://117.78.29.67:443";

	// 双呼默认服务器地址
	public static final String DEFAULT_SERVER = "https://117.78.29.66:10443";

	// (双呼)大客户 SP 简单认证
	private static final String OAUTH_FASTLOGIN_API = "/rest/fastlogin/v1.0?";
	// (双呼)大客户 SP 认证刷新
	private static final String OAUTH_REFRESH_API = "/omp/oauth/refresh?";
	// (双呼)大客户 SP 认证取消
	private static final String OAUTH_LOGOUT_API = "/rest/logout/v1.0?";
	// (双呼)点击拨号测试
	private static final String CLICK_TO_CALL_TEST = "/sandbox/rest/httpsessions/click2Call/v2.0?";
	// (双呼)点击拨号商用正式
	private static final String CLICK_TO_CALL_API = "/rest/httpsessions/click2Call/v2.0?";
	// 双呼回调地址
	private static final String CLICK_TO_CALLBACK = "http://114.55.218.179:20850/MyFirstMaven/click2CallBack.do";
	private int timeout = 30000;

	private static String token = "";
	private static String refresh_token = "";

	private static final String Authorization = "WSSE realm=\"SDP\", profile=\"UsernameToken\", type=\"Appkey\"";

	// 设置请求头字段X-WSSE的值
	public String getHeader_WSSE(InnerType type) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte[] md5Data = MessageDigest.getInstance("MD5").digest(new Integer(new Random().nextInt(1000000)).toString().getBytes("utf-8"));
		String header_Nonce = new BASE64Encoder().encode(md5Data);
		String header_Created = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'").format(new Date());
		if (type == InnerType.sms)
		{
			String header_Password = new BASE64Encoder().encode(MessageDigest.getInstance("SHA-256").digest((header_Nonce + header_Created + APP_SECRECT_SMS).getBytes("utf-8")));
			return "UsernameToken Username=\"" + APP_KEY_SMS + "\", PasswordDigest=\"" + header_Password + "\", Nonce=\"" + header_Nonce + "\", Created=\"" + header_Created + "\"";
		}
		String header_Password = new BASE64Encoder().encode(MessageDigest.getInstance("SHA-256").digest((header_Nonce + header_Created + APP_SECRECT_VIR).getBytes("utf-8")));
		return "UsernameToken Username=\"" + APP_KEY_VIR + "\", PasswordDigest=\"" + header_Password + "\", Nonce=\"" + header_Nonce + "\", Created=\"" + header_Created + "\"";
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
		return sb.toString().trim();
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 *          请求地址
	 * @param method
	 *          请求方法
	 * @param urlParamsString
	 *          请求参数
	 * @return 连接对象
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @Description:
	 */
	private HttpURLConnection getDefaultConnection(InnerType type, URL url, String method, String contentType, String urlParamsString) throws IOException, NoSuchAlgorithmException
	{
		// 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
		// (标识一个url所引用的远程对象连接)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中

		// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
		connection.setDoOutput(true);

		// 设置连接输入流为true
		connection.setDoInput(true);

		// 设置请求方式为post
		connection.setRequestMethod(method);

		// post请求缓存设为false
		connection.setUseCaches(false);

		// 设置该HttpURLConnection实例是否自动执行重定向
		connection.setInstanceFollowRedirects(true);

		// 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)(参照华为文档设置)
		// application/x-javascript text/xml->xml数据 application/x-javascript->json对象
		// application/x-www-form-urlencoded->表单数据
		connection.setRequestProperty("Content-Type", contentType);
		connection.setRequestProperty("Connection", "keep-alive");
		connection.setRequestProperty("Authorization", Authorization);
		connection.setRequestProperty("X-WSSE", this.getHeader_WSSE(type));
		connection.setRequestProperty("Accept", "application/json");
		// 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
		connection.connect();

		// 创建输入输出流,用于往连接里面输出携带的参数
		DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
		// URLEncoder.encode()方法 为字符串进行编码 将参数输出到连接
		dataout.writeBytes(urlParamsString);

		// 输出完成后刷新并关闭流
		dataout.flush();
		// 重要且易忽略步骤 (关闭流,切记!)
		dataout.close();
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ycrm.mymaven.login.service.IHuaWeiService#sendSms(com.ycrm.mymaven.
	 * login.model.entity.SmsParams)
	 */
	@Override
	public SmsResultsEntity sendSms(SmsSendEntity params)
	{
		// TODO Auto-generated method stub
		SmsResultsEntity results = new SmsResultsEntity();
		try
		{
			URL url = new URL(SERVER_ROOT + SENDSMS_URL);
			// 按照华为接口格式拼接参数
			String urlParamsString = "from=" + URLEncoder.encode(params.getFrom(), "UTF-8") + "&to=" + URLEncoder.encode(params.getTo(), "UTF-8") + "&body="
					+ URLEncoder.encode(params.getBody(), "UTF-8");
			if (params.getStatusCallback() != null && !(params.getStatusCallback().isEmpty()))
			{
				urlParamsString += "&statusCallback=" + URLEncoder.encode(params.getStatusCallback(), "UTF-8");
			}
			logger.info(urlParamsString);
			// 开始连接接口调用
			HttpURLConnection connection = this.getDefaultConnection(InnerType.sms, url, "POST", "application/x-www-form-urlencoded", urlParamsString);
			// 解析输入流以获取接口返回的json数据
			String jsonString = this.parseInputStream(connection.getInputStream());
			// 销毁连接
			connection.disconnect();
			logger.info(jsonString);
			results = JSONObject.toJavaObject(JSON.parseObject(jsonString), SmsResultsEntity.class);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ycrm.mymaven.login.service.IHuaWeiService#sendSmsViaTemplate(com.ycrm.
	 * mymaven.login.model.entity.SmsSendEntity)
	 */
	@Override
	public SmsResultsEntity sendSmsViaTemplate(SmsSendEntity params)
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		SmsResultsEntity results = new SmsResultsEntity();
		try
		{
			URL url = new URL(SERVER_ROOT + SENDSMSTMP_URL);
			// 按照华为接口格式拼接参数
			String urlParamsString = "from=" + URLEncoder.encode(params.getFrom(), "UTF-8") + "&to=" + URLEncoder.encode(params.getTo(), "UTF-8") + "&templateId="
					+ URLEncoder.encode(params.getTemplateId(), "UTF-8") + "&templateParams=" + URLEncoder.encode(params.getTemplateParams(), "UTF-8");
			if (params.getStatusCallback() != null && !(params.getStatusCallback().isEmpty()))
			{
				urlParamsString += "&statusCallback=" + URLEncoder.encode(params.getStatusCallback(), "UTF-8");
			}
			// 开始连接接口调用
			HttpURLConnection connection = this.getDefaultConnection(InnerType.sms, url, "POST", "application/x-www-form-urlencoded", urlParamsString);
			// 解析输入流以获取接口返回的json数据
			String jsonString = this.parseInputStream(connection.getInputStream());
			// 销毁连接
			connection.disconnect();
			logger.info(jsonString);
			results = JSONObject.toJavaObject(JSON.parseObject(jsonString), SmsResultsEntity.class);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.ycrm.mymaven.login.dao.IHuaWeiDao#bindTelNumber(com.ycrm.mymaven.login.
//	 * model.entity.VManageEntity)
//	 */ 
//	 @Override 
//	 public VirBindResultEntity bindVirtualNumber(VirBindEntity params) { 
//		 // TODO Auto-generated method stub 
//		 VirBindResultEntity result = new VirBindResultEntity(); 
//		 if (params.getOperId() == null || params.getOperId().isEmpty()) { 
//			 result.setCode("-1");
//			 result.setDescription("操作人不能为空"); 
//			 }
//		 try { 
//			 URL url = new URL(SERVER_ROOT+BINDTEL_URL); 
//			 JSONObject json = (JSONObject)JSONObject.toJSON(params); 
//			 if (params.getCityCode() != null || params.getEndTime() != null) { 
//				 List<Map<String, String>> list = new ArrayList<Map<String,String>>(); 
//				 Map<String, String> cityCodeMap = new HashMap<String, String>(); 
//				 cityCodeMap.put("key", "cityCode");
//				 cityCodeMap.put("value", params.getCityCode()); 
//				 list.add(cityCodeMap);
//				 Map<String, String> endTimeMap = new HashMap<String, String>();
//				 endTimeMap.put("key", "endTime"); 
//				 endTimeMap.put("value",params.getEndTime()); 
//				 list.add(endTimeMap); 
//				 json.put("extParas", list); 
//			 }
//
//			 // 开始连接接口调用 HttpURLConnection connection =
//			 this.getDefaultConnection(InnerType.virtual, url, "POST","application/json; charset=UTF-8",json.toJSONString()); 
//			 //解析输入流以获取接口返回的json数据 
//			 String jsonString = this.parseInputStream(connection.getInputStream()); 
//			 // 销毁连接
//			 connection.disconnect(); 
//			 logger.info(jsonString); 
//			 result = JSONObject.toJavaObject(JSON.parseObject(jsonString),VirBindResultEntity.class); 
//			 if (result != null && "000000".equals(result.getCode())) { 
//				 SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
//				 dateFormat.setCalendar(Calendar.getInstance(Locale.CHINA));
//				 dateFormat.getCalendar().add(Calendar.HOUR, -8);
//				 params.setCreateTime(dateFormat.format(new Date()));
//				 params.setSubscriptionId(result.getResult().getSubscriptionId()); 
//				 if (result.getResult().getVirtualNumber() != null && !(result.getResult().getVirtualNumber().isEmpty())) 
//				 {
//					 params.setVirtualNumber(result.getResult().getVirtualNumber()); 
//				 } 
//				 huaWeiDao.bindVirtual(params); 
//				 VirQueryResultEntity queryEntity = this.queryVirtualBumber(params.getVirtualNumber());
//				 huaWeiDao.updateVirtualNumberStatus(Integer.parseInt(queryEntity.getResult().getNumberStatus()), params.getVirtualNumber()); 
//			 } 
//		 } catch (Exception e) {
//			 e.printStackTrace(); 
//		 } 
//		 return result; 
//	 }
//
//	 /* (non-Javadoc)
//	 * 
//	 * @see
//	 * com.ycrm.mymaven.login.dao.IHuaWeiDao#unbindVirtualNumber(com.ycrm.mymaven.
//	 * login.model.entity.VirUnbindEntity)
//	 */
//	 @Override 
//	 public Map<String, String> unbindVirtualNumber(String virtualNumber, String aParty, String bParty, String operId) { 
//		 // TODO Auto-generated method stub 
//		 Map<String, String> map = new HashMap<String,String>(); 
//		 String subscriptionId = huaWeiDao.getSubscriptionId(virtualNumber, aParty, bParty); 
//		 VirUnbindEntity entity = new VirUnbindEntity("2", "", subscriptionId); 
//		 try { 
//			 URL url = new URL(SERVER_ROOT+UNBINDTEL_URL); 
//			 // 开始连接接口调用 
//			 HttpURLConnection connection = this.getDefaultConnection(InnerType.virtual, url, "POST","application/json; charset=UTF-8",JSONObject.toJSONString(entity)); 
//			 // 解析输入流以获取接口返回的json数据 String jsonString =
//			 this.parseInputStream(connection.getInputStream()); 
//			 // 销毁连接
//			 connection.disconnect(); logger.info(jsonString); 
//			 map = JSON.parseObject(jsonString, new TypeReference<Map<String, String>>(){});
//			 if (map != null && "000000".equals(map.get("code"))) { 
//				 SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
//				 dateFormat.setCalendar(Calendar.getInstance(Locale.CHINA));
//				 dateFormat.getCalendar().add(Calendar.HOUR, -8);
//				 huaWeiDao.unbindVirtual(subscriptionId,operId, dateFormat.format(new Date())); 
//				 huaWeiDao.deleteBindVirtual(subscriptionId); 
//				 VirQueryResultEntity queryEntity = this.queryVirtualBumber(virtualNumber);
//				 huaWeiDao.updateVirtualNumberStatus(Integer.parseInt(queryEntity.getResult().getNumberStatus()), virtualNumber); 
//			 } 
//		 } catch (Exception e) {
//			 e.printStackTrace();
//		 } 
//		 return map; 
//	 }
//	 /* 
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.ycrm.mymaven.login.dao.IHuaWeiDao#modifyVirtualNumber(com.ycrm.mymaven.
//	 * login.model.entity.VirModifyEntity)
//	 */ 
//	 @Override 
//	 public Map<String, String> modifyBind(String virtualNumber, String aParty, String bParty, String bPartyNew, String operId) { 
//		 // TODO Auto-generated method stub 
//		 Map<String, String> map = new HashMap<String, String>(); 
//		 try { 
//			 URL url = new URL(SERVER_ROOT+MODIFYTEL_URL); 
//			 // 获取绑定关系id 
//			 String subscriptionId = huaWeiDao.getSubscriptionId(virtualNumber, aParty, bParty); 
//			 // 创建并初始化参数对象 VirModifyEntity modifyEntity = new
//			 VirModifyEntity(subscriptionId, bPartyNew); 
//			 // 开始连接接口调用 HttpURLConnection
//			 connection = this.getDefaultConnection(InnerType.virtual, url, "POST", "application/json; charset=UTF-8",JSONObject.toJSONString(modifyEntity));
//			 // 解析输入流以获取接口返回的json数据 
//			 String jsonString = this.parseInputStream(connection.getInputStream()); 
//			 // 销毁连接
//			 connection.disconnect(); 
//			 logger.info(jsonString); 
//			 map = JSON.parseObject(jsonString, new TypeReference<Map<String, String>>(){});
//			 if (map != null && "000000".equals(map.get("code"))) { 
//				 SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
//				 dateFormat.setCalendar(Calendar.getInstance(Locale.CHINA));
//				 dateFormat.getCalendar().add(Calendar.HOUR, -8);
//				 huaWeiDao.modifyBind(subscriptionId, bPartyNew);
//				 huaWeiDao.insertBindRecord(subscriptionId, bPartyNew, operId, dateFormat.format(new Date())); 
//			 } 
//		 } catch (Exception e) {
//			 e.printStackTrace(); 
//		 } 
//		 return map; 
//	 }
//	 /* 
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.ycrm.mymaven.login.dao.IHuaWeiDao#queryVirtualBumber(java.lang.String)
//	 */
//	 @Override 
//	 public VirQueryResultEntity queryVirtualBumber(String virtualNumber) { 
//		 // TODO Auto-generated method stub 
//		 VirQueryResultEntity result = new VirQueryResultEntity(); 
//		 try { 
//			 URL url = new URL(SERVER_ROOT+QUERYVIRTEL_URL+virtualNumber); 
//			 // 开始连接接口调用
//			 HttpURLConnection connection = this.getDefaultConnection(InnerType.virtual, url, "GET", "application/json; charset=UTF-8","{}"); 
//			 // 解析输入流以获取接口返回的json数据
//			 String jsonString = this.parseInputStream(connection.getInputStream()); 
//			 //销毁连接 
//			 connection.disconnect(); logger.info(jsonString); 
//			 result = JSONObject.toJavaObject(JSON.parseObject(jsonString), VirQueryResultEntity.class); 
//		 } catch (Exception e) { 
//			 e.printStackTrace(); 
//		 }
//		 return result; 
//	 }
//	 /* 
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.ycrm.mymaven.login.dao.IHuaWeiDao#queryRecordList(com.ycrm.mymaven.
//	 * login.model.entity.RecordQueryEntity)
//	 */
//	 @Override 
//	 public RecordQueryResultEntity queryRecordList(RecordQueryEntity params) { 
//		 // TODO Auto-generated method stub 
//		 RecordQueryResultEntity result = new RecordQueryResultEntity(); 
//		 try { 
//			 URL url = new URL(SERVER_ROOT+QUERYRECORDS_URL); 
//			 // 开始连接接口调用 
//			 HttpURLConnection connection = this.getDefaultConnection(InnerType.virtual, url, "POST", "application/json; charset=UTF-8",JSONObject.toJSONString(params)); 
//			 // 解析输入流以获取接口返回的json数据 
//			 String jsonString = this.parseInputStream(connection.getInputStream()); 
//			 // 销毁连接
//			 connection.disconnect(); 
//			 logger.info(jsonString); 
//			 result = JSONObject.toJavaObject(JSON.parseObject(jsonString), RecordQueryResultEntity.class); 
//		 } catch (Exception e) {
//			 e.printStackTrace(); 
//		 } 
//		 return result; 
//	 }
//	 /* 
//	 * (non-Javadoc)
//	 * 
//	 * @see com.ycrm.mymaven.login.dao.IHuaWeiDao#downloadRecord(java.lang.String)
//	 */
//	 @Override 
//	 public File downloadRecord(String recordId, String createTime, String subscriptionId,String virNumber, String operId) { 
//		 // TODO Auto-generated method stub 
//		 File file = new File(""); 
//		 if (recordId == null) {
//			 logger.info("recorId不能为空");
//			 return file;
//		 }
//		 if (createTime == null) {
//			 logger.info("createTime不能为空");
//			 return file;
//		 }
//		 if (subscriptionId == null) {
//			 logger.info("subscriptionId不能为空");
//			 return file;
//		 }
//		 if (virNumber == null) {
//			 logger.info("virNumber不能为空");
//			 return file;
//		 }
//		 if (operId == null) {
//			 logger.info("operId不能为空");
//			 return file;
//		 }
//		 try { 
//			 URL url = new URL(SERVER_ROOT+DOWNLOADRECORD_URL+recordId); 
//			 // 开始连接接口调用 
//			 HttpURLConnection connection = this.getDefaultConnection(InnerType.virtual, url, "GET", "application/json; charset=UTF-8","{}"); 
//			 Map<String, List<String>> map = connection.getHeaderFields(); 
//			 List<String> locations = map.get("Location");
//			 if (locations.size() <= 0) return file; 
//			 String downloadString = locations.get(0); 
//			 URL downloadUrl = new URL(downloadString); 
//			 try { 
//				 File parent = new File(RECORD_PATH); 
//				 if (!(parent.exists())) { 
//					 parent.mkdirs();
//				 } 
//				 String pathString = RECORD_PATH+File.separator+subscriptionId+"_"+virNumber+"_"+"_"+new Random().nextInt(1000000)+"_"+createTime; 
//				 file = new File(pathString);
//				 while (file.exists()) { 
//					 file = new File(pathString); 
//				 }
//				 Streams.copy(downloadUrl.openStream(), new FileOutputStream(file), true);
//				 logger.info(file.getPath()); 
//			 } catch(IOException e) { 
//				 e.printStackTrace();
//			 } 
//			 // 销毁连接 
//			 connection.disconnect();
//	 
//			 logger.info(downloadString); 
//		 } catch (Exception e) { 
//			 e.printStackTrace(); 
//		 }
//		 return file; 
//	 }
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see com.ycrm.mymaven.login.dao.IHuaWeiDao#deleteRecord(java.lang.String)
//	 */
//	 @Override
//	 public Map<String, String> deleteRecord(String recordId) { 
//		 // TODO Auto-generated method stub 
//		 Map<String, String> map = new HashMap<String, String>(); 
//		 try { 
//			 URL url = new URL(SERVER_ROOT+DELETERECORD_URL); 
//			 // 开始连接接口调用 
//			 HttpURLConnection connection = this.getDefaultConnection(InnerType.virtual, url, "POST", "application/json; charset=UTF-8","{recordId:"+recordId+"}"); 
//			 // 解析输入流以获取接口返回的json数据 
//			 String jsonString = this.parseInputStream(connection.getInputStream()); 
//			 // 销毁连接
//			 connection.disconnect(); 
//			 logger.info(jsonString); 
//			 map = JSON.parseObject(jsonString, new TypeReference<Map<String, String>>(){}); 
//		 } catch (Exception e) { 
//			 e.printStackTrace(); 
//		 } 
//		 return map; 
//	 }
	 

	// 登录鉴权获取token秘钥
	public String getFastloginToken(String username, String password)
	{
		InputStream inputStream = null;
		DataOutputStream out = null;
		ByteArrayOutputStream baos = null;
		String data = "";
		String accessToken = "";
		try
		{
			// prepare post url
			String userName = username.replace("+", "%2b");
			String url = DEFAULT_SERVER + OAUTH_FASTLOGIN_API + "app_key=" + APP_KEY_OMP + "&username=" + new RegulateNumber().process(userName);
			logger.debug("[OmpRestClient.fastLogin] post url: " + url);

			// get https connection
			HttpsURLConnection conn = getHttpsPostConnection(url);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("Authorization", new RegulateNumber().process(password));
			conn.connect();

			int responseCode = conn.getResponseCode();

			if (responseCode == 200)
			{
				inputStream = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int readSize = -1;
				while ((readSize = inputStream.read(buffer)) != -1)
				{
					baos.write(buffer, 0, readSize);
				}
				data = baos.toString();
				logger.debug("[OmpRestClient.fastLogin] response data: " + data);
				if (null != data && !data.trim().equals(""))
				{
					logger.debug("[OmpRestClient.fastLogin] parse response data");
					FastloginResult result = JSONObject.toJavaObject(JSONObject.parseObject(data), FastloginResult.class);
					accessToken = result.getAccess_token();
					refresh_token = result.getRefresh_token();
					logger.debug("[OmpRestClient.fastLogin] parsed token: " + accessToken);
				} else
				{
					logger.debug("[OmpRestClient.fastLogin] 200 response with empty data");
				}
			} else
			{
				inputStream = conn.getErrorStream();
				baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int readSize = -1;
				while ((readSize = inputStream.read(buffer)) != -1)
				{
					baos.write(buffer, 0, readSize);
				}
				data = baos.toString();
				logger.debug("[OmpRestClient.fastLogin] " + responseCode + " response with data: " + data);
			}
		} catch (Exception e)
		{
			logger.debug("[OmpRestClient.fastLogin] exception: " + e.toString());
		} finally
		{
			try
			{
				if (inputStream != null)
					inputStream.close();
				if (out != null)
					out.close();
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		logger.debug("[OmpRestClient.fastLogin] Exit with token: " + accessToken);
		return accessToken;
	}

	// 刷新鉴权token
	public String refreshFastloginToken()
	{
		InputStream inputStream = null;
		DataOutputStream out = null;
		ByteArrayOutputStream baos = null;
		String data = "";
		String accessToken = "";
		try
		{
			// prepare post url
			String url = DEFAULT_SERVER + OAUTH_REFRESH_API + "app_key=" + APP_KEY_OMP + "&grant_type=refresh_token" + "&refresh_token=" + refresh_token + "&app_secret="
					+ APP_SECRET_OMP;
			logger.debug("[OmpRestClient.fastLogin] post url: " + url);

			// get https connection
			HttpsURLConnection conn = getHttpsPostConnection(url);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.connect();

			int responseCode = conn.getResponseCode();

			if (responseCode == 200)
			{
				inputStream = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int readSize = -1;
				while ((readSize = inputStream.read(buffer)) != -1)
				{
					baos.write(buffer, 0, readSize);
				}
				data = baos.toString();
				logger.debug("[OmpRestClient.fastLogin] response data: " + data);
				if (null != data && !data.trim().equals(""))
				{
					logger.debug("[OmpRestClient.fastLogin] parse response data");
					FastloginResult result = JSONObject.toJavaObject(JSONObject.parseObject(data), FastloginResult.class);
					accessToken = result.getAccess_token();
					refresh_token = result.getRefresh_token();
					logger.debug("[OmpRestClient.fastLogin] parsed token: " + accessToken);
				} else
				{
					logger.debug("[OmpRestClient.fastLogin] 200 response with empty data");
				}
			} else
			{
				inputStream = conn.getErrorStream();
				baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int readSize = -1;
				while ((readSize = inputStream.read(buffer)) != -1)
				{
					baos.write(buffer, 0, readSize);
				}
				data = baos.toString();
				logger.debug("[OmpRestClient.fastLogin] " + responseCode + " response with data: " + data);
			}
		} catch (Exception e)
		{
			logger.debug("[OmpRestClient.fastLogin] exception: " + e.toString());
		} finally
		{
			try
			{
				if (inputStream != null)
					inputStream.close();
				if (out != null)
					out.close();
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		logger.debug("[OmpRestClient.fastLogin] Exit with token: " + accessToken);
		return accessToken;
	}

	// 调用双呼进行拨号
	public Click2CallResult triggerClick2call(String caller, String callee, String bindNbr, String callBackUrl)
	{
		if (null == token || token.equals(""))
		{
			token = this.getFastloginToken(FAST_USERNAME, FAST_PASSWORD);
		}
		Click2CallEntity callEntity = new Click2CallEntity(bindNbr, caller, callee);
		callEntity.setDisplayNbr(bindNbr);
		callEntity.setDisplayCalleeNbr(bindNbr);
		try
		{
			String callBackUrlString = new BASE64Encoder().encode(callBackUrl.getBytes("utf-8"));
			callEntity.setStatusUrl(callBackUrlString);
			callEntity.setFeeUrl(callBackUrlString);
		} catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (null == caller || caller.equals("") || null == callee || callee.equals("") || null == bindNbr || bindNbr.equals(""))
		{
			return null;
		}

		InputStream inputStream = null;
		DataOutputStream out = null;
		String data = "";
		try
		{
			// prepare request url
			String requestUrl = DEFAULT_SERVER + CLICK_TO_CALL_TEST + "app_key=" + APP_KEY_OMP + "&access_token=" + token + "&format=json";
			logger.debug("[OmpRestClient.triggerClick2call] requestUrl: " + requestUrl);

			// prepare request body
			JSONObject rootObject = (JSONObject) JSONObject.toJSON(callEntity);

			String requestBody = rootObject.toString();
			logger.debug("[OmpRestClient.triggerClick2call] requestBody: " + requestBody);

			// start http connection
			HttpsURLConnection conn = getHttpsPostConnection(requestUrl);
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			logger.debug("[OmpRestClient.triggerClick2call] conn.setRequestProperty Content-Type: application/json;charset=UTF-8");
			byte[] sendData = requestBody.getBytes();

			conn.connect();
			out = new DataOutputStream(conn.getOutputStream());
			out.write(sendData);

			int responseCode = conn.getResponseCode();
			logger.debug("[OmpRestClient.triggerClick2call] responseCode: " + responseCode);
			if (responseCode == 200)
			{
				inputStream = conn.getInputStream();
				data = this.parseInputStream(inputStream);
				logger.debug("[OmpRestClient.triggerClick2call] 200 data: " + data);
			} else
			{
				inputStream = conn.getErrorStream();
				data = this.parseInputStream(inputStream);
				JSONObject jsonObj = JSONObject.parseObject(data);
				if ("1010005".equals(jsonObj.get("resultcode")) || "1010004".equals(jsonObj.get("resultcode")))
				{
					if (refresh_token != null && !"".equals(refresh_token))
					{
						token = this.refreshFastloginToken();
					} else
					{
						token = this.getFastloginToken(FAST_USERNAME, FAST_PASSWORD);
					}
					return this.triggerClick2call(caller, callee, bindNbr, callBackUrl);
				}
				logger.debug("[OmpRestClient.triggerClick2call] " + responseCode + " data: " + data);
			}
		} catch (Exception e)
		{
			logger.debug("[OmpRestClient.triggerClick2call] exception: " + e.toString());
			return null;
		}
		logger.debug("[OmpRestClient.triggerClick2call] EXIT with: " + data);
		return JSONObject.toJavaObject(JSON.parseObject(data), Click2CallResult.class);

	}

	// 设置ssl信任
	private void setupSSL() throws Exception
	{
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[]
		{ new MyTrustManager() }, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
	}

	private HttpsURLConnection getHttpsPostConnection(String url) throws Exception
	{
		logger.debug("[OmpRestClient.getHttpsPostConnection] Verify SSL");
		// SSL
		setupSSL();

		URL ul = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) ul.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(getServerTimeout());
		conn.setReadTimeout(getServerTimeout());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		logger.debug("[OmpRestClient.getHttpsPostConnection] connect timeout = " + getServerTimeout());
		return conn;
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
			return true;
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

	public String downloadClick2CallRecord(String url, String bucketname, String objectName)
	{
		// 创建一个认证类，输入AK和SK
		AWSCredentials credentials = new BasicAWSCredentials(DOWNLOAD_ACCESS_KEY, DOWNLOAD_SECRET_KEY);
		// 创建一个OceanStor客户端连接，url是域名或IP

		AmazonS3 s3 = new AmazonS3Client(credentials);
		s3.setEndpoint(url);

		GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(bucketname, objectName);

		String tmpUrl = s3.generatePresignedUrl(httpRequest).toString();
		try
		{
			setupSSL();
			URL downloadUrl = new URL(tmpUrl);
			HttpURLConnection conn = (HttpURLConnection) downloadUrl.openConnection();
			conn.setConnectTimeout(this.timeout);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if (!(new Integer(code).toString().contains("200")))
			{
				logger.info("下载失败responseCode: " + code);
				logger.info("下载原因responseMessage: " + conn.getResponseMessage());
				return null;
			}
			InputStream stream = conn.getInputStream();
			File file = new File("");
			File parent = new File(RECORD_PATH);
			if (!(parent.exists()))
			{
				parent.mkdirs();
			}
			String pathString = RECORD_PATH + File.separator + new Random().nextInt(1000000) + "_" + objectName;
			file = new File(pathString);
			while (file.exists())
			{
				file = new File(pathString);
			}
			long result = Streams.copy(stream, new FileOutputStream(file), true);
			logger.debug("返回值：",result);
			return pathString;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
