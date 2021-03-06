package com.room1000.suborder.cleaningorder.dto;

import java.io.Serializable;

/**
 *
 * Description: cleaning_order_state实体
 *
 * Created on 2017年03月07日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class CleaningOrderStateDto implements Serializable {
    /**
     * state 状态
     */
    private String state;

    /**
     * stateName 状态名
     */
    private String stateName;

    /**
     * stateNameEn 状态名 英文
     */
    private String stateNameEn;

    /**
     * comments 备注
     */
    private String comments;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName == null ? null : stateName.trim();
    }

    public String getStateNameEn() {
        return stateNameEn;
    }

    public void setStateNameEn(String stateNameEn) {
        this.stateNameEn = stateNameEn == null ? null : stateNameEn.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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
        sb.append(", state=").append(state);
        sb.append(", stateName=").append(stateName);
        sb.append(", stateNameEn=").append(stateNameEn);
        sb.append(", comments=").append(comments);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}