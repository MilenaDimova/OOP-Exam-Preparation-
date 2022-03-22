package catHouse.entities.houses;

import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;

import static catHouse.common.ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT;
import static catHouse.common.ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY;

public abstract class BaseHouse implements House {
    private String name;
    private int capacity;
    private Collection<Toy> toys;
    private Collection<Cat> cats;

    protected BaseHouse(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.toys = new ArrayList<Toy>();
        this.cats = new ArrayList<Cat>();
    }

    @Override
    public int sumSoftness() {
        return toys.stream().mapToInt(Toy::getSoftness).sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (capacity <= cats.size()) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY_FOR_CAT);
        }

        cats.add(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public void feeding() {
        cats.stream().forEach(Cat::eating);
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s %s:", name, this.getClass().getSimpleName()));
        builder.append(System.lineSeparator());
        builder.append("Cats:");
        if (cats.isEmpty()) {
            builder.append("none");
        } else {
            for (Cat cat : cats) {
                builder.append(" ").append(cat.getName());
            }
        }
        builder.append(System.lineSeparator());

        builder.append("Toys: ").append(toys.size()).append(" Softness: ").append(sumSoftness());
        builder.append(System.lineSeparator());

        return builder.toString();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    @Override
    public Collection<Cat> getCats() {
        return new ArrayList<>(this.cats);
    }

    @Override
    public Collection<Toy> getToys() {
        return new ArrayList<>(this.toys);
    }
}
