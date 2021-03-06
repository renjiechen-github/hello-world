var order2 = {
    //页面初始化操作
    init: function(){
    //初始化类型
	order2.initType();
    //创建表格
	order2.createTable();
	//类型查询
	$("#order_type2").change(function() {
		  $('#search2').click();
	});
	//状态查询
	$("#order_status2").change(function() {
		  $('#search2').click();
	});
	
    },
	/**
     加载结算类型数据
     **/
    initType: function(){
       //类型加载    	
        common.loadItem('ORDER.TYPE', function(json){
        	 var html = "<option value=''>请选择...</option>";
            for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#order_type2').html(html);
        });
        
        /**
		 * 订单状态报表
		 */
		common.ajax({
			url : common.root + '/Order/orderReport.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
			   if (isloadsucc)
			   {
			     if (data.state == 1)
			     {
			    	 var count1=0;
			    	 var vount2=0;
				  for ( var i = 0; i < data.list.length; i++)
				  {
					  switch (data.list[i].r_key) 
					  {
					   case -1:
				    		 $("#badge_end")[0].innerHTML=data.list[i].r_value;
							break;
				    	case 2:
				    		count1=data.list[i].r_value;
				    	   //  $("#badge_pay")[0].innerHTML=data.list[i].r_value;
							break;
				    	case 3:
				    		vount2=data.list[i].r_value;
				    	    // $("#badge_pay")[0].innerHTML=data.list[i].r_value;
							break;
				    	case 4:
				    		 $("#badge_goto")[0].innerHTML=data.list[i].r_value;
							break;
				    	case 6:
				    		 $("#badge_action")[0].innerHTML=data.list[i].r_value;
							break;
				    	case 200:
				    		 $("#badge_done")[0].innerHTML=data.list[i].r_value;
							break;
				    	
				      	default:
						  break;
					   }
				       }
				  $("#badge_pay")[0].innerHTML=count1+vount2;
			     }
			   }
			   else 
			   {
						common.alert({msg : common.msg.error});
			   }
			}
		});
        
		/**
		 * 订单类型报表
		 */
		common.ajax({
			url : common.root + '/Order/typeReport.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
			   if (isloadsucc)
			   {
			     if (data.state == 1)
			     {
				  for ( var i = 0; i < data.list.length; i++) {
					  switch (data.list[i].order_type) 
					  {
					   case 0:
				    		 $("#asr0")[0].innerHTML=data.list[i].count+"("+data.list[i].count0+")";
				    		 $("#count0")[0].innerHTML=data.list[i].c1+"("+data.list[i].rb+")";
							break;
				    	case 1:
				    		 $("#asr1")[0].innerHTML=data.list[i].count+"("+data.list[i].count0+")";
							break;
				    	case 2:
				    		 $("#asr2")[0].innerHTML=data.list[i].count+"("+data.list[i].count0+")";
							break;
				    	case 3:
				    		 $("#asr3")[0].innerHTML=data.list[i].count+"("+data.list[i].count0+")";
							break;
				    	case 4:
				    		 $("#asr4")[0].innerHTML=data.list[i].count+"("+data.list[i].count0+")";
							break;
				    	case 6:
				    		 $("#asr6")[0].innerHTML=data.list[i].count+"("+data.list[i].count0+")";
							break;
				      	default:
						  break;
					   }
				       }
			     }
			   }
			   else 
			   {
						common.alert({msg : common.msg.error});
			   }
			}
		});
        
    },
    /**
     * 表格创建
     */
    createTable: function()
    {
        table.init({
            id: '#order_table2',
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
					"step_name",
					{name:"order_desc",isover:true,isshow:false,title:'订单详情'},
					{name:"create_time",isover:true,isshow:false,title:'创建时间'},
					{name:"account",isover:true,isshow:false,title:'发起人'},
					{name:"wait_oper",isover:true,isshow:false,title:'当前操作人'},
					{name:"nowoper",isover:true,isshow:false,title:'当前修改人'},
					{name:"actiontime",isover:true,isshow:false,title:'当前修改时间'},
					//'<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="order2.view(this);"  href="#">查看详情</a></li></ul></div>'
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
							                          +'onclick="order2.checkTask(\''+tdata.data[i].step_id+'\',\''+tdata.data[i].task_id+'\',\''+tdata.data[i].typenames+'\')" class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button></td >'
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
				  }
				], 
			param: function(){
		                var a = new Array();
		                a.push({name: "order_type", value: $('#order_type2').val()});
						a.push({name:'status',value:"6,8,10"});
						a.push({name:'order_name',value:$('#order_name2').val()});
		                return a;
		            },  
		            //默认按钮等操作
		            createRow:function(rowindex,colindex,name,value,data)
		            {
	            	  if( colindex == 2)
					  {
						 return order2.dealColum({"value":value,"length":5});
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
    	rowdata = table.getRowData('order_table2', obj);
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
};
order2.init();
var rowdata = null;