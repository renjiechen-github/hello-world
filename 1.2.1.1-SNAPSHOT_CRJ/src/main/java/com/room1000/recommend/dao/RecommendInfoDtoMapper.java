package com.room1000.recommend.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.recommend.dto.RecommendInfoDto;

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
public interface RecommendInfoDtoMapper extends SuperMapper {
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
    int insert(RecommendInfoDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(RecommendInfoDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return RecommandInfoDto RecommandInfoDto<br>
     */
    RecommendInfoDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(RecommendInfoDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(RecommendInfoDto record);

    /**
     * 
     * Description: 根据mobile查询有效reCommendInfo
     * 
     * @author jinyanan
     *
     * @param mobile mobile
     * @return RecommendInfoDto
     */
    RecommendInfoDto selectValidInfo(String mobile);
}