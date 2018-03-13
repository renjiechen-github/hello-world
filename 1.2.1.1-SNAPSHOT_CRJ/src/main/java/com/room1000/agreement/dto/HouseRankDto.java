package com.room1000.agreement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Description: yc_houserank_tab实体
 *
 * Created on 2017年04月11日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class HouseRankDto implements Serializable {
    /**
     * id 房源id
     */
    private Long id;

    /**
     * houseId 房源基表id
     */
    private Long houseId;

    /**
     * istop 
     */
    private Long istop;

    /**
     * title 房源标题
     */
    private String title;

    /**
     * createtime 创建时间
     */
    private Date createtime;

    /**
     * rankType 房源类型1合租 其他事整租
     */
    private String rankType;

    /**
     * rankName 房源名称
     */
    private String rankName;

    /**
     * beginDate 起租时间
     */
    private String beginDate;

    /**
     * failureDate 到期时间
     */
    private String failureDate;

    /**
     * feeId 资费配置id
     */
    private String feeId;

    /**
     * fee 基础单价
     */
    private Long fee;

    /**
     * rankDesc 房源说明
     */
    private String rankDesc;

    /**
     * allocationIdA 标配设备
     */
    private String allocationIdA;

    /**
     * allocationIdB 可选设备
     */
    private String allocationIdB;

    /**
     * images 房源图片源
     */
    private String images;

    /**
     * labelId 标签
     */
    private String labelId;

    /**
     * accountId 房源所属用户
     */
    private String accountId;

    /**
     * rankStatus 房源状态
     */
    private String rankStatus;

    /**
     * isdelete 删除状态
     */
    private String isdelete;

    /**
     * decorate 装修程度
     */
    private String decorate;

    /**
     * rankSpec 出租形式
     */
    private String rankSpec;

    /**
     * rankArea 面积
     */
    private String rankArea;

    /**
     * rankCode 
     */
    private String rankCode;

    /**
     * operid 
     */
    private Long operid;

    /**
     * oldState 
     */
    private Long oldState;

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

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getIstop() {
        return istop;
    }

    public void setIstop(Long istop) {
        this.istop = istop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType == null ? null : rankType.trim();
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName == null ? null : rankName.trim();
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getFailureDate() {
        return failureDate;
    }

    public void setFailureDate(String failureDate) {
        this.failureDate = failureDate == null ? null : failureDate.trim();
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId == null ? null : feeId.trim();
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc == null ? null : rankDesc.trim();
    }

    public String getAllocationIdA() {
        return allocationIdA;
    }

    public void setAllocationIdA(String allocationIdA) {
        this.allocationIdA = allocationIdA == null ? null : allocationIdA.trim();
    }

    public String getAllocationIdB() {
        return allocationIdB;
    }

    public void setAllocationIdB(String allocationIdB) {
        this.allocationIdB = allocationIdB == null ? null : allocationIdB.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId == null ? null : labelId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getRankStatus() {
        return rankStatus;
    }

    public void setRankStatus(String rankStatus) {
        this.rankStatus = rankStatus == null ? null : rankStatus.trim();
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete == null ? null : isdelete.trim();
    }

    public String getDecorate() {
        return decorate;
    }

    public void setDecorate(String decorate) {
        this.decorate = decorate == null ? null : decorate.trim();
    }

    public String getRankSpec() {
        return rankSpec;
    }

    public void setRankSpec(String rankSpec) {
        this.rankSpec = rankSpec == null ? null : rankSpec.trim();
    }

    public String getRankArea() {
        return rankArea;
    }

    public void setRankArea(String rankArea) {
        this.rankArea = rankArea == null ? null : rankArea.trim();
    }

    public String getRankCode() {
        return rankCode;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode == null ? null : rankCode.trim();
    }

    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }

    public Long getOldState() {
        return oldState;
    }

    public void setOldState(Long oldState) {
        this.oldState = oldState;
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
        sb.append(", houseId=").append(houseId);
        sb.append(", istop=").append(istop);
        sb.append(", title=").append(title);
        sb.append(", createtime=").append(createtime);
        sb.append(", rankType=").append(rankType);
        sb.append(", rankName=").append(rankName);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", failureDate=").append(failureDate);
        sb.append(", feeId=").append(feeId);
        sb.append(", fee=").append(fee);
        sb.append(", rankDesc=").append(rankDesc);
        sb.append(", allocationIdA=").append(allocationIdA);
        sb.append(", allocationIdB=").append(allocationIdB);
        sb.append(", images=").append(images);
        sb.append(", labelId=").append(labelId);
        sb.append(", accountId=").append(accountId);
        sb.append(", rankStatus=").append(rankStatus);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", decorate=").append(decorate);
        sb.append(", rankSpec=").append(rankSpec);
        sb.append(", rankArea=").append(rankArea);
        sb.append(", rankCode=").append(rankCode);
        sb.append(", operid=").append(operid);
        sb.append(", oldState=").append(oldState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}