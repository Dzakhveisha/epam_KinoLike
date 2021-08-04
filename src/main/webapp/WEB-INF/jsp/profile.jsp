<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Profile</title>
    <style>
        <%@include file="/WEB-INF/css/mainStyle.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp"/>
<div id="mainContent">
    <div id="userInf">
        <div class="userItem">
            <p>Логин: <b>${requestScope.user.name}</b></p>
            <p>Возраст: ${requestScope.user.age}</p>
            <p>email: ${requestScope.user.email}</p>
            <p>Роль: ${requestScope.user.role}</p>
            <p>Статус: ${requestScope.user.status}</p>
        </div>
    </div>
    <h2> Liked films </h2>
    <c:if test="${not empty requestScope.films}">
        <c:forEach var="film" items="${requestScope.films}">
            <img src="${pageContext.request.contextPath}/images/${film.imagePath}" alt="постер">
            <div>${film.name}</div>
        </c:forEach>
    </c:if>
</div>
</body>
<jsp:include page="constJsp/footer.jsp"/>
</html>