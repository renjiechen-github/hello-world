package com.room1000.workorder.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class WorkOrderDto implements Serializable
{
	/**
	 * id 主键
	 * 
	 * @mbg.generated
	 */
	private Long id;
	
	/**
	 * 当前用户的团队及上级团队的负责人id
	 */
	private String leaderStr;

	/**
	 * type 工单类型
	 * 
	 * @mbg.generated
	 */
	private String type;

	/**
	 * refId 具体工单主键
	 * 
	 * @mbg.generated
	 */
	private Long refId;

	/**
	 * name
	 */
	private String name;

	/**
	 * code 编码
	 * 
	 * @mbg.generated
	 */
	private String code;

	/**
	 * createDate 创建时间
	 * 
	 * @mbg.generated
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdDate;

	/**
	 * createdPerson 创建员工主键
	 * 
	 * @mbg.generated
	 */
	private Long createdStaffId;

	/**
	 * createdPerson 创建客户主键
	 * 
	 * @mbg.generated
	 */
	private Long createdUserId;

	/**
	 * houseId 房源主键
	 * 
	 * @mbg.generated
	 */
	private Long houseId;

	/**
	 * takeHouseOrderId 收房合约主键
	 * 
	 * @mbg.generated
	 */
	private Long takeHouseOrderId;

	/**
	 * rentalLeaseOrderId 出租合约主键
	 * 
	 * @mbg.generated
	 */
	private Long rentalLeaseOrderId;

	/**
	 * userName 用户姓名
	 * 
	 * @mbg.generated
	 */
	private String userName;

	/**
	 * userPhone 用户手机号
	 * 
	 * @mbg.generated
	 */
	private String userPhone;

	/**
	 * butlerId 处理员工主键
	 * 
	 * @mbg.generated
	 */
	private Long staffId;

	/**
	 * appointmentDate 预约时间
	 * 
	 * @mbg.generated
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date appointmentDate;

	/**
	 * state 状态
	 * 
	 * @mbg.generated
	 */
	private String state;

	/**
	 * stateDate 状态变更时间
	 * 
	 * @mbg.generated
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stateDate;

	/**
	 * subStateName
	 * 
	 * @mbg.generated
	 */
	private String subOrderState;

	/**
	 * subActualDealerId 当前环节实际处理责任人
	 * 
	 * @mbg.generated
	 */
	private Long subActualDealerId;

	/**
	 * 当前处理人id
	 * 
	 * @mbg.generated
	 */
	private Long subAssignedDealerId;

	/**
	 * 当前处理人角色id
	 * 
	 * @mbg.generated
	 */
	private Long subAssignedDealerRoleId;

	/**
	 * 子订单备注
	 */
	private String subComments;

	/**
	 * 已支付金额
	 */
	private String paidMoney;

	/**
	 * 应支付金额
	 */
	private String payableMoney;

	/**
	 * createdStaffName
	 */
	private String createdStaffName;

	/**
	 * createdUserName
	 */
	private String createdUserName;

	/**
	 * subActualDealerName
	 */
	private String subActualDealerName;

	/**
	 * subAssignedDealerName
	 */
	private String subAssignedDealerName;

	/**
	 * subAssignedDealerRoleName
	 */
	private String subAssignedDealerRoleName;

	/**
	 * stateName
	 */
	private String stateName;

	/**
	 * typeName
	 */
	private String typeName;

	/**
	 * subStateName
	 */
	private String subStateName;

	/**
	 * createdDateStr
	 */
	private String createdDateStr;

	/**
	 * appointmentDateStr
	 */
	private String appointmentDateStr;

	/**
	 * stateDateStr
	 */
	private String stateDateStr;

	/**
	 * orderCommentaryList
	 */
	List<OrderCommentaryDto> orderCommentaryList;

	/**
	 * 是否超时
	 */
	private String timeCode;

	/**
	 * 总时长
	 */
	private String now_date_str;

	/**
	 * 当前步骤时间
	 */
	private String currentStepTime;

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public String getLeaderStr()
	{
		return leaderStr;
	}

	public void setLeaderStr(String leaderStr)
	{
		this.leaderStr = leaderStr;
	}

	public String getTimeCode()
	{
		return timeCode;
	}

	public void setTimeCode(String timeCode)
	{
		this.timeCode = timeCode;
	}

	public String getNow_date_str()
	{
		return now_date_str;
	}

	public void setNow_date_str(String now_date_str)
	{
		this.now_date_str = now_date_str;
	}

	public String getCurrentStepTime()
	{
		return currentStepTime;
	}

	public void setCurrentStepTime(String currentStepTime)
	{
		this.currentStepTime = currentStepTime;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type == null ? null : type.trim();
	}

	public Long getRefId()
	{
		return refId;
	}

	public void setRefId(Long refId)
	{
		this.refId = refId;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code == null ? null : code.trim();
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public Long getCreatedStaffId()
	{
		return createdStaffId;
	}

	public void setCreatedStaffId(Long createdStaffId)
	{
		this.createdStaffId = createdStaffId;
	}

	public Long getCreatedUserId()
	{
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId)
	{
		this.createdUserId = createdUserId;
	}

	public Long getHouseId()
	{
		return houseId;
	}

	public void setHouseId(Long houseId)
	{
		this.houseId = houseId;
	}

	public Long getTakeHouseOrderId()
	{
		return takeHouseOrderId;
	}

	public void setTakeHouseOrderId(Long takeHouseOrderId)
	{
		this.takeHouseOrderId = takeHouseOrderId;
	}

	public Long getRentalLeaseOrderId()
	{
		return rentalLeaseOrderId;
	}

	public void setRentalLeaseOrderId(Long rentalLeaseOrderId)
	{
		this.rentalLeaseOrderId = rentalLeaseOrderId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPhone()
	{
		return userPhone;
	}

	public void setUserPhone(String userPhone)
	{
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public Long getStaffId()
	{
		return staffId;
	}

	public void setStaffId(Long staffId)
	{
		this.staffId = staffId;
	}

	public Date getAppointmentDate()
	{
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate)
	{
		this.appointmentDate = appointmentDate;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state == null ? null : state.trim();
	}

	public Date getStateDate()
	{
		return stateDate;
	}

	public void setStateDate(Date stateDate)
	{
		this.stateDate = stateDate;
	}

	public String getCreatedStaffName()
	{
		return createdStaffName;
	}

	public void setCreatedStaffName(String createdStaffName)
	{
		this.createdStaffName = createdStaffName;
	}

	public String getCreatedUserName()
	{
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName)
	{
		this.createdUserName = createdUserName;
	}

	public Long getSubAssignedDealerId()
	{
		return subAssignedDealerId;
	}

	public void setSubAssignedDealerId(Long subAssignedDealerId)
	{
		this.subAssignedDealerId = subAssignedDealerId;
	}

	public Long getSubAssignedDealerRoleId()
	{
		return subAssignedDealerRoleId;
	}

	public void setSubAssignedDealerRoleId(Long subAssignedDealerRoleId)
	{
		this.subAssignedDealerRoleId = subAssignedDealerRoleId;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	public Long getSubActualDealerId()
	{
		return subActualDealerId;
	}

	public void setSubActualDealerId(Long subActualDealerId)
	{
		this.subActualDealerId = subActualDealerId;
	}

	public String getSubActualDealerName()
	{
		return subActualDealerName;
	}

	public void setSubActualDealerName(String subActualDealerName)
	{
		this.subActualDealerName = subActualDealerName;
	}

	public String getSubAssignedDealerName()
	{
		return subAssignedDealerName;
	}

	public void setSubAssignedDealerName(String subAssignedDealerName)
	{
		this.subAssignedDealerName = subAssignedDealerName;
	}

	public String getSubAssignedDealerRoleName()
	{
		return subAssignedDealerRoleName;
	}

	public void setSubAssignedDealerRoleName(String subAssignedDealerRoleName)
	{
		this.subAssignedDealerRoleName = subAssignedDealerRoleName;
	}

	public String getCreatedDateStr()
	{
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr)
	{
		this.createdDateStr = createdDateStr;
	}

	public String getAppointmentDateStr()
	{
		return appointmentDateStr;
	}

	public void setAppointmentDateStr(String appointmentDateStr)
	{
		this.appointmentDateStr = appointmentDateStr;
	}

	public String getStateDateStr()
	{
		return stateDateStr;
	}

	public void setStateDateStr(String stateDateStr)
	{
		this.stateDateStr = stateDateStr;
	}

	public String getSubComments()
	{
		return subComments;
	}

	public void setSubComments(String subComments)
	{
		this.subComments = subComments;
	}

	public String getPaidMoney()
	{
		return paidMoney;
	}

	public void setPaidMoney(String paidMoney)
	{
		this.paidMoney = paidMoney;
	}

	public String getPayableMoney()
	{
		return payableMoney;
	}

	public void setPayableMoney(String payableMoney)
	{
		this.payableMoney = payableMoney;
	}

	public String getSubOrderState()
	{
		return subOrderState;
	}

	public void setSubOrderState(String subOrderState)
	{
		this.subOrderState = subOrderState;
	}

	public String getSubStateName()
	{
		return subStateName;
	}

	public void setSubStateName(String subStateName)
	{
		this.subStateName = subStateName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<OrderCommentaryDto> getOrderCommentaryList()
	{
		return orderCommentaryList;
	}

	public void setOrderCommentaryList(List<OrderCommentaryDto> orderCommentaryList)
	{
		this.orderCommentaryList = orderCommentaryList;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 
	 * Description: 清空subStateName, subAssignedDealerId, subsAssignedDealerRoleId
	 * 
	 * @author jinyanan
	 *
	 */
	public void clearSubField()
	{
		this.subOrderState = null;
		this.subAssignedDealerId = null;
		this.subAssignedDealerRoleId = null;
	}
}