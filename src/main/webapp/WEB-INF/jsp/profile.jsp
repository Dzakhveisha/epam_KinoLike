<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="profile.">
    <html>
    <head>
        <title>My Profile</title>
        <style>
            <%@include file="/WEB-INF/css/mainStyle.css" %>
            <%@include file="/WEB-INF/css/profile.css" %>
        </style>
    </head>
    <body>
    <jsp:include page="constJsp/header.jsp"/>
    <div id="mainContent">

        <div id="profile">
            <h2><fmt:message key="profile"/></h2>
            <div id="userInf">
                <p><fmt:message key="login"/>: <b>${requestScope.user.name}</b></p>
                <p><fmt:message key="age"/>: ${requestScope.user.age}</p>
                <p><fmt:message key="email"/>: ${requestScope.user.email}</p>
                <p><fmt:message key="role"/>:
                    <fmt:bundle basename="pagecontent" prefix="role.">
                        <fmt:message key="${requestScope.user.role}"/>
                    </fmt:bundle></p>
                <p><fmt:message key="status"/>:
                <fmt:bundle basename="pagecontent" prefix="status.">
                     <fmt:message key="${requestScope.user.status}"/>
                </fmt:bundle>
                </p>
            </div>
        </div>
        <div id="reviews">
            <h2><fmt:message key="reviews"/>: </h2>
            <c:if test="${not empty requestScope.reviews}">
                <c:forEach var="film" items="${requestScope.films}">
                    <h3 class="filmName"> ${film.name} </h3>
                    <div class="review">
                        <h3> ${requestScope.reviews.get(film).value}</h3>
                        <q>${requestScope.reviews.get(film).text}</q>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
    </body>
    <jsp:include page="constJsp/footer.jsp"/>
    </html>
</fmt:bundle>