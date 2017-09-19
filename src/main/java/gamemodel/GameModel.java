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

  public Collection<Entity> getEntities(){
    throw new Error("NYI");
  }

  public void startGame() {
    throw new Error("NYI");
  }
}
