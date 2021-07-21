<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <style>
        <%@include file="/WEB-INF/css/mainStyle.css" %>
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

        <c:if test="${not empty sessionScope.role.equals(UserRole.ADMIN)}">
            <li><a href="${pageContext.request.contextPath}/controller?command=show_admin"> ADMIN_PAGE </a></li>
        </c:if>

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

    Something went wrong!

</body>
</html>
