
package com.ycdc.appserver.model.house;

/**
 * 房源查询条件
 * 
 * @author suntf
 * @date 2016年11月28日
 */
public class HouseCondition
{

	/**
	 * 房源状态
	 */
	public String status;
	/**
	 * 关键字
	 */
	public String keyWord;
	/**
	 * 区域
	 */
	public String areaid;
	/**
	 * 发布状态
	 */
	public String publish;
	/**
	 * 管家
	 */
	public String contractManage;
	/**
	 * 商圈
	 */
	public String trading;
	/**
	 * 是否本人
	 */
	public String is_self;
	/**
	 * 是否空置
	 */
	public String is_kz;
	/**
	 * 开始页码
	 */
	public String startPage = "0";
	/**
	 * 每页显示信息
	 */
	public String pageSize = "25";
	public String userId;
	/**
	 * 签约状态
	 */
	public String signStatus;
	private String newfilepath;
	private String filepath;
	private String g_id;
	/**
	 * 团队id集合
	 */
	private String teamIds;
	
	/**
	 * 时间配置
	 */
	private String time_configure;
	
	public String getTime_configure()
	{
		return time_configure;
	}

	public void setTime_configure(String time_configure)
	{
		this.time_configure = time_configure;
	}

	public String getTeamIds()
	{
		return teamIds;
	}

	public void setTeamIds(String teamIds)
	{
		this.teamIds = teamIds;
	}

	/**
	 * 我的团队
	 * 0：非我的团队，1：我的团队及子团队，默认0
	 */
	public int isMyTeams = 0;
	
	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord()
	{
		return keyWord;
	}

	/**
	 * @param keyWord
	 *            the keyWord to set
	 */
	public void setKeyWord(String keyWord)
	{
		this.keyWord = keyWord;
	}

	/**
	 * @return the areaid
	 */
	public String getAreaid()
	{
		return areaid;
	}

	/**
	 * @param areaid
	 *            the areaid to set
	 */
	public void setAreaid(String areaid)
	{
		this.areaid = areaid;
	}

	/**
	 * @return the publish
	 */
	public String getPublish()
	{
		return publish;
	}

	/**
	 * @param publish
	 *            the publish to set
	 */
	public void setPublish(String publish)
	{
		this.publish = publish;
	}

	/**
	 * @return the contractManage
	 */
	public String getContractManage()
	{
		return contractManage;
	}

	/**
	 * @param contractManage
	 *            the contractManage to set
	 */
	public void setContractManage(String contractManage)
	{
		this.contractManage = contractManage;
	}

	/**
	 * @return the trading
	 */
	public String getTrading()
	{
		return trading;
	}

	/**
	 * @param trading
	 *            the trading to set
	 */
	public void setTrading(String trading)
	{
		this.trading = trading;
	}

	/**
	 * @return the is_self
	 */
	public String getIs_self()
	{
		return is_self;
	}

	/**
	 * @param is_self
	 *            the is_self to set
	 */
	public void setIs_self(String is_self)
	{
		this.is_self = is_self;
	}

	/**
	 * @return the is_kz
	 */
	public String getIs_kz()
	{
		return is_kz;
	}

	/**
	 * @param is_kz
	 *            the is_kz to set
	 */
	public void setIs_kz(String is_kz)
	{
		this.is_kz = is_kz;
	}

	/**
	 * @return the startPage
	 */
	public String getStartPage()
	{
		return startPage;
	}

	/**
	 * @param startPage
	 *            the startPage to set
	 */
	public void setStartPage(String startPage)
	{
		this.startPage = startPage;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize()
	{
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(String pageSize)
	{
		this.pageSize = pageSize;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[status=" + status + ", keyWord=" + keyWord + ", areaid="
				+ areaid + ", publish=" + publish + ", contractManage=" + contractManage
				+ ", trading=" + trading + ", is_self=" + is_self + ", is_kz=" + is_kz
				+ ", startPage=" + startPage + ", pageSize=" + pageSize + "]";
	}

	/**
	 * @return the newfilepath
	 */
	public String getNewfilepath()
	{
		return newfilepath;
	}

	/**
	 * @param newfilepath
	 *            the newfilepath to set
	 */
	public void setNewfilepath(String newfilepath)
	{
		this.newfilepath = newfilepath;
	}

	/**
	 * @return the filepath
	 */
	public String getFilepath()
	{
		return filepath;
	}

	/**
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath)
	{
		this.filepath = filepath;
	}

	/**
	 * @return the signStatus
	 */
	public String getSignStatus()
	{
		return signStatus;
	}

	/**
	 * @param signStatus
	 *            the signStatus to set
	 */
	public void setSignStatus(String signStatus)
	{
		this.signStatus = signStatus;
	}

	/**
	 * @return the userId
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public int getIsMyTeams()
	{
		return isMyTeams;
	}

	public void setIsMyTeams(int isMyTeams)
	{
		this.isMyTeams = isMyTeams;
	}

	public String getG_id()
	{
		return g_id;
	}

	public void setG_id(String g_id)
	{
		this.g_id = g_id;
	}
}
