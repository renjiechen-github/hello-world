package com.ycdc.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 封装签名
 * 
 * @author 孙诚明
 * @date 2017年2月15日
 */
public class SignUtil
{
	/**
	 * 判断签名是否合法
	 * 
	 * @param sPara
	 *          要签名的集合
	 * @param sign
	 *          需要对比的签名字符串
	 * @return
	 */
	public static boolean paramIsLegal(Map<String, String> sPara, String sign)
	{
		// 根据参数获取签名
		String mySign = buildRequestMysign(sPara);
		if (mySign.equals(sign))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *          要签名的集合
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String, String> sPara)
	{
		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = createLinkString(sPara);
		String sign = getEncrypt(prestr, "SHA-1");
		return sign;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *          需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, String> params)
	{

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = params.get(key).trim();
			if (!value.equals("") && !value.equals("null"))
			{
				prestr = prestr + key + "=" + value + "&";
			}
		}
		prestr = prestr.substring(0, prestr.length() - 1);
		return prestr;
	}

	/**
	 * 加密规则
	 * 
	 * @param string
	 *          需要加密的字符串
	 * @param code
	 *          加密规则 MD5、SHA-1
	 * @return
	 */
	public static String getEncrypt(String string, String code)
	{
		try
		{
			if (null == string || "".equals(string.trim()))
			{
				return null;
			}
			
			if (null == code || "".equals(code.trim()))
			{
				return null;
			}
			MessageDigest md = MessageDigest.getInstance(code);
			md.update(string.getBytes());
			return (new sun.misc.BASE64Encoder()).encode(md.digest());
		} catch (Exception e)
		{
			return null;
		}
	}
}
