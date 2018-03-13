package com.ycdc.appserver.base.token;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;

import pccom.web.TestUrl;

/**
 * token生成规则
 * @author suntf
 * @date 2016年11月25日
 */
public class Token
{
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(Token.class);
	public static Map<String, String> tokenMap = new HashMap<String,String>();
	
	
	public static Object getToken(String key)
	{
		Object obj = tokenMap.get(key);
		return obj;
	}
	
	/**
	 * 生成token 规则id+时间+随机数  md5加密 作为key, 用id作为value
	 * @param id
	 * @param time
	 */
	public static String initToken(String id, String time)
	{
		String key = md5(id+time+new Random().nextInt(1000));
		tokenMap.put(id,key);
		logger.debug("token:"+key);
		return key;
	}
	
	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(String source)
	{

		StringBuffer sb = new StringBuffer(32);

		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(source.getBytes("UTF-8"));

			for (int i = 0; i < array.length; i++)
			{
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
		}
		catch (Exception e)
		{
			return "";
		}
		return sb.toString();
	}
}
