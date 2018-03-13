package com.room1000.suborder.houselookingorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderChannelDto;

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
public interface HouseLookingOrderChannelDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param channel channel
     * @return int int<br>
     */
    int deleteByPrimaryKey(String channel);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(HouseLookingOrderChannelDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(HouseLookingOrderChannelDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param channel channel
     * @return HouseLookingOrderChannelDto HouseLookingOrderChannelDto<br>
     */
    HouseLookingOrderChannelDto selectByPrimaryKey(String channel);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(HouseLookingOrderChannelDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(HouseLookingOrderChannelDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @return List<HouseLookingOrderChannelDto>
     */
    List<HouseLookingOrderChannelDto> selectAll();
}