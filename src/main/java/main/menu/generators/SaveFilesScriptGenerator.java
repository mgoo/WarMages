package main.menu.generators;

import java.util.Collection;

/**
 * Generates a script that sets the values of the files in the load menu.
 *
 * @author Andrew McGhie
 */
public class SaveFilesScriptGenerator extends ScriptGenerator {

  private Collection<String> files;
  private String perLoadedScript;

  public SaveFilesScriptGenerator setData(Collection<String> files) {
    this.files = files;
    return this;
  }

  @Override
  String load() {
    assert files != null : "The data (filenames) has not being set. Please call setData(String[])";

    StringBuilder script = new StringBuilder("$('#files').html('");
    files.forEach(fileName -> {
      script.append(fileName);
      script.append(",");
    });
    script.append("')");
    return script.toString();
  }
}
