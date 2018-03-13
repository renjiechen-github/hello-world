package com.yc.rm.caas.appserver.model;

import java.lang.reflect.Field;

public class SuperObject {
	protected String toString(Object obj) {
		StringBuffer sb = new StringBuffer(obj.getClass().getName());
		sb.append("{");
		Field fields[] = obj.getClass().getDeclaredFields();// 获得对象所有属性
		Field field = null;
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);// 修改访问权限
			try {
				sb.append("\'").append(field.getName()).append("\':\'").append(field.get(obj)).append("\'");
				if (i != fields.length - 1) {
					sb.append(",");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} // 读取属性值
		}
		sb.append("}");
		return sb.toString();
	}
}
