var stepDetail5={
	init:function(){
		//添加处理事件
		stepDetail5.addevent();
		$("#changeDiv").show();
		$("#changeDiv3").show();
		$("#changeDiv2").hide();
		$("#changeDiv4").hide();
		var type=$("#type").val();
	 	var role="";
		switch (type) {
		case "0":
			role="22";
			$("#pichides").hide();
			$("#pic1hide").hide();
			stepDetail5.initData(role);
			break;
		case "1":
			role="26,28";
			stepDetail5.initData(role);
			break;
		case"2":
			role="26,28";
			stepDetail5.initData(role);
			break;
		case "3":
			role="27";
			$("#pichides").hide();
			$("#pic1hide").hide();
			stepDetail5.initData(role);
			break;
		case "4":
			role="27";
			$("#pichides").hide();
			$("#pic1hide").hide();
			stepDetail5.initData(role);
			break;
		case "5":
			role="26,28,27";
			stepDetail5.initData(role);
			break;
		case "6":
			role="26,28";
			stepDetail5.initData(role);
			break;
		case "9":
			role="26,28";
			stepDetail5.initData(role);
			break;
		}
	},
	initData : function(role) 
	{ 
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + 'mobile/task/getMangerList.do',
			data:{role_Id:role},
			dataType:'json',
			async : false,
			loadfun : function(isloadsucc, json) 
			{
			  if (isloadsucc)
			  {
				var html = "<option value=''> 请选择...</option>";
				for ( var i = 0; i < json.length; i++) 
				{
					html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
				}
				$('#account')[0].innerHTML=html;
			  } 
			  else 
			  {
			  }
			}
		});
	},
	addevent : function() 
	{
		$("#edit").click(function(){
			$("#changeDiv").hide();
			$("#changeDiv3").hide();
			$("#changeDiv2").show();
			$("#changeDiv4").show();
		});
		$("#back").click(function(){
			$("#changeDiv").show();
			$("#changeDiv3").show();
			$("#changeDiv2").hide();
			$("#changeDiv4").hide();
		});
	},
	selectImages:function(id)
	{
		var picSize = 10; // 可以上传图片数量
		var uploadPic = $('#'+id+' input[name="picImage"]').size();
		if(Math.abs(picSize) > uploadPic)
		{
			njyc.phone.selectImage((Math.abs(picSize) - uploadPic),id);
		}
		else
		{
			$('#'+id).hide();
		}	
		return false;
	},
	/**
	 * 保存操作
	 */
 save:function(state)
 {   
		var msg = "";
		//派单
		if(state == 2 )
		{
			var msg="回访成功？";
			window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
				{ //添加验证信息
				 var type=$("#type").val();
    	    	 if (type=="1"||type=="2"||type=="6"||type=="5")
 		    	 {
    	    		 var picImage=""; //$("picImage")
 				    var retrunI=false;
 					$("#fileUrl3 input[name='picImage']").each(function()
 					 {
 						picImage +=$(this).val()+",";  
 						retrunI=true;
 					 }); 
 					if (retrunI) {
 						picImage = picImage.substring(0, picImage.length - 1);
 						 showDisposeFlowDetail.checkFun=function()
 	    	    		 {
 	    		    			 return {upurl:picImage};
 	    	    		 };
 					}
 		    	 }
			 var flowremark = $('#flowremark').val();
    		 showDisposeFlowDetail.save(state, flowremark, "");
			 },onCancel:function(){
					
				}
			});
		}
		else if (state == 3 ) 
		{
			var msg="是否派单";
			window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
			{
				 if ($('#account').val()!=""&&$('#account').val()!="")
 	 			 {
 	 		     }
 	 		     else
 	 		     {
 	 		    	 showDisposeFlowDetail.getRes(4);
 	 		         njyc.phone.showShortMessage('请选择指派人！');
 	 			     return;
 	 			 }
				 var type=$("#type").val();
				 if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
 		    	 { 
    	    		 showDisposeFlowDetail.checkFun=function()
  		   		      {
    	    			  var picImage=""; //$("picImage")
      				      var retrunI=false;
      					  $("#fileUrl5 input[name='picImage']").each(function()
      					  {
      						picImage +=$(this).val()+",";  
      						retrunI=true;
      					  }); 
      					  if (retrunI)
      					  {
      					    picImage = picImage.substring(0, picImage.length - 1);
      					    return {upurl:picImage,account:$('#account').val()};
      					  }
      					  else
  						  {
      					 	 return {account:$('#account').val()};
  						  }
	    		       };
 		    	 }
    	    	 else
    	    	 {
    	    		 showDisposeFlowDetail.checkFun=function()
 		   		      {
     					 return {account:$('#account').val()};
	    		      };
    	    	 }
				var flowremark = $('#flowremark1').val();
	   		    //添加验证信息
	   		    showDisposeFlowDetail.save(state, flowremark, "");
			},onCancel:function()
			{
			}
			});
		}if(state == 4 )
		{
			var msg="是否回访跟进?";
			window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
			{
				 var type=$("#type").val();
				 if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
 		    	 { 
    	    		    var picImage=""; //$("picImage")
    				    var retrunI=false;
    					$("#fileUrl3 input[name='picImage']").each(function()
    					 {
    						picImage +=$(this).val()+",";  
    						retrunI=true;
    					 }); 
    					if (retrunI)
    					{
    						 picImage = picImage.substring(0, picImage.length - 1);
    						 showDisposeFlowDetail.checkFun=function()
    			    		 {
    				    			 return {upurl:picImage};
    			    		 };
    					}
 		    	  }
				//添加验证信息
				var flowremark = $('#flowremark').val();
    		    showDisposeFlowDetail.save(state, flowremark, "");
			 },onCancel:function()
			 {
					
			 }
			});
		}
	   }
};
$(stepDetail5.init());