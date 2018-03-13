package com.room1000.workorder.dao;

import java.util.List;
import java.util.Map;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.workorder.dto.WorkOrderTypeDto;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月6日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface WorkOrderTypeDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type
     * @return int int<br>
     * @mbg.generated
     */
    int deleteByPrimaryKey(String type);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insert(WorkOrderTypeDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insertSelective(WorkOrderTypeDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type
     * @return WorkOrderTypeDto WorkOrderTypeDto<br>
     * @mbg.generated
     */
    WorkOrderTypeDto selectByPrimaryKey(String type);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WorkOrderTypeDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKey(WorkOrderTypeDto record);

    /**
     * 
     * Description: 查询所有订单类型
     * 
     * @author jinyanan
     *
     * @return List<WorkOrderTypeDto> 
     */
    List<WorkOrderTypeDto> selectAll();
    
    /**
     * 查询订单类型
     * @return
     */
		List<Map<String, Object>> selectWorkOrderType();
}