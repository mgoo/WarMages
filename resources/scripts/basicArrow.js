var Direction = Java.type('main.game.model.entity.Direction');
var Sheet = Java.type('main.images.SpriteSheet.Sheet');
var Sequence = Java.type('main.images.SpriteSheet.Sequence');
var DefaultProjectile = Java.type('main.game.model.entity.DefaultProjectile');
var MapSize = Java.type('main.util.MapSize');

var apply = function(owner, target, attack, world) {
  var effectedUnits = target.getEffectedUnits(world);
  if (effectedUnits.size() == 0) {
    return;
  }
  var unit = effectedUnits.get(0);

  var direction = Direction.between(owner.getCentre(), unit.getLocation());
  var projectile = new DefaultProjectile(
      owner.getCentre(),
      new MapSize(0.7, 0.7),
      owner,
      unit,
      attack,
      Sheet.ARROW_PROJECTILE.getImagesForSequence(Sequence.ARROW, direction),
      Sheet.ARROW_PROJECTILE.getImagesForSequence(Sequence.ARROW, direction),
      new MapSize(0.7, 0.7),
      0.5
  );

  world.addProjectile(projectile);
};