$(document).ready(function(){
  $('.fixed-action-btn').floatingActionButton();
  $('.tooltipped').tooltip();
  $('.modal').modal();
  $('select').formSelect();
  let today = new Date();
  $('.datepicker').datepicker({
    format: 'yyyy-mm-dd',
    container: 'body',
    defaultDate: today,
    autoClose: true,
    showClearBtn: true
  });
  $('.textarea').characterCounter();
  // Messy way of translating the numeric rating to stars.
  // First check if the rating is numeric (it could be the string "No ratings yet")
  $('.star-rating').each(function() {
    let rating = parseFloat($(this).html());
    if (!isNaN(rating)){
      let star_rating = Math.round(rating)/2; // Round to integers between 1 and 10, then divide by 2 to obtain star ratings between 0 and 5 stars.
      let rating_parent = $(this).parent();
      let html_to_set = '';
      let i = 1;
      // Add a star for every point in star_rating
      for(i; i <= star_rating; i++){
        html_to_set = html_to_set + '<i class="inline-icon material-icons">star</i>';
      }
      // Add a star half if the remainder is equal to or greater than 0.5
      if(star_rating - (i-1) >= 0.5){
        html_to_set = html_to_set + '<i class="inline-icon material-icons">star_half</i>';
        i++;
      }
      // Add any remaining empty stars so that 5 stars always show.
      for(i; i <= 5; i++){
        html_to_set = html_to_set + '<i class="inline-icon material-icons">star_border</i>';
      }
      rating_parent.html(html_to_set); // Set new html
      rating_parent.attr("data-tooltip", rating + '/10');
    }

  });

  $('#clear-rating-btn').click(_ => {
    $('input[name=rating]').each((index, e) => {
      e.checked=false;
    });
  });

  $('#delete-entry-btn').click(() => {
    $.post({
      url: '/diary/delete',
      data: $("#delete-entry-form").serialize(),
      timeout: 10000
    }).done(data => {
      location.reload(true);
    }).fail(e => {
      console.log(e);
    });
  });
});
