package com.room1000.core.user.dao;

import java.util.List;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.core.user.dto.StaffDto;

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
public interface StaffDtoMapper extends SuperMapper {
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
    int insert(StaffDto record);

    /**
     * Description: insertSelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int insertSelective(StaffDto record);

    /**
     * Description: selectByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param id id
     * @return StaffDto StaffDto<br>
     */
    StaffDto selectByPrimaryKey(Long id);

    /**
     * Description: updateByPrimaryKeySelective<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKeySelective(StaffDto record);

    /**
     * Description: updateByPrimaryKey<br>
     *
     * @author jinyanan <br>
     * @param record record
     * @return int int<br>
     */
    int updateByPrimaryKey(StaffDto record);
    
    /**
     * 
     * Description: selectByName
     * 
     * @author jinyanan
     *
     * @param name name
     * @return StaffDto
     */
    StaffDto selectByName(String name);
    
    /**
     * 
     * Description: 根据出租合约主键查询房屋所在网格的管家
     * 
     * @author jinyanan
     *
     * @param agreementId agreementId
     * @return StaffDto
     */
    List<StaffDto> selectByAgreementId(Long agreementId);

    /**
     * 
     * Description: 根据条件查询员工
     * 
     * @author jinyanan
     *
     * @param staffDto staffDto
     * @return List<StaffDto>
     */
    List<StaffDto> selectByCond(StaffDto staffDto);

    /**
     * 
     * Description: 根据收房合约查询对应管家
     * 
     * @author jinyanan
     *
     * @param takeHouseOrderId takeHouseOrderId
     * @return StaffDto
     */
    StaffDto selectByTakeHouseAgreementId(Long takeHouseOrderId);
}