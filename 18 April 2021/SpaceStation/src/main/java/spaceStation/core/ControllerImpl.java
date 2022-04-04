package spaceStation.core;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.bags.Bag;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch (type) {
            case "Biologist" :
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }

        this.astronautRepository.add(astronaut);

        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        PlanetImpl planet = new PlanetImpl(planetName);
        planet.setItems(Arrays.asList(items));
        this.planetRepository.add(planet);

        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut astronaut = this.astronautRepository.findByName(astronautName);

        if (astronaut == null) {
            throw new IllegalArgumentException(
                    String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }

        this.astronautRepository.remove(astronaut);

        return String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        Collection<Astronaut> astronautsWithHigherOxygen = this.astronautRepository.getModels().stream()
                .filter(a -> a.getOxygen() > 60)
                .collect(Collectors.toList());

        Planet planet = this.planetRepository.findByName(planetName);

        if (astronautsWithHigherOxygen.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }

        Mission mission = new MissionImpl();
        mission.explore(planet, astronautsWithHigherOxygen);

        return String.format(ConstantMessages.PLANET_EXPLORED,
                planetName, astronautsWithHigherOxygen.stream().filter(a -> !a.canBreath()).count());
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(ConstantMessages.REPORT_PLANET_EXPLORED,
                this.planetRepository.getModels().stream().filter(p -> p.getItems().isEmpty()).count()));
        builder.append(System.lineSeparator());
        builder.append(ConstantMessages.REPORT_ASTRONAUT_INFO);
        builder.append(System.lineSeparator());
        for (Astronaut astronaut : this.astronautRepository.getModels()) {
            builder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_NAME, astronaut.getName()));
            builder.append(System.lineSeparator());
            builder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_OXYGEN, astronaut.getOxygen()));
            builder.append(System.lineSeparator());
            Bag bag = astronaut.getBag();
            if (bag.getItems().isEmpty()) {
                builder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, "none"));
            } else {
                builder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS,
                        String.join(", ", bag.getItems())));
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString().trim();
    }
}
