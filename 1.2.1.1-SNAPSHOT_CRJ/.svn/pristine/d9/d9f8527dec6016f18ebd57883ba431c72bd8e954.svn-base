package com.room1000.core.utils.db.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.core.utils.db.dao.DBMapper;

/**
 * 
 * Description: 
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("DBService")
public class DBServiceImpl {
    
    /**
     * dbMapper
     */
    @Autowired
    private DBMapper dbMapper;

    /**
     * 
     * Description: 获取数据库当前时间
     * 
     * @author jinyanan
     *
     * @return Date
     */
    public Date getDBDateTime() {
        return dbMapper.getDBDateTime();
    }
}
