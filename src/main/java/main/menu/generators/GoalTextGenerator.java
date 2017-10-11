package main.menu.generators;

import java.util.Optional;

/**
 * Created by mgoo on 10/10/17.
 */
public class GoalTextGenerator extends ScriptGenerator {

  private String text = "";

  public GoalTextGenerator setText(String text) {
    this.text = text;
    return this;
  }

  @Override
  Optional<String> load() {
    return Optional.of("$('#goal').html('" + text + "');");
  }
}
