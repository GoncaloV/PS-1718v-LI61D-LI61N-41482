$(document).ready(function(){
  $('#logout-btn').click(_ => {
    $.post({
      url: '/logout',
      data: $('#logout-form').serialize(),
      timeout: 5000
    }).done(_ => {
      location.reload(true);
    });
  });
});
