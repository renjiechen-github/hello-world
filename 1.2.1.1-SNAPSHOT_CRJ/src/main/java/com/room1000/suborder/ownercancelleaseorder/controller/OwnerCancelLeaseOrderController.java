package com.room1000.suborder.ownercancelleaseorder.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.model.ResponseModel;
import com.room1000.suborder.cancelleaseorder.dto.request.RentalMoneyCalculateRequest;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.ownercancelleaseorder.dto.request.OwnerCancelLeaseOrderOperationRequest;
import com.room1000.suborder.ownercancelleaseorder.dto.response.GetOwnerCancelLeaseOrderTypeResponse;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;

import pccom.web.beans.User;

/**
 * Description:
 * 
 * Created on 2017年5月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Controller
@RequestMapping("/ownerCancelLeaseOrder")
public class OwnerCancelLeaseOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(OwnerCancelLeaseOrderController.class);

    /**
     * ownerCancelLeaseOrderService
     */
    @Autowired
    private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * Description: 新增订单
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/inputOwnerCancelLeaseOrderInfo.do")
    @ResponseBody
    public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        if (ownerCancelLeaseOrderService.checkOwnerCancelLeaseOrderWithTrans(req.getTakeHouseOrderId())) {
            // 在途单检测
            throw new BaseAppException("当前订单已生成或当前房源存在出租合约不允许创建！");
        }

        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = new OwnerCancelLeaseOrderDto();
        ownerCancelLeaseOrderDto.setTakeHouseOrderId(req.getTakeHouseOrderId());
        ownerCancelLeaseOrderDto.setAppointmentDate(req.getAppointmentDate());
        ownerCancelLeaseOrderDto.setComments(req.getComments());
        ownerCancelLeaseOrderDto.setType(req.getType());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserMobile());
        workOrderDto.setName(req.getOrderName());

        User currentStaff = SubOrderUtil.getUserByUserName(userName);

        // 如果为空，说明是C端做的操作
        if (StringUtils.isEmpty(userName)) {
            if (null != req.getCreatedUserId()) {
                workOrderDto.setCreatedUserId(req.getCreatedUserId());
                ownerCancelLeaseOrderService.inputSubOrderInfoWithTrans(ownerCancelLeaseOrderDto, workOrderDto,
                    SystemAccountDef.CUSTOMER);
            }
            else if (null != req.getCreatedStaffId()) {
                ownerCancelLeaseOrderService.inputSubOrderInfoWithTrans(ownerCancelLeaseOrderDto, workOrderDto,
                    req.getCreatedStaffId());
            }
            else {
                throw new BaseAppException("CreatedUserId and createdStaffId can not both be null!");
            }
        }
        else {
            workOrderDto.setCreatedStaffId(Long.valueOf(currentStaff.getId()));
            ownerCancelLeaseOrderService.inputSubOrderInfoWithTrans(ownerCancelLeaseOrderDto, workOrderDto,
                Long.valueOf(currentStaff.getId()));
        }

        return new ResponseModel();
    }

    /**
     * 
     * Description: 房源释放
     * 
     * 不再使用，高管审批后，直接释放房源
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/releaseHouse.do")
    @ResponseBody
    @Deprecated
    public ResponseModel releaseHouse(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.releaseHouse(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.releaseHouse(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }
        return new ResponseModel();
    }

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/takeOrder.do")
    @ResponseBody
    public ResponseModel takeOrder(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.takeOrderWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.takeOrderWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }
        return new ResponseModel();
    }

    /**
     * 
     * Description: 重新指派订单
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/reassignOrder.do")
    @ResponseBody
    public ResponseModel reAssignOrder(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.reAssignSubOrderWithTrans(req.getCode(), req.getNewButlerId(),
                req.getOwnerCancelLeaseOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.reAssignSubOrderWithTrans(req.getCode(), req.getNewButlerId(),
                req.getOwnerCancelLeaseOrderValueList(), Long.valueOf(currentStaff.getId()));
        }

        return new ResponseModel();
    }

    /**
     * Description: 管家上门
     * 
     * @author jinyanan
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/butlerGetHome.do")
    @ResponseBody
    public ResponseModel butlerGetHome(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        String realPath = this.request.getSession().getServletContext().getRealPath("/");
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.butlerGetHomeWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                req.getDealerId(), realPath, submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.butlerGetHomeWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                Long.valueOf(currentStaff.getId()), realPath, submitFlag);
        }

        return new ResponseModel();
    }

    /**
     * Description: 租务审核
     * 
     * @author jinyanan
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/rentalAudit.do")
    @ResponseBody
    public ResponseModel rentalAudit(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.rentalAuditWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.rentalAuditWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        return new ResponseModel();
    }

    /**
     * 
     * Description: 租金计算
     * 
     * @param req req
     * @return Map<String, String>
     */
    @RequestMapping(value = "/rentalMoneyCalculate.do")
    @ResponseBody
    public Map<String, String> rentalMoneyCalculate(@RequestBody RentalMoneyCalculateRequest req) {
        return ownerCancelLeaseOrderService.rentalMoneyCalculate(req.getCancelLeaseOrderId());
    }

    /**
     * Description: 高管审批
     * 
     * @author jinyanan
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/managerAudit.do")
    @ResponseBody
    public ResponseModel managerAudit(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.managerAuditWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.managerAuditWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        return new ResponseModel();
    }

    /**
     * Description: 财务审批
     * 
     * @author jinyanan
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/financeAudit.do")
    @ResponseBody
    public ResponseModel financeAudit(@RequestBody OwnerCancelLeaseOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        if (StringUtils.isEmpty(userName)) {
            ownerCancelLeaseOrderService.financeAuditWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            ownerCancelLeaseOrderService.financeAuditWithTrans(req.getCode(), req.getOwnerCancelLeaseOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        return new ResponseModel();
    }

    /**
     * 
     * Description: 查询业主退租类型
     * 
     * @author jinyanan
     *
     * @return GetOwnerCancelLeaseOrderTypeResponse
     */
    @RequestMapping(value = "/getOwnerCancelLeaseOrderType.do")
    @ResponseBody
    public GetOwnerCancelLeaseOrderTypeResponse getOwnerCancelLeaseOrderType() {
        GetOwnerCancelLeaseOrderTypeResponse resp = new GetOwnerCancelLeaseOrderTypeResponse();
        resp.setOwnerCancelLeaseOrderTypeList(ownerCancelLeaseOrderService.getOwnerCancelLeaseOrderType());
        return resp;
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
