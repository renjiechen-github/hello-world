package pccom.web.flow.bean;

/**
 * 任务编号
 * @author 雷杨
 *
 */
public class TaskBean {

	public String task_id="";
	/**
	 * 总的任务id yc_task_cfg_tab中id，不为空
	 */
	public String task_cfg_id="";
	/**
	 * 当前任务名称， 可不传，如果不传默认取yc_task_cfg_tab中的名称
	 */
	public String name="";
	/**
	 * 创建人工号，系统自动发起传入0 ，不为空
	 */
	public String create_oper="";
	/**
	 * （天）预计任务完成时长，可作为后期任务超时对比，完成时长根据创建时间开始计算 ，不传入默认取yc_task_cfg_tab表中信息
	 */
	public String predict_complete_duration="";
	/**
	 * 当前任务超时提醒时间，剩余多久提醒 ,不传入或者为空默认就是0不提醒
	 */
	public String early_duration="";
	/**
	 * 上级任务步骤id，没有可不传入，当存在此项的时候此任务完成就会去同步更新处于挂起中的步骤  
	 */
	public String sup_task_step_id="";
	/**
	 * 当前任务备注信息  可不传入
	 */
	public String remark="";
	/**
	 * 当前任务需要挂载的附件id,对应表：yc_flow_file中的file_id 可不传入
	 */
	public String file_id="";
	
	/**
	 * 当前任务对应的订单id
	 */
	public String resource_id="";
	
	/**
	 * 刚生成流程时 需要下一步处理的人
	 */
	public String nowRole="";
	private String nowOrg="";
	private String nowOper="";
	
	
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public String getNowRole() {
		return nowRole;
	}
	public void setNowRole(String nowRole) {
		this.nowRole = nowRole;
	}
	public String getNowOrg() {
		return nowOrg;
	}
	public void setNowOrg(String nowOrg) {
		this.nowOrg = nowOrg;
	}
	public String getNowOper() {
		return nowOper;
	}
	public void setNowOper(String nowOper) {
		this.nowOper = nowOper;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTask_cfg_id() {
		return task_cfg_id;
	}
	public void setTask_cfg_id(String task_cfg_id) {
		this.task_cfg_id = task_cfg_id;
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
	public String getEarly_duration() {
		return early_duration;
	}
	public void setEarly_duration(String early_duration) {
		this.early_duration = early_duration;
	}
	public String getSup_task_step_id() {
		return sup_task_step_id;
	}
	public void setSup_task_step_id(String sup_task_step_id) {
		this.sup_task_step_id = sup_task_step_id;
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
	
	
}
