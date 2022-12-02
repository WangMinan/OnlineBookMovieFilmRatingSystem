<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!--输出,条件,迭代标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%> <!--数据格式化标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%> <!--数据库相关标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%> <!--常用函数标签库-->
<%@ taglib uri="MyTags" prefix="mytag"%>
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
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
    <link rel="shortcut icon" href="http://121.41.227.153:8081/file/header.png" type="image/x-icon"/>
</head>
<body>
    <div class="wrapper">
        <!--banner-->
        <c:import url="banner.jsp" />

        <div class="container">
            <!--登录页面主体-->
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2" style="border: 5px solid slategray;background-color: white">
                    <c:set var="title" scope="session" value="用户登录" />
                    <mytag:titleTag/>
                    <!--表单内容-->
                    <form class="form-horizontal">
                        <!--用户名-->
                        <div id="usernameDiv" class="form-group">
                            <label for="username" class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-8">
                                <c:choose>
                                    <c:when test="${cookie.rememberMe.value eq 'true'}">
                                        <input type="text" class="form-control" id="username" name="username" value="${cookie.username.value}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <label id="usernameMsg" class="col-sm-2 control-label"></label>
                        </div>
                        <!--密码-->
                        <div id="passwordDiv" class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-8">
                                <c:choose>
                                    <c:when test="${cookie.rememberMe.value eq 'true'}">
                                        <input type="password" class="form-control" id="password" name="password" value="${cookie.password.value}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <label id="passwordMsg" class="col-sm-2 control-label"></label>
                        </div>
                        <!--记住我-->
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <div class="checkbox">
                                    <label>
                                        <c:choose>
                                            <c:when test="${cookie.rememberMe.value eq 'true'}">
                                                <input type="checkbox" id="rememberMe" name="rememberMe" checked> 记住我
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" id="rememberMe" name="rememberMe"> 记住我
                                            </c:otherwise>
                                        </c:choose>

                                    </label>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!--提交按钮-->
                    <div class="text-center">
                        <button id="submitBtn" class="btn btn-default">登录</button><br><br>
                    </div>
                    <div class="text-center">
                        <a href="/view/register">没有账号？点此注册</a>
                    </div>
                </div>

            </div>
        </div>

        <!--Footer-->
        <c:import url="footer.jsp" />
    </div>
</body>
</html>

<style>
    body{
        align-items: center;
    }
    .container{
        width: 800px;
        height: 300px;
        margin: 0 auto;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
    }
    .wrapper {
        min-height: 100%; /* 2. 需要将容器的高度设置成100% */
        position: relative; /* 3. 容器的position设置为relative，给子元素定位提供基点 */
    }

</style>

<script>
    //验证表单元素是否为空
    function checkFormNotNull(nid){
        var nodex=document.getElementById(nid);
        var msg=document.getElementById(nid+"Msg");
        var div=document.getElementById(nid+"Div");
        var reg=/^\s*$/;
        if(reg.test(nodex.value)) {
            div.className+=" has-error";
            msg.innerHTML="不能为空";
            return false;
        } else {
            div.className="form-group";
            msg.innerHTML="";
            return true;
        }

    }
    //表单验证
    function checkForm() {
        var flag1 = checkFormNotNull("username");
        var flag2 = checkFormNotNull("password");
        return flag1 && flag2;
    }

    $(function () {
        $("#submitBtn").on("click", function () {
            if(checkForm()) {
                const loginUser= {
                    "user":{
                        "username" : document.getElementById("username").value,
                        "password" : document.getElementById("password").value
                    },
                    "rememberMe" : document.getElementById("rememberMe").checked
                }

                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/user/login', true);
                // 设定传输格式 很重要 不然前端无法解析JSON
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.send(JSON.stringify(loginUser));

                // 定义回调函数
                xhr.onload = function () {
                    // 打印返回数据 {"msg":"登录成功","code":200}
                    console.log(xhr.responseText);
                    // 如果返回字符串中包括":200"则跳转
                    if (xhr.responseText.indexOf(":200") > 0) {

                        window.location.href = "/user/books";
                    } else {
                        alert("用户名或密码错误，请重新尝试");
                        window.location.href = "/view/loginPage";
                    }
                }
            }

        })
    })
</script>
