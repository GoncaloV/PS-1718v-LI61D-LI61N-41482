window.onload = () => {
    $('.modal').modal();
    $('#delete-list-btn').click(_ => {
        $.post({
            url: '/lists/delete',
            data: $("#delete-list-form").serialize(),
            timeout: 10000
        }).done(data => {
            location.reload(true);
        }).fail(e => {
            console.log(e);
        });
    });
}