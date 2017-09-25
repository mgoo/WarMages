package main.game.model.entity;

import java.util.ArrayList;
import java.util.List;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public class Unit extends Entity implements Damageable, Attackable {
  public static int startingHealth = 200; //todo refactor for special starting health per unit
  protected int health;
  protected final Team team;
  protected final int baselineDamage = 5, startingSpeed = 5;
  protected boolean isDead;
  protected UnitSpriteSheet spriteSheet;
  protected UnitType unitType;
  protected UnitState unitState;
  protected List<GameImage> images;
  protected int imagesIdx, damageAmount, speed;

  /**
   * Constructor takes the unit's position, size, and team.
   * @param position
   * @param size
   * @param team
   */
  public Unit(MapPoint position, MapSize size, Team team, UnitSpriteSheet sheet, UnitType unitType) {
    super(position, size);
    this.team = team;
    isDead = false;
    health = startingHealth;
    speed = startingSpeed;
    spriteSheet=sheet;
    images = new ArrayList<>();
    this.unitType = unitType;
    unitState = UnitState.DEFAULT_STATE;
    unitState.setDirection(Direction.LEFT);
    images = unitType.getImagesFor(unitState, spriteSheet);
    imagesIdx = 0;
    damageAmount = baselineDamage;
  }

  public void setDamageAmount(int amount){
    assert amount>0&&amount<100;
    damageAmount=amount;
  }

  /**
   * Returns the current health of the given unit
   * @return current health of Unit
   */
  public int getCurrentHealth(){
    return health;
  }

  /**
   * Sets the Unit's state to the given state
   * @param state
   */
  private void setStateTo(UnitState state){
    Direction direction = unitState.getDirection();
    unitState = state;
    unitState.setDirection(direction);
    images = unitType.getImagesFor(unitState, spriteSheet);
    imagesIdx = 0;
  }

  public void moveTowards(Point targetPoint){

  }

  @Override
  public void tick(long timeSinceLastTick) {
    //update image
    if(imagesIdx == images.size()-1){
      //reset state back to default
      setStateTo(UnitState.DEFAULT_STATE);
    }
    image=images.get(imagesIdx);
    //update position
    switch(unitState.getDirection()){
      case UP:
        moveY(-1*speed*timeSinceLastTick);
        return;
      case DOWN:
        moveY(speed*timeSinceLastTick);
        return;
      case LEFT:
        moveX(-1*speed*timeSinceLastTick);
        return;
      case RIGHT:
        moveX(speed*timeSinceLastTick);
        return;
      default:
    }
  }

  @Override
  public void setImage(GameImage image) {
    //will not change anything
  }

  @Override
  public void attack(Unit unit) {
    if(isDead) return;
    if(team.canAttackOtherTeam(unit.team)){
      setStateTo(UnitState.ATTACKING);
      unit.takeDamage(damageAmount);
    }
  }

  @Override
  public void takeDamage(int amount) {
    if(isDead) return;
    if(health-amount < 0) {
      isDead = true;
      health=0;
    } else {
      setStateTo(UnitState.BEEN_HIT);
      health-=amount;
    }
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

