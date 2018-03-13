var page_num = 0;
 var isLoading = false;
 var id = ''; //房源id
 var username = '';// 房主姓名
 var user_id = '';// 房主id
 var mobile = '';// 房主mobile
 var house_name = '';//房源名称
 var floor = '';// 楼层
 var roomCnt = '';// 房间数量
 var is_cubicle = '';// 是否可以隔
 var house_code = '';// 房源码
 var free_rent = '';// 
 var areaid = '';// 小区编号
 var area = '';// 房源面积
 var cost_a = '';// 费用
 var lease_period = '';// 
 var house_status = '';// 房源状态
 var specName = '';// 房子户型
 var i = 0;
var house_list = {
		init:function()
		{
			$('.fixdiv').click(function(){
				njyc.phone.openWebKit({title:'新增房源',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/house_detail.html'});
			});
			njyc.phone.showProgress('');
			house_list.loadSelect();
			house_list.getHouseList();
			$('#rightdiv').click(function()
			{
				$(this).find('img').toggleClass('rot2');
				$('#hide1').toggle();
			});
			$('#publish,#contractManage,#trading').change(function()
			{
				$('#rightdiv').find('img').toggleClass('rot2');
				$('#hide1').toggle();
				refreshlList();
//				$('.body').html('');
//				isLoading = false;
//				page_num = 0;
//				house_list.getHouseList();
			});
			$('#areaid').change(function() {
				$('#trading').html('<option value="">请选择...</option>');
				$('#rightdiv').find('img').toggleClass('rot2');
				$('#hide1').toggle();
				refreshlList();
				if($(this).val()!='')
				{
					house_list.initType($(this).val(),'4','trading');
				}	
			});
			$('#checkbox_a1,#checkbox_a2').click(function(){
				$('#rightdiv').find('img').removeClass('rot2');
				$('#hide1').hide();
				refreshlList();
			});
			$('#status').change(function()
			{
				$('.body').html('');
				isLoading = false;
				page_num = 0;
				if($(this).val() == '11' || $(this).val() == '12')
				{
					$('.divflex2').hide();
					$('.divflex4').show();
					$('.lineFlex').css({'opacity':'0'});
					house_list.getRankAgreementList();
				}
				else
				{
					$('.divflex2').show();
					$('.divflex4').hide();
					$('.lineFlex').css({'opacity':'1'});
					house_list.getHouseList();
				}
			});
			$('#sreachBut').click(function()
			{
				refreshlList();
			});
			$('.divCenter').click(function(){
				$('.divCenter').hide();
			});
		},
		loadSelect:function()
		{
			njyc.phone.loadItem('MOBILE.HOUSE.STATE', function(json) {
				var html = "";
				for ( var i = 0; i < json.length; i++) { 
					var selected = '';
					if(json[i].item_id == '9')
					{
						selected = ' selected="selected" ';
					}	
					html += '<option value="' + json[i].item_id + '" '+selected+'>'
							+ json[i].item_name + '</option>';
				}
				$('#status').append(html);
			},false);
			njyc.phone.loadItem('BASEHOUSE.PUBLISH', function(json) {
				var html = "";
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
					+ json[i].item_name + '</option>';
				}
				$('#publish').append(html);
			});
			njyc.phone.ajax(
			{
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getMangerList.do',
				dataType : 'json',
				async:false,
				loadfun : function(isloadsucc, json) 
				{
					if (isloadsucc)
					{
						var html = "请选择...";
						for ( var i = 0; i < json.length; i++) 
						{
							html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
						}
						$('#contractManage').append(html);
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试');
					}
				}
			});
			house_list.initType('101','3','areaid');
//			house_list.initType('','4','trading');
//			njyc.phone.ajax(
//			{
//				url : njyc.phone.getSysInfo('serverPath') + '/BaseHouse/getTrading.do',
//				dataType : 'json',
//				async:false,
//				loadfun : function(isloadsucc, json) 
//				{
//					if (isloadsucc)
//					{
//						var html = "";
//						for ( var i = 0; i < json.length; i++) 
//						{
//							html += '<option value="' + json[i].id + '" >'
//								 + json[i].area_name + '</option>';
//						}
//						$('#trading').append(html);
//					}
//				}
//			});	
		},
		initType:function(fatherId, type, id)
		{
			njyc.phone.loadArea(fatherId, type, function(json) {
				var html = "";
				 $('#'+id).html('<option value="">请选择...</option>');
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].id + '" >'
							+ json[i].area_name + '</option>';
				}
				$('#'+id).append(html);
			});
		},
		getHouseList:function()
		{
			if(isLoading)
			{
				return ;
			}
			njyc.phone.showProgress('');
			njyc.phone.ajax({
            	url:njyc.phone.getSysInfo('serverPath')+'/mobile/houseMgr/getHouseList.do',
            	data:{areaid:$('#areaid').val(),pageNumber:page_num,keyword:$('#keyWord').val(),status:$('#status').val(),publish:$('#publish').val(),isSelf:$('#checkbox_a1:checked').val(),kzhouse:$('#checkbox_a2:checked').val(),trading:$('#trading').val()},
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
    						var isTop = '';
    						if(json.house_status == 9)
    						{
    							if (json.is_top==1) 
    							{
    								isTop = '<div class="state" onclick="house_list.istop(0,\''+json.id+'\');return false;"><p>精</p><i class="mark"></i></div>';
    							}
    							else
    							{
    								isTop = '<div class="state"  onclick="house_list.istop(1,\''+json.id+'\');return false;"><i class="mark1"></i></div>';
    							}	
    						}
//    						console.log(json);
    						appendHtml += '<a href="#">'
    								   +  '  <div class="div">'
    								   +  '	 	<div class="con space">'
    								   +  '		<div class="info"><p>'+json.address+'</p></div>'+isTop+'</div>'
    								   +  '		<div onclick="house_list.openDialog(this);return false;"> <input type="hidden" name="id" value="'+json.id+'" /><input type="hidden" name="username" value="'+json.username+'" /><input type="hidden" name="user_id" value="'+json.user_id+'" /><input type="hidden" name="mobile" value="'+json.mobile+'" /><input type="hidden" name="house_name" value="'+json.house_name+'" /><input type="hidden" name="floor" value="'+json.floor+'" /><input type="hidden" name="spec" value="'+json.spec+'" /><input type="hidden" name="is_cubicle" value="'+json.is_cubicle+'" /><input type="hidden" name="house_code" value="'+json.house_code+'" /><input type="hidden" name="free_rent" value="'+json.free_rent+'" /><input type="hidden" name="area" value="'+json.area+'" /><input type="hidden" name="areaid" value="'+json.areaid+'" /><input type="hidden" name="cost_a" value="'+json.cost_a+'" /><input type="hidden" name="specName" value="'+json.specName+'" /><input type="hidden" name="house_status" value="'+json.house_status+'" /><input type="hidden" name="lease_period" value="'+json.lease_period+'" />'
    							       +  ' 	<div class="detail">'
    							       +  '			<p>'+json.house_code+'&nbsp;<span class="color1">('+json.areaName+')</span></p>'
    							       +  '			<p class="color1">'+json.specName+'&nbsp;&nbsp;'+json.area+'㎡&nbsp;&nbsp;<em>|</em>&nbsp;&nbsp;<span>业主</span>&nbsp;&nbsp;'+(common.isEmpty(json.username)?'':json.username)+'</p>'
    							       +  ' 	</div>'
    							       +  ' 	<div class="con">'
    							       +  '			<div class="info1"><p>房源状态</p></div>'
    							       +  ' 		<div class="state1"><p class="housecolor'+json.house_status+'">'+json.houseStatus+'</p></div>'
    							       +  '		<div class="info1"><p >发布状态</p></div><div class="state1"><p class="publishcolor'+json.publish+'">'+json.publishname+'</p>'
    							       +  '	</div>'
    							       +  '	</div>'
    							       +  '</div><hr></div></a>';
    					}
    					$('.body').append(appendHtml);
    					if(data.length < 25)
    					{
    						njyc.phone.showShortMessage('数据加载完毕!');
    					}	
    				}
    				isLoading = false;
    			}
            });
			page_num++;
			njyc.phone.closeProgress()
		},
		openDialog:function(obj)
		{
			//alert($(obj).find('input[name="json"]'));
			id = $(obj).find('input[name="id"]').val(); //房源id
			username = $(obj).find('input[name="username"]').val();// 房主姓名
			user_id = $(obj).find('input[name="user_id"]').val();// 房主id
			mobile = $(obj).find('input[name="mobile"]').val();// 房主mobile
			house_name = $(obj).find('input[name="house_name"]').val();//房源名称
			floor = $(obj).find('input[name="floor"]').val();// 楼层
			roomCnt = $(obj).find('input[name="spec"]').val().split('|')[0];// 房间数量
			is_cubicle = $(obj).find('input[name="is_cubicle"]').val();// 是否可以隔
			house_code = $(obj).find('input[name="house_code"]').val();// 房源码
			free_rent = $(obj).find('input[name="free_rent"]').val();// 
			areaid = $(obj).find('input[name="areaid"]').val();// 区域编号
			area = $(obj).find('input[name="area"]').val();// 房源面积
			cost_a =  $(obj).find('input[name="cost_a"]').val();// 费用
			lease_period = $(obj).find('input[name="lease_period"]').val();//  
			house_status = $(obj).find('input[name="house_status"]').val();//
			specName = $(obj).find('input[name="specName"]').val();//
//			if(house_status != 2)
//			{
//				// 房源待签约  签约页面
//				njyc.phone.openWebKit({title:'房源信息',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/house_detail.html?id='+id});
//			}
//			else
//			{
				
//			}	
			njyc.phone.openWebKit({title:'房源信息',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/house_detail.html?id='+id+'&username='+encodeURI(encodeURI(username))+'&house_name='+encodeURI(encodeURI(house_name))+'&roomCnt='+roomCnt+'&is_cubicle='+is_cubicle+'&house_code='+house_code+'&free_rent='+free_rent+'&areaid='+areaid+'&cost_a='+cost_a+'&lease_period='+lease_period+'&area='+area+'&mobile='+mobile+'&user_id='+user_id+'&specName='+encodeURI(encodeURI(specName))+'&floor='+floor});
//			console.log(id+":"+username+":"+user_id+":"+mobile+":"+house_name+":"+floor+":"+roomCnt+":"+is_cubicle+":"+house_code+":"+free_rent+":"+areaid+":"+cost_a+":"+lease_period);
		},
		istop:function(state,id)
		{
			var msg="";
			if (state==1) 
			{
				msg="是否设置为精品房源？";
			}
			else
			{
				msg="是否取消精品房源？";
			}
			window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function(){
					house_list.istopFunc(state,id);
				},onCancel:function(){
//					house_detail.istopFunc(state);
				}
			});
		},
		istopFunc:function(state,id)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/rankIstop.do',
				data : {id:id, istop:state},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功!');
							refreshlList();
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
			
		},
		getRankAgreementList:function()
		{
			// 加载合约列表信息
			if(isLoading)
			{
				return ;
			}
			njyc.phone.showProgress('');
			njyc.phone.ajax({
            	url:njyc.phone.getSysInfo('serverPath')+'/mobile/rankAgreement/getRankAgreementList.do',
            	data:{pageNumber:page_num,keyword:$('#keyWord').val(),accountid:$('#contractManage').val(),arear:$('#areaid').val(),newStatus:$('#status').val(),isSelf:$('#checkbox_a1:checked').val(),trading:$('#trading').val()},
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
//    						console.log(json);
    						appendHtml += '<a href="#" onclick="house_list.openRankAgreement(this);return false;"><input type="hidden" name="house_rank_id" value="'+json.house_rank_id+'" /><input type="hidden" name="id" value="'+json.id+'" /><input type="hidden" name="title" value="'+json.title+'" /><input type="hidden" name="rankType" value="'+json.rankType+'" /><input type="hidden" name="house_id" value="'+json.house_id+'" />'
    								   +  '	<div class="div">'
    								   +  '		<div class="con space">'
    								   +  '			<div class="info"><p>'+json.title+'</p></div>'
    								   +  '			<div class="state3"><p class="housecolor'+json.status+'">'+json.rankstatus+'</p></div>'
    								   +  '     </div>'
    								   +  ' <div class="detail">'
    								   +  ' 	<p>'+json.agree_code+'&nbsp;<span class="color1"></span></p>'
    								   +  '		<p class="color2">'+json.userInfo+'&nbsp;&nbsp;<em>|</em>&nbsp;&nbsp;'+json.rankType+'<span>&nbsp;'+json.cost+'</span>&nbsp;&nbsp;/&nbsp;&nbsp;'+json.rank_area+'㎡</p>'
    								   +  ' </div>'
    								   +  ' <div class="con">'
    								   +  ' 	<div class="info1"><img src="../../images/market/person.png" alt="" /></div>'
    								   +  ' 	<div class="state2"><p>'+json.agentName+'</p></div> '
    								   +  ' </div><hr></div></a>';
    					} 
    					$('.body').append(appendHtml); 
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
		openRankAgreement:function(obj)
		{
			// 出租合约
			var agreementId = $(obj).find('input[name="id"]').val();
			var title = $(obj).find('input[name="title"]').val();
			var house_rank_id = $(obj).find('input[name="house_rank_id"]').val();
			var house_id = $(obj).find('input[name="house_id"]').val();
			var rankType = $(obj).find('input[name="rankType"]').val();
			njyc.phone.openWebKit({title:'租房合约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/cz_contract_detail.html?id='+agreementId+'&title='+encodeURI(encodeURI(title))+'&house_rank_id='+house_rank_id+'&houseId='+house_id+'&rankType='+encodeURI(encodeURI(rankType))});
		},
		loadListByStatus:function()
		{
			// 通过状态加载不同列表信息
			var val = $('#status').val();
			if(val == 11 || val == 12)
			{
				house_list.getRankAgreementList();
			}
			else
			{
				house_list.getHouseList();
			}	
			
			
		}
};
$(document).ready(function(){
	house_list.init();
    $(window).scroll(function()
    {
      if($(document).scrollTop()>=$(document).height()-$(window).height())
      {
    	  house_list.loadListByStatus();
      } 
    });
});

//刷新列表
function refreshlList()
{
	$('.body').html('');
	isLoading = false;
	page_num = 0; 
	house_list.loadListByStatus();
}

