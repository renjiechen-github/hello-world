package com.room1000.timedtask.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.room1000.agreement.dao.AgreementDtoMapper;
import com.room1000.agreement.dto.AgreementDto;
import com.room1000.core.user.dao.UserDtoMapper;
import com.room1000.core.user.dto.UserDto;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.define.CancelLeaseOrderTypeDef;
import com.room1000.suborder.cancelleaseorder.define.ChannelDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.timedtask.dto.OrderCountDto;
import com.room1000.timedtask.dto.Staff4OrderCountDto;
import com.room1000.timedtask.service.IOrderTaskService;
import com.room1000.workorder.dao.OrderCommentaryTypeDtoMapper;
import com.room1000.workorder.dao.WorkOrderDtoMapper;
import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.OrderCommentaryTypeDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;
import com.ycdc.core.plugin.jpush.entity.PushMsgBean;
import com.ycqwj.ycqwjApi.request.PlushRequest;
import com.ycqwj.ycqwjApi.request.bean.plush.AppointPlushBean;
import com.ycqwj.ycqwjApi.request.bean.plush.PendingOrderNumBean;

import pccom.common.util.Constants;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月20日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("OrderTaskService")
public class OrderTaskServiceImpl implements IOrderTaskService
{

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderTaskServiceImpl.class);

	/**
	 * workOrderDtoMapper
	 */
	@Autowired
	private WorkOrderDtoMapper workOrderDtoMapper;

	/**
	 * agreementDtoMapper
	 */
	@Autowired
	private AgreementDtoMapper agreementDtoMapper;

	/**
	 * orderCommentaryDtoMapper
	 */
	@Autowired
	private OrderCommentaryTypeDtoMapper orderCommentaryTypeDtoMapper;

	/**
	 * userDtoMapper
	 */
	@Autowired
	private UserDtoMapper userDtoMapper;

	/**
	 * workOrderService
	 */
	@Autowired
	private IWorkOrderService workOrderService;

	/**
	 * cancelLeaseService
	 */
	@Autowired
	private ICancelLeaseOrderService cancelLeaseService;

	@Override
	public void autoOrderCommentaryWithTrans()
	{
		// 设置超时时间，先按照5天来计算，后续可以通过配置下进行配置
		final Long overtime = 5L;
		// 默认打分
		final Long defaultScore = 5L;
		List<WorkOrderDto> workOrderList = workOrderDtoMapper.selectOvertimeCommentWorkOrder(overtime);

		for (WorkOrderDto workOrderDto : workOrderList)
		{

			List<OrderCommentaryTypeDto> commentaryTypeList = orderCommentaryTypeDtoMapper
					.selectByOrderType(workOrderDto.getType());
			List<OrderCommentaryDto> commentaryList = new ArrayList<>();

			OrderCommentaryDto orderCommentaryDto = null;
			for (OrderCommentaryTypeDto orderCommentaryTypeDto : commentaryTypeList)
			{
				orderCommentaryDto = new OrderCommentaryDto();

				orderCommentaryDto.setCommentaryPersonId(null);
				orderCommentaryDto.setCommentDate(DateUtil.getDBDateTime());
				orderCommentaryDto.setScore(defaultScore);
				orderCommentaryDto.setType(orderCommentaryTypeDto.getType());
				orderCommentaryDto.setComments("系统自动好评");
				commentaryList.add(orderCommentaryDto);
			}

			workOrderService.workOrderCommentaryWithTrans(workOrderDto.getId(), commentaryList, SystemAccountDef.SYSTEM, "",
					"", "Y");
		}

	}

	@Override
	public void autoCreateCancelLeaseOrderWithTrans()
	{
		// 查询需要提前生成退租订单的时间参数
		String paramTime = agreementDtoMapper.selectTimeParam();
		if (null == paramTime)
		{
			paramTime = "15";
		}
		// 1. 查询所有符合条件的待退租出租合约
		List<AgreementDto> agreementList = agreementDtoMapper.selectAllOverdueAgreement(paramTime);
		for (AgreementDto agreementDto : agreementList)
		{
			// 3. 生成退租订单
			CancelLeaseOrderDto cancelLeaseOrderDto = new CancelLeaseOrderDto();
			cancelLeaseOrderDto.setRentalLeaseOrderId(Long.valueOf(agreementDto.getId()));
			// 退租时间，当前时间N（数据库配置的时间节点）天之后
      Calendar c = Calendar.getInstance();  
      c.setTime(new Date());  
      c.add(Calendar.DAY_OF_MONTH, Integer.valueOf(paramTime));// 今天+1天  
			cancelLeaseOrderDto.setCancelLeaseDate(c.getTime());
			cancelLeaseOrderDto.setChannel(ChannelDef.CHANNEL_PC);
			cancelLeaseOrderDto.setComments("系统自动生成");
			cancelLeaseOrderDto.setType(CancelLeaseOrderTypeDef.NORMAL_CANCEL_LEASE);

			WorkOrderDto workOrderDto = new WorkOrderDto();
			// 系统 创建
			workOrderDto.setCreatedStaffId(SystemAccountDef.SYSTEM);
			String userName = agreementDto.getUsernameBk();
			if (StringUtils.isEmpty(userName))
			{
				UserDto userDto = userDtoMapper.selectByPrimaryKey(agreementDto.getUserId());
				if (null == userDto)
				{
					// 如果为空
					continue;
				}
				userName = userDto.getUsername();
			}
			workOrderDto.setUserName(userName);
			workOrderDto.setUserPhone(agreementDto.getUserMobile());
			cancelLeaseService.inputSubOrderInfoWithTrans(cancelLeaseOrderDto, workOrderDto, SystemAccountDef.SYSTEM, CancelLeaseOrderTypeDef.NORMAL_CANCEL_LEASE);
		}
	}
	
	@Override
	public void autoSendHousekeeperDealWithMessage()
	{
		// 查询配置时间数
		String time = workOrderDtoMapper.querySendHousekeeperDealWithTimeCode();
		if (null == time)
		{
			time = "1";
		}
		List<Map<String, Object>> list = workOrderDtoMapper.queryHousekeeperDealWithInfo(time);
		for (Map<String, Object> map : list)
		{
	    Map<String, String> param = new HashMap<>();
	    param.put("messag_type", "1");
	    param.put("item_type", "5");
	    param.put("item_title", "工单系统-退租订单");
	    param.put("item_id", String.valueOf(map.get("id")));
	    param.put("item_code", String.valueOf(map.get("code")));
	    param.put("item_time", String.valueOf(map.get("created_date")));
	    param.put("comments", String.valueOf(map.get("sub_comments")));
	    String extrasparam = JSONObject.toJSONString(param);
	    
      AppointPlushBean appointPlushBean = new AppointPlushBean();
      appointPlushBean.setKey(Constants.YCQWJ_API);
      appointPlushBean.setContent("您有一份退租订单，需要上门结算，请注意查收");
      appointPlushBean.setId(String.valueOf(map.get("user_id")));
      appointPlushBean.setExreas_param(extrasparam);
      try {
          PlushRequest.appointPlush(appointPlushBean);
      } catch (IOException ex) {
          logger.error("消息发送失败：" + extrasparam + "--" + String.valueOf(map.get("user_id")), ex);
      } catch (NoSuchAlgorithmException ex) {
          logger.error("消息发送失败：" + extrasparam + "--" + String.valueOf(map.get("user_id")), ex);
      }
		}

	}

	@Override
	public void autoSendWait2DealWorkOrderMessage()
	{
		List<Staff4OrderCountDto> staff4OrderCountList = workOrderDtoMapper.selectStaff4OrderCount();
		logger.debug("staff4OrderCountList: " + staff4OrderCountList);
		List<PushMsgBean> pmbList = new ArrayList<>();
		PushMsgBean pmb = null;
		for (Staff4OrderCountDto staff4OrderCount : staff4OrderCountList)
		{
			pmb = new PushMsgBean();
			logger.debug("发送推送消息到用户手机号１");
			if (StringUtils.isEmpty(staff4OrderCount.getDealerName())
					|| StringUtils.isEmpty(staff4OrderCount.getDealerPhone()))
			{
				logger.info("推送的信息接收人的手机号或者姓名不能为空");
				continue;
			}
//			 pmb.setAlias(staff4OrderCount.getDealerPhone());
//			 pmb.setName(staff4OrderCount.getDealerName());
//			 pmb.setType(PushMsgBean.INNER_STAFF);
//			 pmb.setMsg(getMsg(staff4OrderCount));
//			 pmb.setModel(OrderPushModelDef.ORDER_COUNT);
//			 logger.info("pmb: " + pmb);
//			 pmbList.add(pmb);
			logger.debug("发送推送消息到用户手机号");
			PendingOrderNumBean pendingOrderNumBean = new PendingOrderNumBean();
			pendingOrderNumBean.setKey(Constants.YCQWJ_API);
			pendingOrderNumBean.setContent(getMsg(staff4OrderCount));
			pendingOrderNumBean.setId(String.valueOf(staff4OrderCount.getDealerId()));
			try
			{
				PlushRequest.pendingOrderNum(pendingOrderNumBean);
			} catch (IOException ex)
			{
				logger.error("消息发送失败：" + getMsg(staff4OrderCount) + "--" + staff4OrderCount.toString(), ex);
				continue;
			} catch (NoSuchAlgorithmException ex)
			{
				logger.error("消息发送失败：" + getMsg(staff4OrderCount) + "--" + staff4OrderCount.toString(), ex);
				continue;
			}
		}
	}

	/**
	 * 
	 * Description: 拼接需要构造的信息
	 * 
	 * @author jinyanan
	 *
	 * @param staff4OrderCount
	 *          staff4OrderCount
	 * @return String
	 */
	private String getMsg(Staff4OrderCountDto staff4OrderCount)
	{
		StringBuilder sb = new StringBuilder();
		Long total = 0L;
		// "您有N份未处理订单，其中包括A份退租订单，B份保洁订单"
		for (OrderCountDto orderCount : staff4OrderCount.getOrderCountList())
		{
			total += orderCount.getCnt();
			sb.append(orderCount.getCnt() + "份" + orderCount.getOrderTypeName() + ",");
		}
		sb.insert(0, "您有" + total + "份未处理订单，其中包括");
		if (sb.length() > 0)
		{
			return sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}

}
