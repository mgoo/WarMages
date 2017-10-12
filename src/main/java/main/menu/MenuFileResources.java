package main.menu;

/**
 * All the html/css/js resources used on the web engine (currently only contains stuff for menus).
 */
public enum MenuFileResources {
  JQUERY_JS("resources/html/js/jquery-3.2.1.min.js"),
  BOOTSTRAP_JS("resources/html/js/bootstrap.min.js"),

  HUD_HTML("resources/html/hud.html"),
  HUD_JS("resources/html/js/hud.js"),
  HUD_CSS("resources/html/css/hud.css"),

  MAIN_MENU_HTML("resources/html/main_menu.html"),
  MAIN_MENU_CSS("resources/html/css/main_menu.css");

  private final String path;

  MenuFileResources(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
