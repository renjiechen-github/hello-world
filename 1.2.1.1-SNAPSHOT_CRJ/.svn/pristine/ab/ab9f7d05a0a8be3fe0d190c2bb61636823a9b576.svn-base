package com.room1000.suborder.livingproblemorder.service.impl;

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
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderDtoMapper;
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderHisDtoMapper;
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderValueDtoMapper;
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderValueHisDtoMapper;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderDto;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderHisDto;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderValueDto;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderValueHisDto;
import com.room1000.suborder.livingproblemorder.service.ILivingProblemOrderService;
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
 * Created on 2017年3月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("LivingProblemOrderService")
public class LivingProblemOrderServiceImpl implements ILivingProblemOrderService
{

	/**
	 * logger
	 */
	// private static Logger logger =
	// LoggerFactory.getLogger(LivingProblemOrderServiceImpl.class);

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
	 * livingProblemOrderDtoMapper
	 */
	@Autowired
	private LivingProblemOrderDtoMapper livingProblemOrderDtoMapper;

	/**
	 * livingProblemOrderValueDtoMapper
	 */
	@Autowired
	private LivingProblemOrderValueDtoMapper livingProblemOrderValueDtoMapper;

	/**
	 * livingProblemOrderHisDtoMapper
	 */
	@Autowired
	private LivingProblemOrderHisDtoMapper livingProblemOrderHisDtoMapper;

	/**
	 * livingProblemOrderValueHisDtoMapper
	 */
	@Autowired
	private LivingProblemOrderValueHisDtoMapper livingProblemOrderValueHisDtoMapper;

	@Override
	public void inputSubOrderInfoWithTrans(LivingProblemOrderDto livingProblemOrderDto, WorkOrderDto workOrderDto,
			Long currentStaffId, String realPath)
	{

		workOrderDto.setAppointmentDate(livingProblemOrderDto.getAppointmentDate());
		workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

		workOrderService.createWorkOrderWithTrans(workOrderDto);

		livingProblemOrderDto.setWorkOrderId(workOrderDto.getId());
		livingProblemOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
		livingProblemOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
		livingProblemOrderDto.setStateDate(DateUtil.getDBDateTime());
		livingProblemOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		livingProblemOrderDto.setActualDealerId(currentStaffId);
		livingProblemOrderDto.setUpdatePersonId(currentStaffId);
		livingProblemOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		livingProblemOrderDto
				.setImageUrl(SubOrderUtil.uploadImage(livingProblemOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
		AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("LIVING_PROBLEM_ORDER_PROCESS");
		livingProblemOrderDto.setAttrCatgId(attrCatg.getId());
		livingProblemOrderDtoMapper.insert(livingProblemOrderDto);
		this.updateSubOrderWithTrans(livingProblemOrderDto, currentStaffId, "");

		workOrderDto.setType(WorkOrderTypeDef.LIVING_PROBLEM_ORDER);
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			String orderName = workOrderService.generateOrderName(workOrderDto);
			workOrderDto.setName(orderName);
		}
		workOrderDto.setRefId(livingProblemOrderDto.getId());
		workOrderDto.setCode(livingProblemOrderDto.getCode());
		workOrderDto.setSubActualDealerId(livingProblemOrderDto.getActualDealerId());
		workOrderDto.setSubComments(livingProblemOrderDto.getComments());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		startProcess(livingProblemOrderDto, workOrderDto);

	}

	/**
	 * 
	 * Description: startProcess
	 * 
	 * @author jinyanan
	 *
	 * @param livingProblemOrderDto
	 *          livingProblemOrderDto
	 * @param workOrderDto
	 *          workOrderDto
	 */
	private void startProcess(LivingProblemOrderDto livingProblemOrderDto, WorkOrderDto workOrderDto)
	{
		String processInstanceKey = "LivingProblemOrderProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
		variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
		variables.put(ActivitiVariableDef.LIVING_PROBLEM_ORDER, livingProblemOrderDto);
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
	public LivingProblemOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl)
	{
		LivingProblemOrderDto livingProblemOrderDto = livingProblemOrderDtoMapper.selectDetailById(id);
		if (StringUtils.isNotEmpty(livingProblemOrderDto.getImageUrl()) && needFormatImageUrl)
		{
			livingProblemOrderDto.setImageUrl(SubOrderUtil.getImagePath(livingProblemOrderDto.getImageUrl()));
		}
		if (!singleDetail)
		{
			AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(livingProblemOrderDto.getAttrCatgId());
			livingProblemOrderDto.setAttrCatg(attrCatg);
			List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(id);
			livingProblemOrderDto.setSubOrderValueList(livingProblemOrderValueDtoList);
		}

		return livingProblemOrderDto;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return List<LivingProblemOrderValueDto>
	 */
	private List<LivingProblemOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id)
	{
		List<LivingProblemOrderValueDto> livingProblemOrderValueList = livingProblemOrderValueDtoMapper
				.selectBySubOrderId(id);
		for (LivingProblemOrderValueDto livingProblemOrderValueDto : livingProblemOrderValueList)
		{
			if (AttrTypeDef.PICTURE_UPLOAD.equals(livingProblemOrderValueDto.getAttrType()))
			{
				livingProblemOrderValueDto.setTextInput(SubOrderUtil.getImagePath(livingProblemOrderValueDto.getTextInput()));
			}
		}
		return livingProblemOrderValueList;
	}

	@Override
	public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
			Long currentStaffId)
	{
		// 修改看房订单的管家id和指定处理人
		LivingProblemOrderDto livingProblemOrderDto = this.getSubOrderByCode(code);
		livingProblemOrderDto.setStaffId(staffId);
		livingProblemOrderDto.clearAssignedDealer();
		livingProblemOrderDto.setAssignedDealerId(staffId);
		livingProblemOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(livingProblemOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(livingProblemOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, subOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void reassignOrderWithTrans(String code, Long staffId,
			List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList, Long currentStaffId)
	{
		LivingProblemOrderDto livingProblemOrderDto = this.getSubOrderByCode(code);
		livingProblemOrderDto.setStaffId(staffId);
		livingProblemOrderDto.clearAssignedDealer();
		livingProblemOrderDto.setAssignedDealerId(staffId);
		livingProblemOrderDto.setActualDealerId(currentStaffId);
		livingProblemOrderDto.setState(SubOrderStateDef.REASSIGNING);
		livingProblemOrderDto.setStateDate(DateUtil.getDBDateTime());

		Long priority = this.updateSubOrderWithTrans(livingProblemOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(livingProblemOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
		workOrderDto.setSubAssignedDealerId(staffId);
		workOrderDto.setSubActualDealerId(livingProblemOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		// 第二次更新为了插入历史表
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, livingProblemOrderValueDtoList, priority);

		SubOrderUtil.sendMessage(workOrderDto, livingProblemOrderDto.getAssignedDealerId(),
				OrderPushModelDef.LIVING_PROBLEM_ORDER);
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
		LivingProblemOrderDto record = this.getSubOrderByCode(code);
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
	 * @return LivingProblemOrderDto
	 */
	private LivingProblemOrderDto getSubOrderByCode(String code)
	{
		return livingProblemOrderDtoMapper.selectByCode(code);
	}

	/**
	 * 
	 * Description: 更新看房的同时，插入历史表
	 * 
	 * @author jinyanan
	 *
	 * @param livingProblemOrderDto
	 *          livingProblemOrderDto
	 * @param closeOrder
	 *          closeOrder
	 * @return LivingProblemOrderDto
	 */
	private Long updateSubOrderAndAddHis(LivingProblemOrderDto livingProblemOrderDto, boolean closeOrder, String str)
	{
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(livingProblemOrderDto.getWorkOrderId(), ActivitiVariableDef.LIVING_PROBLEM_ORDER,
				livingProblemOrderDto);
		LivingProblemOrderDto oldLivingProblemOrderDto = livingProblemOrderDtoMapper
				.selectByPrimaryKey(livingProblemOrderDto.getId());
		if (oldLivingProblemOrderDto == null)
		{
			oldLivingProblemOrderDto = livingProblemOrderDtoMapper.selectByCode(livingProblemOrderDto.getCode());
		}
		oldLivingProblemOrderDto.setUpdatePersonId(livingProblemOrderDto.getUpdatePersonId());
		oldLivingProblemOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		if (closeOrder)
		{
			oldLivingProblemOrderDto.setState(SubOrderStateDef.CLOSED);
			oldLivingProblemOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		if (!str.equals(""))
		{
			livingProblemOrderDto.setImageUrl(null);
		}
		livingProblemOrderDtoMapper.updateByPrimaryKey(livingProblemOrderDto);
		// LivingProblemOrderHisDto hisDto =
		// this.createHisDto(oldLivingProblemOrderDto);
		LivingProblemOrderHisDto hisDto = SubOrderUtil.createHisDto(oldLivingProblemOrderDto,
				LivingProblemOrderHisDto.class, getPriority(oldLivingProblemOrderDto.getId()));
		if (BooleanFlagDef.BOOLEAN_TRUE.equals(livingProblemOrderDto.getChangeFlag()))
		{
			hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
			hisDto.setStateDate(DateUtil.getDBDateTime());
			hisDto.setUpdateDate(DateUtil.getDBDateTime());

		}
		livingProblemOrderHisDtoMapper.insert(hisDto);
		return hisDto.getPriority();
	}

	/**
	 * 
	 * Description: 创建历史dto
	 * 
	 * @author jinyanan
	 *
	 * @param oldLivingProblemOrderDto
	 *          oldLivingProblemOrderDto
	 * @return LivingProblemOrderHisDto
	 */
	// private LivingProblemOrderHisDto createHisDto(LivingProblemOrderDto
	// oldLivingProblemOrderDto) {
	// LivingProblemOrderHisDto livingProblemOrderHisDto = new
	// LivingProblemOrderHisDto();
	// Long maxPriority =
	// livingProblemOrderHisDtoMapper.selectMaxPriority(oldLivingProblemOrderDto.getId());
	// if (maxPriority == null) {
	// maxPriority = 0L;
	// }
	// SubOrderUtil.createdHisDto(oldLivingProblemOrderDto,
	// livingProblemOrderHisDto, maxPriority + 1);
	// return livingProblemOrderHisDto;
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
		Long priority = livingProblemOrderHisDtoMapper.selectMaxPriority(subOrderId);
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
	 * Description: 插入LivingProblemOrderValue
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param subOrderValueDtoList
	 *          subOrderValueDtoList
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList, Long priority)
	{
		LivingProblemOrderDto livingProblemOrderDto = livingProblemOrderDtoMapper.selectByCode(code);
		for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList)
		{
			LivingProblemOrderValueDto livingProblemOrderValueDto = (LivingProblemOrderValueDto) subOrderValueDto;
			livingProblemOrderValueDto.setSubOrderId(livingProblemOrderDto.getId());
			doInsertSubOrderValue(livingProblemOrderValueDto);
			addSubOrderValueHis(livingProblemOrderValueDto, priority);
		}

	}

	@Override
	public void takeOrderWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
			Long currentStaffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, livingProblemOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void processOrderWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
			Long currentStaffId, String realPath, Boolean submitFlag)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, livingProblemOrderValueDtoList, realPath, priority);
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
	 * @param livingProblemOrderValueDtoList
	 *          livingProblemOrderValueDtoList
	 * @param realPath
	 *          realPath
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
			String realPath, Long priority)
	{
		LivingProblemOrderDto livingProblemOrderDto = livingProblemOrderDtoMapper.selectByCode(code);
		for (LivingProblemOrderValueDto livingProblemOrderValueDto : livingProblemOrderValueDtoList)
		{
			this.checkImage(livingProblemOrderValueDto, livingProblemOrderDto.getWorkOrderId(), realPath);
			livingProblemOrderValueDto.setSubOrderId(livingProblemOrderDto.getId());
			doInsertSubOrderValue(livingProblemOrderValueDto);
			addSubOrderValueHis(livingProblemOrderValueDto, priority);
		}

	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param livingProblemOrderValueDto
	 *          livingProblemOrderValueDto
	 */
	private void doInsertSubOrderValue(LivingProblemOrderValueDto livingProblemOrderValueDto)
	{
		LivingProblemOrderValueDto existLivingProblemOrderValueDto = livingProblemOrderValueDtoMapper
				.selectByAttrPath(livingProblemOrderValueDto);
		if (existLivingProblemOrderValueDto != null)
		{
			livingProblemOrderValueDtoMapper.deleteByPrimaryKey(existLivingProblemOrderValueDto.getId());
		}
		livingProblemOrderValueDtoMapper.insert(livingProblemOrderValueDto);

	}

	/**
	 * 
	 * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
	 * 
	 * @author jinyanan
	 *
	 * @param livingProblemOrderValueDto
	 *          livingProblemOrderValueDto
	 * @param priority
	 *          priority
	 */
	private void addSubOrderValueHis(LivingProblemOrderValueDto livingProblemOrderValueDto, Long priority)
	{
		LivingProblemOrderValueHisDto livingProblemOrderValueHisDto = SubOrderUtil.createHisDto(livingProblemOrderValueDto,
				LivingProblemOrderValueHisDto.class, priority);
		livingProblemOrderValueHisDtoMapper.insert(livingProblemOrderValueHisDto);
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param livingProblemOrderValueDto
	 *          livingProblemOrderValueDto
	 * @param workOrderId
	 *          workOrderId
	 * @param realPath
	 *          realPath
	 */
	private void checkImage(LivingProblemOrderValueDto livingProblemOrderValueDto, Long workOrderId, String realPath)
	{
		AttrDto attr = attrService.getAttrById(livingProblemOrderValueDto.getAttrId());
		if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType()))
		{
			livingProblemOrderValueDto
					.setTextInput(SubOrderUtil.uploadImage(livingProblemOrderValueDto.getTextInput(), workOrderId, realPath));
		}

	}

	@Override
	public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
			List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList, Long currentStaffId, String realPath)
	{
		LivingProblemOrderDto livingProblemOrderDto = this.getSubOrderByCode(code);
		livingProblemOrderDto.setStaffId(staffId);
		livingProblemOrderDto.clearAssignedDealer();
		livingProblemOrderDto.setAssignedDealerId(staffId);
		livingProblemOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(livingProblemOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(livingProblemOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, livingProblemOrderValueDtoList, realPath, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void staffTaceWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
			Long currentStaffId, String realPath)
	{
		LivingProblemOrderDto livingProblemOrderDto = this.getSubOrderByCode(code);
		livingProblemOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
		livingProblemOrderDto.setStateDate(DateUtil.getDBDateTime());
		livingProblemOrderDto.setActualDealerId(currentStaffId);
		Long priority = this.updateSubOrderWithTrans(livingProblemOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(livingProblemOrderDto.getWorkOrderId());
		workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
		workOrderDto.setSubActualDealerId(livingProblemOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, livingProblemOrderValueDtoList, realPath, priority);

	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str)
	{
		return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str)
	{
		LivingProblemOrderDto livingProblemOrderDto = (LivingProblemOrderDto) subOrderDto;
		livingProblemOrderDto.setUpdatePersonId(updatePersonId);
		livingProblemOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		return this.updateSubOrderAndAddHis(livingProblemOrderDto, closeOrder, str);
	}

	// @Override
	// public String getStateName(String state) {
	// return
	// livingProblemOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
	// }

	@Override
	public void passInStaffReviewWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
			Long currentStaffId, String realPath)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, livingProblemOrderValueDtoList, realPath, priority);
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public LivingProblemOrderValueDto selectByAttrPath(LivingProblemOrderValueDto livingProblemOrderValueDto)
	{
		return livingProblemOrderValueDtoMapper.selectByAttrPath(livingProblemOrderValueDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getHis(Long subOrderId)
	{
		return (List<T>) livingProblemOrderHisDtoMapper.selectById(subOrderId);
	}

	@Override
	public String getWorkOrderType()
	{
		return WorkOrderTypeDef.LIVING_PROBLEM_ORDER;
	}
	
	@Override
	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
	{
		LivingProblemOrderDto livingProblemOrderDto = new LivingProblemOrderDto();
		livingProblemOrderDto.setId(subOrderId);
		livingProblemOrderDto.setAssignedDealerId(subAssignedDealerId);
		return livingProblemOrderDtoMapper.updateByPrimaryKeySelective(livingProblemOrderDto);
	}	

}
