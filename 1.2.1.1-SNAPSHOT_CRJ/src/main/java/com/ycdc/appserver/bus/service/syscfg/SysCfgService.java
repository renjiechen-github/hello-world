package com.ycdc.appserver.bus.service.syscfg;

import com.ycdc.appserver.model.syscfg.CABean;
import com.ycdc.appserver.model.user.User;

/**
 * 公共方法
 * @author suntf
 * @date 2016年12月8日
 */
public interface SysCfgService
{
	/**
	 * 权限验证
	 * @param u
	 * @return
	 */
	String authentorUser(User u, String act);
	
	/**
	 * 网络请求
	 * @param ca
	 * @return
	 */
	String postMessage(CABean ca);
}
