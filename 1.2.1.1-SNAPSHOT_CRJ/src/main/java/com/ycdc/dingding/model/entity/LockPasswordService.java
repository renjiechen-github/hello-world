/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.model.entity;

import java.util.Map;

/**
 * @name: LockPasswordService.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */
public class LockPasswordService {
	
	/**
	 * 密码操作类别
	 * Password_Add_Service 增加密码结果通知  result: {"id":"", "ErrNo":""//1000:成功，其他:错误}
	 * Password_Delete_Service 删除密码结果通知  result: {"id":"", "ErrNo":""//1000:成功，其他:错误}
	 * Password_Update_Service 更新密码结果通知  result: {"id":"", "ErrNo":""//1000:成功，其他:错误}
	 * Password_Frozen_Service 冻结密码结果通知  result: {"id":"", "ErrNo":""//1000:成功，其他:错误}
	 * Password_Unfrozen_Service 解冻密码结果通知  result: {"id":"", "ErrNo":""//1000:成功，其他:错误}
	 */
	private String service;
	/**
	 * 服务 id
	 */
	private String serviceid;
	/**
	 * 设备唯一标识
	 */
	private String uuid;
	/**
	 * 密码操作结果详细信息
	 */
	private Map<String, Object> result;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	
}
