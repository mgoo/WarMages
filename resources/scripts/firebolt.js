var Direction = Java.type('main.game.model.entity.Direction');
var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var Sequence = Java.type('main.images.SpriteSheet.Sequence');
var DefaultProjectile = Java.type('main.game.model.entity.DefaultProjectile');
var MapSize = Java.type('main.util.MapSize');

var apply = function(owner, target, attack, world) {
  var direction = Direction.between(owner.getCentre(), target.getLocation());
  var projectile = new DefaultProjectile(
      owner.getCentre(),
      new MapSize(0.8, 0.8),
      owner,
      target,
      attack,
      Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_FLY, direction),
      Sheet.EXPLOSIONS.getImagesForSequence(Sequence.EXPLOSION_1, direction),
      new MapSize(2, 2),
      0.6
  );
  world.addProjectile(projectile);

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target) {
  return target.getEffectedUnits(world);
};
