/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.model.entity;

import java.util.Map;

/**
 * @name: LockEvent.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */

public class LockEvent {
	/**
	 * 事件类别 
	 * batteryAlarm 设备电量低于10%告警  detail: { battery_level:50 }
	 * clearBattryAlarm 设备电量高于10%清除告警  detail: 同上
	 * brokenAlarm 门锁被暴力破坏  detail: 无
	 * wrongPwdAlarm 连续三次密码输错报警  detail: 无
	 * pwdSync 管理员密码本地同步  detail: {passwords:[{id: ,password: }]}
	 * pwdAddLocal 本地密码添加操作通知  detail: {password:{id: ,password: }}
	 * pwdDelLocal 本地密码删除操作通知  detail: {password:{id: }}
	 * pwdUpdateLocal 本地密码修改操作通知  detail: {password:{id: ,password: }}
	 * lockerOpenAlarm 开锁事件通知。  detail: {source:2 //开锁方式：2 - 密码开锁, sourcid:1020 //某个开锁方式下的开锁 ID，如密码 ID, source_name:"密码 1020" // 某个开锁方式下的开锁 ID 名称}
	 * centerOfflineAlarm 中心离线通知  detail: 无
	 * lockOfflineAlarm 门锁离线通知  detail: 无
	 * clearCenterOfflineAlarm 中心你上线通知  detail: 无
	 * clearLockOfflineAlarm 门锁上线通知  detail: 无
	 * batteryAsync 设备电量回调  detail: { battery:""}
	 * installerSubmit 安装人员提交公寓  detail: {sp_state:""}
	 */
	private String event;
	/**
	 * 时间戳，单位 ms
	 */
	private String time;
	/**
	 * 设备唯一标识
	 */
	private String uuid;
	/**
	 * 公寓标识
	 */
	private String home_id;
	/**
	 * 房间标识，内门锁才有(可选)
	 */
	private String room_id;
	/**
	 * 设备的昵称
	 */
	private String nickname;
	/**
	 * 通知的详细信息
	 */
	private Map<String, Object> detail;
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Map<String, Object> getDetail() {
		return detail;
	}
	public void setDetail(Map<String, Object> detail) {
		this.detail = detail;
	}
	
}
