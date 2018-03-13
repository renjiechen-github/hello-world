package com.room1000.suborder.houselookingorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderStateDto;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface HouseLookingOrderStateDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return int int<br>
     */
    int deleteByPrimaryKey(String state);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(HouseLookingOrderStateDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(HouseLookingOrderStateDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return HouseLookingOrderStateDto HouseLookingOrderStateDto<br>
     */
    HouseLookingOrderStateDto selectByPrimaryKey(String state);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(HouseLookingOrderStateDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(HouseLookingOrderStateDto record);
}