package main.game.model.entity;

import main.images.UnitSpriteSheet;

public class AttackingUnitState extends UnitState {

  public AttackingUnitState(Direction direction, UnitSpriteSheet sheet, UnitType type) {
    super(type.getAttackSequence(), direction, sheet);
  }

  @Override
  UnitState updateState() {
    return (nextState == null && imagesComponent.readyToTransition()) ? this : nextState;
  }
}
