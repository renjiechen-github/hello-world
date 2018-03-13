var stepDetail={
	init:function(){
	    var agree=$("#agree").val();
		var type=$("#type").val();
		switch (type)
		{
		case "0":
			$("#childhide").hide();
			$("#pichide").hide();
			$("#addresshide").hide();
			stepDetail.houseInit(agree);
			break;
		case "1":
			stepDetail. initData("ORDERSERVICE.TYPE");
			stepDetail.addressInit(agree);
			stepDetail.picInit();
			break;
		case "2":
			stepDetail. initData("ORDERCLEAN.TYPE");
			stepDetail.addressInit(agree);
			stepDetail.picInit();
			break;
		case "3":
			$("#addresshide").hide();
			$("#pichide").hide();
			$("#childhide").hide();
			break;
		case "4":
			$("#addresshide").hide();
			$("#pichide").hide();
			$("#childhide").hide();
			break;
		case "5":
			stepDetail. initData("ORDERSERVICE.TYPE");
			stepDetail.OwnerAddress(agree);
			stepDetail.picInit();
			break;
		case "6":
			$("#childhide").hide();
			stepDetail.picInit();
			break;
		case "9":
			$("#childhide").hide();
			stepDetail.picInit();
			stepDetail.addressInit(agree);
			break;
		} 
	},
	addressInit: function(agree) 
	{
		njyc.phone.ajax(
		{
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/getAddress.do',
			data:{id:agree},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, json) 
			{
				if (isloadsucc)
				{
					var address=json.address;
					if (address!=null&&address!="") 
					{
						$('#address').html(json.address);
					}
					else
					{
						
					}
				}
                else
				{
					showDisposeFlowDetail.getRes(2);
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
	},
	OwnerAddress:function (agree)
	  {
		njyc.phone.ajax
		({
		   url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/houseAgreementInfo.do',
		   data:{id:agree},
		   dataType : 'json',
		   async:false,
		   loadfun : function(isloadsucc, json) 
		   {
			if (isloadsucc)
			{
				var address=json.address;
				if (address!=null&&address!="") 
				{
					$('#address').html(json.address);
				}
				else
				{
					showDisposeFlowDetail.getRes(2);
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
            else
			{
				showDisposeFlowDetail.getRes(2);
				njyc.phone.showShortMessage('网络忙,请稍候重试');
			}
		}
		})
	  },
	picInit: function() 
	{
		//初始化图片上传
		 var picurl = $('#hideurl').val();
		 if (picurl!=null&&picurl!="")
		 {
			 picurl=picurl.replace(/,/g,"|");
			 picurl=picurl.substring(0,picurl.lastIndexOf("|")); 
		 }
		 njyc.phone.showPic(picurl,"fileurl");
		 
		 $("#fileUrl .item_close").hide();
		 //fileurl1
		 var picurl = $('#hideurl1').val();
		 if (picurl!=null&&picurl!="")
		 {
			 picurl=picurl.replace(/,/g,"|");
			 picurl=picurl.substring(0,picurl.lastIndexOf("|")); 
		 }
		 njyc.phone.showPic(picurl,"fileurl1");
		 $("#fileurl1 .item_close").hide();
	},
	initData : function(type) 
	{
    njyc.phone.loadItem(type, function(json)
    {
        var dataId = $('#childtype1').attr('dataId');
        for (var i = 0; i < json.length; i++) 
        {
        	if (dataId==json[i].item_id) {
        		$('#childtype')[0].innerHTML=json[i].item_name;
			}
        }
    });
	},
	houseInit: function() 
	{
		var rankid=$("#rank").val();
		njyc.phone.ajax(
		{
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/queryRankHouse.do',
			data:{rank_id:rankid},
			dataType:'json',
			async : false,
			loadfun : function(isloadsucc, json) 
			{
			  if (isloadsucc)
			  {
				  $("#rankcode")[0].innerHTML="<a onclick='stepDetail.alink("+json.id+")'>"+json.house_name+"("+json.rank_code+")</a>";
			  } 
			  else 
			  {
				  
			  }
			}
	     });
	},
	alink: function(rankId){
		njyc.phone.openWebKit({title:'房源信息',url:njyc.phone.getSysInfo('serverPath')+"/client/pages/market/house_agre_detail.html?rankId="+encodeURIComponent(rankId)});
	}
};
$(stepDetail.init());