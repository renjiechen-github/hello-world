var showDisposeFlowDetail = {
	/**
	 * 提供下方进行验证处理，返回false就不进行提交，返回json数据就提交 json数据时name与val的值
	 */
	checkFun:'',
	/**
	 * 初始化数据信息
	 */
	init:function(){
		this.loadEvent();
	},
	
	loadEvent:function(){
		$('#flowStepState').change(function(){
			var val = $(this).val();
			if(val == '2'){
				$('.next_div').removeClass('hide');
			}else{
				$('.next_div').addClass('hide');
			}
		});
	},
	/**
	 * 显示详细信息
	 */
	detail:function(obj,step_id,cfg_step_id){
		if($('#hide_flow_step_'+step_id).hasClass('hide')){
			$('#hide_flow_step_'+step_id).removeClass('hide');
			//进行显示信息，
			/*common.ajax({
				url: common.root + '/flow/showStepDetail.do',
				data:{step_id:step_id,
					cfg_step_id:cfg_step_id
				},
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
			});*/
		}else{
			$('#hide_flow_step_'+step_id).addClass('hide');
		}
	},
	/**
	 * 保存操作
	 * @param flowStepState 流程处理状态信息
	 * @param flowremark 流程处理备注信息 将保存到表yc_task_step_info_tab中
	 * @param flowfile 流程需要处理上传的附件信息 将保存到表yc_task_step_info_tab中
	 */
	save:function(flowStepState,flowremark,flowfile){
		var paramsObj = {};
		if(typeof(showDisposeFlowDetail.checkFun) == 'function'){
			var json = showDisposeFlowDetail.checkFun();
			if(typeof(json) == 'string'){
				common.alert(json);
				return false;
			}else if(typeof(json) != 'object'){//可能是json对象
				common.alert('调用类型错误，请联系管理员谢谢！');
				return false;
			} else{
				paramsObj = jQuery.extend(paramsObj,json);
			}
		}
//		paramsObj.flowStepState = $('#flowStepState').val();
		paramsObj.flowStepState = flowStepState;
		paramsObj.flowRemark = flowremark;
		paramsObj.flowfile = flowfile;
//		paramsObj.flowRemark = $('#flowremark').val();
		paramsObj.task_id = $('#task_id').val();
		paramsObj.task_code = $('#task_code').val();
		paramsObj.step_id = $('#step_id').val();
		paramsObj.cfg_step_id = $('#cfg_step_id').val();
		paramsObj.task_cfg_id = $('#task_cfg_id').val();
		if(paramsObj.flowStepState == ''){
			common.alert('请选择处理状态');
			return;
		}
		common.load.load('正在提交');
		common.ajax({
			url: common.root + '/flow/saveDisposeFlow.do',
			data:paramsObj,
		    dataType: 'json',
		    loadfun: function(isloadsucc, data){
		    	common.load.hide();
		    	if(isloadsucc){
		    		if(data.state == 1){
		    			common.alert({
		    				msg:'操作成功！',
		    				fun:function(){
		    					common.closeWindow('showDisposeFlowDetail', 3);
		    				}
		    			});
		    		}else{
		    			if(data.msg != undefined && data.msg != ''){
		    				common.alert(data.msg);
		    			}else{
		    				common.alert(common.msg.error);
		    			}
		    		}
		    	}else{
		    		common.alert(common.msg.error);
		    	}
		    }
		});
	}
};
$(function(){
	showDisposeFlowDetail.init();
});