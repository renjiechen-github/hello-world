package com.room1000.workorder.dto;

import java.io.Serializable;

/**
 *
 * Description: order_commentary_type实体
 *
 * Created on 2017年03月07日
 * 
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class OrderCommentaryTypeDto implements Serializable {
    /**
     * type 类型
     */
    private String type;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 是否为根类型，根类型则为Y，普通类型为N。暂定不同类型在前端界面上不会录入备注，只有根类型才录入
     */
    private String rootFlag;

    /**
     * typeName 类型名
     */
    private String typeName;

    /**
     * typeNameEn 类型名 英文
     */
    private String typeNameEn;

    /**
     * comments 备注
     */
    private String comments;

    /**
     * priority 优先级
     */
    private Long priority;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(String rootFlag) {
        this.rootFlag = rootFlag;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn == null ? null : typeNameEn.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderCommentaryTypeDto [type=");
        builder.append(type);
        builder.append(", orderType=");
        builder.append(orderType);
        builder.append(", rootFlag=");
        builder.append(rootFlag);
        builder.append(", typeName=");
        builder.append(typeName);
        builder.append(", typeNameEn=");
        builder.append(typeNameEn);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", priority=");
        builder.append(priority);
        builder.append("]");
        return builder.toString();
    }
}