<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我发起的任务</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
	<section class="panel">
        <div class="panel-body">
        	 <section class="panel panel-info panel-seach">
		        <div class="panel-body">
		        	<form class="form-inline" role="form">
			            <div class="form-group flowtypediv " >
	                        <label class="" for="exampleInputEmail2">任务类型:</label>
	                        <select class="form-control" t="type" id="flowType0" name="flowType0" >
                                <option value="" >请选择...</option>
                                <c:forEach var="map" items="${typeList}" >
                                	<option value="${map.type_id}" >${map.type_name}</option>	
                                </c:forEach>
                            </select>
	                    </div>
			            <div class="form-group">
	                        <label class="" for="exampleInputEmail2">任务状态:</label>
	                        <select class="form-control" id="secondary_type" name="secondary_type" >
                                <option value="" >请选择...</option>
                                <c:forEach var="map" items="${stateList}" >
                                	<option value="${map.item_id}" >${map.item_name}</option>	
                                </c:forEach>
                            </select>
	                    </div>
	                    <div class="form-group">
	                        <label class="" for="exampleInputEmail2">任务名称:</label>
	                        <input type="email" class="form-control" id="taskName" name="taskName" placeholder="模糊查询任务名称">
	                    </div>
	                    <div class="form-group">
	                        <label class="" for="exampleInputEmail2">任务编号:</label>
	                        <input type="email" class="form-control" id="taskCode" name="taskCode" placeholder="模糊查询任务编号">
	                    </div>
	                    <div class="form-group">
	                        <label for="exampleInputEmail2">发起任务时间:</label>
	                        <input type="text" style="width: 275px" name="sendTime" readonly="readonly" id="sendTime" class="form-control" placeholder="2013-01-02 01:00 至 2013-01-02 01:10"  class="span4"/>
	                    </div>
	                    <div class="form-group tools">
			            	<a href="javascript:;" class="fa fa-search yc_seach" table="flowYetTable" >查询</a>
			            </div>
	                </form>
		        </div>
		    </section>
	        <div class="adv-table">
	        	<table id="flowYetTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
			        <thead>
			            <tr>	
			            	<th></th>
			             	<th></th>
			                <th>任务类型</th>
			                <th>任务编号</th>
			                <th>任务名称</th>
			                <th>发起任务时间</th>
			                <th>当前任务状态</th>
			                <th>当前步骤状态</th>
			                <th></th>
			            </tr>
			        </thead>
			    </table>
	        </div>
	    </div>
   	</section>
   	<script type="text/javascript" src="/html/flow/js/base/yetDisposeTask.js" ></script>
  </body>
</html>
