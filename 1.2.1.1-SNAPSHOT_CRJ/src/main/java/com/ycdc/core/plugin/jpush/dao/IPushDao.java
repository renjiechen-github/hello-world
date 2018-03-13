
package com.ycdc.core.plugin.jpush.dao;

import java.util.List;
import java.util.Map;

import com.ycdc.core.plugin.jpush.controller.fo.JPushFo;
import com.ycdc.core.plugin.jpush.entity.PushMsgBean;

/**
 * 
 * @author 冷文佩
 * @version 1.0
 * @since 2017年3月10日
 * @category com.ycdc.core.plugin.jpush.dao
 * @copyright
 */
public interface IPushDao
{

	int insertJPushMsg(PushMsgBean p);

	List<PushMsgBean> selectPushList(JPushFo jPushFo);

	/**
	 * 查询当前系统环境（IOS(1：正式，2：测试)）
	 * 
	 * @return
	 */
	Map<String, Object> selectSystemConf();
}
