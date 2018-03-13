package com.room1000.suborder.cleaningorder.dto;

import java.io.Serializable;

import com.room1000.workorder.dto.SubOrderValueDto;

/**
 *
 * Description: cleaning_order_value实体
 *
 * Created on 2017年03月07日
 * 
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class CleaningOrderValueDto extends SubOrderValueDto implements Serializable {
    /**
     * id 主键
     */
    private Long id;

    /**
     * subOrderId 保洁单主键
     */
    private Long subOrderId;

    /**
     * attrId 属性主键
     */
    private Long attrId;

    /**
     * attrPath 由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别
     */
    private String attrPath;

    /**
     * textInput 界面输入项
     */
    private String textInput;

    /**
     * attrValueId 属性可选项主键
     */
    private Long attrValueId;

    /**
     * attrType
     */
    private String attrType;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrPath() {
        return attrPath;
    }

    public void setAttrPath(String attrPath) {
        this.attrPath = attrPath == null ? null : attrPath.trim();
    }

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput == null ? null : textInput.trim();
    }

    public Long getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Long attrValueId) {
        this.attrValueId = attrValueId;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CleaningOrderValueDto [id=");
        builder.append(id);
        builder.append(", subOrderId=");
        builder.append(subOrderId);
        builder.append(", attrId=");
        builder.append(attrId);
        builder.append(", attrPath=");
        builder.append(attrPath);
        builder.append(", textInput=");
        builder.append(textInput);
        builder.append(", attrValueId=");
        builder.append(attrValueId);
        builder.append(", attrType=");
        builder.append(attrType);
        builder.append("]");
        return builder.toString();
    }
}