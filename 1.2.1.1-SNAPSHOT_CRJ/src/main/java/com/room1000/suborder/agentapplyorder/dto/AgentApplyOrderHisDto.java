package com.room1000.suborder.agentapplyorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * Description: agent_apply_order_his 实体
 *
 * Created on 2017年06月22日
 * 
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Table(name = "agent_apply_order_his")
public class AgentApplyOrderHisDto implements Serializable
{
	/**
	 * 主键
	 * 
	 * @mbg.generated
	 */
	@Id
	private Long id;

	/**
	 * 优先级
	 * 
	 * @mbg.generated
	 */
	@Id
	private Long priority;

	/**
	 * 编码
	 * 
	 * @mbg.generated
	 */
	private String code;

	/**
	 * 工单主键
	 * 
	 * @mbg.generated
	 */
	@Column(name = "work_order_id")
	private Long workOrderId;

	/**
	 * 处理员工主键
	 * 
	 * @mbg.generated
	 */
	@Column(name = "staff_id")
	private Long staffId;

	/**
	 * 用户主键
	 * 
	 * @mbg.generated
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 图片
	 * 
	 * @mbg.generated
	 */
	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * 身份证号码
	 * 
	 * @mbg.generated
	 */
	@Column(name = "cert_nbr")
	private String certNbr;

	/**
	 * 创建时间
	 * 
	 * @mbg.generated
	 */
	@Column(name = "created_date")
	private Date createdDate;

	/**
	 * 退租时间
	 * 
	 * @mbg.generated
	 */
	@Column(name = "appointment_date")
	private Date appointmentDate;

	/**
	 * 当前环节名称
	 * 
	 * @mbg.generated
	 */
	@Column(name = "node_name")
	private String nodeName;

	/**
	 * 当前环节实际处理责任人
	 * 
	 * @mbg.generated
	 */
	@Column(name = "actual_dealer_id")
	private Long actualDealerId;

	/**
	 * 当前环节待处理责任人，当前环节的指定人，未必一定由此人处理，也可能由角色中的其他人处理
	 * 
	 * @mbg.generated
	 */
	@Column(name = "assigned_dealer_id")
	private Long assignedDealerId;

	/**
	 * 当前环节待处理角色，默认值为-1，也就是没有角色来处理
	 * 
	 * @mbg.generated
	 */
	@Column(name = "assigned_dealer_role_id")
	private Long assignedDealerRoleId;

	/**
	 * 属性目录主键
	 * 
	 * @mbg.generated
	 */
	@Column(name = "attr_catg_id")
	private Long attrCatgId;

	/**
	 * 状态
	 * 
	 * @mbg.generated
	 */
	private String state;

	/**
	 * 状态变更时间
	 * 
	 * @mbg.generated
	 */
	@Column(name = "state_date")
	private Date stateDate;

	/**
	 * 修改人主键
	 * 
	 * @mbg.generated
	 */
	@Column(name = "update_person_id")
	private Long updatePersonId;

	/**
	 * 修改时间
	 * 
	 * @mbg.generated
	 */
	@Column(name = "update_date")
	private Date updateDate;

	private String currentStepTime;

	/**
	 * 备注
	 * 
	 * @mbg.generated
	 */
	private String comments;

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
	 * subOrderValueList
	 */
	@Transient
	private List<AgentApplyOrderValueHisDto> subOrderValueList;

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Long getWorkOrderId()
	{
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId)
	{
		this.workOrderId = workOrderId;
	}

	public Long getStaffId()
	{
		return staffId;
	}

	public void setStaffId(Long staffId)
	{
		this.staffId = staffId;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getCertNbr()
	{
		return certNbr;
	}

	public void setCertNbr(String certNbr)
	{
		this.certNbr = certNbr;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public Date getAppointmentDate()
	{
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate)
	{
		this.appointmentDate = appointmentDate;
	}

	public String getNodeName()
	{
		return nodeName;
	}

	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	public Long getActualDealerId()
	{
		return actualDealerId;
	}

	public void setActualDealerId(Long actualDealerId)
	{
		this.actualDealerId = actualDealerId;
	}

	public Long getAssignedDealerId()
	{
		return assignedDealerId;
	}

	public void setAssignedDealerId(Long assignedDealerId)
	{
		this.assignedDealerId = assignedDealerId;
	}

	public Long getAssignedDealerRoleId()
	{
		return assignedDealerRoleId;
	}

	public void setAssignedDealerRoleId(Long assignedDealerRoleId)
	{
		this.assignedDealerRoleId = assignedDealerRoleId;
	}

	public Long getAttrCatgId()
	{
		return attrCatgId;
	}

	public void setAttrCatgId(Long attrCatgId)
	{
		this.attrCatgId = attrCatgId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public Date getStateDate()
	{
		return stateDate;
	}

	public void setStateDate(Date stateDate)
	{
		this.stateDate = stateDate;
	}

	public Long getUpdatePersonId()
	{
		return updatePersonId;
	}

	public void setUpdatePersonId(Long updatePersonId)
	{
		this.updatePersonId = updatePersonId;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getStaffName()
	{
		return staffName;
	}

	public void setStaffName(String staffName)
	{
		this.staffName = staffName;
	}

	public String getActualDealerName()
	{
		return actualDealerName;
	}

	public void setActualDealerName(String actualDealerName)
	{
		this.actualDealerName = actualDealerName;
	}

	public String getAssignedDealerName()
	{
		return assignedDealerName;
	}

	public void setAssignedDealerName(String assignedDealerName)
	{
		this.assignedDealerName = assignedDealerName;
	}

	public String getAssignedDealerRoleName()
	{
		return assignedDealerRoleName;
	}

	public void setAssignedDealerRoleName(String assignedDealerRoleName)
	{
		this.assignedDealerRoleName = assignedDealerRoleName;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String getUpdatePersonName()
	{
		return updatePersonName;
	}

	public void setUpdatePersonName(String updatePersonName)
	{
		this.updatePersonName = updatePersonName;
	}

	public List<AgentApplyOrderValueHisDto> getSubOrderValueList()
	{
		return subOrderValueList;
	}

	public void setSubOrderValueList(List<AgentApplyOrderValueHisDto> subOrderValueList)
	{
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
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("AgentApplyOrderHisDto [id=");
		builder.append(id);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", code=");
		builder.append(code);
		builder.append(", workOrderId=");
		builder.append(workOrderId);
		builder.append(", staffId=");
		builder.append(staffId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", imageUrl=");
		builder.append(imageUrl);
		builder.append(", certNbr=");
		builder.append(certNbr);
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
		builder.append(", subOrderValueList=");
		builder.append(subOrderValueList);
		builder.append("]");
		return builder.toString();
	}
}