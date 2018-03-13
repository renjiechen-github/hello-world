var map = new BMap.Map("groupMap", {minZoom : 8,maxZoom : 18});
var group_add = {
    //页面初始化操作
	    init : function() {
		// 加载结算类型
		group_add.initType();
		
		group_add.loadMap();
		// 添加处理事件
		 group_add.addEvent();
		// group_add.initData();
		common.dropzone.init({
			id : '#myAwesomeDropzone',
			maxFiles:10
			//defimg:[{path:'/upload/tmp/855553.gif',first:1}].
		});
		common.dropzone.init({
			id : '#pic0',
			maxFiles:10
			//defimg:[{path:'/upload/tmp/855553.gif',first:1}].
		});
		// 时间按钮选择事件
		var nowTemp = new Date();
		$('#groupDate').daterangepicker(
				{
					startDate : nowTemp.getFullYear() + '-'
							+ (nowTemp.getMonth() + 1) + '-02',
					singleDatePicker : true,
					format : 'YYYY-MM-DD HH:mm'
				}, function(start, end, label) {
					console.log(start.toISOString(), end.toISOString(), label);
				});

	},
    
  addEvent: function(){
        $('#group_add_bnt').click(function(){
        	group_add.save();
        });
        $('#area_select').change(function(){
        	var fatherid= $('#area_select').val();
        	var type1="4";
        	   common.loadArea(fatherid,type1, function(json){
                   var html = "<option value=''>请选择...</option>";
                   for (var i = 0; i < json.length; i++) {
        				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
                   }
                  $('#areaid')[0].innerHTML=html;
               });
        });
    },
    /**
     加载区域选择
     **/
    initType: function(){
        var type="3";//区域
        var type1="4";//商圈
        var fatherid="";//上级Id
        /**
         * 区域
         * 
         * 
         * */
        common.loadArea(fatherid,type, function(json){
            var html = "";
            for (var i = 0; i < json.length; i++) {
 				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
            }
            $('#area_select').append(html);
        });
        /**
         * 商圈
         * 
         * */
        common.loadArea(fatherid,type1, function(json){
            var html = "";
            for (var i = 0; i < json.length; i++) {
 				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
            }
            $('#areaid').append(html);
        });
        /**
         * 小区类型
         * 
         * */
        common.loadItem('GROUP.TYPE', function(json){
            var html = "";
            for (var i = 0; i < json.length; i++) {
 				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#groupType').append(html);
        });
    },
   /* checkspec: function (obj){
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
    },*/
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
    	    + '                <input name="spec" type="text" dataType="Require"    dataType="Integer"  msg="请正确填写卧室数量" class="form-control" maxlength="4">' 
    	    + '              </div>'
    		+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>厅：</label>'
    		+ '              <div class="col-sm-1">'
    		   + '                <input name="spec1" type="text" dataType="Require"    dataType="Integer"  msg="请正确填写客厅数量" class="form-control" maxlength="4">' 
            + '              </div>' 
        	+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>面积：</label>'
    		+ '              <div class="col-sm-1">'
    		   + '                <input name="specarea" type="text" dataType="Money"  msg="请正确填写面积" class="form-control" maxlength="6">' 
            + '              </div>' 
    		+ '              <label class="col-sm-1  control-label"><b style="color: red">*</b>价格：</label>'
    		+ '              <div class="col-sm-1">'
            + '                <input name="cost" type="text" dataType="Require"   msg="请正确填写价格" dataType="Money" class="form-control" maxlength="6">' 
            + '              </div>' 
            /*'  +               <label class="col-sm-1  control-label"><b style="color: red">*</b>房源量：</label>' 
            + '              <div class="col-sm-1">' 
            + '                <input name= "count"  type="number" dataType="Require"   msg="请正确填写数量" dataType="Integer" class="form-control"' 
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
            + ' <div class="row">'
            + '<div class="col-sm-9">'
            + '	</div>'
            + '<div class="col-sm-1">'
            + '<a onclick="group_add.deleteroom(this);"><i class="fa fa-trash-o"></i>删除..</a>'
            + '</div>'
            + '	</div>'
    	    + '    </div>';
    	$('#selectspec').append(html);
    	common.dropzone.init({
			id : '#'+ids,
			maxFiles:10
			//defimg:[{path:'/upload/tmp/855553.gif',first:1}].
		});
    },
    loadMap: function ()
    {
    	var myGeo = new BMap.Geocoder();
    	$('#groupAddress').blur(function() {
    		var val = $('#groupAddress').val();
    		map.clearOverlays();
    		// 创建地址解析器实例
    		
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
    	
    	
    	map.centerAndZoom("上海", 16);
    	
    	map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件
    	map.setCurrentCity("上海"); // 设置地图显示的城市 此项是必须设置的
    	map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
    	
    	map.addEventListener("click", function(e) {
    		map.clearOverlays();
    		map.centerAndZoom(e.point, 15);
    		map.addOverlay(new BMap.Marker(e.point));
    		$('#groupCoordinate').val(e.point.lng + "," + e.point.lat);
    		
    		var geoc = new BMap.Geocoder();
    		geoc.getLocation(e.point, function(rs){
    			var addComp = rs.addressComponents;
    			$('#groupAddress').val(addComp.province + addComp.city + addComp.district +addComp.street +addComp.streetNumber);
    		});  
    		/*var circle = new BMap.Circle(e.point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
    	    map.addOverlay(circle);
    	    var local =  new BMap.LocalSearch(map, {renderOptions: {map: map, autoViewport: false}});  
    	   // local.searchNearby('公交站台',e.point,500);
    		for(i=0;i<local.Ea.content.length;++i)
            {
    		    $("#panel")[0].innerHTML += "<p style='font-size:12px;'>" + (n+1) + "、" + allPois[i].title + ",地址:" + allPois[i].address +",距离："+(map.getDistance(pointA,pointB)).toFixed(2)+' 米。'+ "</p>";
            }
    		*/
    		// 百度地图API功能
    		
    		var mOption =
    		{
    			    poiRadius : 500,           //半径为1000米内的POI,默认100米
    			    numPois : 3000,           //列举出50个POI,默认10个
    		}
    		map.addOverlay(new BMap.Circle(e.point,1000000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3}));   
    		var options = {
    				renderOptions: {
    					map: map,
    					autoViewport: true,
    					panel: "r-result",
    					 poiRadius : 500,           //半径为1000米内的POI,默认100米
    	    			    numPois : 3000,    
    				},
    		onSearchComplete: function(results){
    			// 判断状态是否正确
    			if (local.getStatus() == BMAP_STATUS_SUCCESS){
    				var s = [];
    				var html="";
    				for (var i = 0; i < results.getCurrentNumPois(); i ++)
    				{
    					s.push(results.getPoi(i).title + ", " + results.getPoi(i).address);
    					console.log(results.getPoi(i));
    				    var pointA = new BMap.Point(e.point.lgn,e.point.lat); 
 	            	    var pointB = new BMap.Point(results.getPoi(i).point.lgn,results.getPoi(i).point.lat);
 	            	    html +="<a href="+results.getPoi(i).url+" target='_blank'>"+results.getPoi(i).title + ", " + results.getPoi(i).address+",&nbsp;&nbsp;&nbsp;&nbsp;距离"+(map.getDistance(pointA,pointB)).toFixed(2)+"米</a><br/>"
 	            	    map.addOverlay(new BMap.Marker(results.getPoi(i).point));           
    				}
    				//$("#r-result")[0].innerHTML = s.join("<br/>");
    				$("#r-result")[0].innerHTML =html;
    			}
    		}
    		};
    		var local = new BMap.LocalSearch(map, options);
    		//local.searchNearby("小区",e.point,1000);
    		//var walking = new BMap.WalkingRoute(map, {renderOptions:{map: map, autoViewport: true}});
    		//walking.search("雅瑰园", "新街口");
    		//transit.search("雅瑰园", "天井山公寓");
    		local.search("小区");
    	});
    	
    	//map.addOverlay(new BMap.Circle(e.point,1000000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3}));   
		var options = {
				 pageCapacity: 20,
				renderOptions: {
					map: map,
					autoViewport: true,
					panel: "r-result"
				},
				onSearchComplete: function(results){
	    			// 判断状态是否正确
	    			if (local.getStatus() == BMAP_STATUS_SUCCESS){
	    				var s = [];
	    				var html="";
	    				console.log(results);
	    				
	    				for (var i = 0; i < results.getCurrentNumPois(); i ++)
	    				{
	    					s.push(results.getPoi(i).title + ", " + results.getPoi(i).address);
	    				   /* var pointA = new BMap.Point(e.point.lgn,e.point.lat); 
	 	            	    var pointB = new BMap.Point(results.getPoi(i).point.lgn,results.getPoi(i).point.lat);*/
	 	            	    //html +="<a href="+results.getPoi(i).url+" target='_blank'>"+results.getPoi(i).title + ", " + results.getPoi(i).address+",&nbsp;&nbsp;&nbsp;&nbsp;距离"+(map.getDistance(pointA,pointB)).toFixed(2)+"米</a><br/>"
	    					 html +="<a href="+results.getPoi(i).url+" target='_blank'>"+results.getPoi(i).title + ", " + results.getPoi(i).address+",&nbsp;&nbsp;&nbsp;&nbsp;距离米</a><br/>"
	    					map.addOverlay(new BMap.Marker(results.getPoi(i).point));           
	    				}
	    				//$("#r-result")[0].innerHTML = s.join("<br/>");
	    				$("#r-result")[0].innerHTML =html;
	    			}
	    		}
		}
    	var local = new BMap.LocalSearch(map, options);
    	local.search("小区");
    	
},
    
    /**
     * 保存操作
     */
    save: function()
    {
		if (!Validator.Validate('form2'))
		{
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
		
		var spec="";//室
		$("input[name='spec']").each(function()
		{
			spec +=$(this).val()+",";  
		}); 
		var spec1="";//厅
		$("input[name='spec1']").each(function()
		{
			spec1 +=$(this).val()+",";  
		}); 
		var specarea="";//面积
		$("input[name='specarea']").each(function()
		{
			specarea +=$(this).val()+",";  
		}); 
		var cost="";//价格
		$("input[name='cost']").each(function()
		{  
			cost +=$(this).val()+",";  
		}); 
	/*	var count="";//数量
		$("input[name='count']").each(  
				function(){  
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
		spic=spic.substring(0, spic.length-1);
		//$("$group_add_bnt").attr("diabld",true);
		$("#group_add_bnt").attr("disabled",true);
      common.ajax({
            url: common.root + '/group/create.do',
            data:{
            	roomspec:spec,
            	//roomcount:count,
            	roomcost:cost,
            	roomspec1:spec1,
            	roomarea:specarea,
            	roomimage:spic,
            	areaId : $('#area_select').val(),
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
				//pagetype : rowdata == null ? 'add' : 'edit'
            },
			dataType:'json',
            loadfun: function(isloadsucc, data){
                if (isloadsucc) {
                    if (data.state == 1) {//操作成功
                 		common.alert({
							msg:'操作成功'
						});
						common.closeWindow('addgroupadd',3);
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
group_add.init();