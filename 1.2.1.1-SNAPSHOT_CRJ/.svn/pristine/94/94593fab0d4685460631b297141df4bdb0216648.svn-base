package com.room1000.suborder.ownerrepairorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueDto;

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
public interface OwnerRepairOrderValueDtoMapper extends SuperMapper {
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
    int insert(OwnerRepairOrderValueDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OwnerRepairOrderValueDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return OwnerRepairOrderValueDto OwnerRepairOrderValueDto<br>
     */
    OwnerRepairOrderValueDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OwnerRepairOrderValueDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OwnerRepairOrderValueDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<OwnerRepairOrderValueDto>
     */
    List<OwnerRepairOrderValueDto> selectBySubOrderId(Long subOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param ownerRepairOrderValueDto ownerRepairOrderValueDto
     * @return OwnerRepairOrderValueDto
     */
    OwnerRepairOrderValueDto selectByAttrPath(OwnerRepairOrderValueDto ownerRepairOrderValueDto);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     */
    void deleteBySubOrderId(Long subOrderId);
}