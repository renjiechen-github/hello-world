package com.room1000.recommend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.recommend.dto.RecommendRelationDto;

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
public interface RecommendRelationDtoMapper extends SuperMapper
{
	/**
	 * Description: insert<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int insert(RecommendRelationDto record);

	/**
	 * Description: insertSelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 */
	int insertSelective(RecommendRelationDto record);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param relationType
	 *          relationType
	 * @param relationId
	 *          relationId
	 * @return RecommendRelationDto
	 */
	RecommendRelationDto selectByPrimaryKey(@Param("relationType") Long relationType,
			@Param("relationId") String relationId);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param recommendId
	 *          recommendId
	 * @param relationType
	 *          relationType
	 * @return RecommendRelationDto
	 */
	RecommendRelationDto selectByRecommendIdAndType(@Param("recommendId") Long recommendId,
			@Param("relationType") Long relationType);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param recommendRelationDto
	 *          recommendRelationDto
	 * @return int
	 */
	int updateByPrimaryKey(RecommendRelationDto recommendRelationDto);

	/**
	 * 根据recommendId查询关联关系表中总数
	 * 
	 * @param recommendId
	 * @return
	 */
	List<RecommendRelationDto> selectTotalByRecommendId(Long recommendId);
}