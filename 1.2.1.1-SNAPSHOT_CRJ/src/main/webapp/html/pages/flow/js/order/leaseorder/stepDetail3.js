var resons="";
var stepDetail4={
	init:function(){
	//初始化管家选项
	stepDetail4.initData();
	//添加处理事件
	},
	initData : function() 
	{
		var task_id=$("#task_check_id").val();
		common.ajax(
			    {
					url : common.root + '/flow/getInfo.do',
					data : {task_id:task_id},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							if (data.endelectricdegree==null||data.endelectricdegree=="") 
							{
								stepDetail4.imageDeal("","degreepic3");
								stepDetail4.imageDeal("","housepic3");
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
							//$("#reasons3").val(data.reasonsname);
							$("#key3").val(data.keyrecieve);
							$("#doorcard3").val(data.doorcard);
							$("#otherdesc3").val(data.otherdesc);
							$("#checkdesc3").val(data.housedesc);
							$("#favourable3").val(data.favourable);
							stepDetail4.imageDeal(data.degreepic,"degreepic3");
							stepDetail4.imageDeal(data.housepic,"housepic3");
						}
					}
				 });
		
		common.loadItem("LEASEORDER.REASONS", function(json)
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
	 imageDeal:function(path,id){
	    	if (path!=null&&path!="") {
				var pas=path.split(",");
				var paths=new Array();
				for ( var int = 0; int < pas.length; int++) {
					if (int==0) {
						paths.push({path:pas[int],first:1});	
					}else{
						paths.push({path:pas[int],first:0});
					}
				}
				common.dropzone.init({
					id : '#'+id,
					defimg:paths,
					maxFiles:5,
				});
			}else{
				common.dropzone.init({
					id : '#'+id,
					maxFiles:5,
				});
			}
	    },
	/**
	 * 保存操作
	 */
 save:function(state)
 {                                                 
	 if(state == 2 )
	 {
		 var alipay3,bankcard3,bankname3,degreepic3,housepic3;
		 alipay3=$('#alipay3').val();
		 bankcard3=$('#bankcard3').val();
		 bankname3=$('#bankname3').val();
		 //验证支付宝账号或银行卡号是否填写
		 if ((alipay3==null||alipay3=="")&&(bankcard3==null||bankcard3=="")) 
		 {
			 common.alert({msg: "请填写支付宝账号或银行卡号"});
			 layer.tips("请填写支付宝账号", $("#alipay3"), {
		          tips: [1, '#d9534f'] //还可配置颜色
		      });
			 layer.tips("请填写银行卡号", $("#bankcard3"), {
		          tips: [1, '#d9534f'] //还可配置颜色
		      });
			 return;
		 }
		 //验证银行卡的开户银行是否填写
		 if (bankcard3!="")
		 {
			 var reg=/^\d+$/;
			 if (!reg.test(bankcard3))
			 {
				 common.alert({msg: "请填写正确的银行卡号"});
				 layer.tips("请填写正确的银行卡号", $("#bankcard3"), {tips: [1, '#d9534f']});
				 return;
			 }
		 }
		 //验证银行卡的开户银行是否填写
		 if (bankcard3!=""&&bankname3=="")
		 {
			 common.alert({msg: "请填写开户银行"});
			 layer.tips("请填写开户银行", $("#bankname3"), {tips: [1, '#d9534f']});
			 return;
		 }
		 degreepic3=stepDetail4.imagework("degreepic3");//度数附件
		 housepic3=stepDetail4.imagework("housepic3");//房间附件
		 if (degreepic3==null||degreepic3=="")
		 {
			 common.alert({msg: "请上传图片"});
			 layer.tips("请上传图片", $("#degreepic3"), {tips: [1, '#d9534f']});
			 return;
		 }
		 if (housepic3==null||housepic3=="")
		 {
			 common.alert({msg: "请上传图片"});
			 layer.tips("请上传图片", $("#housepic3"), {tips: [1, '#d9534f']});
			 return;
		 }
		 if (!Validator.Validate('form4'))
	     {
     	     return;
         }
		 var startwaterdegree3,startgasdegree3, startelectricdegree3, endwaterdegree3,endgasdegree3,endelectricdegree3;
		   endwaterdegree3=$("#endwaterdegree3").val(); 
		   endgasdegree3=$("#endgasdegree3").val();   	    	
		   endelectricdegree3=$("#endelectricdegree3").val();  
		   startwaterdegree3=$("#startwaterdegree3").val(); 
		   startgasdegree3=$("#startgasdegree3").val();   	    	
		   startelectricdegree3=$("#startelectricdegree3").val();
		 if (parseFloat(startwaterdegree3)>=parseFloat(endwaterdegree3)) 
		 {
			 common.alert({msg: "起始度数不得大于结束度数"});
			 layer.tips("起始度数不得大于结束度数", $("#endwaterdegree3"), {tips: [1, '#d9534f']});
			 return;
		 }
		 if (parseFloat(startgasdegree3)>=parseFloat(endgasdegree3))
		 {
			 common.alert({msg: "起始度数不得大于结束度数"});
			 layer.tips("起始度数不得大于结束度数", $("#endgasdegree3"), {tips: [1, '#d9534f']});
			 return;
		 }
		 if (parseFloat(startelectricdegree3)>=parseFloat(endelectricdegree3))
		 {
			  common.alert({msg: "起始度数不得大于结束度数"});
			 layer.tips("起始度数不得大于结束度数", $("#endelectricdegree3"), {tips: [1, '#d9534f']});
			 return;
		 }
		 var flowremark=$("#checkdesc3").val();
	     common.alert({
           msg: "是否核对完成",
           confirm: true,
           fun: function(action)
           {
    	       if (action) 
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
               }
           }
	     }); 
	   }
  },
	imagework:function(id){
		 //读取图片路径
	     	var filepath = common.dropzone.getFiles('#'+id);
	    	var pathe = "";
	    	var returnI = false;
	    	for ( var i = 0; i < filepath.length; i++) 
	    	{   //首图判断
	    		if(filepath[i].fisrt==1)
	    		{
	    			pathe = filepath[i].path + ',' + pathe;
	    		}
	    		else
	    		{
	    			pathe += filepath[i].path + ",";
	    		}
	    		returnI = true;
	    	}//图片截取
	    	if (returnI)
	    	{
	    		pathe = pathe.substring(0, pathe.length - 1);
	    	}
	    	return pathe;
	}
};
$(function(){
	stepDetail4.init();
});
var rowdata;
var step_type=0;


