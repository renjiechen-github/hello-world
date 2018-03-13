package com.room1000.suborder.ownerrepairorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;

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
public interface OwnerRepairOrderDtoMapper extends SuperMapper {
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
    int insert(OwnerRepairOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OwnerRepairOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return OwnerRepairOrderDto OwnerRepairOrderDto<br>
     */
    OwnerRepairOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OwnerRepairOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OwnerRepairOrderDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return OwnerRepairOrderDto
     */
    OwnerRepairOrderDto selectDetailById(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return OwnerRepairOrderDto
     */
    OwnerRepairOrderDto selectByCode(String code);
}