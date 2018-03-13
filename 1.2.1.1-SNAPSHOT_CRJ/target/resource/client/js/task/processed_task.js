var page_num = 0;
var isLoading = false; 
var process_task_list = 
{
	init:function()
	{
		process_task_list.loadSelect();
		$('#rightdiv').click(function()
		{
			$('.myinfo').find('img').toggleClass('rot2');
			$('#hide1').toggle();
		});
		process_task_list.getMyTaskList();
		$('#taskType').change(function(){
//			$('#hide1').hide();
			refreshlList();
		});
		$('#taskState').change(function(){
			$('.myinfo').find('img').toggleClass('rot2');
			$('#hide1').hide();
			refreshlList();
		});
		$('#search').click(function(){
			refreshlList();
		});
	},
	loadSelect:function()
	{
		// 任务类型
		njyc.phone.ajax(
		{
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/task/taskStateList.do',
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, json) 
			{
				if (isloadsucc)
				{
					var html = '<option value="">任务状态</option>'; 
					for ( var i = 0; i < json.length; i++) 
					{
						html += '<option value="' + json[i].item_id + '" >'+ json[i].item_name + '</option>';
					}
					$('#taskState').html(html);
				} else {
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
		// 任务状态
		njyc.phone.ajax(
		{
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/task/taskTypeList.do',
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, json) 
			{
				if (isloadsucc)
				{
					var html = '<option value="">任务类型</option>';
					for ( var i = 0; i < json.length; i++) 
					{
						html += '<option value="' + json[i].type_id + '" >'+ json[i].type_name + '</option>';
					}
					$('#taskType').append(html);
				} else {
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
	},
	getMyTaskList:function()
	{
		if(isLoading)
		{
			return ;
		}
		njyc.phone.showProgress('');
		njyc.phone.ajax
		({
        	url:njyc.phone.getSysInfo('serverPath')+'/mobile/task/getMyYetTaskList.do',
        	data:{taskType:$('#taskType').val(),pageNumber:page_num,keyWord:$('#keyWord').val(),lx:3,taskState:$('#taskState').val()},
        	dataType:'json',
        	async:false,
        	loadfun: function(isloadsucc, data)
        	{
        		var appendHtml = '';
				if(isloadsucc)
				{
					var appendHtml = '';
					for(var i = 0; i < data.length; i++)
					{
						var json = data[i];
//						console.log(json);
						appendHtml += '<div class="div" onclick="returnPage(\''+json.step_id+'\',\''+json.task_id+'\')">'
								   + '    <div class="con">'
								   + '      <div class="info"><p>'+json.task_code+'</p><p class="state">'+json.name+'</p></div>'
								  // + '      <div class="state"><p>'+json.name+'</p></div>'
								   + '    </div>'
								   + '    <div class="con1">'
								   + '      <div class="common">'+json.typenames+'</div>'
//								   + '      <div class="common1">'+json.createtime+'</div>'
								   + '      <div class="common r-item">'+json.step_name+'</div>'
								   + '    </div>'
								   + '    <div class="con2">'
								   + '      <div class="info1"><a href="#" onclick="">'+json.createtime+'</a></div>'
								   + '    </div>'
								   + '    <hr>'
								   + '  </div>';
					}
					$('.body').append(appendHtml);
					if(data.length < 25)
					{
						njyc.phone.showShortMessage('数据加载完毕!');
					}
				}
				isLoading = false;
        	}
		});
		page_num++;
		njyc.phone.closeProgress();
	},
}; 
$(document).ready(function(){
	process_task_list.init();
    $(window).scroll(function()
    {
      if($(document).scrollTop()>=$(document).height()-$(window).height())
      {
    	  process_task_list.getMyTaskList();
      } 
    });
});
function returnPage(step_id,task_id)
{
	njyc.phone.openWebKit({title:'任务查看',url:njyc.phone.getSysInfo('serverPath')+'/mobile/task/yetDisposeDitail.do?step_id='+ encodeURIComponent(step_id)+'&task_id='+task_id});
}
//刷新列表
function refreshlList()
{
	$('.body').html('');
	isLoading = false;
	page_num = 0; 
	process_task_list.getMyTaskList();
}
