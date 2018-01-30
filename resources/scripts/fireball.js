var Direction = Java.type('main.game.model.entity.Direction');
var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var Sequence = Java.type('main.images.SpriteSheet.Sequence');
var DefaultProjectile = Java.type('main.game.model.entity.DefaultProjectile');
var MapSize = Java.type('main.util.MapSize');
var Collectors = Java.type('java.util.stream.Collectors');

var apply = function(owner, target, attack, world) {
  var effectedUnits = getEffectedUnits(owner, world, target);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);

  var direction = Direction.between(owner.getCentre(), unit.getLocation());
  var projectile = new DefaultProjectile(
      owner.getCentre(),
      new MapSize(1.5, 1.5),
      owner,
      unit,
      attack,
      Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_FLY, direction),
      Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_IMPACT, direction),
      new MapSize(1, 1),
      0.4
  );

  world.addProjectile(projectile);

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target) {
  return target.getEffectedUnits(world)
    .stream()
    .filter(function (u) {
      return owner.getTeam().canAttack(u.getTeam());
    })
    .collect(Collectors.toList());
};
