<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        <%@include file="/WEB-INF/css/form.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp" />
<div id="mainContent">
    <h2> Введите данные о новом фильме: </h2>
    <c:choose>
        <c:when test="${not empty requestScope.error}">
            <p>  ERROR ! ${error} </p>
            <a href="${pageContext.request.contextPath}/controller?command=show_login"> Try again </a>
        </c:when>
        <c:otherwise>
            <form class="enterForm" action="${pageContext.request.contextPath}/controller?command=new_film" method="post">
                <label for="fieldName"> Название </label><br>
                <input required type="text" id="fieldName" name="name"><br>
                <label for="fieldYear"> Год выпуска </label><br>
                <input required type="number" max="2022" min="1895" id="fieldYear" name="year"><br>
                <label for="fieldCountry"> Страна </label><br>
                <input required type="text" id="fieldCountry" name="country"><br>
                <label for="fieldDescript"> Описание </label><br>
                <textarea required id="fieldDescript" name="descript"></textarea><br>
                <label for="fieldGenre"> Жанр </label><br>
                <select id="fieldGenre" name="genre">
                    <c:if test="${not empty requestScope.genres}">
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option>${genre.name()}</option>
                        </c:forEach>
                    </c:if>
                </select><br>
                <input type="file" id="poster" name="image">
                <input type="submit" value="OK">
            </form>
        </c:otherwise>
    </c:choose>
</div>
</body>
<jsp:include page="constJsp/footer.jsp" />
</html>
