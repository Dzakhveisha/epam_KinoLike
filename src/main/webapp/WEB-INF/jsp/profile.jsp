<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Profile</title>
    <style>
        <%@include file="/WEB-INF/css/mainStyle.css" %>
        <%@include file="/WEB-INF/css/profile.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp"/>
<div id="mainContent">

    <div id="profile">
        <h2> Профиль </h2>
        <div id="userInf">
            <p>Логин: <b>${requestScope.user.name}</b></p>
            <p>Возраст: ${requestScope.user.age}</p>
            <p>email: ${requestScope.user.email}</p>
            <p>Роль: ${requestScope.user.role}</p>
            <p>Статус: ${requestScope.user.status}</p>
        </div>
    </div>
    <div id="reviews">
        <h2> Мои отзывы </h2>
        <c:if test="${not empty requestScope.reviews}">
            <c:forEach var="film" items="${requestScope.films}">
                <h3 class="filmName"> ${film.name} </h3>
                <div class="review">
                    <h3> ${requestScope.reviews.get(film).value}</h3>
                    <q>${requestScope.reviews.get(film).text}</q>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
<jsp:include page="constJsp/footer.jsp"/>
</html>