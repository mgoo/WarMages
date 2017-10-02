package main.menu.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Loads Javascript from the file specified.
 *
 * @author Andrew McGhie
 */
public class ScriptFileGenerator extends ScriptGenerator {

  private String file;

  public ScriptFileGenerator setFile(String file) {
    this.file = file;
    return this;
  }

  @Override
  String load() {
    assert file != null : "Set the File path before generating the script. call setFile(String)";

    try (Scanner scanner = new Scanner(new File(this.file), "utf-8")) {
      scanner.useDelimiter("\\A");
      return scanner.next();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }
}