package com.room1000.suborder.ownercancelleaseorder.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.room1000.core.utils.MoneyUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.ownercancelleaseorder.dao.OwnerCancelLeaseOrderDtoMapper;
import com.room1000.suborder.ownercancelleaseorder.dao.OwnerCancelLeaseOrderHisDtoMapper;
import com.room1000.suborder.ownercancelleaseorder.dao.OwnerCancelLeaseOrderTypeDtoMapper;
import com.room1000.suborder.ownercancelleaseorder.dao.OwnerCancelLeaseOrderValueDtoMapper;
import com.room1000.suborder.ownercancelleaseorder.dao.OwnerCancelLeaseOrderValueHisDtoMapper;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderHisDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderTypeDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueHisDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

import pccom.web.interfaces.Financial;
import pccom.web.interfaces.HouseInterfaces;

/**
 * 
 * Description:
 * 
 * Created on 2017年5月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("OwnerCancelLeaseOrderService")
public class OwnerCancelLeaseOrderServiceImpl implements IOwnerCancelLeaseOrderService
{

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(OwnerCancelLeaseOrderServiceImpl.class);

	/**
	 * workOrderService
	 */
	@Autowired
	private IWorkOrderService workOrderService;

	/**
	 * attrService
	 */
	@Autowired
	private IAttrService attrService;

	/**
	 * ownerCancelLeaseOrderDtoMapper
	 */
	@Autowired
	private OwnerCancelLeaseOrderDtoMapper ownerCancelLeaseOrderDtoMapper;

	/**
	 * ownerCancelLeaseOrderValueDtoMapper
	 */
	@Autowired
	private OwnerCancelLeaseOrderValueDtoMapper ownerCancelLeaseOrderValueDtoMapper;

	/**
	 * ownerCancelLeaseOrderHisDtoMapper
	 */
	@Autowired
	private OwnerCancelLeaseOrderHisDtoMapper ownerCancelLeaseOrderHisDtoMapper;

	/**
	 * ownerCancelLeaseOrderTypeDtoMapper
	 */
	@Autowired
	private OwnerCancelLeaseOrderTypeDtoMapper ownerCancelLeaseOrderTypeDtoMapper;

	/**
	 * staffDtoMapper
	 */
	@Autowired
	private StaffDtoMapper staffDtoMapper;

	/**
	 * ownerCancelLeaseOrderValueHisDtoMapper
	 */
	@Autowired
	private OwnerCancelLeaseOrderValueHisDtoMapper ownerCancelLeaseOrderValueHisDtoMapper;

	/**
	 * houseInterfaces
	 */
	@Autowired
	private HouseInterfaces houseInterfaces;

	/**
	 * financial
	 */
	@Autowired
	private Financial financial;

	@Override
	public void inputSubOrderInfoWithTrans(OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto, WorkOrderDto workOrderDto,
			Long staffId)
	{
		if (this.checkOwnerCancelLeaseOrderWithTrans(ownerCancelLeaseOrderDto.getTakeHouseOrderId()))
		{
			return;
		}

		workOrderDto.setTakeHouseOrderId(ownerCancelLeaseOrderDto.getTakeHouseOrderId());
		workOrderDto.setAppointmentDate(ownerCancelLeaseOrderDto.getAppointmentDate());
		workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

		workOrderService.createWorkOrderWithTrans(workOrderDto);

		ownerCancelLeaseOrderDto.setWorkOrderId(workOrderDto.getId());
		ownerCancelLeaseOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
		ownerCancelLeaseOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
		ownerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		ownerCancelLeaseOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		ownerCancelLeaseOrderDto.setActualDealerId(staffId);
		ownerCancelLeaseOrderDto.setUpdatePersonId(staffId);
		ownerCancelLeaseOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("OCL_ORDER_PROCESS");
		ownerCancelLeaseOrderDto.setAttrCatgId(attrCatg.getId());
		ownerCancelLeaseOrderDtoMapper.insert(ownerCancelLeaseOrderDto);
		this.updateSubOrderWithTrans(ownerCancelLeaseOrderDto, staffId, "");

		workOrderDto.setType(WorkOrderTypeDef.OWNER_CANCEL_LEASE_ORDER);
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			String orderName = workOrderService.generateOrderName(workOrderDto);
			workOrderDto.setName(orderName);
		}
		workOrderDto.setRefId(ownerCancelLeaseOrderDto.getId());
		workOrderDto.setCode(ownerCancelLeaseOrderDto.getCode());
		workOrderDto.setSubActualDealerId(ownerCancelLeaseOrderDto.getActualDealerId());
		workOrderDto.setSubComments(ownerCancelLeaseOrderDto.getComments());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		String processInstanceKey = "OwnerCancelLeaseOrderProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
		variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
		variables.put(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER, ownerCancelLeaseOrderDto);

		IProcessStart start = new ProcessStartImpl();
		start.flowStart(processInstanceKey, variables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = ownerCancelLeaseOrderDtoMapper.selectDetailById(subOrderId);
		if (!singleDetail)
		{
			AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(ownerCancelLeaseOrderDto.getAttrCatgId());
			ownerCancelLeaseOrderDto.setAttrCatg(attrCatg);
			List<OwnerCancelLeaseOrderValueDto> subOrderValueList = this.getSubOrderValueDtoListBySubOrderId(subOrderId);
			ownerCancelLeaseOrderDto.setSubOrderValueList(subOrderValueList);
		}
		return (T) ownerCancelLeaseOrderDto;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param subOrderId
	 *          subOrderId
	 * @return List<OwnerCancelLeaseOrderValueDto>
	 */
	private List<OwnerCancelLeaseOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long subOrderId)
	{
		List<OwnerCancelLeaseOrderValueDto> subOrderValueList = ownerCancelLeaseOrderValueDtoMapper
				.selectBySubOrderId(subOrderId);
		for (OwnerCancelLeaseOrderValueDto subOrderValueDto : subOrderValueList)
		{
			if (AttrTypeDef.PICTURE_UPLOAD.equals(subOrderValueDto.getAttrType()))
			{
				subOrderValueDto.setTextInput(SubOrderUtil.getImagePath(subOrderValueDto.getTextInput()));
			}
		}
		return subOrderValueList;
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str)
	{
		return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) subOrderDto;
		ownerCancelLeaseOrderDto.setUpdatePersonId(updatePersonId);
		ownerCancelLeaseOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		return this.updateSubOrderAndAddHis(ownerCancelLeaseOrderDto, closeOrder, str);
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param ownerCancelLeaseOrderDto
	 *          ownerCancelLeaseOrderDto
	 * @param closeOrder
	 *          closeOrder
	 * @return Long
	 */
	private Long updateSubOrderAndAddHis(OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto, boolean closeOrder, String str)
	{
		// 订单数据更新到流程中
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(ownerCancelLeaseOrderDto.getWorkOrderId(), ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER,
				ownerCancelLeaseOrderDto);

		OwnerCancelLeaseOrderDto oldOwnerCancelLeaseOrderDto = ownerCancelLeaseOrderDtoMapper
				.selectByPrimaryKey(ownerCancelLeaseOrderDto.getId());
		if (oldOwnerCancelLeaseOrderDto == null)
		{
			oldOwnerCancelLeaseOrderDto = ownerCancelLeaseOrderDtoMapper.selectByCode(ownerCancelLeaseOrderDto.getCode());
		}
		oldOwnerCancelLeaseOrderDto.setUpdatePersonId(ownerCancelLeaseOrderDto.getUpdatePersonId());
		oldOwnerCancelLeaseOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		if (closeOrder)
		{
			oldOwnerCancelLeaseOrderDto.setState(SubOrderStateDef.CLOSED);
			oldOwnerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		ownerCancelLeaseOrderDtoMapper.updateByPrimaryKey(ownerCancelLeaseOrderDto);
		OwnerCancelLeaseOrderHisDto hisDto = SubOrderUtil.createHisDto(oldOwnerCancelLeaseOrderDto,
				OwnerCancelLeaseOrderHisDto.class, getPriority(oldOwnerCancelLeaseOrderDto.getId()));
		if (BooleanFlagDef.BOOLEAN_TRUE.equals(ownerCancelLeaseOrderDto.getChangeFlag()))
		{
			hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
			hisDto.setStateDate(DateUtil.getDBDateTime());
			hisDto.setUpdateDate(DateUtil.getDBDateTime());
		}
		ownerCancelLeaseOrderHisDtoMapper.insert(hisDto);
		return hisDto.getPriority();
	}

	/**
	 * 
	 * Description: 创建一个历史Dto
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderDto
	 *          cancelLeaseOrderDto
	 * @return CancelLeaseOrderHisDto
	 */
	// private OwnerCancelLeaseOrderHisDto createHisDto(OwnerCancelLeaseOrderDto
	// ownerCancelLeaseOrderDto) {
	// Long maxPriority =
	// ownerCancelLeaseOrderHisDtoMapper.selectMaxPriority(ownerCancelLeaseOrderDto.getId());
	// if (maxPriority == null) {
	// maxPriority = 0L;
	// }
	// OwnerCancelLeaseOrderHisDto ownerCancelLeaseOrderHisDto = new
	// OwnerCancelLeaseOrderHisDto();
	// SubOrderUtil.createdHisDto(ownerCancelLeaseOrderDto,
	// ownerCancelLeaseOrderHisDto, maxPriority + 1);
	// return ownerCancelLeaseOrderHisDto;
	// }

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
		Long priority = ownerCancelLeaseOrderHisDtoMapper.selectMaxPriority(subOrderId);
		if (priority == null)
		{
			return 1L;
		} else
		{
			return priority + 1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getHis(Long subOrderId)
	{
		return (List<T>) ownerCancelLeaseOrderHisDtoMapper.selectById(subOrderId);
	}

	@Override
	public void assignSubOrderWithTrans(String code)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = this.getSubOrderByCode(code);
		StaffDto staff = staffDtoMapper.selectByTakeHouseAgreementId(ownerCancelLeaseOrderDto.getTakeHouseOrderId());
		ownerCancelLeaseOrderDto.setStaffId(staff.getId());
		ownerCancelLeaseOrderDto.clearAssignedDealer();
		ownerCancelLeaseOrderDto.setAssignedDealerId(staff.getId());
		ownerCancelLeaseOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
		ownerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
		this.updateSubOrderWithTrans(ownerCancelLeaseOrderDto, null, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(ownerCancelLeaseOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setStaffId(staff.getId());
		workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
		workOrderDto.setSubAssignedDealerId(ownerCancelLeaseOrderDto.getAssignedDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

	}

	@Override
	public void updateSubOrderStateWithTrans(String code, String state)
	{
		OwnerCancelLeaseOrderDto record = this.getSubOrderByCode(code);
		record.setState(state);
		record.setStateDate(DateUtil.getDBDateTime());
		this.updateSubOrderWithTrans(record, null, "");
	}

	@Override
	public OwnerCancelLeaseOrderValueDto selectByAttrPath(OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto)
	{
		return ownerCancelLeaseOrderValueDtoMapper.selectByAttrPath(ownerCancelLeaseOrderValueDto);
	}

	@Override
	public OwnerCancelLeaseOrderDto getSubOrderByCode(String code)
	{
		return ownerCancelLeaseOrderDtoMapper.selectByCode(code);
	}

	@Override
	public void releaseHouse(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);
		// 房源释放接口调用
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		int result = houseInterfaces.managerDone(workOrderDto);
		if (result != 1)
		{
			throw new BaseAppException("房源释放失败");
		}
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public void reAssignSubOrderWithTrans(String code, Long butlerId, List<OwnerCancelLeaseOrderValueDto> valueList,
			Long staffId)
	{
		OwnerCancelLeaseOrderDto record = this.getSubOrderByCode(code);
		record.setStaffId(butlerId);
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

		SubOrderUtil.sendMessage(workOrderDto, record.getAssignedDealerId(), OrderPushModelDef.OWNER_CANCEL_LEASE_ORDER);
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
	 *          退租单中各个属性的值
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long priority)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = ownerCancelLeaseOrderDtoMapper.selectByCode(code);
		for (OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto : valueList)
		{
			ownerCancelLeaseOrderValueDto.setSubOrderId(ownerCancelLeaseOrderDto.getId());
			if ("OCL_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY".equals(ownerCancelLeaseOrderValueDto.getAttrPath()))
			{
				updateWorkOrderMoney(code, ownerCancelLeaseOrderValueDto.getTextInput());
			}
			doInsertSubOrderValue(ownerCancelLeaseOrderValueDto);
			addSubOrderValueHis(ownerCancelLeaseOrderValueDto, priority);
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param money
	 *          money
	 */
	private void updateWorkOrderMoney(String code, String money)
	{
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setPayableMoney(MoneyUtil.decimalStrMoney2WithoutDecimalStr(money));
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
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
	private void insertSubOrderValue(String code, List<OwnerCancelLeaseOrderValueDto> valueList, String realPath,
			Long priority)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = ownerCancelLeaseOrderDtoMapper.selectByCode(code);
		for (OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto : valueList)
		{
			this.checkImage(ownerCancelLeaseOrderValueDto, ownerCancelLeaseOrderDto.getWorkOrderId(), realPath);
			ownerCancelLeaseOrderValueDto.setSubOrderId(ownerCancelLeaseOrderDto.getId());
			doInsertSubOrderValue(ownerCancelLeaseOrderValueDto);
			addSubOrderValueHis(ownerCancelLeaseOrderValueDto, priority);
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param ownerCancelLeaseOrderValueDto
	 *          ownerCancelLeaseOrderValueDto
	 */
	private void doInsertSubOrderValue(OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto)
	{
		OwnerCancelLeaseOrderValueDto existSubOrderValueDto = ownerCancelLeaseOrderValueDtoMapper
				.selectByAttrPath(ownerCancelLeaseOrderValueDto);
		if (existSubOrderValueDto != null)
		{
			ownerCancelLeaseOrderValueDtoMapper.deleteByPrimaryKey(existSubOrderValueDto.getId());
		}
		ownerCancelLeaseOrderValueDtoMapper.insert(ownerCancelLeaseOrderValueDto);

	}

	/**
	 * 
	 * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
	 * 
	 * @author jinyanan
	 *
	 * @param ownerCancelLeaseOrderValueDto
	 *          ownerCancelLeaseOrderValueDto
	 * @param priority
	 *          priority
	 */
	private void addSubOrderValueHis(OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto, Long priority)
	{
		OwnerCancelLeaseOrderValueHisDto ownerCancelLeaseOrderValueHisDto = SubOrderUtil
				.createHisDto(ownerCancelLeaseOrderValueDto, OwnerCancelLeaseOrderValueHisDto.class, priority);
		ownerCancelLeaseOrderValueHisDtoMapper.insert(ownerCancelLeaseOrderValueHisDto);
	}

	/**
	 * 
	 * Description: 图片上传ftp
	 * 
	 * @author jinyanan
	 *
	 * @param subOrderValueDto
	 *          subOrderValueDto
	 * @param workOrderId
	 *          workOrderId
	 * @param realPath
	 *          realPath
	 */
	private void checkImage(OwnerCancelLeaseOrderValueDto subOrderValueDto, Long workOrderId, String realPath)
	{
		AttrDto attr = attrService.getAttrById(subOrderValueDto.getAttrId());
		if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType()))
		{
			subOrderValueDto.setTextInput(SubOrderUtil.uploadImage(subOrderValueDto.getTextInput(), workOrderId, realPath));
		}
	}

	@Override
	public void takeOrderWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);
		SubOrderUtil.dispatcherOrder(code);
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
		OwnerCancelLeaseOrderDto record = this.getSubOrderByCode(code);
		record.setActualDealerId(actualDealerId);
		Long priority = this.updateSubOrderWithTrans(record, actualDealerId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(actualDealerId);
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		return priority;
	}

	@Override
	public void butlerGetHomeWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId,
			String realPath, Boolean submitFlag)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, realPath, priority);
		if (submitFlag)
		{
			SubOrderUtil.dispatcherOrder(code);
		}
	}

	@Override
	public void rentalAuditWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public void managerAuditWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);

		// 审批通过才可释放房源
		if (this.getPassed(valueList))
		{
			// 房源释放接口调用
			WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
			int result = houseInterfaces.managerDone(workOrderDto);
			if (result != 1)
			{
				throw new BaseAppException("房源释放失败");
			}
		}

		SubOrderUtil.dispatcherOrder(code);
	}

	/**
	 * 
	 * Description: 查找高管审批PASSED值
	 * 
	 * @author jinyanan
	 *
	 * @param valueList
	 *          valueList
	 * @return boolean
	 */
	private boolean getPassed(List<OwnerCancelLeaseOrderValueDto> valueList)
	{
		for (OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto : valueList)
		{
			if ("OCL_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.PASSED".equals(ownerCancelLeaseOrderValueDto.getAttrPath())
					&& "Y".equals(ownerCancelLeaseOrderValueDto.getTextInput()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void financeAuditWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, staffId);
		this.insertSubOrderValue(code, valueList, priority);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		int result = financial.orderTakeHousePayNew(workOrderDto, staffId.intValue());
		if (result == -1)
		{
			throw new BaseAppException("未查询到合约信息");
		} else if (result == -2)
		{
			throw new BaseAppException("未查询到房间信息");
		} else if (result == 0)
		{
			throw new BaseAppException("操作失败");
		}
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public void payWithTrans(String code, Long staffId, Long paidMoney)
	{
		this.updateSubOrderActualDealer(code, staffId);
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public boolean checkOwnerCancelLeaseOrderWithTrans(Long takeHouseOrderId)
	{
		OwnerCancelLeaseOrderDto qryCond = new OwnerCancelLeaseOrderDto();
		qryCond.setTakeHouseOrderId(takeHouseOrderId);
		List<OwnerCancelLeaseOrderDto> ownerCancelLeaseOrderDtoList = ownerCancelLeaseOrderDtoMapper.selectByCond(qryCond);
		for (OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto : ownerCancelLeaseOrderDtoList)
		{
			if (!SubOrderStateDef.CLOSED.equals(ownerCancelLeaseOrderDto.getState()))
			{
				return true;
			}
		}
		WorkOrderDto workOrderDto = new WorkOrderDto();
		workOrderDto.setTakeHouseOrderId(takeHouseOrderId);
		// result > 0 不允许创建订单
		int result = houseInterfaces.getAgreeCount(workOrderDto);
		return result > 0 ? true : false;
	}

	@Override
	public String getWorkOrderType()
	{
		return WorkOrderTypeDef.OWNER_CANCEL_LEASE_ORDER;
	}

	@Override
	public List<OwnerCancelLeaseOrderTypeDto> getOwnerCancelLeaseOrderType()
	{
		return ownerCancelLeaseOrderTypeDtoMapper.selectAll();
	}

	@Override
	public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
			Long currentStaffId)
	{
		logger.info("Order is automatically assigned !");
	}

	@Override
	public Map<String, String> rentalMoneyCalculate(Long ownerCancelLeaseOrderId)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = this.getOrderDetailById(ownerCancelLeaseOrderId, true);
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(ownerCancelLeaseOrderDto.getWorkOrderId());
		try
		{
			return houseInterfaces.calculate(workOrderDto);
		} catch (ParseException e)
		{
			throw new BaseAppException(e.getMessage());
		}
	}
	
	@Override
	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
	{
		OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = new OwnerCancelLeaseOrderDto();
		ownerCancelLeaseOrderDto.setId(subOrderId);
		ownerCancelLeaseOrderDto.setAssignedDealerId(subAssignedDealerId);
		return ownerCancelLeaseOrderDtoMapper.updateByPrimaryKeySelective(ownerCancelLeaseOrderDto);
	}

}
