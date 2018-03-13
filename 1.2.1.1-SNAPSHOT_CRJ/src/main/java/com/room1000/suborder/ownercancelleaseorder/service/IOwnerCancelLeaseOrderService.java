package com.room1000.suborder.ownercancelleaseorder.service;

import java.util.List;
import java.util.Map;

import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderTypeDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;

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
public interface IOwnerCancelLeaseOrderService extends ISubOrderService, ISubOrderPayService {

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     */
    void assignSubOrderWithTrans(String code);

    /**
     * 
     * Description: 更新订单状态
     * 
     * @author jinyanan
     *
     * @param code code
     * @param state state
     */
    void updateSubOrderStateWithTrans(String code, String state);

    /**
     * 
     * Description: 查询OrderValueDto
     * 
     * @author jinyanan
     *
     * @param ownerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto
     * @return OwnerCancelLeaseOrderValueDto
     */
    OwnerCancelLeaseOrderValueDto selectByAttrPath(OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto);

    /**
     * 
     * Description: 根据code查询订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @return OwnerCancelLeaseOrderDto
     */
    OwnerCancelLeaseOrderDto getSubOrderByCode(String code);

    /**
     * 
     * Description: 录入订单
     * 
     * @author jinyanan
     *
     * @param ownerCancelLeaseOrderDto ownerCancelLeaseOrderDto
     * @param workOrderDto workOrderDto
     * @param staffId staffId
     */
    void inputSubOrderInfoWithTrans(OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto, WorkOrderDto workOrderDto,
        Long staffId);

    /**
     * 
     * Description: 房源释放
     * 
     * @author jinyanan
     *
     * @param code code
     * @param valueList valueList
     * @param staffId staffId
     */
    @Deprecated
    void releaseHouse(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId);

    /**
     * 
     * Description: 重新指派
     * 
     * @author jinyanan
     *
     * @param code code
     * @param butlerId butlerId
     * @param valueList valueList
     * @param staffId staffId
     */
    void reAssignSubOrderWithTrans(String code, Long butlerId, List<OwnerCancelLeaseOrderValueDto> valueList,
        Long staffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param valueList valueList
     * @param staffId staffId
     */
    void takeOrderWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId);

    /**
     * 
     * Description: 管家上门
     * 
     * @author jinyanan
     *
     * @param code code
     * @param valueList valueList
     * @param staffId staffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void butlerGetHomeWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId,
        String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 租务核算
     * 
     * @author jinyanan
     *
     * @param code code
     * @param valueList valueList
     * @param staffId staffId
     */
    void rentalAuditWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId);

    /**
     * 
     * Description: 高管审批
     * 
     * @author jinyanan
     *
     * @param code code
     * @param valueList valueList
     * @param staffId staffId
     */
    void managerAuditWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId);

    /**
     * 
     * Description: 财务审批
     * 
     * @author jinyanan
     *
     * @param code code
     * @param valueList valueList
     * @param staffId staffId
     */
    void financeAuditWithTrans(String code, List<OwnerCancelLeaseOrderValueDto> valueList, Long staffId);

    /**
     * 
     * Description: 支付
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param paidMoney
     */
    // void payWithTrans(String code, Long staffId, Long paidMoney);

    /**
     * 
     * Description: 在途单校验
     * 
     * @author jinyanan
     *
     * @param takeHouseOrderId takeHouseOrderId
     * @return boolean
     */
    boolean checkOwnerCancelLeaseOrderWithTrans(Long takeHouseOrderId);

    /**
     * 
     * Description: 查询业主退租订单类型
     * 
     * @author jinyanan
     *
     * @return List<OwnerCancelLeaseOrderTypeDto>
     */
    List<OwnerCancelLeaseOrderTypeDto> getOwnerCancelLeaseOrderType();

    /**
     * 
     * Description: 租金计算
     * 
     * @param ownerCancelLeaseOrderId ownerCancelLeaseOrderId
     * @return Map<String, String>
     */
    Map<String, String> rentalMoneyCalculate(Long ownerCancelLeaseOrderId);
}
