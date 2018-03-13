package com.room1000.suborder.payorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.room1000.attr.dto.AttrCatgDto;
import com.room1000.recommend.dto.RecommendInfoDto;


/**
 *
 * Description: pay_order 实体
 *
 * Created on 2017年05月23日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Table(name = "pay_order")
public class PayOrderDto implements Serializable {
    /**
     * 主键
     * @mbg.generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 编码
     * @mbg.generated
     */
    private String code;

    /**
     * 工单主键
     * @mbg.generated
     */
    @Column(name = "work_order_id")
    private Long workOrderId;

    /**
     * 支付订单类型
     * @mbg.generated
     */
    private String type;

    /**
     * 关联支付信息表主键
     * @mbg.generated
     */
    @Column(name = "pay_ref_id")
    private Long payRefId;

    /**
     * 创建时间
     * @mbg.generated
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 处理员工主键
     * @mbg.generated
     */
    @Column(name = "staff_id")
    private Long staffId;

    /**
     * 预约时间
     * @mbg.generated
     */
    @Column(name = "appointment_date")
    private Date appointmentDate;

    /**
     * 当前环节实际处理责任人
     * @mbg.generated
     */
    @Column(name = "actual_dealer_id")
    private Long actualDealerId;

    /**
     * 当前环节指定责任人
     * @mbg.generated
     */
    @Column(name = "assigned_dealer_id")
    private Long assignedDealerId;

    /**
     * 当前环节指定处理角色
     * @mbg.generated
     */
    @Column(name = "assigned_dealer_role_id")
    private Long assignedDealerRoleId;

    /**
     * 属性目录主键
     * @mbg.generated
     */
    @Column(name = "attr_catg_id")
    private Long attrCatgId;

    /**
     * 状态
     * @mbg.generated
     */
    private String state;

    /**
     * 状态变更时间
     * @mbg.generated
     */
    @Column(name = "state_date")
    private Date stateDate;

    /**
     * 修改人主键
     * @mbg.generated
     */
    @Column(name = "update_person_id")
    private Long updatePersonId;

    /**
     * 修改时间
     * @mbg.generated
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 备注
     * @mbg.generated
     */
    private String comments;
    
    /**
     * attrCatg 属性目录
     */
    @Transient
    private AttrCatgDto attrCatg;
    
    /**
     * payOrderValueList
     */
    @Transient
    private List<PayOrderValueDto> subOrderValueList;
    
    /**
     * changeFlag
     */
    @Transient
    private String changeFlag;

    /**
     * typeName
     */
    @Transient
    private String typeName;

    /**
     * staffName
     */
    @Transient
    private String staffName;

    /**
     * actualDealerName
     */
    @Transient
    private String actualDealerName;

    /**
     * assignedDealerName
     */
    @Transient
    private String assignedDealerName;

    /**
     * assignedDealerRoleName
     */
    @Transient
    private String assignedDealerRoleName;

    /**
     * stateName
     */
    @Transient
    private String stateName;
    
    /**
     * updatePersonName
     */
    @Transient
    private String updatePersonName;
    
    /**
     * 关联订单详情
     */
    @Transient
    private RecommendInfoDto recommendInfo;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPayRefId() {
        return payRefId;
    }

    public void setPayRefId(Long payRefId) {
        this.payRefId = payRefId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getActualDealerId() {
        return actualDealerId;
    }

    public void setActualDealerId(Long actualDealerId) {
        this.actualDealerId = actualDealerId;
    }

    public Long getAssignedDealerId() {
        return assignedDealerId;
    }

    public void setAssignedDealerId(Long assignedDealerId) {
        this.assignedDealerId = assignedDealerId;
    }

    public Long getAssignedDealerRoleId() {
        return assignedDealerRoleId;
    }

    public void setAssignedDealerRoleId(Long assignedDealerRoleId) {
        this.assignedDealerRoleId = assignedDealerRoleId;
    }

    public Long getAttrCatgId() {
        return attrCatgId;
    }

    public void setAttrCatgId(Long attrCatgId) {
        this.attrCatgId = attrCatgId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public Long getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(Long updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public AttrCatgDto getAttrCatg() {
        return attrCatg;
    }

    public void setAttrCatg(AttrCatgDto attrCatg) {
        this.attrCatg = attrCatg;
    }

    public List<PayOrderValueDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<PayOrderValueDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getActualDealerName() {
        return actualDealerName;
    }

    public void setActualDealerName(String actualDealerName) {
        this.actualDealerName = actualDealerName;
    }

    public String getAssignedDealerName() {
        return assignedDealerName;
    }

    public void setAssignedDealerName(String assignedDealerName) {
        this.assignedDealerName = assignedDealerName;
    }

    public String getAssignedDealerRoleName() {
        return assignedDealerRoleName;
    }

    public void setAssignedDealerRoleName(String assignedDealerRoleName) {
        this.assignedDealerRoleName = assignedDealerRoleName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getUpdatePersonName() {
        return updatePersonName;
    }

    public void setUpdatePersonName(String updatePersonName) {
        this.updatePersonName = updatePersonName;
    }

    public RecommendInfoDto getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(RecommendInfoDto recommendInfo) {
        this.recommendInfo = recommendInfo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PayOrderDto [id=");
        builder.append(id);
        builder.append(", code=");
        builder.append(code);
        builder.append(", workOrderId=");
        builder.append(workOrderId);
        builder.append(", type=");
        builder.append(type);
        builder.append(", payRefId=");
        builder.append(payRefId);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", staffId=");
        builder.append(staffId);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", actualDealerId=");
        builder.append(actualDealerId);
        builder.append(", assignedDealerId=");
        builder.append(assignedDealerId);
        builder.append(", assignedDealerRoleId=");
        builder.append(assignedDealerRoleId);
        builder.append(", attrCatgId=");
        builder.append(attrCatgId);
        builder.append(", state=");
        builder.append(state);
        builder.append(", stateDate=");
        builder.append(stateDate);
        builder.append(", updatePersonId=");
        builder.append(updatePersonId);
        builder.append(", updateDate=");
        builder.append(updateDate);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", attrCatg=");
        builder.append(attrCatg);
        builder.append(", subOrderValueList=");
        builder.append(subOrderValueList);
        builder.append(", changeFlag=");
        builder.append(changeFlag);
        builder.append(", typeName=");
        builder.append(typeName);
        builder.append(", staffName=");
        builder.append(staffName);
        builder.append(", actualDealerName=");
        builder.append(actualDealerName);
        builder.append(", assignedDealerName=");
        builder.append(assignedDealerName);
        builder.append(", assignedDealerRoleName=");
        builder.append(assignedDealerRoleName);
        builder.append(", stateName=");
        builder.append(stateName);
        builder.append(", updatePersonName=");
        builder.append(updatePersonName);
        builder.append(", recommendInfo=");
        builder.append(recommendInfo);
        builder.append("]");
        return builder.toString();
    }

    /**
     * 
     * Description: 重置处理人
     * 
     * @author jinyanan
     *
     */
    public void clearAssignedDealer() {
        this.assignedDealerId = null;
        this.assignedDealerRoleId = null;
    }
}