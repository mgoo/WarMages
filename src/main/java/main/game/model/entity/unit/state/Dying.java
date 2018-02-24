//package main.game.model.entity.unit.state;
//
//import main.game.model.entity.unit.DefaultUnit;
//import main.game.model.world.World;
//import main.images.Animation;
//
///**
// * Used for representing the dying unitAnimation when a unit is dead.
// * @author chongdyla
// */
//public class Dying extends UnitState {
//
//  private static final long serialVersionUID = 1L;
//
//  public Dying(DefaultUnit unit) {
//    super(
//        ,
//        unit
//    );
//  }
//
//  @Override
//  public void tick(Long timeSinceLastTick, World world) {
//    super.tick(timeSinceLastTick, world);
//
//    if (unitAnimation.isFinished()) {
//      world.onEnemyKilled(unit);
//    }
//  }
//
//  @Override
//  public double getCurrentAngle() {
//    return 0;
//  }
//
//  @Override
//  public UnitState updateState() {
//    return this;
//  }
//
//
//}
