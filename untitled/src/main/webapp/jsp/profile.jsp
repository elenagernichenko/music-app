<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="com.itis.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>


<li><a href="/music" class="btn">Music</a></li>

<h2>Инфо профиля</h2>
<c:if test="${not empty user}">
    <h3>Профиль пользователя: ${user.username}</h3>
    <p>Email: ${user.email}</p>
    <p>Биография: ${user.bio}</p>
    <p>Музыкальные предпочтения: ${user.musicPreferences}</p>
    <!-- Дополнительные детали профиля -->
</c:if>

<h2>Обновление Профиля</h2>

<c:choose>
    <c:when test="${not empty user}">
        <form action="/profile" method="post">
            <div class="form-group">
                <label for="bio">Биография:</label>
                <textarea id="bio" name="bio"></textarea>
            </div>
            <div class="form-group">
                <label for="musicPreferences">Музыкальные Предпочтения:</label>
                <input type="text" id="musicPreferences" name="musicPreferences">
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password">
            </div>
            <button type="submit">Обновить Профиль</button>
        </form>
    </c:when>
    <c:otherwise>
        <p>Информация о пользователе не найдена.</p>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jsp" %>
