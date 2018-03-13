package com.web.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.HisTableUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.interfaces.CmSyn;
import pccom.web.job.jd.JdJob;

/**
 * 春眠房源同步接口
 * @author admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring/applicationContext-spring.xml"})  
public class SynJdTest {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdJob jdJob;
	
	@Autowired
	private CmSyn cmSyn;
	
	@Resource(name="dbHelper")
	public DBHelperSpring db;// 数据操作
	
	@Resource(name="hisTableUtil")
	public HisTableUtil hisTableUtil;
	
	@Test
	public void synTest(){
		//con
		Constants.is_test = false; 
//		jdJob.execute();
		jdJob.executeSyn(); 
//		cmSyn.initSyn();
	}
	
//	
//	public void insertHisTableTest(){
//		String sql = " update tmp_ly_datadata a set a.house_id =2  WHERE a.id  = 5 ";
//		
//		try {
//			hisTableUtil.insertHisTable(sql);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
}
