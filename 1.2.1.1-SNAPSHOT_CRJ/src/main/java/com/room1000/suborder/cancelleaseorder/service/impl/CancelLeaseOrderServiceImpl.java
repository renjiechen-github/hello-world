package com.room1000.suborder.cancelleaseorder.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.agreement.dao.AgreementDtoMapper;
import com.room1000.agreement.dao.HouseRankDtoMapper;
import com.room1000.agreement.dto.AgreementDto;
import com.room1000.attr.define.AttrTypeDef;
import com.room1000.attr.dto.AttrCatgDto;
import com.room1000.attr.dto.AttrDto;
import com.room1000.attr.service.IAttrService;
import com.room1000.core.activiti.IProcessStart;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessStartImpl;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.user.dao.StaffDtoMapper;
import com.room1000.core.user.dto.StaffDto;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderDtoMapper;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderHisDtoMapper;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderValueDtoMapper;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderValueHisDtoMapper;
import com.room1000.suborder.cancelleaseorder.define.CancelLeaseOrderTypeDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderHisDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueHisDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderStateDef;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

import pccom.common.util.SpringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Financial.Result;
import pccom.web.interfaces.RankHouse;

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
@Service("CancelLeaseOrderService")
public class CancelLeaseOrderServiceImpl implements ICancelLeaseOrderService
{

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(CancelLeaseOrderServiceImpl.class);

	/**
	 * cancelLeaseOrderDtoMapper
	 */
	@Autowired
	private CancelLeaseOrderDtoMapper cancelLeaseOrderDtoMapper;

	/**
	 * cancelLeaseOrderValueDtoMapper
	 */
	@Autowired
	private CancelLeaseOrderValueDtoMapper cancelLeaseOrderValueDtoMapper;

	/**
	 * cancelLeaseOrderHisDtoMapper
	 */
	@Autowired
	private CancelLeaseOrderHisDtoMapper cancelLeaseOrderHisDtoMapper;

	/**
	 * staffDtoMapper
	 */
	@Autowired
	private StaffDtoMapper staffDtoMapper;

	/**
	 * cancelLeaseOrderDtoMapper
	 */
	@Autowired
	private IWorkOrderService workOrderService;

	/**
	 * attrService
	 */
	@Autowired
	private IAttrService attrService;

	/**
	 * cancelLeaseOrderValueHisDtoMapper
	 */
	@Autowired
	private CancelLeaseOrderValueHisDtoMapper cancelLeaseOrderValueHisDtoMapper;

	@Autowired
	private AgreementDtoMapper agreementDtoMapper;

	@Autowired
	private HouseRankDtoMapper houseRankDtoMapper;

	@Override
	public void inputSubOrderInfoWithTrans(CancelLeaseOrderDto cancelLeaseOrderDto, WorkOrderDto workOrderDto, Long staffId, String str)
	{
		if (this.checkCancelLeaseOrderWithTrans(cancelLeaseOrderDto.getRentalLeaseOrderId()))
		{
			return;
		}
		
		// 退租类型判断规则
		if (!str.equals(""))
		{
			// 3.系统自动发起的则是正常退租
			cancelLeaseOrderDto.setType(CancelLeaseOrderTypeDef.NORMAL_CANCEL_LEASE);
		} else 
		{
			AgreementDto agreementDto = agreementDtoMapper.selectByPrimaryKey(cancelLeaseOrderDto.getRentalLeaseOrderId());
			if (agreementDto.getStatus().equals("12"))
			{
				// 1.在合约还在生效待支付状态进行退租，自动生成未入住违约退租的订单
				cancelLeaseOrderDto.setType(CancelLeaseOrderTypeDef.BREAK_CONTRACT_WITHOUT_LIVING);
			} else
			{
				// 当前时间
				String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
				String endTime = agreementDto.getEndTime().split(" ")[0].replace("-", "");
				if (agreementDto.getStatus().equals("2") && Integer.valueOf(endTime) > Integer.valueOf(nowTime))
				{
					// 2.合约已生效未到合约结束时间就手动发起的退租订单都是已入住违约退租
					cancelLeaseOrderDto.setType(CancelLeaseOrderTypeDef.BREAK_CONTRACT_WITH_LIVING);
				} else
				{
					// 合约到期后发起是正常退租
					cancelLeaseOrderDto.setType(CancelLeaseOrderTypeDef.NORMAL_CANCEL_LEASE);
				}
			}
		}

		// 备注中，增加退租时间
    String comments = cancelLeaseOrderDto.getComments();
		Date date = cancelLeaseOrderDto.getCancelLeaseDate();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    String realTime = df.format(date);
    comments = "退租时间：" + realTime + "，" + comments;
    cancelLeaseOrderDto.setComments(comments);
    workOrderDto.setSubComments(comments);
		workOrderDto.setRentalLeaseOrderId(cancelLeaseOrderDto.getRentalLeaseOrderId());
		workOrderDto.setAppointmentDate(cancelLeaseOrderDto.getCancelLeaseDate());
		workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

		workOrderService.createWorkOrderWithTrans(workOrderDto);

		cancelLeaseOrderDto.setWorkOrderId(workOrderDto.getId());
		cancelLeaseOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
		cancelLeaseOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
		cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		cancelLeaseOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		cancelLeaseOrderDto.setActualDealerId(staffId);
		cancelLeaseOrderDto.setUpdatePersonId(staffId);
		cancelLeaseOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("CANCEL_LEASE_ORDER_PROCESS");
		cancelLeaseOrderDto.setAttrCatgId(attrCatg.getId());
		cancelLeaseOrderDtoMapper.insert(cancelLeaseOrderDto);
		this.updateSubOrderWithTrans(cancelLeaseOrderDto, staffId, "");

		workOrderDto.setType(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER);
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			String orderName = workOrderService.generateOrderName(workOrderDto);
			workOrderDto.setName(orderName);
		}
		workOrderDto.setRefId(cancelLeaseOrderDto.getId());
		workOrderDto.setCode(cancelLeaseOrderDto.getCode());
		workOrderDto.setSubActualDealerId(cancelLeaseOrderDto.getActualDealerId());
		workOrderDto.setSubComments(cancelLeaseOrderDto.getComments());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		// 删除订单录入时，直接赋值合约退租时间。退租时间的添加放到【管家上门】环节。由管家选择填写。
		// updateAgreement(cancelLeaseOrderDto);

		String processInstanceKey = "CancelLeaseOrderProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
		variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
		variables.put(ActivitiVariableDef.CANCEL_LEASE_ORDER, cancelLeaseOrderDto);

		// 新退租订单流程
		if (WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER.equals(workOrderDto.getType()))
		{
			String cancelType = cancelLeaseOrderDto.getType();
			logger.info("退租类型 = " + cancelType);
			// 未入住违约退租
			if (cancelType.equals(CancelLeaseOrderTypeDef.BREAK_CONTRACT_WITHOUT_LIVING))
			{
				cancelLeaseOrderDto.clearAssignedDealer();
				// 高管角色
				cancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_SUPER_MANAGER);
				cancelLeaseOrderDto.setState(SubOrderStateDef.MARKETING_EXECUTIVE_AUDITING);
				cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
				this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");

				workOrderDto.clearSubField();
				workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_SUPER_MANAGER);
				workOrderDto.setSubOrderState(SubOrderStateDef.MARKETING_EXECUTIVE_AUDITING);
				workOrderService.updateWorkOrderWithTrans(workOrderDto);
			} else
			{
				assignSubOrderWithTrans(workOrderDto.getCode());
			}
		} else
		{
			IProcessStart start = new ProcessStartImpl();
			start.flowStart(processInstanceKey, variables);
		}

	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str)
	{
		return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) subOrderDto;
		cancelLeaseOrderDto.setUpdatePersonId(updatePersonId);
		cancelLeaseOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		return this.updateSubOrderAndAddHis(cancelLeaseOrderDto, closeOrder, str);
	}

	/**
	 * 
	 * Description: 更新退租单的同时，插入历史表
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderDto
	 *          cancelLeaseOrderDto
	 * @param closeOrder
	 *          closeOrder
	 * @return CancelLeaseOrderDto
	 */
	private Long updateSubOrderAndAddHis(CancelLeaseOrderDto cancelLeaseOrderDto, boolean closeOrder, String str)
	{
		// 订单数据更新到流程中
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(cancelLeaseOrderDto.getWorkOrderId(), ActivitiVariableDef.CANCEL_LEASE_ORDER, cancelLeaseOrderDto);

		CancelLeaseOrderDto oldCancelLeaseOrderDto = cancelLeaseOrderDtoMapper.selectByPrimaryKey(cancelLeaseOrderDto.getId());
		if (oldCancelLeaseOrderDto == null)
		{
			oldCancelLeaseOrderDto = cancelLeaseOrderDtoMapper.selectByCode(cancelLeaseOrderDto.getCode());
		}
		oldCancelLeaseOrderDto.setUpdatePersonId(cancelLeaseOrderDto.getUpdatePersonId());
		oldCancelLeaseOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		if (closeOrder)
		{
			oldCancelLeaseOrderDto.setState(SubOrderStateDef.CLOSED);
			oldCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		cancelLeaseOrderDtoMapper.updateByPrimaryKey(cancelLeaseOrderDto);
		CancelLeaseOrderHisDto hisDto = SubOrderUtil.createHisDto(oldCancelLeaseOrderDto, CancelLeaseOrderHisDto.class, getPriority(oldCancelLeaseOrderDto.getId()));
		if (BooleanFlagDef.BOOLEAN_TRUE.equals(cancelLeaseOrderDto.getChangeFlag()))
		{
			hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
			hisDto.setStateDate(DateUtil.getDBDateTime());
			hisDto.setUpdateDate(DateUtil.getDBDateTime());
		}
		cancelLeaseOrderHisDtoMapper.insert(hisDto);
		return hisDto.getPriority();
	}

	/**
	 * 
	 * Description: 获取本次历史对应的priority
	 * 
	 * @author jinyanan
	 *
	 * @param subOrderId
	 *          subOrderId
	 * @return Long
	 */
	private Long getPriority(Long subOrderId)
	{
		Long priority = cancelLeaseOrderHisDtoMapper.selectMaxPriority(subOrderId);
		if (priority == null)
		{
			return 1L;
		} else
		{
			return priority + 1;
		}
	}

	@Override
	public CancelLeaseOrderDto getSubOrderByCode(String code)
	{
		return cancelLeaseOrderDtoMapper.selectByCode(code);
	}

	@Override
	public void updateSubOrderStateWithTrans(String code, String state)
	{
		CancelLeaseOrderDto record = this.getSubOrderByCode(code);
		record.setState(state);
		record.setStateDate(DateUtil.getDBDateTime());
		this.updateSubOrderWithTrans(record, null, "");

	}

	/**
	 * 
	 * Description: 修改订单当前处理人
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param actualDealerId
	 *          actualDealerId
	 * @return Long
	 */
	private Long updateSubOrderActualDealer(String code, Long actualDealerId)
	{
		CancelLeaseOrderDto record = this.getSubOrderByCode(code);
		record.setActualDealerId(actualDealerId);
		Long priority = this.updateSubOrderWithTrans(record, actualDealerId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(actualDealerId);
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		return priority;
	}

	@Override
	public void assignSubOrderWithTrans(String code)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		List<StaffDto> staffList = staffDtoMapper.selectByAgreementId(cancelLeaseOrderDto.getRentalLeaseOrderId());
		StaffDto staff = null;
		if (null == staffList || staffList.isEmpty())
		{
			staff = new StaffDto();
			// 查询服务团队负责人id
			staff.setId(Long.valueOf(SystemConfig.getValue("TEAM_LEADER_ID")));
		} else
		{
			staff = staffList.get(0);
		}
		cancelLeaseOrderDto.setButlerId(staff.getId());
		cancelLeaseOrderDto.clearAssignedDealer();
		cancelLeaseOrderDto.setAssignedDealerId(staff.getId());
		cancelLeaseOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
		cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(cancelLeaseOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
		workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		// 新退租订单流程
		if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			workOrderDto = workOrderService.getWorkOrderDtoById(workOrderDto.getId());
			cancelLeaseOrderDto = getOrderDetailById(workOrderDto.getRefId(), true);
			updateSubOrderStateWithTrans(cancelLeaseOrderDto.getCode(), SubOrderStateDef.TAKE_ORDER);
			workOrderDto.clearSubField();
			workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
			workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
			workOrderService.updateWorkOrderWithTrans(workOrderDto);
			SubOrderUtil.sendMessage(workOrderDto, cancelLeaseOrderDto.getAssignedDealerId(), OrderPushModelDef.NEW_CANCEL_LEASE_ORDER);
		}

	}

	@Override
	public void reAssignSubOrderWithTrans(String code, Long butlerId, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		CancelLeaseOrderDto record = this.getSubOrderByCode(code);
		record.setButlerId(butlerId);
		record.clearAssignedDealer();
		record.setAssignedDealerId(butlerId);
		record.setActualDealerId(staffId);
		record.setState(SubOrderStateDef.REASSIGNING);
		record.setStateDate(DateUtil.getDBDateTime());
		Long priority = this.updateSubOrderWithTrans(record, staffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setStaffId(butlerId);
		workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
		workOrderDto.setSubActualDealerId(staffId);
		workOrderDto.setSubAssignedDealerId(butlerId);
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		// 第二次更新为了插入历史表
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		this.insertSubOrderValue(code, valueList, priority);
		SubOrderUtil.sendMessage(workOrderDto, record.getAssignedDealerId(), OrderPushModelDef.CANCEL_LEASE_ORDER);
	}

	@Override
	public void takeOrderWithTrans(String code, List<CancelLeaseOrderValueDto> cancelLeaseOrderValueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, cancelLeaseOrderValueList, priority);

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
			cancelLeaseOrderDto.clearAssignedDealer();
			cancelLeaseOrderDto.setAssignedDealerId(cancelLeaseOrderDto.getButlerId());
			cancelLeaseOrderDto.setState(SubOrderStateDef.RELEASE_HOUSE_RANK);
			cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
			this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
			workOrderDto.clearSubField();
			workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getButlerId());
			workOrderDto.setSubOrderState(SubOrderStateDef.RELEASE_HOUSE_RANK);
			workOrderService.updateWorkOrderWithTrans(workOrderDto);
			SubOrderUtil.sendMessage(workOrderDto, cancelLeaseOrderDto.getAssignedDealerId(), OrderPushModelDef.NEW_CANCEL_LEASE_ORDER);
		} else
		{
			SubOrderUtil.dispatcherOrder(code);
		}
	}

	@Override
	public void butlerGetHomeWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId, String realPath, Boolean submitFlag)
	{
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		CancelLeaseOrderDto record = this.getSubOrderByCode(code);
		String realTime = "";
		for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
		{
			if (cancelLeaseOrderValueDto.getAttrPath().equals("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ACTUAL_CANCEL_LEASE_TIME"))
			{
				realTime = cancelLeaseOrderValueDto.getTextInput();
				break;
			}
		}

    if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
    {
      String comments = record.getComments();
      if (comments.contains("退租时间"))
      {
        comments = comments.substring(comments.indexOf("，") + 1, comments.length());
      }
      comments = "退租时间：" + realTime + "，" + comments;
      cancelLeaseOrderDtoMapper.updateCommentsById(record.getId(), comments);
      cancelLeaseOrderDtoMapper.updateSubCommentsById(workOrderDto.getId(), comments);
    }

		if (submitFlag)
		{
			try
			{
				// 账单冻结
				Financial financial = (Financial) SpringHelper.getBean("financial");
				logger.info("冻结账单 入参 合约id=" + String.valueOf(record.getRentalLeaseOrderId()));
				if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
				{
					// 新退租流程
					Result info = financial.freezeBill(String.valueOf(record.getRentalLeaseOrderId()));
					if (info.getState() != 1)
					{
						throw new BaseAppException("账单冻结失败");
					}
				}
				if (workOrderDto.getType().equals(WorkOrderTypeDef.CANCEL_LEASE_ORDER))
				{
					// 老退租流程
					int result = financial.stateBill(String.valueOf(record.getRentalLeaseOrderId()), "1");
					if (result <= 0)
					{
						throw new BaseAppException("账单冻结失败");
					}
				}
			} catch (Exception e)
			{
				throw new BaseAppException("账单冻结失败");
			}
			// 冻结成功，修改对应出租合约退租时间
			if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
			{
				String checkouttime = "";
				// 查询合约开始时间
				AgreementDto  dto = agreementDtoMapper.selectByPrimaryKey(workOrderDto.getRentalLeaseOrderId());
				String beginTime = dto.getBeginTime();
				for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
				{
					if (cancelLeaseOrderValueDto.getAttrPath().equals("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ACTUAL_CANCEL_LEASE_TIME"))
					{
						checkouttime = cancelLeaseOrderValueDto.getTextInput();
						int len = checkouttime.split(":").length;
						if (len == 2)
						{
							checkouttime += ":00";
						}
						break;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dt1 = new Date();
        Date dt2 = new Date();
        Date lastDate = new Date();
				try
				{
					dt1 = df.parse(checkouttime);
					dt2 = df.parse(beginTime);
				} catch (ParseException e)
				{
					throw new BaseAppException("时间转换失败");
				}
        if (dt1.getTime() < dt2.getTime())
        {
        	throw new BaseAppException("实际退租时间不能小于合约开始时间");
        }
        // 判断上期最后时间是否大于实际退租时间
        String lastDateStr = cancelLeaseOrderDtoMapper.getLastDate(workOrderDto.getRentalLeaseOrderId());
        if (StringUtils.isNotBlank(lastDateStr) && !lastDateStr.equals("null"))
        {
  				try
  				{
  					lastDate = df.parse(lastDateStr);
  				} catch (ParseException e)
  				{
  					throw new BaseAppException("时间转换失败");
  				}
          if (dt1.getTime() < lastDate.getTime())
          {
          	throw new BaseAppException("实际退租时间不能小于上期缴费结束时间【"+lastDateStr+"】");
          }
        }
        Long priority = this.updateSubOrderActualDealer(code, staffId);
				this.insertSubOrderValue(code, valueList, realPath, priority);
				AgreementDto agreementDto = new AgreementDto();
				agreementDto.setId(record.getRentalLeaseOrderId());
				agreementDto.setCheckouttime(dt1);
				agreementDtoMapper.updateByPrimaryKeySelective(agreementDto);
				// 新退租订单流程
				this.updateSubOrderStateWithTrans(record.getCode(), SubOrderStateDef.COST_LIQUIDATION);
				workOrderDto.setSubOrderState(SubOrderStateDef.COST_LIQUIDATION);
				workOrderDto.setSubAssignedDealerId(record.getAssignedDealerId());
				workOrderService.updateWorkOrderWithTrans(workOrderDto);
				// 判断退租类型
				String time = cancelLeaseOrderDtoMapper.selectGraceTime();
				if (StringUtils.isBlank(time) || time.equals("null"))
				{
					time = "0";
				}
				if (checkouttime.contains(" "))
				{
					checkouttime = checkouttime.split(" ")[0];
				}
				// 合约结束时间
				String endTime = dto.getEndTime();
				Date endDate = new Date();
				Date checkouttimeDate = new Date();
				df = new SimpleDateFormat("yyyy-MM-dd");
				try
				{
					endDate = df.parse(endTime);
					checkouttimeDate = df.parse(checkouttime);
				} catch (ParseException e)
				{
					throw new BaseAppException("获取合约结束时间失败");
				}
				long day = (checkouttimeDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
				// 实际退租时间-合约结束时间 < 退租宽限期天数
				String type = "";
				if (day < Long.valueOf(time))
				{
					// 违约退租
					type = CancelLeaseOrderTypeDef.BREAK_CONTRACT_WITH_LIVING;
				} else
				{
					// 正常退租
					type = CancelLeaseOrderTypeDef.NORMAL_CANCEL_LEASE;
				}
				cancelLeaseOrderDtoMapper.updateCancelLeaseType(record.getId(),type);
			} else
			{
				Long priority = this.updateSubOrderActualDealer(code, staffId);
				this.insertSubOrderValue(code, valueList, realPath, priority);
				SubOrderUtil.dispatcherOrder(code);
			}
		} else 
		{
			if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
			{
				String checkouttime = "";
				// 查询合约开始时间
				AgreementDto  dto = agreementDtoMapper.selectByPrimaryKey(workOrderDto.getRentalLeaseOrderId());
				String beginTime = dto.getBeginTime();
				for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
				{
					if (cancelLeaseOrderValueDto.getAttrPath().equals("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ACTUAL_CANCEL_LEASE_TIME"))
					{
						checkouttime = cancelLeaseOrderValueDto.getTextInput();
						int len = checkouttime.split(":").length;
						if (len == 2)
						{
							checkouttime += ":00";
						}
						break;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dt1 = new Date();
        Date dt2 = new Date();
        Date lastDate = new Date();
				try
				{
					dt1 = df.parse(checkouttime);
					dt2 = df.parse(beginTime);
				} catch (ParseException e)
				{
					throw new BaseAppException("时间生产失败");
				}
        if (dt1.getTime() < dt2.getTime())
        {
        	throw new BaseAppException("实际退租时间不能小于合约开始时间");
        }
        // 判断上期最后时间是否大于实际退租时间
        String lastDateStr = cancelLeaseOrderDtoMapper.getLastDate(workOrderDto.getRentalLeaseOrderId());
        if (StringUtils.isNotBlank(lastDateStr) && !lastDateStr.equals("null"))
        {
  				try
  				{
  					lastDate = df.parse(lastDateStr);
  				} catch (ParseException e)
  				{
  					throw new BaseAppException("时间转换失败");
  				}
          if (dt1.getTime() < lastDate.getTime())
          {
          	throw new BaseAppException("实际退租时间不能小于上期缴费结束时间【"+lastDateStr+"】");
          }
        }        
			}
			Long priority = this.updateSubOrderActualDealer(code, staffId);
			this.insertSubOrderValue(code, valueList, realPath, priority);
		}		
	}

	@Override
	public void releaseHouseRank(String code, List<CancelLeaseOrderValueDto> cancelLeaseOrderValueList, Long staffId, Date butlerGetHouseDate)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);

		// 主要解决员工处理重新指派订单，二次释放房源的问题
		// 释放房源，先检查该订单历史表中是否存在房源释放步骤，如果存在，则直接进到下一步 孙诚明 2017-10-28
		List<CancelLeaseOrderHisDto> hisList = cancelLeaseOrderHisDtoMapper.selectById(cancelLeaseOrderDto.getId());
		boolean flag = false;
		for (CancelLeaseOrderHisDto hisDto : hisList)
		{
			String state = hisDto.getState();
			if (state.equals("U"))
			{
				// 存在房源释放
				flag = true;
				break;
			}
		}
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, cancelLeaseOrderValueList, priority);
		RankHouse rankHouse = (RankHouse) SpringHelper.getBean("rankHouse");
		Map<String, String> params = new HashMap<>();
		params.put("agreeid", cancelLeaseOrderDto.getRentalLeaseOrderId().toString());
		params.put("servicetime", DateUtil.date2String(cancelLeaseOrderDto.getCancelLeaseDate(), DateUtil.DATE_FORMAT_1));
		params.put("houseid", cancelLeaseOrderDto.getHouseId().toString());
		params.put("oper", staffId.toString());
		params.put("rankid", String.valueOf(cancelLeaseOrderDto.getHouseRankId()));
		params.put("type", workOrderDto.getType());
		int resp;
		try
		{
			if (!flag)
			{
				resp = rankHouse.managerDone(params);
				if (resp != 1)
				{
					throw new BaseAppException("释放房源失败");
				}
			}
		} catch (ParseException e)
		{
			throw new BaseAppException("释放房源失败");
		}

		if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 单独更新管家上门时间
			cancelLeaseOrderDto.setButlerGetHouseDate(butlerGetHouseDate);
			this.cancelLeaseOrderDtoMapper.updateButlerGetHouseDateByCode(cancelLeaseOrderDto);
			// 新退租订单流程
			this.updateSubOrderStateWithTrans(cancelLeaseOrderDto.getCode(), SubOrderStateDef.DO_IN_ORDER);
			workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
			workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
			workOrderService.updateWorkOrderWithTrans(workOrderDto);
		} else
		{
			SubOrderUtil.dispatcherOrder(code);
		}
	}

	@Override
	public void rentalCostLiquidationBtnWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);

		// 账单保存
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		try
		{
			Financial financial = (Financial) SpringHelper.getBean("financial");
			logger.info("保存账单 入参 订单id=" + cancelLeaseOrderDto.getWorkOrderId());
			Result a = financial.saveOrder(String.valueOf(cancelLeaseOrderDto.getWorkOrderId()));
			logger.info("保存账单 返回值 result（0：失败，1：成功） == " + a.getState());
			if (a.getState() != 1)
			{
				throw new BaseAppException("账单保存失败");
			}
		} catch (Exception e)
		{
			throw new BaseAppException("账单保存失败");
		}

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.clearSubField();
		cancelLeaseOrderDto.clearAssignedDealer();
		// 租务角色
		cancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_RENTAL);
		cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		workOrderDto.setSubAssignedDealerRoleId(cancelLeaseOrderDto.getAssignedDealerRoleId());
		cancelLeaseOrderDto.setState(SubOrderStateDef.RENTAL_ACCOUNTING);
		workOrderDto.setSubOrderState(SubOrderStateDef.RENTAL_ACCOUNTING);
		this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
	}

	@Override
	public void rentalAccountWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 调用财务接口，判断该合约退租账单是否已付
			Financial financial = (Financial) SpringHelper.getBean("financial");
			logger.info("开始调用财务接口，判断该合约退租账单是否已经支付（true：未支付，false：已支付）");
			boolean info = financial.checkTzBillPayState(String.valueOf(workOrderDto.getRentalLeaseOrderId()));
			logger.info("结束调用财务接口，判断该合约退租账单是否已经支付（true：未支付，false：已支付） result = " + info);
			if (!info)
			{
				Map<String, String> param = new HashMap<>();
				param.put("agreement_id", String.valueOf(workOrderDto.getRentalLeaseOrderId()));
				// 调用租赁房源审批失败接口
				int a = financial.repealRentHouse(param, null);
				if (a == 1)
				{
					// 已支付，订单直接到用户评价
					cancelLeaseOrderDto.clearAssignedDealer();
					cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
					cancelLeaseOrderDto.setState(SubOrderStateDef.COMMENT);
					cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
					this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
					workOrderDto.clearSubField();
					workOrderDto.setState(WorkOrderStateDef.WAIT_2_COMMENT);
					workOrderDto.setStateDate(DateUtil.getDBDateTime());
					workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
					workOrderDto.setSubOrderState(SubOrderStateDef.COMMENT);
					workOrderService.updateWorkOrderWithTrans(workOrderDto);
					// 合约置为已失效
					agreementDtoMapper.failureAgreementState(workOrderDto.getRentalLeaseOrderId());
					SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.NEW_CANCEL_LEASE_ORDER);
				}
			} else
			{
				// 未支付，订单到等待支付
				// 查询是收入还是支出
        CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
        cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.FINANCE_TYPE");
				cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
				cancelLeaseOrderValueDto = this.selectByAttrPath(cancelLeaseOrderValueDto);
				
				cancelLeaseOrderDto.clearAssignedDealer();
				workOrderDto.clearSubField();
				if ("1".equals(cancelLeaseOrderValueDto.getTextInput()))
				{
					cancelLeaseOrderDto.setState(SubOrderStateDef.REFUND);
					cancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
					workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
				} else
				{
					cancelLeaseOrderDto.setState(SubOrderStateDef.PAY);
					cancelLeaseOrderDto.setAssignedDealerRoleId(SystemAccountDef.CUSTOMER);
					workOrderDto.setSubAssignedDealerRoleId(SystemAccountDef.CUSTOMER);
				}
				cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
				// 客户
				cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
				workOrderDto.setSubOrderState(cancelLeaseOrderDto.getState());
				workOrderDto.setState(WorkOrderStateDef.WAIT_2_PAY);
				workOrderDto.setStateDate(DateUtil.getDBDateTime());
				workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
				this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
				workOrderService.updateWorkOrderWithTrans(workOrderDto);
			}
		} else
		{
			SubOrderUtil.dispatcherOrder(code);
		}
	}

	@Override
	public void marketingExecutiveAuditWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		// 判断是否审批通过
		Boolean passed = false;
		for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
		{
			if ("CANCEL_LEASE_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.PASSED".equals(cancelLeaseOrderValueDto.getAttrPath()) && BooleanFlagDef.BOOLEAN_TRUE.equals(cancelLeaseOrderValueDto.getTextInput()))
			{
				passed = true;
				break;
			}
		}
		// 如果审批通过
		// 释放房源
		// 如果为未入住违约退租，则订单关闭
		if (passed)
		{
			if (CancelLeaseOrderTypeDef.BREAK_CONTRACT_WITHOUT_LIVING.equals(cancelLeaseOrderDto.getType()))
			{
				cancelLeaseOrderDto.clearAssignedDealer();
				cancelLeaseOrderDto.setState(SubOrderStateDef.CLOSED);
				cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
				this.updateSubOrderWithTrans(cancelLeaseOrderDto, staffId, "");
				WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(cancelLeaseOrderDto.getWorkOrderId());
				workOrderDto.clearSubField();
				workOrderDto.setState(WorkOrderStateDef.DONE);
				workOrderDto.setStateDate(DateUtil.getDBDateTime());
				workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
				workOrderService.updateWorkOrderWithTrans(workOrderDto);
				this.releaseHouseRank(cancelLeaseOrderDto, staffId, true);

				// 账单冻结，由雷杨提供接口 2017-12-21 孙诚明
				Financial financial = (Financial) SpringHelper.getBean("financial");
				logger.info("未入住违约冻结账单 入参 合约id=" + String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
				int result = financial.stateBill(String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()), "1");
				logger.info("未入住违约冻结账单 返回值 result（0：失败，1：成功） == " + result);

				return;
			} else
			{
				// 判断租务核算金额是否为0，如果是0，不生成账单，直接进入评价环节
				// 财务审批时，判断租务审核的金额是否是0，如果是0，则直接进入待评价环节
				String cost = this.getCost(cancelLeaseOrderDto.getId());
				logger.info("财务审批 cost == " + cost);
				if (cost.contains("."))
				{
					cost = cost.split("\\.")[0];
				}
				if (cost.equals("0"))
				{
					logger.info("待评论");
					WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		      cancelLeaseOrderDto.clearAssignedDealer();
		      cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
		      cancelLeaseOrderDto.setState(SubOrderStateDef.COMMENT);
		      cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		      this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
		      workOrderDto.clearSubField();
		      workOrderDto.setState(WorkOrderStateDef.WAIT_2_COMMENT);
		      workOrderDto.setStateDate(DateUtil.getDBDateTime());
		      workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
		      workOrderDto.setSubOrderState(SubOrderStateDef.COMMENT);
		      workOrderService.updateWorkOrderWithTrans(workOrderDto);
		      // 合约置为已失效
		      agreementDtoMapper.failureAgreementState(workOrderDto.getRentalLeaseOrderId());
		      SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.CANCEL_LEASE_ORDER);
				} else 
				{
					// 其他退租订单生成财务明细
					cancelLeaseOrderDto = this.getSubOrderByCode(code);
					Financial financial = (Financial) SpringHelper.getBean("financial");
					Map<String, String> params = new HashMap<>();
					// 合约id
					params.put("agreement_id", cancelLeaseOrderDto.getRentalLeaseOrderId().toString());
					// 金额
					params.put("cost", this.getCost(cancelLeaseOrderDto.getId()));
					// 当前操作人
					params.put("operid", staffId.toString());
					// 订单id
					params.put("order_id", cancelLeaseOrderDto.getWorkOrderId().toString());
					int resp = financial.orderPayNew(params);
					if (resp < 1)
					{
						throw new BaseAppException("高管审批通过失败");
					}
				}
			}
		}

		// 修改当前处理人
		Long priority = this.updateSubOrderActualDealer(code, staffId);

		// 插入orderValue
		this.insertSubOrderValue(code, valueList, priority);

		SubOrderUtil.dispatcherOrder(code);
	}

	/**
	 * 
	 * Description: 释放房源
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderDto
	 *          cancelLeaseOrderDto
	 * @param staffId
	 *          staffId
	 * @param breakContractWithoutLivingFlag
	 *          breakContractWithoutLivingFlag
	 */
	private void releaseHouseRank(CancelLeaseOrderDto cancelLeaseOrderDto, Long staffId, Boolean breakContractWithoutLivingFlag)
	{

		Map<String, String> params = new HashMap<>();
		params.put("houseid", String.valueOf(cancelLeaseOrderDto.getHouseId()));
		params.put("oper", String.valueOf(staffId));
		params.put("rankid", String.valueOf(cancelLeaseOrderDto.getHouseRankId()));
		params.put("agreement_id", String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
		params.put("servicetime", DateUtil.date2String(cancelLeaseOrderDto.getCancelLeaseDate(), DateUtil.DATETIME_FORMAT_1));
		// 未入住违约退租 标记
		if (breakContractWithoutLivingFlag)
		{
			params.put("breakContractWithoutLivingFlag", "1");
		}
		RankHouse rankHouse = (RankHouse) SpringHelper.getBean("rankHouse");
		int resp;
		try
		{
			resp = rankHouse.approveNo(params);
			if (resp != 1)
			{
				throw new BaseAppException("释放房源失败");
			}
		} catch (ParseException e)
		{
			throw new BaseAppException("释放房源失败");
		}
	}

	@Override
	public void financeAuditWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);
		for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
		{
			if ("CANCEL_LEASE_ORDER_PROCESS.FINANCE_AUDIT.PASSED".equals(cancelLeaseOrderValueDto.getAttrPath()) && BooleanFlagDef.BOOLEAN_TRUE.equals(cancelLeaseOrderValueDto.getTextInput()))
			{
				break;
			}
		}
		// 财务审批时，判断租务审核的金额是否是0，如果是0，则直接进入待评价环节
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		String cost = this.getCost(cancelLeaseOrderDto.getId());
		logger.info("财务审批 cost == " + cost);
		if (cost.contains("."))
		{
			cost = cost.split("\\.")[0];
		}
		if (cost.equals("0"))
		{
			logger.info("待评论");
			WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
      cancelLeaseOrderDto.clearAssignedDealer();
      cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
      cancelLeaseOrderDto.setState(SubOrderStateDef.COMMENT);
      cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
      this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
      workOrderDto.clearSubField();
      workOrderDto.setState(WorkOrderStateDef.WAIT_2_COMMENT);
      workOrderDto.setStateDate(DateUtil.getDBDateTime());
      workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
      workOrderDto.setSubOrderState(SubOrderStateDef.COMMENT);
      workOrderService.updateWorkOrderWithTrans(workOrderDto);
      // 合约置为已失效
      agreementDtoMapper.failureAgreementState(workOrderDto.getRentalLeaseOrderId());      
      SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.CANCEL_LEASE_ORDER);
		} else
		{
			SubOrderUtil.dispatcherOrder(code);
		}
	}

	/**
	 * 
	 * Description: 查询费用
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 *          cancelLeaseOrderId
	 * @return String
	 */
	private String getCost(Long cancelLeaseOrderId)
	{
		CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
		cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderId);
		cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY");
		cancelLeaseOrderValueDto = this.selectByAttrPath(cancelLeaseOrderValueDto);
		return cancelLeaseOrderValueDto.getTextInput();
	}

	@Override
	public void rentalCostLiquidationWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		// 费用清算不通过，启用账单
		Financial financial = (Financial) SpringHelper.getBean("financial");
		try
		{
			logger.info("启用账单接口入参 合约id=" + String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
			Result result = financial.unFreezeBill(String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
			logger.info("启用账单接口返回值 result（1：成功） == " + result.getState());
			if (result.getState() != 1)
			{
				throw new BaseAppException("账单启用失败");
			}
		} catch (Exception e)
		{
			throw new BaseAppException("账单启用失败");
		}

		cancelLeaseOrderDto.clearAssignedDealer();
		cancelLeaseOrderDto.setAssignedDealerId(cancelLeaseOrderDto.getButlerId());
		cancelLeaseOrderDto.setActualDealerId(staffId);
		Long priority = this.updateSubOrderWithTrans(cancelLeaseOrderDto, staffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(cancelLeaseOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.NOT_COST_LIQUIDATION);
		workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
		workOrderDto.setSubActualDealerId(cancelLeaseOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		this.insertSubOrderValue(code, valueList, priority);

		// 费用角色
		cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		cancelLeaseOrderDto.setState(SubOrderStateDef.NOT_COST_LIQUIDATION);
		workOrderDto.setSubOrderState(SubOrderStateDef.NOT_COST_LIQUIDATION);
		this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
	}

	@Override
	public void rentalAccountReturnWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(cancelLeaseOrderDto.getWorkOrderId());
		Financial financial = (Financial) SpringHelper.getBean("financial");
		try
		{
			if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
			{
				// 新退租流程
				logger.info("冻结账单 入参 合约id=" + String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
				Result result = financial.freezeBill(String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
				logger.info("冻结账单 返回值 result（1：成功） == " + result.getState());
				if (result.getState() != 1)
				{
					throw new BaseAppException("租务审核打回费用清算账单冻结失败");
				}
			}
			if (workOrderDto.getType().equals(WorkOrderTypeDef.CANCEL_LEASE_ORDER))
			{
				// 老退租流程
				logger.info("启用账单接口入参 合约id=" + String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
				int result = financial.stateBill(String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()), "2");
				logger.info("启用账单接口返回值 result（0：失败，1：成功） == " + result);
				if (result == 0)
				{
					throw new BaseAppException("账单启用失败");
				}		
			}			
		} catch (Exception e)
		{
			throw new BaseAppException("账单操作失败");
		}
		
		cancelLeaseOrderDto.clearAssignedDealer();
		cancelLeaseOrderDto.setAssignedDealerId(cancelLeaseOrderDto.getButlerId());
		cancelLeaseOrderDto.setActualDealerId(staffId);
		Long priority = this.updateSubOrderWithTrans(cancelLeaseOrderDto, staffId, "");
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
		workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
		workOrderDto.setSubActualDealerId(cancelLeaseOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		this.insertSubOrderValue(code, valueList, priority);
		if (workOrderDto.getType().equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
			workOrderDto.setSubAssignedDealerRoleId(cancelLeaseOrderDto.getAssignedDealerRoleId());
			cancelLeaseOrderDto.setState(SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
			workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
			this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
			workOrderService.updateWorkOrderWithTrans(workOrderDto);
		} else
		{
			SubOrderUtil.dispatcherOrder(code);
		}
	}

	/**
	 * 
	 * Description: 插入属性值
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param valueList
	 *          valueList
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<CancelLeaseOrderValueDto> valueList, Long priority)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = cancelLeaseOrderDtoMapper.selectByCode(code);
		for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
		{
			cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
			doInsertSubOrderValue(cancelLeaseOrderValueDto);
			addSubOrderValueHis(cancelLeaseOrderValueDto, priority);
		}
	}

	/**
	 * 
	 * Description: 针对有图片的属性值进行特殊处理
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param valueList
	 *          valueList
	 * @param realPath
	 *          realPath
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<CancelLeaseOrderValueDto> valueList, String realPath, Long priority)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = cancelLeaseOrderDtoMapper.selectByCode(code);
		for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : valueList)
		{
			this.checkImage(cancelLeaseOrderValueDto, cancelLeaseOrderDto.getWorkOrderId(), realPath);
			cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
			doInsertSubOrderValue(cancelLeaseOrderValueDto);
			addSubOrderValueHis(cancelLeaseOrderValueDto, priority);
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderValueDto
	 *          cancelLeaseOrderValueDto
	 */
	private void doInsertSubOrderValue(CancelLeaseOrderValueDto cancelLeaseOrderValueDto)
	{
		CancelLeaseOrderValueDto existCancelLeaseOrderValueDto = cancelLeaseOrderValueDtoMapper.selectByAttrPath(cancelLeaseOrderValueDto);
		if (existCancelLeaseOrderValueDto != null)
		{
			cancelLeaseOrderValueDtoMapper.deleteByPrimaryKey(existCancelLeaseOrderValueDto.getId());
		}
		cancelLeaseOrderValueDtoMapper.insert(cancelLeaseOrderValueDto);
	}

	/**
	 * 
	 * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderValueDto
	 *          cancelLeaseOrderValueDto
	 * @param priority
	 *          priority
	 */
	private void addSubOrderValueHis(CancelLeaseOrderValueDto cancelLeaseOrderValueDto, Long priority)
	{
		CancelLeaseOrderValueHisDto cancelLeaseOrderValueHisDto = SubOrderUtil.createHisDto(cancelLeaseOrderValueDto, CancelLeaseOrderValueHisDto.class, priority);
		cancelLeaseOrderValueHisDtoMapper.insert(cancelLeaseOrderValueHisDto);
	}

	/**
	 * 
	 * Description: 图片上传ftp
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderValueDto
	 *          cancelLeaseOrderValueDto
	 * @param workOrderId
	 *          workOrderId
	 * @param realPath
	 *          realPath
	 */
	private void checkImage(CancelLeaseOrderValueDto cancelLeaseOrderValueDto, Long workOrderId, String realPath)
	{
		AttrDto attr = attrService.getAttrById(cancelLeaseOrderValueDto.getAttrId());
		if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType()))
		{
			cancelLeaseOrderValueDto.setTextInput(SubOrderUtil.uploadImage(cancelLeaseOrderValueDto.getTextInput(), workOrderId, realPath));
		}
	}

	@Override
	public boolean checkCancelLeaseOrderWithTrans(Long rentalLeaseOrderId)
	{
		CancelLeaseOrderDto qryCond = new CancelLeaseOrderDto();
		qryCond.setRentalLeaseOrderId(rentalLeaseOrderId);
		List<CancelLeaseOrderDto> cancelLeaseOrderDtoList = this.cancelLeaseOrderDtoMapper.selectByCond(qryCond);
		for (CancelLeaseOrderDto cancelLeaseOrderDto : cancelLeaseOrderDtoList)
		{
			if (!SubOrderStateDef.CLOSED.equals(cancelLeaseOrderDto.getState()))
			{
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrderDetailById(Long cancelLeaseOrderId, Boolean singleDetail)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = cancelLeaseOrderDtoMapper.selectDetailById(cancelLeaseOrderId);
		if (!singleDetail)
		{
			AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(cancelLeaseOrderDto.getAttrCatgId());
			this.checkAttrCatg(attrCatg);
			cancelLeaseOrderDto.setAttrCatg(attrCatg);
			List<CancelLeaseOrderValueDto> cancelLeaseOrderValueList = this.getSubOrderValueDtoListBySubOrderId(cancelLeaseOrderId, cancelLeaseOrderDto.getRentalLeaseOrderId());
			cancelLeaseOrderDto.setSubOrderValueList(cancelLeaseOrderValueList);
		}
		return (T) cancelLeaseOrderDto;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param attrCatg
	 *          attrCatg
	 */
	private void checkAttrCatg(AttrCatgDto attrCatg)
	{
		for (AttrCatgDto attrCatgDto : attrCatg.getAttrCatgChildren())
		{
			if ("BUTLER_GET_HOME".equals(attrCatgDto.getCode()))
			{
				attrCatgDto.setAttrCatgChildren(new ArrayList<AttrCatgDto>());
			}
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 *          cancelLeaseOrderId
	 * @param rentalLeaseOrderId
	 * @return List<CancelLeaseOrderValueDto>
	 */
	private List<CancelLeaseOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long cancelLeaseOrderId, Long rentalLeaseOrderId)
	{
		List<CancelLeaseOrderValueDto> cancelLeaseOrderValueList = cancelLeaseOrderValueDtoMapper.selectBySubOrderId(cancelLeaseOrderId);

		// 查询yc_wegcost_tab表中水表度数
		Map<String, Object> waterMap = cancelLeaseOrderValueDtoMapper.getRentalInfoByWater(rentalLeaseOrderId);
		String water = "";
		if (null != waterMap && waterMap.size() > 0)
		{
			long waterDate = Long.valueOf(String.valueOf(waterMap.get("lastDate")));
			long agreeDate = Long.valueOf(String.valueOf(waterMap.get("begin_time")));
			// 判断时间大小
			if (waterDate >= agreeDate)
			{
				// 使用最后一次水表度数
				water = String.valueOf(waterMap.get("endMeter"));
				if (water.equals(""))
				{
					water = String.valueOf(waterMap.get("water_meter"));
				}
			} else 
			{
				water = String.valueOf(waterMap.get("water_meter"));
				if (water.equals(""))
				{
					water = String.valueOf(waterMap.get("endMeter"));
				}
			}
		}
		if (water.equals("") || water.equals("null"))
		{
			water = "0";
		}
		
		// 查询yc_wegcost_tab表中电表度数
		Map<String, Object> electricityMap = cancelLeaseOrderValueDtoMapper.getRentalInfoByElectricity(rentalLeaseOrderId);
		String electricity = "";
		if (null != electricityMap && electricityMap.size() > 0)
		{
			long electricityDate = Long.valueOf(String.valueOf(electricityMap.get("lastDate")));
			long agreeDate = Long.valueOf(String.valueOf(electricityMap.get("begin_time")));
			// 判断时间大小
			if (electricityDate >= agreeDate)
			{
				// 使用最后一次电表度数
				electricity = String.valueOf(electricityMap.get("endMeter"));
				if (electricity.equals(""))
				{
					electricity = String.valueOf(electricityMap.get("electricity_meter"));
				}
			} else 
			{
				electricity = String.valueOf(electricityMap.get("electricity_meter"));
				if (electricity.equals(""))
				{
					electricity = String.valueOf(electricityMap.get("endMeter"));
				}
			}
		}
		if (electricity.equals("") || electricity.equals("null"))
		{
			electricity = "0";
		}
		
		// 查询yc_wegcost_tab表中燃气度数
		Map<String, Object> gasMap = cancelLeaseOrderValueDtoMapper.getRentalInfoByGas(rentalLeaseOrderId);
		String gas = "";
		if (null != gasMap && gasMap.size() > 0)
		{
			long gasDate = Long.valueOf(String.valueOf(gasMap.get("lastDate")));
			long agreeDate = Long.valueOf(String.valueOf(gasMap.get("begin_time")));
			// 判断时间大小
			if (gasDate >= agreeDate)
			{
				// 使用最后一次燃气度数
				gas = String.valueOf(gasMap.get("endMeter"));
				if (gas.equals(""))
				{
					gas = String.valueOf(gasMap.get("gas_meter"));
				}
			} else 
			{
				gas = String.valueOf(gasMap.get("gas_meter"));
				if (gas.equals(""))
				{
					gas = String.valueOf(gasMap.get("endMeter"));
				}
			}
		}		
		if (gas.equals("") || gas.equals("null")) 
		{
			gas = "0";
		}
		
		// 查询订单对应的出租合约水表、电表、燃气
//		Map<String, Object> map = cancelLeaseOrderValueDtoMapper.getRentalInfo(rentalLeaseOrderId);

		if (null == cancelLeaseOrderValueList || cancelLeaseOrderValueList.isEmpty())
		{
			cancelLeaseOrderValueList = new ArrayList<>();
			// 水表
			CancelLeaseOrderValueDto water_dto = new CancelLeaseOrderValueDto();
			water_dto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM");
			water_dto.setTextInput(water);
			cancelLeaseOrderValueList.add(water_dto);

			// 电表
			CancelLeaseOrderValueDto ammeter_dto = new CancelLeaseOrderValueDto();
			ammeter_dto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM");
			ammeter_dto.setTextInput(electricity);
			cancelLeaseOrderValueList.add(ammeter_dto);

			// 燃气
			CancelLeaseOrderValueDto gas_dto = new CancelLeaseOrderValueDto();
			gas_dto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM");
			gas_dto.setTextInput(gas);
			cancelLeaseOrderValueList.add(gas_dto);
		} else
		{
			boolean waterBoolean = false;
			boolean ammeterBoolean = false;
			boolean gasBoolean = false;
			for (CancelLeaseOrderValueDto cancelLeaseOrderValueDto : cancelLeaseOrderValueList)
			{
				String str = String.valueOf(cancelLeaseOrderValueDto.getAttrPath());
				if (str.equals("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM"))
				{
					waterBoolean = true;
				}

				if (str.equals("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM"))
				{
					ammeterBoolean = true;
				}

				if (str.equals("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM"))
				{
					gasBoolean = true;
				}

				if (AttrTypeDef.PICTURE_UPLOAD.equals(cancelLeaseOrderValueDto.getAttrType()))
				{
					cancelLeaseOrderValueDto.setTextInput(SubOrderUtil.getImagePath(cancelLeaseOrderValueDto.getTextInput()));
				}
			}

			if (waterBoolean)
			{
				// 水表
				CancelLeaseOrderValueDto water_dto = new CancelLeaseOrderValueDto();
				water_dto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM");
				water_dto.setTextInput(water);
				cancelLeaseOrderValueList.add(water_dto);
			}

			if (ammeterBoolean)
			{
				// 电表
				CancelLeaseOrderValueDto ammeter_dto = new CancelLeaseOrderValueDto();
				ammeter_dto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM");
				ammeter_dto.setTextInput(electricity);
				cancelLeaseOrderValueList.add(ammeter_dto);
			}

			if (gasBoolean)
			{
				// 燃气
				CancelLeaseOrderValueDto gas_dto = new CancelLeaseOrderValueDto();
				gas_dto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM");
				gas_dto.setTextInput(gas);
				cancelLeaseOrderValueList.add(gas_dto);
			}
		}
		return cancelLeaseOrderValueList;
	}

	@Override
	public CancelLeaseOrderValueDto selectByAttrPath(CancelLeaseOrderValueDto cancelLeaseOrderValueDto)
	{
		return cancelLeaseOrderValueDtoMapper.selectByAttrPath(cancelLeaseOrderValueDto);
	}

	@Override
	public void payNewWithTrans(String code, Long staffId, Long paidMoney)
	{
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getSubOrderByCode(code);
		if (workOrderDto.getSubOrderState().equals(SubOrderStateDef.PAY)) 
		{
			staffId = -2L;
		}
		if (workOrderDto.getSubOrderState().equals(SubOrderStateDef.REFUND)) 
		{
			staffId = -1L;
		}
		this.updateSubOrderActualDealer(code, staffId);
		// 判断当前订单环节是否是等待付款
		if (workOrderDto.getSubOrderState().equals(SubOrderStateDef.PAY) || workOrderDto.getSubOrderState().equals(SubOrderStateDef.REFUND)
				|| workOrderDto.getType().equals(WorkOrderTypeDef.CANCEL_LEASE_ORDER))
		{
			logger.info("待评论");
      cancelLeaseOrderDto.clearAssignedDealer();
      cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
      cancelLeaseOrderDto.setState(SubOrderStateDef.COMMENT);
      cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
      this.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
      workOrderDto.clearSubField();
      workOrderDto.setState(WorkOrderStateDef.WAIT_2_COMMENT);
      workOrderDto.setStateDate(DateUtil.getDBDateTime());
      workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
      workOrderDto.setSubOrderState(SubOrderStateDef.COMMENT);
      workOrderService.updateWorkOrderWithTrans(workOrderDto);
      // 合约置为已失效
      agreementDtoMapper.failureAgreementState(workOrderDto.getRentalLeaseOrderId());      
      SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.CANCEL_LEASE_ORDER);
		}
	}

	@Override
	public void payWithTrans(String code, Long staffId, Long paidMoney)
	{
		this.updateSubOrderActualDealer(code, staffId);
		SubOrderUtil.dispatcherOrder(code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getHis(Long subOrderId)
	{
		return (List<T>) cancelLeaseOrderHisDtoMapper.selectById(subOrderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> rentalMoneyCalculate(Long cancelLeaseOrderId)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = this.getOrderDetailById(cancelLeaseOrderId, true);
		Financial financial = (Financial) SpringHelper.getBean("financial");
		Map<String, String> params = new HashMap<>();
		params.put("agreeId", String.valueOf(cancelLeaseOrderDto.getRentalLeaseOrderId()));
		params.put("order_time", DateUtil.date2String(cancelLeaseOrderDto.getCancelLeaseDate(), DateUtil.DATE_FORMAT_1));
		return (Map<String, String>) financial.calculate(params);
	}
	
	@Override
	public void resetAgreementNewState(CancelLeaseOrderDto subOrderDto)
	{
		Map<String, Object> map = agreementDtoMapper.selectAgreementInfo(subOrderDto.getRentalLeaseOrderId());
		agreementDtoMapper.resetAgreementState(subOrderDto.getRentalLeaseOrderId());
		String rank_type = String.valueOf(map.get("rank_type"));
		String rank_id = String.valueOf(map.get("rank_id"));
		String house_id = String.valueOf(map.get("house_id"));
		houseRankDtoMapper.resetHouseRankState(rank_id);
		if (!rank_type.equals("1"))
		{
			// 整租
			houseRankDtoMapper.resetHouseRankStateShared(house_id);
		}
	}

	@Override
	public void resetAgreementState(CancelLeaseOrderDto cancelLeaseOrderDto)
	{
		Map<String, String> params = new HashMap<>();
		params.put("agreeid", cancelLeaseOrderDto.getRentalLeaseOrderId().toString());
		params.put("servicetime", DateUtil.date2String(cancelLeaseOrderDto.getCancelLeaseDate(), DateUtil.DATE_FORMAT_1));

		RankHouse rankHouse = (RankHouse) SpringHelper.getBean("rankHouse");
		int resp = rankHouse.closeOrder(params);
		if (resp != 1)
		{
			throw new BaseAppException("恢复合约状态失败");
		}
	}

	@Override
	public int updateAgreementState(CancelLeaseOrderDto cancelLeaseOrderDto)
	{
		String flag = "";
		String house_id = "";
		// 根据合约主键id，查询合约信息和对应房间信息
		Map<String, Object> info = agreementDtoMapper.selectAgreementInfo(cancelLeaseOrderDto.getRentalLeaseOrderId());
		if (null == info)
		{
			logger.info("新退租订单管家上门环节无法关闭订单，没有查询到对应房间信息 info = " + info);
			return -1;
		} else
		{
			// 房间状态
			String rank_type = String.valueOf(info.get("rank_type"));
			String rank_status = String.valueOf(info.get("rank_status"));
			house_id = String.valueOf(info.get("house_id"));
			if (rank_type.equals("1"))
			{
				// 合租
				if (!rank_status.equals("5") && !rank_status.equals("4"))
				{
					// 不是已签约且不是签约中，查询对应整租是否已签约或者签约中
					String entireRent = agreementDtoMapper.selectEntireRent(house_id);
					if (StringUtils.isNotBlank(entireRent))
					{
						// 存在已签约整租
						flag = "Y";
					} else
					{
						// 不存在已签约整租
						flag = "N";
					}
				} else
				{
					// 已签约
					flag = "Y";
				}
			} else if (rank_type.equals("0"))
			{
				// 整租
				if (!rank_status.equals("5") && !rank_status.equals("4"))
				{
					// 不是已签约且不是签约中，查询对应合租房间是否存在已签约或者签约中
					String sharedRent = agreementDtoMapper.selectSharedRent(house_id);
					if (StringUtils.isNotBlank(sharedRent))
					{
						// 存在已签约合租
						flag = "Y";
					} else
					{
						// 不存在已签约合租
						flag = "N";
					}
				} else
				{
					// 已签约
					flag = "Y";
				}
			} else
			{
				logger.info("新退租订单管家上门环节无法关闭订单，房间类型未知 rank_type=" + rank_type);
				return -2;
			}
		}
		if (flag.equals("Y"))
		{
			// 已签约
			logger.info("新退租订单管家上门环节无法关闭订单，房间已经签约或者正在签约.");
			return -3;
		} else
		{
			// 未签约，查询是否存在已生效或者生效待支付的出租合约
			int total = agreementDtoMapper.selectAgreementTotal(String.valueOf(info.get("id")));
			if (total > 0)
			{
				// 存在已生效或者生效待支付的出租合约
				logger.info("新退租订单管家上门环节无法关闭订单，存在已生效或者生效待支付的出租合约.");
				return -4;
			} else
			{
				return 1;
			}
		}
	}

	@Override
	public String getWorkOrderType()
	{
		return WorkOrderTypeDef.CANCEL_LEASE_ORDER;
	}

	@Override
	public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList, Long currentStaffId)
	{
		logger.info("Order is automatically assigned !");
	}

	@Override
	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
	{
		CancelLeaseOrderDto cancelLeaseOrderDto = new CancelLeaseOrderDto();
		cancelLeaseOrderDto.setId(subOrderId);
		cancelLeaseOrderDto.setAssignedDealerId(subAssignedDealerId);
		return cancelLeaseOrderDtoMapper.updateByPrimaryKeySelective(cancelLeaseOrderDto);
	}

	@Override
	public AgreementDto getInitialValue(Long id)
	{
		AgreementDto dto = agreementDtoMapper.selectByPrimaryKey(id);
		return dto;
	}

}
