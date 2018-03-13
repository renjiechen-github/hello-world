package com.room1000.suborder.ownerrepairorder.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * Description: owner_repair_order_value_his 实体
 *
 * Created on 2017年05月16日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Table(name = "owner_repair_order_value_his")
public class OwnerRepairOrderValueHisDto implements Serializable {
    /**
     * 主键
     * @mbg.generated
     */
    @Id
    private Long id;

    /**
     * 优先级
     * @mbg.generated
     */
    @Id
    private Long priority;

    /**
     * 业主维修订单主键
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
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
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

    /**
     * Description: toString<br>
     *
     * @author autoCreated <br>
    
     * @return String String<br>
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", priority=").append(priority);
        sb.append(", subOrderId=").append(subOrderId);
        sb.append(", attrId=").append(attrId);
        sb.append(", attrPath=").append(attrPath);
        sb.append(", textInput=").append(textInput);
        sb.append(", attrValueId=").append(attrValueId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}