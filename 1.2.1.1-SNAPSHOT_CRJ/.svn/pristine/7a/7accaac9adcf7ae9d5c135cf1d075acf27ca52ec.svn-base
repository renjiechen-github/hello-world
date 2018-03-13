package com.room1000.workorder.dto;

import java.io.Serializable;

/**
 *
 * Description: sub_order_state实体
 *
 * Created on 2017年03月27日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class SubOrderStateDto implements Serializable {
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
     * 是否隐藏，Y隐藏，N不隐藏
     */
    private String hidden;

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

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SubOrderStateDto [state=");
        builder.append(state);
        builder.append(", stateName=");
        builder.append(stateName);
        builder.append(", stateNameEn=");
        builder.append(stateNameEn);
        builder.append(", hidden=");
        builder.append(hidden);
        builder.append(", comments=");
        builder.append(comments);
        builder.append("]");
        return builder.toString();
    }
}