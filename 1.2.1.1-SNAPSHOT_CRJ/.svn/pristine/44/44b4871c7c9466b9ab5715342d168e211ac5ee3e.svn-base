var publish_house = 
{
	init:function()
	{
		 var id = njyc.phone.queryString('id'); // 房源id
		 if(!common.isEmpty(id))
		 {
			 $('#houseId').val(id);
			 publish_house.getRankHouseInfo(id); 
		 }	 
		 else
		 {
			 njyc.phone.showShortMessage('未查询到房源数据!');
			 return ;
		 }
	},
	getRankHouseInfo(id)
	{
		// 获取出租房源的信息
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/seeRankHouseList.do',
			data : {id : id},
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc) 
				{
					var html = '';
					for(var i = 0; i < data.length; i++)
					{
						var selector = 'select'+i;
						html = '<div class="titleDiv" onclick="publish_house.openOrHidden(this,\'div'+i+'\')">'
//							 + '	<img src="../../../images/up.png" width="25px" height="25px" />'
							 + '	<input type="hidden" value="'+data[i].id+'" name="id" /><input type="hidden" value="" name="images" /><input type="hidden" value="'+i+'" name="numberInput" />'
							 + '	<span class="leftSpan"><p>'+data[i].title+'</p><p>'+data[i].rank_code+'</p>'+'</span>'
							 + '	<span class="rightSpan"><p>'+data[i].rankType+'</p><p class="color1">'+data[i].rankStatus+'</p></span>'
							 + '</div>'
							 + '<div id="div'+i+'">'
							 + '	<div class="divPadding">'
							 + '		<div class="textDiv">'
							 + '			<div class="lefeDiv">面积</div>'
							 + '			<div class="rightDiv">'
							 + '				<input type="number" value="'+data[i].rank_area+'" dataType="Integer3" msg="请输入房源面积！" placeholder="20" maxlength="5" name="areaid" />'
							 + '			</div>'
							 + '			<div class="lefeDiv">价格</div>'
							 + '			<div class="rightDiv">'
							 + '				<input type="number" dataType="Integer3" msg="请输入房源价格！" placeholder="2000" value="'+data[i].fee+'" maxlength="5" name="price" />'
							 + '			</div>'
							 + '		</div>'
							 + '	</div>'
							 + '	<div class="updownload">'
							 + '		<div class="weui_cell file" onclick="publish_house.selectImages(\''+selector+'\');return false;" id="selectImage'+i+'">'
							 + '			<a href="">选择文件上传</a>'
							 + '		</div>'
							 + '			<ul class="ipost-list ui-sortable js_fileList" id="'+selector+'"></ul>'
							 + '			<input type="hidden" value="4" name="picSize" id="picSize" />'
							 + '	</div>'
							 + ' </div>';
						 if(i != data.length-1)
						 {
							 html += ' <hr>';
						 }
						 $('#contentDiv').append(html);
						 njyc.phone.showPic(data[i].path,selector);
					}
					if(html != '')
					{
						$('#form1').append('<div id="saveDiv"><button id="saveBtn" type="button" class="">保&nbsp;存</button></div>');
						$('#saveDiv').click(publish_house.save);
					}	
				}
			}
		});
	},
	selectImages:function(selector)
	{
		var picSize = $("#picSize").val(); // 可以上传图片数量
		var uploadPic = $('#'+selector+' input[name="picImage"]').size();
		if(Math.abs(picSize) > uploadPic)
		{
			njyc.phone.selectImage((Math.abs(picSize) - uploadPic), selector);
		}
		else
		{
			$('#'+selector).hide();
		}	
		return false;
	},
	save:function()
	{
		// 保存
		if (!Validator.Validate('form1'))
		{
			return;
		} 
		var numberInputs = $('input[name="numberInput"]').length;
		for(var i = 0; i < numberInputs; i++)
		{
			var image = '';
			var numberInput = 'select'+i;
			var picImage = $('#'+numberInput+' input[name="picImage"]');
			if(picImage.size()==0)
			{
				njyc.phone.showShortMessage('请上传第'+(i+1)+'处房源图片!');
				return ;
			}
			for(var j = 0; j < picImage.length; j++)
			{
				image += '|' + $(picImage[j]).val();
			}
			if(image != '')
			{
				image = image.substring(1);
			}
			$('input[name="images"]').eq(i).val(image);
		}
		$('#saveDiv').unbind("click");
		njyc.phone.showProgress('');
		var ajax_option =
		{
			url:njyc.phone.getSysInfo('serverPath') + "/mobile/rankAgreement/updateRankHouse.do",//默认是form action
			success:publish_house.resFunc,
			type:'post',
			dataType:'json'
		};
		$("#form1").ajaxForm(ajax_option).submit();
	},
	resFunc:function(data)
	{
		njyc.phone.closeProgress()
		$('#saveDiv').click(publish_house.save);
		if(data.state == 1)
		{
			njyc.phone.showShortMessage('操作成功!');
			njyc.phone.closeCallBack("refreshlList()");
		}
		else if(data.state == -3)
		{
			njyc.phone.showShortMessage('图片上传失败,请重试!');
			return ;
		}
		else  
		{
			njyc.phone.showShortMessage('网络忙,请重试！');
			return;
		}
	},
	openOrHidden:function(obj,selector)
	{
		$('#'+selector).toggle();
		var image = $(obj).find('img').attr('src');
//		alert($(obj).find('img').attr('src'))
		if(image.indexOf('up.png') == -1)
		{
			$(obj).find('img').attr('src','../../../images/up.png');
		}
		else
		{
			$(obj).find('img').attr('src','../../../images/down.png');
		}	
	}
};
$(publish_house.init);