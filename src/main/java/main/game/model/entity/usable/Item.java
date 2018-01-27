package main.game.model.entity.usable;

import main.game.model.entity.MapEntity;

/**
 * Item extends {@link MapEntity}. An item is something that can be picked up and used by HeroUnit.
 * For now, all items have an {@link Ability} (through decoration).
 *
 * <p>
 * Functionality should be delegated to the {@link Ability}.
 * </p>
 * @author chongdyla
 */
public interface Item extends MapEntity {

  String getName();

  Ability getAbility();
}
