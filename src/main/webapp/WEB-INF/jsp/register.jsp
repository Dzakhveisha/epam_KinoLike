<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="register.">

<html>
<head>
    <title>Register</title>
    <style>
        <%@include file="/WEB-INF/css/form.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp" />
<div id="mainContent">
    <h2><fmt:message key="register"/> </h2>
<c:choose>
    <c:when test="${not empty requestScope.error}">
        <p> <fmt:message key="error"/>  ${error} </p>
        <a href="${pageContext.request.contextPath}/controller?command=show_register"> <fmt:message key="tryAgain"/> </a>
    </c:when>
    <c:otherwise>
        <form class="registerForm" action="${pageContext.request.contextPath}/controller?command=register"
              method="post">
            <label for="fieldLogin"> <fmt:message key="login"/> </label><br>
            <input required type="text" id="fieldLogin" name="login"><br>
            <label for="fieldPassword"> <fmt:message key="password"/> </label><br>
            <input required type="password" id="fieldPassword" name="password"><br>
            <label for="fieldAge"> <fmt:message key="age"/> </label><br>
            <input required type="number" id="fieldAge" name="age"><br>
            <label for="fieldMail"> <fmt:message key="email"/> </label><br>
            <input required type="email" id="fieldMail" name="mail"><br>
            <input type="submit" value="OK">
        </form>
    </c:otherwise>
</c:choose>
</div>
</body>
<jsp:include page="constJsp/footer.jsp" />
</html>
</fmt:bundle>