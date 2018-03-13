package com.room1000.suborder.ownercancelleaseorder.dao;

import java.util.List;

import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;

import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: owner_cancel_lease_order_value Mapper 接口
 *
 * Created on 2017年05月05日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface OwnerCancelLeaseOrderValueDtoMapper extends Mapper<OwnerCancelLeaseOrderValueDto> {

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<OwnerCancelLeaseOrderValueDto>
     */
    List<OwnerCancelLeaseOrderValueDto> selectBySubOrderId(Long subOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param ownerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto
     * @return OwnerCancelLeaseOrderValueDto
     */
    OwnerCancelLeaseOrderValueDto selectByAttrPath(OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto);
}