<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script>
    $(function(){
        <!--创建表格-->
        $("#albumTable").jqGrid({ //书写的全部是初始化参数
            url:"${pageContext.request.contextPath}/album/findAllAlbum",//用来获取数据远程地址 项目名/user/findAll
            datatype:"json",//用来指定本次请求的数据为json格式
            colNames:["编号","标题","封面","章节数","得分","作者","简介","创建时间"],//表格标题
            // cellEdit:true,//开启单元格编辑功能
            colModel:[  //用来指定标题对应获取json中哪个key的数组作为本列的值
                {name:"id",align:"center",hidden:true,editable: false},
                {name:"title",align:"center",editable: true},//colmodel参数全部写在  列属性都写在对应的列里面
                {name:"cover",editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return"<img style='width:100px;height:60px;' src='${pageContext.request.contextPath}/back/album/img/"+rows.cover+"'>";
                    }},
                {name:"count",align:"center",editable:false},
                {name:"score",align:"center",editable:false},
                {name:"starId",align:"center",editable:true,edittype: "select",editoptions:{
                    dataUrl:"${pageContext.request.contextPath}/album/findAllStar"
                    },formatter:function (value, option, rows) {
                        return rows.star.realname
                    }},
                {name:"brief",align:"center",editable:true},
                {name:"createDate",align:"center",editable:true,edittype: "date"}
            ],
            height:310,
            autowidth:true,
            styleUI:"Bootstrap",
            pager:"#pager",//用来指定分页工具栏  后台接收参数变量名 page 当前页 和 rows 每页显示条数
            rowNum:4,//每页显示记录数 推荐最好是rowList中一个子元素
            rowList:[4,8,12],//下拉列表 指定每页显示记录数
            sortname : 'id',
            viewrecords:true,//显示总记录数
            caption : "专辑列表",
            subGrid : true,
            editurl:"${pageContext.request.contextPath}/album/editAlbum", //执行增删改时的url
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/chapter/findAllChapter?albumId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '歌手', '大小','时长', '歌曲名称','创建时间','在线播放'],
                        colModel : [
                            {name : "id",align:"center",hidden:true},
                            {name : "singer",align:"center",editable : true},
                            {name : "size",align:"center"},
                            {name : "duration",align:"center"},
                            {name : "name",align:"center",editable : true,edittype : "file"},
                            {name : "createDate",align:"center"},
                            {name : "operation",width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/back/album/music/"+rows.name+"' >\n" +
                                        "</audio>";
                                }}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        sortname : 'id',
                        pager : pager_id,
                        autowidth:true,
                        height : '100%',
                        editurl: "${pageContext.request.contextPath}/chapter/edit?albumId=" + id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{},{
                        closeAfterAdd: true,
                        afterSubmit:function (data) {
                            var status = data.responseJSON.status;
                            var message = data.responseJSON.message;
                            if (status){
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/updateChapter",
                                    type:"post",
                                    fileElementId:"name",
                                    data:{id:message,albumId:id},
                                    success:function (response) {
                                        $("#albumTable").trigger("reloadGrid")
                                    }
                                })
                            }else {
                                window.alert(message);
                            }
                            return "xxx";
                        }
                    });
            }
        }).navGrid("#pager", {edit : false,add : true,del : false,search:false},{

        },{
            closeAfterAdd: true,
            afterSubmit:function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/updateAlbum",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function (response) {
                            $("#albumTable").trigger("reloadGrid")
                        }
                    })
                }else {
                    window.alert(id);
                }
                return "xxx";
            }
        });


    });
</script>

<div class="panel panel-heading">
    <h3>所有的专辑</h3>
</div>
<!--创建表格-->
<table id="albumTable"></table>

<!--分页-->
<div id="pager" style="height: 30px"></div>