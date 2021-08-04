<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <p> Администратор: <b>${admin.name}, ${admin.age}</b></p>
        <p> e-mail: <b>${admin.email}</b></p>
    </div>
    <div id="usersCont">
        <h3 class="red"> Все пользователи:</h3>
        <c:if test="${not empty requestScope.users}">
            <c:forEach var="user" items="${requestScope.users}">
                <hr>
                <div class="userItem">
                    <p>Логин: <b>${user.name}</b></p>
                    <p>Возраст: ${user.age}</p>
                    <p>email: ${user.email}</p>
                    <p>Роль: ${user.role}</p>
                    <form action="${pageContext.request.contextPath}/controller?command=change_status&user=${user.name}"
                          method="post">
                        Статус:
                        <select required name="status">
                            <c:if test="${not empty requestScope.statuses}">
                                <c:forEach var="status" items="${requestScope.statuses}">
                                    <option>${status}</option>
                                </c:forEach>
                            </c:if>
                            <option selected="selected">${user.status}</option>
                        </select>
                        <input class="btnApply"  type="submit" value="изменить статус">
                    </form>
                </div>
            </c:forEach>
            <hr>
        </c:if>
    </div>
    <div id="filmsCont">
        <h3 class="red"> Все фильмы:</h3>
        <button id="addNewFilm" onclick="window.location.href = '${pageContext.request.contextPath}/controller?command=show_new_film'">
            Добавить новый фильм
        </button>
        <c:if test="${not empty requestScope.films}">
            <c:forEach var="film" items="${requestScope.films}">
                <hr>
                <div class="filmItem">
                    <form enctype="multipart/form-data" class="enterForm"
                          action="${pageContext.request.contextPath}/controller?command=change_film&film=${film.name}"
                          method="post">
                        <img src="${pageContext.request.contextPath}/images/${film.imagePath}" alt="постер"><br>
                        <input type="file" id="poster" name=image value="${film.imagePath}"
                               onchange="document.getElementById('fileName').value = document.getElementById('poster').value"><br>
                        <input type="hidden" id="fileName" name="fileName" value="${film.imagePath}"><br>
                        <label for="fieldName"> Название: </label><br>
                        <input required type="text" id="fieldName" name="name" value="${film.name}"><br>
                        <label for="fieldYear"> Год выпуска: </label><br>
                        <input required type="number" max="2022" min="1895" id="fieldYear" name="year"
                               value="${film.year}"><br>
                        <label for="fieldCountry"> Страна: </label><br>
                        <input required type="text" id="fieldCountry" name="country" value="${film.country}"><br>
                        <label for="fieldDescript"> Описание: </label><br>
                        <textarea rows="15" cols="90" required id="fieldDescript" name="descript">${film.description}</textarea><br>
                        <label for="fieldGenre"> Жанр: </label><br>
                        <select id="fieldGenre" name="genre">
                            <c:if test="${not empty requestScope.genres}">
                                <c:forEach var="genre" items="${requestScope.genres}">
                                    <option>${genre.name()}</option>
                                </c:forEach>
                            </c:if>
                            <option selected="selected">${film.genre}</option>
                        </select><br>
                        <br><input class="btnApply"  type="submit" value="сохранить изменения">
                    </form>
                    <button class="btnDelete" onclick="window.location.href = '${pageContext.request.contextPath}/controller?command=delete_film&film=${film.name}'">
                        удалить
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
