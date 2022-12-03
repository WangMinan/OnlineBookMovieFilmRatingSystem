<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/12/3
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="assessments" scope="request" type="java.util.List"/>
<html>
<head>
    <title>我的评价</title>
    <link rel="shortcut icon" href="http://121.41.227.153:8081/file/header.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
            integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
            crossorigin="anonymous"></script>

</head>
<body style="overflow:Scroll;overflow-x:hidden">
    <div class="wrapper">
        <!--banner-->
        <c:import url="banner.jsp" />
        <div class="container">
            <a class="btn btn-primary" href="/user/books/AAA">返回首页</a><br><br>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">查询我发表的评价</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <form class="col-sm-10">
                            <input id="name" type="text" class="form-control" placeholder="根据书影音名称查询">
                        </form>
                        <button class="btn btn-info col-sm-offset-1" id="searchBtn">查询</button>
                    </div>

                </div>
            </div>
            <div class="row text-center">
                <h3>我发表的评价</h3>
            </div>

            <table class="table table-bordered table-hover">
                <thead>
                    <tr class="text-nowrap">
                        <th>类型</th>
                        <th>书、影、音名</th>
                        <th>评价内容</th>
                        <th>时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${assessments}">
                        <tr>
                            <td>${item.objecttype}</td>
                            <td>${item.work.name}</td>
                            <td>${item.assessment}</td>
                            <td class="text-nowrap">${item.postdate}</td>
                            <td>
                                <ul class="list-inline text-nowrap">
                                    <li>
                                        <button
                                            class="btn-info"
                                            data-toggle="modal"
                                            data-target="#myModal"
                                            data-id="${item.id}"
                                            data-assessment="${item.assessment}"
                                        >
                                            修改
                                        </button>
                                    </li>
                                    <li><button class="btn-danger" data-id="${item.id}">删除</button></li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-id="">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabel">评价内容</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="submitBtn" class="btn btn-primary">保存</button>
                        </div>
                    </div>
                </div>
            </div>

            <div style="height: 15%;"></div>
        </div>
        <c:import url="footer.jsp" />
    </div>

</body>
</html>

<style>
    html, body {
        height: 100%;  /* 1. 需要将页面的高度设置成浏览器可视区域的高度 */
    }
    .wrapper {
        min-height: 100%; /* 2. 需要将容器的高度设置成100% */
        position: relative; /* 3. 容器的position设置为relative，给子元素定位提供基点 */
    }
</style>

<script>
    let id;

    $(function () {
        $('tbody').on('click', '.btn-danger', function () {
            id= parseInt($(this).attr('data-id'));
            //var dataid = $(this).attr('data-id');
            //var id= parseInt(dataid);

            let xhr = new XMLHttpRequest();
            xhr.open('DELETE', `http://localhost:8080/user/assessments/delete/` + id, true);
            // 设定传输格式 很重要 不然前端无法解析JSON
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(id));

            // 定义回调函数
            xhr.onload = function () {
                // 打印返回数据 {"msg":"登录成功","code":200}
                // 如果返回字符串中包括":200"则跳转
                if (xhr.responseText.indexOf(":200") > 0) {
                    alert("删除成功！");
                    window.location.href = "/user/getMyAssessments/AAA";
                } else {
                    alert("删除失败！");
                    window.location.href = "/user/getMyAssessments/AAA";
                }
            }


        })

        $('tbody').on('click', '.btn-info', function () {
            id= parseInt($(this).attr('data-id'));
            const assessment = $(this).attr('data-assessment')
            //var dataid = $(this).attr('data-id');
            //var id= parseInt(dataid);

            const modal = $('#myModal');
            modal.find('.modal-body').append(
                `<div class="text-center">
                    <label for="postmessage"></label>
                        <textarea
                            rows="7"
                            cols="70"
                            name="message"
                            id="postmessage"
                            maxlength="500"
                            placeholder="请输入评价内容"
                        >`
                            + assessment +
                    `</textarea>
                </div>`
            )

            $('#submitBtn').on('click', function () {
                //var dataid = $(this).attr('data-id');
                //var id= parseInt(dataid);

                const assessmentUpdate= {
                    "id" : id,
                    "assessment" : $("#postmessage").val()
                }

                let xhr = new XMLHttpRequest();
                xhr.open('PUT', `http://localhost:8080/user/assessments/update/` + id, true);
                // 设定传输格式 很重要 不然前端无法解析JSON
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.send(JSON.stringify(assessmentUpdate));

                // 定义回调函数
                xhr.onload = function () {
                    // 打印返回数据 {"msg":"登录成功","code":200}
                    // 如果返回字符串中包括":200"则跳转
                    if (xhr.responseText.indexOf(":200") > 0) {
                        window.location.href = "/user/getMyAssessments/AAA";
                    } else {
                        alert("修改失败！");
                        window.location.href = "/user/getMyAssessments/AAA";
                    }
                }
            })
        })

        $('#myModal').on('hidden.bs.modal', function () {
            // 隐藏模态框后的回调函数
            // 清空modal-body中的内容
            $(this).find('.modal-body').empty()
        });

        $("#searchBtn").on("click", function () {
            // 从id为name的框中获取值
            const text = $("#name").val();
            if(text === ""){
                window.location.href = `/user/getMyAssessments/AAA`
            } else {
                window.location.href = `/user/getMyAssessments/indistinct` + text
            }
        })
    })
</script>
