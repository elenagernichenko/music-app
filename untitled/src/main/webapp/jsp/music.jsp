<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="com.itis.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.itis.model.Playlist" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2>Инфо профиля</h2>
<c:if test="${not empty user}">
    <h3>Профиль пользователя: ${user.username}</h3>
</c:if>


<c:choose>
    <c:when test="${empty errorMessage}">
        <h2>Добавление трека</h2>
        <form action="/music" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="playlistName">Название плейлиста:</label>
                <input type="text" id="playlistName" name="playlistName">
            </div>

            <div class="form-group">
                <label for="playList">Выберите существующий плейлист:</label>
                <select id="playList" name="playList">
                    <option value="">Выберите плейлист</option> <!-- Пустой вариант -->
                    <% List<Playlist> playlists = (List<Playlist>) request.getAttribute("playList"); %>
                    <% for (Playlist playlist : playlists) { %>
                    <option value="<%= playlist.getId() %>"><%= playlist.getName() %></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <label for="name">Название трека:</label>
                <input type="text" id="name" name="name">
            </div>
            <div class="form-group">
                <label for="artist">Название артиста:</label>
                <input type="text" id="artist" name="artist">
            </div>
            <div class="form-group">
                <label for="album">Название альбома:</label>
                <input type="text" id="album" name="album">
            </div>
            <div class="form-group">
                <label for="file">Загрузить файл трека:</label>
                <input type="file" id="file" name="file">
            </div>
            <button type="submit">Добавить трек</button>
        </form>
    </c:when>
    <c:otherwise>
        <div class="error-message">${errorMessage}</div>
    </c:otherwise>
</c:choose>



<section class="music-news">
    <h2>PlayList</h2>
    <ul>
        <c:forEach var="play" items="${playList}">
            <li>
                <strong>Название плейлиста:  ${play.name}</strong>
            </li>
        </c:forEach>
    </ul>
</section>

<section class="popular-tracks">
    <h2>Tracks</h2>
    <ul>
        <c:forEach var="track" items="${music}">
            <li>
                <p>Название трека:  ${track.name}</p>
                <p>Название артиста:  ${track.artist}</p>
                <p>Название альбома:  ${track.album}</p>
                <p>Дата добавления:  ${track.year}/p>
                <p>Название файла:  ${track.filename}</p>
                <a href="/download?filename=${track.filename}">Скачать музыку</a>
            </li>
        </c:forEach>
    </ul>
</section>

<%@ include file="footer.jsp" %>
