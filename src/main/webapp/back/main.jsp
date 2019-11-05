<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--当前页面更好支持移动端-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%--引入bootstrap的样式--%>
    <link rel="stylesheet" href="../statics/boot/css/bootstrap.min.css">
    <script src="../statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="../statics/boot/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="../statics/jqgrid/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="../kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
    <script src="../statics/echarts.min.js"></script>
    <script>
        $(function () {

            /*$("#btn").click(function(){
               //修改中心布局的内容
                $("#centerLayout").load("./user/lists_s.jsp");//引入一张页面到当前页面中
            });*/

        })
    </script>
</head>
<body>
<!--导航条-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!--导航标题-->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法州后台管理系统 </a>
        </div>

        <!--导航条内容-->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<font color="aqua">${sessionScope.selectOne.nickname}</font></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/clearSession">退出登录 <span class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div>
    </div>
</nav>

    <!--row-->
    <div class="row">

        <!--菜单-->
        <div class="col-sm-2">

            <!--手风琴控件-->
            <div class="panel-group" id="accordion" >

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="userPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#userLists" aria-expanded="true" aria-controls="collapseOne">
                                轮播图管理管理
                            </a>
                        </h4>
                    </div>
                    <div id="userLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/banner/banner.jsp');" id="btn">所有轮播图</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--类别面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="categoryPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#categoryLists" aria-expanded="true" aria-controls="collapseOne">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="categoryLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/album/album.jsp')">专辑列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="bookPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bookLists" aria-expanded="true" aria-controls="collapseOne">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="bookLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/atricle/atricle.jsp')">文章列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>


                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="orderPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#orderLists" aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="orderLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/user/user.jsp')">用户列表</a></li>
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/user/userAdd.jsp')">用户添加趋势图</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="starPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#starLists" aria-expanded="true" aria-controls="collapseOne">
                                明星管理
                            </a>
                        </h4>
                    </div>
                    <div id="starLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/back/star/star.jsp')">所有明星</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--中心布局-->
        <%--右侧展示部分--%>
        <div class="col-sm-10" id="centerLayout">
            <div class="jumbotron" style="padding-left: 200px;">
                <h3>欢迎来到持明法州后台管理系统</h3>
            </div>
            <img src="${pageContext.request.contextPath}/img/1D8C4976B83B30000b938.bmp" alt="" style="width: 1569px; height: 600px;">
        </div>

    </div>
    <nav class="navbar navbar-default navbar-fixed-bottom" style="padding-bottom: 60px">
        <div class="container">
            <p style="padding-left: 500px">持名法州后台管理系统@百知教育 2019.10.26</p>
        </div>
    </nav>
</body>
</html>