package com.room1000.suborder.routinecleaningorder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;
import com.room1000.workorder.dto.WorkOrderDto;

/**
 * 
 * Description: 
 *  
 * Created on 2017年3月14日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback
@Transactional
public class RoutineCleaningOrderTest {
    
    /**
     * routineCleaningOrderService
     */
    @Autowired
    private IRoutineCleaningOrderService routineCleaningOrderService;

    /**
     * 
     * Description: 订单录入
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testInputRoutineCleaningOrder() {
        RoutineCleaningOrderDto routineCleaningOrderDto = new RoutineCleaningOrderDto();
        routineCleaningOrderDto.setAppointmentDate(DateUtil.str2DateByFormat("2017-03-15 08:30", "yyyy-MM-dd HH:mm"));
        routineCleaningOrderDto.setRentalLeaseOrderId(6168L);
        routineCleaningOrderDto.setComments("自动生成例行保洁订单");
        
        WorkOrderDto workOrderDto = new WorkOrderDto();
        workOrderDto.setUserName(null);
        workOrderDto.setUserPhone(null);
        
        routineCleaningOrderService.inputSubOrderInfoWithTrans(routineCleaningOrderDto, workOrderDto, null);
    }

}
