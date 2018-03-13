package com.ycdc.nbms.comments.serv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.comments.dao.CommentsDao;
import com.ycdc.util.page.PageResultDealInterface;

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
@Service("commentsServ")
public class CommentsServiceImpl extends BaseService implements ICommentsService {
    
    /** commentsDao */
    @Resource
    private CommentsDao commentsDao;
    
    @Transactional
    @Override
    public void getCommentsList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("moduleId", req.getAjaxValue(request, "moduleId"));
        paramMap.put("moduleTextId", req.getAjaxValue(request, "moduleTextId"));
        getPageMapList(request, response, commentsDao, "selectCommentsList", paramMap, new PageResultDealInterface() {
            
            @SuppressWarnings("unchecked")
            @Override
            public List<Map<String, Object>> deal(List<Map<String, Object>> list) {
                List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> item : list) {
                    if (item.get("parent_id") == null) {
                        resultList.add(item);
                    }
                    else {
                        Map<String, Object> parentItem = this.getItemById(resultList, item.get("parent_id"));
                        if (parentItem.get("children") == null) {
                            parentItem.put("children", new ArrayList<Map<String, Object>>());
                        }
                        ((List<Map<String, Object>>) parentItem.get("children")).add(item);
                    }
                }
                return resultList;
            }
            
            /**
             * 
             * Description: 根据id获取resultList中的一项
             * 
             * @author jinyanan
             *
             * @param resultList 遍历集合
             * @param id id
             * @return Map<String, Object>
             */
            private Map<String, Object> getItemById(List<Map<String, Object>> resultList, Object id) {
                for (Map<String, Object> item : resultList) {
                    if (id.equals(item.get("id"))) {
                        return item;
                    }
                }
                return null;
            }

        });
    }

    @Override
    public void deleteComment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", req.getAjaxValue(request, "id"));
        paramMap.put("state", "D");
        paramMap.put("state_date", new Date());
        getPageMapList(request, response, commentsDao, "updateComment", paramMap);
    }

    @Override
    public void batchDeleteComments(HttpServletRequest request, HttpServletResponse response) {
        String ids = req.getAjaxValue(request, "ids");
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("id", id);
            paramMap.put("state", "D");
            paramMap.put("state_date", new Date());
            commentsDao.updateComment(paramMap);
        }
    }

    @Override
    public void replyComment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", req.getAjaxValue(request, "id"));
        paramMap.put("parent_id", req.getAjaxValue(request, "parent_id"));
        paramMap.put("module_id", req.getAjaxValue(request, "module_id"));
        paramMap.put("module_text_id", req.getAjaxValue(request, "module_text_id"));
        paramMap.put("comments", req.getAjaxValue(request, "comments"));
        paramMap.put("person_id", req.getAjaxValue(request, "person_id"));
        paramMap.put("person_nickname", req.getAjaxValue(request, "person_nickname"));
        paramMap.put("comments_date", new Date());
        paramMap.put("state", "A");
        paramMap.put("state_date", new Date());
        commentsDao.insertComment(paramMap);
    }

    @Override
    public void getCommentsByCondition(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("module_id", req.getAjaxValue(request, "module_id"));
        paramMap.put("module_text_id", req.getAjaxValue(request, "module_text_id"));
        paramMap.put("person_id", req.getAjaxValue(request, "person_id"));
        paramMap.put("person_nickname", req.getAjaxValue(request, "person_nickname"));
        paramMap.put("start_date", req.getAjaxValue(request, "start_date"));
        paramMap.put("end_date", req.getAjaxValue(request, "end_date"));
        paramMap.put("state", req.getAjaxValue(request, "state"));
        getPageMapList(request, response, commentsDao, "selectCommentsList", paramMap);
    }
}
