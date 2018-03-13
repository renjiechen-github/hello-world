var returnr=false;
var returnp=false;
var stepDetail4=
{
	init:function()
	{
	   $("input[type=radio][name=Fruit][value=0]").attr("checked",'checked');
	   $("#order_cost").val(0);
	   stepDetail4.createRecevie();
	   stepDetail4.createPay();
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
							
							$('#receive').html("暂无数据");
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
								$('#paylist').html("暂无数据");
							}
							
						}
		        	}
				});
				njyc.phone.closeProgress();
			  },
	/**
	 * 保存操作
	 */
    save:function(state)
    {
	   var flowremark = $('#flowremark').val();
	   if(state == 2 )
	   {
		    var msg="是否审批成功？";
		    window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		    {
			   showDisposeFlowDetail.save(state, flowremark, "");
		    },
		    onCancel:function()
		    {
				
		    }
		    });
	   }else if(state == 3 )
	   {/*
		    var msg="是否审批拒绝？";
		    window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		    {
			   showDisposeFlowDetail.save(state, flowremark, "");
		    },
		    onCancel:function()
		    {
				
		    }
		    });
	   */}
    }
};
$(stepDetail4.init());
