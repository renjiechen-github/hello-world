var project = {
    //页面初始化操作
    init: function () {
        project.initType();
        //创建表格
        project.createTable();
        //新增操作
        $('.bnt_add').click(function () {
            project.add();
        });
        //时间按钮选择事件
        var nowTemp = new Date();
        $('#actiontime').daterangepicker({
            startDate: nowTemp.getFullYear() + '-' + (nowTemp.getMonth() + 1) + '-02',
            timePicker12Hour: false,
            timePicker: true,
            separator: '至',
            //分钟间隔时间
            timePickerIncrement: 10,
            format: 'YYYY-MM-DD HH:mm'
        }, function (start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
    },
    /**
     加载结算类型数据
     **/
    initType: function () {
        common.loadItem('SETTLEMENTS.TYPE', function (json) {
            var html = "";
            for (var i = 0; i < json.length; i++) {
                html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#jstype').empty();
            $('#jstype').append('<option value="">请选择...</option>');
            $('#jstype').append(html);
        });
        common.ajax({
            url: common.root + '/financial/type/getTypeList.do',
            data: {
                iDisplayStart: 0,
                iDisplayLength: 25
            },
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                common.log(data);
                if (isloadsucc) {
                    project.categoryDate = data.data;
                }
            }
        });
    },
    categoryDate: null,
    /**
     * 表格创建
     */
    createTable: function () {
        table.init({
            id: '#project_table',
            url: common.root + '/financial/project/getProjectList.do',
            columns: ["code", "name",
                "type_name",
                "jh_rec_je",
                "jh_pay_je",
                "capital_receivable",
                "capital_payable",
                "action_time",
                "status_name"/*,
                 "<button title='查看详细' onclick='project.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button><button onclick='project.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>"*/],
            bnt: [
                {
                    name: '查看详细',
                    //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
                    isshow: function (data, row) {
                        return true;
                    },
                    fun: function (data, row) {
                        rowdata = data;
                        project.edit();
                    }
                },
                {
                    name: '新增收入支出明细',
                    isshow: function (data, row) {
                        if(data.status_name == '无效'){
                            return false;
                        }
                        return true;
                    },
                    fun: function (data, row) {
                        data.categoryDate = project.categoryDate;
                        common.openWindow({
                            type: 3,
                            name: 'projectSzWin',
                            data: data,
                            title: '新增收入支出明细',
                            url: '/html/pages/finance/expend_sz_add.html'
                        });
                    }
                },
                {
                    name: '查看支出明细',
                    isshow: function (data, row) {
                        return true;
                    },
                    fun: function (data, row) {
                        index.openMenuNew(null, 'html/pages/finance/expend.html', null, function () {
                            $('#name').val(data.name);
                            $('.yc_seach').click();
                        });
                    }
                },
                {
                    name: '查看收入明细',
                    isshow: function (data, row) {
                        return true;
                    },
                    fun: function (data, row) {
                        index.openMenuNew(null, 'html/pages/finance/income.html', null, function () {
                            $('#name').val(data.name);
                            $('.yc_seach').click();
                        });
                    }
                }
            ],
            param: function () {
                var a = new Array();
                a.push({"name": "jstype", "value": $('#jstype').val()});
                a.push({name: 'xmname', value: $('#xmname').val()});
                //a.push({name:'xmcode',value:$('#xmcode').val()});
                a.push({name: 'actiontime', value: $('#actiontime').val()});
                a.push({name: 'state', value: $('#state').val()});
                return a;
            },
            overflowfun: function () {
                return " ";
            },
            createRow: function (rowindex, colindex, name, value, data, row) {
                if (data.status_name == '无效') {
                    //给符合条件的添加背景颜色
                    $(row).addClass('bzh');
                }
                if (name == 'jh_rec_je') {
                    return data.jh_rec_je + data.jh_rec_je_
                }
                if (name == 'jh_pay_je') {
                    return data.jh_pay_je + data.jh_pay_je_
                }
            }
        });
        //添加点击事件处理
        $("#project_table tbody tr").die('dblclick');
        $("#project_table tbody tr").live('dblclick', function () {
            var $this = this;
            var num = $(this).attr('num');
            var tableObj = $("#project_table").data('table');
            var data = table.getRowData("project_table", this)[num];
            var nTr = $(this);
            if (data.type_name == '出租') {
                return;
            }
            if (nTr.data('open') == 'true') {//已经是打开的，现在就需要进行去除掉
                $('.item_pro_fin_' + data.correlation_id).remove();
                nTr.data('open', 'false');
                return;
            }
            common.load.load('正在打开');
            var correlation_id = data.correlation_id;
            $(".item_pro_fin").remove();
            //调用ajax获取对应下级信息
            common.ajax({
                url: common.root + '/financial/project/getItemProjectList.do',
                data: {
                    jstype: $('#jstype').val(),
                    xmname: $('#xmname').val(),
                    actiontime: $('#actiontime').val(),
                    state: $('#state').val(),
                    supper_id: correlation_id
                },
                dataType: 'json',
                loadfun: function (isloadsucc, data) {
                    common.load.hide();
                    if (isloadsucc) {
                        if (data.state == 1) {
                            var html = "";
                            project.detaildata = data.list;
                            if (data.list.length == 0) {
                                common.alert('该收房合同暂无出租项目');
                                return;
                            }
                            for (var i = 0; i < data.list.length; i++) {
                                var bnthtml = '<div class="btn-group notDefMenu "><button type="button" class="btn btn-default dropdown-toggle">操作<span class="caret"></span></button><ul role="menu" class="dropdown-menu"><li tag="0"><a href="javascript:project.showdeatil(' + i + ');">查看详细</a></li><li tag="1"><a href="javascript:project.addSzDetail(' + i + ');">新增收入支出明细</a></li><li tag="2"><a href="javascript:project.showZcDetail(' + i + ');">查看支出明细</a></li><li tag="3"><a href="javascript:project.showSrDetail(' + i + ');">查看收入明细</a></li></ul></div>';
                                html += "<tr class='item_pro_fin item_pro_fin_" + correlation_id + "' ><td></td><td>" + data.list[i].code + "</td><td>" + data.list[i].name + "</td><td>" + data.list[i].type_name + "</td>" +
                                        "<td>" + data.list[i].jh_rec_je + "</td><td>" + data.list[i].jh_pay_je + "</td><td>" + data.list[i].capital_receivable + "</td><td>" + data.list[i].capital_payable + "</td>" +
                                        "<td>" + data.list[i].action_time + "</td><td>" + data.list[i].status_name + "</td><td>" + bnthtml + "</td>" +
                                        "</tr>";
                            }
                            nTr.after(html);
                            $($this).data('open', 'true');
                        } else {
                            common.alert(common.msg.error);
                        }
                    } else {
                        common.alert(common.msg.error);
                    }
                }
            });


        });
    },
    detaildata: null,
    //查看明细
    showdeatil: function (data, row) {
        if ($.isNumeric(data)) {//为数字是子项目传递过来的参数
            rowdata = project.detaildata[data];
        } else {
            rowdata = data;
        }
        project.edit();
    },
    //新增收支明细
    addSzDetail: function (data) {
        if ($.isNumeric(data)) {//为数字是子项目传递过来的参数
            data = project.detaildata[data];
        }
        data.categoryDate = project.categoryDate;
        common.openWindow({
            type: 3,
            name: 'projectSzWin',
            data: data,
            title: '新增收入支出明细',
            url: '/html/pages/finance/expend_sz_add.html'
        });
    },
    //查看支出明细
    showZcDetail: function (data) {
        if ($.isNumeric(data)) {//为数字是子项目传递过来的参数
            data = project.detaildata[data];
        }
        index.openMenuNew(null, 'html/pages/finance/expend.html', null, function () {
            $('#name').val(data.name);
            $('.yc_seach').click();
        });
    },
    //查看收入明细
    showSrDetail: function (data) {
        if ($.isNumeric(data)) {//为数字是子项目传递过来的参数
            data = project.detaildata[data];
        }
        index.openMenuNew(null, 'html/pages/finance/income.html', null, function () {
            $('#name').val(data.name);
            $('.yc_seach').click();
        });
    },
    /**
     * 编辑
     * @param {Object} obj
     */
    edit: function (obj) {
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name: 'projectWin',
            title: '财务项目修改',
            url: '/html/pages/finance/project_add.html'
        });
    },
    //删除项目信息
    del: function (obj) {
        common.alert({
            msg: '确定需要删除吗？',
            confirm: true,
            fun: function (action) {
                if (action) {
                    common.ajax({
                        url: common.root + '/financial/project/deleteProject.do',
                        data: {
                            id: table.getRowData('project_table', obj).correlation_id
                        },
                        dataType: 'json',
                        loadfun: function (isloadsucc, data) {
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '删除成功',
                                        fun: function () {
                                            table.refreshRedraw('project_table');
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
    /**
     * 修改
     */
    add: function () {
        rowdata = null;
        common.openWindow({
            type: 1,
            title: '财务项目新增',
            url: '/html/pages/finance/project_add.html'
        });
    }
};
project.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;