package com.room1000.suborder.complaintorder.controller;

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
import com.room1000.suborder.complaintorder.dto.ComplaintOrderDto;
import com.room1000.suborder.complaintorder.dto.request.ComplaintOrderOperationRequest;
import com.room1000.suborder.complaintorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.complaintorder.service.IComplaintOrderService;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;

import pccom.web.beans.User;

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
@Controller
@RequestMapping("/complaintOrder")
public class ComplaintOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(ComplaintOrderController.class);

    /**
     * houseLookingOrderService
     */
    @Autowired
    private IComplaintOrderService complainOrderService;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * Description: 点击新增订单时，生成WorkOrder，并录入信息
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/inputComplaintOrderInfo.do")
    @ResponseBody
    public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        ResponseModel resp = new ResponseModel();

        ComplaintOrderDto complaintOrderDto = new ComplaintOrderDto();
        complaintOrderDto.setComments(req.getComments());
        complaintOrderDto.setComplaintDate(req.getComplaintDate());
        complaintOrderDto.setImageUrl(req.getImageUrl());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserMobile());
        workOrderDto.setName(req.getOrderName());
        
        String realPath = request.getSession().getServletContext().getRealPath("/");

        User currentStaff = SubOrderUtil.getUserByUserName(userName);
        if (StringUtils.isEmpty(userName)) {
            workOrderDto.setCreatedUserId(req.getCreatedUserId());
            complainOrderService.inputSubOrderInfoWithTrans(complaintOrderDto, workOrderDto, SystemAccountDef.CUSTOMER, realPath);

        }
        else {
            workOrderDto.setCreatedStaffId(Long.valueOf(currentStaff.getId()));
            complainOrderService.inputSubOrderInfoWithTrans(complaintOrderDto, workOrderDto,
                Long.valueOf(currentStaff.getId()), realPath);

        }

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
    public ResponseModel assignOrder(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.assignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.assignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;
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
    @RequestMapping("/reassignOrder.do")
    @ResponseBody
    public ResponseModel reassignOrder(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.reassignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.reassignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
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
    public ResponseModel takeOrder(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
        }
        else {

            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
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
    public ResponseModel processOrder(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(),
                realPath, submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
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
    public ResponseModel passInStaffReview(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {

            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
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
    public ResponseModel reassign(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), Long.valueOf(currentStaff.getId()));
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
    public ResponseModel staffTace(@RequestBody ComplaintOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            complainOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            complainOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
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
