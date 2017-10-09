/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

function addUnitIcon(image, unit) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.unitIconBtn(unit)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.abilityIconBtn(ability)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#ability-holder').append(icon);
}

function addItemIcon(image, item) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.itemIconBtn(item)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#item-holder').append(icon);
}

function clearUnits() {
  $('#unit-holder').html('');
}

function clearAbilties() {
  $('#ability-holder').html('');
}

function clearItems() {
  $('#item-holder').html('');
}

var gameViewProxy = $('#game-view-proxy');
var menuButton = $('#menu-button');
var resumeButton = $('#resume-btn');

gameViewProxy.on('click', function (event) {
    controller.onLeftClick(event.pageX, event.pageY, event.shiftKey, event.ctrlKey);
});
gameViewProxy.on('contextmenu', function (event) {
  controller.onRightClick(event.pageX, event.pageY, event.shiftKey, event.ctrlKey);
  return false;
});

menuButton.on('click', function (event) {
    $('#overlay').fadeIn();
    $('#pause-menu').fadeIn();
});

resumeButton.on('click', function (event) {
    $('#overlay').fadeOut();
    $('#pause-menu').fadeOut();
});

$('document').on('click', function (event) {
    Rect.init(event.x, event.y);
    Rect.draw(gameViewProxy);
    Rect.update(event.x + 10, event.y + 10);
});

gameViewProxy
  .on('mousedown', function (event) {
     if (!Rect.visible) {
         Rect.init(event.x, event.y);
         Rect.draw(gameViewProxy);
     }
  })
  .on('mousemove', function (event) {
    if (Rect.visible) {
      Rect.update(event.x, event.y);
    }
  });
$('document').on('mouseup', function(event) {
    Rect.reset();
});

var Rect = {
    rect: $('<div class="rect"></div>'),
    visible: false,
    box: {
        startX: 0,
        startY: 0,
        x: 0,
        y: 0,
        reset: function() {
            this.x = 0;
            this.y = 0;
            this.startX = 0;
            this.startY = 0;
        }
    },
    init: function(x, y) {
        visible = true;
        this.box.startX = x;
        this.box.startY = y;
        this.box.x = x;
        this.box.y = y;
        this.rect.css('left', x + 'px');
        this.rect.css('up', y + 'px');
    },
    draw: function(element) {
        element.append(this.rect);
    },
    update: function(x, y) {
        this.box.x = x;
        this.box.y = y;
        this.rect.css('width', (this.box.x - this.box.startX) + 'px');
        this.rect.css('height', (this.box.y - this.box.startY) + 'px');
    },
    reset: function() {
        this.box.reset();
        this.visible = false;
        this.rect.remove();
    }
};
