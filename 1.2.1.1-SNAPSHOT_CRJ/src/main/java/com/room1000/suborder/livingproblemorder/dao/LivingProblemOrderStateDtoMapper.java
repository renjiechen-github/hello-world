package com.room1000.suborder.livingproblemorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderStateDto;

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
public interface LivingProblemOrderStateDtoMapper extends SuperMapper {
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
    int insert(LivingProblemOrderStateDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(LivingProblemOrderStateDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return LivingProblemOrderStateDto LivingProblemOrderStateDto<br>
     */
    LivingProblemOrderStateDto selectByPrimaryKey(String state);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(LivingProblemOrderStateDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(LivingProblemOrderStateDto record);
}