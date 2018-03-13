package com.room1000.workorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.request.QryOrderCommentaryPagerListRequest;

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
public interface OrderCommentaryDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param workOrderId workOrderId 
     * @param type type
     * @return int int<br>
     */
    int deleteByPrimaryKey(@Param("workOrderId") Long workOrderId, @Param("type") String type);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(OrderCommentaryDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OrderCommentaryDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param workOrderId workOrderId 
     * @param type type
     * @return OrderCommentaryDto OrderCommentaryDto<br>
     */
    OrderCommentaryDto selectByPrimaryKey(@Param("workOrderId") Long workOrderId, @Param("type") String type);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OrderCommentaryDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OrderCommentaryDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param req req
     * @return List<OrderCommentaryDto>
     */
    List<OrderCommentaryDto> selectByReqCond(QryOrderCommentaryPagerListRequest req);
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param workOrderId workOrderId
     * @return List<OrderCommentaryDto>
     */
    List<OrderCommentaryDto> selectByWorkOrderId(Long workOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param orderCommentary orderCommentary
     */
    void deleteByCond(OrderCommentaryDto orderCommentary);

}