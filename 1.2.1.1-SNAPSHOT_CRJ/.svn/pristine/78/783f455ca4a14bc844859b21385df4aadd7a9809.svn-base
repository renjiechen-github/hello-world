var house_agre_detail = 
{
	init:function()
	{
		// 加载租赁房源信息
		house_agre_detail.loadRankHouseInfo();
		// 该租赁房源合约列表
		house_agre_detail.loadRankAgreementList();
	},
	loadRankAgreementList:function()
	{
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/getRankAgreementList.do',
			data : {houseId:njyc.phone.queryString("rankId"),pageSize:500},
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc) 
				{
					$('.cz_contract').html('<div id="rankAgreenmentList"></div>');
					var html = '';
					for(var i = 0; i < data.length; i++)
					{
					   html = '<div class="divPadding" onclick="house_agre_detail.showRankAgreement(this);return false;"><input type="hidden" name="house_rank_id" value="'+data[i].house_rank_id+'" /><input type="hidden" name="id" value="'+data[i].id+'" /><input type="hidden" name="title" value="'+data[i].title+'" /><input type="hidden" name="rankType" value="'+data[i].rankType+'" /><input type="hidden" name="house_id" value="'+data[i].house_id+'" />'
							+ '  <div class="titleDiv">'
							+ '    <span class="leftSpan"><p>'+data[i].name+'</p><p p class="color2">'+data[i].code+'</p></span>'
							+ '    <span class="rightSpan"><p>'+data[i].rankType+'</p><p class="color1">'+data[i].rankstatus+'</p></span>&nbsp;&nbsp;'
							+ '  </div>'
							+ '  <div class="textDiv">'
							+ '    <div class="lefeDiv">面积</div>'
							+ '    <div class="rightDiv">&nbsp;'+data[i].rank_area+'</div>'
							+ '    <div class="lefeDiv">价格</div>'
							+ '    <div class="rightDiv">&nbsp;'+data[i].cost+'</div>'
							+ '    <div class="lefeDiv">租客</div>'
							+ '    <div class="rightDiv">&nbsp;'+data[i].username+'</div>'
							+ '   </div>'
							+ '</div>';
					   $('#rankAgreenmentList').append(html);
					}
					if(data.length > 0)
					{
					    html = '<div class="division" onclick="house_agre_detail.showOrHideAgreement(this)">'
							 + '    <div class="left" style="padding-top: 5px;">'
							 + '    <hr>'
							 + '    </div>'
							 + '    <div class="center">'
							 + '      <b class="minus">-</b>&nbsp;租赁合同'
							 + '    </div>'
							 + '    <div class="right" style="padding-top: 5px;">'
							 + '    <hr>'
							 + '    </div>'
							 + '</div>';
					    $('.cz_contract').prepend(html);
					}	
				}
			}
		});
	},
	loadRankHouseInfo:function()
	{
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/getuphouse.do',
			data : {id : njyc.phone.queryString("rankId")},
			dataType : 'json',
			loadfun : function(isloadsucc, array)
			{
				if (isloadsucc) 
				{
					var arrays = array.list;
					if(arrays.length == 0)
					{
						njyc.phone.showShortMessage('未查询到信息!');
						njyc.phone.closeWebView();
						return ;
					}	
					var data = arrays[0];
					$("#house_code").html(data.rank_code);
					$("#house_name").html(data.house_name);
					$("#house_title").html(data.title);
					$("#house_floor").html(data.floor);
					var spec=data.rank_spec;
					var spec0;
					var spec1;
					var spec2;
					var spec3;
					if (spec!=""&&spec!=null)
					{
						spec0=data.rank_spec.split("|");
						if (spec0.length==3) 
					 {
						spec1=spec0[0]+"室";
						spec2=spec0[1]+"厅";
						spec3=spec0[2]+"卫";
						$("#house_hx").html(spec1+spec2+spec3);
					 }
						else if (spec0.length==2)
						{
							spec1=spec0[0]+"室";
							spec2=spec0[1]+"厅";
							spec3=0+"卫";
							$("#house_hx").html(spec1+spec2+spec3);
						}
					}
					$("#house_czfs").html(data.rank_type);
					$("#house_arear").html(data.rank_area);
					$("#house_price").html(data.fee);
					njyc.phone.showPic(data.path);
					$('.item_close').hide();
//					$("#house_desc").text(data.rank_desc);
//					$("#house_address").text(data.address);
				}
			}
		});
	},
	showRankAgreement:function(obj)
	{
		// 显示单个房源信息
		var agreementId = $(obj).find('input[name="id"]').val();
		var title = $(obj).find('input[name="title"]').val();
		var house_rank_id = $(obj).find('input[name="house_rank_id"]').val();
		var house_id = $(obj).find('input[name="house_id"]').val();
		var rankType = $(obj).find('input[name="rankType"]').val();
		njyc.phone.openWebKit({title:'租房合约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/cz_contract_detail.html?id='+agreementId+'&title='+encodeURI(encodeURI(title))+'&house_rank_id='+house_rank_id+'&houseId='+house_id+'&rankType='+encodeURI(encodeURI(rankType))});

	},
	showOrHideAgreement:function(obj)
	{
		$('#rankAgreenmentList').toggle();
		var content = $(obj).find('b').html();
		if(content == '-')
		{
			$(obj).find('b').html('+');
			$(obj).find('b').removeClass('minus').addClass('add');
		}
		else
		{
			$(obj).find('b').html('-');
			$(obj).find('b').removeClass('add').addClass('minus');
		}	
	}
};
$(house_agre_detail.init());
function refreshlList()
{
	house_agre_detail.loadRankAgreementList()
}
