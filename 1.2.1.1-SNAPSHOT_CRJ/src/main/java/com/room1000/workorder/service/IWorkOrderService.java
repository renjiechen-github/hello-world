package com.room1000.workorder.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.OrderCommentaryTypeDto;
import com.room1000.workorder.dto.OrderStepDto;
import com.room1000.workorder.dto.SubOrderStateDto;
import com.room1000.workorder.dto.TypeCountDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.dto.WorkOrderTypeDto;
import com.room1000.workorder.dto.request.AssignOrderRequest;
import com.room1000.workorder.dto.request.QryOrderCommentaryPagerListRequest;
import com.room1000.workorder.dto.request.QryWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.TeamWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.UpdateSubOrderRequest;
import com.room1000.workorder.dto.request.WorkOrderCommentaryRequest;

/**
 * 
 * Description:
 * 
 * Created on 2017年2月6日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IWorkOrderService
{

	/**
	 * 
	 * Description: 创建工单
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderDto
	 *          workOrderDto
	 * @return WorkOrderDto
	 */
	WorkOrderDto createWorkOrderWithTrans(WorkOrderDto workOrderDto);

	/**
	 * 
	 * Description: 根据id查询WorkOrder
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return WorkOrderDto
	 */
	WorkOrderDto getWorkOrderDtoById(Long id);

	/**
	 * 
	 * Description: 根据code查询WorkOrder
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @return WorkOrderDto
	 */
	WorkOrderDto getWorkOrderDtoByCode(String code);

	/**
	 * 
	 * Description: 更新工单信息
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderDto
	 *          workOrderDto
	 * @return WorkOrderDto
	 */
	WorkOrderDto updateWorkOrderWithTrans(WorkOrderDto workOrderDto);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderId
	 *          workOrderId
	 * @return WorkOrderDto
	 */
	WorkOrderDto getWorkOrderDetailById(Long workOrderId);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param request
	 *          request
	 * @param response
	 *          response
	 * @param user
	 */
	// void getWorkOrderList(HttpServletRequest request, HttpServletResponse
	// response, User user);

	/**
	 * 
	 * Description: 查询订单列表
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> getWorkOrderList(QryWorkOrderPagerListRequest req);

	/**
	 * 
	 * Description: 订单评价
	 * 
	 * @author jinyanan
	 * 
	 * @param workOrderId
	 *          workOrderId
	 * @param commentaryList
	 *          commentaryList
	 */
	void workOrderCommentaryWithTrans(Long workOrderId, List<OrderCommentaryDto> commentaryList, String imagrUrl, String realPath, String flag);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return List<OrderCommentaryDto>
	 */
	List<OrderCommentaryDto> getOrderCommentaryDto(QryOrderCommentaryPagerListRequest req);

	/**
	 * 
	 * Description: 修改订单评价
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 */
	void updateOrderCommentaryWithTrans(WorkOrderCommentaryRequest req);

	/**
	 * 
	 * Description: 删除订单评价
	 * 
	 * @author jinyanan
	 *
	 * @param orderCommentary
	 *          orderCommentary
	 */
	void deleteOrderCommentaryWithTrans(OrderCommentaryDto orderCommentary);

	/**
	 * 
	 * Description: 支付
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderId
	 *          workOrderId
	 * @param staffId
	 *          staffId
	 * @param paidMoney
	 *          paidMoney
	 */
	void payWithTrans(Long workOrderId, Long staffId, Long paidMoney);

	/**
	 * 
	 * Description: 修改子订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param realPath
	 *          realPath
	 * @param staffId
	 *          staffId
	 */
	void updateSubOrderWithTrans(UpdateSubOrderRequest req, String realPath, Long staffId);

	/**
	 * 
	 * Description: 查询所有订单类型
	 * 
	 * @author jinyanan
	 *
	 * @return List<WorkOrderTypeDto>
	 */
	List<WorkOrderTypeDto> getWorkOrderType(String roleIds);

	/**
	 * 
	 * Description: 关闭订单
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderId
	 *          workOrderId
	 * @param staffId
	 *          staffId
	 * @throws Exception
	 *           <br>
	 */
	void endProcessWithTrans(Long workOrderId, Long staffId) throws Exception;

	/**
	 * 
	 * Description: 指派订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param staffId
	 *          staffId
	 */
	void assignOrderWithTrans(AssignOrderRequest req, Long staffId);

	/**
	 * 
	 * Description: 查询所有订单状态
	 * 
	 * @author jinyanan
	 *
	 * @return List<SubOrderStateDto>
	 */
	List<SubOrderStateDto> getSubOrderState();

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param request
	 *          request
	 * @param response
	 *          response
	 */
	void getWorkOrderList(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 
	 * Description: 查询订单步骤
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderId
	 *          workOrderId
	 * @return List<OrderStepDto>
	 * @throws Exception
	 *           <br>
	 */
	List<OrderStepDto> getOrderStep(Long workOrderId) throws Exception;

	/**
	 * 
	 * Description: 查询历史
	 * 
	 * @author jinyanan
	 *
	 * @param <T>
	 *          <T>
	 * @param workOrderId
	 *          workOrderId
	 * @return List<T>
	 */
	<T> List<T> getHis(Long workOrderId);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param request
	 *          request
	 * @param response
	 *          response
	 */
	void getWorkOrderList4CustomerService(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 
	 * Description: 查询各个订单数量
	 * 
	 * @author jinyanan
	 *
	 * @return List<TypeCountDto>
	 */
	List<TypeCountDto> getTypeCount();

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @return List<OrderStepDto>
	 * @throws Exception
	 *           <br>
	 */
	List<OrderStepDto> getOrderStep(String code) throws Exception;

	/**
	 * 
	 * Description: 订单评价
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param orderCommentaryList
	 *          orderCommentaryList
	 */
	void workOrderCommentaryWithTrans(String code, List<OrderCommentaryDto> orderCommentaryList, String imagrUrl, String realPath, String flag);

	/**
	 * 
	 * Description: 订单评价
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @param commentaryList
	 *          commentaryList
	 * @param updatePersonId
	 *          updatePersonId
	 */
	void workOrderCommentaryWithTrans(Long id, List<OrderCommentaryDto> commentaryList, Long updatePersonId, String imagrUrl, String realPath, String flag);

	/**
	 * 
	 * Description: 生成订单名称
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderDto
	 *          workOrderDto
	 * @return String
	 */
	String generateOrderName(WorkOrderDto workOrderDto);

	/**
	 * 
	 * Description: 删除订单
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderDto
	 *          workOrderDto
	 */
	void deleteWorkOrder(WorkOrderDto workOrderDto);

	/**
	 * 
	 * Description: 查询所有的评价类型
	 * 
	 * @author jinyanan
	 *
	 * @param orderType
	 *          orderType
	 * @return List<OrderCommentaryTypeDto>
	 */
	List<OrderCommentaryTypeDto> getOrderCommentaryType(String orderType);

	/**
	 * 
	 * Description: 经纪人审批订单
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderId
	 *          workOrderId
	 * @param comments
	 *          comments
	 * @param staffId
	 *          staffId
	 * @param passed
	 *          passed
	 */
	void doAuditBrokerOrderWithTrans(Long workOrderId, String comments, boolean passed, Long staffId);

	/**
	 * 
	 * Description: 关闭经纪人推荐订单
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param staffId
	 *          staffId
	 */
	void closeBrokerOrder(String code, Long staffId);

	/**
	 * 
	 * Description: 获取子订单详情
	 * 
	 * @author jinyanan
	 *
	 * @param workOrderId
	 *          workOrderId
	 * @param singleDetail
	 *          singleDetail
	 * @return Object
	 */
	Object getSubOrderDetailById(Long workOrderId, Boolean singleDetail);

	/**
	 * 团队任务列表（PC端）
	 * 
	 * @param request
	 * @param response
	 */
	void getTeamWorkOrderList(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 团队任务列表（手机端）
	 * 
	 * @param req
	 * @return
	 */
	List<WorkOrderDto> getTeamWorkOrderList(TeamWorkOrderPagerListRequest req);

	/**
	 * 根据工单id，更改工单处理人
	 * 
	 * @param request
	 * @param response
	 */
	Object updateWorkOrderButlerById(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 按照权限获取订单类型列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Object getWorkOrderTypeByPermission(HttpServletRequest request, HttpServletResponse response);
}
