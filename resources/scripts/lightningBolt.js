var Animation = Java.type('main.images.Animation');
var MapSize = Java.type('main.util.MapSize');
var AnimationEntity = Java.type('main.game.model.entity.AnimationEntity');
var Collectors = Java.type('java.util.stream.Collectors');

// This is set from java.
var dataLoader = null;

var apply = function(owner, target, attack, world) {
  var effectedUnits = getEffectedUnits(owner, world, target, attack);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);
  world.addStaticEntity(
      new AnimationEntity(
          unit.getLocation().translate(-1.5, -1.5),
          new MapSize(2, 2),
          new Animation(
              dataLoader.getDataForSpriteSheet('misc_sheet:lightning'),
              'animation:lightning',
              5
          ),
          0
      )
  );
  unit.takeDamage(attack.getAmount(), world, owner);

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target, attack) {
  return target.getEffectedUnits(world, attack.getRadius())
  .stream()
  .filter(function (u) {
    return owner.getTeam().canAttack(u.getTeam());
  })
  .collect(Collectors.toList());
};
