/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

// Icons *************************************************

function addUnitIcon(image, unit, tooltip) {
  var icon = $(
      '<div class="icon icon-tooltip">'
      + '<div class="icon-tooltiptext">' + tooltip + '</div>'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('mouseup', function(event) {
    controller.unitIconBtn(unit, event.shiftKey, event.ctrlKey, event.which === 1);
  });
  $('.unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('mouseup', function (event) {
    controller.abilityIconBtn(ability, event.shiftKey, event.ctrlKey,
        event.which === 1);
  });
  $('.ability-holder').append(icon);
}

function addItemIcon(image, item) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('mouseup', function (event) {
    controller.itemIconBtn(item, event.shiftKey, event.ctrlKey,
        event.which === 1);
  });
  $('.item-holder').append(icon);
}

function removeUnitIcon(index) {
  $('.unit-holder div:nth-child(' + (index + 1) + ')').remove();
}
function removeAbilityIcon(index) {
  $('.ability-holder div:nth-child(' + (index + 1) + ')').remove();
}
function removeItemIcon(index) {
  $('.item-holder div:nth-child(' + (index + 1) + ')').remove();
}

function setAbilityIconToCoolDown(index, time) {
  if ($('.ability-holder div:nth-child(' + (index + 1) + ') .bottom-bar').length !== 0) {
    return;
  }
  var abilityIcon = $('.ability-holder div:nth-child(' + (index + 1) + ')');
  var bottomBar = $('<div class="cooldown-bar"></div>');
  bottomBar.animate({
    width: 0
  }, time, function () {
    $(this).remove();
  });
  abilityIcon.append(bottomBar);
}

function setItemIconToCoolDown(index, time) {
  if ($('.item-holder div:nth-child(' + (index + 1) + ') .cooldown-bar').length !== 0) {
    return;
  }
  var abilityIcon = $('.item-holder div:nth-child(' + (index + 1) + ')');
  var cooldownBar = $('<div class="cooldown-bar"></div>');
  cooldownBar.animate({
    width: 0
  }, time, function () {
    $(this).remove();
  });
  abilityIcon.append(cooldownBar);
}

// Clicking *********************************************

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

// Pause menu ******************************************

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

// Rectangle select **********************************

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
    doDragSelect(event); // Pass the mouse up event through to the dragselect
  })
  .on('mousemove', function (event) {
    if (Rect.visible) {
      Rect.update(event.pageX, event.pageY);  // Update the rectangle
    }
  });
$('.top-bar')
  .on('mouseup', function (event) {
    doDragSelect(event);  // Pass the mouse up event through to the dragselect
  })
  .on('mousemove', function (event) {
    if (Rect.visible) {
      Rect.update(event.pageX, event.pageY); // Update the rectangle
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
