var now;
var nowDayOfWeek;// 今天本周的第几天
var nowDay;// 当前日
var nowMonth;// 当前月
var nowYear;// 当前年
var preDate;// 前一天

var sum = 6;
var home_index = {

	/**
	 * 初始化数据信息
	 */
	init : function() {

		// 初始化时间
		var date = new Date();
		// 年
		var query_year = date.getFullYear();
		// 月
		var query_month = date.getMonth() + 1;
		query_month = query_month < 10 ? "0" + query_month : query_month;
		// 日
		var query_day = date.getDate();
		query_day = query_day < 10 ? "0" + query_day : query_day;

		$("#query_time").val(query_year + "-" + query_month + "-" + query_day);
		// 时间控件
		$("#query_time").datetimepicker({
			language : 'zh-CN', // 显示中文
			format : 'yyyy-mm-dd', // 显示格式
			startView : 'month',
			minView : 'month',
			autoclose : true, // 选中自动关闭
			todayBtn : true
		});

		// 获取当前时间
		var date = $("#query_time").val();
		// 市场数据
		home_index.getMktView(date);

		// 天数据折线图（月）
		home_index.dealMonth(date);
		
		// 饼图
		home_index.initMktPie(date);

		// 根据所选时间，获取数据
		$('#query_time').change(function() {
			home_index.getMktViewByTime();
		});

		// 财务报表数据
		home_index.getPrView(date);

		// 品控指标数据
		home_index.getQAView(date);
	},

	// 点击时间维度按钮
	changeTimeButtonColour : function(type) {
		// 获取当前时间
		var date = $("#query_time").val();
		switch (type) {
		case 'Y': // 年按钮
			$(".house-year").removeClass("btn-default");
			$(".house-year").addClass("btn-success");
			$(".house-month").removeClass("btn-success");
			$(".house-month").addClass("btn-default");
			$(".house-week").removeClass("btn-success");
			$(".house-week").addClass("btn-default");
			$(".house-day").removeClass("btn-success");
			$(".house-day").addClass("btn-default");
			$(".house-day").attr("disabled", false);
			$(".house-week").attr("disabled", false);
			$(".house-month").attr("disabled", false);
			$(".house-year").attr("disabled", true);
			home_index.dealYear(date);
			break;
		case 'M': // 月按钮
			$(".house-month").removeClass("btn-default");
			$(".house-month").addClass("btn-success");
			$(".house-year").removeClass("btn-success");
			$(".house-year").addClass("btn-default");
			$(".house-week").removeClass("btn-success");
			$(".house-week").addClass("btn-default");
			$(".house-day").removeClass("btn-success");
			$(".house-day").addClass("btn-default");
			$(".house-day").attr("disabled", false);
			$(".house-week").attr("disabled", false);
			$(".house-month").attr("disabled", true);
			$(".house-year").attr("disabled", false);
			home_index.dealMonth(date);
			break;
		case 'W': // 周按钮
			$(".house-week").removeClass("btn-default");
			$(".house-week").addClass("btn-success");
			$(".house-year").removeClass("btn-success");
			$(".house-year").addClass("btn-default");
			$(".house-month").removeClass("btn-success");
			$(".house-month").addClass("btn-default");
			$(".house-day").removeClass("btn-success");
			$(".house-day").addClass("btn-default");
			$(".house-day").attr("disabled", false);
			$(".house-week").attr("disabled", true);
			$(".house-month").attr("disabled", false);
			$(".house-year").attr("disabled", false);
			home_index.dealWeek(date);
			break;
		case 'D':
			$(".house-day").removeClass("btn-default");
			$(".house-day").addClass("btn-success");
			$(".house-year").removeClass("btn-success");
			$(".house-year").addClass("btn-default");
			$(".house-month").removeClass("btn-success");
			$(".house-month").addClass("btn-default");
			$(".house-week").removeClass("btn-success");
			$(".house-week").addClass("btn-default");
			$(".house-day").attr("disabled", true);
			$(".house-week").attr("disabled", false);
			$(".house-month").attr("disabled", false);
			$(".house-year").attr("disabled", false);
			home_index.dealDay(date);
			break;
		}
		$("#time_type").val(type);
	},

	// 根据所选时间，获取数据
	getMktViewByTime : function() {
		// 获取当前时间
		var date = $("#query_time").val();
		// 时间类型
		var time_type = $("#time_type").val();

		// 根据时间类型处理时间
		switch (time_type) {
		case 'Y':
			home_index.dealYear(date);
			break;
		case 'M':
			home_index.dealMonth(date);
			break;
		case 'W':
			home_index.dealWeek(date);
			break;
		case 'D':
			home_index.dealDay(date);
			break;
		}
	},
	
	// 处理周数据
	dealWeek : function(date) {
		var labledataKey = []; // 存放key值
		var labledataValue = []; // 存放value值
		for (var i = 0; i < sum; i++) {
			// 获取当前年月日
			home_index.getTime(date);
			var getWeekStartDate;
			var getWeekEndDate;
	    // 获得本周的开始日期
	    getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
	    getWeekStartDate = home_index.formatDate(getWeekStartDate);
	    getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
	    getWeekEndDate = home_index.formatDate(getWeekEndDate);
	    // 根据时间获取前一天数据
			date = home_index.getPreDate(getWeekStartDate);
			// 根据时间获取当前第几周
			var week = home_index.getYearWeek();
			labledataKey.push(getWeekStartDate + "=" + getWeekEndDate + "=" + week + "周");
		}
		
		labledataKey.sort();
		var labledataKey1 = [];
		for (var int = 0; int < labledataKey.length; int++) {
			var begin_date = labledataKey[int].split("=")[0];
			var param = {
				'date' : labledataKey[int].split("=")[1],
				'begin_date' : begin_date,
				'time_flag' : labledataKey[int].split("=")[2]
			};
			labledataKey1.push(labledataKey[int].split("=")[2]);
			common.ajax({
				url : common.root + '/homeView/report/getReportView.do',
				dataType : 'json',
				encode : false,
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify(param),
				loadfun : function(isloadsucc, datas) {
					if (isloadsucc) {
						labledataValue.push(datas);
						home_index.initMktLine(labledataKey1, labledataValue);
					}
				}
			});			
		}
	},
	
	getYearWeek : function() {
    var d1 = new Date(nowYear, nowMonth, nowDay), 
    d2 = new Date(nowYear, 0, 1), 
    d = Math.round((d1 - d2) / 86400000); 
    return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7); 		
	},
	
	getPreDate : function(date) {
    return preDate = new Date(new Date(date).getTime() - 86400000);		
	},
	
	getTime : function(date) {
    now = new Date(date);                // 当前日期
    nowDayOfWeek = now.getDay();         // 今天本周的第几天
    nowDay = now.getDate();              // 当前日
    nowMonth = now.getMonth();           // 当前月
    nowYear = now.getFullYear();         // 当前年
	},
	
	// 处理月数据
	dealMonth : function(date) {
		var labledataKey = []; // 存放key值
		var labledataKey2 = [];
		var labledataValue = []; // 存放value值
		var d = new Date(year, month, 0);
		for (var i = 0; i < sum; i++) {
			if (i == 0) {	    
				labledataKey.push(date);
			} else {
		    var arr = date.split('-');
		    var year = arr[0]; // 获取当前日期的年份
		    var month = arr[1]; // 获取当前日期的月份
				var year2 = year;
				var month2 = parseInt(month) - 1;
        if (month2 == 0) {// 如果是1月份，则取上一年的12月份
          year2 = parseInt(year2) - 1;
          month2 = 12;
        }
        if (month2 < 10) {
          month2 = '0' + month2;// 月份填补成2位。
        }
        var  day = new Date(year2,month2,0);
        var lastdate = year2 + '-' + month2 + '-' + day.getDate();
        date = lastdate;
        labledataKey.push(date);
			}
		}
		labledataKey.sort();
		for (var int = 0; int < labledataKey.length; int++) {
			var date = labledataKey[int];
			labledataKey2.push(date.substring(0, date.lastIndexOf("-")));
			var param = {
				'date' : date,
				'begin_date' : date.substring(0, date.lastIndexOf("-")) + "-01",
				'time_flag' : date.substring(0, date.lastIndexOf("-"))
			};
			common.ajax({
				url : common.root + '/homeView/report/getReportView.do',
				dataType : 'json',
				encode : false,
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify(param),
				loadfun : function(isloadsucc, datas) {
					if (isloadsucc) {
						labledataValue.push(datas);
						home_index.initMktLine(labledataKey2, labledataValue);
					}
				}
			});
		}		
	},

	// 处理天数据
	dealDay : function(date) {
		var labledataKey = []; // 存放key值
		var labledataValue = []; // 存放value值
		// 获取时间列表
		var d = new Date(date);
		for (var i = 0; i < sum; i++) {
			if (i == 0) {
				labledataKey.push(date);
			} else {
				d.setDate(d.getDate() - 1);
				var m = d.getMonth() + 1;
				m = m < 10 ? "0" + m : m;
				var day = d.getDate();
				day = day < 10 ? "0" + day : day;
				labledataKey.push(d.getFullYear() + '-' + m + '-' + day);
			}
		}
		labledataKey.sort();

		for (var int = 0; int < labledataKey.length; int++) {
			var date = labledataKey[int];
			var param = {
				'date' : date,
				'begin_date' : date,
				'time_flag' : date
			};
			common.ajax({
				url : common.root + '/homeView/report/getReportView.do',
				dataType : 'json',
				encode : false,
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify(param),
				loadfun : function(isloadsucc, datas) {
					if (isloadsucc) {
						labledataValue.push(datas);
						home_index.initMktLine(labledataKey, labledataValue);
					}
				}
			});
		}
	},

	// 处理年数据
	dealYear : function(date) {
		var year = date.split("-", 1);
		labledataKey = []; // 存放key值
		labledataValue = []; // 存放value值
		for (var i = 0; i < sum; i++) {
			if (i == 0) {
				labledataKey.push(parseInt(year));
			} else {
				labledataKey.push(parseInt(year) - i);
			}
		}
		labledataKey.sort();
		// 循环获取开始和结束时间
		for (var int = 0; int < labledataKey.length; int++) {
			year = labledataKey[int];
			var begin_time = year + "-01-01";
			var end_time;
			if (year == date.split("-", 1)) {
				end_time = date;
			} else {
				end_time = year + "-12-31";
			}
			var param = {
				'date' : end_time,
				'begin_date' : begin_time,
				'time_flag' : year
			};

			common.ajax({
				url : common.root + '/homeView/report/getReportView.do',
				dataType : 'json',
				encode : false,
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify(param),
				loadfun : function(isloadsucc, datas) {
					if (isloadsucc) {
						labledataValue.push(datas);
						home_index.initMktLine(labledataKey, labledataValue);
					}
				}
			});
		}
	},

	// 初始化嵌套环形图
	initMktPie : function(date) {
		
		var param = {
				'date' : date
			};

			common.ajax({
				url : common.root + '/homeView/report/getReportView.do',
				dataType : 'json',
				encode : false,
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify(param),
				loadfun : function(isloadsucc, datas) {
					if (isloadsucc) {
						var a_total_get = datas.a_total_get; // 收房
						var a_total_rent = datas.a_total_rent; // 出租
						var workingNum = datas.workingNum; // 施工中
						var waitHand = datas.waitHand; // 待交接
						var waitWork = datas.waitWork; // 待开工
						var waitRent = datas.waitRent; // 待出租
						
						// 根据收房计算各个指标占比
						// 出租占比
//						var a_total_rent_per = (parseInt(a_total_rent) / parseInt(a_total_get)) * 100;
//						a_total_rent_per = a_total_rent_per.toFixed(2);
//						a_total_rent = a_total_rent + "（" + a_total_rent_per + "）";
//						a_total_rent = a_total_rent + "（" + a_total_rent_per + "%）";
						
						var myChart = echarts.init(document.getElementById('house_data'));
						option = {
								tooltip : {
					        trigger: 'item',
					        formatter: "{b} : {c} ({d}%)"
					    },
					    series : [
				        {
				            name: '数据',
				            type: 'pie',
				            radius : '45%',
				            data:[
				                {value:a_total_rent, name:'出租总量'},
				                {value:workingNum, name:'施工中'},
				                {value:waitWork, name:'待开工'},
				                {value:waitHand, name:'待交接'},
				                {value:waitRent, name:'待出租'}
				            ],
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                },
				                radius: [0,'50%']
				            	}
				        	}
				        ]					    
						};
						myChart.setOption(option);
                        $(window).resize(function(){
                            myChart.resize();
                        });
					}
				}
			});
	},

	// 初始化房源折线图
	initMktLine : function(labledataKey, labledataValue) {

		// 根据时间处理数据
		var dealValue = [];
		for (i = 0; i < labledataKey.length; i++) {
			for (j = 0; j < labledataValue.length; j++) {
				var date = labledataValue[j].time_flag;
				if (date == labledataKey[i]) {
					dealValue.push(labledataValue[j]);
				}
			}
		}

		var b_a_add_get = []; // 收房
		var b_a_add_rent = []; // 出租
		var b_a_chk_get = []; // 业主退租
		var b_a_chk_rent = []; // 租客退租
		for (var int = 0; int < dealValue.length; int++) {
			b_a_add_get.push(dealValue[int].b_a_add_get);
			b_a_add_rent.push(dealValue[int].b_a_add_rent);
			b_a_chk_get.push(dealValue[int].b_a_chk_get);
			b_a_chk_rent.push(dealValue[int].b_a_chk_rent);
		}

		var myChart = echarts.init(document.getElementById('house_add'));
		var option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				left : 'center',
				top : 15,
				data : [ '收房', '出租' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : {
				max : 1000,
				min : 0,
				data : labledataKey
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				name : '收房',
				type : 'line',
				data : b_a_add_get
			}, {
				name : '出租',
				type : 'line',
				data : b_a_add_rent
			} ]
		};
		myChart.setOption(option);
        $(window).resize(function(){
            myChart.resize();
        });
	},

	// 财务指标数据
	getPrView : function(str) {
		common.ajax({
			url : common.root + '/report/prViewCost.do',
			dataType : 'json',
			data : {
				date : str + " 00:00:00"
			},
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					$("#receivableCount").html(data.receivableCount);
					$("#receivable").html(data.receivable);
					$("#payableCount").html(data.payableCount);
					$("#payable").html(data.payable);
				} else {
					common.alert(common.msg.error);
				}
				common.load.hide();
			}
		});
	},

	// 市场报表数据
	getMktView : function(date) {
		var param = {
			'date' : date
		};
		common.ajax({
			url : common.root + '/homeView/report/getReportView.do',
			dataType : 'json',
			encode : false,
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(param),
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					// 当前房源
					var a_total_get = data.a_total_get == undefined ? 0 : data.a_total_get;
					$(".a_total_get").html(a_total_get);

					// 当前出租
					var a_total_rent = data.a_total_rent == undefined ? 0 : data.a_total_rent;
					$(".a_total_rent").html(a_total_rent);

					// 人均效能
					var perNum = data.perNum == undefined ? 0 : data.perNum;
					$(".perNum").html(perNum);

					// 单间成本
					var singlePrice = data.singlePrice == undefined ? 0 : data.singlePrice;
					$(".singlePrice").html(singlePrice);

					// 租差
					var rentalDiff = data.rentalDiff == undefined ? 0 : data.rentalDiff;
					$(".rentalDiff").text(rentalDiff);

					// 当前空置
					var idle = data.idle == undefined ? 0 : data.idle;
					$(".idle").text(idle);

					// 业主退租
					var b_a_chk_get = data.b_a_chk_get == undefined ? 0 : data.b_a_chk_get;
					$(".a_chkouting_get").html(b_a_chk_get);

					// 租客退租
					var b_a_chk_rent = data.b_a_chk_rent == undefined ? 0 : data.b_a_chk_rent;
					$(".a_chkouting_rent").html(b_a_chk_rent);
				}
			}
		});
	},

	// 品控指标数据
	getQAView : function(str) {
		common.ajax({
			url : common.root + '/workOrder/getTypeCount.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var Barr=new Array();
					var Carr=new Array();
					var Darr=new Array();
					var Harr=new Array();
					for (var i = 0; i < data.typeCountList.length; i++) {
						var datas= data.typeCountList[i];
						switch (datas.type) {
							case "B" : // 保洁订单
								Barr=home_index.stateNullRes(datas,Barr);
								break;
							case "C" : // 投诉订单
								Carr=home_index.stateNullRes(datas,Carr);
								break;
							case "D" : // 看房订单
								Darr=home_index.stateNullRes(datas,Darr);
								break;
							case "H" : // 维修订单
								Harr=home_index.stateNullRes(datas,Harr);
								break;
						}
					}
					$("#bj").html(home_index.arrayNnullres(Barr[1]));
					$("#ts").html(home_index.arrayNnullres(Carr[1]));
					$("#kf").html(home_index.arrayNnullres(Darr[1]));
					$("#wx").html(home_index.arrayNnullres(Harr[1]));
				}
			}
	  });
	},
	
  stateNullRes :function (datas,array) {
  	if (datas.state!=null) {
  		array[1]=datas.typeCnt;// 已完成
  	}else{
  		array[0]=datas.typeCnt;// 未完成
  	}
  	return array;
  },
  
  arrayNnullres :function (data) {
  	if (data == null || data == "" || data == "undefined") {
  		data = 0;
  	}
  	return data;
  },  
	
  // 格式化日期：yyyy-MM-dd
	formatDate : function (date) {
      var myyear = date.getFullYear();
      var mymonth = date.getMonth()+1;
      var myweekday = date.getDate();

      if(mymonth < 10) {
      	mymonth = "0" + mymonth;
      }
      if(myweekday < 10) {
      	myweekday = "0" + myweekday;
      }
      return (myyear + "-" + mymonth + "-" + myweekday);
  },
}

$(function() {
	home_index.init();
});