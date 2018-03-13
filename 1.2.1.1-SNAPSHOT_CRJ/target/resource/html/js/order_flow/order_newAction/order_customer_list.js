var orderList = {
	/**
     * 初始化操作信息
     */
    init : function() {
        this.loadData();
        this.typeInit();
        this.loadEvent();
        this.stateInit();
    },
    loadEvent:function()
    {
   	    // enter监听事件
     	$('#keyword').keydown(function(e)
     	{
     		if (e.which == "13")
     		{
     			$("#serach").click();
     			return false;// 禁用回车事件
     		}
     	});
     	$("#order_type").change(function() 
        {
     		$("#search").click();
		});
     	$("#order_state").change(function() 
        {
     		$("#search").click();
		});
    },
    typeInit:function()
    {
    	common.ajax({
			url : common.root + '/workOrder/getWorkOrderType.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc)
				{
					var html = "<option value=''>请选择...</option>";
				    var date=data.workOrderTypeList;
					for (var i = 0; i < date.length; i++)
		            {
						html += '<option  value="' + date[i].type + '" >' + date[i].typeName + '</option>';
		            }
					$("#order_type").html(html);
				}
			}
    	})
    },
    stateInit:function()
    {
    	common.ajax({
			url : common.root + '/workOrder/getSubOrderState.do	',
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc)
				{
					var html = "<option value=''>请选择...</option>";
				    var date=data.subOrderStateList;
					for (var i = 0; i < date.length; i++)
		            {
						html += '<option  value="' + date[i].state + '" >' + date[i].stateName + '</option>';
		            }
					$("#order_state").html(html);
					//$("#order_type").select2();
				}
			}
    	})
    },
    
    /**
     * 加载数据信息
     */
    loadData : function() {
        table.init({
            id:'#customerOrder',
            // expname:'财务收入',
            url:common.root+'/workOrder/getWorkOrderList4CustomerService.do',
            columns:["code",
                     "name",
                     "type_name",
                     "created_date_str",
                     "user_name",
                     "user_phone",
                     "sub_state_name",
                     "sub_assigned_dealer_name",
                     {name:"appointment_date_str",isover:true,isshow:false,title:'预约时间：'},
                     {name:"sub_comments",isover:true,isshow:false,title:'订单详情'},
                     {name:"assign_comments",isover:true,isshow:false,title:'派单备注'},
                     {name:"execute_comments",isover:true,isshow:false,title:'执行备注'},
                     {name:"sub_assigned_dealer_role_name",isover:true,isshow:false,title:'当前操作角色'},
                     {name:"address",isover:true,isshow:false,title:'地址'}
                    ],
            param: function(){
                var a = new Array();
                a.push({name: "type", value: $('#order_type').val()});
				a.push({name: "subOrderState", value: $('#order_state').val()});
               /* a.push({name: "userPhone", value: $('#user_mobile').val()});
                a.push({name: "userName", value: $('#user_name').val()});
                a.push({name: "code", value: $('#order_code').val()});*/
				a.push({name: "keyword", value: $('#keyword').val()});
                return a;
            },  
            aoColumnDefs:[{ "bSortable": false, "aTargets": [ 0,1,2,3,4,5,6,7,8,9,10]}],
            bnt:[
				{
					name:'查看详情',
					isshow : function(data, row) {
						var resReturn = false;
						if (data.queryPermission == "1") {
							resReturn = true;
						}
						return resReturn;
					},
					fun:function(data,row)
					{
						rowdata = data;
						window.open(common.root + '/workOrder/path2WorkOrderDetailInfoPage.do?id=' + rowdata.id + '&isMobile=N');
						// var title = rowdata.type_name;
						// common.openWindow({
				  //           url : common.root + '/workOrder/path2WorkOrderDetailPage.do?id=' + rowdata.id + '&isMobile=N',
				  //           name : 'workOrderDetail',
				  //           type : 3,
				  //           title : title
				  //       });
					}
				}
            ],
        	bFilter : false,
            overflowfun:function(aData,head)
			{
		    	 var html="";
				 var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
				 var param = {'workOrderId': aData.id};	
		    	//处理创建人
		    	var create_oper="";
				if (aData.created_staff_name!=null&&aData.created_staff_name!="")
				{
					create_oper=aData.created_staff_name
				}
				else if (aData.created_user_name!=null&&aData.created_user_name!="")
				{
					create_oper=aData.created_user_name;
				}
				else if (aData.user_name!=null&&aData.user_name!="")
				{
					create_oper=aData.user_name;
				}
				sOut +='<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">创建人:&nbsp;&nbsp;&nbsp;'+create_oper+'</div>';
		    	for(var i=0;i<head.length;i++)
				{
					var name = aData[head[i].name]!=null&&aData[head[i].name]!=""&&aData[head[i].name]!="undefined"?aData[head[i].name] : "";
					sOut +='<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">'+head[i].title+': '+name+'</div>';
				}
				var typeAddress=orderList.typeAddress(aData);
				if (typeAddress!=null&&typeAddress!="") {
					sOut +='<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">地址:&nbsp;&nbsp;&nbsp;'+typeAddress+'</div>';
				}
		    	sOut += '</table>';
	    	    return sOut;
			},
			createRow:function(rowindex,colindex,name,value,data,row){
				/*if(colindex == 6)//处理创建人
				 {
					if (data.created_staff_name!=null&&data.created_staff_name!="")
					{
						 return '<div style="text-align: center;">'+data.created_staff_name+'</div>';
					}
					else if (data.created_user_name!=null&&data.created_user_name!="")
					{
						 return '<div style="text-align: center;">'+data.created_user_name+'</div>';
					}
					else if (data.user_name!=null&&data.user_name!="")
					{
						 return '<div style="text-align: center;">'+data.user_name+'</div>';
					}
				 }*/
				if(colindex == 7)//处理当前处理人判断
				 {
					return '<div style="text-align: center;">'+data.sub_assigned_dealer_role_name!=null&&data.sub_assigned_dealer_role_name!=""?data.sub_assigned_dealer_role_name:"";+"&nbsp;"+data.sub_assigned_dealer_name!=null||data.sub_assigned_dealer_name!=""?data.sub_assigned_dealer_name:"";+'</div>';
				 }
				if(colindex == 1)//处理当前处理人判断
				 {
					return orderList.dealColum({"value":value,"length":8});
				 }
			}
         });
    },
    typeSwitch:function(type,data){
    	var resData=null;
    	switch (type) {
			case "A":
				resData=data.cancelLeaseOrderHisList;
				return resData ;
			case "D":
				resData=data.houseLookingOrderHisList;
				return resData ;
			case "C":
				resData=data.complaintOrderHisList;
				return resData ;
			case "B":
				resData=data.cleaningOrderHisList;
				return resData ;
			case "E":
				resData=data.livingProblemOrderHisList;
				return resData ;
			case "F":
				resData=data.otherOrderHisList;
				return resData ;
			case "G":
				resData=data.ownerRepairOrderHisList;
				return resData ;
			case "H":
				resData=data.repairOrderHisList;
				return resData ;
			case "I":
				resData=data.routineCleaningOrderHisList;
				return resData ;
			default:
				return resData ;
			}
    },
    typeAddress:function(aData){
    	var resData="";
    	switch (aData.type) {
			case "B":
			//	resData=data.cleaningOrderHisList;//保洁
				resData=orderList.ajaxAgree(aData.rental_lease_order_id ,"/rankHouse/loadAgreementInfo.do");
				break;
			case "G":
				//resData=data.ownerRepairOrderHisList;业主预约
				resData=orderList.ajaxAgree(aData.take_house_order_id ,"/agreementMge/agreementInfo.do");
				break;
			case "H":
				//resData=data.repairOrderHisList;//维修
				resData=orderList.ajaxAgree(aData.rental_lease_order_id ,"/rankHouse/loadAgreementInfo.do");
				break;
			case "I":
				//resData=data.routineCleaningOrderHisList;//例行保洁
				resData=orderList.ajaxAgree(aData.rental_lease_order_id ,"/rankHouse/loadAgreementInfo.do");
				break;
			default:
				break;
			}
    	return resData ;
    },
	ajaxAgree : function(id, url) {
		var address = "";
		common.ajax({
			url : common.root + url,
			data : {id : id},
			dataType : 'json',
			async : false,
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if ( data.address != null &&  data.address != "") {
						address = data.address;
					} 
				}
			}
		});
		return address;
	},
  //新增弹出层
	addButtype:function(type,orderName)
	{
		var url='/html/pages/order_flow/order_newAction/Order_'+type+'_Add.html';
		var title =orderName;
		var name="orderadd"+type;
		 common.openWindow({
	            type: 3,
	            name:name,
	            title: title,
	            url: url
	        });
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
};
$(function() {
    orderList.init();
});
var rowdata=null;
var resFlag=false;