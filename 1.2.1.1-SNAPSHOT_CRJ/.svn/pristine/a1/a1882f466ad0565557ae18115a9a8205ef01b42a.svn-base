var stepDetail5={
	init:function(){
	//初始化管家选项
	stepDetail5.addevent();
	$("#changeDiv").show();
	$("#changeDiv3").show();
	$("#changeDiv2").hide();
	$("#changeDiv4").hide();
	var type=$("#type").val();
	var role="";
	var userPic=false;
	switch (type) 
	{
	case "0":
		role="22";
		//$("#pic1hide").hide();
		//$("#pichides").hide();
		break;
	case "1":
		role="26,28";
		userPic=true;
		break;
	case"2":
		role="26,28";
		userPic=true;
		break;
	case "3":
		role="27";
		break;
	case "4":
		role="27";
		break;
	case "5":
		role="26,28,27";
		userPic=true;
		//stepDetail5.initData(role);
		break;
	case "6":
		role="26,28";
		userPic=true;
		break;
	case "9":
		role="26,28";
		userPic=true;
		//stepDetail5.picInit();
		break;
	}
	if (userPic)
	{
		stepDetail5.picInit();
	}
	else
	{
		$("#pic1hide").hide();
		$("#pichides").hide();
	}
	getMangerList(role,$("#account"),2);
	},//初始化图片工具
	picInit: function() 
	{
		//初始化图片上传
		common.dropzone.init({
			id : '#upurls',
			maxFiles:10
		});
		//初始化图片上传
		common.dropzone.init({
			id : '#upurl',
			maxFiles:10
		});
	},//初始化管家信息
	addevent : function() 
	{
		$("#edit").click(function()
		{
			$("#changeDiv").hide();
			$("#changeDiv3").hide();
			$("#changeDiv2").show();
			$("#changeDiv4").show();
		});
		$("#back").click(function()
		{
			$("#changeDiv").show();
			$("#changeDiv3").show();
			$("#changeDiv2").hide();
			$("#changeDiv4").hide();
		});
	},
	/**
	 * 保存操作
	 */
save:function(state)
{                                                 
	if(state == 2 )
	{
	    common.alert({
           msg: "回访成功？",
           confirm: true,
           fun: function(action)
           {
    	      if (action) 
    	      {
    	    	  var flowremark = $('#flowremark').val();
    	    	  var type=$("#type").val();
    	    	  if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
 		    	  {
    	    		  var filepath = common.dropzone.getFiles('#upurls');
    	    		  if (filepath!=null&&filepath!="") 
    	    		  {
    	    			  //添加验证信息
    	        		  showDisposeFlowDetail.checkFun=function()
    	        		  {
    	        			   //读取图片路径
    	     		    	   var pathe = "";
    	     		    	   var returnI = false;
    	     		    	   for ( var i = 0; i < filepath.length; i++) 
    	     		    	   {
    	     		    		   if(filepath[i].fisrt==1)
    	     		    		   {
    	     		    			   pathe = filepath[i].path + ',' + pathe;
    	     		    		   }
    	     		    		   else
    	     		    		   {
    	     		    			   pathe += filepath[i].path + ",";
    	     		    		   }
    	     		    		   returnI = true;
    	     		    	   } 
    	     		    	   if (returnI)
    	     		    	   {
    	     		    		    pathe = pathe.substring(0, pathe.length - 1);
    	     		    		    return {upurl:pathe};
    	     		    	   }
    	        		    };
    				    }  
 		    	   }
	    		    showDisposeFlowDetail.save(state, flowremark, "");
    	         }
           }
	  }); 
	}
	else if (state == 3 ) 
	{
		 common.alert({
	        msg: "是否重新派单",
	        confirm: true,
	        fun: function(action)
	        {
	    	  if (action) 
	    	  {
	    		  if ($('#account').val()!=""&&$('#account').val()!="")
	 			 {
	 		     }else{
	 				common.alert({msg:"请选择指派人！"});
	 				return;
	 			 }
	    		     var flowremark = $('#flowremark1').val();
	      			 //添加验证信息
	          		 showDisposeFlowDetail.checkFun=function()
	          		 {
	          			 var type=$("#type").val();
	        	    	 if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
	     		    	 { 
	        	    		var filepath = common.dropzone.getFiles('#upurl');
	        	    		if (filepath!=null&&filepath!="") {
		          			   //读取图片路径
	 	       		    	var pathe = "";
	 	       		    	var returnI = false;
	 	       		    	for ( var i = 0; i < filepath.length; i++) 
	 	       		    	{
	 	       		    		if(filepath[i].fisrt==1)
	 	       		    		{
	 	       		    			pathe = filepath[i].path + ',' + pathe;
	 	       		    		}
	 	       		    		else
	 	       		    		{
	 	       		    			pathe += filepath[i].path + ",";
	 	       		    		}
	 	       		    		returnI = true;
	 	       		    	}
	 	       		    	if (returnI)
	 	       		    	{
	 	       		    		  pathe = pathe.substring(0, pathe.length - 1);
	 	       		    	}
	 	       		    	return {upurl:pathe,account:$("#account").val()};
	 	          			}
	 	          			 else
	 	          			{
	 	          				return {account:$("#account").val()};	
	 	          			} 
	     		    	 }else{
	     		    		return {account:$("#account").val()};	 
	     		    	 }
	          		 };
	      		 showDisposeFlowDetail.save(state, flowremark, "");
	           }
	        }
		  }); 
	}else if (state == 9 ) 
	{
	    common.alert({
	           msg: "是否回访跟进",
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
	stepDetail5.init();
});