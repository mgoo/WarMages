package main.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import main.Main;
import main.common.util.Config;
import main.game.model.GameModel;
import main.common.entity.HeroUnit;
import main.common.entity.Unit;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Item;
import main.game.view.GameView;
import main.common.images.ImageProvider;
import main.menu.controller.HudController;
import main.menu.controller.HudController.SaveFunction;
import main.menu.generators.GoalTextGenerator;
import main.menu.generators.ScriptFileGenerator;
import main.renderer.Renderer;

/**
 * The definitions of the file paths to the html file for the Heads Up Display.
 *
 * @author Andrew McGhie
 */
public class Hud extends Menu {

  private final GameModel gameModel;
  private final ImageProvider imageProvider;
  private final Collection<Unit> selectedUnits = new ArrayList<>();
  private HeroUnit hero;
  private final GoalTextGenerator goalScript = new GoalTextGenerator();

  public Hud(Main main,
             MainMenu mainMenu,
             GameView gameView,
             Renderer renderer,
             GameModel gameModel,
             ImageProvider imageProvider,
             SaveFunction saveFunction) {
    super(main);
    this.gameModel = gameModel;
    this.imageProvider = imageProvider;
    this.menuController = new HudController(main, mainMenu, gameView, renderer, saveFunction);
  }

  @Override
  public String getHtml() {
    return this.fileToString(MenuFileResources.HUD_HTML.getPath());
  }

  @Override
  public String getStyleSheetLocation() {
    return new File(MenuFileResources.HUD_CSS.getPath()).toURI().toString();
  }

  @Override
  public String[] getScripts() {
    return new String[]{
      new ScriptFileGenerator()
          .setFile(MenuFileResources.JQUERY_JS.getPath())
          .getScript(),
      new ScriptFileGenerator()
          .setFile(MenuFileResources.BOOTSTRAP_JS.getPath())
          .getScript(),
      new ScriptFileGenerator()
          .setFile(MenuFileResources.HUD_JS.getPath())
          .getScript(),
      new ScriptFileGenerator()
          .setFile(MenuFileResources.FILE_SCRIPTS.getPath())
          .getScript()
    };
  }


  public void updateGoal(String goal) {
    this.main.executeScript(goalScript.setText(goal).getScript());
  }

  /**
   * Upate the icons that are displayed in the HUD.
   */
  public void updateIcons() {
    this.gameModel.getUnitSelection().forEach(this::addUnitIcon);
    this.main.callJsFunction("switchUnitHolder");

    if (gameModel.getUnitSelection().contains(this.gameModel.getHeroUnit())) {
      this.gameModel.getHeroUnit().getAbilities().forEach(this::addAbilityIcon);
      this.gameModel.getHeroUnit().getItemInventory().forEach(this::addItemIcon);
    }
    this.main.callJsFunction("switchAbilitiesHolder");
    this.main.callJsFunction("switchItemsHolder");
  }

  private void addUnitIcon(Unit unit) {
    try {
      BufferedImage baseIcon = unit.getIcon().load(this.imageProvider);
      BufferedImage icon = new BufferedImage(baseIcon.getWidth(),
          baseIcon.getHeight(),
          BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g = ((Graphics2D) icon.getGraphics());
      g.drawImage(baseIcon, 0, 0, null);

      // Adds the units level
      g.setColor(Color.decode("#000000"));
      g.drawString(Integer.toString(unit.getLevel()), 1, 10);

      // Adds the health bar
      g.setColor(new Color(200,200,200, 155));
      g.fillRect(0,
          icon.getHeight() - 10,
          icon.getWidth(),
          10);
      Color healthColor;
      if (unit.getHealthPercent() > 0.5) {
        healthColor = new Color(84,255, 106);
      } else if (unit.getHealthPercent() > 0.25) {
        healthColor = new Color(255, 194, 41);
      } else {
        healthColor = new Color(255, 0, 61);
      }
      g.setColor(healthColor);
      g.fillRect(0,
          icon.getHeight() - 10,
          (int)(icon.getWidth() * unit.getHealthPercent()),
          10);

      this.addIcon("addUnitIcon", icon, unit);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addItemIcon(Item item) {
    try {
      this.addIcon("addItemIcon", item.getIconImage().load(this.imageProvider), item);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addAbilityIcon(Ability ability) {
    try {
      this.addIcon("addAbilityIcon", ability.getIconImage().load(this.imageProvider), ability);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addIcon(String method, BufferedImage image, Object entity) {
    String entityIcon = this.formatImageForHtml(image);
    this.main.callJsFunction(method, entityIcon, entity);
  }


  /**
   * Assumes that the image is png formatted.
   */
  private String formatImageForHtml(RenderedImage image) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", baos);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    byte[] bytes = baos.toByteArray();
    String imageMimeType = "image/png";
    String dataUri =
        "data:" + imageMimeType + ";base64," + DatatypeConverter.printBase64Binary(bytes);
    return dataUri;
  }

}
