
package com.ycdc.core.plugin.jpush;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import pccom.common.util.SpringHelper;
import com.ycdc.bean.NextVal;
import com.ycdc.core.plugin.jpush.entity.PushMsgBean;
import com.ycdc.core.plugin.jpush.serv.IPushServ;

public class PushManagerUtil
{
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(PushManagerUtil.class);
	private static IPushServ pushServ = (IPushServ) SpringHelper.getBean("pushService");

	public static void main(String[] args)
	{
		PushMsgBean pmb = null;
		List<PushMsgBean> list = new ArrayList<PushMsgBean>();
		for (int i = 1; i < 2; i++)
		{
			pmb = new PushMsgBean();
			pmb.setExtrasparam("{extras:extras}");
			if (i % 2 == 0)
			{
				pmb.setAlias("15895880160");
				// pmb.setAlias("18356097560");
				//
			}
			pmb.setMsg("消息555555555！！！" + i);
			pmb.setType(1);
			list.add(pmb);
		}
		logger.debug(pushServ.send(list));
	}

	/**
	 * 发送
	 * 
	 * @param pmb
	 * @return
	 */
	public static String send(PushMsgBean pmb)
	{
		return pushServ.send(pmb);
	};

	/**
	 * 批量发送
	 * 
	 * @param list
	 * @return
	 */
	public static String send(List<PushMsgBean> list)
	{
		return pushServ.send(list);
	};
}
