package main.game.model.entity;

/**
 * Item extends {@link Entity}. An item is something that can be picked up
 * and used by HeroUnit
 */
public abstract class Item extends MapEntity {
  abstract void applyTo(Unit unit);
}
