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
    <title>书影音评价网站|电影</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <%--  设置网页图标  --%>
    <link rel="shortcut icon" href="http://121.41.227.153:8081/file/header.png" type="image/x-icon"/>
</head>
<body style="overflow:Scroll;overflow-x:hidden">
<div class="wrapper">
    <!--banner-->
    <c:import url="banner.jsp" />
    <div class="container">
        <jsp:useBean id="films" scope="request" type="java.util.Map"/>
        <c:set var="worktype" scope="request" value="films" />
        <c:import url="staticNav.jsp"/>
        <c:import url="searchNav.jsp"/>

        <!-- Main component for a primary marketing message or call to action -->
        <c:forEach var="film" items="${films.result}">
            <c:if test="${film.isdeleted==0}">
                <div class="jumbotron">
                    <div class="media">
                        <div class="media-left">
                            <a data-toggle="modal" data-target="#myModal" data-whatever="${film.id}">
                                <img class="media-object"
                                     src="${film.picurl}"
                                     alt="..." width="268"
                                     height="403"
                                >
                            </a>
                        </div>
                        <div class="media-body" id="mediaBody">
                            <h1 class="media-heading">${film.name}</h1>
                            <p>类型:${film.type} 出版时间:${film.publishyear}</p>
                            <p>描述:${film.description}</p>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
        <c:import url="modal.jsp" />
        <%--      曲线救国 增加一下和底部的距离      --%>
        <div style="height: 15%;"></div>
    </div> <!-- /container -->
    <!--Footer-->
    <c:import url="footer.jsp" />
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
        integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
        crossorigin="anonymous"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
</body>
</html>
<script>

    const assessments = []
    let currentFilmId = 0
    const films = []

    // 页面加载时调用函数
    $().ready(function (){
        let xhr = new XMLHttpRequest();
        xhr.open('GET', '/user/assessments/film', true);
        xhr.send();
        xhr.onload = function () {
            const resp = JSON.parse(xhr.responseText)
            if (resp.code === 200) {
                assessments.push(...resp.assessments)
            } else{
                alert(resp.message)
            }
        }
    })

    // 页面加载时调用函数
    $().ready(function (){
        let xhr = new XMLHttpRequest();
        xhr.open('GET', '/user/films/AAA/1/999999', true);
        xhr.send();
        xhr.onload = function () {
            const resp = JSON.parse(xhr.responseText)
            if (resp.code === 200) {
                films.push(...resp.result)
            } else{
                alert(resp.message)
            }
            let years= []
            let types = []
            films.forEach(film => {
                if (!years.includes(film.publishyear)) {
                    years.push(film.publishyear)
                }
                if (!types.includes(film.type)) {
                    types.push(film.type)
                }
            })
            years.sort((a, b) => a - b)
            // 使用jquery的id选择器动态添加years与films到下拉菜单
            // 注意 动态生成的话，需要在生成后再绑定事件
            for(let i = 0; i < years.length;i++){
                $('#yearSelect').append(
                    '<li>' +
                    '<a href="' + `/user/films/year` + years[i] + '">'
                    + years[i] +
                    '</a>' +
                    '</li>')
            }
            for(let i = 0; i < types.length;i++){
                $('#typeSelect').append(
                    '<li>' +
                    '<a href="' + `/user/films/type` + types[i] + '">'
                    + types[i] +
                    '</a>' +
                    '</li>')
            }

            // 解决路径中的中文被UTF-8编码的问题
            const currentLocation = revertUTF8(location.pathname)

            // 绑定事件
            $('#selectYearAndTypeBar').find('li').each(function () {
                const a = $(this).find('a:first')[0];
                if(currentLocation === '/user/films/AAA'){
                    $(this).removeClass('active')
                }else if (currentLocation === $(a).attr('href')) {
                    $(this).addClass('active'); // this.className = 'active';
                } else {
                    $(this).removeClass('active');
                }
            })
        }
    })

    $(document).ready(function () {
        $("#submit").click(function () {
            const message = {
                // id会auto-increment 不需要传入
                // "id": 4,
                // 没事的username也不用传 我已经机智的料到这会成为一个问题，所以很自觉的从session里面拿了
                <%--"username": "${sessionScope.username}",--%>
                // 具体参考Assessment里的第4个构造函数 只要传接下来的3个数据就可以了
                "objectid": currentFilmId,
                "objecttype": "film", // 这个确实可以写死
                "assessment": $("#postmessage").val(),
                // postdate与isDeleted会自动生成 不需要传入
                // "postdate": "2022-11-29 23:46:00",
                // "isdeleted": 0
            }
            if (message.assessment.trim() === '' && ${sessionScope.username!=null}) {
                alert('评论不能为空')
                return
            }
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '/user/assessments', true);
            // 设定传输格式 很重要 不然前端无法解析JSON
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(message));
            xhr.onload = function () {
                // 打印返回数据 {"msg":"评论成功","code":200}
                // 如果返回字符串中包括":200"则跳转
                if (xhr.responseText.indexOf(":200") > 0 && ${sessionScope.username!=null}) {
                    alert("评论成功");
                    // 刷新当前页面
                    window.location.reload();
                } else {
                    alert("请您先登录");
                    window.location.href = "/user/assessments";
                }
            }
        })
    });

    $(document).ready(function () {
        $("#logoutBtn").click(function () {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', '/user/logout', true);
            xhr.send();
            xhr.onload = function () {
                // 打印返回数据 {"msg":"登录成功","code":200}
                // 如果返回字符串中包括":200"则跳转
                if (xhr.responseText.indexOf(":200") > 0) {
                    alert("退出成功");
                    // 刷新当前页面
                    window.location.reload();
                } else {
                    alert(xhr.responseText)
                }
            }
        })
    });

    // 展示模态框后的回调函数
    $('#myModal').on('show.bs.modal', async function (event) {
        const a = $(event.relatedTarget) // 触发事件的按钮
        const filmId = a.data('whatever') // 解析出data-whatever内容
        currentFilmId = filmId
        const modal = $(this)
        let flag = false;
        for (let i = 0; i < assessments.length; i++){
            if (assessments[i].objectid === filmId){
                flag = true
                modal.find('.modal-title').text(assessments[i].work.name)
                break;
            }
        }
        // 将评价内容放入modal-body中
        for(let i = 0; i < assessments.length; i++){
            if (assessments[i].objectid === filmId){
                modal.find('.modal-body').append(`
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">`
                    + `来自:`
                    + assessments[i].username
                    + `的评价` +
                    `</h3>
                        </div>
                        <div class="panel-body">`
                    + assessments[i].assessment +
                    `<br><br>`
                    + `发布时间:`
                    + assessments[i].postdate +
                    `</div>
                    </div>
                `)
            }
        }
        if(!flag){
            modal.find('.modal-title').text('该电影暂无评论')
        }
    }).on('hidden.bs.modal', function () {
        // 隐藏模态框后的回调函数
        // 清空modal-body中的内容
        $(this).find('.modal-title').text('')
        $(this).find('.modal-body').empty()
    });

    // UTF8编码转成汉字字符串
    function revertUTF8(szInput) {
        let x, wch, wch1, wch2, uch = "", szRet = "";
        for (x=0; x<szInput.length; x++) {
            if (szInput.charAt(x)==="%") {
                wch =parseInt(szInput.charAt(++x) + szInput.charAt(++x),16);
                if (!wch) {break;}
                if (!(wch & 0x80)) {
                    wch = wch;
                } else if (!(wch & 0x20)) {
                    x++;
                    wch1 = parseInt(szInput.charAt(++x) + szInput.charAt(++x),16);
                    wch  = (wch & 0x1F)<< 6;
                    wch1 = wch1 & 0x3F;
                    wch  = wch + wch1;
                } else {
                    x++;
                    wch1 = parseInt(szInput.charAt(++x) + szInput.charAt(++x),16);
                    x++;
                    wch2 = parseInt(szInput.charAt(++x) + szInput.charAt(++x),16);
                    wch  = (wch & 0x0F)<< 12;
                    wch1 = (wch1 & 0x3F)<< 6;
                    wch2 = (wch2 & 0x3F);
                    wch  = wch + wch1 + wch2;
                }
                szRet += String.fromCharCode(wch);
            } else {
                szRet += szInput.charAt(x);
            }
        }
        return(szRet);
    }
</script>

<style>
    html, body {
        height: 100%;  /* 1. 需要将页面的高度设置成浏览器可视区域的高度 */
    }
    .wrapper {
        min-height: 100%; /* 2. 需要将容器的高度设置成100% */
        position: relative; /* 3. 容器的position设置为relative，给子元素定位提供基点 */
    }

    textarea {
        resize: none;
        width: 550px;
        height: 100px;
        max-width: 550px;
        max-height: 100px;
    }
</style>
