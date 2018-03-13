package com.room1000.agreement.dao;

import org.apache.ibatis.annotations.Param;

import com.room1000.agreement.dto.HouseRankDto;
import com.room1000.core.mybatis.SuperMapper;

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
public interface HouseRankDtoMapper extends SuperMapper {
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
    int insert(HouseRankDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(HouseRankDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return HouseRankDto HouseRankDto<br>
     */
    HouseRankDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(HouseRankDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(HouseRankDto record);

  	/**
  	 * 释放房源状态
  	 * 
  	 * @param rank_id
  	 * @return
  	 */
  	int resetHouseRankState(@Param(value = "rank_id") String rank_id);
  	
  	/**
  	 * 
  	 * @param house_id
  	 * @return
  	 */
  	int resetHouseRankStateShared(@Param(value = "house_id") String house_id);
}