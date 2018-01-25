var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var Sequence = Java.type('main.images.SpriteSheet.Sequence');
var StaticEntity = Java.type('main.game.model.entity.StaticEntity');

var apply = function(owner, target, attack, world) {
  var effectedUnits = target.getEffectedUnits(world);
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
};