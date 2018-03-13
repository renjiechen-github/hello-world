package com.room1000.agreement.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.room1000.agreement.dto.AgreementDto;
import com.room1000.core.mybatis.SuperMapper;

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
public interface AgreementDtoMapper extends SuperMapper
{
	/**
	 * Description: deleteByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param id
	 *          id
	 * @return int int<br>
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * Description: insert<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int insert(AgreementDto record);

	/**
	 * Description: insertSelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int insertSelective(AgreementDto record);

	/**
	 * Description: selectByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param id
	 *          id
	 * @return AgreementDto AgreementDto<br>
	 */
	AgreementDto selectByPrimaryKey(Long id);

	/**
	 * Description: updateByPrimaryKeySelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int updateByPrimaryKeySelective(AgreementDto record);

	/**
	 * Description: updateByPrimaryKeyWithBLOBs<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int updateByPrimaryKeyWithBLOBs(AgreementDto record);

	/**
	 * Description: updateByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int updateByPrimaryKey(AgreementDto record);

	/**
	 * 
	 * Description: 查询所有已过期出租合约
	 * 
	 * @author jinyanan
	 * @param paramTime
	 *
	 * @return List<AgreementDto>
	 */
	List<AgreementDto> selectAllOverdueAgreement(@Param(value = "paramTime") String paramTime);

	/**
	 * 自动发起退租订单的提前时间参数
	 * 
	 * @return
	 */
	String selectTimeParam();

	/**
	 * 根据出租合约主键id，更改出租合约的退租时间
	 * 
	 * @param checkouttime
	 * @param rentalLeaseOrderId
	 * @return
	 */
	int updateCheckouttimeByCode(@Param(value = "checkouttime") String checkouttime, @Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);

	/**
	 * 根据出租合约主键id，查询合约信息
	 * 
	 * @param rentalLeaseOrderId
	 * @return
	 */
	Map<String, Object> selectAgreementInfo(@Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);

	/**
	 * 查询对应整租是否已签约
	 * 
	 * @param house_id
	 * @return
	 */
	String selectEntireRent(@Param(value = "house_id") String house_id);

	/**
	 * 查询对应合租房间是否存在已签约
	 * 
	 * @param house_id
	 * @return
	 */
	String selectSharedRent(@Param(value = "house_id") String house_id);

	/**
	 * 查询是否存在已签约或者生效待支付的出租合约
	 * 
	 * @param id
	 * @return
	 */
	int selectAgreementTotal(@Param(value = "house_id") String id);

	/**
	 * 根据house_id查询houserank的ids
	 * 
	 * @param house_id
	 * @return
	 */
	String selectHouseRankIds(@Param(value = "house_id") String house_id);

	/**
	 * 释放合约状态
	 * 
	 * @param rentalLeaseOrderId
	 * @return
	 */
	int resetAgreementState(@Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);

	/**
	 * 合约置为失效
	 * 
	 * @param rentalLeaseOrderId
	 * @return
	 */
	int failureAgreementState(@Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);
}