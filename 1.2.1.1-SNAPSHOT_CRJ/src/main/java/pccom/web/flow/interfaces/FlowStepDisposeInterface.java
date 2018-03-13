package pccom.web.flow.interfaces;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import pccom.common.util.Batch;

/**
 * 步骤处理接口
 * 
 * @author 雷杨
 *
 */
public interface FlowStepDisposeInterface {

	/**
	 * 当前步骤处理信息
	 * 
	 * @author 雷杨
	 * @param batch 事物处理
	 * @param step_id 步骤id  yc_task_step_info_tab中的step_id
	 * @param task_id  任务id
	 * @param state 当前步骤处理状态
	 * @param cfg_step_id   yc_task_step_info_tab中的cfg_step_id
	 * @return Map:{state:0为0  的情况下 是自定义下一步到哪里
	 * 				step_id:下一步步骤id 如果state 为0需要传递此参数，否则不传
	 * 				step_name:下一步步骤名称 如果state 为0需要传递此参数，否则不传
	 * 				msg:错误消息显示
	 * 			}
	 */
	public Map<String,Object> stepDispose(Batch batch,HttpServletRequest request,String step_id,String task_id,String state,String cfg_step_id) throws Exception;
	
	/**
	 * 查看步骤信息的时候，如果在yc_task_step_cfg_tab中配置了对应的html_path的时候需要此给他单独设置显示信息
	 * @author 雷杨
	 * @param batch
	 * @param request
	 * @param step_id yc_task_step_info_tab中的step_id
	 * @param task_id
	 * @param isCl 是否是处理状态 如果是处理状态为true 否则查询状态下为false
	 * @param cfg_step_id yc_task_step_info_tab中的cfg_step_id
	 * @return
	 */
	public Map<String,Object> showStepDetail(Batch batch,HttpServletRequest request,String step_id,String task_id,boolean isCl,String cfg_step_id) throws Exception;
	
	/**
	 * 删除当前任务信息
	 * 
	 * @author 雷杨
	 * @param batch
	 * @param task_id
	 * @return
	 */
	public Map<String,Object> deleteFlow(Batch batch,String task_id)throws Exception;
	
}
