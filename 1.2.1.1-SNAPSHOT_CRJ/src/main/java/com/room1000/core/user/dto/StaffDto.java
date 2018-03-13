package com.room1000.core.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Description: yc_account_tab实体
 *
 * Created on 2017年03月17日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class StaffDto implements Serializable {
    /**
     * id 
     */
    private Long id;

    /**
     * mobile 
     */
    private String mobile;

    /**
     * name 
     */
    private String name;

    /**
     * type 
     */
    private String type;

    /**
     * roleid 
     */
    private Long roleid;

    /**
     * descText 
     */
    private String descText;

    /**
     * createTime 
     */
    private Date createTime;

    /**
     * relation 
     */
    private Boolean relation;

    /**
     * passwd 
     */
    private String passwd;

    /**
     * isDelete 
     */
    private Long isDelete;

    /**
     * updatetime 
     */
    private Date updatetime;

    /**
     * ipadress 
     */
    private String ipadress;

    /**
     * equipment 
     */
    private String equipment;

    /**
     * lastlogintime 
     */
    private Date lastlogintime;

    /**
     * state 
     */
    private Long state;

    /**
     * headerimage 
     */
    private String headerimage;

    /**
     * gId 网格化管理表中id
     */
    private Long gId;

    /**
     * registrationid 
     */
    private String registrationid;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText == null ? null : descText.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getRelation() {
        return relation;
    }

    public void setRelation(Boolean relation) {
        this.relation = relation;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress == null ? null : ipadress.trim();
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment == null ? null : equipment.trim();
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getHeaderimage() {
        return headerimage;
    }

    public void setHeaderimage(String headerimage) {
        this.headerimage = headerimage == null ? null : headerimage.trim();
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @return Long
     */
    public Long getgId() {
        return gId;
    }

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param gId gId
     */
    public void setgId(Long gId) {
        this.gId = gId;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid == null ? null : registrationid.trim();
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
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", roleid=").append(roleid);
        sb.append(", descText=").append(descText);
        sb.append(", createTime=").append(createTime);
        sb.append(", relation=").append(relation);
        sb.append(", passwd=").append(passwd);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", ipadress=").append(ipadress);
        sb.append(", equipment=").append(equipment);
        sb.append(", lastlogintime=").append(lastlogintime);
        sb.append(", state=").append(state);
        sb.append(", headerimage=").append(headerimage);
        sb.append(", gId=").append(gId);
        sb.append(", registrationid=").append(registrationid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}