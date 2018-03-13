var id = '';
var rankdesc="";
var basedesc="";
var idarr=new Array();

var rank_update = {
	    // 页面初始化操作
	    init : function() {
		// 添加处理事件
		rank_update.addEvent();
		// 初始化数据
		rank_update.initData();
	    
	    },
	// 添加修改事件
	addEvent : function() {
		//保存
		$('#group_add_bnt').click(function() {
			rank_update.save(0);
		});
		//发布
		$('#group_add_replease').click(function() {
			rank_update.save(1);
		});
		//返回
		$('#closeBUT').click(function() {
			common.closeWindow('groupupdate', 3);
		});
		
	},
	// 加载修改数据信息
	initData : function() {
		common.loadItem('RANKHOUSE.DESC', function(json){
	           var html = "";
	           for (var i = 0; i < json.length; i++) {
	        	   if (json[i].item_id==1) {
	        		   rankdesc=json[i].item_remark;
				}if (json[i].item_id==2) {
					basedesc==json[i].item_remark;
				}
	           }
	       });
		 id=common.getWindowsData('houseRelease').id;
		/**
		 * 获取租赁房源信息
		 */
		common.ajax({
			url : common.root + '/houserank/getuphouse.do',
			data : {houseId : id},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
			if (isloadsucc) {
			  if (data.state == 1){
				  var html="";
				for ( var i = 0; i < data.list.length; i++)
				{
					if (data.list[i].rank_type == "整租")
					{
					    continue;
					}
					idarr.push(data.list[i].id);
				var title="房间"+data.list[i].title.substring(data.list[i].title.length-1,data.list[i].title.length);	
					
				html='<div class="form-group">'
					+'<label class="col-sm-3  control-label"></label>'
					+'<div class="col-sm-6"></div>'
					+'<div class="col-sm-3"></div>'
					+'</div>'

					+'<div class="panel panel-info">'
					+'<div class="panel-heading" style="height: 10px;" align="center">'
					+'	<h4 class="panel-title" style="margin-top: -5px;">'
					+'		<a data-toggle="collapse" data-parent="#accordion"'
					+'			href="#collapse'+i+'"> '+title+'&nbsp;&nbsp;&nbsp;房源编号：'+data.list[i].rank_code+' </a>'
					+'	</h4>'
					+'</div>'
					
					+'	<div id="collapse'+i+'" class="panel-collapse collapse ">'
					+'	<div class="panel-body">'
					+'	<div class="form-group">'
					+'		<label class="col-sm-3  control-label"></label>'
					+'		<div class="col-sm-6"></div>'
					+'		<div class="col-sm-3"></div>'
					+'	</div>'

					+'	<div class="form-group">'
					+'		<label class="col-sm-3  control-label"><b style="color: red">*</b>房源面积：</label>'
					+'		<div class="col-sm-2">'
					+'			<input id="rankArea'+data.list[i].id+'" class="form-control"  msg="请填写房源面积"  dataType="Integer" value="'+data.list[i].rank_area+'">'
					+'		</div>'
					+'		<label class="col-sm-2  control-label"><b style="color: red">*</b>出租价格：</label>'
					+'		<div class="col-sm-2">'
					+'			<input id="fee'+data.list[i].id+'" class="form-control"  msg="请填写出租价格" dataType="double" value="'+data.list[i].fee+'">'
					+'		</div>'
					+'		<div class="col-sm-3"></div>'
					+'	</div>'

					/*+'	<div class="form-group">'
					+'		<label class="col-sm-3  control-label">房源标题：</label>'
					+'		<div class="col-sm-6">'
					+'			<input id="rankName'+data.list[i].id+'" class="form-control"  value="'+data.list[i].title+'">'
					+'		</div>'
					+'		<div class="col-sm-3"></div>'
					+'	</div>'*/
					
					+'	<div class="form-group">'
					+'		<label class="col-sm-3  control-label">图片上传：</label>'
					+'		<div class="col-sm-6">'
					+'			<div class="dropzone" id="path'+data.list[i].id+'"></div>'
					+'			<div class="row"></div>'
					+'		</div>'
					+'		<div class="col-sm-3"></div>'
					+'	</div>'
					+'</div>'
					+'</div>	</div>';
				$("#addpanel").append(html);
					if (data.list[i].path!=null&&data.list[i].path!="") {
					var imag=data.list[i].path;
					var pas=imag.split("|");
					var paths=new Array();
					for ( var int = 0; int < pas.length; int++) {
						if (int==0) {
							paths.push({path:pas[int],first:1});	
						}else{
							paths.push({path:pas[int],first:0});
						}
					}
					common.dropzone.init({
						id : '#path'+data.list[i].id,
						defimg:paths,
						maxFiles:10
					});
				}else{
					common.dropzone.init({
						id : '#path'+data.list[i].id,
						maxFiles:10
					                     });	
				    }
				}
				
				for ( var i = 0; i < data.list.length; i++)
				{
					if (data.list[i].rank_type == "整租")
					{
						var spec=data.list[i].spec.split("|");
						$("#spec")[0].innerHTML=spec[0]+"室"+spec[1]+"厅"+spec[2]+"卫";
						$("#group")[0].innerHTML = data.list[i].group_name;
						$("#floor")[0].innerHTML = data.list[i].floor;
						$("#address")[0].innerHTML = data.list[i].address;
						$("#housecode")[0].innerHTML =data.list[i].house_name+"&nbsp;&nbsp;&nbsp;房源编号："+ data.list[i].rank_code;
						$("#rankAreap").val(data.list[i].rank_area);
						$("#feep").val(data.list[i].fee);
						$("#rankNamep")[0].innerHTML = data.list[i].title;
						idarr.push(data.list[i].id);
					    $('#myAwesomeDropzonep').attr("id","path"+data.list[i].id);
					    $('#rankAreap').attr("id","rankArea"+data.list[i].id);
					    $('#feep').attr("id","fee"+data.list[i].id);
					    if (data.list[i].path!=null&&data.list[i].path!="") {
							var imag=data.list[i].path;
							var pas=imag.split("|");
							var paths=new Array();
							for ( var int = 0; int < pas.length; int++) 
							{
								if (int==0)
								{
									paths.push({path:pas[int],first:1});	
								}
								else
								{
									paths.push({path:pas[int],first:0});
								}
							}
							common.dropzone.init({
								id : '#path'+data.list[i].id,
								defimg:paths,
								maxFiles:10
							});
						}else{
							common.dropzone.init({
								id : '#path'+data.list[i].id,
								maxFiles:10
							                     });	
						    }
					    break;
					}
					}
		        }
				} else {
					common.alert({msg : common.msg.error});
				}
			}
		});
	},
	
	/**
	 * 更新操作
	 */
	save : function(type) {
		if (!Validator.Validate('form2')) {
			return;
		}
		var spic = "";
		var fee="";
		var rankArea="";
		var rankName="";
		var rankid="";
		
		for ( var i = 0; i < idarr.length; i++) {
			var pictpath = common.dropzone.getFiles('#path' + idarr[i]);
			var pict = "";
			var returnT = false;
			for ( var n = 0; n < pictpath.length; n++) {
				if (pictpath[n].fisrt == 1) {
					pict = pictpath[n].path + '|' + pict;
				} else {
					pict += pictpath[n].path + "|";
				}
				returnT = true;
			}
			if (returnT) {
				spic += pict.substring(0, pict.length - 1) + ",";
			} else {
				spic += "0,";
			}
			rankid += idarr[i]+",";
			//rankName +=$("#rankName"+idarr[i]).val()+",";
			fee +=$("#fee"+idarr[i]).val()+",";
			rankArea +=$("#rankArea"+idarr[i]).val()+",";
			
		}
		rankid= rankid.substring(0, rankid.length - 1);
	//	rankName = rankName.substring(0, rankName.length - 1);
		fee = fee.substring(0, fee.length - 1);
		rankArea = rankArea.substring(0, rankArea.length - 1);
		spic = spic.substring(0, spic.length - 1);
		$("#group_add_bnt").attr("disabled", true);
		$("#group_add_replease").attr("disabled", true);
		$("#closeBUT").attr("disabled", true);
		
		common.ajax({
			url : common.root + '/houserank/update.do',
			data : {
				houseid:id,
				type:type,
				rankid:rankid,
				rankdesc:rankdesc,
				basedesc:basedesc,
				rankName:rankName,
				fee:fee,
				rankArea:rankArea,
				images:spic
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.state == 1) {// 操作成功
						common.alert({
							msg : '操作成功'
						});
						common.closeWindow('groupupdate', 3);
					} else {
						$("#closeBUT").attr("disabled", false);
						$("#group_add_replease").attr("disabled", false);
						$("#group_add_bnt").attr("disabled", false);
						common.alert({
							msg : common.msg.error
						});
					}
				} else {
					$("#closeBUT").attr("disabled", false);
					$("#group_add_replease").attr("disabled", false);
					$("#group_add_bnt").attr("disabled", false);
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	}
};
rank_update.init();