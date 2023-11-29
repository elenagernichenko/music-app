<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jsp/header.jsp" %>

<div class="content">
    <h1>Добро пожаловать в Music App</h1>

    <p>Откройте для себя мир музыки - присоединяйтесь к нам сегодня!</p>
    <a href="/register" class="btn">Регистрация</a>
    <a href="/login" class="btn">Вход</a>

    <section class="popular-tracks">
        <h2>Популярные Треки</h2>
        <ul>
            <c:forEach var="track" items="${popularTracks}">
                <li>Автор: ${track.artist} Название трека: ${track.name} - (${track.album}, ${track.year})</li>
            </c:forEach>
        </ul>
    </section>

    <section class="music-news">
        <h2>Музыкальные Новости</h2>
        <ul>
            <c:forEach var="news" items="${musicNews}">
                <li>
                    <strong>${news.title}</strong>
                    <p>${news.content}</p>
                    <p><small>${news.date}</small></p>
                </li>
            </c:forEach>
        </ul>
    </section>
</div>

<%@ include file="/jsp/footer.jsp" %>
