package com.room1000.suborder.payorder;


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
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.dto.PayOrderValueDto;
import com.room1000.suborder.payorder.service.IPayOrderService;
import com.room1000.workorder.dto.WorkOrderDto;
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
public class PayOrderTest {
    
    /**
     * payOrderService
     */
    @Autowired
    private IPayOrderService payOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * 编码
     */
    private static String code = "20170605152648027";

    /**
     * 
     * Description: 订单录入
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testInputSubOrderInfo() {
        PayOrderDto payOrderDto = new PayOrderDto();
        payOrderDto.setType("A");
        payOrderDto.setPayRefId(40L);
        payOrderDto.setAppointmentDate(DateUtil.getDBDateTime());
        payOrderDto.setComments("自测");
        
        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName("name");
        workOrderDto.setUserPhone("13611111111");
        workOrderDto.setName("支付订单_经纪人推荐");
        workOrderDto.setPayableMoney("20000");
        payOrderService.inputSubOrderInfoWithTrans(payOrderDto, workOrderDto, SystemAccountDef.SYSTEM);
    }

    /**
     * 
     * Description: 订单执行
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testProcessOrder() {
        List<PayOrderValueDto> valueList = new ArrayList<>();
        PayOrderValueDto bankCardNbrValueDto = new PayOrderValueDto();
        bankCardNbrValueDto.setAttrId(3L);
        bankCardNbrValueDto.setAttrPath("PAY_ORDER_PROCESS.ORDER_INPUT.BANK_CARD_NBR");
        bankCardNbrValueDto.setTextInput("123123123123123");
        valueList.add(bankCardNbrValueDto);
        PayOrderValueDto bankAddressValueDto = new PayOrderValueDto();
        bankAddressValueDto.setAttrId(4L);
        bankAddressValueDto.setAttrPath("PAY_ORDER_PROCESS.ORDER_INPUT.BANK_ADDRESS");
        bankAddressValueDto.setTextInput("南京支行");
        valueList.add(bankAddressValueDto);
        PayOrderValueDto payeeValueDto = new PayOrderValueDto();
        payeeValueDto.setAttrId(28L);
        payeeValueDto.setAttrPath("PAY_ORDER_PROCESS.ORDER_INPUT.PAYEE");
        payeeValueDto.setTextInput("收款人");
        valueList.add(payeeValueDto);
        PayOrderValueDto commentsValueDto = new PayOrderValueDto();
        commentsValueDto.setAttrId(1L);
        commentsValueDto.setAttrPath("PAY_ORDER_PROCESS.ORDER_INPUT.COMMENTS");
        commentsValueDto.setTextInput("备注");
        valueList.add(commentsValueDto);
        
        payOrderService.processOrderWithTrans(code, valueList, 545605L);
    }

    /**
     * 
     * Description: 订单审核
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testOrderAudit() {
        List<PayOrderValueDto> valueList = new ArrayList<>();
        PayOrderValueDto commentsValueDto = new PayOrderValueDto();
        commentsValueDto.setAttrId(1L);
        commentsValueDto.setAttrPath("PAY_ORDER_PROCESS.OPERATOR_AUDIT.COMMENTS");
        commentsValueDto.setTextInput("订单审核备注");
        valueList.add(commentsValueDto);
        PayOrderValueDto passedValueDto = new PayOrderValueDto();
        passedValueDto.setAttrId(21L);
        passedValueDto.setAttrPath("PAY_ORDER_PROCESS.OPERATOR_AUDIT.PASSED");
        passedValueDto.setTextInput("Y");
        valueList.add(passedValueDto);
        payOrderService.orderAuditWithTrans(code, valueList, 545605L, true);
    }

    /**
     * 
     * Description: 订单支付
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testPay() {
        workOrderService.payWithTrans(123L, 545605L, 10000L);
    }
    
}
