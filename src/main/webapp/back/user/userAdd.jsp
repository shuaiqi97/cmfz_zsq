<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '上半年用户注册趋势'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["一月份","二月份","三月份","四月份","五月份","六月份"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type: 'line',
            data: []
        },
            {
                name: '女',
                type: 'line',
                data: []
            }]

    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url:"${pageContext.request.contentType}/cmfz/user/UserTrend",
        type:"post",
        datattype:"json",
        success:function (data) {
            console.log(data)
            myChart.setOption({
                xAxis: {
                    data : ["1月份","2月份","3月份","4月份","5月份","6月份"]
                },
                series: [{
                    name : '男',
                    type :'bar',
                    data :data.nan
                },
                    {
                        name: '女',
                        type: 'bar',
                        data: data.nv

                    }]
            })
        }
    })
</script>

</body>
</html>