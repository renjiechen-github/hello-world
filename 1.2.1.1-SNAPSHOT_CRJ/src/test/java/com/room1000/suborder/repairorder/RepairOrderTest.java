package com.room1000.suborder.repairorder;


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

import com.room1000.suborder.repairorder.dto.RepairOrderHisDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;

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
@Rollback
@Transactional
public class RepairOrderTest {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(RepairOrderTest.class);
    
    /**
     * repairOrderService
     */
    @Autowired
    private IRepairOrderService repairOrderService;

//    @Test
//    public void testPay() {
//        String code = "20170313160651905";
//        Long staffId = 545597L;
//        Long paidMoney = 100L;
////        repairOrderService.payWithTrans(code, staffId, paidMoney);
//    }
    
    /**
     * 
     * Description: 查询历史
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testQryHis() {
        List<RepairOrderHisDto> repairOrderHisList = repairOrderService.getHis(5318L);
        logger.debug("repairOrderHisList: " + repairOrderHisList);
    }
}
