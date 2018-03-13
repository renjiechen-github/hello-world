package com.room1000.suborder.houselookingorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderValueDto;

/**
 * 
 * Description: 
 *  
 * Created on 2017年3月6日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface HouseLookingOrderValueDtoMapper extends SuperMapper {
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
    int insert(HouseLookingOrderValueDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(HouseLookingOrderValueDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return HouseLookingOrderValueDto HouseLookingOrderValueDto<br>
     */
    HouseLookingOrderValueDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(HouseLookingOrderValueDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(HouseLookingOrderValueDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param houseLookingOrderValueDto houseLookingOrderValueDto
     * @return HouseLookingOrderValueDto
     */
    HouseLookingOrderValueDto selectByAttrPath(HouseLookingOrderValueDto houseLookingOrderValueDto);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<HouseLookingOrderValueDto>
     */
    List<HouseLookingOrderValueDto> selectBySubOrderId(Long subOrderId);

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