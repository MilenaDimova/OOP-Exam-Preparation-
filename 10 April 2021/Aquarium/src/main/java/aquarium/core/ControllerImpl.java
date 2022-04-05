package aquarium.core;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.aquariums.Aquarium;
import aquarium.entities.aquariums.FreshwaterAquarium;
import aquarium.entities.aquariums.SaltwaterAquarium;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.decorations.Ornament;
import aquarium.entities.decorations.Plant;
import aquarium.entities.fish.Fish;
import aquarium.entities.fish.FreshwaterFish;
import aquarium.entities.fish.SaltwaterFish;
import aquarium.repositories.DecorationRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller{
    private DecorationRepository decorations;
    private Collection<Aquarium> aquariums;

    public ControllerImpl() {
        this.decorations = new DecorationRepository();
        this.aquariums = new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        Aquarium aquarium;
        switch (aquariumType) {
            case "FreshwaterAquarium":
                aquarium = new FreshwaterAquarium(aquariumName);
                break;
            case "SaltwaterAquarium":
                aquarium = new SaltwaterAquarium(aquariumName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_AQUARIUM_TYPE);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;
        switch (type) {
            case "Ornament":
                decoration = new Ornament();
                break;
            case "Plant":
                decoration = new Plant();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_DECORATION_TYPE);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Decoration decoration = this.decorations.findByType(decorationType);

        if (decoration == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_DECORATION_FOUND, decorationType));
        }

        Aquarium aquarium = findAquariumByName(aquariumName);

        if (aquarium != null) {
            aquarium.addDecoration(decoration);
            this.decorations.remove(decoration);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        Fish fish;
        switch (fishType) {
            case "FreshwaterFish":
                fish = new FreshwaterFish(fishName, fishSpecies, price);
                break;
            case "SaltwaterFish":
                fish = new SaltwaterFish(fishName, fishSpecies, price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_FISH_TYPE);
        }

        Aquarium aquarium = findAquariumByName(aquariumName);

        String resultMessage = "";
        if (!aquarium.getClass().getSimpleName().contains(fishType.replace("Fish", ""))) {
            resultMessage = ConstantMessages.WATER_NOT_SUITABLE;
        } else {
            try {
                aquarium.addFish(fish);
            } catch (IllegalStateException exception) {
                resultMessage = exception.getMessage();
            }
        }
        resultMessage = String.format(ConstantMessages.SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM,
                fishType, aquariumName);

        return resultMessage;
    }

    @Override
    public String feedFish(String aquariumName) {
        Aquarium aquarium = findAquariumByName(aquariumName);
        aquarium.feed();

        return String.format(ConstantMessages.FISH_FED, aquarium.getFish().size());
    }

    @Override
    public String calculateValue(String aquariumName) {
        Aquarium aquarium = findAquariumByName(aquariumName);
        double allFishPrice = aquarium.getFish().stream().mapToDouble(Fish::getPrice).sum();
        double allDecorationsPrice = aquarium.getDecorations().stream().mapToDouble(Decoration::getPrice).sum();

        return String.format(ConstantMessages.VALUE_AQUARIUM, aquariumName, allFishPrice + allDecorationsPrice);
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();
        for (Aquarium aquarium : this.aquariums) {
            builder.append(aquarium.getInfo());
            builder.append(System.lineSeparator());
        }

        return builder.toString().trim();
    }

    private Aquarium findAquariumByName(String name) {
        return this.aquariums.stream().filter(a -> a.getName().equals(name))
                .findFirst().orElse(null);
    }
}
