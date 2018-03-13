var page_num = 0;
var isLoading = false; 
var returnr=false;
var returnp=false;
var stepDetail4={
	init:function()
	{
	   $("input[type=radio][name=Fruit][value=0]").attr("checked",'checked');
	   $("#order_cost").val(0);
	   stepDetail4.createRecevie();
	   stepDetail4.createPay();
	   stepDetail4.createTable();
	},
	//添加事件
	createCost : function()
	{
        njyc.phone.openWebKit({title:'添加待缴费细目',url:njyc.phone.getSysInfo('serverPath')+"/html/pages/flow/pages/order/leaseorder/createCost.jsp?isMobile=1&orderId="+$('#orderId').val()+"&agree="+$('#agree').val()+"&task_id="+$("#task_id").val()+"&step_type=1"});
	},
	createTable:function ()
	{
		var orderId = $('#orderId').val(); // 
		var agree = $('#agree').val(); // 出租类型
		if(isLoading)
		{
			return;
		}
		njyc.phone.showProgress('');
		njyc.phone.ajax
		({
        	url:njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/getList.do',
        	data:{orderId:orderId,agree:agree},
        	dataType:'json',
        	async:false,
        	loadfun: function(isloadsucc, data)
        	{
        		var appendHtml = '';
        		var cosst=0;
				if(isloadsucc)
				{
					for(var i = 0; i < data.length; i++)
					{
						var json = data[i];
						cosst +=json.cost;
						var id=json.id+"3";
						var html1="";
							html1='<div class="detail" style="margin:6px">'
							+'<div class="left"><a onclick="stepDetail4.update('+json.id+')">修改</a></div>'
							+'	<div class="right">'
							+'		 <a onclick="stepDetail4.deleteI('+json.id+')">删除</a>'
							+'	</div>'
							+'</div>';
						appendHtml +='<div class="info"><div class="headdiv">'
							+'<div class="detail">'
							+'	<p id="name">●&nbsp;名称：'+json.name+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="content">'
							
							+'	<div class="detail">'
							+'	<div class="left">'
							+'		<p>类型:'+json.typename+'</p>'
							+'	</div>'
							+'	<div class="left">'
							+'		<p id="costtype">金额:'+json.cost+'</p>'
							+'	</div>'
							+'</div>'
							
							+'<div class="detail">'
							+'	<div class="left">'
							+'		<p>起始度数:'+json.starttime+'</p>'
							+'</div>'
							+'<div class="left">'
							+'		<p id="starttime">结束度数:'+json.endtime+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="detail">'
							+'	<div class="left">'
							+'		<p>计费度数:</p>'
							+'	</div>'
							+'	<div class="left" id="degree">'
							+json.degree
							+'	</div>'
							  +'	</div>'
							+'<div class="detail">'
							+'	<div class="left">'
							+'		<p></p>'
							+'	</div>'
							+'<div class="right" onclick="showOrHide('+id+')">'
						    +'<img src="/client/images/mission/3.png" alt="" />'
						    +'	</div>'
						    +'	</div>'
							
							+'<div id='+id+' style="display: none">'
						    +'<div class="form-group col-sm-12">'
						    +'<label class="">缴费说明：</label>'
						    +'<div class="">'
						    +'<div class="form-control-static">'
						    +json.costdesc
						    +'	</div>'
						    +'</div>'
						    +'</div>'
						    +'</div>'
						    + html1
							+'	</div> '
							+'</div> ';
					}
					$('#costdivcost3').html("&nbsp;&nbsp;&nbsp;金额总计："+cosst.toFixed(2));
					$('#costdiv3').append(appendHtml);
				}
				isLoading = false;
        	}
		});
		page_num++;
		njyc.phone.closeProgress();
	  },
	  createRecevie:function ()
		{
		    if (returnr) 
		    {
			    return;
		    }
			var orderId = $('#orderId').val(); // 
			var agree = $('#agree').val(); // 出租类型
			njyc.phone.showProgress('');
			njyc.phone.ajax
			({
	        	url:njyc.phone.getSysInfo('serverPath') + '/mobile/task/getFinancialList.do',
	        	data:{agreeRankId:agree},
	        	dataType:'json',
	        	async:false,
	        	loadfun: function(isloadsucc, data)
	        	{
	        		var appendHtml = '';
					if(isloadsucc)
					{
						returnr=true;
						var  returnpay=false;
						for(var i = 0; i < data.length; i++)
						{
							var json = data[i];//statusname
							 appendHtml +='<div class="info"><div class="headdiv">'
								+'<div class="detail">'
								+'	<p >●&nbsp;名称：'+json.name+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="content">'
								
								+'	<div class="detail">'
								+'	<div class="left">'
								+'		<p>状态</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p >'+json.statusname+'</p>'
								+'	</div>'
								+'</div>'
								
								+'	<div class="detail">'
								+'	<div class="left">'
								+'		<p>类型</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p >'+json.typename+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'	<div class="left">'
								+'		<p>金额</p>'
								+'	</div>'
								+'	<div class="right">'
								+'	<p>'+json.cost+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'	<div class="left">'
								+'		<p>开始时间</p>'
								+'</div>'
								+'<div class="right">'
								+'		<p >'+json.starttime+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'<div class="left">'
								+'		<p>结束时间</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p ">'+json.endtime+'</p>'
								+'	</div>'
								+'</div>'
								+'	</div> ' 
								+'</div>  ';
							 returnpay=true;
						}
						if (returnpay) {
							$('#receive').html(appendHtml);
						}else{
							
							$('#receive').html("&nbsp;&nbsp;&nbsp;暂无数据");
						}
					}
	        	}
			});
			njyc.phone.closeProgress();
		  },
		  createPay:function ()
			{
			  if (returnp) 
			    {
				    return;
			    }
				var orderId = $('#orderId').val(); // 
				var agree = $('#agree').val(); // 出租类型
				njyc.phone.showProgress('');
				njyc.phone.ajax
				({
		        	url:njyc.phone.getSysInfo('serverPath') + '/mobile/task/getPayList.do',
		        	data:{agreeRankId:agree},
		        	dataType:'json',
		        	async:false,
		        	loadfun: function(isloadsucc, data)
		        	{
		        		var appendHtml = '';
						if(isloadsucc)
						{
							returnp=true;
							var returnpay=false;
							for(var i = 0; i < data.length; i++)
							{
								var json = data[i];//statusname
								 appendHtml +='<div class="info"><div class="headdiv">'
									+'<div class="detail">'
									+'	<p >●&nbsp;名称：'+json.name+'</p>'
									+'	</div>'
									+'</div>'
									+'<div class="content">'
									
									+'	<div class="detail">'
									+'	<div class="left">'
									+'		<p>状态</p>'
									+'	</div>'
									+'	<div class="right">'
									+'		<p >'+json.statusname+'</p>'
									+'	</div>'
									+'</div>'
									
									+'	<div class="detail">'
									+'	<div class="left">'
									+'		<p>类型</p>'
									+'	</div>'
									+'	<div class="right">'
									+'		<p >'+json.typename+'</p>'
									+'	</div>'
									+'</div>'
									+'<div class="detail">'
									+'	<div class="left">'
									+'		<p>金额</p>'
									+'	</div>'
									+'	<div class="right">'
									+'	<p>'+json.cost+'</p>'
									+'	</div>'
									+'</div>'
									+'<div class="detail">'
									+'	<div class="left">'
									+'		<p>开始时间</p>'
									+'</div>'
									+'<div class="right">'
									+'		<p >'+json.starttime+'</p>'
									+'	</div>'
									+'</div>'
									+'<div class="detail">'
									+'<div class="left">'
									+'		<p>结束时间</p>'
									+'	</div>'
									+'	<div class="right">'
									+'		<p ">'+json.endtime+'</p>'
									+'	</div>'
									+'</div>'
									+'	</div> ' 
									+'</div>  ';
								 returnpay=true;
							}
							if (returnpay) {
								$('#paylist').html(appendHtml);
							}else{
								$('#paylist').html("&nbsp;&nbsp;&nbsp;暂无数据");
							}
						}
		        	}
				});
				njyc.phone.closeProgress();
			  },
	  update:function(id)
		{   
			 njyc.phone.openWebKit({title:'修改退租明细',url:njyc.phone.getSysInfo('serverPath')+"/html/pages/flow/pages/order/leaseorder/updateCost.jsp?id="+id+"&isMobile=1&step_type=1"});
		},
	  deleteI:function(id)
	    {  
	    	njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/delete.do',
			data :{id:id},
			dataType:'json',
	        loadfun: function(isloadsucc, data)
	        {
	            if (isloadsucc)
	            {
	                if (data.state == -2) 
	                {
	                	showDisposeFlowDetail.getRes(2);
	             		njyc.phone.showShortMessage('操作失败');
	                }
	                else
	                {
	                	showDisposeFlowDetail.getRes(1);
	                	njyc.phone.showShortMessage('操作成功');
	                	refreshTODO();
					}
	            }
	            else 
	            {
	            	showDisposeFlowDetail.getRes(2);
	            	njyc.phone.showShortMessage('操作失败');
	            }
	         }
	    });
		},
 save:function(state)
 {
	var flowremark = $('#flowremark').val();
	if(state == 2 )
	{
		 var order_cost=$('#order_cost').val();
		 var reg=/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			 //正则判断金额填写是否正确
		 if (!reg.test(order_cost)||order_cost<0)
		 {
			 showDisposeFlowDetail.getRes2('金额填写错误！');
			 njyc.phone.showShortMessage('金额填写错误！');
			 return;
		 }
		 if ($('input:radio:checked').val()==0&&order_cost!=0)
		 {
		 	order_cost=-order_cost;
	     }
		 var msg="信息是否录入完毕？";
		 window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		 { 
			 showDisposeFlowDetail.checkFun=function()
    		 { 
 	    		return {order_cost:order_cost}
    		 }
		    showDisposeFlowDetail.save(state, flowremark, "");
		 },onCancel:function()
		 {
			
		 }
		 });
	}
	else if(state == 3 )
	{
		var msg="是否打回重填？";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		{
		 showDisposeFlowDetail.save(state, flowremark, "");
		},onCancel:function()
		{
			
		}
		}); 
	}
 }
};
$(stepDetail4.init());
function refreshTODO()
{
	$('#costdiv3').html('');
	isLoading = false;
	page_num = 0; 
	stepDetail4.createTable();
	njyc.phone.closeProgress();
}
