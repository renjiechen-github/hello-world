package com.room1000.suborder.routinecleaningorder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderDtoMapper;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderHisDtoMapper;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderValueDtoMapper;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderValueHisDtoMapper;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderHisDto;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderValueDto;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderValueHisDto;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月7日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("RoutineCleaningOrderService")
public class RoutineCleaningOrderServiceImpl implements IRoutineCleaningOrderService
{

	/**
	 * logger
	 */
	// private static Logger logger =
	// LoggerFactory.getLogger(RoutineCleaningOrderServiceImpl.class);

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
	 * routineCleaningOrderDtoMapper
	 */
	@Autowired
	private RoutineCleaningOrderDtoMapper routineCleaningOrderDtoMapper;

	/**
	 * routineCleaningOrderValueDtoMapper
	 */
	@Autowired
	private RoutineCleaningOrderValueDtoMapper routineCleaningOrderValueDtoMapper;

	/**
	 * routineCleaningOrderHisDtoMapper
	 */
	@Autowired
	private RoutineCleaningOrderHisDtoMapper routineCleaningOrderHisDtoMapper;

	/**
	 * routineCleaningOrderValueHisDtoMapper
	 */
	@Autowired
	private RoutineCleaningOrderValueHisDtoMapper routineCleaningOrderValueHisDtoMapper;

	@Override
	public void inputSubOrderInfoWithTrans(RoutineCleaningOrderDto routineCleaningOrderDto, WorkOrderDto workOrderDto,
			Long currentStaffId)
	{
		workOrderDto.setRentalLeaseOrderId(routineCleaningOrderDto.getRentalLeaseOrderId());
		workOrderDto.setAppointmentDate(routineCleaningOrderDto.getAppointmentDate());
		workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);
		// 系统
		workOrderDto.setCreatedStaffId(SystemAccountDef.SYSTEM);

		workOrderService.createWorkOrderWithTrans(workOrderDto);

		routineCleaningOrderDto.setWorkOrderId(workOrderDto.getId());
		routineCleaningOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
		routineCleaningOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
		routineCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
		routineCleaningOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		routineCleaningOrderDto.setActualDealerId(currentStaffId);
		routineCleaningOrderDto.setUpdatePersonId(currentStaffId);
		routineCleaningOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("ROUTINE_CLEANING_ORDER_PROCESS");
		routineCleaningOrderDto.setAttrCatgId(attrCatg.getId());
		routineCleaningOrderDtoMapper.insert(routineCleaningOrderDto);
		this.updateSubOrderWithTrans(routineCleaningOrderDto, currentStaffId, "");

		workOrderDto.setType(WorkOrderTypeDef.ROUTINE_CLEANING_ORDER);
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			String orderName = workOrderService.generateOrderName(workOrderDto);
			workOrderDto.setName(orderName);
		}
		workOrderDto.setRefId(routineCleaningOrderDto.getId());
		workOrderDto.setCode(routineCleaningOrderDto.getCode());
		workOrderDto.setSubActualDealerId(routineCleaningOrderDto.getActualDealerId());
		workOrderDto.setSubComments(routineCleaningOrderDto.getComments());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		startProcess(routineCleaningOrderDto, workOrderDto);

	}

	/**
	 * 
	 * Description: startProcess
	 * 
	 * @author jinyanan
	 *
	 * @param routineCleaningOrderDto
	 *          routineCleaningOrderDto
	 * @param workOrderDto
	 *          workOrderDto
	 */
	private void startProcess(RoutineCleaningOrderDto routineCleaningOrderDto, WorkOrderDto workOrderDto)
	{
		String processInstanceKey = "RoutineCleaningOrderProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
		variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
		variables.put(ActivitiVariableDef.ROUTINE_CLEANING_ORDER, routineCleaningOrderDto);
		IProcessStart start = new ProcessStartImpl();
		start.flowStart(processInstanceKey, variables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = routineCleaningOrderDtoMapper.selectDetailById(subOrderId);
		if (!singleDetail)
		{
			AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(routineCleaningOrderDto.getAttrCatgId());
			routineCleaningOrderDto.setAttrCatg(attrCatg);
			List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList = this
					.getSubOrderValueDtoListBySubOrderId(subOrderId);
			routineCleaningOrderDto.setSubOrderValueList(routineCleaningOrderValueDtoList);
		}

		return (T) routineCleaningOrderDto;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return List<RoutineCleaningOrderValueDto>
	 */
	private List<RoutineCleaningOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id)
	{
		List<RoutineCleaningOrderValueDto> routineCleaningOrderValueList = routineCleaningOrderValueDtoMapper
				.selectBySubOrderId(id);
		for (RoutineCleaningOrderValueDto routineCleaningOrderValueDto : routineCleaningOrderValueList)
		{
			if (AttrTypeDef.PICTURE_UPLOAD.equals(routineCleaningOrderValueDto.getAttrType()))
			{
				routineCleaningOrderValueDto
						.setTextInput(SubOrderUtil.getImagePath(routineCleaningOrderValueDto.getTextInput()));
			}
		}
		return routineCleaningOrderValueList;
	}

	@Override
	public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
			Long currentStaffId)
	{
		// 修改看房订单的管家id和指定处理人
		RoutineCleaningOrderDto routineCleaningOrderDto = this.getSubOrderByCode(code);
		routineCleaningOrderDto.setStaffId(staffId);
		routineCleaningOrderDto.clearAssignedDealer();
		routineCleaningOrderDto.setAssignedDealerId(staffId);
		routineCleaningOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(routineCleaningOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(routineCleaningOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, subOrderValueDtoList, priority);

		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void reassignOrderWithTrans(String code, Long staffId,
			List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList, Long currentStaffId)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = this.getSubOrderByCode(code);
		routineCleaningOrderDto.setStaffId(staffId);
		routineCleaningOrderDto.clearAssignedDealer();
		routineCleaningOrderDto.setAssignedDealerId(staffId);
		routineCleaningOrderDto.setActualDealerId(currentStaffId);
		routineCleaningOrderDto.setState(SubOrderStateDef.REASSIGNING);
		routineCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());

		Long priority = this.updateSubOrderWithTrans(routineCleaningOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(routineCleaningOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
		workOrderDto.setSubAssignedDealerId(staffId);
		workOrderDto.setSubActualDealerId(routineCleaningOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		// 第二次更新为了插入历史表
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, routineCleaningOrderValueDtoList, priority);

		SubOrderUtil.sendMessage(workOrderDto, routineCleaningOrderDto.getAssignedDealerId(),
				OrderPushModelDef.ROUTINE_CLEANING_ORDER);
	}

	/**
	 * 
	 * Description: 更新看房单当前处理人
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
		RoutineCleaningOrderDto record = this.getSubOrderByCode(code);
		record.setActualDealerId(actualDealerId);
		Long priority = this.updateSubOrderWithTrans(record, actualDealerId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(actualDealerId);
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		return priority;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @return RoutineCleaningOrderDto
	 */
	private RoutineCleaningOrderDto getSubOrderByCode(String code)
	{
		return routineCleaningOrderDtoMapper.selectByCode(code);
	}

	/**
	 * 
	 * Description: 更新看房的同时，插入历史表
	 * 
	 * @author jinyanan
	 *
	 * @param routineCleaningOrderDto
	 *          routineCleaningOrderDto
	 * @param closeOrder
	 *          closeOrder
	 * @return RoutineCleaningOrderDto
	 */
	private Long updateSubOrderAndAddHis(RoutineCleaningOrderDto routineCleaningOrderDto, boolean closeOrder, String str)
	{
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(routineCleaningOrderDto.getWorkOrderId(), ActivitiVariableDef.ROUTINE_CLEANING_ORDER,
				routineCleaningOrderDto);
		RoutineCleaningOrderDto oldRoutineCleaningOrderDto = routineCleaningOrderDtoMapper
				.selectByPrimaryKey(routineCleaningOrderDto.getId());
		if (oldRoutineCleaningOrderDto == null)
		{
			oldRoutineCleaningOrderDto = routineCleaningOrderDtoMapper.selectByCode(routineCleaningOrderDto.getCode());
		}
		oldRoutineCleaningOrderDto.setUpdatePersonId(routineCleaningOrderDto.getUpdatePersonId());
		oldRoutineCleaningOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		if (closeOrder)
		{
			oldRoutineCleaningOrderDto.setState(SubOrderStateDef.CLOSED);
			oldRoutineCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		routineCleaningOrderDtoMapper.updateByPrimaryKey(routineCleaningOrderDto);
		RoutineCleaningOrderHisDto hisDto = SubOrderUtil.createHisDto(oldRoutineCleaningOrderDto,
				RoutineCleaningOrderHisDto.class, getPriority(oldRoutineCleaningOrderDto.getId()));
		if (BooleanFlagDef.BOOLEAN_TRUE.equals(routineCleaningOrderDto.getChangeFlag()))
		{
			hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
			hisDto.setStateDate(DateUtil.getDBDateTime());
			hisDto.setUpdateDate(DateUtil.getDBDateTime());
		}
		routineCleaningOrderHisDtoMapper.insert(hisDto);
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
		Long priority = routineCleaningOrderHisDtoMapper.selectMaxPriority(subOrderId);
		if (priority == null)
		{
			return 1L;
		} else
		{
			return priority + 1;
		}
	}

	/**
	 * 
	 * Description: 创建历史dto
	 * 
	 * @author jinyanan
	 *
	 * @param oldRoutineCleaningOrderDto
	 *          oldRoutineCleaningOrderDto
	 * @return RoutineCleaningOrderHisDto
	 */
	// private RoutineCleaningOrderHisDto createHisDto(RoutineCleaningOrderDto
	// oldRoutineCleaningOrderDto) {
	// RoutineCleaningOrderHisDto routineCleaningOrderHisDto = new
	// RoutineCleaningOrderHisDto();
	// Long maxPriority =
	// routineCleaningOrderHisDtoMapper.selectMaxPriority(oldRoutineCleaningOrderDto.getId());
	// if (maxPriority == null) {
	// maxPriority = 0L;
	// }
	// SubOrderUtil.createdHisDto(oldRoutineCleaningOrderDto,
	// routineCleaningOrderHisDto, maxPriority + 1);
	// return routineCleaningOrderHisDto;
	// }

	/**
	 * 
	 * Description: 插入RoutineCleaningOrderValue
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param subOrderValueDtoList
	 *          routineCleaningOrderValueDtoList
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList, Long priority)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = routineCleaningOrderDtoMapper.selectByCode(code);
		for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList)
		{
			RoutineCleaningOrderValueDto routineCleaningOrderValueDto = (RoutineCleaningOrderValueDto) subOrderValueDto;
			routineCleaningOrderValueDto.setSubOrderId(routineCleaningOrderDto.getId());
			doInsertSubOrderValue(routineCleaningOrderValueDto);
			addSubOrderValueHis(routineCleaningOrderValueDto, priority);
		}

	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param routineCleaningOrderValueDto
	 *          routineCleaningOrderValueDto
	 */
	private void doInsertSubOrderValue(RoutineCleaningOrderValueDto routineCleaningOrderValueDto)
	{
		RoutineCleaningOrderValueDto existRoutineCleaningOrderValueDto = routineCleaningOrderValueDtoMapper
				.selectByAttrPath(routineCleaningOrderValueDto);
		if (existRoutineCleaningOrderValueDto != null)
		{
			routineCleaningOrderValueDtoMapper.deleteByPrimaryKey(existRoutineCleaningOrderValueDto.getId());
		}
		routineCleaningOrderValueDtoMapper.insert(routineCleaningOrderValueDto);

	}

	@Override
	public void takeOrderWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
			Long currentStaffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, routineCleaningOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void processOrderWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
			Long currentStaffId, String realPath, Boolean submitFlag)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, routineCleaningOrderValueDtoList, realPath, priority);
		if (submitFlag)
		{
			SubOrderUtil.dispatcherOrder(code);
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
	 * @param routineCleaningOrderValueDtoList
	 *          routineCleaningOrderValueDtoList
	 * @param realPath
	 *          realPath
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
			String realPath, Long priority)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = routineCleaningOrderDtoMapper.selectByCode(code);
		for (RoutineCleaningOrderValueDto routineCleaningOrderValueDto : routineCleaningOrderValueDtoList)
		{
			this.checkImage(routineCleaningOrderValueDto, routineCleaningOrderDto.getWorkOrderId(), realPath);
			routineCleaningOrderValueDto.setSubOrderId(routineCleaningOrderDto.getId());
			doInsertSubOrderValue(routineCleaningOrderValueDto);
			addSubOrderValueHis(routineCleaningOrderValueDto, priority);
		}

	}

	/**
	 * 
	 * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
	 * 
	 * @author jinyanan
	 *
	 * @param routineCleaningOrderValueDto
	 *          routineCleaningOrderValueDto
	 * @param priority
	 *          priority
	 */
	private void addSubOrderValueHis(RoutineCleaningOrderValueDto routineCleaningOrderValueDto, Long priority)
	{
		RoutineCleaningOrderValueHisDto routineCleaningOrderValueHisDto = SubOrderUtil
				.createHisDto(routineCleaningOrderValueDto, RoutineCleaningOrderValueHisDto.class, priority);
		routineCleaningOrderValueHisDtoMapper.insert(routineCleaningOrderValueHisDto);
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param routineCleaningOrderValueDto
	 *          routineCleaningOrderValueDto
	 * @param workOrderId
	 *          workOrderId
	 * @param realPath
	 *          realPath
	 */
	private void checkImage(RoutineCleaningOrderValueDto routineCleaningOrderValueDto, Long workOrderId, String realPath)
	{
		AttrDto attr = attrService.getAttrById(routineCleaningOrderValueDto.getAttrId());
		if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType()))
		{
			routineCleaningOrderValueDto
					.setTextInput(SubOrderUtil.uploadImage(routineCleaningOrderValueDto.getTextInput(), workOrderId, realPath));
		}

	}

	@Override
	public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
			List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList, Long currentStaffId, String realPath)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = this.getSubOrderByCode(code);
		routineCleaningOrderDto.setStaffId(staffId);
		routineCleaningOrderDto.clearAssignedDealer();
		routineCleaningOrderDto.setAssignedDealerId(staffId);
		routineCleaningOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(routineCleaningOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(routineCleaningOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, routineCleaningOrderValueDtoList, realPath, priority);

		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void staffTaceWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
			Long currentStaffId, String realPath)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = this.getSubOrderByCode(code);
		routineCleaningOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
		routineCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
		routineCleaningOrderDto.setActualDealerId(currentStaffId);
		Long priority = this.updateSubOrderWithTrans(routineCleaningOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(routineCleaningOrderDto.getWorkOrderId());
		workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
		workOrderDto.setSubActualDealerId(routineCleaningOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, routineCleaningOrderValueDtoList, realPath, priority);

	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str)
	{
		return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = (RoutineCleaningOrderDto) subOrderDto;
		routineCleaningOrderDto.setUpdatePersonId(updatePersonId);
		routineCleaningOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		return this.updateSubOrderAndAddHis(routineCleaningOrderDto, closeOrder, str);
	}

	// @Override
	// public String getStateName(String state) {
	// return
	// routineCleaningOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
	// }

	@Override
	public void passInStaffReviewWithTrans(String code,
			List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList, Long currentStaffId, String realPath)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, routineCleaningOrderValueDtoList, realPath, priority);
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public RoutineCleaningOrderValueDto selectByAttrPath(RoutineCleaningOrderValueDto routineCleaningOrderValueDto)
	{
		return routineCleaningOrderValueDtoMapper.selectByAttrPath(routineCleaningOrderValueDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getHis(Long subOrderId)
	{
		return (List<T>) routineCleaningOrderHisDtoMapper.selectById(subOrderId);
	}

	@Override
	public String getWorkOrderType()
	{
		return WorkOrderTypeDef.ROUTINE_CLEANING_ORDER;
	}
	
	@Override
	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
	{
		RoutineCleaningOrderDto routineCleaningOrderDto = new RoutineCleaningOrderDto();
		routineCleaningOrderDto.setId(subOrderId);
		routineCleaningOrderDto.setAssignedDealerId(subAssignedDealerId);
		return routineCleaningOrderDtoMapper.updateByPrimaryKeySelective(routineCleaningOrderDto);
	}	

}
