var id ='';
var flag = "";
var tjAgreement ={
		init:function(){
			id = rowdata.id;
			flag = rowdata.flag;
			var nowTemp = new Date();
			$('#tjDate').daterangepicker(
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
			if (rowdata.status=="2") {
				$("#tzType").html('<option value="A">正常退租</option><option value="B">已入住违约退租</option>');
			}else{
				$("#tzType").html('<option value="C">未入住违约退租</option>');
			}
			$('#tj_btn').click(tjAgreement.saveTz);
		},
		saveTz:function(){
			var tjDate = $('#tjDate').val();
			if(tjDate == '')
			{
				common.alert({msg:'请选择时间!'});
				return;
			}
			
			var tzType = $('#tzType').val();
			var param = {
		            'rentalLeaseOrderId': rowdata.id,
		            'cancelLeaseDate' : $('#tjDate').val(),
					'channel' : "B",
					'type' : $('#tzType').val(),
					'comments' : "",
					'userName': rowdata.username,
					'userMobile': rowdata.user_mobile,
					'orderName' : "退组订单-"+rowdata.name
		        };
			$("#tj_btn").attr("disabled",true);
			common.ajax({
				url : common.root + '/cancelLease/inputCancelLeaseOrderInfo.do',
				dataType : 'json',
	            contentType: 'application/json; charset=utf-8',
	            encode: false,
	            data : JSON.stringify(param),
				loadfun : function(isloadsucc, data) 
				{
						if (data.state == 1)
						{
							common.alert({msg:'操作成功!'});
							common.closeWindow('showTj',1);
							if(flag==1)
							{
								table.refreshRedraw('agreementList_table');
							}	
							else
							{
								table.refreshRedraw('rank_agreement_list_table');
							}	
						} 
						else if(data.state == 2)
						{
							$("#tj_btn").attr("disabled",false);
							common.alert({msg:data.errorMsg});
							return ;
						}
					}
					
			});
		}
}
$(tjAgreement.init());