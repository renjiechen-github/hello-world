package com.room1000.suborder.agentapplyorder.dao;

import java.util.List;

import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderValueDto;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: agent_apply_order_value Mapper 接口
 *
 * Created on 2017年06月26日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface AgentApplyOrderValueDtoMapper extends Mapper<AgentApplyOrderValueDto> {

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param agentApplyOrderValueDto agentApplyOrderValueDto
     * @return AgentApplyOrderValueDto
     */
    AgentApplyOrderValueDto selectByAttrPath(AgentApplyOrderValueDto agentApplyOrderValueDto);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<AgentApplyOrderValueDto>
     */
    List<AgentApplyOrderValueDto> selectBySubOrderId(Long subOrderId);
}