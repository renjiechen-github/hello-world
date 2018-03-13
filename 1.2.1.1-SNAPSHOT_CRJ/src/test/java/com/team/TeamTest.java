package com.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamRelFo;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamSelectFo;
import com.yc.rm.caas.appserver.bus.service.team.ITeamServ;
import com.yc.rm.caas.appserver.model.team.TeamLeaders;
import com.yc.rm.caas.appserver.model.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-spring.xml")
@Rollback(false)
@Transactional
public class TeamTest
{

	@Resource
	ITeamServ _teamServImpl;
	protected org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	
	@Test
	public void test()
	{
		List<Map<String,Object>> teamList=_teamServImpl.getTeamIdListByUserId(3);
		if(teamList==null||teamList.size()==0) {
			log.debug("teamList:"+teamList);
		}
		else 
		{
			for(int i=0;i<teamList.size();i++) {
				log.debug("size:"+teamList.size()+" teamList:"+teamList.get(i).get("team_id"));
			}
		}
	}
	
	@Test
	public void testMemListByUserId() {
		List<User> memList=_teamServImpl.getMemberListByUserId(3);
		if(memList==null||memList.size()==0) {
			log.debug("memList:"+memList);
		}
		else
		{
			for(int i=0;i<memList.size();i++) {
				log.debug("json:"+JSONObject.toJSONString(memList)+"size:"+memList.size()+" memList:"+memList.get(i));
			}
		}
	}
	
	@Test
	public void testDimSearch() {
		TeamSelectFo teamFo=new TeamSelectFo();
		// 1是根据团队名称查询
		teamFo.setSearchType(2);
		// 要搜索的内容
		teamFo.setTeamName("王");
		teamFo.setUserId(545454);
		List<Map<String, Object>> getTeamByDimSearch=_teamServImpl.getTeamByDimSearch(teamFo);
		if(getTeamByDimSearch==null||getTeamByDimSearch.size()==0) {
			log.debug("json:"+JSONObject.toJSONString(getTeamByDimSearch));
		}
		else 
		{
			log.debug("size:"+getTeamByDimSearch.size());
			for(int i=0;i<getTeamByDimSearch.size();i++) {
				log.debug("List"+i+":"+JSONObject.toJSONString(getTeamByDimSearch.get(i)));
			}
			log.debug("json:"+JSONObject.toJSONString(getTeamByDimSearch));
		}
	}
	
	@Test
	public void testTeamPage() {
		TeamSelectFo teamFo=new TeamSelectFo();
		teamFo.setUserId(545454);
		
		List<Map<String, Object>> getTeamPage=_teamServImpl.getTeamPage(teamFo);
		if(getTeamPage==null||getTeamPage.size()==0) {
			log.debug("json:"+JSONObject.toJSONString(getTeamPage));
		}
		else 
		{
			log.debug("size:"+getTeamPage.size());
			for(int i=0;i<getTeamPage.size();i++) {
				log.debug("List"+i+":"+JSONObject.toJSONString(getTeamPage.get(i)));
			}
			log.debug("json:"+JSONObject.toJSONString(getTeamPage));
		}
	}
	
	@Test
	public void testGetTeamById() {
		TeamSelectFo teamFo=new TeamSelectFo();
		teamFo.setUserId(545448);
		teamFo.setTeamId(1);
		Map<String, Object> getTeamPage=_teamServImpl.getTeamByTeamId(teamFo);
		log.debug("json:"+JSONObject.toJSONString(getTeamPage));
	}
	
	@Test
	public void testAddTeam()
	{
		TeamSelectFo teamFo=new TeamSelectFo();
		teamFo.setTeamId(1);
		teamFo.setTeamLevel(2);
		teamFo.setTeamName("技术部");
		teamFo.setUserId(545454);
		List<User> memList=new ArrayList<User>();
		List<TeamLeaders> leaderList=new ArrayList<TeamLeaders>();
		int[] userId= {545454,1,545453,545480,545426};
		for(int i=0;i<userId.length;i++) {
			User user=new User();
			user.setUserId(userId[i]);
			memList.add(user);
		}
		teamFo.setMemberList(memList);
		int[] leaderId= {545480,545426,1};
		for(int j=0;j<leaderId.length;j++)
		{
			TeamLeaders leader=new TeamLeaders();
			leader.setLeaderId(leaderId[j]);
			leaderList.add(leader);
		}
		teamFo.setLeaderList(leaderList);
		int n=_teamServImpl.addTeam(teamFo);
		log.debug("n:"+n);
	}
	
	@Test
	public void testRemoveMem()
	{
		TeamRelFo teamRelFo=new TeamRelFo();
		teamRelFo.setUserId(545454);
		teamRelFo.setTeamId(22);
		teamRelFo.setMemberId(545454);
		int n=_teamServImpl.removeTeamMember(teamRelFo);
		log.debug("n:"+n);
	}
	
	@Test
	public void testRemoveTeam()
	{
		TeamSelectFo teamFo=new TeamSelectFo();
		teamFo.setUserId(545454);
		teamFo.setTeamId(21);
		int n=_teamServImpl.removeTeam(teamFo);
		log.debug("n:"+n);
	}
	
	@Test
	public void testGetTeamPage()
	{
		TeamSelectFo teamFo=new TeamSelectFo();
		teamFo.setUserId(3);
		_teamServImpl.getTeamPage(teamFo);
	}
	
	@Test 
	public void testGetTeamIdsByCharge()
	{
		List<Map<String,Object>> teams=_teamServImpl.getTeamIdsByCharge(3);
		if(teams==null||teams.size()==0)
		{
			log.debug("teams:"+JSONObject.toJSONString(teams));
		}
		else
		{
			for(int i=0;i<teams.size();i++)
			{
				log.debug("team:"+JSONObject.toJSONString(teams.get(i)));
			}
		}
	}
}
