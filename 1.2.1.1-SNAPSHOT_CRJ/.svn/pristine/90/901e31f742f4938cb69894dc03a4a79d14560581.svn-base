var total=0.0;
var stepDetail4={
	init:function(){
	//初始化管家选项
	stepDetail4.initData();
	//添加处理事件
	var agree =$('#agree').val();
	var order_time=$('#order_time').val().substring(0,10);
	stepDetail4.createFinancial(agree);
	stepDetail4.payCreateFinancial(agree);
	stepDetail4.calculate(agree,order_time);
	stepDetail4.createFinancialPay();
	$("input[type=radio][name=Fruit][value=0]").attr("checked",'checked');
	$("#order_cost").val(0);
	},
	calculate :function(agree,order_time){
		common.ajax({
	        url: common.root + '/flow/calculate.do',
	        data:{agreeId:agree,order_time:order_time},
			dataType:'json',
	        loadfun: function(isloadsucc, data)
	        {
	          if (isloadsucc)
	          {
	        	//$("#order_cost").val(data.nowcoat);  //"+data.month+"&nbsp;月"
	        	  $("#order_desc").html("已付租金："+data.allCost+"&nbsp;每月租金："+data.cost_a+"&nbsp;租住时长："+data.day+"&nbsp;天&nbsp;租金应退："+data.nowcoat+"(租金计算)"); 
	          }
	        }
	        })
	},
	createFinancial:function(agree){
		table.init({
	        id: '#receive',
	        url: common.root + '/flow/getFinancialList.do',
	        "iDisplayLength":100,
	        isexp:false,
            bStateSave:false,
            "oLanguage": 
            {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				'sSearch':'搜索',
				"oPaginate": 
				{
					"sFirst": "首页",
					"sPrevious": "前一页",
					"sNext": "后一页",
					"sLast": "尾页"
				},
				"sZeroRecords": "没有检索到数据",
				sProcessing:'<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
				sEmptyTable:'很抱歉，暂无数据'
			},
	        columns: 
        	[  
        	    "name",
				"typename", 
				"cost",  
				"starttime",
				"endtime",
				"statusname",
				{name:"remark",isover:true,isshow:false,title:'备注'},
			],
			 param: function()
			 {
	               var a = new Array();
					a.push({"name": "agreeRankId","value":agree});
	               return a;
	          },
	          createRow:function(rowindex,colindex,name,value,data)
	            {
	                if(colindex == 0)
					{
						 return stepDetail4.dealColum({"value":value,"length":5});
					}
		        	return;
	            }
		 });
		$('.dataTables_wrapper').hide();
	},
	tab:function (date1,date2)
	{
		var returni=false;
	    var oDate1 = new Date(date1);
	    var oDate2 = new Date(date2);
	    if(oDate1.getTime() > oDate2.getTime())
	    {
	    	returni=true
	    } 
	    return returni;
	},
	
	payCreateFinancial:function(agree){
		table.init({
	        id: '#paytable',
	        url: common.root + '/flow/getPayList.do',
	        "iDisplayLength":100,
	        isexp:false,
            bStateSave:false,
            "oLanguage": 
            {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				'sSearch':'搜索',
				"oPaginate": 
				{
					"sFirst": "首页",
					"sPrevious": "前一页",
					"sNext": "后一页",
					"sLast": "尾页"
				},
				"sZeroRecords": "没有检索到数据",
				sProcessing:'<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
				sEmptyTable:'很抱歉，暂无数据'
			},
	        columns: 
        	[  
        	   "name",
				"typename", 
				"cost",  
				"starttime",
				"endtime",
				"statusname",
				{name:"remark",isover:true,isshow:false,title:'备注'},
			],
			 param: function()
			 {
	               var a = new Array();
					a.push({"name": "agreeRankId","value":agree});
	               return a;
	          },
	          createRow:function(rowindex,colindex,name,value,data)
	            {
	                 if(colindex == 0)
					 {
						 return stepDetail4.dealColum({"value":value,"length":5});
					 }
		        	return;
	            }
		 });
		$('.dataTables_wrapper').hide();
	},
	initData : function(type) 
	{
	},
	//添加事件
	createCost : function() {
        //打开对于的窗口
        common.openWindow({
            type: 1,
            name:'createCost',
            title: '新增待缴项目',
            area : [ ($(window).width()-500)+'px', ($(window).height()-300)+'px' ],
            url: '/html/pages/flow/pages/order/leaseorder/createCost.jsp'
        });
	},
	createFinancialPay:function(){
		total=0.0;
		table.init({
	        id: '#certificateleave',
	        url: common.root + '/CertificateLeave/getList.do',
	        "iDisplayLength":100,
	        isexp:false,
            bStateSave:false,
            "oLanguage": 
            {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				'sSearch':'搜索',
				"oPaginate": 
				{
					"sFirst": "首页",
					"sPrevious": "前一页",
					"sNext": "后一页",
					"sLast": "尾页"
				},
				"sZeroRecords": "没有检索到数据",
				sProcessing:'<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
				sEmptyTable:'很抱歉，暂无数据'
			},
	        columns: 
        	[  
                {name:"name",sort:false},
				"typename", 
				"cost",  
				"starttime",
				"endtime",
				"degree",
				{name:"costdesc",isover:true,isshow:false,title:'待缴费说明'},
			],
			aoColumnDefs:[{ "bSortable": false, "aTargets": [ 0,1,2,3,4,5,6,7,8 ] }],
			bnt:
			[
			  {
				name:'删除',
				fun:function(data,row)
				{
					common.alert({
						msg : "是否删除此项？",
						confirm : true,
						closeIcon:true,
						confirmButton:'是',
						cancelButton:'否',
						fun : function(action)
						{
						 if (action)
						 {
							 common.ajax({
									url : common.root + '/CertificateLeave/delete.do',
									data : {id : data.id},
									dataType : 'json',
									loadfun : function(isloadsucc, data) 
									{
										if (isloadsucc)
										{
											if (data.state == 1)
											{
												common.alert({msg:'删除成功'});
												table.refreshRedraw('certificateleave');
												total=0.0;
												$("#costCountp").html(total);
											}
											else
											{
												common.alert({ msg: common.msg.error });
											}
										}
										else
										{
											common.alert({ msg: common.msg.error });
										}
									} 
								}); 
							
						  }
						 }
					});
				}
			   },{
				   name:'修改',
					fun:function(data,row)
					{
						rowdata=data;
				        //打开对于的窗口
				        common.openWindow({
				            type: 1,
				            name:'updateCost',
				            area : [ ($(window).width()-500)+'px', ($(window).height()-300)+'px' ],
				            title: '修改待缴项目',
				            url: '/html/pages/flow/pages/order/leaseorder/updateCost.jsp'
				        });
				        total=0.0;
					}
			   }
			],
			 param: function()
			 {
                var a = new Array();
				a.push({"name": "orderId","value": $("#orderId").val()});
				a.push({ "name": "step_type", "value":1});
                return a;
	          },
	        createRow:function(rowindex,colindex,name,value,data)
            {
	        	if (colindex==2)
	        	{
	        		total +=value;
				}
	        	$("#costCountp").html(total.toFixed(2));
	        	return;
            }
		 });
		$('.dataTables_wrapper').hide();
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	var msg = "";
	var flowremark = $('#flowremark').val();
	if(state == 2 )
	{
		 var order_cost=$('#order_cost').val();
		 var reg=/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			 //正则判断金额填写是否正确
		 if (!reg.test(order_cost)||order_cost<0)
		 {
			 common.alert({ msg: "金额填写错误！"});
			 return;
		 }
		 if ($('input:radio:checked').val()==0&&order_cost!=0)
		 {
		 	order_cost=-order_cost;
	     }
	 common.alert({
        msg: "是否核对完成",
        confirm: true,
        fun: function(action)
        {
    	    if (action) 
    	    {
    	    	showDisposeFlowDetail.checkFun=function()
       		    { 
    	    		return {order_cost:order_cost}
       		    }
			   showDisposeFlowDetail.save(state, flowremark, "");
    	    }
         }
	  }); 
	}else if(state == 3 )
	{
		 common.alert({
	        msg: "是否打回重填？",
	        confirm: true,
	        fun: function(action)
	        {
	    	    if (action) 
	    	    {
				   showDisposeFlowDetail.save(state, flowremark, "");
	    	    }
	         }
		  }); 
	}
   },
	dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(common.isEmpty(def.value))
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
	}
};

$(function(){
	stepDetail4.init();
});
var rowdata;
var step_type=1;