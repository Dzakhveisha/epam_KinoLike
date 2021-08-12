<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="login.">

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
    <h2> <fmt:message key="enter"/> </h2>
        <form class="enterForm" action="${pageContext.request.contextPath}/controller?command=login" method="post">
            <label for="fieldLogin"> <fmt:message key="login"/> </label><br>
            <input required type="text" id="fieldLogin" name="login"><br>
            <label for="fieldPassword"><fmt:message key="password"/>  </label><br>
            <input required type="password" id="fieldPassword" name="password"><br>
            <input type="submit" value="OK">
        </form>
</div>
</body>
<jsp:include page="constJsp/footer.jsp" />
</html>
</fmt:bundle>