package com.room1000.suborder.payorder.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.model.ResponseModel;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.payorder.dto.request.PayOrderOperationRequest;
import com.room1000.suborder.payorder.service.IPayOrderService;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;

import pccom.web.beans.User;

/**
 * 
 * Description: 支付订单Controller
 * 
 * Created on 2017年5月23日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Controller
@RequestMapping("/payOrder")
public class PayOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(PayOrderController.class);

    /**
     * payOrderService
     */
    @Autowired
    private IPayOrderService payOrderService;

    /**
     * 
     * Description: 录入订单信息
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/inputPayOrderInfo.do")
    @ResponseBody
    public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        PayOrderDto payOrderDto = new PayOrderDto();
        payOrderDto.setType(req.getType());
        payOrderDto.setPayRefId(req.getPayRefId());
        payOrderDto.setAppointmentDate(req.getAppointmentDate());
        payOrderDto.setComments(req.getComments());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserMobile());
        workOrderDto.setPayableMoney(req.getPayableMoney());
        workOrderDto.setName(req.getOrderName());

        if (StringUtils.isEmpty(userName)) {
            workOrderDto.setCreatedUserId(req.getCreatedUserId());
            payOrderService.inputSubOrderInfoWithTrans(payOrderDto, workOrderDto, SystemAccountDef.CUSTOMER);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            workOrderDto.setCreatedStaffId(Long.valueOf(currentStaff.getId()));
            payOrderService.inputSubOrderInfoWithTrans(payOrderDto, workOrderDto, Long.valueOf(currentStaff.getId()));

        }
        return new ResponseModel();
    }

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/processOrder.do")
    @ResponseBody
    public ResponseModel processOrder(@RequestBody PayOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            payOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            payOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;
    }

    /**
     * 
     * Description: 订单审核(运营)
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/orderAudit.do")
    @ResponseBody
    public ResponseModel orderAudit(@RequestBody PayOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            payOrderService.orderAuditWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(), submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            payOrderService.orderAuditWithTrans(req.getCode(), req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()), submitFlag);
        }
        return new ResponseModel();
    }

    /**
     * 
     * Description: 运行时异常统一处理
     * 
     * @author jinyanan
     *
     * @param ex ex
     * @return ResponseModel
     */
    @ExceptionHandler(value = BaseAppException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseModel handlerError(BaseAppException ex) {
        logger.error("error", ex);
        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.BUSINESS_ERROR);
        resp.setErrorMsg(ex.getErrorMessage());
        return resp;
    }
}
