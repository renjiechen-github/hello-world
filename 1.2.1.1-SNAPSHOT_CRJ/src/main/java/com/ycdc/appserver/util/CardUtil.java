
package com.ycdc.appserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

/**
 * @author 冷文佩
 * @since 2017年3月17日
 * @category com.ycdc.appserver.util
 */
/**
 * 身份证信息算法类
 * 
 */
public class CardUtil
{
	/**
	 * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getCarInfo(String CardCode) throws Exception
	{
		Map<String, Object> map = null;
		String year = "";
		String month = "";
		String day = "";
		String usex = "";
		if (CardCode.length() == 15)
		{
			map = new HashMap<String, Object>();
			year = "19" + CardCode.substring(6, 8);// 年份
			month = CardCode.substring(8, 10);// 月份
			day = CardCode.substring(10, 12);// 日
			usex = CardCode.substring(14, 15);// 用户的性别
		}
		else if (CardCode.length() == 18)
		{
			map = new HashMap<String, Object>();
			year = CardCode.substring(6).substring(0, 4);// 得到年份
			month = CardCode.substring(10).substring(0, 2);// 得到月份
			day = CardCode.substring(12).substring(0, 2);// 得到日
			usex = CardCode.substring(16).substring(0, 1);
		}
		String sex;
		Date date = new Date();// 得到当前的系统时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fyear = format.format(date).substring(0, 4);// 当前年份
		String fyue = format.format(date).substring(5, 7);// 月份
		int age = 0;
		if (map != null)
		{
			if (Integer.parseInt(month) <= Integer.parseInt(fyue))
			{ // 当前月份大于用户出身的月份表示已过生
				age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
			}
			else
			{// 当前用户还没过生
				age = Integer.parseInt(fyear) - Integer.parseInt(year);
			}
			map.put("age", age);
			// 出生年月
			map.put("birthday", year + "-" + month + "-" + day);
			if (Integer.parseInt(usex) % 2 == 0)
			{// 判断性别
				sex = "女";
			}
			else
			{
				sex = "男";
			}
			map.put("sex", sex);
		}
		return map;
	}

	public static void main(String[] args)
	{
		String certificateno = "412701199407181522";
		Logger log = org.slf4j.LoggerFactory.getLogger(CardUtil.class);
		Map<String, Object> map=null;
		try
		{
			map = getCarInfo(certificateno);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("error:"+e);
		}
		if(map!=null){
			log.debug("certificateno:"+certificateno+",sex:"+map.get("sex").toString()+",birthday:"+map.get("birthday").toString());
		}
		else
		{
			log.debug("不是身份证！");
		}
	}
}