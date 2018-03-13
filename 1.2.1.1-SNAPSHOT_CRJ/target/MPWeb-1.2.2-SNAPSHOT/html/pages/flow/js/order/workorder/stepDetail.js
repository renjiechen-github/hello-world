var stepDetail=
{
	init:function()
	{
		var agree=$("#agree").val();
		var type=$("#type").val();
		switch (type)
		{
		case "0":
			$("#typehide").hide();
			$("#pichide").hide();
			$("#addresshide").hide();
			stepDetail.houseInit(agree);
			break;
		case "1":
			stepDetail. initData("ORDERSERVICE.TYPE");
			stepDetail.typeInit();
			stepDetail.addressInit(agree);
			break;
		case "2":
			stepDetail.typeInit();
			stepDetail. initData("ORDERCLEAN.TYPE");
			stepDetail.addressInit(agree);
			break;
		case "3":
			$("#addresshide").hide();
			$("#pichide").hide();
			$("#typehide").hide();
			break;
		case "4":
			$("#addresshide").hide();
			$("#pichide").hide();
			$("#typehide").hide();
			break;
		case "5":
			stepDetail. initData("ORDERSERVICE.TYPE");
			stepDetail.typeInit();
			stepDetail.OwnerAddress(agree);
			break;
		case "6":
			$("#typehide").hide();
			stepDetail.typeInit();
			break;
		case "7":
			$("#typehide").hide();
			$("#pichide").hide();
			$("#addresshide").hide();
			stepDetail.typeInit();
			break;
		case "9":
			stepDetail.typeInit();
			$("#typehide").hide();
			stepDetail.addressInit(agree);
			break;
		}
	},
initData : function(type) 
{
      common.loadItem(type, function(json)
      {
        var dataId = $('#childtype1').attr('dataId');
        for (var i = 0; i < json.length; i++) 
        {
        	if (dataId==json[i].item_id)
        	{
        		$('#childtype1')[0].innerHTML=json[i].item_name;
			}
        }
       });
 },
 typeInit: function()
 {
	//初始化图片上传
	 var picurl = $('#upurlp').attr('dataId');
	 if (picurl!=null&&picurl!="")
	 {
		var pas=picurl.split(",");
		var paths=new Array();
		for ( var int = 0; int < pas.length-1; int++) 
		{
			if (int==0) 
			{
				paths.push({path:pas[int],first:1});	
			}else{
				paths.push({path:pas[int],first:0});
			}
		}
		common.dropzone.init({
			id : '#upurlp',
			defimg:paths,
			maxFiles:10,
			clickEventOk:false
		});
		}
	 else
		{
			common.dropzone.init({
				id : '#upurlp',
				maxFiles:10,
				clickEventOk:false
			});
		}
  },//加载房源名称链接
  houseInit :function (agree)
  {
	  common.ajax(
				{
					url : common.root + '/houserank/getrankhouse.do',
					data : {id:agree},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							$('#rankName')[0].innerHTML ="<a onclick='stepDetail.sigleHouseInfo("+agree+")'>"+ data.data[0].house_name+"("+data.data[0].rank_code+")</a>";
						}
					}
				});
  },//加载维修地址和保洁地址
  addressInit:function (agree)
  {
	  common.ajax({
					url : common.root + '/rankHouse/loadAgreementInfo.do',
					data : {id:agree},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							var address=data.address;
							if (address!=null&&address!="") 
							{
								$('#address')[0].innerHTML = data.address;
							}
							else
							{
								
							}
						}
					}
				});
  },
  OwnerAddress:function (agree)
  {
	  common.ajax({
					url : common.root + '/agreementMge/agreementInfo.do',
					data : {id:agree},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							var address=data.address;
							if (address!=null&&address!="") 
							{
								$('#address')[0].innerHTML = data.address;
							}
							else
							{
								
							}
						}
					}
				});
  },
  sigleHouseInfo:function(id)
	{
		//弹窗查看合约
		common.openWindow({
			name:'sigleHouseInfo',
			type : 1,
			data:{id:id},
			title : '查看房间信息',
			url : '/html/pages/house/houseInfo/sigleHouseInfo.html' 
		});
	},
};
$(function(){
	stepDetail.init();
});