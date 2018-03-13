package com.room1000.suborder.agentapplyorder.controller;

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
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderDto;
import com.room1000.suborder.agentapplyorder.dto.request.AgentApplyOrderOperationRequest;
import com.room1000.suborder.agentapplyorder.dto.request.InputSubOrderInfoRequest;
import com.room1000.suborder.agentapplyorder.service.IAgentApplyOrderService;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;

import pccom.web.beans.User;

/**
 * Description: 
 * 
 * Created on 2017年6月22日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Controller
@RequestMapping("/agentApplyOrder")
public class AgentApplyOrderController {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AgentApplyOrderController.class);

    /**
     * agentApplyOrderService
     */
    @Autowired
    private IAgentApplyOrderService agentApplyOrderService;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * Description: 录入订单，该订单仅针对于C端使用，所以不附带userName
     * 
     * @author jinyanan
     * @param req req
     * @return ResponseModel
     */
    @RequestMapping("/inputAgentApplyOrderInfo.do")
    public ResponseModel inputSubOrderInfo(@RequestBody InputSubOrderInfoRequest req) {

        AgentApplyOrderDto agentApplyOrderDto = new AgentApplyOrderDto();
        agentApplyOrderDto.setAppointmentDate(req.getAppointmentDate());
        agentApplyOrderDto.setCertNbr(agentApplyOrderDto.getCertNbr());
        agentApplyOrderDto.setComments(req.getComments());
        agentApplyOrderDto.setImageUrl(req.getImageUrl());
        agentApplyOrderDto.setUserId(req.getUserId());

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setCreatedUserId(req.getUserId());
        workOrderDto.setName(req.getOrderName());
        workOrderDto.setUserName(req.getUserName());
        workOrderDto.setUserPhone(req.getUserPhone());

        String realPath = request.getSession().getServletContext().getRealPath("/");

        agentApplyOrderService.inputSubOrderInfoWithTrans(agentApplyOrderDto, workOrderDto, realPath,
            SystemAccountDef.CUSTOMER);

        return new ResponseModel();
    }

    /**
     * Description: 申请订单审批
     * 
     * @author jinyanan
     * @param req req
     * @param userName userName
     * @return ResponseModel
     */
    @RequestMapping("orderAudit.do")
    public ResponseModel orderAudit(@RequestBody AgentApplyOrderOperationRequest req,
        @CookieValue(value = "username", required = false) String userName) {
        Boolean submitFlag = BooleanFlagDef.BOOLEAN_TRUE.equals(req.getSubmitFlag()) ? true : false;
        if (StringUtils.isEmpty(userName)) {
            agentApplyOrderService.orderAuditWithTrans(req.getCode(), req.getSubOrderValueList(), req.getDealerId(),
                submitFlag);
        }
        else {
            User currentStaff = SubOrderUtil.getUserByUserName(userName);
            agentApplyOrderService.orderAuditWithTrans(req.getCode(), req.getSubOrderValueList(),
                Long.valueOf(currentStaff.getId()), submitFlag);
        }
        return new ResponseModel();
    }

    /**
     * Description: 运行时异常统一处理
     * 
     * @author jinyanan
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
