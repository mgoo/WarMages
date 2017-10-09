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

gameViewProxy
  .on('mousedown', function (event) {
    Rect.mouseDown = true
  })
  .on('mousemove', function (event) {
    if (!Rect.visible && Rect.mouseDown) {
      Rect.init(event.pageX, event.pageY);
      Rect.draw(gameViewProxy);
    }
    if (Rect.visible) {
      Rect.update(event.pageX, event.pageY);
    }
  })
  .on('mouseup', function (event) {
    if (Rect.visible) {
      controller.onDrag(Rect.box.startX,
          Rect.box.startY,
          event.pageX,
          event.pageY,
          event.shiftKey,
          event.ctrlKey);
    } else {
      controller.onLeftClick(event.pageX, event.pageY, event.shiftKey, event.ctrlKey);
    }
    Rect.reset();
  });

var Rect = {
    rect: $('<div class="rect"></div>'),
    container: gameViewProxy,
    visible: false,
    mouseDown: false,
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
        this.visible = true;
        this.box.startX = x;
        this.box.startY = y;
        this.box.x = x;
        this.box.y = y;
        this.rect.css('left', x + 'px');
        this.rect.css('top', y + 'px');
    },
    draw: function() {
      this.rect.appendTo(this.container);
    },
    update: function(x, y) {
        this.box.x = x;
        this.box.y = y;
        this.rect.css('width', (this.box.x - this.box.startX) + 'px');
        this.rect.css('height', (this.box.y - this.box.startY) + 'px');
    },
    reset: function() {
      this.rect.remove();
      this.mouseDown = false;
      this.visible = false;
      this.box.reset();
    }
};
