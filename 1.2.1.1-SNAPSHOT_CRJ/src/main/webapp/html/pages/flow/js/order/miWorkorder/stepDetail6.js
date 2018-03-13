var stepDetail7={
	init:function(){
	},
	
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	var msg = "";
	var flowremark ="";
	 if (state == 2) 
	{
		var msg="是否撤销支付";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		{
   		    showDisposeFlowDetail.save(state, flowremark, "");
		},onCancel:function()
		{
				
		}
		});
	}
  }
};
$(stepDetail7.init());