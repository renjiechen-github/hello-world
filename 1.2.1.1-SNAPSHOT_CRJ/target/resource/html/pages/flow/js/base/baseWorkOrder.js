/**
 * Created by duanyongrui on 2017/5/27.
 */
var baseWorkOrder = {
		init: function() {
			$('textarea').on('input',function(event) {
	     	   if(this.value.length >= 512) {
	     	        this.value = this.value.substr(0,512);
	     	    }
	     	})
		},
	// 加载时间轴
	loadStaft : function(id) {
		console.log('loadStaft');
		//处理创建人
		var create_oper = "";

		if (workOrder.createdStaffName != null
				&& workOrder.createdStaffName != "") {
			create_oper = workOrder.createdStaffName
		} else if (workOrder.createdUserName != null
				&& workOrder.createdUserName != "") {
			create_oper = workOrder.createdUserName;
		} else if (workOrder.userName != null && workOrder.userName != "") {
			create_oper = workOrder.userName;
		}
		console.log('loadProgress');

		$('#cd-timeline').css('display', 'block');
		var sOut = '';
		var param = {
			'workOrderId' : id
		};
		common.ajax({
					url : common.root + '/workOrder/getHis.do',
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					encode : false,
					data : JSON.stringify(param),
					async : false,
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							var resData = data.subOrderHisList;
							var date = "";
							var length = resData.length;
							for (var i = 0; i < length; i++) {
								if (i == 0) {
									date = resData[0].createdDate;
								}
								var changeMessage = "";
								var subOrderValueHisList = resData[i].subOrderValueList;
								if (subOrderValueHisList != null && subOrderValueHisList.length >= 0) {
									for (var j = 0; j < subOrderValueHisList.length; j++) {
										if (subOrderValueHisList[j].attrPath.lastIndexOf('.COMMENTS') > -1
													|| subOrderValueHisList[j].attrPath.lastIndexOf('.HOUSE_INSPECTION_COMMENTS') > -1) {
											changeMessage = subOrderValueHisList[j].textInput;
											break;
										}
									}
								}

								// if (resData[i].changeMessage) {
								// 	changeMessage = resData[i].changeMessage;
								// }
								// else {
								// 	changeMessage = "";
								// }
								var stateName = "";
								if (resData[i].stateName != null && resData[i].stateName != "") {
									stateName = resData[i].stateName;
								}
								sOut += '<div class="cd-timeline-block">'
										+ '<div class="cd-timeline-img cd-picture">'
										+ '<img src="/html/images/cd-icon-picture.svg" alt="Picture" />'
										+ '</div>'
										+ '<div class="cd-timeline-content">'
										+ '<h2>' + resData[i].updatePersonName
										+ '</h2>' + '<h2>'
										+ (i == 0 ? '创建订单' : stateName)
										+ '</h2>' + '<p>' + changeMessage
										+ '</p>' + '<span class="cd-date">'
										+ resData[i].updateDate + '</span>'
										+ '</div>' + '</div>';
								date = resData[i].updateDate;
							}
						}
					}
				});
		$('.cd-container').append(sOut);
	}
};

$(function() {
	baseWorkOrder.init();
});