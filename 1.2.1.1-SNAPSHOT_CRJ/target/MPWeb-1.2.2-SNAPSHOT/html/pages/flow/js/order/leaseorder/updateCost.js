var createCost = {
    //页面初始化操作
    init: function () {
        // 加载结算类型
        createCost.initType();
        // 添加处理事件
        createCost.addEvent();
        // 时间按钮选择事件
        createCost.initTime();
        $("#endtime,#starttime").blur(function ()
        {
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            if (parseFloat(endtime) >= parseFloat(starttime))
            {
                $("#degree").val(endtime - starttime);
            } else {
                alert("起始度数不得大于结束度数");
            }
        });
    },
    initTime: function () {
    },
    addEvent: function () {
        $('#orderadd_bnt').click(function ()
        {
            orderadd.save();
        });
    },
    /**
     加载区域选择
     **/
    initType: function () {

        common.loadItem("CERTIFICATELEAVE.TYPE", function (json)
        {
            var html = "<option value=''>请选择...</option>";
            for (var i = 0; i < json.length; i++)
            {
                if (json[i].item_name == '其他') {
                    html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
                }
            }
            $('#costtype').html(html);
            $('#costtype').val(rowdata.type);
        });
        $('#degree').val(rowdata.degree);
        $('#starttime').val(rowdata.starttime);
        $('#endtime').val(rowdata.endtime);
        $('#name').val(rowdata.name);
        $("#cost").val(rowdata.cost);
        $('#costtype').val(rowdata.type);
        $('#desc').val(rowdata.costdesc);
    },
    save: function () {
        if (!Validator.Validate('form2'))
        {
            return;
        }
        var cost = $('#cost').val();
        if (cost == 0) {
            alert("添加明细费用不能为0");
            retrun;
        }
        common.ajax({
            url: common.root + '/CertificateLeave/update.do',
            data: {
                id: rowdata.id,
                degree: $('#degree').val(),
                starttime: $('#starttime').val(),
                endtime: $('#endtime').val(),
                name: $('#name').val(),
                type: $('#costtype').val(),
                cost: $('#cost').val(),
                desc: $('#desc').val(),
            },
            dataType: 'json',
            loadfun: function (isloadsucc, data)
            {
                if (isloadsucc)
                {
                    if (data.state == -2)
                    {//操作成功
                        common.alert({msg: '修改失败'});
                    } else
                    {
                        common.alert({msg: '修改成功'});
                        common.closeWindow('updateCost', 1);
                        table.refreshRedraw('wait2PayTable');
                        total = 0;
                    }
                } else
                {
                    common.alert({msg: common.msg.error});
                }
            }
        });
    }
};
$(function () {
    createCost.init();
});
