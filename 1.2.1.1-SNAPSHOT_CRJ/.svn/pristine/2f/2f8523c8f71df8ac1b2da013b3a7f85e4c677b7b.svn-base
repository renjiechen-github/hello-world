package com.room1000.workorder.dto.request;

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
public class TeamWorkOrderPagerListRequest
{
	/**
	 * 所有处理过的订单id
	 */
	private String ids;
	
	/**
	 * 判断是待处理还是已处理订单
	 */
	private String todoType;
	
	/**
	 * 团队信息
	 */
	private String teamIds;

	/**
	 * 排序方式（1：按照总时长从高到低，2：按照总时长从低到高，3：按照当前步骤从高到底，4：按照当前步骤从低到高）
	 */
	private String sequenceWay;

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
	 * 类型列表
	 */
	private List<String> typeList;

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
	 * subAssignedDealerId
	 * 
	 * @mbg.generated
	 */
	private Long subAssignedDealerId;

	/**
	 * subAssignedDealerRoleId
	 * 
	 * @mbg.generated
	 */
	private Long subAssignedDealerRoleId;

	/**
	 * currentDealerId
	 */
	private Long currentDealerId;

	private String currentDealerIds;

	/**
	 * currentDealerRoleIds
	 */
	private String currentDealerRoleIds;

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

	public String getTodoType()
	{
		return todoType;
	}

	public void setTodoType(String todoType)
	{
		this.todoType = todoType;
	}

	public String getTeamIds()
	{
		return teamIds;
	}

	public void setTeamIds(String teamIds)
	{
		this.teamIds = teamIds;
	}

	public String getSequenceWay()
	{
		return sequenceWay;
	}

	public void setSequenceWay(String sequenceWay)
	{
		this.sequenceWay = sequenceWay;
	}

	public String getCurrentDealerIds()
	{
		return currentDealerIds;
	}

	public void setCurrentDealerIds(String currentDealerIds)
	{
		this.currentDealerIds = currentDealerIds;
	}

	public List<String> getTypeList()
	{
		return typeList;
	}

	public void setTypeList(List<String> typeList)
	{
		this.typeList = typeList;
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

	public String getCurrentDealerFlag()
	{
		return currentDealerFlag;
	}

	public void setCurrentDealerFlag(String currentDealerFlag)
	{
		this.currentDealerFlag = currentDealerFlag;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public String getCurrentDealerRoleIds()
	{
		return currentDealerRoleIds;
	}

	public void setCurrentDealerRoleIds(String currentDealerRoleIds)
	{
		this.currentDealerRoleIds = currentDealerRoleIds;
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

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
