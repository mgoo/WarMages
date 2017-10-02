package main.game.model.entity;

import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class BeenHitUnitState extends UnitState {

  public BeenHitUnitState(Direction direction, UnitSpriteSheet sheet) {
    super(Sequence.HURT, direction, sheet);
  }

  @Override
  UnitState updateState() {
    return (nextState == null && imagesComponent.readyToTransition()) ? this : nextState;
  }
}
