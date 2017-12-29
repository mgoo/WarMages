package main.images;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.common.images.GameImage;
import main.common.images.GameImageBuilder;

public class ProjectileSpriteSheet {

  private final String file;
  private final List<GameImage> fireSequence;

  public ProjectileSpriteSheet(String file, ImageSequence fire) {
    this.file = file;
    this.fireSequence = this.createSequence(fire);
  }

  private List<GameImage> createSequence(ImageSequence sequenceDefinition) {
    return IntStream.range(0, sequenceDefinition.frames)
        .mapToObj(col ->
            new GameImageBuilder(file)
                .setStartX(sequenceDefinition.offsetX + col * sequenceDefinition.frameWidth)
                .setStartY(sequenceDefinition.offsetY)
                .setWidth(sequenceDefinition.frameWidth)
                .setHeight(sequenceDefinition.frameHeigh)
                .create()
        )
        .collect(Collectors.toList());
  }

  public List<GameImage> getFireSequence() {
    return fireSequence;
  }

  public static class ImageSequence {

    final int offsetX;
    final int offsetY;
    final int frameWidth;
    final int frameHeigh;
    final int frames;

    public ImageSequence(int offsetX, int offsetY, int frameWidth, int frameHeigh, int frames) {
      this.offsetX = offsetX;
      this.offsetY = offsetY;
      this.frameWidth = frameWidth;
      this.frameHeigh = frameHeigh;
      this.frames = frames;
    }

  }

}
