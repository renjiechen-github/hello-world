var payDiscountModify={
		init:function(){
			//获取数据
			var data = common.getWindowsData("payDiscountEdit");
			//页面赋值
			$('#phone').val(data.phone);
			$('#code').val(data.code);
			$('#cost').val(data.cost);
			$('#num').val(data.num);
			$('#aId').val(data.aId);
		},
		save:function(){
			var phone = $('#phone').val();
			var code = $('#code').val();
			var cost = $('#cost').val();
			var num = $('#num').val();
			var aId = $('#aId').val();
			if(phone.length<1){
				common.alert({
					msg : '请输入手机号码'
				});
				return;
			}
			if(code.length<1){
				common.alert({
					msg : '请输入合约编号'
				});
				return;
			}
			if(num.code<1){
				common.alert({
					msg : '请输入使用次数'
				});
				return;
			}
			common.load.load('提交中');
			common.ajax({
				url:common.root+'/payDiscount/modifyPayDiscount.do',
	 			 data:{
	 				phone:phone, 
	 				code:code,
	 				cost:cost,
	 				num:num,
	 				aId:aId
	 			 },
	 			 dataType:'json',
	 			 loadfun:function(isSuc,data){
	 				common.load.hide();
	 				if(isSuc){
	 					if(data.status!=0){
	 						common.alert({
	 							msg:'保存成功',
	 							fun:function(){
	 								common.closeWindow("payDiscountEdit", 3);
	 							}
	 						});
	 					}else{
		 					 common.alert("保存失败,请重试");
		 				 }
	 				}else{
	 					 common.alert(common.msg.error);
	 				 }
	 			 }
			});
		}
}
$(function(){
	payDiscountModify.init();
});