
package com.yc.rm.caas.appserver.bus.dao.house;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.model.user.User;
import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.model.house.Area;
import com.yc.rm.caas.appserver.model.house.House;
import com.yc.rm.caas.appserver.model.house.ProjectInfo;
import com.yc.rm.caas.appserver.model.house.ProjectList;
import com.yc.rm.caas.appserver.model.house.RankAgreement;
import com.yc.rm.caas.appserver.model.house.RankHouse;
import com.yc.rm.caas.appserver.model.syscfg.DictiItem;

@Component("_houseDao")
public interface IHouseDao
{

	/**
	 * 房源列表
	 * 
	 * @param condition
	 * @return
	 */
	List<House> getHouseList(HouseFo condition);

	/**
	 * 获取房源信息
	 * 
	 * @param id
	 * @return
	 */
	House getHouseInfo(@Param("newfilepath") String newfilepath,
			@Param("filepath") String filepath, @Param("id") String id);

	/**
	 * 获取工程基本信息
	 * 
	 * @param furnituremoney
	 * @param appliancesmoney
	 * @param othermoney
	 * @param agreementid
	 * @return
	 */
	ProjectInfo getProjectBaseInfo(@Param("furnituremoney") String furnituremoney,
			@Param("appliancesmoney") String appliancesmoney,
			@Param("othermoney") String othermoney,
			@Param("agreementid") String agreementid);

	/**
	 * 房源花费
	 * 
	 * @param agreementId
	 * @return
	 */
	ProjectInfo getProjectMonery(@Param("agreementid") String agreementId);

	/**
	 * 获取工程清单
	 * 
	 * @param agreementId
	 * @param fatherid
	 * @return
	 */
	List<ProjectList> getProjectList(@Param("agreementid") String agreementId,
			@Param("fatherid") String fatherid);

	

	/**
	 * 租赁房源信息
	 * 
	 * @param house_id
	 * @return
	 */
	List<RankHouse> loadRankHouseList(@Param("house_id") String house_id);

	/**
	 * 租赁房源明细
	 * 
	 * @param rankId
	 * @return
	 */
	RankHouse loadRankHouseInfo(@Param("newfilepath") String newfilepath,
			@Param("filepath") String filepath, @Param("id") String rankId);

	/**
	 * 租赁房源合约列表信息
	 * 
	 * @param id
	 * @return
	 */
	List<RankAgreement> loadRankAgreementList(@Param("id") String id);

	/**
	 * 获取出租信息
	 * 
	 * @param id
	 * @return
	 */
	List<RankAgreement> getRankAgreementInfo(@Param("newfilepath") String newfilepath,
			@Param("filepath") String filepath, @Param("id") String id);

	/**
	 * 验证租赁房源状态
	 * 
	 * @param houseId
	 * @param rank_status
	 * @return
	 */
	int checkRankHouseStatus(@Param("id") String id,
			@Param("rank_status") String rank_status);

	/**
	 * 更新用户信息
	 * 
	 * @param u
	 */
	void updateUserInfo(User u);

	


	/**
	 * 更新房源状态
	 * 
	 * @param rank_status
	 * @param operid
	 * @param id
	 */
	void updateRankHouseStatus(@Param("rank_status") String rank_status,
			@Param("operid") String operid, @Param("id") String id);

	/**
	 * 更新租赁房源信息
	 * 
	 * @param columns
	 */
	void updateRankHouse(@Param("columns") String columns, @Param("id") String id);

	/**
	 * 更新租赁房源
	 * 
	 * @param rankAgreement
	 */
	void updateRankAgreementInfo(RankAgreement rankAgreement);

	

	int checkHouseNameIsExist(@Param("id") String id, @Param("houseName") String houseName);

	int insertHouse(House house);

	void updateHouseCode(@Param("regionId") String regionId, @Param("id") String id,
			@Param("appendix") String appendix);

	List<Area> loadGroupList(HouseFo condition);

	/**
	 * 删除房源信息
	 * 
	 * @param id
	 * @return
	 */
	int delHouseInfo(@Param("id") String id, @Param("operid") String operid);

	/**
	 * 更新房源信息
	 * 
	 * @param columns
	 * @param id
	 */
	void updateHouseInfo(@Param("columns") String columns, @Param("id") String id);

	/**
	 * 验证房源状态
	 * 
	 * @param id
	 * @return
	 */
	int checkHouseState(@Param("id") String id, @Param("house_status") String house_status);




	/**
	 * 由租赁房源id获取最近一条出租合约id
	 * 
	 * @param rankHouseId
	 * @return
	 */
	String getRankAgreementIdByRankHouseId(@Param("houseId") String rankHouseId);



	/**
	 * 获取月份和租赁类型
	 * 
	 * @param cnt
	 * @return
	 */
	List<DictiItem> getJlAndPaytype(@Param("cnt") String cnt);

	/**
	 * 待验收房源数
	 * 
	 * @param user_id
	 * @return
	 */
	int loadApprovalHouseCnt(@Param("user_id") String user_id);

	/**
	 * 待出租房源
	 * 
	 * @param user_id
	 * @return
	 */
	int loadRankHouseCnt(@Param("user_id") String user_id);

	/**
	 * 待缴费数量
	 * 
	 * @param user_id
	 * @return
	 */
	int loadPayCnt(@Param("user_id") String user_id);

	/**
	 * 合约到期
	 * 
	 * @param user_id
	 * @return
	 */
	int loadRankExpireCnt(@Param("user_id") String user_id);

	/**
	 * 待缴费
	 * 
	 * @param user_id
	 * @param keyWord
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	List<Map<String, String>> loadPayCntList(@Param("user_id") String user_id,
			@Param("keyWord") String keyWord, @Param("startPage") String startPage,
			@Param("pageSize") String pageSize, @Param("areaid") String areaid,
			@Param("trading") String trading);

	
	
	/**
	 * 验证网格是否为本人负责的网格
	 * 
	 * @param houseId
	 * @param user_id
	 * @return
	 */
	int checkIsSelfGrid(@Param("houseId") String houseId, @Param("user_id") String user_id);

	/**
	 * 回款率
	 * 
	 * @param user_id
	 * @return
	 */
	String payMentRate(@Param("user_id") String user_id);

	/**
	 * 加载该网格已经签约的房间
	 * 
	 * @param user_id
	 * @return
	 */
	int loadSignAllHouse(@Param("user_id") String user_id);

	/**
	 * 未签约房源数量
	 * 
	 * @param user_id
	 * @return
	 */
	int loadnoSignAllHouse(@Param("user_id") String user_id);

	/**
	 * 合租
	 * 
	 * @param user_id
	 * @return
	 */
	int flatShareHouse(@Param("user_id") String user_id);

	/**
	 * 整租
	 * 
	 * @param user_id
	 * @return
	 */
	int allRankHouse(@Param("user_id") String user_id);

	/**
	 * 通过房源编码判断出租房间的状态 5 已出租 6失效 其他 待出租
	 * 
	 * @param houseId
	 * @return
	 */
	//String loadRankHouseStatusByHouseId(@Param("house_id") String houseId);

	/**
	 * 已经签约的房源 状态为 2，11，12
	 * 
	 * @param user_id
	 * @return
	 */
	int readySignRankHouse(@Param("user_id") String user_id);
}