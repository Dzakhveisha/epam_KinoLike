<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="utf-8" %>
<html>
<head>
    <title>Review</title>
    <style>
        <%@include file="/WEB-INF/css/form.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp"/>
<div id="mainContent">
<c:choose>
    <c:when test="${not empty requestScope.error}">
        <p>  ERROR ! ${error} </p>
        <a href="${pageContext.request.contextPath}/controller?command=show_main"> на главную </a>
    </c:when>
    <c:otherwise>
    <h2> Оставте свой отзыв о фильме ${film} </h2>
    <form class="enterForm" action="${pageContext.request.contextPath}/controller?command=review&film=${film}"
          method="post">
        <label for="fieldValue"> Оценка </label><br>
        <input required type="number" max="10" id="fieldValue" name="value"><br>
        <label for="fieldText"> Коммеентарий </label><br>
        <textarea id="fieldText" rows="10" cols="45" name="text"></textarea>
        <input type="submit" value="OK">
    </form>
    </c:otherwise>
</c:choose>
</div>
</body>
<jsp:include page="constJsp/footer.jsp" />
</html>
