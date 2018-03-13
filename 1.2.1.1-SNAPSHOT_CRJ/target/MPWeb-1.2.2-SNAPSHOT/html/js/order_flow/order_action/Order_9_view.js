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
		 if (rowdata.picurls!=null&&rowdata.picurls!="") {
				var pas=rowdata.picurls.split(",");
				var paths=new Array();
				for ( var int = 0; int < pas.length-1; int++) {
					if (int==0) {
						paths.push({path:pas[int],first:1});	
					}else{
						paths.push({path:pas[int],first:0});
					}
				}
				common.dropzone.init({
					id : '#myAwesomeDropzone',
					defimg:paths,
					maxFiles:10,
					clickEventOk:false
				});
			}else{
				common.dropzone.init({
					id : '#myAwesomeDropzone',
					maxFiles:10,
					clickEventOk:false
				});
			}
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
		
		common.ajax({
			url : common.root + '/rankHouse/loadAgreementInfo.do',
			data : {id:rowdata.correspondent},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					console.log(data);
					var address=data.address;
					if (address!=null&&address!="") {
						$('#address1')[0].innerHTML = data.address;
					}
					else
					{
						
					}
				}
			}
		    });
     }
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
	},
};
orderview.init();