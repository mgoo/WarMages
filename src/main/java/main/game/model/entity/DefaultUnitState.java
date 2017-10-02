package main.game.model.entity;

import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class DefaultUnitState extends AbstractUnitState {

  public DefaultUnitState(Direction direction, UnitSpriteSheet sheet) {
    super(Sequence.IDLE, direction, sheet);
  }

  @Override
  AbstractUnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
