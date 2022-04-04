package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.bags.Bag;
import spaceStation.models.planets.Planet;

import java.util.ArrayList;
import java.util.Collection;

public class MissionImpl implements Mission{

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        for (Astronaut astronaut : astronauts) {
            Bag bag = astronaut.getBag();
            Collection<String> items = new ArrayList<>(planet.getItems());
            for (String item : items) {
                if (!astronaut.canBreath()) {
                    break;
                }
                bag.getItems().add(item);
                planet.getItems().remove(item);
                astronaut.breath();
            }
        }
    }
}
