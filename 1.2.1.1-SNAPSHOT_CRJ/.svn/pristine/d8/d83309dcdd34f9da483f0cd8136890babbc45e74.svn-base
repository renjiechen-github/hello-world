package com.room1000.suborder.ownercancelleaseorder.dto;

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


/**
 *
 * Description: owner_cancel_lease_order 实体
 *
 * Created on 2017年05月05日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Table(name = "owner_cancel_lease_order")
public class OwnerCancelLeaseOrderDto implements Serializable {
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
     * 收房合约主键
     * @mbg.generated
     */
    @Column(name = "take_house_order_id")
    private Long takeHouseOrderId;

    /**
     * 处理员工主键
     * @mbg.generated
     */
    @Column(name = "staff_id")
    private Long staffId;

    /**
     * 创建时间
     * @mbg.generated
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 退租时间
     * @mbg.generated
     */
    @Column(name = "appointment_date")
    private Date appointmentDate;

    /**
     * 当前环节名称
     * @mbg.generated
     */
    @Column(name = "node_name")
    private String nodeName;

    /**
     * 当前环节实际处理责任人
     * @mbg.generated
     */
    @Column(name = "actual_dealer_id")
    private Long actualDealerId;

    /**
     * 当前环节待处理责任人，当前环节的指定人，未必一定由此人处理，也可能由角色中的其他人处理
     * @mbg.generated
     */
    @Column(name = "assigned_dealer_id")
    private Long assignedDealerId;

    /**
     * 当前环节待处理角色，默认值为-1，也就是没有角色来处理
     * @mbg.generated
     */
    @Column(name = "assigned_dealer_role_id")
    private Long assignedDealerRoleId;

    /**
     * 退租类型
     * @mbg.generated
     */
    private String type;

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
     * attrCatgDto
     */
    @Transient
    private AttrCatgDto attrCatg;
    
    /**
     * subOrderValueList
     */
    @Transient
    private List<OwnerCancelLeaseOrderValueDto> subOrderValueList;
    
    /**
     * changeFlag
     */
    @Transient
    private String changeFlag;

    /**
     * takeHouseOrderName
     */
    @Transient
    private String takeHouseOrderName;

    /**
     * stateName
     */
    @Transient
    private String stateName;

    /**
     * typeName
     */
    @Transient
    private String typeName;

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
     * assignedDealerPhone
     */
    @Transient
    private String assignedDealerPhone;

    /**
     * updatePersonName
     */
    @Transient
    private String updatePersonName;
    
    /**
     * assignedDealerRoleName
     */
    @Transient
    private String assignedDealerRoleName;

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

    public Long getTakeHouseOrderId() {
        return takeHouseOrderId;
    }

    public void setTakeHouseOrderId(Long takeHouseOrderId) {
        this.takeHouseOrderId = takeHouseOrderId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<OwnerCancelLeaseOrderValueDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<OwnerCancelLeaseOrderValueDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getTakeHouseOrderName() {
        return takeHouseOrderName;
    }

    public void setTakeHouseOrderName(String takeHouseOrderName) {
        this.takeHouseOrderName = takeHouseOrderName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getAssignedDealerPhone() {
        return assignedDealerPhone;
    }

    public void setAssignedDealerPhone(String assignedDealerPhone) {
        this.assignedDealerPhone = assignedDealerPhone;
    }

    public String getUpdatePersonName() {
        return updatePersonName;
    }

    public void setUpdatePersonName(String updatePersonName) {
        this.updatePersonName = updatePersonName;
    }

    public String getAssignedDealerRoleName() {
        return assignedDealerRoleName;
    }

    public void setAssignedDealerRoleName(String assignedDealerRoleName) {
        this.assignedDealerRoleName = assignedDealerRoleName;
    }

    /**
     * Description: toString<br>
     *
     * @author autoCreated <br>
    
     * @return String String<br>
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", workOrderId=").append(workOrderId);
        sb.append(", takeHouseOrderId=").append(takeHouseOrderId);
        sb.append(", staffId=").append(staffId);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", appointmentDate=").append(appointmentDate);
        sb.append(", nodeName=").append(nodeName);
        sb.append(", actualDealerId=").append(actualDealerId);
        sb.append(", assignedDealerId=").append(assignedDealerId);
        sb.append(", assignedDealerRoleId=").append(assignedDealerRoleId);
        sb.append(", type=").append(type);
        sb.append(", attrCatgId=").append(attrCatgId);
        sb.append(", state=").append(state);
        sb.append(", stateDate=").append(stateDate);
        sb.append(", updatePersonId=").append(updatePersonId);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", comments=").append(comments);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
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