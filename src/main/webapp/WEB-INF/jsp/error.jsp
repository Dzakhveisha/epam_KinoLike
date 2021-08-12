<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cust" uri="/WEB-INF/tld/customLib.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="error.">

    <html>
    <head>
        <title>Error</title>
        <style>
            <%@include file="/WEB-INF/css/mainStyle.css" %>
        </style>
    </head>
    <body>
    <jsp:include page="constJsp/header.jsp"/>
    <div id="mainContent">
        <c:choose>
            <c:when test="${not empty requestScope.error}">
                <cust:errorTag errorText="${requestScope.error}"
                               link="${pageContext.request.contextPath}/controller?command=show_login"/>
            </c:when>
            <c:otherwise>
                <h4><fmt:message key="generalError"/></h4>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="constJsp/footer.jsp"/>
    </body>
    </html>

</fmt:bundle>