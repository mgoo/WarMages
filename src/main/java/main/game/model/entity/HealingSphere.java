package main.game.model.entity;

import main.images.GameImage;

/**
 * HealingSphere extends {@link Projectile}. It heals a target by __ hp
 */
public class HealingSphere extends Projectile {

  private final int healingAmount = 5; //todo how to set projectile heal/damage amounts?

  public HealingSphere(Unit target) {
    super(target);
  }

  @Override
  public void hits(Unit unit) {
    throw new Error("NYI");
    //    unit.gainHealth(healingAmount);
  }

  @Override
  public GameImage getImage() {
    throw new Error("NYI");
  }
}
