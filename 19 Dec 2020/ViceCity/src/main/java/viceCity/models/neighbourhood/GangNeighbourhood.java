package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Player civilPlayer : civilPlayers) {
            playerShoots(civilPlayer, mainPlayer);
            if (civilPlayer.isAlive()) {
                break;
            }
        }

        for (Player civilPlayer : civilPlayers.stream().
                filter(Player::isAlive).collect(Collectors.toList())) {
            playerShoots(mainPlayer, civilPlayer);
            if (!mainPlayer.isAlive()) {
                break;
            }
        }
    }

    private void playerShoots(Player victim, Player killer) {
        for (Gun gunModel : killer.getGunRepository().getModels().stream()
                .filter(g -> g.getTotalBullets() > 0 || g.getBulletsPerBarrel() > 0).collect(Collectors.toList())) {
            Gun gun = killer.getGunRepository().find(gunModel.getName());
            while ((gun.getTotalBullets() > 0 || gun.getBulletsPerBarrel() > 0) && victim.isAlive()) {
                int damage = gun.fire();
                victim.takeLifePoints(damage);
            }
            if (!victim.isAlive()) {
                break;
            }
        }
    }
}