package pccom.web.mobile.flow.base;

import java.util.HashMap;
import java.util.Map;
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
@RequestMapping("mobile/task/")
public class MiFlowController extends BaseController{

	@Autowired
	private MiFlowService miFlowService;
	
	/**
	 * 我发起的任务界面
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "myStartTask.do")
	public void myStartTask(HttpServletResponse response){
		//查询出当前任务状态信息
	/*	request.setAttribute("stateList",miFlowService.getStateList(request,response));
		request.setAttribute("typeList",miFlowService.getTypeList(request,response));*/
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("stateList", miFlowService.getStateList(request,response));
		returnMap.put("typeList", miFlowService.getTypeList(request,response));
		this.writeJsonData(returnMap, response);
	} 
	
	
	/**
	 * 获取财务数据
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "getFinancialList.do")
	public void getFinancial(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(miFlowService.getFinancialList(request, response), response);
	}
	
	/**
	 * 获取财务数据
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "getPayList.do")
	public void getPayList(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(miFlowService.getPayList(request, response), response);
	}
	
	
	/**
	 * 任务类型
	 * @param response
	 */
	@RequestMapping("taskTypeList.do")
	public void taskTypeList(HttpServletResponse response)
	{
		this.writeJsonData(miFlowService.getTypeList(request, response), response);
	}
	
	/**
	 * 状态列表
	 * @param response
	 */
	@RequestMapping("taskStateList.do")
	public void taskStateList(HttpServletResponse response)
	{
		this.writeJsonData(miFlowService.getStateList(request, response), response);
	}
	
	/**
	 * 获取类型级联信息
	 * @author 雷杨
	 * @return
	 */
	@RequestMapping (value = "getType.do")
	public void getType(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(miFlowService.getType(request, response), response);
	}
	
	/**
	 * 获取我发起的任务列表
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "getMyStartTaskList.do")
	public void getMyStartTaskList(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(miFlowService.getMyStartTaskList(request, response), response);
	}
	
	/**
	 * 我发起任务详细显示页面信息
	 * @author 雷杨
	 * @param response
	 * @return 
	 */
	@RequestMapping (value = "showFlowDetail.do")
	public String showFlowDetail(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("map",miFlowService.getShowFlowDetail(request,response));
		//final String oper = this.getUser(request).getId();// 获取操作人
		return "flow/pages/base/showFlowDetail";
	}
	
	/**
	 * 我已处理的订单
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "yetDisposeTask.do")
	public String yetDisposeTask(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("stateList",miFlowService.getStateList(request,response));
		request.setAttribute("typeList",miFlowService.getTypeList(request,response));
		return "flow/pages/base/yetDisposeTask";
	}
	
	/**
	 * 我已处理的订单详细信息
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "yetDisposeDitail.do")
	public String yetDisposeDitail(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("map",miFlowService.yetDisposeDitail(request,response));
		return "flow/pages/base/yetDisposeDitail";
	}
	
	/**
	 * 获取我已经处理的订单
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "getMyYetTaskList.do")
	public void getMyYetTaskList( HttpServletResponse response){
		//miFlowService.getMyYetTaskList(request,response);
		this.writeJsonData(miFlowService.getMyYetTaskList(request, response), response);
	}
	
	/**
	 * 我待处理信息详细
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "showDetailFlowDetail.do")
	public String showDetailFlowDetail(HttpServletRequest request, HttpServletResponse response){
		//在刚下发的情况下更改状态为已阅读
		//flowService.changeFlowState(request,response);
		//查询出当前任务状态信息
		request.setAttribute("map",miFlowService.showDetailFlowDetail(request,response));
		
		//查询当前步骤配置的下一步信息
		request.setAttribute("stepList",miFlowService.getNowNextStepList(request,response));
		return "flow/pages/base/showDisposeFlowDetail";
	}
	
	/**
	 * 我待处理的信息
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "myDisposeFlow.do")
	public String myDisposeFlow(HttpServletRequest request, HttpServletResponse response){
		//查询出当前任务状态信息
		request.setAttribute("stateList",miFlowService.getStateList(request,response));
		request.setAttribute("typeList",miFlowService.getTypeList(request,response));
		return "flow/pages/base/myDisposeFlow";
	}
	
	/**
	 * 获取待处理信息列表
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "getDisposetTaskList.do")
	public void getDisposetTaskList(HttpServletRequest request, HttpServletResponse response){
		//miFlowService.getDisposetTaskList(request,response);
		this.writeJsonData(miFlowService.getDisposetTaskList(request, response), response);
	}
	
	/**
	 * 获取处理步骤详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value = "showStepDetail.do")
	public String showStepDetail(HttpServletRequest request, HttpServletResponse response){
		logger.debug("????????????");
		return miFlowService.showStepDetail(request,response);
	}
	
	/**
	 * 获取处理步骤详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value = "showTaskDetail.do")
	public String showTaskDetail(HttpServletRequest request, HttpServletResponse response){
		return miFlowService.showTaskDetail(request,response);
	}
	
	/**
	 * 处理步骤详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "saveDisposeFlow.do")
	public void saveDisposeFlow(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(miFlowService.saveDisposeFlow(request,response), response);
	}
	
	/**
	 * 删除流程操作
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "deleteFlow.do")
	public void deleteFlow(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(miFlowService.deleteFlow(request,response), response);
	}
	
	/**
	 * 发起任务界面
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "createFlow.do")
	public String createFlow(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("typeList",miFlowService.getTypeCreateList(request,response));
		return "flow/pages/base/createFlow";
	}
	
	/**
	 * 获取管家列表信息
	 * @param response
	 * @author liuf
	 * @date 2016.9.19
	 */
	@RequestMapping("getMangerList.do")
	public void getMangerList(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(miFlowService.queryManager(request,response), response);
	
	}
	/**
	 * 获取验房录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "getInfo.do")
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(miFlowService.getInfo(request,response), response);
	} 
}
