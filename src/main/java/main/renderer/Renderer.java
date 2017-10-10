package main.renderer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import main.common.util.Config;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.util.Looper;
import main.game.view.GameView;

/**
 * Renders all renderables onto a canvas and supplies the Renderable interface. Ideally it will use
 * OpenGL to take advantage of hardware acceleration. This class should also be reponsible for
 * looping.
 */
public class Renderer {

  private final GameView gameView;
  private final ImageView imageView;
  private final Config config;
  private final Looper looper;

  /**
   * Creates a Renderer and the rendering loop.
   *
   * @param gameView the object the contains the GUI.
   * @param imageView the javaFX object that actually draws the GUI.
   */
  public Renderer(
      GameView gameView, ImageView imageView, Config config, Looper looper
  ) {
    this.gameView = gameView;
    this.imageView = imageView;
    this.config = config;
    this.looper = looper;
  }

  /**
   * A method which draws all the renderables in gameView and sets it to the imageView.
   *
   * @param gameView the object the contains the GUI.
   * @param imageView the javaFX object that actually draws the GUI.
   */
  public void drawAll(long currentTime, GameView gameView, ImageView imageView) {
    Objects.requireNonNull(gameView);
    Objects.requireNonNull(imageView);

    BufferedImage image = new BufferedImage(config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );
    g.setRenderingHints(rh);
    for (Renderable r : gameView.getRenderables(currentTime)) {
      MapPoint position = r.getImagePosition(currentTime);
      MapSize size = r.getImageSize();
      g.drawImage(r.getImage(),
          (int)(position.x + gameView.getViewBox().topLeft.x),
          (int)(position.y + gameView.getViewBox().topLeft.y),
          (int)size.width,
          (int)size.height,
          null);
    }
    imageView.setImage(SwingFXUtils.toFXImage(image, null));
  }

  /**
   * Pauses the rendering loop.
   *
   * @throws InterruptedException InterruptedException if any thread interrupted the current thread
   *        before or while the current thread was waiting for a notification.  The <i>interrupted
   *        status</i> of the current thread is cleared when this exception is thrown.
   */
  public void pause() throws InterruptedException {
    looper.setPaused(true);
  }

  /**
   * Resumes the rendering loop. Assumes that rendering loop is currently waiting.
   */
  public void resume() {
    looper.setPaused(false);
  }

  /**
   * Starts the looper to render the game.
   */
  public void start() {
    looper.start(
        () -> drawAll(System.currentTimeMillis(), gameView, imageView)
    );
  }
}
