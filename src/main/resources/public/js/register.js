window.onload = () => {
    let name = document.getElementById("name");
    let div_name = document.getElementById("div-name");
    let password = document.getElementById("password");
    let confirm_password = document.getElementById("confirm-password");
    let div_confirm_password = document.getElementById("div-confirm-password");
    let alert_name;
    let alert_confirm_password;
    let signup = document.getElementById("signup");
    signup.disabled = true;

    name.onchange = () => {
        if (name.value && name.value.length <= 0 || name.value.length > 20) {
            if (alert_name == null) {
                alert_name = document.createElement("div");
                alert_name.setAttribute("class", "alert alert-warning");
                alert_name.setAttribute("role", "alert");
                alert_name.innerHTML = "Username must be between 1 and 20 characters long.";
                div_name.appendChild(alert_name);
            }
        }
        else if (alert_name) {
            div_name.removeChild(alert_name);
            alert_name = null;
        }
    }
    confirm_password.onchange = () => {
        if (password.value && confirm_password.value && confirm_password.value != password.value) {
            if (!alert_confirm_password) {
                signup.disabled = true;
                alert_confirm_password = document.createElement("div");
                alert_confirm_password.setAttribute("class", "alert alert-warning");
                alert_confirm_password.setAttribute("role", "alert");
                alert_confirm_password.innerHTML = "Passwords don't match!";
                div_confirm_password.appendChild(alert_confirm_password);
            }
        }
        else {
            signup.disabled = false;
            if (alert_confirm_password) {
                div_confirm_password.removeChild(alert_confirm_password);
                alert_confirm_password = null;
            }
        }
    }

    password.onchange = confirm_password.onchange;
}