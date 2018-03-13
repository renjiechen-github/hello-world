$(function()
{
	
});
function getMangerList(role,obj,select)
{
	 common.ajax({
		url : common.root + '/sys/getMangerList.do',
		dataType : 'json',
		data:{role_Id:role},
		async : false,
		loadfun : function(isloadsucc, json) 
		{
		  if (isloadsucc)
		  {
			 var html = "<option value=''> 请选择...</option>";
			 for ( var i = 0; i < json.length; i++) 
			 {
				 html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
			 }
			     obj.append(html);
			 if (select!=null&&select=="2") 
			 {
				 obj.select2();
			 }
		   } 
		   else 
		   {
		       common.alert({msg : common.msg.error});
		   }
		}
	});
}