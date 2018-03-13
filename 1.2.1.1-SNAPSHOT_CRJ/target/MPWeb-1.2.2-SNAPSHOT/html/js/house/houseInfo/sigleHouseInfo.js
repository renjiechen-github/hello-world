var sigleHouseInfo = 
{
	init:function()
	{
		$("#tijiaoBtn").click(function(){
			sigleHouseInfo.btnInfo();
			return false;
		});
		
		common.ajax({
			url : common.root + '/houserank/getuphouse.do',
			data : {id : common.getWindowsData('sigleHouseInfo').id},
			dataType : 'json',
			loadfun : function(isloadsucc, array) 
			{
				if (isloadsucc) 
				{
					var arrays = array.list;
					var data = arrays[0];
					var roleIds = data.roleIds;
					var flag = false;
					// 运营，平台管理员，系统管理员
					if (roleIds.indexOf("35") > -1 || roleIds.indexOf("1") > -1 || roleIds.indexOf("25") > -1) {
						flag = true;
						$("#tijiaoBtn").show();
					} else {
						$("#tijiaoBtn").hide();
					}
					
					$("#house_code").text(data.rank_code);
					$("#house_name").text(data.house_name);
					$("#house_title").text(data.title);
					$("#house_floor").text(data.floor);
					$("#houseId").val(data.house_id);
					var spec=data.rank_spec;
					var spec0;
					var spec1;
					var spec2;
					var spec3;
					if (spec!=""&&spec!=null)
					{
						spec0=data.rank_spec.split("|");
						if (spec0.length==3) 
					 {
						spec1=spec0[0]+"室";
						spec2=spec0[1]+"厅";
						spec3=spec0[2]+"卫";
						$("#house_hx").text(spec1+spec2+spec3);
					 }
						else if (spec0.length==2)
						{
							spec1=spec0[0]+"室";
							spec2=spec0[1]+"厅";
							spec3=0+"卫";
							$("#house_hx").text(spec1+spec2+spec3);
						}
					}
					$("#house_czfs").text(data.rank_type);
					$("#house_arear").text(data.rank_area);
					$("#house_price").text(data.fee);
					$("#house_desc").text(data.property_desc);
					$("#house_address").text(data.address);
					var picArray = new Array();
					for (var i = 0; i < data.path.split("|").length; i++) {
						var picPath = data.path.split("|")[i];
						var picStr = picPath.substring(0, picPath.lastIndexOf("."));
						var suffix = picPath.substring(picPath.lastIndexOf("."), picPath.length);
						picStr = picStr + "_big" + suffix;
						if (i == 0) {
							picArray.push({
								path : picStr,
								first : 1
							});
						} else {
							picArray.push({
								path : picStr,
								first : 0
							});
						}
					}
					common.dropzone.init({
						id : '#house_pic',
						maxFiles : 9,
						defimg : picArray, 
						clickEventOk: flag
					});
				}
			}
		});
	},
	
	btnInfo : function () {
		var id = common.getWindowsData('sigleHouseInfo').id;
		var houseId = $("#houseId").val();
		var propertyDesc = $("#house_desc").val();
		var filepath = common.dropzone.getFiles('#house_pic');
		if (filepath.length == 0) 
		{
			common.alert(
			{
				msg : '请选择房源图片!'
			});
			return;
		}
		
		var path = "";
		var returnI = false;
		for (var i = 0; i < filepath.length; i++) 
		{
			var pic = filepath[i].path;
			if (pic.indexOf("_big") > -1) {
				pic = pic.replace("_big", "");
			}
			if (filepath[i].fisrt == 1) 
			{
				path = pic + ',' + path;
			}
			else
			{
				path += pic + ",";
			}
			returnI = true;
		}
		if (returnI) 
		{
			path = path.substring(0, path.length - 1);
		}		
		common.ajax({
			url : common.root + '/BaseHouse/savePartInfoOfHouse.do',
			data : {
				path : path,
				id : id,
				houseId: houseId,
				propertyDesc: propertyDesc
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.state == 1) {// 操作成功
						common.alert({
							msg : '操作成功',
							fun : function() {
								common.closeWindow('sigleHouseInfo', 1);
							}
						});
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				} else {
					common.alert({
						msg : common.msg.error,
						fun : function() {
							common.closeWindow('sigleHouseInfo', 3);
						}
					});
				}
			}
		});		
		
	},	
	
	
};
$(sigleHouseInfo.init);