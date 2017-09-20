/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

function addUnitIcon(image, unit) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.unitClick(unit)">'
      + '</div>');
  icon.css('background-image', 'url(' + image + ')');
  $('#unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.abililtyClick(ability)">'
      + '</div>');
  icon.css('background-image', 'url(' + image + ')');
  $('#ability-holder').append(icon);
}

function addItemIcon(image, item) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.itemClick(item)">'
      + '</div>');
  icon.css('background-image', 'url(' + image + ')');
  $('#item-holder').append(icon);
}
