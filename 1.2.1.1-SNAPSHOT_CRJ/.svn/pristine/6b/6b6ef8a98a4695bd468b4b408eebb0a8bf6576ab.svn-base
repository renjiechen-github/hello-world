package com.room1000.core.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Description: 日志切面
 * 
 * Created on 2017年4月13日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class LoggerAspect4Service {

    /**
     * 
     * Description: 环绕通知
     * 
     * @author jinyanan
     *
     * @param jp jp
     * @return Object
     * @throws Throwable Throwable
     */
    public Object doInMethod(ProceedingJoinPoint jp) throws Throwable {
        Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass());
        String methodName = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        try {
            logger.info(
                methodName + " service begin------------------------------------------------------------------------");
            if (jp.getArgs().length > 0) {
                MethodSignature signature = (MethodSignature) jp.getSignature();
                Method method = signature.getMethod();
                Class<?>[] clazzArr = method.getParameterTypes();
                StringBuilder builder = new StringBuilder();
                builder.append("Parameters: ");
                for (int i = 0; i < jp.getArgs().length; i++) {
                    Class<?> clazz = clazzArr[i];
                    Object args = jp.getArgs()[i];
                    builder.append(clazz.getName() + ": " + args + ", ");
                }
                logger.info(builder.toString().substring(0, builder.length() - 2));
            }

            Object returnValue = jp.proceed();

            if (returnValue != null) {
                logger.debug("return agrs: " + returnValue);
            }
            logger.info(
                methodName + " service end--------------------------------------------------------------------------");
            return returnValue;
        }
        catch (Throwable e) {
            logger.info(methodName + " service throw an " + e.getMessage()
                + " exception-----------------------------------------------------------");
            throw e;
        }
    }
}
