package main.menu.generators;

import java.util.Optional;

/**
 * sets the goal text when in game.
 *
 * @author Andrew McGhie
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
