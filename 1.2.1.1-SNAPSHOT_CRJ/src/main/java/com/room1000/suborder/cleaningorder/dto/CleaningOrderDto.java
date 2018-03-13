package com.room1000.suborder.cleaningorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.room1000.attr.dto.AttrCatgDto;

/**
 *
 * Description: cleaning_order实体
 *
 * Created on 2017年03月08日
 * 
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class CleaningOrderDto implements Serializable {
    /**
     * id 主键
     */
    private Long id;

    /**
     * code 编码
     */
    private String code;

    /**
     * workOrderId 工单主键
     */
    private Long workOrderId;

    /**
     * rentalLeaseOrderId 出租合约主键
     */
    private Long rentalLeaseOrderId;

    /**
     * staffId 保洁人员主键
     */
    private Long staffId;

    /**
     * createdDate 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createdDate;

    /**
     * appointmentDate 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentDate;

    /**
     * state 状态
     */
    private String state;

    /**
     * stateDate 状态变更时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date stateDate;

    /**
     * nodeName 当前环节名称
     */
    private String nodeName;

    /**
     * actualDealerId 当前环节实际处理责任人
     */
    private Long actualDealerId;

    /**
     * assignedDealerId 当前环节指定责任人
     */
    private Long assignedDealerId;

    /**
     * assignedDealerRoleId 当前环节指定处理角色
     */
    private Long assignedDealerRoleId;

    /**
     * type 保洁类型
     */
    private String type;

    /**
     * attrCatgId 属性目录主键
     */
    private Long attrCatgId;

    /**
     * cleaningMoney 保洁金额
     */
    private Long cleaningMoney;

    /**
     * imageUrl 图片地址
     */
    private String imageUrl;

    /**
     * updatePersonId 修改人主键
     * 
     * @mbg.generated
     */
    private Long updatePersonId;

    /**
     * updateDate 修改时间
     * 
     * @mbg.generated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateDate;

    /**
     * comments 备注
     */
    private String comments;

    /**
     * attrCatg
     */
    private AttrCatgDto attrCatg;

    /**
     * subOrderValueList
     */
    private List<CleaningOrderValueDto> subOrderValueList;

    /**
     * rentalLeaseOrderName
     */
    private String rentalLeaseOrderName;

    /**
     * stateName
     */
    private String stateName;

    /**
     * typeName
     */
    private String typeName;

    /**
     * 当前环节实际处理责任人
     */
    private String actualDealerName;

    /**
     * 当前环节实际处理责任人手机号
     */
    private String actualDealerPhone;

    /**
     * 当前环节待处理责任人，当前环节的指定人，未必一定由此人处理，也可能由角色中的其他人处理
     */
    private String assignedDealerName;

    /**
     * 当前环节待处理角色
     */
    private String assignedDealerRoleName;

    /**
     * updatePersonId 修改人主键
     * 
     * @mbg.generated
     */
    private String updatePersonName;

    /**
     * assignedDealerPhone
     */
    private String assignedDealerPhone;

    /**
     * 订单修改标记
     */
    private String changeFlag;

    public String getAssignedDealerPhone() {
        return assignedDealerPhone;
    }

    public void setAssignedDealerPhone(String assignedDealerPhone) {
        this.assignedDealerPhone = assignedDealerPhone;
    }

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
        this.code = code == null ? null : code.trim();
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getRentalLeaseOrderId() {
        return rentalLeaseOrderId;
    }

    public void setRentalLeaseOrderId(Long rentalLeaseOrderId) {
        this.rentalLeaseOrderId = rentalLeaseOrderId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
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
        this.type = type == null ? null : type.trim();
    }

    public Long getAttrCatgId() {
        return attrCatgId;
    }

    public void setAttrCatgId(Long attrCatgId) {
        this.attrCatgId = attrCatgId;
    }

    public Long getCleaningMoney() {
        return cleaningMoney;
    }

    public void setCleaningMoney(Long cleaningMoney) {
        this.cleaningMoney = cleaningMoney;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public AttrCatgDto getAttrCatg() {
        return attrCatg;
    }

    public void setAttrCatg(AttrCatgDto attrCatg) {
        this.attrCatg = attrCatg;
    }

    public List<CleaningOrderValueDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<CleaningOrderValueDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public String getRentalLeaseOrderName() {
        return rentalLeaseOrderName;
    }

    public void setRentalLeaseOrderName(String rentalLeaseOrderName) {
        this.rentalLeaseOrderName = rentalLeaseOrderName;
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

    public String getUpdatePersonName() {
        return updatePersonName;
    }

    public void setUpdatePersonName(String updatePersonName) {
        this.updatePersonName = updatePersonName;
    }

    public String getActualDealerPhone() {
        return actualDealerPhone;
    }

    public void setActualDealerPhone(String actualDealerPhone) {
        this.actualDealerPhone = actualDealerPhone;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CleaningOrderDto [id=");
        builder.append(id);
        builder.append(", code=");
        builder.append(code);
        builder.append(", workOrderId=");
        builder.append(workOrderId);
        builder.append(", rentalLeaseOrderId=");
        builder.append(rentalLeaseOrderId);
        builder.append(", staffId=");
        builder.append(staffId);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", state=");
        builder.append(state);
        builder.append(", stateDate=");
        builder.append(stateDate);
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
        builder.append(", cleaningMoney=");
        builder.append(cleaningMoney);
        builder.append(", imageUrl=");
        builder.append(imageUrl);
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
        builder.append(", rentalLeaseOrderName=");
        builder.append(rentalLeaseOrderName);
        builder.append(", stateName=");
        builder.append(stateName);
        builder.append(", typeName=");
        builder.append(typeName);
        builder.append(", actualDealerName=");
        builder.append(actualDealerName);
        builder.append(", actualDealerPhone=");
        builder.append(actualDealerPhone);
        builder.append(", assignedDealerName=");
        builder.append(assignedDealerName);
        builder.append(", assignedDealerRoleName=");
        builder.append(assignedDealerRoleName);
        builder.append(", updatePersonName=");
        builder.append(updatePersonName);
        builder.append(", assignedDealerPhone=");
        builder.append(assignedDealerPhone);
        builder.append(", changeFlag=");
        builder.append(changeFlag);
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