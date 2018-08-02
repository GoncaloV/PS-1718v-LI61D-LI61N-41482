$(document).ready(function(){
  let pathname = window.location.pathname;
  if(pathname.includes('login')) $('#login-li').addClass('active');
  if(pathname.includes('register')) $('#signup-li').addClass('active');
  if(pathname.includes('lists')) $('#lists-li').addClass('active');
  if(pathname.includes('diary')) $('#diary-li').addClass('active');

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
