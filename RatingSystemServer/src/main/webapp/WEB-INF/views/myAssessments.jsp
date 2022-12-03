<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/12/3
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的评价</title>
    <link rel="shortcut icon" href="http://121.41.227.153:8081/file/header.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
            integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
            crossorigin="anonymous"></script>

</head>
<body style="overflow:Scroll;overflow-x:hidden">
    <div class="wrapper">
        <!--banner-->
        <c:import url="banner.jsp" />
        <div class="container">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">查询我发表的评价</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <form class="col-sm-10">
                            <input id="name" type="text" class="form-control" placeholder="根据书影音名称查询">
                        </form>
                        <button class="btn btn-info col-sm-offset-1">查询</button>
                    </div>

                </div>
            </div>
            <div class="row text-center">
                <h3>我发表的评价</h3>
            </div>

            <table class="table table-bordered table-hover">
                <thead>
                    <tr class="text-nowrap">
                        <th>id</th>
                        <th>书、影、音名</th>
                        <th>评价内容</th>
                        <th>时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <jsp:useBean id="assessments" scope="request" type="java.util.List"/>
                    <c:forEach var="item" items="${assessments}">
                        <tr>
                            <td>${item.objectid}</td>
                            <td>${item.work.name}</td>
                            <td>${item.assessment}</td>
                            <td class="text-nowrap">${item.postdate}</td>
                            <td>
                                <ul class="list-inline text-nowrap">
                                    <li><a class="btn btn-info" href="">修改</a></li>
                                    <li><a class="btn btn-danger" href="">删除</a></li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div style="height: 15%;"></div>
        </div>
        <c:import url="footer.jsp" />
    </div>

</body>
</html>

<style>
    html, body {
        height: 100%;  /* 1. 需要将页面的高度设置成浏览器可视区域的高度 */
    }
    .wrapper {
        min-height: 100%; /* 2. 需要将容器的高度设置成100% */
        position: relative; /* 3. 容器的position设置为relative，给子元素定位提供基点 */
    }
</style>