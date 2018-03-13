 var page_num = 0;
 var isLoading = false;
/**
 * 收房合约列表
 */
var house_agreement_list=
{
		init:function()
		{
			njyc.phone.showProgress('');
			// 加载合约状态
			house_agreement_list.loadSelect(); 
			$('#rightdiv').click(function(){
				$(this).find('img').toggleClass('rot2');
				$('#hide1').toggle();
			});
			$("#areaid,#contractManage").change(function(){
				// 加载房源合约列表
				$('#rightdiv').find('img').toggleClass('rot2');
				$('#hide1').toggle();
				$('.body').html('');
				isLoading = false;
				page_num = 0; 
				house_agreement_list.getHouseAgreementList();
			});
			$("#status").change(function(){
				// 加载房源合约列表
				$('.body').html('');
				isLoading = false;
				page_num = 0; 
				house_agreement_list.getHouseAgreementList();
			});
			$("#sreachBut").click(function(){
				// 加载房源合约列表
				$('.body').html('');
				isLoading = false;
				page_num = 0; 
				house_agreement_list.getHouseAgreementList();
			});
			
			// 加载房源合约列表
			house_agreement_list.getHouseAgreementList();
		},
		loadSelect:function()
		{
			// 加载下拉
			njyc.phone.loadItem('GROUP.AGREEMENT', function(json) {
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
			njyc.phone.loadArea('101', '3', function(json) {
				var html = "";
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].id + '" >'
							+ json[i].area_name + '</option>';
				}
				$('#areaid').append(html);
			});
		},
		getHouseAgreementList:function()
		{
			if(isLoading)
			{
				return ;
			}
			njyc.phone.showProgress('');
			njyc.phone.ajax({
            	url:njyc.phone.getSysInfo('serverPath')+'/mobile/houseAgreement/getHouseAgreementList.do',
            	data:{status:$('#status').val(),pageNumber:page_num,keyword:$('#keyword').val(),arear:$('#areaid').val(),accountid:$('#contractManage').val()},
            	dataType:'json',
            	loadfun: function(isloadsucc, data)
            	{
            		njyc.phone.closeProgress()
            		var appendHtml = '';
    				if(isloadsucc)
    				{
    					for(var i = 0; i < data.length; i++)
    					{
    						var json = data[i];
//    						console.log(json);
    						appendHtml += '<a href="#" onclick="house_agreement_list.openHouseAgreement(this);return false;"><input type="hidden" name="id" value="'+json.id+'" /><input type="hidden" name="house_id" value="'+json.house_id+'" /><input type="hidden" name="username" value="'+json.user_name+'" />'
    								   +  ' <div class="div">'
    								   +  '		<div class="con space">'
    								   +  '			<div class="info"><p>'+json.name+'</p></div>'
    								   +  '			<div class="state1"><p class="housecolor'+json.status+'">'+json.agreement_statue+'</p></div>'
    								   +  '	    </div>'
    								   +  '		<div class="detail">'
    								   +  '			<p>'+json.house_code+'&nbsp;<span class="color1">('+json.xq_name+')</span></p>'
    								   +  '			<p class="color1">'+json.begin_time+'~'+json.end_time+'&nbsp;&nbsp;<b>￥'+json.cost+'</b>'
//    								   +  '				<em>|</em>&nbsp;&nbsp;<span>业主&nbsp;'+json.user_name+'('+json.user_mobile+')</span>'
    								   +  '			</p>'
    								   +  '		</div>'
    								   +  '		<div class="con">'
    								   +  '			<div class="info1"><img src="../../images/market/person.png" alt="" /></div>'
    								   +  '			<div class="state"><p>'+json.agentName+'</p></div>'
    								   +  '		</div>'
    								   +  '<hr>'
    								   +  '</div></a>';
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
		},
		openHouseAgreement:function(obj)
		{
			// 打开收房合约列表信息
			var id = $(obj).find('input[name="id"]').val();
			var houseId = $(obj).find('input[name="house_id"]').val();
			njyc.phone.openWebKit({title:'房源合约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/wt_contract_detail.html?agreementId='+id+'&id='+houseId});
		}
};
$(document).ready(function(){
	house_agreement_list.init()
    $(window).scroll(function()
    {
      if($(document).scrollTop()>=$(document).height()-$(window).height())
      {
    	  house_agreement_list.getHouseAgreementList();
      }
    });
});

// 刷新列表
function refreshlList()
{
	$('.body').html('');
	isLoading = false;
	page_num = 0; 
	house_agreement_list.getHouseAgreementList();
}
