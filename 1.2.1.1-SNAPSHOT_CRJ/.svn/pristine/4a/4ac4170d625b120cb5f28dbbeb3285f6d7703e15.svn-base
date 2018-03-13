package com.room1000.suborder.cleaningorder.service.impl;

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
import com.room1000.suborder.cleaningorder.dao.CleaningOrderDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningOrderHisDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningOrderValueDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningOrderValueHisDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningTypeDtoMapper;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderHisDto;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderValueDto;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderValueHisDto;
import com.room1000.suborder.cleaningorder.dto.CleaningTypeDto;
import com.room1000.suborder.cleaningorder.service.ICleaningOrderService;
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
@Service("CleaningOrderService")
public class CleaningOrderServiceImpl implements ICleaningOrderService {

    /**
     * logger
     */
    // private static Logger logger = LoggerFactory.getLogger(CleaningOrderServiceImpl.class);

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
     * cleaningOrderDtoMapper
     */
    @Autowired
    private CleaningOrderDtoMapper cleaningOrderDtoMapper;

    /**
     * cleaningOrderValueDtoMapper
     */
    @Autowired
    private CleaningOrderValueDtoMapper cleaningOrderValueDtoMapper;

    /**
     * cleaningOrderHisDtoMapper
     */
    @Autowired
    private CleaningOrderHisDtoMapper cleaningOrderHisDtoMapper;

    /**
     * cleaningTypeDtoMapper
     */
    @Autowired
    private CleaningTypeDtoMapper cleaningTypeDtoMapper;

    /**
     * cleaningOrderValueHisDtoMapper
     */
    @Autowired
    private CleaningOrderValueHisDtoMapper cleaningOrderValueHisDtoMapper;

    @Override
    public void inputSubOrderInfoWithTrans(CleaningOrderDto cleaningOrderDto, WorkOrderDto workOrderDto, Long staffId,
        String realPath) {

        workOrderDto.setRentalLeaseOrderId(cleaningOrderDto.getRentalLeaseOrderId());
        workOrderDto.setAppointmentDate(cleaningOrderDto.getAppointmentDate());
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

        workOrderService.createWorkOrderWithTrans(workOrderDto);

        cleaningOrderDto.setWorkOrderId(workOrderDto.getId());
        cleaningOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
        cleaningOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        cleaningOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        cleaningOrderDto.setActualDealerId(staffId);
        cleaningOrderDto
            .setImageUrl(SubOrderUtil.uploadImage(cleaningOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
        cleaningOrderDto.setUpdatePersonId(staffId);
        cleaningOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("CLEANING_ORDER_PROCESS");
        cleaningOrderDto.setAttrCatgId(attrCatg.getId());
        cleaningOrderDtoMapper.insert(cleaningOrderDto);
        this.updateSubOrderWithTrans(cleaningOrderDto, staffId, "");

        workOrderDto.setType(WorkOrderTypeDef.CLEANING_ORDER);
        if (StringUtils.isEmpty(workOrderDto.getName())) {
            String orderName = workOrderService.generateOrderName(workOrderDto);
            workOrderDto.setName(orderName);
        }
        workOrderDto.setRefId(cleaningOrderDto.getId());
        workOrderDto.setCode(cleaningOrderDto.getCode());
        workOrderDto.setSubActualDealerId(cleaningOrderDto.getActualDealerId());
        workOrderDto.setSubComments(cleaningOrderDto.getComments());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        startProcess(cleaningOrderDto, workOrderDto);

    }

    /**
     * 
     * Description: startProcess
     * 
     * @author jinyanan
     *
     * @param cleaningOrderDto cleaningOrderDto
     * @param workOrderDto workOrderDto
     */
    private void startProcess(CleaningOrderDto cleaningOrderDto, WorkOrderDto workOrderDto) {
        String processInstanceKey = "CleaningOrderProcess";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
        variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
        variables.put(ActivitiVariableDef.CLEANING_ORDER, cleaningOrderDto);
        IProcessStart start = new ProcessStartImpl();
        start.flowStart(processInstanceKey, variables);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail) {
        return (T) this.getOrderDetailById(subOrderId, singleDetail, true);
    }

    @Override
    public CleaningOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl) {
        CleaningOrderDto cleaningOrderDto = cleaningOrderDtoMapper.selectDetailById(id);
        if (StringUtils.isNotEmpty(cleaningOrderDto.getImageUrl()) && needFormatImageUrl) {
            cleaningOrderDto.setImageUrl(SubOrderUtil.getImagePath(cleaningOrderDto.getImageUrl()));
        }
        if (!singleDetail) {
            AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(cleaningOrderDto.getAttrCatgId());
            cleaningOrderDto.setAttrCatg(attrCatg);
            List<CleaningOrderValueDto> cleaningOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(id);
            cleaningOrderDto.setSubOrderValueList(cleaningOrderValueDtoList);
        }

        return cleaningOrderDto;
    }

    /**
     * 
     * Description: 根据子订单id查询所有子订单value
     * 
     * @author jinyanan
     *
     * @param id id
     * @return List<CleaningOrderValueDto>
     */
    private List<CleaningOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id) {
        List<CleaningOrderValueDto> cleaningOrderValueList = cleaningOrderValueDtoMapper.selectBySubOrderId(id);
        for (CleaningOrderValueDto cleaningOrderValueDto : cleaningOrderValueList) {
            if (AttrTypeDef.PICTURE_UPLOAD.equals(cleaningOrderValueDto.getAttrType())) {
                cleaningOrderValueDto.setTextInput(SubOrderUtil.getImagePath(cleaningOrderValueDto.getTextInput()));
            }
        }
        return cleaningOrderValueList;
    }

    @Override
    public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId) {
        // 修改staffId和指定处理人
        CleaningOrderDto cleaningOrderDto = this.getSubOrderByCode(code);
        cleaningOrderDto.setStaffId(staffId);
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setAssignedDealerId(staffId);
        cleaningOrderDto.setActualDealerId(currentStaffId);

        Long priority = this.updateSubOrderWithTrans(cleaningOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(cleaningOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, subOrderValueDtoList, priority);

        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void reassignOrderWithTrans(String code, Long staffId, List<CleaningOrderValueDto> cleaningOrderValueDtoList,
        Long currentStaffId) {

        CleaningOrderDto cleaningOrderDto = this.getSubOrderByCode(code);
        cleaningOrderDto.setStaffId(staffId);
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setAssignedDealerId(staffId);
        cleaningOrderDto.setActualDealerId(currentStaffId);
        cleaningOrderDto.setState(SubOrderStateDef.REASSIGNING);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());

        Long priority = this.updateSubOrderWithTrans(cleaningOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(cleaningOrderDto.getWorkOrderId());
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
        workOrderDto.setSubAssignedDealerId(staffId);
        workOrderDto.setSubActualDealerId(cleaningOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        // 第二次更新为了插入历史表
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, cleaningOrderValueDtoList, priority);
        
        // 孙诚明 by 2017-07-14
        SubOrderUtil.sendMessage(workOrderDto, cleaningOrderDto.getAssignedDealerId(), OrderPushModelDef.CLEANING_ORDER);

    }

    /**
     * 
     * Description: 更新子订单单当前处理人
     * 
     * @author jinyanan
     *
     * @param code code
     * @param actualDealerId actualDealerId
     * @return Long
     */
    private Long updateSubOrderActualDealer(String code, Long actualDealerId) {
        CleaningOrderDto record = this.getSubOrderByCode(code);
        record.setActualDealerId(actualDealerId);
        Long priority = this.updateSubOrderWithTrans(record, actualDealerId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(actualDealerId);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        return priority;
    }

    /**
     * 
     * Description: 根据code查询子订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @return CleaningOrderDto
     */
    private CleaningOrderDto getSubOrderByCode(String code) {
        return cleaningOrderDtoMapper.selectByCode(code);
    }

    /**
     * 
     * Description: 更新子订单的同时，插入子订单历史表
     * 
     * @author jinyanan
     *
     * @param cleaningOrderDto cleaningOrderDto
     * @param closeOrder closeOrder
     * @return CleaningOrderDto
     */
    private Long updateSubOrderAndAddHis(CleaningOrderDto cleaningOrderDto, boolean closeOrder, String str) {
        IProcessTask task = new ProcessTaskImpl();
        // 子订单放入流程实例中
        task.putInstanceVariable(cleaningOrderDto.getWorkOrderId(), ActivitiVariableDef.CLEANING_ORDER,
            cleaningOrderDto);
        // 查询子订单的上次记录
        CleaningOrderDto oldCleaningOrderDto = cleaningOrderDtoMapper.selectByPrimaryKey(cleaningOrderDto.getId());
        if (oldCleaningOrderDto == null) {
            oldCleaningOrderDto = cleaningOrderDtoMapper.selectByCode(cleaningOrderDto.getCode());
        }
        oldCleaningOrderDto.setUpdatePersonId(cleaningOrderDto.getUpdatePersonId());
        oldCleaningOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        if (closeOrder) {
            oldCleaningOrderDto.setState(SubOrderStateDef.CLOSED);
            oldCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        }
        // 全量update，如果存在null，则用null替换之前的值
        if (!str.equals(""))
        {
        	cleaningOrderDto.setImageUrl(null);
        }
        cleaningOrderDtoMapper.updateByPrimaryKey(cleaningOrderDto);
        CleaningOrderHisDto hisDto = SubOrderUtil.createHisDto(oldCleaningOrderDto, CleaningOrderHisDto.class,
            getPriority(oldCleaningOrderDto.getId()));
        if (BooleanFlagDef.BOOLEAN_TRUE.equals(cleaningOrderDto.getChangeFlag())) {
            hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
            hisDto.setStateDate(DateUtil.getDBDateTime());
            hisDto.setUpdateDate(DateUtil.getDBDateTime());
        }
        cleaningOrderHisDtoMapper.insert(hisDto);
        return hisDto.getPriority();
    }

    /**
     * 
     * Description: 创建历史dto
     * 
     * @author jinyanan
     *
     * @param oldCleaningOrderDto oldCleaningOrderDto
     * @return CleaningOrderHisDto
     */
    // private CleaningOrderHisDto createHisDto(CleaningOrderDto oldCleaningOrderDto) {
    // CleaningOrderHisDto cleaningOrderHisDto = new CleaningOrderHisDto();
    // Long maxPriority = cleaningOrderHisDtoMapper.selectMaxPriority(oldCleaningOrderDto.getId());
    // if (maxPriority == null) {
    // maxPriority = 0L;
    // }
    // SubOrderUtil.createdHisDto(oldCleaningOrderDto, cleaningOrderHisDto, maxPriority + 1);
    // return cleaningOrderHisDto;
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
        Long priority = cleaningOrderHisDtoMapper.selectMaxPriority(subOrderId);
        if (priority == null) {
            return 1L;
        }
        else {
            return priority + 1;
        }
    }

    /**
     * 
     * Description: 插入子订单value
     * 
     * @author jinyanan
     *
     * @param code code
     * @param priority priority
     * @param subOrderValueDtoList subOrderValueDtoList
     */
    private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long priority) {
        CleaningOrderDto cleaningOrderDto = cleaningOrderDtoMapper.selectByCode(code);

        for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList) {
            CleaningOrderValueDto cleaningOrderValueDto = (CleaningOrderValueDto) subOrderValueDto;
            cleaningOrderValueDto.setSubOrderId(cleaningOrderDto.getId());
            doInsertSubOrderValue(cleaningOrderValueDto);
            addSubOrderValueHis(cleaningOrderValueDto, priority);

        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param cleaningOrderValueDto cleaningOrderValueDto
     */
    private void doInsertSubOrderValue(CleaningOrderValueDto cleaningOrderValueDto) {
        CleaningOrderValueDto existCleaningOrderValueDto = cleaningOrderValueDtoMapper
            .selectByAttrPath(cleaningOrderValueDto);
        if (existCleaningOrderValueDto != null) {
            cleaningOrderValueDtoMapper.deleteByPrimaryKey(existCleaningOrderValueDto.getId());
        }
        cleaningOrderValueDtoMapper.insert(cleaningOrderValueDto);
    }

    /**
     * 
     * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
     * 
     * @author jinyanan
     *
     * @param cleaningOrderValueDto cleaningOrderValueDto
     * @param priority priority
     */
    private void addSubOrderValueHis(CleaningOrderValueDto cleaningOrderValueDto, Long priority) {
        CleaningOrderValueHisDto cleaningOrderValueHisDto = SubOrderUtil.createHisDto(cleaningOrderValueDto,
            CleaningOrderValueHisDto.class, priority);
        cleaningOrderValueHisDtoMapper.insert(cleaningOrderValueHisDto);
    }

    @Override
    public void takeOrderWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueDtoList,
        Long currentStaffId) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, cleaningOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void processOrderWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, cleaningOrderValueDtoList, realPath, priority);
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
     * @param cleaningOrderValueDtoList cleaningOrderValueDtoList
     * @param realPath realPath
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<CleaningOrderValueDto> cleaningOrderValueDtoList,
        String realPath, Long priority) {

        CleaningOrderDto cleaningOrderDto = cleaningOrderDtoMapper.selectByCode(code);

        for (CleaningOrderValueDto cleaningOrderValueDto : cleaningOrderValueDtoList) {
            this.checkImage(cleaningOrderValueDto, cleaningOrderDto.getWorkOrderId(), realPath);
            cleaningOrderValueDto.setSubOrderId(cleaningOrderDto.getId());
            doInsertSubOrderValue(cleaningOrderValueDto);
            addSubOrderValueHis(cleaningOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param cleaningOrderValueDto cleaningOrderValueDto
     * @param workOrderId workOrderId
     * @param realPath realPath
     */
    private void checkImage(CleaningOrderValueDto cleaningOrderValueDto, Long workOrderId, String realPath) {
        AttrDto attr = attrService.getAttrById(cleaningOrderValueDto.getAttrId());
        if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType())) {
            cleaningOrderValueDto
                .setTextInput(SubOrderUtil.uploadImage(cleaningOrderValueDto.getTextInput(), workOrderId, realPath));
        }

    }

    @Override
    public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<CleaningOrderValueDto> cleaningOrderValueDtoList, Long currentStaffId, String realPath) {

        CleaningOrderDto cleaningOrderDto = this.getSubOrderByCode(code);
        cleaningOrderDto.setStaffId(staffId);
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setAssignedDealerId(staffId);
        cleaningOrderDto.setActualDealerId(currentStaffId);

        Long priority = this.updateSubOrderWithTrans(cleaningOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(cleaningOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, cleaningOrderValueDtoList, realPath, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void staffTaceWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueDtoList,
        Long currentStaffId, String realPath) {

        CleaningOrderDto cleaningOrderDto = this.getSubOrderByCode(code);
        cleaningOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        cleaningOrderDto.setActualDealerId(currentStaffId);
        Long priority = this.updateSubOrderWithTrans(cleaningOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(cleaningOrderDto.getWorkOrderId());
        workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
        workOrderDto.setSubActualDealerId(cleaningOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, cleaningOrderValueDtoList, realPath, priority);

    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str) {
        return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str) {
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) subOrderDto;
        cleaningOrderDto.setUpdatePersonId(updatePersonId);
        cleaningOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        return this.updateSubOrderAndAddHis(cleaningOrderDto, closeOrder, str);
    }

    // @Override
    // public String getStateName(String state) {
    // return cleaningOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
    // }

    @Override
    public void payWithTrans(String code, Long staffId, Long paidMoney) {
        this.updateSubOrderActualDealer(code, staffId);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public CleaningOrderValueDto selectByAttrPath(CleaningOrderValueDto cleaningOrderValueDto) {
        return cleaningOrderValueDtoMapper.selectByAttrPath(cleaningOrderValueDto);
    }

    @Override
    public void passInStaffReviewWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueList,
        Long currentStaffId, String realPath) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);

        this.insertSubOrderValue(code, cleaningOrderValueList, realPath, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public List<CleaningTypeDto> getCleaningType() {
        return cleaningTypeDtoMapper.selectAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getHis(Long subOrderId) {
        return (List<T>) cleaningOrderHisDtoMapper.selectById(subOrderId);
    }

    @Override
    public String getWorkOrderType() {
        return WorkOrderTypeDef.CLEANING_ORDER;
    }
    
  	@Override
  	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
  	{
  		CleaningOrderDto cleaningOrderDto = new CleaningOrderDto();
  		cleaningOrderDto.setId(subOrderId);
  		cleaningOrderDto.setAssignedDealerId(subAssignedDealerId);
  		return cleaningOrderDtoMapper.updateByPrimaryKeySelective(cleaningOrderDto);
  	}    

}
