<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.ycdc.appserver.bus.service.syscfg.Apis" %>
<script typet="text/javascript" src="http://manager.room1000.com:8187/html/adminX/js/jquery-1.10.2.js"></script>
<script src="http://manager.room1000.com:8187/html/adminX/js/jquery.form.js"></script>
  <%
  	Logger logger = LoggerFactory.getLogger(this.getClass()); 
  	String key = String.valueOf(request.getParameter("key"));
  	pageContext.setAttribute("key", key); 
  	System.out.print(key);
  	String content = String.valueOf(request.getParameter("content"));
  	pageContext.setAttribute("content", content); 
  	String signed = String.valueOf(request.getParameter("signed"));
  	pageContext.setAttribute("signed", signed);
  	logger.debug("serverUrl:"+Apis.serverUrl);
  	pageContext.setAttribute("serverUrl", Apis.serverUrl);
  %>
  <body>
  <form id="form1" method="post">
  	<input type="hidden"  id="key" value="${key}" name="key">
  	<input type="hidden" id="content" value="${content}" name="content">
  	<input type="hidden" id="signed" value="${signed}" name="signed">
  </form>
  </body>
  <script type="text/javascript">
  	  $(save());
	  function save()
	  {
		var ajax_option =
		{
			url:"${serverUrl}/rankHouse/caCallBackUrl.do",//默认是form action
			success:resFunc,
			type:'post',
			dataType:'json'
		}; 
		$("#form1").ajaxForm(ajax_option).submit();
	  }
	  
	  function resFunc(data)
	  {
		 if(data.state == 0)
		 {
			window.location.href="http://manager.room1000.com:8187";
	  	    window.close();
		 }
		 else
		 {
			 window.location.href="http://www.room1000.com";
		  	 window.close();
		 } 
	  }
  </script>
 
