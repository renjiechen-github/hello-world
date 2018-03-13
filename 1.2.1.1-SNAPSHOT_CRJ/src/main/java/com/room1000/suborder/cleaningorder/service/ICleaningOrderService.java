package com.room1000.suborder.cleaningorder.service;

import java.util.List;

import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderValueDto;
import com.room1000.suborder.cleaningorder.dto.CleaningTypeDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface ICleaningOrderService extends ISubOrderService, ISubOrderPayService {

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
    // CleaningOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 获取详情
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail singleDetail
     * @param needFormatImageUrl needFormatImageUrl
     * @return CleaningOrderDto
     */
    CleaningOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl);

    /**
     * 
     * Description: 订单录入
     * 
     * @author jinyanan
     *
     * @param cleaningOrderDto cleaningOrderDto
     * @param workOrderDto workOrderDto
     * @param staffId staffId
     * @param realPath realPath
     */
    void inputSubOrderInfoWithTrans(CleaningOrderDto cleaningOrderDto, WorkOrderDto workOrderDto, Long staffId,
        String realPath);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param cleaningOrderValueList
     * @param currentStaffId
     */
    // void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueList, Long
    // currentStaffId);

    /**
     * 
     * Description: 重新指派
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param cleaningOrderValueList cleaningOrderValueList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId, List<CleaningOrderValueDto> cleaningOrderValueList,
        Long currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param cleaningOrderValueList cleaningOrderValueList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueList, Long currentStaffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param cleaningOrderValueList cleaningOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueList, Long currentStaffId,
        String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客户回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param cleaningOrderValueList cleaningOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<CleaningOrderValueDto> cleaningOrderValueList, Long currentStaffId, String realPath);

    /**
     * 
     * Description: 客户回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param cleaningOrderValueList cleaningOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueList, Long currentStaffId,
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
     * @param cleaningOrderDto
     * @return
     */
    // CleaningOrderDto updateSubOrderWithTrans(CleaningOrderDto cleaningOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param cleaningOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // CleaningOrderDto updateSubOrderWithTrans(CleaningOrderDto cleaningOrderDto, Long staffId, boolean closeOrder);

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
     * @param cleaningOrderValueDto cleaningOrderValueDto
     * @return CleaningOrderValueDto
     */
    CleaningOrderValueDto selectByAttrPath(CleaningOrderValueDto cleaningOrderValueDto);

    /**
     * 
     * Description: 客服回访通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param cleaningOrderValueList cleaningOrderValueList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<CleaningOrderValueDto> cleaningOrderValueList,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @return List<CleaningTypeDto>
     */
    List<CleaningTypeDto> getCleaningType();

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param cleaningOrderId
     * @return
     */
    // List<CleaningOrderHisDto> getHis(Long cleaningOrderId);

}
