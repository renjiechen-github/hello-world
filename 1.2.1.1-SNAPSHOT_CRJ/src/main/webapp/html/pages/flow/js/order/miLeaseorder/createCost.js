var step_type="";
var createCost = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	createCost.initType();
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
				  showDisposeFlowDetail.getRes(8);
				njyc.phone.showShortMessage('起始度数不得大于结束度数');
			}
        });
	},
	initTime: function(){
	step_type = njyc.phone.queryString('step_type');
    $("#starttime").val(0.0);
    $("#endtime").val(0.0);
    $("#degree").val(0.0);
    },
   addEvent: function(){
        $('#orderadd_bnt').click(function()
        {
        	createCost.save();
        });
    },
    /**
     加载区域选择
     **/
    initType: function()
    {
       $("#cost").val(0);
	    njyc.phone.loadItem("CERTIFICATELEAVE.TYPE", function(json)
	    {
		       var html="<option value=''>请选择..</option>"
		       for (var i = 0; i < json.length; i++) 
		       {
		        	html  +="<option value="+json[i].item_id+">"+json[i].item_name+"</option>"
		       }
		       $('#costtype')[0].innerHTML=html;
		 });
    },
   save:function()
   {
	 if (!Validator.Validate('form2'))
	 {
     	return;
     }
	 njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/miLeaveService/create.do',
			data :
			{
           	  orderId :$('#orderId').val(),
			  taskId:$('#task_id').val(),
			  degree : $('#degree').val(),
			  starttime : $('#starttime').val(),
			  endtime :$('#endtime').val(),
			  name :$('#name').val(),
			  type : $('#costtype').val(),
			  cost : $('#cost').val(),
			  desc : $('#desc').val(),
			  step_type :step_type,
            },
			dataType:'json',
	        loadfun: function(isloadsucc, data)
	        {
                if (isloadsucc)
                {
                    if (data.state == -2) 
                    {
                 		  njyc.phone.showShortMessage('操作失败');
                 		  showDisposeFlowDetail.getRes(2);
                    }
                    else
                    {
                    	njyc.phone.showShortMessage('操作成功');
                    	njyc.phone.closeCallBack("refreshTODO()");
                    	 showDisposeFlowDetail.getRes(1);
					}
                }
                else 
                {
                	njyc.phone.showShortMessage('操作失败');
                	  showDisposeFlowDetail.getRes(2);
                }
	         }
	     });
	}
};
$(createCost.init());
