package com.room1000.suborder.payorder.service;

import java.util.List;

import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.dto.PayOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IPayOrderService extends ISubOrderService, ISubOrderPayService {

    /**
     * 
     * Description: 新增支付订单
     * 需要填入的信息如下：
     * payOrderDto.type 参考pay_order_type表
     * payOrderDto.payRefId 例如type为A时，payRefId为yc_recommend_info主键
     * payOrderDto.appointmentDate 
     * payOrderDto.comments
     * workOrderDto.userName
     * workOrderDto.userPhone
     * workOrderDto.name 订单名称
     * workOrderDto.payableMoney 需要支付的金额(精确到分)
     * staffId 新增该订单的员工，查yc_account_tab表 -1为系统，-2为客户，常量表为SystemAccountDef
     * 
     * @author jinyanan
     *
     * @param payOrderDto payOrderDto
     * @param workOrderDto workOrderDto
     * @param staffId staffId
     */
    void inputSubOrderInfoWithTrans(PayOrderDto payOrderDto, WorkOrderDto workOrderDto, Long staffId);

    /**
     * 
     * Description: 订单审核
     * 
     * @author jinyanan
     *
     * @param code code
     * @param subOrderValueList subOrderValueList
     * @param currentStaffId currentStaffId
     * @param submitFlag 是否提交
     */
    void orderAuditWithTrans(String code, List<PayOrderValueDto> subOrderValueList, Long currentStaffId, Boolean submitFlag);

    /**
     * 
     * Description: 订单执行，主要由客服录入信息
     * 
     * @author jinyanan
     *
     * @param code code
     * @param subOrderValueList subOrderValueList
     * @param currentStaffId currentStaffId
     */
    void processOrderWithTrans(String code, List<PayOrderValueDto> subOrderValueList, Long currentStaffId);
    
    /**
     * 
     * Description: 查询orderValue
     * 
     * @author jinyanan
     *
     * @param payOrderValueDto payOrderValueDto
     * @return PayOrderValueDto
     */
    PayOrderValueDto selectByAttrPath(PayOrderValueDto payOrderValueDto);
}
