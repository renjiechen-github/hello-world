package com.ycdc.nbms.comments.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.nbms.comments.serv.ICommentsService;

import pccom.web.action.BaseController;

/**
 * 
 * Description: 评论功能Controller
 *  
 * Created on 2017年1月10日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Controller
@RequestMapping("/comments")
public class CommentsAction extends BaseController {
    
    /** commentsService */
    @Resource
    private ICommentsService commentsService;
    
    /**
     * 
     * Description: 根据模块名和具体内容id，获取其下所有评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/getCommentsList.do")
    public void getCommentsList(HttpServletRequest request, HttpServletResponse response) {
        commentsService.getCommentsList(request, response);
    }
    
    /**
     * 
     * Description: 逻辑删除评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/deleteComment.do")
    public void deleteComment(HttpServletRequest request, HttpServletResponse response) {
        commentsService.deleteComment(request, response);
    }
    
    /**
     * 
     * Description: 批量逻辑删除评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/batchDeleteComments.do")
    public void batchDeleteComments(HttpServletRequest request, HttpServletResponse response) {
        commentsService.batchDeleteComments(request, response);
    }
    
    /**
     * 
     * Description: 回复评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/replyComment.do")
    public void replyComment(HttpServletRequest request, HttpServletResponse response) {
        commentsService.replyComment(request, response);
    }
    
    /**
     * 
     * Description: 评论管理中根据条件查询评论
     * 
     * @author jinyanan
     *
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/getCommentsByCondition.do")
    public void getCommentsByCondition(HttpServletRequest request, HttpServletResponse response) {
        commentsService.getCommentsByCondition(request, response);
    }
    
}
