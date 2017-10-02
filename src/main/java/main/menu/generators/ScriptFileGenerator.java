package main.menu.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Loads Javascript from the file specified.
 *
 * @author Andrew McGhie
 */
public class ScriptFileGenerator extends ScriptGenerator {
  private /*@ spec_public @*/ String file = "";

  /*@
    requires !this.isLocked();
   @*/
  /**
   * Loads a file and locks the generator.
   */
  public /*@ non_null @*/ ScriptFileGenerator setFile(String file) {
    this.file = file;
    this.preload();
    this.lock();
    return this;
  }

  /*@
    requires !this.file.equals("");
   @*/
  @Override
  Optional<String> load() {
    try (Scanner scanner = new Scanner(new File(this.file))) {
      scanner.useDelimiter("\\A");
      return Optional.of(scanner.next());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}