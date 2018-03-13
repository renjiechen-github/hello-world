package com.ycdc.nbms.comments.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * Description: CommentsDao接口
 *  
 * Created on 2017年1月10日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface CommentsDao {
    
    /**
     * 
     * Description: getCommentsList
     * 
     * @author jinyanan
     *
     * @param param 入参
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectCommentsList(Object param);
    
    /**
     * 
     * Description: updateComment
     * 
     * @author jinyanan
     *
     * @param param 入参
     */
    void updateComment(Object param);

    /**
     * 
     * Description: insertComment
     * 
     * @author jinyanan
     *
     * @param param 入参
     */
    void insertComment(Object param);
}
