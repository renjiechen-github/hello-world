package com.room1000.workorder.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * Description: order_commentary实体
 *
 * Created on 2017年03月07日
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class OrderCommentaryDto implements Serializable {
    /**
     * workOrderId 工单主键
     */
    private Long workOrderId;

    /**
     * type 评价分类
     */
    private String type;

    /**
     * score 评分
     */
    private Long score;

    /**
     * comments 评论
     */
    private String comments;

    /**
     * commentaryPersonId 评论人主键
     */
    private Long commentaryPersonId;
    
    /**
     * commentDate 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentDate;
    
    /**
     * 类型名
     */
    private String typeName;
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 图片地址
     */
    private String imageUrl;

		public String getImageUrl()
		{
			return imageUrl;
		}

		public void setImageUrl(String imageUrl)
		{
			this.imageUrl = imageUrl;
		}

		public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Long getCommentaryPersonId() {
        return commentaryPersonId;
    }

    public void setCommentaryPersonId(Long commentaryPersonId) {
        this.commentaryPersonId = commentaryPersonId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderCommentaryDto [workOrderId=");
        builder.append(workOrderId);
        builder.append(", type=");
        builder.append(type);
        builder.append(", score=");
        builder.append(score);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", commentaryPersonId=");
        builder.append(commentaryPersonId);
        builder.append(", commentDate=");
        builder.append(commentDate);
        builder.append(", typeName=");
        builder.append(typeName);
        builder.append("]");
        return builder.toString();
    }
}