/*
 * Copyright (c) 2015  . All Rights Reserved.
 */
package pccom.common.util;

/**
 * @author 雷杨
 * @date 2015-04-01
 */
public class LogUtil
{
	/**
	 * 项目名称
	 */
	private static final String SERVER_NAME = "ryweix_server";
	
	/**
	 * 记录错误日志
	 * @author 雷杨 2015-04-01
	 * @param db 数据库
	 * @param className 错误引起的类名
	 * @param detailMsg 错误详细
	 * @param briefMsg 错误简要
	 */
	public static void writerErrorLog (DBHelperSpring db, String className, String detailMsg, String briefMsg)
	{
		String sql = "insert into yc_wx_ERROR_LOG                                "
		             + "  (id, server_name, class_name, detail_msg, oper_date,brief_msg)                                "
		             + "values                                "
		             + "  (nextval(), ?, ?, ?, now(),?)                                 ";
		db.update(sql, new Object[] {SERVER_NAME, className, detailMsg, briefMsg});
	}
}
