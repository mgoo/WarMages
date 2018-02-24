var DefaultUnit = Java.type('main.game.model.entity.unit.DefaultUnit');
var Team = Java.type('main.game.model.entity.Team');

// This is set from java.
var dataLoader = null;

var apply = function(owner, target, attack, world) {
  var turret = new DefaultUnit(
      dataLoader.getDataForUnitType("unittype:turret"),
      target.getLocation(),
      Team.PLAYER,
      dataLoader
  );
  world.addUnit(turret);

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target, attack) {
  return target.getEffectedUnits(world, attack.getRadius());
};
