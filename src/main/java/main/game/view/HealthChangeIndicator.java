package main.game.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.util.Collections;
import main.renderer.Drawable;

public class HealthChangeIndicator implements Drawable {

  private final int amount;
  private double xPercent;
  private int yOffset;
  private final UnitView unitView;
  private Color color;
  private int red;
  private int green;
  private int blue;
  private final long ticksToShow = 40;
  private long ticksShown = 0;

  HealthChangeIndicator(UnitView unitView, double amount, Color color) {
    this.unitView = unitView;
    this.color = color;
    this.red = color.getRed();
    this.green = color.getGreen();
    this.blue = color.getBlue();
    this.amount = (int)Math.abs(amount);
    this.yOffset = 0;
    this.xPercent = Math.random();
  }

  @Override
  public void draw(Graphics2D g, int viewX, int viewY, int viewWidth, int viewHeight) {
    Font oldFont = g.getFont();
    Font newFont = oldFont.deriveFont(
        Collections.singletonMap(TextAttribute.WEIGHT, 4f)
    );
    newFont = newFont.deriveFont(18f);
    g.setFont(newFont);
    g.setColor(this.color);
    g.drawString(
        Integer.toString(this.amount),
        viewX + (int)(this.xPercent * viewWidth),
        viewY +  yOffset);
    g.setFont(oldFont);
  }

  @Override
  public void onTick() {
    this.yOffset -= Math.random() * 5;
    ticksShown++;
    int alpha = (int)((1d - (double)ticksShown / (double)ticksToShow) * 255);
    if (alpha < 0) {
      alpha = 0;
    }
    this.color = new Color(this.red, this.green, this.blue, alpha);
    if (this.ticksShown >= this.ticksToShow) {
      unitView.removeHealthChangeIndicator(this);
    }
  }
}
