<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!--输出,条件,迭代标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%> <!--数据格式化标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%> <!--数据库相关标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%>
<%@ taglib prefix="mytag" uri="MyTags" %>
<!--常用函数标签库-->
<%--
  Created by IntelliJ IDEA.
  User: 85077
  Date: 2022/12/1
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息修改</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
</head>
<body>
  <div class="wrapper">
    <!--banner-->
    <c:import url="banner.jsp" />
    <div class="container">
      <!--注册页面主体-->
      <div class="row" style="background-color: white">
        <div class="col-sm-8 col-sm-offset-2" style="border: 5px solid slategray;background-color: white">
          <!--表单标题-->
          <c:set var="title" scope="session" value="个人信息修改" />
          <mytag:titleTag/>
          <!--表单内容-->
          <form class="form-horizontal">
            <!-- 用户名-->
            <div id="usernameDiv" class="form-group">
              <label for="username" class="col-sm-2 control-label">用户名</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" id="username" name="username" value="${sessionScope.username}" placeholder="请输入用户名">
              </div>
              <label id="usernameMsg" class="col-sm-2 control-label"></label>
            </div>
            <!-- 密码-->
            <div id="passwordDiv" class="form-group">
              <label for="password" class="col-sm-2 control-label">密码</label>
              <div class="col-sm-8">
                <input type="password" class="form-control" id="password" name="password" value="${sessionScope.password}" placeholder="请输入密码">
              </div>
              <label id="passwordMsg" class="col-sm-2 control-label"></label>
            </div>
            <!-- 确认密码-->
            <div id="repasswordDiv" class="form-group">
              <label for="repassword" class="col-sm-2 control-label">确认密码</label>
              <div class="col-sm-8">
                <input type="password" class="form-control" id="repassword" value="${sessionScope.password}" placeholder="请再次输入密码">
              </div>
              <label id="repasswordMsg" class="col-sm-2 control-label"></label>
            </div>
            <!-- 电子邮箱-->
            <div id="emailDiv" class="form-group">
              <label for="email" class="col-sm-2 control-label">Email</label>
              <div class="col-sm-8">
                <input type="email" class="form-control" id="email" name="email" value="${sessionScope.mail}" placeholder="请输入电子邮箱">
              </div>
              <label id="emailMsg" class="col-sm-2 control-label"></label>
            </div>
          </form>
          <!--提交按钮-->
          <div class="text-center">
            <button id="submitBtn" class="btn btn-success">提交</button>
            <button id="cancelBtn" class="btn btn-danger" onclick="window.location.href = '/user/books';">取消</button>
            <br><br>
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
  .container{
    width: 800px;
    height: 400px;
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

<!--表单校验-->
<script>
  //校验密码和确认密码是否一致
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
  //校验表单元素是否为空
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

  $(function () {
    $("#submitBtn").on("click", function () {
      if(checkForm()) {
        const user= {
          "username" : document.getElementById("username").value,
          "password" : document.getElementById("password").value,
          "mail" : document.getElementById("email").value
        }

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/user/users', true);
        // 设定传输格式 很重要 不然前端无法解析JSON
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(user));

        // 定义回调函数
        xhr.onload = function () {
          // 打印返回数据 {"msg":"注册成功","code":200}
          console.log(xhr.responseText);
          // 如果返回字符串中包括":200"则跳转
          if (xhr.responseText.indexOf(":200") > 0) {
            window.location.href = "/user/books";
          } else {
            alert("个人信息修改失败！");
            window.location.href = "/view/userUpdate";
          }
        }
      }

    })
  })

  $(function () {
    $("#submitBtn").on("click", function () {
      if(checkForm()) {
        const user= {
          "username" : document.getElementById("username").value,
          "password" : document.getElementById("password").value,
          "mail" : document.getElementById("email").value
        }

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/user/users', true);
        // 设定传输格式 很重要 不然前端无法解析JSON
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(user));

        // 定义回调函数
        xhr.onload = function () {
          // 打印返回数据 {"msg":"注册成功","code":200}
          console.log(xhr.responseText);
          // 如果返回字符串中包括":200"则跳转
          if (xhr.responseText.indexOf(":200") > 0) {
            window.location.href = "/user/books";
          } else {
            alert("个人信息修改失败！");
            window.location.href = "/view/userUpdate";
          }
        }
      }

    })
  })

</script>
