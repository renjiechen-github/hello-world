package com.room1000.suborder.routinecleaningorder.service;

import java.util.List;

import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderValueDto;
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
public interface IRoutineCleaningOrderService extends ISubOrderService {

    /**
     * 
     * Description: routineCleaningOrderDto必须为全量的routineCleaningOrderDto，否则会被重置为null
     * 
     * @author jinyanan
     *
     * @param routineCleaningOrderDto
     * @return
     */
    // RoutineCleaningOrderDto updateSubOrderWithTrans(RoutineCleaningOrderDto routineCleaningOrderDto, Long
    // updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param routineCleaningOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // RoutineCleaningOrderDto updateSubOrderWithTrans(RoutineCleaningOrderDto routineCleaningOrderDto, Long staffId,
    // boolean closeOrder);

    /**
     * 
     * Description: 录入看房订单信息
     * 
     * @author jinyanan
     *
     * @param routineCleaningOrderDto routineCleaningOrderDto
     * @param workOrderDto workOrderDto
     * @param currentStaffId currentStaffId
     */
    void inputSubOrderInfoWithTrans(RoutineCleaningOrderDto routineCleaningOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail
     * @return RoutineCleaningOrderDto
     */
    // RoutineCleaningOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param routineCleaningOrderValueDtoList
     * @param user
     */
    // void assignOrderWithTrans(String code, Long staffId, List<RoutineCleaningOrderValueDto>
    // routineCleaningOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param routineCleaningOrderValueDtoList routineCleaningOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param routineCleaningOrderValueDtoList routineCleaningOrderValueDtoList
     * @param submitFlag submitFlag
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    void processOrderWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param routineCleaningOrderValueDtoList routineCleaningOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList, Long currentStaffId, String realPath);

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param routineCleaningOrderValueDtoList routineCleaningOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
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
     * @param routineCleaningOrderValueDtoList routineCleaningOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId,
        List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 客服回访时通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param routineCleaningOrderValueDtoList routineCleaningOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<RoutineCleaningOrderValueDto> routineCleaningOrderValueDtoList,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param routineCleaningOrderValueDto routineCleaningOrderValueDto
     * @return RoutineCleaningOrderValueDto
     */
    RoutineCleaningOrderValueDto selectByAttrPath(RoutineCleaningOrderValueDto routineCleaningOrderValueDto);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param routineCleaningOrderId
     * @return
     */
    // List<RoutineCleaningOrderHisDto> getHis(Long routineCleaningOrderId);

}
