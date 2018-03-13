package com.room1000.suborder.houselookingorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.recommend.dao.RecommendRelationDtoMapper;
import com.room1000.recommend.define.RecommendRelationStateDef;
import com.room1000.recommend.define.RecommendRelationTypeDef;
import com.room1000.recommend.dto.RecommendRelationDto;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.service.IHouseLookingOrderService;
import com.room1000.suborder.node.ICompleteOrderNodeProcess;
import com.room1000.workorder.define.WorkOrderStateDef;
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
@Component("HouseLookingOrder.CompleteOrderNode")
public class CompleteOrderNode extends SubOrderTypeNode implements ICompleteOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CompleteOrderNode.class);

    /**
     * houseLookingOrderService
     */
    @Autowired
    private IHouseLookingOrderService houseLookingOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * recommendRelationDtoMapper
     */
    @Autowired
    private RecommendRelationDtoMapper recommendRelationDtoMapper;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("订单关闭");

        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        HouseLookingOrderDto houseLookingOrderDto = (HouseLookingOrderDto) variables
            .get(ActivitiVariableDef.HOUSE_LOOKING_ORDER);

        houseLookingOrderDto.clearAssignedDealer();
        houseLookingOrderDto.setState(SubOrderStateDef.CLOSED);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());
        houseLookingOrderService.updateSubOrderWithTrans(houseLookingOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setState(WorkOrderStateDef.DONE);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        completeRecommend(workOrderDto.getCode());
    }

    /**
     * 
     * Description: 结束经纪人推荐订单
     * 
     * @author jinyanan
     *
     * @param code code
     */
    private void completeRecommend(String code) {
        RecommendRelationDto recommendRelationDto = recommendRelationDtoMapper
            .selectByPrimaryKey(RecommendRelationTypeDef.HOUSE_LOOKING, code);
        if (recommendRelationDto == null) {
            return;
        }
        recommendRelationDto.setState(RecommendRelationStateDef.FINISHED);
        recommendRelationDtoMapper.updateByPrimaryKey(recommendRelationDto);
    }

}
