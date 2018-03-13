package pccom.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;

import net.sf.json.JSONObject;

/**
 * 请求校验工具类
 * @description
 * @author 雷杨
 * @date Mar 5, 2014
 */
public class SignUtil
{
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(SignUtil.class);
	/**
	 * 密钥
	 */
	public static String KEY = "YCQWJ";
	
	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature (String token, String signature, String timestamp, String nonce)
	{
		String[] arr = new String[] {token, timestamp, nonce};
		
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
		{
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		
		try
		{
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr (byte[] byteArray)
	{
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++)
		{
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	
	/**
	 * 将字节转换为十六进制字符串
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr (byte mByte)
	{
		char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[ (mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		
		String s = new String(tempArr);
		return s;
	}
	
	/**
	 * get the md5 hash of a string
	 * @param str
	 * @return
	 */
	public static String md5 (String str)
	{
		
		if (str == null)
		{
			return null;
		}
		
		MessageDigest messageDigest = null;
		
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("utf-8"));
		}
		catch (NoSuchAlgorithmException e)
		{
			
			return str;
		}
		catch (UnsupportedEncodingException e)
		{
			return str;
		}
		
		byte[] byteArray = messageDigest.digest();
		
		StringBuffer md5StrBuff = new StringBuffer();
		
		for (int i = 0; i < byteArray.length; i++)
		{
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) md5StrBuff.append("0").append(
			        Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		
		return md5StrBuff.toString();
	}
	
	/**
	 * 进行验签
	 * @description
	 * @author 雷杨 Jun 4, 2014
	 * @param params serverId=1&type=1211
	 * @param code
	 * @return
	 */
	public static boolean Sign (String params, String code)
	{
		if (code == null || "".equals(code) || params == null || "".equals(params))
		{// 验签失败
			return false;
		}
		if (!SignUtil.md5(SignUtil.md5(params) + SignUtil.KEY).equals(code))
		{// 验签失败
			return false;
		}
		return true;
	}
	
	/**
	 * 获取对应的ASCII码
	 * @description
	 * @author 雷杨 Jun 5, 2014
	 * @param s
	 * @return
	 */
	public String toASCII (String s)
	{
		char[] c = s.toCharArray();
		String returnStr = "";
		for (int i = 0; i < c.length; i++)
		{
			//logger.debug((int)(c[i]));
			returnStr += (int) (c[i]);
		}
		return returnStr;
	}
	
	static String[] one   = {"1", "8", "2", "3", "5", "0", "4", "9", "7", "6"};
	
	static String[] two   = {"1", "2", "9", "0", "3", "7", "4", "5", "6", "8"};
	
	static String[] three = {"5", "2", "1", "8", "4", "9", "0", "6", "3", "7"};
	
	static String[] four  = {"4", "5", "7", "3", "2", "1", "8", "9", "0", "6"};
	
	/**
	 * 获取验证码
	 * @description
	 * @author 雷杨 Jun 5, 2014
	 * @return
	 */
	public static String getSignCode ()
	{
		int o = (int) (Math.random() * 10);
		int t = (int) (Math.random() * 10);
		int h = (int) (Math.random() * 10);
		int f = (int) (Math.random() * 10);
		return o + "" + t + "" + h + "" + f + one[o] + two[t] + three[h] + four[f];
	}
	
	/**
	 * 根据数据库获取验证码
	 * @param server_id 服务器ID
	 * @param type 1时间过期2点击一次失效
	 * @param code_lose_date 失效时间（传入分钟）
	 * @return
	 */
	@Deprecated
	public static String getDbSignCode (String server_id, int type, int code_lose_date)
	{
		//		String code = md5(getSignCode());
		//		DBHelperSpring db = new DbMutualSql(server_id).getDbHelper();
		//		//先检查该验证码在数据库中是否存在，如果不存在就插入存在重新生成
		//		if(db.queryForInt("select count(1) from yc_wx_verification_code a where a.CODE = ? ", new Object[]{code})==1){
		//			return getDbSignCode(server_id,type,code_lose_date);
		//		}else{
		//			//插入随机码到数据库
		//			db.update("insert into yc_wx_verification_code " +
		//					"  (CODE, CODE_CRATE_DATE, CODE_LOSE_DATE,type,state) " + 
		//					"values " + 
		//					"  (?, sysdate, sysdate + ? / (60 * 24),?,1)",new Object[]{code,code_lose_date,type});
		//			return code;
		//		}
		return "12345678";
	}
	
	/**
	 * 获取验证码拼接失效时间
	 * @description
	 * @author 雷杨 Jun 5, 2014
	 * @param lostTime 失效时间 20140303121111
	 * @return
	 */
	public String getSignCode (String lostTime)
	{
		int o = (int) (Math.random() * 10);
		int t = (int) (Math.random() * 10);
		int h = (int) (Math.random() * 10);
		int f = (int) (Math.random() * 10);
		return o + "" + t + "" + h + "" + f + one[o] + two[t] + three[h] + four[f] + lostTime;
	}
	
	/**
	 * 验证传入值是否是正确
	 * @description
	 * @author 雷杨 Jun 5, 2014
	 * @param code秘钥code
	 * @param encrypt加密内容
	 * @return
	 */
	public boolean checkSignCode (String code)
	{
		try
		{
			char[] codes = code.toCharArray();
			if (code.length() == 8)
			{//只需要验证码有效就行
				if (one[Integer.valueOf(codes[0] + "")].equals(codes[4] + "")
				    && two[Integer.valueOf(codes[1] + "")].equals(codes[5] + "")
				    && three[Integer.valueOf(codes[2] + "")].equals(codes[6] + "")
				    && four[Integer.valueOf(codes[3] + "")].equals(codes[7] + ""))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (code.length() == 22)
			{//需要验证试飞是在有效时间内
				String time = code.substring(8);
				String now_time = DateHelper.getToday("yyyyMMddHHmmss");
				if (Double.valueOf(now_time) - Double.valueOf(time) > 0)
				{
					return false;
				}
				else
				{
					if (one[Integer.valueOf(codes[0] + "")].equals(codes[4] + "")
					    && two[Integer.valueOf(codes[1] + "")].equals(codes[5] + "")
					    && three[Integer.valueOf(codes[2] + "")].equals(codes[6] + "")
					    && four[Integer.valueOf(codes[3] + "")].equals(codes[7] + ""))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			else if (code.length() == 23)
			{//需验验证是否是在有效时间内点击一次
			
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 进行参数验证是否正确
	 * @author 雷杨 2014-12-16
	 * @param msg
	 * @return
	 */
	public static Map<String, String> checkParam (String msg)
	{
		TreeMap<String, String> map = new TreeMap<String, String>();
		try
		{
			JSONObject object = JSONObject.fromObject(msg);
			Iterator<String> i = object.keys();
			while (i.hasNext())
			{
				String key = i.next();
				if (!"code".equals(key))
				{
					map.put(key, object.getString(key));
				}
			}
			String code = object.getString("code");
			String code_ = DESUtil.md5(URLEncoder.encode(new DESUtil(SignUtil.KEY).encryptStr(JSONObject
			        .fromObject(map).toString()), "UTF-8"));
			logger.debug(code + "---" + code_+"=--"+SignUtil.KEY);
			if (!code.equals(code_))
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	
	public static void main (String[] args)
	{
		logger.debug(new SignUtil().getSignCode());
		logger.debug(new SignUtil().checkSignCode(DateHelper.getToday("yyyyMMddHHmmss")) + "--"
		                   + new SignUtil().checkSignCode(DateHelper.getToday("yyyyMMddHHmmss")));
		logger.debug(new SignUtil().getSignCode(DateHelper.getToday("yyyyMMddHHmmss")) + "--"
		                   + new SignUtil().getSignCode(DateHelper.getToday("yyyyMMddHHmmss")).length());
		String sb = "611842213232";
		logger.debug(sb.substring(8, sb.length() - 1));
		logger.debug(new SignUtil().getDbSignCode("gh_bc81796d46b3", 2, 10));
	}
}
