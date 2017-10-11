package main.game.model.entity.unit.state;

import main.common.Unit;
import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.state.UnitState;

public class WalkingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public WalkingUnitState(Unit unit) {
    super(Sequence.WALK, unit);
  }

  @Override
  public UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
