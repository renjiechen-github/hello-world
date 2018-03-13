/**
 * date: 2017年4月1日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: CallEventInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月1日
 */
public class CallEventInfo {
	// 呼叫唯一标识。
	private String callIdentifier;
	/*
	 * 通知模式：
	 * Notify：App不对呼叫进行控制。
	 * Block：App可以指示小号业务平台做呼叫控制。
	 */
	private String notificationMode;
	/*
	 * 真实主叫号码
	 * 格式：
	 * 手机号码：国家码+手机号，
	 * 固话：国家码+区号+固话号。
	 * 国家码固定86，区号是去0后的号码，例如南京区号是025，填写为25，号码为7位或者8位的固话号码。
	 * 示例：手机号码：8613800000001
	 * 固话：862586321234
	 */
	private String calling;
	// 真实被叫号码 格式同上
	private String called;
	// 虚拟号码。格式：国家码+手机号。示例：8617000000000
	private String virtualNumber;
	/*
	 * 呼叫状态事件，取值如下：
	 * IDP：呼叫开始
	 * Answer：应答
	 * Release：
	 * 呼叫结束包括：通话后主叫挂机、被叫挂机、接续被叫失败或呼叫正常结束，例如被叫无应答、用户费用耗尽、呼叫被其他App结束等。
	 * 固定以“Notify”模式上报到App。
	 * Exception：呼叫过程中发生的异常
	 * 固定以“Notify”模式上报到App。
	 */
	private String event;
	/*
	 * 呼叫事件发生的时间戳。
	 * UTC时间。
	 * 格式：YYYY-MM-DD'T'hh:mm:ss:SSS'Z'
	 * SSS表示毫秒，注意固定字符“T”、“Z”。
	 */
	private String timeStamp;
	/*
	 * 是否录音。
	 * 1：录音
	 * 0：不录音
	 * 只有在notify情况下的release事件，exception事件和answer事件推送isRecord字段。
	 */
	private String isRecord; 
	/*
	 * 扩展呼叫事件信息。（可选）
	 * 如果是Release或者Exception事件，则extInfo的extParas不为空，详见表1-38对应字段描述。
	 */
	private ExtensionInfo extInfo = new ExtensionInfo();
	public String getCallIdentifier() {
		return callIdentifier;
	}
	public void setCallIdentifier(String callIdentifier) {
		this.callIdentifier = callIdentifier;
	}
	public String getNotificationMode() {
		return notificationMode;
	}
	public void setNotificationMode(String notificationMode) {
		this.notificationMode = notificationMode;
	}
	public String getCalling() {
		return calling;
	}
	public void setCalling(String calling) {
		this.calling = calling;
	}
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public String getVirtualNumber() {
		return virtualNumber;
	}
	public void setVirtualNumber(String virtualNumber) {
		this.virtualNumber = virtualNumber;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getIsRecord() {
		return isRecord;
	}
	public void setIsRecord(String isRecord) {
		this.isRecord = isRecord;
	}
	public ExtensionInfo getExtInfo() {
		return extInfo;
	}
	public void setExtInfo(ExtensionInfo extInfo) {
		this.extInfo = extInfo;
	}
	/**
	 * @param callIdentifier
	 * @param notificationMode
	 * @param calling
	 * @param called
	 * @param virtualNumber
	 * @param event
	 * @param timeStamp
	 * @param isRecord
	 * @param extInfo
	 */
	public CallEventInfo(String callIdentifier, String notificationMode, String calling, String called,
			String virtualNumber, String event, String timeStamp, String isRecord, ExtensionInfo extInfo) {
		super();
		this.callIdentifier = callIdentifier;
		this.notificationMode = notificationMode;
		this.calling = calling;
		this.called = called;
		this.virtualNumber = virtualNumber;
		this.event = event;
		this.timeStamp = timeStamp;
		this.isRecord = isRecord;
		this.extInfo = extInfo;
	}
	
}
