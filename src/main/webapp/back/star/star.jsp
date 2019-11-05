<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {


        $("#starTable").jqGrid({
            url : "${pageContext.request.contextPath}/star/findAll",
            datatype : "json",
            height : 300,
            colNames : [ '编号', '艺名', '真名', '照片', '性别', '生日'],
            colModel : [
                {name : 'id',align:"center",hidden:true,editable:false},
                {name : 'nickname',align:"center",editable: true},
                {name : 'realname',align:"center",editable: true},
                {name : 'photo',align:"center",editable: true,edittype:"file",formatter: function (value,option,rows) {
                        return "<img style='width:100px;height:60px;'src='${pageContext.request.contextPath}/back/star/img/"+rows.photo+"'>"
                    }},
                {name : 'sex',align:"center",editable: true,edittype: "select",editoptions:{value:"男:男;女:女"}},
                {name : 'bir',align:"center",editable: true,edittype:"date"}
            ],
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 6, 10, 20 ],
            pager : '#pager',
            sortname : 'id',
            viewrecords : true,
            subGrid : true,
            caption : "所有明星列表",
            editurl:"${pageContext.request.contextPath}/star/edit", //执行增删改时的url
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/user/findAllUser?starId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '用户名', '密码','昵称', '电话','省份','地址','签名','头像','性别','创建时间' ],
                        colModel : [
                            {name : "id",align:"center"},
                            {name : "username",align:"center"},
                            {name : "password",align:"center"},
                            {name : "nickname",align:"center"},
                            {name : "phone",align:"center"},
                            {name : "province",align:"center"},
                            {name : "city",align:"center"},
                            {name : "sign",align:"center"},
                            {name : "photo",align:"center",edittype:"file",formatter: function (value,option,rows) {
                                    return "<img style='width:100px;height:60px;'src='${pageContext.request.contextPath}/back/user/img/"+rows.photo+"'>"
                                }},
                            {name : "sex",align:"center"},
                            {name : "creatDate",align:"center"}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            }

        }).jqGrid('navGrid', '#pager', {
            add: true,
            edit: true,
            del: false,
            search:false
        },{
            //修改
            closeAfterEdit: true,
            afterSubmit:function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status){
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/star/updateStarPhoto",
                        type:"post",
                        fileElementId: "photo",
                        data:{id:id},
                        success:function (response) {
                            $("#starTable").trigger("reloadGrid");
                        }
                    })
                }else {
                    window.alert(id);
                }
                return "xxxx";
        }
        },{
            //添加
            closeAfterAdd: true,
            afterSubmit:function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status){
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/star/updateStarPhoto",
                        type:"post",
                        fileElementId: "photo",
                        data:{id:id},
                        success:function (response) {
                            $("#starTable").trigger("reloadGrid");
                        }
                    })
                }else {
                    window.alert(id);
                }
                return "xxxx";
            }
        });
    });

</script>

<div class="panel panel-heading">
    <h3>所有明星</h3>
</div>
<table id="starTable"></table>
<div id="pager" style="height: 30px"></div>