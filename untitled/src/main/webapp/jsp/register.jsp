<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp" %>

<h2>Регистрация</h2>
<form id="registrationForm" onsubmit="submitRegistration(event)">
    <div class="form-group">
        <label for="username">Логин:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <button type="submit">Зарегистрироваться</button>
</form>

<script src="/js/registration.js"></script> <!-- Путь к вашему JavaScript файлу -->

<%@ include file="footer.jsp" %>
