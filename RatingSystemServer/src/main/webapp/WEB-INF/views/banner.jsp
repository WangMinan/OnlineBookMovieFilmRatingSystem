<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 85077
  Date: 2022/12/2
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Banner</title>
</head>
<body>
    <div class="jumbotron" style="background-color: #41b883">
        <c:choose>
            <c:when test="${sessionScope.username!=null}">
                <h2 class="text-center" style="color: white">${sessionScope.username}您好，欢迎来到书影音评价系统！</h2>
            </c:when>
            <c:otherwise>
                <h2 class="text-center" style="color: white">欢迎来到书影音评价系统！</h2>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
