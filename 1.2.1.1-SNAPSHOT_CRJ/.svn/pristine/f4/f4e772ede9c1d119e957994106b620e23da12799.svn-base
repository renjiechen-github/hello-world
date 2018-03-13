package com.web.interfaces;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import pccom.web.interfaces.CmSyn;

/**
 * 春眠房源同步接口
 * @author admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring/applicationContext-spring.xml"})  
public class CmSynTest {

	@Autowired
	private CmSyn cmSyn;
	
	@Test
	public void initSynTest(){
		System.out.println("开始同步数据信息");
		try{
			cmSyn.initSyn();
		}catch(Exception e){
			e.printStackTrace();
		} 
		
		System.out.println("结束同步数据信息");
	}
	
	@Autowired  
    private WebApplicationContext wac;  
    private MockMvc mockMvc;
    
	public void loginTest(){
		
	}
	
}
