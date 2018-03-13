package com.room1000.workorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.workorder.dto.OrderCommentaryTypeDto;

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
public interface OrderCommentaryTypeDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type 
     * @param orderType orderType
     * @return int int<br>
     */
    int deleteByPrimaryKey(@Param("type") String type, @Param("orderType") String orderType);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(OrderCommentaryTypeDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OrderCommentaryTypeDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type 
     * @param orderType orderType
     * @return OrderCommentaryTypeDto OrderCommentaryTypeDto<br>
     */
    OrderCommentaryTypeDto selectByPrimaryKey(@Param("type") String type, @Param("orderType") String orderType);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OrderCommentaryTypeDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OrderCommentaryTypeDto record);
    
    /**
     * 
     * Description: 根据订单类型查询支持的评论类型
     * 
     * @author jinyanan
     *
     * @param orderType orderType
     * @return List<OrderCommentaryTypeDto>
     */
    List<OrderCommentaryTypeDto> selectByOrderType(String orderType);
}