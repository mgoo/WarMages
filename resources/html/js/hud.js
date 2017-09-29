/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

function addUnitIcon(image, unit) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.unitClick(unitIdx)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.abililtyClick(abilityIdx)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#ability-holder').append(icon);
}

function addItemIcon(image, item) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.itemClick(itemIdx)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#item-holder').append(icon);
}

$('#menu-button').on('click', function (event) {
  $('#overlay').fadeIn();
  $('#pause-menu').fadeIn();
});

$('#resume-btn').on('click', function (event) {
  $('#overlay').fadeOut();
  $('#pause-menu').fadeOut();
});
