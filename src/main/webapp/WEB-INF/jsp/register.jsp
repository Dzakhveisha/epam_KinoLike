<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style>
        <%@include file="/WEB-INF/css/form.css" %>
    </style>
</head>
<body>
<header>
    <a href=${pageContext.request.contextPath}/controller?command=show_main">
        <img class="logo" src="../../img/logo.png" alt=" logo ">
    </a>
    <ul class="menu">
        <li>
            <form action="" method="get">
                <input class="search" name="s" placeholder="Искать здесь..." type="search">
            </form>

        </li>
        <li><a href="${pageContext.request.contextPath}/controller?command=show_main&sort=new"> Новинки </a></li>
        <li><a href="${pageContext.request.contextPath}/controller?command=show_main&sort=popular"> Популярное </a></li>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/controller?command=show_login"> Любимое </a>
                <li><a href="${pageContext.request.contextPath}/controller?command=show_register"> Зарегаться </a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/controller?command=show_likes"> Любимое </a></li>
            </c:otherwise>
        </c:choose>
        <li><c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/controller?command=show_login">LOGIN</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/controller?command=logout">LOGOUT</a>
            </c:otherwise>
        </c:choose></li>
    </ul>
</header>
<div id="mainContent">
    <h2> Регистрация </h2>
<c:choose>
    <c:when test="${not empty requestScope.error}">
        <p> ERROR ! ${error} </p>
        <a href="${pageContext.request.contextPath}/controller?command=show_register"> Try again </a>
    </c:when>
    <c:otherwise>
        <form class="registerForm" action="${pageContext.request.contextPath}/controller?command=register"
              method="post">
            <label for="fieldLogin"> Логин </label><br>
            <input required type="text" id="fieldLogin" name="login"><br>
            <label for="fieldPassword"> Пароль </label><br>
            <input required type="password" id="fieldPassword" name="password"><br>
            <label for="fieldAge"> Возраст </label><br>
            <input required type="text" id="fieldAge" name="age"><br>
            <label for="fieldMail"> E-mail </label><br>
            <input required type="email" id="fieldMail" name="mail"><br>
            <input type="submit" value="OK">
        </form>
    </c:otherwise>
</c:choose>
</div>
</body>
</html>
