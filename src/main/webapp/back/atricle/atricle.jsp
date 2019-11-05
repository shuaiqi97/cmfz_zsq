<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>


<script>
    $(function(){

        <!--创建表格-->
        $("#atricleTable").jqGrid({ //书写的全部是初始化参数
            url:"${pageContext.request.contextPath}/atricle/findAllAtricle",//用来获取数据远程地址 项目名/user/findAll
            datatype:"json",//用来指定本次请求的数据为json格式
            colNames:["编号","标题","简介","作者","内容","创建时间","操作"],//表格标题
            // cellEdit:true,//开启单元格编辑功能
            colModel:[  //用来指定标题对应获取json中哪个key的数组作为本列的值
                {name:"id",align:"center",hidden:true,editable: false},
                {name:"title",align:"center",editable: true},//colmodel参数全部写在  列属性都写在对应的列里面
                {name:"description",align:"center"},
                {name:"star",align:"center",editable: true},
                {name:"content",align:"center",hidden:true},
                {name:"createDate",align:"center",edittype: "date"},
                {name:"operation",align:"center",formatter: function (value, grid, rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('edit','"+rows.id+"')\">修改</a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger' onclick=\"dele("+rows.id+")\">删除</a>";
                    }}
            ],
            height:310,
            autowidth:true,
            styleUI:"Bootstrap",
            pager:"#pager",//用来指定分页工具栏  后台接收参数变量名 page 当前页 和 rows 每页显示条数
            rowNum:4,//每页显示记录数 推荐最好是rowList中一个子元素
            rowList:[4,8,12],//下拉列表 指定每页显示记录数
            sortname : 'id',
            viewrecords:true,//显示总记录数
            caption : "所有的文章展示",
            editurl:"${pageContext.request.contextPath}/album/editAlbum" //执行增删改时的url
        }).navGrid("#pager", {edit : false,add : false,del : false,search:false},{

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
                            $("#atricleTable").trigger("reloadGrid")
                        }
                    })
                }else {
                    window.alert(id);
                }
                return "xxx";
            }
        });


    });
    function openModal(oper,id) {
        if("add"==oper){
            $("#article-id").val("");
            $("#article-title").val("");
            $("#article-author").val("");
            $("#article-description").val("");
            KindEditor.html("#editor_id","");
        }
        if("edit"==oper){
            var article = $("#atricleTable").jqGrid("getRowData",id);
            console.log(article);
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.star);
            $("#article-description").val(article.description);
            KindEditor.html("#editor_id",article.content);
        }
        $("#article-modal").modal("show");
    }
    function save(){
        var id = $("#article-id").val();
        var url = "";
        if(id){
            url= "${pageContext.request.contextPath}/atricle/editAtricle";
        }else{
            url = "${pageContext.request.contextPath}/atricle/addAtricle";
        }
        $.ajax({
            url:url,
            type:"post",
            data:$("#article-form").serialize(),
            datatype:"json",
            success:function () {
                $("#atricleTable").trigger("reloadGrid");
            }
        });
        $("#article-modal").modal("hide");
    }
    KindEditor.create('#editor_id',{
        width : '460px',
        //点击图片空间按钮时发送的请求
        fileManagerJson:"${pageContext.request.contextPath}/atricle/browse",
        //展示图片空间按钮
        allowFileManager:true,
        //上传图片所对应的方法
        uploadJson:"${pageContext.request.contextPath}/atricle/uploadAtricle",
        //上传图片是图片的形参名称
        filePostName:"articleImg",
        afterBlur:function () {
            this.sync();
        }
    });
    function dele(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/atricle/deleteAtrcle",
            data:{id:id},
            type:"post",
            datatype:"json",
            success:function () {
                $("#atricleTable").trigger("reloadGrid");
            }
        })
    }
</script>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有文章</a></li>
    <li role="presentation"><a href="#" onclick="openModal('add','')">添加文章</a></li>
</ul>
<div id="article-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章操作</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">文章标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="请输入文章标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">文章作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="star" class="form-control" id="article-author" placeholder="请输入文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-description" class="col-sm-2 control-label">文章简介</label>
                        <div class="col-sm-10">
                            <input type="text" name="description" class="form-control" id="article-description" placeholder="请输入文章简介">
                        </div>
                    </div>
                    <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="save()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--创建表格-->
<table id="atricleTable"></table>

<!--分页-->
<div id="pager" style="height: 30px"></div>