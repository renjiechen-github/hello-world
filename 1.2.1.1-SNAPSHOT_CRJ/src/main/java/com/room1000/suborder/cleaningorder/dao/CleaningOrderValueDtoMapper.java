package com.room1000.suborder.cleaningorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderValueDto;

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
public interface CleaningOrderValueDtoMapper extends SuperMapper {
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
    int insert(CleaningOrderValueDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(CleaningOrderValueDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return CleaningOrderValueDto CleaningOrderValueDto<br>
     */
    CleaningOrderValueDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(CleaningOrderValueDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(CleaningOrderValueDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<CleaningOrderValueDto>
     */
    List<CleaningOrderValueDto> selectBySubOrderId(Long subOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param cleaningOrderValueDto cleaningOrderValueDto
     * @return CleaningOrderValueDto
     */
    CleaningOrderValueDto selectByAttrPath(CleaningOrderValueDto cleaningOrderValueDto);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     */
    void deleteBySubOrderId(Long subOrderId);
}