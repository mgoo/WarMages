var Animation = Java.type('main.images.Animation');
var AnimationLoop = Java.type('main.images.AnimationLoop');
var DefaultProjectile = Java.type('main.game.model.entity.DefaultProjectile');
var MapSize = Java.type('main.util.MapSize');

// This is set from java.
var dataLoader = null;

var apply = function(owner, target, attack, world) {
  var projectile = new DefaultProjectile(
      owner.getTopLeft(),
      new MapSize(0.8, 0.8),
      owner,
      target,
      attack,
      new AnimationLoop(
          dataLoader.getDataForSpriteSheet('projectile_sheet:fireball'),
          'animation:fireball',
          2
      ),
      new Animation(
          dataLoader.getDataForSpriteSheet('misc_sheet:explosion'),
          'animation:explosion',
          5
      ),
      new MapSize(2, 2),
      0.6
  );
  world.addProjectile(projectile);

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target, attack) {
  return target.getEffectedUnits(world, attack.getRadius());
};
