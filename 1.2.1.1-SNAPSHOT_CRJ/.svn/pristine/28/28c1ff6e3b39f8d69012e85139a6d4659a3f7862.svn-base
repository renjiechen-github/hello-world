package com.room1000.workorder.dto.request;

import java.util.Date;
import java.util.List;

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
public class QryWorkOrderPagerListRequest
{

	/**
	 * 当前页码（页码从1开始计算）
	 */
	private int pageNum;

	/**
	 * 每页行数
	 */
	private int pageSize;

	/**
	 * 工单类型
	 */
	private String type;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 创建时间 起始
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDateStart;

	/**
	 * 创建时间 结束
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDateEnd;

	/**
	 * 预约时间 起始
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date appointmentDateStart;

	/**
	 * 预约时间 结束
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date appointmentDateEnd;

	/**
	 * 创建员工主键
	 */
	private Long createdStaffId;

	/**
	 * 创建客户主键
	 */
	private Long createdUserId;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 状态列表
	 */
	private List<String> stateList;

	/**
	 * 排除的订单状态，sql中会用"not in"来拼接
	 */
	private List<String> excludeStateList;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 用户手机号
	 */
	private String userPhone;

	/**
	 * currentDealerId
	 */
	private Long currentDealerId;

	/**
	 * currentDealerRoleIds
	 */
	private List<String> currentDealerRoleIds;

	/**
	 * 只查询当前联系人能看到的数据，则该字段设置为Y
	 */
	private String currentDealerFlag;

	/**
	 * 子订单状态
	 */
	private String subOrderState;

	/**
	 * 子订单状态列表
	 */
	private List<String> subOrderStateList;

	/**
	 * 当前用户已处理的订单
	 */
	private String currentDealerDealtOrder;

	/**
	 * 当前用户创建的订单
	 */
	private String currentDealerStartedOrder;

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 需要排除的订单类型
	 */
	private List<String> excludeTypeList;

	private String sequenceWay;

	public String getSequenceWay()
	{
		return sequenceWay;
	}

	public void setSequenceWay(String sequenceWay)
	{
		this.sequenceWay = sequenceWay;
	}

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Date getCreatedDateStart()
	{
		return createdDateStart;
	}

	public void setCreatedDateStart(Date createdDateStart)
	{
		this.createdDateStart = createdDateStart;
	}

	public Date getCreatedDateEnd()
	{
		return createdDateEnd;
	}

	public void setCreatedDateEnd(Date createdDateEnd)
	{
		this.createdDateEnd = createdDateEnd;
	}

	public Date getAppointmentDateStart()
	{
		return appointmentDateStart;
	}

	public void setAppointmentDateStart(Date appointmentDateStart)
	{
		this.appointmentDateStart = appointmentDateStart;
	}

	public Date getAppointmentDateEnd()
	{
		return appointmentDateEnd;
	}

	public void setAppointmentDateEnd(Date appointmentDateEnd)
	{
		this.appointmentDateEnd = appointmentDateEnd;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserPhone()
	{
		return userPhone;
	}

	public void setUserPhone(String userPhone)
	{
		this.userPhone = userPhone;
	}

	public Long getCurrentDealerId()
	{
		return currentDealerId;
	}

	public void setCurrentDealerId(Long currentDealerId)
	{
		this.currentDealerId = currentDealerId;
	}

	public List<String> getCurrentDealerRoleIds()
	{
		return currentDealerRoleIds;
	}

	public void setCurrentDealerRoleIds(List<String> currentDealerRoleIds)
	{
		this.currentDealerRoleIds = currentDealerRoleIds;
	}

	public String getCurrentDealerFlag()
	{
		return currentDealerFlag;
	}

	public void setCurrentDealerFlag(String currentDealerFlag)
	{
		this.currentDealerFlag = currentDealerFlag;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCurrentDealerDealtOrder()
	{
		return currentDealerDealtOrder;
	}

	public void setCurrentDealerDealtOrder(String currentDealerDealtOrder)
	{
		this.currentDealerDealtOrder = currentDealerDealtOrder;
	}

	public String getCurrentDealerStartedOrder()
	{
		return currentDealerStartedOrder;
	}

	public void setCurrentDealerStartedOrder(String currentDealerStartedOrder)
	{
		this.currentDealerStartedOrder = currentDealerStartedOrder;
	}

	public Long getCreatedStaffId()
	{
		return createdStaffId;
	}

	public void setCreatedStaffId(Long createdStaffId)
	{
		this.createdStaffId = createdStaffId;
	}

	public List<String> getStateList()
	{
		return stateList;
	}

	public void setStateList(List<String> stateList)
	{
		this.stateList = stateList;
	}

	public String getSubOrderState()
	{
		return subOrderState;
	}

	public void setSubOrderState(String subOrderState)
	{
		this.subOrderState = subOrderState;
	}

	public Long getCreatedUserId()
	{
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId)
	{
		this.createdUserId = createdUserId;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public List<String> getSubOrderStateList()
	{
		return subOrderStateList;
	}

	public void setSubOrderStateList(List<String> subOrderStateList)
	{
		this.subOrderStateList = subOrderStateList;
	}

	public List<String> getExcludeStateList()
	{
		return excludeStateList;
	}

	public void setExcludeStateList(List<String> excludeStateList)
	{
		this.excludeStateList = excludeStateList;
	}

	public List<String> getExcludeTypeList()
	{
		return excludeTypeList;
	}

	public void setExcludeTypeList(List<String> excludeTypeList)
	{
		this.excludeTypeList = excludeTypeList;
	}

	@Override
	public String toString()
	{
		return "QryWorkOrderPagerListRequest [pageNum=" + pageNum + ", pageSize=" + pageSize + ", type=" + type + ", code="
				+ code + ", createdDateStart=" + createdDateStart + ", createdDateEnd=" + createdDateEnd
				+ ", appointmentDateStart=" + appointmentDateStart + ", appointmentDateEnd=" + appointmentDateEnd
				+ ", createdStaffId=" + createdStaffId + ", createdUserId=" + createdUserId + ", state=" + state
				+ ", stateList=" + stateList + ", excludeStateList=" + excludeStateList + ", userName=" + userName
				+ ", userPhone=" + userPhone + ", currentDealerId=" + currentDealerId + ", currentDealerRoleIds="
				+ currentDealerRoleIds + ", currentDealerFlag=" + currentDealerFlag + ", subOrderState=" + subOrderState
				+ ", subOrderStateList=" + subOrderStateList + ", currentDealerDealtOrder=" + currentDealerDealtOrder
				+ ", currentDealerStartedOrder=" + currentDealerStartedOrder + ", keyword=" + keyword + ", excludeTypeList="
				+ excludeTypeList + ", sequenceWay=" + sequenceWay + "]";
	}

}
