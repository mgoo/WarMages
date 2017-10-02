package main.menu.generators;

import java.util.Collection;
import java.util.Optional;

/**
 * Generates a script that sets the values of the files in the load menu.
 *
 * @author Andrew McGhie
 */
public class SaveFilesScriptGenerator extends ScriptGenerator {

  private /*@ nullable; spec_public @*/ Collection<String> files;

  /*@
    ensures this.files != null;
    ensures \result == this;
   @*/
  public SaveFilesScriptGenerator setData(/*@ non_null @*/ Collection<String> files) {
    this.files = files;
    return this;
  }

  /*@
    requires this.files != null;
    ensures \result.isPresent();
  also
    requires this.files == null;
    signals_only IllegalStateException;
   @*/
  @Override
  Optional<String> load() {
    if (this.files == null) {
      throw new IllegalStateException("Files need to be set before generating script");
    }
    StringBuffer script = new StringBuffer("$('#files').html('");
    this.files.forEach(fileName -> {
      script.append(fileName);
      script.append(",");
    });
    script.append("')");
    return Optional.of(script.toString());
  }
}
