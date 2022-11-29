<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--输出,条件,迭代标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt" %>
<!--数据格式化标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql" %>
<!--数据库相关标签库-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn" %>
<!--常用函数标签库-->
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: wangminan
  Date: 2022/11/20
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>书籍影音评价网站|书籍</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
</head>
<body>
<div class="container">

    <!-- Static navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <c:if test="${sessionScope.username!=null}">
                    <a class="navbar-brand" href="#">用户名称</a>
                </c:if>
                <c:if test="${sessionScope.username==null}">
                    <a class="navbar-brand" href="/view/loginPage">登录/注册</a>
                </c:if>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/user/books">书籍</a></li>
                    <li><a href="/user/films">电影</a></li>
                    <li><a href="/user/musics">音乐</a></li>
                    <li><a href="#">关于</a></li>
                    <li><a href="#">联系我们</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">年份</a></li>
                    <li><a href="#">类别</a></li>
                </ul>
                <form class="navbar-form navbar-right">
                    <label>
                        <input type="text" class="form-control" placeholder="搜索...">
                    </label>
                </form>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>
    <jsp:useBean id="books" scope="request" type="java.util.Map"/>
    <jsp:useBean id="assessments" scope="request" type="java.util.Map"/>
    <!-- Main component for a primary marketing message or call to action -->
    <c:forEach var="book" items="${books.result}">
        <c:if test="${book.isdeleted==0}">
            <div class="jumbotron">
                <div class="media">
                    <div class="media-left">
                        <a data-toggle="modal" data-target="#myModal">
                            <img class="media-object" src="${book.picurl}" alt="..." width="268" height="403">
                        </a>
                    </div>
                    <div class="media-body">
                        <h1 class="media-heading">${book.name}</h1>
                        <p>作者:${book.author} 类型:${book.type} 出版时间:${book.publishyear}</p>
                        <p>描述:${book.description}</p>
                    </div>
                </div>
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;
                                </button>
                                <h4 class="modal-title" id="myModalLabel">《${book.name}》的书评</h4>
                            </div>

                            <c:forEach var="assessment" items="${assessments.result}">
                                <c:if test="${assessment.isdeleted==0&&assessment.objecttype=='book'}">
                                    <div class="modal-body">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h3 class="panel-title">${assessment.username}</h3>
                                            </div>
                                            <div class="panel-body">
                                                <p>${assessment.assessment}</p>
                                                <p>${assessment.postdate}</p>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>

                            <div class="modal-footer">
                                <c:if test="${sessionScope.username!=null}">
                                    <div class="area">
                                        <label for="postmessage"></label><textarea rows="7" cols="60" name="message" id="postmessage"></textarea>
                                    </div>
                                </c:if>

                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <c:if test="${sessionScope.username!=null}">
                                    <button id="submit" type="button" class="btn btn-primary">提交评论</button>
                                </c:if>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
            </div>
            <%
                System.out.println(books);
            %>
        </c:if>
    </c:forEach>


</div> <!-- /container -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
        integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
        crossorigin="anonymous"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
</body>
</html>
<script>
    $(document).ready(function () {
        $("#submit").click(function () {
            const message = {
                "id": 4,
                "username": "wagnminan",
                "objectid": 1,
                "objecttype": "book",
                "assessment": $("#postmessage").val(),
                "postdate": "2022-11-29 23:46:00",
                "isdeleted": 0
            }
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '/user/assessments', true);
            // 设定传输格式 很重要 不然前端无法解析JSON
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(message));
            xhr.onload = function () {
                // 打印返回数据 {"msg":"登录成功","code":200}
                console.log(xhr.responseText);
                // 如果返回字符串中包括":200"则跳转
                if (xhr.responseText.indexOf(":200") > 0) {

                } else {
                    alert(xhr.responseText)
                }
            }
        });
    });
</script>

<style>
    textarea {
        resize: none;
        width: 550px;
        height: 100px;
        max-width: 550px;
        max-height: 100px;
    }
</style>