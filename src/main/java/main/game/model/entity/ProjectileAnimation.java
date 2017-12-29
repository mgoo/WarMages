package main.game.model.entity;

import java.util.List;
import java.util.stream.Collectors;
import main.common.images.GameImage;
import main.images.Animation;
import main.images.ProjectileSpriteSheet;

public class ProjectileAnimation{

  private final ProjectileSpriteSheet spriteSheet;
  private double direction;

  private List<GameImage> images;

  /**
   * Constructor uses list of frames and time.
   */
  public ProjectileAnimation(ProjectileSpriteSheet spriteSheet, double direction) {
    this.spriteSheet = spriteSheet;
    this.direction = direction;
  }

  private List<GameImage> rotateImages(List<GameImage> images, double theta) {
    return images.stream().map(image -> image.rotate(theta)).collect(Collectors.toList());
  }
}
