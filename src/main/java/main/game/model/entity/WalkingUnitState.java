package main.game.model.entity;

import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class WalkingUnitState extends AbstractUnitState {

  public WalkingUnitState(Direction direction, UnitSpriteSheet sheet) {
    super(Sequence.WALK, direction, sheet);
  }

  @Override
  AbstractUnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
