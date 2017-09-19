package main.Entity;

/**
 * Entity Abstract class: entities have positions on the screen,
 * images, and sizes.
 */
public abstract class Entity {
  protected MapPoint position;
  protected GameImage image;
  protected float size;

  public Entity(MapPoint position, float size){
    throw new Error("NYI");
    this.position=position;
    this.size=size;
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

  public abstract GameImage.Config getImage();

}
