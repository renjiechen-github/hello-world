/*
 * Copyright (c) 2015  南京瑞玥科技有限公司. All Rights Reserved.
 */
package pccom.common.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;

/**
 * @author 雷杨
 * @date 2015-03-05
 */
public abstract class BatchBase<T>
{
	/**
	 * 真正执行入口
	 * @author 雷杨 2015-03-05
	 * @throws Exception
	 */
	public abstract T execute () throws Exception;
	
	/**
	 * 真正执行入口
	 * @author 雷杨 2015-03-05
	 * @throws Exception
	 */
	public abstract void excute (JdbcTemplate jdbc, TransactionStatus status);
	
	/**
	 * 设置参数
	 * @author 雷杨 2015-03-05
	 * @param jdbc
	 * @param status
	 */
	public abstract void setParams (JdbcTemplate jdbc, TransactionStatus status) throws Exception;
	
	/**
	 * 进行回滚
	 * @author 雷杨 2015-03-05
	 */
	public abstract void rollBack ();
}
