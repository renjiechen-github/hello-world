<%@page import="pccom.common.util.SpringHelper"%>
<%@page import="pccom.common.util.RequestHelper"%>
<%@page import="pccom.common.util.DBHelperSpring"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:x="urn:schemas-microsoft-com:office:excel">
  <head>
    <title>My JSP 'finance.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <%
	    response.setContentType("application/vnd.ms-excel");  
	    response.setHeader("Content-Disposition", "inline; filename="  
	            + "excel.xls");  
    	DBHelperSpring db = (DBHelperSpring)SpringHelper.getBean("dbHelper");
    	String date = new RequestHelper().getAjaxValue(request,"date");
    	/* String sql = "SELECT a.`start_time`,(SELECT b.`transaction_id` FROM yc_wxpay_log_tab b WHERE a.`log_id` = b.`id` AND b.`result_code` = 'SUCCESS')`transaction_id`,a.`cost`,f.`username`,f.`mobile`,c.`remarks`"+
    			 " FROM yc_pay_tab a,financial_receivable_tab c,yc_agreement_tab e,yc_userinfo_tab f "+
    			 " WHERE a.`relevance_id` = c.`id` "+
    			 "   AND c.`secondary` = e.`id` "+
    			 "   AND e.`user_id` = f.`id` "+
    			 "   AND a.`relevance_type` = 2 "+
    			 "   AND a.state = 1 "+
    			 "   AND a.pay_type = 1 "+
    			 "   AND DATE_FORMAT(a.`start_time`,'%Y-%m-%d')>=?"; */
    	String sql =" SELECT a.`start_time`,(SELECT b.`transaction_id` FROM yc_wxpay_log_tab b WHERE a.`log_id` = b.`id` AND b.`result_code` = 'SUCCESS')`transaction_id`,a.`cost` "+
    		      	" ,g.username,g.mobile,(SELECT a1.name FROM financial_category_tab a1 WHERE a1.id = c.category) type, "+
    		     	"  c.remarks "+
		    		" FROM yc_pay_tab a,financial_receivable_tab c,financial_settlements_tab e,yc_agreement_tab f,yc_userinfo_tab g "+
		    		" WHERE a.`relevance_id` = c.`id` "+
		    		"   AND e.correlation_id = c.correlation "+
		    		"   AND f.id = e.ager_id " +
		    		"   AND g.id = f.user_id " +
		    		"   AND a.`relevance_type` = 2 " +
		    		"   AND a.state = 1 "+
		    		"   AND a.pay_type = 1 " +
		    		"   AND DATE_FORMAT(a.`start_time`,'%Y-%m-%d') >= ? ";
		List<Map<String,Object>> list = db.queryForList(sql,new Object[]{date});
    %>
    <table align="left" border="2">  
        <thead>  
            <tr bgcolor="lightgreen">  
                <th>开始时间</th>  
                <th>微信支付编号</th>  
                <th>价格</th>  
                <th>姓名</th>
                <th>手机号</th>
                <th>备注</th>  
            </tr>  
        </thead>  
        <tbody>  
            <%  
                for (int i = 0; i < list.size(); i++) {
                	Map<String,Object> map = list.get(i);
            %>
            <tr bgcolor="lightblue">  
                <td align="center"><%=map.get("start_time")%></td>  
                <td align="center"><%=String.valueOf(map.get("transaction_id"))+" "%></td>  
                <td align="center"><%=map.get("cost")%></td>  
                <td  align="center"><%=map.get("username")%></td>
                <td  align="center"><%=map.get("mobile")%></td>
                <td  align="center"><%=map.get("remarks")%></td>    
            </tr>  
            <%  
                }  
            %>  
        </tbody>  
    </table>  
    
  </body>
</html>
