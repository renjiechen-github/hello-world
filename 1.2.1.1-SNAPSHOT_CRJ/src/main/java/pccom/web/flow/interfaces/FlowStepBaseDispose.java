package pccom.web.flow.interfaces;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import pccom.common.util.Batch;

public class FlowStepBaseDispose extends FlowStepDispose {

	
	
	public Map<String,Object> stepDispose(Batch batch, HttpServletRequest request,
			String step_id, String task_id, String state,String cfg_step_id) throws Exception {
		return super.stepDispose(batch, request, step_id, task_id, state,cfg_step_id);
	}

	@Override
	public Map<String, Object> deleteFlow(Batch batch, String task_id) throws Exception{
		return getReturnMap("1", "操作成功");
	}

}