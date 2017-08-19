package main;

/**
 * Delete this when you put your assignments here.
 */
public class Main {

  // Should throw warning about naming
  class NonException{}

  // SHould throw IC_SUPERCLASS_USES_SUBCLASS_DURING_INITIALIZATION error ?
public static class CircularClassInitialization {
    static class InnerClassSingleton extends CircularClassInitialization {
        static InnerClassSingleton singleton = new InnerClassSingleton();
    }

    static CircularClassInitialization foo = InnerClassSingleton.singleton;
}

  public static void main(String[] args) {
    // Poor indenting
    // Long line
      System.out.println("The program ran!****************************************************************************************************");
  }
}
