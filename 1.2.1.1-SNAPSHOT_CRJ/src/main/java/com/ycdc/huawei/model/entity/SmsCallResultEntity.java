/**
 * date: 2017年3月31日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: SmsCallResult.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年3月31日
 */
public class SmsCallResultEntity {
	// AEP为每个短消息发送请求分配的唯一ID标识。
	private String smsMsgId;
	// 状态报告枚举值，见状态报告。
	private String status;
	// 状态报告来源：
	// 1：易联自行产生的状态报告
	// 2：南向网元返回的状态报告
	private String source;
	// 短信资源的更新时间，通常为AEP接收到短信状态报告的时间. 格式为：YYYY-MM-DD'T'hh:mm:ss'Z'。
	private String updateTime;
	
	public String getSmsMsgId() {
		return smsMsgId;
	}
	public void setSmsMsgId(String smsMsgId) {
		this.smsMsgId = smsMsgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
