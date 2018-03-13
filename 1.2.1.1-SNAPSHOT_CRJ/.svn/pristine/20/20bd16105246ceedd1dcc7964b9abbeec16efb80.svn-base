package com.yc.rm.caas.appserver.model.team;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TeamBean
{
	/** 团队id **/
	private int teamId;
	/** 团队名称 **/
	private String teamName;
	/** 团队负责人 **/
	private int leaderId;
	/** 团队负责人姓名 **/
	private String leaderName;
	/** 父团队 **/
	private int parentTeamId;
	/** 创建人 **/
	private int createId;
	/** 修改人 **/
	private int modifyId;
	/** 修改时间 **/
	private Timestamp modifyDate;
	/** 是否被删除，默认为0，未被删除 **/
	private int isDelete = 0;
	/** 团队级别，1是大团队，2是二级团队，依次类推 **/
	private int teamLevel;
	/** 团队关联房源数量 **/
	private int houseCnt;
	/** 子团队信息 **/
	private List<TeamBean> subTeam;
	/** 团队成员 **/
	private List<TeamRelation> memberList = new ArrayList<>();
	/** 团队单个成员 **/
	private TeamRelation memberOne;
	/**
	 * 子团队集合
	 */
	private List<TeamBean> subTeams;
	/** 是否可操作标识 **/
	private int ifOp = 0;
	/** 负责人列表 **/
	private List<TeamLeaders> leaderList;
	/** 是否是级别最高的团队，1是，0不是 **/
	private int isParent;
	
	// 判断当前登录人是否是负责人 1是 0不是
	private int isLeader;

	public int getIsLeader()
	{
		return isLeader;
	}

	public void setIsLeader(int isLeader)
	{
		this.isLeader = isLeader;
	}

	public int getIsParent()
	{
		return isParent;
	}

	public void setIsParent(int isParent)
	{
		this.isParent = isParent;
	}

	public List<TeamLeaders> getLeaderList()
	{
		return leaderList;
	}

	public void setLeaderList(List<TeamLeaders> leaderList)
	{
		this.leaderList = leaderList;
	}

	public TeamRelation getMemberOne()
	{
		return memberOne;
	}

	public void setMemberOne(TeamRelation memberOne)
	{
		this.memberOne = memberOne;
	}

	public int getIfOp()
	{
		return ifOp;
	}

	public void setIfOp(int ifOp)
	{
		this.ifOp = ifOp;
	}

	public List<TeamBean> getSubTeams()
	{
		return subTeams;
	}

	public void setSubTeams(List<TeamBean> subTeams)
	{
		this.subTeams = subTeams;
	}

	public List<TeamRelation> getMemberList()
	{
		return memberList;
	}

	public void setMemberList(List<TeamRelation> memberList)
	{
		this.memberList = memberList;
	}

	public List<TeamBean> getSubTeam()
	{
		return subTeam;
	}

	public void setSubTeam(List<TeamBean> subTeam)
	{
		this.subTeam = subTeam;
	}

	public String getLeaderName()
	{
		return leaderName;
	}

	public void setLeaderName(String leaderName)
	{
		this.leaderName = leaderName;
	}

	public int getHouseCnt()
	{
		return houseCnt;
	}

	public void setHouseCnt(int houseCnt)
	{
		this.houseCnt = houseCnt;
	}

	public int getTeamId()
	{
		return teamId;
	}

	public String getTeamName()
	{
		return teamName;
	}

	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}

	public int getLeaderId()
	{
		return leaderId;
	}

	public void setLeaderId(int leaderId)
	{
		this.leaderId = leaderId;
	}

	public int getParentTeamId()
	{
		return parentTeamId;
	}

	public void setParentTeamId(int parentTeamId)
	{
		this.parentTeamId = parentTeamId;
	}

	public int getCreateId()
	{
		return createId;
	}

	public void setCreateId(int createId)
	{
		this.createId = createId;
	}

	public int getModifyId()
	{
		return modifyId;
	}

	public void setModifyId(int modifyId)
	{
		this.modifyId = modifyId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getModifyDate()
	{
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	public void setTeamId(int teamId)
	{
		this.teamId = teamId;
	}

	public int getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(int isDelete)
	{
		this.isDelete = isDelete;
	}

	public int getTeamLevel()
	{
		return teamLevel;
	}

	public void setTeamLevel(int teamLevel)
	{
		this.teamLevel = teamLevel;
	}

	@Override
	public String toString()
	{
		return "TeamBean [teamId=" + teamId + ", teamName=" + teamName + ", leaderId=" + leaderId + ", leaderName=" + leaderName + ", parentTeamId=" + parentTeamId + ", createId="
				+ createId + ", modifyId=" + modifyId + ", modifyDate=" + modifyDate + ", isDelete=" + isDelete + ", teamLevel=" + teamLevel + ", houseCnt=" + houseCnt + ", subTeam="
				+ subTeam + ", memberList=" + memberList + ", memberOne=" + memberOne + ", subTeams=" + subTeams + ", ifOp=" + ifOp + ", leaderList=" + leaderList + ", isParent=" + isParent
				+ "]";
	}
}
