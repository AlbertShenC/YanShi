<%--
  Created by IntelliJ IDEA.
  User: shen_
  Date: 2021/1/31
  Time: 1:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="account/findAll">测试SpringMVC</a>

<h3>测试保存</h3>

<form action="/account/save" method="post">
    姓名：<input type="text" name="name"><br>
    金额：<input type="text" name="money"><br>
    <input type="submit" value="保存"><br>
</form>

<a href="/event/findByPage">按页查询事件</a>

</body>
</html>
