package com.room1000.suborder.otherorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.otherorder.dto.OtherOrderDto;

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
public interface OtherOrderDtoMapper extends SuperMapper {
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
    int insert(OtherOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OtherOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return OtherOrderDto OtherOrderDto<br>
     */
    OtherOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OtherOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OtherOrderDto record);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param id id
     * @return OtherOrderDto
     */
    OtherOrderDto selectDetailById(Long id);

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param code code
     * @return OtherOrderDto
     */
    OtherOrderDto selectByCode(String code);
}