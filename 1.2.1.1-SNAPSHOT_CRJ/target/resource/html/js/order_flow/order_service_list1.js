var order1 = {
    //页面初始化操作
    init: function(){
    //初始化类型
	order1.initType();
    //创建表格
	order1.createTable();
	//类型查询
	$("#order_type1").change(function() {
		  $('#search1').click();
	});
	//状态查询
	$("#order_status1").change(function() {
		  $('#search1').click();
	});
    },
	/**
     加载数据
     **/
    initType: function(){
    	//新增操作
    	 common.loadItem('ORDER.TYPE', function(json){
             var html = "";
             for (var i = 0; i < json.length; i++) {
            	 if (json[i].item_id==9) {
            		 continue;
				}
 				html +='<li><a target="_blank" onclick="order1.addButtype('+json[i].item_id+',\''+json[i].item_name+'\');"role="button">'+json[i].item_name+'</a></li>';
             }
        $('#addul1')[0].innerHTML=html;
         });
    	 
    	//状态加载    	
         common.loadItem('ORDER.STATUS', function(json){
        	 var html = "<option value=''>请选择...</option>";
             for (var i = 0; i < json.length; i++) {
 				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
             }
             $('#order_status1').html(html);
             $('#order_status1').val(4);
             $('#search1').click();
         });
       //类型加载    	
        common.loadItem('ORDER.TYPE', function(json){
        	 var html = "<option value=''>请选择...</option>";
            for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#order_type1').html(html);
        });
    },
    /**
     * 表格创建
     */
    createTable: function()
    {
        table.init({
            id: '#order_table1',
            url: common.root + '/Order/getList.do',
            columns: [
                    "order_code",
					"typename",
					"order_name",
					"service_time",
					"user_mobile",
					"user_name",
					"order_cost",
					"statusname",
					{name:"order_desc",isover:true,isshow:false,title:'订单详情'},
					{name:"create_time",isover:true,isshow:false,title:'创建时间'},
					{name:"account",isover:true,isshow:false,title:'发起人'},
					{name:"nowoper",isover:true,isshow:false,title:'当前修改人'},
					{name:"actiontime",isover:true,isshow:false,title:'当前修改时间'},
					//'<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="order1.dispatch(this);"  href="#">派单</a></li><li><a class="paddingClass" onclick="order1.update(this);"  href="#">编辑</a></li><li><a class="paddingClass" onclick="order1.closeOrder(this);"  href="#">关闭</a></li><li><a class="paddingClass" onclick="order1.view(this);"  href="#">查看详情</a></li></ul></div>'
					],
					overflowfun:function(aData,head)
					{
				    	var html="";
						var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
						for(var i=0;i<head.length;i++)
						{
							var name = head[i].name;
							sOut += '<tr><td>'+head[i].title+':</td><td> '+aData[name]+'</td></tr>';
						}
						if (aData.order_type==1||aData.order_type==2||aData.order_type==9)
						{
							common.ajax({
								url : common.root + '/rankHouse/loadAgreementInfo.do',
								data : {id:aData.correspondent},
								dataType : 'json',
								async:false,
								loadfun : function(isloadsucc, data) 
								{
									if (isloadsucc)
									{
										var address=data.address;
										if (address!=null&&address!="") {
											sOut += '<tr><td>地址:</td><td> '+data.address+'</td></tr>';
										}
										else
										{
											sOut += '<tr><td>地址:</td><td></td></tr>';
										}
									}
								}
							    });
						}
						sOut += '</table><div class="row" >';
						var retrunI=false;
				    	common.ajax(
								{
									url : common.root + '/flow/getMyStartTaskList.do',
									data : {orderId : aData.id},
									dataType : 'json',
									async:false,
									loadfun : function(isloadsucc, tdata) 
									{
										if (isloadsucc)
										{
											html='<tbody  role="alert" aria-live="polite" aria-relevant="all">';
											for(var i=0;i<tdata.data.length;i++)
											{
												retrunI=true;
												html +='<tr num="0" class="odd">'
							                          +'<td style="border: 1px solid #ddd !important;">'+tdata.data[i].typenames+'</td >'
							                          +'<td style="border: 1px solid #ddd !important;">'+tdata.data[i].task_code+'</td >'
							                          +'<td style="border: 1px solid #ddd !important;">'+tdata.data[i].name+'</td >'
							                          +'<td style="border: 1px solid #ddd !important;">'+tdata.data[i].createtime+'</td >'
							                          +'<td style="border: 1px solid #ddd !important;">'+tdata.data[i].statename+'</td >'
							                          +'<td style="border: 1px solid #ddd !important;"><button title="查看详细"'
							                          +'onclick="order1.checkTask(\''+tdata.data[i].step_id+'\',\''+tdata.data[i].task_id+'\',\''+tdata.data[i].typenames+'\')" class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button></td >'
							                          +'</tr >';
											}
											html+='</tbody>';
										 }
									}
								});
				    	if (retrunI)
				    	{
				    		sOut +='<div class="tab-panel">'
			                     +' <div class="panel-body">'
			                     +' <div class="adv-table">'
			                     +' 	<table id="flowexample" class="display tablehover table table-bordered dataTable" cellspacing="0" width="80%">'
			                     +'	        <thead>'
			                     +'	            <tr>'
			                     +'	                <th>任务类型</th>'
			                     +'	                <th>任务编号</th>'
			                     +'	                <th>任务名称</th>'
			                     +'	                <th>发起任务时间</th>'
			                     +'	                <th>当前任务状态</th>'
			                     +'	                <th>操作</th>'
			                     +'	            </tr>'
			                     +'	        </thead>'
			                     +        html
			                     +'	    </table>'
			                     +'   </div>'
			                     +'</div>'
			                     +'</div>'
			                     +'<div';
						}
				    	else
				    	{
							
						}
			    	    return sOut;
					},
			bnt:[
				 {
			    	 name:'派单',
					 fun:function(data,row)
					 {
						rowdata = data;
						var url='/html/pages/order_flow/order_action/dispatch.html';
						var name="dispatch";
						 common.openWindow({
					            type: 1,
					            name:name,
					            title: "派单",
					            url: url
					        });
				     }
				  },
				  {
			    	 name:'编辑',
					 fun:function(data,row)
					 {
				    	rowdata = data;
				    	var type=rowdata.order_type;
				    	var orderName=rowdata.order_name;
						var url='/html/pages/order_flow/order_action/Order_'+type+'_update.html';
						var title =orderName;
						var name="orderupdate"+type;
						 common.openWindow({
					            type: 3,
					            name:name,
					            title: title,
					            url: url
						        });
					 }
				},
				{
			    	 name:'关闭',
					 fun:function(data,row)
				     {
						rowdata =data;
				        common.alert(
				        {
				            msg: '是否关闭订单',
				            confirm: true,
				            fun: function(action)
				            {
				                if (action) 
				                {
				                    common.ajax(
				                    {
				                        url: common.root + '/Order/closeOrder.do',
				                        data: {id: rowdata.id,status:rowdata.order_status},
				                        dataType: 'json',
				                        loadfun: function(isloadsucc, data)
				                        {
				                            if (isloadsucc)
				                            {
				                                if (data.state == '1') 
				                                {
				                                    common.alert({
				                                        msg: '操作成功',
				                                        fun: function()
				                                        {
				                							table.refreshRedraw('order_table1');
				                							table.refreshRedraw('order_table2');
				                							table.refreshRedraw('order_table3');
				                							table.refreshRedraw('order_table');
				                							table.refreshRedraw('order_table4');
				                							order1.initType();
				                                        }
				                                    });
				                                }
				                                else  if (data.state == '-4')
				                                {
				                                	common.alert
				                                    ({
				                                        msg: '当前状态不允许关闭'
				                                    });
				                                }
				                                else 
				                                {
				                                    common.alert
				                                    ({
				                                        msg: common.msg.error
				                                    });
				                                }
				                            }
				                            else {
				                                common.alert({
				                                    msg: common.msg.error
				                                });
				                            }
				                        }
				                    });
				                }
				            }
				        }); 
						}
				},
				  {
			    	 name:'查看详情',
					 fun:function(data,row)
				     {
						rowdata = data;
						var type=rowdata.order_type;
				    	var orderName=rowdata.order_name;
						var url='/html/pages/order_flow/order_action/Order_'+type+'_view.html';
						var title =orderName;
						var name="orderview"+type;
						 common.openWindow({
					            type: 3,
					            name:name,
					            title: title,
					            url: url
					        });
					  }
				},
				 ],
			param: function(){
		                var a = new Array();
		                a.push({name: "order_type", value: $('#order_type1').val()});
						a.push({name: "status", value: $('#order_status1').val()});
						a.push({name:'order_name',value:$('#order_name1').val()});
		                return a;
		            },  
		            //默认按钮等操作
		            createRow:function(rowindex,colindex,name,value,data)
		            {
	            	  if( colindex == 2)
					  {
						 return order1.dealColum({"value":value,"length":5});
					  }
		            }
        });	
    },
    checkTask:function(step_id,task_id,typenames)
    {
		common.openWindow({
			url:common.root + '/flow/showFlowDetail.do?step_id='+step_id+'&task_id='+task_id,
			name:'showFlowDetail',
			type:1,
			area : [ ($(window).width()-200)+'px', ($(window).height()-200)+'px' ],
			title:typenames+"_任务处理查看",
		});
	},
    view: function(obj)
    {
    	rowdata = table.getRowData('order_table1', obj);
    	var type=rowdata.order_type;
    	var orderName=rowdata.order_name;
		var url='/html/pages/order_flow/order_action/Order_'+type+'_view.html';
		var title =orderName;
		var name="orderview"+type;
		 common.openWindow({
	            type: 3,
	            name:name,
	            title: title,
	            url: url
	        });
    }, update: function(obj)
    {
    	rowdata = table.getRowData('order_table1', obj);
    	var type=rowdata.order_type;
    	var orderName=rowdata.order_name;
		var url='/html/pages/order_flow/order_action/Order_'+type+'_update.html';
		var title =orderName;
		var name="orderupdate"+type;
		 common.openWindow({
	            type: 3,
	            name:name,
	            title: title,
	            url: url
	        });
    },
    dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(def.value == "null" || def.value == '' || def.value == null)
		 {
			 return "";
		 }
		 if(def.value.length > def.length)
		 {
			 return "<div title='"+def.value+"'>"+def.value.substr(0,def.length)+"...</div>";
		 }
		 else
		 {
			 return def.value;
		 }
	},
	//派单
	dispatch:function(obj)
		{
		rowdata = table.getRowData('order_table1', obj);
		var url='/html/pages/order_flow/order_action/dispatch.html';
		var name="dispatch";
		 common.openWindow({
	            type: 1,
	            name:name,
	            title: "派单",
	            url: url
	        });
		},
		closeOrder:function(obj)
		{
			rowdata = table.getRowData('order_table1', obj);
	        common.alert(
	        {
	            msg: '是否关闭订单',
	            confirm: true,
	            fun: function(action)
	            {
	                if (action) 
	                {
	                    common.ajax(
	                    {
	                        url: common.root + '/Order/closeOrder.do',
	                        data: {id: rowdata.id,status:rowdata.order_status},
	                        dataType: 'json',
	                        loadfun: function(isloadsucc, data)
	                        {
	                            if (isloadsucc)
	                            {
	                                if (data.state == '1') 
	                                {
	                                    common.alert({
	                                        msg: '操作成功',
	                                        fun: function()
	                                        {
	                							table.refreshRedraw('order_table1');
	                							table.refreshRedraw('order_table2');
	                							table.refreshRedraw('order_table3');
	                							table.refreshRedraw('order_table');
	                							table.refreshRedraw('order_table4');
	                							order1.initType();
	                                        }
	                                    });
	                                }
	                                else  if (data.state == '-4')
	                                {
	                                	common.alert
	                                    ({
	                                        msg: '当前状态不允许关闭'
	                                    });
	                                }
	                                else 
	                                {
	                                    common.alert
	                                    ({
	                                        msg: common.msg.error
	                                    });
	                                }
	                            }
	                            else {
	                                common.alert({
	                                    msg: common.msg.error
	                                });
	                            }
	                        }
	                    });
	                }
	            }
	        }); 
		},
		//新增弹出层
		addButtype:function(type,orderName)
			{
			var url='/html/pages/order_flow/order_newAction/Order_'+type+'_Add.html';
			var title =orderName;
			var name="orderadd"+type;
			 common.openWindow({
		            type: 3,
		            name:name,
		            title: title,
		            url: url
		        });
			},
};
order1.init();
var rowdata = null;