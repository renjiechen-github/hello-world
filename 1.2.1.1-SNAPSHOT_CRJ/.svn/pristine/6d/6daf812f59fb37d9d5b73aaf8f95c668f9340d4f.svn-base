package com.room1000.suborder.agentapplyorder.dao;

import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderDto;

import tk.mybatis.mapper.common.Mapper;


/**
 *
 * Description: agent_apply_order Mapper 接口
 *
 * Created on 2017年06月22日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface AgentApplyOrderDtoMapper extends Mapper<AgentApplyOrderDto> {

    /**
     * 
     * Description: 查询订单详情
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return AgentApplyOrderDto
     */
    AgentApplyOrderDto selectDetailById(Long subOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return AgentApplyOrderDto
     */
    AgentApplyOrderDto selectByCode(String code);
}