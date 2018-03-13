package com.room1000.suborder.ownercancelleaseorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.room1000.suborder.repairorder.dto.RepairOrderValueHisDto;


/**
 *
 * Description: owner_cancel_lease_order_his 实体
 *
 * Created on 2017年05月05日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Table(name = "owner_cancel_lease_order_his")
public class OwnerCancelLeaseOrderHisDto implements Serializable {
    /**
     * 优先级
     * @mbg.generated
     */
    @Id
    private Long priority;

    /**
     * 主键
     * @mbg.generated
     */
    @Id
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
    
    private String currentStepTime;

    /**
     * 备注
     * @mbg.generated
     */
    private String comments;
    
    /**
     * subOrderValueList
     */
    @Transient
    private List<RepairOrderValueHisDto> subOrderValueList;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

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

    public List<RepairOrderValueHisDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<RepairOrderValueHisDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public String getCurrentStepTime()
		{
			return currentStepTime;
		}

		public void setCurrentStepTime(String currentStepTime)
		{
			this.currentStepTime = currentStepTime;
		}

		@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OwnerCancelLeaseOrderHisDto [priority=");
        builder.append(priority);
        builder.append(", id=");
        builder.append(id);
        builder.append(", code=");
        builder.append(code);
        builder.append(", workOrderId=");
        builder.append(workOrderId);
        builder.append(", takeHouseOrderId=");
        builder.append(takeHouseOrderId);
        builder.append(", staffId=");
        builder.append(staffId);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", nodeName=");
        builder.append(nodeName);
        builder.append(", actualDealerId=");
        builder.append(actualDealerId);
        builder.append(", assignedDealerId=");
        builder.append(assignedDealerId);
        builder.append(", assignedDealerRoleId=");
        builder.append(assignedDealerRoleId);
        builder.append(", type=");
        builder.append(type);
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
        builder.append(", subOrderValueList=");
        builder.append(subOrderValueList);
        builder.append("]");
        return builder.toString();
    }
}