package main.renderer;

import java.awt.Graphics2D;
import main.game.view.GameView;

/**
 * Part of a view.
 * for example the damage numbers
 */
public interface Drawable {
  void draw(Graphics2D g, int viewX, int viewY, int viewWidth, int viewHeight);

  void onTick();
}
