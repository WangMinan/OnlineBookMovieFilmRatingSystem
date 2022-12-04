<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTag" uri="MyTags" %>
<%--
  Created by IntelliJ IDEA.
  User: 85077
  Date: 2022/12/4
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <!-- Static navbar -->
  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">R</a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <myTag:activeWorktypeTag/>
        </ul>
        <ul class="nav navbar-nav navbar-right">

          <c:if test="${sessionScope.username!=null}">
            <li class="dropdown">
              <a
                      href="#"
                      class="dropdown-toggle"
                      data-toggle="dropdown"
                      role="button"
                      aria-haspopup="true"
                      aria-expanded="false">
                  ${sessionScope.username}
                <span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a href="/view/userUpdate">个人信息修改</a>
                </li>
                <li>
                  <a href="/user/getMyAssessments/AAA">
                    查询发表的评价信息
                  </a>
                </li>
                <li role="separator" class="divider"></li>
                <li><a href="#" id="logoutBtn">退出</a></li>
              </ul>
            </li>
          </c:if>
          <c:if test="${sessionScope.username==null}">
            <li><a href="/view/loginPage">登录/注册</a></li>
          </c:if>
        </ul>
      </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
  </nav>

</body>
</html>
