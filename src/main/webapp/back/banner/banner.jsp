<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script>
        $(function(){


            <!--创建表格-->
            $("#userTable").jqGrid({ //书写的全部是初始化参数
                url:"${pageContext.request.contextPath}/banner/findAll",//用来获取数据远程地址 项目名/user/findAll
                datatype:"json",//用来指定本次请求的数据为json格式
                colNames:["编号","名称","图片","描述","状态","创建时间"],//表格标题
               // cellEdit:true,//开启单元格编辑功能
                colModel:[  //用来指定标题对应获取json中哪个key的数组作为本列的值
                    {name:"id",align:"center",hidden:true,editable:false},
                    {name:"name",align:"center",editable: true},//colmodel参数全部写在  列属性都写在对应的列里面
                    {name:"cover",editable:true,edittype:"file",formatter:function (value,option,rows) {
                            return"<img style='width:100px;height:60px;' src='${pageContext.request.contextPath}/back/banner/img/"+rows.cover+"'>";
                        }},
                    {name:"description",align:"center",editable:true},
                    {name:"status",align:"center",editable:true,edittype: "select",editoptions:{value:"正常:正常;冻结:冻结"}},
                    {name:"createDate",align:"center"}
                ],
                height:310,
                autowidth:true,
                styleUI:"Bootstrap",
                pager:"#pager",//用来指定分页工具栏  后台接收参数变量名 page 当前页 和 rows 每页显示条数
                rowNum:4,//每页显示记录数 推荐最好是rowList中一个子元素
                rowList:[4,8,12],//下拉列表 指定每页显示记录数
                sortname : 'id',
                viewrecords:true,//显示总记录数
                caption : "轮播图列表",
                editurl:"${pageContext.request.contextPath}/banner/edit"//执行增删改时的url
            }).navGrid("#pager", {edit : true,add : true,del : true,search:false},{
                    closeAfterEdit:true,
                    beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);
                }
                },{
                    closeAfterAdd: true,
                    afterSubmit:function (data) {
                        var status = data.responseJSON.status;
                        var id = data.responseJSON.message;
                        if (status){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/banner/upload",
                                type:"post",
                                fileElementId:"cover",
                                data:{id:id},
                                success:function (response) {
                                    $("#userTable").trigger("reloadGrid")
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
    <h3>所有的轮播图</h3>
</div>
<!--创建表格-->
<table id="userTable"></table>

<!--分页-->
<div id="pager" style="height: 30px"></div>
