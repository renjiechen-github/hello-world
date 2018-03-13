var orderview =
{
    //页面初始化操作
    init : function()
    {
       // 加载结算类型
  	   orderview.initData();
	   orderview.addEvent();
	},
    addEvent: function()
    {
    	$("#backrow").click(function() {
    		common.closeWindow('orderview', 3);
		});
    	$("#dispatch").click(function()
    	{
			 var url='/html/pages/order_flow/order_action/dispatch.html';
			 var name="dispatch";
			 common.openWindow({type: 1,name:name,title: "派单", url: url});
		 });
    },
    initData: function()
    {
	 if (rowdata != null)
	 {
		//$('#rankName')[0].innerHTML = rowdata.area_name;
		$('#oname')[0].innerHTML = rowdata.order_name;
		$('#order_code')[0].innerHTML = rowdata.order_code;
		$('#ouserMobile')[0].innerHTML = rowdata.user_mobile;
		$('#ouserName')[0].innerHTML = rowdata.user_name;
		$('#oserviceTime')[0].innerHTML = rowdata.service_time;
		$('#odesc')[0].innerHTML = rowdata.order_desc;
		if (rowdata.order_status!=4)
		{
		   $('#dispatch').hide();
		}
		 common.loadItem('EVICTION.TYPE', function(json)
		{
            for (var i = 0; i < json.length; i++) 
            {
        	    if (json[i].item_id==rowdata.childtype) 
        	    {
        			$('#childtype')[0].innerHTML =json[i].item_name ;
  				}
            }
	    });
		 
		common.ajax({
					url : common.root + '/rankHouse/loadAgreementInfo.do',
					data : {id:rowdata.correspondent},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							$('#rankNames')[0].innerHTML ="<a onclick='orderview.sigleHouseInfo("+data.house_rank_id+",0,"+data.house_id+",\""+data.rankType+"\",\""+data.title+"\","+rowdata.correspondent+")'>"+ data.name+"("+data.agree_code+")</a>";
						}
					}
				});
		
		 table.init({
	         id: '#flowexample',
	         url: common.root + '/flow/getMyStartTaskList.do',
	         columns: ["typenames",
						"task_code", 
						"name", 
						"createtime",  
						"statename",
						"<button title='查看详细'onclick='orderview.edit(this)' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button>"],
			 param: function()
			 {
	                var a = new Array();
					a.push({ "orderId": rowdata.id});
					a.push({ "name": "orderId", "value": rowdata.id});
	                return a;
	         },
		 });
     }
    },
    edit:function(obj){
		var data = table.getRowData('flowexample', obj);
		common.openWindow({
			url:common.root + '/flow/showFlowDetail.do?step_id='+data.step_id+'&task_id='+data.task_id,
			name:'showFlowDetail',
			type:1,
			area : [ ($(window).width()-200)+'px', ($(window).height()-200)+'px' ],
			title:data.typenames+"_我发起的任务",
		});
		 
	},sigleHouseInfo:function(house_rank_id,flag,houseId,rankType,title,agreementId)
	{
		// 查看单个房间信息
		common.openWindow({
			name:'signHouse',
			type : 1,
			data:{id:house_rank_id,flag:flag,houseId:houseId,rankType:rankType,title:title,agreementId:agreementId},
			title : '查询出租信息',
			area : [ ($(window).width()-200)+'px', ($(window).height()-300)+'px' ],
			url : '/html/pages/house/houseInfo/rank_house_agreement.html' 
		});
	},
};
orderview.init();