package com.room1000.suborder.houselookingorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;

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
public interface HouseLookingOrderDtoMapper extends SuperMapper {
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
    int insert(HouseLookingOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(HouseLookingOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return HouseLookingOrderDto HouseLookingOrderDto<br>
     */
    HouseLookingOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(HouseLookingOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(HouseLookingOrderDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return HouseLookingOrderDto
     */
    HouseLookingOrderDto selectDetailById(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return HouseLookingOrderDto
     */
    HouseLookingOrderDto selectByCode(String code);

}