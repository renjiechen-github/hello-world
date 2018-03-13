package com.room1000.suborder.houselookingorder.controller;

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
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.dto.request.HouseLookingOrderOperationRequest;
import com.room1000.suborder.houselookingorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.houselookingorder.dto.response.GetHouseLookingOrderChannelResponse;
import com.room1000.suborder.houselookingorder.dto.response.InputHouseLookingOrderInfoResponse;
import com.room1000.suborder.houselookingorder.service.IHouseLookingOrderService;
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
@RequestMapping("/houseLookingOrder")
public class HouseLookingOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(HouseLookingOrderController.class);

    /**
     * houseLookingOrderService
     */
    @Autowired
    private IHouseLookingOrderService houseLookingOrderService;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return InputHouseLookingOrderInfoResponse
     */
    @RequestMapping(value = "/inputHouseLookingOrderInfo.do")
    @ResponseBody
    public InputHouseLookingOrderInfoResponse inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req,
        @CookieValue(value = "username", required = false) String userName) {

        HouseLookingOrderDto houseLookingOrderDto = new HouseLookingOrderDto();
        houseLookingOrderDto.setAreaId(req.getAreaId());
        houseLookingOrderDto.setGroupId(req.getGroupId());
        houseLookingOrderDto.setHouseId(req.getHouseId());
        houseLookingOrderDto.setChannel(req.getChannel());
        houseLookingOrderDto.setAppointmentDate(req.getAppointmentDate());
        houseLookingOrderDto.setComments(req.getComments());
        // 指派处理人
        houseLookingOrderDto.setButlerId(req.getAssignedDealerId());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserMobile());
        workOrderDto.setStaffId(houseLookingOrderDto.getButlerId());
        workOrderDto.setName(req.getOrderName());

        User currentStaff = SubOrderUtil.getUserByUserName(userName);
        if (StringUtils.isEmpty(userName)) {
            workOrderDto.setCreatedUserId(req.getCreatedUserId());
            houseLookingOrderService.inputSubOrderInfoWithTrans(houseLookingOrderDto, workOrderDto,
                SystemAccountDef.CUSTOMER, req.getRecommendId());
        }
        else {
            workOrderDto.setCreatedStaffId(Long.valueOf(currentStaff.getId()));
            houseLookingOrderService.inputSubOrderInfoWithTrans(houseLookingOrderDto, workOrderDto,
                Long.valueOf(currentStaff.getId()), req.getRecommendId());
        }
        InputHouseLookingOrderInfoResponse resp = new InputHouseLookingOrderInfoResponse();
        resp.setWorkOrderId(workOrderDto.getId());
        resp.setCode(workOrderDto.getCode());
        resp.setState(ResponseModel.NORMAL);
        return resp;

    }

    /**
     * 
     * Description: 指派订单
     * 
     * 看房订单删去指派订单环节
     * 
     * @author jinyanan
     *
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("/assignOrder.do")
    @ResponseBody
    @Deprecated
    public ResponseModel assignOrder(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            houseLookingOrderService.assignOrderWithTrans(req.getCode(), req.getButlerId(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            houseLookingOrderService.assignOrderWithTrans(req.getCode(), req.getButlerId(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
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
    public ResponseModel reassignOrder(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {

            houseLookingOrderService.reassignOrderWithTrans(req.getCode(), req.getButlerId(),
                req.getSubOrderValueList(), req.getDealerId());
        }
        else {

            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            houseLookingOrderService.reassignOrderWithTrans(req.getCode(), req.getButlerId(),
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
    public ResponseModel takeOrder(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            logger.debug("insert by mobile!");
            houseLookingOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            logger.debug("insert by pc!");
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            houseLookingOrderService.takeOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
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
    public ResponseModel processOrder(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            houseLookingOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(),
                realPath, submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            houseLookingOrderService.processOrderWithTrans(req.getCode(), req.getSubOrderValueList(),
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
     * 删去客户回访环节
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
    public ResponseModel passInStaffReview(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            houseLookingOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
                req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            houseLookingOrderService.passInStaffReviewWithTrans(req.getCode(), req.getSubOrderValueList(),
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
     * 删去客户回访环节
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
    public ResponseModel reassign(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            houseLookingOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getButlerId(),
                req.getSubOrderValueList(), req.getDealerId());
        }
        else {

            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            houseLookingOrderService.reassignOrderInStaffReviewWithTrans(req.getCode(), req.getButlerId(),
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
     * 删去客户回访环节
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
    public ResponseModel staffTace(@RequestBody HouseLookingOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        if (StringUtils.isEmpty(userName)) {
            houseLookingOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId());
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);

            houseLookingOrderService.staffTaceWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()));
        }

        ResponseModel resp = new ResponseModel();
        resp.setState(ResponseModel.NORMAL);
        return resp;

    }

    /**
     * 
     * Description: 获取渠道
     * 
     * @author jinyanan
     * 
     * @return GetHouseLookingOrderChannelResponse
     */
    @RequestMapping(value = "/getHouseLookingOrderChannel.do")
    @ResponseBody
    public GetHouseLookingOrderChannelResponse getHouseLookingOrderChannel() {

        GetHouseLookingOrderChannelResponse resp = new GetHouseLookingOrderChannelResponse();
        resp.setHouseLookingOrderChannelList(houseLookingOrderService.getHouseLookingOrderChannel());
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
