var home={
	/**
	 * 初始化数据信息
	 */
	init:function(){
		var date = new Date();
		var month = date.getMonth()+1;
		month = month < 10?"0"+month:month;
		$('#datetimepicker1').val(date.getFullYear()+"-"+month);
		this.getData();
		$('#datetimepicker1').datetimepicker({
			startView:'year',
			minView:'year',
			format:'yyyy-mm',
			autoclose:true
	    });
		
		$('#datetimepicker1').change(function(){
			home.getData();
		});
		$('a[href="#about-2"]').on('show.bs.tab', function(e) {
		  e.preventDefault();
		});
		
	},	
	
	// +---------------------------------------------------
	// | 字符串转成日期类型
	// | 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd
	// +---------------------------------------------------
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
	/**
	 * 获取数据
	 */
	getData:function(){
		var date = $('#datetimepicker1').val();
		var d = new Date(date);
		var labledata = [];
		// 向前推11个月
		for(var i=0;i<11;i++){
			if (i == 0) {
				labledata.push(date);
			}
			
            d.setMonth(d.getMonth() - 1);
            var m = d.getMonth() + 1;  
            m = m < 10 ? "0" + m : m;
            labledata.push(d.getFullYear() + "-" + m);
		}
		labledata.sort();
		$('#hometableMonth').text(date);
		common.load.load('数据生成中');
		
		common.ajax({
			url: common.root + '/report/mktView.do',
		    dataType: 'json',
		    data:{
		    	date:date
		    },
		    loadfun: function(isloadsucc, data){
		        if (isloadsucc) {
		        	common.log("labledata="+labledata);
		        	common.log(data);
		        	// 基于准备好的dom，初始化echarts实例
		        	var myChart = echarts.init(document.getElementById('main'));
		        	// [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月' ]
		        	// 图像表格下方的标签展示
		        	
		        	var lableName = [];
		        	var options = {};
		        	options.options = [];
		        	
	        		for(m in data.mrtgView){
	        			// 添加lable
	        			if(m.indexOf("_mp") >= 0 ){
	        				if(m.indexOf("全市_mp") >= 0){
	        				var name = m.replace('_mp','');
		        			lableName.push(m.replace('_mp',''));
		        			
		        			var ldata = {};
			        		ldata.title={text : name};
			        		ldata.series=[];
			        		var seriessfdata={};// 当前房源量
			        		seriessfdata.data=[];
			        		
			        		var serieszfdata={};// 当前出租量
			        		serieszfdata.data=[];
			        		
			        		var seriedysfdata={};// 当月出租
			        		seriedysfdata.data=[];
			        		
			        		var seriesdyzfdata={};// 当月收房
			        		seriesdyzfdata.data=[];
			        		
			        		// 出租均价
			        		var seriesczjedata={};
			        		seriesczjedata.data=[];
			        		
			        		// 收房均价
			        		var seriessfjedata={};
			        		seriessfjedata.data=[];
			        		
			        		// 租差比
			        		var seriesyratedate={};
			        		seriesyratedate.data=[]; 
			        		
			        		var zgsf = 0;
			        		var zgcz = 0;
			        		var zgbysf = 0;
			        		var zgbycz = 0;
			        		
			        		// 出租均价
			        		var zgbyczje = 0;
			        		
			        		// 收房均价
			        		var zgbysfje = 0;
			        		
			        		// 租差比
			        		var zgyrate = 0;
			        		 
			        		for(var i=0;i<12;i++){
			        			// 月收
			        			var valsf = data.mrtgView[name+"_mp"][i];
			        			zgsf += valsf;
			        			seriesdyzfdata.data.push({name:(i+1)+'月',value:valsf});
			        			
	        					// 月租
	        					var valcz = data.mrtgView[name+"_mr"][i];
	        					zgcz += valcz;
	        					seriedysfdata.data.push({name:(i+1)+'月',value:valcz});
	        					
	        					// 年收
	        					var valbysf = data.mrtgView[name+"_yp"][i];
	        					zgbysf += valbysf;
	        					seriessfdata.data.push({name:(i+1)+'月',value:valbysf});
	        					
	        					// 年租
	        					var valbycz = data.mrtgView[name+"_yr"][i];
	        					zgbycz += valbycz;
	        					serieszfdata.data.push({name:(i+1)+'月',value:valbycz});
	        					
	        					// 出租均价
	        					var valbyczje = data.mrtgView[name+"_yra"][i];
	        					zgbyczje += valbyczje;
	        					seriesczjedata.data.push({name:(i+1)+'月',value:valbyczje});
	        					
	        					// 收房均价
	        					var valbysfje = data.mrtgView[name+"_ycr"][i];
	        					zgbysfje += valbysfje;
	        					seriessfjedata.data.push({name:(i+1)+'月',value:valbysfje});	
	        					
	        					// 租差比
	        					var valyrate = data.mrtgView[name+"_yrate"][i];
	        					zgyrate += valyrate;
	        					seriesyratedate.data.push({name:(i+1)+'月',value:valyrate});		        					
			        		}
			        		
			        		var seriesdzsdata={};// 饼状图
			        		seriesdzsdata.data=[];
			        		seriesdzsdata.data.push({name:'当前房源量',value:zgbysf});
			        		seriesdzsdata.data.push({name:'当前出租量',value:zgbycz});
			        		seriesdzsdata.data.push({name:'当月收房',value:zgsf});
			        		seriesdzsdata.data.push({name:'当月出租',value:zgcz});
			        		
			        		seriesdzsdata.data.push({name:'出租均价',value:zgbyczje});
			        		seriesdzsdata.data.push({name:'收房均价',value:zgbysfje});
			        		seriesdzsdata.data.push({name:'租差比',value:zgyrate});
			        		
			        		ldata.series.push(seriessfdata);
			        		ldata.series.push(serieszfdata);
			        		ldata.series.push(seriedysfdata);
			        		ldata.series.push(seriesdyzfdata);

			        		ldata.series.push(seriesczjedata);
			        		ldata.series.push(seriessfjedata);
			        		ldata.series.push(seriesyratedate);
			        		
			        		options.options.push(ldata);
	        				}
		        		}
		        	}
	        		
	        		for(m in data.mrtgView){
	        			// 添加lable
	        			if(m.indexOf("_mp") >= 0 ){
	        				if(m.indexOf("全市_mp") >= 0){
	        					continue;
	        				}
	        				var name = m.replace('_mp','');
		        			lableName.push(m.replace('_mp',''));
		        			
		        			var ldata = {};
			        		ldata.title={text : name};
			        		ldata.series=[];
			        		var seriessfdata={};// 收房总数
			        		seriessfdata.data=[];
			        		
			        		var serieszfdata={};// 租房总数
			        		serieszfdata.data=[];
			        		
			        		var seriedysfdata={};// 当月租房
			        		seriedysfdata.data=[];
			        		
			        		var seriesdyzfdata={};// 当月收房
			        		seriesdyzfdata.data=[];
			        		
			        		// 出租均价
			        		var seriesczjedata={};
			        		seriesczjedata.data=[];
			        		
			        		// 收房均价
			        		var seriessfjedata={};
			        		seriessfjedata.data=[];
			        		
			        		// 租差比
			        		var seriesyratedate={};
			        		seriesyratedate.data=[]; 
			        		
			        		var zgsf = 0;
			        		var zgcz = 0;
			        		var zgbysf = 0;
			        		var zgbycz = 0;
			        		
			        		// 出租均价
			        		var zgbyczje = 0;
			        		
			        		// 收房均价
			        		var zgbysfje = 0;
			        		
			        		// 租差比
			        		var zgyrate = 0;
			        		 
			        		for(var i=0;i<12;i++){
			        			// 月收
			        			var valsf = data.mrtgView[name+"_mp"][i] == null ? 0 : data.mrtgView[name+"_mp"][i];
			        			zgsf += valsf;
			        			seriesdyzfdata.data.push({name:(i+1)+'月',value:valsf});
			        			
	        					// 月租
	        					var valcz = data.mrtgView[name+"_mr"][i] == null ? 0 : data.mrtgView[name+"_mr"][i];
	        					zgcz += valcz;
	        					seriedysfdata.data.push({name:(i+1)+'月',value:valcz});
	        					
	        					// 年收
	        					var valbysf = data.mrtgView[name+"_yp"][i] == null ? 0 : data.mrtgView[name+"_yp"][i];
	        					zgbysf += valbysf;
	        					seriessfdata.data.push({name:(i+1)+'月',value:valbysf});
	        					
	        					// 年租
	        					var valbycz = data.mrtgView[name+"_yr"][i] == null ? 0 : data.mrtgView[name+"_yr"][i];
	        					zgbycz += valbycz;
	        					serieszfdata.data.push({name:(i+1)+'月',value:valbycz});
	        					
	        					// 出租均价
	        					var valbyczje = data.mrtgView[name+"_yra"][i];
	        					zgbyczje += valbyczje;
	        					seriesczjedata.data.push({name:(i+1)+'月',value:valbyczje});
	        					
	        					// 收房均价
	        					var valbysfje = data.mrtgView[name+"_ycr"][i];
	        					zgbysfje += valbysfje;
	        					seriessfjedata.data.push({name:(i+1)+'月',value:valbysfje});	
	        					
	        					// 租差比
	        					var valyrate = data.mrtgView[name+"_yrate"][i];
	        					zgyrate += valyrate;
	        					seriesyratedate.data.push({name:(i+1)+'月',value:valyrate});		        					
			        		}
			        		
			        		var seriesdzsdata={};// 饼状图
			        		seriesdzsdata.data=[];
			        		seriesdzsdata.data.push({name:'当前房源量',value:zgbysf});
			        		seriesdzsdata.data.push({name:'当前出租量',value:zgbycz});
			        		seriesdzsdata.data.push({name:'当月收房',value:zgsf});
			        		seriesdzsdata.data.push({name:'当月出租',value:zgcz});
			        		
			        		seriesdzsdata.data.push({name:'出租均价',value:zgbyczje});
			        		seriesdzsdata.data.push({name:'收房均价',value:zgbysfje});
			        		seriesdzsdata.data.push({name:'租差比',value:zgyrate});
			        		
			        		ldata.series.push(seriessfdata);
			        		ldata.series.push(serieszfdata);
			        		ldata.series.push(seriedysfdata);
			        		ldata.series.push(seriesdyzfdata);

			        		ldata.series.push(seriesczjedata);
			        		ldata.series.push(seriessfjedata);
			        		ldata.series.push(seriesyratedate);
			        		
			        		options.options.push(ldata);
		        		}
		        	}	        		
	        		
		        	options.baseOption ={
	        			timeline : {
	        				// y: 0,
	        				axisType : 'category',
	        				// realtime: false,
	        				// loop: false,
	        				autoPlay : false,
	        				// currentIndex: 2,
	        				playInterval : 3000,
	        				// controlStyle: {
	        				// position: 'left'
	        				// },
	        				data : lableName,
	        				label : lableName
	        			},
	        			title : {
	        				subtext : '数据来自运营系统'
	        			},
	        			tooltip : {
	        				trigger : 'axis'
	        			},
	        			legend : {
	        				x : 'center',
	        				data : ['当前房源量','当前出租量','当月收房','当月出租','出租均价','收房均价','租差比'],
	        				selected : {
	        					'当前房源量' : true,
	        					'当前出租量' : true,
	        					'当月收房' : false,
	        					'当月出租' : false,
	        					'出租均价' : false,
	        					'收房均价' : false,
	        					'租差比' : false
	        				}
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
	        			calculable : false,
	        			grid : {
	        				top : 80,
	        				bottom : 100
	        			},
	        			xAxis : [ {
	        				'type' : 'category',
	        				'axisLabel' : {
	        					'interval' : 0
	        				},
	        				'data' : labledata,
	        				splitLine : {
	        					show : false
	        				}
	        			} ],
	        			yAxis : [ {
	        				type : 'value',
	        				name : '间数（个）'
	        			} ],
	        			series : [ {
	        				name : '当前房源量',
	        				type : 'bar'
	        			}, {
	        				name : '当前出租量',
	        				type : 'bar'
	        			}, {
	        				name : '当月出租',
	        				type : 'bar'
	        			}, {
	        				name : '当月收房',
	        				type : 'bar'
	        			}, {
	        				name : '出租均价',
	        				type : 'bar'
	        			}, {
	        				name : '收房均价',
	        				type : 'bar'
	        			}, {
	        				name : '租差比',
	        				type : 'bar'
	        			}
	        			, 
	        			{
	        				name : '占比',
	        				type : 'pie',
	        				center : [ '25%', '35%' ],
	        				radius : '20%'
	        			} 
	        			]
	        		};
		        	common.log(options);
		        	myChart.setOption(options);
		        	
		        	// 看有多少条数据信息，然后除以2就是循环次数
		        	var len = data.monthView.length;
		        	var cnt = Math.ceil(len/2);
		        	var html = "";
					var puryearPerColor;
					var purmonthPerColor;
					var puryearPer1Color;
					var purmonthPer1Color;
		        	for(var i=0;i< cnt ;i++){
		        		for(var j=0;j<3;j++){// 循环3次标签
		        			var title = '收房';
		        			if(j == 1){
		        				title = '出租';
		        			}else if(j == 2){
		        				title = '空置率'; 
		        			}
		        			var pur = "0间";
        					var purmonthPer = "0间";
        					var puryearPer = "0间";
        					var purmonthPerTtitle="0间";
        					var puryearPerTtitle="0间";
        					var pur1 = "0间";
        					var purmonthPer1 = "0间";
        					var puryearPer1 = "0间";
        					var purmonthPerTtitle1="0间";
        					var puryearPerTtitle1="0间";

	    					 puryearPerColor='';
	    					 purmonthPerColor='';
	    					 puryearPer1Color='';
	    					 purmonthPer1Color='';
        					
		        			if(j==0){
        						puryearPer = data.monthView[i].puryearPer;
        						if(puryearPer==null||puryearPer=='null'||puryearPer==undefined||puryearPer=='undefined') {
        							puryearPer=0;
        						}
        						if(parseFloat(puryearPer)<0) {
        							puryearPerColor='color="red"';
        						} 
        						if (parseFloat(puryearPer)>100) {
        							puryearPerColor='color="blue"';
        						}
        						if(parseFloat(puryearPer)>1000) {
        							puryearPer=">1000";
        						}
        						
        						puryearPer=puryearPer+"%"
        						
        						purmonthPer=data.monthView[i].purmonthPer;
        						if(purmonthPer==null||purmonthPer=='null'||purmonthPer==undefined||purmonthPer=='undefined') {
        							purmonthPer=0;
        						}
        						if(parseFloat(purmonthPer)<0) {
        							purmonthPerColor='color="red"';
        						}
        						if(parseFloat(purmonthPer)>100) {
        							purmonthPerColor='color="blue"';
        						}
        						if(parseFloat(purmonthPer)>1000) {
        							purmonthPer=">1000";
        						}       						
        						purmonthPer=purmonthPer+"%";
        						
        						pur=data.monthView[i].pur;
        						if(pur==null||pur=='null'||pur==undefined||pur=='undefined'||pur==0||pur=="0") {
        							pur=0+"(间)";
        						} else {
        							pur=pur+"(间)"
        						}
        						
        						purmonth=data.monthView[i].purmonth;
        						if(purmonth==null||purmonth=='null'||purmonth==undefined||purmonth=='undefined') {
        							purmonth=0;
        						}
        						purmonthPerTtitle = "（本月"+pur+"-上月"+purmonth+"(间)）/上月"+purmonth+"(间) X 100%";
        						puryear=data.monthView[i].puryear;
        						if(puryear==null||puryear=='null'||puryear==undefined||puryear=='undefined') {
        							puryear=0;
        						}
        						puryearPerTtitle = "本月"+pur+"/上年本月"+puryear+"(间) X 100%";
        						
        						if(data.monthView[i+cnt] != undefined){
        							puryearPer1 = data.monthView[i+cnt].puryearPer;
            						if(parseFloat(puryearPer1)<0) {
            							puryearPer1Color = 'color="red"';
            						}
            						if(parseFloat(puryearPer1)>100) {
            							puryearPer1Color = 'color="blue"';
            						}
            						if(parseFloat(puryearPer1)>1000) {
            							puryearPer1 = ">1000";
            						} 
            						puryearPer1=puryearPer1 + "%";
            						
        							purmonthPer1 = data.monthView[i+cnt].purmonthPer;
            						if(parseFloat(purmonthPer1)<0) {
            							purmonthPer1Color='color="red"';
            						}
            						if(parseFloat(purmonthPer1)>100) {
            							purmonthPer1Color='color="blue"';
            						}
            						if(parseFloat(purmonthPer1)>1000) {
            							purmonthPer1=">1000";
            						} 
            						purmonthPer1=purmonthPer1+"%";
        							
        							pur1=data.monthView[i+cnt].pur;
        							if(pur1==null||pur1=='null'||pur1==undefined||pur1=='undefined'||pur1==0||pur1=="0") {
        								pur1=0+"(间)";
        							} else {
        								pur1=pur1+"(间)";
        							}
        							
        							purmonth=data.monthView[i+cnt].purmonth;
        							if(purmonth==null||purmonth=='null'||purmonth==undefined||purmonth=='undefined') {
        								purmonth=0;
        							}
        							puryear=data.monthView[i+cnt].puryear;
        							if(puryear==null||puryear=='null'||puryear==undefined||puryear=='undefined') {
        								puryear=0;
        							}
        							purmonthPerTtitle1 = "（本月"+pur1+"-上月"+purmonth+"(间)）/上月"+purmonth+"(间) X 100%";
            						puryearPerTtitle1 = "本月"+pur1+"/上年本月"+puryear+"(间) X 100%";
        						}
	        					if(data.monthView[i+cnt] == undefined){
		        					html += '<tr class="t">'+
										'	<td rowspan="3">'+data.monthView[i].area_name+'</td>'+
										'	<td>'+title+'</td>'+
										'	<td><font>'+pur+'</font></td>'+
										'	<td title="'+puryearPerTtitle+'" ><font '+puryearPerColor+'>'+puryearPer+'</font></td>'+
										'	<td title="'+purmonthPerTtitle+'"><font '+purmonthPerColor+'>'+purmonthPer+'</font></td>'+
										'	<td rowspan="3" colspan="5" ></td>'+
										'</tr>';
		        				}else{
		        					html += '<tr class="t">'+
										'	<td rowspan="3">'+data.monthView[i].area_name+'</td>'+
										'	<td>'+title+'</td>'+
										'	<td>'+pur+'</td>'+
										'	<td title="'+puryearPerTtitle+'" ><font '+puryearPerColor+'>'+puryearPer+'</font></td>'+
										'	<td title="'+purmonthPerTtitle+'"><font '+purmonthPerColor+'>'+purmonthPer+'</font></td>'+
										'	<td rowspan="3">'+data.monthView[i+cnt].area_name+'</td>'+
										'	<td>'+title+'</td>'+
										'	<td>'+pur1+'</td>'+
										'	<td title="'+puryearPerTtitle1+'"><font '+puryearPer1Color+'>'+puryearPer1+'</font></td>'+
										'	<td title="'+purmonthPerTtitle1+'"><font '+purmonthPer1Color+'>'+purmonthPer1+'</font></td>'+
										'</tr>';
		        				}
		        			}else{
	        					if(j==1){
	        						pur=data.monthView[i].rent;
	        						if(pur==null||pur=='null'||pur==undefined||pur=='undefined'||pur==0||pur=="0") {
	        							pur=0+"(间)";
	        						} else {
	        							pur=pur+"(间)"
	        						}
        							puryearPer = data.monthView[i].rentyearPer;
            						if(parseFloat(puryearPer)<0) {
            							puryearPerColor = 'color="red"';
            						}
            						if(parseFloat(puryearPer)>100) {
            							puryearPerColor = 'color="blue"';
            						}
            						if(parseFloat(puryearPer)>1000) {
            							puryearPer = ">1000";
            						} 
            						puryearPer=puryearPer + "%";
            						
	        						purmonthPer = data.monthView[i].rentmonthPer;
            						if(parseFloat(purmonthPer)<0) {
            							purmonthPerColor = 'color="red"';
            						}
            						if(parseFloat(purmonthPer)>100) {
            							purmonthPerColor = 'color="blue"';
            						}
            						if(parseFloat(purmonthPer)>1000) {
            							purmonthPer = ">1000";
            						} 
            						purmonthPer=purmonthPer + "%";
            						
            						rentmonth=data.monthView[i].rentmonth;
        							if(rentmonth==null||rentmonth=='null'||rentmonth==undefined||rentmonth=='undefined') {
        								rentmonth=0;
        							}
        							rentyear=data.monthView[i].rentyear;
        							if(rentyear==null||rentyear=='null'||rentyear==undefined||rentyear=='undefined') {
        								rentyear=0;
        							}
	        						purmonthPerTtitle = "（本月"+pur+"-上月"+rentmonth+"(间)）/上月"+rentmonth+"(间) X 100%";
	        						puryearPerTtitle = "本月"+pur+"/上年本月"+rentyear+"(间) X 100%";
	        						if(data.monthView[i+cnt] != undefined){
	        							pur1=data.monthView[i+cnt].rent;
	        							if(pur1==null||pur1=='null'||pur1==undefined||pur1=='undefined'||pur1==0||pur1=="0") {
	        								pur1=0+"(间)";
		        						} else {
		        							pur1=pur1+"(间)"
		        						}
	        							puryearPer1 = data.monthView[i+cnt].rentyearPer;
	            						if(parseFloat(puryearPer1)<0) {
	            							puryearPer1Color = 'color="red"';
	            						}
	            						if(parseFloat(puryearPer1)>100) {
	            							puryearPer1Color = 'color="blue"';
	            						}
	            						if(parseFloat(puryearPer1)>1000) {
	            							puryearPer1 = ">1000";
	            						} 
	            						puryearPer1=puryearPer1 + "%";
	            						
	        							purmonthPer1 = data.monthView[i+cnt].rentmonthPer;
	            						if(parseFloat(purmonthPer1)<0) {
	            							purmonthPer1Color = 'color="red"';
	            						}
	            						if(parseFloat(purmonthPer1)>100) {
	            							purmonthPer1Color = 'color="blue"';
	            						}
	            						if(parseFloat(purmonthPer1)>1000) {
	            							purmonthPer1 = ">1000";
	            						} 
	            						purmonthPer1=purmonthPer1 + "%";
	        							
	            						rentmonth=data.monthView[i+cnt].rentmonth;
	        							if(rentmonth==null||rentmonth=='null'||rentmonth==undefined||rentmonth=='undefined') {
	        								rentmonth=0;
	        							}
	            						
	        							rentyear=data.monthView[i+cnt].rentyear;
	        							if(rentyear==null||rentyear=='null'||rentyear==undefined||rentyear=='undefined') {
	        								rentyear=0;
	        							}
	        							
	        							purmonthPerTtitle1 = "（本月"+pur1+"-上月"+rentmonth+"(间)）/上月"+rentmonth+"(间) X 100%";
	            						puryearPerTtitle1 = "本月"+pur1+"/上年本月"+rentyear+"(间) X 100%";
	        						}
	        						if(data.monthView[i+cnt] == undefined){
			        					html += '<tr class="t" >'+
											'	<td>'+title+'</td>'+
											'	<td>'+pur+'</td>'+
											'	<td title="'+puryearPerTtitle+'" ><font '+puryearPerColor+'>'+puryearPer+'</font></td>'+
											'	<td title="'+purmonthPerTtitle+'"><font '+purmonthPerColor+'>'+purmonthPer+'</font></td>'+
											'</tr>';
			        				}else{
			        					html += '<tr class="t">'+
											'	<td>'+title+'</td>'+
											'	<td>'+pur+'</td>'+
											'	<td title="'+puryearPerTtitle+'"><font '+puryearPerColor+'>'+puryearPer+'</font></td>'+
											'	<td title="'+purmonthPerTtitle+'" ><font '+purmonthPerColor+'>'+purmonthPer+'</font></td>'+
											'	<td>'+title+'</td>'+
											'	<td>'+pur1+'</td>'+
											'	<td title="'+puryearPerTtitle1+'" ><font '+puryearPer1Color+'>'+puryearPer1+'</font></td>'+
											'	<td title="'+purmonthPerTtitle1+'"><font '+purmonthPer1Color+'>'+purmonthPer1+'</font></td>'+
											'</tr>';
			        				}
	        					}else if(j == 2){
	        						if(data.monthView[i] != undefined){
	        							pur=data.monthView[i].kp;
	        							if(pur==null||pur=='null'||pur==undefined||pur=='undefined'||pur==0||pur=="0") {
	        								pur=0+"%";
		        						} else {
		        							pur=pur+"%"
		        						}
	        						}
	        						if(data.monthView[i+cnt] != undefined){
	        							pur1 = data.monthView[i+cnt].kp;
	        							if(pur1==null||pur1=='null'||pur1==undefined||pur1=='undefined'||pur1==0||pur1=="0") {
	        								pur1=0+"%";
		        						} else {
		        							pur1=pur1+"%"
		        						}
	        						}
	        						if(data.monthView[i+cnt] == undefined){
			        					html += '<tr class="t">'+
											'	<td>'+title+'</td>'+
											'	<td  style="text-align: center;" colspan="3">'+pur+'</td>'+
											'</tr>';
			        				}else{
			        					html += '<tr class="t">'+
											'	<td>'+title+'</td>'+
											'	<td style="text-align: center;"  colspan="3" >'+pur+'</td>'+
											'	<td>'+title+'</td>'+
											'	<td style="text-align: center;"  colspan="3">'+pur1+'</td>'+
											'</tr>';
			        				}
	        					} 
		        			}
		        		}
		        	}
		        	$('#home_sc_table .t').remove();
		        	$('#home_sc_table').append(html);
		        }else{
		        	common.alert(common.msg.error);
		        }
		        common.load.hide();
		    }
		});
	}
}
$(function(){
	home.init();
});