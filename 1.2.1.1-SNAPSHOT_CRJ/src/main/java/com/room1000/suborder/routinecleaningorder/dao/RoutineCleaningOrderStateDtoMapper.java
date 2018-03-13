package com.room1000.suborder.routinecleaningorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderStateDto;

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
public interface RoutineCleaningOrderStateDtoMapper extends SuperMapper {
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
    int insert(RoutineCleaningOrderStateDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(RoutineCleaningOrderStateDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return RoutineCleaningOrderStateDto RoutineCleaningOrderStateDto<br>
     */
    RoutineCleaningOrderStateDto selectByPrimaryKey(String state);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(RoutineCleaningOrderStateDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(RoutineCleaningOrderStateDto record);
}