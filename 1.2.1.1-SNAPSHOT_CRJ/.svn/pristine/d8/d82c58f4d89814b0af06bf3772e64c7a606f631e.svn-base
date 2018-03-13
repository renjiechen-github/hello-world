/**
 * date: 2017年4月1日
 */
package com.ycdc.huawei.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ycdc.huawei.model.entity.NotifyCallEntity;
import com.ycdc.huawei.model.entity.SmsCallResultEntity;
import com.ycdc.huawei.model.entity.StatusAndFeeResult;

/**
 * @name: HuaWeiCallBackDaoImpl.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年4月1日
 */
@Service("huaWeiCallBackService")
public class HuaWeiCallBackServiceImpl implements IHuaWeiCallBackService
{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	// @Autowired
	// private IHuaWeiDao huaWeiDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ycrm.mymaven.login.dao.IHuaWeiCallBackDao#smsCallBack(com.ycrm.mymaven.
	 * login.model.entity.SmsCallResultEntity)
	 */
	@Override
	public void smsCallBack(SmsCallResultEntity result)
	{
		// TODO Auto-generated method stub
		logger.info(result.getSmsMsgId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ycrm.mymaven.login.dao.IHuaWeiCallBackDao#virtualCallBack(com.ycrm.
	 * mymaven.login.model.entity.notifyCallEntity)
	 */
	@Override
	public void virtualCallBack(NotifyCallEntity result)
	{
		// TODO Auto-generated method stub
		logger.info(JSONObject.toJSON(result).toString());
		// huaWeiDao.createCalledEvent(result.getCallEvent());
		logger.info("------------------------------------------------------------------------------------------------------------------------------------");
	}

	public void click2CallBack(StatusAndFeeResult result)
	{
		logger.info(JSONObject.toJSON(result).toString());
		logger.info("------------------------------------------------------------------------------------------------------------------------------------");
	}

}
