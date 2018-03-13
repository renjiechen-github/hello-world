package com.ycdc.appserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import pccom.web.server.BaseService;


/**
 * 手机正则校验
 * 
 * @author 孙诚明
 * @date 2017年12月20日
 */
@Component
public class PhoneRegular extends BaseService
{
	/**
	 * 判断手机号是否合法
	 * 
	 * @param phoneNum
	 *          手机号码
	 * @return
	 */
	public boolean phoneNumIsLegal(String phoneNum)
	{
		// 查询手机正则表达式
		String sql = "select a.value from yc_systemparameter_tab a where a.`key`='PHONE_NUMBER_REGULAR'";
		String phoneNumRegular = db.queryForString(sql);
		if (StringUtils.isBlank(phoneNumRegular)) 
		{
			phoneNumRegular = "^((13[0-9])|(14[1,4-8])|(15[0-3,5,7-9])|(16[6])|(17[6-8])|(18[0-9])|(19[8,9]))\\d{8}$";
		}
		Pattern pattern = Pattern.compile(phoneNumRegular);
		Matcher matcher = pattern.matcher(phoneNum);
		if (matcher.matches())
		{
			// 匹配，返回true
			return true;
		}
		else
		{
			return false;
		}
	}
}
