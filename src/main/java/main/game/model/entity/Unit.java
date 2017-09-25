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
  protected final int baselineDamage = 5;
  protected boolean isDead;
  protected UnitSpriteSheet spriteSheet;
  protected UnitType unitType;
  protected UnitState unitState;
  protected List<GameImage> images;
  protected int imagesIdx;

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
    spriteSheet=sheet;
    images = new ArrayList<>();
    this.unitType = unitType(sheet);
    unitState = UnitState.DEFAULT_STATE;
    images = unitType.getImagesFor(unitState);
    imagesIdx = 0;
  }

  /**
   * Returns the current health of the given unit
   * @return
   */
  public int getCurrentHealth(){
    return health;
  }

  @Override
  public GameImage getImage() {
    return images.get(imagesIdx);
  }

  @Override
  public void setImage(GameImage image) {
    if (image == null) {
      throw new NullPointerException("Parameter image cannot be null");
    }
    this.image = image;
    //todo spriteSheet.getImagesForSequence(Sequence.WALK, Direction.LEFT) ?? use when changing x and y position? ASK DYLAN
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
    if(health-amount < 0) {
      isDead = true;
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

