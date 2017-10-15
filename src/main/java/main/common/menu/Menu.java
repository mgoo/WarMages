package main.common.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import main.Main;

/**
 * Essentially a wrapper that brings the html and the controller together.
 *
 * @author Andrew McGhie
 */
public abstract class Menu {

  protected final Main main;
  protected MenuController menuController;

  public Menu(Main main) {
    this.main = main;
  }

  /**
   * Turns a file into a string.
   */
  protected String fileToString(String fileName) {
    try (Scanner scanner = new Scanner(new File(fileName), "utf-8")) {
      scanner.useDelimiter("\\A");
      return scanner.next();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * Gets the HTML to display on the page.
   */
  public abstract String getHtml();

  /**
   * Gets the addresses to the css files to use.
   */
  public abstract String getStyleSheetLocation();

  /**
   * Gets the scripts to run.
   */
  public abstract String[] getScripts();

  /**
   * returns the instance of the object to bind to javascript.
   */
  public MenuController getMenuController() {
    return this.menuController;
  }

  /**
   * Is Executed when the menu is loaded.
   * By default do nothing
   */
  public void onLoad() {

  }

  /**
   * Is executed when a menu is replaces.
   * By default do nothing
   */
  public void onExit() {

  }
}
