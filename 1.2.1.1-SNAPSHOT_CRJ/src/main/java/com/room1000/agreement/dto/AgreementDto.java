package com.room1000.agreement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Description: yc_agreement_tab实体
 *
 * Created on 2017年03月28日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class AgreementDto implements Serializable {
    /**
     * id 
     */
    private Long id;

    /**
     * code 
     */
    private String code;

    /**
     * name 
     */
    private String name;

    /**
     * type 
     */
    private Long type;

    /**
     * userId 
     */
    private Long userId;

    /**
     * usernameBk 
     */
    private String usernameBk;

    /**
     * userMobile 
     */
    private String userMobile;

    /**
     * beginTime 
     */
    private String beginTime;

    /**
     * endTime 
     */
    private String endTime;

    /**
     * fatherId 
     */
    private Long fatherId;

    /**
     * houseId 
     */
    private Long houseId;

    /**
     * filePath 
     */
    private String filePath;

    /**
     * descText 
     */
    private String descText;

    /**
     * status 
     */
    private String status;

    /**
     * cost 
     */
    private Float cost;

    /**
     * createTime 
     */
    private Date createTime;

    /**
     * isdelete 
     */
    private Long isdelete;

    /**
     * agents 所属帐号
     */
    private Long agents;

    /**
     * bankcard 
     */
    private String bankcard;

    /**
     * cardbank 
     */
    private String cardbank;

    /**
     * cardowen 
     */
    private String cardowen;

    /**
     * watercard 
     */
    private String watercard;

    /**
     * electriccard 
     */
    private String electriccard;

    /**
     * gascard 
     */
    private String gascard;

    /**
     * payType 支付类型
     */
    private Long payType;

    /**
     * sbcode 合约子编码
     */
    private String sbcode;

    /**
     * costA 每年租金列表
     */
    private String costA;

    /**
     * payTime 下次支付时间
     */
    private String payTime;

    /**
     * freePeriod 
     */
    private Long freePeriod;

    /**
     * waterMeter 
     */
    private String waterMeter;

    /**
     * gasMeter 
     */
    private String gasMeter;

    /**
     * electricityMeterH 
     */
    private String electricityMeterH;

    /**
     * electricityMeterL 
     */
    private String electricityMeterL;

    /**
     * electricityMeter 
     */
    private String electricityMeter;

    /**
     * sms 
     */
    private Long sms;

    /**
     * isCubicle 
     */
    private Long isCubicle;

    /**
     * keysCount 
     */
    private String keysCount;

    /**
     * areaid 小区编号
     */
    private String areaid;

    /**
     * operid 操作人id
     */
    private String operid;

    /**
     * bankid 银行编号
     */
    private String bankid;

    /**
     * propertypath 产权人附件
     */
    private String propertypath;

    /**
     * agentpath 代理人图片
     */
    private String agentpath;

    /**
     * payway 支付方式 支付宝 0 微信 1 银行卡 2
     */
    private String payway;

    /**
     * servicemonery 服务费
     */
    private String servicemonery;

    /**
     * propertymonery 物业费
     */
    private String propertymonery;

    /**
     * checkouttime 
     */
    private Date checkouttime;

    /**
     * notaryid 公正流水号
     */
    private String notaryid;

    /**
     * deposit 押金
     */
    private String deposit;

    /**
     * freeranktype 免租类型
     */
    private String freeranktype;

    /**
     * propertytype 产权证明文件
     */
    private String propertytype;

    /**
     * mortgage 抵押
     */
    private String mortgage;

    /**
     * propertycode 产权证明文件编号
     */
    private String propertycode;

    /**
     * propertyperson 产权人姓名
     */
    private String propertyperson;

    /**
     * coOwner 共有人
     */
    private String coOwner;

    /**
     * wtuserid 委托人用户id
     */
    private String wtuserid;

    /**
     * dluserid 代理人编号
     */
    private String dluserid;

    /**
     * wtmobile 委托人号码
     */
    private String wtmobile;

    /**
     * wtaddress 委托人住址
     */
    private String wtaddress;

    /**
     * wtidcard 委托方身份证
     */
    private String wtidcard;

    /**
     * wtname 委托方姓名
     */
    private String wtname;

    /**
     * wtemail 委托方邮件
     */
    private String wtemail;

    /**
     * wtrealaddress 委托人真实住址方邮件
     */
    private String wtrealaddress;

    /**
     * dlmobile 代理人号码
     */
    private String dlmobile;

    /**
     * dladdress 代理人住址
     */
    private String dladdress;

    /**
     * dlidcard 代理人身份证
     */
    private String dlidcard;

    /**
     * dlname 代理方姓名
     */
    private String dlname;

    /**
     * dlemail 代理方邮件
     */
    private String dlemail;

    /**
     * newOldMatched 房屋家具家电及厨卫设施
     */
    private String newOldMatched;

    /**
     * rentDeposit 出租押金
     */
    private String rentDeposit;

    /**
     * oldMatched 
     */
    private String oldMatched;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsernameBk() {
        return usernameBk;
    }

    public void setUsernameBk(String usernameBk) {
        this.usernameBk = usernameBk == null ? null : usernameBk.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText == null ? null : descText.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Long isdelete) {
        this.isdelete = isdelete;
    }

    public Long getAgents() {
        return agents;
    }

    public void setAgents(Long agents) {
        this.agents = agents;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard == null ? null : bankcard.trim();
    }

    public String getCardbank() {
        return cardbank;
    }

    public void setCardbank(String cardbank) {
        this.cardbank = cardbank == null ? null : cardbank.trim();
    }

    public String getCardowen() {
        return cardowen;
    }

    public void setCardowen(String cardowen) {
        this.cardowen = cardowen == null ? null : cardowen.trim();
    }

    public String getWatercard() {
        return watercard;
    }

    public void setWatercard(String watercard) {
        this.watercard = watercard == null ? null : watercard.trim();
    }

    public String getElectriccard() {
        return electriccard;
    }

    public void setElectriccard(String electriccard) {
        this.electriccard = electriccard == null ? null : electriccard.trim();
    }

    public String getGascard() {
        return gascard;
    }

    public void setGascard(String gascard) {
        this.gascard = gascard == null ? null : gascard.trim();
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public String getSbcode() {
        return sbcode;
    }

    public void setSbcode(String sbcode) {
        this.sbcode = sbcode == null ? null : sbcode.trim();
    }

    public String getCostA() {
        return costA;
    }

    public void setCostA(String costA) {
        this.costA = costA == null ? null : costA.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public Long getFreePeriod() {
        return freePeriod;
    }

    public void setFreePeriod(Long freePeriod) {
        this.freePeriod = freePeriod;
    }

    public String getWaterMeter() {
        return waterMeter;
    }

    public void setWaterMeter(String waterMeter) {
        this.waterMeter = waterMeter == null ? null : waterMeter.trim();
    }

    public String getGasMeter() {
        return gasMeter;
    }

    public void setGasMeter(String gasMeter) {
        this.gasMeter = gasMeter == null ? null : gasMeter.trim();
    }

    public String getElectricityMeterH() {
        return electricityMeterH;
    }

    public void setElectricityMeterH(String electricityMeterH) {
        this.electricityMeterH = electricityMeterH == null ? null : electricityMeterH.trim();
    }

    public String getElectricityMeterL() {
        return electricityMeterL;
    }

    public void setElectricityMeterL(String electricityMeterL) {
        this.electricityMeterL = electricityMeterL == null ? null : electricityMeterL.trim();
    }

    public String getElectricityMeter() {
        return electricityMeter;
    }

    public void setElectricityMeter(String electricityMeter) {
        this.electricityMeter = electricityMeter == null ? null : electricityMeter.trim();
    }

    public Long getSms() {
        return sms;
    }

    public void setSms(Long sms) {
        this.sms = sms;
    }

    public Long getIsCubicle() {
        return isCubicle;
    }

    public void setIsCubicle(Long isCubicle) {
        this.isCubicle = isCubicle;
    }

    public String getKeysCount() {
        return keysCount;
    }

    public void setKeysCount(String keysCount) {
        this.keysCount = keysCount == null ? null : keysCount.trim();
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid == null ? null : areaid.trim();
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid == null ? null : operid.trim();
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
    }

    public String getPropertypath() {
        return propertypath;
    }

    public void setPropertypath(String propertypath) {
        this.propertypath = propertypath == null ? null : propertypath.trim();
    }

    public String getAgentpath() {
        return agentpath;
    }

    public void setAgentpath(String agentpath) {
        this.agentpath = agentpath == null ? null : agentpath.trim();
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway == null ? null : payway.trim();
    }

    public String getServicemonery() {
        return servicemonery;
    }

    public void setServicemonery(String servicemonery) {
        this.servicemonery = servicemonery == null ? null : servicemonery.trim();
    }

    public String getPropertymonery() {
        return propertymonery;
    }

    public void setPropertymonery(String propertymonery) {
        this.propertymonery = propertymonery == null ? null : propertymonery.trim();
    }

    public Date getCheckouttime() {
        return checkouttime;
    }

    public void setCheckouttime(Date checkouttime) {
        this.checkouttime = checkouttime;
    }

    public String getNotaryid() {
        return notaryid;
    }

    public void setNotaryid(String notaryid) {
        this.notaryid = notaryid == null ? null : notaryid.trim();
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit == null ? null : deposit.trim();
    }

    public String getFreeranktype() {
        return freeranktype;
    }

    public void setFreeranktype(String freeranktype) {
        this.freeranktype = freeranktype == null ? null : freeranktype.trim();
    }

    public String getPropertytype() {
        return propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype == null ? null : propertytype.trim();
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage == null ? null : mortgage.trim();
    }

    public String getPropertycode() {
        return propertycode;
    }

    public void setPropertycode(String propertycode) {
        this.propertycode = propertycode == null ? null : propertycode.trim();
    }

    public String getPropertyperson() {
        return propertyperson;
    }

    public void setPropertyperson(String propertyperson) {
        this.propertyperson = propertyperson == null ? null : propertyperson.trim();
    }

    public String getCoOwner() {
        return coOwner;
    }

    public void setCoOwner(String coOwner) {
        this.coOwner = coOwner == null ? null : coOwner.trim();
    }

    public String getWtuserid() {
        return wtuserid;
    }

    public void setWtuserid(String wtuserid) {
        this.wtuserid = wtuserid == null ? null : wtuserid.trim();
    }

    public String getDluserid() {
        return dluserid;
    }

    public void setDluserid(String dluserid) {
        this.dluserid = dluserid == null ? null : dluserid.trim();
    }

    public String getWtmobile() {
        return wtmobile;
    }

    public void setWtmobile(String wtmobile) {
        this.wtmobile = wtmobile == null ? null : wtmobile.trim();
    }

    public String getWtaddress() {
        return wtaddress;
    }

    public void setWtaddress(String wtaddress) {
        this.wtaddress = wtaddress == null ? null : wtaddress.trim();
    }

    public String getWtidcard() {
        return wtidcard;
    }

    public void setWtidcard(String wtidcard) {
        this.wtidcard = wtidcard == null ? null : wtidcard.trim();
    }

    public String getWtname() {
        return wtname;
    }

    public void setWtname(String wtname) {
        this.wtname = wtname == null ? null : wtname.trim();
    }

    public String getWtemail() {
        return wtemail;
    }

    public void setWtemail(String wtemail) {
        this.wtemail = wtemail == null ? null : wtemail.trim();
    }

    public String getWtrealaddress() {
        return wtrealaddress;
    }

    public void setWtrealaddress(String wtrealaddress) {
        this.wtrealaddress = wtrealaddress == null ? null : wtrealaddress.trim();
    }

    public String getDlmobile() {
        return dlmobile;
    }

    public void setDlmobile(String dlmobile) {
        this.dlmobile = dlmobile == null ? null : dlmobile.trim();
    }

    public String getDladdress() {
        return dladdress;
    }

    public void setDladdress(String dladdress) {
        this.dladdress = dladdress == null ? null : dladdress.trim();
    }

    public String getDlidcard() {
        return dlidcard;
    }

    public void setDlidcard(String dlidcard) {
        this.dlidcard = dlidcard == null ? null : dlidcard.trim();
    }

    public String getDlname() {
        return dlname;
    }

    public void setDlname(String dlname) {
        this.dlname = dlname == null ? null : dlname.trim();
    }

    public String getDlemail() {
        return dlemail;
    }

    public void setDlemail(String dlemail) {
        this.dlemail = dlemail == null ? null : dlemail.trim();
    }

    public String getNewOldMatched() {
        return newOldMatched;
    }

    public void setNewOldMatched(String newOldMatched) {
        this.newOldMatched = newOldMatched == null ? null : newOldMatched.trim();
    }

    public String getRentDeposit() {
        return rentDeposit;
    }

    public void setRentDeposit(String rentDeposit) {
        this.rentDeposit = rentDeposit == null ? null : rentDeposit.trim();
    }

    public String getOldMatched() {
        return oldMatched;
    }

    public void setOldMatched(String oldMatched) {
        this.oldMatched = oldMatched == null ? null : oldMatched.trim();
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
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", userId=").append(userId);
        sb.append(", usernameBk=").append(usernameBk);
        sb.append(", userMobile=").append(userMobile);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", fatherId=").append(fatherId);
        sb.append(", houseId=").append(houseId);
        sb.append(", filePath=").append(filePath);
        sb.append(", descText=").append(descText);
        sb.append(", status=").append(status);
        sb.append(", cost=").append(cost);
        sb.append(", createTime=").append(createTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", agents=").append(agents);
        sb.append(", bankcard=").append(bankcard);
        sb.append(", cardbank=").append(cardbank);
        sb.append(", cardowen=").append(cardowen);
        sb.append(", watercard=").append(watercard);
        sb.append(", electriccard=").append(electriccard);
        sb.append(", gascard=").append(gascard);
        sb.append(", payType=").append(payType);
        sb.append(", sbcode=").append(sbcode);
        sb.append(", costA=").append(costA);
        sb.append(", payTime=").append(payTime);
        sb.append(", freePeriod=").append(freePeriod);
        sb.append(", waterMeter=").append(waterMeter);
        sb.append(", gasMeter=").append(gasMeter);
        sb.append(", electricityMeterH=").append(electricityMeterH);
        sb.append(", electricityMeterL=").append(electricityMeterL);
        sb.append(", electricityMeter=").append(electricityMeter);
        sb.append(", sms=").append(sms);
        sb.append(", isCubicle=").append(isCubicle);
        sb.append(", keysCount=").append(keysCount);
        sb.append(", areaid=").append(areaid);
        sb.append(", operid=").append(operid);
        sb.append(", bankid=").append(bankid);
        sb.append(", propertypath=").append(propertypath);
        sb.append(", agentpath=").append(agentpath);
        sb.append(", payway=").append(payway);
        sb.append(", servicemonery=").append(servicemonery);
        sb.append(", propertymonery=").append(propertymonery);
        sb.append(", checkouttime=").append(checkouttime);
        sb.append(", notaryid=").append(notaryid);
        sb.append(", deposit=").append(deposit);
        sb.append(", freeranktype=").append(freeranktype);
        sb.append(", propertytype=").append(propertytype);
        sb.append(", mortgage=").append(mortgage);
        sb.append(", propertycode=").append(propertycode);
        sb.append(", propertyperson=").append(propertyperson);
        sb.append(", coOwner=").append(coOwner);
        sb.append(", wtuserid=").append(wtuserid);
        sb.append(", dluserid=").append(dluserid);
        sb.append(", wtmobile=").append(wtmobile);
        sb.append(", wtaddress=").append(wtaddress);
        sb.append(", wtidcard=").append(wtidcard);
        sb.append(", wtname=").append(wtname);
        sb.append(", wtemail=").append(wtemail);
        sb.append(", wtrealaddress=").append(wtrealaddress);
        sb.append(", dlmobile=").append(dlmobile);
        sb.append(", dladdress=").append(dladdress);
        sb.append(", dlidcard=").append(dlidcard);
        sb.append(", dlname=").append(dlname);
        sb.append(", dlemail=").append(dlemail);
        sb.append(", newOldMatched=").append(newOldMatched);
        sb.append(", rentDeposit=").append(rentDeposit);
        sb.append(", oldMatched=").append(oldMatched);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}