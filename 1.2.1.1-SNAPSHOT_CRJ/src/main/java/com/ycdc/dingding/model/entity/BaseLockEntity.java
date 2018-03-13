/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: BaseLockEntity.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */
public class BaseLockEntity {
	/**
	 * 错误码 0: 成功 其他值:失败
	 */
	private int ErrNo;
	/**
	 * 原因描述
	 */
	private String ErrMsg;
	
	public int getErrNo() {
		return ErrNo;
	}
	public void setErrNo(int errNo) {
		ErrNo = errNo;
	}
	public String getErrMsg() {
		return ErrMsg;
	}
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	/**
	 * @param errNo
	 * @param errMsg
	 */
	public BaseLockEntity(int errNo, String errMsg) {
		super();
		ErrNo = errNo;
		ErrMsg = errMsg;
	}
	
}
