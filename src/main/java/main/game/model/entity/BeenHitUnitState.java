package main.game.model.entity;

import main.images.UnitSpriteSheet.Sequence;

public class BeenHitUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public BeenHitUnitState(Direction direction, Unit unit) {
    super(Sequence.HURT, direction, unit);
  }

  @Override
  UnitState updateState() {
    return (nextState == null && imagesComponent.readyToTransition()) ? this : nextState;
  }
}
