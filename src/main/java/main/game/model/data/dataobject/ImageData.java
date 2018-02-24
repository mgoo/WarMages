package main.game.model.data.dataobject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Data object for Image Data.
 * @author Andrew McGhie
 */
public class ImageData {

  private String id;
  private String location;

  private double northOverflow = 0;
  private double southOverflow = 0;
  private double eastOverflow = 0;
  private double westOverflow = 0;

  private int x = 0;
  private int y = 0;
  private int width = 0;
  private int height = 0;

  private BufferedImage image;

  /**
   * Constructor so SpriteSheetData can build map of images.
   */
  ImageData(BufferedImage image,
            double northOverflow, double southOverflow, double eastOverflow, double westOverflow) {
    this.image = image;
    this.northOverflow = northOverflow;
    this.southOverflow = southOverflow;
    this.eastOverflow = eastOverflow;
    this.westOverflow = westOverflow;
  }

  /**
   * Reads the image from the file system and builds the images for them.
   */
  public void build() {
    try {
      this.image = ImageIO.read(new File(this.location));
      if (this.width != 0 || this.height != 0 || this.x != 0 || this.y != 0) {
        this.image = image.getSubimage(this.x, this.y, this.width, this.height);
      }
    } catch (IOException e) {
      System.err.println("Image " + this.id + " Location " + this.location + " was not found");
      this.image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
    }
  }

  public String getId() {
    return id;
  }

  public BufferedImage getImage() {
    return this.image;
  }

  public double getNorthOverflow() {
    return this.northOverflow;
  }

  public double getSouthOverflow() {
    return this.southOverflow;
  }

  public double getEastOverflow() {
    return this.eastOverflow;
  }

  public double getWestOverflow() {
    return this.westOverflow;
  }
}
