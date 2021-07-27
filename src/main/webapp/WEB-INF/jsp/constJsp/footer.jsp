<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<footer>
    <a href=${pageContext.request.contextPath}/controller?command=show_main"s>
        <img class = "logo2" src = "${pageContext.request.contextPath}/img/logo.png" alt = " logo ">
    </a>
    <ul class = "footerMenu">
        <li> <a href = ""> О проекте </a> </li>
        <li> <a href = ""> Контакты </a></li>
        <li id = "name"><i> Kino-Like, 2021</i></li>
    </ul>
</footer>
