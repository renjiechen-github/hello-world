var stepDetail3={
	init:function()
	{
	$("#changeDiv").show();
	$("#changeDiv3").show();
	$("#changeDiv2").hide();
	$("#changeDiv4").hide();
	var type=$("#type").val();
 	var role="";
	switch (type) {
	case "0":
		role="22";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "1":
		role="26,28";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case"2":
		role="26,28";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "3":
		role="27";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "4":
		role="27";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "5":
		role="26,28,27";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "6":
		role="26,28";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "7":
		role="22";
		//初始化管家选项
		stepDetail3.initData(role);
		break;
	case "9":
		role="26,28";
		stepDetail3.initData(role);
		break;
	}
	//添加处理事件
	stepDetail3.addevent();
	},
	initData : function(role) 
	{
		njyc.phone.ajax({
		url : njyc.phone.getSysInfo('serverPath') + 'mobile/task/getMangerList.do',
		data:{role_Id:role},
		dataType:'json',
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
			$('#account')[0].innerHTML=html;
		   } 
		   else 
		   {
		    //  common.alert({msg : common.msg.error});
		   }
		 }
	});
	},
	addevent : function() 
	{
		$("#edit").click(function(){
			$("#changeDiv").hide();
			$("#changeDiv3").hide();
			$("#changeDiv2").show();
			$("#changeDiv4").show();
		});
		$("#back").click(function(){
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
	//派单
	if(state == 3 )
	{
		var msg="是否派单";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,
		{
			title:'提示',onOk:function()
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
		    	showDisposeFlowDetail.getRes(4);
		        njyc.phone.showShortMessage('请选择指派人！');
			    return;
			}
		},onCancel:function()
		{
				
		}
		});
	}
	else if(state == 2 )
	{
		var msg="是否接单";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		{
			 var flowremark = $('#flowremark').val();
			 showDisposeFlowDetail.save(state, flowremark, "");
			},onCancel:function(){
				
			}
		});
	}
   }
};
$(stepDetail3.init());