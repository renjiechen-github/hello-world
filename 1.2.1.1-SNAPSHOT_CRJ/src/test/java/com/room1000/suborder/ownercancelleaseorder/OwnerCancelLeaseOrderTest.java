package com.room1000.suborder.ownercancelleaseorder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * Description: Created on 2017年6月13日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback(false)
@Transactional
public class OwnerCancelLeaseOrderTest {

    /**
     * 项目路径
     */
    private String realPath = "/Users/littlefisher/Documents/workspace/MPWeb-maven/.metadata/"
        + ".plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MPWeb";

    /**
     * 编码
     */
    private String code = "20170510145306433";

    /**
     * ownerCancelLeaseOrderService
     */
    @Autowired
    private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * 
     * Description: 订单录入
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testInputSubOrderInfo() {
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = new OwnerCancelLeaseOrderDto();
        ownerCancelLeaseOrderDto.setTakeHouseOrderId(359L);
        ownerCancelLeaseOrderDto.setAppointmentDate(DateUtil.getDBDateTime());
        ownerCancelLeaseOrderDto.setComments("业主退租测试");
        ownerCancelLeaseOrderDto.setType("A");

        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName("仲鸣");
        workOrderDto.setUserPhone("13611518866");
        workOrderDto.setName("业主退租_钟鼎雅居");

        ownerCancelLeaseOrderService.inputSubOrderInfoWithTrans(ownerCancelLeaseOrderDto, workOrderDto, 545605L);
    }

    // @Test
    // public void testReleaseHouse() {
    // List<OwnerCancelLeaseOrderValueDto> valueList = new ArrayList<>();
    // OwnerCancelLeaseOrderValueDto commentsValue = new OwnerCancelLeaseOrderValueDto();
    // commentsValue.setAttrId(1L);
    // commentsValue.setAttrPath("OCL_ORDER_PROCESS.HOUSE_RELEASE.COMMENTS");
    // commentsValue.setTextInput("房源释放");
    // valueList.add(commentsValue);
    // ownerCancelLeaseOrderService.releaseHouse(code, valueList, 545605L);
    // }

    /**
     * 
     * Description: 接单
     * 
     * @author jinyanan
     *
     */
    @Test
    public void takeOrder() {
        List<OwnerCancelLeaseOrderValueDto> valueList = new ArrayList<>();
        OwnerCancelLeaseOrderValueDto takeOrderValue = new OwnerCancelLeaseOrderValueDto();
        takeOrderValue.setAttrId(1L);
        takeOrderValue.setAttrPath("OCL_ORDER_PROCESS.TAKE_ORDER.COMMENTS");
        takeOrderValue.setTextInput("接单");
        valueList.add(takeOrderValue);
        ownerCancelLeaseOrderService.takeOrderWithTrans(code, valueList, 545605L);
    }

    /**
     * 
     * Description: 执行
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testDoInOrder() {
        List<OwnerCancelLeaseOrderValueDto> valueList = new ArrayList<>();
        OwnerCancelLeaseOrderValueDto commentsValue = new OwnerCancelLeaseOrderValueDto();
        commentsValue.setAttrId(1L);
        commentsValue.setAttrPath("OCL_ORDER_PROCESS.OCL_BUTLER_GET_HOME.COMMENTS");
        commentsValue.setTextInput("管家上门");
        valueList.add(commentsValue);
        ownerCancelLeaseOrderService.butlerGetHomeWithTrans(code, valueList, 545605L, realPath, true);
    }

    /**
     * 
     * Description: 租务核算
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testRentalAudit() {
        List<OwnerCancelLeaseOrderValueDto> valueList = new ArrayList<>();
        OwnerCancelLeaseOrderValueDto commentsValue = new OwnerCancelLeaseOrderValueDto();
        commentsValue.setAttrId(1L);
        commentsValue.setAttrPath("OCL_ORDER_PROCESS.RENTAL_ACCOUNT.COMMENTS");
        commentsValue.setTextInput("租务核算");
        valueList.add(commentsValue);
        OwnerCancelLeaseOrderValueDto passedValue = new OwnerCancelLeaseOrderValueDto();
        passedValue.setAttrId(21L);
        passedValue.setAttrPath("OCL_ORDER_PROCESS.RENTAL_ACCOUNT.PASSED");
        passedValue.setTextInput("Y");
        valueList.add(passedValue);
        OwnerCancelLeaseOrderValueDto totalMoneyValue = new OwnerCancelLeaseOrderValueDto();
        totalMoneyValue.setAttrId(20L);
        totalMoneyValue.setAttrPath("OCL_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY");
        totalMoneyValue.setTextInput("100.00");
        valueList.add(totalMoneyValue);
        ownerCancelLeaseOrderService.rentalAuditWithTrans(code, valueList, 545605L);
    }

    /**
     * 
     * Description: 高管审批
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testManagerAudit() {
        List<OwnerCancelLeaseOrderValueDto> valueList = new ArrayList<>();
        OwnerCancelLeaseOrderValueDto commentsValue = new OwnerCancelLeaseOrderValueDto();
        commentsValue.setAttrId(1L);
        commentsValue.setAttrPath("OCL_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.COMMENTS");
        commentsValue.setTextInput("高管审批，不可打回");
        valueList.add(commentsValue);
        ownerCancelLeaseOrderService.managerAuditWithTrans(code, valueList, 545605L);
    }

    /**
     * 
     * Description: 财务审批
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testFinanceAudit() {
        List<OwnerCancelLeaseOrderValueDto> valueList = new ArrayList<>();
        OwnerCancelLeaseOrderValueDto commentsValue = new OwnerCancelLeaseOrderValueDto();
        commentsValue.setAttrId(1L);
        commentsValue.setAttrPath("OCL_ORDER_PROCESS.FINANCE_AUDIT.COMMENTS");
        commentsValue.setTextInput("财务审批，不可打回");
        valueList.add(commentsValue);
        OwnerCancelLeaseOrderValueDto passedValue = new OwnerCancelLeaseOrderValueDto();
        passedValue.setAttrId(21L);
        passedValue.setAttrPath("OCL_ORDER_PROCESS.FINANCE_AUDIT.PASSED");
        passedValue.setTextInput("Y");
        valueList.add(passedValue);
        ownerCancelLeaseOrderService.financeAuditWithTrans(code, valueList, 545605L);
    }

    /**
     * 
     * Description: 支付
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testPay() {
        workOrderService.payWithTrans(123L, 545605L, 10000L);
        // ownerCancelLeaseOrderService.payWithTrans(code, 545605L, 10000L);
    }
}
