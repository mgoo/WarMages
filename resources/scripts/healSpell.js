var AnimationEntity = Java.type('main.game.model.entity.AnimationEntity');
var Collectors = Java.type('java.util.stream.Collectors');
var Animation = Java.type('main.images.Animation');

// This is set from java.
var dataLoader = null;

var apply = function(owner, target, attack, world) {
  var effectedUnits = getEffectedUnits(owner, world, target, attack);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);
  unit.gainHealth(attack.getAmount());
  world.addStaticEntity(
      new AnimationEntity(
          unit.getTopLeft(),
          unit.getSize(),
          new Animation(
              dataLoader.getDataForSpriteSheet('misc_sheet:healeffect'),
              'animation:healeffect',
              10
          ),
          0
      )
  );

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function (owner, world, target, attack) {
  return target.getEffectedUnits(world, attack.getRadius())
  .stream()
  .filter(function (u) {
    return !owner.getTeam().canAttack(u.getTeam());
  })
  .filter(function(u){
    return u.getHealthPercent() < 0.999;
  })
  .collect(Collectors.toList());
}