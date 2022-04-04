package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut{
    private static final double DEFAULT_OXYGEN = 70;
    private static final int DECREASE_OXYGEN = 5;

    public Biologist(String name) {
        super(name, DEFAULT_OXYGEN);
    }

    @Override
    public void breath() {
        if (this.getOxygen() - DECREASE_OXYGEN < 0) {
            this.setOxygen(0);
        } else {
            this.setOxygen(this.getOxygen() - DECREASE_OXYGEN);
        }
    }
}
