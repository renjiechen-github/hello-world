var stepDetail=
{
	init:function()
	{
		var agree=$("#agree").val();
		var type=$("#type").val();
		common.loadItem('EVICTION.TYPE', function(json)
		{
			var dataId = $('#childtype1').attr('dataId');
            for (var i = 0; i < json.length; i++) 
            {
        	    if (json[i].item_id==dataId) 
        	    {
        			$('#childtype1').html(json[i].item_name);
  				}
            }
	    });
		$("#pichide").hide();
		$("#addresshide").hide();
		stepDetail.addressInit(agree);
	},sigleHouseInfo:function(house_rank_id,flag,houseId,rankType,title,agreementId)
	{
		// 查看单个房间信息
		common.openWindow({
			name:'signHouse',
			type : 1,
			data:{id:house_rank_id,flag:flag,houseId:houseId,rankType:rankType,title:title,agreementId:agreementId},
			area : [ ($(window).width()-200)+'px', ($(window).height()-300)+'px' ],
			title : '查询出租信息',
			url : '/html/pages/house/houseInfo/rank_house_agreement.html' 
		});
			
	},//加载维修地址和保洁地址
	addressInit:function (agree)
	{
	  common.ajax(
	  {
			url : common.root + '/rankHouse/loadAgreementInfo.do',
			data : {id:agree},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					$('#rankName')[0].innerHTML ="<a onclick='stepDetail.sigleHouseInfo("+data.house_rank_id+",0,"+data.house_id+",\""+data.rankType+"\",\""+data.title+"\","+agree+")'>"+ data.name+"("+data.agree_code+")</a>";
				}
			}
		});
	  },
};
$(function(){
	stepDetail.init();
});