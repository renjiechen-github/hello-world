var datacube={
	init:function(){
		var data = common.getWindowsData("coupondatacube");
		var nowTemp = new Date();
		$('#card_name').text(data.title);
		$('#card_id').text(data.card_id);
		$('#time').daterangepicker({
			startDate:nowTemp.getFullYear()+'-'+(nowTemp.getMonth()+1)+'-01',
			maxDate:nowTemp.getFullYear()+'-'+(nowTemp.getMonth()+1)+nowTemp.getDate(),
			timePicker12Hour:false,
	        timePicker: true,
			separator:'至',
			//分钟间隔时间
	        timePickerIncrement: 10,
	        format: 'YYYY-MM-DD',
	        dateLimit : {  
	            days : 60  
	        }, //起止时间的最大间隔 
	      }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	      });
		//初始化表格信息
		table.init({
   			id:'#couponDataTable',
   			expname:'卡券统计信息',
   			url:common.root+'/coupon/datacube.do',
   			columns:["ref_date",
   			         "view_cnt",
   			      	"view_user",
   			      	"receive_cnt",
   			     	"receive_user",
		   			"verify_cnt",
		   			"verify_user",
		   			"given_cnt",
		   			"given_user",
		   			"expire_cnt",
		   			"expire_user",
		   			
		   			"view_friends_cnt",
		   			"view_friends_user",
		   			"receive_friends_cnt",
		   			"receive_friends_user",
		   			"verify_friends_cnt",
		   			"verify_friends_user"],
		   	"aLengthMenu":[5,10, 25, 50,100,200,500,1000],
   			param:function(){
   				var a = new Array(); 
   				a.push({ "name": "time", "value": $('#time').val()});
   				a.push({ "name": "card_id", "value": data.card_id});
   				return a;
   			}
   		});
	},
	/**
	 * 同步卡券信息
	 */
	getdatacube:function(){
		if($('#time').val() == ''){
			layer.tips('请选择需要同步的时间', $('#time'), {
                tips: [1, '#d9534f'] //还可配置颜色
            });
			return ;
		}
		common.load.load('同步卡券统计信息中');
		var data = common.getWindowsData("coupondatacube");
		common.ajax({
 			 url:common.root+'/coupon/synDataCube.do',
 			 data:{
 				 id:data.id,
 				 card_id:data.card_id,
 				 time:$('#time').val()
 			 },
 			 dataType:'json',
 			 loadfun:function(a,b){
 				common.load.hide();
 				 if(a){
 					if(b.state == 1){//同步成功
 						common.alert('同步成功');
 						table.refresh('couponDataTable');
 					} else {//同步失败
 						
 						common.alert('操作失败，请重新操作，如还是失败请联系管理员，谢谢！');
 					}
 				 }else{
 					 common.alert(common.msg.error);
 				 }
 			 }
 		 });
	}
};
 
$(function(){
	datacube.init();
	//coupon.addCoupon();
});