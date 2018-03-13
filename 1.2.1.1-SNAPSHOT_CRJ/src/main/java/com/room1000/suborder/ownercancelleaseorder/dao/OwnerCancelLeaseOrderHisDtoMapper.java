package com.room1000.suborder.ownercancelleaseorder.dao;

import java.util.List;

import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderHisDto;

import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: owner_cancel_lease_order_his Mapper 接口
 *
 * Created on 2017年05月05日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface OwnerCancelLeaseOrderHisDtoMapper extends Mapper<OwnerCancelLeaseOrderHisDto> {

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return Long
     */ 
    Long selectMaxPriority(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<OwnerCancelLeaseOrderHisDto>
     */
    List<OwnerCancelLeaseOrderHisDto> selectById(Long subOrderId);
}