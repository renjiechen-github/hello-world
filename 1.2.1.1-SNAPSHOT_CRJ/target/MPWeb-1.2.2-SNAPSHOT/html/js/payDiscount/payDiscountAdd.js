var addPayDiscount={
		init:function(){
			
		},
		save:function(){
			var phone = $('#phone').val();
			if(phone.length!=11){
				common.alert('请输入11位手机号码');
				return;
			}
			var cost = $('#cost').val();
			var code = $('#code').val();
			var num = $('#num').val();
			common.load.load('提交中');
			common.ajax({
				url:common.root+'/payDiscount/addPayDiscount.do',
	 			 data:{
	 				phone:phone, 
	 				code:code,
	 				cost:cost,
	 				num:num
	 			 },
	 			 dataType:'json',
	 			 loadfun:function(isSuc,data){
	 				common.load.hide();
	 				if(isSuc){
	 					if(data.status!=0){
	 						if(data.status==-1){
	 							common.alert('该优惠信息已存在');
	 						}else if(data.status==-2){
	 							common.alert('未查询到合约编号');
	 						}else{
	 							common.alert({
		 							msg:'保存成功',
		 							fun:function(){
		 								common.closeWindow("payDiscountAdd", 3);
		 							}
		 						});
	 						}
	 					}else{
		 					 common.alert("保存失败,请重试");
		 				 }
	 				}else{
	 					 common.alert(common.msg.error);
	 				 }
	 			 }
			});
		}
};
$(function(){
	addPayDiscount.init();
});