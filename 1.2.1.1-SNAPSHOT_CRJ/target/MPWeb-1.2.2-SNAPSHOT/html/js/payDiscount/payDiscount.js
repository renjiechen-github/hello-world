var payDiscount = {
		//页面初始化操作
	    init: function(){
	    	//创建表格
	    	payDiscount.creatTable();
	    },
	    creatTable:function(){
	    	table.init({
	    		id:'#payDiscount_table',
	    		url:common.root + '/payDiscount/queryPayCountList.do',
	    		columns: [
	    			"userName",//用户名
	    			"phone",//电话
	    			"code",//合约编码
	    			"name",//合约名称
	    			"cost",//费用
	    			"num"//次数
	    		],
	    		bnt:[
	    			{
	    				name:'修改',
	    				//控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
	                	isshow:function(){
	                		return true;
	                	},
	                	fun:function(data,row){
	                		 rowdata = data;
	                		 payDiscount.edit(rowdata);
	                	 }
	    			},
	    			{
	    				name:'删除',
	    				//控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
	                	isshow:function(data,row){
	                		return true;
	                	},
	                	fun:function(data,row){
	                		 rowdata = data;
	                		 payDiscount.del(data,row);
	                	 }
	    			}
	    		],
	    		isexp: false,
	    		param:function(){
	    			var params = new Array();
	    			params.push({"name":"userName","value":$("#userName").val()});
	    			params.push({"name":"phone","value":$("#phone").val()});
	    			params.push({"name":"code","value":$("#code").val()});
	    			return  params;
	    		}
	    	});
	    },
	    detaildata:null,
	    add:function(){
	    	//打开对于的窗口
	        common.openWindow({
	            type: 3,
	            name:'payDiscountAdd', 
	            title: '代缴费优惠新增',
	            url: '/html/pages/payDiscount/payDiscountAdd.html' 
	        });
	    },
	    edit:function(data){
	    	//打开对于的窗口
	        common.openWindow({
	            type: 3,
	            name:'payDiscountEdit', 
	            title: '代缴费优惠修改',
	            data:data,
	            url: '/html/pages/payDiscount/payDiscountEdit.html' 
	        });
	    },
	    del:function(data,row){
	    	common.alert({
	    		msg:"确认要删除吗?",
	    		confirm: true,
	    		fun: function(action){
	    			if (action) {
	    				common.ajax({
	    					 url: common.root + '/payDiscount/delPayDiscount.do',
	    					 data: {
	    						 phone:data.phone,
	    						 aId:data.aId
	    					 },
	    					 dataType: 'json',
	    					 loadfun: function(isloadsucc, data){
	    						 if(isloadsucc){
	    							 if(data.status!=0){
	    								 common.alert({
	                                         msg: '删除成功',
	                                         fun: function(){
	                                             table.refreshRedraw('payDiscount_table');
	                                         }
	                                     });
	    							 }else{
	    								 common.alert({
	                                         msg: '删除失败请重试'
	                                     });
	    							 }
	    						 }else{
	    							 common.alert({
	                                     msg: common.msg.error
	                                 });
	    						 }
	    					 }
	    				})
	    			}
	    		}
	    	});
	    }
};
$(function(){
	payDiscount.init();
});