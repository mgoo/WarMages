package main.menu.generators;

/**
 * This is the base class used by javascript generators.
 * Their main point to try to separate the javascript code from the java code
 * while still allowing dynamic javascript to be used.
 *
 * @author Andrew McGhie
 */
public abstract class ScriptGenerator {
  String preloadScript;

  /**
   * Caches the script that is generated at this point.
   */
  public ScriptGenerator preload() {
    this.preloadScript = this.load();
    return this;
  }

  /**
   * Resets the cache so a new script can be loaded if need be.
   */
  public ScriptGenerator invalidateCache() {
    this.preloadScript = null;
    return this;
  }

  /**
   * Gets a string that is the script ready to be loaded into a javascript engine.
   */
  public String getScript() {
    if (this.preloadScript == null) {
      return this.load();
    } else {
      return this.preloadScript;
    }
  }

  /**
   * loads the script.
   */
  abstract String load();
}
