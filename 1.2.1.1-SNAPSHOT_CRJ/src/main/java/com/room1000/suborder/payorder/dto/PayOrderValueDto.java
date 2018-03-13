package com.room1000.suborder.payorder.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.room1000.workorder.dto.SubOrderValueDto;


/**
 *
 * Description: pay_order_value 实体
 *
 * Created on 2017年05月23日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Table(name = "pay_order_value")
public class PayOrderValueDto extends SubOrderValueDto implements Serializable {
    /**
     * 主键
     * @mbg.generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付订单主键
     * @mbg.generated
     */
    @Column(name = "sub_order_id")
    private Long subOrderId;

    /**
     * 属性主键
     * @mbg.generated
     */
    @Column(name = "attr_id")
    private Long attrId;

    /**
     * 由于属性目录下还可以继续配置目录，就导致可能父目录和子目录同时拥有相同的属性。所以使用路径来进行区别
     * @mbg.generated
     */
    @Column(name = "attr_path")
    private String attrPath;

    /**
     * 界面输入项
     * @mbg.generated
     */
    @Column(name = "text_input")
    private String textInput;

    /**
     * 属性可选项主键
     * @mbg.generated
     */
    @Column(name = "attr_value_id")
    private Long attrValueId;
    
    /**
     * attrType
     */
    @Transient
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

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PayOrderValueDto [id=");
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