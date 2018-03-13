var coupon={
	init:function(){
		//初始化字段信息
		common.autoLoadItem('COUPON.CODE_TYPE', 'code_type',true);
		common.autoLoadItem('COUPON.CARD_TYPE', 'card_type',true);
		common.autoLoadItem('COUPON.CARD_WX_STATE', 'wx_state',true);
		//初始化表格信息
		table.init({
   			id:'#couponTable',
   			expname:'卡券信息',
   			url:common.root+'/coupon/showCoupon.do',
   			columns:["card_id",
   			         "code_type_name",
   			      	"card_type_name",
   			      	"title",
   			     	"sku_quantity",
   			     	"sy_quantity",
		   			"can_share",
		   			"can_give_friend",
		   			"isnew_name",
		   			"rule_degree",
		   			"rule_can_use_with_other_discount",
		   			"lq_cnt",
		   			"rule_already_user_degree",
   			      	"state_name",
   			      	"wx_state_name",
   			      	"operdate"],
		   	"aLengthMenu":[5,10, 25, 50,100,200,500,1000],
   			param:function(){
   				var a = new Array(); 
   				a.push({ "name": "code_type", "value": $('#code_type').val()});
   				a.push({ "name": "card_type", "value": $('#card_type').val()});
   				a.push({ "name": "wx_state", "value": $('#wx_state').val()});
   				a.push({ "name": "status", "value": $('#status').val()});
   				a.push({ "name": "name", "value": $('#name').val()});
   				return a;
   			},
   			createRow:function(rowindex,colindex,name,value,data,row){
   				if(name == 'can_share'){
   					if(value == 1){
   						return "可分享";
   					}else{
   						return "不可分享";
   					}
   				}else if(name == 'can_give_friend'){
   					if(value == 1){
   						return "可转增";
   					}else{
   						return "不可转增";
   					}
   				}else if(name == 'rule_degree'){
   					if(value == 0){
   						return "合约";
   					}else if(value == '1'){
   						return "月份";
   					}else{
   						return "无";
   					}
   				}else if(name == 'rule_can_use_with_other_discount'){
   					if(value == 1){
   						return "可共享";
   					}else{
   						return "不可共享";
   					}
   				}
   				
   			},
   			bnt:[
				{
				   	name:'详细',
				   	isshow:function(data,row){
				  		 if(data.state == 0){
				  			 return false;
				  		 }
				  		 return true;
				  	},
				   	fun:function(data,row){
				   		common.openWindow({
				            type: 3,
				            name:'couponShowDetail',
				            title: '查看详细',
				            data:data,
				            url: '/html/pages/coupon/couponShow.html' 
				        });
				   	}
				},
   			     {
   			    	name:'修改',
   			    	isshow:function(data,row){
  			    		 if(data.state == 0){
  			    			 return false;
  			    		 }
  			    		 return true;
  			    	},
   			    	fun:function(data,row){
   			    		common.openWindow({
   			             type: 3,
   			             name:'couponModify',
   			             title: '修改卡券',
   			             data:data,
   			             url: '/html/pages/coupon/couponModify.html' 
   			         });
   			    	}
   			     },
   			     {
   			    	 name:'删除',
   			    	 isshow:function(data,row){
   			    		 if(data.state == 0){
   			    			 return false;
   			    		 }
   			    		 return true;
   			    	 },
   			    	 fun:function(data,row){
   			    		 common.alert({
   			    			 msg:'确定进行删除吗？',
   			    			 confirm:true,
   			    			 fun:function(action){
   			    				 if(action){
   			    					common.load.load('正在进行删除');
   			    					common.ajax({
	   		 			    			 url:common.root+'/coupon/deleteCoupon.do',
	   		 			    			 data:{
	   		 			    				 id:data.id,
	   		 			    				 card_id:data.card_id
	   		 			    			 },
	   		 			    			 dataType:'json',
	   		 			    			 loadfun:function(a,b){
	   		 			    				common.load.hide();
	   		 			    				 if(a){
	   		 			    					if(b.state == 1){//同步成功
	   		 			    						common.alert('删除成功');
	   		 			    						table.refresh('couponTable');
	   		 			    					}else if(b.state == -2){
	   		 			    					common.alert('无法获取该条卡券信息，请核实。');
	   		 			    					} else {//同步失败
	   		 			    						common.alert('删除失败，请联系后台确认。');
	   		 			    					}
	   		 			    				 }else{
	   		 			    					 common.alert(common.msg.error);
	   		 			    				 }
	   		 			    			 }
	   		 			    		 });
   			    				 }
   			    			 }
   			    		 });
   			    	 }
   			     },
   			     {
   			    	 name:'同步状态信息',
   			    	 isshow:function(data,row){
  			    		 if(data.card_id == ''||data.card_id == 'null'||data.card_id == null){
  			    			 return false;
  			    		 }
  			    		 return true;
  			    	 },
   			    	 fun:function(data,row){
   			    		 common.ajax({
   			    			 url:common.root+'/coupon/tbState.do',
   			    			 data:{
   			    				 id:data.id,
   			    				 card_id:data.card_id
   			    			 },
   			    			 dataType:'json',
   			    			 loadfun:function(a,b){
   			    				 if(a){
   			    					if(b.state == 1){//同步成功
   			    						common.alert('同步成功');
   			    						table.refresh('couponTable');
   			    					} else {//同步失败
   			    						common.alert(common.msg.error);
   			    					}
   			    				 }else{
   			    					 common.alert(common.msg.error);
   			    				 }
   			    			 }
   			    		 });
   			    	 }
   			     },
   			     {
   			    	 name:'获取二维码投放',
   			    	 isshow:function(data,row){
   			    		 if(data.wx_state == '3'||data.wx_state == '5'){
   			    			 return true;
   			    		 }
   			    		 return false;
   			    	 },
   			    	 fun:function(data,row){
   			    		 common.load.load('二维码生成中');
   			    		 if(data.qrcode != null && data.qrcode != ''){//已经存在对应的二维码信息，直接获取
   			    			common.load.hide('二维码生成成功');
   			    			common.openWindow({
	    							name:'showQcode',
	    							title:data.title+'  对应的卡券二维码',
	    							type:'1',
	    							content:'<div  style="height: 90%;text-align: center;" ><img style="height: 100%;" src="'+data.qrcode+'" ><br /></div>'
	    						});
   			    		 }else{//没有获取对应的二维码，到后台进行获取
   			    			common.ajax({
      			    			 url:common.root+'/coupon/qrcode.do',
      			    			 data:{
      			    				 id:data.id,
      			    				 card_id:data.card_id
      			    			 },
      			    			 dataType:'json',
      			    			 loadfun:function(a,b){
      			    				common.load.hide('二维码生成成功');
      			    				 if(a){
      			    					if(b.state == 1){//同步成功
      			    						table.refresh('couponTable');
      			    						common.openWindow({
      			    							name:'showQcode',
      			    							title:data.title+'  对应的卡券二维码',
      			    							type:'1',
      			    							content:'<div  style="height: 90%;text-align: center;" ><img style="height: 100%;" src="'+b.url+'" ><br /></div>'
      			    						});
      			    					} else {//同步失败
      			    						common.alert('二维码获取失败，请重新获取。');
      			    					}
      			    				 }else{
      			    					 common.alert(common.msg.error);
      			    				 }
      			    			 }
      			    		 });
   			    		 }
   			    	 }
   			     },
   			     {
   			    	 name:'统计数据信息',
   			    	 isshow:function(data,row){
   			    		 return true;
  			    	 },
  			    	 fun:function(data,row){
  			    		//common.load.load('数据统计中');
  			    		common.openWindow({
  	   			             type: 3,
  	   			             name:'coupondatacube',
  	   			             title: '卡券信息统计',
  	   			             data:data,
  	   			             url: '/html/pages/coupon/datacube.html' 
  	   			         });
  			    	 }
   			     }
   			]
   		});
	},
	/**
	 * 新增操作
	 */
	addCoupon:function(){
		common.openWindow({
            type: 3,
            name:'addCoupon',
            title: '新增卡券',
            url: '/html/pages/coupon/couponAdd.html' 
        });
	}
};
 
$(function(){
	coupon.init();
	//coupon.addCoupon();
});