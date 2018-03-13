package com.room1000.attr.dao;

import com.room1000.attr.dto.AttrCatgDto;
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
public interface AttrCatgDtoMapper extends SuperMapper {
    /**
     * Description: deleteByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return int int<br>
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Description: insert<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insert(AttrCatgDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int insertSelective(AttrCatgDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return AttrCatgDto AttrCatgDto<br>
     * @mbg.generated
     */
    AttrCatgDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AttrCatgDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     * @mbg.generated
     */
    int updateByPrimaryKey(AttrCatgDto record);

    /**
     * 
     * Description: selectByCode
     * 
     * @author jinyanan
     *
     * @param catgCode catgCode
     * @return AttrCatgDto
     */
    AttrCatgDto selectByCode(String catgCode);

    /**
     * 
     * Description: selectAllChildrenById
     * 
     * @author jinyanan
     *
     * @param catgId catgId
     * @return AttrCatgDto
     */
    AttrCatgDto selectAllChildrenById(Long catgId);
}