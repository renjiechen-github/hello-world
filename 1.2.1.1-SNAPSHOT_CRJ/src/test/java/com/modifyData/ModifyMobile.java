package com.modifyData;

import com.ycqwj.ycqwjApi.Initialize;

import static org.junit.Assert.assertArrayEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;
import pccom.web.interfaces.Financial;
import pccom.web.job.financial.BillRemind;
import pccom.web.server.financial.FinancialService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-spring.xml"})
public class ModifyMobile {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DBHelperSpring db;

    @Autowired
    private Financial financial;

    @Autowired
    private FinancialService financialService;
    
    @Autowired
    private BillRemind billRemind;
    
    @Test
    public void init() {
        System.out.println("???????????");
        //知悉数据库操作
//		LoginController loginController = (LoginController) this.applicationContext.getBean("loginController");  
//        MockHttpServletRequest request = new MockHttpServletRequest();  
//        MockHttpServletResponse response = new MockHttpServletResponse();  
//        request.setMethod("POST");  
//        request.addParameter("username", "aa");  
//        request.addParameter("password", "bb");  
//        loginController.login(request, response, "", "", "");

        //根据手机号查询出内容
    }
//	
//	/**
//	 * 修改手机号
//	 */
//	@Test
//	public void modifyMobile(){
//		//根据手机号查询到合约付款信息
//				//读取文件，
//				File f = new File("d://修改手机号.txt");
//				StringBuffer str = new StringBuffer();
//				InputStreamReader read;
//				try {
//					read = new InputStreamReader(new FileInputStream(f));
//					BufferedReader reader = new BufferedReader(read);
//					String line;
//					while ((line = reader.readLine()) != null) {
//						try{
//							String[] mobiles = line.trim().split(",");
//							//检查新手机号是否存在
//							String sql = "SELECT a.id FROM yc_userinfo_tab a WHERE a.mobile = ?";
//							String oldId = db.queryForString(sql,new Object[]{mobiles[0].trim()});
//							String newId = db.queryForString(sql,new Object[]{mobiles[1].trim()});
//							if("".equals(oldId)){
//								logger.error("未查询到老手机号，"+mobiles[0]+"变更为"+mobiles[1]);
//								continue;
//							}
//							if("".equals(newId)){//新手机号不存在
//								//直接修改表信息
//								sql = "UPDATE yc_userinfo_tab a SET a.mobile = ? WHERE a.mobile = ?";
//								if(db.update(sql,new Object[]{mobiles[1].trim(),mobiles[0].trim()}) != 1){
//									logger.error("修改yc_userinfo_tab表失败，"+mobiles[0]+"变更为"+mobiles[1]);
//									continue;
//								}
//								sql = "UPDATE yc_agreement_tab a SET a.user_mobile = ? where a.user_id = ? AND a.type = 2";
//								if(db.update(sql,new Object[]{mobiles[1].trim(),oldId}) != 1){
//									logger.error("修改yc_agreement_tab表失败，"+mobiles[0]+"变更为"+mobiles[1]);
//									continue;
//								}
//								sql = "update yc_wx_user_info a set a.mobile = ? WHERE a.mobile = ?";
//								if(db.update(sql,new Object[]{mobiles[1].trim(),mobiles[0].trim()}) != 1){
//									logger.error("修改yc_wx_user_info失败，"+mobiles[0]+"变更为"+mobiles[1]);
//									continue;
//								}
//								logger.debug("手机号修改成功，"+mobiles[0]+"变更为"+mobiles[1]);
//							}else{//新手机号存在
//								//检查是否存在微信绑定新手机号
//								sql = "select COUNT(1) from yc_wx_user_info a WHERE a.mobile = ?";
//								if(db.queryForInt(sql,new Object[]{mobiles[1].trim()}) == 0){//微信中新手机号不存在
//									//修改原始数据
//									sql = "update yc_wx_user_info a set a.mobile = ? WHERE a.mobile = ?";
//									if(db.update(sql,new Object[]{mobiles[1].trim(),mobiles[0].trim()}) != 1){
//										logger.error("修改微信信息失败，"+mobiles[0]+"变更为"+mobiles[1]);
//										continue;
//									}
//								}
//								//直接更改
//								sql = "UPDATE yc_agreement_tab a SET a.user_id = ?,a.user_mobile = ? WHERE a.user_id = ? AND a.type = 2";
//								if(db.update(sql,new Object[]{newId,mobiles[1].trim(),oldId}) == 1){
//									logger.debug("手机号修改成功，"+mobiles[0]+"变更为"+mobiles[1]);
//								}else{
//									logger.error("手机号修改失败，"+mobiles[0]+"变更为"+mobiles[1]);
//								}
//							}
//						}catch(Exception e){
//							e.printStackTrace();
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				} 
//	}
//	
//	/**
//	 * 大学生免押金处理
//	 */
//	@Test 
//	public void modifyDxsMyj(){
//		//根据手机号查询到合约付款信息
//		//读取文件，
//		File f = new File("d://大学生免押金.txt");
//		StringBuffer str = new StringBuffer();
//		InputStreamReader read;
//		try {
//			read = new InputStreamReader(new FileInputStream(f),
//					"utf-8");
//			BufferedReader reader = new BufferedReader(read);
//			String line;
//			while ((line = reader.readLine()) != null) {
//				try{
//					//进行数据库操作
//					String mobile = line.trim();
//					String sql = "SELECT c.id,c.cost FROM yc_userinfo_tab a,yc_agreement_tab b,financial_receivable_tab c "+
//								 " WHERE a.id = b.user_id "+
//								 "   AND b.id = c.secondary "+
//								 "   AND b.status = 12  "+
//								 "   AND b.type = 2 "+
//								 "   AND c.category = 5 "+
//								 " AND c.status = 0 "+ 
//								 "   and a.mobile = ?";
//					Map<String,Object> map = db.queryForMap(sql,new Object[]{mobile});
//					String fin_id = StringHelper.get(map, "ID");
//					String cost = StringHelper.get(map, "COST");
//					if("".equals(fin_id)){
//						logger.error("出现错误，未找到财务信息:"+mobile);
//					}else{
//						//检查是否已经存在
//						float cost_ = Float.valueOf(cost);
//						sql = "SELECT COUNT(1) FROM yc_coupon_user_detail a WHERE a.financial_id = ?";
//						if(db.queryForInt(sql,new Object[]{fin_id}) == 0){
//							sql = "INSERT INTO yc_coupon_user_detail(financial_id,cost,isuser)VALUES(?,?,1)";
//							if(db.update(sql,new Object[]{fin_id,cost_-0.1f}) == 1){
//								logger.debug("执行成功:"+mobile);
//							}else{
//								logger.error("出现错误，插入失败:"+mobile);
//							}
//						}else{
//							logger.error("已经存在免押金信息:"+mobile);
//						}
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//	}

    /**
     * 水电费拆分 create table tmp_ly_初始数据 ( 备注	varchar(200), hth	varchar(200), 辅助账房间
     * varchar(200), 费用期间	varchar(200), 月	varchar(200), 初始量	varchar(200), 实际量
     * varchar(200), 用量	varchar(200), 实付金额	varchar(200) state int, //0 初始化 1成功
     * 2失败 remark varcha2(200) )
     *
     * ALTER TABLE `tmp_ly_初始数据` ADD COLUMN `id` INT NULL AUTO_INCREMENT AFTER
     * `实付金额`, ADD COLUMN `state` INT DEFAULT 0 NULL AFTER `id`, ADD COLUMN
     * `remark` VARCHAR(200) NULL AFTER `state`, ADD KEY(`id`);
     *
     * ALTER TABLE tmp_ly_初始数据 DROP state; ALTER TABLE tmp_ly_初始数据 DROP remark;
     * ALTER TABLE tmp_ly_初始数据 DROP id;
     *
     *
     * CREATE TABLE ycroom.tmp_ly_fdsfds ( 合同号 varchar(200) DEFAULT NULL, hth
     * varchar(200) DEFAULT NULL, 辅助账房间 varchar(200) DEFAULT NULL, 费用期间
     * varchar(200) DEFAULT NULL, 核对数字 varchar(200) DEFAULT NULL, 已粘贴
     * varchar(200) DEFAULT NULL, yiluxit varchar(200) DEFAULT NULL, type
     * varchar(255) DEFAULT NULL )
     */
    @Test
    public void WaterSplitTest() {
        System.out.println("com.modifyData.ModifyMobile.WaterSplitTest()::--------------------------------");
        DBHelperSpring db = SpringHelper.getApplicationContext().getBean("dbHelper", DBHelperSpring.class);
        //去除了重复数据的7-12月数据
//        String sql = "select a.* from yc_wjs_weg_tab a where a.re_endDate < a.begin_time or (a.re_endDate > a.begin_time and a.re_endDate < a.begin_time )";
        //跨区间的109条数据
//        String sql = "SELECT * from yc_weg_part6_tab";
        //12月与18年1月数据
        String sql = "select * from yc_wegcost_tab where id =112175";
        List<Map<String, Object>> list = db.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String remark = StringHelper.get(map, "remark");
            String hth = StringHelper.get(map, "agerId");
            String upcp = StringHelper.get(map, "startMeter");
            String nowcp = StringHelper.get(map, "endMeter");
            String dushu = StringHelper.get(map, "amount");
            String month = StringHelper.get(map, "month");
            String year = StringHelper.get(map, "year");
            String cost = StringHelper.get(map, "cost");
            String type = StringHelper.get(map, "type");
            String id = StringHelper.get(map, "id");
            String firstDate = StringHelper.get(map, "firstDate");
            String lastDate = StringHelper.get(map, "lastDate");
//            if (hth!=null&&!"".equals(hth)&&year!=null&&!"".equals(year)&&type!=null&&!"".equals(type)&&null!=month&&!"".equals(month)) {
//				
//			}else {
//				return;
//			}
//            sql="select * from yc_temp_weg_part2_tab where year=? and month =? and agerId =? and type=?";
//            int qInt = db.queryForInt(sql, new Object[]{year,month,hth,type});
//            if (qInt>0) {
//				return;
//			}else {
//				sql = "insert into yc_temp_weg_part2_tab (year,month,agerId,type) values(?,?,?,?)";
//				qInt = db.update(sql, new Object[]{
//						year,month,hth,type
//				});
//			}
            //检查对应房源合约是否存在有效的房源信息
            sql = "SELECT COUNT(1) FROM yc_agreement_tab a,yc_houserank_tab c,yc_agreement_tab d "
                    + "WHERE a.house_id = c.house_id "
                    + "  AND c.id = d.house_id "
                    + "  AND d.status NOT IN(2) "
                    + "  AND a.id = ?";
            if (db.queryForInt(sql, new Object[]{hth}) == 0) {
                sql = "update yc_wegcost_part5_tab  a set a.state = 2 ,a.remark = '未查询到有效的合约信息' where a.id = ? ";
                db.update(sql, new Object[]{id});
            } else {

                Map<String, Object> params = new HashMap<String, Object>();

                try {

                    //电费 eMeter 燃气费 cardgas 水费 water
                	params.put("12".equals(type) ? "eMeter" : "13".equals(type) ? "cardgas" : "water", dushu + "$$" + cost + "$$" + (firstDate) + "#" + lastDate + "$$上期抄表：" + upcp + " 本期抄表：" + nowcp);//电费
//                   params.put("12".equals(type) ? "eMeter" : "13".equals(type) ? "cardgas" : "water", dushu + "$$" + cost + "$$" + (subMonth(year + "-" + (month.length() == 1 ? "0" + month : month) + "-01", -2)) + "#" + checkOption("pre", year + "-" + (month.length() == 1 ? "0" + month : month) + "-01") + "$$上期抄表：" + upcp + " 本期抄表：" + nowcp);//电费
//                  params.put("1".equals(type) ? "eMeter" : "2".equals(type) ? "cardgas" : "water", dushu + "$$" + cost + "$$" + (subMonth(year + "-" + (month.length() == 1 ? "0" + month : month) + "-01", -2)) + "#" + checkOption("pre", year + "-" + (month.length() == 1 ? "0" + month : month) + "-01") + "$$上期抄表：" + upcp + " 本期抄表：" + nowcp);//电费
                    params.put("agreement_id", hth);
                    params.put("operator", 96);
                    int result = financial.jiaonashuidianfei(params, null);
                    if (result == 1) {
                        sql = "update yc_wegcost_part5_tab  a set a.state = 1 ,a.remark = ? where a.id = ? ";
                        db.update(sql, new Object[]{result, id});
                    } else {
                        sql = "update yc_wegcost_part5_tab  a set a.state = 2 ,a.remark = ? where a.id = ? ";
                        db.update(sql, new Object[]{result, id});
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
    
//    /**
//     * 生成水电煤账单
//     */
//    @Test
//    public void createSDMBill(){
//        financialService.createSDMBill("2017-10", "");
////        Initialize.setRootUrl("http://121.40.165.132:8380/message/");
////        Constants.YCQWJ_API = "ycqwj";
////        billRemind.execute();
//    }
    
    public static String subMonth(String date, int mun) throws Exception {
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

}
