package main.game.model.entity;


import java.util.List;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public enum UnitType {

  ARCHER() {
    @Override
    public List<GameImage> getImagesFor(UnitState state, UnitSpriteSheet sheet) {
      switch(state){
        case ATTACKING:
          return sheet.getImagesForSequence(Sequence.SHOOT, state.getDirection());
        case BEEN_HIT:
          return sheet.getImagesForSequence(Sequence.HURT, state.getDirection());
        case WALKING:
          return sheet.getImagesForSequence(Sequence.WALK, state.getDirection());
        case DEFAULT_STATE:
          return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
        default:
          return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
      }
    }
  },

  SWORDSMAN() {
    @Override
    public List<GameImage> getImagesFor(UnitState state, UnitSpriteSheet sheet) {
      switch(state){
        case ATTACKING:
          return sheet.getImagesForSequence(Sequence.SLASH, state.getDirection());
        case BEEN_HIT:
          return sheet.getImagesForSequence(Sequence.HURT, state.getDirection());
        case WALKING:
          return sheet.getImagesForSequence(Sequence.WALK, state.getDirection());
        case DEFAULT_STATE:
          return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
        default:
          return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
      }
    }
  },

  SPEARMAN() {
    @Override
    public List<GameImage> getImagesFor(UnitState state, UnitSpriteSheet sheet) {
    switch(state){
      case ATTACKING:
        return sheet.getImagesForSequence(Sequence.THRUST, state.getDirection());
      case BEEN_HIT:
        return sheet.getImagesForSequence(Sequence.HURT, state.getDirection());
      case WALKING:
        return sheet.getImagesForSequence(Sequence.WALK, state.getDirection());
      case DEFAULT_STATE:
        return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
      default:
        return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
    }
    }
  },

  MAGICIAN() {
    @Override
    public List<GameImage> getImagesFor(UnitState state, UnitSpriteSheet sheet) {
      switch(state){
        case ATTACKING:
          return sheet.getImagesForSequence(Sequence.SPELL_CAST, state.getDirection());
        case BEEN_HIT:
          return sheet.getImagesForSequence(Sequence.HURT, state.getDirection());
        case WALKING:
          return sheet.getImagesForSequence(Sequence.WALK, state.getDirection());
        case DEFAULT_STATE:
          return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
        default:
          return sheet.getImagesForSequence(Sequence.IDLE, state.getDirection());
      }
    }
  };

  protected abstract List<GameImage> getImagesFor(UnitState state, UnitSpriteSheet sheet);
}


