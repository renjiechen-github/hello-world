var finance = {
    /**
     * 折现图
     */
    zxt: function(data1){
		//数据信息
        var pv = [], ip = []; 
		for(var i=0;i<30;i++){
			if(data1.sr[i] != undefined){
				pv.push(data1.sr[i]); 	
			}else{
				pv.push(0);
			}
			if(data1.zc[i] != undefined){
				ip.push(data1.zc[i]); 	
			}else{
				ip.push(0);
			}
		}
        var data = [{
            name: '收入',
            value: pv,
            color: '#aad0db',
            line_width: 2
        }, {
            name: '支出',
            value: ip,
            color: '#f68f70',
            line_width: 2
        }];
        
        //创建x轴标签文本   
        var labels = [];
		for(var i=0;i<30;i++){
			labels.push(data1.lable[""+i+""].substr(8));
		}
        var chart = new iChart.LineBasic2D({
            render: 'main-chart',
            data: data,
            align: 'center',
            title: '',
            subtitle: '',
            footnote: '',
            width: $('.main-chart').width(),
            height: 400,
            background_color: '#FEFEFE',
            tip: {
                enable: true,
                shadow: true,
                move_duration: 400,
                border: {
                    enable: true,
                    radius: 5,
                    width: 2,
                    color: '#3f8695'
                },
                listeners: {
                    //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
                    parseText: function(tip, name, value, text, i){
                        return name + ":" + value + "<i class='fa fa-yen'></i>";
                    }
                }
            },
            tipMocker: function(tips, i){
                return "<div style='font-weight:600'>" +
                data1.lable[Math.floor(i )] +
                "</div>" +
                tips.join("<br/>");
            },
            legend: {
                enable: true,
                row: 1,//设置在一行上显示，与column配合使用
                column: 'max',
                valign: 'top',
                sign: 'bar',
                background_color: null,//设置透明背景
                offsetx: -80,//设置x轴偏移，满足位置需要
                border: true
            },
            crosshair: {
                enable: true,
                line_color: '#62bce9'//十字线的颜色
            },
            sub_option: {
                label: false,
                point_size: 10
            },
            coordinate: {
                axis: {
                    color: '#dcdcdc',
                    width: 1
                },
                scale: [{
                    position: 'left',
                    start_scale: 0,
                    end_scale: 500000,
                    scale_space: 50000,
                    scale_size: 4,
                    scale_color: '#9f9f9f',
					shadow:true
                }, {
                    position: 'bottom',
                    labels: labels
                }]
            }
        });
        
        //开始画图
        chart.draw();
        
        $(window).on('resize', function(){
            //chart1.push("crosshair",null);//设置为null则每次重新计算柱子宽度
            var w = $('#main-chart').width();
            var h = $('#main-chart').height();
            chart.resize(parseInt(w), parseInt(h));
			chart.setUp();
			chart.draw();
        });
    },
    /**
     * 饼状图
     * @param {Object} data
     */
    bzt: function(data){
        /**
         * 饼状图
         */
        Morris.Donut({
            element: 'graph-donut',
            data: [{
                value: 40,
                label: '待收入',
                formatted: 'at least 70%'
            }, {
                value: 30,
                label: '待支出',
                formatted: 'approx. 15%'
            }, {
                value: 20,
                label: '实际收入',
                formatted: 'approx. 10%'
            }, {
                value: 10,
                label: '实际支出',
                formatted: 'at most 99.99%'
            }],
            backgroundColor: false,
            labelColor: '#fff',
            colors: ['#4acacb', '#6a8bc0', '#5ab6df', '#fe8676'],
            formatter: function(x, data){
                return data.formatted;
            },
			width:343
        });
    },
    /**
     * 日历
     */
    calendars: function(data){
        /**
         * 日历控件
         */
        var calendars = {};
        var thisMonth = moment().format('YYYY-MM');
        
        var eventArray1 = [{
            startDate: thisMonth + '-10',
            endDate: thisMonth + '-14',
            title: 'Multi-Day Event'
        }, {
            startDate: thisMonth + '-21',
            endDate: thisMonth + '-23',
            title: 'Another Multi-Day Event'
        }];
        calendars.clndr1 = $('.cal1').clndr({
            template: "<div class='clndr-controls'>" +
            "<div class='clndr-control-button'><span class='clndr-previous-button'><i class='fa fa-chevron-left'></i></span></div><div class='month'><span><%= month %></span> <%= year %></div><div class='clndr-control-button leftalign'><span class='clndr-next-button'><i class='fa fa-chevron-right'></i></span></div>" +
            "</div>" +
            "<table class='clndr-table' border='0' cellspacing='0' cellpadding='0'>" +
            "<thead>" +
            "<tr class='header-days'>" +
            "<% for(var i = 0; i < daysOfTheWeek.length; i++) { %>" +
            "<td class='header-day'><%= daysOfTheWeek[i] %></td>" +
            "<% } %>" +
            "</tr>" +
            "</thead>" +
            "<tbody>" +
            "<% for(var i = 0; i < numberOfRows; i++){ %>" +
            "<tr>" +
            "<% for(var j = 0; j < 7; j++){ %>" +
            "<% var d = j + i * 7; %>" +
            "<td class='<%= days[d].classes %>'><i class='fa fa-spinner fa-lg fa-spin fa-fw faloading' ></i><div class='day-contents'><%= days[d].day %>" +
            "</div></td>" +
            "<% } %>" +
            "</tr>" +
            "<% } %>" +
            "</tbody>" +
            "</table>" +
            '<button class="btn btn-warning btn-block clndr-today-button" type="button">今天</button>',
            events: eventArray1,
            clickEvents: {
                click: function(target){
                    //console.log('Cal-1 clicked: ', target); 
                    //console.log('Cal-1 clicked: ', target.element);
					//显示出加载层，并加载数据信息
					$(target.element).find('.faloading').show();
					common.ajax({
						url: common.root + '/financial/loadCost.do',
						data:{date:target.date._i},
					    dataType: 'json',
					    loadfun: function(isloadsucc, data){
							$(target.element).find('.faloading').hide();
							if (isloadsucc) {
								if (data.state == 1) {
									var html = "<table><tr><td>当天收支情况</td></tr>"
											 +"<tr><td>待收入：</td><td>"+data.dsrMap.cnt+"笔   "+data.dsrMap.cost+"￥</td><td></td></tr>"
											 +"<tr><td>待支出：</td><td>"+data.dzcMap.cnt+"笔   "+data.dzcMap.cost+"￥</td><td></td></tr>"
											 +"<tr><td>已收入：</td><td>"+data.sjsrMap.cnt+"笔   "+data.sjsrMap.cost+"￥</td><td></td></tr>"
											 +"<tr><td>已支出：</td><td>"+data.sjzcMap.cnt+"笔   "+data.sjzcMap.cost+"￥</td><td></td></tr>"
											 +"</table>"
									layer.tips(html, '.calendar-day-' + target.date._i, {
				                        tips: [1, '#0FA6D8'] //还可配置颜色
				                    });
								}else{
									layer.tips('获取数据失败，请重新点击获取', '.calendar-day-' + target.date._i, {
				                        tips: [1, '#0FA6D8'] //还可配置颜色
				                    });
								}
							}
						}
					});
                },
                today: function(){
                    console.log('Cal-1 today');
                },
                nextMonth: function(){
                    console.log('Cal-1 next month');
                },
                previousMonth: function(){
                    console.log('Cal-1 previous month');
                },
                onMonthChange: function(){
                    console.log('Cal-1 month changed');
                },
                nextYear: function(){
                    console.log('Cal-1 next year');
                },
                previousYear: function(){
                    console.log('Cal-1 previous year');
                },
                onYearChange: function(){
                    console.log('Cal-1 year changed');
                },
                nextInterval: function(){
                    console.log('Cal-1 next interval');
                },
                previousInterval: function(){
                    console.log('Cal-1 previous interval');
                },
                onIntervalChange: function(){
                    console.log('Cal-1 interval changed');
                }
            },
            multiDayEvents: {
                startDate: 'startDate',
                endDate: 'endDate'
            },
            showAdjacentMonths: true,
            adjacentDaysChangeMonth: true
        });
    }
}


/**
 * 加载数据信息
 */
common.ajax({
    url: common.root + '/financial/loadFinanceData.do',
    dataType: 'json',
    loadfun: function(isloadsucc, data){
        if (isloadsucc) {
            if (data.state == 1) {
                $('.dzcbsvalue').html(data.dzcMap.cnt);
                $('.dzcjevalue').html(data.dzcMap.cost + '<i class="fa fa-yen"></i>');
                $('.dsrbsvalue').html(data.dsrMap.cnt);
                $('.dsrjevalue').html(data.dsrMap.cost + '<i class="fa fa-yen"></i>');
                $('.sjzcbsvalue').html(data.sjzcMap.cnt);
                $('.sjzcjevalue').html(data.sjzcMap.cost + '<i class="fa fa-yen"></i>');
                $('.sjsrbsvalue').html(data.sjsrMap.cnt);
                $('.sjsrjevalue').html(data.sjsrMap.cost + '<i class="fa fa-yen"></i>');
				console.log(data.zxtMap);
                //开始生成饼状图
                finance.zxt(data.zxtMap);
				finance.bzt();
				finance.calendars();
            }else {
                common.alert({
                    msg: common.msg.error
                });
            }
        }
        else {
            common.alert({
                msg: common.msg.error
            });
        }
    }
});

