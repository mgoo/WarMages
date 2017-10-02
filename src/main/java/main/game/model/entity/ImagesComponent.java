package main.game.model.entity;

import main.images.GameImage;

public interface ImagesComponent {

  void changeImage(Long timeSinceLastTick);

  GameImage getImage();

  boolean readyToTransition();

}
