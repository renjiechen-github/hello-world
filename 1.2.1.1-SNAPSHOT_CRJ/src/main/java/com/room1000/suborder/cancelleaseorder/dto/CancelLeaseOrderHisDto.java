package com.room1000.suborder.cancelleaseorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月6日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class CancelLeaseOrderHisDto implements Serializable {
    /**
     * id 主键
     * @mbg.generated
     */
    private Long id;

    /**
     * priority 优先级
     * @mbg.generated
     */
    private Long priority;

    /**
     * code 编码
     * @mbg.generated
     */
    private String code;
    
    /**
     * workOrderId
     */
    private Long workOrderId;

    /**
     * rentalLeaseOrderId 出租合约主键
     * @mbg.generated
     */
    private Long rentalLeaseOrderId;

    /**
     * butlerId 处理管家
     * @mbg.generated
     */
    private Long butlerId;
    
    /**
     * createDate
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createdDate;

    /**
     * cancelLeaseDate 退租时间
     * @mbg.generated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date cancelLeaseDate;

    /**
     * channel 渠道。退租订单是通过微信、UP、客服还是管家生成的
     * @mbg.generated
     */
    private String channel;

    /**
     * 当前环节名称
     */
    private String nodeName;
    
    /**
     * 当前环节实际处理责任人
     */
    private Long actualDealerId;
    
    /**
     * 当前环节待处理责任人，当前环节的指定人，未必一定由此人处理，也可能由角色中的其他人处理
     */
    private Long assignedDealerId;
    
    /**
     * 当前环节待处理角色
     */
    private Long assignedDealerRoleId;

    /**
     * type 退租类型
     * @mbg.generated
     */
    private String type;

    /**
     * attrCatgId 属性目录主键
     * @mbg.generated
     */
    private Long attrCatgId;

    /**
     * state 状态
     * @mbg.generated
     */
    private String state;

    /**
     * stateDate 状态变更时间
     * @mbg.generated
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
    
    private String currentStepTime;

    /**
     * comments 备注
     * @mbg.generated
     */
    private String comments;
    
    /**
     * 出租合约名称
     */
    private String rentalLeaseOrderName;
    
    /**
     * 状态名
     */
    private String stateName;
    
    /**
     * typeName
     */
    private String typeName;
    
    /**
     * channelName
     */
    private String channelName;
    
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
     * 修改内容
     */
    private String changeMessage;
    
    /**
     * subOrderValueList
     */
    private List<CancelLeaseOrderValueHisDto> subOrderValueList;

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

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
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

    public Long getButlerId() {
        return butlerId;
    }

    public void setButlerId(Long butlerId) {
        this.butlerId = butlerId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCancelLeaseDate() {
        return cancelLeaseDate;
    }

    public void setCancelLeaseDate(Date cancelLeaseDate) {
        this.cancelLeaseDate = cancelLeaseDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
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
        this.type = type == null ? null : type.trim();
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    public String getActualDealerPhone() {
        return actualDealerPhone;
    }

    public void setActualDealerPhone(String actualDealerPhone) {
        this.actualDealerPhone = actualDealerPhone;
    }

    public String getChangeMessage() {
        return changeMessage;
    }

    public void setChangeMessage(String changeMessage) {
        this.changeMessage = changeMessage;
    }

    public List<CancelLeaseOrderValueHisDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<CancelLeaseOrderValueHisDto> subOrderValueList) {
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
        builder.append("CancelLeaseOrderHisDto [id=");
        builder.append(id);
        builder.append(", priority=");
        builder.append(priority);
        builder.append(", code=");
        builder.append(code);
        builder.append(", workOrderId=");
        builder.append(workOrderId);
        builder.append(", rentalLeaseOrderId=");
        builder.append(rentalLeaseOrderId);
        builder.append(", butlerId=");
        builder.append(butlerId);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", cancelLeaseDate=");
        builder.append(cancelLeaseDate);
        builder.append(", channel=");
        builder.append(channel);
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
        builder.append(", rentalLeaseOrderName=");
        builder.append(rentalLeaseOrderName);
        builder.append(", stateName=");
        builder.append(stateName);
        builder.append(", typeName=");
        builder.append(typeName);
        builder.append(", channelName=");
        builder.append(channelName);
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
        builder.append(", changeMessage=");
        builder.append(changeMessage);
        builder.append(", subOrderValueList=");
        builder.append(subOrderValueList);
        builder.append("]");
        return builder.toString();
    }
}