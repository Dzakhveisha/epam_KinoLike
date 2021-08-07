<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="footer.">
<footer>
    <a href=${pageContext.request.contextPath}/controller?command=show_main"s>
        <img class = "logo2" src = "${pageContext.request.contextPath}/img/logo.png" alt = " logo ">
    </a>
    <ul class = "footerMenu">
        <li> <a href = ""> <fmt:message key="project"/> </a> </li>
        <li> <a href = ""> <fmt:message key="contacts"/>  </a></li>
        <li id = "name"><i> Kino-Like, 2021</i></li>
    </ul>
</footer>
</fmt:bundle>