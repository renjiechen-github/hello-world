package com.ycdc.task.serv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ycdc.core.base.BaseService;
import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import com.ycdc.task.dao.DunTaskDAO;
import com.ycdc.task.entitys.Dun;

/**
 * 定时查询所有待付款详情，发送短信进行催租操作
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2017年4月12日上午9:52:27
 */
@Service("dunTaskServ")
public class DunTaskServImpl extends BaseService implements DunTaskServ
{

	@Resource
	private DunTaskDAO dao;
	
	// 查询时间
	private String querytime = "";

	/**
	 * 手工触发催租操作
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public Object artificial(HttpServletRequest request, HttpServletResponse response)
	{
		querytime = getValue(request, "querytime");
		dunTask();
		return "success";
	}

	/**
	 * 定时触发待付款详情
	 */
	public void dunTask()
	{
		if ("".equals(querytime) || "null".equals(querytime))
		{
			querytime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		
		// 查询信息
		List<Dun> info = getDunInfo(querytime);
		logger.info("符合发送短信的待付款信息数量：" + info);

		if (null != info && !info.isEmpty())
		{
			// 发送短信
			smsInfo(info);
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param info
	 */
	private void smsInfo(List<Dun> info)
	{
		// 模板变量及对应值集合
		Map<String, String> map = new HashMap<>();
		// 手机号码
		String phoneNumber = null;
		
		for (Dun dun : info)
		{
			phoneNumber = dun.getMobile();
			if (null == phoneNumber || phoneNumber.equals("") || phoneNumber.equals("null"))
			{
				// 手机号为空				
				continue;
			}
			
			// 租客名称
			map.put("username", dun.getUsername());
			// 合约名称
			map.put("name", dun.getName());
			// 支付时间段
			map.put("remarks", dun.getRemarks());
			// 支付金额
			map.put("cost", dun.getCost());
			
			logger.info("手机号码是：" + phoneNumber + "  map = " + map);
			// 发送短信
			SmsSendMessage.smsSendMessage(phoneNumber, map, SmsSendContants.DUN_NOTIFY);
			
		}

	}

	/**
	 * 查询符合条件的信息
	 * 
	 * @return
	 */
	private List<Dun> getDunInfo(String querytime)
	{
		List<Dun> info = dao.getDunInfo(querytime);
		return info;
	}

}
