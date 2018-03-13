package com.room1000.suborder.otherorder.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.otherorder.dto.OtherOrderValueDto;

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
public interface OtherOrderValueDtoMapper extends SuperMapper {
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
    int insert(OtherOrderValueDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OtherOrderValueDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return OtherOrderValueDto OtherOrderValueDto<br>
     */
    OtherOrderValueDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OtherOrderValueDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OtherOrderValueDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param subOrderId subOrderId
     * @return List<OtherOrderValueDto>
     */
    List<OtherOrderValueDto> selectBySubOrderId(Long subOrderId);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param otherOrderValueDto otherOrderValueDto
     * @return OtherOrderValueDto
     */
    OtherOrderValueDto selectByAttrPath(OtherOrderValueDto otherOrderValueDto);

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