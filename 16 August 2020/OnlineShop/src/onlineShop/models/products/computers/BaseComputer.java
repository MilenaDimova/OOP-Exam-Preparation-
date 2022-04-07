package onlineShop.models.products.computers;

import onlineShop.common.constants.OutputMessages;
import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;

public abstract class BaseComputer extends BaseProduct implements Computer{
    private List<Component> components;
    private List<Peripheral> peripherals;

    protected BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public List<Component> getComponents() {
        return Collections.unmodifiableList(this.components);
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return Collections.unmodifiableList(this.peripherals);
    }

    @Override
    public void addComponent(Component component) {
        if (this.components.contains(component)) {
            throw new IllegalArgumentException(String.format(EXISTING_COMPONENT,
                    component.getClass().getSimpleName(), this.getClass().getSimpleName(), this.getId()));
        }
        this.components.add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        Component componentToRemove = this.components.stream()
                .filter(c -> c.getClass().getSimpleName().equals(componentType))
                .findFirst()
                .orElse(null);

        if (this.components.isEmpty() || componentToRemove == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT,
                    componentType, this.getClass().getSimpleName(), this.getId()));
        }
        this.components.remove(componentToRemove);

        return componentToRemove;
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        if (this.peripherals.contains(peripheral)) {
            throw new IllegalArgumentException(String.format(EXISTING_PERIPHERAL,
                    peripheral.getClass().getSimpleName(), this.getClass().getSimpleName(), this.getId()));
        }
        this.peripherals.add(peripheral);
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Peripheral peripheralToRemove = this.peripherals.stream()
                .filter(p -> p.getClass().getSimpleName().equals(peripheralType))
                .findFirst()
                .orElse(null);

        if (this.peripherals.isEmpty() || peripheralToRemove == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL,
                    peripheralType, this.getClass().getSimpleName(), this.getId()));
        }
        this.peripherals.remove(peripheralToRemove);

        return peripheralToRemove;
    }

    @Override
    public double getPrice() {
        return this.getPrice() + this.components.stream().mapToDouble(Component::getPrice).sum()
                + this.peripherals.stream().mapToDouble(Peripheral::getPrice).sum();
    }

    @Override
    public double getOverallPerformance() {
        return  super.getOverallPerformance() +
                this.components.stream()
                        .mapToDouble(Component::getOverallPerformance)
                        .average()
                        .orElse(0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(System.lineSeparator());
        builder.append(" ").append(String.format(OutputMessages.COMPUTER_COMPONENTS_TO_STRING, this.components.size()));
        builder.append(System.lineSeparator());
        for (Component component : this.components) {
            builder.append("  ").append(component).append(System.lineSeparator());
        }
        double avgOverallPerformance = this.peripherals.stream()
                .mapToDouble(Peripheral::getOverallPerformance).average().orElse(0);
        builder.append(" ").append(String.format(OutputMessages.COMPUTER_PERIPHERALS_TO_STRING, this.peripherals.size(),
                avgOverallPerformance)).append(System.lineSeparator());
        for (Peripheral peripheral : this.peripherals) {
            builder.append("  ").append(peripheral).append(System.lineSeparator());
        }

        return builder.toString().trim();
    }
}
