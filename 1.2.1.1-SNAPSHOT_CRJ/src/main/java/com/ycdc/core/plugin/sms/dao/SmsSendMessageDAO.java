package com.ycdc.core.plugin.sms.dao;

import com.ycdc.core.plugin.sms.po.SmsSendMessagePO;

/**
 * 短信发送
 * 
 * @author 孙诚明
 * @since 2016年9月28日
 */
public interface SmsSendMessageDAO
{

	/**
	 * 短信信息入库
	 * 
	 * @param po
	 * @return
	 */
	int insertSmsInfo(SmsSendMessagePO po);

	/**
	 * 判断信息是否存在
	 * 
	 * @param po
	 * @return
	 */
	int isExist(SmsSendMessagePO po);

}
