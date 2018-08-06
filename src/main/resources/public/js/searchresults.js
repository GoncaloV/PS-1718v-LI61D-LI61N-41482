$(document).ready(function(){
  $('.modal').modal();
  $('.game-result').click(function() {
    let href = $(this).find('a').attr('href');
    window.location = href;
  });
});
