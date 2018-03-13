package com.room1000.suborder.complaintorder.dao;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderDto;

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
public interface ComplaintOrderDtoMapper extends SuperMapper {
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
    int insert(ComplaintOrderDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(ComplaintOrderDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return ComplaintOrderDto ComplaintOrderDto<br>
     */
    ComplaintOrderDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(ComplaintOrderDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(ComplaintOrderDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return ComplaintOrderDto
     */
    ComplaintOrderDto selectDetailById(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param code code
     * @return ComplaintOrderDto
     */
    ComplaintOrderDto selectByCode(String code);
}