package pccom.common.util;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 数据库操作类
 *
 * @author chang
 * @createDate Aug 5, 2013
 * @description
 */
public class DBHelperSpring<T> extends JdbcDaoSupport {

    private static Logger logger = LoggerFactory.getLogger(DBHelperSpring.class);

    public final static int DEFAULT_FETCHSIZE = 32;                                    //默认的fetchsize

    public TransactionTemplate transactionTemplate;

    public LobHandler lobHandler;

    public StringHelper str = new StringHelper();

    public LobHandler getLobHandler() {
        return lobHandler;
    }

    public void setLobHandler(LobHandler lobHandler) {
        this.lobHandler = lobHandler;
    }

    public synchronized static DBHelperSpring getInstance() {
        DBHelperSpring db = null;
        try {
            db = (DBHelperSpring) SpringHelper.getBean("dbHelper");
        } catch (Exception e) {
            logger.error("创建数据操作对象失败！");
        }
        return db;
    }

    public synchronized static Connection getDBConnection() {
        Connection conn = null;
        DBHelperSpring db = DBHelperSpring.getInstance();
        try {
            conn = db.getConnection();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return conn;
    }

    /**
     * 获取报错信息
     *
     * @author 雷杨
     * @date 2015年3月2日下午11:53:20
     * @return
     */
    public void getSqlError(Exception e, String sql, Object[] objects) {
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        for (int i = 0; i < s.length; i++) {
            if (s[i].getClassName().contains("$$") || s[i].getClassName().contains("apache") || s[i].getClassName().contains("DBHelperSpring") || s[i].getClassName().contains("springframework")) {
                continue;
            } else if (s[i].getClassName().contains("com") || s[i].getClassName().contains("web.action") || s[i].getClassName().contains("web.server") || s[i].getClassName().contains("web.mobile")|| s[i].getClassName().contains("web.interfaces")) {
                if (objects == null) {
                    logger.error(e.getMessage() + "[错误引用地址：" + s[i].getClassName() + "." + s[i].getMethodName() + "(" + s[i].getLineNumber() + ")]-" + sql);
                } else {
                    logger.error(e.getMessage() + "[错误引用地址：" + s[i].getClassName() + "." + s[i].getMethodName() + "(" + s[i].getLineNumber() + ")]-" + str.getSql(sql, objects));
                }
                return;
            }
        }
    }

    /**
     * 打印日志
     *
     * @author 雷杨
     * @date 2016年2月23日下午11:09:17
     * @param sql
     * @param objects
     */
    public void getlog(String sql, Object[] objects) {
        try {

//            if (Constants.is_test) {
                StackTraceElement[] s = Thread.currentThread().getStackTrace();
                for (int i = 0; i < s.length; i++) {
                    if (s[i].getClassName().contains("$$") || s[i].getClassName().contains("apache") || s[i].getClassName().contains("DBHelperSpring") || s[i].getClassName().contains("springframework")) {
                        continue;
                    } else if (s[i].getClassName().contains("web.interfaces") ||s[i].getClassName().contains("web.server") || s[i].getClassName().contains("web.action") || s[i].getClassName().contains("web.")) {
                        try {
                            logger.debug("[" + s[i].getClassName() + "." + s[i].getMethodName() + "(" + s[i].getLineNumber() + ")]-执行的sql语句：" + str.getSql(sql, objects));
                        } catch (Exception e) {

                        }
                        return;
                    } 
                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询条件确定时
     *
     * @param sql 传入的sql语句: select *from table where name=? and msisdn like
     * '%?%'
     * @param objects 传入的参数 new Object[] { "张三",1234}
     * @param rowMapper
     * @return
     */
    public Object queryForObject(String sql, Object[] objects, RowMapper<?> rowMapper) {
        Object object = null;
        object = this.getJdbcTemplate().queryForObject(sql, objects, rowMapper);
        return object;
    }

    /**
     * 返回一条记录
     *
     * @param sql 传入的sql语句: select *from table where user_id=?
     * @param objects
     * @return
     */
    public Map<String, Object> queryForMap(String sql, Object[] objects) {
        Map<String, Object> map = null;
        try {
            getlog(sql, objects);
            map = this.getJdbcTemplate().queryForMap(sql, objects);
        } catch (Exception e) {
            this.getSqlError(e, sql, objects);
        }
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        return map;
    }

    public Map<String, Object> queryForMap(String sql) {
        Map<String, Object> map = null;
        try {
            map = this.getJdbcTemplate().queryForMap(sql);
        } catch (Exception e) {
            this.getSqlError(e, sql, null);
        }
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        return map;
    }

    /**
     * 获取某个字段的值
     *
     * @param sql
     * @param args
     * @return
     */
    public String queryForString(String sql, Object[] args) {
        try {
            getlog(sql, args);
            return StringHelper.notEmpty(this.getJdbcTemplate().queryForObject(sql, args, String.class));
        } catch (Exception ex) {
            this.getSqlError(ex, sql, args);
            return "";
        }
    }

    public String queryForString(String sql) {
        try {
            return StringHelper.notEmpty(this.getJdbcTemplate().queryForObject(sql, null, String.class));
        } catch (Exception ex) {
            this.getSqlError(ex, sql, null);
            return "";
        }
    }

    public String queryForString(String sql, List<String> list) {
        try {
            return StringHelper.notEmpty(this.getJdbcTemplate().queryForObject(sql, list.toArray(), String.class));
        } catch (Exception ex) {
            this.getSqlError(ex, sql, list.toArray());
            return "";
        }
    }

    /**
     * 返回一条记录剔除null值
     *
     * @param sql 传入的sql语句: select *from table where user_id=?
     * @param objects
     * @return
     */
    public Map<String, Object> queryForMapNotNull(String sql, Object[] objects) {
        Map<String, Object> map = null;
        Map<String, Object> temp = new HashMap<String, Object>();
        try {
            map = this.getJdbcTemplate().queryForMap(sql, objects);
            if (map != null) {
                Set<String> s = map.keySet();
                for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
                    String key = StringHelper.notEmpty(iter.next()).toString();
                    String value = StringHelper.notEmpty(map.get(key)).toString();
                    temp.put(key, value);
                }
                map.clear();
            }
        } catch (Exception e) {
            this.getSqlError(e, sql, objects);
        }
        return temp;
    }

    /**
     * 返回相应sequence的下一个值
     *
     * @param sequenceName sequence名称
     * @return 下个值
     */
    public String getNextSequenceValue(String sequenceName) {
        Map<String, Object> map = null;
        String nextVal = "";
        try {
            map = this.getJdbcTemplate().queryForMap("select nextval() SEQ from dual");
            nextVal = StringHelper.get(map, "SEQ");
        } catch (Exception e) {
            this.getSqlError(e, "select nextval() SEQ from dual", null);
        }
        return nextVal;
    }

    /**
     * 获取数据库当前时间
     *
     * @param time_formate 日期格式
     * @return
     */
    public String getSysdate(String time_formate) {
        Map<String, Object> map = null;
        String sysdate = "";
        try {
            map = this.getJdbcTemplate().queryForMap(
                    "select to_char(now(),'" + time_formate + "') date_time from dual");
            sysdate = StringHelper.get(map, "DATE_TIME");
        } catch (Exception e) {
            this.getSqlError(e, "select to_char(now(),'" + time_formate + "') date_time from dual", null);
        }
        return sysdate;
    }

    /**
     * 返回数据集
     *
     * @param sql 传入的sql语句: select *from table where user_id=?
     * @param objects
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] objects) {
        logger.debug("执行sql语句："+str.getSql(sql, objects));
        return this.queryForList(sql, objects, DEFAULT_FETCHSIZE);
    }

    /**
     * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
     *
     * @param sql
     * @param list
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, List<String> list) {
        return this.queryForList(sql, list.toArray(), DEFAULT_FETCHSIZE);
    }

    /**
     * 查询条件不确定时返回数据集
     *
     * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql) {
        return this.queryForList(sql, DEFAULT_FETCHSIZE);
    }

    /**
     * 查询条件不确定时返回数据集
     *
     * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
     * @param fetchSize 一次获取的数据条数
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, int fetchSize) {
        JdbcTemplate jdbc = this.getJdbcTemplate();
        jdbc.setFetchSize(fetchSize);

        List<Map<String, Object>> list = null;
        try {
            list = jdbc.queryForList(sql);
        } catch (Exception e) {
            this.getSqlError(e, sql, null);
        }
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        return list;
    }

    /**
     * 返回数据集
     *
     * @param sql 传入的sql语句: select *from table where user_id=?
     * @param objects
     * @param fetchSize
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] objects, int fetchSize) {
        JdbcTemplate jdbc = this.getJdbcTemplate();
        jdbc.setFetchSize(fetchSize);

        List<Map<String, Object>> list = null;
        try {
            getlog(sql, objects);
            list = jdbc.queryForList(sql, objects);
        } catch (Exception e) {
            this.getSqlError(e, sql, objects);
        }
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        return list;
    }

    /**
     * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
     *
     * @param sql
     * @param list
     * @param fetchSize
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, List<String> list, int fetchSize) {
        return this.queryForList(sql, list.toArray(), fetchSize);
    }

    /**
     * 插入获取自增长id,只做insert操作
     *
     * @author 雷杨
     * @param sql
     * @param objects
     * @return -1成功 -2失败
     */
    public int insert(final String sql, final Object[] objects) {
        int exc = -1;
        try {
            getlog(sql, objects);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.getJdbcTemplate().update(new PreparedStatementCreator() {

                @Override
                public java.sql.PreparedStatement createPreparedStatement(Connection con)
                        throws SQLException {
                    java.sql.PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    for (int i = 0; i < objects.length; i++) {
                        ps.setObject(i + 1, objects[i]);
                    }
//                    ps.setInt(1, wsstxContent.getSstxType());
//                    ps.setString(2, wsstxContent.getSstxContent());
//                    ps.setString(3, wsstxContent.getSstxTitle());
//                    ps.setString(4, wsstxContent.getTsTag());
                    return ps;
                }
            }, keyHolder);
            int generatedId = keyHolder.getKey().intValue();
            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            exc = -2;
            this.getSqlError(e, sql, objects);
        }
        return exc;
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        try{
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }catch(NoTransactionException ex){
        }catch(Exception ex ){
            logger.debug("事物回滚出错",ex);
        }
    }
    
    /**
     * insert,update,delete 操作
     *
     * @param sql 传入的语句 sql="insert into tables values(?,?)";
     * @param objects
     * @return 0:失败 1:成功
     */
    public int update(String sql, Object[] objects) {
        int exc = 1;
        try {
            getlog(sql, objects);
            this.getJdbcTemplate().update(sql, objects);
        } catch (Exception e) {
            exc = 0;
            this.getSqlError(e, sql, objects);
        }
        return exc;
    }

    public int update(String sql) {
        int exc = 1;
        try {
            this.getlog(sql, null);
            this.getJdbcTemplate().update(sql);
        } catch (Exception e) {
            exc = 0;
            this.getSqlError(e, sql, null);
        }
        return exc;
    }

    /**
     * 返还记录数
     *
     * @param sql 传入的sql语句 select count(*) from table where name=?
     * @param objects 参数值
     * @return -1:数据库异常
     */
    public int queryForInt(String sql, Object[] objects) {
        int exc = -1;
        try {
            getlog(sql, objects);
            exc = this.getJdbcTemplate().queryForObject(sql, objects, Integer.class);
        } catch (Exception e) {
            exc = -1;
            this.getSqlError(e, sql, objects);
        }
        return exc;
    }

    /**
     * 返还记录数
     *
     * @param sql 传入的sql语句 select count(*) from table where name=?
     * @param list 参数值
     * @return -1:数据库异常
     */
    public int queryForInt(String sql, List<String> list) {
        this.getlog(sql, list.toArray());
        return this.queryForInt(sql, list.toArray());
    }

    /**
     * 返还记录数
     *
     * @param sql 传入的sql语句直接拼接好
     * @return
     */
    public int queryForInt(String sql) {
        this.getlog(sql, null);
        return this.getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    /**
     * 返还记录数--返回记录数超出int范围
     *
     * @param sql 传入的sql语句直接拼接好 sql="select count(*) from table where
     * name='"+mike+"'"
     * @return
     */
    public Long queryForLong(String sql) {
        this.getlog(sql, null);
        return this.getJdbcTemplate().queryForObject(sql, Long.class);
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * 进行批处理，实现原子批处理语句
     *
     * @author 雷杨 2015-03-02
     * @param batch
     * @return
     */
    public T doInTransaction(final Batch<T> batch) {
        T exc = null;
        if (batch == null) {
            return exc;
        }
        try {
            exc = transactionTemplate.execute(new TransactionCallback<T>() {
                public T doInTransaction(final TransactionStatus status) {
                    try {
                        batch.setParams(getJdbcTemplate(), status);
                        batch.excute(getJdbcTemplate(), status);
                        return batch.execute();
                    } catch (final Exception e) {
                        e.printStackTrace();
                        batch.rollBack();
                        batch.getError(e);
                        return null;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            batch.rollBack();
            batch.getError(e);
        }
        return exc;
    }

    /**
     * 事务处理
     *
     * @param batchSqls
     * @return
     */
    public int doInTransaction(final BatchSql batch) {
        int exc = 1;
        if (batch == null) {
            exc = 0;
        }
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    List sqlList = batch.getSqlList();
                    for (int i = 0; i < sqlList.size(); i++) {
                        Map sqlMap = (Map) sqlList.get(i);
                        String sql = (String) sqlMap.get("sql");
                        Object[] objects = (Object[]) sqlMap.get("objects");
                        String sql_type = (String) sqlMap.get("sql_type");
                        // 如果包括大字段操作
                        if (sql_type != null && sql_type.equals("clob")) {
                            int[] colIndex = (int[]) sqlMap.get("clob_index");
                        } else {
                            logger.debug("执行的批处理语句：" + StringHelper.getSql(sql, objects));
                            getJdbcTemplate().update(sql, objects);
                        }
                    }
                }
            });
        } catch (Exception e) {
            exc = 0;
            //logger.error(e);
            this.getSqlError(e, "批处理错误", null);
        }
        return exc;
    }

    /**
     * 过程调用
     *
     * @param sql
     * @param inParam
     * @param out
     * @return
     */
    public ProcHelper getProcHelper(String sql) {
        ProcHelper proc = null;
        try {
            proc = new ProcHelper(this.getDataSource(), sql);
            proc.setSql(sql);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return proc;
    }

}
