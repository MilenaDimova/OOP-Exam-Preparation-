package christmasRaces.core;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.common.OutputMessages;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerImpl implements Controller {
    private Repository<Driver> driverRepository;
    private Repository<Car> carRepository;
    private Repository<Race> raceRepository;

    public ControllerImpl(Repository<Driver> driverRepository, Repository<Car> carRepository, Repository<Race> raceRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driver) {
        Driver existingDriver = this.driverRepository.getByName(driver);

        if (existingDriver != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS, driver));
        }

        Driver createdNewDriver = new DriverImpl(driver);
        this.driverRepository.add(createdNewDriver);

        return String.format(OutputMessages.DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {

        Car existingCar = this.carRepository.getByName(model);

        if (existingCar != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
        }

        Car createdNewCar = null;
        String carType = "";

        switch (type) {
            case "Muscle":
                createdNewCar = new MuscleCar(model, horsePower);
                carType = "MuscleCar";
                break;
            case "Sports":
                createdNewCar = new SportsCar(model, horsePower);
                carType = "SportsCar";
                break;
        }

        this.carRepository.add(createdNewCar);
        return String.format(OutputMessages.CAR_CREATED, carType, model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {

        Driver existingDriver = this.driverRepository.getByName(driverName);
        if (existingDriver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }

        Car existingCar = this.carRepository.getByName(carModel);
        if (existingCar == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND, carModel));
        }

        existingDriver.addCar(existingCar);

        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {

        Race race = this.raceRepository.getByName(raceName);
        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }

        Driver driver = this.driverRepository.getByName(driverName);
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }

        race.addDriver(driver);
        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {

        Race race = this.raceRepository.getByName(raceName);
        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }

        int driversCount = race.getDrivers().size();

        if (driversCount < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID, raceName, 3));
        }

        int laps = race.getLaps();
        Collection<Driver> drivers = race.getDrivers();

        List<Driver> fastestThreeDrivers = drivers.stream()
                .sorted((d1, d2) -> Double.compare(d2.getCar().calculateRacePoints(laps),
                        d1.getCar().calculateRacePoints(laps)))
                .limit(3)
                .collect(Collectors.toList());

        this.raceRepository.remove(race);

        Driver firstOne = fastestThreeDrivers.get(0);
        Driver secondOne = fastestThreeDrivers.get(1);
        Driver thirdOne = fastestThreeDrivers.get(2);

        StringBuilder builder = new StringBuilder();
        builder.append(String.format(OutputMessages.DRIVER_FIRST_POSITION, firstOne.getName(), raceName));
        builder.append(System.lineSeparator());
        builder.append(String.format(OutputMessages.DRIVER_SECOND_POSITION, secondOne.getName(), raceName));
        builder.append(System.lineSeparator());
        builder.append(String.format(OutputMessages.DRIVER_THIRD_POSITION, thirdOne.getName(), raceName));

        return builder.toString();
    }

    @Override
    public String createRace(String name, int laps) {

        Race race = this.raceRepository.getByName(name);
        if (race != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }

        Race raceToAdd = new RaceImpl(name, laps);
        this.raceRepository.add(raceToAdd);

        return String.format(OutputMessages.RACE_CREATED, name);
    }
}
