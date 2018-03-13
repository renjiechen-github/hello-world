package com.room1000.workorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.OrderCommentaryTypeDef;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.OrderCommentaryTypeDto;
import com.room1000.workorder.dto.OrderStepDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.dto.request.QryOrderCommentaryPagerListRequest;
import com.room1000.workorder.dto.request.QryWorkOrderPagerListRequest;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月13日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback(false)
@Transactional
public class WorkOrderTest {
    
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(WorkOrderTest.class);
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * 
     * Description: 评价功能
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testOrderCommentary() {
        Long workOrderId = 18655L;
        OrderCommentaryDto commentaryDto = new OrderCommentaryDto();
        commentaryDto.setWorkOrderId(workOrderId);
        commentaryDto.setScore(5L);
        commentaryDto.setCommentaryPersonId(null);
        commentaryDto.setType(OrderCommentaryTypeDef.GENERAL_EVALUATION);
        commentaryDto.setComments("评价内容");
        List<OrderCommentaryDto> commentaryList = new ArrayList<>();
        commentaryList.add(commentaryDto);
        workOrderService.workOrderCommentaryWithTrans(workOrderId, commentaryList, "", "", "Y");
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testQryWorkOrderList() {
        QryWorkOrderPagerListRequest req = new QryWorkOrderPagerListRequest();
        req.setPageNum(1);
        req.setPageSize(20);
        req.setCurrentDealerId(545437L);
        req.setCurrentDealerDealtOrder(BooleanFlagDef.BOOLEAN_TRUE);
//        req.setCurrentDealerFlag(BooleanFlagDef.BOOLEAN_TRUE);
        
//        List<String> currentDealerRoleIds = new ArrayList<>();
//        currentDealerRoleIds.add("27");
//        req.setCurrentDealerRoleIds(currentDealerRoleIds);
        List<WorkOrderDto> workOrderList =  workOrderService.getWorkOrderList(req);
        logger.info("workOrderList: " + workOrderList);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testQryOrderCommentaryList() {
        QryOrderCommentaryPagerListRequest req = new QryOrderCommentaryPagerListRequest();
        req.setPageNum(1);
        req.setPageSize(20);
        req.setType("A");
        req.setScore(5L);
        List<OrderCommentaryDto> orderCommentaryList = workOrderService.getOrderCommentaryDto(req);
        logger.info("orderCommentaryList : " + orderCommentaryList);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testPay() {
        workOrderService.payWithTrans(6093L, 545597L, 6000L);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @throws Exception <br>
     */
    @Test
    public void testEndProcess() throws Exception {
        workOrderService.endProcessWithTrans(14851L, 7L);
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @throws Exception <br>
     */
    @Test
    public void getActivitis() throws Exception {
        IProcessTask task = new ProcessTaskImpl();
        task.getAllTaskActiviti(1L);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @throws Exception <br>
     */
    @Test
    public void testGetOrderStep() throws Exception {
        List<OrderStepDto> list = workOrderService.getOrderStep(31446L);
        logger.debug("list: " + JSONObject.toJSONString(list));
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @throws Exception <br>
     */
    @Test
    public void testDirect2Activity() throws Exception {
        IProcessTask task = new ProcessTaskImpl();
        task.direct2Activity(39647L, "C");
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testGetDate() {
        Date date = DateUtil.getDBDateTime();
        logger.info("date: " + date);
        
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testQryOrderCommentaryTypeList() {
        String orderType = "B";
        List<OrderCommentaryTypeDto> orderCommentaryTypeList = workOrderService.getOrderCommentaryType(orderType);
        logger.info("orderCommentaryTypeList: " + orderCommentaryTypeList);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testGetHis() {
        Long workOrderId = 26518L;
        List<Object> subOrderList = workOrderService.getHis(workOrderId);
        logger.debug("subOrderHisList: " + JSONObject.toJSONString(subOrderList));
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testCreateWorkOrder() {
        WorkOrderDto workOrderDto = new WorkOrderDto();
        // 管家id
        workOrderDto.setStaffId(1L);
        workOrderDto.setSubAssignedDealerId(1L);
        
        workOrderDto.setName("订单名称");
        workOrderDto.setType(WorkOrderTypeDef.BROKER_ORDER);
        workOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
        workOrderDto.setUserName("用户名");
        workOrderDto.setUserPhone("18600000000");
        // 合约id
        workOrderDto.setRentalLeaseOrderId(2012L);
        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        workOrderDto.setCreatedStaffId(SystemAccountDef.SYSTEM);
        workOrderDto.setAppointmentDate(DateUtil.getDBDateTime());
        workOrderDto.setSubComments("订单备注");
        
        workOrderService.createWorkOrderWithTrans(workOrderDto);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testDoAuditBrokerOrderWithTrans() {
        workOrderService.doAuditBrokerOrderWithTrans(8181L, "备注", false, 545602L);
    }
}
