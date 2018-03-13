package com.room1000.suborder.cancelleaseorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;

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
public interface CancelLeaseOrderDtoMapper extends SuperMapper
{
	/**
	 * Description: deleteByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param id
	 *          id
	 * @return int int<br>
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * Description: insert<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int insert(CancelLeaseOrderDto record);

	/**
	 * Description: insertSelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int insertSelective(CancelLeaseOrderDto record);

	/**
	 * Description: selectByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param id
	 *          id
	 * @return CancelLeaseOrderDto CancelLeaseOrderDto<br>
	 * @mbg.generated
	 */
	CancelLeaseOrderDto selectByPrimaryKey(Long id);

	/**
	 * 
	 * Description: selectByCode
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @return CancelLeaseOrderDto
	 */
	CancelLeaseOrderDto selectByCode(String code);

	/**
	 * 
	 * Description: selectByConf
	 * 
	 * @author jinyanan
	 *
	 * @param record
	 *          record
	 * @return List<CancelLeaseOrderDto>
	 */
	List<CancelLeaseOrderDto> selectByCond(CancelLeaseOrderDto record);

	/**
	 * Description: updateByPrimaryKeySelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(CancelLeaseOrderDto record);

	/**
	 * Description: updateByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int updateByPrimaryKey(CancelLeaseOrderDto record);

	/**
	 * 更新管家上门时间
	 * 
	 * @param cancelLeaseOrderDto
	 * @return
	 */
	int updateButlerGetHouseDateByCode(CancelLeaseOrderDto cancelLeaseOrderDto);

	/**
	 * 
	 * Description: updateByCode
	 * 
	 * @author jinyanan
	 *
	 * @param record
	 *          record
	 * @return int
	 */
	int updateByCodeSelective(CancelLeaseOrderDto record);

	/**
	 * 
	 * Description: 查询详情
	 * 
	 * @author jinyanan
	 *
	 * @param cancelLeaseOrderId
	 *          cancelLeaseOrderId
	 * @return CancelLeaseOrderDto
	 */
	CancelLeaseOrderDto selectDetailById(Long cancelLeaseOrderId);

	/**
	 * 查询退租宽限期天数
	 * 
	 * @return
	 */
	String selectGraceTime();

	/**
	 * 根据退租主键id，更改退租类型
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	int updateCancelLeaseType(@Param(value="id") Long id, @Param(value="type") String type);

	/**
	 * 查询上期最后时间
	 * @param rentalLeaseOrderId
	 * @return
	 */
	String getLastDate(@Param(value="rentalLeaseOrderId") Long rentalLeaseOrderId);

	int updateCommentsById(@Param(value="rentalLeaseOrderId") Long id, @Param(value="comments") String comments);

	int updateSubCommentsById(@Param(value="rentalLeaseOrderId") Long id, @Param(value="comments") String comments);

}