var Animation = Java.type('main.images.Animation');
var AnimationLoop = Java.type('main.images.AnimationLoop');
var DefaultProjectile = Java.type('main.game.model.entity.DefaultProjectile');
var MapSize = Java.type('main.util.MapSize');
var Collectors = Java.type('java.util.stream.Collectors');

// This is set from java.
var dataLoader = null;

var apply = function(owner, target, attack, world) {
  var effectedUnits = getEffectedUnits(owner, world, target, attack);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);

  var projectile = new DefaultProjectile(
      owner.getTopLeft(),
      new MapSize(0.5, 0.5),
      owner,
      unit,
      attack,
      new AnimationLoop(
          dataLoader.getDataForSpriteSheet('projectile_sheet:whitemissile'),
          'animation:whitemissile',
          2
      ),
      new Animation(
          dataLoader.getDataForSpriteSheet('projectile_sheet:whitemissile'),
          'animation:whitemissile-impact',
          5
      ),
      new MapSize(0.5, 0.5),
      0.1
  );

  world.addProjectile(projectile);

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
