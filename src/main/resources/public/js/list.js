window.onload = () => {
    let alert = $('#alert-generic');
    $('.collapsible').collapsible();
    $('.clear-tag-btn').click(event => {
        $.post({
            url: '/lists/untag',
            data: $(event.target).parent().serialize(),
            timeout: 5000
        }).done(data => {
            $(event.target).parent().remove();
        }).fail(error => {
            alert(error);
        });
    });

    $('.add-tag-btn').click(event => {
        // Warn the user if the tag's name doesn't meet the standard.
        let tagname = $(event.target).prev('div').find('input').val();
        const regExp = /^([0-9]|[a-z])+([0-9a-z]+)$/i;
        if (tagname.length < 1 || tagname.length > 20) {
            alert.find('span').text('Tag names must be between 1 and 20 characters long.');
            M.toast({
                html: alert.html()
            });
        }
        else if (!tagname.match(regExp)) {
            alert.find('span').text('Tag names must be alphanumeric.');
            M.toast({
                html: alert.html()
            });
        }
        else {
            $.post({
                url: '/lists/tag',
                data: $(event.target).parent().serialize(),
                timeout: 5000
            }).done(data => {
                location.reload(true);
            }).fail(error => {
                alert(error);
            });
        }
    });

    $('.clear-game-btn').click(event => {
        $.post({
            url: '/lists/removeGame',
            data: $(event.target).closest('form').serialize(),
            timeout: 5000
        }).done(data => {
            $(event.target).closest('tr').remove();
        }).fail(error => {
            alert(error);
        });
    });
}
