var page_num = 0;
var page_num1 = 0;
var page_numre = 0;
var page_numpay = 0;
var isLoading = false; 
var isLoading1 = false; 
var isLoadingre = false; 
var isLoadingpay = false; 
var viewF=
{
	init:function()
	{
		var step = njyc.phone.queryString('step');
		if (step=="0")
		{
			viewF.createTable(step);//查看代缴费信息
			viewF.createTable1(step);//查看代缴费信息
		}
		else
		{
			viewF.createTable1(step);//查看代缴费信息
			viewF.createTable(step);//查看代缴费信息
			viewF.createRecevie();//查看收入数据
			viewF.createPay();//查看支出数据
		}
	},//链接到租赁查看页面
	update:function(id)
	{    var step_type = njyc.phone.queryString('step_type'); // 出租类型
		 njyc.phone.openWebKit({title:'查看财务信息',url:njyc.phone.getSysInfo('serverPath')+"/html/pages/flow/pages/order/leaseorder/updateCost.jsp?id="+id+"&isMobile=1&step_type="+step_type+""});;
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
             		njyc.phone.showShortMessage('操作失败');
                }
                else
                {
                	njyc.phone.showShortMessage('操作成功');
                	refreshTODO();
				}
            }
            else 
            {
            	njyc.phone.showShortMessage('操作失败');
            }
         }
    });
	},
	createTable:function (step)
	{
		var orderId = njyc.phone.queryString('orderId'); // 
		var agree = njyc.phone.queryString('agree'); // 出租类型
		var step_type = njyc.phone.queryString('step_type'); // 出租类型
		if(isLoading)
		{
			return;
		}
		njyc.phone.showProgress('');
		njyc.phone.ajax
		({
        	url:njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/getList.do',
        	data:{orderId:orderId,agree:agree,step_type:0},
        	dataType:'json',
        	async:false,
        	loadfun: function(isloadsucc, data)
        	{
        		var appendHtml = '';
				if(isloadsucc)
				{
					for(var i = 0; i < data.length; i++)
					{
						var json = data[i];
						var html1="";
						if (step=="3"&&step_type=="0")
						{
							html1='<div class="detail" style="margin:6px">'
							+'<div class="left"><a onclick="viewF.update('+json.id+')">修改</a></div>'
							+'	<div class="left">'
							+'		 <a onclick="viewF.deleteI('+json.id+')">删除</a>'
							+'	</div>'
							+'</div>';
						}
						 appendHtml +='<div class="headdiv">'
							+'<div class="detail">'
							+'	<p id="name">●&nbsp;名称：'+json.name+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="content">'
							+'	<div class="detail">'
							+'	<div class="left">'
							+'		<p>类型</p>'
							+'	</div>'
							+'	<div class="right">'
							+'		<p id="costtype">'+json.typename+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="detail">'
							+'	<div class="left">'
							+'		<p>金额</p>'
							+'	</div>'
							+'	<div class="right">'
							+'	<p id="cost">'+json.cost+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="detail">'
							+'	<div class="left">'
							+'		<p>起始度数</p>'
							+'</div>'
							+'<div class="right">'
							+'		<p id="starttime">'+json.starttime+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="detail">'
							+'<div class="left">'
							+'		<p>结束度数</p>'
							+'	</div>'
							+'	<div class="right">'
							+'		<p id="endtime">'+json.endtime+'</p>'
							+'	</div>'
							+'</div>'
							+'<div class="detail">'
							+'	<div class="left">'
							+'		<p>计费度数</p>'
							+'	</div>'
							+'	<div class="right">'
							+'		<p id="degree">'+json.degree+'</p>'
							+'	</div>'
							+'<div class="left" onclick="showOrHide('+json.id+')">'
						    +'<img src="/client/images/mission/3.png" alt="" />'
						    +'	</div>'
							+'</div>'
							+'<div id="'+json.id+'" style="display: none">'
							+'<div class="detail">'
							+'<div class="left">'
							+'		<p>说明</p>'
							+'	</div>'
							+'	<div class="right">'
							+'		<p >'+json.costdesc+'</p>'
							+'	</div>'
							+'</div>'
							+'	</div>'
						    + html1
							+'	</div>  <div class="form-group">'
							+'<label class="col-sm-2  control-label"></label>'
							+'<div class="col-sm-4">'
							+'</div>'
							+'	<div class="col-sm-6"></div>'
							+'</div> ';
					}
					$('#createCost2').append(appendHtml);
					if(data.length < 25)
					{
						njyc.phone.showShortMessage('数据加载完毕!');
					}
				}
				isLoading = false;
        	}
		});
		page_num++;
		njyc.phone.closeProgress();
	  },
	  createTable1:function (step)
		{
			var orderId = njyc.phone.queryString('orderId'); // 
			var agree = njyc.phone.queryString('agree'); // 出租类型
			var step_type = njyc.phone.queryString('step_type'); // 出租类型
			if(isLoading1)
			{
				return;
			}
			njyc.phone.showProgress('');
			njyc.phone.ajax
			({
	        	url:njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/getList.do',
	        	data:{orderId:orderId,agree:agree,step_type:1},
	        	dataType:'json',
	        	async:false,
	        	loadfun: function(isloadsucc, data)
	        	{
	        		var appendHtml = '';
					if(isloadsucc)
					{
						for(var i = 0; i < data.length; i++)
						{
							var json = data[i];
							var html1="";
							if (step=="3"&&step_type=="1")
							{
								html1='<div class="detail" style="margin:6px">'
								+'<div class="left"><a onclick="viewF.update('+json.id+')">修改</a></div>'
								+'	<div class="left">'
								+'		 <a onclick="viewF.deleteI('+json.id+')">删除</a>'
								+'	</div>'
								+'</div>';
							}
							 appendHtml +='<div class="headdiv">'
								+'<div class="detail">'
								+'	<p id="name">●&nbsp;名称：'+json.name+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="content">'
								+'	<div class="detail">'
								+'	<div class="left">'
								+'		<p>类型</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p id="costtype">'+json.typename+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'	<div class="left">'
								+'		<p>金额</p>'
								+'	</div>'
								+'	<div class="right">'
								+'	<p id="cost">'+json.cost+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'	<div class="left">'
								+'		<p>起始度数</p>'
								+'</div>'
								+'<div class="right">'
								+'		<p id="starttime">'+json.starttime+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'<div class="left">'
								+'		<p>结束度数</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p id="endtime">'+json.endtime+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="detail">'
								+'	<div class="left">'
								+'		<p>计费度数</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p id="degree">'+json.degree+'</p>'
								+'	</div>'
								+'<div class="left" onclick="showOrHide('+json.id+')">'
							    +'<img src="/client/images/mission/3.png" alt="" />'
							    +'	</div>'
								+'</div>'
								+'<div id="'+json.id+'" style="display: none">'
								+'<div class="detail">'
								+'<div class="left">'
								+'		<p>说明</p>'
								+'	</div>'
								+'	<div class="right">'
								+'		<p >'+json.costdesc+'</p>'
								+'	</div>'
								+'</div>'
								+'	</div>'
							    + html1
								+'	</div>  <div class="form-group">'
								+'<label class="col-sm-2  control-label"></label>'
								+'<div class="col-sm-4">'
								+'</div>'
								+'	<div class="col-sm-6"></div>'
								+'</div> ';
						}
						$('#createCost1').append(appendHtml);
						if(data.length < 25)
						{
							njyc.phone.showShortMessage('数据加载完毕!');
						}
					}
					isLoading1 = false;
	        	}
			});
			page_num1++;
			njyc.phone.closeProgress();
		  },
	  createRecevie:function ()
		{
			var orderId = njyc.phone.queryString('orderId'); // 
			var agree = njyc.phone.queryString('agree'); // 出租类型
			if(isLoadingre)
			{
				return ;
			}
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
						for(var i = 0; i < data.length; i++)
						{
							var json = data[i];
							 appendHtml +='<div class="headdiv">'
								+'<div class="detail">'
								+'	<p >●&nbsp;名称：'+json.name+'</p>'
								+'	</div>'
								+'</div>'
								+'<div class="content">'
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
								+'	</div>  <div class="form-group">'
								+'<label class="col-sm-2  control-label"></label>'
								+'<div class="col-sm-4">'
								+'</div>'
								+'	<div class="col-sm-6"></div>'
								+'</div>  ';
						}
						$('#receives').append(appendHtml);
						if(data.length < 25)
						{
							njyc.phone.showShortMessage('数据加载完毕!');
						}
					}
					isLoadingre = false;
	        	}
			});
			page_numre++;
			njyc.phone.closeProgress();
		  },
		  createPay:function ()
			{
				var orderId = njyc.phone.queryString('orderId'); // 
				var agree = njyc.phone.queryString('agree'); // 出租类型
				if(isLoadingpay)
				{
					return ;
				}
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
							for(var i = 0; i < data.length; i++)
							{
								var json = data[i];
								 appendHtml +='<div class="headdiv">'
									+'<div class="detail">'
									+'	<p >●&nbsp;名称：'+json.name+'</p>'
									+'	</div>'
									+'</div>'
									+'<div class="content">'
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
									+'	</div>  <div class="form-group">'
									+'<label class="col-sm-2  control-label"></label>'
									+'<div class="col-sm-4">'
									+'</div>'
									+'	<div class="col-sm-6"></div>'
									+'</div>  ';
							}
							$('#pay').append(appendHtml);
							if(data.length < 25)
							{
								njyc.phone.showShortMessage('数据加载完毕!');
							}
						}
						isLoadingpay = false;
		        	}
				});
				page_numpay++;
				njyc.phone.closeProgress();
			  },
};
$(viewF.init());
//刷新列表
function refreshTODO()
{
	$('#createCost2').html('');
	$('#createCost1').html('');
	isLoading = false;
	page_num = 0; 
	isLoading1 = false;
	page_num1 = 0; 
	viewF.createTable(3);
	viewF.createTable1(3);
}