/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

import java.util.List;

/**
 * @name: RecordQueryResult.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class RecordQueryResult {
	// 符合条件的总记录数
	private int total;
	// 当页的录音列表，total=0则不返回。
	private List<RecordInfo> record;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<RecordInfo> getRecord() {
		return record;
	}
	public void setRecord(List<RecordInfo> record) {
		this.record = record;
	}
	public void addRecord(RecordInfo rec) {
		this.record.add(rec);
	}
}
