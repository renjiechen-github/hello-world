var stepDetail3={
	init:function()
	{
	//初始化管家选项
	stepDetail3.initData();
	//添加处理事件
	stepDetail3.addevent();
	},
	initData : function(role) 
	{
	},
	addevent : function() 
	{
		
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	var msg = "";
	//派单
	if(state == 2 )
	{//编辑订单
	  common.alert({
        msg: "是否核对完成",
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