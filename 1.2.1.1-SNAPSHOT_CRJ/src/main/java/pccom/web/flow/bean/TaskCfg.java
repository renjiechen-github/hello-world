package pccom.web.flow.bean;

public class TaskCfg {

	private String id = "";
	private String type = "";
	private String name = "";
	private String create_oper = "";
	private String predict_complete_duration = "";
	private String state = "";
	private String early_duration = "";
	private String send_rule_id = "";
	private String send_org_id = "";
	private String send_oper = "";
	private String html_path="";
	private String dispose_class = "";
	private String create_time="";
	
	public String getHtml_path() {
		return html_path;
	}
	public void setHtml_path(String html_path) {
		this.html_path = html_path;
	}
	public String getDispose_class() {
		return dispose_class;
	}
	public void setDispose_class(String dispose_class) {
		this.dispose_class = dispose_class;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreate_oper() {
		return create_oper;
	}
	public void setCreate_oper(String create_oper) {
		this.create_oper = create_oper;
	}
	public String getPredict_complete_duration() {
		return predict_complete_duration;
	}
	public void setPredict_complete_duration(String predict_complete_duration) {
		this.predict_complete_duration = predict_complete_duration;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEarly_duration() {
		return early_duration;
	}
	public void setEarly_duration(String early_duration) {
		this.early_duration = early_duration;
	}
	public String getSend_rule_id() {
		return send_rule_id;
	}
	public void setSend_rule_id(String send_rule_id) {
		this.send_rule_id = send_rule_id;
	}
	public String getSend_org_id() {
		return send_org_id;
	}
	public void setSend_org_id(String send_org_id) {
		this.send_org_id = send_org_id;
	}
	public String getSend_oper() {
		return send_oper;
	}
	public void setSend_oper(String send_oper) {
		this.send_oper = send_oper;
	}
	
}
