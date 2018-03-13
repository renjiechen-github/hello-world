package com.room1000.suborder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderDtoMapper;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderHisDtoMapper;
import com.room1000.suborder.cancelleaseorder.dao.CancelLeaseOrderValueDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningOrderDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningOrderHisDtoMapper;
import com.room1000.suborder.cleaningorder.dao.CleaningOrderValueDtoMapper;
import com.room1000.suborder.complaintorder.dao.ComplaintOrderDtoMapper;
import com.room1000.suborder.complaintorder.dao.ComplaintOrderHisDtoMapper;
import com.room1000.suborder.complaintorder.dao.ComplaintOrderValueDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderHisDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderValueDtoMapper;
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderDtoMapper;
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderHisDtoMapper;
import com.room1000.suborder.livingproblemorder.dao.LivingProblemOrderValueDtoMapper;
import com.room1000.suborder.otherorder.dao.OtherOrderDtoMapper;
import com.room1000.suborder.otherorder.dao.OtherOrderHisDtoMapper;
import com.room1000.suborder.otherorder.dao.OtherOrderValueDtoMapper;
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderDtoMapper;
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderHisDtoMapper;
import com.room1000.suborder.ownerrepairorder.dao.OwnerRepairOrderValueDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairOrderDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairOrderHisDtoMapper;
import com.room1000.suborder.repairorder.dao.RepairOrderValueDtoMapper;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderDtoMapper;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderHisDtoMapper;
import com.room1000.suborder.routinecleaningorder.dao.RoutineCleaningOrderValueDtoMapper;
import com.room1000.workorder.dao.OrderCommentaryDtoMapper;
import com.room1000.workorder.dao.WorkOrderDtoMapper;
import com.room1000.workorder.dao.WorkOrderHisDtoMapper;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.WorkOrderDto;

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
public class DeleteOrderTest {
    
    /**
     * workOrderDtoMapper
     */
    @Autowired
    private WorkOrderDtoMapper workOrderDtoMapper;

    /**
     * workOrderHisDtoMapper
     */
    @Autowired
    private WorkOrderHisDtoMapper workOrderHisDtoMapper;

    /**
     * orderCommentaryDtoMapper
     */
    @Autowired
    private OrderCommentaryDtoMapper orderCommentaryDtoMapper;

    /**
     * cancelLeaseOrderDtoMapper
     */
    @Autowired
    private CancelLeaseOrderDtoMapper cancelLeaseOrderDtoMapper;

    /**
     * cancelLeaseOrderValueDtoMapper
     */
    @Autowired
    private CancelLeaseOrderValueDtoMapper cancelLeaseOrderValueDtoMapper;

    /**
     * cancelLeaseOrderHisDtoMapper
     */
    @Autowired
    private CancelLeaseOrderHisDtoMapper cancelLeaseOrderHisDtoMapper;
    
    /**
     * houseLookingOrderDtoMapper
     */
    @Autowired
    private HouseLookingOrderDtoMapper houseLookingOrderDtoMapper;

    /**
     * houseLookingOrderValueDtoMapper
     */
    @Autowired
    private HouseLookingOrderValueDtoMapper houseLookingOrderValueDtoMapper;

    /**
     * houseLookingOrderHisDtoMapper
     */
    @Autowired
    private HouseLookingOrderHisDtoMapper houseLookingOrderHisDtoMapper;

    /**
     * complaintOrderDtoMapper
     */
    @Autowired
    private ComplaintOrderDtoMapper complaintOrderDtoMapper;

    /**
     * complaintOrderValueDtoMapper
     */
    @Autowired
    private ComplaintOrderValueDtoMapper complaintOrderValueDtoMapper;

    /**
     * complaintOrderHisDtoMapper
     */
    @Autowired
    private ComplaintOrderHisDtoMapper complaintOrderHisDtoMapper;

    /**
     * cleaningOrderDtoMapper
     */
    @Autowired
    private CleaningOrderDtoMapper cleaningOrderDtoMapper;

    /**
     * cleaningOrderValueDtoMapper
     */
    @Autowired
    private CleaningOrderValueDtoMapper cleaningOrderValueDtoMapper;

    /**
     * cleaningOrderHisDtoMapper
     */
    @Autowired
    private CleaningOrderHisDtoMapper cleaningOrderHisDtoMapper;

    /**
     * livingProblemOrderDtoMapper
     */
    @Autowired
    private LivingProblemOrderDtoMapper livingProblemOrderDtoMapper;

    /**
     * livingProblemOrderValueDtoMapper
     */
    @Autowired
    private LivingProblemOrderValueDtoMapper livingProblemOrderValueDtoMapper;

    /**
     * livingProblemOrderHisDtoMapper
     */
    @Autowired
    private LivingProblemOrderHisDtoMapper livingProblemOrderHisDtoMapper;

    /**
     * otherOrderDtoMapper
     */
    @Autowired
    private OtherOrderDtoMapper otherOrderDtoMapper;

    /**
     * otherOrderValueDtoMapper
     */
    @Autowired
    private OtherOrderValueDtoMapper otherOrderValueDtoMapper;

    /**
     * otherOrderHisDtoMapper
     */
    @Autowired
    private OtherOrderHisDtoMapper otherOrderHisDtoMapper;

    /**
     * ownerRepairOrderDtoMapper
     */
    @Autowired
    private OwnerRepairOrderDtoMapper ownerRepairOrderDtoMapper;

    /**
     * ownerRepairOrderValueDtoMapper
     */
    @Autowired
    private OwnerRepairOrderValueDtoMapper ownerRepairOrderValueDtoMapper;

    /**
     * ownerRepairOrderHisDtoMapper
     */
    @Autowired
    private OwnerRepairOrderHisDtoMapper ownerRepairOrderHisDtoMapper;

    /**
     * repairOrderDtoMapper
     */
    @Autowired
    private RepairOrderDtoMapper repairOrderDtoMapper;

    /**
     * repairOrderValueDtoMapper
     */
    @Autowired
    private RepairOrderValueDtoMapper repairOrderValueDtoMapper;

    /**
     * repairOrderHisDtoMapper
     */
    @Autowired
    private RepairOrderHisDtoMapper repairOrderHisDtoMapper;

    /**
     * routineCleaningOrderDtoMapper
     */
    @Autowired
    private RoutineCleaningOrderDtoMapper routineCleaningOrderDtoMapper;

    /**
     * routineCleaningOrderValueDtoMapper
     */
    @Autowired
    private RoutineCleaningOrderValueDtoMapper routineCleaningOrderValueDtoMapper;

    /**
     * routineCleaningOrderHisDtoMapper
     */
    @Autowired
    private RoutineCleaningOrderHisDtoMapper routineCleaningOrderHisDtoMapper;

    /**
     * 
     * Description: 删除订单
     * 
     * @author jinyanan
     *
     */
    @Test
    public void deleteOrder() {
//        QryWorkOrderPagerListRequest req = new QryWorkOrderPagerListRequest();
//        req.setCreatedUserId(5183L);
//        req.setCreatedStaffId(545484L);
//        req.setUserName("测试");
//        List<WorkOrderDto> workOrderList = workOrderDtoMapper.selectByReqCond(req);
//        
        List<String> codeList = new ArrayList<>();
//        
//        for (WorkOrderDto workOrder : workOrderList) {
//            codeList.add(workOrder.getCode());
//        }
        
        codeList.add("20170505090228001");
        codeList.add("20170504170010597");
//        codeList.add("20170408113828310");
//        codeList.add("20170408114424457");
//        codeList.add("20170408121413148");
//        codeList.add("20170408124745749");
//        codeList.add("20170408131723361");
//        codeList.add("20170408133304411");
        
        for (String code : codeList) {
            WorkOrderDto workOrderDto = workOrderDtoMapper.selectByCode(code);
            OrderCommentaryDto param = new OrderCommentaryDto();
            param.setWorkOrderId(workOrderDto.getId());
            orderCommentaryDtoMapper.deleteByCond(param);
            switch (workOrderDto.getType()) {
                case WorkOrderTypeDef.CANCEL_LEASE_ORDER:
                    cancelLeaseOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    cancelLeaseOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    cancelLeaseOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.HOUSE_LOOKING_ORDER:
                    houseLookingOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    houseLookingOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    houseLookingOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.COMPLAINT_ORDER:
                    complaintOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    complaintOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    complaintOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.CLEANING_ORDER:
                    cleaningOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    cleaningOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    cleaningOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.LIVING_PROBLEM_ORDER:
                    livingProblemOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    livingProblemOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    livingProblemOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.OTHER_ORDER:
                    otherOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    otherOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    otherOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.OWNER_REPAIR_ORDER:
                    ownerRepairOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    ownerRepairOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    ownerRepairOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.REPAIR_ORDER:
                    repairOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    repairOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    repairOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                    
                case WorkOrderTypeDef.ROUTINE_CLEANING_ORDER:
                    routineCleaningOrderValueDtoMapper.deleteBySubOrderId(workOrderDto.getRefId());
                    routineCleaningOrderHisDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId(), null);
                    routineCleaningOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getRefId());
                    workOrderDtoMapper.deleteByPrimaryKey(workOrderDto.getId());
                    workOrderHisDtoMapper.deleteByPrimaryKey(null, workOrderDto.getId());
                    break;
                default:
                    break;
            }
        }
    }
}
