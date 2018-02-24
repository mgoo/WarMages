package main.game.model.data.dataObject;

public class AnimationData {
  private String id;
  private int width;
  private int height;
  private int frames;
  private int directions;
  private double northOverflow = 0;
  private double southOverflow = 0;
  private double eastOverflow = 0;
  private double westOverflow = 0;

  public String getId() {
    return id;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getFrames() {
    return frames;
  }

  public int getDirections() {
    return directions;
  }

  public double getNorthOverflow() {
    return northOverflow;
  }

  public double getSouthOverflow() {
    return southOverflow;
  }

  public double getEastOverflow() {
    return eastOverflow;
  }

  public double getWestOverflow() {
    return westOverflow;
  }
}
