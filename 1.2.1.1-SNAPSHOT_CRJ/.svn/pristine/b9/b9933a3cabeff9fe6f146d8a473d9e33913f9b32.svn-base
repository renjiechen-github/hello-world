package pccom.web.flow.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import pccom.common.util.AmountUtil;
import pccom.common.util.Batch;
import pccom.common.util.StringHelper;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Financial.Result;

@Service("certificateLeaveService")
public class CertificateLeaveService extends FlowBase {
	
	@Autowired
	private Financial financial;

    /**
     * 获取列表
     *
     * @author 刘飞
     * @param request
     * @param response
     * @return
     */
    @Transactional
    public void getList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String orderId = req.getAjaxValue(request, "orderId");
        String flag = String.valueOf(req.getAjaxValue(request, "flag"));
        if (StringUtils.isBlank(flag) || flag.equals("null"))
        {
        	try
					{
        		disCost(orderId);
					} catch (Exception e)
					{
						logger.error("核算失败" + e);
					}
        	
        }
        String step_type = req.getAjaxValue(request, "step_type");
        String taskId = req.getAjaxValue(request, "taskId");

        String sql = getSql("certificateLeaveService.main");
        List<String> params = new ArrayList<String>();
        if (!"".equals(orderId)) {
            sql += getSql("certificateLeaveService.orderId");
            params.add(orderId);
        }
        if (!"".equals(taskId)) {
            sql += getSql("certificateLeaveService.taskId");
            params.add(taskId);
        }
        if (!"".equals(step_type)) {
            sql += getSql("certificateLeaveService.step_type");
            params.add(step_type);
        }
        getPageList(request, response, sql, params.toArray());
    }

    /**
     * 進行金額處理
     *
     * @param orderId
     */
    public void disCost(String orderId) throws ParseException {
        logger.debug("退租中进行金额收退处理---start----------------------------");
        //查询出订单的退租时间
        String sql = "SELECT a.appointment_date,a.sub_order_state,(case when a.appointment_date < DATE_SUB(b.end_time,INTERVAL 15 day) then 1 else 0 end) is_wy FROM work_order a,yc_agreement_tab b \n"
                + "where a.rental_lease_order_id = b.id\n"
                + "  and a.id = ?";
        Map<String, Object> orderMap = db.queryForMap(sql, new Object[]{orderId});
        String appointment_date = StringHelper.get(orderMap, "appointment_date");//退租时间
        String sub_order_state = StringHelper.get(orderMap, "sub_order_state");
        String is_wy = StringHelper.get(orderMap, "is_wy");//是否违约退租
        if (!"M".equals(sub_order_state)) {
            return;
        }
        logger.debug("是否违约:"+is_wy);
        appointment_date = appointment_date.substring(0, 10);
        
        //查詢出當前訂單所有的收支明細
        sql = "SELECT b.start_time,b.end_time,b.cost,ifnull((select sum(e.yet_cost) from financial_yet_tab e where e.fin_id = b.id),0) yet_cost,b.category,b.status state ,ifnull((select sum(e.discount_cost) from financial_yet_tab e where e.fin_id = b.id),0) yh "
                + "FROM work_order a,financial_receivable_tab b,financial_settlements_tab c "
                + "WHERE a.rental_lease_order_id = c.ager_id "
                + "  AND b.correlation = c.correlation_id "
                + "  and a.id = ? ";
        logger.debug("查詢出當前訂單所有的收支明細:"+StringHelper.getSql(sql, new Object[]{orderId}));
        List<Map<String, Object>> list = db.queryForList(sql, new Object[]{orderId});
        Double tk_cost = 0D;
        Double financeIncomeTable_tk_cost = 0D;//其他退款金额
        for (int i = 0; i < list.size(); i++) {
            logger.debug("list:map:"+list.get(i));
//            System.out.println("pccom.web.flow.base.CertificateLeaveService.disCost()" + list.get(i));
            String start_time = StringHelper.get(list.get(i), "start_time").substring(0, 10);
            String end_time = StringHelper.get(list.get(i), "end_time").substring(0, 10);
            String cost = StringHelper.get(list.get(i), "cost");//明细需要支付的金额
            String yet_cost = StringHelper.get(list.get(i), "yet_cost");//用户支付的钱交费不包含优惠金额 只退还用户支付的钱
            String yh = StringHelper.get(list.get(i), "yh");//优惠金额
            String state = StringHelper.get(list.get(i), "state");
            String ze = AmountUtil.subtract(Double.valueOf(yet_cost), Double.valueOf("0"), 2).toString();
            String category = StringHelper.get(list.get(i), "category");

            if("0".equals(state)){
                if(Double.valueOf(yet_cost) > 0 || Double.valueOf(yh) > 0){
                    state = "1";
                }
            }
            
            if ("5".equals(category)) {//押金  押金退还 用户交款明细金额
                logger.debug("ze:" + ze);
                if (!"1".equals(is_wy)) {
                    //检查
                    tk_cost = AmountUtil.add(Double.valueOf(ze), tk_cost, 2);
                }
                continue;
            }

            if ("14".equals(category)) {//金融月付暂且不理
                continue;
            }

            //核实时间信息
            logger.debug((compare_date(appointment_date, start_time) >= 0 && !"0".equals(state))+"进行核减金额退减："+state+"---"+list.get(i)+"---"+compare_date(appointment_date, start_time));
            if (compare_date(appointment_date, start_time) >= 0 && !"0".equals(state)) {
                logger.debug("时间点："+compare_date(end_time, appointment_date)+"-end_time:"+end_time+"--appointment_date:"+appointment_date);
                if (compare_date(end_time, appointment_date) >= 0 ) {
                    long days = jgDay(end_time, appointment_date);
                    Double day_cost = AmountUtil.divide(Double.valueOf(ze), Double.valueOf(jgDay(end_time, start_time).toString()), 2);
                    Double cosths = AmountUtil.multiply(day_cost, Double.valueOf(String.valueOf(days)), 2);
                    if(cosths > Double.valueOf(yet_cost)){
                        cosths = Double.valueOf(yet_cost);
                    }
                    logger.debug("days:" + days + "---" + day_cost + "---" + cosths + "---" + end_time + "---" + start_time);
                    financeIncomeTable_tk_cost = AmountUtil.add(financeIncomeTable_tk_cost, cosths, 2);
                }
            } else if (!"0".equals(state)) {
                logger.debug("-----------:"+financeIncomeTable_tk_cost);
                financeIncomeTable_tk_cost = AmountUtil.add(financeIncomeTable_tk_cost, Double.valueOf(ze), 2);
            } else if ("0".equals(state)) {
                if (compare_date(appointment_date, start_time) == 1) {
                    if (compare_date(end_time, appointment_date) == 1 || compare_date(end_time, appointment_date) == 0) {
                        long days = jgDay(end_time, appointment_date);
                        Double day_cost = AmountUtil.divide(Double.valueOf(ze), Double.valueOf(jgDay(end_time, start_time).toString()), 2);
                        Double cosths = AmountUtil.multiply(day_cost, Double.valueOf(String.valueOf(days)), 2);
                        logger.debug("days:" + days + "---" + day_cost + "---" + cosths + "---" + end_time + "---" + start_time);
                        financeIncomeTable_tk_cost = AmountUtil.add(financeIncomeTable_tk_cost, cosths, 2);
                    }
                }
            }
        }
        logger.debug("tk_cost:" + tk_cost);
        if (tk_cost != 0D) {
            sql = "SELECT COUNT(1) FROM yc_certificateleave_tab a WHERE a.order_id = ? AND a.name = '退还押金'";
            if (db.queryForInt(sql, new Object[]{orderId}) == 0) {
                sql = getSql("certificateLeaveService.info.insert");
                db.insert(sql, new Object[]{"退还押金", "3", AmountUtil.subtract(Double.valueOf("0"), Double.valueOf(tk_cost), 2), "已交押金需要退还", "0", "0", "0", orderId, "-1", "-1", null});
            }
        }
        logger.debug("financeIncomeTable_tk_cost:" + financeIncomeTable_tk_cost);
        if (financeIncomeTable_tk_cost != 0D) {
            sql = "SELECT COUNT(1) FROM yc_certificateleave_tab a WHERE a.order_id = ? AND a.name = '退还提前交款费用'";
            if (db.queryForInt(sql, new Object[]{orderId}) == 0) {
                sql = getSql("certificateLeaveService.info.insert");
                db.insert(sql, new Object[]{"退还提前交款费用", "3", AmountUtil.subtract(Double.valueOf("0"), financeIncomeTable_tk_cost, 2), "退还提前交款的明细费用，包含租金与其他项", "0", "0", "0", orderId, "-1", "-1", null});
            }
        }

        //查詢出當前訂單所有的收支明細
        sql = "SELECT b.start_time,b.end_time,b.cost,b.category,IFNULL((SELECT SUM(e.cost) FROM yc_coupon_user_detail e WHERE e.financial_id = b.id and e.type = 2 ),0) yh "
                + "FROM work_order a,financial_payable_tab b,financial_settlements_tab c\n"
                + "WHERE a.rental_lease_order_id = c.ager_id\n"
                + "  AND b.correlation = c.correlation_id\n"
                + "  and a.id = ?\n"
                + "  AND b.status <> 0";
        list = db.queryForList(sql, new Object[]{orderId});
        String payable_tk_cost = "0";//其他退款金额
        for (int i = 0; i < list.size(); i++) {
            String start_time = StringHelper.get(list.get(i), "start_time").substring(0, 10);
            String end_time = StringHelper.get(list.get(i), "end_time").substring(0, 10);
            String cost = StringHelper.get(list.get(i), "cost");
            String yh = StringHelper.get(list.get(i), "yh");
            String ze = AmountUtil.subtract(Double.valueOf(cost), Double.valueOf(yh), 2).toString();
            String category = StringHelper.get(list.get(i), "category");

            //核实时间信息
            if ("14".equals(category)) {//金融月付暂且不理
                continue;
            }
            if (compare_date(appointment_date, start_time) == 1) {
                if (compare_date(end_time, appointment_date) == 1 || compare_date(end_time, appointment_date) == 0) {
                    long days = jgDay(end_time, appointment_date);
                    logger.debug("days:" + days);
                    String day_cost = AmountUtil.divide(Double.valueOf(ze), Double.valueOf(jgDay(end_time, start_time).toString()), 2).toString();
                    logger.debug("days:" + days);
                    String cosths = AmountUtil.multiply(Double.valueOf(day_cost), Double.valueOf(String.valueOf(days)), 2).toString();
                    payable_tk_cost = AmountUtil.add(Double.valueOf(payable_tk_cost), Double.valueOf(cosths), 2).toString();
                }
            } else {
                payable_tk_cost = AmountUtil.add(Double.valueOf(payable_tk_cost), Double.valueOf(ze), 2).toString();
            }
        }
//        System.out.println("pccom.web.flow.base.CertificateLeaveService.disCost()" + payable_tk_cost);
        if (!"0.0".equals(payable_tk_cost) && !"0".equals(payable_tk_cost)) {
            sql = "SELECT COUNT(1) FROM yc_certificateleave_tab a WHERE a.order_id = ? AND a.name = '超前付款给用户需收回'";
            if (db.queryForInt(sql, new Object[]{orderId}) == 0) {
                Double cos = AmountUtil.subtract(Double.valueOf("0"), Double.valueOf(payable_tk_cost), 2);
//                System.out.println("pccom.web.flow.base.CertificateLeaveService.disCost()" + cos);
                if (cos < 0) {
                    sql = getSql("certificateLeaveService.info.insert");
                    db.insert(sql, new Object[]{"超前付款给用户需收回", "3", cos, "超前付款给用户需收回对应金额", "0", "0", "0", orderId, "-1", "-1", null});
                }
            }
        }
        logger.debug("退租中进行金额收退处理---end----------------------------");
    }

    public Long jgDay(String day1, String day2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(day1);
        Date date2 = format.parse(day2);
        return (date1.getTime() - date2.getTime()) / (24 * 3600 * 1000);
    }

    /**
     * 时间比较
     * @param DATE1
     * @param DATE2
     * @return 1 date1>date2  -1 date2<date1 0 相等 
     */
    public int compare_date(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取录入数据
     *
     * @author 刘飞
     * @param request
     * @param response
     * @return
     */
    public Object getInfo(HttpServletRequest request, HttpServletResponse response) {
        String orderId = req.getAjaxValue(request, "orderId");
        String step_type = req.getAjaxValue(request, "step_type");
        String sql = getSql("certificateLeaveService.main");
        List<String> params = new ArrayList<String>();
        if (!"".equals(orderId)) {
            sql += getSql("certificateLeaveService.orderId");
            params.add(orderId);
        }
        if (!"".equals(step_type)) {
            sql += getSql("certificateLeaveService.step_type");
            params.add(step_type);
        }
        Map<String, Object> returnMap = getReturnMap("1");
        returnMap.put("list", JSONArray.fromObject(db.queryForList(sql, params.toArray())));
        return returnMap;
    }

    /**
     * 订单修改
     *
     * @author 刘飞
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    public Object update(final HttpServletRequest request, final HttpServletResponse response) {
        final String oper = this.getUser(request).getId();// 获取操作人
        @SuppressWarnings("unchecked")
        Object i = db.doInTransaction(new Batch<Object>() {
            @Override
            public Integer execute() throws Exception {
                String id = req.getAjaxValue(request, "id");
                String degree = req.getAjaxValue(request, "degree");// 租赁ID
                String starttime = req.getAjaxValue(request, "starttime");// 开始时间
                String endtime = req.getAjaxValue(request, "endtime");// 结束时间
                String name = req.getAjaxValue(request, "name");// 订单说明
                String type = req.getAjaxValue(request, "type");// 订单金额
                String cost = req.getAjaxValue(request, "cost");// 子类型
                String desc = req.getAjaxValue(request, "desc");// 图片路径
                String sql = getSql("certificateLeaveService.info.update");
                this.update(sql.replace("####", "name=?,type=?,cost=?,costdesc=?,starttime=?,endtime=?,degree=?"), new Object[]{name, type, cost, desc, starttime, endtime, degree, id});
                return 1;
            }
        });
        return getReturnMap(i);
    }

    /**
     * 删除
     * @throws Exception 
     *
     */
  	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
  		{ RuntimeException.class, Exception.class })
    public Object delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = req.getAjaxValue(request, "id");
        String orderId = req.getAjaxValue(request, "orderId");
        String cancelType = req.getAjaxValue(request, "cancelType");
        String rentalLeaseOrderId = req.getAjaxValue(request, "rentalLeaseOrderId");
        
        String sqlhouse = getSql("certificateLeaveService.info.delete");
        int info = db.update(sqlhouse, new Object[]{id});
        logger.info("info == " + info);
        if (info < 1) 
        {
        	db.rollback();
        	return getReturnMap(-2);
        }
        
        int type_ = 0;
        if (cancelType.equals("B") || cancelType.equals("C")) {
        	type_ = 0;
        } else {
        	type_ = 1;
        }
        if (StringUtils.isNotBlank(rentalLeaseOrderId) && !rentalLeaseOrderId.equals("null"))
        {
          try
  				{
            Result a = financial.getSettleBill(rentalLeaseOrderId, 1, type_, orderId);
            logger.info("a.getState() == " + a.getState());
          	if (a.getState() != 1) 
          	{
          		return getReturnMap(-2, a.getMsg());
          	}
  				} catch (Exception e)
  				{
  					db.rollback();
  					return getReturnMap(-2);
  				}
        }
        return getReturnMap(1);
    }

    /**
     * 新增
     *
     * @author 刘飞
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
  	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
  		{ RuntimeException.class, Exception.class })
    public Object createOrder(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
      String orderId = req.getAjaxValue(request, "orderId");// 订单名称
      String financial_type = req.getAjaxValue(request, "financial_type");// 财务类型（1：应收，0：应付）
      if (StringUtils.isBlank(financial_type) || financial_type.equals("null"))
      {
      	financial_type = null;
      }
      String taskId = req.getAjaxValue(request, "taskId");// 订单名称
      String degree = req.getAjaxValue(request, "degree");// 租赁ID
      String rentalLeaseOrderId = req.getAjaxValue(request, "rentalLeaseOrderId");// 合约id
      String starttime = req.getAjaxValue(request, "starttime");// 开始时间
      String endtime = req.getAjaxValue(request, "endtime");// 结束时间
      String name = req.getAjaxValue(request, "name");// 订单说明
      String type = req.getAjaxValue(request, "type");// 订单金额
      String cost = req.getAjaxValue(request, "cost");// 子类型
      String desc = req.getAjaxValue(request, "desc");// 图片路径
      String step_type = req.getAjaxValue(request, "step_type");// 图片路径
      String cancelType = req.getAjaxValue(request, "cancelType");// 图片路径
      
      String sql = getSql("certificateLeaveService.info.insert");
      int keyId = db.insert(sql, new Object[]{name, type, cost, desc, starttime, endtime, degree, orderId, step_type, taskId, financial_type});
      if (keyId < 1) 
      {
      	db.rollback();
      	return getReturnMap(-2);
      }
      
      int type_ = 0;
      if (cancelType.equals("B") || cancelType.equals("C")) {
      	type_ = 0;
      } else {
      	type_ = 1;
      }
      // 刷新财务
      if (StringUtils.isNotBlank(rentalLeaseOrderId) && !rentalLeaseOrderId.equals("null"))
      {
        try
  			{
        	Result a = financial.getSettleBill(rentalLeaseOrderId, 1, type_, orderId);
        	if (a.getState() != 1) 
        	{
        		return getReturnMap(-2, a.getMsg());
        	}
  			} catch (ParseException e)
  			{
  				db.rollback();
  				return getReturnMap(-2);
  			}
      }
      return getReturnMap(1);
    }

}
