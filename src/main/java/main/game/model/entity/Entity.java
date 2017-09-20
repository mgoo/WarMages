package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Entity Abstract class: entities have positions on the screen,
 * images, and sizes.
 */
public abstract class Entity {
  protected MapPoint position;
  protected GameImage image;
  protected float size;

  public Entity(MapPoint position, float size){
    this.position=position;
    this.size=size;
    throw new Error("NYI");
  }

  public MapPoint getPosition(){
    throw new Error("NYI");
  }

  public MapSize getSize() {
    throw new Error("NYI");
  }

  void moveX(float amount){
    throw new Error("NYI");
  }

  void moveY(float amount){
    throw new Error("NYI");
  }

  public abstract GameImage getImage();

}
