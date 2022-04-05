package aquarium.entities.aquariums;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public abstract class BaseAquarium implements Aquarium{
    private String name;
    private int capacity;
    private Collection<Decoration> decorations;
    private Collection<Fish> fish;

    protected BaseAquarium(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.decorations = new ArrayList<>();
        this.fish = new ArrayList<>();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.AQUARIUM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public int calculateComfort() {
        int sum = 0;
        for (Decoration decoration : this.decorations) {
            sum += decoration.getComfort();
        }
        return sum;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addFish(Fish fish) {
        if (this.capacity <= this.fish.size()) {
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY);
        }
        this.fish.add(fish);
    }

    @Override
    public void removeFish(Fish fish) {
        Fish fishToRemove = this.fish.stream().filter(f -> f.getName().equals(fish.getName())).findFirst().orElse(null);
        if (fishToRemove != null) {
            this.fish.remove(fishToRemove);
        }
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public void feed() {
        for (Fish fish1 : this.fish) {
            fish1.eat();
        }
    }

    @Override
    public String getInfo() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("%s (%s):", this.getName(), this.getClass().getSimpleName()));
        output.append(System.lineSeparator());
        output.append("Fish: ");
        if (this.fish.isEmpty()) {
            output.append("none");
        } else {
            output.append(this.fish.stream().map(Fish::getName).collect(Collectors.joining(" ")));
        }

        output.append(System.lineSeparator());
        output.append(String.format("Decorations: %d", this.decorations.size()));
        output.append(System.lineSeparator());
        output.append(String.format("Comfort: %d", this.getDecorations().stream().mapToInt(Decoration::getComfort).sum()));

        return output.toString();
    }

    @Override
    public Collection<Fish> getFish() {
        return Collections.unmodifiableCollection(this.fish);
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return Collections.unmodifiableCollection(this.decorations);
    }
}
