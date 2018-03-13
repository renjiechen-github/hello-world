 var rankid="";
var dispatch = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	dispatch.initType();
		// 添加处理事件
    	dispatch.addEvent();
	},
   addEvent: function(){
        $('#orderadd_bnt').click(function()
        {
        	dispatch.save();
        });
    },
    /**
     加载区域选择
     **/
    initType: function(){
    	var role ="";
    	switch (rowdata.type) {
			case "A": // 退租
				role="29"; // 售后
				break;
			case "D": // 看房
				role="22"; // 市场管家
				break;
			case "C": // 投诉
				role="27,29";// 客服,售后
				break;
			case "B": // 保洁
				role="26,28"; // 供应商
				break;
			case "E": // 入住问题 
				role="26,28,27"; // 工程管家,供应商,客服
				break;
			case "F": // 其他订单
				role="26,28,27"; // 工程管家,供应商,客服
				break;
			case "G": // 业主维修
				role="26,28,27,29"; // 工程管家,供应商,客服,售后
				break;
			case "H": // 维修订单
				role="26,28,29"; // 工程管家,供应商
				break;
			case "I":// 例行保洁
				role="26,28"; // 工程管家,供应商
				break;
			default:
				break;
    	}
    	var rentalLeaseOrderId = rowdata.rental_lease_order_id
    	if (rentalLeaseOrderId == undefined || rentalLeaseOrderId == 'undefined') {
    		rentalLeaseOrderId = "";
    	}
    	common.ajax({
    		url : common.root + '/cascading/getUserListByAuthority.do',
    		dataType : 'json',
    		data:{roleId:role,type:rowdata.type},
    		async : false,
    		loadfun : function(isloadsucc, json) 
    		{
    		  if (isloadsucc)
    		  {
    			var html = "请选择...";
    			for ( var i = 0; i < json.length; i++) 
    			{
    				html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
    			}
    			$('#account').append(html);
    			$('#account').select2();
    		  } 
    		  else 
    		  {
    		    common.alert({msg : common.msg.error});
    		  }
    		}
    	});
    },
   save:function(){
	   if (!Validator.Validate('form2'))
	   {
        	return;
       }
	   $("#orderadd_bnt").attr("disabled", true);
	   var param = {'workOrderId': rowdata.id,staffId:$('#account').val(),comments:$('#odesc').val()};	
       common.ajax({
				url : common.root + '/workOrder/assignOrder.do',
				dataType : 'json',
	            contentType: 'application/json; charset=utf-8',
	            encode: false,
	            data : JSON.stringify(param),
				loadfun : function(isloadsucc, data)
				{
					if (isloadsucc)
					{
						common.alert({msg : "操作成功！"});
						common.closeWindow('dispatch',1);
						table.refreshRedraw('workOrderTable');
					}else{
						  $("#orderadd_bnt").attr("disabled", false);
						common.alert({msg : common.msg.error});
					}
				}
	    	});
	},
};
dispatch.init();