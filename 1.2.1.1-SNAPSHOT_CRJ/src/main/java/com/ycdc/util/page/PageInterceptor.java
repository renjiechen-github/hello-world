package com.ycdc.util.page;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;

import pccom.common.util.HisTableUtil;
import pccom.common.util.SpringHelper;

/**
 *
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 利用拦截器实现Mybatis分页的原理：
 * 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句
 * 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。在Mybatis中Statement语句是通过RoutingStatementHandler对象的
 * prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用
 * StatementHandler对象的prepare方法，即调用invocation.proceed()。
 * 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设
 * 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。
 *
 */
@Intercepts({
    @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})
public class PageInterceptor implements Interceptor {

    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(PageInterceptor.class);
    private String databaseType;//数据库类型，不同的数据库有不同的分页方法

    /**
     * 拦截后要执行的方法
     */
    public Object intercept(Invocation invocation) throws Throwable {
        //对于StatementHandler其实只有两个实现类，一个是RoutingStatementHandler，另一个是抽象类BaseStatementHandler，
        //BaseStatementHandler有三个子类，分别是SimpleStatementHandler，PreparedStatementHandler和CallableStatementHandler，
        //SimpleStatementHandler是用于处理Statement的，PreparedStatementHandler是处理PreparedStatement的，而CallableStatementHandler是
        //处理CallableStatement的。Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler，而在RoutingStatementHandler里面拥有一个
        //StatementHandler类型的delegate属性，RoutingStatementHandler会依据Statement的不同建立对应的BaseStatementHandler，即SimpleStatementHandler、
        //PreparedStatementHandler或CallableStatementHandler，在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。
        //我们在PageInterceptor类上已经用@Signature标记了该Interceptor只拦截StatementHandler接口的prepare方法，又因为Mybatis只有在建立RoutingStatementHandler的时候
        //是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        //通过反射获取到当前RoutingStatementHandler对象的delegate属性
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        //获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
        //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
        BoundSql boundSql = delegate.getBoundSql();
        //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        Object obj = boundSql.getParameterObject();
        logger.debug("obj instanceof Map:" + (obj instanceof Map));
        if (obj instanceof Map) {
            Map<String, Object> mapSql = (Map<String, Object>) obj;
            logger.debug(mapSql.containsKey("page") + "--" + mapSql);
            //这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
            if (mapSql.containsKey("page")) {
                Page<?> page = (Page<?>) mapSql.get("page");
                //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
                MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                //拦截到的prepare方法参数是一个Connection对象
                Connection connection = (Connection) invocation.getArgs()[0];
                //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
                String sql = boundSql.getSql();
                logger.debug("page.getSizelength():" + page.getSizelength());
                if (("".equals(page.getSizelength()) || "1".equals(page.getIsCxSeach()))) {//
                    //给当前的page参数对象设置总记录数
                    this.setTotalRecords(page, mappedStatement, connection, obj);
                } else {
                    page.setTotalRecord(Integer.valueOf(page.getSizelength()));
                }

                //获取分页Sql语句
                String pageSql = this.getPageSql(page, sql);
                logger.debug("---" + pageSql);
                //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
                ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
            }
        } else {//检查是否在该对象中存在对应的page方法
            if (obj != null) {
                Class c = obj.getClass();
                Method[] method = c.getMethods();
                Object o = null;
                for (Method m : method) {
                    //logger.debug("--111111111111111111111111111111111111---"+m.getName());
                    if (m.getName().equals("getMap")) {
                        o = m.invoke(obj);
                    }
                }
                if (o != null) {
                    Map<String, Object> map = (Map<String, Object>) o;
                    if (map.containsKey("page_")) {
                        Page<?> page = (Page<?>) map.get("page_");
                        //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
                        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                        //拦截到的prepare方法参数是一个Connection对象
                        Connection connection = (Connection) invocation.getArgs()[0];
                        //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
                        String sql = boundSql.getSql();
                        if (("".equals(page.getSizelength()) || "1".equals(page.getIsCxSeach()))) {//
                            //给当前的page参数对象设置总记录数
                            this.setTotalRecords(page,
                                    mappedStatement, connection, obj);
                        } else {
                            page.setTotalRecord(Integer.valueOf(page.getSizelength()));
                        }

                        //获取分页Sql语句
                        String pageSql = this.getPageSql(page, sql);
                        //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
                        ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
                    }
                }
            }
        }

        //获取拼接后的sql语句
        String sql = getYsSql(invocation);
        try {
            if (!"".equals(sql)) {
                String sql_ = sql.toLowerCase().trim();
                if (sql_.startsWith("update") || sql_.startsWith("delete")) {
                    SpringHelper.getApplicationContext().getBean("hisTableUtil", HisTableUtil.class).insertHisTable(sql);
                }
            }
        } catch (Exception e) {
            logger.debug("插入历史表出现异常：" + sql, e);
        }
        return invocation.proceed();
    }

    /**
     * 获取执行的sql语句
     *
     * @param invocation
     */
    public String getYsSql(Invocation invocation) {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //通过反射获取到当前RoutingStatementHandler对象的delegate属性
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = statementHandler.getBoundSql();
        Configuration configuration = mappedStatement.getConfiguration();
        return showSql(configuration, boundSql);
    }

    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ").trim();
        if (!sql.toLowerCase().endsWith("update") && !sql.toLowerCase().endsWith("delete")) {
            return "";
        }
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                String value = getParameterValue(parameterObject);
                if (value == null || "".equals(value)) {
                    value = "null";
                }
                sql = sql.replaceFirst("\\?", value);
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        String value = getParameterValue(obj);
                        if (value == null || "".equals(value)) {
                            value = "null";
                        }
                        sql = sql.replaceFirst("\\?", value);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        String value = getParameterValue(obj);
                        if (value == null || "".equals(value)) {
                            value = "null";
                        }
                        sql = sql.replaceFirst("\\?", value);
                    }
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    /**
     * 拦截器对应的封装原始对象的方法
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置注册拦截器时设定的属性
     */
    public void setProperties(Properties properties) {
        this.databaseType = properties.getProperty("databaseType");
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
     *
     * @param page 分页对象
     * @param sql 原sql语句
     * @return
     */
    private String getPageSql(Page<?> page, String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if ("mysql".equalsIgnoreCase(databaseType)) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if ("oracle".equalsIgnoreCase(databaseType)) {
            return getOraclePageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }

    /**
     * 获取Mysql数据库的分页查询语句
     *
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
        //计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        int offset = (page.getPageNo() - 1) * page.getPageSize();

        //添加排序信息
        if (!page.getiSortCol().equals("")) {
            if (page.getbSortable().equals("false")) {//没有排序

            } else {
                sqlBuffer.append(" order by  ").append(page.getmDataProp()).append(" ").append(page.getsSortDir_0());
            }
        }
        logger.debug(page.getiSortCol() + "1---21--" + page.isExp());
        if (!page.isExp()) {//不是导出操作
            logger.debug(page.getiDisplayLength() + "|" + page.getiDisplayLength() + "|" + page.getTotalRecord());
            if (!"".equals(page.getiDisplayLength()) && Integer.valueOf(page.getiDisplayLength()) < Integer.valueOf(page.getTotalRecord())) {//当总长度大于分页数量可进行分页
                sqlBuffer.append("  limit ").append(page.getiDisplayStart()).append(",").append(page.getiDisplayLength());
            }
        }
//       logger.debug(sqlBuffer);
        return sqlBuffer.toString();
    }

    /**
     * 获取Oracle数据库的分页查询语句
     *
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(Page<?> page, StringBuffer sqlBuffer) {
        //计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
        sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
        //上面的Sql语句拼接之后大概是这个样子：
        //select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16
        return sqlBuffer.toString();
    }

    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private int setTotalRecord(Page<?> page,
            MappedStatement mappedStatement, Connection connection, Object obj, String countSql) {
        int size = -1;
        //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
        //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
        BoundSql boundSql = mappedStatement.getBoundSql(obj);
        //获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql = boundSql.getSql();
        //通过查询Sql语句获取到对应的计算总记录数的sql语句
//       String countSql = this.getCountSql(sql);
        //logger.debug("-----------------"+boundSql.getParameterObject());
        //通过BoundSql获取对应的参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //logger.debug(parameterMappings);
        //利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, obj);
        //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, obj, countBoundSql);
        //通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            //通过parameterHandler给PreparedStatement对象设置参数
            parameterHandler.setParameters(pstmt);
            //之后就是执行获取总记录数的Sql语句和获取结果了。
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                //给当前的参数page对象设置总记录数
                size = totalRecord;
//              page.setTotalRecord(totalRecord);
//              page.setSizelength(totalRecord+"");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    public int setTotalRecords(Page<?> page,
            MappedStatement mappedStatement, Connection connection, Object obj) {
        int size = -1;
        //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
        //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
        BoundSql boundSql = mappedStatement.getBoundSql(obj);
        //获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql1 = boundSql.getSql();
        try {
            CCJSqlParserManager parser = new CCJSqlParserManager();
            Statement stmt = parser.parse(new StringReader(sql1));
            if (stmt instanceof Select) {
                Select selectStatement = (Select) stmt;
                SelectBody selectBody = selectStatement.getSelectBody();
                PlainSelect plainSelect = (PlainSelect) selectBody;
                Distinct distinct = plainSelect.getDistinct();
                List<Expression> groupby = plainSelect.getGroupByColumnReferences();
                if (distinct != null || groupby != null) {
                    size = setTotalRecord(page, mappedStatement, connection, obj, "select count(1) from (" + sql1 + ") ccc");
                } else {
                    FromItem fromItem = plainSelect.getFromItem();
                    StringBuilder sqlStb = new StringBuilder();
                    sqlStb.append("SELECT 1");
                    if (fromItem != null) {
                        sqlStb.append(" FROM ").append(fromItem);
                        List<Join> joins = plainSelect.getJoins();
                        if (joins != null) {
                            Iterator<Join> it = joins.iterator();
                            while (it.hasNext()) {
                                Join join = it.next();
                                if (join.isSimple()) {
                                    sqlStb.append(", ").append(join);
                                } else {
                                    sqlStb.append(" ").append(join);
                                }
                            }
                        }
                        Expression where = plainSelect.getWhere();
                        if (where != null) {
                            sqlStb.append(" WHERE ").append(where);
                        }
                    }
                    String sql2 = sqlStb.toString();
                    size = setTotalRecord(page, mappedStatement, connection, obj, "select count(1) from (" + sql2 + ") ccc");
                }
            }
        } catch (JSQLParserException ex) {
            logger.error("分页查询总条数错误,该语句可能过于复杂，sql解析器解析不成功，建议优化处理：：" + sql1);
            //将sql进行拆分
            String[] sqlfrom = sql1.split("[fF][rR][oO][mM]");
            String sql2 = "";
            page.setSizelength("");
            //logger.debug("原始sql1:"+sql1);
            for (int i = 0; i < sqlfrom.length - 1; i++) {
                //logger.debug("--1-"+sqlfrom[i]);
                if (!"".equals(page.getSizelength())) {
                    logger.debug("没有走查询，直接走的缓存");
                    continue;
                }
                sql2 = "select 1  ";
                for (int j = i + 1; j < sqlfrom.length; j++) {
                    sql2 += " from " + sqlfrom[j];
                }
                //"select count(1) from ("+sql2+") ccc"
                size = setTotalRecord(page, mappedStatement, connection, obj, "select count(1) from (" + sql2 + ") ccc");
            }
        }
        if (size != -1) {
            page.setTotalRecord(size);
            page.setSizelength(size + "");
        }
        return size;
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        int index = sql.indexOf("from");
        return "select count(1) " + sql.substring(index);
    }

    /**
     * 利用反射进行操作的一个工具类
     *
     */
    private static class ReflectUtil {

        /**
         * 利用反射获取指定对象的指定属性
         *
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         *
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         *
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName,
                String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
