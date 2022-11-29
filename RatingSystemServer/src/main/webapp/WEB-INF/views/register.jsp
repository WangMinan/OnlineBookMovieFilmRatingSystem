<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/11/20
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <!--表单校验-->
    <script>
        function checkPwdAndRepwd(f1,f2) {
            if(f1&&f2) {
                var pwd = document.getElementById("password").value;
                var repwd = document.getElementById("repassword").value;
                var msg = document.getElementById("repasswordMsg");
                var div = document.getElementById("repasswordDiv");
                if(pwd===repwd) {
                    div.className="form-group";
                    msg.innerHTML="";
                    return true;
                } else {
                    div.className+=" has-error";
                    msg.innerHTML="确认密码与密码不一致";
                    return false;
                }

            } else {
                return false;
            }

        }

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

        function checkForm() {
            var flag1 = checkFormNotNull("username");
            var flag2 = checkFormNotNull("password");
            var flag3 = checkFormNotNull("repassword");
            var flag4 = checkFormNotNull("email");
            var flag5 = checkPwdAndRepwd(flag2,flag3);
            return flag1 && flag2 && flag3 && flag4 && flag5;
        }
    </script>
</head>
<body>
    <div class="container">
        <!--注册页面主体-->
        <div class="row" style="background-color: white">
            <div class="col-sm-8 col-sm-offset-2" style="border: 5px solid slategray;background-color: white">
                <!--表单标题-->
                <div class="row">
                    <div class="text-center">
                        <h3>用户注册</h3>
                    </div>
                </div>
                <!--表单内容-->
                <form class="form-horizontal" onsubmit="return checkForm()">
                    <!-- 用户名-->
                    <div id="usernameDiv" class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                        </div>
                        <label id="usernameMsg" class="col-sm-2 control-label"></label>
                    </div>
                    <!-- 密码-->
                    <div id="passwordDiv" class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                        </div>
                        <label id="passwordMsg" class="col-sm-2 control-label"></label>
                    </div>
                    <!-- 确认密码-->
                    <div id="repasswordDiv" class="form-group">
                        <label for="repassword" class="col-sm-2 control-label">确认密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="repassword" placeholder="请再次输入密码">
                        </div>
                        <label id="repasswordMsg" class="col-sm-2 control-label"></label>
                    </div>
                    <!-- 电子邮箱-->
                    <div id="emailDiv" class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-8">
                            <input type="email" class="form-control" id="email" name="mail" placeholder="请输入电子邮箱">
                        </div>
                        <label id="emailMsg" class="col-sm-2 control-label"></label>
                    </div>
                    <!-- 提交按钮-->
                    <div class="form-group">
                        <div class="text-center">
                            <button type="submit" class="btn btn-default">注册</button>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="/view/loginPage">已有账号？点此登录</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

</body>
</html>

<style>
    .container{
        width: 800px;
        height: 400px;
        margin: 0 auto;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
    }
</style>
