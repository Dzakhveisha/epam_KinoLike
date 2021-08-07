<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="newReview.">
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
        <p><fmt:message key="error"/> ${error} </p>
        <a href="${pageContext.request.contextPath}/controller?command=show_main"> <fmt:message key="back"/> </a>
    </c:when>
    <c:otherwise>
    <h2> <fmt:message key="giveReview" /> ${film} </h2>
    <form class="enterForm" action="${pageContext.request.contextPath}/controller?command=review&film=${film}"
          method="post">
        <label for="fieldValue"> <fmt:message key="rating"/> </label><br>
        <input required type="number" max="10" id="fieldValue" name="value"><br>
        <label for="fieldText"> <fmt:message key="comment"/> </label><br>
        <textarea id="fieldText" rows="10" cols="45" name="text"></textarea><br>
        <input type="submit" value="OK">
    </form>
    </c:otherwise>
</c:choose>
</div>
</body>
<jsp:include page="constJsp/footer.jsp" />
</html>
</fmt:bundle>