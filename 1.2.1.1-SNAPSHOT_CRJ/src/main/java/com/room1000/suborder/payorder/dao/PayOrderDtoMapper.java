package com.room1000.suborder.payorder.dao;

import com.room1000.suborder.payorder.dto.PayOrderDto;

import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: pay_order Mapper 接口
 *
 * Created on 2017年05月23日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface PayOrderDtoMapper extends Mapper<PayOrderDto> {

    /**
     * 
     * Description: 根据id查询详情
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return PayOrderDto
     */
    PayOrderDto selectDetailById(Long subOrderId);

    /**
     * 
     * Description: 根据code查询订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @return PayOrderDto
     */
    PayOrderDto selectByCode(String code);
}