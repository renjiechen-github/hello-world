/**
 * date: 2017年3月31日
 */
package com.ycdc.huawei.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @name: SmsResponse.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年3月31日
 */
public class SmsResultsEntity {
		// 返回码
		private String code;
		// 返回码描述
		private String description;
		// 下发的短信内容。
		private String body;
		// smsResult结构体，当返回响应出现异常时，不包含此字段。
		private List<SmsResultEntity> result = new ArrayList<SmsResultEntity>();
		
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
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public List<SmsResultEntity> getResult() {
			return result;
		}
		public void setResult(List<SmsResultEntity> result) {
			this.result = result;
		}
		
		public void addResult(SmsResultEntity res) {
			this.result.add(res);
		}

}
