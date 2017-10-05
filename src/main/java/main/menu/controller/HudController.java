package main.menu.controller;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import main.Main;
import main.game.model.entity.usables.Ability;
import main.game.model.entity.Entity;
import main.game.model.entity.usables.Item;
import main.menu.MainMenu;

/**
 * Controls the hud when in game. Receives the click events when in game.
 *
 * @author Andrew McGhie
 */
public class HudController implements MenuController {

  final Main main;
  final MainMenu mainMenu;

  public HudController(Main main, MainMenu mainMenu) {
    this.main = main;
    this.mainMenu = mainMenu;
  }

  /**
   * Assumes that the image is png formatted.
   */
  private String formatImageForHtml(RenderedImage image) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", baos);
    } catch (IOException e) {
      // unreachable
    }
    byte[] bytes = baos.toByteArray();
    String imageMimeType = "image/png";
    String dataUri =
        "data:" + imageMimeType + ";base64," + DatatypeConverter.printBase64Binary(bytes);
    return dataUri;
  }

  /**
   * Triggers event for when the icon of an entity is clicked.
   */
  public void entityIconBtn(Entity entity) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when the icon of an ability is clicked.
   */
  public void abilityIconBtn(Ability ability) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when the icon of an item is clicked.
   */
  public void itemIconBtn(Item item) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onLeftClick(int x, int y) {
    try {
      // TODO Handle left click here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onRightClick(int x, int y) {
    try {
      // TODO handle Right click here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the ui enters a state that the game should pause.
   */
  public void pause() {
    try {
      // TODO handle pause here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the ui enters a state that the game should unpause.
   */
  public void resume() {
    try {
      // TODO handle resume
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the quit button was pressed.
   */
  public void quitBtn() {
    try {
      // TODO handle exiting the game
      this.main.loadMenu(this.mainMenu);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * handles when the save button was pressed.
   */
  public void saveBtn() {
    try {
      // TODO handle going to the save menu and saving the game
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
