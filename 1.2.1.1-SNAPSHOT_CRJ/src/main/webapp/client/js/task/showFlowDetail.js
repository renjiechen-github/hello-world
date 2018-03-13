var showFlowDetail = {
	/**
	 * 初始化数据信息
	 */
	init:function(){
		var step_id=njyc.phone.queryString("step_id");
		var task_id=njyc.phone.queryString("task_id");
		showFlowDetail.detail(step_id,task_id);
	/*	njyc.phone.ajax({
			url: njyc.phone.getSysInfo('serverPath')+'/mobile/task/myStartTask.do',
			data:{step_id:step_id,task_id:task_id},
		    dataType: 'json',
		    loadfun: function(isloadsucc, data){
		    	console.log(data);
		    	if(isloadsucc){
		    		
		    	}else{
		    		alert(common.msg.error);
		    	}
		    }
		});*/
		
		njyc.phone.ajax({
			url: njyc.phone.getSysInfo('serverPath')+'/mobile/task/showFlowDetail.do',
			data:{step_id:step_id,task_id:task_id},
		    dataType: 'json',
		    loadfun: function(isloadsucc, data)
		    {
		    	
				if (isloadsucc) {
					$("#createtime")[0].innerHTML = data.map.taskdetail.createtime;
					$("#name")[0].innerHTML = data.map.taskdetail.name;
					$("#oper_name")[0].innerHTML = data.map.taskdetail.oper_name;
					$("#task_code")[0].innerHTML = data.map.taskdetail.task_code;
					$("#remark")[0].innerHTML = data.map.taskdetail.remark;
					$("#statename")[0].innerHTML = data.map.taskdetail.statename;
					$("#typenames")[0].innerHTML = data.map.taskdetail.typenames;
					var html="";
					for ( var i = 0; i <  data.map.stepList.length; i++) {
					 html +='	<div class="info"><div class="headdiv">'
							+'<div class="list"><p>●&nbsp;'+ data.map.stepList[i].step_name+'</p></div>'
							+'</div>'
							+'<div class="content">'
							+'	<div class="detail">'
							+'       <div class="left"><p>创建时间</p></div>'
							+'    	<div class="right"><p>'+ data.map.stepList[i].createtime+'</p></div>'
							+'     </div>'
							+'   <div class="detail">'
							+'      <div class="left"><p>当前处理人</p></div>'
							+' 	<div class="right"><p>'+ data.map.stepList[i].oper_name+'</p></div>'
							+' </div>'
							+'<div class="detail">'
							+'    <div class="left"><p>当前处理时间</p></div>'
							+'	<div class="right"><p>'+ data.map.stepList[i].operdate+'</p></div>'
							+' </div>'
							+' <div class="detail">'
							+'     <div class="left"><p>状态</p></div>'
							+' 	<div class="right">'+ data.map.stepList[i].step_name+'</div>'
							+' </div>'
							+' <div class="detail">'
							+'     <div class="left"><p>是否存在子任务</p></div>'
							+' 	<div class="right"><p>否</p></div>'
							+' </div>'
							+'<div class="detail">'
							+'   <div class="left"><p>已完成</p></div>'
							+'	<div class="left"><p>未超时</p></div>'
							+' 	<div class="left" onclick="showOrHide('+i+')"><img src="../../images/mission/3.png" alt="" /></div>	'	        	
							+'  </div>'
							+'  <div id="'+i+'" class="hide">备注：12334</div>	    	'
							+'	</div>'
							+'	</div>';
					}
					$("#actionHtml")[0].innerHTML=html;
		    	}else{
		    		alert(common.msg.error);
		    	}
		    }
		});
	},
	/**
	 * 显示详细信息
	 */
	detail:function(step_id,cfg_step_id){
		
		
		
	}
	
};
$(function(){
	showFlowDetail.init();
});