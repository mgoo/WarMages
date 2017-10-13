package main.common.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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
   *
   * @param team A team that you may be able to attack.
   * @return Whether this can attack team.
   */
  public boolean canAttack(Team team) {
    if (team == this) {
      return false;
    }

    return canAttackOtherTeam(team);
  }

  /**
   * The teams that can this team can attack.
   */
  public Collection<Team> getEnemies() {
    return Arrays.stream(Team.values())
        .filter(team -> team != this)
        .filter(this::canAttack)
        .collect(Collectors.toList());
  }

  protected abstract boolean canAttackOtherTeam(Team otherTeam);
}
