package main.game.model.entity;

public enum Team {
  PLAYER {
    @Override
    public boolean canAttackOtherTeam(Team otherTeam) {
      return true;
    }
  },
  ENEMY {
    @Override
    public boolean canAttackOtherTeam(Team otherTeam) {
      return true;
    }
  };

  public boolean canAttack(Team team) {
    if (team == this) {
      return false;
    }

    return canAttackOtherTeam(team);
  }

  protected abstract boolean canAttackOtherTeam(Team otherTeam);
}
