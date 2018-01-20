package main.game.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import main.game.model.entity.usable.Item;
import main.util.Config;

public class ItemView extends EntityView {

  private static final int BACKGROUND_PADDING = 4;

  private final Item item;

  ItemView(Config config, Item item) {
    super(config, item);
    this.item = item;
  }

  @Override
  public void drawDecorationsOntop(Graphics2D g, int x, int y, int width, int height) {
    LineMetrics lineMetrics =
        g.getFont().getLineMetrics(this.item.getName(), g.getFontRenderContext());
    int stringHeight = (int)lineMetrics.getHeight();
    int stringWidth = g.getFontMetrics().stringWidth(this.item.getName());

    int xpos = x + width / 2 - stringWidth / 2;

    g.setColor(new Color(40, 40, 40, 160));
    g.fillRect(
        xpos - BACKGROUND_PADDING,
        y - BACKGROUND_PADDING,
        stringWidth + BACKGROUND_PADDING * 2,
        stringHeight + BACKGROUND_PADDING * 2
    );
    g.setColor(new Color(240, 240, 240, 255));
    g.setStroke(new BasicStroke(2));
    g.drawRect(
        xpos - BACKGROUND_PADDING,
        y - BACKGROUND_PADDING,
        stringWidth + BACKGROUND_PADDING * 2,
        stringHeight + BACKGROUND_PADDING * 2
    );
    g.drawString(
        this.item.getName(),
        xpos + BACKGROUND_PADDING,
        y + (stringHeight + BACKGROUND_PADDING * 2) / 2);
  }
}
