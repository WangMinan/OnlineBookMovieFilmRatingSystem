<%@ taglib prefix="myTag" uri="MyTags" %>
<%--
  Created by IntelliJ IDEA.
  User: 85077
  Date: 2022/12/4
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
  <nav class="navbar navbar-default" id="selectYearAndTypeBar">
    <div class="container-fluid">
      <ul class="nav navbar-nav navbar-left" id="yearSelect">
        <li>
          <a><strong>按年份搜索</strong></a>
        </li>
        <li>
          <myTag:searchNavTypeTag/>
        </li>
      </ul>
    </div>
    <div class="container-fluid">
      <ul class="nav navbar-nav navbar-left" id="typeSelect">
        <li>
          <a><strong>按类别搜索</strong></a>
        </li>
        <li>
            <myTag:searchNavTypeTag/>
        </li>
      </ul>
    </div>
  </nav>

</body>
</html>
