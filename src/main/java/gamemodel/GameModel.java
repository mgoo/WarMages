package gamemodel;

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
}
