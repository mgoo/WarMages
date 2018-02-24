package main.game.model.entity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import main.game.model.data.dataObject.ImageData;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.Animation;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Concrete implementation of Projectile.
 *
 * @author paladogabr
 */
public class DefaultProjectile extends DefaultEntity implements Projectile {

  private static final long serialVersionUID = 1L;

  private static final double IMPACT_DISTANCE = 0.01;
  private static final double ANIMATION_SPEED = 0.4;

  private final Unit owner;
  private final Targetable target;
  private final Attack attack;
  private final Animation flyAnimation;
  private final Animation impactAnimation;
  private final MapSize impactSize;
  private final double moveDistancePerTick;
  private final Invocable engine;

  private double angle = 0;
  private boolean hasHit = false;

  /**
   * Constructor takes the starting coordinates of the projectile, the size,
   * and the target of the projectile.
   * @param topLeft at start of projectile path.
   * @param size of projectile.
   * @param owner the unit that fired the projectile
   * @param flyAnimation images to show when the prjectile is flying
   * @param impactAnimation images to show when the projectile hits
   * @param moveDistancePerTick distance to be moved per tick.
   */
  public DefaultProjectile(
      MapPoint topLeft,
      MapSize size,
      Unit owner,
      Targetable target,
      Attack attack,
      Animation flyAnimation,
      Animation impactAnimation,
      MapSize impactSize,
      double moveDistancePerTick
  ) {
    super(topLeft, size);
    this.owner = owner;
    this.target = target;
    this.attack = attack;
    this.flyAnimation = flyAnimation;
    this.impactAnimation = impactAnimation;
    this.impactSize = impactSize;
    this.moveDistancePerTick = moveDistancePerTick;

    // TODO allow for projectiles to do other stuff than instant damaged
    // eg pass the script location in as a string.
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    try {
      engine.eval(new FileReader("resources/scripts/instantDamage.js"));
    } catch (ScriptException | FileNotFoundException e) {
      e.printStackTrace();
    }
    this.engine = (Invocable) engine;
  }

  @Override
  public ImageData getImage() {
    return this.flyAnimation.getImage(this.angle);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (hasHit) {
      throw new IllegalStateException();
    }

    this.flyAnimation.tick();

    double distToTarget = getDistanceToTarget();
    // 0.5 if we move halfway there, 1 or greater if we move all the way there, etc
    double percentage = moveDistancePerTick / distToTarget;

    if (percentage >= 1) {
      percentage = 1; // teleport there because we are close enough
    }

    this.angle = this.target.getLocation().angleTo(this.getCentre());
    translatePosition(
        percentage * (this.target.getLocation().x - getCentre().x),
        percentage * (this.target.getLocation().y - getCentre().y)
    );


    if (getDistanceToTarget() <= IMPACT_DISTANCE) {
      hasHit = true;
      hitTarget(world);
    }
  }

  @Override
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

  @Override
  public boolean hasHit() {
    return hasHit;
  }

  @Override
  public void hitTarget(World world) {
    try {
      this.engine.invokeFunction("apply", this.owner, this.target, this.attack, world);
    } catch (ScriptException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    world.removeProjectile(this);
    AnimationEntity hitMarker = new AnimationEntity(
        this.getTopLeft(),
        this.impactSize,
        this.impactAnimation,
        this.angle
    );
    hitMarker.setLayer(1); // Same Layer as the projectile
    world.addStaticEntity(hitMarker);
  }

  @Override
  public double getDistanceToTarget() {
    return getCentre().distanceTo(target.getLocation());
  }

  @Override
  public int getLayer() {
    return 1;
  }
}
