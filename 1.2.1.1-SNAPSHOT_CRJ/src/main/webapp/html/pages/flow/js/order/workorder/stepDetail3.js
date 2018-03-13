var stepDetail4={
	init:function(){
	//初始化管家选项
	stepDetail4.initData();
	//添加处理事件
	stepDetail4.addevent();
	$("#changeDiv").show();
	$("#changeDiv3").show();
	$("#changeDiv2").hide();
	$("#changeDiv4").hide();
	var type=$("#type").val();
	switch (type)
	{
	case "0":
		$("#childhide").hide();
		$("#pic1hide").hide();
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
		$("#pic1hide").hide();
		$("#childhide").hide();
		break;
	case "4":
		$('#ocost').attr("readonly",true);
		$("#pichides").hide();
		$("#pic1hide").hide();
		$("#childhide").hide();
		break;
	case "5":
		stepDetail4. initData("ORDERSERVICE.TYPE");
		stepDetail4.picInit();
		$('#ocost').attr("readonly",true);
		break;
	case "6":
		$('#ocost').attr("readonly",true);
		$("#childhide").hide();
		stepDetail4.picInit();
		break;
	case "7":
		$("#childhide").hide();
		$("#pic1hide").hide();
		$("#pichides").hide();
		$('#ocost').attr("readonly",true);
		var agree =$('#agree').val();
		stepDetail4.addressInit(agree);
		stepDetail4.creatTable(agree);
		break;
	case "9":
		$("#childhide").hide();
		stepDetail4.picInit();
		break;
	}
	},
	sigleHouseInfo:function(house_rank_id,flag,houseId,rankType,title,agreementId)
	{
		// 查看单个房间信息
		common.openWindow({
			name:'signHouse',
			type : 1,
			data:{id:house_rank_id,flag:flag,houseId:houseId,rankType:rankType,title:title,agreementId:agreementId},
			title : '查询出租信息',
			url : '/html/pages/house/houseInfo/rank_house_agreement.html' 
		});
	},//加载维修地址和保洁地址
	  addressInit:function (agree)
	  {
		  common.ajax({
						url : common.root + '/rankHouse/loadAgreementInfo.do',
						data : {id:agree},
						dataType : 'json',
						async:false,
						loadfun : function(isloadsucc, data) 
						{
							if (isloadsucc)
							{
								$('#rankName')[0].innerHTML ="<a onclick='stepDetail4.sigleHouseInfo("+data.house_rank_id+",0,"+data.house_id+",\""+data.rankType+"\",\""+data.title+"\","+agree+")'>"+ data.name+"("+data.agree_code+")</a>";
							}
						}
					});
	  },
	//初始化图片上传
	picInit : function() 
	{
		common.dropzone.init
		({
			id : '#upurls',
			maxFiles:10
		});
		//初始化图片上传
		 var picurl = $('#upurl').attr('dataId');
		 if (picurl!=null&&picurl!="")
		 {
				var pas=picurl.split(",");
				var paths=new Array();
				for ( var int = 0; int < pas.length-1; int++)
				{
					if (int==0) 
					{
						paths.push({path:pas[int],first:1});	
					}
					else
					{
						paths.push({path:pas[int],first:0});
					}
				}
				common.dropzone.init({
					id : '#upurl',
					defimg:paths,
					maxFiles:10
				});
		 }
		 else
		 {
			common.dropzone.init({
				id : '#upurl',
				maxFiles:10
			});
		 }
	},
	initData : function(type) 
	{
	 /**
     * 加载维修类型
     * */
    common.loadItem(type, function(json)
    {
        var html = "";
        for (var i = 0; i < json.length; i++) 
        {
 		   html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
        }
        $('#childtype').html(html);
        var dataId = $('#childtype').attr('dataId');
        $('#childtype').val(dataId);
    });
	// 时间按钮选择事件
	var nowTemp = new Date();
	$('#servicetime').daterangepicker(
	{
		   startDate : nowTemp.getFullYear() + '-'+ (nowTemp.getMonth() + 1) + '-02',
		   singleDatePicker : true,
		   timePicker12Hour:false,
		   timePicker: true,
		   timePickerIncrement: 10,
		   format : 'YYYY-MM-DD HH:mm'
	}, function(start, end, label)
    {
		   console.log(start.toISOString(), end.toISOString(), label);
	});
	},//添加事件
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
        msg: "是否完成订单",
        confirm: true,
        fun: function(action)
        {
    	 if (action) 
    	 {
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
    			//正则判断金额填写是否正确
    			if (!reg.test(ocost))
    			{
    				common.alert({msg:"请正确填费用信息！"});
					return;
				}
		    	//判断订单类型维修、保洁、入住问题有上传图片，维修保洁会有相关费用的判断
		    	var type=$("#type").val();
		    	if (type=="1"||type=="2"||type=="6"||type=="9"||type=="5")
		    	{
		    		 //读取图片路径
			     	var filepath = common.dropzone.getFiles('#upurl');
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
			    	if (type=="6"||type=="9") {
			    		childtype="0";
					}
		    		if (oname!=null&&oname!=""&&servicetime!=null&&servicetime!=""&&odesc.length<256&&childtype!=null&&childtype!="")
	    			{ 
				       if (odesc==null||odesc=="") 
				       {
				    	   odesc="";
					   }
				       return {oname:oname,servicetime:servicetime,odesc:odesc,ocost:ocost,upurl:pathe,childtype:childtype};
					}else
	    			{
						common.alert({msg:"请检查修改信息是否正确！"});
						return;
					}
				}else{
					//看房订单、其他订单、投诉订单验证信息
					if (oname!=null&&oname!=""&&servicetime!=null&&servicetime!=""&&odesc.length<256)
	    			{ 
				       if (odesc==null||odesc=="") 
				       {
				    	   odesc="";
					   }
				       return {oname:oname,servicetime:servicetime,odesc:odesc,ocost:ocost};
					}else
	    			{
						common.alert({msg:"请检查修改信息是否正确！"});
						return;
					}
				}
		     };
		     showDisposeFlowDetail.save(state, "", "");
    	 }
        }
	  }); 
	}
   }
};
$(function(){
	stepDetail4.init();
});