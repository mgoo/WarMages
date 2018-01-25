var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var Sequence = Java.type('main.images.SpriteSheet.Sequence');
var MapSize = Java.type('main.util.MapSize');
var StaticEntity = Java.type('main.game.model.entity.StaticEntity');

var apply = function(owner, target, attack, world) {
  var effectedUnits = target.getEffectedUnits(world);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);
  world.addStaticEntity(
      new StaticEntity(
          unit.getLocation().translate(-1.5, -1.5),
          new MapSize(2, 2),
          Sheet.LIGHTING.getImagesForSequence(Sequence.LIGHTING_1),
          false,
          0.5
      )
  );
  unit.takeDamage(attack.getModifiedAmount(owner), world, owner);
};