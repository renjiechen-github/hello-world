var stepDetail4={
	init:function()
	{
		var cost = $('#cost').attr('dataId');
		if (cost>=0)
		{
			 $('#costtype1').html("应收金额:");
		}else{
			 $('#cost').html(-cost+"￥")
		}
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {
	var flowremark = $('#flowremark').val();
	if(state == 2 )
	{
		var msg="是否审批通过？";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		{
			showDisposeFlowDetail.save(state, flowremark, "");
		},
		onCancel:function()
		{
				
		}
		});
	}//派单
	else if(state == 3 )
	{
		var msg="是否审批拒绝？";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		{
			 showDisposeFlowDetail.save(state, flowremark, "");
			},
			onCancel:function()
			{
				
			}
		});
	}
 }
};
$(stepDetail4.init());
