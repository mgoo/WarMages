package main.renderer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.game.view.GameView;

/**
 * Renders all renderables onto a canvas and supplies the Renderable interface. Ideally it will use
 * OpenGL to take advantage of hardware acceleration. This class should also be reponsible for
 * looping.
 */
public abstract class Renderer {

  public Renderer(GameView gameView, ImageView imageView) {
    for (Renderable r : gameView.getRenderables()){
      imageView.setImage(new Image(r.getImage()));
    }
  }
}
