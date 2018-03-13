package com.room1000.suborder.cancelleaseorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderHisDto;

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
public interface CancelLeaseOrderHisDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id 
     * @param priority priority
     * @return int int<br>
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("id") Long id, @Param("priority") Long priority);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insert(CancelLeaseOrderHisDto record);
    
    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insertSelective(CancelLeaseOrderHisDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id 
     * @param priority priority
     * @return CancelLeaseOrderHisDto CancelLeaseOrderHisDto<br>
     * @mbg.generated
     */
    CancelLeaseOrderHisDto selectByPrimaryKey(@Param("id") Long id, @Param("priority") Long priority);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CancelLeaseOrderHisDto record);


    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKey(CancelLeaseOrderHisDto record);

    /**
     * 
     * Description: selectMaxPriority
     * 
     * @author jinyanan
     *
     * @param cancelLeaseOrderId cancelLeaseOrderId
     * @return Long
     */
    Long selectMaxPriority(Long cancelLeaseOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return List<CancelLeaseOrderHisDto>
     */
    List<CancelLeaseOrderHisDto> selectById(Long id);
}