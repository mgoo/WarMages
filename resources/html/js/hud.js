/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

function addUnitIcon(image, unit) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('click', function(event) {
    controller.unitIconBtn(unit, event.shiftKey, event.ctrlKey, event.which === 1);
  });
  $('#unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('click', function(event) {
    controller.abilityIconBtn(ability, event.shiftKey, event.ctrlKey, event.which === 1);
  });
  $('#ability-holder').append(icon);
}

function addItemIcon(image, item) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('click', function(event) {
    controller.itemIconBtn(item, event.shiftKey, event.ctrlKey, event.which === 1);
  });
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

gameViewProxy.on('dblclick', function (event) {
  controller.onDbClick(event.pageX, event.pageY, event.shiftKey, event.ctrlKey);
  return false;
});

menuButton.on('click', function (event) {
    $('#overlay').fadeIn();
    $('#pause-menu').fadeIn();
});

$("#pause-menu").on("hidden.bs.modal", function (event) {
    controller.resume();
});
$("#pause-menu").on("shown.bs.modal", function(event) {
    controller.pause();
});

$(document).keyup(function(event) {
  if (event.keyCode === 27) { // escape key maps to keycode `27`
    $("#pause-menu").modal("toggle")
  }
});

resumeButton.on('click', function (event) {
    $('#overlay').fadeOut();
    $('#pause-menu').fadeOut();
});

gameViewProxy
  .on('mousedown', function (event) {
    if (event.which !== 1) return;
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
      var wasDrag = doDragSelect(event);
      if (!wasDrag && event.which === 1) {
        controller.onLeftClick(event.pageX, event.pageY, event.shiftKey,
            event.ctrlKey);
      }
      Rect.reset()
  });

$('.bottom-bar')
  .on('mouseup', function (event) {
    doDragSelect(event);
  })
  .on('mousemove', function (event) {
    if (Rect.visible) {
      Rect.update(event.pageX, event.pageY);
    }
  });
$('.top-bar')
  .on('mouseup', function (event) {
    doDragSelect(event);
  })
  .on('mousemove', function (event) {
    if (Rect.visible) {
      Rect.update(event.pageX, event.pageY);
    }
  });

var doDragSelect = function (event) {
  if (event.which === 1 && Rect.visible) {
    controller.onDrag(Rect.box.originX,
        Rect.box.originY,
        Rect.box.x,
        Rect.box.y,
        event.shiftKey,
        event.ctrlKey);
    Rect.reset();
    return true;
  }
  return false;
};


var Rect = {
    rect: $('<div class="rect"></div>'),
    visible: false,
    mouseDown: false,
    box: {
        originX: 0,
        originY: 0,
        x: 0,
        y: 0,
        reset: function() {
            this.x = 0;
            this.y = 0;
            this.originX = 0;
            this.originY = 0;
        }
    },
    init: function(x, y) {
        this.visible = true;
        this.box.originX = x;
        this.box.originY = y;
        this.box.x = x;
        this.box.y = y;
        this.rect.css('left', x + 'px');
        this.rect.css('top', y + 'px');
    },
    draw: function(container) {
      this.rect.appendTo(container);
    },
    update: function(x, y) {
      this.box.x = x;
      this.box.y = y;
      var dispX = Math.min(this.box.x, this.box.originX);
      var dispY = Math.min(this.box.y, this.box.originY);
      var dispWidth = Math.abs(this.box.x - this.box.originX);
      var dispHeight = Math.abs(this.box.y - this.box.originY);

      this.rect.css('left', dispX + 'px');
      this.rect.css('top', dispY + 'px');
      this.rect.css('width', dispWidth + 'px');
      this.rect.css('height', dispHeight + 'px');
    },
    reset: function() {
      this.rect.remove();
      this.mouseDown = false;
      this.visible = false;
      this.box.reset();
    }
};
