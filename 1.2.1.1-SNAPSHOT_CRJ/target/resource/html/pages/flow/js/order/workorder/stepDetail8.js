var stepDetail2={
	init:function(){
	//初始化管家选项
	stepDetail2.initData();
	//添加处理事件
	stepDetail2.addevent();
	$("#changeDiv").show();
	$("#changeDiv3").show();
	$("#changeDiv2").hide();
	$("#changeDiv4").hide();
	
	//初始化图片上传
	common.dropzone.init({
		id : '#upurl',
		maxFiles:10
	});
	},
	initData : function() 
	{
	/**
     * 加载维修类型
     * */
    common.loadItem('ORDERSERVICE.TYPE', function(json)
    {
    	var dataId = $('#childtype').attr('dataId');
        var html = "";
        for (var i = 0; i < json.length; i++) 
        {
 		   html += '<option value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
        }
        $('#childtype').append(html);
        $('#childtype').val(dataId);
    });
	common.ajax({
		url : common.root + '/sys/getMangerList.do',
		dataType : 'json',
		data:{role_Id:"28"},
		async : false,
		loadfun : function(isloadsucc, json) 
		{
		  if (isloadsucc)
		  {
			var html = "请选择...";
			for ( var i = 0; i < json.length; i++) 
			{
				html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
			}
			$('#account').append(html);
			$('#account').select2();
		  } 
		  else 
		  {
		   common.alert({msg : common.msg.error});
		  }
		}
	});
	// 时间按钮选择事件
	var nowTemp = new Date();
	$('#servicetime').daterangepicker(
	{
		singleDatePicker : true,
		format : 'YYYY-MM-DD HH:mm'
	}, function(start, end, label)
    {
	   console.log(start.toISOString(), end.toISOString(), label);
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
	
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	var msg = "";
	var flowremark = $('#flowremark').val();
	//派单
	if(state == 2 )
	{
	 common.alert({
        msg: "是否派单",
        confirm: true,
        fun: function(action)
        {
    	 if (action) 
    	 {
    		 //添加验证信息
    		 showDisposeFlowDetail.checkFun=function()
    		 {
    			return {account:$('#account').val()};
    		 };
    		 showDisposeFlowDetail.save(state, flowremark, "");
         }
        }
	  }); 
	}
	else if(state == 6 )
	{//编辑订单
	 common.alert({
        msg: "是否修改订单",
        confirm: true,
        fun: function(action)
        {
    	 if (action)
    	 {
    		 showDisposeFlowDetail.checkFun=function()
    		 { 
				var ocost=$("#ocost").val(); 
    			var oname=$("#oname").val(); 
			    var servicetime=$("#servicetime").val(); 
		        var odesc=$("#odesc").val(); 
		        var childtype=$("#childtype").val(); 
    			var reg=/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
    			
    			if (!reg.test(ocost))
    			{
    				common.alert({msg:"请正确填费用信息！"});
					return;
				}
		         //读取图片路径
		     	var filepath = common.dropzone.getFiles('#upurl');
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
    			if (oname!=null&&oname!=""&&servicetime!=null&&servicetime!=""&&odesc.length<256&&childtype!=null&&childtype!="")
    			{ 
			       if (odesc==null||odesc=="") 
			       {
			    	   odesc="";
				   }
			       return {oname:oname,servicetime:servicetime,odesc:odesc,ocost:ocost,upurl:pathe,childtype:childtype};
				}
    			else
    			{
					common.alert({msg:"请检查修改信息是否正确！"});
					return;
				}
		     };
		     showDisposeFlowDetail.save(state, flowremark, "");
		     
		     
		     
		     
		     
		     
		     
		 }
        }
	  }); 
	}
	else if(state == 8 )
	{//关闭
		common.alert({
            msg: "是否关闭订单",
            confirm: true,
            fun: function(action)
            {
            	 if (action) {showDisposeFlowDetail.save(state, flowremark, "");}
            }
	  }); 
	}
   }
};
$(function(){
	stepDetail2.init();
});