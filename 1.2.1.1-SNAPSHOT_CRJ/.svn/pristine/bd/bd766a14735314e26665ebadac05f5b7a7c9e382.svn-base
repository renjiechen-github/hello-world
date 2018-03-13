package com.web.interfaces;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;
import pccom.web.interfaces.Financial;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring/applicationContext-spring.xml"})  
public class WaterSplitTest {

	@Resource(name="financial")
	private Financial financial;
	
	/**
	 * 水电费拆分
	 * create table tmp_ly_初始数据 
  (
  备注	varchar(200),
  hth	varchar(200),	
  辅助账房间		varchar(200),
  费用期间		varchar(200),
  月		varchar(200),
  初始量		varchar(200),
  实际量		varchar(200),
  用量		varchar(200),
  实付金额	varchar(200)
  )
  
  
  ALTER TABLE `tmp_ly_初始数据`   
  ADD COLUMN `id` INT NULL AUTO_INCREMENT AFTER `实付金额`,
  ADD COLUMN `state` INT DEFAULT 0  NULL AFTER `id`,
  ADD COLUMN `remark` VARCHAR(200) NULL AFTER `state`,
  ADD KEY(`id`);

ALTER TABLE tmp_ly_初始数据 DROP state;
ALTER TABLE tmp_ly_初始数据 DROP remark;
ALTER TABLE tmp_ly_初始数据 DROP id;
	 */
	@Test
	public void WaterSplit(){
		DBHelperSpring db = SpringHelper.getApplicationContext().getBean("dbHelper",DBHelperSpring.class);
		String sql = "select * from tmp_ly_初始数据 a where a.state = 0";
		List<Map<String,Object>> list = db.queryForList(sql);
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String remark = StringHelper.get(map, "备注");
			String hth = StringHelper.get(map, "hth");
			String year = StringHelper.get(map, "费用期间");
			String month = StringHelper.get(map, "月");
			String dushu = StringHelper.get(map, "用量");
			String cost = StringHelper.get(map, "实付金额");
			String id = StringHelper.get(map, "id");
			
			//检查对应房源合约是否存在有效的房源信息
			sql = "SELECT COUNT(1) FROM yc_agreement_tab a,yc_houserank_tab c,yc_agreement_tab d "+
				  "WHERE a.id = c.house_id "+
				  "  AND c.id = d.house_id "+
				  "  AND d.status NOT IN(3,7) "+
				  "  AND a.id = ?";
			if(db.queryForInt(sql,new Object[]{hth}) == 0){
				sql = "update tmp_ly_初始数据  a set a.state = 2 ,a.remark = '未查询到有效的合约信息' where a.id = ? ";
				db.update(sql,new Object[]{id});
			}else{
				
				Map<String,Object> params = new HashMap<String, Object>();
				
				try {
					params.put("eMeter", dushu+"$$"+cost+"$$"+(WaterSplitTest.subMonth(year+"-"+(month.length()==1?"0"+month:month)+"-01", -2))+"#"+WaterSplitTest.checkOption("pre",year+"-"+(month.length()==1?"0"+month:month)+"-01"));//电费
					params.put("agreement_id",hth);
					params.put("operator",96);
					financial.jiaonashuidianfei(params, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public static String subMonth(String date,int mun) throws Exception {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date dt = sdf.parse(date);  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(dt);  
  
        rightNow.add(Calendar.MONTH, mun);  
        Date dt1 = rightNow.getTime();  
        String reStr = sdf.format(dt1);  
  
        return reStr;  
    }
	
	public static String checkOption(String option, String _date) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cl = Calendar.getInstance();  
        Date date = null;  
  
        try {  
            date = (Date) sdf.parse(_date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        cl.setTime(date);  
        if ("pre".equals(option)) {  
            // 时间减一天  
            cl.add(Calendar.DAY_OF_MONTH, -1);  
        } else if ("next".equals(option)) {  
            // 时间加一天  
            cl.add(Calendar.DAY_OF_YEAR, 1);  
        } else {  
            // do nothing  
        }  
        date = cl.getTime();  
        return sdf.format(date);  
    }
	
	public static void main(String[] args) {
		try {
			System.out.println(WaterSplitTest.subMonth("2017-01-01", -2));
			System.out.println(WaterSplitTest.checkOption("pre", "2017-01-01"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
