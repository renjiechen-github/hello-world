package com.room1000.suborder.routinecleaningorder.controller;

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
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.routinecleaningorder.dto.request.RoutineCleaningOrderOperationRequest;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;

import pccom.web.beans.User;

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
@Controller
@RequestMapping("/routineCleaningOrder")
public class RoutineCleaningOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(RoutineCleaningOrderController.class);

    /**
     * routineCleaningOrderService
     */
    @Autowired
    private IRoutineCleaningOrderService routineCleaningOrderService;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * Description: 录入订单
     * 
     * @author jinyanan
     *
     * @param req req
     * @return ResponseModel
     */
    @RequestMapping(value = "/inputRoutineCleaningOrderInfo.do")
    @ResponseBody
    public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req) {

        RoutineCleaningOrderDto routineCleaningOrderDto = new RoutineCleaningOrderDto();
        routineCleaningOrderDto.setAppointmentDate(req.getAppointmentDate());
        routineCleaningOrderDto.setRentalLeaseOrderId(req.getRentalLeaseOrderId());
        routineCleaningOrderDto.setComments(req.getComments());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserMobile());
        workOrderDto.setName(req.getOrderName());

        routineCleaningOrderService.inputSubOrderInfoWithTrans(routineCleaningOrderDto, workOrderDto, null);

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;

    }

    /**
     * 
     * Description: 指派订单
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/assignOrder.do")
    @ResponseBody
    public ResponseModel assignOrder(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.assignOrderWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            routineCleaningOrderService.assignOrderWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
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
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/reassignOrder.do")
    @ResponseBody
    public ResponseModel reassignOrder(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.reassignOrderWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            routineCleaningOrderService.reassignOrderWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;
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
    @RequestMapping("/takeOrder.do")
    @ResponseBody
    public ResponseModel takeOrder(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            routineCleaningOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;
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
    public ResponseModel processOrder(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
                req.getDealerId(), realPath, submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            routineCleaningOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()), realPath, submitFlag);
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;

    }

    /**
     * 
     * Description: 客户回访时通过
     * 
     * 客服回访环节删去
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/passInStaffReview.do")
    @ResponseBody
    @Deprecated
    public ResponseModel passInStaffReview(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
                req.getDealerId(), realPath);
        }
        else {

            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            routineCleaningOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()), realPath);
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;
    }

    /**
     * 
     * Description: 客服回访时重新指派订单
     * 
     * 客服回访环节删去
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/reassignOrderInStaffReview.do")
    @ResponseBody
    @Deprecated
    public ResponseModel reassign(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), req.getDealerId(), realPath);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            routineCleaningOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()), realPath);
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;
    }

    /**
     * 
     * Description: 客服回访时订单追踪
     * 
     * 客服回访环节删去
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/staffTace.do")
    @ResponseBody
    @Deprecated
    public ResponseModel staffTace(@RequestBody RoutineCleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        if (StringUtils.isEmpty(userName)) {
            routineCleaningOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(),
                realPath);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            routineCleaningOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()), realPath);
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
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