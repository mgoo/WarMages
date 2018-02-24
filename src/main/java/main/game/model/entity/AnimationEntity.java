package main.game.model.entity;

import main.game.model.world.World;
import main.images.Animation;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * This is a fixed map entity that will animate
 * @author Andrew McGhie
 */
public class AnimationEntity extends StaticEntity{
  public AnimationEntity(MapPoint topLeft, MapSize size, Animation animation, double angle) {
    super(topLeft, size, animation, angle);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    if (this.animation.isFinished()) {
      world.removeStaticEntity(this);
    }
  }
}
