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

  /**
   * Check with the units of this team can attack units of the given team.
   * @param team A team that you may be able to attack.
   * @return Whether this can attack team.
   */
  public boolean canAttack(Team team) {
    if (team == this) {
      return false;
    }

    return canAttackOtherTeam(team);
  }

  protected abstract boolean canAttackOtherTeam(Team otherTeam);
}
