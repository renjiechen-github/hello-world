var resons="";
var stepDetail4={
	init:function()
	{
		stepDetail4.initData();
		//stepDetail4.picInit();
	},
	initData:function()
	{
		var task_id=$("#task_check_id").val();
		njyc.phone.ajax(
	    {
			url : njyc.phone.getSysInfo('serverPath') + 'mobile/task/getInfo.do',
			data : {task_id:task_id},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					if (data.endelectricdegree==null||data.endelectricdegree=="") 
					{
						//stepDetail4.imageDeal("","degreepic3");
						//stepDetail4.imageDeal("","housepic3");
						return;
					}
					$("#alipay3").val(data.alipay);
					$("#bankcard3").val(data.bank);
					$("#bankname3").val(data.bankname);
					$("#endwaterdegree3").val(data.endwaterdegree);
					$("#endgasdegree3").val(data.endgasdegree);
					$("#endelectricdegree3").val(data.endelectricdegree);
					$("#startwaterdegree3").val(data.startwaterdegree);
					$("#startgasdegree3").val(data.startgasdegree);
					$("#startelectricdegree3").val(data.startelectricdegree);
					resons=data.reasons;
					$("#key3").val(data.keyrecieve);
					$("#doorcard3").val(data.doorcard);
					$("#otherdesc3").val(data.otherdesc);
					$("#checkdesc3").val(data.housedesc);
					$("#favourable3").val(data.favourable);
					stepDetail4.picInit(data.degreepic,"degreepic3");
					stepDetail4.picInit(data.housepic,"housepic3");
				}
			}
		 });
		 njyc.phone.loadItem("LEASEORDER.REASONS", function(json)
	     {
			 var html = "<option  value='' >请选择...</option>";
	         for (var i = 0; i < json.length; i++) 
	         {
	        	 html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
	         }
	         $('#reasons3').html(html);
	    	 if (resons!=null&&resons!="")
	    	 {
	    		 $('#reasons3').val(resons);
			 }
	    });
	},
	picInit:function(url,id)
	{
		 //初始化图片上传
		 var picurl = url;
		 if (picurl!=null&&picurl!="") 
		 {
			 picurl=picurl.replace(/,/g,"|");
		 }
		 njyc.phone.showPic(picurl,id);
	},selectImages:function(id)
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
	if(state == 2 )
	{
		 var alipay3,bankcard3,bankname3,degreepic3="",housepic3="";
		 alipay3=$('#alipay3').val();
		 bankcard3=$('#bankcard3').val();
		 bankname3=$('#bankname3').val();
		 //验证支付宝账号或银行卡号是否填写
		 if ((alipay3==null||alipay3=="")&&(bankcard3==null||bankcard3=="")) 
		 {
			 showDisposeFlowDetail.getRes2("请填写支付宝账号或银行卡号");
			 window.client.showShortMessage("请填写支付宝账号或银行卡号");
			 return;
		 }
		 //验证银行卡的开户银行是否填写
		 if (bankcard3!="")
		 {
			 var reg=/^\d+$/;
			 if (!reg.test(bankcard3))
			 {
				 showDisposeFlowDetail.getRes2("请填写正确的银行卡号");
				 window.client.showShortMessage("请填写正确的银行卡号");
				 return;
			 }
		 }
		 //验证银行卡的开户银行是否填写
		 if (bankcard3!=""&&bankname3=="")
		 {
			 showDisposeFlowDetail.getRes2("请填写开户银行");
			 window.client.showShortMessage("请填写开户银行");
			 return;
		 }
		 var startwaterdegree3,startgasdegree3, startelectricdegree3, endwaterdegree3,endgasdegree3,endelectricdegree3;
		   endwaterdegree3=$("#endwaterdegree3").val(); 
		   endgasdegree3=$("#endgasdegree3").val();   	    	
		   endelectricdegree3=$("#endelectricdegree3").val();  
		   startwaterdegree3=$("#startwaterdegree3").val(); 
		   startgasdegree3=$("#startgasdegree3").val();   	    	
		   startelectricdegree3=$("#startelectricdegree3").val();
		 if (parseFloat(startwaterdegree3) >=parseFloat(endwaterdegree3))  
		 {
			 showDisposeFlowDetail.getRes2("水表起始度数不得大于结束度数");
			 window.client.showShortMessage("水表起始度数不得大于结束度数");
			 return;
		 }
		 if (parseFloat(startgasdegree3) >=parseFloat(endgasdegree3)) 
		 {
			 showDisposeFlowDetail.getRes2("燃气起始度数不得大于结束度数");
			 window.client.showShortMessage("燃气起始度数不得大于结束度数");
			 return;
		 }
		 if (parseFloat(startelectricdegree3) >=parseFloat(endelectricdegree3)) 
		 {
			 showDisposeFlowDetail.getRes2("电表起始度数不得大于结束度数");
			 window.client.showShortMessage("电表起始度数不得大于结束度数");
			 return;
		 }
		 
		 if (!Validator.Validate('form4'))
	     {
     	     return;
         }
		 //取出图片地址
		 $("#degreepic3 input[name='picImage']").each(function()
		 {
			degreepic3 +=$(this).val()+",";  
		 }); 
		 //取出图片地址
		 $("#housepic3 input[name='picImage']").each(function()
		 {
			housepic3 +=$(this).val()+",";  
		 });
		 if (degreepic3==null||degreepic3=="")
		 {
			 showDisposeFlowDetail.getRes2("请上传度数图片");
			 window.client.showShortMessage("请上传度数图片");
			 return;
		 }
		 if (housepic3==null||housepic3=="")
		 {
			 showDisposeFlowDetail.getRes2("请上传验房图片");
			 window.client.showShortMessage("请上传验房图片");
			 return;
		 }
		 //截取图片
		 degreepic3 = degreepic3.substring(0, degreepic3.length - 1);
		 housepic3 = housepic3.substring(0, housepic3.length - 1);
		 
		var flowremark=$("#checkdesc3").val();  
		 var msg="信息是否录入完毕？";
		 window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function()
		 {
			showDisposeFlowDetail.checkFun=function()
 		 {
    		  var reasons3,key3,doorcard3,otherdesc3,favourable3;
    		  reasons3=$("#reasons3").val();   	    	
    		  key3=$("#key3").val();   	  
    		  favourable3=$("#favourable3").val();   	  
    		  doorcard3=$("#doorcard3").val();   	    	
    		  otherdesc3=$("#otherdesc3").val();  
    		  checkdesc3=$("#checkdesc3").val();  
    		  return{favourable:favourable3,housedesc:checkdesc3,alipay:alipay3,bankcard:bankcard3,bankname:bankname3,
    			  startwaterdegree:startwaterdegree3,startgasdegree:startgasdegree3,startelectricdegree:startelectricdegree3,
    			  endwaterdegree:endwaterdegree3,endgasdegree:endgasdegree3,endelectricdegree:endelectricdegree3,
    			  degreepic:degreepic3,reasons:reasons3,key:key3,doorcard:doorcard3,otherdesc:otherdesc3,housepic:housepic3};
	     }
		 showDisposeFlowDetail.save(state, flowremark, "");
		 },onCancel:function()
		 {
				
		 }
		});
	}
 }
};
$(stepDetail4.init());
