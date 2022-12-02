<%@ taglib uri="MyTags" prefix="mytag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 85077
  Date: 2022/12/2
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Footer</title>
</head>
<body>
  <mytag:footerTag/>
  <div class="footer">
    <div class="row">
      <div class="col-sm-2" style="text-align: center">
        <img src="http://121.41.227.153:8081/file/header.png" class="img-rounded" height="100px" width="100px"><br>
      </div>
      <div class="col-sm-5" >
        <br>
        <ul class="list-inline">
          <li><span style="color: white">快速入口：</span></li>
          <li><a href="/user/books"><strong style="color: white">书籍</strong></a></li>
          <li>|</li>
          <li><a href="/user/films"><strong style="color: white">电影</strong></a></li>
          <li>|</li>
          <li><a href="/user/musics"><strong style="color: white">音乐</strong></a></li>
        </ul><br>
        <ul class="list-inline">
          <li><span style="color: white">友情链接：</span></li>
          <li><a href=""><strong style="color: white">管理员入口</strong></a></li>
        </ul>
      </div>
      <div class="col-sm-4">
        <br>
        <p style="color: white">版权所有© 西北工业大学</p>
        <span style="color: white; white-space:pre">友谊校区地址：西安市友谊西路127号       邮编:710072</span><br>
        <span style="color: white; white-space:pre">长安校区地址：西安市长安区东祥路1号    邮编:710129</span><br>
      </div>
    </div>
  </div>
</body>
</html>

<style>
  .footer{
    margin-top: 20px;
    background: #41b883;
    width: 100%;
    height: 100px;
    color: white;
    position: absolute;
    bottom: 0;
  }
</style>
