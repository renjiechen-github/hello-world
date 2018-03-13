package com.room1000.suborder.otherorder.service.impl;

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
import com.room1000.suborder.otherorder.dao.OtherOrderDtoMapper;
import com.room1000.suborder.otherorder.dao.OtherOrderHisDtoMapper;
import com.room1000.suborder.otherorder.dao.OtherOrderValueDtoMapper;
import com.room1000.suborder.otherorder.dao.OtherOrderValueHisDtoMapper;
import com.room1000.suborder.otherorder.dto.OtherOrderDto;
import com.room1000.suborder.otherorder.dto.OtherOrderHisDto;
import com.room1000.suborder.otherorder.dto.OtherOrderValueDto;
import com.room1000.suborder.otherorder.dto.OtherOrderValueHisDto;
import com.room1000.suborder.otherorder.service.IOtherOrderService;
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
 * Created on 2017年3月6日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("OtherOrderService")
public class OtherOrderServiceImpl implements IOtherOrderService
{

	/**
	 * logger
	 */
	// private static Logger logger =
	// LoggerFactory.getLogger(OtherOrderServiceImpl.class);

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
	 * otherOrderDtoMapper
	 */
	@Autowired
	private OtherOrderDtoMapper otherOrderDtoMapper;

	/**
	 * otherOrderValueDtoMapper
	 */
	@Autowired
	private OtherOrderValueDtoMapper otherOrderValueDtoMapper;

	/**
	 * otherOrderHisDtoMapper
	 */
	@Autowired
	private OtherOrderHisDtoMapper otherOrderHisDtoMapper;

	/**
	 * otherOrderValueHisDtoMapper
	 */
	@Autowired
	private OtherOrderValueHisDtoMapper otherOrderValueHisDtoMapper;

	@Override
	public void inputSubOrderInfoWithTrans(OtherOrderDto otherOrderDto, WorkOrderDto workOrderDto, Long currentStaffId, String realPath)
	{
		workOrderDto.setAppointmentDate(otherOrderDto.getAppointmentDate());
		workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

		workOrderService.createWorkOrderWithTrans(workOrderDto);

		otherOrderDto.setWorkOrderId(workOrderDto.getId());
		otherOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
		otherOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
		if (null == otherOrderDto.getStateDate())
		{
			otherOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		if (null == otherOrderDto.getCreatedDate())
		{
			otherOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		}
		otherOrderDto.setActualDealerId(currentStaffId);
		otherOrderDto.setUpdatePersonId(currentStaffId);
		if (null == otherOrderDto.getUpdateDate())
		{
			otherOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		}
		otherOrderDto.setImageUrl(SubOrderUtil.uploadImage(otherOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
		AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("OTHER_ORDER_PROCESS");
		otherOrderDto.setAttrCatgId(attrCatg.getId());
		otherOrderDtoMapper.insert(otherOrderDto);
		this.updateSubOrderWithTrans(otherOrderDto, currentStaffId, "");

		workOrderDto.setType(WorkOrderTypeDef.OTHER_ORDER);
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			String orderName = workOrderService.generateOrderName(workOrderDto);
			workOrderDto.setName(orderName);
		}
		workOrderDto.setRefId(otherOrderDto.getId());
		workOrderDto.setCode(otherOrderDto.getCode());
		workOrderDto.setSubActualDealerId(otherOrderDto.getActualDealerId());
		workOrderDto.setSubComments(otherOrderDto.getComments());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		startProcess(otherOrderDto, workOrderDto);

	}

	/**
	 * 
	 * Description: startProcess
	 * 
	 * @author jinyanan
	 *
	 * @param otherOrderDto
	 *          otherOrderDto
	 * @param workOrderDto
	 *          workOrderDto
	 */
	private void startProcess(OtherOrderDto otherOrderDto, WorkOrderDto workOrderDto)
	{
		String processInstanceKey = "OtherOrderProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
		variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
		variables.put(ActivitiVariableDef.OTHER_ORDER, otherOrderDto);
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
  public OtherOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl) {
  	OtherOrderDto otherOrderDto = otherOrderDtoMapper.selectDetailById(id);
    if (StringUtils.isNotEmpty(otherOrderDto.getImageUrl()) && needFormatImageUrl) {
    	otherOrderDto.setImageUrl(SubOrderUtil.getImagePath(otherOrderDto.getImageUrl()));
    }
		if (!singleDetail)
		{
			AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(otherOrderDto.getAttrCatgId());
			otherOrderDto.setAttrCatg(attrCatg);
			List<OtherOrderValueDto> otherOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(id);
			otherOrderDto.setSubOrderValueList(otherOrderValueDtoList);
		}

    return otherOrderDto;
  }  	

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return List<OtherOrderValueDto>
	 */
	private List<OtherOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id)
	{
		List<OtherOrderValueDto> orderValueList = otherOrderValueDtoMapper.selectBySubOrderId(id);
		for (OtherOrderValueDto otherOrderValueDto : orderValueList)
		{
			if (AttrTypeDef.PICTURE_UPLOAD.equals(otherOrderValueDto.getAttrType()))
			{
				otherOrderValueDto.setTextInput(SubOrderUtil.getImagePath(otherOrderValueDto.getTextInput()));
			}
		}
		return orderValueList;
	}

	@Override
	public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
			Long currentStaffId)
	{
		// 修改订单的管家id和指定处理人
		OtherOrderDto otherOrderDto = this.getSubOrderByCode(code);
		otherOrderDto.setStaffId(staffId);
		;
		otherOrderDto.clearAssignedDealer();
		otherOrderDto.setAssignedDealerId(staffId);
		otherOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(otherOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(otherOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, subOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void reassignOrderWithTrans(String code, Long staffId, List<OtherOrderValueDto> otherOrderValueDtoList,
			Long currentStaffId)
	{
		OtherOrderDto otherOrderDto = this.getSubOrderByCode(code);
		otherOrderDto.setStaffId(staffId);
		otherOrderDto.clearAssignedDealer();
		otherOrderDto.setAssignedDealerId(staffId);
		otherOrderDto.setActualDealerId(currentStaffId);
		otherOrderDto.setState(SubOrderStateDef.REASSIGNING);
		otherOrderDto.setStateDate(DateUtil.getDBDateTime());

		Long priority = this.updateSubOrderWithTrans(otherOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(otherOrderDto.getWorkOrderId());
		workOrderDto.clearSubField();
		workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
		workOrderDto.setSubAssignedDealerId(staffId);
		workOrderDto.setSubActualDealerId(otherOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);
		// 第二次更新为了插入历史表
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, otherOrderValueDtoList, priority);

		SubOrderUtil.sendMessage(workOrderDto, otherOrderDto.getAssignedDealerId(), OrderPushModelDef.OTHER_ORDER);

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
		OtherOrderDto record = this.getSubOrderByCode(code);
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
	 * @return OtherOrderDto
	 */
	private OtherOrderDto getSubOrderByCode(String code)
	{
		return otherOrderDtoMapper.selectByCode(code);
	}

	/**
	 * 
	 * Description: 更新看房的同时，插入历史表
	 * 
	 * @author jinyanan
	 *
	 * @param otherOrderDto
	 *          otherOrderDto
	 * @param closeOrder
	 *          closeOrder
	 * @return OtherOrderDto
	 */
	private Long updateSubOrderAndAddHis(OtherOrderDto otherOrderDto, boolean closeOrder, String str)
	{
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(otherOrderDto.getWorkOrderId(), ActivitiVariableDef.OTHER_ORDER, otherOrderDto);
		OtherOrderDto oldOtherOrderDto = otherOrderDtoMapper.selectByPrimaryKey(otherOrderDto.getId());
		if (oldOtherOrderDto == null)
		{
			oldOtherOrderDto = otherOrderDtoMapper.selectByCode(otherOrderDto.getCode());
		}
		oldOtherOrderDto.setUpdatePersonId(otherOrderDto.getUpdatePersonId());
		oldOtherOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		if (closeOrder)
		{
			oldOtherOrderDto.setState(SubOrderStateDef.CLOSED);
			oldOtherOrderDto.setStateDate(DateUtil.getDBDateTime());
		}
		if (!str.equals(""))
		{
			otherOrderDto.setImageUrl(null);
		}
		otherOrderDtoMapper.updateByPrimaryKey(otherOrderDto);
		// OtherOrderHisDto hisDto = this.createHisDto(oldOtherOrderDto);
		OtherOrderHisDto hisDto = SubOrderUtil.createHisDto(oldOtherOrderDto, OtherOrderHisDto.class,
				getPriority(oldOtherOrderDto.getId()));
		if (BooleanFlagDef.BOOLEAN_TRUE.equals(otherOrderDto.getChangeFlag()))
		{
			hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
			hisDto.setStateDate(DateUtil.getDBDateTime());
			hisDto.setUpdateDate(DateUtil.getDBDateTime());
		}
		otherOrderHisDtoMapper.insert(hisDto);
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
		Long priority = otherOrderHisDtoMapper.selectMaxPriority(subOrderId);
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
	 * @param oldOtherOrderDto
	 *          oldOtherOrderDto
	 * @return OtherOrderHisDto
	 */
	// private OtherOrderHisDto createHisDto(OtherOrderDto oldOtherOrderDto) {
	// OtherOrderHisDto otherOrderHisDto = new OtherOrderHisDto();
	// Long maxPriority =
	// otherOrderHisDtoMapper.selectMaxPriority(oldOtherOrderDto.getId());
	// if (maxPriority == null) {
	// maxPriority = 0L;
	// }
	// SubOrderUtil.createdHisDto(oldOtherOrderDto, otherOrderHisDto, maxPriority +
	// 1);
	// return otherOrderHisDto;
	// }

	/**
	 * 
	 * Description: 插入OtherOrderValue
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
		OtherOrderDto otherOrderDto = otherOrderDtoMapper.selectByCode(code);
		for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList)
		{
			OtherOrderValueDto otherOrderValueDto = (OtherOrderValueDto) subOrderValueDto;
			otherOrderValueDto.setSubOrderId(otherOrderDto.getId());
			doInsertSubOrderValue(otherOrderValueDto);
			addSubOrderValueHis(otherOrderValueDto, priority);
		}

	}

	@Override
	public void takeOrderWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, otherOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void processOrderWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId,
			String realPath, Boolean submitFlag)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, otherOrderValueDtoList, realPath, priority);
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
	 * @param otherOrderValueDtoList
	 *          otherOrderValueDtoList
	 * @param realPath
	 *          realPath
	 * @param priority
	 *          priority
	 */
	private void insertSubOrderValue(String code, List<OtherOrderValueDto> otherOrderValueDtoList, String realPath,
			Long priority)
	{
		OtherOrderDto otherOrderDto = otherOrderDtoMapper.selectByCode(code);
		for (OtherOrderValueDto otherOrderValueDto : otherOrderValueDtoList)
		{
			this.checkImage(otherOrderValueDto, otherOrderDto.getWorkOrderId(), realPath);
			otherOrderValueDto.setSubOrderId(otherOrderDto.getId());
			doInsertSubOrderValue(otherOrderValueDto);
			addSubOrderValueHis(otherOrderValueDto, priority);
		}

	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param otherOrderValueDto
	 *          otherOrderValueDto
	 */
	private void doInsertSubOrderValue(OtherOrderValueDto otherOrderValueDto)
	{
		OtherOrderValueDto existOtherOrderValueDto = otherOrderValueDtoMapper.selectByAttrPath(otherOrderValueDto);
		if (existOtherOrderValueDto != null)
		{
			otherOrderValueDtoMapper.deleteByPrimaryKey(existOtherOrderValueDto.getId());
		}
		otherOrderValueDtoMapper.insert(otherOrderValueDto);

	}

	/**
	 * 
	 * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
	 * 
	 * @author jinyanan
	 *
	 * @param otherOrderValueDto
	 *          otherOrderValueDto
	 * @param priority
	 *          priority
	 */
	private void addSubOrderValueHis(OtherOrderValueDto otherOrderValueDto, Long priority)
	{
		OtherOrderValueHisDto otherOrderValueHisDto = SubOrderUtil.createHisDto(otherOrderValueDto,
				OtherOrderValueHisDto.class, priority);
		otherOrderValueHisDtoMapper.insert(otherOrderValueHisDto);
	}

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param otherOrderValueDto
	 *          otherOrderValueDto
	 * @param workOrderId
	 *          workOrderId
	 * @param realPath
	 *          realPath
	 */
	private void checkImage(OtherOrderValueDto otherOrderValueDto, Long workOrderId, String realPath)
	{
		AttrDto attr = attrService.getAttrById(otherOrderValueDto.getAttrId());
		if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType()))
		{
			otherOrderValueDto
					.setTextInput(SubOrderUtil.uploadImage(otherOrderValueDto.getTextInput(), workOrderId, realPath));
		}

	}

	@Override
	public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
			List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId)
	{
		OtherOrderDto otherOrderDto = this.getSubOrderByCode(code);
		otherOrderDto.setStaffId(staffId);
		otherOrderDto.clearAssignedDealer();
		otherOrderDto.setAssignedDealerId(staffId);
		otherOrderDto.setActualDealerId(currentStaffId);

		Long priority = this.updateSubOrderWithTrans(otherOrderDto, currentStaffId, "");
		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
		workOrderDto.setSubActualDealerId(otherOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, otherOrderValueDtoList, priority);

		SubOrderUtil.dispatcherOrder(code);

	}

	@Override
	public void staffTaceWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId)
	{
		OtherOrderDto otherOrderDto = this.getSubOrderByCode(code);
		otherOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
		otherOrderDto.setStateDate(DateUtil.getDBDateTime());
		otherOrderDto.setActualDealerId(currentStaffId);
		Long priority = this.updateSubOrderWithTrans(otherOrderDto, currentStaffId, "");

		WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(otherOrderDto.getWorkOrderId());
		workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
		workOrderDto.setSubActualDealerId(otherOrderDto.getActualDealerId());
		workOrderService.updateWorkOrderWithTrans(workOrderDto);

		this.insertSubOrderValue(code, otherOrderValueDtoList, priority);

	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str)
	{
		return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
	}

	@Override
	public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str)
	{
		OtherOrderDto otherOrderDto = (OtherOrderDto) subOrderDto;
		otherOrderDto.setUpdatePersonId(updatePersonId);
		otherOrderDto.setUpdateDate(DateUtil.getDBDateTime());
		return this.updateSubOrderAndAddHis(otherOrderDto, closeOrder, str);
	}

	// @Override
	// public String getStateName(String state) {
	// return otherOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
	// }

	@Override
	public void passInStaffReviewWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList,
			Long currentStaffId)
	{
		Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
		this.insertSubOrderValue(code, otherOrderValueDtoList, priority);
		SubOrderUtil.dispatcherOrder(code);
	}

	@Override
	public OtherOrderValueDto selectByAttrPath(OtherOrderValueDto otherOrderValueDto)
	{
		return otherOrderValueDtoMapper.selectByAttrPath(otherOrderValueDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getHis(Long subOrderId)
	{
		return (List<T>) otherOrderHisDtoMapper.selectById(subOrderId);
	}

	@Override
	public String getWorkOrderType()
	{
		return WorkOrderTypeDef.OTHER_ORDER;
	}
	
	@Override
	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
	{
		OtherOrderDto otherOrderDto = new OtherOrderDto();
		otherOrderDto.setId(subOrderId);
		otherOrderDto.setAssignedDealerId(subAssignedDealerId);
		return otherOrderDtoMapper.updateByPrimaryKeySelective(otherOrderDto);
	}	

}
