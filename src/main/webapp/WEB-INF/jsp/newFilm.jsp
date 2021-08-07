<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="newFilm.">
    <html>
    <head>
        <title>Login</title>
        <style>
            <%@include file="/WEB-INF/css/form.css" %>
        </style>
    </head>
    <body>
    <jsp:include page="constJsp/header.jsp"/>
    <div id="mainContent">
        <h2><fmt:message key="enter"/></h2>
        <c:choose>
            <c:when test="${not empty requestScope.error}">
                <p><fmt:message key="error"/> ${error} </p>
                <a href="${pageContext.request.contextPath}/controller?command=show_login"> Try again </a>
            </c:when>
            <c:otherwise>
                <form enctype="multipart/form-data" class="enterForm"
                      action="${pageContext.request.contextPath}/controller?command=new_film" method="post">
                    <label for="fieldName"> <fmt:message key="name"/> </label><br>
                    <input required type="text" id="fieldName" name="name"><br>
                    <label for="fieldYear"> <fmt:message key="year"/> </label><br>
                    <input required type="number" max="2022" min="1895" id="fieldYear" name="year"><br>
                    <label for="fieldCountry"> <fmt:message key="country"/> </label><br>
                    <input required type="text" id="fieldCountry" name="country"><br>
                    <label for="fieldDescript"> <fmt:message key="description"/> </label><br>
                    <textarea required id="fieldDescript" name="descript"></textarea><br>
                    <label for="fieldGenre"> <fmt:message key="genre"/> </label><br>
                    <select id="fieldGenre" name="genre">
                        <c:if test="${not empty requestScope.genres}">
                            <c:forEach var="genre" items="${requestScope.genres}">
                                <option>${genre.name()}</option>
                            </c:forEach>
                        </c:if>
                    </select><br>
                    <div class="chooseFile">
                        <button type="button" class="btnFile" onclick="document.getElementById('poster').click()">
                            <fmt:message key="chooseImg"/>
                        </button>
                        <input class="fileNameInput" readonly id="fileName" name="fileName"><br>
                        <input style="display:none" type="file" id="poster" name=image
                               onchange="document.getElementById('fileName').value = document.getElementById('poster').value">
                    </div>
                    <input type="submit" value="OK">
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    </body>
    <jsp:include page="constJsp/footer.jsp"/>
    </html>
</fmt:bundle>