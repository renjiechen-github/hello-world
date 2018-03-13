package com.room1000.suborder.complaintorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.room1000.attr.dto.AttrCatgDto;

/**
 *
 * Description: complaint_order实体
 *
 * Created on 2017年03月07日
 * 
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class ComplaintOrderDto implements Serializable {
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
     * createdDate 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createdDate;

    /**
     * staffId 处理员工主键
     */
    private Long staffId;

    /**
     * complaintDate 投诉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date complaintDate;

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
     * attrCatgId 属性目录主键
     */
    private Long attrCatgId;

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
     * complaintOrderValueList
     */
    private List<ComplaintOrderValueDto> subOrderValueList;

    /**
     * stateName
     */
    private String stateName;

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
    
    /**
     * imageUrl 图片地址
     */
    private String imageUrl;

    public String getImageUrl()
		{
			return imageUrl;
		}

		public void setImageUrl(String imageUrl)
		{
			this.imageUrl = imageUrl;
		}

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
        this.state = state == null ? null : state.trim();
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
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

    public List<ComplaintOrderValueDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<ComplaintOrderValueDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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
        builder.append("ComplaintOrderDto [id=");
        builder.append(id);
        builder.append(", code=");
        builder.append(code);
        builder.append(", workOrderId=");
        builder.append(workOrderId);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", staffId=");
        builder.append(staffId);
        builder.append(", complaintDate=");
        builder.append(complaintDate);
        builder.append(", nodeName=");
        builder.append(nodeName);
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
        builder.append(", stateName=");
        builder.append(stateName);
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