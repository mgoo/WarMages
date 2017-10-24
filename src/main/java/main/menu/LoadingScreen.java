package main.menu;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import main.common.menu.Menu;
import main.common.menu.MenuController;
import main.common.menu.MenuFileResources;
import main.menu.generators.ScriptFileGenerator;

/**
 * A Loading screen for when the game is loading.
 * @author Andrew McGhie
 */
public class LoadingScreen extends Menu {

  private final Runnable loader;
  private final Configuration cfg;

  public LoadingScreen(Runnable loader, Configuration cfg) {
    this.loader = loader;
    this.cfg = cfg;
    this.menuController = new MenuController() {}; // Empty Controller
  }

  @Override
  public String getHtml() {
    try {
      Template template = this.cfg.getTemplate("loading_screen.ftl");
      Writer writer = new CharArrayWriter();
      template.process(null, writer);
      return writer.toString();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
      return "";
    }
  }

  @Override
  public String getStyleSheetLocation() {
    return new File(MenuFileResources.LOADING_SCREEN_CSS.getPath()).toURI().toString();
  }

  @Override
  public String[] getScripts() {
    return new String[]{
        new ScriptFileGenerator()
            .setFile(MenuFileResources.JQUERY_JS.getPath())
            .getScript(),
        new ScriptFileGenerator()
            .setFile(MenuFileResources.BOOTSTRAP_JS.getPath())
            .getScript()};
  }

  @Override
  public void onLoad() {
    new Thread(this.loader).start();
  }
}
