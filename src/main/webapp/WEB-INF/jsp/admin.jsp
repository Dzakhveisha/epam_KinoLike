<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language != null ? sessionScope.language : 'ru'}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix="admin.">
<html>
<head>
    <title>Admin</title>
    <style>
        <%@include file="/WEB-INF/css/mainStyle.css" %>
        <%@include file="/WEB-INF/css/adminPage.css" %>
    </style>
</head>
<body>
<jsp:include page="constJsp/header.jsp"/>
<div id="mainContent">
    <div id="adminCont">
        <p> <fmt:message key="admin"/> <b>${admin.name}, ${admin.age}</b></p>
        <p> <fmt:message key="email"/> <b>${admin.email}</b></p>
    </div>
    <div id="usersCont">
        <h3 class="red"> <fmt:message key="users"/> </h3>
        <c:if test="${not empty requestScope.users}">
            <c:forEach var="user" items="${requestScope.users}">
                <hr>
                <div class="userItem">
                    <p><fmt:message key="login"/> <b>${user.name}</b></p>
                    <p><fmt:message key="age"/> ${user.age}</p>
                    <p><fmt:message key="email"/> ${user.email}</p>
                    <p><fmt:message key="role"/> ${user.role}</p>
                    <form action="${pageContext.request.contextPath}/controller?command=change_status&user=${user.name}"
                          method="post">
                        <fmt:message key="status"/>
                        <select required name="status">
                            <c:if test="${not empty requestScope.statuses}">
                                <c:forEach var="status" items="${requestScope.statuses}">
                                    <option>${status}</option>
                                </c:forEach>
                            </c:if>
                            <option selected="selected">${user.status}</option>
                        </select>
                        <input class="btnApply"  type="submit" value="<fmt:message key="changeStatus"/>" >
                    </form>
                </div>
            </c:forEach>
            <hr>
        </c:if>
    </div>
    <div id="filmsCont">
        <h3 class="red"> <fmt:message key="films"/></h3>
        <button id="addNewFilm" onclick="window.location.href = '${pageContext.request.contextPath}/controller?command=show_new_film'">
            <fmt:message key="newFilm"/>
        </button>
        <c:if test="${not empty requestScope.films}">
            <c:forEach var="film" items="${requestScope.films}">
                <hr>
                <div class="filmItem">
                    <form enctype="multipart/form-data" class="enterForm"
                          action="${pageContext.request.contextPath}/controller?command=change_film&film=${film.name}"
                          method="post">

                        <img src="${pageContext.request.contextPath}/images/${film.imagePath}" alt="постер"><br>

                        <div class="chooseFile">
                            <button type="button" class="btnFile" onclick="document.getElementById('poster${film.id}').click();">
                                <fmt:message key="chooseImg"/>
                            </button>
                            <input class="fileNameInput" readonly id="fileName${film.id}" name="fileName" value="${film.imagePath}"><br>
                            <input style="display:none" type="file" id="poster${film.id}" name=image value="${film.imagePath}"
                            onchange="document.getElementById('fileName${film.id}').value = document.getElementById('poster${film.id}').value;">
                        </div><br>


                        <label for="fieldName"> <fmt:message key="name"/> </label><br>
                        <input required type="text" id="fieldName" name="name" value="${film.name}"><br>
                        <label for="fieldYear"> <fmt:message key="year"/> </label><br>
                        <input required type="number" max="2022" min="1895" id="fieldYear" name="year"
                               value="${film.year}"><br>
                        <label for="fieldCountry"> <fmt:message key="country"/> </label><br>
                        <input required type="text" id="fieldCountry" name="country" value="${film.countryName}"><br>
                        <label for="fieldDescript"> <fmt:message key="description"/> </label><br>
                        <textarea rows="15" cols="90" required id="fieldDescript" name="descript">${film.description}</textarea><br>
                        <label for="fieldGenre"> <fmt:message key="genre"/> </label><br>
                        <select id="fieldGenre" name="genre">
                            <c:if test="${not empty requestScope.genres}">
                                <c:forEach var="genre" items="${requestScope.genres}">
                                    <option>${genre.name()}</option>
                                </c:forEach>
                            </c:if>
                            <option selected="selected">${film.genre}</option>
                        </select><br>
                        <br><input class="btnApply" type="submit" value="<fmt:message key="save"/>">
                    </form>
                    <button class="btnDelete" onclick="window.location.href ='${pageContext.request.contextPath}/controller?command=delete_film&film=${film.name}'">
                        <fmt:message key="delete"/>
                    </button>
                </div>
            </c:forEach>
            <hr>
        </c:if>
    </div>
</div>
</div>
<jsp:include page="constJsp/footer.jsp"/>
</body>
</html>
</fmt:bundle>
