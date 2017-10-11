package main.game.model.entity;

import main.common.images.UnitSpriteSheet.Sequence;

public class WalkingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public WalkingUnitState(Unit unit) {
    super(Sequence.WALK, unit);
  }

  @Override
  UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
