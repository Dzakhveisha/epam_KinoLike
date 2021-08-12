<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<header>
<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="header.">

    <ul class="localization">
        <li><a href="${pageContext.request.contextPath}/controller?${pageContext.request.queryString}&lang=ru">RU</a></li>
        <li><a href="${pageContext.request.contextPath}/controller?${pageContext.request.queryString}&lang=en">EN</a></li>
        <li><a href="${pageContext.request.contextPath}/controller?${pageContext.request.queryString}&lang=be">BE</a></li>
    </ul>
    <a href=${pageContext.request.contextPath}/controller?command=show_main">
        <img class="logo" src="${pageContext.request.contextPath}/img/logo.png">
    </a>
    <ul class="menu">
        <li>
            <form action="${pageContext.request.contextPath}/controller?command=show_main&sort=search" method="post">
                <input class="search" name="search" placeholder=<fmt:message key="search"/> type="search"/>
                <button><img src="${pageContext.request.contextPath}/img/search.png" height="25"> </button>
            </form>

        </li>
        <li><a href="${pageContext.request.contextPath}/controller?command=show_main&sort=new"> <fmt:message key="new"/></a></li>
        <li><a href="${pageContext.request.contextPath}/controller?command=show_main&sort=popular"> <fmt:message key="popular"/> </a></li>

        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <li><a href="${pageContext.request.contextPath}/controller?command=show_register"> <fmt:message key="register"/> </a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/controller?command=show_profile"> <fmt:message key="profile"/> </a></li>
            </c:otherwise>
        </c:choose>

        <c:if test="${sessionScope.role == 'ADMIN'}">
            <li><a href="${pageContext.request.contextPath}/controller?command=show_admin"> <fmt:message key="admin"/> </a></li>
        </c:if>

        <li><c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/controller?command=show_login"> <fmt:message key="login"/> </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/controller?command=logout"> <fmt:message key="logout"/> </a>
            </c:otherwise>
        </c:choose></li>
    </ul>
</header>
</fmt:bundle>