package com.room1000.suborder.agentapplyorder;

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
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderDto;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderValueDto;
import com.room1000.suborder.agentapplyorder.service.IAgentApplyOrderService;
import com.room1000.workorder.dto.WorkOrderDto;

/**
 * Description: Created on 2017年6月27日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback(false)
@Transactional
public class AgentApplyOrderServiceTest {

    /**
     * agentApplyOrderService
     */
    @Autowired
    private IAgentApplyOrderService agentApplyOrderService;

    /**
     * 项目路径
     */
    private static final String realPath = "/Users/littlefisher/Documents/workspace/MPWeb/1.1.1.1-SNAPSHOT_jin/"
        + ".metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MPWeb";
    
    private static String code = "20170627093844329";

    /**
     * Description: 订单录入
     * 
     * @author jinyanan
     */
    @Test
    public void testInputSubOrderInfo() {
        AgentApplyOrderDto agentApplyOrderDto = new AgentApplyOrderDto();
        agentApplyOrderDto.setAppointmentDate(DateUtil.getDBDateTime());
        agentApplyOrderDto.setCertNbr("410111111111111111");
        agentApplyOrderDto.setComments("备注");
        agentApplyOrderDto.setImageUrl("身份证图片地址");
        agentApplyOrderDto.setUserId(88L);
        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setCreatedUserId(88L);
        workOrderDto.setUserName("汤燕");
        workOrderDto.setUserPhone("13611111111");
        agentApplyOrderService.inputSubOrderInfoWithTrans(agentApplyOrderDto, workOrderDto, realPath, -2L);
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testOrderAudit() {
        List<AgentApplyOrderValueDto> valueList = new ArrayList<>();
        AgentApplyOrderValueDto commentsValueDto = new AgentApplyOrderValueDto();
        commentsValueDto.setAttrId(1L);
        commentsValueDto.setAttrPath("AGENT_APPLY_ORDER_PROCESS.ORDER_AUDIT.COMMENTS");
        commentsValueDto.setTextInput("订单审核备注");
        valueList.add(commentsValueDto);
        AgentApplyOrderValueDto passedValueDto = new AgentApplyOrderValueDto();
        passedValueDto.setAttrId(21L);
        passedValueDto.setAttrPath("AGENT_APPLY_ORDER_PROCESS.ORDER_AUDIT.PASSED");
        passedValueDto.setTextInput("Y");
        valueList.add(passedValueDto);
        agentApplyOrderService.orderAuditWithTrans(code, valueList, 545454L, true);
    }
}
