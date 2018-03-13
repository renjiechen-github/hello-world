var order_statistics={
	/**
	 * 初始化数据信息
	 */
	init:function(){
		// 初始化查询时间
		var date = new Date();
		var month = date.getMonth()+1;
		month = month < 10?"0"+month:month;
		// 赋值查询时间
		$('#queryTime').val(date.getFullYear()+"-"+month);
		// 服务报表
		this.getOrderView();
		$('#queryTime').daterangepicker({
			singleDatePicker : true,
			format: 'YYYY-MM',
	        autoclose: true,
	        todayBtn: true,
	        startView: 'year',
	        minView:'year',
	        maxView:'decade'
		});
		
		$("#querySub").click(function(){
			var order_type = $("#order_type").val();
			if (order_type != '') {
				order_statistics.getOrderType();	
			} else {
				order_statistics.getOrderView();
			}
		});
		
		// 选择服务类型
		$("#order_type").change(function(){
			var order_type = $("#order_type").val();
			if (order_type != '') {
				order_statistics.getOrderType();	
			} else {
				order_statistics.getOrderView();
			}
		});
		
		// 选择服务状态
		$("#order_status").change(function(){
			var order_type = $("#order_type").val();
			if (order_type != '') {
				order_statistics.getOrderType();	
			} else {
				order_statistics.getOrderView();
			}
		});
	},
	
	StringToDate:function(DateStr){
		var converted = Date.parse(DateStr); 
		var myDate = new Date(converted); 
		if (isNaN(myDate)) 
		{ 
			var arys= DateStr.split('-'); 
			myDate = new Date(arys[0],--arys[1],arys[2]); 
		} 
		return myDate;
	},
	addDate:function(date,days){
		var d=new Date(date); 
		d.setDate(d.getDate()+days); 
		var month=d.getMonth()+1; 
		var day = d.getDate(); 
		if(month<10){ 
			month = "0"+month; 
		}
		if(day<10){ 
		day = "0"+day; 
		} 
		var val = d.getFullYear()+"-"+month; 
		return val; 
	},	
	
	getOrderType : function() {
		// 查询时间
		var time = $("#queryTime").val();
		// 服务类型
		var order_type = $("#order_type").val();
		// 服务状态
		var order_status = $("#order_status").val();
		
		var d = new Date(time);
		var labledata = [];
		// 向前推11个月
		for(var i=0;i<11;i++){
			if (i == 0) {
				labledata.push(time);
			}
			
            d.setMonth(d.getMonth() - 1);
            var m = d.getMonth() + 1;  
            m = m < 10 ? "0" + m : m;
            labledata.push(d.getFullYear() + "-" + m);
		}
		labledata.sort();
		$('#hometableMonth').text(time);
		common.ajax({
			url : common.root + '/report/qaViewTotal.do',
			dataType : 'json',
			data : {
				date : time,
				order_type : order_type,
				order_status : order_status
			},
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					console.log(data);
		        	var myChart = echarts.init(document.getElementById('main'));
		        	//图像表格下方的标签展示
		        	var lableName = [];
		        	var options = {};
		        	options.options = [];
		        	
		        	// 预约
		        	var reserve_total = {};
		        	// 维修
		        	var maintenance_total = {};
		        	// 保洁
		        	var cleaning_total = {};
		        	// 例行保洁
		        	var routine_cleaning_total = {};
		        	// 投诉
		        	var complaint_total = {};
		        	// 其他
		        	var other_total = {};
		        	// 入住
		        	var checkIn_total = {};
		        	// 退租
		        	var refund_total = {};
		        	// 处理周期
		        	var period_day = {};
		        	
		        	// 类型
		        	var type;
		        	// 数量
		        	var total;
		        	
		    		var legendName = [];
		    		if (order_type == 0) { // 看房订单
		    			legendName = ['预约'];
		    			type = "预约";
		    			total = data[11].reserve_total;
		    		}
		    		if (order_type == 1) { // 维修订单
		    			legendName = ['维修'];
		    			type = "维修";
		    			total = data[11].maintenance_total;
		    		}
		    		if (order_type == 2) { // 保洁订单
		    			legendName = ['保洁'];
		    			type = "保洁";
		    			total = data[11].cleaning_total;
		    		}
		    		if (order_type == 3) { // 投诉订单
		    			legendName = ['投诉'];
		    			type = "投诉";
		    			total = data[11].complaint_total;
		    		}
		    		if (order_type == 4) { // 其他订单
		    			legendName = ['其他'];
		    			type = "其他";
		    			total = data[11].other_total;		    			
		    		}
		    		if (order_type == 6) { // 入住订单
		    			legendName = ['入住'];
		    			type = "入住";
		    			total = data[11].checkIn_total;		    			
		    		}
		    		if (order_type == 7) { // 退租订单
		    			legendName = ['退租'];
		    			type = "退租";
		    			total = data[11].refund_total;			    			
		    		}
		    		if (order_type == 9) { // 例行保洁
		    			legendName = ['例行保洁'];
		    			type = "例行保洁";
		    			total = data[11].routine_cleaning_total;		    			
		    		}
		    		legendName.push("处理周期");
		    		
		        	reserve_total.name='预约';
		        	maintenance_total.name='维修';
		        	cleaning_total.name='保洁';
		        	routine_cleaning_total.name='例行保洁';
		        	complaint_total.name='投诉';
		        	other_total.name='其他';
		        	checkIn_total.name='入住';
		        	refund_total.name='退租';
		        	period_day.name='处理周期';
		        	
		        	reserve_total.type='bar';
		        	maintenance_total.type='bar';
		        	cleaning_total.type='bar';
		        	routine_cleaning_total.type='bar';
		        	complaint_total.type='bar';
		        	other_total.type='bar';
		        	checkIn_total.type='bar';
		        	refund_total.type='bar';
		        	period_day.type='bar';
		        	
		        	var reserve_totaldata = [];
		        	var maintenance_totaldata = [];
		        	var cleaning_totaldata = [];
		        	var routine_cleaning_totaldata = [];
		        	var complaint_totaldata = [];
		        	var other_totaldata = [];
		        	var checkIn_totaldata = [];
		        	var refund_totaldata = [];
		        	var period_daydata = [];
		        	
		        	var reserve_totals = 0;
		        	var maintenance_totals = 0;
		        	var cleaning_totals = 0;
		        	var routine_cleaning_totals = 0;
		        	var complaint_totals = 0;
		        	var other_totals = 0;
		        	var checkIn_totals = 0;
		        	var refund_totals = 0;
		        	var period_days = 0;
		        	
		        	for(var i=0;i<data.length;i++){
		        		reserve_totals += data[i].reserve_total;
		        		maintenance_totals += data[i].maintenance_total;
		        		cleaning_totals += data[i].cleaning_total;
		        		routine_cleaning_totals += data[i].routine_cleaning_total;
		        		complaint_totals += data[i].complaint_total;
		        		other_totals += data[i].other_total;
		        		checkIn_totals += data[i].checkIn_total;
		        		refund_totals += data[i].refund_total;
		        		period_days += data[i].period_day;
		        		
		        		reserve_totaldata.push(data[i].reserve_total);
		        		maintenance_totaldata.push(data[i].maintenance_total);
		        		cleaning_totaldata.push(data[i].cleaning_total);
		        		routine_cleaning_totaldata.push(data[i].routine_cleaning_total);
		        		complaint_totaldata.push(data[i].complaint_total);
		        		other_totaldata.push(data[i].other_total);
		        		checkIn_totaldata.push(data[i].checkIn_total);
		        		refund_totaldata.push(data[i].refund_total);
		        		period_daydata.push(data[i].period_day);
		        	}
		        	
		        	reserve_total.data=reserve_totaldata;
		        	maintenance_total.data=maintenance_totaldata;
		        	cleaning_total.data=cleaning_totaldata;
		        	routine_cleaning_total.data=routine_cleaning_totaldata;
		        	complaint_total.data=complaint_totaldata;
		        	other_total.data=other_totaldata;
		        	checkIn_total.data=checkIn_totaldata;
		        	refund_total.data=refund_totaldata;
		        	period_day.data=period_daydata;
		        	
		        	var series = [];
		    		if (order_type == 0) { // 看房订单
		    			series.push(reserve_total);
		    		}
		    		if (order_type == 1) { // 维修订单
		    			series.push(maintenance_total);
		    		}
		    		if (order_type == 2) { // 保洁订单
		    			series.push(cleaning_total);
		    		}
		    		if (order_type == 3) { // 投诉订单
		    			series.push(complaint_total);
		    		}
		    		if (order_type == 4) { // 其他订单
		    			series.push(other_total);
		    		}
		    		if (order_type == 6) { // 入住订单
		    			series.push(checkIn_total);
		    		}
		    		if (order_type == 7) { // 退租订单
		    			series.push(refund_total);
		    		}	        	
		    		if (order_type == 9) { // 例行保洁
		    			series.push(routine_cleaning_total);
		    		}	        	
		        	series.push(period_day);
		        	
		        	options = {
	        			title : {
	        				subtext : '数据来自运营系统'
	        			},
	        			tooltip : {
	        				trigger : 'axis'
	        			},
	        			legend: {
	        		        data : legendName
	        		    },
	        			toolbox : {
	        				feature : {
	        					dataView : {
	        						show : true,
	        						readOnly : false
	        					},
	        					magicType : {
	        						show : true,
	        						type : [ 'line', 'bar' ]
	        					},
	        					restore : {
	        						show : true
	        					},
	        					saveAsImage : {
	        						show : true
	        					}
	        				}
	        			},
	        		    grid: {
	        		        left: '3%',
	        		        right: '4%',
	        		        bottom: '3%',
	        		        containLabel: true
	        		    },
	        		    xAxis : [
	        		        {
	        		            type : 'category',
	        		            data : labledata,
	        		            axisTick: {
	        		                alignWithLabel: true
	        		            }
	        		        }
	        		    ],
	        		    yAxis : [
	        		        {
	        		            type : 'value'
	        		        }
	        		    ],
	        		    series :series
	        		}
		        	myChart.setOption(options);
		        	
			        var html ='<tr class="t1">'+
								'<td>类型</td>'+
								'<td>数量</td>'+
								'<td>类型</td>'+
								'<td>数量</td>'+
								'</tr>'+ 
						        '<tr class="t" >'+
								'	<td >'+type+'</td>'+
								'	<td >'+total+'</td>'+
						        '	<td >处理周期</td>'+
						        '	<td >'+data[11].period_day+'</td>'+				
								'</tr>';
		        	
		        	$('#home_sc_table .t').remove();
		        	$('#home_sc_table .t1').remove();
		        	$('#home_sc_table').append(html);		        	

				} else {
					common.alert(common.msg.error);
				}
				common.load.hide();
			}			
		});
	},
	
	// 服务报表数据
	getOrderView : function() {
		// 查询时间
		var time = $("#queryTime").val();
		// 服务类型
		var order_type = $("#order_type").val();
		// 服务状态
		var order_status = $("#order_status").val();
		
		var d = new Date(time);
		var labledata = [];
		// 向前推11个月
		for(var i=0;i<11;i++){
			if (i == 0) {
				labledata.push(time);
			}
			
            d.setMonth(d.getMonth() - 1);
            var m = d.getMonth() + 1;  
            m = m < 10 ? "0" + m : m;
            labledata.push(d.getFullYear() + "-" + m);
		}
		labledata.sort();	
		$('#hometableMonth').text(time);
		
		common.ajax({
			url : common.root + '/report/qaViewTotal.do',
			dataType : 'json',
			data : {
				date : time,
				order_type : order_type,
				order_status : order_status
			},
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					console.log(data);
		        	var myChart = echarts.init(document.getElementById('main'));
		        	//图像表格下方的标签展示
		        	var lableName = [];
		        	var options = {};
		        	options.options = [];
		        	
		        	// 预约
		        	var reserve_total = {};
		        	// 维修
		        	var maintenance_total = {};
		        	// 保洁
		        	var cleaning_total = {};
		        	// 例行保洁
		        	var routine_cleaning_total = {};
		        	// 投诉
		        	var complaint_total = {};
		        	// 其他
		        	var other_total = {};
		        	// 入住
		        	var checkIn_total = {};
		        	// 退租
		        	var refund_total = {};
		        	// 处理周期
		        	var period_day = {};		        	
		        	
		        	reserve_total.name='预约';
		        	maintenance_total.name='维修';
		        	cleaning_total.name='保洁';
		        	routine_cleaning_total.name='例行保洁';
		        	complaint_total.name='投诉';
		        	other_total.name='其他';
		        	checkIn_total.name='入住';
		        	refund_total.name='退租';
		        	period_day.name='处理周期';
		        	
		        	reserve_total.type='bar';
		        	maintenance_total.type='bar';
		        	cleaning_total.type='bar';
		        	routine_cleaning_total.type='bar';
		        	complaint_total.type='bar';
		        	other_total.type='bar';
		        	checkIn_total.type='bar';
		        	refund_total.type='bar';
		        	period_day.type='bar';
		        	
		        	var reserve_totaldata = [];
		        	var maintenance_totaldata = [];
		        	var cleaning_totaldata = [];
		        	var routine_cleaning_totaldata = [];
		        	var complaint_totaldata = [];
		        	var other_totaldata = [];
		        	var checkIn_totaldata = [];
		        	var refund_totaldata = [];
		        	var period_daydata = [];
		        	
		        	var reserve_totals = 0;
		        	var maintenance_totals = 0;
		        	var cleaning_totals = 0;
		        	var routine_cleaning_totals = 0;
		        	var complaint_totals = 0;
		        	var other_totals = 0;
		        	var checkIn_totals = 0;
		        	var refund_totals = 0;
		        	var period_days = 0;
		        	
		        	for(var i=0;i<data.length;i++){
		        		reserve_totals += data[i].reserve_total;
		        		maintenance_totals += data[i].maintenance_total;
		        		cleaning_totals += data[i].cleaning_total;
		        		routine_cleaning_totals += data[i].routine_cleaning_total;
		        		complaint_totals += data[i].complaint_total;
		        		other_totals += data[i].other_total;
		        		checkIn_totals += data[i].checkIn_total;
		        		refund_totals += data[i].refund_total;
		        		period_days += data[i].period_day;
		        		
		        		reserve_totaldata.push(data[i].reserve_total);
		        		maintenance_totaldata.push(data[i].maintenance_total);
		        		cleaning_totaldata.push(data[i].cleaning_total);
		        		routine_cleaning_totaldata.push(data[i].routine_cleaning_total);
		        		complaint_totaldata.push(data[i].complaint_total);
		        		other_totaldata.push(data[i].other_total);
		        		checkIn_totaldata.push(data[i].checkIn_total);
		        		refund_totaldata.push(data[i].refund_total);
		        		period_daydata.push(data[i].period_day);
		        	}
		        	
		        	reserve_total.data=reserve_totaldata;
		        	maintenance_total.data=maintenance_totaldata;
		        	cleaning_total.data=cleaning_totaldata;
		        	routine_cleaning_total.data=routine_cleaning_totaldata;
		        	complaint_total.data=complaint_totaldata;
		        	other_total.data=other_totaldata;
		        	checkIn_total.data=checkIn_totaldata;
		        	refund_total.data=refund_totaldata;
		        	period_day.data=period_daydata;
		        	
		        	var series = [];
		        	series.push(reserve_total);
		        	series.push(maintenance_total);
		        	series.push(cleaning_total);
		        	series.push(routine_cleaning_total);
		        	series.push(complaint_total);
		        	series.push(other_total);
		        	series.push(checkIn_total);
		        	series.push(refund_total);
		        	series.push(period_day);
		        	
		        	options = {
	        			title : {
	        				subtext : '数据来自运营系统'
	        			},
	        			tooltip : {
	        				trigger : 'axis'
	        			},
	        			legend: {
	        		        data:['预约','维修','保洁','例行保洁','投诉','其他','入住','退租','处理周期']
	        		    },
	        			toolbox : {
	        				feature : {
	        					dataView : {
	        						show : true,
	        						readOnly : false
	        					},
	        					magicType : {
	        						show : true,
	        						type : [ 'line', 'bar' ]
	        					},
	        					restore : {
	        						show : true
	        					},
	        					saveAsImage : {
	        						show : true
	        					}
	        				}
	        			},
	        		    grid: {
	        		        left: '3%',
	        		        right: '4%',
	        		        bottom: '3%',
	        		        containLabel: true
	        		    },
	        		    xAxis : [
	        		        {
	        		            type : 'category',
	        		            data : labledata,
	        		            axisTick: {
	        		                alignWithLabel: true
	        		            }
	        		        }
	        		    ],
	        		    yAxis : [
	        		        {
	        		            type : 'value'
	        		        }
	        		    ],
	        		    series :series
	        		}
		        	myChart.setOption(options);		
		        	
		        var html ='<tr class="t1">'+
							'<td>类型</td>'+
							'<td>数量</td>'+
							'<td>类型</td>'+
							'<td>数量</td>'+
							'<td>类型</td>'+
							'<td>数量</td>'+
							'<td>类型</td>'+
							'<td>数量</td>'+
						'</tr>'+ 
			        	'<tr class="t" >'+
						'	<td >预约</td>'+
						'	<td >'+data[11].reserve_total+'</td>'+
						'	<td >维修</td>'+
						'	<td >'+data[11].maintenance_total+'</td>'+
						'	<td >保洁</td>'+
						'	<td >'+data[11].cleaning_total+'</td>'+							
						'	<td >例行保洁</td>'+
						'	<td >'+data[11].routine_cleaning_total+'</td>'+						
						'</tr>'+
						'<tr class="t">'+
						'	<td >投诉</td>'+
						'	<td >'+data[11].complaint_total+'</td>'+						
						'	<td >其他</td>'+
						'	<td >'+data[11].other_total+'</td>'+
						'	<td >入住</td>'+
						'	<td >'+data[11].checkIn_total+'</td>'+
						'	<td >退租</td>'+
						'	<td >'+data[11].refund_total+'</td>'+						
						'</tr>'+
				        '<tr class="t">'+				
				        '	<td >处理周期</td>'+
				        '	<td >'+data[11].period_day+'</td>'+							
				        '</tr>';
		        	
		        	$('#home_sc_table .t').remove();
		        	$('#home_sc_table .t1').remove();
		        	$('#home_sc_table').append(html);

				} else {
					common.alert(common.msg.error);
				}
				common.load.hide();
			}
		});
	},	

}
$(function(){
	order_statistics.init();
});