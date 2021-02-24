<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${isCreate == true}">
                新建流程
            </c:when>
            <c:otherwise>
                修改流程
            </c:otherwise>
        </c:choose>
    </title>
    <script type="text/javascript" src="/js/jquery/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#submit").click(function () {
                let name = $("#name").val();
                let month = $("#month").val();
                let day = $("#day").val();
                let weekDay = $("#weekDay").val();
                let hour = $("#hour").val();
                let minute = $("#minute").val();
                let dayInterval = $("#dayInterval").val();
                let hourInterval = $("#hourInterval").val();
                let minuteInterval = $("#minuteInterval").val();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    <c:choose>
                    <c:when test="${isCreate == true}">
                    url: "/procedure/edit",
                    </c:when>
                    <c:otherwise>
                    url: "/procedure/edit/${procedureId}",
                    </c:otherwise>
                    </c:choose>
                    data: {
                        <c:if test="${isCreate != true}">
                        "_method": "put",
                        </c:if>
                        "name": name,
                        "month": month,
                        "day": day,
                        "weekDay": weekDay,
                        "hour": hour,
                        "minute": minute,
                        "dayInterval": dayInterval,
                        "hourInterval": hourInterval,
                        "minuteInterval": minuteInterval
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
    <form id="update">
        <p>流程名<input id="name" type="text" value="${name}"></p>
        <p>启动时间</p>
        <p>month<input id="month" type="text" value="${month}"></p>
        <p>day<input id="day" type="text" value="${day}"></p>
        <p>weekDay<input id="weekDay" type="text" value="${weekDay}"></p>
        <p>hour<input id="hour" type="text" value="${hour}"></p>
        <p>minute<input id="minute" type="text" value="${minute}"></p>
        <p>dayInterval<input id="dayInterval" type="text" value="${dayInterval}"></p>
        <p>hourInterval<input id="hourInterval" type="text" value="${hourInterval}"></p>
        <p>minuteInterval<input id="minuteInterval" type="text" value="${minuteInterval}"></p>
        <p><input type="button" value="提交" id="submit"></p>
    </form>
</body>
</html>
