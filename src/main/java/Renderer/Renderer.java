package Renderer;

import view.GameView;

/**
 * Renders all renderables onto a canvas and supplies the Renderable interface.
 * Ideally it will use OpenGL to take advantage of hardware acceleration.
 * This class should also be reponsible for looping.
 */
public abstract class Renderer {
  public Renderer(GameView gameView){}
}
