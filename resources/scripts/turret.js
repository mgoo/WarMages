var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var MapSize = Java.type('main.util.MapSize');
var DefaultUnit = Java.type('main.game.model.entity.unit.DefaultUnit');
var Team = Java.type('main.game.model.entity.Team');
var UnitType = Java.type('main.game.model.entity.unit.UnitType');
var DefaultUnitSpriteSheet = Java.type('main.images.DefaultUnitSpriteSheet');
var GameImageResource = Java.type('main.images.GameImageResource');

var apply = function(owner, target, attack, world) {
  var turret = new DefaultUnit(
      target.getLocation(),
      new MapSize(0.5, 0.5),
      Team.PLAYER,
      new DefaultUnitSpriteSheet(GameImageResource.CRYSTAL_SPRITE_SHEET),
      UnitType.TURRET
  );
  world.addUnit(turret);

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target) {
  return target.getEffectedUnits(world);
};
