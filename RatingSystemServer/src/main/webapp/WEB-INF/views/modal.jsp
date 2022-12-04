<%--
  Created by IntelliJ IDEA.
  User: 85077
  Date: 2022/12/4
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modal</title>
</head>
<body>
    <div class="modal focus"
         id="myModal"
         tabindex="-1"
         role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="true"
    >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-body">
                    <%--                            插入代码的位置--%>
                </div>
                <div class="modal-footer">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class='panel-title text-left'>
                                评价区
                            </h3>
                        </div>
                        <div class='panel-body'>
                            <div class='area'>
                                <label for='postmessage'></label>
                                <textarea
                                        class="assessment-area"
                                        rows='7'
                                        cols='60'
                                        name='message'
                                        id='postmessage'
                                        maxlength='500'
                                        placeholder='请输入评价内容'
                                >
                                            </textarea>
                            </div>
                        </div>
                    </div>


                    <button id='submit' type='button' class='btn btn-primary'>提交评论</button>

                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->

</body>
</html>
