package com.ycdc.bean;

import java.io.Serializable;

import org.slf4j.Logger;

public class NextVal implements Serializable {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(NextVal.class);
	public Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void test(Object... params){
		Object o = params[0];
		logger.debug(o.toString());
	}
	
	public static void main(String[] args) {
		NextVal nextVal = new NextVal();
		String name = "1";
		nextVal.test(name);
	}
	
}
