var checkHouse = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
	    	checkHouse.initType();
	},
    /**
     加载区域选择
     **/
    initType: function(){
    	var task_id=$("#task_check_id").val();
    	common.loadItem("LEASEORDER.REASONS", function(json)
	    {
	        var html = "'<option  value='' >请选择...</option>'";
	        for (var i = 0; i < json.length; i++) 
	        {
	 		   html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
	        }
	        $('#reasons3').html(html);
	    });
    	common.ajax(
	    {
			url : common.root + '/flow/getInfo.do',
			data : {task_id:task_id},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					console.log(data);
					if (data.endelectricdegree==null||data.endelectricdegree=="") 
					{
						$("#hidedic").html("暂无信息");
						return;
					}
					$("#alipay").html(data.alipay);
					$("#bankcard").html(data.bank);
					$("#bankname").html(data.bankname);
					$("#endwaterdegree").html(data.endwaterdegree+"m³");
					$("#endgasdegree").html(data.endgasdegree+"m³");
					$("#endelectricdegree").html(data.endelectricdegree+"°");
					$("#startwaterdegree").html(data.startwaterdegree+"m³");
					$("#startgasdegree").html(data.startgasdegree+"m³");
					$("#startelectricdegree").html(data.startelectricdegree+"°");
					$("#reasons").html(data.reasonsname);
					$("#key").html(data.keyrecieve+"&nbsp;把");
					$("#doorcard").html(data.doorcard+"&nbsp;张");
					$("#otherdesc").html(data.otherdesc);
					$("#checkdesc").html(data.housedesc);
					$("#favourable").html(data.favourable);
					checkHouse.imageWork(data.degreepic,"degreepic");
					checkHouse.imageWork(data.housepic,"housepic");
				}
			}
		 });
    	 checkHouse.creatTable1();
    },
    creatTable1:function(){
    	var FinancialCount=0;
		table.init({
	        id: '#Cost_table',
	        url: common.root + '/CertificateLeave/getList.do',
	        "iDisplayLength":100,
	        isexp:false,
            bStateSave:false,
            "oLanguage": 
            {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				'sSearch':'搜索',
				"oPaginate": 
				{
					"sFirst": "首页",
					"sPrevious": "前一页",
					"sNext": "后一页",
					"sLast": "尾页"
				},
				"sZeroRecords": "没有检索到数据",
				sProcessing:'<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
				sEmptyTable:'很抱歉，暂无数据'
			},
	        columns: 
        	[  
        	   "name",
				"typename", 
				"cost",  
				"starttime",
				"endtime",
				"degree",
				{name:"costdesc",isover:true,isshow:false,title:'待缴费说明'}
			],
			aoColumnDefs:[
				        	{ "bSortable": false, "aTargets": [ 0,1,2,3,4,5,6,7] }
				        ],
			 param: function()
			 {
	               var a = new Array();
				   a.push({"name": "orderId","value":$("#order_id1").val()});
				   a.push({ "name": "step_type", "value":1});
	               return a;
	          },
	        createRow:function(rowindex,colindex,name,value,data)
            {
	        	if (colindex==2)
	        	{
	        		FinancialCount += value;
				}
                 $("#costCounts").html(FinancialCount.toFixed(2));
                if(colindex == 0)
				{
				    return checkHouse.dealColum({"value":value,"length":15});
				}
                
	        	return;
            }
		 });
		$('.dataTables_wrapper').hide();
	},
    dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(common.isEmpty(def.value))
		 {
			 return "";
		 }
		 if(def.value.length > def.length)
		 {
			 return "<div title='"+def.value+"'>"+def.value.substr(0,def.length)+"...</div>";
		 }
		 else
		 {
			 return def.value;
		 }
	},
    imageWork:function(path,id){
    	if (path!=null&&path!="") {
			var pas=path.split(",");
			var paths=new Array();
			for ( var int = 0; int < pas.length; int++) {
				if (int==0) {
					paths.push({path:pas[int],first:1});	
				}else{
					paths.push({path:pas[int],first:0});
				}
			}
			common.dropzone.init({
				id : '#'+id,
				defimg:paths,
				maxFiles:10,
				clickEventOk:false
			});
		}else{
			common.dropzone.init({
				id : '#'+id,
				maxFiles:10,
				clickEventOk:false
			});
		}
    }
};
$(function(){
	checkHouse.init();
});
