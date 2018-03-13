package com.modifyData;

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

import pccom.common.util.DBHelperSpring;
import pccom.common.util.DateHelper;
import pccom.common.util.StringHelper;
import pccom.web.interfaces.Financial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-spring.xml"})
public class CwDetailCreate {
//

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DBHelperSpring db;

    @Autowired
    Financial financial;

    /**
     * 将所有的退租订单中 环节才财务审核的订单进行手动插入到财务表中
     */
    @Test
    public void init() {
        //查询所有订单信息
        String sql = "SELECT a.rental_lease_order_id,a.id\n"
                + "  FROM work_order a \n"
                + "WHERE a.sub_order_state IN('N') \n"
                + "  AND a.type = 'A'";  
        List<Map<String, Object>> list = db.queryForList(sql);
        for (Map<String, Object> map : list) {
            System.out.println("com.modifyData.CwDetailCreate.init():"+map);
            String agreementId = StringHelper.get(map, "rental_lease_order_id");
            String operid = "1";
            String order_id = StringHelper.get(map, "id");
            String settlements_id = db.queryForString("SELECT a.`correlation` FROM financial_receivable_tab a WHERE a.`secondary` = ? AND secondary_type = ? LIMIT 0,1", new Object[]{agreementId, 1});
            if ("".equals(settlements_id)) {
                continue;
            }
            //查询出出租房名称
            String settlements_name = db.queryForString("SELECT b.title FROM yc_agreement_tab a,yc_houserank_tab b WHERE a.`house_id` = b.`id` AND a.`type` = 2 AND a.id = ?", new Object[]{agreementId});
            if ("".equals(settlements_name)) {
                continue;
            }
            //删除对应的合约数据
            int exc = deleteFinancial(settlements_id, agreementId);
            if (exc != 1) {
                continue;
            }
            //插入对应的退租单条明细信息
            String ordersql = "select *,fn_getdictitemname ('CERTIFICATELEAVE.TYPE', type) typename from yc_certificateleave_tab where 1=1 " + "and order_id=?";
            List<Map<String, Object>> leaveMap = db.queryForList(ordersql, new Object[]{order_id});//查询所有代缴费信息
            Map<String, String> pramas = new HashMap<String, String>();
            pramas.put("order_id", order_id);//
            pramas.put("operid", operid);//操作人
            pramas.put("settlements_name", settlements_name);//房间名称
            pramas.put("settlements_id", settlements_id);//项目Id

            String updateCertificateLeave = "update yc_certificateleave_tab set createtime=now(), #### where id=?".replace("####", " financial_type=? , financial_id=? ");//"update yc_certificateleave_tab set financial_type=? ,set financial_id=? where id=?";

            for (Map<String, Object> mp : leaveMap) {
                String type = returnType(StringHelper.get(mp, "type"));//代缴费对应财务数据类型
                if (type != null) {
                    int res = 1;
                    if (Float.parseFloat(StringHelper.get(mp, "cost")) > 0) {
                        res = insertSel(pramas, mp, -1, type);//插入收入数据
                        if (res > 0) {
                            res = db.update(updateCertificateLeave, new Object[]{1, res, StringHelper.get(mp, "id")});//更改代缴费财务类型-财务id
                            if (res < 0) {

                            }
                        }
                    } else if (Float.parseFloat(StringHelper.get(mp, "cost")) < 0) {
                        res = insertSel(pramas, mp, 1, type);//插入支出数据
                        if (res > 0) {
                            res = db.update(updateCertificateLeave, new Object[]{0, res, StringHelper.get(mp, "id")});//更改代缴费财务类型-财务id
                            if (res < 0) {

                            }
                        }
                    }
                }
            }
        }

    }

    private int insertSel(Map<String, String> params, Map<String, Object> leaveMap, int state, String type) {
        String cost = StringHelper.get(leaveMap, "cost");//价格
        String operid = StringHelper.get(params, "operid");//操作人
        String order_id = StringHelper.get(params, "order_id");//合约Id
        String settlements_name = StringHelper.get(params, "settlements_name") + "_" + StringHelper.get(leaveMap, "name");//财务项目名称
        String settlements_id = StringHelper.get(params, "settlements_id");//财务项目Id
        String startTime = DateHelper.getToday("YYYY-MM-dd HH:mm:ss");//开始结束时间
        String sql = "";
        float costs = Float.parseFloat(cost);
        if (state == -1) {
            sql = "INSERT INTO financial_receivable_tab(`name`,`correlation`,`secondary_type`,`secondary`,`category`,`create_time`,`cost`,`plat_time`,`update_time`,`target_bank`,`target_user`,`target_type`,`operator`,`remarks`,`status`,`BankSlip`,start_time,end_time)VALUES(?,?,?,?,?,NOW(),?,?,NOW(),?,?,?,?,?,0,?,DATE_FORMAT(?,'%Y-%m-%d'),DATE_FORMAT(?,'%Y-%m-%d'))";
        } else if (state == 1) {
            costs = -costs;
            sql = "INSERT INTO financial_payable_tab(`name`,`correlation`,`secondary_type`,`secondary`,`category`,`create_time`,`cost`,`plat_time`,`update_time`,`target_bank`,`target_user`,`target_type`,`operator`,`remarks`,`status`,`BankSlip`,start_time,end_time)VALUES(?,?,?,?,?,NOW(),?,?,NOW(),?,?,?,?,?,0,?,DATE_FORMAT(?,'%Y-%m-%d'),DATE_FORMAT(?,'%Y-%m-%d'))";
        }
        int exc = db.insert(sql, new Object[]{settlements_name, settlements_id, 2, order_id, type, costs, startTime, "", "", null, operid, "", "", startTime, startTime});
        return exc;
    }

    /**
     * 删除财务收入与支出数据信息，退租使用，当前时间点之后的全部设置为无效，当前时间点之前的 如果未支付就不予设置支付
     *
     * @param obj
     * @param settlements_id
     * @return -1 退租时间没有
     */
    public int deleteFinancial(String settlements_id, String arge_id) {
        //获取退租时间
        String sql = "SELECT a.appointment_date FROM work_order a WHERE a.rental_lease_order_id = ? AND a.type = 'A' ";
        String tz_date = db.queryForString(sql, new Object[]{arge_id});
        if ("".equals(tz_date)) {
            logger.error("退租删除数据错误，未查询到退租时间：" + StringHelper.getSql(sql, new Object[]{arge_id}));
            return -1;
        }
        //小于退租时间并且已支付状态的变更为失效
        sql = "UPDATE financial_receivable_tab a SET a.isdelete = 0 "
                + "  WHERE a.correlation = ? "
                + "    AND ((a.plat_time < ? and a.status > 0) OR a.plat_time >= ?)";
        return db.update(sql, new Object[]{settlements_id, tz_date, tz_date});
    }

    private String returnType(String key) {
        switch (key) {
            case "0":
                key = "11";//水费
                return key;
            case "1":
                key = "12";//电费
                return key;
            case "2":
                key = "2";//物业
                return key;
            case "3":
                key = "10";//其他 
                return key;
            case "4":
                key = "13";//燃气
                return key;
        }
        return null;
    }

}
