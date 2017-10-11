package main.game.model.entity.unit.state;

import main.common.Unit;
import main.common.images.UnitSpriteSheet.Sequence;

public class IdleUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public IdleUnitState(Unit unit) {
    super(Sequence.IDLE, unit);
  }

  @Override
  public UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
