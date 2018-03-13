package com.room1000.workorder.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class QryOrderCommentaryPagerListRequest {
    
    /**
     * pageNum
     */
    private int pageNum;
    
    /**
     * pageSize
     */
    private int pageSize;

    /**
     * workOrderId
     */
    private Long workOrderId;

    /**
     * type
     */
    private String type;

    /**
     * score
     */
    private Long score;

    /**
     * commentaryPersonId
     */
    private Long commentaryPersonId;
    
    /**
     * keywords
     */
    private String keywords;
    
    /**
     * commentDateStart
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentDateStart;
    
    /**
     * commentDateEnd
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentDateEnd;

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
        this.type = type;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getCommentaryPersonId() {
        return commentaryPersonId;
    }

    public void setCommentaryPersonId(Long commentaryPersonId) {
        this.commentaryPersonId = commentaryPersonId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getCommentDateStart() {
        return commentDateStart;
    }

    public void setCommentDateStart(Date commentDateStart) {
        this.commentDateStart = commentDateStart;
    }

    public Date getCommentDateEnd() {
        return commentDateEnd;
    }

    public void setCommentDateEnd(Date commentDateEnd) {
        this.commentDateEnd = commentDateEnd;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QryOrderCommentaryPagerListRequest [pageNum=");
        builder.append(pageNum);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append(", workOrderId=");
        builder.append(workOrderId);
        builder.append(", type=");
        builder.append(type);
        builder.append(", score=");
        builder.append(score);
        builder.append(", commentaryPersonId=");
        builder.append(commentaryPersonId);
        builder.append(", keywords=");
        builder.append(keywords);
        builder.append(", commentDateStart=");
        builder.append(commentDateStart);
        builder.append(", commentDateEnd=");
        builder.append(commentDateEnd);
        builder.append("]");
        return builder.toString();
    }

}
