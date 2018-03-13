/*
 * Copyright (c) 2015  南京瑞玥科技有限公司. All Rights Reserved.
 */
package pccom.common.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionStatus;


/**
 * 事物处理机制（实现原子性处理事物）
 * @author 雷杨
 * @date 2015-03-02
 */
public abstract class Batch<T> extends BatchBase<T>
{
	
	private static Logger     logger            = LoggerFactory.getLogger(Batch.class);
	
	private JdbcTemplate      jdbcTemplate;
	
	private TransactionStatus transactionStatus;
	
	public final static int   DEFAULT_FETCHSIZE = 32;                           //默认的fetchsize
	                                                                             
	public JdbcTemplate getJdbcTemplate ()
	{
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate (JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public TransactionStatus getTransactionStatus ()
	{
		return transactionStatus;
	}
	
	public void setTransactionStatus (TransactionStatus transactionStatus)
	{
		this.transactionStatus = transactionStatus;
	}
	
	@Override
	public void setParams (JdbcTemplate jdbc, TransactionStatus status)
	{
		// TODO Auto-generated method stub
		this.setJdbcTemplate(jdbc);
		this.setTransactionStatus(status);
	}
	
	@Override
	public void rollBack ()
	{
		this.getTransactionStatus().setRollbackOnly();
	}
	
	/**
	 * 显示错误信息
	 * @author 雷杨 2015-03-05
	 * @param e
	 * @param sql
	 * @param obj
	 * @throws Exception
	 */
	private void getError (Exception e, String sql, Object[] objects) throws Exception
	{
		logger.error("出错sql语句：" + new StringHelper().getSql(sql, objects) + "\t\n" + this.getError(e));
		throw new Exception("数据库查询出错");
	}
	
	/**
	 * 显示错误信息
	 * @author 雷杨 2015-03-05
	 * @param e
	 * @param sql
	 * @param obj
	 */
	public String getError (Exception e)
	{
		StringBuffer str = new StringBuffer();
		str.append(e.getMessage());
		for (int i = 0; i < e.getStackTrace().length; i++)
		{
			str.append(e.getStackTrace()[i] + "\n");
		}
		return str.toString();
	}
	
	/**
	 * 返回一条记录
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryForMap (String sql, Object[] objects)
	{
		Map<String, Object> map = null;
		try
		{
			getlog(sql, objects);
			map = this.getJdbcTemplate().queryForMap(sql, objects);
		}
		catch (Exception e)
		{
			try
			{
				getError(e, sql, objects);
			}
			catch (Exception e1)
			{
				return new HashMap<String, Object>();
			}
		}
		if (map == null) map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 返回一条记录
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryForMap (String sql) throws Exception
	{
		return this.queryForMap(sql, null);
	}
	
	/**
	 * 获取某个字段的值
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public String queryForString (String sql, Object[] objects)
	{
		try
		{
			getlog(sql, objects);
			return StringHelper.notEmpty(this.getJdbcTemplate().queryForObject(sql, objects, String.class));
		}
		catch (Exception e)
		{
			try
			{
				getError(e, sql, objects);
			}
			catch (Exception e1)
			{
				return "";
			}
			return "";
		}
	}
	
	/**
	 * 获取某个字段的值
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public String queryForString (String sql) throws Exception
	{
		return this.queryForString(sql, null);
	}
	
	/**
	 * 返回相应sequence的下一个值
	 * @param sequenceName sequence名称
	 * @return 下个值
	 */
	public String getNextSequenceValue (String sequenceName) throws Exception
	{
		Map<String, Object> map = null;
		String nextVal = "";
		try
		{
			map = this.getJdbcTemplate().queryForMap("select nextval('" + sequenceName + "') seq");
			nextVal = StringHelper.get(map, "SEQ");
		}
		catch (Exception e)
		{
			getError(e, "select " + sequenceName + ".NEXTVAL SEQ from dual", null);
		}
		return nextVal;
	}
	/**
	 * 打印日志
	 *
	 * @author 雷杨
	 * @date 2016年2月23日下午11:09:17
	 * @param sql
	 * @param objects
	 */
	public void getlog(String sql,Object[] objects){
		try{
			
			if(Constants.is_test){
				StackTraceElement[] s = Thread.currentThread().getStackTrace();
				for(int i=0;i<s.length;i++){
					if(s[i].getClassName().contains("$$")||s[i].getClassName().contains("apache")||s[i].getClassName().contains("DBHelperSpring")||s[i].getClassName().contains("springframework")){
						continue;
					}else if(s[i].getClassName().contains("web.server")||s[i].getClassName().contains("web.action")||s[i].getClassName().contains("web.")){
						try{
							logger.debug("[" + s[i].getClassName() + "." + s[i].getMethodName() + "(" + s[i].getLineNumber() + ")]-执行的sql语句："+StringHelper.getSql(sql, objects));
						}catch(Exception e){
							
						} 
						return;
					}
				}
			}
		
		}catch (Exception e) {
			logger.error("sql日志打印失败:",e);
		} finally {
			String sqlHis = StringHelper.getSql(sql, objects);
			try{
				//插入历史表
				
				String sqlHis_ = sqlHis.toLowerCase();
				if(sqlHis.endsWith("update")||sqlHis.endsWith("delete")){
					SpringHelper.getApplicationContext().getBean("hisTableUtil",HisTableUtil.class).insertHisTable(sqlHis_);
				}
			}catch(Exception e){
				logger.error("历史表插入失败:"+sqlHis, e);
			}
		}
	}
	/**
	 * 返回数据集
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @param fetchSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, Object[] objects, int fetchSize)
	{
		JdbcTemplate jdbc = this.getJdbcTemplate();
		jdbc.setFetchSize(fetchSize);
		List<Map<String, Object>> list = null;
		try
		{
			getlog(sql, objects);
			list = jdbc.queryForList(sql, objects);
		}
		catch (Exception e)
		{
			try
			{
				getError(e, sql, objects);
			}
			catch (Exception e1)
			{
				return new ArrayList<Map<String, Object>>();
			}
		}
		if (list == null) list = new ArrayList<Map<String, Object>>();
		return list;
	}
	
	/**
	 * 查询条件不确定时返回数据集
	 * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
	 * @param fetchSize 一次获取的数据条数
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, int fetchSize) throws Exception
	{
		return this.queryForList(sql, new Object[] {}, fetchSize);
	}
	
	/**
	 * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
	 * @param sql
	 * @param list
	 * @param fetchSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, List<String> list, int fetchSize) throws Exception
	{
		return this.queryForList(sql, list.toArray(), fetchSize);
	}
	
	/**
	 * 返回数据集
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, Object[] objects) throws Exception
	{
		return this.queryForList(sql, objects, DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
	 * @param sql
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, List<String> list) throws Exception
	{
		return this.queryForList(sql, list.toArray(), DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 查询条件不确定时返回数据集
	 * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql) throws Exception
	{
		return this.queryForList(sql, DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 插入获取自增长id,只做insert操作
	 * 
	 * @author 雷杨
	 * @param sql
	 * @param objects
	 * @return -2失败
	 * @throws Exception 
	 */
	public int insert(final String sql, final Object[] objects) throws Exception{
		int exc = -2;
		try
		{
			getlog(sql, objects);
			KeyHolder keyHolder = new GeneratedKeyHolder(); 
			int e = this.getJdbcTemplate().update( new PreparedStatementCreator() {
				 
				@Override
				public java.sql.PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					java.sql.PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					for(int i=0;i<objects.length;i++){
						ps.setObject(i+1, objects[i]);
					}
					
                    return ps;
				}
			} ,keyHolder);
			logger.debug("e:"+e);
			int generatedId = keyHolder.getKey().intValue();
			return generatedId;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			getError(e, sql, objects);
			exc = -2; 
		}
		return exc;
	}
	
	/**
	 * insert,update,delete 操作
	 * @param sql 传入的语句 sql="insert into tables values(?,?)";
	 * @param objects
	 * @return 0:失败 1:成功
	 */
	public int update (String sql, Object[] objects) throws Exception
	{
		int exc = 1;
		try
		{
			getlog(sql, objects);
			this.getJdbcTemplate().update(sql, objects);
		}
		catch (Exception e)
		{
			exc = 0;
			getError(e, sql, objects);
		}
		return exc;
	}
	
	
	/**
	 * insert,update,delete 操作
	 * @param sql 传入的语句 sql="insert into tables values(?,?)";
	 * @param objects
	 * @return 0:失败 1:成功
	 */
	public int update (String sql) throws Exception
	{
		return this.update(sql, null);
	}
	
	/**
	 * 返还记录数
	 * @param sql 传入的sql语句 select count(*) from table where name=?
	 * @param objects 参数值
	 * @return -1:数据库异常
	 */
	public int queryForInt (String sql, Object[] objects)
	{
		int exc = -1;
		try
		{
			getlog(sql, objects);
			exc = this.getJdbcTemplate().queryForObject(sql, objects,Integer.class);
		}
		catch (Exception e)
		{
			try
			{
				getError(e, sql, objects);
			}
			catch (Exception e1)
			{
				return exc;
			}
		}
		return exc;
	}
	
	/**
	 * 返还记录数
	 * @param sql 传入的sql语句 select count(*) from table where name=?
	 * @param objects 参数值
	 * @return -1:数据库异常
	 */
	public int queryForInt (String sql) throws Exception
	{
		return this.queryForInt(sql, null);
	}
	
	/**
	 * @author 雷杨 2015-03-05
	 * @param sql
	 * @return
	 */
	public Long queryForLong (String sql) throws Exception
	{
		return this.queryForLong(sql, null);
	}
	
	/**
	 * @author 雷杨 2015-03-05
	 * @param sql
	 * @return
	 */
	public Long queryForLong (String sql, Object[] objects)
	{
		long exc = -1;
		try
		{
			getlog(sql, objects);
			exc = this.getJdbcTemplate().queryForObject(sql, objects,Long.class);
		}
		catch (Exception e)
		{
			try
			{
				getError(e, sql, objects);
			}
			catch (Exception e1)
			{
				
			}
		}
		return exc;
	}
	
	@Override
	public void excute (JdbcTemplate jdbc, TransactionStatus status)
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 插入历史表记录 表不存在则新建 表中对应的必须增加 his_oper_id,his_oper_date,ip字段 对应的表中对应的必须增加
	 * his_oper_id,his_oper_date,ip字段 历史表名必须是tableName_his格式，如：t_user_his
	 * @author 雷杨
	 * @date 2013-8-15
	 * @param tableName 要插入的表名：
	 * @param owner 数据库用户名
	 * @param id 需要插入历史表中的id值
	 * @param oper_content 操作简单说明
	 * @param params 参数，如果没有传入null
	 * @return
	 * @throws Exception 
	 */
	public void insertTableLog (HttpServletRequest request, String tableName, String id,
	        String oper_content, List<String> params, String oper_id) throws Exception
	{
		String ip = request.getRemoteAddr();
		String sql = "select a.COLUMN_NAME,a.DATA_TYPE,a.CHARACTER_MAXIMUM_LENGTH,a.NUMERIC_PRECISION,a.NUMERIC_SCALE from information_schema.COLUMNS a where UPPER(TABLE_NAME) = UPPER(?) and table_schema = database()";
		List<Map<String, Object>> list = this.queryForList(sql,new Object[]{tableName});
		String tableName_his = tableName + "_his";
		String columns = "";
		for (int i = 0; i < list.size(); i++)
		{
			if (i == list.size() - 1)
			{
				columns += StringHelper.get((Map) list.get(i), "COLUMN_NAME");
			}
			else
			{
				columns += StringHelper.get((Map) list.get(i), "COLUMN_NAME") + ",";
			}
		}
		// logger.debug("columns:"+columns);
		sql = "select a.COLUMN_NAME,a.DATA_TYPE,a.CHARACTER_MAXIMUM_LENGTH,a.NUMERIC_PRECISION,a.NUMERIC_SCALE from information_schema.COLUMNS a where UPPER(TABLE_NAME) = UPPER(?) and table_schema = database()";
		List<Map<String, Object>> his = this.queryForList(sql,new Object[]{tableName_his});
		int exc = his.size();
		if (exc == 0)
		{// 不存在进行新建表
			sql = "create table " + tableName_his +" as select '" + oper_id + "' his_oper_id ,now() his_oper_date,  '" + ip + "' ip," + "'" + oper_content + "' oper_content," + columns + " from "
			      + tableName + " a where 1=1 " + id;
			if (params != null)
			{
				this.update(sql, params.toArray());
			}
			else
			{
				this.update(sql);
			}
			sql = "alter table " + tableName_his + " modify his_oper_id varchar(255)";
			this.update(sql);
			sql = "alter table " + tableName_his + " modify oper_content varchar(255)";
			this.update(sql);
			sql = "alter table " + tableName_his + " modify ip varchar(255)";
			this.update(sql);
		}
		else
		{// 存在进行插入表数据信息
			// 检查历史表中的字段信息与需要插入历史表的字段信息的字段内容是否是一致，如果不一致进行调整
			List<Map<String, Object>> add_list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++)
			{
				Map<String, Object> map = list.get(i);
				String column = StringHelper.get(map, "COLUMN_NAME");
				if (!this.checkHisCol(his, column))
				{
					add_list.add(map);
				}
			}
			for (int i = 0; i < add_list.size(); i++)
			{
				Map<String, Object> map = add_list.get(i);
				String column = StringHelper.get(map, "COLUMN_NAME");
				String data_type = StringHelper.get(map, "DATA_TYPE");
				String char_data_length = StringHelper.get(map, "CHARACTER_MAXIMUM_LENGTH");
				String numeric_precision = StringHelper.get(map, "NUMERIC_PRECISION");
				String numeric_scale = StringHelper.get(map, "NUMERIC_SCALE");
				if ("varchar".equals(data_type))
				{
					column += " varchar(" + char_data_length + ")";
				}
				else if("decimal".equals(data_type))
				{
					column += " decimal(" + numeric_precision + "," + numeric_scale + ")";
				}else{
					column += " " + data_type;
				}
				//alter table MyClass add passtest int(4) default '0';
				sql = "alter table " + tableName_his + " add " + column;
				this.update(sql);
			}
			List<String> param = new ArrayList<String>();
			param.add(oper_id);
			param.add(ip);
			param.add(oper_content);
			if (params != null)
			{
				param.addAll(params);
			}
			sql = "insert into " + tableName_his + "(his_oper_id,his_oper_date,ip,oper_content," + columns
			      + ") select ?,now(),?,?," + columns + " from " + tableName + " a where 1=1 " + id;
			logger.debug("新增数据sql:" + StringHelper.getSql(sql, param.toArray()));
			this.update(sql, param.toArray());
		}
	}
	
	/**
	 * 检查字段在list中是否存在
	 * @description
	 * @author 雷杨 Feb 25, 2014
	 * @param his
	 * @param column
	 * @return
	 */
	private boolean checkHisCol (List<Map<String, Object>> his, String column)
	{
		for (int j = 0; j < his.size(); j++)
		{
			String column_his = StringHelper.get((Map) his.get(j), "COLUMN_NAME");
			if (column.equals(column_his))
			{
				return true;
			}
		}
		return false;
	}
}
