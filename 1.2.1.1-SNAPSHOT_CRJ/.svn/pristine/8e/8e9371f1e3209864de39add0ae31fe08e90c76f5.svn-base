var stepDetail4={
	init:function(){
	//初始化管家选项
	stepDetail4.initData();
	//添加处理事件
	var agree =$('#agree').val();
	stepDetail4.createFinancial(agree);
	stepDetail4.createFinancialPay(agree);
	$("input[type=radio][name=Fruit][value=0]").attr("checked",'checked');
	$("#order_cost").val(0);
	},
	sigleHouseInfo:function(house_rank_id,flag,houseId,rankType,title,agreementId)
	{
		// 查看单个房间信息
		common.openWindow({
			name:'signHouse',
			type : 1,
			data:{id:house_rank_id,flag:flag,houseId:houseId,rankType:rankType,title:title,agreementId:agreementId},
			area : [ ($(window).width()-500)+'px', ($(window).height()-300)+'px' ],
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
					$('#rankName')[0].innerHTML ="<a onclick='stepDetail4.sigleHouseInfo("+data.house_rank_id+",0,"+data.house_id+",\""+data.rankType+"\",\""+data.title+"\","+agree+")'>"+ data.name+"("+data.agree_code+")</a>";
				}
			}
		});
	  },
	initData : function(type) 
	{
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
	createFinancial:function(agree){
		table.init({
	        id: '#receive',
	        url: common.root + '/flow/getFinancialList.do',
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
				"statusname",
				{name:"remark",isover:true,isshow:false,title:'备注'},
			],
			 param: function()
			 {
	               var a = new Array();
					a.push({"name": "agreeRankId","value":agree});
	               return a;
	          },
	          createRow:function(rowindex,colindex,name,value,data)
	            {
	                 if(colindex == 0)
					 {
						 return stepDetail4.dealColum({"value":value,"length":5});
					 }
		        	return;
	            }
		 });
		$('.dataTables_wrapper').hide();
	},
	createFinancialPay:function(agree)
	{
		table.init({
	        id: '#paytable',
	        url: common.root + '/flow/getPayList.do',
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
				"statusname",
				{name:"remark",isover:true,isshow:false,title:'备注'},
			],
			 param: function()
			 {
	               var a = new Array();
					a.push({"name": "agreeRankId","value":agree});
	               return a;
	          },
	          createRow:function(rowindex,colindex,name,value,data)
              {
                 if(colindex == 0)
				 {
					 return stepDetail4.dealColum({"value":value,"length":5});
				 }
	        	return;
              }
		 });
		$('.dataTables_wrapper').hide();
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {                     
	var msg = "";
	var flowremark = $('#flowremark').val();
	if(state == 2 )
	{
	    common.alert({
        msg: "是否审批成功",
        confirm: true,
        fun: function(action)
        {
    	    if (action) 
    	    {
			   showDisposeFlowDetail.save(state, flowremark, "");
    	    }
        }
	    }); 
	}
	else if(state == 3 )
	{/*
	    common.alert({
        msg: "是否审批拒绝",
        confirm: true,
        fun: function(action)
        {
    	    if (action) 
    	    {
			   showDisposeFlowDetail.save(state, flowremark, "");
    	    }
         }
	  }); 
	*/}
   }
};
$(function(){
	stepDetail4.init();
});