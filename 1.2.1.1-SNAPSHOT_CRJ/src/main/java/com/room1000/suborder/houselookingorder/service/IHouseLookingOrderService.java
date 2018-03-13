package com.room1000.suborder.houselookingorder.service;

import java.util.List;

import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderChannelDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
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
public interface IHouseLookingOrderService extends ISubOrderService {

    /**
     * 
     * Description: houseLookingOrderDto必须为全量的houseLookingOrderDto，否则会被重置为null
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderDto
     * @return
     */
    // HouseLookingOrderDto updateSubOrderWithTrans(HouseLookingOrderDto houseLookingOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderDto
     * @param updatePersonId
     * @param closeOrder
     * @return
     */
    // HouseLookingOrderDto updateSubOrderWithTrans(HouseLookingOrderDto houseLookingOrderDto, Long updatePersonId,
    // boolean closeOrder);

    /**
     * 
     * Description: 录入看房订单信息
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderDto houseLookingOrderDto
     * @param workOrderDto workOrderDto
     * @param currentStaffId currentStaffId
     * @param recommendId recommendId
     */
    void inputSubOrderInfoWithTrans(HouseLookingOrderDto houseLookingOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId, Long recommendId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail
     * @return HouseLookingOrderDto
     */
    // HouseLookingOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param butlerId
     * @param houseLookingOrderValueDtoList
     * @param user
     */
    // @Deprecated
    // void assignOrderWithTrans(String code, Long butlerId, List<HouseLookingOrderValueDto>
    // houseLookingOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param butlerId butlerId
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long butlerId,
        List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId);

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
     * @param butlerId butlerId
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long butlerId,
        List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 客服回访时通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderValueDto houseLookingOrderValueDto
     * @return HouseLookingOrderValueDto
     */
    HouseLookingOrderValueDto selectByAttrPath(HouseLookingOrderValueDto houseLookingOrderValueDto);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderId
     * @return
     */
    // List<HouseLookingOrderHisDto> getHis(Long houseLookingOrderId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @return List<HouseLookingOrderChannelDto>
     */
    List<HouseLookingOrderChannelDto> getHouseLookingOrderChannel();

}
