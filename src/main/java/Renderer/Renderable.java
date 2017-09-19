package Renderer;

import java.awt.image.BufferedImage;
import util.MapPoint;

public interface Renderable {
  MapPoint getImagePosition();
  BufferedImage getImage();
  MapPoint getEffectiveEntityPosition();
}