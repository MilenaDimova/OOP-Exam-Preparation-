package catHouse.core;

import catHouse.common.HouseType;
import catHouse.common.ToyType;
import catHouse.entities.cat.BaseCat;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.Repository;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Toy> toys;
    private Collection<House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<House>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house = null;
        if (type.equals(HouseType.ShortHouse.toString())) {
            house = new ShortHouse(name);
        }

        if (type.equals(HouseType.LongHouse.toString())) {
            house = new LongHouse(name);
        }

        if (house == null) {
            throw new NullPointerException(INVALID_HOUSE_TYPE);
        }

        houses.add(house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy = null;
        if (type.equals(ToyType.Ball.toString())) {
            toy = new Ball();
        }

        if (type.equals(ToyType.Mouse.toString())) {
            toy = new Mouse();
        }

        if (toy == null) {
            throw new IllegalArgumentException(INVALID_TOY_TYPE);
        }

        toys.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        House houseToAddToy = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst()
                .orElse(null);

        Toy toyToAdd = toys.findFirst(toyType);

        if (toyToAdd == null) {
            throw new IllegalArgumentException(String.format(NO_TOY_FOUND, toyType));
        }

        houseToAddToy.buyToy(toyToAdd);
        toys.removeToy(toyToAdd);

        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        BaseCat cat = null;
        if (catType.equals("ShorthairCat")) {
            cat = new ShorthairCat(catName, catBreed, price);
        }

        if (catType.equals("LonghairCat")) {
            cat = new LonghairCat(catName, catBreed, price);
        }

        if (cat == null) {
            throw new IllegalArgumentException(INVALID_CAT_TYPE);
        }

        House house = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst().orElse(null);

        String result = "";
        if (house.getClass().getSimpleName().equals(cat.getCanLiveInHouse().toString())) {
            house.addCat(cat);
            result = String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
        } else {
            result = UNSUITABLE_HOUSE;
        }

        return result;
    }

    @Override
    public String feedingCat(String houseName) {
        House houseWithCatsToFeed = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst()
                .orElse(null);

        houseWithCatsToFeed.feeding();
        return String.format(FEEDING_CAT, houseWithCatsToFeed.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House house = this.houses.stream()
                .filter(h -> h.getName().equals(houseName))
                .findFirst()
                .orElse(null);
        double catsSum =  house.getCats().stream().mapToDouble(Cat::getPrice).sum();
        double toysSum = house.getToys().stream().mapToDouble(Toy::getPrice).sum();

        return String.format(VALUE_HOUSE, houseName, catsSum + toysSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        for (House house : houses) {
            builder.append(house.getStatistics());
        }

        return builder.toString().trim();
    }
}
