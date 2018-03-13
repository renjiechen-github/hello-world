package com.room1000.suborder.cancelleaseorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderStateDto;

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
public interface CancelLeaseOrderStateDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return int int<br>
     * @mbg.generated
     */
    int deleteByPrimaryKey(String state);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insert(CancelLeaseOrderStateDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insertSelective(CancelLeaseOrderStateDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return CancelLeaseOrderStateDto CancelLeaseOrderStateDto<br>
     * @mbg.generated
     */
    CancelLeaseOrderStateDto selectByPrimaryKey(String state);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CancelLeaseOrderStateDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKey(CancelLeaseOrderStateDto record);
}