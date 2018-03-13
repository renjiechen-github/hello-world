package com.room1000.suborder.ownerrepairorder.service.impl;

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
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderDtoMapper;
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderHisDtoMapper;
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderValueDtoMapper;
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderValueHisDtoMapper;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderHisDto;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueDto;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueHisDto;
import com.room1000.suborder.ownerrepairorder.service.IOwnerRepairOrderService;
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
@Service("OwnerRepairOrderService")
public class OwnerRepairOrderServiceImpl implements IOwnerRepairOrderService
{

	/**
	 * logger
	 */
	// private static Logger logger =
	// LoggerFactory.getLogger(OwnerRepairOrderServiceImpl.class);

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
	 * ownerRepairOrderDtoMapper
	 */
	@Autowired
	private OwnerRepairOrderDtoMapper ownerRepairOrderDtoMapper;

	/**
	 * ownerRepairOrderValueDtoMapper
	 */
	@Autowired
	private OwnerRepairOrderValueDtoMapper ownerRepairOrderValueDtoMapper;

	/**
	 * ownerRepairOrderHisDtoMapper
	 */
	@Autowired
	private OwnerRepairOrderHisDtoMapper ownerRepairOrderHisDtoMapper;

	/**
	 * ownerRepairOrderValueHisDtoMapper
	 */
	@Autowired
	private OwnerRepairOrderValueHisDtoMapper ownerRepairOrderValueHisDtoMapper;

	@Override
	public void inputSubOrderInfoWithTrans(OwnerRepairOrderDto ownerRepairOrderDto, WorkOrderDto workOrderDto,
			Long currentStaffId, String realPath)
	{

		workOrderDto.setAppointmentDate(ownerRepairOrderDto.getAppointmentDate());
		workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

		workOrderService.createWorkOrderWithTrans(workOrderDto);

		ownerRepairOrderDto.setWorkOrderId(workOrderDto.getId());
		ownerRepairOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
		ownerRepairOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
		ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());
		ownerRepairOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		ownerRepairOrderDto.setActualDealerId(currentStaffId);
		ownerRepairOrderDto.setUpdatePersonId(currentStaffId);
		ownerRepairOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		ownerRepairOrderDto
				.setImageUrl(SubOrderUtil.uploadImage(ownerRepairOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
		AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("OWNER_REPAIR_ORDER_PROCESS");
		ownerRepairOrderDto.setAttrCatgId(attrCatg.getId());
		ownerRepairOrderDtoMapper.insert(ownerRepairOrderDto);
		this.updateSubOrderWithTrans(ownerRepairOrderDto, currentStaffId, "");

		workOrderDto.setType(WorkOrderTypeDef.OWNER_REPAIR_ORDER);
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			String orderName = workOrderService.generateOrderName(workOrderDto);
			workOrderDto.setName(orderName);
		}
		workOrderDto.setRefId(ownerRepairOrderDto.getId());
		workOrderDto.setTakeHouseOrderId(ownerRepairOrderDto.getTakeHouseOrderId());
		workOrderDto.setCode(ownerRepairOrderDto.getCode());
		workOrderDto.setSubActualDealerId(ownerRepairOrderDto.getActualDealerId());
		workOrderDto.setSubComments(ownerRepairOrderDto.getComments());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		startProcess(ownerRepairOrderDto, workOrderDto);

	}

	/**
	 * 
	 * Description: startProcess
	 * 
	 * @author jinyanan
	 *
	 * @param ownerRepairOrderDto
	 *          ownerRepairOrderDto
	 * @param workOrderDto
	 *          workOrderDto
	 */
	private void startProcess(OwnerRepairOrderDto ownerRepairOrderDto, WorkOrderDto workOrderDto)
	{
		String processInstanceKey = "OwnerRepairOrderProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
		variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
		variables.put(ActivitiVariableDef.OWNER_REPAIR_ORDER, ownerRepairOrderDto);
		IProcessStart start = new ProcessStartImpl();
		start.flowStart(processInstanceKey, variables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail)
	{
		return (T) this.getOrderDetailById(subOrderId, singleDetail, true);
	}

	@Override
	public OwnerRepairOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = ownerRepairOrderDtoMapper.selectDetailById(id);
		if (StringUtils.isNotEmpty(ownerRepairOrderDto.getImageUrl()) && needFormatImageUrl)
		{
			ownerRepairOrderDto.setImageUrl(SubOrderUtil.getImagePath(ownerRepairOrderDto.getImageUrl()));
		}
		if (!singleDetail)
		{
			AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(ownerRepairOrderDto.getAttrCatgId());
			ownerRepairOrderDto.setAttrCatg(attrCatg);
			List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(id);
			ownerRepairOrderDto.setSubOrderValueList(ownerRepairOrderValueDtoList);
		}

		return ownerRepairOrderDto;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return List<OwnerRepairOrderValueDto>
	 */
	private List<OwnerRepairOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id)
	{
		List<OwnerRepairOrderValueDto> ownerRepairOrderValueList = ownerRepairOrderValueDtoMapper.selectBySubOrderId(id);
		for (OwnerRepairOrderValueDto ownerRepairOrderValueDto : ownerRepairOrderValueList)
		{
			if (AttrTypeDef.PICTURE_UPLOAD.equals(ownerRepairOrderValueDto.getAttrType()))
			{
				ownerRepairOrderValueDto.setTextInput(SubOrderUtil.getImagePath(ownerRepairOrderValueDto.getTextInput()));
			}
		}
		return ownerRepairOrderValueList;
	}

	@Override
	public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
			Long currentStaffId)
	{
		// 修改看房订单的管家id和指定处理人
		OwnerRepairOrderDto ownerRepairOrderDto = this.getSubOrderByCode(code);
		ownerRepairOrderDto.setStaffId(staffId);
		ownerRepairOrderDto.clearAssignedDealer();
		ownerRepairOrderDto.setAssignedDealerId(staffId);
		ownerRepairOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(ownerRepairOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(ownerRepairOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, subOrderValueDtoList, priority);

		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void reassignOrderWithTrans(String code, Long staffId,
			List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList, Long currentStaffId)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = this.getSubOrderByCode(code);
		ownerRepairOrderDto.setStaffId(staffId);
		ownerRepairOrderDto.clearAssignedDealer();
		ownerRepairOrderDto.setAssignedDealerId(staffId);
		ownerRepairOrderDto.setActualDealerId(currentStaffId);
		ownerRepairOrderDto.setState(SubOrderStateDef.REASSIGNING);
		ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());

		Long priority = this.updateSubOrderWithTrans(ownerRepairOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(ownerRepairOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
		workOrderDto.setSubAssignedDealerId(staffId);
		workOrderDto.setSubActualDealerId(ownerRepairOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		// 第二次更新为了插入历史表
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, ownerRepairOrderValueDtoList, priority);

		SubOrderUtil.sendMessage(workOrderDto, ownerRepairOrderDto.getAssignedDealerId(),
				OrderPushModelDef.OWNER_REPAIR_ORDER);
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
	 *          Long actualDea
	 * @return Long
	 */
	private Long updateSubOrderActualDealer(String code, Long actualDealerId)
	{
		OwnerRepairOrderDto record = this.getSubOrderByCode(code);
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
	 * @return OwnerRepairOrderDto
	 */
	private OwnerRepairOrderDto getSubOrderByCode(String code)
	{
		return ownerRepairOrderDtoMapper.selectByCode(code);
	}

	/**
	 * 
	 * Description: 更新订单的同时，插入历史表
	 * 
	 * @author jinyanan
	 *
	 * @param ownerRepairOrderDto
	 *          ownerRepairOrderDto
	 * @param closeOrder
	 *          closeOrder
	 * @return OwnerRepairOrderDto
	 */
	private Long updateSubOrderAndAddHis(OwnerRepairOrderDto ownerRepairOrderDto, boolean closeOrder, String str)
	{
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(ownerRepairOrderDto.getWorkOrderId(), ActivitiVariableDef.OWNER_REPAIR_ORDER,
				ownerRepairOrderDto);
		OwnerRepairOrderDto oldOwnerRepairOrderDto = ownerRepairOrderDtoMapper
				.selectByPrimaryKey(ownerRepairOrderDto.getId());
		if (oldOwnerRepairOrderDto == null)
		{
			oldOwnerRepairOrderDto = ownerRepairOrderDtoMapper.selectByCode(ownerRepairOrderDto.getCode());
		}
		oldOwnerRepairOrderDto.setUpdatePersonId(ownerRepairOrderDto.getUpdatePersonId());
		oldOwnerRepairOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		if (closeOrder)
		{
			oldOwnerRepairOrderDto.setState(SubOrderStateDef.CLOSED);
			oldOwnerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		if (!str.equals(""))
		{			
			ownerRepairOrderDto.setImageUrl(null);
		}
		ownerRepairOrderDtoMapper.updateByPrimaryKey(ownerRepairOrderDto);
		OwnerRepairOrderHisDto hisDto = SubOrderUtil.createHisDto(oldOwnerRepairOrderDto, OwnerRepairOrderHisDto.class,
				getPriority(oldOwnerRepairOrderDto.getId()));
		if (BooleanFlagDef.BOOLEAN_TRUE.equals(ownerRepairOrderDto.getChangeFlag()))
		{
			hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
			hisDto.setStateDate(DateUtil.getDBDateTime());
			hisDto.setUpdateDate(DateUtil.getDBDateTime());

		}
		ownerRepairOrderHisDtoMapper.insert(hisDto);
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
		Long priority = ownerRepairOrderHisDtoMapper.selectMaxPriority(subOrderId);
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
	 * @param oldOwnerRepairOrderDto
	 *          oldOwnerRepairOrderDto
	 * @return OwnerRepairOrderHisDto
	 */
	// private OwnerRepairOrderHisDto createHisDto(OwnerRepairOrderDto
	// oldOwnerRepairOrderDto) {
	// OwnerRepairOrderHisDto ownerRepairOrderHisDto = new OwnerRepairOrderHisDto();
	// Long maxPriority =
	// ownerRepairOrderHisDtoMapper.selectMaxPriority(oldOwnerRepairOrderDto.getId());
	// if (maxPriority == null) {
	// maxPriority = 0L;
	// }
	// SubOrderUtil.createdHisDto(oldOwnerRepairOrderDto, ownerRepairOrderHisDto,
	// maxPriority + 1);
	// return ownerRepairOrderHisDto;
	// }

	/**
	 * 
	 * Description: 插入OwnerRepairOrderValue
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param subOrderValueDtoList
	 *          ownerRepairOrderValueDtoList
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList, Long priority)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = ownerRepairOrderDtoMapper.selectByCode(code);
		for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList)
		{
			OwnerRepairOrderValueDto ownerRepairOrderValueDto = (OwnerRepairOrderValueDto) subOrderValueDto;
			ownerRepairOrderValueDto.setSubOrderId(ownerRepairOrderDto.getId());
			doInsertSubOrderValue(ownerRepairOrderValueDto);
			addSubOrderValueHis(ownerRepairOrderValueDto, priority);
		}

	}

	@Override
	public void takeOrderWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
			Long currentStaffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, ownerRepairOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void processOrderWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
			Long currentStaffId, String realPath, Boolean submitFlag)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, ownerRepairOrderValueDtoList, realPath, priority);
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
	 * @param ownerRepairOrderValueDtoList
	 *          ownerRepairOrderValueDtoList
	 * @param realPath
	 *          realPath
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
			String realPath, Long priority)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = ownerRepairOrderDtoMapper.selectByCode(code);
		for (OwnerRepairOrderValueDto ownerRepairOrderValueDto : ownerRepairOrderValueDtoList)
		{
			this.checkImage(ownerRepairOrderValueDto, ownerRepairOrderDto.getWorkOrderId(), realPath);
			ownerRepairOrderValueDto.setSubOrderId(ownerRepairOrderDto.getId());
			doInsertSubOrderValue(ownerRepairOrderValueDto);
			addSubOrderValueHis(ownerRepairOrderValueDto, priority);
		}

	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param ownerRepairOrderValueDto
	 *          ownerRepairOrderValueDto
	 */
	private void doInsertSubOrderValue(OwnerRepairOrderValueDto ownerRepairOrderValueDto)
	{
		OwnerRepairOrderValueDto existOwnerRepairOrderValueDto = ownerRepairOrderValueDtoMapper
				.selectByAttrPath(ownerRepairOrderValueDto);
		if (existOwnerRepairOrderValueDto != null)
		{
			ownerRepairOrderValueDtoMapper.deleteByPrimaryKey(existOwnerRepairOrderValueDto.getId());
		}
		ownerRepairOrderValueDtoMapper.insert(ownerRepairOrderValueDto);

	}

	/**
	 * 
	 * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
	 * 
	 * @author jinyanan
	 *
	 * @param ownerRepairOrderValueDto
	 *          ownerRepairOrderValueDto
	 * @param priority
	 *          priority
	 */
	private void addSubOrderValueHis(OwnerRepairOrderValueDto ownerRepairOrderValueDto, Long priority)
	{
		OwnerRepairOrderValueHisDto ownerRepairOrderValueHisDto = SubOrderUtil.createHisDto(ownerRepairOrderValueDto,
				OwnerRepairOrderValueHisDto.class, priority);
		ownerRepairOrderValueHisDtoMapper.insert(ownerRepairOrderValueHisDto);
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param ownerRepairOrderValueDto
	 *          ownerRepairOrderValueDto
	 * @param workOrderId
	 *          workOrderId
	 * @param realPath
	 *          realPath
	 */
	private void checkImage(OwnerRepairOrderValueDto ownerRepairOrderValueDto, Long workOrderId, String realPath)
	{
		AttrDto attr = attrService.getAttrById(ownerRepairOrderValueDto.getAttrId());
		if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType()))
		{
			ownerRepairOrderValueDto
					.setTextInput(SubOrderUtil.uploadImage(ownerRepairOrderValueDto.getTextInput(), workOrderId, realPath));
		}

	}

	@Override
	public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
			List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList, Long currentStaffId, String realPath)
	{

		OwnerRepairOrderDto ownerRepairOrderDto = this.getSubOrderByCode(code);
		ownerRepairOrderDto.setStaffId(staffId);
		ownerRepairOrderDto.clearAssignedDealer();
		ownerRepairOrderDto.setAssignedDealerId(staffId);
		ownerRepairOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(ownerRepairOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(ownerRepairOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, ownerRepairOrderValueDtoList, realPath, priority);

		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void staffTaceWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
			Long currentStaffId, String realPath)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = this.getSubOrderByCode(code);
		ownerRepairOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
		ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());
		ownerRepairOrderDto.setActualDealerId(currentStaffId);
		Long priority = this.updateSubOrderWithTrans(ownerRepairOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(ownerRepairOrderDto.getWorkOrderId());
		workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
		workOrderDto.setSubActualDealerId(ownerRepairOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, ownerRepairOrderValueDtoList, realPath, priority);

	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str)
	{
		return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = (OwnerRepairOrderDto) subOrderDto;
		ownerRepairOrderDto.setUpdatePersonId(updatePersonId);
		ownerRepairOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		return this.updateSubOrderAndAddHis(ownerRepairOrderDto, closeOrder, str);
	}

	// @Override
	// public String getStateName(String state) {
	// return
	// ownerRepairOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
	// }

	@Override
	public void passInStaffReviewWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
			Long currentStaffId, String realPath)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, ownerRepairOrderValueDtoList, realPath, priority);
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public OwnerRepairOrderValueDto selectByAttrPath(OwnerRepairOrderValueDto ownerRepairOrderValueDto)
	{
		return ownerRepairOrderValueDtoMapper.selectByAttrPath(ownerRepairOrderValueDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getHis(Long subOrderId)
	{
		return (List<T>) ownerRepairOrderHisDtoMapper.selectById(subOrderId);
	}

	@Override
	public String getWorkOrderType()
	{
		return WorkOrderTypeDef.OWNER_REPAIR_ORDER;
	}
	
	@Override
	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
	{
		OwnerRepairOrderDto ownerRepairOrderDto = new OwnerRepairOrderDto();
		ownerRepairOrderDto.setId(subOrderId);
		ownerRepairOrderDto.setAssignedDealerId(subAssignedDealerId);
		return ownerRepairOrderDtoMapper.updateByPrimaryKeySelective(ownerRepairOrderDto);
	}

}
