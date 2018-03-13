/**
 * date: 2017年3月31日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: SmsParams.java
 * @Description: 发送短信参数
 * @author duanyongrui
 * @since: 2017年3月31日
 */
public class SmsSendEntity {
	// 短消息发送方的地址。如果消息中携带多个不同运营商的号码，该字段无需携带。
	private String from;
	// 接收短消息的地址。如果携带多个号码，以英文逗号分隔。每个号码最大长度为21位，最多允许携带1000个号码。号码格式遵循国际电信联盟定义的E.164标准。
	/*
	 * 号码格式遵循国际电信联盟定义的E.164标准。
	 * 标准号码格式为：+{CC}{NDC}{SN}。
	 * {CC}表示国家码。
	 * {NDC}表示地区码，被叫方号码为手机号码时{NDC}可选。
	 * {SN}表用户终端的号码
	 * [示例]+8613112345678
	 * 
	 * 如果+{CC}不存在，则默认的国家码为86。
	 * [示例]13112345678
	*/
	private String to;
	/*
	 * 发送的短消息内容，最大长度2000字节。如果消息长度超过140字节，则可能会被拆分成多条消息发送。
	 * 备注：发送模板短信时无需设置
	 */
	private String body;
	//模板短信ID
	private String templateId;
	/*
	 * 模板短信变量参数，Json字符串。
	 * [示例]{“name”:”Google”,”url”:”www.google.com”}
	 */
	private String templateParams;
	// 应用的回调地址。用于接收短信状态报告。
	// 使用“批量查询状态报告”接口来获取状态报告，该字段无需携带。
	private String statusCallback;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateParams() {
		return templateParams;
	}
	public void setTemplateParams(String templateParams) {
		this.templateParams = templateParams;
	}
	public String getStatusCallback() {
		return statusCallback;
	}
	
	public void setStatusCallback(String statusCallback) {
		this.statusCallback = statusCallback;
	}
	/**
	 * 发送短信使用构造方法
	 * @param from
	 * @param to
	 * @param body
	 * @param statusCallback
	 */
	public SmsSendEntity(String from, String to, String body, String statusCallback) {
		super();
		this.from = from;
		this.to = to;
		this.body = body;
		this.statusCallback = statusCallback;
	}
	/**
	 * 发送模板短信构造方法
	 * @param from
	 * @param to
	 * @param templateId
	 * @param templateParams
	 * @param statusCallback
	 */
	public SmsSendEntity(String from, String to, String templateId, String templateParams, String statusCallback) {
		super();
		this.from = from;
		this.to = to;
		this.templateId = templateId;
		this.templateParams = templateParams;
		this.statusCallback = statusCallback;
	}
	
}
