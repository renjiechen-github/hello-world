package com.ycdc.bean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

 
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class CglibBean {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(CglibBean.class);
	/**
	  * 实体Object
	  */
	public Object object = null;

	/**
	  * 属性map
	  */
	public BeanMap beanMap = null;

	public CglibBean() {
	  super();
	}

	@SuppressWarnings("unchecked")
	public CglibBean(Map propertyMap) {
	  this.object = generateBean(propertyMap);
	  this.beanMap = BeanMap.create(this.object);
	}

	/**
	  * 给bean属性赋值
	  * @param property 属性名
	  * @param value 值
	  */
	public void setValue(String property, Object value) {
	  beanMap.put(property, value);
	}

	/**
	  * 通过属性名得到属性值
	  * @param property 属性名
	  * @return 值
	  */
	public Object getValue(String property) {
	  return beanMap.get(property);
	}

	/**
	  * 得到该实体bean对象
	  * @return
	  */
	public Object getObject() {
	  return this.object;
	}

	@SuppressWarnings("unchecked")
	private Object generateBean(Map propertyMap) {
	  BeanGenerator generator = new BeanGenerator();
//	  generator.setUseCache(true);
	  Set keySet = propertyMap.keySet();
	  for (Iterator i = keySet.iterator(); i.hasNext();) {
	   String key = (String) i.next();
	   generator.addProperty(key, (Class) propertyMap.get(key));
	  }
	  return generator.create();
	}
	
	public static void main(String[] args) {
		try{
			// 设置类成员属性
			HashMap propertyMap = new HashMap();
			propertyMap.put("id", Class.forName("java.lang.Integer"));
			propertyMap.put("name", Class.forName("java.lang.String"));
			propertyMap.put("address", Class.forName("java.lang.String"));
			// 生成动态 Bean
			CglibBean bean = new CglibBean(propertyMap);

			// 给 Bean 设置值
			bean.setValue("id", new Integer(123));

			bean.setValue("name", "454");

			bean.setValue("address", "789");
			// 从 Bean 中获取值，当然了获得值的类型是 Object
			logger.debug("  >> id      = " + bean.getValue("id"));
			logger.debug("  >> name    = " + bean.getValue("name"));
			logger.debug("  >> address = " + bean.getValue("address"));
			// 获得bean的实体
			Object object = bean.getObject();
			// 通过反射查看所有方法名
			Class clazz = object.getClass();
			logger.debug(clazz.getSimpleName());
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				logger.debug(methods[i].getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
