package com.room1000.suborder.repairorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;

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
public interface RepairOrderDtoMapper extends SuperMapper {
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
    int insert(RepairOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(RepairOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return RepairOrderDto RepairOrderDto<br>
     */
    RepairOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(RepairOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(RepairOrderDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return RepairOrderDto
     */ 
    RepairOrderDto selectDetailById(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return RepairOrderDto
     */
    RepairOrderDto selectByCode(String code);
}