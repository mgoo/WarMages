package main.game.model.data.dataobject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import main.game.model.data.DataLoader;

/**
 * Data object for a sprite sheet.
 * @author Andrew McGhie
 */
public class SpriteSheetData {

  private String id;
  private String location;
  private String type;

  private SpriteSheetTypeData typeData;
  private Map<String, ImageData[][]> images = new HashMap<>();

  /**
   * Loads all the images an sorts them into animation and directions.
   */
  public void build(DataLoader dataLoader) {
    this.typeData = dataLoader.getDataForSpriteSheetType(type);
    Collection<AnimationData> animations = typeData.getAnimations();
    try {
      BufferedImage image = ImageIO.read(new File(this.location));
      animations.forEach(animationData -> {
        int startY = typeData.getAnimationStart(animationData.getId());
        images.put(animationData.getId(),
            new ImageData[animationData.getDirections()][animationData.getFrames()]);

        for (int direction = 0; direction < animationData.getDirections(); direction++) {
          for (int frame = 0; frame < animationData.getFrames(); frame++) {
            int x = frame * animationData.getWidth();
            int y = startY + direction * animationData.getHeight();
            BufferedImage spriteImage = null;
            try {
              spriteImage = image
                  .getSubimage(x, y, animationData.getWidth(), animationData.getHeight());
            } catch (Exception e) {
              e.printStackTrace();
            }
            ImageData spriteImageData = new ImageData(spriteImage,
                animationData.getNorthOverflow(),
                animationData.getSouthOverflow(),
                animationData.getEastOverflow(),
                animationData.getWestOverflow()
            );
            images.get(animationData.getId())[direction][frame] = spriteImageData;
          }
        }
      });
    } catch (IOException e) {
      System.err.println("SpriteSheet image" + this.id + " at Location " + this.location
          + " was not found");
    }
  }

  public String getId() {
    return this.id;
  }

  public AnimationData getAnimation(String id) {
    return this.typeData.getAnimationData(id);
  }

  /**
   * Gets the image for an animation at a perticular frame and direction.
   * @param direction the number clock wise of the direction that the animation supports
   *                    i.e. not the angle
   */
  public ImageData getImage(String animation, int frame, int direction) {
    if (!images.containsKey(animation)) {
      throw new IllegalArgumentException("The spritesheet does not contain " + animation);
    }

    ImageData[][] animationImages = images.get(animation);

    if (direction >= animationImages.length) {
      throw new IllegalArgumentException("Direction was larger than the direcitions defined"
        + "defined: " + animationImages.length + " passed: " + direction);
    }
    if (frame >= animationImages[direction].length) {
      throw new IllegalArgumentException("The animation " + animation + " only has "
          + animationImages[direction].length + " tried to get frame " + frame);
    }

    return animationImages[direction][frame];
  }

}
