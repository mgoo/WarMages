package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;

public interface ImagesComponent extends Serializable {

  void tick(Long timeSinceLastTick);

  GameImage getImage();

  boolean isReadyToTransition();

}
