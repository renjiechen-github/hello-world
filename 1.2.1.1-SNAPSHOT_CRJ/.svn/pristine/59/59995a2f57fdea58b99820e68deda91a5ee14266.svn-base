package com.room1000.workorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.workorder.dto.SubOrderStateDto;

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
public interface SubOrderStateDtoMapper extends SuperMapper {
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
    int insert(SubOrderStateDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(SubOrderStateDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param state state
     * @return SubOrderStateDto SubOrderStateDto<br>
     */
    SubOrderStateDto selectByPrimaryKey(String state);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(SubOrderStateDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(SubOrderStateDto record);

    /**
     * 
     * Description: 查询所有订单状态
     * 
     * @author jinyanan
     *
     * @return List<SubOrderStateDto>
     */
    List<SubOrderStateDto> selectAll();
}