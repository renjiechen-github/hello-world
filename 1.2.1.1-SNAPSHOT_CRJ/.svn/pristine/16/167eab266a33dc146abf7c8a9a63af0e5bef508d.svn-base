/**
 * date: 2017年4月18日
 */
package com.ycdc.huawei.model.entity;

import java.util.List;

/**
 * @name: StatusAndFeeResult.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月18日
 */
public class StatusAndFeeResult {
	/*
	 * 事件类型。
	 * callin:呼入事件   
	 * callout:呼出事件   
	 * alerting:振铃事件
	 * answer:应答事件
	 * disconnect:挂机事件   
	 * fee:话单事件
	 */
	private String eventType;
	// 呼叫状态事件内容，但 eventType 为 callin、callout、alerting、 answer、disconnect 时携带。
	private Click2CallStatusInfo statusInfo;
	// 话单列表，最大 50 条。 当 event 为 fee 时携带。
	private List<Click2CallFeeInfo> feeLst;
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Click2CallStatusInfo getStatusInfo() {
		return statusInfo;
	}
	public void setStatusInfo(Click2CallStatusInfo statusInfo) {
		this.statusInfo = statusInfo;
	}
	public List<Click2CallFeeInfo> getFeeLst() {
		return feeLst;
	}
	public void setFeeLst(List<Click2CallFeeInfo> feeLst) {
		this.feeLst = feeLst;
	}
	public void addFeeLst(Click2CallFeeInfo info) {
		this.feeLst.add(info);
	}
	
}
