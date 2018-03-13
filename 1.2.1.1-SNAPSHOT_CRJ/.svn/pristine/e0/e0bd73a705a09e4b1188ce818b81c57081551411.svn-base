package com.ycdc.core.plugin.jpush.serv;

import java.util.List;

import com.ycdc.core.plugin.jpush.controller.fo.JPushFo;
import com.ycdc.core.plugin.jpush.entity.PushMsgBean;

public interface IPushServ {
	/**
	 * 发送
	 * 
	 * @param pmb
	 * @return
	 */
	public String send(PushMsgBean pmb);

	/**
	 * 批量发送
	 * 
	 * @param list
	 * @return
	 */
	public String send(List<PushMsgBean> list);
	
	/**
	 * 展示推送信息列表
	 * @param jPushFo 
	 * 
	 * @return
	 */
	List<PushMsgBean> selectPushList(JPushFo jPushFo);
}
