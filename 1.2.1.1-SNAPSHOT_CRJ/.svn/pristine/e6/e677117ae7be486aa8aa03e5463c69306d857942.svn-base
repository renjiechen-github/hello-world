package com.room1000.attr.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class AttrCatgDto implements Serializable {
    /**
     * id 主键
     * @mbg.generated
     */
    private Long id;

    /**
     * name 目录名
     * @mbg.generated
     */
    private String name;

    /**
     * code 目录编码
     * @mbg.generated
     */
    private String code;

    /**
     * type 目录类型
     * @mbg.generated
     */
    private String type;

    /**
     * state 状态
     * @mbg.generated
     */
    private String state;

    /**
     * stateDate 状态变更时间
     * @mbg.generated
     */
    private Date stateDate;
    
    /**
     * 子目录
     */
    private List<AttrCatgDto> attrCatgChildren;
    
    /**
     * 子属性
     */
    private List<AttrDto> attrChildren;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }
    
    public List<AttrCatgDto> getAttrCatgChildren() {
        return attrCatgChildren;
    }

    public void setAttrCatgChildren(List<AttrCatgDto> attrCatgChildren) {
        this.attrCatgChildren = attrCatgChildren;
    }

    public List<AttrDto> getAttrChildren() {
        return attrChildren;
    }

    public void setAttrChildren(List<AttrDto> attrChildren) {
        this.attrChildren = attrChildren;
    }

    /**
     * Description: toString<br>
     *
     * @author jinyanan <br>
    
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
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", type=").append(type);
        sb.append(", state=").append(state);
        sb.append(", stateDate=").append(stateDate);
        sb.append(", attrCatgChildren=").append(attrCatgChildren);
        sb.append(", attrChildren=").append(attrChildren);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}