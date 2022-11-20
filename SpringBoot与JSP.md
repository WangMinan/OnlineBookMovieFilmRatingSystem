<div align = "center">
    <h1>
        SpringBoot与JSP
    </h1>
</div>

### 前置说明

由于在SpringBoot中，我们并不使用显式的class来继承HttpServlet，而是在框架下使用@Controller注解来引导tomcat自动配置servlet，因此就不使用web.xml来配置映射关系。(我记得spring也不用web.xml了，我看大家实验4也没有用web.xml。)撰写这篇说明的原因就是希望说明在SpringBoot中如何配置相关的映射。

### 开发环境修正

本次项目中的依赖我已经进行了引入，包括JSP支持与jstl标准库依赖。配置文件已经写入。在IDEA中对JSP开发环境进行配置请参考博文https://blog.csdn.net/pan_junbiao/article/details/105600172   如果JSP资源无法正常访问，最大的可能就是没有设置webapp的目录映射。请检查项目结构-模块中是否已添加设置。

<img src="https://cdn.jsdelivr.net/gh/WangMinan/Pics/image-20221120191410612.png" alt="image-20221120191410612" style="zoom:50%;" />

### 在访问时暴露JSP文件

由于使用Springboot时按路径访问只能访问到src/resources/static底下的静态资源，如果直接访问webapp下的文件会报404.因此我们需要在@Controller的部分对访问进行转发。对于本项目而言，**在webapp中新建了一个JSP文件之后，应当直接在controller.JspExportController设置暴露转发。**具体的设置如下所示。

例如我希望暴露的文件URL为webapp/WEB-INF/views/HelloWorld.jsp

<img src="https://cdn.jsdelivr.net/gh/WangMinan/Pics/image-20221120192212338.png" alt="image-20221120191410612"  />

那么需要在Controller中添加如下代码

```java
@Controller // 注意使用Controller而不是RestController 此处的字符串需要被springboot解析而不是作为json对象返回给前端
@RequestMapping("/view")
public class JspExportController {
    // 接下来是暴露jsp页面的接口 做页面跳转时请使用如下接口

    /**
     * 验证jsp页面是否能够正常访问
     * @return jsp页面
     */
    @RequestMapping("/hello") // 对应的 不需要加@ResponseBody注解 让springboot直接解析这个字符串
    public String hello(){
        return "/HelloWorld";
    }
    
    // ... 其他接口
}
```

在进行如上面的代码所示的配置之后，访问该JSP文件的路径将为两个`@RequestMapping`后面的路径拼接之后的结果。再加上用户ip与端口号。因此实际访问路径为http://localhost:8080/view/hello。

此时根据配置文件中的前后缀关系

```yaml
  spring:
    mvc:
      view:
        prefix: /WEB-INF/views
        suffix: .jsp
```

SpringBoot将会找到webapp/WEB-INF/views/HelloWold.jsp文件，实际上就是将prefix+return的字符串+suffix作为文件名进行拼接之后的结果。最终的访问结果如下图所示

![image-20221120193535936](https://cdn.jsdelivr.net/gh/WangMinan/Pics/image-20221120193535936.png)

对应的jsp文件如下所示

```jsp
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
</head>
<body>
    HelloWorld!
    <% System.out.println("helloWorld");%>
</body>
</html>
```

### 使用request.attributes的变量

如以下代码所示，如果后端在创建JSP页面时返回了一个List类型的变量

```java
@RequestMapping("/jstl-brand")
public String handleJstlBrand(HttpServletRequest request){
    List<Brand> brands = new ArrayList<Brand>();
    brands.add(new Brand(1,"三只松鼠","三只松鼠",100,"三只松鼠，好吃不上火",1));
    brands.add(new Brand(2,"优衣库","优衣库",200,"优衣库，服适人生",0));
    brands.add(new Brand(3,"小米","小米科技有限公司",1000,"为发烧而生",1));
    // 要把数据存到request域中
    request.setAttribute("brands",brands);
    // 转发到el.jsp
    return "/jstl-brand";
}
```

那么我们需要在body中加入如下代码来在JSP中访问brands对象

```jsp
<table border="1" cellspacing="0" width="800">
        <tr>
            <th>序号</th>
            <th>品牌名称</th>
            <th>企业名称</th>
            <th>排序</th>
            <th>品牌介绍</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        <jsp:useBean id="brands" scope="request" type="java.util.List"/>
        <c:forEach items="${brands}" var="brand">
            <tr align="=center">
                <%--实际上调用的是get方法 id->Id ->getId --%>
                <td>${brand.id}</td>
                <td>${brand.brandName}</td>
                <td>${brand.companyName}</td>
                <td>${brand.ordered}</td>
                <td>${brand.description}</td>
                <c:if test="${brand.status == 1}">
                    <td>启用</td>
                </c:if>
                <c:if test="${brand.status == 0}">
                    <td>禁用</td>
                </c:if>
                <td><a href="#">修改</a> <a href="#">删除</a></td>
            </tr>
        </c:forEach>
    </table>
```

其中`<tr/>`块包裹的是表头对象，之后通过`jsp:useBean`引入了该对象(但直接使用$引入应该也是可以的，大家可以尝试一下)

之后使用了JSTL标准库中的`<c:forEach/>`来逐行添加内容。由于项目中domain中的基类我已经全部添加了`@Data`注释，因此自动生成了get方法，请不用担心，直接调用有关属性即可。上述JSP代码中的启用与禁用的情况在本项目中不存在，逻辑删除字段会帮助我们直接规避这个问题。当然，最终使用数据进行建表时应当使用bootstrap中的表格组件。

在本次项目中，由于分页组件需要兼容管理端的原因，所有的数据都被封装在Map对象里。但实际的访问逻辑是一致的。以Book为例。访问书籍页面时调用的controller代码如下：

```java
@RequestMapping(value = "/books")
public String booksInit(HttpServletRequest request, HttpServletResponse response) throws IOException {
    checkSession(request, response);
    QueryInfo queryInfo = new QueryInfo();
    queryInfo.setQuery(MEANINGLESS_QUERY);
    queryInfo.setPagenum(MIN_PAGE_NUM);
    queryInfo.setPagesize(MAX_PAGE_SIZE);
    request.setAttribute("books", bookService.getAllBooks(queryInfo));
    request.setAttribute("assessments", assessmentService.getAllAssessments(queryInfo));
    return "/booksPage";
}
```

books对象与assessments对象均为map对象。则应当在JSP中使用如下代码进行访问：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table>
        <jsp:useBean id="books" scope="request" type="java.util.Map"/>
        <%
            System.out.println(books);
        %>
        <c:forEach var="book" items="${books.result}">
            <tr>
                <td>${book.id}</td>
                <td>${book.name}</td>
                <td>${book.isbn}</td>
                <td>${book.type}</td>
                <td>${book.publishyear}</td>
            </tr>
        </c:forEach>
</table>
</body>
</html>
```

记得引入jstl库，那么就呈现了Map中的具体内容。
