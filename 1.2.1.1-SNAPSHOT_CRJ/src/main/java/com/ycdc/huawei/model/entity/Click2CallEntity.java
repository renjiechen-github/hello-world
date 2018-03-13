/**
 * date: 2017年4月17日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: Call2CallEntity.java
 * @Description: 双呼入参实体
 * @author duanyongrui
 * @since: 2017年4月17日
 */
public class Click2CallEntity {
	/*
	 * APP 绑定的呼叫业务号码，需要 SP 在开发者 Portal 中为 APP 申 请。
	 * 号码仅支持全局号码格式，比如 +8613866887021。
	 */
	private String bindNbr;
	/*
	 * 主显号码，被叫终端上显示的主
	 * 叫号码。
	 * 填写 MSISDN 的值包含国家码 如: +8613423222222。
	 * 主显号码需先通过运营商审核。
	 * 如果不填，系统默认使用 bindNbr 当该主显。
	 */
	private String displayNbr;
	// 主叫号码。
	private String callerNbr;
	/*
	 * 拨打被叫号码时的主显号码。
	 * 如果不填，系统默认使用
	 * callerNbr 当该主显。
	 * 如果不是 callerNbr，则该主显号 码需先通过运营商审核。
	 */
	private String displayCalleeNbr;
	/*
	 * 被叫号码。
	 * 填写 MSISDN 的值包含国家码 如: +8613423222222
	 */
	private String calleeNbr;
	/*
	 * 单位为分钟。
	 * 允许的最大通话分钟数，指这次通话最多能进行多长时间的通话。
	 * 通话时间的计算是从被叫接通的时刻开始计算。
	 * 取值范围:
	 * 0:系统不主动结束通话;
	 * 1~1440:系统主动结束通话。 最大值 1440 分(即 24 小 时)。
	 */
	private Integer maxDuration = 0;
	/*
	 * 最后一分钟放音提示音的放音文件名。
	 * maxDuration 非 0 时有效。 不带，则使用默认系统音，内
	 * 容:“本次通话时长还剩 1 分 钟”。
	 */
	private String lastMinVoice;
	/*
	 * 最后一分钟放音的播放对象。 
	 * toa:对第一个用户放; 
	 * tob:对第二个用户放; 
	 * both:两个用户同时放。 
	 * 默认为 both。
	 */
	private String lastMinToUE;
	/*
	 * 主叫应答后的点击拨号等待音的 放音文件名，需要 SP 在开发者 Portal 中提前上传。
	 * 系统会边给主叫放音(循环放音)边拨打被叫用户;被叫应答才中止放音。
	 */
	private String waitVoice;
	/*
	 * 被叫的早期媒体音播放方式:
	 * all:透传被叫所有放音。被叫返回 振铃音等所有早期媒体音，则终 止主叫的等待音，播放被叫的早 期媒体音，如彩铃音等。
	 * none:不透传被叫所有放音，一直 播放主叫的等待音，直到被叫应 答或挂机。
	 * fail:只有在被叫回失败放音时 (带 reason 原因值)，才终止主叫的等待音、播放被叫的失败放音。
	 * 默认为:all
	 */
	private String calleeMedia;
	/*
	 * SP 用于接收状态上报的 URL; 
	 * 点击类业务触发过程中通话的状 态信息 CaaS 平台会推送至此服 务器地址;此字段要求进行 BASE64 编码。
	 * 最大 128 字节。
	 */
	private String statusUrl;
	// SP 用于接收话单上报的 URL;其他同 接收状态上报
	private String feeUrl;
	// 是否录音，默认 false true:录音
	private String recordFlag = "true";
	// 录音提示音的放音文件名。此参数在 recordFlag 为 true 时才 有效。
	private String recordHintTone;
	/*
	 * 用户附属信息，此标识由第三方
	 * 服务器定义，会在后续的通知消
	 * 息中携带此信息。
	 * 最大 128 字节。
	 */
	private String userData;

	public String getBindNbr() {
		return bindNbr;
	}
	public void setBindNbr(String bindNbr) {
		this.bindNbr = bindNbr;
	}
	public String getDisplayNbr() {
		return displayNbr;
	}
	public void setDisplayNbr(String displayNbr) {
		this.displayNbr = displayNbr;
	}
	public String getCallerNbr() {
		return callerNbr;
	}
	public void setCallerNbr(String callerNbr) {
		this.callerNbr = callerNbr;
	}
	public String getDisplayCalleeNbr() {
		return displayCalleeNbr;
	}
	public void setDisplayCalleeNbr(String displayCalleeNbr) {
		this.displayCalleeNbr = displayCalleeNbr;
	}
	public String getCalleeNbr() {
		return calleeNbr;
	}
	public void setCalleeNbr(String calleeNbr) {
		this.calleeNbr = calleeNbr;
	}
	public Integer getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}
	public String getLastMinVoice() {
		return lastMinVoice;
	}
	public void setLastMinVoice(String lastMinVoice) {
		this.lastMinVoice = lastMinVoice;
	}
	public String getLastMinToUE() {
		return lastMinToUE;
	}
	public void setLastMinToUE(String lastMinToUE) {
		this.lastMinToUE = lastMinToUE;
	}
	public String getWaitVoice() {
		return waitVoice;
	}
	public void setWaitVoice(String waitVoice) {
		this.waitVoice = waitVoice;
	}
	public String getCalleeMedia() {
		return calleeMedia;
	}
	public void setCalleeMedia(String calleeMedia) {
		this.calleeMedia = calleeMedia;
	}
	public String getStatusUrl() {
		return statusUrl;
	}
	public void setStatusUrl(String statusUrl) {
		this.statusUrl = statusUrl;
	}
	public String getFeeUrl() {
		return feeUrl;
	}
	public void setFeeUrl(String feeUrl) {
		this.feeUrl = feeUrl;
	}
	public String getRecordFlag() {
		return recordFlag;
	}
	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}
	public String getRecordHintTone() {
		return recordHintTone;
	}
	public void setRecordHintTone(String recordHintTone) {
		this.recordHintTone = recordHintTone;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	/**
	 * @param bindNbr
	 * @param callerNbr
	 * @param calleeNbr
	 */
	public Click2CallEntity(String bindNbr, String callerNbr, String calleeNbr) {
		super();
		this.bindNbr = bindNbr;
		this.callerNbr = callerNbr;
		this.calleeNbr = calleeNbr;
	}
	
	
}
