/**
 * 
 */
package com.yc.rm.caas.appserver.bus.dao.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.model.contract.ContractBean;
import com.yc.rm.caas.appserver.model.house.RankAgreement;
/**
 * @author stephen
 * 
 */
@Component("_contractDao")
public interface IContractDao {
	/**
	 * 获取合约id
	 * 
	 * @param agreementId
	 * @return
	 */
	ContractBean getAgreementInfo(@Param("newfilepath") String newfilePath,
			@Param("filepath") String filepath,
			@Param("agreementid") String agreementId,
			@Param("house_id") String house_id);

	/**
	 * @param id
	 * @return
	 */
	String getRankAgreementIdByRankHouseId(@Param("id") String id);

	/**
	 * 获取出租信息
	 * 
	 * @param id
	 * @return
	 */
	List<RankAgreement> getRankAgreementInfo(
			@Param("newfilepath") String newfilepath,
			@Param("filepath") String filepath, @Param("id") String id);

	/**
	 * 验证合同在10分钟内没有认证或者缴费
	 * 
	 * @param houseId
	 * @return
	 */
	Map<String, String> checkAgreementISNotPay(@Param("house_id") String houseId);

	/**
	 * 获取有效出租合约最大结束时间
	 * 
	 * @param houseId
	 * @return
	 */
	String loadValidRankAgreementMaxEndTime(@Param("houseId") String houseId);

	/**
	 * 验证时间是否超时
	 * 
	 * @param time
	 * @return
	 */
	Map<String, String> checkDays(@Param("time") String time);

	/**
	 * 获取出租合约的fatherid 和 区域
	 * 
	 * @param housee_id
	 * @return
	 */
	ContractBean getRankAgreementFatherId(@Param("house_id") String housee_id);

	/**
	 * 保存出租合约
	 * 
	 * @param rankAgreement
	 */
	void insertRankAgreement(RankAgreement rankAgreement);

	/**
	 * 更新出租合约编码
	 * 
	 * @param rank_code
	 * @param id
	 */
	void updateRankAgreemtCode(@Param("rank_code") String rank_code,
			@Param("file_path") String file_path, @Param("id") String id);

	/**
	 * 由月份和开始时间计算结束时间
	 * 
	 * @param begin_time
	 * @param rent_month
	 * @return
	 */
	Map<String, String> calcEndTime(@Param("begin_time") String begin_time,
			@Param("rent_month") String rent_month, @Param("count") String count);

	/**
	 * 验证合约状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	int checkAgreementStatus(@Param("status") String status,
			@Param("id") String id, @Param("type") String type);

	/**
	 * 出租合约
	 * 
	 * @param condition
	 * @return
	 */
	List<ContractBean> agreementList(HouseFo condition);

	/**
	 * 更新合约信息
	 * 
	 * @param columns
	 * @param id
	 */
	void updateAgreement(@Param("columns") String columns,
			@Param("id") String id, @Param("type") String type);

	/**
	 * 出租合约列表
	 * 
	 * @param condition
	 * @return
	 */
	List<RankAgreement> rankAgreementList(HouseFo condition);

	/**
	 * 保存房源签约
	 * 
	 * @param agreement
	 */
	void saveHouseAgreement(ContractBean agreement);
}
