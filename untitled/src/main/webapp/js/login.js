function submitLogin(event) {
    event.preventDefault();

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) {
                window.location.href = '/profile';
            } else {
                // отображение сообщения об ошибке
                document.getElementById('loginError').style.display = 'block';
            }
        }
    }
    xhr.send("username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password));
}
