package fairyShop.core;

import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static fairyShop.common.ConstantMessages.*;
import static fairyShop.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{
    private HelperRepository helperRepository;
    private PresentRepository presentRepository;
    private Shop shop;

    public ControllerImpl() {
        this.helperRepository = new HelperRepository();
        this.presentRepository = new PresentRepository();
        this.shop = new ShopImpl();
    }

    @Override
    public String addHelper(String type, String helperName) {
        Helper helper;
        switch (type) {
            case "Happy":
                helper = new Happy(helperName);
                break;
            case "Sleepy":
                helper = new Sleepy(helperName);
                break;
            default:
                throw new IllegalArgumentException(HELPER_TYPE_DOESNT_EXIST);
        }
        this.helperRepository.add(helper);

        return String.format(ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Helper helper = this.helperRepository.findByName(helperName);
        if (helper == null) {
            throw new IllegalArgumentException(HELPER_DOESNT_EXIST);
        }
        Instrument instrument = new InstrumentImpl(power);
        helper.addInstrument(instrument);

        return String.format(SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        this.presentRepository.add(present);

        return String.format(SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        List<Helper> helpersWithEnoughEnergy = this.helperRepository.getModels().stream()
                .filter(h -> h.getEnergy() > 50).collect(Collectors.toList());
        if (helpersWithEnoughEnergy.isEmpty()) {
            throw new IllegalArgumentException(NO_HELPER_READY);
        }
        Present present = this.presentRepository.findByName(presentName);
        long brokenInstruments = 0;
        for (Helper helper : helpersWithEnoughEnergy) {
            shop.craft(present, helper);
            brokenInstruments += helper.getInstruments().stream().filter(i -> i.getPower() == 0).count();
        }
        StringBuilder builder = new StringBuilder();
        if (present.isDone()) {
            builder.append(String.format(PRESENT_DONE, presentName, "done"));
        } else {
            builder.append(String.format(PRESENT_DONE, presentName, "not done"));
        }

        builder.append(String.format(COUNT_BROKEN_INSTRUMENTS, brokenInstruments));

        return builder.toString();
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();
        long craftedPresent = this.presentRepository.getModels().stream().filter(Present::isDone).count();
        builder.append(String.format("%d presents are done!", craftedPresent));
        builder.append(System.lineSeparator());
        builder.append("Helpers info:").append(System.lineSeparator());
        long notBrokenInstruments = 0;
        for (Helper helper : this.helperRepository.getModels()) {
            builder.append(String.format("Name: %s", helper.getName())).append(System.lineSeparator());
            builder.append(String.format("Energy: %d", helper.getEnergy())).append(System.lineSeparator());
            notBrokenInstruments = helper.getInstruments().stream().filter(i -> i.getPower() > 0).count();
            builder.append(String.format("Instruments: %d not broken left", notBrokenInstruments));
            builder.append(System.lineSeparator());
        }

        return builder.toString().trim();
    }
}
