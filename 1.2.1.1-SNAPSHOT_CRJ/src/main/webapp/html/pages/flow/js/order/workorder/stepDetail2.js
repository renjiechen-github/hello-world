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
		break;
	case "1":
		role="26,28";
		break;
	case"2":
		role="26,28";
		break;
	case "3":
		role="27";
		break;
	case "4":
		role="27";
		break;
	case "5":
		role="26,28,27";
		break;
	case "6":
		role="26,28";
		break;
	case "7":
		role="22";
		break;
	case "9":
		role="26,28";
		break;
	}
	getMangerList(role,$("#account"),2);
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
	 common.alert({
        msg: "是否派单",
        confirm: true,
        fun: function(action)
        {
    	 if (action) 
    	 {
    		 var flowremark = $('#flowremark1').val();
    		 if ($('#account').val()!=null&&$('#account').val()!="")
			 {
    			 //添加验证信息
        		 showDisposeFlowDetail.checkFun=function()
        		 {
        			 return {account:$('#account').val()};
        		 };
        		 showDisposeFlowDetail.save(state, flowremark, "");
		     }
    		 else
    		 {
				  common.alert({msg:"请选择指派人！"});
				  return;
			 } 
         }
        }
	  }); 
	}
	else if(state == 2 )
	{ 
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