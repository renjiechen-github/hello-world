package com.room1000.workorder.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.pagehelper.PageInfo;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.model.ResponseModel;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderDtoMapper;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dao.GetOrderCommentaryTypeResponse;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.OrderCommentaryTypeDto;
import com.room1000.workorder.dto.OrderStepDto;
import com.room1000.workorder.dto.SubOrderStateDto;
import com.room1000.workorder.dto.TypeCountDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.dto.WorkOrderTypeDto;
import com.room1000.workorder.dto.request.AssignOrderRequest;
import com.room1000.workorder.dto.request.AuditBrokerOrderRequest;
import com.room1000.workorder.dto.request.CloseBrokerOrderRequest;
import com.room1000.workorder.dto.request.DeleteWorkOrderRequest;
import com.room1000.workorder.dto.request.EndProcessRequest;
import com.room1000.workorder.dto.request.GetHisRequest;
import com.room1000.workorder.dto.request.GetOrderCommentaryTypeRequest;
import com.room1000.workorder.dto.request.GetOrderStepRequest;
import com.room1000.workorder.dto.request.GetWorkOrderDetailRequest;
import com.room1000.workorder.dto.request.QryOrderCommentaryPagerListRequest;
import com.room1000.workorder.dto.request.QryWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.TeamWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.UpdateSubOrderRequest;
import com.room1000.workorder.dto.request.WorkOrderCommentaryRequest;
import com.room1000.workorder.dto.response.GetHisResponse;
import com.room1000.workorder.dto.response.GetOrderStepResponse;
import com.room1000.workorder.dto.response.GetSubOrderStateResponse;
import com.room1000.workorder.dto.response.GetTypeCountResponse;
import com.room1000.workorder.dto.response.GetWorkOrderDetailResponse;
import com.room1000.workorder.dto.response.GetWorkOrderTypeResponse;
import com.room1000.workorder.service.IWorkOrderService;
import com.ycdc.core.base.BaseController;

import pccom.web.beans.User;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月6日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Controller
@RequestMapping("/workOrder")
public class WorkOrderController extends BaseController
{

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

	/**
	 * workOrderService
	 */
	@Autowired
	private IWorkOrderService workOrderService;
	
	@Autowired
	private CancelLeaseOrderDtoMapper cancelLeaseOrderDtoMapper;

	/**
	 * 
	 * Description: 通用列表展示
	 * 
	 * @author jinyanan
	 *
	 * @param response
	 *          response
	 */
	@RequestMapping("/getWorkOrderList.do")
	public void getWorkOrderList(HttpServletResponse response)
	{
		workOrderService.getWorkOrderList(request, response);
	}

	/**
	 * 
	 * Description: 获取订单类型
	 * 
	 * @author jinyanan
	 *
	 * @param response
	 *          response
	 */
	@RequestMapping("/getWorkOrderTypeByPermission.do")
	public void getWorkOrderTypeByPermission(HttpServletResponse response)
	{
		writeJsonData(workOrderService.getWorkOrderTypeByPermission(request, response), response);
	}

	/**
	 * 
	 * Description: 售后列表展示
	 * 
	 * @author jinyanan
	 *
	 * @param response
	 *          response
	 */
	@RequestMapping("/getWorkOrderList4CustomerService.do")
	public void getWorkOrderList4CustomerService(HttpServletResponse response)
	{
		workOrderService.getWorkOrderList4CustomerService(request, response);
	}

	/**
	 * 
	 * Description: 查询所有订单类型
	 * 
	 * @author jinyanan
	 *
	 * @return GetWorkOrderTypeResponse
	 */
	@RequestMapping("/getWorkOrderType.do")
	@ResponseBody
	public GetWorkOrderTypeResponse getWorkOrderType(@CookieValue(value = "username", required = false) String userName)
	{
		String roleIds = "";
		if (StringUtils.isBlank(userName) || userName.equals("null"))
		{
			roleIds = "";
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			roleIds = currentStaff.getRoles();
		}

		List<WorkOrderTypeDto> workOrderTypeList = workOrderService.getWorkOrderType(roleIds);

		GetWorkOrderTypeResponse resp = new GetWorkOrderTypeResponse();
		resp.setWorkOrderTypeList(workOrderTypeList);
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 获取订单列表
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return PageInfo<WorkOrderDto>
	 */
	@RequestMapping(value = "/getWorkOrderPagerList.do")
	@ResponseBody
	public PageInfo<WorkOrderDto> getWorkOrderList(@RequestBody QryWorkOrderPagerListRequest req)
	{
		List<WorkOrderDto> workOrderList = workOrderService.getWorkOrderList(req);
		return new PageInfo<WorkOrderDto>(workOrderList);
	}

	/**
	 * 更改流程节点
	 * 
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/directActivity.do")
	public void directActivity(HttpServletResponse response) throws Exception
	{
		IProcessTask task = new ProcessTaskImpl();
		long workOrderId = Long.valueOf(request.getParameter("workOrderId"));
		String activityId = request.getParameter("activityId");
		String subAssignedDealerId = String.valueOf(request.getParameter("butlerId"));
		String subOrderId = String.valueOf(request.getParameter("subOrderId"));
		String type = String.valueOf(request.getParameter("type"));
		task.direct2Activity(workOrderId, activityId);
		
		if (!subAssignedDealerId.equals("") && !subAssignedDealerId.equals("null") && !subOrderId.equals("") && !subOrderId.equals("null"))
		{
			if (type.equals("A"))
			{
				// 修改操作人
				CancelLeaseOrderDto cancelLeaseOrderDto = new CancelLeaseOrderDto();
				cancelLeaseOrderDto.setActualDealerId(Long.valueOf(subAssignedDealerId));
				cancelLeaseOrderDto.setAssignedDealerId(Long.valueOf(subAssignedDealerId));
				cancelLeaseOrderDto.setButlerId(Long.valueOf(subAssignedDealerId));
				cancelLeaseOrderDto.setId(Long.valueOf(subOrderId));
				cancelLeaseOrderDtoMapper.updateByPrimaryKeySelective(cancelLeaseOrderDto);
			}
			this.writeJsonData(workOrderService.updateWorkOrderButlerById(request,response), response);
		}
	}

	/**
	 * 团队任务列表（PC端）
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getTeamWorkOrderList.do")
	public void getTeamWorkOrderList(HttpServletResponse response)
	{
		workOrderService.getTeamWorkOrderList(request, response);
	}

	/**
	 * 团队任务列表（手机端）
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getTeamWorkOrderPagerList.do")
	@ResponseBody
	public PageInfo<WorkOrderDto> getTeamWorkOrderPagerList(@RequestBody TeamWorkOrderPagerListRequest req)
	{
		List<WorkOrderDto> teamWorkOrderList = workOrderService.getTeamWorkOrderList(req);
		return new PageInfo<WorkOrderDto>(teamWorkOrderList);
	}

	/**
	 * 
	 * Description: 获取评价列表
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return PageInfo<OrderCommentaryDto>
	 */
	@RequestMapping(value = "/getOrderCommentaryPagerList.do")
	@ResponseBody
	public PageInfo<OrderCommentaryDto> getOrderCommentaryDto(@RequestBody QryOrderCommentaryPagerListRequest req)
	{
		List<OrderCommentaryDto> orderCommentaryList = workOrderService.getOrderCommentaryDto(req);
		return new PageInfo<OrderCommentaryDto>(orderCommentaryList);

	}

	/**
	 * 
	 * Description: 更新子订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/updateSubOrder.do")
	@ResponseBody
	public ResponseModel updateSubOrder(@RequestBody UpdateSubOrderRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		String realPath = request.getSession().getServletContext().getRealPath("/");
		User currentStaff = SubOrderUtil.getUserByUserName(userName);
		workOrderService.updateSubOrderWithTrans(req, realPath, Long.valueOf(currentStaff.getId()));

		return new ResponseModel();

	}

	/**
	 * 
	 * Description: 给C端使用的，用于逻辑删除
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/deleteWorkOrder.do")
	@ResponseBody
	public ResponseModel deleteWorkOrder(@RequestBody DeleteWorkOrderRequest req)
	{
		WorkOrderDto workOrderDto = new WorkOrderDto();
		workOrderDto.setId(req.getWorkOrderId());
		workOrderDto.setCode(req.getCode());
		workOrderService.deleteWorkOrder(workOrderDto);

		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 页面跳转到订单详情页面
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          workOrder.id
	 * @param isMobile
	 *          是否手机触发
	 * @param staffId
	 *          处理员工
	 * @return 页面地址
	 */
	@RequestMapping("/path2WorkOrderDetailPage.do")
	public String path2WorkOrderDetailPage(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "isMobile", required = false) String isMobile,
			@RequestParam(value = "staffId", required = false) Long staffId, @RequestParam(value = "isLeader", required = false) String isLeader)
	{
		request.setAttribute("id", id);
		String s1 = request.getHeader("user-agent");
		if (s1.toLowerCase().contains("android") || s1.toLowerCase().contains("iphone") || s1.toLowerCase().contains("ipad"))
		{
			request.setAttribute("isMobile", "Y");
		} else
		{
			request.setAttribute("isMobile", "N");
		}
		request.setAttribute("staffId", staffId);
		request.setAttribute("isLeader", isLeader);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDetailById(Long.valueOf(id));
		if (!"Y".equals(isMobile) && !"Y".equals(isLeader))
		{
			if ((workOrderDto.getSubAssignedDealerId() == null || "".equals(String.valueOf(workOrderDto.getSubAssignedDealerId())))
					&& (workOrderDto.getSubAssignedDealerRoleId() == null || "".equals(String.valueOf(workOrderDto.getSubAssignedDealerRoleId()))))
			{
				return null;
			}
			User user = this.getUser(request);
			if (workOrderDto.getSubAssignedDealerId() != null && !"".equals(String.valueOf(workOrderDto.getSubAssignedDealerId())))
			{
				if (!String.valueOf(workOrderDto.getSubAssignedDealerId()).equals(user.getId()))
				{
					return null;
				}
			} else if (workOrderDto.getSubAssignedDealerRoleId() != null && !"".equals(String.valueOf(workOrderDto.getSubAssignedDealerRoleId())))
			{
				boolean isRole = false;
				if (user.getRoles() != null && !"".equals(user.getRoles()))
					for (String role : user.getRoles().split(","))
					{
						if (String.valueOf(workOrderDto.getSubAssignedDealerRoleId()).equals(role))
						{
							isRole = true;
							break;
						}
					}
				if (!isRole)
				{
					return null;
				}
			}
		}
		switch (workOrderDto.getType())
		{
		case WorkOrderTypeDef.CANCEL_LEASE_ORDER:
			return "flow/pages/base/cancelLeaseOrderDetail";
		// 添加新退租
		case WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER:
			return "flow/pages/base/newCancelLeaseOrderDetail";
		case WorkOrderTypeDef.HOUSE_LOOKING_ORDER:
			return "flow/pages/base/houseLookingOrderDetail";
		case WorkOrderTypeDef.COMPLAINT_ORDER:
			return "flow/pages/base/complaintOrderDetail";
		case WorkOrderTypeDef.CLEANING_ORDER:
			return "flow/pages/base/cleaningOrderDetail";
		case WorkOrderTypeDef.LIVING_PROBLEM_ORDER:
			return "flow/pages/base/livingProblemOrderDetail";
		case WorkOrderTypeDef.OTHER_ORDER:
			return "flow/pages/base/otherOrderDetail";
		case WorkOrderTypeDef.OWNER_REPAIR_ORDER:
			return "flow/pages/base/owerRepairOrderDetail";
		case WorkOrderTypeDef.REPAIR_ORDER:
			return "flow/pages/base/repairOrderDetail";
		case WorkOrderTypeDef.ROUTINE_CLEANING_ORDER:
			return "flow/pages/base/routineCleaningOrderDetail";
		case WorkOrderTypeDef.BROKER_ORDER:
			return "flow/pages/base/brokerOrderDetail";
		case WorkOrderTypeDef.OWNER_CANCEL_LEASE_ORDER:
			return "flow/pages/base/ownerCancelLeaseOrderDetail";
		case WorkOrderTypeDef.PAY_ORDER:
			return "flow/pages/base/brokerCashDetail";
		default:
			break;
		}
		return null;
	}

	/**
	 * 
	 * Description: 页面跳转到订单详情页面
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          workOrder.id
	 * @param isMobile
	 *          是否手机触发
	 * @param staffId
	 *          处理员工
	 * @return 页面地址
	 */
	@RequestMapping("/path2WorkOrderDetailInfoPage.do")
	public String path2WorkOrderDetailInfoPage(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "isMobile", required = false) String isMobile,
			@RequestParam(value = "staffId", required = false) Long staffId, @RequestParam(value = "isLeader", required = false) String isLeader)
	{
		request.setAttribute("id", id);
		String s1 = request.getHeader("user-agent");
		if (s1.toLowerCase().contains("android") || s1.toLowerCase().contains("iphone") || s1.toLowerCase().contains("ipad"))
		{
			request.setAttribute("isMobile", "Y");
		} else
		{
			request.setAttribute("isMobile", "N");
		}
		request.setAttribute("staffId", staffId);
		request.setAttribute("isLeader", isLeader);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDetailById(Long.valueOf(id));
		switch (workOrderDto.getType())
		{
		case WorkOrderTypeDef.CANCEL_LEASE_ORDER:
			return "flow/pages/base/cancelLeaseOrderDetail";
		// 添加新退租
		case WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER:
			return "flow/pages/base/newCancelLeaseOrderDetail";
		case WorkOrderTypeDef.HOUSE_LOOKING_ORDER:
			return "flow/pages/base/houseLookingOrderDetail";
		case WorkOrderTypeDef.COMPLAINT_ORDER:
			return "flow/pages/base/complaintOrderDetail";
		case WorkOrderTypeDef.CLEANING_ORDER:
			return "flow/pages/base/cleaningOrderDetail";
		case WorkOrderTypeDef.LIVING_PROBLEM_ORDER:
			return "flow/pages/base/livingProblemOrderDetail";
		case WorkOrderTypeDef.OTHER_ORDER:
			return "flow/pages/base/otherOrderDetail";
		case WorkOrderTypeDef.OWNER_REPAIR_ORDER:
			return "flow/pages/base/owerRepairOrderDetail";
		case WorkOrderTypeDef.REPAIR_ORDER:
			return "flow/pages/base/repairOrderDetail";
		case WorkOrderTypeDef.ROUTINE_CLEANING_ORDER:
			return "flow/pages/base/routineCleaningOrderDetail";
		case WorkOrderTypeDef.BROKER_ORDER:
			return "flow/pages/base/brokerOrderDetail";
		case WorkOrderTypeDef.OWNER_CANCEL_LEASE_ORDER:
			return "flow/pages/base/ownerCancelLeaseOrderDetail";
		case WorkOrderTypeDef.PAY_ORDER:
			return "flow/pages/base/brokerCashDetail";
		default:
			break;
		}
		return null;
	}

	/**
	 * 
	 * Description: 获取订单详情，包括子订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return GetWorkOrderDetailResponse
	 */
	@RequestMapping("/getWorkOrderDetail.do")
	@ResponseBody
	public GetWorkOrderDetailResponse getWorkOrderDetailById(@RequestBody GetWorkOrderDetailRequest req)
	{
		Long workOrderId = req.getWorkOrderId();

		// 如果只查询子订单信息，则置为Y；空或者N则查询全量信息（全量信息的查询速度有欠缺）
		String singleDetailFlag = req.getSingleDetailFlag();

		Boolean singleDetail = StringUtils.isNotEmpty(singleDetailFlag) && BooleanFlagDef.BOOLEAN_TRUE.equals(singleDetailFlag);
		
		GetWorkOrderDetailResponse resp = new GetWorkOrderDetailResponse();
		Object subOrder = workOrderService.getSubOrderDetailById(req.getWorkOrderId(), singleDetail);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDetailById(workOrderId);
		resp.setWorkOrder(workOrderDto);
		resp.setSubOrder(subOrder);
		resp.setState(ResponseModel.NORMAL);
		logger.info("resp ==== " + resp);
		return resp;
	}

	/**
	 * 
	 * Description: 统一指派订单接口
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/assignOrder.do")
	@ResponseBody
	public ResponseModel assignOrder(@RequestBody AssignOrderRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		User currentStaff = SubOrderUtil.getUserByUserName(userName);
		workOrderService.assignOrderWithTrans(req, Long.valueOf(currentStaff.getId()));
		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 订单评价
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return ResponseModel
	 */
	@RequestMapping("/workOrderCommentary.do")
	@ResponseBody
	public ResponseModel workOrderCommentary(@RequestBody WorkOrderCommentaryRequest req)
	{

		String imagrUrl = "";
		if (StringUtils.isNotBlank(req.getImageUrl()))
		{
			imagrUrl = req.getImageUrl();
		}
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if (req.getWorkOrderId() != null)
		{
			workOrderService.workOrderCommentaryWithTrans(req.getWorkOrderId(), req.getOrderCommentaryList(), imagrUrl, realPath, req.getFlag());
		} else
		{
			workOrderService.workOrderCommentaryWithTrans(req.getCode(), req.getOrderCommentaryList(), imagrUrl, realPath, req.getFlag());
		}

		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 查询订单评价类型
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return GetOrderCommentaryTypeResponse
	 */
	@RequestMapping(value = "/getOrderCommentaryType.do")
	@ResponseBody
	public GetOrderCommentaryTypeResponse getOrderCommentaryType(@RequestBody GetOrderCommentaryTypeRequest req)
	{
		GetOrderCommentaryTypeResponse resp = new GetOrderCommentaryTypeResponse();
		List<OrderCommentaryTypeDto> orderCommentaryTypeList = workOrderService.getOrderCommentaryType(req.getOrderType());
		resp.setOrderCommentaryTypeList(orderCommentaryTypeList);
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 修改评价
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/updateOrderCommentary.do")
	@ResponseBody
	public ResponseModel updateOrderCommentary(@RequestBody WorkOrderCommentaryRequest req)
	{
		workOrderService.updateOrderCommentaryWithTrans(req);

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 删除评论
	 * 
	 * @author jinyanan
	 *
	 * @param orderCommentary
	 *          orderCommentary
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/deleteOrderCommentary.do")
	@ResponseBody
	public ResponseModel deleteOrderCommentary(@RequestBody OrderCommentaryDto orderCommentary)
	{
		workOrderService.deleteOrderCommentaryWithTrans(orderCommentary);

		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 查询历史
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return GetHisResponse
	 */
	@RequestMapping(value = "/getHis.do")
	@ResponseBody
	public GetHisResponse getHis(@RequestBody GetHisRequest req)
	{
		GetHisResponse resp = new GetHisResponse();
		resp.setSubOrderHisList(workOrderService.getHis(req.getWorkOrderId()));
		return resp;
	}

	/**
	 * 手工直接关闭订单
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/testEndProcess.do")
	public void testEndProcess() throws Exception
	{
		// 订单ID
		long workOrderId = Long.valueOf(request.getParameter("workOrderId"));
		// 执行人
		long staffId = Long.valueOf(request.getParameter("staffId"));
		workOrderService.endProcessWithTrans(workOrderId, staffId);
	}

	/**
	 * 
	 * Description: 直接关闭流程
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 * @throws Exception
	 *           <br>
	 */
	@RequestMapping(value = "/endProcess.do")
	@ResponseBody
	public ResponseModel endProcess(@RequestBody EndProcessRequest req, @CookieValue(value = "username", required = false) String userName) throws Exception
	{
		if (StringUtils.isEmpty(userName))
		{
			workOrderService.endProcessWithTrans(req.getWorkOrderId(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			workOrderService.endProcessWithTrans(req.getWorkOrderId(), Long.valueOf(currentStaff.getId()));
		}
		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 经纪人审批
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/auditBrokerOrder.do")
	@ResponseBody
	public ResponseModel doAuditBrokerOrder(@RequestBody AuditBrokerOrderRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			workOrderService.doAuditBrokerOrderWithTrans(req.getWorkOrderId(), req.getComments(), BooleanFlagDef.BOOLEAN_TRUE.equals(req.getPassed()), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			workOrderService.doAuditBrokerOrderWithTrans(req.getWorkOrderId(), req.getComments(), BooleanFlagDef.BOOLEAN_TRUE.equals(req.getPassed()), Long.valueOf(currentStaff.getId()));
		}
		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 经纪人推荐订单关闭
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/closeBrokerOrder.do")
	@ResponseBody
	public ResponseModel closeBrokerOrder(@RequestBody CloseBrokerOrderRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			workOrderService.closeBrokerOrder(req.getCode(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			workOrderService.closeBrokerOrder(req.getCode(), Long.valueOf(currentStaff.getId()));
		}

		return new ResponseModel();
	}

	/**
	 * 
	 * Description: 查询所有的子订单状态
	 * 
	 * @author jinyanan
	 *
	 * @return GetSubOrderStateResponse
	 */
	@RequestMapping(value = "/getSubOrderState.do")
	@ResponseBody
	public GetSubOrderStateResponse getSubOrderState()
	{
		GetSubOrderStateResponse resp = new GetSubOrderStateResponse();

		List<SubOrderStateDto> subOrderStateList = workOrderService.getSubOrderState();
		// resp.setState(ResponseModel.NORMAL);
		resp.setSubOrderStateList(subOrderStateList);
		return resp;
	}

	/**
	 * 
	 * Description: 获取步骤信息
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return GetOrderStepResponse
	 * @throws Exception
	 *           <br>
	 */
	@RequestMapping(value = "/getOrderStep.do")
	@ResponseBody
	public GetOrderStepResponse getOrderStep(@RequestBody GetOrderStepRequest req) throws Exception
	{
		GetOrderStepResponse resp = new GetOrderStepResponse();
		List<OrderStepDto> orderStepList = new ArrayList<>();
		if (req.getWorkOrderId() != null)
		{
			orderStepList = workOrderService.getOrderStep(req.getWorkOrderId());
		} else
		{
			orderStepList = workOrderService.getOrderStep(req.getCode());
		}
		resp.setOrderStepList(orderStepList);

		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 获取各个订单的数量
	 * 
	 * @author jinyanan
	 *
	 * @return GetTypeCountResponse
	 */
	@RequestMapping(value = "/getTypeCount.do")
	@ResponseBody
	public GetTypeCountResponse getTypeCount()
	{
		GetTypeCountResponse resp = new GetTypeCountResponse();
		List<TypeCountDto> typeCountList = workOrderService.getTypeCount();
		resp.setTypeCountList(typeCountList);

		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 运行时异常统一处理
	 * 
	 * @author jinyanan
	 *
	 * @param ex
	 *          ex
	 * @return ResponseModel
	 */
	@ExceptionHandler(value = BaseAppException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseModel handlerError(BaseAppException ex)
	{
		logger.error("error", ex);
		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.BUSINESS_ERROR);
		resp.setErrorMsg(ex.getErrorMessage());
		return resp;
	}

}
