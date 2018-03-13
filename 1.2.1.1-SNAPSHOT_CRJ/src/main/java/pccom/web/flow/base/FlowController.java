package pccom.web.flow.base;

import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pccom.web.action.BaseController;

/**
 * 流程处理类
 * @author 雷杨
 *
 */
@Controller
public class FlowController extends BaseController{

	@Autowired
	private FlowService flowService;
	
	/**
	 * 我发起的任务界面
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "flow/myStartTask.do")
	public String myStartTask(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("stateList",flowService.getStateList(request,response));
		request.setAttribute("typeList",flowService.getTypeList(request,response));
		return "flow/pages/base/myStartTask";
	} 
	
	/**
	 * 获取类型级联信息
	 * @author 雷杨
	 * @return
	 */
	@RequestMapping (value = "flow/getType.do")
	public void getType(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(flowService.getType(request, response), response);
	}
	
	/**
	 * 获取我发起的任务列表
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/getMyStartTaskList.do")
	public void getMyStartTaskList(HttpServletRequest request, HttpServletResponse response){
		flowService.getMyStartTaskList(request,response);
	}
	
	/**
	 * 获取财务数据
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/getFinancialList.do")
	public void getFinancial(HttpServletRequest request, HttpServletResponse response){
		flowService.getFinancialList(request,response);
	}
        
	/**
	 * 获取财务数据
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/getPayList.do")
	public void getPayList(HttpServletRequest request, HttpServletResponse response){
		flowService.getPayList(request,response);
	}
	
	@RequestMapping (value = "flow/getFinanceDetails.do")
	public void getFinanceDetails(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(flowService.getFinanceDetails(request, response), response);
	}
	
	@RequestMapping (value = "flow/getFinanceTable.do")
	public void getFinanceTable(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(flowService.getFinanceTable(request, response), response);
	}
	
	/**
	 * 我发起任务详细显示页面信息
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "flow/showFlowDetail.do")
	public String showFlowDetail(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("map",flowService.getShowFlowDetail(request,response));
		return "flow/pages/base/showFlowDetail";
	}
	
	/**
	 * 我已处理的订单
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "flow/yetDisposeTask.do")
	public String yetDisposeTask(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("stateList",flowService.getStateList(request,response));
		request.setAttribute("typeList",flowService.getTypeList(request,response));
		return "flow/pages/base/yetDisposeTask";
	}
	
	/**
	 * 我已处理的订单详细信息
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "flow/yetDisposeDitail.do")
	public String yetDisposeDitail(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("map",flowService.yetDisposeDitail(request,response));
		return "flow/pages/base/yetDisposeDitail";
	}
	
	/**
	 * 获取我已经处理的订单
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/getMyYetTaskList.do")
	public void getMyYetTaskList(HttpServletRequest request, HttpServletResponse response){
		flowService.getMyYetTaskList(request,response);
	}
	
	/**
	 * 我待处理信息详细
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "flow/showDetailFlowDetail.do")
	public String showDetailFlowDetail(HttpServletRequest request, HttpServletResponse response){
		//在刚下发的情况下更改状态为已阅读
		//flowService.changeFlowState(request,response);
		//查询出当前任务状态信息
		request.setAttribute("map",flowService.showDetailFlowDetail(request,response));
		//查询当前步骤配置的下一步信息
		request.setAttribute("stepList",flowService.getNowNextStepList(request,response));
		
		return "flow/pages/base/showDisposeFlowDetail";
	}
	
	/**
	 * 我待处理的信息
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "flow/myDisposeFlow.do")
	public String myDisposeFlow(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("stateList",flowService.getStateList(request,response));
		request.setAttribute("typeList",flowService.getTypeList(request,response));
		return "flow/pages/base/myDisposeFlow";
	}
	
	/**
	 * 获取待处理信息列表
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/getDisposetTaskList.do")
	public void getDisposetTaskList(HttpServletRequest request, HttpServletResponse response){
		flowService.getDisposetTaskList(request,response);
	}
	
	/**
	 * 获取处理步骤详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value = "flow/showStepDetail.do")
	public String showStepDetail(HttpServletRequest request, HttpServletResponse response){
		logger.debug("????????????");
		return flowService.showStepDetail(request,response);
	}
	
	/**
	 * 获取处理步骤详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value = "flow/showTaskDetail.do")
	public String showTaskDetail(HttpServletRequest request, HttpServletResponse response){
		return flowService.showTaskDetail(request,response);
	}
	
	/**
	 * 处理步骤详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/saveDisposeFlow.do")
	public void saveDisposeFlow(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(flowService.saveDisposeFlow(request,response), response);
	}
	
	
	/**
	 * 计算应退租金
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @throws TaskException 
	 * @throws ParseException 
	 */
	@RequestMapping (value = "flow/calculate.do")
	public void calculate(HttpServletRequest request, HttpServletResponse response) throws ParseException, TaskException{
		this.writeJsonData(flowService.calculate(request,response), response);
	}
	
	/**
	 * 删除流程操作
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/deleteFlow.do")
	public void deleteFlow(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(flowService.deleteFlow(request,response), response);
	}
	
	/**
	 * 发起任务界面
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "flow/createFlow.do")
	public String createFlow(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("typeList",flowService.getTypeCreateList(request,response));
		return "flow/pages/base/createFlow";
	}

	/**
	 * 录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "flow/getInfo.do")
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(flowService.getInfo(request,response), response);
	} 
}
