function formatterState(val) {
    if (val == 0) {
        return "未分配";
    } else if (val == 1) {
        return "已分配";
    } else {
        return "未定义";
    }
}

function formatterDevResult(value) {
    /**
     * 0-损坏
     * 1-维修中
     * 2-正常成功
     * 3-报废
     */
    if (value == 0) {
        return "未开发";
    } else if (value == 1) {
        return "开发中";
    } else if (value == 2) {
        return "开发成功";
    } else if (value == 3) {
        return "开发失败";
    } else {
        return "未知"
    }

}

//查询方法
function searchSaleChance() {
    $("#dg").datagrid("load", {
        createMan: $("#s_createMan").val(),
        customerName: $("#s_customerName").val(),
        state: $("#s_state").combobox("getValue")
    })
}

function openSaleChanceAddDialog() {
    $("#dlg").dialog("setTitle", "添加营销机会记录");
    $("#fm").form("clear");
    $("#dlg").dialog("open");

}

function closeSaleChanceDialog() {
    $("#dlg").dialog("close");
}

/**
 * 清空表单信息
 */
function clearFormData() {
    $("#customerName").val("");
    $("#chanceSource").val("");
    $("#linkMan").val("");
    $("#linkPhone").val("");
    $("#cgjl").val("");
    $("#overview").val("");
    $("#assignMan").combobox("setValue", "");
    $("input[name='id']").val("");
}

/**
 * 保存
 * */
function saveOrUpdateSaleChance() {
    //根据id的值是否未空来判断是添加还是修改     添加的话id=null 修改id！=null
    var url = ctx + "/sale_chance/update";
    if (isEmpty($("input[name='id']").val())) {//添加
        url = ctx + "/sale_chance/save";
    }
    $("#fm").form("submit", {
        url: url,
        onSubmit: function (params) {
            params.createMan = $.cookie("trueName");
            return $("#fm").form("validate");
        },
        success: function (data) {
            data = JSON.parse(data);
            if (data.code == 200) {
                $.messager.alert("来自Crm项目", data.msg, "info");
                closeSaleChanceDialog();
                searchSaleChance();
                clearFormData();
            } else {
                $.messager.alert("来自Crm项目", "添加机会数据失败!", "error");
            }
        }
    })
}

function openSaleChanceModifyDialog() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待修改的机会数据!", "error");
        return;
    }
    if (rows.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量修改!", "error");
        return;
    }
    $("#fm").form("load", rows[0]);
    $("#dlg").dialog("open").dialog("setTitle", "机会数据更新")
}

function deleteSaleChance() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待修改的机会数据!", "error");
        return;
    }
    $.messager.confirm("来自crm", "确定删除待选中的记录?", function (r) {
        if (r) {
            var ids = "ids=";
            for (var i = 0; i < rows.length; i++) {
                if (i < rows.length - 1) {
                    ids = ids + rows[i].id + "&ids=";
                } else {
                    ids = ids + rows[i].id;
                }
            }

            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if (data.code==200){
                        searchSaleChance();
                    } else{
                        $.messager.alert("来自crm",data.msg,"error")
                    }
                }
            })
        }
    })
}