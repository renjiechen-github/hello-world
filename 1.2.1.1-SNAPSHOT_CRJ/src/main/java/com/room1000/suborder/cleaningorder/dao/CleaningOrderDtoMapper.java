package com.room1000.suborder.cleaningorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;

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
public interface CleaningOrderDtoMapper extends SuperMapper {
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
    int insert(CleaningOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(CleaningOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return CleaningOrderDto CleaningOrderDto<br>
     */
    CleaningOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(CleaningOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(CleaningOrderDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return CleaningOrderDto
     */
    CleaningOrderDto selectDetailById(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return CleaningOrderDto
     */
    CleaningOrderDto selectByCode(String code);
}