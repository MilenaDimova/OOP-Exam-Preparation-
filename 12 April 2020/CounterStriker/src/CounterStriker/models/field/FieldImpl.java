package CounterStriker.models.field;

import CounterStriker.models.guns.Gun;
import CounterStriker.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static CounterStriker.common.OutputMessages.*;

public class FieldImpl implements Field{

    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorists = players.stream()
                .filter(p -> p.getClass().getSimpleName().equals("Terrorist"))
                .collect(Collectors.toList());

        List<Player> counterTerrorists = players.stream()
                .filter(p -> p.getClass().getSimpleName().equals("CounterTerrorist"))
                .collect(Collectors.toList());

        while (terrorists.stream().allMatch(Player::isAlive) || counterTerrorists.stream().allMatch(Player::isAlive)) {
            for (Player terrorist : terrorists) {
                for (Player counterTerrorist : counterTerrorists) {
                    terrorist.getGun().fire();
                    int damage = terrorist.getGun().fire();
                    counterTerrorist.takeDamage(damage);
                }
            }

            for (Player counterTerrorist : counterTerrorists) {
                for (Player terrorist : terrorists) {
                    counterTerrorist.getGun().fire();
                    int damage = counterTerrorist.getGun().fire();
                    terrorist.takeDamage(damage);
                }
            }
        }

        String result = "";
        if (counterTerrorists.stream().noneMatch(Player::isAlive)) {
            result = TERRORIST_WINS;
        } else {
            result = COUNTER_TERRORIST_WINS;
        }

        return result;
    }
}
