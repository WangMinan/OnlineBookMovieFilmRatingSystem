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
    <title>Title</title>
</head>
<body>
    <table>
        <jsp:useBean id="books" scope="request" type="java.util.Map"/>
        <%
            System.out.println(books);
        %>
        <c:forEach var="book" items="${books.result}">
            <tr>
                <td>${book.id}</td>
                <td>${book.name}</td>
                <td>${book.isbn}</td>
                <td>${book.type}</td>
                <td>${book.publishyear}</td>
            </tr>
        </c:forEach>
</table>
</body>
</html>
