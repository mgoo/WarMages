package main.game.model.entity.unit.state;

import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.DefaultUnit;

/**
 * Walking state for a Unit.
 *
 * @author paladogabr
 */
public class WalkingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public WalkingUnitState(DefaultUnit unit) {
    super(Sequence.WALK, unit);
  }

  @Override
  public UnitState updateState() {
    return (nextState == null) ? this : nextState;
  }
}
