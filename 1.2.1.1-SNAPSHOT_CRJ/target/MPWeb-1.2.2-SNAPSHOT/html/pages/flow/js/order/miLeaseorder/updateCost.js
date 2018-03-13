var jobId="";
var createCost = {
    //页面初始化操作
	    init : function() {
		// 添加处理事件
	    createCost.addEvent();
    	// 时间按钮选择事件
		createCost.initTime();
		$("#endtime,#starttime").blur(function()
        {
        	var starttime= $("#starttime").val();
        	var endtime= $("#endtime").val();
        	if (parseFloat(endtime)>=parseFloat(starttime)) 
        	{
    		   $("#degree").val(endtime-starttime);
			}else{
				showDisposeFlowDetail.getRes2('起始度数不得大于结束度数');
				njyc.phone.showShortMessage('起始度数不得大于结束度数');
			}
        });
	},
	initTime: function(){
		var stype="";
		 njyc.phone.loadItem("CERTIFICATELEAVE.TYPE", function(json)
	    {
		       var html="<option value=''>请选择..</option>"
		       for (var i = 0; i < json.length; i++) 
		       {
		        	html  +="<option value="+json[i].item_id+">"+json[i].item_name+"</option>"
		       }
		       $('#costtype').html(html);
		       $('#costtype').val(stype);
		 });
		var id = njyc.phone.queryString('id');
		jobId=id;
		njyc.phone.ajax(
		{
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/getInfo.do',
			data:{id:id},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, json) 
			{
				if (isloadsucc)
				{
					 stype=json.type;
					 $('#costtype').val(json.type);
					 $('#degree').val(json.degree);
					 $('#starttime').val(json.starttime);
					 $("#endtime").val(json.endtime);
					 $('#name').val(json.name);
					 $("#cost").val(json.cost);
					 $('#desc').val(json.costdesc);
				} else {
					showDisposeFlowDetail.getRes(2);
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
	},
   addEvent: function(){
        $('#orderadd_bnt').click(function()
        {
        	createCost.save();
        });
    },

   save:function()
   {
	 if (!Validator.Validate('form2'))
	 {
     	return;
     }
	 njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/update.do',
			data :
			{
			 id:jobId,
			  degree : $('#degree').val(),
			  starttime : $('#starttime').val(),
			  endtime :$('#endtime').val(),
			  name :$('#name').val(),
			  type : $('#costtype').val(),
			  cost : $('#cost').val(),
			  desc : $('#desc').val(),
            },
			dataType:'json',
	        loadfun: function(isloadsucc, data)
	        {
                if (isloadsucc)
                {
                    if (data.state == -2) 
                    {
                    	showDisposeFlowDetail.getRes(2);
                 		njyc.phone.showShortMessage('操作失败');
                    }
                    else
                    {
                    	showDisposeFlowDetail.getRes(1);
                    	njyc.phone.showShortMessage('操作成功');
                    	njyc.phone.closeCallBack("refreshTODO()");
					}
                }
                else 
                {
                	showDisposeFlowDetail.getRes(2);
                	njyc.phone.showShortMessage('操作失败');
                }
	         }
	     });
	}
};
$(createCost.init());
