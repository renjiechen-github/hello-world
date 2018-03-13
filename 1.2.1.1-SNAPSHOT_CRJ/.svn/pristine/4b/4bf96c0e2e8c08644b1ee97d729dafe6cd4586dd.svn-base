package com.room1000.suborder.complaintorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderHisDto;

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
public interface ComplaintOrderHisDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param priority priority 
     * @param id id
     * @return int int<br>
     */
    int deleteByPrimaryKey(@Param("priority") Long priority, @Param("id") Long id);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insert(ComplaintOrderHisDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(ComplaintOrderHisDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param priority priority 
     * @param id id
     * @return ComplaintOrderHisDto ComplaintOrderHisDto<br>
     */
    ComplaintOrderHisDto selectByPrimaryKey(@Param("priority") Long priority, @Param("id") Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(ComplaintOrderHisDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(ComplaintOrderHisDto record);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return Long
     */
    Long selectMaxPriority(Long id);

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param id id
     * @return List<ComplaintOrderHisDto>
     */
    List<ComplaintOrderHisDto> selectById(Long id);
}