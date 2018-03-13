package com.room1000.suborder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.suborder.utils.SubOrderUtil;

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
public class SubOrderDeployTest {

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testCancelLeaseOrderDeploy() {
        String processName = "CancelLeaseOrderProcess";
        String processPath = "com/room1000/suborder/cancelleaseorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testHouseLookingOrderDeploy() {
        String processName = "HouseLookingOrderProcess";
        String processPath = "com/room1000/suborder/houselookingorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testComplaintOrderDeploy() {
        String processName = "ComplaintOrderProcess";
        String processPath = "com/room1000/suborder/complaintorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testCleaningOrderDeploy() {
        String processName = "CleaningOrderProcess";
        String processPath = "com/room1000/suborder/cleaningorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testLivingProblemOrderDeploy() {
        String processName = "LivingProblemOrderProcess";
        String processPath = "com/room1000/suborder/livingproblemorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testOtherOrderDeploy() {
        String processName = "OtherOrderProcess";
        String processPath = "com/room1000/suborder/otherorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testOwnerRepairOrderDeploy() {
        String processName = "OwnerRepairOrderProcess";
        String processPath = "com/room1000/suborder/ownerrepairorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testRepairOrderDeploy() {
        String processName = "RepairOrderProcess";
        String processPath = "com/room1000/suborder/repairorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testRoutineCleaningOrderDeploy() {
        String processName = "RoutineCleaningOrderProcess";
        String processPath = "com/room1000/suborder/routinecleaningorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testOwnerCancelLeaseOrderDeploy() {
        String processName = "OwnerCancelLeaseOrderProcess";
        String processPath = "com/room1000/suborder/ownercancelleaseorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testPayOrderDeploy() {
        String processName = "PayOrderProcess";
        String processPath = "com/room1000/suborder/payorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     */
    @Test
    public void testAgentApplyOrderDeploy() {
        String processName = "AgentApplyOrderProcess";
        String processPath = "com/room1000/suborder/agentapplyorder/diagrams";
        SubOrderUtil.deployProcess(processName, processPath);
    }
}
