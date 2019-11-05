<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>


<script>
    $(function(){

        <!--创建表格-->
        $("#userTable").jqGrid({ //书写的全部是初始化参数
            url:"${pageContext.request.contextPath}/user/selectAll",//用来获取数据远程地址 项目名/user/findAll
            datatype:"json",//用来指定本次请求的数据为json格式
            colNames:["编号","用户名称","真实姓名","电话","地址","签名","照片","性别","创建日期"],//表格标题
            // cellEdit:true,//开启单元格编辑功能
            colModel:[  //用来指定标题对应获取json中哪个key的数组作为本列的值
                {name:"id",align:"center",hidden:true},
                {name:"username",align:"center"},
                {name:"nickname",align:"center"},
                {name:"phone",align:"center"},
                {name:"province",align:"center",formatter:function (value,option,rows) {
                        return rows.province+"-"+rows.city;
                    }},
                {name:"sign",align:"center"},
                {name:"photo",align:"center"},
                {name:"sex",align:"center"},
                {name:"creatDate",align:"center"}
            ],
            height:310,
            autowidth:true,
            styleUI:"Bootstrap",
            pager:"#pager",//用来指定分页工具栏  后台接收参数变量名 page 当前页 和 rows 每页显示条数
            rowNum:4,//每页显示记录数 推荐最好是rowList中一个子元素
            rowList:[4,8,12],//下拉列表 指定每页显示记录数
            sortname : 'id',
            viewrecords:true,//显示总记录数
            caption : "所有用户",
            //editurl:"${pageContext.request.contextPath}/album/editAlbum" //执行增删改时的url
        }).navGrid("#pager", {edit : false,add : false,del : false,search:false},{},{});


    });
    /*function poi() {
        $.ajax({
            url:"${pageContext.request.contextPath}/user/userPoi",
            datatype: "json",
            type:"post"
        })
    }*/


</script>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${pageContext.request.contextPath}/user/userPoi">用户导出</a></li>
</ul>

<!--创建表格-->
<table id="userTable"></table>

<!--分页-->
<div id="pager" style="height: 30px"></div>