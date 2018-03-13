package com.ycdc.task.serv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;
import com.room1000.workorder.dto.WorkOrderDto;
import com.ycdc.core.base.BaseService;
import com.ycdc.task.dao.OrderTaskDAO;
import com.ycdc.task.entitys.Order;

/**
 * 定时查询所有有效合租合约信息，入库订单表
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2016年12月7日上午9:52:27
 */
@Service("orderTaskServ")
public class OrderTaskServImpl extends BaseService implements OrderTaskServ
{
	@Resource
	private OrderTaskDAO dao;

	@Autowired
	private IRoutineCleaningOrderService routineCleaningOrderService;

	/**
	 * 手工触发例行保洁订单入库（老版本服务模块）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public Object artificial(HttpServletRequest request, HttpServletResponse response)
	{
		orderTask();
		return "success";
	}

	/**
	 * 定时触发例行保洁订单入库（老版本服务模块）【每个月1号、15号凌晨执行】
	 */
	public void orderTask()
	{
		// 查询有效合租合约以及租客信息
		List<Order> info = getUserInfo();
		logger.info("符合入库订单表的信息数量：" + info.size());

		// 入库订单表
		insertOrderTable(info);
	}

	/**
	 * 手工触发例行保洁订单入库（新版本工作流服务模块）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public Object newArtificial(HttpServletRequest request, HttpServletResponse response)
	{
		orderNewTask();
		return "success";
	}
	
	/**
	 * 手工触发单个房间例行保洁订单入库（新版本工作流服务模块）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public Object singleNewArtificial(HttpServletRequest request, HttpServletResponse response)
	{
		// 合约ID
		String id = getValue(request, "id");
		
		// 查询有效合租合约以及租客信息
		List<Order> info = dao.getSingleUserInfo(id);
		logger.info("符合入库订单表的信息数量：" + info.size());

		// 入库订单表
		insertNewOrder(info);
		return null;
	}

	/**
	 * 定时触发例行保洁订单入库（新版本服务模块）【每个月1号、15号凌晨执行】
	 */
	public void orderNewTask()
	{
		// 查询有效合租合约以及租客信息
		List<Order> info = getUserInfo();
		logger.info("符合入库订单表的信息数量：" + info.size());

		// 入库订单表
		insertNewOrder(info);
	}

	/**
	 * 调用新流程接口
	 * 
	 * @param info
	 */
	private void insertNewOrder(List<Order> info)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = new RoutineCleaningOrderDto();
		WorkOrderDto workOrderDto = new WorkOrderDto();
		// 预约时间
		routineCleaningOrderDto.setAppointmentDate(new Date());
		for (Order order : info)
		{
			logger.info("order = " + order);
			
			routineCleaningOrderDto.setId(null);
			workOrderDto.setId(null);
			// 出租合约ID
			routineCleaningOrderDto.setRentalLeaseOrderId(Long.valueOf(order.getCorrespondent()));

			// 备注
			String desc = order.getArea_name() + "(" + order.getOrder_desc() + ")";
			routineCleaningOrderDto.setComments(desc);
			
			// 订单名称
			if (null == order.getName() || order.getName().equals(""))
			{
				workOrderDto.setName("例行保洁");
			} else 
			{
				workOrderDto.setName("例行保洁_" + order.getName());
			}
			
			workOrderDto.setUserName(order.getUser_name());
			workOrderDto.setUserPhone(order.getUser_mobile());

			routineCleaningOrderService.inputSubOrderInfoWithTrans(routineCleaningOrderDto, workOrderDto, SystemAccountDef.SYSTEM);
		}

	}

	/**
	 * 查询符合条件的房源以及租客信息
	 * 
	 * @return
	 */
	private List<Order> getUserInfo()
	{
		// 按照条件查询信息
		/*
		 * yc_agreement_tab 中 type=2（出租） status=2（已生效） isdelete=1（未删除）
		 * yc_houserank_tab 中 rank_type=1（合租）
		 */
		List<Order> info = dao.getUserInfo();

		return info;
	}

	/**
	 * 入库订单表
	 * 
	 * @param info
	 */
	private void insertOrderTable(List<Order> info)
	{
		for (Order order : info)
		{
			order.setName("例行保洁-" + order.getName());
			if (null == order.getUser_name() || order.getUser_name().equals("null"))
			{
				order.setUser_name("");
			}

			// 拼接描述
			String desc = order.getArea_name() + "(" + order.getOrder_desc() + ")";
			order.setOrder_desc(desc);

			// order_tab 表入库
			// 插入操作
			dao.insertAndGetId(order);

			// 拼装order_code
			assembleOrderCode(order);

			// 更改order_code
			dao.updateOrderCode(order);
		}
	}

	/**
	 * 组装order_code
	 * 
	 * @param order
	 */
	private void assembleOrderCode(Order order)
	{
		StringBuffer sb = new StringBuffer();

		// 时间
		String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
		// 用户ID
		String user_id = order.getUser_id();
		String order_type = "9";
		int id = order.getId();

		sb.append("O").append(time).append("U").append(user_id).append("T").append(order_type).append(id);

		order.setOrder_code(sb.toString());

	}

}
