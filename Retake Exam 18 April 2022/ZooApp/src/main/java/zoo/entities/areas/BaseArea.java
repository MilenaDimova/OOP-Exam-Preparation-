package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static zoo.common.ExceptionMessages.*;

public abstract class BaseArea implements Area{
    private String name;
    private int capacity;
    private Collection<Food> foods;
    private Collection<Animal> animals;

    public BaseArea(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.foods = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(AREA_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return this.foods;
    }

    @Override
    public int sumCalories() {
        return this.foods.stream().mapToInt(Food::getCalories).sum();
    }

    @Override
    public void addAnimal(Animal animal) {
        if (this.capacity <= this.animals.size()) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }
        this.animals.add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
    }

    @Override
    public void addFood(Food food) {
        this.foods.add(food);
    }

    @Override
    public void feed() {
        for (Animal animal : this.animals) {
            animal.eat();
        }
    }

    @Override
    public String getInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s (%s):", this.name, this.getClass().getSimpleName()));
        builder.append(System.lineSeparator());
        builder.append("Animals: ");
        if (this.animals.isEmpty()) {
            builder.append("none");
        } else {
            builder.append(this.animals.stream().map(Animal::getName).collect(Collectors.joining(" ")).trim());
        }
        builder.append(System.lineSeparator());
        builder.append(String.format("Foods: %d", this.foods.size()));
        builder.append(System.lineSeparator());
        builder.append(String.format("Calories: %d", this.sumCalories()));

        return builder.toString().trim();
    }
}
