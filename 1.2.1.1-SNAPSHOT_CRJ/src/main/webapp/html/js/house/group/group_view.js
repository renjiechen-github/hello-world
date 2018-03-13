var area_id="";
var area_value="";
var type_value="";
var arry=new Array();
var group_view = {
        //页面初始化操作
	    init : function() {
		// 加载结算类型
		group_view.initType();
		//初始化数据
		group_view.initData(); 
		//返回
		$('#group_cancel').click(function() {
			common.closeWindow('groupupdate', 3);
		});	 
		
	    },
    /**
	 * 加载区域选择
	 */
   initType: function(){
       var type="3";//区域
       var type1="4";//商圈
       var fatherid="";//上级Id
       common.loadArea(fatherid,type1, function(json){
           var html = "";
           for (var i = 0; i < json.length; i++) {
        	   if (json[i].id==area_id) {
        		   html=json[i].area_name;
			}
           }
           $('#areaid')[0].innerHTML=html;
       });
		 // 加载小区类型
		common.loadItem('GROUP.TYPE', function(json) {
			var html = "";
			for ( var i = 0; i < json.length; i++) {
				if (json[i].item_id == type_value) {
					$('#groupType')[0].innerHTML = json[i].item_name;
				}
			}
		});
		
   },
  //加载修改数据信息
	initData:function(){
		if(rowdata != null){
		//	area_value=rowdata.areaid;
			area_id=rowdata.trading;
			type_value=rowdata.group_type;
			 $('#area_select')[0].innerHTML=rowdata.area_name;
			 $('#groupName')[0].innerHTML=rowdata.group_name;
			 $('#groupDesc')[0].innerHTML=rowdata.group_desc;
			 $('#groupTraffic')[0].innerHTML=rowdata.traffic;
			 $('#groupDeveloper')[0].innerHTML=rowdata.developer;
			 $('#groupProperty')[0].innerHTML=rowdata.property;
			 $('#groupDate')[0].innerHTML=rowdata.createtime;
			 $('#groupArea')[0].innerHTML=rowdata.area;
			 $('#plotRatio')[0].innerHTML=rowdata.plot_ratio;
			 $('#houseCount')[0].innerHTML=rowdata.house_count;
			 $('#greenRate')[0].innerHTML=rowdata.green_rate;
			 $('#parkCount')[0].innerHTML=rowdata.park_count;
			 $('#groupCoordinate')[0].innerHTML=rowdata.coordinate;
			 $('#groupAddress')[0].innerHTML=rowdata.address;
			 if (rowdata.coordinate!=null&&rowdata.coordinate!="") {
				 group_view.loadMap(rowdata.coordinate);
			}
			 if (rowdata.path!=null&&rowdata.path!="") {
				 var paths=rowdata.path.split("|");
				 for ( var i = 0; i < paths.length; i++) {
					html="<a href="+paths[i]+" target=\"_Blank\"><image style='height:100px;width:150px;' src="+paths[i]+"></a>&nbsp;&nbsp;"; 
					$("#imageview").append(html);
				}
			}
			
			 /**
				 * 小区获取对应房源关系
				 */
				common.ajax({
					url : common.root + '/grouproom/getRoom.do',
					data : {
						group_id : rowdata.id
					},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == 1) {
								for ( var i = 0; i < data.list.length; i++) {
									var ids = "pic"+($("input[name='cost']").length);
									var html1="";
									if (data.list[i].urlpath!=null&&data.list[i].urlpath!="") {
										var urlpaths=data.list[i].urlpath.split("|");
										for ( var int = 0; int < urlpaths.length; int++) {
											html1 += "<a href="+urlpaths[int]+" target=\"_Blank\"><image style='height:100px;width:150px;' src="+urlpaths[int]+"></a>&nbsp;&nbsp;";
										}
									}
									var html="";
									html='<div><div class="row">&nbsp;</div><div class="row">'
						        		+ ' <label class="col-sm-2  control-label">户型 ：</label>'
						        		+ '              <div class="col-sm-2">'
						        		+'<span class="form-control" style="border: 0" >'+data.list[i].bedroom+'室&nbsp;&nbsp;'+data.list[i].spec1+'厅&nbsp;</span>'
						        		+ '              </div>'
						        	/*	+ '              <label class="col-sm-1  control-label">厅：</label>'
						        		+ '              <div class="col-sm-1">'
						        		+'<span class="form-control" style="border: 0">'+data.list[i].spec1+'</span>'
						                + '              </div>' */
						            	+ '              <label class="col-sm-1  control-label">面积：</label>'
						        		+ '              <div class="col-sm-1">'
						        		+'<span class="form-control" style="border: 0" >'+data.list[i].specarea+'㎡</span>'
						                + '              </div>' 
						        		+ '              <label class="col-sm-1  control-label">价格：</label>'
						        		+ '              <div class="col-sm-1">'
						        		+'<span  class="form-control" style="border: 0">'+data.list[i].cost+'元</span>'
						                + '              </div>' 
						              /*  + '              <label class="col-sm-1  control-label">房源量：</label>' 
						                + '              <div class="col-sm-1">' 
						            	+'<span class="form-control" style="border: 0">'+data.list[i].count+'</span>'
						                + '              </div>' */
						                + '       <div class="col-sm-4"></div>' 
						                + '</div>'
						                + '</div>'
						                + '  <div class="row">&nbsp;</div>  '
						                + '       <div class="row">'
						                + '    <label class="col-sm-2  control-label">图片:</label>'
						                + '    <div class="col-sm-8">'
						                + html1
						                + '    	</div>'
						                + '    </div>'
						        	    + '</div>';
						       $("#selectspec").append(html);
								}
								
								}
							}
						}
					});
				
				/**
				 * 小区获取对应房源报表
				 */
				common.ajax({
					url : common.root + '/group/queryHouseFlow.do',
					data : {
						group_id : rowdata.id
					},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
								console.log(data);
									var r0 ,r1,r2,r3,r4,r5,r6,r7,r8;
									r0=data.countall!=null&&data.countall!=""?data.countall:0;
									r1=data.specall!=null&&data.specall!=""?data.specall:0;
									r2=data.onstall!=null&&data.onstall!=""?data.onstall:0;
									r3=data.onspec!=null&&data.onspec!=""?data.onspec:0;
									r4=data.r1!=null&&data.r1!=""?data.r1:0;
									r5=data.r2!=null&&data.r2!=""?data.r2:0;
									r6=data.r3!=null&&data.r3!=""?data.r3:0;
									r7=data.r4!=null&&data.r4!=""?data.r4:0;
									
									$("#asr0")[0].innerHTML=r0+"套("+r1+"间)";
									$("#asr1")[0].innerHTML=r2+"套("+r3+"间)";
									$("#asr2")[0].innerHTML="整租："+r5+"套<br>单间："+r4+"间";//+r8+"间";
									$("#asr3")[0].innerHTML="整租："+r6+"套<br>单间："+r7+"间";//+r8+"间";
							}
						}
					});
		}
	},
	loadMap:function (coordinate){
		var split = coordinate.split(",");
		var map = new BMap.Map("groupMap", {
			minZoom : 8,
			maxZoom : 18
		});
		map.setCurrentCity("南京"); // 设置地图显示的城市 此项是必须设置的
		map.centerAndZoom(new BMap.Point(split[0], split[1]), 14);
		//map.centerAndZoom("南京", 12);
		map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件 
		map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
		var circleOptions = {
				strokeColor : "#FF0000",
				strokeOpacity : 0.35,
				strokeWeight : 2,
				fillColor : "#40E0D0",
				fillOpacity : 0.35
			};
			var point = new BMap.Point(split[0], split[1]);
			var circle = new BMap.Circle(point, 200, circleOptions);
			// circle.zIndex = circleList.length;
			map.addOverlay(circle);
	},
};
group_view.init();