package main.game.model.entity;

import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class BeenHitUnitState extends AbstractUnitState {

  public BeenHitUnitState(Direction direction, UnitSpriteSheet sheet) {
    super(Sequence.HURT, direction, sheet);
  }

  @Override
  AbstractUnitState updateState() {
    return (nextState == null && imagesComponent.readyToTransition()) ? this : nextState;
  }
}
