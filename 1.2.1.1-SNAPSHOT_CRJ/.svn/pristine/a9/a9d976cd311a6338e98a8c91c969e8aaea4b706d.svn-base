package pccom.common.util;

import pccom.common.SQLconfig;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;


public class SmsBean {

	private String id;
	private String toMobile;
	private String context;
	private String sendTime;
	private String response;

	public int insertSMS() {
		((DBHelperSpring)SpringHelper.getBean("dbHelper")).update(SQLconfig.getSql("sms.send"),
						new Object[] { this.getToMobile(),
								this.getContext(), this.getSendTime(),
								this.getResponse() });
		
		return 0;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
