<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/10/28
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
</head>
<body>
    HelloWorld!
    <% System.out.println("helloWorld");%>
    <button id="btnPost">发送post请求</button>
</body>
</html>
<script>
    // 请严格按照JSON格式进行书写
    const loginUser= {
        "user":{
            "username" : "wangminan",
            "password" : "123456"
        },
        "rememberMe" : "true"
    }

    $(function () {
        $("#btnPost").on("click", function () {
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '/user/login', true);
            // 设定传输格式 很重要 不然前端无法解析JSON
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(loginUser));

            // 定义回调函数
            xhr.onload = function () {
                // 打印返回数据 {"msg":"登录成功","code":200}
                // 如果返回字符串中包括":200"则跳转
                if (xhr.responseText.indexOf(":200") > 0) {
                    window.location.href = "/user/books/AAA";
                } else {
                    alert(xhr.responseText)
                }
            }
        })
    })
</script>
