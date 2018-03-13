/**
 * date: 2017年7月25日
 */
package com.yc.rm.caas.appserver.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.yc.rm.caas.appserver.model.privilege.PublicRoleBean;

/**
 * @name: RoleManagerPostProcessor.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年7月25日
 */
public class RoleManagerPostProcessor implements BeanPostProcessor
{

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException
	{
		// TODO Auto-generated method stub
		if(arg0 instanceof PublicRoleBean) {  
            ((PublicRoleBean)arg0).initRoleMap(); //调用方法加载数据  
        }
		return arg0;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException
	{
		// TODO Auto-generated method stub
		return arg0;
	}

}
