var yetDisposeDitail = {
	/**
	 * 初始化数据信息
	 */
	init:function(){
		
	},
	/**
	 * 显示详细信息
	 */
	detail:function(obj,step_id){
		if($('#hide_flow_step_'+step_id).hasClass('hide')){
			//进行显示信息，
			common.ajax({
				url: common.root + '/flow/showStepDetail.do',
				data:{step_id:step_id},
			    dataType: 'html',
			    loadfun: function(isloadsucc, data){
			    	common.log(data);
			    	if(isloadsucc){
			    		$('#hide_flow_step_'+step_id+' #showFlowDetail_'+step_id).html(data);
			    		$('#hide_flow_step_'+step_id).removeClass('hide');
			    	}else{
			    		common.alert(common.msg.error);
			    	}
			    }
			});
		}else{
			$('#hide_flow_step_'+step_id).addClass('hide');
		}
	}
};
$(function(){
	yetDisposeDitail.init();
});