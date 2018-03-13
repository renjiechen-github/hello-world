package com.room1000.workorder.dto;

import javax.persistence.Transient;

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
public class SubOrderValueDto {
    
    /**
     * id 主键
     */
    @Transient
    private Long id;

    /**
     * subOrderId 投诉订单主键
     */
    @Transient
    private Long subOrderId;

    /**
     * attrId 属性主键
     */
    @Transient
    private Long attrId;

    /**
     * attrPath 由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别
     */
    @Transient
    private String attrPath;

    /**
     * textInput 界面输入项
     */
    @Transient
    private String textInput;

    /**
     * attrValueId 属性可选项主键
     */
    @Transient
    private Long attrValueId;

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
        this.attrPath = attrPath;
    }

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

    public Long getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Long attrValueId) {
        this.attrValueId = attrValueId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SubOrderValueDto [id=");
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
        builder.append("]");
        return builder.toString();
    }
    
}
