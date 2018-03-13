/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: LockOperator.java
 * @Description: 操作人/受益人/操作对象/操作对象的容器的信息
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class LockOperator {
	/**
	 * 操作人/受益人/操作对象/操作对象的容器 id
	 */
	private String id;
	/**
	 * 操作人/受益人/操作对象/操作对象的容器的名称
	 */
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
