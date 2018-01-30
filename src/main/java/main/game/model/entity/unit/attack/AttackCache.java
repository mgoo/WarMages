package main.game.model.entity.unit.attack;

import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.AttackGroundAbility;
import main.game.model.entity.usable.AttackUnitAbility;
import main.game.model.entity.usable.BaseAbility;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet.Sequence;

public class AttackCache {

  public static final Attack BASIC_ARROW = new Attack(
      "resources/scripts/basicArrow.js",
      5,
      20,
      0.7,
      Sequence.SHOOT,
      AttackType.MISSILE,
      18
  );
  public static final Attack DAGGER = new Attack(
      "resources/scripts/instantDamage.js",
      0.02,
      14,
      0.66,
      Sequence.SLASH,
      AttackType.SLASH,
      15
  );
  public static final Attack SPEAR = new Attack(
      "resources/scripts/instantDamage.js",
      0.2,
      16,
      0.625,
      Sequence.THRUST,
      AttackType.STAB,
      10
  );
  public static final Attack FIREBALL = new Attack(
      "resources/scripts/fireball.js",
      4,
      30,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.MAGIC,
      60
  );
  public static final Attack ICEBALL = new Attack(
      "resources/scripts/iceBall.js",
      4,
      30,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.MAGIC,
      60
  );
  public static final Attack LASER = new Attack(
      "resources/scripts/laser.js",
      3,
      1,
      0,
      Sequence.SPELL_CAST,
      AttackType.MAGIC,
      1
  );
  public static final Attack ICE_LASER = new Attack(
      "resources/scripts/laserWhite.js",
      3,
      1,
      0,
      Sequence.SPELL_CAST,
      AttackType.MAGIC,
      1
  );

  /**
   * Creates a new Firebolt ability.
   */
  public static BaseAbility makeFirebolt() {
    return new AttackGroundAbility(
        GameImageResource.FIREBALL_ICON.getGameImage(),
        20,
        2,
        "Fires an explosive bolt of fire",
        "resources/scripts/firebolt.js",
        4,
        10,
        0.66,
        Sequence.SPELL_CAST,
        AttackType.MAGIC,
        200
    );
  }

  public static final BaseAbility LIGHTNING = new AttackUnitAbility(
      GameImageResource.LIGHTING_ICON.getGameImage(),
      20,
      "Smite a unit with a bolt from heaven",
      "resources/scripts/lightningBolt.js",
      4,
      30,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.MAGIC,
      400
  );

  public static final BaseAbility HEAL_SPELL = new AttackUnitAbility(
      GameImageResource.HEAL_SPELL_ICON.getGameImage(),
      15,
      "Heals a unit instantly",
      "resources/scripts/healSpell.js",
      4,
      10,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.HEAL,
      100
  );

  /**
   * Makes a new small potion heal ability.
   */
  public static BaseAbility makeHealPotionSmall() {
    return new AttackUnitAbility(
      GameImageResource.SMALL_POTION_ICON.getGameImage(),
      .01,
      "Heals a unit instantly",
      "resources/scripts/healSpell.js",
      4,
      0,
      0,
      Sequence.SPELL_CAST,
      AttackType.HEAL,
      50,
      Attack.INSTANT_DURATION,
      3
    );
  }

  public static final BaseAbility HEAL_POTION_SMALL = new AttackUnitAbility(
      GameImageResource.SMALL_POTION_ICON.getGameImage(),
      .01,
      "Heals a unit instantly",
      "resources/scripts/healSpell.js",
      4,
      0,
      0,
      Sequence.SPELL_CAST,
      AttackType.HEAL,
      50,
      Attack.INSTANT_DURATION,
      3
  );

  public static final BaseAbility DAMAGE_BUFF = new AttackUnitAbility(
      GameImageResource.RING_ICON.getGameImage(),
      20,
      "Buff the damage on a friendly unit",
      "resources/scripts/damageBuff.js",
      4,
      10,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.BUFF,
      100,
      40
  );
  public static final BaseAbility TURRET = new AttackGroundAbility(
      GameImageResource.FAIRYFIRE_ICON.getGameImage(),
      60,
      0,
      "Spawn a turret to kill everything",
      "resources/scripts/turret.js",
      1,
      20,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.MAGIC
  );


  public static final Ability TEST_HEAL = new AttackUnitAbility(
      GameImageResource.RING_ICON.getGameImage(),
      20,
      "Buff the damage on a friendly unit",
      "resources/scripts/healSpell.js",
      4,
      1,
      0.85,
      Sequence.SPELL_CAST,
      AttackType.MAGIC,
      1
  );

}
