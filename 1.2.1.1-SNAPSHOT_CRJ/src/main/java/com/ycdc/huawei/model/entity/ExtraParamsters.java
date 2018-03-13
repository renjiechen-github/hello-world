/**
 * date: 2017年4月10日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: ExtraParamsters.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月10日
 */
public class ExtraParamsters {
	/* 只有Release时间存在此字段值
	 * 呼叫结束原因。取值如下：
	 * Caller Hang up：主叫挂机
	 * Called Hang up：被叫挂机
	 * Not Reachable：被叫不可达
	 * Route Failure：路由被叫失败
	 * No Answer：被叫无应答
	 * Abandon：主叫放弃
	 * Call Terminated：呼叫被终止（当绑定关系查询不到，会推送该事件）
	 * Call Forbidden：呼叫被禁止，比如被叫位于黑名单中
	 * Busy：被叫忙
	 */
	private String ReleaseReason;
	
	// 标识消息的唯一ID
	private String UniqueId;
	
	// 绑定关系ID
	private String BindID;
	
	/*
	 * 呼叫开始的时间戳。
	 * UTC时间。
	 * 格式：YYYY-MM-DDThh:mm:ss.SSSZ
	 * SSS表示毫秒，最后一位固定为Z
	 */
	private String StartTime;
	
	// 通话时长，单位为秒。
	private String Duration;

	public String getReleaseReason() {
		return ReleaseReason;
	}

	public void setReleaseReason(String releaseReason) {
		ReleaseReason = releaseReason;
	}

	public String getUniqueId() {
		return UniqueId;
	}

	public void setUniqueId(String uniqueId) {
		UniqueId = uniqueId;
	}

	public String getBindID() {
		return BindID;
	}

	public void setBindID(String bindID) {
		BindID = bindID;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	
	/**
	 * 
	 */
	public ExtraParamsters() {
		super();
	}

	/**
	 * @param releaseReason
	 * @param uniqueId
	 * @param bindID
	 * @param startTime
	 * @param duration
	 */
	public ExtraParamsters(String releaseReason, String uniqueId, String bindID, String startTime, String duration) {
		super();
		ReleaseReason = releaseReason;
		UniqueId = uniqueId;
		BindID = bindID;
		StartTime = startTime;
		Duration = duration;
	}
	
}
