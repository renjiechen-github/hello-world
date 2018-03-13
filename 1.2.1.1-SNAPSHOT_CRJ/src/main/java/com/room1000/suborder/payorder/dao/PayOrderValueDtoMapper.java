package com.room1000.suborder.payorder.dao;

import java.util.List;

import com.room1000.suborder.payorder.dto.PayOrderValueDto;

import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: pay_order_value Mapper 接口
 *
 * Created on 2017年05月23日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface PayOrderValueDtoMapper extends Mapper<PayOrderValueDto> {

    /**
     * 
     * Description: 根据subOrderId查询PayOrderValueDto
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<PayOrderValueDto>
     */
    List<PayOrderValueDto> selectBySubOrderId(Long subOrderId);

    /**
     * 
     * Description: 根据attrPath查询PayOrderValueDto
     * 
     * @author jinyanan
     *
     * @param payOrderValueDto payOrderValueDto
     * @return PayOrderValueDto
     */
    PayOrderValueDto selectByAttrPath(PayOrderValueDto payOrderValueDto);
}