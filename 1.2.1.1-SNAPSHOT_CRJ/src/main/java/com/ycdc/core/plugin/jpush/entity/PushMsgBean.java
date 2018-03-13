package com.ycdc.core.plugin.jpush.entity;

public class PushMsgBean {

	/**
	 * 内部员工
	 */
	public static final int INNER_STAFF = 0;
	/**
	 * 外部客户
	 */
	public static final int OUTER_USER = 1;
	private int id; // 消息id
	private String alias = "-1";// 用户ID，发送给全网用户时为-1
	private int type;// 用户类型 0:内部员工，1：外部客户
	private String msg = "";// 消息
	private String delayDate = "";// 实时发送时间为空，延迟为想要发送成功的时间
	private String extrasparam = "";// 模块参数
	private int ifSend;// 发送结果 0：失败，1：成功
	private String name = "";// 用户名称，发送全网用户时为空
	private String model = "";// 操作模块

	public int getId() {
		return id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getExtrasparam() {
		return extrasparam;
	}

	public void setExtrasparam(String extrasparam) {
		this.extrasparam = extrasparam;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDelayDate() {
		return delayDate;
	}

	public void setDelayDate(String delayDate) {
		this.delayDate = delayDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIfSend() {
		return ifSend;
	}

	public void setIfSend(int ifSend) {
		this.ifSend = ifSend;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PushMsgBean [id=");
		builder.append(id);
		builder.append(", alias=");
		builder.append(alias);
		builder.append(", type=");
		builder.append(type);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", delayDate=");
		builder.append(delayDate);
		builder.append(", extrasparam=");
		builder.append(extrasparam);
		builder.append(", ifSend=");
		builder.append(ifSend);
		builder.append(", name=");
		builder.append(name);
		builder.append(", model=");
		builder.append(model);
		builder.append("]");
		return builder.toString();
	}
}
