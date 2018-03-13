var stepDetail4={
	init:function()
	{
	var cost = $('#cost').attr('dataId');
	if (cost>=0)
	{
		 $('#costtype').html("应收金额：");
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
	    common.alert({
           msg: "是否审批通过",
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
	{ 
		common.alert({
        msg: "是否拒绝",
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
	stepDetail4.init();
});