package com.room1000.suborder.agentapplyorder.dao;

import java.util.List;

import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderHisDto;

import tk.mybatis.mapper.common.Mapper;


/**
 *
 * Description: agent_apply_order_his Mapper 接口
 *
 * Created on 2017年06月22日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface AgentApplyOrderHisDtoMapper extends Mapper<AgentApplyOrderHisDto> {

    /**
     * 
     * Description: selectMaxPriority
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return Long
     */
    Long selectMaxPriority(Long subOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<AgentApplyOrderHisDto>
     */
    List<AgentApplyOrderHisDto> selectById(Long subOrderId);
}