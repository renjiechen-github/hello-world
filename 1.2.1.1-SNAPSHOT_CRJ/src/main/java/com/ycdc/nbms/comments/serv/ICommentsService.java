package com.ycdc.nbms.comments.serv;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Description: CommentsService
 *  
 * Created on 2017年1月10日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface ICommentsService {
    
    
    /**
     * 
     * Description: 根据模块名和具体内容id，获取其下所有评论
     * 
     * author jinyanan
     *
     * @param request request
     * @param response response
     */
    void getCommentsList(HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * Description: 逻辑删除评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    void deleteComment(HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * Description: 批量逻辑删除评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    void batchDeleteComments(HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * Description: 回复评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    void replyComment(HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * Description: 评论管理中根据条件查询评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    void getCommentsByCondition(HttpServletRequest request, HttpServletResponse response);
}
