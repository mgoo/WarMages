package main.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import main.Main;
import main.common.GameView;
import main.common.entity.Unit;
import main.common.entity.Usable;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Item;
import main.common.GameModel;
import main.common.util.Config;
import main.common.menu.Menu;
import main.common.menu.MenuFileResources;
import main.common.images.ImageProvider;
import main.menu.controller.HudController;
import main.menu.controller.HudController.SaveFunction;
import main.menu.generators.GoalTextGenerator;
import main.menu.generators.ScriptFileGenerator;
import main.common.Renderer;

/**
 * The definitions of the file paths to the html file for the Heads Up Display.
 *
 * @author Andrew McGhie
 */
public class Hud extends Menu {

  private final Main main;
  private final GameModel gameModel;
  private final ImageProvider imageProvider;
  private final Config config;
  private final GoalTextGenerator goalScript = new GoalTextGenerator();
  private List<Unit> unitsShowingIcons = new CopyOnWriteArrayList<>();
  private List<Ability> abilitiesShowingIcons = new CopyOnWriteArrayList<>();
  private List<Item> itemsShowingIcons = new CopyOnWriteArrayList<>();
  private List<Item> abilitiesInCoolDown = new CopyOnWriteArrayList<>();
  private List<Item> itemsInCoolDown = new CopyOnWriteArrayList<>();

  public Hud(Main main,
             MainMenu mainMenu,
             GameView gameView,
             Renderer renderer,
             GameModel gameModel,
             ImageProvider imageProvider,
             SaveFunction saveFunction,
             Config config) {
    this.main = main;
    this.config = config;
    this.gameModel = gameModel;
    this.imageProvider = imageProvider;
    this.menuController = new HudController(main,
        mainMenu,
        gameView,
        renderer,
        saveFunction,
        config);
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
    Collection<Unit> selectedUnits = this.gameModel.getUnitSelection();
    selectedUnits.stream()
        .filter(unit -> !this.unitsShowingIcons.contains(unit))
        .forEach(unit -> {
          addUnitIcon(unit);
          this.unitsShowingIcons.add(unit);
        });
    this.unitsShowingIcons.stream()
        .filter(unit -> !selectedUnits.contains(unit))
        .forEach(unit -> {

          int index = this.unitsShowingIcons.indexOf(unit);
          this.main.callJsFunction("removeUnitIcon", index);
          this.unitsShowingIcons.remove(unit);
        });


    Collection<Ability> visibleAbilities;
    if (gameModel.getUnitSelection().contains(this.gameModel.getHeroUnit())) {
      visibleAbilities = this.gameModel.getHeroUnit().getAbilities();
    } else {
      visibleAbilities = Collections.emptySet();
    }
    visibleAbilities.stream()
        .filter(ability -> !this.abilitiesShowingIcons.contains(ability))
        .forEach(ability -> {
          addAbilityIcon(ability);
          this.abilitiesShowingIcons.add(ability);
        });
    this.abilitiesShowingIcons.stream()
        .filter(ability -> !visibleAbilities.contains(ability))
        .forEach(ability -> {
          int index = this.abilitiesShowingIcons.indexOf(ability);
          this.main.callJsFunction("removeAbilityIcon", index);
          this.abilitiesShowingIcons.remove(ability);
        });


    Collection<Item> visibleItems;
    if (gameModel.getUnitSelection().contains(this.gameModel.getHeroUnit())) {
      visibleItems = this.gameModel.getHeroUnit().getItemInventory();
    } else {
      visibleItems = Collections.emptySet();
    }
    visibleItems.stream()
        .filter(item -> !this.itemsShowingIcons.contains(item))
        .forEach(item -> {
          addItemIcon(item);
          this.itemsShowingIcons.add(item);
        });
    this.itemsShowingIcons.stream()
        .filter(item -> !visibleItems.contains(item))
        .forEach(item -> {
          int index = this.itemsShowingIcons.indexOf(item);
          this.main.callJsFunction("removeItemIcon", index);
          this.itemsShowingIcons.remove(item);
        });

    // Add cooldown bars
    visibleAbilities.stream()
        .filter(ability -> ability.getCoolDownProgress() < 0.999
            && !this.abilitiesInCoolDown.contains(ability))
        .forEach(ability -> {
          int index = this.abilitiesShowingIcons.indexOf(ability);
          int time = ability.getCoolDownTicks() * config.getGameModelDelay();
          this.main.callJsFunction("setAbilityIconToCoolDown", index, time);
        });
    this.abilitiesInCoolDown.stream()
        .filter(ability -> ability.getCoolDownProgress() > 0.999)
        .forEach(ability -> this.abilitiesInCoolDown.remove(ability));

    visibleItems.stream()
        .filter(item -> item.getCoolDownProgress() < 0.999
            && !this.itemsInCoolDown.contains(item))
        .forEach(item -> {
          int index = this.itemsShowingIcons.indexOf(item);
          int time = item.getCoolDownTicks() * config.getGameModelDelay();
          this.main.callJsFunction("setItemIconToCoolDown", index, time);
        });
    this.itemsInCoolDown.stream()
        .filter(item -> item.getCoolDownProgress() > 0.999)
        .forEach(item -> this.itemsInCoolDown.remove(item));
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

      String entityIcon = this.formatImageForHtml(icon);
      this.main.callJsFunction("addUnitIcon", entityIcon, unit, generateUnitTooltip(unit));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addItemIcon(Item item) {
    try {
      this.addIcon("addItemIcon", this.getIcon(item), item);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addAbilityIcon(Ability ability) {
    try {
      this.addIcon("addAbilityIcon", this.getIcon(ability), ability);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private BufferedImage getIcon(Usable usable) throws IOException {
    BufferedImage baseIcon = usable.getIconImage().load(this.imageProvider);

    BufferedImage icon = new BufferedImage(baseIcon.getWidth(),
        baseIcon.getHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = ((Graphics2D) icon.getGraphics());

    // @HACK to top the icon background from flashing from the lag of css appyling
    if (usable instanceof Item) {
      g.setColor(Color.decode("#433ab9"));
    }
    if (usable instanceof Ability) {
      g.setColor(Color.decode("#9c8d46"));
    }

    g.fillRect(0, 0, icon.getWidth(), icon.getHeight());
    g.drawImage(baseIcon, 0, 0, null);
    if (usable.isReadyToBeUsed()) {
      return icon;
    }

    double progress = usable.getCoolDownProgress();
    Arc2D arc = new Double(0, 0,
        icon.getWidth(), icon.getHeight(),
        90, (360 - (int)(360 * progress)) % 360,
        Arc2D.PIE);
    g.setColor(new Color(0,0,0, 155));
    g.fill(arc);

    return icon;
  }

  private void addIcon(String method, BufferedImage image, Object entity) {
    try {
      String entityIcon = this.formatImageForHtml(image);
      this.main.callJsFunction(method, entityIcon, entity);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  private String generateUnitTooltip(Unit unit) {
    int damage = (int)unit.getUnitType().getBaseAttack().getModifiedDamage(unit);
    int attackSpeed = (int)unit.getUnitType().getBaseAttack().getModifiedAttackSpeed(unit);
    int range = (int)unit.getUnitType().getBaseAttack().getModifiedRange(unit);
    int maxHealth = (int)unit.getMaxHealth();
    int currentHealth = (int)unit.getHealth();
    double movementSpeed = Math.round(unit.getSpeed() * 100.0) / 100.0;

    return "<b>Health</b>: " + currentHealth + "/" + maxHealth + "<br>"
        + "<b>Damage</b>: " + damage + "<br>"
        + "<b>Range</b>: " + range + "<br>"
        + "<b>Attack Speed</b>: " + attackSpeed + "<br>"
        + "<b>Movement Speed</b>: " + movementSpeed;
  }

  /**
   * Assumes that the image is png formatted.
   */
  private String formatImageForHtml(RenderedImage image) throws UnsupportedEncodingException {
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
    dataUri = dataUri.replace("\'", "\\'");
    dataUri = dataUri.replace("\\", "\\\\");
    return dataUri;
  }
}
