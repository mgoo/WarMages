package main.game.model.entity;

import java.util.List;
import main.common.World;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapSize;

public class StaticEntity extends DefaultEntity {

  private final double animationSpeed;

  private final List<GameImage> gameImageList;
  private final boolean loop;
  private double currentImage = 0;

  public StaticEntity(MapPoint topLeft,
                      MapSize size,
                      List<GameImage> gameImageList,
                      boolean loop) {
    this(topLeft, size, gameImageList, loop, 1);
  }

  public StaticEntity(MapPoint topLeft,
                      MapSize size,
                      List<GameImage> gameImageList,
                      boolean loop,
                      double animationSpeed) {
    super(topLeft, size);
    this.animationSpeed = animationSpeed;
    this.gameImageList = gameImageList;
    this.loop = loop;
  }

  @Override
  public GameImage getImage() {
    return this.gameImageList.get((int)currentImage);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    this.currentImage += this.animationSpeed;
    if (this.currentImage >= this.gameImageList.size()) {
      if (this.loop) {
        this.currentImage = 0;
      } else {
        world.removeStaticEntity(this);
      }
    }
  }

  @Override
  public boolean contains(MapPoint point) {
    return false;
  }
}
