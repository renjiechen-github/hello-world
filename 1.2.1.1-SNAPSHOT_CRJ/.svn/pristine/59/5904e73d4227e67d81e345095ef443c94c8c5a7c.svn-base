package com.room1000.suborder.complaintorder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.attr.dao.AttrDtoMapper;
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
import com.room1000.suborder.complaintorder.dao.ComplaintOrderDtoMapper;
import com.room1000.suborder.complaintorder.dao.ComplaintOrderHisDtoMapper;
import com.room1000.suborder.complaintorder.dao.ComplaintOrderValueDtoMapper;
import com.room1000.suborder.complaintorder.dao.ComplaintOrderValueHisDtoMapper;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderDto;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderHisDto;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderValueDto;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderValueHisDto;
import com.room1000.suborder.complaintorder.service.IComplaintOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
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
@Service("ComplaintOrderService")
public class ComplaintOrderServiceImpl implements IComplaintOrderService {

    /**
     * logger
     */
    // private static Logger logger = LoggerFactory.getLogger(ComplaintOrderServiceImpl.class);

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
     * complaintOrderDtoMapper
     */
    @Autowired
    private ComplaintOrderDtoMapper complaintOrderDtoMapper;

    /**
     * complaintOrderValueDtoMapper
     */
    @Autowired
    private ComplaintOrderValueDtoMapper complaintOrderValueDtoMapper;

    /**
     * complaintOrderHisDtoMapper
     */
    @Autowired
    private ComplaintOrderHisDtoMapper complaintOrderHisDtoMapper;

    /**
     * attrDtoMapper
     */
    @Autowired
    private AttrDtoMapper attrDtoMapper;

    /**
     * complaintOrderValueHisDtoMapper
     */
    @Autowired
    private ComplaintOrderValueHisDtoMapper complaintOrderValueHisDtoMapper;

    @Override
    public void inputSubOrderInfoWithTrans(ComplaintOrderDto complaintOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId, String realPath) {

        workOrderDto.setAppointmentDate(complaintOrderDto.getComplaintDate());
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);
        workOrderDto.setType(WorkOrderTypeDef.COMPLAINT_ORDER);

        workOrderService.createWorkOrderWithTrans(workOrderDto);

        complaintOrderDto.setWorkOrderId(workOrderDto.getId());
        complaintOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
        complaintOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
        complaintOrderDto.setStateDate(DateUtil.getDBDateTime());
        complaintOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        complaintOrderDto.setActualDealerId(currentStaffId);
        complaintOrderDto.setUpdatePersonId(currentStaffId);
        complaintOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        complaintOrderDto.setImageUrl(SubOrderUtil.uploadImage(complaintOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
        AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("COMPLAINT_ORDER_PROCESS");
        complaintOrderDto.setAttrCatgId(attrCatg.getId());
        complaintOrderDtoMapper.insert(complaintOrderDto);
        this.updateSubOrderWithTrans(complaintOrderDto, currentStaffId, "");
        workOrderDto.setRefId(complaintOrderDto.getId());
        workOrderDto.setCode(complaintOrderDto.getCode());
        workOrderDto.setSubActualDealerId(complaintOrderDto.getActualDealerId());
        workOrderDto.setSubComments(complaintOrderDto.getComments());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        startProcess(complaintOrderDto, workOrderDto);
    }

    /**
     * 
     * Description: startProcess
     * 
     * @author jinyanan
     *
     * @param complaintOrderDto complaintOrderDto
     * @param workOrderDto workOrderDto
     */
    private void startProcess(ComplaintOrderDto complaintOrderDto, WorkOrderDto workOrderDto) {
        String processInstanceKey = "ComplaintOrderProcess";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
        variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
        variables.put(ActivitiVariableDef.COMPLAINT_ORDER, complaintOrderDto);
        IProcessStart start = new ProcessStartImpl();
        start.flowStart(processInstanceKey, variables);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail) {
    	return (T) this.getOrderDetailById(subOrderId, singleDetail, true);
    }
    
    @Override
    public ComplaintOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl) {
      ComplaintOrderDto complaintOrderDto = complaintOrderDtoMapper.selectDetailById(id);
      if (StringUtils.isNotEmpty(complaintOrderDto.getImageUrl()) && needFormatImageUrl) {
      	complaintOrderDto.setImageUrl(SubOrderUtil.getImagePath(complaintOrderDto.getImageUrl()));
      }
      if (!singleDetail) {
          AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(complaintOrderDto.getAttrCatgId());
          complaintOrderDto.setAttrCatg(attrCatg);
          List<ComplaintOrderValueDto> complaintOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(id);
          complaintOrderDto.setSubOrderValueList(complaintOrderValueDtoList);
      }

      return complaintOrderDto;
    }    
    
    

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @return List<ComplaintOrderValueDto>
     */
    private List<ComplaintOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id) {
        List<ComplaintOrderValueDto> complaintOrderValueList = complaintOrderValueDtoMapper.selectBySubOrderId(id);
        for (ComplaintOrderValueDto complaintOrderValueDto : complaintOrderValueList) {
            if (AttrTypeDef.PICTURE_UPLOAD.equals(complaintOrderValueDto.getAttrType())) {
                complaintOrderValueDto.setTextInput(SubOrderUtil.getImagePath(complaintOrderValueDto.getTextInput()));
            }
        }
        return complaintOrderValueList;
    }

    @Override
    public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId) {
        // 修改指定处理人
        ComplaintOrderDto complaintOrderDto = this.getSubOrderByCode(code);
        complaintOrderDto.setStaffId(staffId);
        complaintOrderDto.clearAssignedDealer();
        complaintOrderDto.setAssignedDealerId(staffId);
        complaintOrderDto.setActualDealerId(currentStaffId);

        Long priority = updateSubOrderWithTrans(complaintOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(complaintOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, subOrderValueDtoList, priority);

        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void reassignOrderWithTrans(String code, Long staffId,
        List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long currentStaffId) {

        ComplaintOrderDto complaintOrderDto = this.getSubOrderByCode(code);
        complaintOrderDto.setStaffId(staffId);
        complaintOrderDto.clearAssignedDealer();
        complaintOrderDto.setAssignedDealerId(staffId);
        complaintOrderDto.setActualDealerId(currentStaffId);
        complaintOrderDto.setState(SubOrderStateDef.REASSIGNING);
        complaintOrderDto.setStateDate(DateUtil.getDBDateTime());

        Long priority = this.updateSubOrderWithTrans(complaintOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(complaintOrderDto.getWorkOrderId());
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
        workOrderDto.setSubAssignedDealerId(staffId);
        workOrderDto.setSubActualDealerId(complaintOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        // 第二次更新为了插入历史表
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, complaintOrderValueDtoList, priority);
        
        SubOrderUtil.sendMessage(workOrderDto, complaintOrderDto.getAssignedDealerId(), OrderPushModelDef.COMPLAINT_ORDER);

    }

    /**
     * 
     * Description: 更新当前处理人
     * 
     * @author jinyanan
     *
     * @param code code
     * @param actualDealerId actualDealerId
     * @return Long
     */
    private Long updateSubOrderActualDealer(String code, Long actualDealerId) {
        ComplaintOrderDto record = this.getSubOrderByCode(code);
        record.setActualDealerId(actualDealerId);

        Long priority = updateSubOrderWithTrans(record, actualDealerId, "");
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
     * @param code code
     * @return ComplaintOrderDto
     */
    private ComplaintOrderDto getSubOrderByCode(String code) {
        return complaintOrderDtoMapper.selectByCode(code);
    }

    /**
     * 
     * Description: 更新订单的同时，插入历史表
     * 
     * @author jinyanan
     *
     * @param complaintOrderDto complaintOrderDto
     * @param closeOrder closeOrder
     * @return Long
     */
    private Long updateSubOrderAndAddHis(ComplaintOrderDto complaintOrderDto, boolean closeOrder, String str) {
        IProcessTask task = new ProcessTaskImpl();
        task.putInstanceVariable(complaintOrderDto.getWorkOrderId(), ActivitiVariableDef.COMPLAINT_ORDER,
            complaintOrderDto);
        ComplaintOrderDto oldComplaintOrderDto = complaintOrderDtoMapper.selectByPrimaryKey(complaintOrderDto.getId());
        if (oldComplaintOrderDto == null) {
            oldComplaintOrderDto = complaintOrderDtoMapper.selectByCode(complaintOrderDto.getCode());
        }
        oldComplaintOrderDto.setUpdatePersonId(complaintOrderDto.getUpdatePersonId());
        oldComplaintOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        if (closeOrder) {
            oldComplaintOrderDto.setState(SubOrderStateDef.CLOSED);
            oldComplaintOrderDto.setStateDate(DateUtil.getDBDateTime());
        }
        if (!str.equals(""))
        {
        	complaintOrderDto.setImageUrl(null);
        }
        complaintOrderDtoMapper.updateByPrimaryKey(complaintOrderDto);
        // ComplaintOrderHisDto hisDto = this.createHisDto(oldComplaintOrderDto);
        ComplaintOrderHisDto hisDto = SubOrderUtil.createHisDto(oldComplaintOrderDto, ComplaintOrderHisDto.class,
            getPriority(oldComplaintOrderDto.getId()));
        if (BooleanFlagDef.BOOLEAN_TRUE.equals(complaintOrderDto.getChangeFlag())) {
            hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
            hisDto.setStateDate(DateUtil.getDBDateTime());
            hisDto.setUpdateDate(DateUtil.getDBDateTime());
        }
        complaintOrderHisDtoMapper.insert(hisDto);
        return hisDto.getPriority();
    }

    /**
     * 
     * Description: 创建历史dto
     * 
     * @author jinyanan
     *
     * @param oldComplaintOrderDto oldComplaintOrderDto
     * @return ComplaintOrderHisDto
     */
    // private ComplaintOrderHisDto createHisDto(ComplaintOrderDto oldComplaintOrderDto) {
    //// ComplaintOrderHisDto complaintOrderHisDto = new ComplaintOrderHisDto();
    //// Long maxPriority = complaintOrderHisDtoMapper.selectMaxPriority(oldComplaintOrderDto.getId());
    //// if (maxPriority == null) {
    //// maxPriority = 0L;
    //// }
    // ComplaintOrderHisDto complaintOrderHisDto = SubOrderUtil.createHisDto(oldComplaintOrderDto,
    // ComplaintOrderHisDto.class, getPriority(oldComplaintOrderDto.getId()));
    //// SubOrderUtil.createdHisDto(oldComplaintOrderDto, complaintOrderHisDto,
    // getPriority(oldComplaintOrderDto.getId()));
    // return complaintOrderHisDto;
    // }

    /**
     * 
     * Description: 获取本次历史对应的priority
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return Long
     */
    private Long getPriority(Long subOrderId) {
        Long priority = complaintOrderHisDtoMapper.selectMaxPriority(subOrderId);
        if (priority == null) {
            return 1L;
        }
        else {
            return priority + 1;
        }
    }

    /**
     * 
     * Description: 插入ComplaintOrderValue
     * 
     * @author jinyanan
     *
     * @param code code
     * @param priority priority
     * @param subOrderValueDtoList subOrderValueDtoList
     */
    private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long priority) {
        ComplaintOrderDto complaintOrderDto = complaintOrderDtoMapper.selectByCode(code);
        for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList) {
            ComplaintOrderValueDto complaintOrderValueDto = (ComplaintOrderValueDto) subOrderValueDto;
            complaintOrderValueDto.setSubOrderId(complaintOrderDto.getId());
            doInsertSubOrderValue(complaintOrderValueDto);
            addSubOrderValueHis(complaintOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param complaintOrderValueDto complaintOrderValueDto
     */
    private void doInsertSubOrderValue(ComplaintOrderValueDto complaintOrderValueDto) {
        ComplaintOrderValueDto existComplaintOrderValueDto = complaintOrderValueDtoMapper
            .selectByAttrPath(complaintOrderValueDto);
        if (existComplaintOrderValueDto != null) {
            complaintOrderValueDtoMapper.deleteByPrimaryKey(existComplaintOrderValueDto.getId());
        }
        complaintOrderValueDtoMapper.insert(complaintOrderValueDto);
    }

    /**
     * 
     * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
     * 
     * @author jinyanan
     *
     * @param complaintOrderValueDto complaintOrderValueDto
     * @param priority priority
     */
    private void addSubOrderValueHis(ComplaintOrderValueDto complaintOrderValueDto, Long priority) {
        ComplaintOrderValueHisDto complaintOrderValueHisDto = SubOrderUtil.createHisDto(complaintOrderValueDto,
            ComplaintOrderValueHisDto.class, priority);
        complaintOrderValueHisDtoMapper.insert(complaintOrderValueHisDto);
    }

    @Override
    public void takeOrderWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList,
        Long currentStaffId) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, complaintOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);
    }

    @Override
    public void processOrderWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, complaintOrderValueDtoList, realPath, priority);
        if (submitFlag) {
            SubOrderUtil.dispatcherOrder(code);
        }

    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param realPath realPath
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList,
        String realPath, Long priority) {
        ComplaintOrderDto complaintOrderDto = complaintOrderDtoMapper.selectByCode(code);
        for (ComplaintOrderValueDto complaintOrderValueDto : complaintOrderValueDtoList) {
            this.checkImage(complaintOrderValueDto, complaintOrderDto.getWorkOrderId(), realPath);
            complaintOrderValueDto.setSubOrderId(complaintOrderDto.getId());
            doInsertSubOrderValue(complaintOrderValueDto);
            addSubOrderValueHis(complaintOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param complaintOrderValueDto complaintOrderValueDto
     * @param workOrderId workOrderId
     * @param realPath realPath
     */
    private void checkImage(ComplaintOrderValueDto complaintOrderValueDto, Long workOrderId, String realPath) {
        AttrDto attr = attrDtoMapper.selectByPrimaryKey(complaintOrderValueDto.getAttrId());
        if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType())) {
            complaintOrderValueDto
                .setTextInput(SubOrderUtil.uploadImage(complaintOrderValueDto.getTextInput(), workOrderId, realPath));
        }

    }

    @Override
    public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long currentStaffId) {
        ComplaintOrderDto complaintOrderDto = this.getSubOrderByCode(code);
        complaintOrderDto.setStaffId(staffId);
        complaintOrderDto.clearAssignedDealer();
        complaintOrderDto.setAssignedDealerId(staffId);
        complaintOrderDto.setActualDealerId(currentStaffId);

        Long priority = this.updateSubOrderWithTrans(complaintOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(complaintOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, complaintOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void staffTaceWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList,
        Long currentStaffId) {
        ComplaintOrderDto complaintOrderDto = this.getSubOrderByCode(code);
        complaintOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
        complaintOrderDto.setStateDate(DateUtil.getDBDateTime());
        complaintOrderDto.setActualDealerId(currentStaffId);
        Long priority = this.updateSubOrderWithTrans(complaintOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(complaintOrderDto.getWorkOrderId());
        workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
        workOrderDto.setSubActualDealerId(complaintOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, complaintOrderValueDtoList, priority);

    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str) {
        return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str) {
        ComplaintOrderDto complaintOrderDto = (ComplaintOrderDto) subOrderDto;
        complaintOrderDto.setUpdatePersonId(updatePersonId);
        complaintOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        return this.updateSubOrderAndAddHis(complaintOrderDto, closeOrder, str);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param <T> <T>
     * @param subOrderDto subOrderDto
     * @param updatePersonId updatePersonId
     * @param closeOrder closeOrder
     * @param a a
     * @return Long
     */
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder) {
        return null;
    }

    // @Override
    // public String getStateName(String state) {
    // return complaintOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
    // }

    @Override
    public ComplaintOrderValueDto selectByAttrPath(ComplaintOrderValueDto complaintOrderValueDto) {
        return complaintOrderValueDtoMapper.selectByAttrPath(complaintOrderValueDto);
    }

    @Override
    public void passInStaffReviewWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList,
        Long currentStaffId) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);

        this.insertSubOrderValue(code, complaintOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getHis(Long subOrderId) {
        return (List<T>) complaintOrderHisDtoMapper.selectById(subOrderId);
    }

    @Override
    public String getWorkOrderType() {
        return WorkOrderTypeDef.COMPLAINT_ORDER;
    }
    
  	@Override
  	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
  	{
  		ComplaintOrderDto complaintOrderDto = new ComplaintOrderDto();
  		complaintOrderDto.setId(subOrderId);
  		complaintOrderDto.setAssignedDealerId(subAssignedDealerId);
  		return complaintOrderDtoMapper.updateByPrimaryKeySelective(complaintOrderDto);
  	}   
}
