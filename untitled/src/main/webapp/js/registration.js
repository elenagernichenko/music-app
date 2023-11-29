function submitRegistration(event) {
    event.preventDefault(); // предотвращаю стандартную отправку формы

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var email = document.getElementById('email').value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/register", false);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            window.location.href = '/index';
        }
    }
    xhr.send("username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password) + "&email=" + encodeURIComponent(email));
}
