var table = {
    /**
     * 存放当前对象中有多少表格信息
     * @param {Object} opt
     */
    obj: [],
    /**
     * 用来存储是否是需要进行重新绘制表格数量的存储,起到标示作用
     * @param {Object} opt
     */
    redrawObj: [],
    /**
     * 初始化加载表格
     * @param {Object} opt
     */
    init: function (opt) {
        var def = {
            /**
             * 表格对应的值，跟jquery选择器一致
             */
            id: '',
            /**
             * 表格加载的url
             */
            url: '',
            /**
             * 数据加载对应的列，与后台传入对应，第一个传递默认是操作，如删除等按钮的标签入：{'<a>删除</a>','menu_id','saa'}
             * 完整传递参数：{"name":"name_1",isdc:true,isshow:false,title:'名称'}：isdc 为true支出导出，false 不导出，默认导出 ，isshow true显示到table中，false不显示，只做导出时使用,title 是表头显示的文字
             */
            columns: {},
            /**
             * 排序[[列,排序方式],[列,排序方式]]
             */
            aaSorting: [],
            /**
             * 是否需要多选框
             */
            ismultiple: false,
            /**
             * 是否需要单选框
             */
            issingle: false,
            singleid: "table" + parseInt(Math.random() * 100000),
            select: function (data) {

            },
            /**
             * 是否显示导出按钮 
             */
            isexp: true,
            /**
             * 上面对应的简写，此处对应的全写,此值不可与columns同时设置，同时设置会以aoColumns为准
             */
            aoColumns: [],
            /**
             * 是否显示默认的加载框 默认为false 因为已经重绘了
             */
            bProcessing: false,
            /**
             * 溢出界面内容是否显示在首界面添加+
             */
            isOverflowShow: true,
            /**
             * 操作按钮处理，传入方式：[{name:'按钮',isshow:function(data){ return true;},fun:function(data,row){}}]
             */
            bnt: null,
            /**
             * 创建溢出显示的时候调用的函数
             * @param data 当前行的数据信息
             * @param head 已经标记需要添加到溢出中显示的标签
             */
            overflowfun: function (data, head) {},
            /**
             * 每创建一行对应会执行该方法，此行包含添加序号的默认方法，请尽可能不要重绘
             * @param {Object} row
             * @param {Object} data
             * @param {Object} index
             */
            fnCreatedRow: function (row, data, index) {
                //添加序号
                var idisplaylength = $(def.id).attr('idisplaylength');
                var iDisplayStart = $(def.id).attr('iDisplayStart');
                var datalength = $(def.id).attr('datalength');//
                var num = parseInt(iDisplayStart == '' ? 0 : iDisplayStart) + index + 1;
                if (def.isOverflowShow) {
                    if (def.ismultiple) {//存在多选，需要进行出现多选按钮
                        $('td:eq(1)', row).html('<b><input type="checkbox" id="_table_select_" style="width: 20px" num="' + num + '" class="checkbox form-control"  >' + num + '</b>');
                    } else if (def.issingle) {
                        $('td:eq(1)', row).html('<b><input type="radio" style="width: 20px" title="双击选择"  id="' + def.singleid + '" name="' + def.singleid + '" num="' + num + '" class="checkbox form-control"  >' + num + '</b>');
                    } else {
                        $('td:eq(1)', row).html('<b>' + num + '</b>');
                    }
                } else {
                    if (def.ismultiple) {//存在多选，需要进行出现多选按钮
                        $('td:eq(0)', row).html('<b><input type="checkbox" id="_table_select_" style="width: 20px" num="' + num + '" class="checkbox form-control"  >' + num + '</b>');
                    } else if (def.issingle) {
                        $('td:eq(0)', row).html('<b><input type="radio" style="width: 20px" title="双击选择" id="' + def.singleid + '" name="' + def.singleid + '" num="' + num + '" class="checkbox form-control"  >' + num + '</b>');
                    } else {
                        $('td:eq(0)', row).html('<b>' + num + '</b>');
                    }
                }
                //console.log(row);
                //console.log(data); 
                var rowobj = $('td', row);
                var length = rowobj.size();

                var aoColumns = $(def.id).data('aoColumns');//所有列
                //console.log(aoColumns);
                var iss = $(def.id).data('iss');//是否存在对应的操作列
                //var cznum = iss?1:0;
                var onum = aoColumns[0].sTitle == '' ? 1 : 0;
                //console.log("onum:"+onum);
                var j = 0;
                //检查是否存在对应的操作跟，前缀+号
                for (var i = 0; i < length; i++) {
                    //def.createRow(index,);
                    if (i < length && i >= onum + 1) {
                        var name = aoColumns[i].mData == undefined ? '操作' : aoColumns[i].mData;
                        //console.log(i);
                        var value = def.createRow(index, j, name, data[name], data, row);
                        if (name == '操作')
                        {
                            if (value === undefined)
                            {
                                $('td:eq(' + i + ')', row).html(value);
                            } else
                            {
                                $('td:eq(' + i + ')', row).html('<div style="width:100%;text-align: center;">' + value + '</div>');
                            }
                            if (def.bnt != null) {
                                //创建按钮
//								var html = '<div class="btn-group caozuobnt">'+
//                                           ' <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">Default <span class="caret"></span></button>'+
//                                           ' <ul role="menu" class="dropdown-menu">'+
//                                            '    <li><a href="#">Action</a></li>'+
//                                             '   <li><a href="#">Another action</a></li>'+
//                                              '  <li><a href="#">Something else here</a></li>'+
//                                               ' <li><a href="#">Separated link</a></li>'+
//                                            '</ul>'+
//                                        '</div>';
                                var div = document.createElement("div");
                                div.setAttribute("class", "btn-group caozuobnt");
                                div.setAttribute("rowi", index + 1);
                                var button = document.createElement("a");
//								button.setAttribute("data-toggle", "dropdown");
                                button.setAttribute("type", "button");
                                button.setAttribute("class", "fa fa-external-link");
                                button.setAttribute("style", "color: #000000; font-size: 18px; text-decoration:none; cursor:pointer;");
//								var span = document.createElement("span");
//								span.setAttribute("class", "caret");
//								button.innerHTML  = "操作";
//								button.appendChild(span);
                                div.appendChild(button);

                                var ul = document.createElement("ul");
                                ul.setAttribute("role", "menu");
                                ul.setAttribute("role", "menu" + index);
                                ul.setAttribute("class", "dropdown-menu");
                                div.appendChild(ul);
                                $('td:eq(' + i + ')', row).html(div);

                                $(row).data('rowData', data);
                                $('.caozuobnt').die('click');
                                $('.caozuobnt').live('click', function (event) {
                                    //阻止事件传递到后面的body上面
                                    event.preventDefault();
                                    if (event.stopPropagation)
                                        event.stopPropagation();
                                    else
                                        event.cancelBubble = true;
                                    //如果不存在就打开，存在就清除
                                    if ($(this).attr('isopen') == 1) {
                                        $('.table_menu').remove();
                                        $(this).attr('isopen', 0);
                                        return;
                                    }
                                    var rowData = $(this).parents('tr').data('rowData');
                                    $('.table_menu').remove();
                                    $(this).attr('isopen', '1');
                                    var menudata = $(this).parents('.dataTable').data('menuData');
                                    var htmlul = "<div class='table_menu' ><ul class='ulClass'>";
                                    for (var ii = 0; ii < menudata.length; ii++) {
                                        var t = true;
                                        if (typeof (menudata[ii].isshow) == 'function') {
                                            t = menudata[ii].isshow(rowData, $(this).parents('tr'));
                                        }
                                        if (t == true) {
                                            htmlul += '<li><a class="paddingClass" datai="' + ii + '" href="#">' + menudata[ii].name + '</a></li>';
                                        }
                                    }
                                    htmlul += '</ul></div>';
                                    $('body').append(htmlul);
                                    $('.table_menu').attr('tableid', $(this).parents('.dataTable').attr('id'));
                                    $('.table_menu').attr('rowi', $(this).attr('rowi'));
                                    //计算坐标位置信息
                                    var table_menu_w = $('.table_menu').width();
                                    var caozuobnt_h = $('.table_menu').height();
                                    var caozuobnt_w = $(this).width();
                                    var top = $(this).offset().top;
                                    var left = $(this).offset().left;
                                    $('.table_menu').css({'left': left - table_menu_w + caozuobnt_w, 'top': top + 34});
                                    return false;
                                });
                                $('.table_menu .paddingClass').die('click');
                                $('.table_menu .paddingClass').live('click', function (event) {
                                    var datai = $(this).attr('datai');
                                    var tableid = $(this).parents('.table_menu').attr('tableid');
                                    var rowi = $(this).parents('.table_menu').attr('rowi');
                                    var row1 = $("#" + tableid).find("tr[num='" + (rowi - 1) + "']");
                                    var menudata = $("#" + tableid).data('menuData');
                                    var rowData = $(row1).data('rowData');
                                    menudata[datai].fun(rowData, row1);
                                });
                                $('body').click(function () {
                                    $('.table_menu').remove();
                                });
                                $(def.id).data('menuData', def.bnt);
                            }
                        } else
                        {
                            $('td:eq(' + i + ')', row).html(value);
                        }

                        j++;
                    }
                }
                //alert($('td',row).size());
                $(row).attr('num', index);
                //进行统一更改null赋值为空
                //common.log(row);
                $(row).find('td').each(function (i, n) {
                    if ($(this).text() == 'null') {
                        $(this).text('');
                    }
                });
                //console.log(data);
                if (datalength == num) {//进行判断是否已经结束了,结束后清除对应的加载等待
                    table.rmLoad(def.loadObj);
                }
            },
            /**
             * 创建函数执行
             * @param {Object} rowindex 所在行
             * @param {Object} colindex 所在列
             * @param {Object} name 字段名称
             * @param {Object} value 字段值
             * @param {Object} data 此行的数据信息
             */
            createRow: function (rowindex, colindex, name, value, data, row) {
                //console.log(rowindex+"--"+colindex+"--"+name+"--"+value+"--"+data);
                return value;
            },
            /**
             * 表格绘制结束调用
             * @param {Object} nRow
             * @param {Object} aData
             * @param {Object} iDisplayIndex
             * @param {Object} iDisplayIndexFull
             */
            fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                var idisplaylength = $(def.id).attr('idisplaylength');//需要显示出的总条数
                var datalength = $(def.id).attr('datalength');//
                if (iDisplayIndex + 1 == idisplaylength || iDisplayIndex + 1 == datalength) {//进行判断是否已经结束了,结束后清除对应的加载等待
                    table.rmLoad(def.loadObj);
                }
            },
            /**
             * 表格所占用的款
             */
            sScrollX: '100%',
            /**
             * 可设置表头信息，默认设置第一列与第二列不排序
             */
            aoColumnDefs: [
                {"bSortable": false, "aTargets": [0]}
            ],
            "oLanguage": {
                bAutoWidth: true,
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "抱歉， 没有找到",
                "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                "sInfoEmpty": "",
                "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                'sSearch': '搜索',
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                },
                "sZeroRecords": "没有检索到数据",
                sProcessing: '<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
                sEmptyTable: '很抱歉，暂无数据，你可以尝试更换查询条件进行检索'
            },
            /**
             * 导出excel名称
             */
            expname: window.document.title,
            /**
             * 是否去除搜索框
             * @param {Object} sUrl
             * @param {Object} aoData
             * @param {Object} fnCallback
             * @param {Object} oSettings
             */
            bFilter: false,
            "aLengthMenu": [5, 10, 25, 50, 100],
            "iDisplayLength": 10, //table表格默认每页显示行数
            bStateSave: true, // 是否记录上次状态
            fnPreCallback: function (data) {
                return data;
            },
            fnServerData: function (sUrl, aoData, fnCallback, oSettings) {
                oSettings.jqXHR = $.ajax({
                    "url": sUrl,
                    "data": aoData,
                    "success": function (json) {
                        if (json.state == '-99999') {
                            common.alert({
                                msg: '您还未登陆或登陆已经失效，请登录。',
                                fun: function () {
                                    window.location.href = 'login.html';
                                }
                            });
                            return;
                        } else if (json.state == '-99998') {
                            common.alert({
                                msg: '很抱歉，您没有权限进行此操作。',
                                fun: function () {
                                    table.rmLoad(def.loadObj);
                                }
                            });
                            return;
                        } else if (json.state == '-99997') {
                            common.alert({
                                msg: '很抱歉，您已在其他地方登陆了系统，如不是本人操作可重新登陆修改密码。',
                                fun: function () {
                                    window.location.href = 'login.html';
                                }
                            });
                            return;
                        }
                        if (json.state == '1') {
                            $(def.id).attr('datalength', json.iTotalRecords);
                            $(def.id).attr('iDisplayLength', json.iDisplayLength);
                            $(def.id).attr('iDisplayStart', json.iDisplayStart);
                            $(oSettings.oInstance).trigger('xhr', [oSettings, json]);
                            if (json.iTotalRecords == 0) {
                                table.rmLoad(def.loadObj);
                            }
                            $(def.id).data('datajson',json);
                            if (def.fnPreCallback != null) {
                                fnCallback(def.fnPreCallback(json));
                                
                                return;
                                return;
                            }
                            fnCallback(json);
                        } else {
                        	if (json != undefined && json != null && json.data != '') {
                            common.alert({
                                msg: json.data
                            });                        		
                        	} else {
                            common.alert({
                                msg: '出现未知错误'
                            });                        		
                        	}

                            return;
                        }
                    },
                    "dataType": "json",
                    "cache": false,
                    "type": oSettings.sServerMethod,
                    "error": function (xhr, error, thrown) {
                        common.alert({
                            msg: '加载失败'
                        });
                        table.rmLoad(def.loadObj);
                        return;
                    }
                });
            },
            /**
             * 需要额外添加的传递参数值，传递后台的时候会连带此参数一并传递过去，请返回数组形式的参数，如[{ "name": "isCxSeach", "value": isCxSeach},{ "name": "isCxSeach", "value": isCxSeach}]
             */
            param: function () {
                return []
            },
            loadObj: '.wrapper',
            /**
             * 在开始调用后台之前进行参数处理
             * @param {Object} aoData
             */
            fnServerParams: function (aoData) {
                //进行加载对应的加载框
                table.addLoad(def.loadObj);
                var datalength = $(def.id).attr('datalength');
                aoData.push({"name": "sizelength", "value": datalength == undefined ? '' : datalength});
                //检查搜索框内容内容是否已经改变 1需要重新计算数据长度，0不需要重新计算数据长度
                var isCxSeach = 1;
                var seach = $(def.id).attr('seachvalue');
                if (seach == undefined) {
                    $(def.id).attr('seachvalue', aoData[11].value);
                } else {
                    $(def.id).attr('seachvalue', aoData[11].value);
                    if (seach == aoData[11].value) {
                        isCxSeach = 0;
                    }
                }
                //进行判断是否存在需要进行重新绘制表格
                /**
                 * 检查是否改变了对应的查询条件
                 */
                if (table.checkRedraw(def.id)) {//需要进行重新计算长度
                    isCxSeach = 1;
                }
                aoData.push({"name": "isCxSeach", "value": isCxSeach});
                var paramlist = [];
                var param = def.param();
                for (var i = 0; i < param.length; i++) {
                    aoData.push({"name": param[i].name, "value": param[i].value});
                    paramlist.push({'name': param[i].name, "value": encodeURI((param[i].value))});
                }
                //进行转码操作
                for (var i = 0; i < aoData.length; i++) {
                    aoData[i].value = encodeURI((aoData[i].value));
                }
                $(def.id).data('paramlist', paramlist);
            },
            /**
             * 表格加载完成事件
             * @param {Object} oSettings
             * @param {Object} oData
             */
            fnStateLoaded: function (oSettings, oData) {
                var nCloneTd = document.createElement('td');
                $(def.id + ' tbody tr').each(function () {
                    this.insertBefore(nCloneTd.cloneNode(true), this.childNodes[0]);
                });
                return false;
            },
            sDom: "<<'dataTables_l'Tli>p>",
            fnDrawCallback: function (o) {
                table.rmLoad(def.loadObj);
            },
            /**
             * 点击导出按钮后弹出单独页面进行选择按钮导出，
             * @params href 原始导出的链接
             */
            expPage: function (href) {
                return "";
            }
        }
        jQuery.extend(def, opt);
        //判断此table表格是在哪一个层级 防止load对话框加载错误地方
        var layui = $(def.id).parents("div[class='layui-layer-content']");
        var wrapper = $(def.id).parents("div[class='wrapper']");
        if (layui.size() > 0) {
            def.loadObj = "#" + layui.attr('id');
        } else if (wrapper.size() > 0) {
            def.loadObj = ".wrapper";
        } else {
            def.loadObj = 'body';
        }
        var iss = false;
        //导出列设置
        var dclist = [];
        var aoColumns = [{"sDefaultContent": '', sTitle: '序号'}];
        if (def.aoColumns.length == 0) {
            for (var i = 0; i < def.columns.length; i++) {
                var dc = {};
                var datac = {};
                if (i == def.columns.length - 1) {
                    //bVisible
                    if (typeof (def.columns[i]) == 'string') {
                        if (def.columns[i].indexOf('<') != -1) {
                            datac.sDefaultContent = '<div style="width:100%;text-align: center;">' + def.columns[i] + '<div>';
                            datac.sTitle = '操作';
                            aoColumns.push(datac);
                            //aoColumns[i+1] = {"sDefaultContent": '<div style="width:100%;text-align: center;">'+def.columns[i]+'<div>',"sTitle":'操作' };	
                            iss = true;
                        } else {
                            datac.mData = def.columns[i];
                            aoColumns.push(datac);
                            //添加导出
                            dc.name = def.columns[i];
                            dc.title = $(def.id).find('th').eq(aoColumns.length).text();
                            if (dc.title == '处理人/角色') {
                            	dc.title = '处理人';
                            }
                            dclist.push(dc);
                        }
                    } else {
                        if (def.columns[i].name.indexOf('<') != -1) {
                            datac.sDefaultContent = '<div style="width:100%;text-align: center;">' + def.columns[i].name + '<div>';
                            datac.sTitle = '操作';
                            aoColumns.push(datac);
                            iss = true;
                        } else {
                            datac.mData = def.columns[i].name;

                            if (def.columns[i].hasOwnProperty("title")) {
                                datac.sTitle = def.columns[i].title;
                            }
                            if (def.columns[i].hasOwnProperty("isdc")) {
                                if (def.columns[i].isdc) {
                                    dc.name = def.columns[i].name;
                                    if (def.columns[i].hasOwnProperty("title")) {
                                        dc.title = def.columns[i].title;
                                    } else {
                                        dc.title = $(def.id).find('th').eq(aoColumns.length + 1).text();
                                    }
                                    dclist.push(dc);
                                }
                            } else {//默认是需要进行导出的
                                dc.name = def.columns[i].name;
                                if (def.columns[i].hasOwnProperty("title")) {
                                    dc.title = def.columns[i].title;
                                } else {
                                    dc.title = $(def.id).find('th').eq(aoColumns.length).text();
                                }
                                dclist.push(dc);
                            }
                            if (def.columns[i].hasOwnProperty("isshow")) {
                                if (def.columns[i].isshow) {
                                    aoColumns.push(datac);
                                }
                            } else {
                                aoColumns.push(datac);
                            }
                        }
                    }
                } else {
                    if (typeof (def.columns[i]) == 'string') {
                        //默认是需要进行导出
                        if (def.columns[i].indexOf('<') != -1) {
                            datac.sDefaultContent = '<div style="width:100%;text-align: center;">' + def.columns[i] + '<div>';
                            datac.sTitle = '操作';
                            aoColumns.push(datac);
                            iss = true;
                        } else {
                            datac.mData = def.columns[i];
                            aoColumns.push(datac);
                            //添加导出
                            dc.name = def.columns[i];
                            dc.title = $(def.id).find('th').eq(aoColumns.length).text();
                            dclist.push(dc);
                        }
                    } else {
                        if (def.columns[i].name.indexOf('<') != -1) {
                            datac.sDefaultContent = '<div style="width:100%;text-align: center;">' + def.columns[i].name + '<div>';
                            datac.sTitle = '操作';
                            aoColumns.push(datac);
                            iss = true;
                        } else {
                            datac.mData = def.columns[i].name;
                            if (def.columns[i].hasOwnProperty("title")) {
                                datac.sTitle = def.columns[i].title;
                            }
                            if (def.columns[i].hasOwnProperty("isdc")) {
                                if (def.columns[i].isdc) {
                                    dc.name = def.columns[i].name;
                                    if (def.columns[i].hasOwnProperty("title")) {
                                        dc.title = def.columns[i].title;
                                    } else {
                                        dc.title = $(def.id).find('th').eq(aoColumns.length + 1).text();
                                    }
                                    dclist.push(dc);
                                }
                            } else {//默认是需要进行导出的
                                dc.name = def.columns[i].name;
                                if (def.columns[i].hasOwnProperty("title")) {
                                    dc.title = def.columns[i].title;
                                } else {
                                    dc.title = $(def.id).find('th').eq(aoColumns.length + 1).text();
                                }
                                dclist.push(dc);
                            }
                            if (def.columns[i].hasOwnProperty("isshow")) {
                                if (def.columns[i].isshow) {
                                    aoColumns.push(datac);
                                }
                            } else {
                                aoColumns.push(datac);
                            }
                        }
                    }
                }
            }
            if (def.bnt != null) {
                var datac = {};
                datac.sDefaultContent = '';
                datac.sTitle = '操作';
                aoColumns.push(datac);
                iss = true;
            }
        } else {
            for (var i = 0; i < def.columns.length; i++) {
                aoColumns[i + 1] = def.aoColumns[i];
            }
        }

        //找出所有需要溢出显示的列
        var overobj = [];
        for (var i = 0; i < def.columns.length; i++) {
            if (typeof (def.columns[i]) != 'string') {
                if (def.columns[i].hasOwnProperty("isover")) {//需要进行溢出显示
                    var dataover = {};
                    if (def.columns[i].hasOwnProperty("title")) {
                        dataover.title = def.columns[i].title;
                    } else {
                        dataover.title = def.columns[i].name;
                    }
                    dataover.name = def.columns[i].name;
                    overobj.push(dataover);
                }
            }
        }

        //本身没有设置任何溢出显示就取出+号显示
        if (overobj.length == 0) {
            def.isOverflowShow = false;
            $(def.id + " th").eq(0).remove();
        } else {
            $(def.id).data('overobj', overobj);
        }
        if (iss) {
            //设置第一列与最后一列不排序
            if (def.aoColumnDefs[0].aTargets.length == 1) {
                if (def.isOverflowShow) {//设置了溢出设置前两列不排序
                    def.aoColumnDefs[0].aTargets = [0, 1, aoColumns.length];
                } else {
                    def.aoColumnDefs[0].aTargets = [0, aoColumns.length - 1];
                }
            }
        } else {//否则只有第一列进行排序
            if (def.aoColumnDefs[0].aTargets.length == 1) {
                if (def.isOverflowShow) {//设置了溢出设置前两列不排序
                    def.aoColumnDefs[0].aTargets = [0, 1];
                } else {
                    def.aoColumnDefs[0].aTargets = [0];
                }
            }
            //去除最后的th
            var objth = $(def.id + " th");
            objth.eq(objth.length - 1).remove();
        }
        //设置了第一列不排序就需要进行设置第一列内容
        if (def.isOverflowShow) {
            aoColumns.unshift({"sWidth": "30", "sClass": "wctable", "sDefaultContent": '<div class="squarediv" style="width:100%;text-align: center;"><i class="fa fa-plus-square"></i><div>', "sTitle": ''});
        }
        //console.log(aoColumns);
        if (def.ismultiple) {//存在多选，需要进行出现多选按钮
            for (var i = 0; i < aoColumns.length; i++) {
                if (aoColumns[i].sTitle == '序号') {
                    aoColumns[i].sTitle = '<input id="_table_allselect_" type="checkbox" style="width: 20px" class="checkbox form-control"  >序号';
                }
            }
        }

        if (def.ismultiple || def.issingle) {
            //给单选按钮添加对应的处理事件
            $(def.id + " input[name='" + def.singleid + "']").die('dblclick');
            $(def.id + " input[name='" + def.singleid + "']").live('dblclick', function () {
                //获取当前选择的数据信息
                var data = table.getRowData(def.id.replace('#', ''), this);
                def.select(data);
            });
            if (def.issingle) {//只有单选按钮的时候才添加双击行选中
                // 双击行，选中
                $(def.id + " tr").die('dblclick');
                $(def.id + " tr").live('dblclick', function () {
                    if (def.issingle) {
                        $(this).find("input[name='" + def.singleid + "']").attr('checked');
                        def.select(table.getTable(def.id.replace('#', '')).fnGetData(this));
                    }
                });
                //单机选中
                $(def.id + " tr").die('click');
                $(def.id + " tr").live('click', function () {
                    //获取当前选择的数据信息
                    if (def.issingle) {
                        //	$(this).find("input[name='"+def.singleid+"']").attr('checked');
                        //	def.select(table.getTable(def.id.replace('#','')).fnGetData(this));
                    } else if (def.ismultiple) {
                        var truth = true;
                        if ($(this).find("input[id='_table_select_']").attr('checked')) {
                            $(this).find("input[id='_table_select_']").attr('checked', false);
                            truth = false;
                        } else {
                            $(this).find("input[id='_table_select_']").attr('checked', true);
                        }
                        selectFun(truth, table.getTable(def.id.replace('#', '')).fnGetData(this));
                    }
                });
            }
            //给多选按钮添加处理事件
            $(def.id).parent().parent().find("#_table_allselect_").die('click');
            $(def.id).parent().parent().find("#_table_allselect_").live('click', function () {
                var o = $(def.id + " input[id='_table_select_']");
                o.attr('checked', this.checked);
                $.each(o, function (i, n) {
                    var num = $(this).attr('num');
                    var data_ = table.getRowData(def.id.replace('#', ''), this);
                    data_.num_ = num;
                    selectFun(this.checked, data_);
                });
                console.log($(def.id).data('multipledata'));
            });
            //给单个添加处理
            $(def.id + " input[id='_table_select_']").die("click");
            $(def.id + " input[id='_table_select_']").live('click', function (n) {
                selectFun(this.checked, table.getRowData(def.id.replace('#', ''), this));
            });
            function selectFun(check, value) {
                var data = $(def.id).data('multipledata');//先获取数据信息
                if (check) {//选中的需要需要添加
                    if (data == undefined || data.length == 0) {
                        data = [];
                        data.push(value);
                        $(def.id).data('multipledata', data);
                    } else {
                        data.push(value);
                        $(def.id).data('multipledata', data);
                    }
                } else {//取消，需要去除
                    for (var i = 0; i < data.length; i++) {
                        if (value.num_ == data[i].num_) {
                            data.splice(i, 1);
                        }
                    }
                    $(def.id).data('multipledata', data);
                    //console.log($(def.id).data('multipledata'));
                }
            }
        }

        //console.log(aoColumns);
        $(def.id).data('aoColumns', aoColumns);
        $(def.id).data('iss', iss);//是否存在对应的操作列
        //添加数据到表格中
        $(def.id).data('colum', dclist);
        var aButtons = [];
        
        // AJAX根据当前角色判断是否屏蔽导出按钮
  			common.ajax({
  				url : common.root + '/datapermission/judgeExportButton.do',
  				dataType : 'json',
  				encode : false,
  				async: false,
  				contentType : 'application/json; charset=utf-8',
  				data : JSON.stringify({}),
  				loadfun : function(isloadsucc, data) {
  					if (isloadsucc) {
  						if (data == 1) {
  							def.isexp = true;
  						} else {
  							def.isexp = false;
  						}
  					} else {
  						def.isexp = false;
  					}
  				}
  			});
        
        if (dclist.length > 0 && def.isexp) {
            aButtons.push({
                "sExtends": "ajax",
                "sButtonText": "导出Excel",
                sAjaxUrl: def.url,
                fnClick: function (nButton, oConfig) {
                    var this__ = this;
                    var sData = this__.fnGetTableData(oConfig);
                    console.log(this__.s.dt.oInit.aoColumns);
                    //进行判断是否需要进行导出
                    //根系数据信息
                    var dataObj = this__.s.dt;
                    //
                    //console.log(dataObj);
                    //console.log($(def.id).data('paramlist'));

                    var colum = $(def.id).data('colum');
                    //console.log(colum);
                    if (colum.length == 0) {
                        common.alert({
                            msg: '没有可导出的列'
                        });
                        return;
                    }
                    var href = "";
                    console.log("aa=>" ,colum);
                    if (oConfig.sAjaxUrl.indexOf('?') > 0) {
                        href = oConfig.sAjaxUrl + "&isDc=true&expname=" + encodeURIComponent(encodeURI(def.expname)) + "&colum=" + encodeURIComponent(encodeURI(JSON.stringify(colum)));
                    } else {
                        href = oConfig.sAjaxUrl + "?isDc=true&expname=" + encodeURIComponent(encodeURI(def.expname)) + "&colum=" + encodeURIComponent(encodeURI(JSON.stringify(colum)));
                        ;
                    }
                    var paramlist = $(def.id).data('paramlist');
                    for (var i = 0; i < paramlist.length; i++) {
                        href += "&" + paramlist[i].name + "=" + encodeURI(paramlist[i].value);
                    }
                    href += '&istz_=1';

                    var expPage = def.expPage(href);

                    if (expPage != '') {
                        $('body').append(expPage);
                        return;
                    }

                    common.load.load('正在执行导出，请稍等');
//						window.location.href = href;
                    common.ajax({
                        url: href,
                        timeout: 0,
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
                }
            });
        }

        var tableobj = $(def.id).dataTable({
            "bProcessing": def.bProcessing,
            "bServerSide": true,
            bScrollCollapse: true,
            "sAjaxSource": def.url,
            bStateSave: def.bStateSave,
            bRetrieve: false,
            "iDisplayLength": def.iDisplayLength,
            sScrollX: def.sScrollX,
            bFilter: def.bFilter,
            sAjaxDataProp: 'data',
            fnServerParams: def.fnServerParams,
            fnServerData: def.fnServerData,
            aaSorting: def.aaSorting,
            "aLengthMenu": def.aLengthMenu,
            aoColumns: aoColumns,
            fnCreatedRow: def.fnCreatedRow,
            aoColumnDefs: def.aoColumnDefs,
            "oLanguage": def.oLanguage,
            fnStateLoaded: function (oSettings, oData) {
                def.fnStateLoaded(oSettings, oData)
            },
            sErrMode: '',
            fnRowCallback: def.fnRowCallback,
            sDom: def.sDom,
            "tableTools": {
                "sSwfPath": "js/advanced-datatable/swf/copy_csv_xls_pdf.swf",
                "aButtons": aButtons
            },
            fnDrawCallback: function (oSettings) {
                $(def.id).removeData('multipledata');
                table.rmLoad(def.loadObj);
                def.fnDrawCallback(oSettings);
                $('#' + oSettings.sTableId).parent()
                $('#' + oSettings.sTableId).wrap('<div class="tableDiv" style="width:100%;    overflow: auto;"  ></div>');
                //var objtablediv = $('#'+oSettings.sTableId).parent('.tableDiv');

//			    	$(def.id).data('nice',nice);
//			    	if(nice != undefined){
//			    		$("#mydiv").getNiceScroll();
//			    	}else{
//			    		nice = $('.tableDiv').niceScroll({enablemousewheel:false,touchbehavior:true,bouncescroll:true,autohidemode:false,styler:"fb",cursorcolor:"#65cea7", cursorwidth: '3', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0'});
//			    	}
//			    	$(def.id).data('nice',nice);
                //$('#'+oSettings.sTableId).parent().niceScroll({enablemousewheel:false,touchbehavior:true,bouncescroll:true,autohidemode:false,styler:"fb",cursorcolor:"#65cea7", cursorwidth: '3', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0'});
                //最后一项下拉出不定位，防止被截取 
                $('.tableDiv table .dropdown-menu:last').css('position', 'relative');
            }
        });
        //table.obj[table.obj.length] = tableobj;
        //table.obj.push(tableobj);
        $(def.id).data('table', tableobj);
        //先移除事件，在进行添加
        $(def.id + ' .wctable .squarediv').die('click');
        if (def.isOverflowShow) {



            var overflowfun = function (aData, head) {
                var str = def.overflowfun(aData, head);
                if (typeof (str) == 'string') {
                    if (str != '') {
                        return str;
                    }
                }

                var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
                for (var i = 0; i < head.length; i++) {
                    var name = head[i].name;
                    sOut += '<tr><td>' + head[i].title + ':</td><td> ' + aData[name] + '</td></tr>';
                }
                sOut += '</table>';
                return sOut;
            }
            $(def.id + ' .wctable .squarediv').live('click', function () {
                //获取单行数据信息
                var num = $(this).parents('tr').attr('num');
                //alert(num);
                var table1 = table.getTable(def.id.replace("#", ''));
                var data = table.getRowData(def.id.replace('#', ''), this);
                //console.log(data);

                var nTr = $(this).parents('tr')[0];
                //console.log("Open:"+tableobj.fnIsOpen(nTr) );
                if (tableobj.fnIsOpen(nTr)) {//已经是打开的就进行关闭
                    //fa-minus-square
                    $('i', this).removeClass('fa-minus-square').addClass('fa-plus-square');
                    tableobj.fnClose(nTr);
                } else {//关闭的就进行打开
//					console.log("Open11:"+tableobj ); 
                    $('i', this).removeClass('fa-plus-square').addClass('fa-minus-square');
                    tableobj.fnOpen(nTr, table.fnFormatDetails(overflowfun, tableobj, nTr), 'details');
                }
            });
        }

        return tableobj;
    },
    fnFormatDetails: function (fun, oTable, nTr) {
        //console.log(oTable.selector);
        var aData = oTable.fnGetData(nTr);
        //进行计算添加
        var head = $(oTable.selector).data('overobj');
        /*var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
         for(var i=0;i<head.length;i++){
         var name = head[i].name;
         sOut += '<tr><td>'+head[i].title+':</td><td> '+aData[name]+'</td></tr>';
         }
         sOut += '</table>';*/
        for (var i in aData) {
            if (aData['' + i + ''] == null) {
                aData['' + i + ''] = '';
            }
        }
        return fun(aData, head);
    },
    /**
     * 添加重新计算数据长度标志
     * @param {Object} tableid 不带有#
     */
    addkRedraw: function (tableid) {
        tableid = "#" + tableid;
        //检查是否存在相同的
        if (table.redrawObj.length == 0) {
            table.redrawObj[table.redrawObj.length] = tableid;
        } else {
            for (var i = 0; i < table.redrawObj.length; i++) {
                if (table.redrawObj[i] == tableid) {//存在需要进行重新绘制，先清除标志，然后返回标志需要重新绘制
                    return;
                }
            }
            table.redrawObj[table.redrawObj.length] = tableid;
        }
    },
    /**
     * 判断是否需要进行重新计算数据长度
     * @param {Object} tableid 带有#
     */
    checkRedraw: function (tableid) {
        if (table.redrawObj.length == 0) {
            return false;
        } else {
            for (var i = 0; i < table.redrawObj.length; i++) {
                if (table.redrawObj[i] == tableid) {//存在需要进行重新绘制，先清除标志，然后返回标志需要重新绘制
                    table.redrawObj.splice(i, 1);
                    return true;
                }
            }
        }
//		console.log(table.redrawObj);
        return false;
    },
    /**
     * 删除行,请先异步调用后台然后在进行删除操作
     */
    deteleRow: function (oTable, index) {
        var nRow = $(oTable.selector).find('tr')[index];
        oTable.fnDeleteRow(nRow);
    },
    /**
     * 刷新表格，传入对于的table的 id值 
     * @param {Object} tableid 前面需要带入#，如：#table
     */
    refresh: function (tableid) {
        tableid = "#" + tableid;
        /*for(var i=0;i<table.obj.length;i++){
         if(table.obj[i].selector == tableid){
         table.obj[i].fnDraw();
         }
         }*/
        $(tableid).data('table').fnDraw();
    },
    /**
     * 同样是刷新，但是不会重新计算数据的数量
     * @param {Object} tableid
     */
    refreshRedraw: function (tableid) {
        var tableid_ = "#" + tableid;
        var tableobj = $(tableid_).data('table');
        table.addkRedraw(tableid);
        tableobj.fnDraw();

        /*for(var i=0;i<table.obj.length;i++){
         if(table.obj[i].selector == tableid_){
         table.addkRedraw(tableid);
         table.obj[i].fnDraw();
         }
         }*/
    },
    /**
     * 根据id获取table对象
     * @param {Object} tableid 前面需要带入#，如：#table
     */
    getTable: function (tableid) {
        tableid = "#" + tableid;
        return $(tableid).data('table');
        /*
         for(var i=0;i<table.obj.length;i++){
         if(table.obj[i].selector == tableid){
         return table.obj[i];
         }
         }*/
    },
    /**
     * 获取当前行的数据信息
     * @param {Object}  tableid 前面需要带入#，如：#table
     * @param {Object} 当前对象
     */
    getRowData: function (tableid, obj) {
        return table.getTable(tableid).fnGetData($(obj).parents('tr')[0]);
    },
    /**
     * 添加加载层
     */
    addLoad: function (id) {
        //添加加载语句
        var html = '<div id="_project_table_processing_" class="dataTables_processing"><i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...</div>';
        $(id).addClass('wrapper_loading').append(html);
    },
    /**
     * 移除对应的加载
     */
    rmLoad: function (id) {
        $('.wrapper_loading').removeClass('wrapper_loading');
        $(id).removeClass('wrapper_loading');
        $('#_project_table_processing_').remove();
        //common.log('移除成功');
    },
    /**
     * 获取多选的数据信息
     * @param tableid
     */
    getSelectData: function (tableid) {
        tableid = "#" + tableid;
        var data = $(tableid).data('multipledata');
        if (data == undefined) {
            data = [];
        }
        return data;
    },
}
