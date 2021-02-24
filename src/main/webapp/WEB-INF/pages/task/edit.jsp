<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../header.jsp"%>
    <script type="text/javascript">
        $(function () {
            $("#submit").click(function () {
                let name = $("#name").val();
                let type = parseInt($("#type").val());
                let procedureId = $("#procedureId").val();
                let inputTaskId = $("#inputTaskId").val();
                let inputEventProperty = $("#inputEventProperty").val();
                let overview = $("#overview").val();
                let header = $("#header").val();
                let body = $("#body").val();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    <c:choose>
                    <c:when test="${isCreate == true}">
                    url: "/task/edit",
                    </c:when>
                    <c:otherwise>
                    url: "/task/edit/${taskId}",
                    </c:otherwise>
                    </c:choose>
                    data: {
                        <c:if test="${isCreate != true}">
                        "_method": "put",
                        </c:if>
                        "name": name,
                        "type": type,
                        "procedureId": procedureId,
                        "inputTaskId": inputTaskId,
                        "inputEventProperty": inputEventProperty,
                        "request.overview": overview,
                        "request.header": header,
                        "request.body": body
                    },
                    success: function (data) {
                        alert(data['message']);
                    },
                });
            })
        })
    </script>
</head>
<body>
    <form>
        <p>任务种类：
            <select id="type">
            <c:forEach items="${enumList}" var="item">
                <c:choose>
                    <c:when test="${item == type}">
                        <option value="${item.getValue()}" selected>
                    </c:when>
                    <c:otherwise>
                        <option value="${item.getValue()}">
                    </c:otherwise>
                </c:choose>
                    ${item.toString()}
                </option>
            </c:forEach>
            </select>
        </p>
        <p>任务名<input id="name" type="text" value="${name}"></p>
        <p>为流程<input id="procedureId" type="text" value="${procedureId}">的起始任务</p>
        <p>前置任务<input id="inputTaskId" type="text" value="${inputTaskId}"></p>
        <p>前置任务的<input id="inputEventProperty" type="text" value="${inputEventProperty}">部分</p>
        <p>Overview<textarea id="overview" cols="60" rows="10">${requestOverview}</textarea></p>
        <p>Header<textarea id="header" cols="60" rows="10">${requestHeader}</textarea></p>
        <p>Body<textarea id="body" cols="60" rows="10">${requestBody}</textarea></p>
        <p><input type="button" value="提交" id="submit"></p>
    </form>
</body>
</html>
