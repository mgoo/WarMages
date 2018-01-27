/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

// Icons *************************************************

let units = [];
let abilities = [];
let items = [];

function addUnitIcon(image, unit) {
  units.push(unit);
  let icon = $(
      '<div class="icon icon-tooltip">'
      + '<div class="icon-tooltiptext"></div>'
      + '<div class="icon-top-bar"><span class="level"></span></div>'
      + '<div class="icon-bottom-bar"><div class="health-bar"></div></div>'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('mouseup', function(event) {
    controller.unitIconBtn(unit, event.shiftKey, event.ctrlKey, event.which === 1);
  });
  $('.unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  abilities.push(ability);
  let icon = $(
      '<div class="icon icon-tooltip">'
      + '<div class="icon-tooltiptext"></div>'
      + '<div class="icon-bottom-bar"><div class="cooldown"></div></div>'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('mouseup', function (event) {
    controller.abilityIconBtn(ability, event.shiftKey, event.ctrlKey,
        event.which === 1);
  });
  $('.ability-holder').append(icon);
}

function addItemIcon(image, item) {
  items.push(item);
  let icon = $(
      '<div class="icon icon-tooltip">'
      + '<div class="icon-tooltiptext"></div>'
      + '<span class="icon-uses"></span>'
      + '<div class="icon-bottom-bar"><div class="cooldown"></div></div>'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  icon.on('mouseup', function (event) {
    controller.itemIconBtn(item, event.shiftKey, event.ctrlKey,
        event.which === 1);
  });
  $('.item-holder').append(icon);
}

function updateIcons() {
  for (let i = 0; i < units.length; i++) {
    let icon = $('.unit-holder .icon:nth-child(' + (i + 1) + ')');
    let healthPercentage = units[i].getHealthPercent();
    let healthBar = icon.find('.health-bar');
    healthBar.width((healthPercentage * 100) + '%');
    if (healthPercentage > 0.5) {
      healthBar.css('background-color', '#54FF6A')
    } else if (healthPercentage > 0.25) {
      healthBar.css('background-color', '#FFC229')
    } else {
      healthBar.css('background-color', '#FF003D')
    }
    let level = icon.find('.level');
    level.html(units[i].getLevel());

    let damage = Math.round(
        units[i].getUnitType().getBaseAttack().getAmount()
    );
    let attackSpeed = Math.round(
        units[i].getUnitType().getBaseAttack().getModifiedAttackSpeed(units[i])
    );
    let range = units[i].getUnitType().
        getBaseAttack().getModifiedRange(units[i]).toFixed(1);
    let maxHealth = Math.round(units[i].getMaxHealth());
    let currentHealth = Math.round(units[i].getHealth());
    let movementSpeed = units[i].getSpeed().toFixed(2);

    let tooltiptext = "<b>Health</b>: " + currentHealth + "/" + maxHealth + "<br>"
        + "<b>Damage</b>: " + damage + "<br>"
        + "<b>Range</b>: " + range + "<br>"
        + "<b>Attack Speed</b>: " + attackSpeed + "<br>"
        + "<b>Movement Speed</b>: " + movementSpeed;

    icon.find('.icon-tooltiptext').html(tooltiptext)
  }

  for (let i = 0; i < abilities.length; i++) {
    let icon = $('.ability-holder .icon:nth-child(' + (i + 1) + ')');
    icon.find('.cooldown').width((abilities[i].getCoolDownProgress() * 100) + '%');
    if (abilities[i].isSelected() && !icon.hasClass('selected')) {
      icon.addClass('selected');
    }
    if (!abilities[i].isSelected() && icon.hasClass('selected')) {
      icon.removeClass('selected');
    }
    icon.find('.icon-tooltiptext').html(abilities[i].getDescription())
  }

  for (let i = 0; i < items.length; i++) {
    let icon = $('.item-holder .icon:nth-child(' + (i + 1) + ')');
    if (items[i].getUses() >= 0) {
      icon.find('.icon-uses').html(items[i].getUses());
    }
    icon.find('.cooldown').width((items[i].getCoolDownProgress() * 100) + '%');
    if (items[i].isSelected() && !icon.hasClass('selected')) {
      icon.addClass('selected');
    }
    if (!items[i].isSelected() && icon.hasClass('selected')) {
      icon.removeClass('selected');
    }
    icon.find('.icon-tooltiptext').html(items[i].getDescription())
  }
}

function removeUnitIcon(index) {
  units.splice(index, 1);
  $('.unit-holder .icon:nth-child(' + (index + 1) + ')').remove();
}
function removeAbilityIcon(index) {
  abilities.splice(index, 1);
  $('.ability-holder .icon:nth-child(' + (index + 1) + ')').remove();
}
function removeItemIcon(index) {
  items.splice(index, 1);
  $('.item-holder .icon:nth-child(' + (index + 1) + ')').remove();
}

// Clicking *********************************************

let gameViewProxy = $('#game-view-proxy');
let menuButton = $('#menu-button');
let resumeButton = $('#resume-btn');

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
    let wasDrag = doDragSelect(event);
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

let doDragSelect = function (event) {
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


let Rect = {
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
      let dispX = Math.min(this.box.x, this.box.originX);
      let dispY = Math.min(this.box.y, this.box.originY);
      let dispWidth = Math.abs(this.box.x - this.box.originX);
      let dispHeight = Math.abs(this.box.y - this.box.originY);

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
