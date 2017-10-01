package main.menu.generators;

import java.util.Optional;

/**
 * This is the base class used by javascript generators.
 * Their main point to try to separate the javascript code from the java code
 * while still allowing dynamic javascript to be used.
 *
 * @author Andrew McGhie
 */
public abstract class ScriptGenerator {
  private /*@ nullable;spec_public @*/ String preloadScript;
  private /*@ spec_public @*/ boolean locked = false;
  /*@ public invariant this.locked ==> this.preloadScript != null; @*/

  /*@
    ensures \result == this.locked;
   @*/
  public /*@ pure @*/ boolean isLocked() {
    return this.locked;
  }

  /*@
      requires this.preloadScript != null;
      assignable locked;
      ensures this == \result;
      ensures this.preloadScript != null ==> \result.locked;
    also
      requires this.preloadScript == null;
      assignable \nothing
      ensures this.locked == false;
      signals_only IllegalStateException;
   @*/
  public /*@ non_null @*/ ScriptGenerator lock() {
    if (this.preloadScript == null) {
      throw new IllegalStateException("Cannot lock if script has not being preloaded");
    }
    this.locked = true;
    return this;
  }

  /**
   * Caches the script that is generated at this point.
   */
  /*@
  normal_behavior
    requires !this.isLocked();
    assignable preloadScript;
    ensures this.preloadScript != null;
    ensures this == \result;
  also
  exceptional_behavior
    requires this.isLocked();
    assignable \nothing;
    signals_only IllegalStateException;
   @*/
  public /*@ non_null @*/ ScriptGenerator preload() {
    if (this.isLocked()) {
      throw new IllegalStateException("you cannot preload a locked generator");
    }
    this.preloadScript = this.load().orElse("");
    return this;
  }

  /**
   * Resets the cache so a new script can be loaded if need be.
   */
  /*@
  normal_behavior
    requires !this.isLocked();
    assignable this.preloadScript;
    ensures this.preloadScript == null;
    ensures this == \result;
  also
  exceptional_behavior
    requires this.isLocked();
    assignable \nothing
    signals_only IllegalStateException;
   @*/
  public /*@ non_null @*/ ScriptGenerator invalidateCache() {
    if (this.isLocked()) {
      throw new IllegalStateException("you cannot invalidate a locked generator");
    }
    this.preloadScript = null;
    return this;
  }

  /**
   * Gets a string that is the script ready to be loaded into a javascript engine.
   */
  /*@
    requires this.preloadScript != null;
    ensures \result.equals(this.preloadScript);
  also
    requires this.preloadScript == null;
    ensures \result != null;
   @*/
  public /*@ pure; non_null @*/ String getScript() {
    if (this.preloadScript != null) {
      return this.preloadScript;
    }
    return this.load().orElse("");
  }

  /**
   * loads the script.
   */
  abstract /*@ non_null; spec_public @*/ Optional<String> load();
}
