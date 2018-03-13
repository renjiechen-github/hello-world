/**
 * date: 2017年4月17日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: FastloginResult.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月17日
 */
public class FastloginResult {
	// 接口调用结果。
		private String resultcode;
		// 结果描述
		private String resultdesc;
		// 访问令牌，调用平台能力接口的通行证
		private String access_token;
		// 刷新令牌，用于访问令牌过期后重新申请访问令牌。
		private String refresh_token;
		// 有效期，访问令牌的有效期，单位为秒。
		private int expires_in;
		public String getResultcode() {
			return resultcode;
		}
		public void setResultcode(String resultcode) {
			this.resultcode = resultcode;
		}
		public String getResultdesc() {
			return resultdesc;
		}
		public void setResultdesc(String resultdesc) {
			this.resultdesc = resultdesc;
		}
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public String getRefresh_token() {
			return refresh_token;
		}
		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}
		public int getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
}
