<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
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
<div id=mainContent>
    <ul id="filtres">
        <li><a href="${pageContext.request.contextPath}/controller?command=show_main">all</a></li>
        <c:if test="${not empty requestScope.genres}">
            <c:forEach var="genre" items="${requestScope.genres}">
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=show_main&genre=${genre.name()}">${genre.name()}</a>
                </li>
            </c:forEach>
        </c:if>
    </ul>
    <div id="movies">
        <c:if test="${not empty requestScope.films}">
            <c:forEach var="film" items="${requestScope.films}">
                <div class="movieItem">
                    <img src="pictureLink" alt="постер">
                    <div class="textDescription">
                        <h2>${film.name}</h2>
                        <a href="${pageContext.request.contextPath}/controller?command=show_main&genre=${film.genre.name()}">${film.genre.name()}</a>
                        <h3>${film.year}, <i> ${film.country} </i></h3>
                        <p> ${film.description} </p>
                        <h4> ${film.rating} </h4>

                        <button type="button"
                                style="padding: 0;border: none;font: inherit;color: inherit;background-color: transparent; cursor: pointer;"
                                onclick=" const url = '/setLike.php?f={filmName}';
                fetch(url).then(function (response) {
                     return response.text();})
                        .then(function (src) {
                            document.getElementById('{filmName}').src = src;
                });">
                            <img src="{likeLink}" id="{filmName}"
                                 style=" height : 45px; width : 45px;">
                        </button>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty requestScope.films}">
            <p> Sorry, we haven't got movies in this category :( </p>
        </c:if>
    </div>
</div>
</body>
</html>
