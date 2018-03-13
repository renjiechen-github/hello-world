/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: RecordQueryResultEntity.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class RecordQueryResultEntity {
	// 返回码。
	private String code;
	// 返回码的描述信息。
	private String description;
	// 号码信息，失败响应中不返回
	private RecordQueryResult result;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RecordQueryResult getResult() {
		return result;
	}
	public void setResult(RecordQueryResult result) {
		this.result = result;
	}
	
}
