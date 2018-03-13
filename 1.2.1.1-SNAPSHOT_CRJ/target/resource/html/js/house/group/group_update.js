var area_id="";
var area_value="";
var type_value="";
var groupid="";
var arry=new Array();
var group_update = {
        //页面初始化操作
	    init : function() {
		// 加载结算类型
		group_update.initType();
		// 添加处理事件
		group_update.addEvent();
		//初始化数据
		group_update.initData(); 
		// 时间按钮选择事件
		var nowTemp = new Date();
		$('#groupDate').daterangepicker({
					startDate : nowTemp.getFullYear() + '-'
							+ (nowTemp.getMonth() + 1) + '-02',
					singleDatePicker : true,
					format : 'YYYY-MM-DD HH:mm'
				}, function(start, end, label) {
					console.log(start.toISOString(), end.toISOString(), label);
				});
	  },
	    // 添加修改事件
	   addEvent : function() {
		$('#group_add_bnt').click(function() {
			group_update.save();
		});
	}, checkspec: function (obj){/*
    	var returnI=0;
    	$("input[name='spec']").each(  
				function(){  
					if (obj.value==$(this).val()&&obj.value!=null&&obj.value!="") {
						returnI++;
					}
				}); 
    	if (returnI>1) {
    		obj.value="";	
    		common.alert({
    			msg:"已有当前户型价格，请核对后重新填写"
    		});
		}
    */},
    deleteroom: function (obj){
    	$(obj).parent('div').parent('div').parent('div').remove();
    	var leng=$("input[name='cost']").length;
		 $(".rebuild").each(function(i,val){
				    $(this).attr("id","pic"+i);
			});
    },
    /**
    *小区房源对应关系
    */
    addroom: function ()
    {
    	var ids = "pic"+($("input[name='cost']").length);
    	var html="";
    	html='<div><div class="row">&nbsp;</div><div class="row">'
    		+ ' <label class="col-sm-2  control-label"><b style="color: red">*</b>室：</label>'
    		+ '              <div class="col-sm-1">'
    	    + '                <input name="spec" type="text"  onblur="group_update.checkspec(this)" dataType="Integer2"  msg="请正确填写卧室数量" class="form-control" maxlength="4">' 
    		+ '              </div>'
    		+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>厅：</label>'
    		+ '              <div class="col-sm-1">'
    		+ '                <input name="spec1" type="text"    dataType="Integer2"  msg="请正确填写客厅数量" class="form-control" maxlength="4">' 
            + '              </div>' 
        	+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>面积：</label>'
    		+ '              <div class="col-sm-1">'
    		+ '                <input name="specarea" type="text"   dataType="Money"  msg="请正确填写面积" class="form-control" maxlength="6">' 
            + '              </div>' 
    		+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>价格：</label>'
    		+ '              <div class="col-sm-1">'
            + '                <input name="cost" type="text"   msg="请正确填写价格" dataType="Money" class="form-control" maxlength="6">' 
            + '              </div>' 
           /* + '              <label class="col-sm-1  control-label"><b style="color: red">*</b>房源量：</label>' 
            + '              <div class="col-sm-1">' 
            + '                <input name= "count"  type="number" dataType="Require"  msg="请正确填写数量" dataType="Integer" class="form-control"' 
            + '                  maxlength="10">' 
            + '              </div>' */
            + '       <div class="col-sm-3">' 
            + '     	</div>' 
            + '            </div>'
            + '       <div class="row">'
            + '    <label class="col-sm-2  control-label">图片</label>'
            + '    <div class="col-sm-8">'
            + '    <div class="dropzone rebuild" id='+ids+'></div>'
            + '    	</div>'
            + '    </div>'
            + ' <div class="row" >'
            + '<div class="col-sm-9">'
            + '	</div>'
            + '<div class="col-sm-1">'
            + '<a onclick="group_update.deleteroom(this);"><i class="fa fa-trash-o"></i>删除..</a>'
            + '</div>'
            + '	</div>'
    	    + '    </div>';
    	$('#selectspec').append(html);
    	
    	common.dropzone.init({
			id : '#'+ids,
			maxFiles:10
			//defimg:[{path:'/upload/tmp/855553.gif',first:1}].
		});
    	
    	$("#deletediv").show();//显示div  
    },
    /**
	 * 加载区域选择
	 */
   initType: function(){
       //加载小区类型
       common.loadItem('GROUP.TYPE', function(json){
           var html = "";
           for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
           }
           $('#groupType').append(html);
           $('#groupType').val(type_value);
       });
   },
  //加载修改数据信息
	initData : function() {
		if (rowdata != null) {
			area_value=rowdata.areaid;
			area_id = rowdata.trading;
			groupid=rowdata.id;
			type_value = rowdata.group_type;
			$('#area_select')[0].innerHTML = rowdata.area_name;
			$('#groupName').val(rowdata.group_name);
			$('#groupDesc').val(rowdata.group_desc);
			$('#groupTraffic').val(rowdata.traffic);
			$('#groupDeveloper').val(rowdata.developer);
			$('#groupProperty').val(rowdata.property);
			$('#groupDate').val(rowdata.build_date);
			$('#groupArea').val(rowdata.area.replace(/\,/g, ""));// 面积
			$('#plotRatio').val(rowdata.plot_ratio);
			$('#houseCount').val(rowdata.house_count);
			$('#greenRate').val(rowdata.green_rate);
			$('#parkCount').val(rowdata.park_count);
			$('#groupCoordinate').val(rowdata.coordinate);
			$('#groupAddress').val(rowdata.address);
			//初始化地图
			group_update.loadMap(rowdata.coordinate);
			//初始化图片
			if (rowdata.path!=null&&rowdata.path!="") {
				var pas=rowdata.path.split("|");
				var paths=new Array();
				for ( var int = 0; int < pas.length; int++) {
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
			/**
			 * 小区获取对应房源关系
			 */
			common.ajax({
				url : common.root + '/grouproom/getRoom.do',
				data : {group_id : rowdata.id},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
				  if (data.state == 1){
					for ( var i = 0; i < data.list.length; i++){
								var ids = "pic"+($("input[name='cost']").length);
								var html="";
								var cost=data.list[i].cost == null ? "" : data.list[i].cost.replace(",", "");
								var areaspec=data.list[i].specarea.replace(/\,/g, "");
					        	html='<div><div class="row">&nbsp;</div><div class="row">'
					        		+ ' <label class="col-sm-2  control-label"><b style="color: red">*</b>室：</label>'
					        		+ '              <div class="col-sm-1">'
					        	    + '                <input name="spec" type="text"  dataType="Integer2" msg="请正确填写卧室数量" value="'+data.list[i].bedroom+'" class="form-control" maxlength="4">' 
					        		+ '              </div>'
					        		+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>厅：</label>'
					        		+ '              <div class="col-sm-1">'
					        		+ '                <input name="spec1" type="text"     dataType="Integer2"  msg="请正确填写客厅数量" value="'+data.list[i].spec1+'"      class="form-control" maxlength="4">' 
					                + '              </div>' 
					            	+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>面积：</label>'
					        		+ '              <div class="col-sm-1">'
					        		+ '                <input name="specarea" type="text"    dataType="Money"  msg="请正确填写面积" value="'+areaspec+'"  class="form-control" maxlength="6">' 
					                + '              </div>' 
					        		+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>价格：</label>'
					        		+ '              <div class="col-sm-1">'
					                + '                <input name="cost" type="text" msg="请正确填写价格"  dataType="Money" value="'+cost+'"    class="form-control" maxlength="6">' 
					                + '              </div>' 
					             /*   + '              <label class="col-sm-1  control-label"><b style="color: red">*</b>房源量：</label>' 
					                + '              <div class="col-sm-1">' 
					                + '                <input name= "count"  type="number"  value="'+data.list[i].count+'"   dataType="Require" dataType="Integer" msg="请正确填写数量" class="form-control"' 
					                + '                  maxlength="10">' 
					                + '              </div>' */
					                + '       <div class="col-sm-3">' 
					                + '     	</div>' 
					                + '            </div>'
					                + '       <div class="row">'
					                + '    <label class="col-sm-2  control-label">图片</label>'
					                + '    <div class="col-sm-8">'
					                + '    <div class="dropzone rebuild" id='+ids+'></div>'
					                + '    	</div>'
					                + '    </div>'
					                + ' <div class="row" >'
						            + '<div class="col-sm-9">'
						            + '	</div>'
						            + '<div class="col-sm-1">'
						            + '<a onclick="group_add.deleteroom(this);"><i class="fa fa-trash-o"></i>删除..</a>'
						            + '</div> </div>'
					        	    + '</div>';
					       $("#selectspec").append(html);
			if (data.list[i].urlpath!=null&&data.list[i].urlpath!="") {
				var pas=data.list[i].urlpath.split("|");
				var paths=new Array();
				for ( var int = 0; int < pas.length; int++) {
				   if (int==0) {
							paths.push({path:pas[int],first:1});	
					}else{
						    paths.push({path:pas[int],first:0});
					}
			     }
				common.dropzone.init({
				         id :'#'+ids,
						defimg:paths,
						maxFiles:10
								    });
		    }else{
				common.dropzone.init({
				id :'#'+ids,
				maxFiles:10
								    });
				 }
			}
				} else {
							common.alert({msg : common.msg.error});
					   }
					} else {
						common.alert({msg : common.msg.error});
					}
				}
			});
	 var type1="4";//商圈
	 var fatherid=area_value;//上级Id
		  common.loadArea(fatherid,type1, function(json){
		           var html = "<option value=''>请选择...</option>";
		           for (var i = 0; i < json.length; i++) {
						html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
		           }
		           $('#areaid')[0].innerHTML=html;
		           $('#areaid').val(area_id);
		       });
		}
	},
	loadMap : function(coordinate) {
		
		$('#groupAddress').blur(function() {
			var val = $('#groupAddress').val();
			map.clearOverlays();
			// 创建地址解析器实例
			var myGeo = new BMap.Geocoder();
			// 将地址解析结果显示在地图上,并调整地图视野
			myGeo.getPoint(val, function(point) {
				if (point) {
					map.centerAndZoom(point, 16);
					map.addOverlay(new BMap.Marker(point));
					$('#groupCoordinate').val(point.lng + "," + point.lat);
				} else {
					alert("您输入的地址没有解析到结果，请在地图中选取您的地址!");
				}
			}, "南京市");
		});
		
		var map = new BMap.Map("groupMap", {
			minZoom : 8,
			maxZoom : 18
		});
		if (coordinate!=null&&coordinate!="") {
			var split = coordinate.split(",");
			map.setCurrentCity("南京"); // 设置地图显示的城市 此项是必须设置的
			map.centerAndZoom(new BMap.Point(split[0], split[1]), 14);
			// map.centerAndZoom("南京", 12);
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
			map.addEventListener("click", function(e) {
				map.clearOverlays();
				map.centerAndZoom(e.point, 16);
				map.addOverlay(new BMap.Marker(e.point));
				$('#groupCoordinate').val(e.point.lng + "," + e.point.lat);
				var geoc = new BMap.Geocoder();
				geoc.getLocation(e.point, function(rs) {
					var addComp = rs.addressComponents;
					$('#groupAddress').val(
							addComp.province + addComp.city + addComp.district
									+ addComp.street + addComp.streetNumber);
				});
			});
		}else{
			map.centerAndZoom("南京", 12);
	    	map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件
	    	map.setCurrentCity("南京"); // 设置地图显示的城市 此项是必须设置的
	    	map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
	    	map.addEventListener("click", function(e) {
	    		map.clearOverlays();
	    		map.centerAndZoom(e.point, 16);
	    		map.addOverlay(new BMap.Marker(e.point));
	    		$('#groupCoordinate').val(e.point.lng + "," + e.point.lat);
	    		
	    		var geoc = new BMap.Geocoder();
	    		geoc.getLocation(e.point, function(rs){
	    			var addComp = rs.addressComponents;
	    			$('#groupAddress').val(addComp.province + addComp.city + addComp.district +addComp.street +addComp.streetNumber);
	    		});  
	    	});
		}
	},
    
    /**
     * 更新操作
     */
    save: function(){
      if (!Validator.Validate('form2')) {
            return;
        }
        var reg1=/^\d+$/;
    	var reg2=/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		if ($('#greenRate').val()!=null&&$('#greenRate').val()!=""&&!reg2.test($('#greenRate').val()))
		{
			common.alert({msg:'绿化率填写错误请重新填写'});
			 return;	
		}
		if ($('#houseCount').val()!=null&&$('#houseCount').val()!=""&&!reg1.test($('#houseCount').val()))
		{
			common.alert({msg:'总户数填写错误请重新填写'});
			 return;	
		}
		if ($('#parkCount').val()!=null&&$('#parkCount').val()!=""&&!reg1.test($('#parkCount').val()))
		{
			common.alert({msg:'停车位填写错误请重新填写'});
			 return;	
		}
		if ($('#plotRatio').val()!=null&&$('#plotRatio').val()!=""&&!reg2.test($('#plotRatio').val()))
		{
			 common.alert({msg:'容积率填写错误请重新填写'});
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
		var spec="";//户型
		$("input[name='spec']").each(function(){  
					spec +=$(this).val()+",";  
				}); 
		var spec1="";//厅
		$("input[name='spec1']").each(  
				function(){
					spec1 +=$(this).val()+",";  
				}); 
		var specarea="";//面积
		$("input[name='specarea']").each(  
				function(){
					specarea +=$(this).val()+",";  
				}); 
		var cost="";//价格
		$("input[name='cost']").each(function()
				{  
					cost +=$(this).val()+",";  
				}); 
		/*var count="";//数量
		$("input[name='count']").each(function(){  
					count +=$(this).val()+",";  
				}); 
		count=count.split(0, count.length-1);*/
		var arr=$("input[name='cost']").length;
		var spic="";
		var returno=false;
		for ( var i = 0; i < arr; i++) {
			var pictpath = common.dropzone.getFiles('#pic'+i);
			var pict = "";
			var returnT = false;
			for ( var n = 0; n < pictpath.length; n++) {
				if(pictpath[n].fisrt==1){
					pict = pictpath[n].path + ',' + pict;
				}
				else{
					pict += pictpath[n].path + ",";
				}
				returnT = true;
			}
			if (returnT) {
				spic +=pict.substring(0, pict.length - 1) +"|";
			}else{
				spic +="0|";
			}
		}
		$("#group_add_bnt").attr("disabled",true);
		common.ajax({
            url: common.root + '/group/update.do',
            data:{
            	roomspec:spec,
            	//roomcount:count,
            	roomcost:cost,
            	roomspec1:spec1,
            	roomarea:specarea,
            	roomimage:spic,
            	group_id:groupid,
            	areaid:area_value,
            	trading : $('#areaid').val(),
				type : $('#groupType').val(),
				name : $('#groupName').val(),
				desc : $('#groupDesc').val(),
				traffic : $('#groupTraffic').val(),
				developer : $('#groupDeveloper').val(),
				property : $('#groupProperty').val(),
				buildDate : $('#groupDate').val(),
				area : $('#groupArea').val(),//面积
				plotratio : $('#plotRatio').val(),
				houseCount : $('#houseCount').val(),
				greenRate : $('#greenRate').val(),
				parkCount : $('#parkCount').val(),
				images : pathe,
				coordinate : $('#groupCoordinate').val(),
				address : $('#groupAddress').val(),
            },
			dataType:'json',
            loadfun: function(isloadsucc, data){
                if (isloadsucc) {
                    if (data.state == 1) {//操作成功
                 		common.alert({
							msg:'操作成功'
						});
						common.closeWindow('groupupdate',3);
                    }else{
                    	$("#group_add_bnt").attr("disabled",false);
						common.alert({
	                        msg: common.msg.error
	                    });
					}
                }else {
                	$("#group_add_bnt").attr("disabled",false);
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
        });
    }
};
group_update.init();