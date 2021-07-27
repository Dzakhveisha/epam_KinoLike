<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Films</title>
    <style>
        <%@include file="/WEB-INF/css/form.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp" />
<h2> Liked films </h2>
<c:if test="${not empty requestScope.films}">
    <c:forEach var="film"  items="${requestScope.films}">
        <div>${film.name}</div>
    </c:forEach>
</c:if>

</body>
<jsp:include page="constJsp/footer.jsp" />
</html>