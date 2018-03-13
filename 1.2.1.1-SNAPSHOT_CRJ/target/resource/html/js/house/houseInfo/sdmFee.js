var id = '';
var sdmFee = 
{
	init:function()
	{
		var nowTemp = new Date();
		$('#eMeterDate,#cardgasDate,#waterDate').on('apply.daterangepicker',function(ev, picker) {
			if(picker.startDate.format('YYYY-MM-DD') == picker.endDate.format('YYYY-MM-DD'))
			{
				$(this).val('')
				common.alert({msg:'开始时间和结束时间相同'})
				return ;
			}
			var waterDate = $('#waterDate').val();
			var waterD = $('#waterD').val();
			var waterF = $('#waterF').val();
			if(waterD == '' && waterF == '' && waterDate == '')
			{
				 $('#waterD').removeAttr('dataType');
				 $('#waterF').removeAttr('dataType');
				 $('#waterDate').removeAttr('dataType');
			}
			else
			{
				 $('#waterD').attr('dataType','Double2');
				 $('#waterF').attr('dataType','Double2');
				 $('#waterDate').attr('dataType','Require');
			}
			var eMeterD = $('#eMeterD').val();
			var eMeterF = $('#eMeterF').val();
			var eMeterDate = $('#eMeterDate').val();
			if(eMeterD == '' && eMeterF == '' && eMeterDate == '')
			{
				$('#eMeterD').removeAttr('dataType');
				$('#eMeterF').removeAttr('dataType');
				$('#eMeterDate').removeAttr('dataType');
			}
			else
			{
				$('#eMeterD').attr('dataType','Double2');
				$('#eMeterF').attr('dataType','Double2');
				$('#eMeterDate').attr('dataType','Require');
			}
			var cardgasD = $('#cardgasD').val();
			var cardgasF = $('#cardgasF').val();
			var cardgasDate = $('#cardgasDate').val();
			if(cardgasD == '' && cardgasF == '' && cardgasDate == '')
			{
				$('#cardgasD').removeAttr('dataType');
				$('#cardgasF').removeAttr('dataType');
				$('#cardgasDate').removeAttr('dataType');
			}
			else
			{
				$('#cardgasD').attr('dataType','Double2');
				$('#cardgasF').attr('dataType','Double2');
				$('#cardgasDate').attr('dataType','Require');
			}
		});
	
		id = common.getWindowsData('houseSign').id
		$('#id').val(id);
		$('#waterD,#waterF,#eMeterD,#eMeterF,#cardgasD,#cardgasF').change(function(){
			var waterD = $('#waterD').val();
			var waterF = $('#waterF').val();
			var waterDate = $('#waterDate').val();
			if(waterD == '' && waterF == '' && waterDate == '')
			{
				 $('#waterD').removeAttr('dataType');
				 $('#waterF').removeAttr('dataType');
				 $('#waterDate').removeAttr('dataType');
			}
			else
			{
				 $('#waterD').attr('dataType','Double2');
				 $('#waterF').attr('dataType','Double2');
				 $('#waterDate').attr('dataType','Require');
			}
			var eMeterD = $('#eMeterD').val();
			var eMeterF = $('#eMeterF').val();
			var eMeterDate = $('#eMeterDate').val();
			if(eMeterD == '' && eMeterF == '' && eMeterDate == '')
			{
				$('#eMeterD').removeAttr('dataType');
				$('#eMeterF').removeAttr('dataType');
				$('#eMeterDate').removeAttr('dataType');
			}
			else
			{
				$('#eMeterD').attr('dataType','Double2');
				$('#eMeterF').attr('dataType','Double2');
				$('#eMeterDate').attr('dataType','Require');
			}
			var cardgasD = $('#cardgasD').val();
			var cardgasF = $('#cardgasF').val();
			var cardgasDate = $('#cardgasDate').val();
			if(cardgasD == '' && cardgasF == '' && cardgasDate == '')
			{
				$('#cardgasD').removeAttr('dataType');
				$('#cardgasF').removeAttr('dataType');
				$('#cardgasDate').removeAttr('dataType');
			}
			else
			{
				$('#cardgasD').attr('dataType','Double2');
				$('#cardgasF').attr('dataType','Double2');
				$('#cardgasDate').attr('dataType','Require');
			}
		});
		$('#eMeterDate,#cardgasDate,#waterDate').daterangepicker(
				{
						timePicker12Hour : false,
						timePicker : false,
						separator : '~',
						maxDate : moment(),
						dateLimit : {
		                       days : 60
		                   },
						// 分钟间隔时间
						timePickerIncrement : 10,
						format : 'YYYY-MM-DD'
				},function(startDate, endDate,chosenLabel){
					console.log(startDate);
					console.log(endDate);
					console.log(chosenLabel);
				});
		$('#w_add_bnt').click(sdmFee.save);
 	},
 	save:function()
 	{
 		if (!Validator.Validate('form2'))
		{
			return;
		}
		if($('#waterD').val() == '' && $('#waterF').val() == '' && $('#eMeterD').val() == '' && $('#eMeterF').val() == '' && $('#cardgasF').val() == '' && $('#cardgasD').val() == '' && $('#waterDate').val() == '' && $('#eMeterDate').val() == '' && $('#cardgasDate').val() == '') 
		{
			common.alert({msg:'至少填写一组费用信息'});
			return ;
		}
		$('#w_add_bnt').unbind("click");
		var ajax_option =
		{
			url:"/BaseHouse/saveSDM.do",//默认是form action
			success:sdmFee.resFunc,
			type:'post',
			dataType:'json'
		};
		$("#form2").ajaxForm(ajax_option).submit();
 	},
 	resFunc:function(data)
 	{
 		$('#w_add_bnt').click(sdmFee.save);
 		if(data.state == 1)
 		{
 			common.alert({
 				msg:'操作成功！',
 				fun : function() {
					common.closeWindow('houseSign', 1);
					return ;
				}
 			});
 		}
 		else if(data.state == -1)
 		{
 			common.alert({
 				msg:'未找到合约信息！',
 				fun : function() {
 					common.closeWindow('houseSign', 1);
 					return ;
 				}
 			});
 		}
 		else if(data.state == -2)
 		{
 			common.alert({
 				msg:'没有租客！',
 				fun : function() {
 					common.closeWindow('houseSign', 1);
 					return ;
 				}
 			});
 		}
 		else if(data.state == -3)
 		{
 			common.alert({
 				msg:'生成水费失败！'
 			});
 		}
 		else if(data.state == -4)
 		{
 			common.alert({
 				msg:'生成电费失败！'
 			});
 		}
 		else if(data.state == -5)
 		{
 			common.alert({
 				msg:'生成燃气费失败！'
 			});
 		}
 		else
 		{
 			common.alert({
				msg : '网络忙,请重试!',
			}); 
 		}	
 	}
}
$(sdmFee.init());