package com.room1000.recommend.dto;

import java.io.Serializable;

/**
 *
 * Description: yc_recommend_relation实体
 *
 * Created on 2017年04月24日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class RecommendRelationDto implements Serializable {
    /**
     * recommendId 
     */
    private Long recommendId;

    /**
     * relationType 1看房记录2合约记录
     */
    private Long relationType;

    /**
     * relationId 
     */
    private String relationId;

    /**
     * state 1进行中2已结束
     */
    private Long state;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public Long getRelationType() {
        return relationType;
    }

    public void setRelationType(Long relationType) {
        this.relationType = relationType;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    /**
     * Description: toString<br>
     *
     * @author jinyanan <br>
    
     * @return String String<br>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recommendId=").append(recommendId);
        sb.append(", relationType=").append(relationType);
        sb.append(", relationId=").append(relationId);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}