package gamemodel;

import util.MapRect;

/**
 * A container class for all model components (including units, items,
 * projectiles, etc). This also handles the progression of the story/game
 */
public class GameModel {

  public GameModel(
      World world,
      Collection<Unit> units,
      Collection<Item> items
  ) {
  }

  /**
   * A getter method to get all possible entities.
   * @return a collection of all possible entities
   */
  public Collection<Entity> getEntities(){
    throw new Error("NYI");
  }

  /**
   * Starts the main game loop of this app.
   */
  public void startGame() {
    throw new Error("NYI");
  }

  /**
   * A setter method to select a collection
   * @param selection points on the world that may contain entities
   */
  public void setEntitySelection(MapRect selection){
    throw new Error("NYI");
  }

  /**
   * A getter method get a previously selected collection
   * @return a collection of selected entities
   */
  public Collection<Entity> getEntitySelection() {
    throw new Error("NYI");
  }

  /**
   * A getter method which sets the selected collection and
   * returns a collection selected entities
   * @param selection points on the world that may contain entities
   * @return a collection of selected entities
   */
  public Collection<Entity> getEntitySelection(MapRect selection) {
    throw new Error("NYI");
  }

}
