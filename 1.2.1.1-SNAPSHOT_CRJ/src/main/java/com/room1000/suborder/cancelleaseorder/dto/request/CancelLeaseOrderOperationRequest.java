package com.room1000.suborder.cancelleaseorder.dto.request;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;

/**
 * 
 * Description:
 * 
 * Created on 2017年5月26日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class CancelLeaseOrderOperationRequest
{

	/**
	 * code
	 */
	private String code;

	/**
	 * newButlerId
	 */
	private Long newButlerId;

	/**
	 * 处理员工id
	 */
	private Long dealerId;

	/**
	 * 管家上门时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date butlerGetHouseDate;

	/**
	 * subOrderValueList
	 */
	private List<CancelLeaseOrderValueDto> subOrderValueList;

	/**
	 * 如果仅是保存，传N，如果是提交，传Y
	 */
	private String submitFlag = BooleanFlagDef.BOOLEAN_TRUE;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public List<CancelLeaseOrderValueDto> getSubOrderValueList()
	{
		return subOrderValueList;
	}

	public void setSubOrderValueList(List<CancelLeaseOrderValueDto> subOrderValueList)
	{
		this.subOrderValueList = subOrderValueList;
	}

	public Long getNewButlerId()
	{
		return newButlerId;
	}

	public void setNewButlerId(Long newButlerId)
	{
		this.newButlerId = newButlerId;
	}

	public Long getDealerId()
	{
		return dealerId;
	}

	public void setDealerId(Long dealerId)
	{
		this.dealerId = dealerId;
	}

	public String getSubmitFlag()
	{
		return submitFlag;
	}

	public void setSubmitFlag(String submitFlag)
	{
		this.submitFlag = submitFlag;
	}

	public Date getButlerGetHouseDate()
	{
		return butlerGetHouseDate;
	}

	public void setButlerGetHouseDate(Date butlerGetHouseDate)
	{
		this.butlerGetHouseDate = butlerGetHouseDate;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
