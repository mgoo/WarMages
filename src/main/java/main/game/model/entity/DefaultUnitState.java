package main.game.model.entity;

import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class DefaultUnitState extends UnitState {

  public DefaultUnitState(Direction direction, UnitSpriteSheet sheet) {
    super(Sequence.IDLE, direction, sheet);
  }

  @Override
  UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
