package com.room1000.suborder.cleaningorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cleaningorder.dto.CleaningTypeDto;

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
public interface CleaningTypeDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type
     * @return int int<br>
     */
    int deleteByPrimaryKey(String type);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(CleaningTypeDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(CleaningTypeDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type
     * @return CleaningOrderTypeDto CleaningOrderTypeDto<br>
     */
    CleaningTypeDto selectByPrimaryKey(String type);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(CleaningTypeDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(CleaningTypeDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @return List<CleaningTypeDto>
     */
    List<CleaningTypeDto> selectAll();
}