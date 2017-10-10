package main.game.model.entity;

import main.common.images.UnitSpriteSheet.Sequence;

public class WalkingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public WalkingUnitState(Direction direction, Unit unit) {
    super(Sequence.WALK, direction, unit);
  }

  @Override
  UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
