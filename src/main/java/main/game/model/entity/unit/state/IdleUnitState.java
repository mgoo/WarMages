package main.game.model.entity.unit.state;

import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.DefaultUnit;

/**
 * Idle state for Unit.
 *
 * @author paladogabr
 */
public class IdleUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public IdleUnitState(DefaultUnit unit) {
    super(Sequence.IDLE, unit);
  }

  @Override
  public UnitState updateState() {
    return (requestedNextState == null) ? this : requestedNextState;
  }
}
