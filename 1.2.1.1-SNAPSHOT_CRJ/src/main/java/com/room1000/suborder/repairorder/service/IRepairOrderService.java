package com.room1000.suborder.repairorder.service;

import java.util.List;

import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.dto.RepairOrderValueDto;
import com.room1000.suborder.repairorder.dto.RepairTypeDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月7日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IRepairOrderService extends ISubOrderService, ISubOrderPayService {

    /**
     * 
     * Description: 获取详情
     * 
     * @author jinyanan
     *
     * @param id
     * @param singleDetail
     * @return
     */
    // RepairOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 获取详情
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail singleDetail
     * @param needFormatImageUrl needFormatImageUrl
     * @return RepairOrderDto
     */
    RepairOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl);

    /**
     * 
     * Description: 订单录入
     * 
     * @author jinyanan
     *
     * @param repairOrderDto repairOrderDto
     * @param workOrderDto workOrderDto
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    void inputSubOrderInfoWithTrans(RepairOrderDto repairOrderDto, WorkOrderDto workOrderDto, Long currentStaffId,
        String realPath);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param repairOrderValueList
     * @param user
     */
    // void assignOrderWithTrans(String code, Long staffId, List<RepairOrderValueDto> repairOrderValueList, Long
    // currentStaffId);

    /**
     * 
     * Description: 重新指派
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param repairOrderValueList repairOrderValueList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId, List<RepairOrderValueDto> repairOrderValueList,
        Long currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param repairOrderValueList repairOrderValueList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<RepairOrderValueDto> repairOrderValueList, Long currentStaffId);

    /**
     * 
     * Description:订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param payableMoney payableMoney 
     * @param repairOrderValueList repairOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, Long payableMoney, List<RepairOrderValueDto> repairOrderValueList,
        Long currentStaffId, String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客户回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param repairOrderValueList repairOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId, List<RepairOrderValueDto> repairOrderValueList,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description: 客户回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param repairOrderValueList repairOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<RepairOrderValueDto> repairOrderValueList, Long currentStaffId,
        String realPath);

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
     * Description: 更新子订单，必须是全量订单内容，否则会被置为null
     * 
     * @author jinyanan
     *
     * @param repairOrderDto
     * @return
     */
    // RepairOrderDto updateSubOrderWithTrans(RepairOrderDto repairOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param repairOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // RepairOrderDto updateSubOrderWithTrans(RepairOrderDto repairOrderDto, Long staffId, boolean closeOrder);

    /**
     * 
     * Description: 根据state查询stateName
     * 
     * @author jinyanan
     *
     * @param state
     * @return
     */
    // String getStateName(String state);

    /**
     * 
     * Description: 根据attrPath查询属性
     * 
     * @author jinyanan
     *
     * @param repairOrderValueDto repairOrderValueDto
     * @return RepairOrderValueDto
     */
    RepairOrderValueDto selectByAttrPath(RepairOrderValueDto repairOrderValueDto);

    /**
     * 
     * Description: 客服回访通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param repairOrderValueList repairOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<RepairOrderValueDto> repairOrderValueList, Long currentStaffId,
        String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @return List<RepairOrderDto>
     */
    List<RepairTypeDto> getRepairType();

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param repairOrderId
     * @return
     */
    // List<RepairOrderHisDto> getHis(Long repairOrderId);

}
