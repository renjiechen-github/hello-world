package com.room1000.suborder.payorder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.agreement.dao.AgreementDtoMapper;
import com.room1000.agreement.dto.AgreementDto;
import com.room1000.attr.define.AttrTypeDef;
import com.room1000.attr.dto.AttrCatgDto;
import com.room1000.attr.service.IAttrService;
import com.room1000.core.activiti.IProcessStart;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessStartImpl;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.utils.DateUtil;
import com.room1000.recommend.dao.RecommendInfoDtoMapper;
import com.room1000.recommend.dto.RecommendInfoDto;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.payorder.dao.PayOrderDtoMapper;
import com.room1000.suborder.payorder.dao.PayOrderHisDtoMapper;
import com.room1000.suborder.payorder.dao.PayOrderValueDtoMapper;
import com.room1000.suborder.payorder.dao.PayOrderValueHisDtoMapper;
import com.room1000.suborder.payorder.define.PayOrderTypeDef;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.dto.PayOrderHisDto;
import com.room1000.suborder.payorder.dto.PayOrderValueDto;
import com.room1000.suborder.payorder.dto.PayOrderValueHisDto;
import com.room1000.suborder.payorder.service.IPayOrderService;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 支付订单Service
 * 
 * Created on 2017年5月23日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service
public class PayOrderServiceImpl implements IPayOrderService {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(PayOrderServiceImpl.class);

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
     * payOrderDtoMapper
     */
    @Autowired
    private PayOrderDtoMapper payOrderDtoMapper;

    /**
     * payOrderHisDtoMapper
     */
    @Autowired
    private PayOrderHisDtoMapper payOrderHisDtoMapper;

    /**
     * payOrderValueDtoMapper
     */
    @Autowired
    private PayOrderValueDtoMapper payOrderValueDtoMapper;

    /**
     * payOrderValueHisDtoMapper
     */
    @Autowired
    private PayOrderValueHisDtoMapper payOrderValueHisDtoMapper;
    
    /**
     * recommendInfoDtoMapper
     */
    @Autowired
    private RecommendInfoDtoMapper recommendInfoDtoMapper;
    
    /**
     * agreementDtoMapper
     */
    @Autowired
    private AgreementDtoMapper agreementDtoMapper;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrderDetailById(Long subOrderId, Boolean singleDetail) {
        PayOrderDto payOrderDto = payOrderDtoMapper.selectDetailById(subOrderId);
        getRefDto(payOrderDto);
        if (!singleDetail) {
            AttrCatgDto attrCatg = attrService.getAttrCatgIncludeChildrenById(payOrderDto.getAttrCatgId());
            payOrderDto.setAttrCatg(attrCatg);
            List<PayOrderValueDto> otherOrderValueDtoList = this.getSubOrderValueDtoListBySubOrderId(subOrderId);
            payOrderDto.setSubOrderValueList(otherOrderValueDtoList);
        }
        return (T) payOrderDto;
    }

    /**
     * 
     * Description: 查询关联表
     * 
     * @author jinyanan
     *
     * @param payOrderDto payOrderDto
     */
    private void getRefDto(PayOrderDto payOrderDto) {
        if (PayOrderTypeDef.RECOMMEND_PAY.equals(payOrderDto.getType())) {
            RecommendInfoDto recommendInfoDto = recommendInfoDtoMapper.selectByPrimaryKey(payOrderDto.getPayRefId());
            AgreementDto agreementDto = agreementDtoMapper.selectByPrimaryKey(recommendInfoDto.getAgreementId());
            recommendInfoDto.setAgreement(agreementDto);
            payOrderDto.setRecommendInfo(recommendInfoDto);
        }
    }

    /**
     * 
     * Description: 查询SubOrderValueDtoList
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<PayOrderValueDto>
     */
    private List<PayOrderValueDto> getSubOrderValueDtoListBySubOrderId(Long subOrderId) {
        List<PayOrderValueDto> subValueList = payOrderValueDtoMapper.selectBySubOrderId(subOrderId);
        for (PayOrderValueDto subOrderValueDto : subValueList) {
            if (AttrTypeDef.PICTURE_UPLOAD.equals(subOrderValueDto.getAttrType())) {
                subOrderValueDto.setTextInput(SubOrderUtil.getImagePath(subOrderValueDto.getTextInput()));
            }
        }
        return subValueList;
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, String str) {
        return this.updateSubOrderWithTrans(subOrderDto, updatePersonId, false, str);
    }

    @Override
    public <T> Long updateSubOrderWithTrans(T subOrderDto, Long updatePersonId, boolean closeOrder, String str) {
        PayOrderDto payOrderDto = (PayOrderDto) subOrderDto;
        payOrderDto.setUpdatePersonId(updatePersonId);
        payOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        return this.updateSubOrderAndAddHis(payOrderDto, closeOrder, str);
    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param payOrderDto payOrderDto
     * @param closeOrder closeOrder
     * @return Long
     */
    private Long updateSubOrderAndAddHis(PayOrderDto payOrderDto, boolean closeOrder, String str) {
        IProcessTask task = new ProcessTaskImpl();
        task.putInstanceVariable(payOrderDto.getWorkOrderId(), ActivitiVariableDef.PAY_ORDER, payOrderDto);
        PayOrderDto oldPayOrderDto = payOrderDtoMapper.selectByPrimaryKey(payOrderDto.getId());
        if (oldPayOrderDto == null) {
            oldPayOrderDto = payOrderDtoMapper.selectByCode(payOrderDto.getCode());
        }
        oldPayOrderDto.setUpdatePersonId(payOrderDto.getUpdatePersonId());
        oldPayOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        if (closeOrder) {
            oldPayOrderDto.setState(SubOrderStateDef.CLOSED);
            oldPayOrderDto.setStateDate(DateUtil.getDBDateTime());
        }
        payOrderDtoMapper.updateByPrimaryKey(payOrderDto);
        PayOrderHisDto hisDto = SubOrderUtil.createHisDto(oldPayOrderDto, PayOrderHisDto.class,
            getPriority(oldPayOrderDto.getId()));
        if (BooleanFlagDef.BOOLEAN_TRUE.equals(payOrderDto.getChangeFlag())) {
            hisDto.setState(SubOrderStateDef.ORDER_CHANGE);
            hisDto.setStateDate(DateUtil.getDBDateTime());
            hisDto.setUpdateDate(DateUtil.getDBDateTime());
        }
        payOrderHisDtoMapper.insert(hisDto);
        return hisDto.getPriority();
    }

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
        Long priority = payOrderHisDtoMapper.selectMaxPriority(subOrderId);
        if (priority == null) {
            return 1L;
        }
        else {
            return priority + 1;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getHis(Long subOrderId) {
        return (List<T>) payOrderHisDtoMapper.selectById(subOrderId);
    }

    @Override
    public String getWorkOrderType() {
        return WorkOrderTypeDef.PAY_ORDER;
    }

    @Override
    public void assignOrderWithTrans(String code, Long staffId, List<? extends SubOrderValueDto> subOrderValueDtoList,
        Long currentStaffId) {
        logger.info("支付订单没有指派订单环节");
    }

    @Override
    public void processOrderWithTrans(String code, List<PayOrderValueDto> subOrderValueList, Long currentStaffId) {
        PayOrderDto payOrderDto = this.getSubOrderByCode(code);
        payOrderDto.setStaffId(currentStaffId);
        
        payOrderDto.clearAssignedDealer();
        payOrderDto.setActualDealerId(currentStaffId);
        
        Long priority = this.updateSubOrderWithTrans(payOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(payOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        
        this.insertSubOrderValue(code, subOrderValueList, priority);
        SubOrderUtil.dispatcherOrder(code);
    }

    @Override
    public void orderAuditWithTrans(String code, List<PayOrderValueDto> subOrderValueList, Long currentStaffId, Boolean submitFlag) {
        PayOrderDto payOrderDto = this.getSubOrderByCode(code);
        
        payOrderDto.clearAssignedDealer();
        payOrderDto.setActualDealerId(currentStaffId);
        
        Long priority = this.updateSubOrderWithTrans(payOrderDto, currentStaffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(payOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        
        this.insertSubOrderValue(code, subOrderValueList, priority);
        if (submitFlag) {
            SubOrderUtil.dispatcherOrder(code);
        }
    }

    /**
     * 
     * Description: 插入subOrderValue
     * 
     * @author jinyanan
     *
     * @param code code
     * @param subOrderValueList subOrderValueList
     * @param priority priority
     */
    private void insertSubOrderValue(String code, List<? extends SubOrderValueDto> subOrderValueList,
        Long priority) {
        PayOrderDto payOrderDto = payOrderDtoMapper.selectByCode(code);
        for (SubOrderValueDto subOrderValueDto : subOrderValueList) {
            PayOrderValueDto payOrderValueDto = (PayOrderValueDto) subOrderValueDto;
            payOrderValueDto.setSubOrderId(payOrderDto.getId());
            doInsertSubOrderValue(payOrderValueDto);
            addSubOrderValueHis(payOrderValueDto, priority);
        }

    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param payOrderValueDto payOrderValueDto
     */
    private void doInsertSubOrderValue(PayOrderValueDto payOrderValueDto) {
        PayOrderValueDto existPayOrderValueDto = payOrderValueDtoMapper.selectByAttrPath(payOrderValueDto);
        if (existPayOrderValueDto != null) {
            payOrderValueDtoMapper.deleteByPrimaryKey(existPayOrderValueDto.getId());
        }
        payOrderValueDtoMapper.insert(payOrderValueDto);

    }

    /**
     * 
     * Description: 校验subOrderValue是否存在，存在则插入历史，并删除
     * 
     * @author jinyanan
     *
     * @param payOrderValueDto payOrderValueDto
     * @param priority priority
     */
    private void addSubOrderValueHis(PayOrderValueDto payOrderValueDto, Long priority) {
        PayOrderValueHisDto payOrderValueHisDto = SubOrderUtil.createHisDto(payOrderValueDto, PayOrderValueHisDto.class,
            priority);
        payOrderValueHisDtoMapper.insert(payOrderValueHisDto);
    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param code code
     * @return PayOrderDto
     */
    private PayOrderDto getSubOrderByCode(String code) {
        return payOrderDtoMapper.selectByCode(code);
    }

    @Override
    public void payWithTrans(String code, Long staffId, Long paidMoney) {
        PayOrderDto payOrderDto = this.getSubOrderByCode(code);
        payOrderDto.setActualDealerId(staffId);
        this.updateSubOrderWithTrans(payOrderDto, staffId, "");
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderDtoByCode(code);
        workOrderDto.setSubActualDealerId(payOrderDto.getActualDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        SubOrderUtil.dispatcherOrder(code);
    }

    @Override
    public void inputSubOrderInfoWithTrans(PayOrderDto payOrderDto, WorkOrderDto workOrderDto, Long staffId) {
        workOrderDto.setAppointmentDate(payOrderDto.getAppointmentDate());
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);

        workOrderService.createWorkOrderWithTrans(workOrderDto);

        payOrderDto.setWorkOrderId(workOrderDto.getId());
        payOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
        payOrderDto.setState(SubOrderStateDef.ORDER_INPUT);
        payOrderDto.setStateDate(DateUtil.getDBDateTime());
        payOrderDto.setCreatedDate(DateUtil.getDBDateTime());
        payOrderDto.setActualDealerId(staffId);
        payOrderDto.setUpdatePersonId(staffId);
        payOrderDto.setUpdateDate(DateUtil.getDBDateTime());
        AttrCatgDto attrCatg = attrService.getSingleAttrCatgDtoByCode("PAY_ORDER_PROCESS");
        payOrderDto.setAttrCatgId(attrCatg.getId());
        payOrderDtoMapper.insert(payOrderDto);
        this.updateSubOrderWithTrans(payOrderDto, staffId, "");

        workOrderDto.setType(WorkOrderTypeDef.PAY_ORDER);
        if (StringUtils.isEmpty(workOrderDto.getName())) {
            String orderName = workOrderService.generateOrderName(workOrderDto);
            workOrderDto.setName(orderName);
        }
        workOrderDto.setRefId(payOrderDto.getId());
        workOrderDto.setCode(payOrderDto.getCode());
        workOrderDto.setSubActualDealerId(payOrderDto.getActualDealerId());
        workOrderDto.setSubComments(payOrderDto.getComments());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        startProcess(payOrderDto, workOrderDto);
    }

    /**
     * 
     * Description: startProcess
     * 
     * @author jinyanan
     *
     * @param payOrderDto payOrderDto
     * @param workOrderDto workOrderDto
     */
    private void startProcess(PayOrderDto payOrderDto, WorkOrderDto workOrderDto) {
        String processInstanceKey = "PayOrderProcess";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiVariableDef.WORK_ORDER, workOrderDto);
        variables.put(ActivitiVariableDef.WORK_ORDER_ID, workOrderDto.getId());
        variables.put(ActivitiVariableDef.PAY_ORDER, payOrderDto);
        IProcessStart start = new ProcessStartImpl();
        start.flowStart(processInstanceKey, variables);
    }

    @Override
    public PayOrderValueDto selectByAttrPath(PayOrderValueDto payOrderValueDto) {
        return payOrderValueDtoMapper.selectByAttrPath(payOrderValueDto);
    }
    
  	@Override
  	public int updateSubOrderAssignedDealerId(long subOrderId, long subAssignedDealerId)
  	{
  		PayOrderDto payOrderDto = new PayOrderDto();
  		payOrderDto.setId(subOrderId);
  		payOrderDto.setAssignedDealerId(subAssignedDealerId);
  		return payOrderDtoMapper.updateByPrimaryKeySelective(payOrderDto);
  	}
}
