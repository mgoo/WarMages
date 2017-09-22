package main.game.model.entity;

/**
 * Attackables can attack units.
 */
public interface Attackable {

  /**
   * Attacks the given unit.
   * @param unit to be attacked
   */
  void attack(Unit unit);

}
