/**
 * date: 2017年3月31日
 */
package com.ycdc.huawei.service;

import com.ycdc.huawei.model.entity.SmsCallResultEntity;
import com.ycdc.huawei.model.entity.StatusAndFeeResult;
import com.ycdc.huawei.model.entity.NotifyCallEntity;

/**
 * @name: HuaWeiCallBackDao.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年3月31日
 */
public interface IHuaWeiCallBackService
{

	/**
	 * 获取发送短信回调信息
	 * 
	 * @param result
	 *          回调信息
	 * @Description: statusCallback参数获取的回调信息
	 */
	public void smsCallBack(SmsCallResultEntity result);

	/**
	 * 拨号回调接口
	 * 
	 * @param entity
	 *          回调信息
	 * @Description:
	 */
	public void virtualCallBack(NotifyCallEntity entity);

	public void click2CallBack(StatusAndFeeResult result);
}
