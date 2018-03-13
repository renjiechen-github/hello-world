package com.room1000.suborder.livingproblemorder.service;

import java.util.List;

import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderDto;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderValueDto;
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
public interface ILivingProblemOrderService extends ISubOrderService {

    /**
     * 
     * Description: livingProblemOrderDto必须为全量的livingProblemOrderDto，否则会被重置为null
     * 
     * @author jinyanan
     *
     * @param livingProblemOrderDto
     * @return
     */
    // LivingProblemOrderDto updateSubOrderWithTrans(LivingProblemOrderDto livingProblemOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param livingProblemOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // LivingProblemOrderDto updateSubOrderWithTrans(LivingProblemOrderDto livingProblemOrderDto, Long staffId, boolean
    // closeOrder);

    /**
     * 
     * Description: 录入订单信息
     * 
     * @author jinyanan
     *
     * @param livingProblemOrderDto livingProblemOrderDto
     * @param workOrderDto workOrderDto
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    void inputSubOrderInfoWithTrans(LivingProblemOrderDto livingProblemOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail
     * @return LivingProblemOrderDto
     */
    // LivingProblemOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 获取详情
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail singleDetail
     * @param needFormatImageUrl needFormatImageUrl
     * @return LivingProblemOrderDto
     */
    LivingProblemOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param livingProblemOrderValueDtoList
     * @param user
     */
    // void assignOrderWithTrans(String code, Long staffId, List<LivingProblemOrderValueDto>
    // livingProblemOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param livingProblemOrderValueDtoList livingProblemOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param livingProblemOrderValueDtoList livingProblemOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param livingProblemOrderValueDtoList livingProblemOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList, Long currentStaffId, String realPath);

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param livingProblemOrderValueDtoList livingProblemOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
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
     * @param livingProblemOrderValueDtoList livingProblemOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId,
        List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 客服回访时通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param livingProblemOrderValueDtoList livingProblemOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<LivingProblemOrderValueDto> livingProblemOrderValueDtoList,
        Long currentStaffId, String realPath);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param livingProblemOrderValueDto livingProblemOrderValueDto
     * @return LivingProblemOrderValueDto
     */
    LivingProblemOrderValueDto selectByAttrPath(LivingProblemOrderValueDto livingProblemOrderValueDto);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param livingProblemOrderId
     * @return
     */
    // List<LivingProblemOrderHisDto> getHis(Long livingProblemOrderId);

}
