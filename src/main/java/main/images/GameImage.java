package main.images;

/**
 * Reference all of the image files in the app here by adding a new enum value.
 *
 * @see ImageProvider for loading the images
 */
public enum GameImage {
  // These are just placeholder values / files. We will add more later
  ONE("one.png"),
  TWO("two.png");

  GameImage(String filename) {
    throw new Error("NYI");
  }

  GameImage(String filename, int startX, int startY, int width, int height) {
    throw new Error("NYI");
  }
}

