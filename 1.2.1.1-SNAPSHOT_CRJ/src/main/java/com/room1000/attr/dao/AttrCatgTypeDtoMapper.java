package com.room1000.attr.dao;

import com.room1000.attr.dto.AttrCatgTypeDto;
import com.room1000.core.mybatis.SuperMapper;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月7日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface AttrCatgTypeDtoMapper extends SuperMapper {
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
    int insert(AttrCatgTypeDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insertSelective(AttrCatgTypeDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param type type
     * @return AttrCatgTypeDto AttrCatgTypeDto<br>
     * @mbg.generated
     */
    AttrCatgTypeDto selectByPrimaryKey(String type);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AttrCatgTypeDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKey(AttrCatgTypeDto record);
}