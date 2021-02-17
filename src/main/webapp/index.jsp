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
    <h3>测试首页</h3>

    <a href="/event/findByPage">按页查询事件</a>
    <br>
    <a href="/task/execute?taskId=1">测试http请求：百度搜索Albert</a>
    <br>
    <a href="/task/execute?taskId=2">测试http请求：博客园首页</a>
    <br>
    <a href="/task/execute?taskId=3">测试字符串解析请求：Xpath</a>
    <br>
    <a href="/task/execute?taskId=4">测试生成rss</a>
    <br>
    <a href="/task/execute?taskId=5">测试网页生成</a>
    <br>
    <a href="/customPage?id=4">查看生成xml（仅id=4）</a>

</body>
</html>
