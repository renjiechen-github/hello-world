/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: RecordQueryEntity.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class RecordQueryEntity {
	// (可选)号码绑定关系的绑定ID。说明：若callIdentifier不携带，则绑定ID不允许不携带，取值不能为空。
	private String subscriptionId;
	/*
	 * 呼叫唯一标识。
	 * 说明
	 * 1.若subscriptionId不携带，则呼叫唯一标识不允许不携带，取值不能为空。
	 * 2.呼叫唯一标识从呼叫通知事件中获取
	 */
	private String callIdentifier;
	/*
	 * 查询条件的起始时间，含该时间点（UTC时间）。即startTime<=呼叫结束时间<= endTime。
	 * 格式：YYYY-MM-DD'T'hh:mm:ss'Z'
	 * 示例：2014-01-07T00:00:00Z
	 */
	private String startTime;
	/*
	 * 查询条件的结束时间，含该时间点（UTC时间）。即startTime<=呼叫结束时间<= endTime。
	 * 格式：YYYY-MM-DD'T'hh:mm:ss'Z'。
	 * 示例：2014-01-08T23:59:59Z
	 */
	private String endTime;
	// (可选)录音列表当前页数，起始值为1。默认值为1。
	private int page = 1;
	// (可选)每个录音列表页面包含的数量。默认值为10，最大值为100。超过100时，则按照100进行处理。
	private int pageSize = 10;
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getCallIdentifier() {
		return callIdentifier;
	}
	public void setCallIdentifier(String callIdentifier) {
		this.callIdentifier = callIdentifier;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
