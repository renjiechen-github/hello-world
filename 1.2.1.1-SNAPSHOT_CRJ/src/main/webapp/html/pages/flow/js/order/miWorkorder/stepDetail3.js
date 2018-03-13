var stepDetail4={
	init:function(){
	$("#changeDiv").show();
	$("#changeDiv3").show();
	$("#changeDiv2").hide();
	$("#changeDiv4").hide();
	//添加处理事件
	stepDetail4.addevent();
	 var type=$("#type").val();
		switch (type)
		{
		case "0":
			$("#childhide1").hide();
			$("#pichide1").hide();
			$("#pichides").hide();
			$('#ocost').attr("readonly",true);
			break;
		case "1":
			stepDetail4. initData("ORDERSERVICE.TYPE");
			stepDetail4.picInit();
			break;
		case "2":
			stepDetail4. initData("ORDERCLEAN.TYPE");
			stepDetail4.picInit();
			break;
		case "3":
			$('#ocost').attr("readonly",true);
			$("#pichides").hide();
			$("#pichide").hide();
			$("#childhide1").hide();
			break;
		case "5":
			$('#ocost').attr("readonly",true);
			stepDetail4. initData("ORDERSERVICE.TYPE");
			stepDetail4.picInit();
			break;
		case "4":
			$('#ocost').attr("readonly",true);
			$("#pichides").hide();
			$("#pichide").hide();
			$("#childhide1").hide();
			break;
		case "6":
			$('#ocost').attr("readonly",true);
			$("#childhide1").hide();
			stepDetail4.picInit();
			break;
		case "9":
			$("#childhide1").hide();
			stepDetail4.picInit();
			break;
		}
	},
	picInit:function(){
		//初始化图片上传
		 var picurl = $('#hideurl').val();
		 if (picurl!=null&&picurl!="") 
		 {
			 picurl=picurl.replace(/,/g,"|");
			 picurl=picurl.substring(0,picurl.lastIndexOf("|")); 
		 }
		 njyc.phone.showPic(picurl,"fileUrl3");
		 njyc.phone.showPic("","fileUrl5");
	},
	initData : function(type) 
	{
	 /**
     * 加载维修类型
     * */
	    njyc.phone.loadItem(type, function(json)
	    {
	        var dataId = $('#childtype2').val();
	        var html="<option value=''>请选择..</option>"
	        for (var i = 0; i < json.length; i++) 
	        {
	        	html  +="<option value="+json[i].item_id+">"+json[i].item_name+"</option>"
	        }
	        $('#childtype3')[0].innerHTML=html;
	        $('#childtype3').val(dataId);
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
		 // 初始化日期
		var currYear = (new Date()).getFullYear();	
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.default = {
			theme: 'android-ics light', // 皮肤样式
			display: 'modal', // 显示方式
			mode: 'scroller', // 日期选择模式
			dateFormat: 'yyyy-mm-dd ',
			lang: 'zh',
			showNow: true,
			nowText: "今天",
			startYear: currYear - 50, // 开始年份
			endYear: currYear + 10 // 结束年份,
		};
		//$("#servicetime").mobiscroll($.extend(opt['date'], opt['default']));
	 	var optDateTime = $.extend(opt['datetime'], opt['default']);
	  	var optTime = $.extend(opt['time'], opt['default']);
		$("#servicetime").mobiscroll(optDateTime).datetime(optDateTime);
	    $("#servicetime").val($("#servicetime1").val());
	},
	selectImages:function(id)
	{
		var picSize = $("#picSize").val(); // 可以上传图片数量
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
	var flowremark = $('#flowremark').val();
	//派单
	if(state == 2 )
	{
		var msg="是否完成订单";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
			{
			var type=$("#type").val();
			if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
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
						 showDisposeFlowDetail.checkFun=function()
			    		 { 
				    			 return {upurl:picImage};
			    		 }
					}	
	    	}
			 showDisposeFlowDetail.save(state, flowremark, "");
			},onCancel:function()
			{
				
			}
		});
	}
	else if(state == 6 )
	{
		//编辑订单
		var msg="是否修改订单";
		window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		{
   		 showDisposeFlowDetail.checkFun=function()
   		 { 
		     var oname=$("#oname").val(); 
		     var servicetime=$("#servicetime").val(); 
	         var odesc=$("#odesc").val(); 
	         var childtype=$("#childtype3").val(); 
    		 var reg=/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
    		 var ocost=$("#ocost").val(); 
    		 var picImage=""; //$("picImage")
			 var retrunI=false;
			 if (!reg.test(ocost))
				{
				    showDisposeFlowDetail.getRes(5);
					njyc.phone.showShortMessage('请正确填费用信息！');
					return;
				}
		 //判断订单类型维修、保洁、入住问题有上传图片，维修保洁会有相关费用的判断
	    	var type=$("#type").val();
	    	if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
	    	{
	    		
	    		$("#fileUrl3 input[name='picImage']").each(function()
			    {
				     picImage +=$(this).val()+",";  
				     retrunI=true;
			    }); 
			    if (retrunI)
			    {
				    picImage = picImage.substring(0, picImage.length - 1);
			    }
			    if (type=="6"||type=="9") {
		    		childtype="0";
				}
		        if (oname!=null&&oname!=""&&servicetime!=null&&servicetime!=""&&odesc.length<256&&childtype!=null&&childtype!="")
			    { 
			        if (odesc==null||odesc=="") 
		            {
		    	     odesc="";
			        }
		            return {oname:oname,servicetime:servicetime,odesc:odesc,ocost:ocost,upurl:picImage,childtype:childtype};
		    	}else
   			    {
		    		 showDisposeFlowDetail.getRes(6);
		    		njyc.phone.showShortMessage('请检查修改信息是否正确！');
					return;
				}
	    	}
	    	else
	    	{
	    		if (oname!=null&&oname!=""&&servicetime!=null&&servicetime!=""&&odesc.length<256)
			    { 
			        if (odesc==null||odesc=="") 
		            {
		    	     odesc="";
			        }
		            return {oname:oname,servicetime:servicetime,odesc:odesc};
		    	}
	    		else
   			    {
	    			 showDisposeFlowDetail.getRes(6);
		    		njyc.phone.showShortMessage('请检查修改信息是否正确！');
					return;
				}
			}
	     }
		   showDisposeFlowDetail.save(state, "", "");
		  },onCancel:function()
		  {
		  }
		});
	}
   }
};
$(stepDetail4.init());