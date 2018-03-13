package com.room1000.suborder.repairorder.service.impl;

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
import com.room1000.suborder.repairorder.dao.RepairOrderDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairOrderHisDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairOrderValueDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairOrderValueHisDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairTypeDtoMapper;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.dto.RepairOrderHisDto;
import com.room1000.suborder.repairorder.dto.RepairOrderValueDto;
import com.room1000.suborder.repairorder.dto.RepairOrderValueHisDto;
import com.room1000.suborder.repairorder.dto.RepairTypeDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;
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
@Service("RepairOrderService")
public class RepairOrderServiceImpl implements IRepairOrderService {

    /**
     * logger
     */
    // private static Logger logger = LoggerFactory.getLogger(RepairOrderServiceImpl.class);

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
     * repairOrderDtoMapper
     */
    @Autowired
    private RepairOrderDtoMapper repairOrderDtoMapper;

    /**
     * repairOrderValueDtoMapper
     */
    @Autowired
    private RepairOrderValueDtoMapper repairOrderValueDtoMapper;

    /**
     * repairOrderHisDtoMapper
     */
    @Autowired
    private RepairOrderHisDtoMapper repairOrderHisDtoMapper;

    /**
     * repairOrderTypeDtoMapper
     */
    @Autowired
    private RepairTypeDtoMapper repairOrderTypeDtoMapper;

    /**
     * repairOrderValueHisDtoMapper
     */
    @Autowired
    private RepairOrderValueHisDtoMapper repairOrderValueHisDtoMapper;

    @Override
    public void inputSubOrderInfoWithTrans(RepairOrderDto repairOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId, String realPath) {
        workOrderDto.setRentalLeaseOrderId(repairOrderDto.getRentalLeaseOrderId());
        workOrderDto.setAppointmentDate(repairOrderDto.getAppointmentDate());
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

        workOrderService.createWorkOrderWithTrans(workOrderDto);

        repairOrderDto.setWorkOrderId(workOrderDto.getId());
        repairOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
        repairOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
        repairOrderDto.setStateDate(DateUtil.getDBDateTime());
        repairOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        repairOrderDto.setPaidMoney(0L);
        repairOrderDto.setPayableMoney(0L);
        repairOrderDto.setActualDealerId(currentStaffId);
        repairOrderDto.setUpdatePersonId(currentStaffId);
        repairOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        repairOrderDto
            .setImageUrl(SubOrderUtil.uploadImage(repairOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
        AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("REPAIR_ORDER_PROCESS");
        repairOrderDto.setAttrCatgId(attrCatg.getId());
        repairOrderDtoMapper.insert(repairOrderDto);
        this.updateSubOrderWithTrans(repairOrderDto, currentStaffId, "");

        workOrderDto.setType(WorkOrderTypeDef.REPAIR_ORDER);
        if (StringUtils.isEmpty(workOrderDto.getName())) {
            String orderName = workOrderService.generateOrderName(workOrderDto);
            workOrderDto.setName(orderName);
        }
        workOrderDto.setRefId(repairOrderDto.getId());
        workOrderDto.setCode(repairOrderDto.getCode());
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderDto.setSubComments(repairOrderDto.getComments());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        startProcess(repairOrderDto, workOrderDto);

    }

    /**
     * 
     * Description: startProcess
     * 
     * @author jinyanan
     *
     * @param repairOrderDto repairOrderDto
     * @param workOrderDto workOrderDto
     */
    private void startProcess(RepairOrderDto repairOrderDto, WorkOrderDto workOrderDto) {
        String processInstanceKey = "RepairOrderProcess";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
        variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
        variables.put(ActivitiVariableDef.REPAIR_ORDER, repairOrderDto);
        IProcessStart start = new ProcessStartImpl();
        start.flowStart(processInstanceKey, variables);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail) {
        return (T) this.getOrderDetailById(subOrderId, singleDetail, true);
    }

    @Override
    public RepairOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl) {
        RepairOrderDto repairOrderDto = repairOrderDtoMapper.selectDetailById(id);
        if (StringUtils.isNotEmpty(repairOrderDto.getImageUrl()) && needFormatImageUrl) {
            repairOrderDto.setImageUrl(SubOrderUtil.getImagePath(repairOrderDto.getImageUrl()));
        }
        if (!singleDetail) {
            AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(repairOrderDto.getAttrCatgId());
            repairOrderDto.setAttrCatg(attrCatg);
            List<RepairOrderValueDto> repairOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(id);
            repairOrderDto.setSubOrderValueList(repairOrderValueDtoList);
        }

        return repairOrderDto;
    }

    /**
     * 
     * Description: 根据子订单id查询所有子订单value
     * 
     * @author jinyanan
     *
     * @param id id
     * @return List<RepairOrderValueDto>
     */
    private List<RepairOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id) {
        List<RepairOrderValueDto> repairOrderValueList = repairOrderValueDtoMapper.selectBySubOrderId(id);
        for (RepairOrderValueDto repairOrderValueDto : repairOrderValueList) {
            if (AttrTypeDef.PICTURE_UPLOAD.equals(repairOrderValueDto.getAttrType())) {
                repairOrderValueDto.setTextInput(SubOrderUtil.getImagePath(repairOrderValueDto.getTextInput()));

            }
        }
        return repairOrderValueList;
    }

    @Override
    public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId) {
        // 修改staffId和指定处理人
        RepairOrderDto repairOrderDto = this.getSubOrderByCode(code);
        repairOrderDto.setStaffId(staffId);
        repairOrderDto.clearAssignedDealer();
        repairOrderDto.setAssignedDealerId(staffId);
        repairOrderDto.setActualDealerId(currentStaffId);

        Long priority = this.updateSubOrderWithTrans(repairOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, subOrderValueDtoList, priority);

        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void reassignOrderWithTrans(String code, Long staffId, List<RepairOrderValueDto> repairOrderValueDtoList,
        Long currentStaffId) {
        RepairOrderDto repairOrderDto = this.getSubOrderByCode(code);
        repairOrderDto.setStaffId(staffId);
        repairOrderDto.clearAssignedDealer();
        repairOrderDto.setAssignedDealerId(staffId);
        repairOrderDto.setActualDealerId(currentStaffId);
        repairOrderDto.setState(SubOrderStateDef.REASSIGNING);
        repairOrderDto.setStateDate(DateUtil.getDBDateTime());

        Long priority = this.updateSubOrderWithTrans(repairOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(repairOrderDto.getWorkOrderId());
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderDto.setSubAssignedDealerId(staffId);
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        // 第二次更新为了插入历史表
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, repairOrderValueDtoList, priority);

        SubOrderUtil.sendMessage(workOrderDto, repairOrderDto.getAssignedDealerId(), OrderPushModelDef.REPAIR_ORDER);
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
        RepairOrderDto record = this.getSubOrderByCode(code);
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
     * @return RepairOrderDto
     */
    private RepairOrderDto getSubOrderByCode(String code) {
        return repairOrderDtoMapper.selectByCode(code);
    }

    /**
     * 
     * Description: 更新子订单的同时，插入子订单历史表
     * 
     * @author jinyanan
     *
     * @param repairOrderDto repairOrderDto
     * @param closeOrder closeOrder
     * @return RepairOrderDto
     */
    private Long updateSubOrderAndAddHis(RepairOrderDto repairOrderDto, boolean closeOrder, String str) {
        IProcessTask task = new ProcessTaskImpl();
        // 子订单放入流程实例中
        task.putInstanceVariable(repairOrderDto.getWorkOrderId(), ActivitiVariableDef.REPAIR_ORDER, repairOrderDto);
        // 查询子订单的上次记录
        RepairOrderDto oldRepairOrderDto = repairOrderDtoMapper.selectByPrimaryKey(repairOrderDto.getId());
        if (oldRepairOrderDto == null) {
            oldRepairOrderDto = repairOrderDtoMapper.selectByCode(repairOrderDto.getCode());
        }
        oldRepairOrderDto.setUpdatePersonId(repairOrderDto.getUpdatePersonId());
        oldRepairOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        if (closeOrder) {
            oldRepairOrderDto.setState(SubOrderStateDef.CLOSED);
            oldRepairOrderDto.setStateDate(DateUtil.getDBDateTime());
        }
        // 全量update，如果存在null，则用null替换之前的值
        if (!str.equals(""))
        {
        	repairOrderDto.setImageUrl(null);
        }
        repairOrderDtoMapper.updateByPrimaryKey(repairOrderDto);
        RepairOrderHisDto hisDto = SubOrderUtil.createHisDto(oldRepairOrderDto, RepairOrderHisDto.class,
            getPriority(oldRepairOrderDto.getId()));
        if (BooleanFlagDef.BOOLEAN_TRUE.equals(repairOrderDto.getChangeFlag())) {
            hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
            hisDto.setStateDate(DateUtil.getDBDateTime());
            hisDto.setUpdateDate(DateUtil.getDBDateTime());
        }
        repairOrderHisDtoMapper.insert(hisDto);
        return hisDto.getPriority();
    }

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
        Long priority = repairOrderHisDtoMapper.selectMaxPriority(subOrderId);
        if (priority == null) {
            return 1L;
        }
        else {
            return priority + 1;
        }
    }

    /**
     * 
     * Description: 创建历史dto
     * 
     * @author jinyanan
     *
     * @param oldRepairOrderDto oldRepairOrderDto
     * @return RepairOrderHisDto
     */
    // private RepairOrderHisDto createHisDto(RepairOrderDto oldRepairOrderDto) {
    // RepairOrderHisDto repairOrderHisDto = new RepairOrderHisDto();
    // Long maxPriority = repairOrderHisDtoMapper.selectMaxPriority(oldRepairOrderDto.getId());
    // if (maxPriority == null) {
    // maxPriority = 0L;
    // }
    // SubOrderUtil.createdHisDto(oldRepairOrderDto, repairOrderHisDto, maxPriority + 1);
    // return repairOrderHisDto;
    // }

    /**
     * 
     * Description: 插入子订单value
     * 
     * @author jinyanan
     *
     * @param code code
     * @param subOrderValueDtoList repairOrderValueDtoList
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long priority) {
        RepairOrderDto repairOrderDto = repairOrderDtoMapper.selectByCode(code);

        for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList) {
            RepairOrderValueDto repairOrderValueDto = (RepairOrderValueDto) subOrderValueDto;
            repairOrderValueDto.setSubOrderId(repairOrderDto.getId());
            doInsertSubOrderValue(repairOrderValueDto);
            // 根据attrPath查询，如果存在则删除，如果不存在则插入
            addSubOrderValueHis(repairOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description: 存在则先删除在新增，不存在则直接新增
     * 
     * @author jinyanan
     *
     * @param repairOrderValueDto repairOrderValueDto
     */
    private void doInsertSubOrderValue(RepairOrderValueDto repairOrderValueDto) {
        RepairOrderValueDto existRepairOrderValueDto = repairOrderValueDtoMapper.selectByAttrPath(repairOrderValueDto);
        if (existRepairOrderValueDto != null) {
            repairOrderValueDtoMapper.deleteByPrimaryKey(existRepairOrderValueDto.getId());
        }
        repairOrderValueDtoMapper.insert(repairOrderValueDto);
    }

    @Override
    public void takeOrderWithTrans(String code, List<RepairOrderValueDto> repairOrderValueDtoList,
        Long currentStaffId) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, repairOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void processOrderWithTrans(String code, Long payableMoney, List<RepairOrderValueDto> repairOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag) {
        RepairOrderDto repairOrderDto = this.getSubOrderByCode(code);
        Long paidMoney = 0L;
        if (repairOrderDto.getPaidMoney() != null) {
            paidMoney = repairOrderDto.getPaidMoney();
        }
        if (payableMoney > paidMoney) {
            repairOrderDto.setPayableMoney(payableMoney);
        }

        repairOrderDto.setActualDealerId(currentStaffId);
        Long priority = this.updateSubOrderWithTrans(repairOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, repairOrderValueDtoList, realPath, priority);

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
     * @param repairOrderValueDtoList repairOrderValueDtoList
     * @param realPath realPath
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<RepairOrderValueDto> repairOrderValueDtoList, String realPath,
        Long priority) {
        RepairOrderDto repairOrderDto = repairOrderDtoMapper.selectByCode(code);

        for (RepairOrderValueDto repairOrderValueDto : repairOrderValueDtoList) {
            this.checkImage(repairOrderValueDto, repairOrderDto.getWorkOrderId(), realPath);
            repairOrderValueDto.setSubOrderId(repairOrderDto.getId());
            doInsertSubOrderValue(repairOrderValueDto);
            // 根据attrPath查询，如果存在则删除，如果不存在则插入
            addSubOrderValueHis(repairOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
     * 
     * @author jinyanan
     *
     * @param repairOrderValueDto repairOrderValueDto
     * @param priority priority
     */
    private void addSubOrderValueHis(RepairOrderValueDto repairOrderValueDto, Long priority) {
        RepairOrderValueHisDto repairOrderValueHisDto = SubOrderUtil.createHisDto(repairOrderValueDto,
            RepairOrderValueHisDto.class, priority);
        repairOrderValueHisDtoMapper.insert(repairOrderValueHisDto);

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param repairOrderValueDto repairOrderValueDto
     * @param workOrderId workOrderId
     * @param realPath realPath
     */
    private void checkImage(RepairOrderValueDto repairOrderValueDto, Long workOrderId, String realPath) {
        AttrDto attr = attrService.getAttrById(repairOrderValueDto.getAttrId());
        if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType())) {
            repairOrderValueDto
                .setTextInput(SubOrderUtil.uploadImage(repairOrderValueDto.getTextInput(), workOrderId, realPath));
        }

    }

    @Override
    public void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<RepairOrderValueDto> repairOrderValueDtoList, Long currentStaffId, String realPath) {
        RepairOrderDto repairOrderDto = this.getSubOrderByCode(code);
        repairOrderDto.setStaffId(staffId);
        repairOrderDto.clearAssignedDealer();
        repairOrderDto.setAssignedDealerId(staffId);
        repairOrderDto.setActualDealerId(currentStaffId);

        Long priority = this.updateSubOrderWithTrans(repairOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, repairOrderValueDtoList, realPath, priority);

        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void staffTaceWithTrans(String code, List<RepairOrderValueDto> repairOrderValueDtoList, Long currentStaffId,
        String realPath) {
        RepairOrderDto repairOrderDto = this.getSubOrderByCode(code);
        repairOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
        repairOrderDto.setStateDate(DateUtil.getDBDateTime());
        repairOrderDto.setActualDealerId(currentStaffId);
        Long priority = this.updateSubOrderWithTrans(repairOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(repairOrderDto.getWorkOrderId());
        workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, repairOrderValueDtoList, realPath, priority);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str) {
        return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str) {
        RepairOrderDto repairOrderDto = (RepairOrderDto) subOrderDto;
        repairOrderDto.setUpdatePersonId(updatePersonId);
        repairOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        return this.updateSubOrderAndAddHis(repairOrderDto, closeOrder, str);
    }

    // @Override
    // public String getStateName(String state) {
    // return repairOrderStateDtoMapper.selectByPrimaryKey(state).getStateName();
    // }

    @Override
    public void payWithTrans(String code, Long staffId, Long paidMoney) {
        RepairOrderDto repairOrderDto = this.getSubOrderByCode(code);
        if (null == paidMoney) {
            repairOrderDto.setPaidMoney(repairOrderDto.getPayableMoney());
        }
        else {
            repairOrderDto.setPaidMoney(paidMoney + repairOrderDto.getPaidMoney());
        }
        repairOrderDto.setActualDealerId(staffId);
        this.updateSubOrderWithTrans(repairOrderDto, staffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(repairOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public RepairOrderValueDto selectByAttrPath(RepairOrderValueDto repairOrderValueDto) {
        return repairOrderValueDtoMapper.selectByAttrPath(repairOrderValueDto);
    }

    @Override
    public void passInStaffReviewWithTrans(String code, List<RepairOrderValueDto> repairOrderValueList,
        Long currentStaffId, String realPath) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, repairOrderValueList, realPath, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public List<RepairTypeDto> getRepairType() {
        return repairOrderTypeDtoMapper.selectAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getHis(Long subOrderId) {
        return (List<T>) repairOrderHisDtoMapper.selectById(subOrderId);
    }

    @Override
    public String getWorkOrderType() {
        return WorkOrderTypeDef.REPAIR_ORDER;
    }
    
  	@Override
  	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
  	{
  		RepairOrderDto repairOrderDto = new RepairOrderDto();
  		repairOrderDto.setId(subOrderId);
  		repairOrderDto.setAssignedDealerId(subAssignedDealerId);
  		return repairOrderDtoMapper.updateByPrimaryKeySelective(repairOrderDto);
  	}
    
}
