var construction_detail = 
{
	init:function()
	{
		var agreementId = njyc.phone.queryString("agreementId");
		construction_detail.loadProjectInfo(agreementId);
		construction_detail.loadProjectList(agreementId,1);
		construction_detail.loadProjectList(agreementId,2);
		construction_detail.loadProjectList(agreementId,0); 
	},
	loadProjectInfo:function(agreementId)
	{// 加载工程基本信息
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getProjectInfo.do',
			data : {agreementId : agreementId},
			dataType : 'json', 
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					$('#hx').text(data.spce);
					$('#lztime').text(data.createtime);
					$('#kgtime').text(data.constructiontime);
					$('#wgtime').text(data.completetime);
					$('#zxmonery').text(data.money);
					$('#jjmonery').text(data.furnituremoney);
					$('#djmonery').text(data.appliancesmoney);
					$('#totalmonery').text(data.total);
					$('#othermonery').text(data.othermoney);
					$('#gcjd').text(data.STATUS);
				}
			}});
		
	},
	loadProjectList:function(agreementId,flag)
	{
		var url = njyc.phone.getSysInfo('serverPath') + 'mobile/houseMgr/getProjectList.do?isMobile=1&flag='+flag;
		njyc.phone.ajax
		({
			url : url,
			data : {agreementId : agreementId},
			dataType : 'json', 
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					var html = '';
					 for(var i = 0; i < data.length; i++)
					 {
						 var json = data[i];
						 html += '<div class="divPadding">'
							  + '  <div class="titleDiv1">'
							  + '    <span class="leftSpan"><p id="gysName">'+json.supname+'</p></span>'
							  + '    <span class="rightSpan color1"><p id="price">价格:'+json.price+'</p></span>&nbsp;&nbsp;'
							  + '  </div>'
							  + '  <div class="textDiv1">'
							  + '    <div  id="clname">名称:'+json.name+'&nbsp;</div>'
							  + '    <div class="kindDiv">规格:'+json.spec+'</div>'
							  + '  </div>'
							  + '  <div class="textDiv1">'
							  + '    <div class="lefeDiv" style="padding: 0 0">品牌:</div>'
							  + '    <div class="rightDiv2" id="brand">'+json.brand+'</div>'
							  + '    <div class="lefeDiv" style="text-align: center;">编码:</div>'
							  + '    <div class="rightDiv" id="code">'+json.code+'</div>'
							  + '  </div>'
							  + '</div>';
					 }
					 $('#listDiv'+flag).html(html);
					 if(data.length == 0)
					 {
						 $('#listDiv'+flag).html('<div style="text-align: center;">暂无数据!</div>');
					 }	 
				}
			}});
	},
	showOrHidenInfo:function(flag)
	{
		$('#listDiv'+flag).toggle();
	}
	
};
$(construction_detail.init());