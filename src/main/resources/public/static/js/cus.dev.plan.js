function formatterOp(value, rowData) {
    var devResult = rowData.devResult;
    var href = "javascript:openCusDevPlanDialog()";
    if (devResult == 2 || devResult == 3) {
        return "<a href='" + href + "'>查看详情</a>";
    } else {
        return "<a href='" + href + "'>开发</a>";
    }

}

function openCusDevPlanDialog() {
    var recode = $("#dg").datagrid("getSelections")[0];
    $("#fm").form("load", recode);

    if (recode.devResult == 2 || recode.devResult == 3) {
        $("#toolbar").css("display", "none");
        $("#dg02").edatagrid({
            url: ctx + "/cus_dev_plan/list?sid" + recode.id,
            saveUrl: ctx + "/cus_dev_plan/save?saleChanceId=" + recode.id,
            updateUrl: ctx + "/cus_dev_plan/update",
            destroyUrl: ctx + "/cus_dev_plan/delete"
        });
        $("#dg02").edatagrid("disableEditing");
    } else {
        $("toolbar").show();
        $("#dg02").edatagrid({
            toolbar: "#toolbar",
            url: ctx + "/cus_dev_plan/list?sid" + recode.id,
            saveUrl: ctx + "/cus_dev_plan/save?saleChanceId=" + recode.id,
            updateUrl: ctx + "/cus_dev_plan/update",
            destroyUrl: ctx + "/cus_dev_plan/delete"
        });
    }

    $("#dg02").edatagrid({
        url: ctx + "/cus_dev_plan/list?sid=" + recode.id
    });
    $("#dlg").dialog("open").dialog("setTitle", "计划项展示");

}

function saveCusDevPlan() {
    $("#dg02").edatagrid("saveRow");
    $("#dg02").edatagrid("load");
}

function delCusDevPlan() {
    var rows = $("#dg02").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待删除的数据!", "error");
        return;
    }
    if (rows.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量删除", "error");
        return;
    }

    $.messager.confirm("来自crm", "确定删除选中记录吗?", function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: ctx + "/cus_dev_plan/delete",
                data: {id: rows[0].id},
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        $("#dg02").edatagrid("load");
                    } else {
                        $.messager.alert("来自crm", data.msg, "error");
                    }
                }
            })
        }
    })
}
function closeCusDevPlanDialog() {
    $("#dlg").dialog("close");
}
function updateSaleChanceDevResult(status) {
    $.ajax({
        type:"post",
        url:ctx+"/sale_chance/updateDevResult",
        data:{
            devResult:status,
            sid:$("#dg").datagrid("getSelections")[0].id,
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $("#dg").edatagrid("load");
                /**
                 * 1.更新营销机会表格数据
                 * 2.隐藏开发计划项数据 工具栏
                 * 3.计划项表格数据不可编辑
                 */
                $("#dg02").edatagrid("disableEditing");
                closeCusDevPlanDialog();
                searchSaleChance();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}