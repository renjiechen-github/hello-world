package com.room1000.suborder.ownercancelleaseorder.dao;

import java.util.List;

import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;

import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: owner_cancel_lease_order Mapper 接口
 *
 * Created on 2017年05月05日
 * 
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface OwnerCancelLeaseOrderDtoMapper extends Mapper<OwnerCancelLeaseOrderDto> {

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return OwnerCancelLeaseOrderDto
     */
    OwnerCancelLeaseOrderDto selectDetailById(Long subOrderId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param code code
     * @return OwnerCancelLeaseOrderDto
     */
    OwnerCancelLeaseOrderDto selectByCode(String code);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param qryCond qryCond
     * @return List<OwnerCancelLeaseOrderDto>
     */
    List<OwnerCancelLeaseOrderDto> selectByCond(OwnerCancelLeaseOrderDto qryCond);
}