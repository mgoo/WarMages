package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public class Unit extends Entity implements Damageable, Attackable {
  private static int startingHealth = 200;
  protected int health;
  protected final Team team;
  protected final int baselineDamage = 5;
  protected boolean isDead;

  public Unit(MapPoint position, MapSize size, Team team) {
    super(position, size);
    this.team=team;
    isDead=false;
    health = startingHealth;
  }

  @Override
  public void setImage(GameImage image) {
    if (image == null) {
      throw new NullPointerException("Parameter image cannot be null");
    }
    this.image=image;
  }

  @Override
  public void attack(Unit unit) {
    if(isDead) return;
    if(!unit.team.equals(team)){
      unit.takeDamage(baselineDamage);
    }
  }

  @Override
  public void takeDamage(int amount) {
    if(isDead) return;
    if(health-amount<0) {
      isDead=true;
    } else health-=amount;
  }

  @Override
  public void gainHealth(int amount) {
    if(isDead) return;
    health+=amount;
  }

  @Override
  public void moveY(float amount) {
    if(isDead) return;
    super.moveY(amount);
  }

  @Override
  public void moveX(float amount) {
    if(isDead) return;
    super.moveX(amount);
  }
}

