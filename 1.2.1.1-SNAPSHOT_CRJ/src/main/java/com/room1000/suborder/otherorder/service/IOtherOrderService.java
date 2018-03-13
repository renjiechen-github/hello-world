package com.room1000.suborder.otherorder.service;

import java.util.List;

import com.room1000.suborder.otherorder.dto.OtherOrderDto;
import com.room1000.suborder.otherorder.dto.OtherOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderService;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月6日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IOtherOrderService extends ISubOrderService {

    /**
     * 
     * Description: otherOrderDto必须为全量的otherOrderDto，否则会被重置为null
     * 
     * @author jinyanan
     *
     * @param otherOrderDto
     * @return
     */
    // OtherOrderDto updateSubOrderWithTrans(OtherOrderDto otherOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param otherOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // OtherOrderDto updateSubOrderWithTrans(OtherOrderDto otherOrderDto, Long staffId, boolean closeOrder);
	
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
		OtherOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl);

    /**
     * 
     * Description: 录入看房订单信息
     * 
     * @author jinyanan
     *
     * @param otherOrderDto otherOrderDto
     * @param workOrderDto workOrderDto
     * @param currentStaffId currentStaffId
     */
    void inputSubOrderInfoWithTrans(OtherOrderDto otherOrderDto, WorkOrderDto workOrderDto, Long currentStaffId, String realPath);
    
    

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @param singleDetail
     * @return OtherOrderDto
     */
    // OtherOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param butlerId
     * @param otherOrderValueDtoList
     * @param user
     */
    // void assignOrderWithTrans(String code, Long butlerId, List<OtherOrderValueDto> otherOrderValueDtoList, Long
    // currentStaffId);
    
    

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param otherOrderValueDtoList otherOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void takeOrderWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param otherOrderValueDtoList otherOrderValueDtoList
     * @param currentStaffId currentStaffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId,
        String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param otherOrderValueDtoList otherOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId, List<OtherOrderValueDto> otherOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param otherOrderValueDtoList otherOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId);

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
     * @param otherOrderValueDtoList otherOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId, List<OtherOrderValueDto> otherOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description: 客服回访时通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param otherOrderValueDtoList otherOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<OtherOrderValueDto> otherOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param otherOrderValueDto otherOrderValueDto
     * @return OtherOrderValueDto
     */
    OtherOrderValueDto selectByAttrPath(OtherOrderValueDto otherOrderValueDto);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param otherOrderId
     * @return
     */
    // List<OtherOrderHisDto> getHis(Long otherOrderId);

}
