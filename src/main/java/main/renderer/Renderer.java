package main.renderer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.game.view.GameView;
import main.util.MapPoint;

/**
 * Renders all renderables onto a canvas and supplies the Renderable interface. Ideally it will use
 * OpenGL to take advantage of hardware acceleration. This class should also be reponsible for
 * looping.
 */
public class Renderer {

  private final Thread thread;

  /**
   * Creates a Renderer and the rendering loop.
   *
   * @param gameView the object the contains the GUI.
   * @param imageView the javaFX object that actually draws the GUI.
   */
  public Renderer(GameView gameView, ImageView imageView) {
    thread = new Thread(() -> {
      while (true) {
        drawAll(gameView, imageView);
      }
    });
  }

  /**
   * A method which draws all the renderables in gameView and sets it to the imageView.
   *
   * @param gameView the object the contains the GUI.
   * @param imageView the javaFX object that actually draws the GUI.
   */
  private void drawAll(GameView gameView, ImageView imageView) {
    //    BufferedImage image = null;
    //    for (Renderable r : gameView.getRenderables()) {
    //      if (image == null) {
    //        r.getImage();
    //      }
    //      MapPoint position = r.getImagePosition();
    //      Graphics2D g = image.createGraphics();
    //      RenderingHints rh = new RenderingHints(
    //          RenderingHints.KEY_ANTIALIASING,
    //          RenderingHints.VALUE_ANTIALIAS_ON);
    //      g.setRenderingHints(rh);
    //      g.drawImage(r.getImage(), (int)position.x, (int)position.y, null);
    //    }
    //    imageView.setImage(SwingFXUtils.toFXImage(image, null));
  }

  /**
   * Pauses the rendering loop.
   *
   * @throws InterruptedException InterruptedException if any thread interrupted the current thread
   *     before or while the current thread was waiting for a notification.  The <i>interrupted
   *     status</i> of the current thread is cleared when this exception is thrown.
   */
  public void pause() throws InterruptedException {
    thread.wait();
  }

  /**
   * Resumes the rendering loop. Assumes that rendering loop is currently waiting.
   */
  public void resume() {
    thread.notify();
  }
}
