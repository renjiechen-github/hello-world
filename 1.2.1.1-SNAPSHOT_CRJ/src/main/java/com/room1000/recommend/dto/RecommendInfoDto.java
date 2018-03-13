package com.room1000.recommend.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.room1000.agreement.dto.AgreementDto;
import com.room1000.core.user.dto.UserDto;

/**
 *
 * Description: yc_recommend_info实体
 *
 * Created on 2017年04月24日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class RecommendInfoDto implements Serializable {
    /**
     * id 主键
     */
    private Long id;

    /**
     * name 被推荐人的名称
     */
    private String name;

    /**
     * mobile 被推荐人手机号
     */
    private String mobile;

    /**
     * areaId 被推荐人的区域di
     */
    private Long areaId;

    /**
     * openId 创建人id
     */
    private Long openId;

    /**
     * openTime 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;

    /**
     * kfTime 看房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date kfTime;

    /**
     * state 0、刚创建 1、用户已确认 2、推荐成功 3、推荐失败4、看房中
     */
    private Long state;

    /**
     * stateTime 状态更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stateTime;
    
    /**
     * agreementId 合约id
     */
    private Long agreementId;
    
    /**
     * 推荐人
     */
    private UserDto openUser;
    
    /**
     * 出租合约
     */
    private AgreementDto agreement;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getOpenId() {
        return openId;
    }

    public void setOpenId(Long openId) {
        this.openId = openId;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getKfTime() {
        return kfTime;
    }

    public void setKfTime(Date kfTime) {
        this.kfTime = kfTime;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public UserDto getOpenUser() {
        return openUser;
    }

    public void setOpenUser(UserDto openUser) {
        this.openUser = openUser;
    }

    public AgreementDto getAgreement() {
        return agreement;
    }

    public void setAgreement(AgreementDto agreement) {
        this.agreement = agreement;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RecommendInfoDto [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", mobile=");
        builder.append(mobile);
        builder.append(", areaId=");
        builder.append(areaId);
        builder.append(", openId=");
        builder.append(openId);
        builder.append(", openTime=");
        builder.append(openTime);
        builder.append(", kfTime=");
        builder.append(kfTime);
        builder.append(", state=");
        builder.append(state);
        builder.append(", stateTime=");
        builder.append(stateTime);
        builder.append(", agreementId=");
        builder.append(agreementId);
        builder.append(", openUser=");
        builder.append(openUser);
        builder.append(", agreement=");
        builder.append(agreement);
        builder.append("]");
        return builder.toString();
    }
}