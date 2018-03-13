package com.room1000.suborder.ownerrepairorder.service;

import java.util.List;

import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
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
public interface IOwnerRepairOrderService extends ISubOrderService {

    /**
     * 
     * Description: ownerRepairOrderDto必须为全量的ownerRepairOrderDto，否则会被重置为null
     * 
     * @author jinyanan
     *
     * @param ownerRepairOrderDto
     * @return
     */
    // OwnerRepairOrderDto updateSubOrderWithTrans(OwnerRepairOrderDto ownerRepairOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param ownerRepairOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // OwnerRepairOrderDto updateSubOrderWithTrans(OwnerRepairOrderDto ownerRepairOrderDto, Long staffId, boolean
    // closeOrder);

    /**
     * 
     * Description: 录入看房订单信息
     * 
     * @author jinyanan
     *
     * @param ownerRepairOrderDto ownerRepairOrderDto
     * @param workOrderDto workOrderDto
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    void inputSubOrderInfoWithTrans(OwnerRepairOrderDto ownerRepairOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail
     * @return OwnerRepairOrderDto
     */
    // OwnerRepairOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail singleDetail
     * @param needFormatImageUrl needFormatImageUrl
     * @return OwnerRepairOrderDto
     */
    OwnerRepairOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param ownerRepairOrderValueDtoList
     * @param user
     */
    // void assignOrderWithTrans(String code, Long staffId, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
    // Long currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param ownerRepairOrderValueDtoList ownerRepairOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param ownerRepairOrderValueDtoList ownerRepairOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param ownerRepairOrderValueDtoList ownerRepairOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList, Long currentStaffId, String realPath);

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param ownerRepairOrderValueDtoList ownerRepairOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param state state
     * @return String
     */
    // String getStateName(String state);

    /**
     * 
     * Description: 管家重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param ownerRepairOrderValueDtoList ownerRepairOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 客服回访时通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param ownerRepairOrderValueDtoList ownerRepairOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<OwnerRepairOrderValueDto> ownerRepairOrderValueDtoList,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param ownerRepairOrderValueDto ownerRepairOrderValueDto
     * @return OwnerRepairOrderValueDto
     */
    OwnerRepairOrderValueDto selectByAttrPath(OwnerRepairOrderValueDto ownerRepairOrderValueDto);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param ownerRepairOrderId
     * @return
     */
    // List<OwnerRepairOrderHisDto> getHis(Long ownerRepairOrderId);

}
