package com.room1000.suborder.cancelleaseorder.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.room1000.agreement.dto.AgreementDto;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.model.ResponseModel;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.request.CancelLeaseOrderOperationRequest;
import com.room1000.suborder.cancelleaseorder.dto.request.CheckCancelLeaseOrderRequest;
import com.room1000.suborder.cancelleaseorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.cancelleaseorder.dto.request.RentalMoneyCalculateRequest;
import com.room1000.suborder.cancelleaseorder.dto.response.CheckCancelLeaseOrderResponse;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;

import pccom.web.beans.User;

/**
 * 
 * Description:
 * 
 * Created on 2017年2月6日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Controller
@RequestMapping("/cancelLease")
public class CancelLeaseOrderController
{

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(CancelLeaseOrderController.class);

	/**
	 * cancelLeaseService
	 */
	@Autowired
	private ICancelLeaseOrderService cancelLeaseOrderService;

	/**
	 * request
	 */
	@Autowired
	private HttpServletRequest request;

	/**
	 * 
	 * Description: 点击新增退租订单时，生成WorkOrder，并录入退租单信息
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/inputCancelLeaseOrderInfo.do")
	@ResponseBody
	public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		ResponseModel resp = new ResponseModel();

		if (cancelLeaseOrderService.checkCancelLeaseOrderWithTrans(req.getRentalLeaseOrderId()))
		{
			// 当前出租单已存在在途退租单
			resp.setState(ResponseModel.BUSINESS_ERROR);
			resp.setErrorMsg("当前房源已生成订单，请勿重复操作");
			return resp;
		}

		CancelLeaseOrderDto cancelLeaseOrderDto = new CancelLeaseOrderDto();
		cancelLeaseOrderDto.setRentalLeaseOrderId(req.getRentalLeaseOrderId());
		cancelLeaseOrderDto.setCancelLeaseDate(req.getCancelLeaseDate());
		cancelLeaseOrderDto.setChannel(req.getChannel());
		cancelLeaseOrderDto.setComments(req.getComments());
		cancelLeaseOrderDto.setType(req.getType());

		WorkOrderDto workOrderDto = new WorkOrderDto();
		workOrderDto.setUserName(req.getUserName());
		workOrderDto.setUserPhone(req.getUserMobile());
		workOrderDto.setName(req.getOrderName());

		User currentStaff = SubOrderUtil.getUserByUserName(userName);
		// 如果为空，说明是C端做的操作
		if (StringUtils.isEmpty(userName))
		{
			if (null != req.getCreatedUserId())
			{
				workOrderDto.setCreatedUserId(req.getCreatedUserId());
				cancelLeaseOrderService.inputSubOrderInfoWithTrans(cancelLeaseOrderDto, workOrderDto, SystemAccountDef.CUSTOMER, "");
			} else if (null != req.getCreatedStaffId())
			{
				workOrderDto.setCreatedStaffId(req.getCreatedStaffId());
				cancelLeaseOrderService.inputSubOrderInfoWithTrans(cancelLeaseOrderDto, workOrderDto, req.getCreatedStaffId(), "");
			}
		} else
		{
			workOrderDto.setCreatedStaffId(Long.valueOf(currentStaff.getId()));
			cancelLeaseOrderService.inputSubOrderInfoWithTrans(cancelLeaseOrderDto, workOrderDto, Long.valueOf(currentStaff.getId()), "");
		}

		return resp;
	}

	/**
	 * 
	 * Description: 管家接单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/takeOrder.do")
	@ResponseBody
	public ResponseModel takeOrder(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 管家重新指派订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/reassignOrder.do")
	@ResponseBody
	public ResponseModel reAssignOrder(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.reAssignSubOrderWithTrans(req.getCode(), req.getNewButlerId(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.reAssignSubOrderWithTrans(req.getCode(), req.getNewButlerId(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 获取水电燃初始值
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getInitialValue.do")
	@ResponseBody
	public AgreementDto getInitialValue(@RequestBody InputSubOrderInfoRequest req)
	{
		AgreementDto dto = cancelLeaseOrderService.getInitialValue(req.getRentalLeaseOrderId());
		return dto;
	}

	/**
	 * 
	 * Description: 释放房源
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "releaseHouseRank.do")
	@ResponseBody
	public ResponseModel releaseHouseRank(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.releaseHouseRank(req.getCode(), req.getSubOrderValueList(), req.getDealerId(), req.getButlerGetHouseDate());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.releaseHouseRank(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()), req.getButlerGetHouseDate());
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;

	}

	/**
	 * 
	 * Description: 费用清算提交
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/rentalCostLiquidation.do")
	@ResponseBody
	public ResponseModel rentalCostLiquidation(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.rentalCostLiquidationBtnWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.rentalCostLiquidationBtnWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}
	/**
	 * 
	 * Description: 管家上门记录信息
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/butlerGetHome.do")
	@ResponseBody
	public ResponseModel butlerGetHome(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{

		String realPath = this.request.getSession().getServletContext().getRealPath("/");
		Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.butlerGetHomeWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(), realPath, submitFlag);
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.butlerGetHomeWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()), realPath, submitFlag);
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 费用清算时打回
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/rentalCostLiquidationReturn.do")
	@ResponseBody
	public ResponseModel rentalCostLiquidationReturn(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.rentalCostLiquidationWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.rentalCostLiquidationWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}
		
		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}
	/**
	 * 
	 * Description: 租务审核时打回
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/rentalAccountReturn.do")
	@ResponseBody
	public ResponseModel rentalAccountReturn(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.rentalAccountReturnWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.rentalAccountReturnWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 重定向
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return String
	 */
	@RequestMapping(value = "path2CreateCostPage.do")
	public String path2CreateCostPage(@RequestParam(value = "id", required = false) Long id)
	{
		request.setAttribute("data", id);
		return "flow/pages/order/leaseorder/createCost";
	}

	/**
	 * 
	 * Description: 租务核算，并录入信息
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/rentalAccount.do")
	@ResponseBody
	public ResponseModel rentalAccount(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.rentalAccountWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.rentalAccountWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 市场高管审批
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/marketingExecutiveAudit.do")
	@ResponseBody
	public ResponseModel marketingExecutiveAudit(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.marketingExecutiveAuditWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.marketingExecutiveAuditWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 财务审批
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @param userName
	 *          userName
	 * @return ResponseModel
	 */
	@RequestMapping(value = "/financeAudit.do")
	@ResponseBody
	public ResponseModel financeAudit(@RequestBody CancelLeaseOrderOperationRequest req, @CookieValue(value = "username", required = false) String userName)
	{
		if (StringUtils.isEmpty(userName))
		{
			cancelLeaseOrderService.financeAuditWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
		} else
		{
			User currentStaff = SubOrderUtil.getUserByUserName(userName);
			cancelLeaseOrderService.financeAuditWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
		}

		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.NORMAL);
		return resp;
	}

	/**
	 * 
	 * Description: 租金计算
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return Map<String, String>
	 */
	@RequestMapping(value = "/rentalMoneyCalculate.do")
	@ResponseBody
	public Map<String, String> rentalMoneyCalculate(@RequestBody RentalMoneyCalculateRequest req)
	{
		return cancelLeaseOrderService.rentalMoneyCalculate(req.getCancelLeaseOrderId());
	}

	/**
	 * 
	 * Description: 检验是否已存在退租订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return CheckCancelLeaseOrderResponse
	 */
	@RequestMapping(value = "/checkCancelLeaseOrder.do")
	@ResponseBody
	public CheckCancelLeaseOrderResponse checkCancelLeaseOrder(@RequestBody CheckCancelLeaseOrderRequest req)
	{
		Boolean haveOrder = cancelLeaseOrderService.checkCancelLeaseOrderWithTrans(req.getRentalLeaseOrderId());
		CheckCancelLeaseOrderResponse resp = new CheckCancelLeaseOrderResponse();
		if (haveOrder)
		{
			resp.setHaveOrder(BooleanFlagDef.BOOLEAN_TRUE);
		} else
		{
			resp.setHaveOrder(BooleanFlagDef.BOOLEAN_FALSE);
		}
		return resp;
	}

	/**
	 * 
	 * Description: 运行时异常统一处理
	 * 
	 * @author jinyanan
	 *
	 * @param ex
	 *          ex
	 * @return ResponseModel
	 */
	@ExceptionHandler(value = BaseAppException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseModel handlerError(BaseAppException ex)
	{
		logger.error("error", ex);
		ResponseModel resp = new ResponseModel();
		resp.setState(ResponseModel.BUSINESS_ERROR);
		resp.setErrorMsg(ex.getErrorMessage());
		return resp;
	}

}
