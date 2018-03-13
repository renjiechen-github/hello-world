var stepDetailRec={
	
	init:function(){
	},
	/**
	 * 保存操作
	 */
	save:function(state,cfg_step_id){
		var msg = "";
		if(state == 2){
			msg = "是否确定审批通过？";
		}else{
			msg = "是否确定审批不通过？";
		}
		//第三部需要进行确定
		if(state == 2 && cfg_step_id == 3){
			//核对判断是否已经勾选核对信息
			var tablehtml = $('#tableclassfin').clone();
			var inp = $(tablehtml).find('input');
			$('#tableclassfin input').each(function(i,n){
				var truth = this.checked;
				var o = inp.eq(i).parent();
				if(truth){
					o.html('已核对');
				}else{
					o.html('未核对');
				}
			});
			var layer1 = layer.confirm("<div>"+tablehtml.html()+"</div>", {
				area:'850px',
				title:'请认真核对信息是否正确',
				  btn: ['确定','取消'] //按钮
				}, function(){
					layer.close(layer1);
					var flowremark = $('#flowremark').val();
					//添加验证信息
					showDisposeFlowDetail.checkFun=function(){
						var payable_tab_id = "";
						$("input[id^=payaler_]").each(function(){
							if(this.checked){
								var id = $(this).attr('id').replace('payaler_','');
								payable_tab_id += id+",";
							}
						});
						if(payable_tab_id != ''){
							payable_tab_id = payable_tab_id.substring(0, payable_tab_id.length-1);
						}
						return {payable_tab_id:payable_tab_id};
					};
					showDisposeFlowDetail.save(state, flowremark, "");
				}, function(){
				  
				});
			return;
		}
		common.alert({
			msg:msg,
			confirm:true,
			fun:function(action){
				if(action){
					var flowremark = $('#flowremark').val();
					showDisposeFlowDetail.save(state, flowremark, "");
				}
			}
		});
	}
};
$(function(){
	stepDetailRec.init();
});