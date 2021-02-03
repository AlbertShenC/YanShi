<%--
  Created by IntelliJ IDEA.
  User: shen_
  Date: 2021/2/1
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>按页查询结果</title>
</head>
<body>
    <h3>按页查询结果</h3>

    <c:forEach items="${events}" var="event">
        ${event.id} | ${event.generatedTime} | ${event.successful} | ${event.type} |
        ${event.overview} | ${event.header} | ${event.body}
    </c:forEach>

</body>
</html>
