<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!--输出,条件,迭代标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%> <!--数据格式化标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%> <!--数据库相关标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%> <!--常用函数标签库-->
<%@ page isELIgnored="false"%>
<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/11/20
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
</head>
<body>
<div class="container">

    <!-- Static navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">用户名称</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">书籍</a></li>
                    <li><a href="#">电影</a></li>
                    <li><a href="#">音乐</a></li>
                    <li><a href="#">关于</a></li>
                    <li><a href="#">联系我们</a></li>
                    <li class="dropdown">
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">年份</a></li>
                    <li><a href="#">类别</a></li>
                </ul>
                <form class="navbar-form navbar-right">
                    <input type="text" class="form-control" placeholder="搜索...">
                </form>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>
    <jsp:useBean id="books" scope="request" type="java.util.Map"/>
    <!-- Main component for a primary marketing message or call to action -->
    <c:forEach var="book" items="${books.result}">

        <div class="jumbotron">
            <div class="media">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object" src="${book.picurl}" alt="..." width="268" height="403">
                    </a>
                </div>
                <div class="media-body">
                    <h1 class="media-heading">${book.name}</h1>
                    <p>作者:${book.author} 类型:${book.type} 出版时间:${book.publishyear}</p>
                    <p>描述:${book.description}</p>
                </div>
            </div>
        </div>
        <%
            System.out.println(books);
        %>
    </c:forEach>

</div> <!-- /container -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</body>
</html>
