package com.room1000.suborder.payorder.dao;

import java.util.List;

import com.room1000.suborder.payorder.dto.PayOrderHisDto;

import tk.mybatis.mapper.common.Mapper;

/**
 *
 * Description: pay_order_his Mapper 接口
 *
 * Created on 2017年05月23日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface PayOrderHisDtoMapper extends Mapper<PayOrderHisDto> {

    /**
     * 
     * Description: 查询最大优先级
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return Long
     */
    Long selectMaxPriority(Long subOrderId);

    /**
     * 
     * Description: 根据subOrderId查询历史
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<PayOrderHisDto>
     */
    List<PayOrderHisDto> selectById(Long subOrderId);
}