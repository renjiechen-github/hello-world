/**
 * 
 */
package com.yc.rm.caas.appserver.base.annotation;

import com.yc.rm.caas.appserver.model.base.BaseModel;

/**
 * @author stephen
 *
 */
public class MethodBean extends BaseModel{
	private String method;
	private String reqMsg;
	private String rspMsg;
	private String errorCode;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	public String getRspMsg() {
		return rspMsg;
	}
	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
