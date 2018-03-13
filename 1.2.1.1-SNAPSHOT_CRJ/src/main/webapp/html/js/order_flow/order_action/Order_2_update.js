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
    	 /**
         * 加载维修类型
         * */
        common.loadItem('ORDERCLEAN.TYPE', function(json)
        {
            var html = "";
            for (var i = 0; i < json.length; i++) 
            {
     		   html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#childtype').append(html);
            $('#childtype').val(rowdata.childtype);   
        });
	 if (rowdata != null)
	 {
		 var status=rowdata.order_status;
		$('#oname').val(rowdata.order_name) ;
		$('#order_code')[0].innerHTML = rowdata.order_code;
		$('#ouserMobile')[0].innerHTML = rowdata.user_mobile;
		$('#ouserName')[0].innerHTML = rowdata.user_name;
		$('#oserviceTime').val(rowdata.service_time);
		$('#odesc').val(rowdata.order_desc);
		$('#ocost').val(rowdata.order_cost);
		if (rowdata.order_cost>0||status!="4")
		{
			$("#ocost ").prop("disabled", true);
		}
		if (rowdata.picurls!=null&&rowdata.picurls!="") {
			var pas=rowdata.picurls.split(",");
			var paths=new Array();
			for ( var int = 0; int < pas.length-1; int++) {
				if (int==0) {
					paths.push({path:pas[int],first:1});	
				}else{
					paths.push({path:pas[int],first:0});
				}
			}
			common.dropzone.init({
				id : '#myAwesomeDropzone',
				defimg:paths,
				maxFiles:10
			});
		}else{
			common.dropzone.init({
				id : '#myAwesomeDropzone',
				maxFiles:10
			});
		}
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
	    
	    
	    var filepath = common.dropzone.getFiles('#myAwesomeDropzone');
		var pathe = "";
		var returnI = false;
		for ( var i = 0; i < filepath.length; i++) {
			if(filepath[i].fisrt==1){
				pathe = filepath[i].path + ',' + pathe;
			}
			else{
				pathe += filepath[i].path + ",";
			}
			returnI = true;
		}
		if (returnI) {
			pathe = pathe.substring(0, pathe.length - 1);
		}
	    
	    $("#orderadd_bnt").attr("disabled",true);
		 common.ajax({
	            url: common.root + '/Order/updateOrder.do',
	            data:{
	            	id:rowdata.id,
	            	orderName:$('#oname').val(),
	            	oserviceTime:$('#oserviceTime').val(),
	            	odesc:$('#odesc').val(),
	            	otype:2,
	            	upurl:pathe,
	            	ocost:$('#ocost').val(),
	            	childtype:$('#childtype').val(),
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
							common.closeWindow('orderupdate2',3);
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