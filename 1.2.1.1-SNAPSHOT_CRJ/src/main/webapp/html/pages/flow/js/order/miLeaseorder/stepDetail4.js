var stepDetail3={
	init:function()
	{
	var type=$("#type").val();
	//添加处理事件
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {          
	if(state == 2 )
	{
	   var msg="是否完成";
	   window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,
	   {
		   title:'提示',onOk:function()
	   {
		   var flowremark = $('#flowremark').val();
	       showDisposeFlowDetail.save(state, flowremark, "");
       },onCancel:function()
	   {
			
	   }
	   });
	}
  }
};
$(stepDetail3.init());