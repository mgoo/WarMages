var DamageBuffEffect = Java.type('main.game.model.entity.usable.DamageBuffEffect');
var Collectors = Java.type('java.util.stream.Collectors');

var apply = function(owner, target, attack, world) {
  var effectedUnits = getEffectedUnits(owner, world, target);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);

  unit.addEffect(
      new DamageBuffEffect(
          unit,
          world,
          attack.getDuration(),
          attack.getAmount()
      )
  );

  if (attack instanceof Java.type('main.game.model.entity.usable.Ability')) {
    attack.startCoolDown();
    attack.consume();
  }
};

var getEffectedUnits = function(owner, world, target) {
  return target.getEffectedUnits(world)
  .stream()
  .filter(function (u) {
    return !owner.getTeam().canAttack(u.getTeam());
  })
  .collect(Collectors.toList());
};
