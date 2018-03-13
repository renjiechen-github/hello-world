package com.room1000.timedtask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.timedtask.service.IOrderTaskService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月5日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback
@Transactional
public class OrderTaskServiceTest {
    
    /**
     * orderTaskService
     */
    @Autowired
    private IOrderTaskService orderTaskService;
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testAutoSendWait2DealWorkOrderMessage() {
        orderTaskService.autoSendWait2DealWorkOrderMessage();
    }
}
