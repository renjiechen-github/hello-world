var orderupdate =
{
    //页面初始化操作
    init : function()
    {
       // 加载结算类型
    	orderupdate.initData();
    	orderupdate.addEvent();
    	// 时间按钮选择事件
		var nowTemp = new Date();
		$('#oserviceTime').daterangepicker(
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
	},
    addEvent: function()
    {
    	$("#backrow").click(function() {
    		common.closeWindow('orderupdate0', 3);
		});
    	
    	$("#orderadd_bnt").click(function() {
    		orderupdate.save();
		});
    },
    initData: function()
    {
	 if (rowdata != null)
	 {
		$('#oname').val(rowdata.order_name) ;
		$('#order_code')[0].innerHTML = rowdata.order_code;
		$('#ouserMobile')[0].innerHTML = rowdata.user_mobile;
		$('#ouserName')[0].innerHTML = rowdata.user_name;
		$('#oserviceTime').val(rowdata.service_time);
		$('#odesc').val(rowdata.order_desc);
		common.ajax(
				{
					url : common.root + '/houserank/getrankhouse.do',
					data : {id:rowdata.correspondent},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							console.log(data);
							$('#rankName')[0].innerHTML = data.data[0].house_name;
						}
					}
				});
     }
    },
    /*修改
    */
    save:function()
    {
	    if (!Validator.Validate('form2'))
	    {
     	 return;
        }
	    $("#orderadd_bnt").attr("disabled",true);
		 common.ajax({
	            url: common.root + '/Order/updateOrder.do',
	            data:{
	            	id:rowdata.id,
	            	orderName:$('#oname').val(),
	            	oserviceTime:$('#oserviceTime').val(),
	            	odesc:$('#odesc').val(),
	            	otype:3,
	            	upurl:"",
	            	ocost:0,
	            	childtype:0,
	            	correspondent:rowdata.correspondent,
	            	status:rowdata.order_status
	            },
				dataType:'json',
	            loadfun: function(isloadsucc, data)
	            {
	                if (isloadsucc)
	                {
	                    if (data.state == 1) 
	                    {//操作成功
	                 		common.alert({msg:'操作成功'});
							common.closeWindow('orderupdate3',3);
	                    }
	                    else
	                    {
	                    	$("#orderadd_bnt").attr("disabled",false);
							common.alert({ msg: common.msg.error});
						}
	                }
	                else 
	                {
	                	$("#orderadd_bnt").attr("disabled",false);
	                    common.alert({ msg: common.msg.error });
	                }
	            }
	        });
	},
};
orderupdate.init();