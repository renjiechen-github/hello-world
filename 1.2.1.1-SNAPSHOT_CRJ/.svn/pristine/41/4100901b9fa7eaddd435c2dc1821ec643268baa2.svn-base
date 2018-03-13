var stepDetail7={
	init:function()
	{
		
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	var msg = "";
	var flowremark = $('#flowremark').val();
	 if (state == 2) 
	{
		common.alert({
	        msg: "是否撤销支付",
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
  }
};
$(function(){
	stepDetail7.init();
});