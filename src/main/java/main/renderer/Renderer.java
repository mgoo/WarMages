package main.renderer;

import javafx.scene.image.ImageView;
import main.game.view.GameView;

/**
 * Renders all renderables onto a canvas and supplies the Renderable interface. Ideally it will use
 * OpenGL to take advantage of hardware acceleration. This class should also be reponsible for
 * looping.
 *
 * @author Eric Diputado
 */
public interface Renderer {

  /**
   * A method which draws all the renderables in gameView and sets it to the imageView.
   *
   * @param gameView the object the contains the GUI.
   * @param imageView the javaFX object that actually draws the GUI.
   */
  void drawAll(long currentTime, GameView gameView, ImageView imageView);

  /**
   * Pauses the rendering loop.
   *
   * @throws InterruptedException InterruptedException if any thread interrupted the current thread
   *        before or while the current thread was waiting for a notification.  The <i>interrupted
   *        status</i> of the current thread is cleared when this exception is thrown.
   */
  void pause() throws InterruptedException;

  /**
   * Resumes the rendering loop. Assumes that rendering loop is currently waiting.
   */
  void resume();

  /**
   * Starts the looper to render the game.
   */
  void start();

  /**
   * Stops the looper.
   */
  void stop();
}
