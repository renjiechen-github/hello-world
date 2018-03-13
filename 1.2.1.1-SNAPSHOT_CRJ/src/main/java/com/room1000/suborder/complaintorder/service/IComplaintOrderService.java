package com.room1000.suborder.complaintorder.service;

import java.util.List;

import com.room1000.suborder.complaintorder.dto.ComplaintOrderDto;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderValueDto;
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
public interface IComplaintOrderService extends ISubOrderService {

    /**
     * 
     * Description: complaintOrderDto必须为全量的complaintOrderDto，否则会被重置为null
     * 
     * @author jinyanan
     *
     * @param complaintOrderDto
     * @return
     */
    // ComplaintOrderDto updateSubOrderWithTrans(ComplaintOrderDto complaintOrderDto, Long updatePersonId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param complaintOrderDto
     * @param staffId
     * @param closeOrder
     * @return
     */
    // ComplaintOrderDto updateSubOrderWithTrans(ComplaintOrderDto complaintOrderDto, Long staffId, boolean closeOrder);
	
	
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
		ComplaintOrderDto getOrderDetailById(Long id, Boolean singleDetail, Boolean needFormatImageUrl);

    /**
     * 
     * Description: 录入订单信息
     * 
     * @author jinyanan
     *
     * @param complaintOrderDto complaintOrderDto
     * @param workOrderDto workOrderDto
     * @param staffId staffId
     * @param realPath 
     */
    void inputSubOrderInfoWithTrans(ComplaintOrderDto complaintOrderDto, WorkOrderDto workOrderDto, Long staffId, String realPath);

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
    // ComplaintOrderDto getOrderDetailById(Long id, Boolean singleDetail);

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param code
     * @param staffId
     * @param complaintOrderValueDtoList
     * @param staffId
     */
    // void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList, Long
    // currentStaffId);

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param staffId staffId
     */
    void takeOrderWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long staffId);

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param code code
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param staffId staffId
     * @param realPath realPath
     * @param submitFlag submitFlag
     */
    void processOrderWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long staffId,
        String realPath, Boolean submitFlag);

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    @Deprecated
    void reassignOrderInStaffReviewWithTrans(String code, Long staffId,
        List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long currentStaffId);

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * @author jinyanan
     *
     * @param code code
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param staffId staffId
     */
    @Deprecated
    void staffTaceWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long staffId);

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
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param currentStaffId currentStaffId
     */
    void reassignOrderWithTrans(String code, Long staffId, List<ComplaintOrderValueDto> complaintOrderValueDtoList,
        Long currentStaffId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param complaintOrderValueDto complaintOrderValueDto
     * @return ComplaintOrderValueDto
     */
    ComplaintOrderValueDto selectByAttrPath(ComplaintOrderValueDto complaintOrderValueDto);

    /**
     * 
     * Description: 客户回访时通过
     * 
     * @author jinyanan
     *
     * @param code code
     * @param complaintOrderValueDtoList complaintOrderValueDtoList
     * @param staffId staffId
     */
    @Deprecated
    void passInStaffReviewWithTrans(String code, List<ComplaintOrderValueDto> complaintOrderValueDtoList, Long staffId);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param complaintOrderId
     * @return
     */
    // List<ComplaintOrderHisDto> getHis(Long complaintOrderId);
}
