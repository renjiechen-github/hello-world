var agreementType = "2";
var agreementList = {
    // 页面初始化操作
    init: function () {
        // 创建表格
        agreementList.createTable();
        // 初始化下拉列表  
        agreementList.initSelect();

        $('#accountid,#status,#trading').change(function () {
            $('#serach').click();
        });
        $('#agreementType').change(function () {
            agreementType = $(this).val();
            agreementList.loadStatus(agreementType);
            $('#serach').click();
        });

        $('#areaid').change(function () {
            $('#trading').html('<option value="">请选择...</option>');
            $('#serach').click();
            if ($(this).val() != '')
            {
                agreementList.initType($(this).val(), '4', 'trading');
            }
        });
        $('#isSelf').click(function ()
        {
            if ($('#isSelf').attr('checked'))
            {
                $('#isSelf').prop('checked', true);
            } else
            {
                $('#isSelf').prop('checked', false)
            }
            $('#serach').click();
        });
        $('#myDiv').click(function ()
        {
            if ($('#isSelf').attr('checked'))
            {
                $('#isSelf').attr('checked', false);
            } else
            {
                $('#isSelf').attr('checked', true)
            }
            $('#serach').click();
        });

        // 时间按钮选择事件
        var nowTemp = new Date();
        $('#createtime').daterangepicker(
                {
                    startDate: nowTemp.getFullYear() + '-'
                            + (nowTemp.getMonth() + 1) + '-02',
                    timePicker12Hour: false,
                    timePicker: false,
                    separator: '~',
                    // 分钟间隔时间
                    timePickerIncrement: 10,
                    format: 'YYYY-MM-DD'
                }, function (start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
        $('#keyword').keydown(function (e) {
            if (e.which == "13") {

                // var focusActId = document.activeElement.id;获取焦点ID，根据ID判断执行事件
                $("#serach").click();
                return false;// 禁用回车事件
            }
        });
    },
    initSelect: function () {
        agreementList.loadStatus('2');
        agreementList.initType('101', '3', 'areaid');
//		agreementList.initType('','4','trading');
        // accountid
        common.ajax({
            url: common.root + '/BaseHouse/getMangerList.do',
            dataType: 'json',
            loadfun: function (isloadsucc, json) {
                if (isloadsucc) {
                    var html = '<option value="">请选择...</option>';
                    for (var i = 0; i < json.length; i++)
                    {
                        html += '<option value="' + json[i].id + '" >'
                                + json[i].name + '</option>';
                    }
                    $('#accountid').html(html);
                } else {
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
        });
    },
    initType: function (fatherid, type, id) {
        common.loadArea(fatherid, type, function (json) {
            var html = '<option value="">请选择...</option>';
            $('#' + id).html('');
            for (var i = 0; i < json.length; i++) {
                html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
            }
            $('#' + id).html(html);
        });
    },
    loadStatus: function (type) {
        var typeName = type == 1 ? 'GROUP.AGREEMENT' : 'GROUP.RANK.AGREEMENT';
        common.loadItem(typeName, function (json) {
            var html = '<option value="">请选择...</option>';
            for (var i = 0; i < json.length; i++) {
                html += '<option value="' + json[i].item_id + '" >'
                        + json[i].item_name + '</option>';
            }
            $('#status').html(html);
        });
    },
    createTable: function () {
        table
                .init({
                    id: '#agreementList_table',
                    url: common.root + '/agreementMge/loadAgreementList.do',
                    expname: '房源合约',
                    columns: [
                        "agree_code",
                        "name",
                        "areaname",
                        "userInfo",
                        "serviceMonery",
                        "propertyMonery",
                        "time",
                        "agreementStatus",
                        "cost",
                        "agentsname",
                        "checkouttime",
                        {
                            name: "rankspec",
                            isshow: false,
                            title: '出租房间数',
                            isover: true
                        },
                        {
                            name: "watercard",
                            isshow: false,
                            title: '水卡',
                            isover: false
                        },
                        {
                            name: "water_meter",
                            isshow: false,
                            title: '水表读数',
                            isover: false
                        },
                        {
                            name: "electriccard",
                            isshow: false,
                            title: '电卡帐号',
                            isover: false
                        },
                        {
                            name: "electricity_meter",
                            isshow: false,
                            title: '电表总值',
                            isover: false
                        },
                        {
                            name: "gascard",
                            isshow: false,
                            title: '燃气帐号',
                            isover: false
                        },
                        {
                            name: "gas_meter",
                            isshow: false,
                            title: '燃气读数',
                            isover: false
                        },
                        {
                            name: "createTime",
                            isshow: false,
                            title: '创建时间',
                            isover: false
                        }
//							"<div class=\"btn-group\"><button type=\"button\"  class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">操作<span class=\"caret\"></span></button><ul class='dropdown-menu ulClass'><li><a class='paddingClass' onclick='agreementList.edit(this,0);' href='#'>查看合约</a></li></ul></div>" 
                    ],
                    param: function () {
                        var a = getParamArray();
//						a.push({
//							"name" : "keyWord",
//							"value" : $('#keyword').val()
//						});
//						a.push({
//							"name" : "status",
//							"value" : $('#status').val()
//						});
//						a.push({
//							"name" : "arear",
//							"value" : $('#areaid').val()
//						});
//						a.push({
//							"name" : "accountid",
//							"value" : $('#accountid').val()
//						});
//						a.push({
//							"name" : "createtime",
//							"value" : $('#createtime').val()
//						}); 
//						a.push({
//							"name" : "isSelf",
//							"value" : $('#isSelf:checked').val()
//						});
//						a.push({
//							"name" : "trading",
//							"value" : $('#trading').val()
//						});
                        return a;
                    },
                    expPage: function ()
                    {
                        var newColumns = new Array();
                        newColumns.push({name: "agree_code", title: "合约编码"});
                        newColumns.push({name: "name", title: "合约名称"});
                        newColumns.push({name: "agentsname", title: "管家"});
                        newColumns.push({name: "areaname", title: "区域"});
                        newColumns.push({name: "username", title: "用户姓名"});
                        newColumns.push({name: "certificateno", title: "用户证件"});
                        newColumns.push({name: "user_mobile", title: "用户号码"});
                        newColumns.push({name: "begin_time", title: "合约开始时间"});
                        newColumns.push({name: "end_time", title: "合约结束时间"});
                        newColumns.push({name: "serviceMonery", title: "服务费"});
                        newColumns.push({name: "propertyMonery", title: "物业费"});
                        newColumns.push({name: "cost", title: "总费用"});
                        newColumns.push({name: "cost_a", title: "每年费用"});
                        newColumns.push({name: "agreementStatus", title: "合约状态"});
                        newColumns.push({name: "watercard", title: "水卡帐号"});
                        newColumns.push({name: "water_meter", title: "水表读数"});
                        newColumns.push({name: "gascard", title: "燃气帐号"});
                        newColumns.push({name: "gas_meter", title: "燃气读数"});
                        newColumns.push({name: "electriccard", title: "电表卡号"});
                        newColumns.push({name: "electricity_meter", title: "电表总值"});
                        newColumns.push({name: "electricity_meter_h", title: "电表峰值"});
                        newColumns.push({name: "electricity_meter_l", title: "电表谷值"});
                        newColumns.push({name: "createTime", title: "录入时间"});
                        newColumns.push({name: "checkouttime", title: "退租日期"});
                        newColumns.push({name: "rankspec", title: "出租间数"});
                        var href = common.root + '/agreementMge/loadAgreementList.do?isDc=true&expname=' + encodeURIComponent(encodeURI('房源合约')) + "&colum=" + encodeURIComponent(encodeURI(JSON.stringify(newColumns))) + '&istz_=1';
//						var paramlist = $('#loadAgreementList_table').data('paramlist');
                        var paramlist = getParamArray();

                        for (var i = 0; i < paramlist.length; i++) {
                            href += "&" + paramlist[i].name + "=" + encodeURI(encodeURI(paramlist[i].value));
                        }
                        common.load.load('正在执行导出，请稍等...');
                        common.ajax({
                            url: href,
                            dataType: 'json',
                            loadfun: function (isloadsucc, data) {
                                common.load.hide();
                                if (isloadsucc) {
                                    if (data.state == 1) {
                                        window.location.href = data.name;
                                    } else {
                                        common.alert(common.msg.error);
                                    }
                                } else {
                                    common.alert(common.msg.error);
                                }
                            }
                        });
                    },
                    bFilter: false,
                    bnt: [
                        {
                            name: '查看合约',
                            isshow: function (data, row) {
                                return true;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                if (agreementType == 1)
                                {
                                    agreementList.edit(0);
                                } else
                                {
                                    agreementList.rankEdit2(0);
                                }
                            }
                        },
                        {
                            name: '修改合约',
                            isshow: function (data, row) {
                            	//return true;
                            	var returnStatus = false;
                                common.ajax({
                                    url: common.root + '/agreementMge/isShowAgreement.do',
                                    dataType: 'json',
                                    data: {
                                    	aType:agreementType
                                    },
                                    encode: false,
                                    async: false,
                                    loadfun: function (isloadsucc, data)
                                    {
                                        if (data.status == "1")
                                        {
                                            returnStatus = true;
                                        }else{
                                        	returnStatus = false;
                                        }
                                    }
                                });
                                return returnStatus;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                if (agreementType == 1)
                                {
                                    agreementList.edit(1);
                                } else
                                {
                                    agreementList.rankEdit2(1);
                                }
                            }
                        },
                        {
                            name: '退租',
                            //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
                            isshow: function (data, row) {
                                var returnI = false;
                                if (agreementType == "2" && (data.status == "2" || data.status == "12" || data.status == "11"))
                                {
                                    var param = {'rentalLeaseOrderId': data.id};
                                    common.ajax({
                                        url: common.root + '/cancelLease/checkCancelLeaseOrder.do',
                                        dataType: 'json',
                                        contentType: 'application/json; charset=utf-8',
                                        encode: false,
                                        async: false,
                                        data: JSON.stringify(param),
                                        loadfun: function (isloadsucc, data)
                                        {
                                            if (data.haveOrder == "N")
                                            {
                                                returnI = true;
                                            }
                                        }
                                    });
                                }
                                return returnI;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.showTj(data.id);
                            }
                        },
                        {
                            name: '删除合约',
                            //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
                            isshow: function (data, row) {
//		                		 return (agreementType == 2 && (data.status == 0 || data.status == 11 || data.status == 12))?true:false;
                                return false; // (agreementType == 2 && (data.status == 0 || data.status == 11 || data.status == 12))?true:false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                if (data.status == 12)
                                {
                                    agreementList.delRankAgreement(data.status, '/rankHouse/delRankAgreementByStatus.do');
                                } else
                                {
                                    agreementList.delRankAgreement(data.status, '/rankHouse/delRankAgreement.do');
                                }
                            }
                        },
                        {
                            name: '下载合约',
                            //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
                            isshow: function (data, row) {
                                return (!common.isEmpty(data.notaryid)) ? true : false;
                                // return true;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.downloadAgreement(data.notaryid);
                            }
                        },
                        {
                            name: '查看合约图片',
                            //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
                            isshow: function (data, row) {
                                return (common.isEmpty(data.notaryid)) ? true : false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                var url = '/html/pages/house/agreement/viewAgreeImg.html';
                                var name = "viewAgreeImg";
                                common.openWindow({
                                    type: 1,
                                    name: name,
                                    title: "合约图片查看",
                                    url: url
                                });
                            }
                        },
                        {
                            name: '删除合约',
                            isshow: function (data, row) {
                                return (agreementType == 1 && (data.status == 0 || data.status == 11)) ? true : false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.del(data.status);
                            }
                        },

                        {
                            name: '提交合约',
                            isshow: function (data, row) {
                                return false;
//		                		 return data.status == 0? true : false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.submitAgreement();
                            }
                        },
                        {
                            name: '公证待审批',
                            isshow: function (data, row) {
                                return (data.status == 12 && agreementType == 1) ? true : false;
//		                		 return  false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.approvalAgreement(data.status);
                            }
                        },
                        {
                            name: '撤销合约',
                            isshow: function (data, row) {
//		                		 alert(data.is_work == 0);
                                return  false;
//		                		 return (data.status == 2 && data.is_work == 0)? true : false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.cancelAgreement();
                            }
                        },
                        {
                            name: '公证待审批',
                            isshow: function (data, row) {
//		                		 return data.status == 12 ? true : false;
                                return false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.spAgeement(data.status);
                            }
                        },
                        {
                            name: '结束合约',
                            isshow: function (data, row) {
//		                		 return data.status == 5 ? true : false;
                                return  false;
                            },
                            fun: function (data, row) {
                                rowdata = data;
                                agreementList.overAgreement();
                            }
                        },
                    ],
                    createRow: function (rowindex, colindex, name, value, data) {
                        // console.log(data[colindex]);
//						console.log(data.house_status); 
                        /** if(data.status == 0 && colindex == 8) // 待提交
                         {
                         return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.edit(this,1);" href="#">修改合约</a></li><li><a class="paddingClass" onclick="agreementList.del(this);" href="#">删除合约</a></li><li><a class="paddingClass" onclick="agreementList.submitAgreement(this);" href="#">提交合约</a></li></ul></div>';
                         //							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.edit(this,1);' title='修改合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button>&nbsp;<button onclick='agreementList.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button onclick='agreementList.submitAgreement(this);' title='提交合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
                         }
                         else if(data.status == 1 && colindex == 8) // 提交待审批
                         {
                         return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.approvalAgreement(this);" href="#">审批合约</a></li></ul></div>';
                         //							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button><button onclick='agreementList.approvalAgreement(this);' title='审批合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-filter\"></i></button></div>";
                         }
                         else if(data.status == 2 && colindex == 8 && data.is_work == 0) // 2已生效 
                         { 
                         return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.cancelAgreement(this);" href="#">撤销合约</a></li></ul></div>';
                         //							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.cancelAgreement(this);' title='撤销合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button></div>";
                         }
                         //						 else if(data.status == 3 && colindex == 8) // 3 已失效
                         //						 {
                         //							 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" href="javascript:agreementList.cancelAgreement(this);">撤销合约</a></li></ul></div>';
                         //							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>";
                         //						 }
                         else if(data.status == 4 && colindex == 8) // 4审批撤销
                         {
                         return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.spAgeement(this);" href="#">审批撤销</a></li></ul></div>';
                         //							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.spAgeement(this);' title='审批撤销' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
                         }
                         else if(data.status == 5 && colindex == 8) // 结束待审批
                         {
                         return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.overAgreement(this);" href="#">结束合约</a></li></ul></div>';
                         //							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.houseRelease(this);' title='发布合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
                         }*/
                        console.log("agreementType:" + agreementType);
                        if (agreementType == 1)
                        {
                            console.log("agreementType:" + agreementType + "data.status:" + data.status);
                            if (data.status == 0 && colindex == 7)
                            {
                                return '<div style="color:#F8B62D;text-align: center;"><img src="/html/images/dtj.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 1 && colindex == 7)
                            {
                                return '<div style="color:#ED7210;text-align: center;"><img src="/html/images/dsp.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 2 && colindex == 7)
                            {
                                return '<div style="color:#3E82BC;text-align: center;"><img src="/html/images/dkg.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 3 && colindex == 7)
                            {
                                return '<div style="color:#E74086;text-align: center;"><img src="/html/images/jsdsp.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 4 && colindex == 7)
                            {
                                return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/djj.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 5 && colindex == 7)
                            {
                                return '<div style="color:#D0B6D7;text-align: center;"><img src="/html/images/ysx.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            }
                        } else
                        {
                            if (data.status == 0 && colindex == 7)
                            {
                                return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/djj.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 1 && colindex == 7)
                            {
                                return '<div style="color:#ED7210;text-align: center;"><img src="/html/images/dsp.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 2 && colindex == 7)
                            {
                                return '<div style="color:#A94F9A;text-align: center;"><img src="/html/images/yfb.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 3 && colindex == 7)
                            {
                                return '<div style="color:#ff0000;text-align: center;"><img src="/html/images/qyz.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 4 && colindex == 7)
                            {
                                return '<div style="color:#F8B62D;text-align: center;"><img src="/html/images/dtj.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            } else if (data.status == 5 && colindex == 7)
                            {
                                return '<div style="color:#D0B6D7;text-align: center;"><img src="/html/images/ysx.png">&nbsp;&nbsp;' + data.agreementStatus + '</div>';
                            }
                        }
                        if (colindex == 0)
                        {
                            if (agreementType == 1)
                            {
                                return "<a onclick='agreementList.edit2(" + data.id + "," + data.house_id + ");return false;' href='#' title='点击查看合约信息' style='cursor: pointer;'>" + value + "</a>";
                            } else
                            {
                                return "<a onclick='agreementList.rankEdit(this,0);return false;' href='#' title='点击查看合约信息' style='cursor: pointer;'>" + value + "</a>";
                            }
                        }



                        if (colindex == 1 || colindex == 3)
                        {
                            return agreementList.dealColum({"value": value, "length": 16});
                        }
                        if (colindex == 0)
                        {
                            return agreementList.dealColum({"value": value, "length": 5});
                        }
                        return value;
                    }
                });
    },
    dealColum: function (opt)
    {
        var def = {value: '', length: 5};
        jQuery.extend(def, opt);
        if (common.isEmpty(def.value))
        {
            return "";
        }
        if (def.value.length > def.length)
        {
            return "<div title='" + def.value + "'>" + def.value.substr(0, def.length) + "...</div>";
        } else
        {

            return def.value;
        }
    },
    // 修改
    edit: function (flag) {
        var id = rowdata.id;
        var house_id = rowdata.house_id;
        var title = '修改合约';
        if (flag == 0)
        {
            title = '合约详细';
        }
        common.openWindow({
            area: ['900px', '630px'],
            name: 'houseSign',
            type: 3,
            data: {agreementId: id, flag: flag, id: house_id},
            title: title,
            url: '/html/pages/house/houseInfo/new_house_agreement.html'
//			url : '/html/pages/house/houseInfo/houseSign.html'
        });
    },
    edit2: function (id, house_id) {
        var title = '合约详细';
        common.openWindow({
            area: ['900px', '630px'],
            name: 'houseSign',
            type: 3,
            data: {agreementId: id, flag: 0, id: house_id},
            title: title,
            url: '/html/pages/house/houseInfo/new_house_agreement.html'
        });
    },
    rankEdit: function (obj, flag) {
        //	console.log(rowdata);
        rowdata = table.getRowData('agreementList_table', obj);
        var name = rowdata.title;
        var house_rank_id = rowdata.house_rank_id;
        var agreementId = rowdata.id;
        var house_id = rowdata.house_id;
        var rankType = rowdata.rankType;
        var title = '修改合约';
        if (flag == 0)
        {
            title = '合约详细';
        }
        common.openWindow({
            area: ['900px', '630px'],
            name: 'signHouse',
            type: 3,
            data: {id: house_rank_id, flag: flag, houseId: house_id, rankType: rankType, title: name, agreementId: agreementId},
            title: title,
            url: '/html/pages/house/houseInfo/new_rank_house_agreement.html'
        });
    },
    rankEdit2: function (flag) {
        //	console.log(rowdata);
        var name = rowdata.title;
        var house_rank_id = rowdata.house_rank_id;
        var agreementId = rowdata.id;
        var house_id = rowdata.house_id;
        var rankType = rowdata.rankType;
        var title = '修改合约';
        if (flag == 0)
        {
            title = '合约详细';
        }
        common.openWindow({
            area: ['900px', '630px'],
            name: 'signHouse',
            type: 3,
            data: {id: house_rank_id, flag: flag, houseId: house_id, rankType: rankType, title: name, agreementId: agreementId},
            title: title,
            url: '/html/pages/house/houseInfo/new_rank_house_agreement.html'
        });
    },
    // 审批合约
    approvalAgreement: function (status) {
        var id = rowdata.id;
        var houseId = rowdata.house_id;
        var isPass = 0;
        common.alert({
            msg: '此合约是否审核通过？',
            confirm: true,
            closeIcon: true,
            confirmButton: '通过',
            cancelButton: '拒绝',
            fun: function (action)
            {
                if (action)
                {
                    isPass = 2;
                }
                common.ajax({
                    url: common.root + '/agreementMge/approvalAgreement.do',
                    data: {
                        id: id,
                        isPass: isPass,
                        houseId: houseId,
                        status: status
                    },
                    dataType: 'json',
                    loadfun: function (isloadsucc, data) {
                        if (isloadsucc) {
                            if (data.state == '1') {
                                //msg = isPass == 1?'审批通过操作成功!':'操作成功！';
                                common.alert({
                                    msg: '操作成功！',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else if (data.state == -12) {
                                common.alert({
                                    msg: ' 未查询到财务项目信息  !',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else if (data.state == -2) {
                                common.alert({
                                    msg: '房源状态已经改变，请稍候操作!',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        } else {
                            common.alert({
                                msg: common.msg.error
                            });
                        }
                    }
                });
            }
        });

    },
    // 审批合约
    spAgeement: function (status) {
        var id = rowdata.id;
        var houseId = rowdata.house_id;
        var isPass = 2;
        common.alert({
            msg: '是否将此合约置为无效？',
            confirm: true,
            confirmButton: '通过',
            cancelButton: '拒绝',
            closeIcon: true,
            fun: function (action)
            {
                if (action)
                {
                    isPass = 3;
                }
                common.ajax({
                    url: common.root + '/agreementMge/spAgeement.do',
                    data: {
                        id: id,
                        isPass: isPass,
                        houseId: houseId,
                        status: status
                    },
                    dataType: 'json',
                    loadfun: function (isloadsucc, data) {
                        if (isloadsucc) {
                            if (data.state == '1') {
                                //msg = isPass == 1?'审批通过操作成功!':'操作成功！';
                                common.alert({
                                    msg: '操作成功！',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else if (data.state == -2) {
                                common.alert({
                                    msg: '房源状态已经改变，请稍候操作!',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else if (data.state == -10) {
                                common.alert({
                                    msg: '工程已经开工,不能撤销!',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else if (data.state == -12) {
                                common.alert({
                                    msg: ' 未查询到财务项目信息  ',
                                    fun: function () {
                                        table.refreshRedraw('agreementList_table');
                                    }
                                });
                            } else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        } else {
                            common.alert({
                                msg: common.msg.error
                            });
                        }
                    }
                });
            }
        });
    },
    // 提交合约
    submitAgreement: function (obj) {
        var id = rowdata.id;
        common.alert({
            msg: '确定提交合约吗？',
            confirm: true,
            fun: function (action) {
                if (action) {
                    common.ajax({
                        url: common.root + '/agreementMge/submitAgreement.do',
                        data: {
                            id: id
                        },
                        dataType: 'json',
                        loadfun: function (isloadsucc, data) {
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '提交成功',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else if (data.state == -2) {
                                    common.alert({
                                        msg: '合约状态已经改变,不能提交合约!',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else {
                                    common.alert({
                                        msg: common.msg.error
                                    });
                                }
                            } else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        }
                    });
                }
            }
        });
    },
    // 结束合约
    overAgreement: function (obj) {
//		console.log(table.getRowData('agreementList_table', obj));
        var id = rowdata.id;
        var house_id = rowdata.house_id;
        common.alert({
            msg: '确定结束房源合约吗？',
            confirm: true,
            closeIcon: true,
            fun: function (action) {
                if (action) {
                    common.ajax({
                        url: common.root + '/agreementMge/overHouseAgreement.do',
                        data: {id: id, houseId: house_id},
                        dataType: 'json',
                        loadfun: function (isloadsucc, data) {
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '操作成功',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else {
                                    common.alert({
                                        msg: common.msg.error
                                    });
                                }
                            } else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        }
                    });
                }
            }
        });
    },
    // 取消合约
    cancelAgreement: function (obj) {
        var id = rowdata.id;
        var housid = rowdata.house_id;
        var isPass = 4;
        confirmButton:'是';
        cancelButton:'否';
        confirm : true,
                common.alert({
                    msg: '您确定撤销此合约？',
                    confirm: true,
                    fun: function (action) {
                        if (action)
                        {
                            common.ajax({
                                url: common.root + '/agreementMge/cancelAgreement.do',
                                data: {id: id, isPass: isPass, housid: housid},
                                dataType: 'json',
                                loadfun: function (isloadsucc, data) {
                                    if (isloadsucc) {
                                        if (data.state == '1') {
                                            common.alert({
                                                msg: '操作成功!',
                                                fun: function () {
                                                    table.refreshRedraw('agreementList_table');
                                                }
                                            });
                                        } else if (data.state == -2) {
                                            common.alert({
                                                msg: '房源状态审批,请重新查看!',
                                                fun: function () {
                                                    table.refreshRedraw('agreementList_table');
                                                }
                                            });
                                        } else if (data.state == -11)
                                        {
                                            common.alert({
                                                msg: '房源已开工无法撤销!',
                                                fun: function () {
                                                    table.refreshRedraw('agreementList_table');
                                                }
                                            });
                                        } else
                                        {
                                            common.alert({
                                                msg: common.msg.error
                                            });
                                        }
                                    } else {
                                        common.alert({
                                            msg: common.msg.error
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
    },
    del: function (status) {
//		var table1 = table.getTable('agreementList_table');
        //获取当前行的数据信息,用来区分是新增还是修改的，同时也是修改的数据信息
//		console.log(rowdata);
        common.alert({
            msg: '确定删除合约吗？',
            confirm: true,
            fun: function (action) {
                if (action) {
                    common.ajax({
                        url: common.root + '/agreementMge/delAgreement.do',
                        data: {id: rowdata.id, houseId: rowdata.house_id, status: status},
                        dataType: 'json',
                        loadfun: function (isloadsucc, data) {
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '删除成功',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else if (data.state == -2) {
                                    common.alert({
                                        msg: '合约状态发生改变,请重新确认!',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else {
                                    common.alert({
                                        msg: common.msg.error
                                    });
                                }
                            } else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        }
                    });
                }
            }
        });
    },
    showTj: function (id)
    {
        common.openWindow({
            area: ['500px', '300px'],
            name: 'showTj',
            type: 1,
            data: {id: id, flag: 1},
            title: '合约退租',
            url: '/html/pages/house/rankHouse/tjAgreement.html'
        });
    },
    delRankAgreement: function (status, url) {
        common.alert({
            msg: '确定需要删除合约吗？',
            confirm: true,
            fun: function (action) {
                if (action) {
                    common.ajax({
//						url : common.root + '/rankHouse/delRankAgreement.do',
                        url: common.root + url,
                        data: {id: rowdata.id, houseId: rowdata.house_id, house_rank_id: rowdata.house_rank_id, status: status},
                        dataType: 'json',
                        loadfun: function (isloadsucc, data) {
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '删除成功',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else if (data.state == -2) {
                                    common.alert({
                                        msg: '合约状态发生改变,请重新确认!',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else if (data.state == -12) {
                                    common.alert({
                                        msg: '当前房源已下架,请勿删除!',
                                        fun: function () {
                                            table.refreshRedraw('agreementList_table');
                                        }
                                    });
                                } else {
                                    common.alert({
                                        msg: common.msg.error
                                    });
                                }
                            } else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        }
                    });
                }
            }
        });
    },
    downloadAgreement: function (notaryid) {
        common.ajax({
            url: common.root + '/agreementMge/downloadAgreement.do',
            data: {notaryid: notaryid},
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.result == '1') {
                        console.log(data)
                        $('#form3').attr("action", data.url);
                        $('#content').val(data.content);
                        $('#signed').val(data.signed);
                        $('#key').val(data.key);
                        $('#apiid').val(data.apiid);
                        $('#form3').submit();
                    } else {
                        common.alert({
                            msg: '网络忙重试!'
                        });
                    }
                } else {
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
        });
    }
//	//房源签约
//	houseSign:function(obj){
//		 console.log(table.getRowData('agreementList_table', obj));
//		var username = table.getRowData('agreementList_table', obj).username; //房主姓名
//		var user_id = table.getRowData('agreementList_table', obj).user_id; //房主id
//		var mobile = table.getRowData('agreementList_table', obj).mobile; //房主mobile
//		var house_name = table.getRowData('agreementList_table', obj).house_name; //房源名称
//		var id = table.getRowData('agreementList_table', obj).id;
//		var floor = table.getRowData('agreementList_table', obj).floor; // 楼层
//		var roomCnt = table.getRowData('agreementList_table', obj).spec.split("|"); //roomCnt
//		var is_cubicle = table.getRowData('agreementList_table', obj).is_cubicle; //roomCnt
//		var houseCode = table.getRowData('agreementList_table', obj).house_code; //roomCnt
//		var free_rent = table.getRowData('agreementList_table', obj).free_rent; //roomCnt
//		var title = '签约房源';
//		// 验证房源现在状态是否签约中，如果是签约中，不能签约房源
//		common.ajax(
//		{
//			url : common.root + '/agreementList/checkHouseStatus.do',
//			data : {id : id},
//			dataType : 'json', 
//			loadfun : function(isloadsucc, data) 
//			{
//				if (isloadsucc) 
//				{
//					if (data.state == '1') 
//					{
//						common.openWindow({
//							area:['900px','630px'],
//							name:'houseSign',
//							type : 3,
//							data:{id:id,username:username,user_id:user_id,mobile:mobile,house_name:house_name,floor:floor,roomCnt:roomCnt,is_cubicle:is_cubicle,houseCode:houseCode,free_rent:free_rent},
//							title : title,
//							url : '/html/pages/house/houseInfo/houseSign.html'
//						});
//					}
//					else
//					{
//						common.alert(
//						{
//							msg : '房源或许已经被签约,请重新操作!'
//						});
//						table.refreshRedraw('agreementList_table');
//					}
//				}
//			}
//		});
//	},
};
agreementList.init();

function getParamArray()
{
    var paramlist = new Array();
    paramlist.push({
        "name": "keyWord",
        "value": $('#keyword').val()
    });
    paramlist.push({
        "name": "agreementType",
        "value": $('#agreementType').val()
    });
    paramlist.push({
        "name": "status",
        "value": $('#status').val()
    });
    paramlist.push({
        "name": "arear",
        "value": $('#areaid').val()
    });
    paramlist.push({
        "name": "accountid",
        "value": $('#accountid').val()
    });
    paramlist.push({
        "name": "createtime",
        "value": $('#createtime').val()
    });
    paramlist.push({
        "name": "isSelf",
        "value": $('#isSelf:checked').val()
    });
    paramlist.push({
        "name": "trading",
        "value": $('#trading').val()
    });
    return paramlist;
}
