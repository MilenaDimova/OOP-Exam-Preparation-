package onlineShop.core;

import onlineShop.core.interfaces.Controller;
import onlineShop.models.products.components.*;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.*;

public class ControllerImpl implements Controller {
    private List<Computer> computers;

    public ControllerImpl() {
        this.computers = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        Computer computer;
        switch (computerType) {
            case "DesktopComputer":
                computer = new DesktopComputer(id, manufacturer, model, price);
                break;
            case "Laptop":
                computer = new Laptop(id, manufacturer, model, price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMPUTER_TYPE);
        }

        checkComputerById(id);
        this.computers.add(computer);

        return String.format(ADDED_COMPUTER, id);
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model,
                                double price, double overallPerformance, String connectionType) {
        Peripheral peripheral;
        switch (peripheralType) {
            case "Headset":
                peripheral = new Headset(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Keyboard":
                peripheral = new Keyboard(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Monitor":
                peripheral = new Monitor(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Mouse":
                peripheral = new Mouse(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            default:
                throw new IllegalArgumentException(INVALID_PERIPHERAL_TYPE);
        }

        Computer computer = getComputerById(computerId);
        if (computer.getPeripherals().stream().anyMatch(p -> p.getId() == id)) {
            throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
        }

        computer.addPeripheral(peripheral);

        return String.format(ADDED_PERIPHERAL, peripheralType, id, computerId);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        Computer computer = getComputerById(computerId);
        Peripheral peripheral = computer.removePeripheral(peripheralType);

        return String.format(REMOVED_PERIPHERAL, peripheralType, peripheral.getId());
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer,
                               String model, double price, double overallPerformance, int generation) {
        Component component;
        switch (componentType) {
            case "CentralProcessingUnit":
                component = new CentralProcessingUnit(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "Motherboard":
                component = new Motherboard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "PowerSupply":
                component = new PowerSupply(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "RandomAccessMemory":
                component = new RandomAccessMemory(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "SolidStateDrive":
                component = new SolidStateDrive(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "VideoCard":
                component = new VideoCard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMPONENT_TYPE);
        }

        Computer computer = getComputerById(computerId);
        if (computer.getComponents().stream().anyMatch(c -> c.getId() == id)) {
            throw new IllegalArgumentException(EXISTING_COMPONENT_ID);
        }

        computer.addComponent(component);

        return String.format(ADDED_COMPONENT, componentType, id, computer.getId());
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        Computer computer = getComputerById(computerId);
        Component component = computer.removeComponent(componentType);

        return String.format(REMOVED_COMPONENT, componentType, component.getId());
    }

    @Override
    public String buyComputer(int id) {
        Computer computer = getComputerById(id);
        this.computers.remove(computer);

        return computer.toString();
    }

    @Override
    public String BuyBestComputer(double budget) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getPrice() <= budget)
                .max(Comparator.comparing(Computer::getOverallPerformance))
                .orElseThrow(() -> new IllegalArgumentException(String.format(CAN_NOT_BUY_COMPUTER, budget)));

        this.buyComputer(computer.getId());

        return computer.toString();
    }

    @Override
    public String getComputerData(int id) {
        return getComputerById(id).toString();
    }

    private Computer getComputerById(int id) {
        return this.computers.stream().filter(c -> c.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID));
    }

    private void checkComputerById(int id) {
        Optional<Computer> computer = this.computers.stream().filter(c -> c.getId() == id).findAny();
        if (computer.isPresent()) {
            throw new IllegalArgumentException(EXISTING_COMPUTER_ID);
        }
    }
}
