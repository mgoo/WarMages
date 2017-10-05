package main.game.model.entity;

import main.images.UnitSpriteSheet.Sequence;

public class IdleUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public IdleUnitState(Direction direction, Unit unit) {
    super(Sequence.IDLE, direction, unit);
  }

  @Override
  UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
