var cost={
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
		this.getCostView();
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
			var category = $("#category").val();
			if (category != '') {
				cost.getCostType();	
			} else {
				// 分类为空，查询全部
				cost.getCostView();
			}
		});
		
		// 选择服务类型
		$("#cost_type").change(function(){
			var category = $("#category").val();
			if (category != '') {
				cost.getCostType();	
			} else {
				// 分类为空，查询全部
				cost.getCostView();
			}
		});
		
		// 选择服务状态
		$("#category").change(function(){
			var category = $("#category").val();
			if (category != '') {
				cost.getCostType();	
			} else {
				// 分类为空，查询全部
				cost.getCostView();
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
	
	getCostType : function() {
		// 查询时间
		var time = $("#queryTime").val();
		// 财务类型
		var cost_type = $("#cost_type").val();
		// 财务分类
		var category = $("#category").val();
		
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
			url : common.root + '/report/prView.do',
			dataType : 'json',
			data : {
				date : time,
				cost_type : cost_type,
				category : category
			},
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					console.log(data);
		        	var myChart = echarts.init(document.getElementById('main'));
		        	//图像表格下方的标签展示
		        	var lableName = [];
		        	var options = {};
		        	options.options = [];
		        	
		        	// 账款
		        	var cost = {};
		        	// 租金
		        	var lease_cost = {};
		        	// 物业费
		        	var estate_cost = {};
		        	// 退款
		        	var refund_cost = {};
		        	// 待缴费
		        	var wait_cost = {};
		        	// 押金
		        	var deposit_cost = {};
		        	// 服务费
		        	var service_cost = {};
		        	// 家居
		        	var home_cost = {};
		        	// 家电
		        	var appliance_cost = {};
		        	// 装修
		        	var decorate_cost = {};
		        	// 其他
		        	var other_cost = {};
		        	// 水费
		        	var water_cost = {};
		        	// 电费
		        	var electric_cost = {};
		        	// 燃气费
		        	var gas_cost = {};
		        	
		        	// 类型
		        	var type;
		        	// 金额数量
		        	var total;
		        	
		    		var legendName = [];
		        	var cost_name = "";
		        	if (cost_type == 1) { // 账款
		        		cost.name='应收账款';
		        		cost_name = '应收账款'
		        	} else {
		        		cost.name='应付账款';
		        		cost_name = '应付账款';
		        	}   		
		        	
		    		if (category == 1) { // 租金
		    			legendName = ['租金'];
		    			type = "租金";
		    			total = data[11].lease_cost;
		    		}
		    		if (category == 2) { // 物业费
		    			legendName = ['物业费'];
		    			type = "物业费";
		    			total = data[11].estate_cost;
		    		}
		    		if (category == 3) { // 退款
		    			legendName = ['退款'];
		    			type = "退款";
		    			total = data[11].refund_cost;
		    		}
		    		if (category == 4) { // 待缴费
		    			legendName = ['待缴费'];
		    			type = "待缴费";
		    			total = data[11].wait_cost;
		    		}
		    		if (category == 5) { // 押金
		    			legendName = ['押金'];
		    			type = "押金";
		    			total = data[11].deposit_cost;		    			
		    		}
		    		if (category == 6) { // 服务费
		    			legendName = ['服务费'];
		    			type = "服务费";
		    			total = data[11].service_cost;		    			
		    		}
		    		if (category == 7) { // 家居
		    			legendName = ['家居'];
		    			type = "家居";
		    			total = data[11].home_cost;			    			
		    		}
		    		if (category == 8) { // 家电
		    			legendName = ['家电'];
		    			type = "家电";
		    			total = data[11].appliance_cost;			    			
		    		}
		    		if (category == 9) { // 装修
		    			legendName = ['装修'];
		    			type = "装修";
		    			total = data[11].decorate_cost;		    			
		    		}
		    		if (category == 10) { // 其他
		    			legendName = ['其他'];
		    			type = "其他";
		    			total = data[11].other_cost;		    			
		    		}
		    		if (category == 11) { // 水费
		    			legendName = ['水费'];
		    			type = "水费";
		    			total = data[11].water_cost;		    			
		    		}
		    		if (category == 11) { // 水费
		    			legendName = ['水费'];
		    			type = "水费";
		    			total = data[11].water_cost;		    			
		    		}
		    		if (category == 12) { // 电费
		    			legendName = ['电费'];
		    			type = "电费";
		    			total = data[11].electric_cost;		    			
		    		}
		    		if (category == 13) { // 燃气费
		    			legendName = ['燃气费'];
		    			type = "燃气费";
		    			total = data[11].gas_cost;		    			
		    		}
		    		legendName.push(cost_name);
		    		
		        	lease_cost.name='租金';
		        	estate_cost.name='物业费';
		        	refund_cost.name='退款';
		        	wait_cost.name='待缴费';
		        	deposit_cost.name='押金';
		        	service_cost.name='服务费';
		        	home_cost.name='家居';
		        	appliance_cost.name='家电';
		        	decorate_cost.name='装修';
		        	other_cost.name='其他';
		        	water_cost.name='水费';
		        	electric_cost.name='电费';
		        	gas_cost.name='燃气费';
		        	
		        	cost.type='bar';
		        	lease_cost.type='bar';
		        	estate_cost.type='bar';
		        	refund_cost.type='bar';
		        	wait_cost.type='bar';
		        	deposit_cost.type='bar';
		        	service_cost.type='bar';
		        	home_cost.type='bar';
		        	appliance_cost.type='bar';
		        	decorate_cost.type='bar';
		        	other_cost.type='bar';
		        	water_cost.type='bar';
		        	electric_cost.type='bar';
		        	gas_cost.type='bar';
		        	
		        	var costdata = [];
		        	var lease_costdata = [];
		        	var estate_costdata = [];
		        	var refund_costdata = [];
		        	var wait_costdata = [];
		        	var deposit_costdata = [];
		        	var service_costdata = [];
		        	var home_costdata = [];
		        	var appliance_costdata = [];
		        	var decorate_costdata = [];
		        	var other_costdata = [];
		        	var water_costdata = [];
		        	var electric_costdata = [];
		        	var gas_costdata = [];
		        	
		        	var costs = 0;
		        	var lease_costs = 0;
		        	var estate_costs = 0;
		        	var refund_costs = 0;
		        	var wait_costs = 0;
		        	var deposit_costs = 0;
		        	var service_costs = 0;
		        	var home_costs = 0;
		        	var appliance_costs = 0;
		        	var decorate_costs = 0;
		        	var other_costs = 0;
		        	var water_costs = 0;
		        	var electric_costs = 0;
		        	var gas_costs = 0;
		        	
		        	for(var i=0;i<data.length;i++){
		        		costs += data[i].cost;
		        		lease_costs += data[i].lease_cost;
		        		estate_costs += data[i].estate_cost;
		        		refund_costs += data[i].refund_cost;
		        		wait_costs += data[i].wait_cost;
		        		deposit_costs += data[i].deposit_cost;
		        		service_costs += data[i].service_cost;
		        		home_costs += data[i].home_cost;
		        		appliance_costs += data[i].appliance_cost;
		        		decorate_costs += data[i].decorate_cost;
		        		other_costs += data[i].other_cost;
		        		water_costs += data[i].water_cost;
		        		electric_costs += data[i].electric_cost;
		        		gas_costs += data[i].gas_cost;
		        		
		        		costdata.push(data[i].cost);
		        		lease_costdata.push(data[i].lease_cost);
		        		estate_costdata.push(data[i].estate_cost);
		        		refund_costdata.push(data[i].refund_cost);
		        		wait_costdata.push(data[i].wait_cost);
		        		deposit_costdata.push(data[i].deposit_cost);
		        		service_costdata.push(data[i].service_cost);
		        		home_costdata.push(data[i].home_cost);
		        		appliance_costdata.push(data[i].appliance_cost);
		        		decorate_costdata.push(data[i].decorate_cost);
		        		other_costdata.push(data[i].other_cost);
		        		water_costdata.push(data[i].water_cost);
		        		electric_costdata.push(data[i].electric_cost);
		        		gas_costdata.push(data[i].gas_cost);
		        	}
		        	
		        	cost.data=costdata;
		        	lease_cost.data=lease_costdata;
		        	estate_cost.data=estate_costdata;
		        	refund_cost.data=refund_costdata;
		        	wait_cost.data=wait_costdata;
		        	deposit_cost.data=deposit_costdata;
		        	service_cost.data=service_costdata;
		        	home_cost.data=home_costdata;
		        	appliance_cost.data=appliance_costdata;
		        	decorate_cost.data=decorate_costdata;
		        	other_cost.data=other_costdata;
		        	water_cost.data=water_costdata;
		        	electric_cost.data=electric_costdata;
		        	gas_cost.data=gas_costdata;
		        	
		        	var series = [];
		        	series.push(cost);
		    		if (category == 1) {
		    			series.push(lease_cost);
		    		}
		    		if (category == 2) {
		    			series.push(estate_cost);
		    		}
		    		if (category == 3) {
		    			series.push(refund_cost);
		    		}
		    		if (category == 4) {
		    			series.push(wait_cost);
		    		}
		    		if (category == 5) {
		    			series.push(deposit_cost);
		    		}
		    		if (category == 6) {
		    			series.push(service_cost);
		    		}
		    		if (category == 7) {
		    			series.push(home_cost);
		    		}	        	
		    		if (category == 8) {
		    			series.push(appliance_cost);
		    		}	        	
		    		if (category == 9) {
		    			series.push(decorate_cost);
		    		}	        	
		    		if (category == 10) {
		    			series.push(other_cost);
		    		}	        	
		    		if (category == 11) {
		    			series.push(water_cost);
		    		}	        	
		    		if (category == 12) {
		    			series.push(electric_cost);
		    		}	        	
		    		if (category == 13) {
		    			series.push(gas_cost);
		    		}	        	
		        	
		        	options = {
	        			title : {
	        				subtext : '数据来自运营系统（单位：万元）'
	        			},
	        			tooltip : {
	        				trigger : 'axis'
	        			},
	        			legend: {
	        		        data : legendName
	        		    },
	        			toolbox : {
	        				feature : {
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
								'<td>金额</td>'+
								'<td>类型</td>'+
								'<td>金额</td>'+
								'</tr>'+ 
						        '<tr class="t" >'+
						        '	<td >'+cost_name+'</td>'+
						        '	<td >'+data[11].cost+'</td>'+								        
								'	<td >'+type+'</td>'+
								'	<td >'+total+'</td>'+
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
	
	// 财务报表数据（查询全部）
	getCostView : function() {
		// 查询时间
		var time = $("#queryTime").val();
		// 财务类型
		var cost_type = $("#cost_type").val();
		// 财务分类
		var category = $("#category").val();
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
			url : common.root + '/report/prView.do',
			dataType : 'json',
			data : {
				date : time,
				cost_type : cost_type,
				category : category
			},
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					console.log(data);
		        	var myChart = echarts.init(document.getElementById('main'));
		        	//图像表格下方的标签展示
		        	var lableName = [];
		        	var options = {};
		        	options.options = [];
		        	
		        	// 账款
		        	var cost = {};
		        	// 租金
		        	var lease_cost = {};
		        	// 物业费
		        	var estate_cost = {};
		        	// 退款
		        	var refund_cost = {};
		        	// 待缴费
		        	var wait_cost = {};
		        	// 押金
		        	var deposit_cost = {};
		        	// 服务费
		        	var service_cost = {};
		        	// 家居
		        	var home_cost = {};
		        	// 家电
		        	var appliance_cost = {};
		        	// 装修
		        	var decorate_cost = {};
		        	// 其他
		        	var other_cost = {};
		        	// 水费
		        	var water_cost = {};
		        	// 电费
		        	var electric_cost = {};
		        	// 燃气费
		        	var gas_cost = {};
		        	
		        	var cost_name = "";
		        	if (cost_type == 1) {
		        		cost.name='应收账款';
		        		cost_name = '应收账款'
		        	} else {
		        		cost.name='应付账款';
		        		cost_name = '应付账款';
		        	}
		        	
		        	lease_cost.name='租金';
		        	estate_cost.name='物业费';
		        	refund_cost.name='退款';
		        	wait_cost.name='待缴费';
		        	deposit_cost.name='押金';
		        	service_cost.name='服务费';
		        	home_cost.name='家居';
		        	appliance_cost.name='家电';
		        	decorate_cost.name='装修';
		        	other_cost.name='其他';
		        	water_cost.name='水费';
		        	electric_cost.name='电费';
		        	gas_cost.name='燃气费';
		        	
		        	cost.type='bar';
		        	lease_cost.type='bar';
		        	estate_cost.type='bar';
		        	refund_cost.type='bar';
		        	wait_cost.type='bar';
		        	deposit_cost.type='bar';
		        	service_cost.type='bar';
		        	home_cost.type='bar';
		        	appliance_cost.type='bar';
		        	decorate_cost.type='bar';
		        	other_cost.type='bar';
		        	water_cost.type='bar';
		        	electric_cost.type='bar';
		        	gas_cost.type='bar';
		        	
		        	var costdata = [];
		        	var lease_costdata = [];
		        	var estate_costdata = [];
		        	var refund_costdata = [];
		        	var wait_costdata = [];
		        	var deposit_costdata = [];
		        	var service_costdata = [];
		        	var home_costdata = [];
		        	var appliance_costdata = [];
		        	var decorate_costdata = [];
		        	var other_costdata = [];
		        	var water_costdata = [];
		        	var electric_costdata = [];
		        	var gas_costdata = [];
		        	
		        	var costs = 0;
		        	var lease_costs = 0;
		        	var estate_costs = 0;
		        	var refund_costs = 0;
		        	var wait_costs = 0;
		        	var deposit_costs = 0;
		        	var service_costs = 0;
		        	var home_costs = 0;
		        	var appliance_costs = 0;
		        	var decorate_costs = 0;
		        	var other_costs = 0;
		        	var water_costs = 0;
		        	var electric_costs = 0;
		        	var gas_costs = 0;
		        	
		        	for(var i=0;i<data.length;i++){
		        		costs += data[i].cost;
		        		lease_costs += data[i].lease_cost;
		        		estate_costs += data[i].estate_cost;
		        		refund_costs += data[i].refund_cost;
		        		wait_costs += data[i].wait_cost;
		        		deposit_costs += data[i].deposit_cost;
		        		service_costs += data[i].service_cost;
		        		home_costs += data[i].home_cost;
		        		appliance_costs += data[i].appliance_cost;
		        		decorate_costs += data[i].decorate_cost;
		        		other_costs += data[i].other_cost;
		        		water_costs += data[i].water_cost;
		        		electric_costs += data[i].electric_cost;
		        		gas_costs += data[i].gas_cost;
		        		
		        		costdata.push(data[i].cost);
		        		lease_costdata.push(data[i].lease_cost);
		        		estate_costdata.push(data[i].estate_cost);
		        		refund_costdata.push(data[i].refund_cost);
		        		wait_costdata.push(data[i].wait_cost);
		        		deposit_costdata.push(data[i].deposit_cost);
		        		service_costdata.push(data[i].service_cost);
		        		home_costdata.push(data[i].home_cost);
		        		appliance_costdata.push(data[i].appliance_cost);
		        		decorate_costdata.push(data[i].decorate_cost);
		        		other_costdata.push(data[i].other_cost);
		        		water_costdata.push(data[i].water_cost);
		        		electric_costdata.push(data[i].electric_cost);
		        		gas_costdata.push(data[i].gas_cost);
		        	}
		        	
		        	cost.data=costdata;
		        	lease_cost.data=lease_costdata;
		        	estate_cost.data=estate_costdata;
		        	refund_cost.data=refund_costdata;
		        	wait_cost.data=wait_costdata;
		        	deposit_cost.data=deposit_costdata;
		        	service_cost.data=service_costdata;
		        	home_cost.data=home_costdata;
		        	appliance_cost.data=appliance_costdata;
		        	decorate_cost.data=decorate_costdata;
		        	other_cost.data=other_costdata;
		        	water_cost.data=water_costdata;
		        	electric_cost.data=electric_costdata;
		        	gas_cost.data=gas_costdata;
		        	
		        	var series = [];
		        	series.push(cost);
		        	series.push(lease_cost);
		        	series.push(estate_cost);
		        	series.push(refund_cost);
		        	series.push(wait_cost);
		        	series.push(deposit_cost);
		        	series.push(service_cost);
		        	series.push(home_cost);
		        	series.push(appliance_cost);
		        	series.push(decorate_cost);
		        	series.push(other_cost);
		        	series.push(water_cost);
		        	series.push(electric_cost);
		        	series.push(gas_cost);
		        	
		        	options = {
	        			title : {
	        				subtext : '数据来自运营系统（单位：万元）'
	        			},
	        			tooltip : {
	        				trigger : 'axis'
	        			},
	        			legend: {
	        		        data:[cost_name,'租金','物业费','退款','待缴费','押金','服务费','家居','家电','装修','其他','水费','电费','燃气费']
	        		    },
	        			toolbox : {
	        				feature : {
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
							'<td>金额</td>'+
							'<td>类型</td>'+
							'<td>金额</td>'+
							'<td>类型</td>'+
							'<td>金额</td>'+
							'<td>类型</td>'+
							'<td>金额</td>'+
						'</tr>'+ 
			        	'<tr class="t" >'+
						'	<td >'+cost_name+'</td>'+
						'	<td >'+data[11].cost+'</td>'+
						'	<td >租金</td>'+
						'	<td >'+data[11].lease_cost+'</td>'+
						'	<td >物业费</td>'+
						'	<td >'+data[11].estate_cost+'</td>'+							
						'	<td >退款</td>'+
						'	<td >'+data[11].refund_cost+'</td>'+
						'</tr>'+
						'<tr class="t">'+
						'	<td >待缴费</td>'+
						'	<td >'+data[11].wait_cost+'</td>'+						
						'	<td >押金</td>'+
						'	<td >'+data[11].deposit_cost+'</td>'+
						'	<td >服务费</td>'+
						'	<td >'+data[11].service_cost+'</td>'+
						'	<td >家居</td>'+
						'	<td >'+data[11].home_cost+'</td>'+						
						'</tr>'+
				        '<tr class="t">'+				
				        '	<td >家电</td>'+
				        '	<td >'+data[11].appliance_cost+'</td>'+							
				        '	<td >装修</td>'+
				        '	<td >'+data[11].decorate_cost+'</td>'+							
				        '	<td >其他</td>'+
				        '	<td >'+data[11].other_cost+'</td>'+							
				        '	<td >水费</td>'+
				        '	<td >'+data[11].water_cost+'</td>'+							
				        '</tr>' +
						'<tr class="t">'+
						'	<td >电费</td>'+
						'	<td >'+data[11].electric_cost+'</td>'+						
						'	<td >燃气费</td>'+
						'	<td >'+data[11].gas_cost+'</td>'+				
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
	cost.init();
});