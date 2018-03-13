/**
 * 
 */
package com.yc.rm.caas.appserver.base.annotation;

import java.util.List;

import com.yc.rm.caas.appserver.model.base.BaseModel;

/**
 * @author stephen
 * 
 */
public class ApiBean extends BaseModel {
	private String apiName;
	private List<MethodBean> methods;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public List<MethodBean> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodBean> methods) {
		this.methods = methods;
	}
}
