package com.room1000.workorder.service;

import java.util.List;

import com.room1000.workorder.dto.SubOrderValueDto;

/**
 * 
 * Description:
 * 
 * Created on 2017年5月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface ISubOrderService {

    /**
     * 
     * Description: 查询订单详情
     * 
     * @author jinyanan
     *
     * @param <T> <T>
     * @param subOrderId subOrderId
     * @param singleDetail singleDetail
     * @return T
     */
    <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail);

    /**
     * 
     * Description: 修改订单
     * 
     * @author jinyanan
     * 
     * @param <T> <T>
     * @param subOrderDto subOrderDto
     * @param updatePersonId updatePersonId
     * @return Long
     */
    <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str);

    /**
     * 
     * Description: 修改订单
     * 
     * @author jinyanan
     * 
     * @param <T> <T>
     * @param subOrderDto subOrderDto
     * @param updatePersonId updatePersonId
     * @param closeOrder closeOrder
     * @return Long
     */
    <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str);

    /**
     * 
     * Description: 查询历史
     * 
     * @author jinyanan
     * 
     * @param <T> <T>
     * @param subOrderId subOrderId
     * @return List<T>
     */
    <T> List<T> getHis(Long subOrderId);

    /**
     * 
     * Description: 获取当前子订单类型，必须实现
     * 
     * @author jinyanan
     *
     * @return 订单类型
     */
    String getWorkOrderType();

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param subOrderValueDtoList subOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId);
    
    /**
     * 根据工单id，更改子工单的处理人id
     * @param subOrderId
     * @param subAssignedDealerId
     * @return
     */
		int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId);
}
