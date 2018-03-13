var page_num = 0;
var isLoading = false; 
var returnI=false;
var stepDetail=
{
	init:function()
	{
		stepDetail.createTable();
		var agree=$("#agree").val();
		var type=$("#type").val();
		njyc.phone.loadItem('EVICTION.TYPE', function(json)
		{
			var dataId = $('#childtype1').attr('dataId');
			for ( var i = 0; i < json.length; i++)
			{
				if (dataId == json[i].item_id)
				{
					$('#childtype')[0].innerHTML = json[i].item_name;
				}
			}
		});
		$("#pichide").hide();
		$("#addresshide").hide();
		stepDetail.addressInit(agree);
	},//链接到租赁查看页面
	sigleHouseInfo:function(house_rank_id,flag,houseId,rankType,title,agreementId)
	{
		njyc.phone.openWebKit({title:'租赁信息',url:njyc.phone.getSysInfo('serverPath')+"/client/pages/market/cz_contract_detail.html?title="+title+"&rankType="+rankType+"&houseId="+houseId+"&house_rank_id="+house_rank_id+"&id="+agreementId+"&orderflag=1"});
	},
	addressInit:function (agree)
	{
		njyc.phone.ajax(
				{//mobile/rankAgreement/
					url : njyc.phone.getSysInfo('serverPath') + 'mobile/rankAgreement/getRankAgreementList.do',
					data:{agreeId:agree},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, json) 
					{
						if (isloadsucc)
						{
							$('#rankName').html('<a onclick="stepDetail.sigleHouseInfo('+json[0].house_rank_id+',0,'+json[0].house_id+',\''+json[0].rankType+'\',\''+json[0].title+'\',\''+agree+'\')" >'+json[0].name+'</a>');
						} else {
							var sysRes=njyc.phone.clientType;
							if (sysRes=="IOS") 
							{
								alert('网络忙,请稍候重试');
							}
							njyc.phone.showShortMessage('网络忙,请稍候重试');
						}
					}
				});
	  },
	  createTable:function ()
		{
		    if (returnI)
		    {
			   return;
		    } 
		    var orderId = $('#order_id1').val(); // 
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
					if(isloadsucc)
					{
						returnI=true;
						var cost=0;
						for(var i = 0; i < data.length; i++)
						{
							var json = data[i];
							cost +=json.cost;
							var html1="";
								/*html1='<div class="detail" style="margin:6px">'
								+'<div class="left"><a onclick="stepDetail4.update('+json.id+')">修改</a></div>'
								+'	<div class="right">'
								+'		 <a onclick="stepDetail4.deleteI('+json.id+')">删除</a>'
								+'	</div>'
								+'</div>';*/
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
								+'<div class="right" onclick="showOrHide('+json.id+')">'
							    +'<img src="/client/images/mission/3.png" alt="" />'
							    +'	</div>'
							    +'	</div>'
								
								+'<div id='+json.id+' style="display: none">'
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
								+'	</div>  '
								+'</div> ';
						}
						$('#costdivcost').html("金额总计："+cost.toFixed(2));
						$('#costdiv').html(appendHtml);
					}
					isLoading = false;
	        	}
			});
			page_num++;
			njyc.phone.closeProgress();
		  },
};
$(stepDetail.init());