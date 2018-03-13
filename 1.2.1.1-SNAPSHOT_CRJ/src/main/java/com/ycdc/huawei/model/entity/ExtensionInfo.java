/**
 * date: 2017年4月1日
 */
package com.ycdc.huawei.model.entity;


/**
 * @name: ExtensionInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月1日
 */
public class ExtensionInfo {
	// 信令中的主叫号码。
	private String rawCalling;
	/*
	 * 信令中主叫号码的号码属性，取值如下：
	 * 1：SUBSCRIBER
	 * 2：UNKNOWN
	 * 4：INTERNATIONAL
	 * 3：其他
	 */
	private String rawCallingNOA;
	// 信令中被叫号码的号码属性，取值同上。
	private String rawCalledNOA;
	// 信令中的被叫号码。
	private String rawCalled;
	// 扩展信息(Key-Value)列表。（可选）
	/*
	 * Exception:
	 * 1.Duration 通话时长。单位：秒
	 * 2.UniqueId 标识消息的唯一ID
	 * 3.BindID 绑定关系ID
	 * 4.StartTime 呼叫开始的时间戳。UTC时间。格式：YYYY-MM-DD'T'hh:mm:ss:SSS'Z'。SSS表示毫秒，最后一位固定为Z
	 * Release:
	 * 1.ReleaseReason 呼叫结束原因。取值：
	 *   • Caller Hang up：主叫挂机
	 *   • Called Hang up：被叫挂机
	 *   • Not Reachable：被叫不可达
	 *   • Route Failure：路由被叫失败
	 *   • No Answer：被叫无应答
	 *   • Abandon：主叫放弃
	 *   • Call Terminated：呼叫被终止（当绑定关系查询不到，会推送该事件）
	 *   • Call Forbidden：呼叫被禁止，比如被叫位于黑名单中
	 *   • Busy：被叫忙
	 * 2.其他字段同Exception。
	 */
	private ExtraParamsters extParas = new ExtraParamsters();
	public String getRawCalling() {
		return rawCalling;
	}
	public void setRawCalling(String rawCalling) {
		this.rawCalling = rawCalling;
	}
	public String getRawCallingNOA() {
		return rawCallingNOA;
	}
	public void setRawCallingNOA(String rawCallingNOA) {
		this.rawCallingNOA = rawCallingNOA;
	}
	public String getRawCalledNOA() {
		return rawCalledNOA;
	}
	public void setRawCalledNOA(String rawCalledNOA) {
		this.rawCalledNOA = rawCalledNOA;
	}
	public String getRawCalled() {
		return rawCalled;
	}
	public void setRawCalled(String rawCalled) {
		this.rawCalled = rawCalled;
	}
	public ExtraParamsters getExtParas() {
		return extParas;
	}
	public void setExtParas(ExtraParamsters extParas) {
		this.extParas = extParas;
	}
	
	/**
	 * 
	 */
	public ExtensionInfo() {
		super();
	}
	/**
	 * @param rawCalling
	 * @param rawCallingNOA
	 * @param rawCalledNOA
	 * @param rawCalled
	 * @param extParas
	 */
	public ExtensionInfo(String rawCalling, String rawCallingNOA, String rawCalledNOA, String rawCalled,
			ExtraParamsters extParas) {
		super();
		this.rawCalling = rawCalling;
		this.rawCallingNOA = rawCallingNOA;
		this.rawCalledNOA = rawCalledNOA;
		this.rawCalled = rawCalled;
		this.extParas = extParas;
	}
	
	
}
