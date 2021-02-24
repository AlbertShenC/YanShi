<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../header.jsp"%>
</head>
<body>
    <table border="1">
        <tr>
            <th>名称</th>
            <th>最近一次执行时刻</th>
            <th>启动间隔</th>
        </tr>
        <c:forEach items="${procedureList}" var="item">
        <tr>
            <td><a href="/procedure/edit/${item.getId()}">${item.getName()}</a></td>
            <td>${item.getLastExecuteDateTime()}</td>
            <td>${item.getInitTime().getMonth()}&${item.getInitTime().getDay()}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
