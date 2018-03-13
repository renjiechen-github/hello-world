package com.yc.rm.caas.appserver.bus.service.team;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.yc.rm.caas.appserver.base.support.BaseService;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamRelAreaFo;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamRelFo;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamSelectFo;
import com.yc.rm.caas.appserver.bus.dao.team.ITeamDao;
import com.yc.rm.caas.appserver.model.house.Group;
import com.yc.rm.caas.appserver.model.team.TeamBean;
import com.yc.rm.caas.appserver.model.team.TeamLeaders;
import com.yc.rm.caas.appserver.model.team.TeamRelHouse;
import com.yc.rm.caas.appserver.model.team.TeamRelation;
import com.yc.rm.caas.appserver.model.user.User;

@Service("_teamServImpl")
public class TeamServImpl extends BaseService implements ITeamServ
{

	@Resource(name = "_teamDao")
	private ITeamDao _teamDao;

	public List<Map<String, Object>> getLeaders(int teamId)
	{
		List<Map<String, Object>> leaders = new ArrayList<Map<String, Object>>();
		leaders = _teamDao.selectLeaderInfo(teamId, 0);
		return leaders;
	}

	/**
	 * 某团队名下的该用户未关联的小区列表/获取未分配团队的小区列表/获取某用户名下关联的小区列表/该团队关联的小区列表
	 */
	public List<Group> getAreaInfo(TeamSelectFo teamFo)
	{
		log.info("into getAreaList:");
		List<Group> areaList = new ArrayList<Group>();
		// 某团队下，该团员未关联的小区列表
		if (teamFo.getIfMemRel() == -1)
		{
			areaList = _teamDao.selectAreaByTeam(teamFo.getMemberList().get(0).getUserId(), teamFo.getTeamId());
		}
		// 该团员名下关联的小区列表
		else if (teamFo.getIfMemRel() == 1)
		{
			areaList = _teamDao.selectAreaByUser(teamFo.getMemberList().get(0).getUserId());
		}
		// 未关联团队的小区列表
		else if (teamFo.getIfTeamRel() == -1)
		{
			// 查询根据teamId查找父级团队
			TeamBean team = _teamDao.selectTeamInfoOne(teamFo);
			int p = team.getParentTeamId();
			// 一级团队，未分配的是全部团队
			if (p == 0)
			{
				areaList = _teamDao.selectAreaList(teamFo.getTeamId(), 0);
			}
			// 子团队，未分配的是上级团队分配的团队
			else
			{
				areaList = _teamDao.selectAreaList(teamFo.getTeamId(), p);
			}
		}
		// 该团队关联的小区列表--已分配的
		else if (teamFo.getIfTeamRel() == 1)
		{
			areaList = _teamDao.selectAreaListByTeam(teamFo.getTeamId());
		} 
		else
		{
			log.info("不获取任何小区列表！");
		}
		log.info("end getAreaList");
		
		if (!areaList.isEmpty())
		{
			// 排序，按照姓氏首字母排列
	    Collections.sort(areaList, new Comparator<Group>() {
	      public int compare(Group o1, Group o2) {
	          String name1=String.valueOf(o1.getGroupName());
	          String name2=String.valueOf(o2.getGroupName());
	          Collator instance = Collator.getInstance(Locale.CHINA);
	          return instance.compare(name1, name2);
	      }
	    });
		}
		
		return areaList;
	}

	/**
	 * 获取人员列表（有根团队，无子团队）/获取某团队下团员信息，需要teamId
	 * 
	 * @param teamFo
	 * @return
	 */
	@Override
	public List<User> getUserList(TeamSelectFo teamFo)
	{
		log.info("into getUserList:");
		List<User> userList = new ArrayList<User>();
		// 某团队下的人员列表
		if (teamFo.getIfMem() == 1)
		{
			userList = _teamDao.selectUserListByTeam(teamFo);
		}
		// 新增团队和变更团队成员（需传入团队id）时展示的团员，
		else if (teamFo.getIfMem() == -1)
		{
			userList = _teamDao.selectAllUser(teamFo.getTeamId());
		}
		log.info("end getUserList");
		
		if (!userList.isEmpty())
		{
			// 排序，按照姓氏首字母排列
	    Collections.sort(userList, new Comparator<User>() {
	      public int compare(User o1, User o2) {
	          String name1=String.valueOf(o1.getUserName());
	          String name2=String.valueOf(o2.getUserName());
	          Collator instance = Collator.getInstance(Locale.CHINA);
	          return instance.compare(name1, name2);
	      }
	    });	
		}
		
		return userList;
	}

	/**
	 * 查询新建子团队的等级
	 */
	@Override
	public int getTeamLevel(int userId)
	{
		int teamLevel = 0;
		int state = ifPermission(userId, 0);
		if (state == 2)
		{
			teamLevel = 1;
		} 
		else if (state >= 0)
		{
			teamLevel = _teamDao.selectTeamLevel(userId);
			if (teamLevel >= 3)
			{
				teamLevel = -1;
				log.info("子团队深度已达上限，不能再新建子他团队！");
			} 
			else
			{
				teamLevel = teamLevel + 1;
			}
		} 
		else
		{
			teamLevel = -1;
			log.info("该用户没有权限新建子团队！");
		}
		return teamLevel;
	}

	/**
	 * 团队首页信息获取
	 * 修改内容：新增负责人和团队关联表，新增selectLeaderInfo，实现多个负责人信息的查询，修改selectTeamInfo将里面的leader信息删掉
	 */
	@Override
	public List<Map<String, Object>> getTeamPage(TeamSelectFo teamFo)
	{
		log.info("into getTeamPage:");
		int userId = teamFo.getUserId();
		int state = ifPermission(userId, teamFo.getTeamId());
		// 多级团队依次展开
		List<Map<String, Object>> teams = new ArrayList<Map<String, Object>>();
		List<TeamBean> teamList = new ArrayList<TeamBean>();
		if (state >= 0)
		{
			StringBuffer teamIdss =new StringBuffer();
			if (state == 2)
			{
				teamFo.setIfAdmin(2);
				// 是平台管理员，则显示所有一级团队（多个）
				teamList = _teamDao.selectTeamInfo(teamFo);
				log.debug("teamList:" + teamList);
				for (int j = 0; j < teamList.size(); j++)
				{
					teamIdss.append(teamList.get(j).getTeamId()).append(",");
				}
				for (int j = 0; j < teamList.size(); j++)
				{
					// 将该团队的操作权限设置为可操作
					teamList.get(j).setIfOp(1);
					teamList.get(j).setIsParent(1);
					// 获取leaderId和leaderName以及houseCnt
					if (_teamDao.selectCharge(userId, teamList.get(j).getTeamId()) > 0) {
						teamList.get(j).setIsLeader(1);
					} else {
						teamList.get(j).setIsLeader(0);
					}
					teamList.get(j).setHouseCnt(Integer.parseInt(setHouseCnt(teamList.get(j).getTeamId()).get("houseCnt").toString()));
					teamList.get(j).setLeaderId(Integer.parseInt(setLeaders(teamList.get(j).getTeamId()).get("id").toString()));
					teamList.get(j).setLeaderName(setLeaders(teamList.get(j).getTeamId()).get("name").toString());
					Map<String, Object> oneTeam = new HashMap<String, Object>();
					oneTeam.put("teamIds",teamIdss.substring(0, teamIdss.length()-1));
					oneTeam.put("teamBean1", teamList.get(j));
					oneTeam.put("ifAdmin", 1);
					teams.add(oneTeam);
				}
				log.debug("teams:" + JSONObject.toJSONString(teams));
			}
			// 获取自己所管理的团队信息
			else
			{
				// 根据用户id获取TeamId，查询团队基本信息
				List<Map<String, Object>> teamIds = _teamDao.selectLeaderInfo(0, userId);
				for (int j = 0; j < teamIds.size(); j++)
				{
					teamFo.setTeamId(Integer.parseInt(teamIds.get(j).get("team_id").toString()));
					teamList.add(_teamDao.selectTeamInfoOne(teamFo));
					teamIdss.append(teamIds.get(j).get("team_id")).append(",");
					log.debug("teamList:"+teamList);
				}
				for (int k = 0; k < teamList.size(); k++)
				{
					// 当前团队信息
					teamList.get(k).setIfOp(1);
					if (_teamDao.selectCharge(userId, teamList.get(k).getTeamId()) > 0) {
						teamList.get(k).setIsLeader(1);
					} else {
						teamList.get(k).setIsLeader(0);
					}
					int stateTwo=isParent(userId, teamList.get(k).getTeamId());
					log.debug("teamId:"+teamList.get(k).getTeamId());
					log.debug("stateTwo:"+stateTwo);
					if(stateTwo==3)
					{
						teamList.get(k).setIsParent(1);
					}
					// 获取leaderId和leaderName
					teamList.get(k).setLeaderId(Integer.parseInt(setLeaders(teamList.get(k).getTeamId()).get("id").toString()));
					teamList.get(k).setLeaderName(setLeaders(teamList.get(k).getTeamId()).get("name").toString());
					// 获取关联房源数
					teamList.get(k).setHouseCnt(Integer.parseInt(setHouseCnt(teamList.get(k).getTeamId()).get("houseCnt").toString()));
					Map<String, Object> teamSelf = new HashMap<String, Object>();
					teamSelf.put("teamBean" + teamList.get(k).getTeamLevel(), teamList.get(k));
					teamSelf.put("teamIds", teamIdss.substring(0, teamIdss.length()-1));
					int p = teamList.get(k).getParentTeamId();
					while (p != 0)
					{
						teamFo.setTeamId(p);
						TeamBean team = _teamDao.selectTeamInfoOne(teamFo);
						stateTwo=isParent(userId, p);
						if(stateTwo==3)
						{
							team.setIsParent(1);
						}
						if (_teamDao.selectCharge(userId, team.getTeamId()) > 0) {
							team.setIsLeader(1);
						} else {
							team.setIsLeader(0);
						}
						// 获取leaderId和leaderName
						team.setLeaderId(Integer.parseInt(setLeaders(team.getTeamId()).get("id").toString()));
						team.setLeaderName(setLeaders(team.getTeamId()).get("name").toString());
						// 获取关联房源数
						team.setHouseCnt(Integer.parseInt(setHouseCnt(team.getTeamId()).get("houseCnt").toString()));
						teamSelf.put("teamBean" + team.getTeamLevel(), team);
						p = team.getParentTeamId();
					}
					teams.add(teamSelf);
				}
				log.debug("teams:" + teams);
				log.debug("teams:" + JSONObject.toJSONString(teams));
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end getTeamPage");
		return teams;
	}

	/**
	 * 通过TeamId设置leaderId和leaderName的值
	 * 
	 * @param teamId
	 * @return
	 */
	private Map<String, Object> setLeaders(int teamId)
	{
		Map<String, Object> leaders = new HashMap<String, Object>();
		List<Map<String, Object>> leaderInfo = _teamDao.selectLeaderInfo(teamId, 0);
		if (leaderInfo == null || leaderInfo.size() == 0)
		{
			leaders.put("id", 0);
			leaders.put("name", "无");
		} 
		else
		{
			if (leaderInfo.size() > 1)
			{
				leaders.put("name", leaderInfo.get(0).get("name").toString() + "...");
			} 
			else
			{
				leaders.put("name", leaderInfo.get(0).get("name").toString());
			}
			leaders.put("id", Integer.parseInt(leaderInfo.get(0).get("id").toString()));
		}
		return leaders;
	}

	/**
	 * 获取团队与小区关联房源数量
	 * 
	 * @param teamId
	 * @return
	 */
	private Map<String, Object> setHouseCnt(int teamId)
	{
		Map<String, Object> houseCnt = new HashMap<String, Object>();
		// 根据团队id判断此团队是否关联小区
		if (_teamDao.selectIfRelArea(teamId) > 0)
		{
			// 是的话，计算小区下的房源数量
			houseCnt.put("houseCnt", _teamDao.selectHouseCnt(teamId));
		} 
		else
		{
			// 不是，房源数量为0
			houseCnt.put("houseCnt", 0);
		}
		return houseCnt;
	}

	/**
	 * 人员与小区关联房源
	 * 
	 * @param memId
	 * @return
	 */
	private Map<String, Object> setMemCnt(int memId, int teamId)
	{
		Map<String, Object> memCnt = new HashMap<String, Object>();
		// 判断人员是否关联小区
		log.debug("teamId:" + teamId);
		if (_teamDao.selectIfRelMember(memId, teamId) > 0)
		{
			memCnt.put("memCnt", _teamDao.selectMemberHCnt(memId, teamId));
		} 
		else
		{
			memCnt.put("memCnt", 0);
		}
		return memCnt;
	}

	/**
	 * 点击团队名称获取团队子团队信息
	 * 
	 * @param teamFo
	 * @return
	 */
	@Override
	public Map<String, Object> getTeamByTeamId(TeamSelectFo teamFo)
	{
		log.info("into getTeamByTeamId:");
		int userId = teamFo.getUserId();
		TeamBean team = new TeamBean();
		int state = ifPermission(userId, teamFo.getTeamId());
		Map<String, Object> teamInfo = new HashMap<String, Object>();
		if (teamFo.getTeamId() == 0)
		{
			log.info("请点击团队进行子团队查看！");
		}
		else
		{
			// 根据所选团队id获取此团队下的子团队
			teamFo.setIfSubTeam(1);
			team.setSubTeam(_teamDao.selectTeamInfo(teamFo));
			if (state < 1) {
				// 若不是其团队负责人或父团队负责人，要判断负责人是否是下级团队的负责人
				for (Iterator<TeamBean> iterator = team.getSubTeam().iterator(); iterator.hasNext();)
				{
					TeamBean teamBean = (TeamBean) iterator.next();
					if (!this.isSubTeamLeader(teamBean.getTeamId(), userId) && teamBean.getLeaderId() != userId) {
						iterator.remove();
					} else {
						if (_teamDao.selectCharge(userId, teamBean.getTeamId()) > 0) {
							teamBean.setIsLeader(1);
						} else {
							teamBean.setIsLeader(0);
						}
					}
				}
			} else {
				List<Integer> teamIds = new ArrayList<>();
				for (Iterator<TeamBean> iterator = team.getSubTeam().iterator(); iterator.hasNext();)
				{
					TeamBean teamBean = (TeamBean) iterator.next();
					if (teamIds.contains(teamBean.getTeamId())) {
						iterator.remove();
					} else {
						teamIds.add(teamBean.getTeamId());
					}
				}
			}
			if (state == 2) {
				teamInfo.put("ifAdmin", 1);
			}
			teamInfo.put("teamList", team.getSubTeam());
			for (int i = 0; i < team.getSubTeam().size(); i++)
			{
				int stateTwo=isParent(userId, team.getSubTeam().get(i).getTeamId());
				if(stateTwo==3)
				{
					team.getSubTeam().get(i).setIsParent(1);
				}
				if (_teamDao.selectCharge(userId, team.getSubTeam().get(i).getTeamId()) > 0) {
					team.getSubTeam().get(i).setIsLeader(1);
				} else {
					team.getSubTeam().get(i).setIsLeader(0);
				}
				team.getSubTeam().get(i).setLeaderId(Integer.parseInt(setLeaders(team.getSubTeam().get(i).getTeamId()).get("id").toString()));
				team.getSubTeam().get(i).setLeaderName(setLeaders(team.getSubTeam().get(i).getTeamId()).get("name").toString());
				// 判断每一个子团队是否关联小区
				team.getSubTeam().get(i).setHouseCnt(Integer.parseInt(setHouseCnt(team.getSubTeam().get(i).getTeamId()).get("houseCnt").toString()));
			}
			// 根据所选团队，显示团队下的成员
			team.setMemberList(_teamDao.selectMemberListById(teamFo.getTeamId(), ""));
			teamInfo.put("memberList", state >= 1?team.getMemberList() : new ArrayList<>());
			for (int j = 0; j < team.getMemberList().size(); j++)
			{
				team.getMemberList().get(j).setMemIfOp(1);
				team.getMemberList().get(j).setMemCnt(Integer.parseInt(setMemCnt(team.getMemberList().get(j).getMemberId(), teamFo.getTeamId()).get("memCnt").toString()));
			}
		}
		log.info("end getTeamByTeamId");
		log.debug("teamInfo:" + JSONObject.toJSONString(teamInfo));
		return teamInfo;
	}
	// 递归判断子团队中是否存在是我管理的团队
	private boolean isSubTeamLeader(int teamId, int userId) {
		TeamSelectFo teamFo = new TeamSelectFo();
		teamFo.setTeamId(teamId);
		teamFo.setIfSubTeam(1);
		for (TeamBean teamBean : _teamDao.selectTeamInfo(teamFo))
		{
			log.debug("duanyongrui   ---------- "+teamBean.getLeaderId());
			if (userId == teamBean.getLeaderId()) {
				return true;
			} else {
				if (this.isSubTeamLeader(teamBean.getTeamId(), userId) == true) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 通过关键字查询，查询选项分为根据团队姓名和人员姓名查询
	 * state为1是该团队负责人，0是团队负责人，2平台管理员，3父团队负责人
	 * 
	 * @param search_type,search_content,user_id
	 */
	@Override
	public List<Map<String, Object>> getTeamByDimSearch(TeamSelectFo teamFo)
	{
		log.info("into getTeamByDimSearch:");
		// 返回类型定义
		List<Map<String, Object>> teamList = new ArrayList<Map<String, Object>>();
		// 要搜索的类型
		int searchType = teamFo.getSearchType();
		// 要搜索的内容
		// String searchContent=teamFo.getSearchContent();
		String searchContent = teamFo.getTeamName();
		int userId = teamFo.getUserId();
		searchContent = EmojiParser.parseToAliases(searchContent);
		// 团队id
		int teamId = 0;
		int state = ifPermission(userId, teamFo.getTeamId());
		if (state >= 0)
		{
			// 1是按照团队名称搜索，2是按照团员名称搜索
			// selectMemberListById根据团队id查找团队下人员id/成员姓名--模糊
			if (searchType == 1)
			{
				String teamName = searchContent;
				// 通过团队名称模糊匹配查找团队id
				List<Map<String, Integer>> teamIds = _teamDao.selectTeamIdList(teamName);
				// teamList=getTeamBeanList(teamIds,state,userId,teamId,teamFo);
				if (teamIds != null && teamIds.size() > 0)
				{
					// 遍历符合要求的TeamId
					for (int i = 0; i < teamIds.size(); i++)
					{
						teamId = teamIds.get(i).get("teamId");
						teamFo.setTeamId(teamId);
						Map<String, Object> teamSelf = new HashMap<String, Object>();
						// 最低级别的团队放置
						TeamBean team = _teamDao.selectTeamInfoOne(teamFo);
						if (_teamDao.selectCharge(userId, team.getTeamId()) > 0) {
							team.setIsLeader(1);
						} else {
							team.setIsLeader(0);
						}
						team.setHouseCnt(Integer.parseInt(setHouseCnt(team.getTeamId()).get("houseCnt").toString()));
						team.setLeaderId(Integer.parseInt(setLeaders(team.getTeamId()).get("id").toString()));
						team.setLeaderName(setLeaders(team.getTeamId()).get("name").toString());
						team.setIfOp(1);
						if(state==2)
						{
							team.setIsParent(1);
							teamSelf.put("teamBean" + team.getTeamLevel(), team);
							teamSelf.put("ifAdmin", 1);
							int p = team.getParentTeamId();
							while (p != 0)
							{
								teamFo.setTeamId(p);
								TeamBean teamNext = _teamDao.selectTeamInfoOne(teamFo);
								if (_teamDao.selectCharge(userId, teamNext.getTeamId()) > 0) {
									teamNext.setIsLeader(1);
								} else {
									teamNext.setIsLeader(0);
								}
								teamNext.setHouseCnt(Integer.parseInt(setHouseCnt(teamNext.getTeamId()).get("houseCnt").toString()));
								teamNext.setLeaderId(Integer.parseInt(setLeaders(teamNext.getTeamId()).get("id").toString()));
								teamNext.setLeaderName(setLeaders(teamNext.getTeamId()).get("name").toString());
								teamNext.setIfOp(1);
								teamNext.setIsParent(1);
								teamSelf.put("teamBean" + teamNext.getTeamLevel(), teamNext);
								p = teamNext.getParentTeamId();
							}
							teamList.add(teamSelf);
						}
						else
						{
							int stateTwo=isParent(userId, teamId);
							if(stateTwo==3)
							{
								team.setIsParent(1);
							}
							int stateThree=ifPermission(userId, teamId);
							if(stateTwo==3||stateThree>=1)
							{
								teamSelf.put("teamBean" + team.getTeamLevel(), team);
							  // 判断是否是该用户权限下可以查看的内容
								// teamList.add(teamSelf);
								int p = team.getParentTeamId();
								while (p != 0)
								{
									teamFo.setTeamId(p);
									TeamBean teamNext = _teamDao.selectTeamInfoOne(teamFo);
									stateTwo=isParent(userId, p);
									if(stateTwo==3)
									{
										teamNext.setIsParent(1);
									}
									if (_teamDao.selectCharge(userId, teamNext.getTeamId()) > 0) {
										teamNext.setIsLeader(1);
									} else {
										teamNext.setIsLeader(0);
									}
									teamNext.setHouseCnt(Integer.parseInt(setHouseCnt(teamNext.getTeamId()).get("houseCnt").toString()));
									teamNext.setLeaderId(Integer.parseInt(setLeaders(teamNext.getTeamId()).get("id").toString()));
									teamNext.setLeaderName(setLeaders(teamNext.getTeamId()).get("name").toString());
									teamNext.setIfOp(0);
									teamSelf.put("teamBean" + teamNext.getTeamLevel(), teamNext);
									p = teamNext.getParentTeamId();
								}
								teamList.add(teamSelf);
							}
							else
							{
								log.info("该团队不符合要求");
							}
						}
					}
				} else
				{
					log.info("没有找到符合的团队！");
				}
			}
			// 添加团员信息
			else if (searchType == 2)
			{
				String memName = searchContent;
				List<TeamRelation> teamIds = _teamDao.selectMemberListById(0, memName);
				if (teamIds != null && teamIds.size() > 0)
				{
					for (int i = 0; i < teamIds.size(); i++)
					{
						TeamRelation memList = new TeamRelation();
						memList = teamIds.get(i);
						// 设置人员可操作
						memList.setMemIfOp(1);
						// 设置人员关联的房源数量
						teamId = teamIds.get(i).getTeamId();
						memList.setMemCnt(Integer.parseInt(setMemCnt(memList.getMemberId(), teamId).get("memCnt").toString()));
						teamFo.setTeamId(teamId);
						Map<String, Object> teamSelf = new HashMap<String, Object>();
						// 最低级别的团队放置
						TeamBean team_ = _teamDao.selectTeamInfoOne(teamFo);
						net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(team_);
						TeamBean team = (TeamBean) net.sf.json.JSONObject.toBean(json, TeamBean.class);
						team.setHouseCnt(Integer.parseInt(setHouseCnt(team.getTeamId()).get("houseCnt").toString()));
						team.setLeaderId(Integer.parseInt(setLeaders(team.getTeamId()).get("id").toString()));
						team.setLeaderName(setLeaders(team.getTeamId()).get("name").toString());
						team.setIfOp(1);
						// 添加队员
						team.getMemberList().add(memList);
						// 防止重复添加团队
						boolean isExist = false;
						for (Iterator<Map<String, Object>> iterator = teamList.iterator(); iterator.hasNext();)
						{
							
							Map<String, Object> teamsMap = (Map<String, Object>) iterator.next();
							Object tempTeamBeam = teamsMap.get("teamBean" + memList.getTeamLevel());
							if (tempTeamBeam != null && ((TeamBean)tempTeamBeam).getTeamId() == memList.getTeamId()) {
								((TeamBean)tempTeamBeam).getMemberList().add(memList);
								log.debug("leng1---"+JSONObject.toJSONString(tempTeamBeam));
								((TeamBean)tempTeamBeam).setIfOp(((TeamBean)tempTeamBeam).getIfOp() | team.getIfOp());
								log.debug("leng2---"+JSONObject.toJSONString(team));
								
								log.debug("leng3---"+JSONObject.toJSONString(tempTeamBeam));
								isExist = true;
								break;
							}
						}
						if (isExist) {
							continue;
						}
						// 判断是否是该用户权限下可以查看的内容
						if(state==2)
						{
							team.setIsParent(1);
							teamSelf.put("teamBean" + team.getTeamLevel(), team);
							teamSelf.put("ifAdmin", 1);
							int p = team.getParentTeamId();
							while (p != 0)
							{
								teamFo.setTeamId(p);
								TeamBean teamNext = _teamDao.selectTeamInfoOne(teamFo);
								teamNext.setHouseCnt(Integer.parseInt(setHouseCnt(teamNext.getTeamId()).get("houseCnt").toString()));
								teamNext.setLeaderId(Integer.parseInt(setLeaders(teamNext.getTeamId()).get("id").toString()));
								teamNext.setLeaderName(setLeaders(teamNext.getTeamId()).get("name").toString());
								teamNext.setIfOp(1);
								teamNext.setIsParent(1);
								teamSelf.put("teamBean" + teamNext.getTeamLevel(), teamNext);
								p = teamNext.getParentTeamId();
							}
							teamList.add(teamSelf);
						}
						// 不是平台管理员
						else
						{
							int stateTwo=isParent(userId, teamId);
							if(stateTwo==3)
							{
								team.setIsParent(1);
							}
							int stateThree=ifPermission(userId, teamId);
							if(stateTwo==3||stateThree>=1)
							{
								teamSelf.put("teamBean" + team.getTeamLevel(), team);
								log.debug("teamSelf:"+JSONObject.toJSONString(teamSelf));
								// teamList.add(teamSelf);
								int p = team.getParentTeamId();
								while (p != 0)
								{
									teamFo.setTeamId(p);
									TeamBean teamNext = _teamDao.selectTeamInfoOne(teamFo);
									stateTwo=isParent(userId, p);
									if(stateTwo==3)
									{
										teamNext.setIsParent(1);
									}
									teamNext.setHouseCnt(Integer.parseInt(setHouseCnt(teamNext.getTeamId()).get("houseCnt").toString()));
									teamNext.setLeaderId(Integer.parseInt(setLeaders(teamNext.getTeamId()).get("id").toString()));
									teamNext.setLeaderName(setLeaders(teamNext.getTeamId()).get("name").toString());
									teamNext.setIfOp(0);
									teamSelf.put("teamBean" + teamNext.getTeamLevel(), teamNext);
									p = teamNext.getParentTeamId();
								}
								teamList.add(teamSelf);
							}
							else
							{
								log.debug("团队不符合要求！");
							}
						}
					}
				} 
				else
				{
					log.info("没有找到符合的团队！");
				}
			}
		} 
		else
		{
			log.info("权限不够！");
		}
		log.debug("teamList:" + teamList);
		log.info("end getTeamByDimSearch");
		return teamList;
	}

	/**
	 * 新增团队，需操作上表cf_team，cf_team_rel，cf_team_leaders
	 * 传参，teamId若为0则新增一级团队，teamLevel,teamName,memberList,leaderList
	 * 
	 */
	@Override
	public int addTeam(TeamSelectFo teamFo)
	{
		TeamBean team = new TeamBean();
		log.info("into insertTeam");
		int userId = teamFo.getUserId();
		int state = ifPermission(userId, teamFo.getTeamId());
		if (state >= 0)
		{
			// 新建一级团队不需传入teamId
			team.setParentTeamId(teamFo.getParentTeamId());
			team.setTeamLevel(teamFo.getTeamLevel());
			team.setCreateId(userId);
			team.setModifyId(userId);
			// 判断团队名是否唯一
			if (_teamDao.selectUniName(teamFo.getTeamName(), 0) > 0)
			{
				log.info("团队名称不唯一！");
				state = -2;
			} 
			else
			{
				log.info("团队名称唯一");
				team.setTeamName(teamFo.getTeamName());
				try
				{
					// 新增团队，在cf_team表中新增记录
					int i = _teamDao.insertTeam(team);
					if (i < 1)
					{
						state = -1;
						log.info("新增团队失败！");
						return state;
					} 
					else
					{
						log.info("新增团队成功！");
						// 新增团队成功后获取主键及teamId
						log.debug("teamId:" + team.getTeamId());
						// 新增团队关联，将成员添加到cf_team_rel表中，并将成员中是leader的值注明出来
						// 同时将在无主队中的人员删除
						for (int w = 0; w < teamFo.getMemberList().size(); w++)
						{
							TeamRelation teamRel = new TeamRelation();
							for (int k = 0; k < teamFo.getLeaderList().size(); k++)
							{
								if (teamFo.getMemberList().get(w).getUserId() == teamFo.getLeaderList().get(k).getLeaderId())
								{
									teamRel.setIfCharge(1);
									break;
								} 
								else
								{
									teamRel.setIfCharge(0);
								}
							}
							teamRel.setMemberId(teamFo.getMemberList().get(w).getUserId());
							teamRel.setTeamId(team.getTeamId());
							teamRel.setTopTeamId(teamFo.getTopTeamId());
							int j = _teamDao.insertTeamRelation(teamRel);
							// 删除无主队中的记录
							if (_teamDao.selectMemInNull(teamFo.getMemberList().get(w).getUserId()) >= 1)
							{
								_teamDao.deleteMemInNull(teamFo.getMemberList().get(w).getUserId());
								log.info("删除无主队信息成功！");
							}
							if (j < 1)
							{
								state = -1;
								log.info("插入关联记录失败！");
								return state;
							} 
							else
							{
								state = 1;
								log.info("插入关联记录成功！");
							}
						}
						// 新增团队负责人记录
						// insertTeamLeader
						String newUserId = "";
						TeamLeaders leaders = new TeamLeaders();
						for (int m = 0; m < teamFo.getLeaderList().size(); m++)
						{
							leaders.setTeamId(team.getTeamId());
							leaders.setLeaderId(teamFo.getLeaderList().get(m).getLeaderId());
							newUserId =  teamFo.getLeaderList().get(m).getLeaderId() + ",";
							int n = _teamDao.insertTeamLeader(leaders);
							if (n < 1)
							{
								state = -1;
								log.info("新增团队负责人记录失败！");
								return state;
							} 
							else
							{
								state = 1;
								// 新增负责人
								if (StringUtils.isNotBlank(newUserId) || !newUserId.equals("null"))
								{
									newUserId = newUserId.substring(0, newUserId.length() - 1);
								}
								insertLeaderRole(newUserId);
								log.info("新增团队负责人记录成功！");
							}
						}
						// 新增一级团队关联所有小区操作
						if(teamFo.getTeamLevel()==1)
						{
							// 在团队关联小区中插入小区记录
							List<Group> allGroup=new ArrayList<Group>();
							allGroup=_teamDao.selectAreaList(0, 0);
							for(int n=0;n<allGroup.size();n++)
							{
								TeamRelHouse relArea=new TeamRelHouse();
								relArea.setAreaId(allGroup.get(n).getGroupId());
								relArea.setTeamId(team.getTeamId());
								relArea.setTopTeamId(team.getTeamId());
								_teamDao.insertAreaRel(relArea);
							}
						}
					}
				} 
				catch (Exception e)
				{
					log.error("e:", e);
				}
			}
		} 
		else
		{
			log.info("身份信息不合法!");
			return state;
		}
		log.info("end insertTeam");
		return state;
	}

	/** 判断是否是父级团队 **/
	private int isParent(int userId,int teamId)
	{
		int state=ifPermission(userId, teamId);
		// 父团队负责人
		if(state==3)
		{
			return state;
		}
		// 平台管理员
		else if(state==2)
		{
			state=3;
			return state;
		}
		// 本团队负责人
		else if(state>=1)
		{
		  // 查询父团队id
			if (_teamDao.selectParentTeamId(teamId) == null || _teamDao.selectParentTeamId(teamId).size() == 0)
			{
				state = -1;
				log.info("只有本团队的权限！");
				return state;
			} 
			else
			{
				String pId = String.valueOf(_teamDao.selectParentTeamId(teamId).get("parent_teamid"));
				int parentTeamId = Integer.parseInt(pId);
				// 根据父团队id，找他的上级团队
				if (_teamDao.selectParentTeamId(parentTeamId) == null || _teamDao.selectParentTeamId(parentTeamId).size() == 0)
				{
					state = -1;
					log.info("没有上级团队！");
					return state;
				} 
				else
				{
					List<Map<String, Object>> leaders = _teamDao.selectLeaderInfo(parentTeamId, 0);
					log.debug("userId:" + userId);
					if (leaders == null || leaders.size() == 0)
					{
						state = -1;
						log.info("团队负责人信息为空！");
					} 
					else
					{
						for (int i = 0; i < leaders.size(); i++)
							if (Integer.parseInt(leaders.get(i).get("id").toString()) == userId)
							{
								state = 3;
								log.info("该用户有权限操作此团队");
								return state;
							}
							else
							{
								teamId = parentTeamId;
							}
					}
				}
				return isParent(userId, teamId);
			}
		}
		else if(state==-1)
		{
			return state;
		}
		return state;
	}
	
	
	/**
	 * 新增团员，与变更团员重复
	 */
	public int addTeamMember(TeamSelectFo teamFo)
	{
		int state = 0;
		state = ifPermission(teamFo.getUserId(), teamFo.getTeamId());
		if (state > 0)
		{
			for (int i = 0; i < teamFo.getMemberList().size(); i++)
			{
				TeamRelation member = new TeamRelation();
				member.setIfCharge(0);
				member.setTopTeamId(teamFo.getTopTeamId());
				member.setMemberId(teamFo.getMemberList().get(i).getUserId());
				member.setTeamId(teamFo.getTeamId());
				if (_teamDao.insertTeamRelation(member) > 0)
				{
					state = 1;
					log.info("新增团员成功!");
				} 
				else
				{
					state = -1;
					log.info("新增团员失败!");
					return state;
				}
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		return state;
	}

	public void removeRec(TeamBean team)
	{
		// 查询出所有下级
		List<Map<String, Object>> teamIds = _teamDao.selectSubTeamIdsAndLeaderIds(team.getTeamId());
		if (teamIds == null || teamIds.size() == 0)
		{
			log.debug("没有要删除的下级团队");
			return;
		} 
		else
		{
			// 循环下级
			for (int i = 0; i < teamIds.size(); i++)
			{
				int id = Integer.parseInt(teamIds.get(i).get("team_id").toString());
				team.setTeamId(id);
				// 删除
				try
				{
					// 删除团队表
					_teamDao.deleteTeam(team);
					// 删除团队关联表
					TeamSelectFo teamFo = new TeamSelectFo();
					teamFo.setIfMem(1);
					List<User> memberList = getUserList(teamFo);
					for (int j = 0; j < memberList.size(); j++)
					{
						// 判断人员是否隶属其他团队管理，若无则加入无主队中
						if (_teamDao.selectMemInOther(team.getTeamId(), memberList.get(j).getUserId()) > 0)
						{
							log.info("还隶属其他团队");
						} 
						else
						{
							TeamRelation teamRel = new TeamRelation();
							teamRel.setTeamId(-2);
							teamRel.setTopTeamId(-2);
							teamRel.setMemberId(memberList.get(i).getUserId());
							teamRel.setIfCharge(0);
							int n = _teamDao.insertTeamRelation(teamRel);
							if (n < 1)
							{
								log.info("加入无主队失败！");
								return;
							} 
							else
							{
								log.info("加入无主队成功！");
							}
						}
					}
					_teamDao.deleteTeamMember(0, id);
					TeamRelation teamRel = new TeamRelation();
					TeamRelHouse relArea = new TeamRelHouse();
					relArea.setTeamId(id);
					teamRel.setTeamId(id);
					_teamDao.deleteAreaRel(relArea);
					_teamDao.deleteMemberRelAreaList(teamRel);
				} 
				catch (Exception e)
				{
					log.error("e", e);
				}
				removeRec(team);
			}
		}
	}

	/**
	 * 删除团队，涉及表cf_team，cf_team_rel，cf_team_rel_area，cf_team_member_area，cf_team_leaders
	 */
	@Override
	public int removeTeam(TeamSelectFo teamFo)
	{
		TeamBean team = new TeamBean();
		log.info("into deleteTeam");
		teamFo.setIfMem(1);
		int userId = teamFo.getUserId();
		team.setModifyId(userId);
		team.setTeamId(teamFo.getTeamId());
		int state = ifPermission(userId, teamFo.getTeamId());
		if (state >= 1)
		{
			try
			{
				// 删除团队
				_teamDao.deleteTeam(team);
				// 删除团队中人员，先获取该团队的人员列表
				List<User> memberList = getUserList(teamFo);
				for (int i = 0; i < memberList.size(); i++)
				{
					// 判断人员是否隶属其他团队管理，若无则加入无主队中
					if (_teamDao.selectMemInOther(team.getTeamId(), memberList.get(i).getUserId()) > 0)
					{
						log.info("还隶属其他团队");
					} 
					else
					{
						TeamRelation teamRel = new TeamRelation();
						teamRel.setTeamId(-2);
						teamRel.setTopTeamId(-2);
						teamRel.setIfCharge(0);
						teamRel.setMemberId(memberList.get(i).getUserId());
						int n = _teamDao.insertTeamRelation(teamRel);
						if (n < 1)
						{
							log.info("加入无主队失败！");
							state = -1;
							return state;
						} 
						else
						{
							log.info("加入无主队成功！");
							state = 1;
						}
					}
				}
				_teamDao.deleteTeamMember(0, teamFo.getTeamId());
				log.debug("getUserList:" + getUserList(teamFo));
				TeamRelHouse relArea = new TeamRelHouse();
				TeamRelation teamRel = new TeamRelation();
				relArea.setTeamId(teamFo.getTeamId());
				teamRel.setTeamId(teamFo.getTeamId());
				// 删除团队与小区关联
				_teamDao.deleteAreaRel(relArea);
				// 删除人员与小区关联
				_teamDao.deleteMemberRelAreaList(teamRel);
				// 删除该团队的负责人记录
				_teamDao.delTeamLeaderByTeamId(teamFo.getTeamId(),0);

			} 
			catch (Exception e)
			{
				log.error("e", e);
			}
			removeRec(team);

		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end deleteTeam");
		return state;
	}

	/**
	 * 更改团队名称
	 */
	@Override
	public int modifyTeamName(TeamSelectFo teamFo)
	{
		log.info("into modifyTeamName");
		int userId = teamFo.getUserId();
		TeamBean team = new TeamBean();
		int state = 0;
		state = ifPermission(userId, teamFo.getTeamId());
		if (state >= 1)
		{
			team.setModifyId(userId);
			team.setTeamName(teamFo.getTeamName());
			team.setTeamId(teamFo.getTeamId());
			if (_teamDao.selectUniName(teamFo.getTeamName(), teamFo.getTeamId()) == 0)
			{
				_teamDao.updateTeamName(team);
				log.info("团队名称修改完成！");
				state = 1;
			} 
			else
			{
				log.info("团队名称重复，请重新输入！");
				state = -1;
				return state;
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end modifyTeamName");
		return state;
	}

	/**
	 * 变更团队负责人，删除原先的团队负责人项，增加新的团队负责人
	 * 涉及表，cf_team_leaders和cf_team_rel
	 */
	@Override
	public int modifyTeamCharge(TeamSelectFo teamFo)
	{
		log.info("into modifyTeamCharge");
		log.debug("teamFo:"+teamFo);
		TeamBean team = new TeamBean();
		int userId = teamFo.getUserId();
		int state = ifPermission(userId, teamFo.getTeamId());
		if (state >= 1)
		{
			team.setLeaderList(teamFo.getLeaderList());
			team.setTeamId(teamFo.getTeamId());
			if (teamFo.getLeaderList() == null || teamFo.getLeaderList().size() == 0)
			{
				log.info("没有要新增的负责人！");
			} 
			else
			{
				// 获取原来的负责人信息
				List<Map<String, Object>> oldUserIds = _teamDao.selectLeaderInfo(teamFo.getTeamId(), 0);
				// 删除原先的负责人
				int n = _teamDao.delTeamLeaderByTeamId(teamFo.getTeamId(),0);
				
				// 将其对应的team_rel中的负责人的ifCharge值更新为0
				_teamDao.updateTeamCharge(0,teamFo.getTeamId(), 0);
				if (n < 1)
				{
					log.info("没有要删除的负责人！");
					state = 1;
				} 
				else
				{
					log.info("删除原先的负责人成功！");
					state = 1;
				}
				
				// 新增选定的负责人
				String newUserId = "";
				for (int j = 0; j < teamFo.getLeaderList().size(); j++)
				{
					TeamLeaders leaders = new TeamLeaders();
					leaders.setLeaderId(teamFo.getLeaderList().get(j).getLeaderId());
					log.debug("leaderId:"+teamFo.getLeaderList().get(j).getLeaderId());
					newUserId =  teamFo.getLeaderList().get(j).getLeaderId() + ",";
					leaders.setTeamId(teamFo.getTeamId());
					int m = _teamDao.insertTeamLeader(leaders);
				  // 将其对应的team_rel中的负责人的ifCharge值更新为1
					_teamDao.updateTeamCharge(1,teamFo.getTeamId(), teamFo.getLeaderList().get(j).getLeaderId());
					if (m < 1)
					{
						log.info("新增团队负责人失败！");
						state = -1;
					} 
					else
					{
						log.info("新增团队负责人成功！");
						state = 1;
						// 查询原负责人
						String oldUserId = "";
						for (Map<String, Object> userIdMap : oldUserIds)
						{
							String userIdStr = String.valueOf(userIdMap.get("id"));
							if (StringUtils.isNotBlank(userIdStr) || !userIdStr.equals("null"))
							{
								oldUserId += userIdStr + ",";
							}
						}
						if (StringUtils.isNotBlank(oldUserId) || !oldUserId.equals("null"))
						{
							oldUserId = oldUserId.substring(0, oldUserId.length() - 1);
						}
						log.info("变更负责人，原来的负责人信息：" + oldUserIds);
						deleteLeaderRole(oldUserId);
						// 新增负责人
						if (StringUtils.isNotBlank(newUserId) || !newUserId.equals("null"))
						{
							newUserId = newUserId.substring(0, newUserId.length() - 1);
						}
						insertLeaderRole(newUserId);
					}
				}
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end modifyTeamCharge");
		return state;
	}

	/**
	 * 变更团队成员
	 */
	@Override
	@Transactional
	public int modifyTeamMember(TeamRelFo teamRelFo)
	{
		log.info("into modifyTeamMember");
		log.debug("teamRelFo:" + teamRelFo);
		int userId = teamRelFo.getUserId();
		int state = ifPermission(userId, teamRelFo.getTeamId());
		if (state >= 1)
		{
		  // 移除成员
			if (teamRelFo.getRemoveMemberList() == null||teamRelFo.getRemoveMemberList().size()==0)
			{
				state = 1;
				log.info("没有要删除的人员！");
			}
			else
			{
			  // 判断是否移除了所有的负责人
				if(teamRelFo.getRemoveMemberList() != null&&teamRelFo.getRemoveMemberList().size()>0)
				{
					int n=0;
					for(int i=0;i<teamRelFo.getRemoveMemberList().size();i++)
					{
						if(_teamDao.selectCharge(teamRelFo.getRemoveMemberList().get(i).getUserId(), teamRelFo.getTeamId())>=1)
						{
							n++;
						}
					}
					if(n==_teamDao.ifUiqOne(teamRelFo.getTeamId(),0))
					{
						state=-2;
						log.debug("团队必需保留一个负责人");
						return state;
					}
				}
				int count = -1;
				for (int m = 0; m < teamRelFo.getRemoveMemberList().size(); m++)
				{
					int i = _teamDao.deleteTeamMember(teamRelFo.getRemoveMemberList().get(m).getUserId(), teamRelFo.getTeamId());
					if (i == 0)
					{
						log.info("删除成员失败！");
						state = -1;
						return state;
					} 
					else
					{
						TeamRelation teamRel = new TeamRelation();
						log.info("删除成员成功！");
					  // 判断该成员是否是团队负责人，是的话，将cf_team_leaders里对应的信息删掉
						if(_teamDao.selectCharge(teamRelFo.getRemoveMemberList().get(m).getUserId(), teamRelFo.getTeamId())==1)
						{
							_teamDao.delTeamLeaderByTeamId(teamRelFo.getTeamId(),teamRelFo.getRemoveMemberList().get(m).getUserId());
						}
						state = 1;
						int j = _teamDao.selectMemInOther(teamRelFo.getTeamId(), teamRelFo.getRemoveMemberList().get(m).getUserId());
						if (j > 0)
						{
							log.info("该成员还隶属其他团队！");
						} 
						else
						{
							log.info("该成员不隶属其他团队，将加入无主队中");
							teamRel.setTeamId(-2);
							teamRel.setTopTeamId(-2);
							teamRel.setIfCharge(0);
							teamRel.setMemberId(teamRelFo.getRemoveMemberList().get(m).getUserId());
							int n = _teamDao.insertTeamRelation(teamRel);
							if (n < 1)
							{
								log.info("加入无主队失败！");
								state = -1;
								return state;
							} 
							else
							{
								log.info("加入无主队成功！");
								state = 0;
							}
						}
						// 删除人员与小区关联，并提示释放了多少无人管理的房源数
						teamRel.setTeamId(teamRelFo.getTeamId());
						count = _teamDao.selectMemberHCnt(teamRelFo.getMemberId(), teamRelFo.getTeamId());
						_teamDao.deleteMemberRelAreaList(teamRel);
					}
					count = count + count;
				}
				return count;
			}
			// 新增成员
			if (teamRelFo.getMemberList() == null||teamRelFo.getMemberList().size()==0)
			{
				state = 1;
				log.info("没有要变更的人员！");
			} 
			else
			{
				for (int n = 0; n < teamRelFo.getMemberList().size(); n++)
				{
					// 新增新成员
					TeamRelation teamRel = new TeamRelation();
					teamRel.setTeamId(teamRelFo.getTeamId());
					teamRel.setMemberId(teamRelFo.getMemberList().get(n).getUserId());
					teamRel.setTopTeamId(teamRelFo.getTopTeamId());
					teamRel.setIfCharge(0);
					int j = _teamDao.insertTeamRelation(teamRel);
				  // 删除无主队中的记录
					if (_teamDao.selectMemInNull(teamRelFo.getMemberList().get(n).getUserId()) >= 1)
					{
						_teamDao.deleteMemInNull(teamRelFo.getMemberList().get(n).getUserId());
						log.info("删除无主队信息成功！");
					}
					if (j == 0)
					{
						log.info("新增成员失败！");
						state = -1;
					} 
					else
					{
						log.info("新增成员成功！");
						state = 1;
					}
				}
			}
			log.debug("teamRelFo:" + teamRelFo);
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.debug("state:"+state);
		log.info("end modifyTeamMember");
		return state;
	}

	/**
	 * 团队关联小区
	 * 修改：移除团队与小区关联关系后会将该团队下人员与此小区，以及子团队和子团队中人员与小区的关联删掉
	 */
	@Override
	public int findAreaRel(TeamRelAreaFo areaFo)
	{
		log.info("into findAreaRel");
		log.debug("areaFo:" + areaFo);
		int userId = areaFo.getUserId();
		int state = ifPermission(userId, areaFo.getTeamId());
		TeamRelHouse relArea = new TeamRelHouse();
		relArea.setTeamId(areaFo.getTeamId());
		relArea.setTopTeamId(1);
		if (state >= 1)
		{
			if (areaFo.getAreaIdList() == null || areaFo.getAreaIdList().size() == 0)
			{
				state = 1;
				log.info("没有要关联的小区！");
			} 
			else
			{
				int n = 0;
				log.debug("areaFo:" + JSONObject.toJSONString(areaFo));
				for (int i = 0; i < areaFo.getAreaIdList().size(); i++)
				{
					relArea.setAreaId(areaFo.getAreaIdList().get(i).getGroupId());
					log.debug("areaId:" + areaFo.getAreaIdList().get(i).getGroupId());
					try
					{
						log.debug("getTeamId:" + relArea.getTeamId());
						log.debug("getTopTeamId:" + relArea.getTopTeamId());
						log.debug("getAreaId:" + relArea.getAreaId());

						_teamDao.insertAreaRel(relArea);
						n++;
					} 
					catch (Exception e)
					{
						log.error("e:", e);
					}
				}
				if (n > 0)
				{
					log.info("团队关联小区成功！");
					state = 1;
				} 
				else
				{
					log.info("关联小区失败！");
					state = -1;
				}
			}
			if (areaFo.getRemoveAreaList() == null || areaFo.getRemoveAreaList().size() == 0)
			{
				state = 1;
				log.info("没有要删除的小区！");
			} 
			else
			{
				for (int i = 0; i < areaFo.getRemoveAreaList().size(); i++)
				{
					log.debug("removeList.size:" + areaFo.getRemoveAreaList().size());
					log.debug("removeList:" + areaFo.getRemoveAreaList().get(i).getGroupId());
					if (areaFo.getRemoveAreaList().get(i).getGroupId() == 0)
					{
						state = 1;
						log.info("没有要删除的小区！");
						return state;
					}
				}
				int m = 0;
				for (int j = 0; j < areaFo.getRemoveAreaList().size(); j++)
				{
					relArea.setAreaId(areaFo.getRemoveAreaList().get(j).getGroupId());
					_teamDao.deleteAreaRel(relArea);
					m++;
				}
				if (m > 0)
				{
					log.info("删除团队关联小区成功！");
					state = 1;
				} 
				else
				{
					log.info("没有要删除的小区关联！");
					state = 1;
				}
				// 删除团队下该团队下人员与此小区，以及子团队和子团队中人员与小区的关联
				List<Map<String,Object>> teamIdList=getTeamIdsByTeamId(relArea.getTeamId());
				if(teamIdList!=null && teamIdList.size()>0)
				{
					for(int i=0;i<teamIdList.size();i++)
					{
						log.debug("teamId:"+JSONObject.toJSONString(teamIdList.get(i).get("team_id")));
						for (int j = 0; j < areaFo.getRemoveAreaList().size(); j++)
						{
							// 根据teamId删除团队与小区关联
							relArea.setTeamId(Integer.parseInt(teamIdList.get(i).get("team_id").toString()));
							relArea.setAreaId(areaFo.getRemoveAreaList().get(j).getGroupId());
							_teamDao.deleteAreaRel(relArea);
						  // 根据teamId删除人员与小区关联
							TeamRelation memberRel=new TeamRelation();
							memberRel.setTeamId(Integer.parseInt(teamIdList.get(i).get("team_id").toString()));
							memberRel.setAreaId(areaFo.getRemoveAreaList().get(j).getGroupId());
							_teamDao.deleteMemberRelAreaList(memberRel);
						}
					}
				}
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end findAreaRel");
		return state;
	}

	/**
	 * 删除团员
	 * 判断是否是该团队唯一一个负责人，是的话，不允许删除
	 * 删除负责人需删除cf_team_leaders表里对应的信息
	 */
	@Override
	public int removeTeamMember(TeamRelFo teamRelFo)
	{
		log.info("into removeTeamMember:");
		int userId = teamRelFo.getUserId();
		TeamRelation teamRel = new TeamRelation();
		teamRel.setTeamId(teamRelFo.getTeamId());
		teamRel.setMemberId(teamRelFo.getMemberId());
		int state = ifPermission(userId, teamRelFo.getTeamId());
		if (state >= 1)
		{
			// 判断是否是该团队唯一的负责人
			int uniqOne=_teamDao.ifUiqOne(teamRel.getTeamId(),teamRelFo.getMemberId());
			if(uniqOne==1)
			{
				state=-2;
				log.debug("不能删除唯一的负责人");
				return state;
			}
			else
			{
			  // 删除团队成员
				int i = _teamDao.deleteTeamMember(teamRelFo.getMemberId(), teamRelFo.getTeamId());
				if (i > 0)
				{
					state = 0;
					log.info("删除成员成功！");
					// 判断该成员是否是团队负责人，是的话，将cf_team_leaders里对应的信息删掉
					if(_teamDao.selectCharge(teamRelFo.getMemberId(), teamRelFo.getTeamId())==1)
					{
						_teamDao.delTeamLeaderByTeamId(teamRelFo.getTeamId(),teamRelFo.getMemberId());
					}
					// 查询该成员是否隶属其他团队，若无，则加入无主队中--insertTeamRelation
					int j = _teamDao.selectMemInOther(teamRel.getTeamId(), teamRel.getMemberId());
					if (j > 0)
					{
						log.info("该成员还隶属其他团队！");
					} 
					else
					{
						log.info("该成员不隶属其他团队，将加入无主队中");
						teamRel.setTeamId(-2);
						teamRel.setTopTeamId(-2);
						teamRel.setIfCharge(0);
						int n = _teamDao.insertTeamRelation(teamRel);
						if (n < 1)
						{
							log.info("加入无主队失败！");
							state = -1;
							return state;
						} 
						else
						{
							log.info("加入无主队成功！");
							state = 0;
						}
					}
					// 删除人员与小区关联，并提示释放了多少无人管理的房源数
					teamRel.setTeamId(teamRelFo.getTeamId());
					int count = _teamDao.selectMemberHCnt(teamRelFo.getMemberId(), teamRelFo.getTeamId());
					_teamDao.deleteMemberRelAreaList(teamRel);
					if (count > 0)
					{
						return count;
					}
				} 
				else
				{
					state = -1;
					log.info("删除成员失败！");
					return state;
				}
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end removeTeamMember");
		return state;
	}

	/**
	 * 人员关联小区
	 */
	@Override
	public int findAreaMemberRel(TeamRelFo teamFo)
	{
		log.info("into findAreaMemberRel:");
		int userId = teamFo.getUserId();
		int state = ifPermission(userId, teamFo.getTeamId());
		TeamRelation teamRel = new TeamRelation();
		teamRel.setTeamId(teamFo.getTeamId());
		teamRel.setMemberId(teamFo.getMemberId());
		if (state >= 1)
		{
			if (teamFo.getAreaIdList() == null || teamFo.getAreaIdList().size() == 0)
			{
				state = 1;
				log.info("没有要关联小区的人员！");
			} 
			else
			{
				int n = 0;
				for (int i = 0; i < teamFo.getAreaIdList().size(); i++)
				{
					try
					{
						teamRel.setAreaId(teamFo.getAreaIdList().get(i).getGroupId());
						_teamDao.insertMemberRelArea(teamRel);
						n++;
					} 
					catch (Exception e)
					{
						log.error("e:", e);
					}
				}
				if (n > 0)
				{
					log.info("人员关联小区成功！");
				} 
				else
				{
					log.info("人员关联小区失败！");
				}
			}
			if (teamFo.getRemoveAreaList() == null || teamFo.getRemoveAreaList().size() == 0)
			{
				state = 1;
				log.info("没有要删除关联的人员！");
			} 
			else
			{
				for (int j = 0; j < teamFo.getRemoveAreaList().size(); j++)
				{
					teamRel.setAreaId(teamFo.getRemoveAreaList().get(j).getGroupId());
					try
					{
						_teamDao.deleteMemberRelAreaList(teamRel);
					} 
					catch (Exception e)
					{
						log.error("e:", e);
					}
				}
			}
		} 
		else
		{
			log.info("身份信息不合法!");
		}
		log.info("end findAreaMemberRel");
		return state;
	}

	/**
	 * 修改leaderId的获取方式
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public int permission(int teamId, int userId)
	{
		int state = 0;
		// 查询父团队id
		if (_teamDao.selectParentTeamId(teamId) == null || _teamDao.selectParentTeamId(teamId).size() == 0)
		{
			state = -1;
			log.info("输入的团队无对应的父团队id！");
			return state;
		} 
		else
		{
			String pId = String.valueOf(_teamDao.selectParentTeamId(teamId).get("parent_teamid"));
			int parentTeamId = Integer.parseInt(pId);
			// 根据父团队id，找他的团队负责人
			if (_teamDao.selectParentTeamId(parentTeamId) == null || _teamDao.selectParentTeamId(parentTeamId).size() == 0)
			{
				state = -1;
				log.info("输入的团队无对应的信息！");
				return state;
			} 
			else
			{
				List<Map<String, Object>> leaders = _teamDao.selectLeaderInfo(parentTeamId, 0);
				log.debug("userId:" + userId);
				if (leaders == null || leaders.size() == 0)
				{
					state = -1;
					log.info("未搜索到负责人id！");
					return state;
				}
				else
				{
					for (int i = 0; i < leaders.size(); i++)
						if (Integer.parseInt(leaders.get(i).get("id").toString()) == userId)
						{
							state = 3;
							log.info("该用户有权限操作此团队");
							return state;
						}
						// leaderId不为空，查找下一级团队
						else
						{
							teamId = parentTeamId;
						}
				}
			}
			return permission(teamId, userId);
		}
	}

	public int ifPermission(int userId, int teamId)
	{
		int state = -1;
		log.info("into ifPermission");
		// 通过用户id，获取权限
		if (_teamDao.selectPlatFormAdmin(userId) > 0)
		{
			state = 2;
			log.info("你是平台管理员！");
			return state;
		} 
		else
		{
			log.info("你不是平台管理员！");
		}
		if (teamId <= 0&&teamId!=-2)
		{
			// 判断用户是否为团队负责人，是的话，可进行团队首页查看
			if (_teamDao.selectCharge(userId, teamId) > 0)
			{
				state = 0;
				log.info("你是团队负责人！");
			} 
			else
			{
				state = -1;
				log.info("你没有权限操作！");
			}
		}
		else
		{
			if (_teamDao.selectCharge(userId, teamId) > 0)
			{
				state = 1;
				log.info("你是该团队负责人！");
				return state;
			} 
			else
			{
				log.info("你不是该团队的负责人");
			  state = permission(teamId, userId);
			}
		}
		log.info("end ifPermission");
		return state;
	}

	/**
	 * 获取某用户名下的成员列表，userId和userName
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<User> getMemberListByUserId(int userId)
	{
		List<User> memberListByUserId = new ArrayList<User>();
		int state = ifPermission(userId, 0);
		TeamSelectFo teamFo = new TeamSelectFo();
		// 是平台管理员，获取全部成员
		if (state == 2)
		{
			memberListByUserId = _teamDao.selectUserList();
		} 
		else
		{
			List<Map<String, Object>> teamIds = new ArrayList<Map<String, Object>>();
			teamIds = getTeamIdListByUserId(userId);
			for (int i = 0; i < teamIds.size(); i++)
			{
				teamFo.setTeamId(Integer.parseInt(teamIds.get(i).get("team_id").toString()));
				List<User> userList = _teamDao.selectUserListByTeam(teamFo);
				for (int j = 0; j < userList.size(); j++)
				{
					memberListByUserId.add(userList.get(j));
				}
			}
		} 
		return memberListByUserId;
	}

	/**
	 * 获取某用户名下的团队列表(自己所在团队和管理的团队及子团队)，team_id
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getTeamIdListByUserId(int userId)
	{
		// selectSubTeamIdsAndLeaderIds
		List<Map<String, Object>> teamIdListByUserId = new ArrayList<Map<String, Object>>();
		int state = ifPermission(userId, 0);
		// 是平台管理员，获取全部团队id
		if (state == 2)
		{
			teamIdListByUserId = _teamDao.selectAllTeamId();
		}
		// 不是平台管理员，是团队负责人，获取本身团队及子团队下团队id
		else if (state >= 0)
		{
			// 获取自己管理的团队id
			List<Map<String, Object>> teamIdList = _teamDao.selectLeaderInfo(0, userId);
			teamIdRec(teamIdList, teamIdListByUserId);
		}
		List<Map<String, Object>> teamSelf=_teamDao.selectTeamIdByMem(userId);
		if(teamSelf==null||teamSelf.size()==0)
		{
			return teamIdListByUserId;
		}
		else
		{
			for(int i=0;i<teamSelf.size();i++)
			{
				teamIdListByUserId.add(teamSelf.get(i));
			}
		}
		// 去重复值
		for  ( int  i  =   0 ; i  <  teamIdListByUserId.size()  -   1 ; i ++ )  {       
      for  ( int  j  =  teamIdListByUserId.size()  -   1 ; j  >  i; j -- )  {       
           if  (teamIdListByUserId.get(j).get("team_id").equals(teamIdListByUserId.get(i).get("team_id")))  {       
          	 teamIdListByUserId.remove(j);       
            }        
        }        
      }   
		return teamIdListByUserId;
	}
	
	public void teamIdRec(List<Map<String, Object>> teamIdList, List<Map<String, Object>> teamIdListByUserId)
	{
		if (teamIdList == null || teamIdList.size() == 0)
		{
			log.info("没有子团队");
		} 
		else
		{
			for (int i = 0; i < teamIdList.size(); i++)
			{
				teamIdListByUserId.add(teamIdList.get(i));
				log.debug("teamIdList:" + teamIdList.get(i));
				// 子团队
				List<Map<String, Object>> subTeamIds = _teamDao.selectSubTeamIdsAndLeaderIds(Integer.parseInt(teamIdList.get(i).get("team_id").toString()));
				teamIdRec(subTeamIds, teamIdListByUserId);
			}
		}
	}
	
	/**
	 * 根据团队id获取自己所属团队和子团队id
	 * 
	 * @param teamId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getTeamIdsByTeamId(int teamId)
	{
		List<Map<String, Object>> getTeamIdsByTeamId = new ArrayList<Map<String, Object>>();
		Map<String,Object> teamMap=new HashMap<String,Object>();
		teamMap.put("team_id", teamId);
		getTeamIdsByTeamId.add(teamMap);
		List<Map<String, Object>> subTeamIds = _teamDao.selectSubTeamIdsAndLeaderIds(teamId);
		teamIdRec(subTeamIds, getTeamIdsByTeamId);
    return getTeamIdsByTeamId;
	}
	
	/**
	 * 获取某用户名下管理的团队列表，平台管理员返回所有团队，团队管理员返回所管理的团队和子团队，一般人员返回null
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getTeamIdsByCharge(int userId)
	{
		// selectSubTeamIdsAndLeaderIds
		List<Map<String, Object>> teamIdListByUserId = new ArrayList<Map<String, Object>>();
		int state = ifPermission(userId, 0);
		// 是平台管理员，获取全部团队id
		if (state == 2)
		{
			teamIdListByUserId = _teamDao.selectAllTeamId();
		}
		// 不是平台管理员，是团队负责人，获取本身团队及子团队下团队id
		else if (state >= 0)
		{
			// 获取自己管理的团队id
			List<Map<String, Object>> teamIdList = _teamDao.selectLeaderInfo(0, userId);
			for(int i=0;i<teamIdList.size();i++)
			{
				Map<String,Object> teamMap=new HashMap<String,Object>();
				teamMap.put("team_id", teamIdList.get(i).get("team_id"));
				teamMap.put("team_level", "1");
				teamMap.put("team_name", teamIdList.get(i).get("team_name"));
				teamMap.put("parent_teamid", teamIdList.get(i).get("parent_teamid"));
				teamIdListByUserId.add(teamMap);
				List<Map<String, Object>> subTeamIds = _teamDao.selectSubTeamIdsAndLeaderIds(Integer.parseInt(teamIdList.get(i).get("team_id").toString()));
				teamIdRec(subTeamIds, teamIdListByUserId);
			}
		}
		else
		{
			teamIdListByUserId=null;
		}
		return teamIdListByUserId;
	}
	
	/**
	 * 获取某用户名下的小区列表，groupId和groupName
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getGroupListByUserId(int userId)
	{
		return null;
	}
	
	/**
	 * 删除原负责人的团队管理菜单权限
	 * @param userIds 人员列表
	 * @return
	 */
	private int deleteLeaderRole(String userIds)
	{
		// 删除负责人之前，先判断当前负责人是否还是团队责任人，如果是，则不删除团队菜单
		String[] userIdArry = userIds.split(",");
		String userIdStr = "";
		for (String userId : userIdArry)
		{
			int total = _teamDao.selectLaderCount(userId);
			if (total > 0) 
				continue;
			else
				userIdStr += userId + ",";
		}
		if (StringUtils.isNotBlank(userIdStr))
		{
			userIdStr = userIdStr.substring(0, userIdStr.length() - 1);
		}
		return _teamDao.deleteLeaderRole(userIdStr);
	}
	
	/**
	 * 新增新负责人的团队管理菜单权限
	 * @param userIds 人员列表
	 * @return
	 */
	private int insertLeaderRole(String userIds)
	{
		String[] userArryIds = userIds.split(",");
		int info = 0;
		for (String userId : userArryIds) 
		{
			info = _teamDao.selectLeaderRole(userId);
			if (info > 0)
			{
				continue;
			}
			info = _teamDao.insertLeaderRole(userId);
		}
		return info;
	}
}
