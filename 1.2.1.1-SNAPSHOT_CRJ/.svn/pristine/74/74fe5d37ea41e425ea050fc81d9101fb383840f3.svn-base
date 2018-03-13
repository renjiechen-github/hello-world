package com.room1000.workorder.dto.request;

import java.util.List;

import com.room1000.workorder.dto.OrderCommentaryDto;

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
public class WorkOrderCommentaryRequest
{

	/**
	 * 工单主键
	 */
	private Long workOrderId;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 图片url
	 */
	private String imageUrl;

	/**
	 * Y：合约失效，N或者null合约不失效
	 */
	private String flag;

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	/**
	 * 评论内容
	 */
	private List<OrderCommentaryDto> orderCommentaryList;

	public Long getWorkOrderId()
	{
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId)
	{
		this.workOrderId = workOrderId;
	}

	public List<OrderCommentaryDto> getOrderCommentaryList()
	{
		return orderCommentaryList;
	}

	public void setOrderCommentaryList(List<OrderCommentaryDto> orderCommentaryList)
	{
		this.orderCommentaryList = orderCommentaryList;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	@Override
	public String toString()
	{
		return "WorkOrderCommentaryRequest [workOrderId=" + workOrderId + ", code=" + code + ", imageUrl=" + imageUrl
				+ ", flag=" + flag + ", orderCommentaryList=" + orderCommentaryList + "]";
	}

}
