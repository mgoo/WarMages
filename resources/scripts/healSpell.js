var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var Sequence = Java.type('main.images.SpriteSheet.Sequence');
var StaticEntity = Java.type('main.game.model.entity.StaticEntity');
var Collectors = Java.type('java.util.stream.Collectors');

var apply = function(owner, target, attack, world) {
  var effectedUnits = getEffectedUnits(owner, world, target);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);
  unit.gainHealth(attack.getAmount());
  world.addStaticEntity(
      new StaticEntity(
          unit.getTopLeft(),
          unit.getSize(),
          Sheet.HEAL_EFFECT.getImagesForSequence(Sequence.HEAL),
          false
      )
  );

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
  }
};

var getEffectedUnits = function (owner, world, target) {
  return target.getEffectedUnits(world)
  .stream()
  .filter(function (u) {
    return !owner.getTeam().canAttack(u.getTeam());
  })
  .filter(function(u){
    return u.getHealthPercent() < 0.999;
  })
  .collect(Collectors.toList());
}