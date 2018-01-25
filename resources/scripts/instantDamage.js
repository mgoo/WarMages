var apply = function(owner, target, attack, world) {
  var effectedUnits = target.getEffectedUnits(world);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);

  unit.takeDamage(attack.getModifiedAmount(owner), world, owner);
};
