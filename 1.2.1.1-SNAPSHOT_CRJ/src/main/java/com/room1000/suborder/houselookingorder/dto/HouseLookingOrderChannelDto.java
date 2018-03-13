package com.room1000.suborder.houselookingorder.dto;

import java.io.Serializable;

/**
 *
 * Description: house_looking_order_channel实体
 *
 * Created on 2017年04月14日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class HouseLookingOrderChannelDto implements Serializable {
    /**
     * channel 渠道
     */
    private String channel;

    /**
     * channelName 渠道名
     */
    private String channelName;

    /**
     * channelNameEn 渠道名 英文
     */
    private String channelNameEn;

    /**
     * comments 备注
     */
    private String comments;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelNameEn() {
        return channelNameEn;
    }

    public void setChannelNameEn(String channelNameEn) {
        this.channelNameEn = channelNameEn == null ? null : channelNameEn.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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
        sb.append(", channel=").append(channel);
        sb.append(", channelName=").append(channelName);
        sb.append(", channelNameEn=").append(channelNameEn);
        sb.append(", comments=").append(comments);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}