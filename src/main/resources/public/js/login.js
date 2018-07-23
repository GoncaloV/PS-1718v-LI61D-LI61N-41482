$(document).ready(function () {
    let name = $("#name");
    name.valid = false;
    const regExp = /^([0-9]|[a-z])+([0-9a-z]+)$/i;

    let password = $("#password");
    password.valid = false;

    let alert_name = $("#alert_name");
    let alert_password = $("#alert_password");

    // Alerts the user if the username doesn't conform to the imposed limitations.
    // The username has to be alphanumeric and between 6 and 20 characters long.
    name.change(function () {
        if (name.val() && name.val().length < 6 || name.val().length > 20) {
            name.valid = false;
            alert_name.text("Username must be between 6 and 20 characters long.").show();
        } else if (!name.val().match(regExp)) {
            name.valid = false;
            alert_name.text("Username must be alphanumeric (can only contain numbers and/or letters).").show();
        }
        else {
            name.valid = true;
            alert_name.hide();
        }
    });

    // Alerts the user if the password doesn't conform to the imposed limitations.
    // Passwords must be between 8 and 128 characters long.
    password.change(function () {
        if (password.val().length < 8 || password.val().length > 128) {
            password.valid = false;
            alert_password.show();
        }
        else {
            password.valid = true;
            alert_password.hide();
        }
    });

    $("#login_btn").click(function (e) {
        if (name.valid && password.valid) {
            e.preventDefault();
            $("#login_btn").hide();
            $("#loading").show();
            $.post({
                url: 'login',
                data: $("#login_form").serialize(),
                timeout: 10000
            }).done(function(){
                window.location.replace("/");
            }
            );
        }
    });
});