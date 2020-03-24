function formatterOp(value,rowData) {
    var devResult = rowData.devResult;
    var href="javascript:openCusDevPlanDialog()";
    if (devResult == 2 || devResult == 3){
        return "<a href='"+href+"'>查看详情</a>"
    }else{
        return "<a href='"+href+"'>开发</a>"
    }

}

function openCusDevPlanDialog() {
    var recode=$("#dg").datagrid("getSelections")[0];
    $("#fm").form("load",recode);

    if (recode.devResult ==2 || recode.devResult==3){
        $("#toolbar").css("display","none");
        $("#dg02").edatagrid({
            url:ctx+"/cus_dev_plan/list?sid"+recode.id,
            saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+recode.id,
            updateUrl:ctx+"/cus_dev_plan/update",
            destroyUrl:ctx+"/cus_dev_plan/delete"
        });
        $("#dg02").edatagrid("disableEditing");
    }else{
        $("toolbar").show();
        $("#dg02").edatagrid({
            toolbar:"#toolbar",
            url:ctx+"/cus_dev_plan/list?sid"+recode.id,
            saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+recode.id,
            updateUrl:ctx+".cus_dev_plan/update",
            destroyUrl:ctx+"/cus_dev_plan/delete"
        });
    }

    $("#dg02").edatagrid({
        url:ctx+"/cus_dev_plan/list?sid="+recode.id
    });
    $("#dlg").dialog("open").dialog("setTitle","计划项展示");

}

function saveCusDevPlan() {
    $("#dg02").edatagrid("saveRow");
    $("#dg02").edatagrid("load");
}