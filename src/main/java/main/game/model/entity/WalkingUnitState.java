package main.game.model.entity;

import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class WalkingUnitState extends UnitState {

  public WalkingUnitState(Direction direction, UnitSpriteSheet sheet) {
    super(Sequence.WALK, direction, sheet);
  }

  @Override
  UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
