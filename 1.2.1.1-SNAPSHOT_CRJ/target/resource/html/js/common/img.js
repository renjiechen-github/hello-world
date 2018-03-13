try{
	_img_ = null;
}catch(e){
	
}
/**
 * 图片空间
 */
var _img_ = { 
	selectobj:[],
	/**
	 * 初始化方法
	 */
	init:function(){
		_img_.loadMyData();
		_img_.loadevent();
	},
	/**
	 * 加载数据信息
	 */
	loadMyData:function(){
		common.ajax({
			url:common.root+'/sys/loadTmpImg.do',
			data:{
				type:1
			},
			dataType: 'json',
			loadfun: function(isloadsucc, data){
				if (isloadsucc) {
					if(data.list.length == 0){
						$('._img_ #home .divload').html('<div>暂无文件</div>');
					}else{
						var html = '';
						for(var i=0;i<data.list.length;i++){
							html += '<li title="右击鼠标预览" imgid="'+data.list[i].id+'" >'+
									'	<i class="fa fa-check-circle"></i>'+
									'	<img src="'+data.list[i].path+data.list[i].changename+'">'+
									'</li>';
						}
						$('._img_ #home .divload').remove();
						$('._img_ #home ul').html(html);
					}
				}else{
					common.alert({msg:'文件管理器加载失败'});
				}
			}
		});
	},
	/**
	 * 加载事件
	 */
	loadevent:function(){
		$('._img_ li').die('click');
		$('._img_ li').live('click',function(){
			$(this).toggleClass('select');
			var path = $('img',this).attr('src');
			if($(this).attr('class') == 'select'){//选中就添加，否则删除
				_img_.selectobj.push({path:path,id:$(this).attr('imgid'),obj:this});
				$('._img_ .bntdiv').show();
			}else{
				for(var i=0;i<_img_.selectobj.length;i++){
					if(_img_.selectobj[i].path == path){
						_img_.selectobj.splice(i, 1);
					}
				}
				console.log(_img_.selectobj);
				//检查是否已经出现按钮对话框，如果出现不予理睬，如果没有出现就需要新增出现
				if(_img_.selectobj.length == 0){
					$('._img_ .bntdiv').hide();
				}
			} 
		});
		$('._img_ #bntsucc').click(function(){
			layer.close($('#_images_').attr('index'));
		});
		$('._img_ #bntcommon').click(function(){
			//获取图片id
			var id = '';
			for(var i=0;i<_img_.selectobj.length;i++){
				id += '\''+_img_.selectobj[i].id+'\',';
			}
			common.ajax({
				url:common.root+'/sys/setCommonImg.do',
				data:{
					ids:id.substr(0,id.length -1)
				},
				dataType: 'json',
				loadfun: function(isloadsucc, data){
					if(isloadsucc){
						if(data.state == 1){
							common.alert({
								msg: '设置成功'
							});
						}else{
							common.alert({
								msg: '设置失败。'
							});
						}
					}else{
						common.alert({
							msg: '设置失败。'
						});
					}	
				}
			});
		});
		$('._img_ #bntdelete').click(function(){
			var id = '';
			var path = '';
			for(var i=0;i<_img_.selectobj.length;i++){
				id += '\''+_img_.selectobj[i].id+'\',';
				path += _img_.selectobj[i].path+',';
			}
			common.ajax({
				url:common.root+'/sys/deleteImg.do',
				data:{
					ids:id.substr(0,id.length -1),
					paths:path
				},
				dataType: 'json',
				loadfun: function(isloadsucc, data){
					if(isloadsucc){
						if(data.state == 1){
							common.alert({
								msg: '删除成功',
								fun:function(){
									for (var i = 0; i < _img_.selectobj.length; i++) {
										$(_img_.selectobj[i].obj).remove();
									}
									_img_.selectobj = [];
								}
							});
						}else{
							common.alert({
								msg: '删除失败。'
							});
						}
					}else{
						common.alert({
							msg: '删除失败。'
						});
					}	
				}
			});
		});
		$('._img_ li').die('mousedown');
		$('._img_ li').live('mousedown',function(event){
			event.preventDefault();
			var src = $(this).find('img').attr('src');
			if(event.button == 2){//点击鼠标右键
                layer.photos({
                    photos:{
						title:'',
						id:'1',
						start:0,
						data:[
							{
								alt:'',
								pid:'',
								src:src,
								thumb:''
							}
						]
					}
                });
			}
		});
		
		$('._img_ .nav-tabs li').click(function(){
			_img_.selectobj = [];
			$('._img_ .tab-content li').removeClass('select');
			if($(this).find('a').attr('href') == '#about'){//公共图片
				common.ajax({
					url:common.root+'/sys/loadTmpImg.do',
					data:{
						type:2
					},
					dataType: 'json',
					loadfun: function(isloadsucc, data){
						if (isloadsucc) {
							if(data.list.length == 0){
								$('._img_ #about .divload').html('<div>暂无文件</div>');
							}else{
								var html = '';
								for(var i=0;i<data.list.length;i++){
									html += '<li title="右击鼠标预览" imgid="'+data.list[i].id+'" >'+
											'	<i class="fa fa-check-circle"></i>'+
											'	<img src="'+data.list[i].path+data.list[i].changename+'">'+
											'</li>';
								}
								$('._img_ #about .divload').remove();
								$('._img_ #about ul').html(html);
							}
						}else{
							common.alert({msg:'文件管理器加载失败'});
						}
					}
				});
			}
		});
	}
}

_img_.init();
