package com.room1000.suborder.cancelleaseorder.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.room1000.agreement.dto.AgreementDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;

/**
 * 
 * Description:
 * 
 * Created on 2017年1月23日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface ICancelLeaseOrderService extends ISubOrderService, ISubOrderPayService
{

	/**
	 * 
	 * Description: 根据Code查询退租单详情
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @return CancelLeaseOrderDto
	 */
	CancelLeaseOrderDto getSubOrderByCode(String code);

	/**
	 * 
	 * Description: 根据code更新退租单状态
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param state
	 *          要更新的状态
	 */
	void updateSubOrderStateWithTrans(String code, String state);

	/**
	 * 
	 * Description: 根据code更新退租单实际处理人
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param actualDealerId
	 *          要更新的实际处理人
	 */
	// void updateCancelLeaseOrderActualDealer(String code, Long acutalDealerId);

	/**
	 * 
	 * Description: 新发起退租单，并录入信息
	 * 
	 * @author jinyanan
	 * @param cancelLeaseOrderDto
	 *          cancelLeaseOrderDto
	 * @param workOrderDto
	 * @param staffId
	 *
	 */
	void inputSubOrderInfoWithTrans(CancelLeaseOrderDto cancelLeaseOrderDto, WorkOrderDto workOrderDto, Long staffId, String str);

	/**
	 * 
	 * Description: 对退租单进行派单
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 */
	void assignSubOrderWithTrans(String code);

	/**
	 * 
	 * Description: 对退租单进行重新派单
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param butlerId
	 *          重新指派为其他管家
	 * @param valueList
	 * @param staffId
	 */
	void reAssignSubOrderWithTrans(String code, Long butlerId, List<CancelLeaseOrderValueDto> valueList, Long staffId);

	/**
	 * 
	 * Description: 管家接单
	 * 
	 * @author jinyanan
	 * @param code
	 *          code
	 * @param cancelLeaseOrderValueList
	 * @param staffId
	 *
	 */
	void takeOrderWithTrans(String code, List<CancelLeaseOrderValueDto> cancelLeaseOrderValueList, Long staffId);

	/**
	 * 
	 * Description: 管家上门，录入信息
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param valueList
	 *          valueList
	 * @param staffId
	 *          staffId
	 * @param realPath
	 *          realPath
	 * @param submitFlag
	 *          submitFlag
	 */
	void butlerGetHomeWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId, String realPath, Boolean submitFlag);

	/**
	 * 
	 * Description: 租务核算，录入租务信息
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param valueList
	 *          根据属性目录，录入各项租务信息(包括该环节是否审核通过)
	 * @param staffId
	 */
	void rentalAccountWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId);

	/**
	 * 
	 * Description: 市场部高管审批
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param valueList
	 *          根据属性目录，录入审批信息(包括该环节是否审核通过)
	 * @param staffId
	 *          staffId
	 */
	void marketingExecutiveAuditWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId);

	/**
	 * 
	 * Description: 财务审批
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param valueList
	 *          根据属性目录，录入审批信息(包括该环节是否审核通过)
	 * @param staffId
	 */
	void financeAuditWithTrans(String code, List<CancelLeaseOrderValueDto> valueList, Long staffId);

	/**
	 * 
	 * Description: 校验当前出租订单是否已经存在退租单
	 * 
	 * @author jinyanan
	 *
	 * @param rentalLeaseOrderId
	 *          rentalLeaseOrderId
	 * @return boolean
	 */
	boolean checkCancelLeaseOrderWithTrans(Long rentalLeaseOrderId);

	/**
	 * 
	 * Description: 获取CancelLeaseOrder详情
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 *          cancelLeaseOrderId
	 * @param singleDetail
	 * @return CancelLeaseOrderDto
	 */
	// CancelLeaseOrderDto getOrderDetailById(Long cancelLeaseOrderId, Boolean
	// singleDetail);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 *          cancelLeaseOrderId
	 * @return List<CancelLeaseOrderValueDto>
	 */
	// List<CancelLeaseOrderValueDto>
	// getCancelLeaseOrderValueDtoListByCancelLeaseOrderId(Long cancelLeaseOrderId);

	/**
	 * 
	 * Description: 传入的cancelLeaseOrderDto必须为全量的cancelLeaseOrderDto,否则会被重置为null
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderDto
	 *          cancelLeaseOrderDto
	 * @return CancelLeaseOrderDto
	 */
	// CancelLeaseOrderDto updateSubOrderWithTrans(CancelLeaseOrderDto
	// cancelLeaseOrderDto, Long updatePersonId);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderDto
	 * @param staffId
	 * @param b
	 * @return
	 */
	// CancelLeaseOrderDto updateSubOrderWithTrans(CancelLeaseOrderDto
	// cancelLeaseOrderDto, Long staffId, boolean closeOrder);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderValueDto
	 *          cancelLeaseOrderValueDto
	 * @return CancelLeaseOrderValueDto
	 */
	CancelLeaseOrderValueDto selectByAttrPath(CancelLeaseOrderValueDto cancelLeaseOrderValueDto);

	/**
	 * 
	 * Description: 租务审核打回
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderCode
	 *          cancelLeaseOrderCode
	 * @param valueList
	 *          valueList
	 * @param staffId
	 */
	void rentalAccountReturnWithTrans(String cancelLeaseOrderCode, List<CancelLeaseOrderValueDto> valueList, Long staffId);

	/**
	 * 费用清算提交
	 * 
	 * @param code
	 * @param subOrderValueList
	 * @param dealerId
	 */
	void rentalCostLiquidationBtnWithTrans(String code, List<CancelLeaseOrderValueDto> subOrderValueList, Long dealerId);

	/**
	 * 
	 * Description: 费用清算打回
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderCode
	 *          cancelLeaseOrderCode
	 * @param valueList
	 *          valueList
	 * @param staffId
	 */
	void rentalCostLiquidationWithTrans(String code, List<CancelLeaseOrderValueDto> subOrderValueList, Long valueOf);

	/**
	 * 
	 * Description: 支付
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderCode
	 * @param staffId
	 * @param paidMoney
	 */
	// void payWithTrans(String cancelLeaseOrderCode, Long staffId, Long paidMoney);

	/**
	 * 
	 * Description: 查询订单状态名
	 * 
	 * @author jinyanan
	 *
	 * @param state
	 *          state
	 * @return String
	 */
	// String getStateName(String state);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 * @return
	 */
	// List<CancelLeaseOrderHisDto> getHis(Long cancelLeaseOrderId);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 *          cancelLeaseOrderId
	 * @return Map<String, String>
	 */
	Map<String, String> rentalMoneyCalculate(Long cancelLeaseOrderId);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderDto
	 *          cancelLeaseOrderDto
	 */
	void resetAgreementState(CancelLeaseOrderDto cancelLeaseOrderDto);

	/**
	 * 管家上门订单关闭
	 * 
	 * @param cancelLeaseOrderDto
	 */
	int updateAgreementState(CancelLeaseOrderDto cancelLeaseOrderDto);

	/**
	 * 
	 * Description: 释放房源
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @param cancelLeaseOrderValueList
	 *          cancelLeaseOrderValueList
	 * @param staffId
	 *          staffId
	 * @param butlerGetHouseDate
	 */
	void releaseHouseRank(String code, List<CancelLeaseOrderValueDto> cancelLeaseOrderValueList, Long staffId, Date butlerGetHouseDate);

	/**
	 * 根据出租合约主键id，获取水电燃初始值
	 * 
	 * @param id
	 * @return
	 */
	AgreementDto getInitialValue(Long id);

	void payNewWithTrans(String code, Long l, Long a);

	void resetAgreementNewState(CancelLeaseOrderDto subOrderDto);

}
