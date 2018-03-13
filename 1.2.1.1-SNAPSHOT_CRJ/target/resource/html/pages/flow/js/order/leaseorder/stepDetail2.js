var stepDetail3={
	init:function(){
	//添加处理事件
	stepDetail3.addevent();
	$("#changeDiv").show();
	$("#changeDiv3").show();
	$("#changeDiv2").hide();
	$("#changeDiv4").hide();
	var type=$("#type").val();
	var role="";
	switch (type) {
	case "0":
		role="22";
		stepDetail3.initData(role);
		break;
	case "1":
		role="26,28";
		stepDetail3.initData(role);
		break;
	case"2":
		role="26,28";
		stepDetail3.initData(role);
		break;
	case "3":
		role="27";
		stepDetail3.initData(role);
		break;
	case "4":
		role="27";
		stepDetail3.initData(role);
		break;
	case "6":
		role="26,28";
		stepDetail3.initData(role);
		break;
	case "7":
		role="22";
		stepDetail3.initData(role);
		break;
	}
	},
	initData : function(role) 
	{
	common.ajax({
		url : common.root + '/sys/getMangerList.do',
		dataType : 'json',
		data:{role_Id:role},
		async : false,
		loadfun : function(isloadsucc, json) 
		{
		  if (isloadsucc)
		  {
			var html = "<option value=''> 请选择...</option>";
			for ( var i = 0; i < json.length; i++) 
			{
				html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
			}
			$('#account').html(html);
			$('#account').select2();
		  } 
		  else 
		  {
		   common.alert({msg : common.msg.error});
		  }
		}
	});
	// 时间按钮选择事件
	var nowTemp = new Date();
	$('#servicetime').daterangepicker(
	{
		singleDatePicker : true,
		format : 'YYYY-MM-DD HH:mm'
	}, function(start, end, label)
	{
				console.log(start.toISOString(), end.toISOString(), label);
	});
	},
	addevent : function() 
	{
		$("#edit").click(function()
		{
			$("#changeDiv").hide();
			$("#changeDiv3").hide();
			$("#changeDiv2").show();
			$("#changeDiv4").show();
		});
		$("#back").click(function()
		{
			$("#changeDiv").show();
			$("#changeDiv3").show();
			$("#changeDiv2").hide();
			$("#changeDiv4").hide();
		});
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	var msg = "";
	//派单
	if(state == 3 )
	{
	 if ($('#account').val()!=null&&$('#account').val()!="")
	 {
		
     }else{
		common.alert({msg:"请选择指派人！"});
		return;
	 }
	 common.alert({
        msg: "是否派单",
        confirm: true,
        fun: function(action)
        {
    	 if (action) 
    	 {
    		 var flowremark = $('#flowremark1').val();
    		 //添加验证信息
    		 showDisposeFlowDetail.checkFun=function()
    		 {
    			 if ($('#account').val()!=null&&$('#account').val()!="")
    			 {
    				 return {account:$('#account').val()};
			     }else{
					common.alert({msg:"请选择指派人！"});
					return;
				 }
    		 };
    		 showDisposeFlowDetail.save(state, flowremark, "");
         }
        }
	  }); 
	}
	else if(state == 2 )
	{//编辑订单
	  common.alert({
        msg: "是否接单",
        confirm: true,
        fun: function(action)
        {
    	    if (action)
    	    {
    		 var flowremark = $('#flowremark').val();
		     showDisposeFlowDetail.save(state, flowremark, "");
		    }
        }
	  }); 
	}
   }
};
$(function(){
	stepDetail3.init();
});