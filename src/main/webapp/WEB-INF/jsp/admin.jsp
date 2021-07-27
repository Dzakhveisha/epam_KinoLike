<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <style>
        <%@include file="/WEB-INF/css/mainStyle.css" %>
        <%@include file="/WEB-INF/css/adminPage.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp"/>
<div id="mainContent">
    <div id="adminCont">
        <p> Администратор: <b>${admin.name}, ${admin.age}</b></p>
        <p> e-mail: <b>${admin.email}</b></p>
    </div>
    <div id="usersCont">
        <h3 class="red"> Все пользователи:</h3>
        <c:if test="${not empty requestScope.users}">
            <c:forEach var="user" items="${requestScope.users}">
                <hr>
                <div class="userItem">
                    <p>Логин: <b>${user.name}</b></p>
                    <p>Возраст: ${user.age}</p>
                    <p>Статус: ${user.role}, ${user.status}</p>
                    <p>email: ${user.email}</p>
                    <button> изменить статус </button>
                </div>
            </c:forEach>
            <hr>
        </c:if>
    </div>
    <div id="filmsCont">
        <h3 class="red"> Все фильмы:</h3>
        <button onclick="window.location.href = '${pageContext.request.contextPath}/controller?command=show_new_film'"> Добавить новый фильм </button>
        <c:if test="${not empty requestScope.films}">
            <c:forEach var="film" items="${requestScope.films}">
                <hr>
                <div class="filmItem">
                    <h2>${film.name}</h2>
                    <h3> ${film.genre.name()}</h3>
                    <h3>${film.year}, <i> ${film.country} </i></h3>
                    <p> ${film.description} </p>
                    <h4> ${film.rating} </h4>
                    <button> изменить </button>
                    <button> удалить </button>
                </div>
            </c:forEach>
            <hr>
        </c:if>
    </div>
</div>
<jsp:include page="constJsp/footer.jsp" />
</body>
</html>
