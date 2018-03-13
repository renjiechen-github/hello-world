package com.room1000.suborder.houselookingorder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.attr.define.AttrTypeDef;
import com.room1000.attr.dto.AttrCatgDto;
import com.room1000.attr.dto.AttrDto;
import com.room1000.attr.service.IAttrService;
import com.room1000.core.activiti.IProcessStart;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessStartImpl;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.utils.DateUtil;
import com.room1000.recommend.dao.RecommendRelationDtoMapper;
import com.room1000.recommend.define.RecommendRelationStateDef;
import com.room1000.recommend.define.RecommendRelationTypeDef;
import com.room1000.recommend.dto.RecommendRelationDto;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderChannelDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderHisDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderValueDtoMapper;
import com.room1000.suborder.houselookingorder.dao.HouseLookingOrderValueHisDtoMapper;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderChannelDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderHisDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderValueDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderValueHisDto;
import com.room1000.suborder.houselookingorder.service.IHouseLookingOrderService;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description:
 * 
 * Created on 2017年3月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("HouseLookingOrderService")
public class HouseLookingOrderServiceImpl implements IHouseLookingOrderService {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(HouseLookingOrderServiceImpl.class);

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * attrService
     */
    @Autowired
    private IAttrService attrService;

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
     * houseLookingOrderChannelDtoMapper
     */
    @Autowired
    private HouseLookingOrderChannelDtoMapper houseLookingOrderChannelDtoMapper;

    /**
     * recommendRelationDtoMapper
     */
    @Autowired
    private RecommendRelationDtoMapper recommendRelationDtoMapper;

    /**
     * houseLookingOrderValueHisDtoMapper
     */
    @Autowired
    private HouseLookingOrderValueHisDtoMapper houseLookingOrderValueHisDtoMapper;

    @Override
    public void inputSubOrderInfoWithTrans(HouseLookingOrderDto houseLookingOrderDto, WorkOrderDto workOrderDto,
        Long currentStaffId, Long recommendId) {

        workOrderDto.setHouseId(houseLookingOrderDto.getHouseId());
        workOrderDto.setAppointmentDate(houseLookingOrderDto.getAppointmentDate());
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

        workOrderService.createWorkOrderWithTrans(workOrderDto);

        houseLookingOrderDto.setWorkOrderId(workOrderDto.getId());
        houseLookingOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
        houseLookingOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());
        houseLookingOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        houseLookingOrderDto.setActualDealerId(currentStaffId);
        houseLookingOrderDto.setUpdatePersonId(currentStaffId);
        houseLookingOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        // 设置处理人
        houseLookingOrderDto.setAssignedDealerId(houseLookingOrderDto.getButlerId());
        AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("HOUSE_LOOKING_ORDER_PROCESS");
        houseLookingOrderDto.setAttrCatgId(attrCatg.getId());
        houseLookingOrderDtoMapper.insert(houseLookingOrderDto);
        this.updateSubOrderWithTrans(houseLookingOrderDto, currentStaffId, "");

        workOrderDto.setType(WorkOrderTypeDef.HOUSE_LOOKING_ORDER);
        if (StringUtils.isEmpty(workOrderDto.getName())) {
            String orderName = workOrderService.generateOrderName(workOrderDto);
            workOrderDto.setName(orderName);
        }
        workOrderDto.setRefId(houseLookingOrderDto.getId());
        workOrderDto.setCode(houseLookingOrderDto.getCode());
        workOrderDto.setSubAssignedDealerId(houseLookingOrderDto.getButlerId());
        workOrderDto.setSubActualDealerId(houseLookingOrderDto.getActualDealerId());
        workOrderDto.setSubComments(houseLookingOrderDto.getComments());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertRecommand(recommendId, workOrderDto);

        startProcess(houseLookingOrderDto, workOrderDto);

    }

    /**
     * 
     * Description: 插入经纪人推荐表
     * 
     * @author jinyanan
     * 
     * @param recommendId recommendId
     * @param workOrderDto workOrderDto
     */
    private void insertRecommand(Long recommendId, WorkOrderDto workOrderDto) {
        if (recommendId != null) {
            RecommendRelationDto recommendRelationDto = new RecommendRelationDto();
            recommendRelationDto.setRecommendId(recommendId);
            recommendRelationDto.setRelationType(RecommendRelationTypeDef.HOUSE_LOOKING);
            recommendRelationDto.setRelationId(workOrderDto.getCode());
            recommendRelationDto.setState(RecommendRelationStateDef.PROCESSING);
            recommendRelationDtoMapper.insert(recommendRelationDto);
        }
    }

    /**
     * 
     * Description: startProcess
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderDto houseLookingOrderDto
     * @param workOrderDto workOrderDto
     */
    private void startProcess(HouseLookingOrderDto houseLookingOrderDto, WorkOrderDto workOrderDto) {
        String processInstanceKey = "HouseLookingOrderProcess";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
        variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
        variables.put(ActivitiVariableDef.HOUSE_LOOKING_ORDER, houseLookingOrderDto);
        IProcessStart start = new ProcessStartImpl();
        start.flowStart(processInstanceKey, variables);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail) {
        HouseLookingOrderDto houseLookingOrderDto = houseLookingOrderDtoMapper.selectDetailById(subOrderId);
        if (!singleDetail) {
            AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(houseLookingOrderDto.getAttrCatgId());
            houseLookingOrderDto.setAttrCatg(attrCatg);
            List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList = this
                .getSubOrderValueDtoListBySubOrderId(subOrderId);
            houseLookingOrderDto.setSubOrderValueList(houseLookingOrderValueDtoList);
        }

        return (T) houseLookingOrderDto;
    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @return List<HouseLookingOrderValueDto>
     */
    private List<HouseLookingOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long id) {
        List<HouseLookingOrderValueDto> houseLookingOrderValueList = houseLookingOrderValueDtoMapper
            .selectBySubOrderId(id);
        for (HouseLookingOrderValueDto houseLookingOrderValueDto : houseLookingOrderValueList) {
            if (AttrTypeDef.PICTURE_UPLOAD.equals(houseLookingOrderValueDto.getAttrType())) {
                houseLookingOrderValueDto
                    .setTextInput(SubOrderUtil.getImagePath(houseLookingOrderValueDto.getTextInput()));
            }
        }
        return houseLookingOrderValueList;
    }

    @Override
    public void assignOrderWithTrans(String code, Long butlerId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId) {
        logger.info("Order is automatically assigned !");
        // this.insertSubOrderValue(code, subOrderValueDtoList);
        // // 修改看房订单的管家id和指定处理人
        // HouseLookingOrderDto houseLookingOrderDto = this.getSubOrderByCode(code);
        // houseLookingOrderDto.setButlerId(butlerId);
        // houseLookingOrderDto.clearAssignedDealer();
        // houseLookingOrderDto.setAssignedDealerId(butlerId);
        // houseLookingOrderDto.setActualDealerId(currentStaffId);
        //
        // this.updateSubOrderWithTrans(houseLookingOrderDto, currentStaffId);
        // WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        // workOrderDto.setSubActualDealerId(houseLookingOrderDto.getActualDealerId());
        // workOrderService.updateWorkOrderWithTrans(workOrderDto);
        // SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void reassignOrderWithTrans(String code, Long butlerId,
        List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList, Long currentStaffId) {

        HouseLookingOrderDto houseLookingOrderDto = this.getSubOrderByCode(code);
        houseLookingOrderDto.setButlerId(butlerId);
        houseLookingOrderDto.clearAssignedDealer();
        houseLookingOrderDto.setAssignedDealerId(butlerId);
        houseLookingOrderDto.setActualDealerId(currentStaffId);
        houseLookingOrderDto.setState(SubOrderStateDef.REASSIGNING);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());

        Long priority = this.updateSubOrderWithTrans(houseLookingOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(houseLookingOrderDto.getWorkOrderId());
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.REASSIGNING);
        workOrderDto.setSubAssignedDealerId(butlerId);
        workOrderDto.setSubActualDealerId(houseLookingOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        // 第二次更新为了插入历史表
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, houseLookingOrderValueDtoList, priority);
        
        SubOrderUtil.sendMessage(workOrderDto, houseLookingOrderDto.getAssignedDealerId(), OrderPushModelDef.HOUSE_LOOKING_ORDER);

    }

    /**
     * 
     * Description: 更新看房单当前处理人
     * 
     * @author jinyanan
     *
     * @param code code
     * @param actualDealerId actualDealerId
     * @return Long
     */
    private Long updateSubOrderActualDealer(String code, Long actualDealerId) {
        HouseLookingOrderDto record = this.getSubOrderByCode(code);
        record.setActualDealerId(actualDealerId);
        Long priority = this.updateSubOrderWithTrans(record, actualDealerId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(actualDealerId);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        return priority;
    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param code code
     * @return HouseLookingOrderDto
     */
    private HouseLookingOrderDto getSubOrderByCode(String code) {
        return houseLookingOrderDtoMapper.selectByCode(code);
    }

    /**
     * 
     * Description: 更新看房的同时，插入历史表
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderDto houseLookingOrderDto
     * @param closeOrder closeOrder
     * @return HouseLookingOrderDto
     */
    private Long updateSubOrderAndAddHis(HouseLookingOrderDto houseLookingOrderDto, boolean closeOrder, String str) {
        IProcessTask task = new ProcessTaskImpl();
        task.putInstanceVariable(houseLookingOrderDto.getWorkOrderId(), ActivitiVariableDef.HOUSE_LOOKING_ORDER,
            houseLookingOrderDto);
        HouseLookingOrderDto oldHouseLookingOrderDto = houseLookingOrderDtoMapper
            .selectByPrimaryKey(houseLookingOrderDto.getId());
        if (oldHouseLookingOrderDto == null) {
            oldHouseLookingOrderDto = houseLookingOrderDtoMapper.selectByCode(houseLookingOrderDto.getCode());
        }
        oldHouseLookingOrderDto.setUpdatePersonId(houseLookingOrderDto.getUpdatePersonId());
        oldHouseLookingOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        if (closeOrder) {
            oldHouseLookingOrderDto.setState(SubOrderStateDef.CLOSED);
            oldHouseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());
        }
        houseLookingOrderDtoMapper.updateByPrimaryKey(houseLookingOrderDto);
        // HouseLookingOrderHisDto hisDto = this.createHisDto(oldHouseLookingOrderDto);
        HouseLookingOrderHisDto hisDto = SubOrderUtil.createHisDto(oldHouseLookingOrderDto,
            HouseLookingOrderHisDto.class, getPriority(oldHouseLookingOrderDto.getId()));
        if (BooleanFlagDef.BOOLEAN_TRUE.equals(houseLookingOrderDto.getChangeFlag())) {
            hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
            hisDto.setStateDate(DateUtil.getDBDateTime());
            hisDto.setUpdateDate(DateUtil.getDBDateTime());
        }
        houseLookingOrderHisDtoMapper.insert(hisDto);
        return hisDto.getPriority();
    }

    /**
     * 
     * Description: 创建历史dto
     * 
     * @author jinyanan
     *
     * @param oldHouseLookingOrderDto oldHouseLookingOrderDto
     * @return HouseLookingOrderHisDto
     */
    // private HouseLookingOrderHisDto createHisDto(HouseLookingOrderDto oldHouseLookingOrderDto) {
    // HouseLookingOrderHisDto houseLookingOrderHisDto = new HouseLookingOrderHisDto();
    // Long maxPriority = houseLookingOrderHisDtoMapper.selectMaxPriority(oldHouseLookingOrderDto.getId());
    // if (maxPriority == null) {
    // maxPriority = 0L;
    // }
    // SubOrderUtil.createdHisDto(oldHouseLookingOrderDto, houseLookingOrderHisDto, maxPriority + 1);
    // return houseLookingOrderHisDto;
    // }

    /**
     * 
     * Description: 获取本次历史对应的priority
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return Long
     */
    private Long getPriority(Long subOrderId) {
        Long priority = houseLookingOrderHisDtoMapper.selectMaxPriority(subOrderId);
        if (priority == null) {
            return 1L;
        }
        else {
            return priority + 1;
        }
    }

    /**
     * 
     * Description: 插入HouseLookingOrderValue
     * 
     * @author jinyanan
     *
     * @param code code
     * @param subOrderValueDtoList subOrderValueDtoList
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long priority) {
        HouseLookingOrderDto houseLookingOrderDto = houseLookingOrderDtoMapper.selectByCode(code);
        for (SubOrderValueDto subOrderValueDto : subOrderValueDtoList) {
            HouseLookingOrderValueDto houseLookingOrderValueDto = (HouseLookingOrderValueDto) subOrderValueDto;
            houseLookingOrderValueDto.setSubOrderId(houseLookingOrderDto.getId());
            doInsertSubOrderValue(houseLookingOrderValueDto);
            addSubOrderValueHis(houseLookingOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderValueDto houseLookingOrderValueDto
     */
    private void doInsertSubOrderValue(HouseLookingOrderValueDto houseLookingOrderValueDto) {
        HouseLookingOrderValueDto existHouseLookingOrderValueDto = houseLookingOrderValueDtoMapper
            .selectByAttrPath(houseLookingOrderValueDto);
        if (existHouseLookingOrderValueDto != null) {
            houseLookingOrderValueDtoMapper.deleteByPrimaryKey(existHouseLookingOrderValueDto.getId());
        }
        houseLookingOrderValueDtoMapper.insert(houseLookingOrderValueDto);

    }

    /**
     * 
     * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderValueDto houseLookingOrderValueDto
     * @param priority priority
     */
    private void addSubOrderValueHis(HouseLookingOrderValueDto houseLookingOrderValueDto, Long priority) {
        HouseLookingOrderValueHisDto houseLookingOrderValueHisDto = SubOrderUtil.createHisDto(houseLookingOrderValueDto,
            HouseLookingOrderValueHisDto.class, priority);
        houseLookingOrderValueHisDtoMapper.insert(houseLookingOrderValueHisDto);
    }

    @Override
    public void takeOrderWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, houseLookingOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void processOrderWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId, String realPath, Boolean submitFlag) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, houseLookingOrderValueDtoList, realPath, priority);
        if (submitFlag) {
            SubOrderUtil.dispatcherOrder(code);
        }

    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @param houseLookingOrderValueDtoList houseLookingOrderValueDtoList
     * @param realPath realPath
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        String realPath, Long priority) {

        HouseLookingOrderDto houseLookingOrderDto = houseLookingOrderDtoMapper.selectByCode(code);
        for (HouseLookingOrderValueDto houseLookingOrderValueDto : houseLookingOrderValueDtoList) {
            this.checkImage(houseLookingOrderValueDto, houseLookingOrderDto.getWorkOrderId(), realPath);
            houseLookingOrderValueDto.setSubOrderId(houseLookingOrderDto.getId());
            doInsertSubOrderValue(houseLookingOrderValueDto);
            addSubOrderValueHis(houseLookingOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderValueDto houseLookingOrderValueDto
     * @param workOrderId workOrderId
     * @param realPath realPath
     */
    private void checkImage(HouseLookingOrderValueDto houseLookingOrderValueDto, Long workOrderId, String realPath) {
        AttrDto attr = attrService.getAttrById(houseLookingOrderValueDto.getAttrId());
        if (AttrTypeDef.PICTURE_UPLOAD.equals(attr.getType())) {
            houseLookingOrderValueDto.setTextInput(
                SubOrderUtil.uploadImage(houseLookingOrderValueDto.getTextInput(), workOrderId, realPath));
        }

    }

    @Override
    public void reassignOrderInStaffReviewWithTrans(String code, Long butlerId,
        List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList, Long currentStaffId) {
        HouseLookingOrderDto houseLookingOrderDto = this.getSubOrderByCode(code);
        houseLookingOrderDto.setButlerId(butlerId);
        houseLookingOrderDto.clearAssignedDealer();
        houseLookingOrderDto.setAssignedDealerId(butlerId);
        houseLookingOrderDto.setActualDealerId(currentStaffId);

        Long priority = this.updateSubOrderWithTrans(houseLookingOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(houseLookingOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, houseLookingOrderValueDtoList, priority);

        SubOrderUtil.dispatcherOrder(code);

    }

    @Override
    public void staffTaceWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId) {
        HouseLookingOrderDto houseLookingOrderDto = this.getSubOrderByCode(code);
        houseLookingOrderDto.setState(SubOrderStateDef.WAIT_2_TACE);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());
        houseLookingOrderDto.setActualDealerId(currentStaffId);
        Long priority = this.updateSubOrderWithTrans(houseLookingOrderDto, currentStaffId, "");

        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoById(houseLookingOrderDto.getWorkOrderId());
        workOrderDto.setSubOrderState(SubOrderStateDef.WAIT_2_TACE);
        workOrderDto.setSubActualDealerId(houseLookingOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        this.insertSubOrderValue(code, houseLookingOrderValueDtoList, priority);

    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str) {
        return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str) {
        HouseLookingOrderDto houseLookingOrderDto = (HouseLookingOrderDto) subOrderDto;
        houseLookingOrderDto.setUpdatePersonId(updatePersonId);
        houseLookingOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        return this.updateSubOrderAndAddHis(houseLookingOrderDto, closeOrder, str);
    }

    @Override
    public void passInStaffReviewWithTrans(String code, List<HouseLookingOrderValueDto> houseLookingOrderValueDtoList,
        Long currentStaffId) {
        Long priority = this.updateSubOrderActualDealer(code, currentStaffId);
        this.insertSubOrderValue(code, houseLookingOrderValueDtoList, priority);
        SubOrderUtil.dispatcherOrder(code);
    }

    @Override
    public HouseLookingOrderValueDto selectByAttrPath(HouseLookingOrderValueDto houseLookingOrderValueDto) {
        return houseLookingOrderValueDtoMapper.selectByAttrPath(houseLookingOrderValueDto);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getHis(Long subOrderId) {
        return (List<T>) houseLookingOrderHisDtoMapper.selectById(subOrderId);
    }

    @Override
    public List<HouseLookingOrderChannelDto> getHouseLookingOrderChannel() {
        return houseLookingOrderChannelDtoMapper.selectAll();
    }

    @Override
    public String getWorkOrderType() {
        return WorkOrderTypeDef.HOUSE_LOOKING_ORDER;
    }

  	@Override
  	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
  	{
  		HouseLookingOrderDto houseLookingOrderDto = new HouseLookingOrderDto();
  		houseLookingOrderDto.setId(subOrderId);
  		houseLookingOrderDto.setAssignedDealerId(subAssignedDealerId);
  		return houseLookingOrderDtoMapper.updateByPrimaryKeySelective(houseLookingOrderDto);
  	}
    
}
