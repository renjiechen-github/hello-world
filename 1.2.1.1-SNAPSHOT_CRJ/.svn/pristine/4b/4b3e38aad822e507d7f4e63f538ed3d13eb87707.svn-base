package com.room1000.suborder.agentapplyorder.service.impl;

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
import com.room1000.attr.service.IAttrService;
import com.room1000.core.activiti.IProcessStart;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessStartImpl;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.agentapplyorder.dao.AgentApplyOrderDtoMapper;
import com.room1000.suborder.agentapplyorder.dao.AgentApplyOrderHisDtoMapper;
import com.room1000.suborder.agentapplyorder.dao.AgentApplyOrderValueDtoMapper;
import com.room1000.suborder.agentapplyorder.dao.AgentApplyOrderValueHisDtoMapper;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderDto;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderHisDto;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderValueDto;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderValueHisDto;
import com.room1000.suborder.agentapplyorder.service.IAgentApplyOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月22日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service
public class AgentApplyOrderServiceImpl implements IAgentApplyOrderService {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AgentApplyOrderServiceImpl.class);
    
    /**
     * agentApplyOrderDtoMapper
     */
    @Autowired
    private AgentApplyOrderDtoMapper agentApplyOrderDtoMapper;
    
    /**
     * agentApplyOrderHisDtoMapper
     */
    @Autowired
    private AgentApplyOrderHisDtoMapper agentApplyOrderHisDtoMapper;

    /**
     * agentApplyOrderValueDtoMapper
     */
    @Autowired
    private AgentApplyOrderValueDtoMapper agentApplyOrderValueDtoMapper;

    /**
     * agentApplyOrderValueHisDtoMapper
     */
    @Autowired
    private AgentApplyOrderValueHisDtoMapper agentApplyOrderValueHisDtoMapper;
    
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail) {
        return (T) this.getOrderDetailById(subOrderId, singleDetail, true);
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @param singleDetail singleDetail
     * @param needFormatImageUrl 是否需要格式化imageUrl
     * @return AgentApplyOrderDto AgentApplyOrderDto
     */
    private AgentApplyOrderDto getOrderDetailById(Long subOrderId, Boolean singleDetail, Boolean needFormatImageUrl) {
        AgentApplyOrderDto agentApplyOrderDto = agentApplyOrderDtoMapper.selectDetailById(subOrderId);
        if (StringUtils.isNotEmpty(agentApplyOrderDto.getImageUrl()) && needFormatImageUrl) {
            agentApplyOrderDto.setImageUrl(SubOrderUtil.getImagePath(agentApplyOrderDto.getImageUrl()));
        }
        if (!singleDetail) {
            AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(agentApplyOrderDto.getAttrCatgId());
            agentApplyOrderDto.setAttrCatg(attrCatg);
            List<AgentApplyOrderValueDto> agentApplyOrderValueList = this.getSubOrderValueDtoListBySubOrderId(subOrderId);
            agentApplyOrderDto.setSubOrderValueList(agentApplyOrderValueList);
        }

        return agentApplyOrderDto;
    }
    


    /**
     * 
     * Description: 根据子订单id查询所有子订单value
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<CleaningOrderValueDto>
     */
    private List<AgentApplyOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long subOrderId) {
        List<AgentApplyOrderValueDto> agentApplyOrderValueList = agentApplyOrderValueDtoMapper.selectBySubOrderId(subOrderId);
        for (AgentApplyOrderValueDto agentApplyOrderValueDto : agentApplyOrderValueList) {
            if (AttrTypeDef.PICTURE_UPLOAD.equals(agentApplyOrderValueDto.getAttrType())) {
                agentApplyOrderValueDto.setTextInput(SubOrderUtil.getImagePath(agentApplyOrderValueDto.getTextInput()));
            }
        }
        return agentApplyOrderValueList;
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str) {
        return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str) {
        AgentApplyOrderDto agentApplyOrderDto = (AgentApplyOrderDto) subOrderDto;
        agentApplyOrderDto.setUpdatePersonId(updatePersonId);
        agentApplyOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        return this.updateSubOrderAndAddHis(agentApplyOrderDto, closeOrder, str);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param agentApplyOrderDto agentApplyOrderDto
     * @param closeOrder closeOrder
     * @return Long
     */
    private Long updateSubOrderAndAddHis(AgentApplyOrderDto agentApplyOrderDto, boolean closeOrder, String str) {
        IProcessTask task = new ProcessTaskImpl();
        // 子订单放入流程实例中
        task.putInstanceVariable(agentApplyOrderDto.getWorkOrderId(), ActivitiVariableDef.AGENT_APPLY_ORDER,
            agentApplyOrderDto);
        AgentApplyOrderDto oldAgentApplyOrderDto = agentApplyOrderDtoMapper.selectByPrimaryKey(agentApplyOrderDto.getId());
        if (oldAgentApplyOrderDto == null) {
            oldAgentApplyOrderDto = agentApplyOrderDtoMapper.selectByCode(agentApplyOrderDto.getCode());
        }
        oldAgentApplyOrderDto.setUpdatePersonId(agentApplyOrderDto.getUpdatePersonId());
        oldAgentApplyOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        if (closeOrder) {
            oldAgentApplyOrderDto.setState(SubOrderStateDef.CLOSED);
            oldAgentApplyOrderDto.setStateDate(DateUtil.getDBDateTime());
        }
        if (!str.equals(""))
        {
        	agentApplyOrderDto.setImageUrl(null);
        }
        agentApplyOrderDtoMapper.updateByPrimaryKey(agentApplyOrderDto);
        AgentApplyOrderHisDto hisDto = SubOrderUtil.createHisDto(oldAgentApplyOrderDto, AgentApplyOrderHisDto.class,
            getPriority(oldAgentApplyOrderDto.getId()));
        if (BooleanFlagDef.BOOLEAN_TRUE.equals(agentApplyOrderDto.getChangeFlag())) {
            hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
            hisDto.setStateDate(DateUtil.getDBDateTime());
            hisDto.setUpdateDate(DateUtil.getDBDateTime());
        }
        agentApplyOrderHisDtoMapper.insert(hisDto);
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
        Long priority = agentApplyOrderHisDtoMapper.selectMaxPriority(subOrderId);
        if (priority == null) {
            return 1L;
        }
        else {
            return priority + 1;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getHis(Long subOrderId) {
        return (List<T>) agentApplyOrderHisDtoMapper.selectById(subOrderId);
    }

    @Override
    public String getWorkOrderType() {
        return WorkOrderTypeDef.AGENT_APPLY_ORDER;
    }

    @Override
    public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId) {
        logger.info("agentApplyOrder cannot be assigned!");
        throw new BaseAppException("经纪人申请审批订单没有指派订单环节");
    }

    @Override
    public void inputSubOrderInfoWithTrans(AgentApplyOrderDto agentApplyOrderDto, WorkOrderDto workOrderDto,
        String realPath, Long currentStaffId) {
        workOrderDto.setAppointmentDate(agentApplyOrderDto.getAppointmentDate());
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_AUDIT);
        workOrderService.createWorkOrderWithTrans(workOrderDto);
        
        agentApplyOrderDto.setWorkOrderId(workOrderDto.getId());
        agentApplyOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
//        agentApplyOrderDto.setImageUrl(SubOrderUtil.uploadImage(agentApplyOrderDto.getImageUrl(), workOrderDto.getId(), realPath));
        agentApplyOrderDto.setState(SubOrderStateDef.ORDER_AUDIT);
        agentApplyOrderDto.setStateDate(DateUtil.getDBDateTime());
        agentApplyOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        agentApplyOrderDto.setActualDealerId(currentStaffId);
        agentApplyOrderDto.setUpdatePersonId(currentStaffId);
        agentApplyOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("AGENT_APPLY_ORDER_PROCESS");
        agentApplyOrderDto.setAttrCatg(attrCatg);
        agentApplyOrderDto.setAttrCatgId(attrCatg.getId());
        agentApplyOrderDtoMapper.insert(agentApplyOrderDto);
        this.updateSubOrderWithTrans(agentApplyOrderDto, currentStaffId, "");
        
        workOrderDto.setType(WorkOrderTypeDef.AGENT_APPLY_ORDER);
        if (StringUtils.isEmpty(workOrderDto.getName())) {
            String orderName = workOrderService.generateOrderName(workOrderDto);
            workOrderDto.setName(orderName);
        }
        workOrderDto.setRefId(agentApplyOrderDto.getId());
        workOrderDto.setCode(agentApplyOrderDto.getCode());
        workOrderDto.setSubActualDealerId(agentApplyOrderDto.getActualDealerId());
        workOrderDto.setSubComments(agentApplyOrderDto.getComments());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        

        startProcess(agentApplyOrderDto, workOrderDto);
        
    }

    /**
     * 
     * Description: startProcess
     * 
     * @author jinyanan
     *
     * @param agentApplyOrderDto agentApplyOrderDto
     * @param workOrderDto workOrderDto
     */
    private void startProcess(AgentApplyOrderDto agentApplyOrderDto, WorkOrderDto workOrderDto) {
        String processInstanceKey = "AgentApplyOrderProcess";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
        variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
        variables.put(ActivitiVariableDef.AGENT_APPLY_ORDER, agentApplyOrderDto);
        IProcessStart start = new ProcessStartImpl();
        start.flowStart(processInstanceKey, variables);
    }

    @Override
    public void orderAuditWithTrans(String code, List<AgentApplyOrderValueDto> subOrderValueList, Long dealerId,
        Boolean submitFlag) {
        Long priority = this.updateSubOrderActualDealer(code, dealerId);
        this.insertSubOrderValue(code, subOrderValueList, priority);
        if (submitFlag) {
            // 1. TODO: 如果审批通过，调用接口修改经纪人相关表
            // 2. 回单
            SubOrderUtil.dispatcherOrder(code);
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
        AgentApplyOrderDto agentApplyOrderDto = agentApplyOrderDtoMapper.selectByCode(code);

        for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList) {
            AgentApplyOrderValueDto agentApplyOrderValueDto = (AgentApplyOrderValueDto) subOrderValueDto;
            agentApplyOrderValueDto.setSubOrderId(agentApplyOrderDto.getId());
            doInsertSubOrderValue(agentApplyOrderValueDto);
            addSubOrderValueHis(agentApplyOrderValueDto, priority);

        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param agentApplyOrderValueDto agentApplyOrderValueDto
     */
    private void doInsertSubOrderValue(AgentApplyOrderValueDto agentApplyOrderValueDto) {
        AgentApplyOrderValueDto existAgentApplyOrderValueDto = agentApplyOrderValueDtoMapper
            .selectByAttrPath(agentApplyOrderValueDto);
        if (existAgentApplyOrderValueDto != null) {
            agentApplyOrderValueDtoMapper.deleteByPrimaryKey(existAgentApplyOrderValueDto.getId());
        }
        agentApplyOrderValueDtoMapper.insert(agentApplyOrderValueDto);
    }

    /**
     * 
     * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
     * 
     * @author jinyanan
     *
     * @param agentApplyOrderValueDto agentApplyOrderValueDto
     * @param priority priority
     */
    private void addSubOrderValueHis(AgentApplyOrderValueDto agentApplyOrderValueDto, Long priority) {
        AgentApplyOrderValueHisDto agentApplyOrderValueHisDto = SubOrderUtil.createHisDto(agentApplyOrderValueDto,
            AgentApplyOrderValueHisDto.class, priority);
        agentApplyOrderValueHisDtoMapper.insert(agentApplyOrderValueHisDto);
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
        AgentApplyOrderDto record = this.getSubOrderByCode(code);
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
    private AgentApplyOrderDto getSubOrderByCode(String code) {
        return agentApplyOrderDtoMapper.selectByCode(code);
    }

  	@Override
  	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
  	{
  		AgentApplyOrderDto agentApplyOrderDto = new AgentApplyOrderDto();
  		agentApplyOrderDto.setId(subOrderId);
  		agentApplyOrderDto.setAssignedDealerId(subAssignedDealerId);
  		return agentApplyOrderDtoMapper.updateByPrimaryKeySelective(agentApplyOrderDto);
  	}
    
}
