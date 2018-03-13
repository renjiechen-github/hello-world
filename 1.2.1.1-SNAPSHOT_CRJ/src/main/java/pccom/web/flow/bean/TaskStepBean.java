package pccom.web.flow.bean;

public class TaskStepBean {

	private String task_id="";
	private String step_id="";
	private String step_name="";
	private String next_step_id="";
	private String sup_step_id="";
	private String oper_id="";
	private String is_overtime="";
	private String state="";
	private String remark="";
	private String file_id="";
	private String wait_task_id="";
	private String dispose_class="";
	private String cfg_step_id="";
	
	public String getCfg_step_id() {
		return cfg_step_id;
	}
	public void setCfg_step_id(String cfg_step_id) {
		this.cfg_step_id = cfg_step_id;
	}
	public String getDispose_class() {
		return dispose_class;
	}
	public void setDispose_class(String dispose_class) {
		this.dispose_class = dispose_class;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getStep_id() {
		return step_id;
	}
	public void setStep_id(String step_id) {
		this.step_id = step_id;
	}
	public String getStep_name() {
		return step_name;
	}
	public void setStep_name(String step_name) {
		this.step_name = step_name;
	}
	public String getNext_step_id() {
		return next_step_id;
	}
	public void setNext_step_id(String next_step_id) {
		this.next_step_id = next_step_id;
	}
	public String getSup_step_id() {
		return sup_step_id;
	}
	public void setSup_step_id(String sup_step_id) {
		this.sup_step_id = sup_step_id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getIs_overtime() {
		return is_overtime;
	}
	public void setIs_overtime(String is_overtime) {
		this.is_overtime = is_overtime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getWait_task_id() {
		return wait_task_id;
	}
	public void setWait_task_id(String wait_task_id) {
		this.wait_task_id = wait_task_id;
	}
	
	
}
