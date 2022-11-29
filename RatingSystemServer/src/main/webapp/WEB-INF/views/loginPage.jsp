<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!--输出,条件,迭代标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%> <!--数据格式化标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%> <!--数据库相关标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%> <!--常用函数标签库-->
<%@ page isELIgnored="false"%>
<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/11/20
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <script>
        $('#submit').on('click', function () {

        })
    </script>
</head>
<body>
    <div class="container">
        <!--登录页面主体-->
        <div class="row" style="background-color: #3c3c3c">
            <div class="col-sm-8 col-sm-offset-2" style="border: 5px solid slategray;background-color: white">
                <!--表单标题-->
                <div class="row">
                    <div class="text-center">
                        <h3>用户登录</h3>
                    </div>
                </div>
                <!--表单内容-->
                <form class="form-horizontal">
                    <!--用户名-->
                    <div id="usernameDiv" class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                        </div>
                        <label id="usernameMsg" class="col-sm-2 control-label"></label>
                    </div>
                    <!--密码-->
                    <div id="passwordDiv" class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                        </div>
                        <label id="passwordMsg" class="col-sm-2 control-label"></label>
                    </div>
                    <!--记住我-->
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox" name="rememberme">
                                <label>
                                    <input type="checkbox"> 记住我
                                </label>
                            </div>
                        </div>
                    </div>
                    <!--提交按钮-->
                    <div class="form-group">
                        <div class="text-center">
                            <button type="submit" id="submit" class="btn btn-default">登录</button>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="<c:url value="/view/register" />">没有账号？点此注册</a>
                    </div>
                </form>
            </div>

        </div>

    </div>
</body>
</html>
