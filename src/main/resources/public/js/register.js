$(document).ready(function () {
    const name = $("#name");
    const alert_name = $("#alert_name");
    name.valid = false;

    const password = $("#password");
    password.valid = false;
    const alert_password = $("#alert_password")

    const confirm_password = $("#confirm_password");
    confirm_password.valid = false;
    const alert_confirm_password = $("#alert_confirm_password");

    const regExp = /^([0-9]|[a-z])+([0-9a-z]+)$/i;

    const signup_btn = $("#signup");

    // Alerts the user if the username doesn't conform to the imposed limitations.
    // The username has to be alphanumeric andbetween 6 and 20 characters long.
    name.change(function () {
        if (name.val() && name.val().length < 6 || name.val().length > 20) {
            name.valid = false;
            signup_btn.prop("disabled", true);
            alert_name.find('span').text("Username must be between 6 and 20 characters long.");
            alert_name.show();
        } else if(!name.val().match(regExp)){
            name.valid = false;
            signup_btn.prop("disabled", true);
            alert_name.find('span').text("Username must be alphanumeric (can only contain numbers and/or letters).");
            alert_name.show();
        }
        else {
            name.valid = true;
            if(password.valid && confirm_password.valid) signup_btn.prop("disabled", false);
            alert_name.hide();
        }
    });

    // Alerts the user if the password and password confirmation contain different strings or if the password doesn't conform to the imposed limitations.
    // Passwords must be between 8 and 128 characters long.
    password.change(function () {
        if(password.val().length < 8 || password.val().length > 128){
            password.valid = false;
            signup_btn.prop("disabled", true);
            alert_password.show();
        }
        else {
            password.valid = true;
            if(name.valid && confirm_password.valid) signup_btn.prop("disabled", false);
            alert_password.hide();
        }
    });

    confirm_password.change(function () {
        if (password.val() && confirm_password.val() && confirm_password.val() != password.val()) {
            confirm_password.valid = false;
            signup_btn.prop("disabled", true);
            alert_confirm_password.show();
        }
        else {
            confirm_password.valid = true;
            if(name.valid && password.valid) signup_btn.prop("disabled", false);
            alert_confirm_password.hide();
        }
    });

    // Signup button executes an AJAX post request with form data to the url /register.
    $("#signup").click(function (e) {
        if (name.valid && password.valid) {
            e.preventDefault();
            $("#signup").hide();
            $("#loading_text").text(" Registering...")
            $("#loading").show();
            $.post({
                url: 'register',
                data: $("#register").serialize(),
                timeout: 10000
            }).done(function (exists) {
                if (exists) {
                    $("#alert_existing_name").find('span').text(`User "${name.val()}" already exists!`);
                    $("#alert_existing_name").show();
                    $("#loading").hide();
                    $("#signup").show();
                }
                else {
                    $("#loading_text").text(" Logging in...")
                    $("#login_name").val(name.val());
                    $("#login_pass").val(password.val());
                    $.post({
                        url: 'login',
                        data: $("#login").serialize(),
                        timeout: 10000
                    }).done(function(){
                        window.location.replace("/");
                    });
                }
            });
        }
    });
});
