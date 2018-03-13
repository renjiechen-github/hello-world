package com.room1000.suborder.cleaningorder.controller;

import java.util.List;

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
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.dto.CleaningTypeDto;
import com.room1000.suborder.cleaningorder.dto.request.CleaningOrderOperationRequest;
import com.room1000.suborder.cleaningorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.cleaningorder.dto.response.GetCleaningTypeResponse;
import com.room1000.suborder.cleaningorder.service.ICleaningOrderService;
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
@RequestMapping("/cleaningOrder")
public class CleaningOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CleaningOrderController.class);

    /**
     * cleaningOrderService
     */
    @Autowired
    private ICleaningOrderService cleaningOrderService;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * Description: 点击新增订单时，生成WorkOrder，并录入退租单信息
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping(value = "/inputCleaningOrderInfo.do")
    @ResponseBody
    public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        ResponseModel resp = new ResponseModel();

        CleaningOrderDto cleaningOrderDto = new CleaningOrderDto();
        cleaningOrderDto.setRentalLeaseOrderId(req.getRentalLeaseOrderId());
        cleaningOrderDto.setAppointmentDate(req.getAppointmentDate());
        cleaningOrderDto.setType(req.getType());
        cleaningOrderDto.setImageUrl(req.getImageUrl());
        cleaningOrderDto.setCleaningMoney(req.getCleaningMoney());
        cleaningOrderDto.setComments(req.getComments());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserMobile());
        workOrderDto.setName(req.getOrderName());

        String realPath = request.getSession().getServletContext().getRealPath("/");
        User currentStaff = SubOrderUtil.getUserByUserName(userName);
        // 如果为空，说明是C端做的操作
        if (StringUtils.isEmpty(userName)) {
            workOrderDto.setCreatedUserId(req.getCreatedUserId());
            cleaningOrderService.inputSubOrderInfoWithTrans(cleaningOrderDto, workOrderDto, SystemAccountDef.CUSTOMER,
                realPath);
        }
        else {
            workOrderDto.setCreatedStaffId(Long.valueOf(currentStaff.getId()));
            cleaningOrderService.inputSubOrderInfoWithTrans(cleaningOrderDto, workOrderDto,
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
    public ResponseModel assignOrder(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            cleaningOrderService.assignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            cleaningOrderService.assignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
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
    public ResponseModel reassignOrder(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            cleaningOrderService.reassignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            cleaningOrderService.reassignOrderWithTrans(req.getCode(), req.getStaffId(), req.getSubOrderValueList(),
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
    public ResponseModel takeOrder(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            cleaningOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            cleaningOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
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
    public ResponseModel processOrder(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            cleaningOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(),
                realPath, submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            cleaningOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
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
    public ResponseModel passInStaffReview(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        if (StringUtils.isEmpty(userName)) {
            cleaningOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
                req.getDealerId(), realPath);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            cleaningOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
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
    @Deprecated
    @ResponseBody
    public ResponseModel reassign(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        if (StringUtils.isEmpty(userName)) {

            cleaningOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getStaffId(),
                req.getSubOrderValueList(), req.getDealerId(), realPath);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            cleaningOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getStaffId(),
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
    public ResponseModel staffTace(@RequestBody CleaningOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        if (StringUtils.isEmpty(userName)) {
            cleaningOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(),
                realPath);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            cleaningOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()), realPath);
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;

    }

    /**
     * 
     * Description: 查询所有保洁类型
     * 
     * @author jinyanan
     * 
     * @return GetCleaningTypeResponse
     *
     */
    @RequestMapping("/getCleaningType.do")
    @ResponseBody
    public GetCleaningTypeResponse getCleaningType() {
        List<CleaningTypeDto> cleaningTypeList = cleaningOrderService.getCleaningType();
        GetCleaningTypeResponse resp = new GetCleaningTypeResponse();
        resp.setCleaningTypeList(cleaningTypeList);
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
