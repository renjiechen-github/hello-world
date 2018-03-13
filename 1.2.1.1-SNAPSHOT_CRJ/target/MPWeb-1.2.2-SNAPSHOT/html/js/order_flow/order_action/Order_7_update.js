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
		   timePicker12Hour:false,
		   timePicker: true,
		   timePickerIncrement: 10,
		   singleDatePicker : true,
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
					url : common.root + '/rankHouse/loadAgreementInfo.do',
					data : {id:rowdata.correspondent},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							$('#rankName')[0].innerHTML ="<a onclick='orderupdate.sigleHouseInfo("+data.house_rank_id+",0,"+data.house_id+",\""+data.rankType+"\",\""+data.title+"\","+rowdata.correspondent+")'>"+ data.name+"("+data.agree_code+")</a>";
						}
					}
				});
		common.loadItem('EVICTION.TYPE', function(json)
		        {
		            var html = "";
		            for (var i = 0; i < json.length; i++) 
		            {
		     		   html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
		            }
		            $('#childtype').append(html);
		            $('#childtype').val(rowdata.childtype);
		        });
     }
    },
    /*修改
    */
    save:function()
    {
	    if (!Validator.Validate('form3'))
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
	            	otype:0,
	            	upurl:"",
	            	ocost:rowdata.order_cost,
	            	childtype:$('#childtype').val(),
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
							common.closeWindow('orderupdate0',3);
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
	},sigleHouseInfo:function(house_rank_id,flag,houseId,rankType,title,agreementId)
	{
		console.log({id:house_rank_id,flag:flag,houseId:houseId,rankType:rankType,title:title,agreementId:agreementId});
		// 查看单个房间信息
		common.openWindow({
			name:'signHouse',
			type : 1,
			data:{id:house_rank_id,flag:flag,houseId:houseId,rankType:rankType,title:title,agreementId:agreementId},
			title : '查询出租信息',
			area : [ ($(window).width()-200)+'px', ($(window).height()-300)+'px' ],
			url : '/html/pages/house/houseInfo/rank_house_agreement.html' 
		});
	},
};
orderupdate.init();