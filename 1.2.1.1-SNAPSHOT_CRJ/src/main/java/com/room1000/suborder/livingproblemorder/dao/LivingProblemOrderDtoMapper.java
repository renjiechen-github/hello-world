package com.room1000.suborder.livingproblemorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderDto;

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
public interface LivingProblemOrderDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return int int<br>
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(LivingProblemOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(LivingProblemOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return LivingProblemOrderDto LivingProblemOrderDto<br>
     */
    LivingProblemOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(LivingProblemOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(LivingProblemOrderDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return LivingProblemOrderDto
     */
    LivingProblemOrderDto selectDetailById(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return LivingProblemOrderDto
     */
    LivingProblemOrderDto selectByCode(String code);
}