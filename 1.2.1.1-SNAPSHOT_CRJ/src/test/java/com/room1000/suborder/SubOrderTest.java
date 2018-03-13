package com.room1000.suborder;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.define.CancelLeaseOrderTypeDef;
import com.room1000.suborder.cancelleaseorder.define.ChannelDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderHisDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback
@Transactional
public class SubOrderTest {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(SubOrderTest.class);
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;
    
    /**
     * cancelLeaseService
     */
    @Autowired
    private ICancelLeaseOrderService cancelLeaseService;

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @throws Exception <br>
     */
    @Test
    public void testCreatedHis() throws Exception {
        CancelLeaseOrderDto cancelLeaseOrderDto = new CancelLeaseOrderDto();
        cancelLeaseOrderDto.setId(1L);
        cancelLeaseOrderDto.setActualDealerId(2L);
        cancelLeaseOrderDto.setAssignedDealerId(3L);
        cancelLeaseOrderDto.setAssignedDealerRoleId(4L);
        cancelLeaseOrderDto.setAttrCatgId(5L);
        cancelLeaseOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        cancelLeaseOrderDto.setRentalLeaseOrderName("");
        CancelLeaseOrderHisDto cancelLeaseOrderHisDto = SubOrderUtil.createHisDto(cancelLeaseOrderDto, CancelLeaseOrderHisDto.class, 10L);
        
        logger.debug("hisDto: " + cancelLeaseOrderHisDto);
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testSendMessage() {
        WorkOrderDto workOrder = workOrderService.getWorkOrderDtoById(26658L);
        SubOrderUtil.sendMessage(workOrder, 545454L, OrderPushModelDef.REPAIR_ORDER);
//        final String message = "您有一份新的服务订单需要处理，请注意查收";
//        PushMsgBean pmb = new PushMsgBean();
//        pmb.setAlias("18969266372");
//        pmb.setType(PushMsgBean.OUTER_USER);
//        pmb.setMsg(message);
//        pmb.setModel(OrderPushModelDef.HOUSE_LOOKING_ORDER);
//        
//        Map<String, String> param = new HashMap<>();
//        param.put("messag_type", "1");
//        param.put("item_type", "1");
//        param.put("item_title", OrderPushModelDef.HOUSE_LOOKING_ORDER);
////        param.put("item_add", value);
//        param.put("item_id", workOrder.getId().toString());
//        param.put("item_time", DateUtil.date2String(workOrder.getCreatedDate(), DateUtil.DATETIME_FORMAT_1));
//        param.put("comments", workOrder.getSubComments());
//        
//        pmb.setExtrasparam(JSONObject.toJSONString(param));
//        PushManagerUtil.send(pmb);
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testInputCancelLeaseOrder() {
        // 3. 生成退租订单
        CancelLeaseOrderDto cancelLeaseOrderDto = new CancelLeaseOrderDto();
        cancelLeaseOrderDto.setRentalLeaseOrderId(2323L);
        cancelLeaseOrderDto.setCancelLeaseDate(DateUtil.getDBDateTime());
        cancelLeaseOrderDto.setChannel(ChannelDef.CHANNEL_PC);
        cancelLeaseOrderDto.setComments("系统自动生成");
        cancelLeaseOrderDto.setType(CancelLeaseOrderTypeDef.NORMAL_CANCEL_LEASE);
        
        WorkOrderDto workOrderDto = new WorkOrderDto();
        // 系统 创建
        workOrderDto.setCreatedStaffId(-1L);
        workOrderDto.setUserName("靳雅楠");
        workOrderDto.setUserPhone("18651891490");
        cancelLeaseService.inputSubOrderInfoWithTrans(cancelLeaseOrderDto, workOrderDto, null, "");
        
    }
}
