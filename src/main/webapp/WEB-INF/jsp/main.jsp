<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ page pageEncoding="utf-8" %>
<html>
<head>
    <title>Main</title>
    <style>
        <%@include file="/WEB-INF/css/mainStyle.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp"/>
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
                    <img src="${pageContext.request.contextPath}/images/${film.imagePath}" alt="постер">
                    <div class="textDescription">
                        <h2>${film.name}</h2>
                        <a href="${pageContext.request.contextPath}/controller?command=show_main&genre=${film.genre.name()}">${film.genre.name()}</a>
                        <h3>${film.year}, <i> ${film.country} </i></h3>
                        <p> ${film.description} </p>
                        <h4> ${film.rating} </h4>
                    </div>
                    <div class="buttons">
                        <c:choose>
                            <c:when test="${ requestScope.filmReviews.get(film.id) != null}">
                                <div class="review">
                                    <p> Ваш отзыв:</p>
                                    <h3> ${filmReviews.get(film.id).value}</h3>
                                    <q>${filmReviews.get(film.id).text}</q>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <button class="setReview" type="button"
                                        onclick=" window.location.href = '${pageContext.request.contextPath}/controller?command=show_review&film=${film.name}'">
                                    Оставить отзыв
                                </button>
                            </c:otherwise>
                        </c:choose>
                        <button class="setLike" type="button" onclick="
                                const url = '/setLike.php?f={filmName}';
                                fetch(url).then(function (response) {
                                return response.text();})
                                .then(function (src) {
                                    document.getElementById('{filmName}').src = src;
                            });
                            ">
                            <img src="${pageContext.request.contextPath}/img/notLike.png" id="{filmName}">
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
<jsp:include page="constJsp/footer.jsp"/>
</body>
</html>
