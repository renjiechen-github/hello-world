package pccom.web.flow.interfaces;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import pccom.common.util.Batch;
import pccom.web.flow.base.FlowBase;

public abstract class FlowStepDispose extends FlowBase implements FlowStepDisposeInterface {

	/**
	 * 当需要进行自定义下一步处理人的时候调用此类返回，带上下一步的step_id
	 * @author 雷杨
	 * @param state
	 * @param step_id
	 * @return
	 */
	public Map<String,String> getReturn(String state,String step_id){
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("state", state);
		returnMap.put("step_id", step_id);
		return returnMap;
	}
	
	/**
	 * 不需要进行下一步处理的时候默认使用此项
	 * @author 雷杨
	 * @param state
	 * @param step_id
	 * @return
	 */
	public Map<String,String> getReturn(String state){
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("state", state);
		return returnMap;
	}
	
	@Override
	public Map<String,Object> stepDispose(Batch batch, HttpServletRequest request,
			String step_id, String task_id, String state,String cfg_step_id) throws Exception {
		return getReturnMap("1", "操作成功");
	}
	
	@Override
	public Map<String, Object> showStepDetail(Batch batch,
			HttpServletRequest request, String step_id, String task_id,boolean iscl,String cfg_step_id) throws Exception {
		request.setAttribute("edit",iscl==true?"1":"0");
		request.setAttribute("cfg_step_id",cfg_step_id);
		request.setAttribute("task_cfg_id",req.getAjaxValue(request, "task_cfg_id"));
		request.setAttribute("task_id",task_id);
		request.setAttribute("step_id",step_id);
		return new HashMap<String, Object>();
	}

}
