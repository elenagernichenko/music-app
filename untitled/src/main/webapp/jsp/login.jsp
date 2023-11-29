<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp" %>

<h2>Авторизация</h2>

<div id="loginError" class="error-message" style="display: none;">Неверный логин или пароль.</div>

<form id="loginForm" onsubmit="submitLogin(event)">
    <div class="form-group">
        <label for="username">Логин:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Войти</button>
</form>

<script src="/js/login.js"></script> <!-- Путь к вашему JavaScript файлу -->

<%@ include file="footer.jsp" %>
