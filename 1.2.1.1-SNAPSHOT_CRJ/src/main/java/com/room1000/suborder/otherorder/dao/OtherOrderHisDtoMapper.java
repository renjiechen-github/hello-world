package com.room1000.suborder.otherorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.suborder.otherorder.dto.OtherOrderHisDto;

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
public interface OtherOrderHisDtoMapper extends SuperMapper {
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
    int insert(OtherOrderHisDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(OtherOrderHisDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param priority priority 
     * @param id id
     * @return OtherOrderHisDto OtherOrderHisDto<br>
     */
    OtherOrderHisDto selectByPrimaryKey(@Param("priority") Long priority, @Param("id") Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(OtherOrderHisDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(OtherOrderHisDto record);

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
     * @return List<OtherOrderHisDto>
     */
    List<OtherOrderHisDto> selectById(Long id);
}