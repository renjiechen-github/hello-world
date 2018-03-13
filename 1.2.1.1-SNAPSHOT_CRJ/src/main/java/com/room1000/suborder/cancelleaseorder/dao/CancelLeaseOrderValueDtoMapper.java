package com.room1000.suborder.cancelleaseorder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月6日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface CancelLeaseOrderValueDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return int int<br>
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insert(CancelLeaseOrderValueDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insertSelective(CancelLeaseOrderValueDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return CancelLeaseOrderValueDto CancelLeaseOrderValueDto<br>
     * @mbg.generated
     */
    CancelLeaseOrderValueDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CancelLeaseOrderValueDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKey(CancelLeaseOrderValueDto record);

    /**
     * 
     * Description: 查询value
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<CancelLeaseOrderValueDto>
     */
    List<CancelLeaseOrderValueDto> selectBySubOrderId(Long subOrderId);
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param record record
     * @return CancelLeaseOrderValueDto
     */
    CancelLeaseOrderValueDto selectByAttrPath(CancelLeaseOrderValueDto record);
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     */
    void deleteBySubOrderId(Long subOrderId);

    /**
     * 订单对应出租合约水电煤
     * @param rentalLeaseOrderId
     * @return
     */
		Map<String, Object> getRentalInfo(@Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);

		/**
		 * 查询yc_wegcost_tab表中水表度数
		 * @param rentalLeaseOrderId
		 * @return
		 */
		Map<String, Object> getRentalInfoByWater(@Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);

		/**
		 * 查询yc_wegcost_tab表中电表度数
		 * @param rentalLeaseOrderId
		 * @return
		 */
		Map<String, Object> getRentalInfoByElectricity(@Param(value = "rentalLeaseOrderId") Long rentalLeaseOrderId);

		/**
		 * 查询yc_wegcost_tab表中燃气度数
		 * @param rentalLeaseOrderId
		 * @return
		 */
		Map<String, Object> getRentalInfoByGas(Long rentalLeaseOrderId);
    
}