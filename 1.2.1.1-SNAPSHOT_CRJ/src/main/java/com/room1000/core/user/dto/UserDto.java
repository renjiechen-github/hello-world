package com.room1000.core.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Description: yc_userinfo_tab实体
 *
 * Created on 2017年03月17日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class UserDto implements Serializable {
    /**
     * id 用户id
     */
    private Long id;

    /**
     * username 用户名称
     */
    private String username;

    /**
     * mobile 电话号码
     */
    private String mobile;

    /**
     * certificateno 证件号
     */
    private String certificateno;

    /**
     * qq qq号码
     */
    private String qq;

    /**
     * email 电子邮件
     */
    private String email;

    /**
     * wechat 微信
     */
    private String wechat;

    /**
     * sex 性别
     */
    private Long sex;

    /**
     * birthday 出生年月
     */
    private String birthday;

    /**
     * photourl 用户头像
     */
    private String photourl;

    /**
     * registertime 注册时间
     */
    private Date registertime;

    /**
     * descText 
     */
    private String descText;

    /**
     * isDelete 是否已删除（0:是;1:否）
     */
    private Long isDelete;

    /**
     * password 
     */
    private String password;

    /**
     * type 
     */
    private Long type;

    /**
     * lastlogintime 
     */
    private Date lastlogintime;

    /**
     * equipment 
     */
    private String equipment;

    /**
     * ipadress 
     */
    private String ipadress;

    /**
     * caAuthor 是否ca验证 0 否 1 是
     */
    private Long caAuthor;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno == null ? null : certificateno.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl == null ? null : photourl.trim();
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText == null ? null : descText.trim();
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment == null ? null : equipment.trim();
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress == null ? null : ipadress.trim();
    }

    public Long getCaAuthor() {
        return caAuthor;
    }

    public void setCaAuthor(Long caAuthor) {
        this.caAuthor = caAuthor;
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
        sb.append(", username=").append(username);
        sb.append(", mobile=").append(mobile);
        sb.append(", certificateno=").append(certificateno);
        sb.append(", qq=").append(qq);
        sb.append(", email=").append(email);
        sb.append(", wechat=").append(wechat);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", photourl=").append(photourl);
        sb.append(", registertime=").append(registertime);
        sb.append(", descText=").append(descText);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", password=").append(password);
        sb.append(", type=").append(type);
        sb.append(", lastlogintime=").append(lastlogintime);
        sb.append(", equipment=").append(equipment);
        sb.append(", ipadress=").append(ipadress);
        sb.append(", caAuthor=").append(caAuthor);
        sb.append(", registrationid=").append(registrationid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}