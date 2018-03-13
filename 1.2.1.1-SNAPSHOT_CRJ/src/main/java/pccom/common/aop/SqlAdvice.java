package pccom.common.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import pccom.common.util.HisTableUtil;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;

public class SqlAdvice implements MethodInterceptor {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Resource(name="hisTableUtil")
//	public HisTableUtil hisTableUtil; 
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch clock = new StopWatch();
        clock.start(); //计时开始
        Method method = invocation.getMethod();

        String className = method.getDeclaringClass().getSimpleName();
        //监控的方法名  
        Object[] arguments = invocation.getArguments();
        if (method.getName().startsWith("delete") || method.getName().startsWith("update")) {
            try {
                String sql = "";
                Object[] params = null;
                for (int i = 0; i < arguments.length; i++) {
                    if (arguments[i] instanceof String) {
                        sql = String.valueOf(arguments[i]);
                    } else if (arguments[i] instanceof Object[]) {
                        params = (Object[]) arguments[i];
                    }
                }
                String className1 = MDC.get("className");
                String methodName = MDC.get("methodName");
                String userid = MDC.get("userid");
                String requestURL = MDC.get("requestURL");
                String queryString = MDC.get("queryString");
                String remark = MDC.get("remark");
                String zxtype = MDC.get("type");
                if(!"".equals(className1) && className1 != null){
                    logger.debug("className1:"+className1);
                    //打印执行日志
                    logger.info("访问终端："+zxtype+"|执行人："+userid+"｜执行sql语句："+StringHelper.getSql(sql, params)+"|执行说明："+remark+"|执行类："+className1+"【"+methodName+"】"+"|执行链接："+requestURL+"|请求参数："+queryString);
                }
                //update操作中可能会包含insert操作
                if (!sql.toLowerCase().trim().startsWith("insert")) {
                    logger.debug("------------------------------??????????" + method.getName() + "--" + sql + "--" + StringHelper.getSql(sql, params));
                    //执行插入历史表操作
                    SpringHelper.getApplicationContext().getBean("hisTableUtil", HisTableUtil.class).insertHisTable(StringHelper.getSql(sql, params));
                }
            } catch (Exception e) {
                logger.error("执行插入历史记录出现异常：", e);
            }

        }
        return invocation.proceed();
    }

}
