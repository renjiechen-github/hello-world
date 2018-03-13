 var page_num = 0;
 var isLoading = false;
 /**
  * 出租合约
  */
var rank_agreement = 
{
		
		init:function()
		{ 
			// 初始化下拉框 
			rank_agreement.loadSelect();
			$('#rightdiv').click(function(){
				$(this).find('img').toggleClass('rot2');
				$('#hide1').toggle();
			});
			$("#status").change(function(){
				// 加载房源合约列表
				$('.body').html('');
				isLoading = false;
				page_num = 0; 
				rank_agreement.getRankAgreementList();
			});
			$("#areaid,#contractManage").change(function(){
				// 加载房源合约列表
				$('#rightdiv').find('img').toggleClass('rot2');
				$('#hide1').toggle();
				$('.body').html('');
				isLoading = false;
				page_num = 0; 
				rank_agreement.getRankAgreementList();
			});
			$("#sreachBut").click(function(){
				// 加载房源合约列表
				$('.body').html('');
				isLoading = false;
				page_num = 0; 
				rank_agreement.getRankAgreementList();
			});
			$('#checkbox_a1').click(function(){
				$('#rightdiv').find('img').toggleClass('rot2');
				$('.body').html('');
				$('#hide1').hide();
				isLoading = false;
				page_num = 0; 
				rank_agreement.getRankAgreementList();
			});
			rank_agreement.getRankAgreementList();
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
            	data:{status:$('#status').val(),pageNumber:page_num,keyword:$('#keyword').val(),accountid:$('#contractManage').val(),arear:$('#areaid').val(),isSelf:$('#checkbox_a1:checked').val()},
            	dataType:'json',
            	loadfun: function(isloadsucc, data)
            	{
            		var appendHtml = '';
    				if(isloadsucc)
    				{
    					for(var i = 0; i < data.length; i++)
    					{
    						var json = data[i];
//    						console.log(json);
    						appendHtml += '<a href="#" onclick="rank_agreement.openRankAgreement(this);return false;"><input type="hidden" name="house_rank_id" value="'+json.house_rank_id+'" /><input type="hidden" name="id" value="'+json.id+'" /><input type="hidden" name="title" value="'+json.title+'" /><input type="hidden" name="rankType" value="'+json.rankType+'" /><input type="hidden" name="house_id" value="'+json.house_id+'" />'
    								   +  '	<div class="div">'
    								   +  '		<div class="con space">'
    								   +  '			<div class="info"><p>'+json.title+'</p></div>'
    								   +  '			<div class="state1"><p class="housecolor'+json.status+'">'+json.rankstatus+'</p></div>'
    								   +  '     </div>'
    								   +  ' <div class="detail">'
    								   +  ' 	<p>'+json.agree_code+'&nbsp;<span class="color1"></span></p>'
    								   +  '		<p class="color1">'+json.userInfo+'&nbsp;&nbsp;<em>|</em>&nbsp;&nbsp;'+json.rankType+'<span>&nbsp;'+json.cost+'&nbsp;&nbsp;/&nbsp;&nbsp;'+json.rank_area+'㎡</span></p>'
    								   +  ' </div>'
    								   +  ' <div class="con">'
    								   +  ' 	<div class="info1"><img src="../../images/market/person.png" alt="" /></div>'
    								   +  ' 	<div class="state"><p>'+json.agentName+'</p></div> '
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
		loadSelect:function()
		{
			// 初始化下拉框
			// 加载下拉
			njyc.phone.loadItem('GROUP.RANK.AGREEMENT', function(json) {
				var html = '';
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
						 + json[i].item_name + '</option>';
				}
				$('#status').append(html);
			},false);
			njyc.phone.ajax(
			{
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getMangerList.do',
				dataType : 'json',
				async:false,
				loadfun : function(isloadsucc, json) 
				{
					if (isloadsucc)
					{
						var html = "";
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
			njyc.phone.loadArea('101', '3', function(json) {
				var html = "";
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].id + '" >'
							+ json[i].area_name + '</option>';
				}
				$('#areaid').append(html);
			});
		},
		openRankAgreement:function(obj)
		{
			// 
			var agreementId = $(obj).find('input[name="id"]').val();
			var title = $(obj).find('input[name="title"]').val();
			var house_rank_id = $(obj).find('input[name="house_rank_id"]').val();
			var house_id = $(obj).find('input[name="house_id"]').val();
			var rankType = $(obj).find('input[name="rankType"]').val();
			njyc.phone.openWebKit({title:'租房合约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/cz_contract_detail.html?id='+agreementId+'&title='+encodeURI(encodeURI(title))+'&house_rank_id='+house_rank_id+'&houseId='+house_id+'&rankType='+encodeURI(encodeURI(rankType))});
		}
		
		
}
$(document).ready(function(){
	rank_agreement.init();
    $(window).scroll(function()
    {
      if($(document).scrollTop()>=$(document).height()-$(window).height())
      {
    	  rank_agreement.getRankAgreementList();
      }
    });
}); 
//刷新列表
function refreshlList()
{
	njyc.phone.showProgress('');
	$('.body').html('');
	isLoading = false;
	page_num = 0; 
	rank_agreement.getRankAgreementList();
}