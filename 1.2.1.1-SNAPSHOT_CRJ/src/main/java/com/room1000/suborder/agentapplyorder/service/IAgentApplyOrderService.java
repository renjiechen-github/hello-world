package com.room1000.suborder.agentapplyorder.service;

import java.util.List;

import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderDto;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderService;

/**
 * Description: Created on 2017年6月22日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IAgentApplyOrderService extends ISubOrderService {

    /**
     * Description: 录入订单信息
     * 
     * @author jinyanan
     * @param agentApplyOrderDto agentApplyOrderDto
     * @param workOrderDto workOrderDto
     * @param realPath 项目路径
     * @param currentStaffId currentStaffId
     */
    void inputSubOrderInfoWithTrans(AgentApplyOrderDto agentApplyOrderDto, WorkOrderDto workOrderDto, String realPath,
        Long currentStaffId);

    /**
     * Description: 订单审核
     * 
     * @author jinyanan
     * @param code code
     * @param subOrderValueList subOrderValueList
     * @param dealerId dealerId
     * @param submitFlag submitFlag
     */
    void orderAuditWithTrans(String code, List<AgentApplyOrderValueDto> subOrderValueList, Long dealerId,
        Boolean submitFlag);

}
