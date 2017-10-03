package main.game.model.entity;

public class AttackingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public AttackingUnitState(Direction direction, Unit unit) {
    super(unit.getUnitType().getAttackSequence(), direction, unit);
  }

  @Override
  UnitState updateState() {
    return (nextState == null && imagesComponent.readyToTransition()) ? this : nextState;
  }
}
