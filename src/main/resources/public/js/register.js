$(document).ready(function () {
    const name = $("#name");
    const alert_name = $("#alert_name");
    const password = $("#password");
    const confirm_password = $("#confirm_password");
    const alert_confirm_password = $("#alert_confirm_password");

    // Alerts the user if the username doesn't conform to the imposed limitations.
    // The username has to be between 1 and 20 characters long.
    name.change(function () {
        if (name.val() && name.val().length <= 0 || name.val().length > 20) {
            alert_name.show();
        }
        else {
            alert_name.hide();
        }
    });

    // Alerts the user if the password and password confirmation contain different strings.
    const differentPasswordsAlert = function () {
        if (password.val() && confirm_password.val() && confirm_password.val() != password.val()) {
            alert_confirm_password.show();
        }
        else {
            alert_confirm_password.hide();
        }
    }

    password.change(differentPasswordsAlert);
    confirm_password.change(differentPasswordsAlert);

    // Signup button executes an AJAX post request with form data to the url /register.
    $("#signup").click(function (e) {
        if (name.val() && password.val() && confirm_password.val()) {
            e.preventDefault();
            $("#signup").hide();
            $("#loading").text(" Registering...").show();
            $.post({
                url: 'register',
                data: $("#register").serialize(),
            }).done(function (data) {
                if (data) {
                    $("#alert_existing_name").text(`User "${data.name}" already exists!`).show();
                    $("#loading").hide();
                    $("#signup").show();
                }
                else {
                    $("#loading").text(" Logging in...");
                    $("#login_name").val(name.val());
                    $("#login_pass").val(password.val());
                    $.post({
                        url: 'login',
                        data: $("#login").serialize()
                    }).done(function(){
                        window.location.replace("/");
                    });
                }
            });
        }
    });
});